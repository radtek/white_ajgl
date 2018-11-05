 package com.taiji.pubsec.ajgl.dbtx.executor;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajgl.dbtx.task.util.Constant;
import com.taiji.pubsec.ajgl.dbtx.task.util.DbtxComponent;
import com.taiji.pubsec.ajgl.dbtx.task.util.susobj.pojo.SusObjRule;
import com.taiji.pubsec.ajqlc.alert.model.AlertMessage;
import com.taiji.pubsec.ajqlc.alert.model.AlertRule;
import com.taiji.pubsec.ajqlc.alert.service.IAlertMessageService;
import com.taiji.pubsec.ajqlc.alert.service.IAlertRuleService;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageForm;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageFormService;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.model.DocKouYaJueDingNew2012;
import com.taiji.pubsec.ajqlc.sla.model.DocZhengJvBaoQuanJueDingNew;
import com.taiji.pubsec.ajqlc.sla.service.IArchivedFileService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalPersonService;
import com.taiji.pubsec.ajqlc.util.ServiceConstant;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IAccountService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.common.tool.comet.model.DefaultHintMsg;
import com.taiji.pubsec.common.tool.comet.service.CometdPushService;
import com.taiji.pubsec.common.tool.mission.task.core.MainPeriodTask;
import com.taiji.pubsec.common.tool.mission.task.core.MainTask;
import com.taiji.pubsec.common.tool.mission.task.core.PeriodTaskExecutor;
import com.taiji.pubsec.common.tool.mission.task.core.Task;
import com.taiji.pubsec.common.tool.mission.task.core.TaskGroup;
import com.taiji.pubsec.common.tool.mission.task.service.MainPeriodTaskService;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;
import com.taiji.pubsec.common.tool.spring.SpringContextUtil;

@Service
@Transactional
public class SusObjExecutor implements PeriodTaskExecutor, DbtxComponent{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SusObjExecutor.class);
	
	private static Map<String, ScheduledExecutorService> schedules = new HashMap<String, ScheduledExecutorService>();

	public static final String EXECUTOR_TASK_RULE_TYPE = Constant.TASK_TYPE_SUSOBJ + "_Executor";
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@Resource
	private IArchivedFileService archivedFileService;
	@Resource
	private IAccountService accountManageService;
	@Resource
	private CometdPushService cometdPushService;
	@Resource
	private ICriminalPersonService criminalPersonService;
	@Resource
	private ICriminalCaseService criminalCaseService;
	@Resource
	private IInStorageFormService inStorageFormService;
	@Resource
	private IPersonService personService;
	@Resource
	private IAlertMessageService alertMessageService;
	@Resource
	private IAlertRuleService alertRuleService;
	@Resource
	private MainPeriodTaskService mainPeriodTaskService;
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Task task) {
		
		LOGGER.debug("schedule数量：{}", schedules.size());
		if(!(task instanceof MainPeriodTask)){
			return false ;
		}
		
		MainPeriodTask mtsk =  (MainPeriodTask) dao.findById(MainPeriodTask.class, ((MainPeriodTask)task).getId());
		if(schedules.get(mtsk.getId())!=null){
			LOGGER.debug("schedules已存在该task：{}", mtsk.getId());
			return true ;
		}
		
		if(!support(mtsk.getMainTask().getTaskGroup().getName())){
			//返回true chain会终止，返回false时chain继续执行
			return false ;
		}
		
		TaskGroup tg = mtsk.getMainTask().getTaskGroup();
		InStorageForm isf = inStorageFormService.findInStorageFormByDocIdAndDocType(tg.getBusTargetId(), tg.getBusType());
		AlertRule rule = alertRuleService.findByid(Constant.RULE_TYPE_ID_JSJZ);
		if(isf != null || Constant.RULE_STATUS_ON.equals(rule.getStatus())){
			LOGGER.debug("该任务被预警规则或其他终止预警条件终止：{}", mtsk.getId());
			MainTask mt = mtsk.getMainTask();
			mt.setStatus(MainTask.MAINTASK_STATUS_EXPIRE);
			mt.update() ;
			return true ;
		}
		
		Calendar nowDate = Calendar.getInstance();
		nowDate.setTime(new Date());
		Long nowL = nowDate.getTimeInMillis() ;
		
		Long executingTime = mtsk.getMainTask().getStartTime();
		
		//如果该任务此时已过期，直接推送该预警消息，然后将该预警任务状态改为已完成
		if(executingTime < nowL){
			if(MainTask.MAINTASK_STATUS_ON.equals(mtsk.getMainTask().getStatus())){
				SusObjRule executedRule = (SusObjRule)TaskPojoJSONFmtUtil.getPojo(mtsk.getMainTask().getTaskExecutedRule(), SusObjRule.class) ;
				
				if(executedRule.isAlertForSys() || executedRule.isAlertForPDA() || executedRule.isAlertForText()){
					DecimalFormat df = new java.text.DecimalFormat("##.##"); 
					double t = 24*60 - executedRule.getAheadTime();
					String tempTime = df.format(t/60);
					String context = "";
					Person person = null;
					//通过文书查询录入人并推送
					if(mtsk.getMainTask().getTaskGroup().getBusType().equals(DocKouYaJueDingNew2012.class.getName())){
						DocKouYaJueDingNew2012 doc = archivedFileService.findDocKouYaJueDingNew2012ById(mtsk.getMainTask().getTaskGroup().getBusTargetId());
						person = personService.findPersonByCode(doc.getInputpsnId());
						CriminalPerson cp = criminalPersonService.findById(doc.getPersonid());
						CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(doc.getCaseid());
						String suspectName = "";
						String caseName = "";
						if(cbc != null){
							caseName = cbc.getCaseName();
						}
						context = doc.getCaseid() + caseName + "，涉案嫌疑人：" + executedRule.getSuspectName() + "的扣押决定书已生效达" + tempTime + "小时，请及时办理涉案物品入库";
					}else if(mtsk.getMainTask().getTaskGroup().getBusType().equals(DocZhengJvBaoQuanJueDingNew.class.getName())){
						DocZhengJvBaoQuanJueDingNew doc = archivedFileService.findDocZhengJvBaoQuanJueDingNewById(mtsk.getMainTask().getTaskGroup().getBusTargetId());
						person = personService.findPersonByCode(doc.getInputpsnId());
						CriminalPerson cp = criminalPersonService.findById(doc.getPersonid());
						CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(doc.getCaseid());
						String suspectName = "";
						String caseName = "";
						if(cbc != null){
							caseName = cbc.getCaseName();
						}
						if(cp != null){
							suspectName = cp.getName();
						}
						context = doc.getCaseid() + caseName + "，涉案嫌疑人：" + executedRule.getSuspectName() + "的证据保全清单已生效达" + tempTime + "小时，请及时办理涉案物品入库";
					}
					if(person != null){
						AlertMessage message = new AlertMessage();
						message.setAlertRule(alertRuleService.findByid("0000000013008"));
						message.setCaseCode(executedRule.getCaseCode());
						message.setContent(context);
						message.setCreatedTime(new Date());
						message.setReceiver(person);
						message.setStatus("0");
						message.setSuspectId(executedRule.getSuspectId());
						message.setSuspectName(executedRule.getSuspectName());
						alertMessageService.saveMessage(message);
						LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message.getId(),person.getId()});
						
						if(executedRule.isAlertForSys()){
							LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
							DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+context+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
							cometdPushService.pushHint(person.getAccount().getAccountName(), msg);
							LOGGER.debug("预警消息接收人id（发pc端）：{}", person.getId());
						}
						if(executedRule.isAlertForPDA()){
							LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
							String context1 = "有新的消息，请查收";
							DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
							cometdPushService.pushHint(person.getAccount().getAccountName() + "PDA", msg);
							LOGGER.debug("预警消息接收人id（发手机端）：{}", person.getId());
						}
						if(executedRule.isAlertForText()){
							LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
							//TODO
						}
					}
					
					//通过文书获取对应案件的法制审核人
					Person personFazhi = archivedFileService.findPersonByDocIdAndDocType(mtsk.getMainTask().getTaskGroup().getBusTargetId(),mtsk.getMainTask().getTaskGroup().getBusType());
					if(personFazhi != null){
						AlertMessage message1 = new AlertMessage();
						message1.setAlertRule(alertRuleService.findByid("0000000013008"));
						message1.setCaseCode(executedRule.getCaseCode());
						message1.setContent(context);
						message1.setCreatedTime(new Date());
						message1.setReceiver(personFazhi);
						message1.setStatus("0");
						message1.setSuspectId(executedRule.getSuspectId());
						message1.setSuspectName(executedRule.getSuspectName());
						alertMessageService.saveMessage(message1);
						LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message1.getId(),personFazhi.getId()});
						
						if(executedRule.isAlertForSys()){
							LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
							DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+context+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
							cometdPushService.pushHint(personFazhi.getAccount().getAccountName(), msg);
							LOGGER.debug("预警消息接收人id（发pc端）：{}", personFazhi.getId());
						}
						if(executedRule.isAlertForPDA()){
							LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
							String context1 = "有新的消息，请查收";
							DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
							cometdPushService.pushHint(personFazhi.getAccount().getAccountName() + "PDA", msg);
							LOGGER.debug("预警消息接收人id（发手机端）：{}", personFazhi.getId());
						}
						if(executedRule.isAlertForText()){
							LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
							//TODO
						}
					}
				}
				
				MainTask mt = mtsk.getMainTask();
				mt.setStatus(MainTask.MAINTASK_STATUS_FINISH);
				mt.update();
				LOGGER.debug("该任务生成预警消息后更新状态为已完成：{}", mtsk.getId());
				return true ;
			}
			return true;
		}
		
		if(executingTime > nowL + Integer.valueOf(ServiceConstant.YJ_DSTQSJ)*60*60*1000){
			LOGGER.debug("未到任务执行前{}小时", Integer.valueOf(ServiceConstant.YJ_DSTQSJ));
			return true ;
		}
		
		generateSchedule(mtsk.getId(), executingTime) ;
		
		return true;
	}
	
	private void generateSchedule(final String mainTaskId, Long executingTime){
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		schedules.put(mainTaskId, scheduledExecutorService);
		LOGGER.debug("创建schedule：{}", mainTaskId);
		Long delayTime = executingTime - new Date().getTime();
		final MainPeriodTaskService service = SpringContextUtil.getApplicationContext().getBean(MainPeriodTaskService.class) ;
		Runnable runnable = new Runnable() {
			@Override
		    public void run() {

				MainPeriodTask mtsk = service.findMainPeriodTaskById(mainTaskId);
				LOGGER.debug("进入run()方法：{}", mtsk.getId());
				TaskGroup tg = mtsk.getMainTask().getTaskGroup();
				InStorageForm isf = inStorageFormService.findInStorageFormByDocIdAndDocType(tg.getBusTargetId(), tg.getBusType());
				AlertRule rule = alertRuleService.findByid(Constant.RULE_TYPE_ID_JSJZ);
				if(isf != null || Constant.RULE_STATUS_ON.equals(rule.getStatus()) || 
						MainTask.MAINTASK_STATUS_EXPIRE.equals(mtsk.getMainTask().getStatus()) || 
						MainTask.MAINTASK_STATUS_FINISH.equals(mtsk.getMainTask().getStatus())){
					LOGGER.debug("进入run()方法后，该任务被预警规则或其他终止预警条件或该任务状态为已完成或已终止终止：{}", mtsk.getId());
					MainTask mt = mtsk.getMainTask();
					mt.setStatus(MainTask.MAINTASK_STATUS_EXPIRE);
					mt.update() ;
					schedules.get(mainTaskId).shutdown();
					schedules.remove(mainTaskId) ;
					return;
				}
				
				synchronized (schedules) {
					SusObjRule executedRule = (SusObjRule)TaskPojoJSONFmtUtil.getPojo(mtsk.getMainTask().getTaskExecutedRule(), SusObjRule.class) ;
					
					if(executedRule.isAlertForSys() || executedRule.isAlertForPDA() || executedRule.isAlertForText()){
						DecimalFormat df = new java.text.DecimalFormat("##.##"); 
						double t = 24*60 - executedRule.getAheadTime();
						String tempTime = df.format(t/60);
						String context = "";
						Person person = null;
						//通过文书查询录入人并推送
						if(mtsk.getMainTask().getTaskGroup().getBusType().equals(DocKouYaJueDingNew2012.class.getName())){
							DocKouYaJueDingNew2012 doc = archivedFileService.findDocKouYaJueDingNew2012ById(mtsk.getMainTask().getTaskGroup().getBusTargetId());
							person = personService.findPersonByCode(doc.getInputpsnId());
							CriminalPerson cp = criminalPersonService.findById(doc.getPersonid());
							CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(doc.getCaseid());
							String suspectName = "";
							String caseName = "";
							if(cbc != null){
								caseName = cbc.getCaseName();
							}
							context = doc.getCaseid() + caseName + "，涉案嫌疑人：" + executedRule.getSuspectName() + "的扣押决定书已生效达" + tempTime + "小时，请及时办理涉案物品入库";
						}else if(mtsk.getMainTask().getTaskGroup().getBusType().equals(DocZhengJvBaoQuanJueDingNew.class.getName())){
							DocZhengJvBaoQuanJueDingNew doc = archivedFileService.findDocZhengJvBaoQuanJueDingNewById(mtsk.getMainTask().getTaskGroup().getBusTargetId());
							person = personService.findPersonByCode(doc.getInputpsnId());
							CriminalPerson cp = criminalPersonService.findById(doc.getPersonid());
							CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(doc.getCaseid());
							String suspectName = "";
							String caseName = "";
							if(cbc != null){
								caseName = cbc.getCaseName();
							}
							if(cp != null){
								suspectName = cp.getName();
							}
							context = doc.getCaseid() + caseName + "，涉案嫌疑人：" + executedRule.getSuspectName() + "的证据保全清单已生效达" + tempTime + "小时，请及时办理涉案物品入库";
						}
						if(person != null){
							AlertMessage message = new AlertMessage();
							message.setAlertRule(alertRuleService.findByid("0000000013008"));
							message.setCaseCode(executedRule.getCaseCode());
							message.setContent(context);
							message.setCreatedTime(new Date());
							message.setReceiver(person);
							message.setStatus("0");
							message.setSuspectId(executedRule.getSuspectId());
							message.setSuspectName(executedRule.getSuspectName());
							alertMessageService.saveMessage(message);
							LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message.getId(),person.getId()});
							
							if(executedRule.isAlertForSys()){
								LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
								DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+context+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
								cometdPushService.pushHint(person.getAccount().getAccountName(), msg);
								LOGGER.debug("预警消息接收人id（发pc端）：{}", person.getId());
							}
							if(executedRule.isAlertForPDA()){
								LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
								String context1 = "有新的消息，请查收";
								DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
								cometdPushService.pushHint(person.getAccount().getAccountName() + "PDA", msg);
								LOGGER.debug("预警消息接收人id（发手机端）：{}", person.getId());
							}
							if(executedRule.isAlertForText()){
								LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
								//TODO
							}
						}
						
						//通过文书获取对应案件的法制审核人
						Person personFazhi = archivedFileService.findPersonByDocIdAndDocType(mtsk.getMainTask().getTaskGroup().getBusTargetId(),mtsk.getMainTask().getTaskGroup().getBusType());
						if(personFazhi != null){
							AlertMessage message1 = new AlertMessage();
							message1.setAlertRule(alertRuleService.findByid("0000000013008"));
							message1.setCaseCode(executedRule.getCaseCode());
							message1.setContent(context);
							message1.setCreatedTime(new Date());
							message1.setReceiver(personFazhi);
							message1.setStatus("0");
							message1.setSuspectId(executedRule.getSuspectId());
							message1.setSuspectName(executedRule.getSuspectName());
							alertMessageService.saveMessage(message1);
							LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message1.getId(),personFazhi.getId()});
							
							if(executedRule.isAlertForSys()){
								LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
								DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+context+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
								cometdPushService.pushHint(personFazhi.getAccount().getAccountName(), msg);
								LOGGER.debug("预警消息接收人id（发pc端）：{}", personFazhi.getId());
							}
							if(executedRule.isAlertForPDA()){
								LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
								String context1 = "有新的消息，请查收";
								DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
								cometdPushService.pushHint(personFazhi.getAccount().getAccountName() + "PDA", msg);
								LOGGER.debug("预警消息接收人id（发手机端）：{}", personFazhi.getId());
							}
							if(executedRule.isAlertForText()){
								LOGGER.debug("SusObjRule任务AlertForSys(id："+mtsk.getId()+")执行");
								//TODO
							}
						}
					}
					
					MainTask mt = mtsk.getMainTask();
					mt.setStatus(MainTask.MAINTASK_STATUS_FINISH);
					mt.update();
					LOGGER.debug("该任务生成预警消息后更新状态为已完成：{}", mtsk.getId());
				}
				schedules.get(mainTaskId).shutdown();
				schedules.remove(mainTaskId) ;
	    	}
		};
		scheduledExecutorService.schedule(runnable, delayTime, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void removeAllSchedules() {
		for(ScheduledExecutorService schedule : schedules.values()){
			schedule.shutdown();
		}
		schedules.clear();
	}

	@Override
	public boolean support(String name) {
		if(EXECUTOR_TASK_RULE_TYPE.equals(name + "_Executor")){
			return true ;
		}
		return false;
	}

}
