package com.taiji.pubsec.ajgl.dbtx.executor;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajgl.dbtx.task.util.Constant;
import com.taiji.pubsec.ajgl.dbtx.task.util.DbtxComponent;
import com.taiji.pubsec.ajgl.dbtx.task.util.subpoena.pojo.SubpoenaRule;
import com.taiji.pubsec.ajqlc.alert.model.AlertMessage;
import com.taiji.pubsec.ajqlc.alert.model.AlertRule;
import com.taiji.pubsec.ajqlc.alert.service.IAlertMessageService;
import com.taiji.pubsec.ajqlc.alert.service.IAlertRuleService;
import com.taiji.pubsec.ajqlc.sla.model.DocChuanHuanNew;
import com.taiji.pubsec.ajqlc.sla.model.DocChuanHuanNewXingShi2012;
import com.taiji.pubsec.ajqlc.sla.service.IArchivedFileService;
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
import com.taiji.pubsec.common.tool.mission.task.service.MainPeriodTaskService;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;
import com.taiji.pubsec.common.tool.spring.SpringContextUtil;

@Service
@Transactional
public class SubpoenaExecutor implements PeriodTaskExecutor, DbtxComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubpoenaExecutor.class);

	private static Map<String, ScheduledExecutorService> schedules = new HashMap<String, ScheduledExecutorService>();

	public static final String EXECUTOR_TASK_RULE_TYPE = Constant.TASK_TYPE_SUBPOENA + "_Executor";
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	JdbcTemplate jdbcTemplate;

	@Resource
	private IArchivedFileService archivedFileService;
	@Resource
	private IAccountService accountManageService;
	@Resource
	private CometdPushService cometdPushService;
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
		if (!(task instanceof MainPeriodTask)) {
			return false;
		}

		MainPeriodTask mtsk =  (MainPeriodTask) dao.findById(MainPeriodTask.class, ((MainPeriodTask)task).getId());
		if (schedules.get(mtsk.getId()) != null) {
			LOGGER.debug("schedules已存在该task：{}", mtsk.getId());
			return true;
		}

		if (!support(mtsk.getMainTask().getTaskGroup().getName())) {
			// 返回true chain会终止，返回false时chain继续执行
			return false;
		}
		
		AlertRule rule = alertRuleService.findByid(Constant.RULE_TYPE_ID_CH);
		if(Constant.RULE_STATUS_ON.equals(rule.getStatus())){
			LOGGER.debug("该任务被预警规则或其他终止预警条件终止：{}", mtsk.getId());
			MainTask mt = mtsk.getMainTask();
			mt.setStatus(MainTask.MAINTASK_STATUS_EXPIRE);
			mt.update();
			return true;
		}

		Calendar nowDate = Calendar.getInstance();
		nowDate.setTime(new Date());
		Long nowL = nowDate.getTimeInMillis();

		Long executingTime = mtsk.getMainTask().getStartTime();

		//如果该任务此时已过期，直接推送该预警消息，然后将该预警任务状态改为已完成
		if (executingTime < nowL) {
			if(MainTask.MAINTASK_STATUS_ON.equals(mtsk.getMainTask().getStatus())){
				SubpoenaRule executedRule = (SubpoenaRule)TaskPojoJSONFmtUtil.getPojo(mtsk.getMainTask().getTaskExecutedRule(), SubpoenaRule.class) ;
				
				if(executedRule.isAlertForSys() || executedRule.isAlertForPDA() || executedRule.isAlertForText()){
					//通过文书查询录入人并推送
					if(mtsk.getMainTask().getTaskGroup().getBusType().equals(DocChuanHuanNew.class.getName())){
						DocChuanHuanNew doc = archivedFileService.findDocChuanHuanNewById(mtsk.getMainTask().getTaskGroup().getBusTargetId());
						Person person = personService.findPersonByCode(doc.getInputpsnId());
						if(person != null){
							AlertMessage message = new AlertMessage();
							message.setAlertRule(alertRuleService.findByid("0000000013001"));
							message.setCaseCode(executedRule.getCaseCode());
							message.setContent(executedRule.getContext());
							message.setCreatedTime(new Date());
							message.setReceiver(person);
							message.setStatus("0");
							message.setSuspectId(executedRule.getPersonId());
							message.setSuspectName(executedRule.getSuspectName());
							alertMessageService.saveMessage(message);
							LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message.getId(),person.getId()});
							
							if(executedRule.isAlertForSys()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+executedRule.getContext()+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
								if(person.getAccount() != null){
									cometdPushService.pushHint(person.getAccount().getAccountName(), msg);
									LOGGER.debug("预警消息接收人id（发pc端）：{}", person.getId());
								}
							}
							if(executedRule.isAlertForPDA()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								String context = "有新的消息，请查收";
								DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
								if(person.getAccount() != null){
									cometdPushService.pushHint(person.getAccount().getAccountName() + "PDA", msg);
									LOGGER.debug("预警消息接收人id（发手机端）：{}", person.getId());
								}
							}
							if(executedRule.isAlertForText()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								//TODO
							}
						}
					}else if(mtsk.getMainTask().getTaskGroup().getBusType().equals(DocChuanHuanNewXingShi2012.class.getName())){
						DocChuanHuanNewXingShi2012 doc = archivedFileService.findDocChuanHuanNewXingShi2012ById(mtsk.getMainTask().getTaskGroup().getBusTargetId());
						Person person = personService.findPersonByCode(doc.getInputpsnId());
						if(person != null){
							AlertMessage message = new AlertMessage();
							message.setAlertRule(alertRuleService.findByid("0000000013001"));
							message.setCaseCode(executedRule.getCaseCode());
							message.setContent(executedRule.getContext());
							message.setCreatedTime(new Date());
							message.setReceiver(person);
							message.setStatus("0");
							message.setSuspectId(executedRule.getPersonId());
							message.setSuspectName(executedRule.getSuspectName());
							alertMessageService.saveMessage(message);
							LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message.getId(),person.getId()});
							
							if(executedRule.isAlertForSys()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+executedRule.getContext()+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
								if(person.getAccount() != null){
									cometdPushService.pushHint(person.getAccount().getAccountName(), msg);
									LOGGER.debug("预警消息接收人id（发pc端）：{}", person.getId());
								}
							}
							if(executedRule.isAlertForPDA()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								String context = "有新的消息，请查收";
								DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
								if(person.getAccount() != null){
									cometdPushService.pushHint(person.getAccount().getAccountName() + "PDA", msg);
									LOGGER.debug("预警消息接收人id（发手机端）：{}", person.getId());
								}
							}
							if(executedRule.isAlertForText()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								//TODO
							}
						}
					}
					
					//通过文书获取对应案件的法制审核人
					Person personFazhi = archivedFileService.findPersonByDocIdAndDocType(mtsk.getMainTask().getTaskGroup().getBusTargetId(),mtsk.getMainTask().getTaskGroup().getBusType());
					if(personFazhi != null){
						AlertMessage message = new AlertMessage();
						message.setAlertRule(alertRuleService.findByid("0000000013001"));
						message.setCaseCode(executedRule.getCaseCode());
						message.setContent(executedRule.getContext());
						message.setCreatedTime(new Date());
						message.setReceiver(personFazhi);
						message.setStatus("0");
						message.setSuspectId(executedRule.getPersonId());
						message.setSuspectName(executedRule.getSuspectName());
						alertMessageService.saveMessage(message);
						LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message.getId(),personFazhi.getId()});
						
						if(executedRule.isAlertForSys()){
							LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
							DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+executedRule.getContext()+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
							if(personFazhi.getAccount() != null){
								cometdPushService.pushHint(personFazhi.getAccount().getAccountName(), msg);
								LOGGER.debug("预警消息接收人id（发pc端）：{}", personFazhi.getId());
							}
						}
						if(executedRule.isAlertForPDA()){
							LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
							String context = "有新的消息，请查收";
							DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
							if(personFazhi.getAccount() != null){
								cometdPushService.pushHint(personFazhi.getAccount().getAccountName() + "PDA", msg);
								LOGGER.debug("预警消息接收人id（发手机端）：{}", personFazhi.getId());
							}
						}
						if(executedRule.isAlertForText()){
							LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
							//TODO
						}
					}
				}
				
				MainTask mt = mtsk.getMainTask();
				mt.setStatus(MainTask.MAINTASK_STATUS_FINISH);
				mt.update();
				LOGGER.debug("该任务生成预警消息后更新状态为已完成：{}", mtsk.getId());
				return true;
			}
			return true;
		}

		if (executingTime > nowL + Integer.valueOf(ServiceConstant.YJ_DSTQSJ) * 60 * 60 * 1000) {
			LOGGER.debug("未到任务执行前{}小时", Integer.valueOf(ServiceConstant.YJ_DSTQSJ));
			return true;
		}

		generateSchedule(mtsk.getId(), executingTime);

		return true;
	}

	private void generateSchedule(final String mainTaskId, Long executingTime){
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		schedules.put(mainTaskId, scheduledExecutorService);
		LOGGER.debug("创建schedule：{}", mainTaskId);
		Long delayTime = executingTime - new Date().getTime();
		final MainPeriodTaskService service = SpringContextUtil.getApplicationContext().getBean(MainPeriodTaskService.class) ;
		Runnable runnable = new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
		    public void run() {		

				MainPeriodTask mtsk = service.findMainPeriodTaskById(mainTaskId);
				LOGGER.debug("进入run()方法：{}", mtsk.getId());
				AlertRule rule = alertRuleService.findByid(Constant.RULE_TYPE_ID_CH);
				if(Constant.RULE_STATUS_ON.equals(rule.getStatus()) || 
						MainTask.MAINTASK_STATUS_EXPIRE.equals(mtsk.getMainTask().getStatus()) || 
						MainTask.MAINTASK_STATUS_FINISH.equals(mtsk.getMainTask().getStatus())){
					LOGGER.debug("进入run()方法后，该任务被预警规则或其他终止预警条件或该任务状态为已完成或已终止终止：{}", mtsk.getId());
					MainTask mt = mtsk.getMainTask();
					mt.setStatus(MainTask.MAINTASK_STATUS_EXPIRE);
					mt.update();
					schedules.get(mainTaskId).shutdown();
					schedules.remove(mainTaskId) ;
					return;
				}
				
				synchronized (schedules) {
					SubpoenaRule executedRule = (SubpoenaRule)TaskPojoJSONFmtUtil.getPojo(mtsk.getMainTask().getTaskExecutedRule(), SubpoenaRule.class) ;
					
					if(executedRule.isAlertForSys() || executedRule.isAlertForPDA() || executedRule.isAlertForText()){
						//通过文书查询录入人并推送
						if(mtsk.getMainTask().getTaskGroup().getBusType().equals(DocChuanHuanNew.class.getName())){
							DocChuanHuanNew doc = archivedFileService.findDocChuanHuanNewById(mtsk.getMainTask().getTaskGroup().getBusTargetId());
							Person person = personService.findPersonByCode(doc.getInputpsnId());
							if(person != null){
								AlertMessage message = new AlertMessage();
								message.setAlertRule(alertRuleService.findByid("0000000013001"));
								message.setCaseCode(executedRule.getCaseCode());
								message.setContent(executedRule.getContext());
								message.setCreatedTime(new Date());
								message.setReceiver(person);
								message.setStatus("0");
								message.setSuspectId(executedRule.getPersonId());
								message.setSuspectName(executedRule.getSuspectName());
								alertMessageService.saveMessage(message);
								LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message.getId(),person.getId()});
								
								if(executedRule.isAlertForSys()){
									LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
									DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+executedRule.getContext()+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
									if(person.getAccount() != null){
										cometdPushService.pushHint(person.getAccount().getAccountName(), msg);
										LOGGER.debug("预警消息接收人id（发pc端）：{}", person.getId());
									}
								}
								if(executedRule.isAlertForPDA()){
									LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
									String context = "有新的消息，请查收";
									DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
									if(person.getAccount() != null){
										cometdPushService.pushHint(person.getAccount().getAccountName() + "PDA", msg);
										LOGGER.debug("预警消息接收人id（发手机端）：{}", person.getId());
									}
								}
								if(executedRule.isAlertForText()){
									LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
									//TODO
								}
							}
						}else if(mtsk.getMainTask().getTaskGroup().getBusType().equals(DocChuanHuanNewXingShi2012.class.getName())){
							DocChuanHuanNewXingShi2012 doc = archivedFileService.findDocChuanHuanNewXingShi2012ById(mtsk.getMainTask().getTaskGroup().getBusTargetId());
							Person person = personService.findPersonByCode(doc.getInputpsnId());
							if(person != null){
								AlertMessage message = new AlertMessage();
								message.setAlertRule(alertRuleService.findByid("0000000013001"));
								message.setCaseCode(executedRule.getCaseCode());
								message.setContent(executedRule.getContext());
								message.setCreatedTime(new Date());
								message.setReceiver(person);
								message.setStatus("0");
								message.setSuspectId(executedRule.getPersonId());
								message.setSuspectName(executedRule.getSuspectName());
								alertMessageService.saveMessage(message);
								LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message.getId(),person.getId()});
								
								if(executedRule.isAlertForSys()){
									LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
									DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+executedRule.getContext()+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
									if(person.getAccount() != null){
										cometdPushService.pushHint(person.getAccount().getAccountName(), msg);
										LOGGER.debug("预警消息接收人id（发pc端）：{}", person.getId());
									}
								}
								if(executedRule.isAlertForPDA()){
									LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
									String context = "有新的消息，请查收";
									DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
									if(person.getAccount() != null){
										cometdPushService.pushHint(person.getAccount().getAccountName() + "PDA", msg);
										LOGGER.debug("预警消息接收人id（发手机端）：{}", person.getId());
									}
								}
								if(executedRule.isAlertForText()){
									LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
									//TODO
								}
							}
						}
						
						//通过文书获取对应案件的法制审核人
						Person personFazhi = archivedFileService.findPersonByDocIdAndDocType(mtsk.getMainTask().getTaskGroup().getBusTargetId(),mtsk.getMainTask().getTaskGroup().getBusType());
						if(personFazhi != null){
							AlertMessage message = new AlertMessage();
							message.setAlertRule(alertRuleService.findByid("0000000013001"));
							message.setCaseCode(executedRule.getCaseCode());
							message.setContent(executedRule.getContext());
							message.setCreatedTime(new Date());
							message.setReceiver(personFazhi);
							message.setStatus("0");
							message.setSuspectId(executedRule.getPersonId());
							message.setSuspectName(executedRule.getSuspectName());
							alertMessageService.saveMessage(message);
							LOGGER.debug("生成该消息的maintaskId和生成的预警消息的id：{},{},{}", new Object[]{ mtsk.getId(), message.getId(),personFazhi.getId()});
							
							if(executedRule.isAlertForSys()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								DefaultHintMsg msg = new DefaultHintMsg("{businessData:\""+executedRule.getContext()+"\", existingTime:"+mtsk.getMainTask().getExistingTime()+"}") ;
								if(personFazhi.getAccount() != null){
									cometdPushService.pushHint(personFazhi.getAccount().getAccountName(), msg);
									LOGGER.debug("预警消息接收人id（发pc端）：{}", personFazhi.getId());
								}
								
							}
							if(executedRule.isAlertForPDA()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								String context = "有新的消息，请查收";
								DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
								if(personFazhi.getAccount() != null){
									cometdPushService.pushHint(personFazhi.getAccount().getAccountName() + "PDA", msg);
									LOGGER.debug("预警消息接收人id（发手机端）：{}", personFazhi.getId());
								}
							}
							if(executedRule.isAlertForText()){
								LOGGER.debug("SubpoenaRule任务AlertForSys(id："+mtsk.getId()+")执行");
								//TODO
							}
						}
					}
					
					MainTask mTask = mtsk.getMainTask();
					mTask.setStatus(MainTask.MAINTASK_STATUS_FINISH);
					mTask.update();
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
		if (EXECUTOR_TASK_RULE_TYPE.equals(name + "_Executor")) {
			return true;
		}
		return false;
	}

}
