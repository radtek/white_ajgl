package com.taiji.pubsec.ajgl.dbtx.builder;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajgl.dbtx.task.util.Constant;
import com.taiji.pubsec.ajgl.dbtx.task.util.DbtxComponent;
import com.taiji.pubsec.ajgl.dbtx.task.util.residentialSurveillance.pojo.ResidentialSurveillanceData;
import com.taiji.pubsec.ajgl.dbtx.task.util.residentialSurveillance.pojo.ResidentialSurveillanceRule;
import com.taiji.pubsec.common.tool.mission.builder.custom.AbstractTaskBuilder;
import com.taiji.pubsec.common.tool.mission.task.core.MainPeriodTask;
import com.taiji.pubsec.common.tool.mission.task.core.MainTask;
import com.taiji.pubsec.common.tool.mission.task.core.TaskBuilderService;
import com.taiji.pubsec.common.tool.mission.task.core.TaskGroup;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;

import net.sf.json.JSONObject;

@Service
@Transactional
public class ResidentialSurveillanceBuilder extends AbstractTaskBuilder implements DbtxComponent {
//无数据暂时不实现，结构同Bail
public static final String BUILDER_TASK_RULE_TYPE = Constant.TASK_TYPE_RESIDENTIALSURVEILLANCE + "_Builder";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResidentialSurveillanceBuilder.class);

	@SuppressWarnings("rawtypes")
	@Resource 
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean builderTask(String businessData) {
		
		JSONObject businessDataObj = TaskPojoJSONFmtUtil.getJsonObject(businessData);
		
		if(!support((String)businessDataObj.get("type"))){
			//返回true chain会终止，返回false时chain继续执行
			return false ;
		}
		
		ResidentialSurveillanceData data = (ResidentialSurveillanceData)TaskPojoJSONFmtUtil.getPojo(businessDataObj, ResidentialSurveillanceData.class) ;
		
		//判断该文书是否已经生成任务，把之前的任务状态改为不可用，重新生成任务
		String xql1 = "select t from TaskGroup as t where t.busTargetId = ? and t.busType = ?";
		TaskGroup tg = (TaskGroup) this.dao.findByParams(TaskGroup.class, xql1, new Object[]{data.getBusinessId(), data.getBusinessType()});
		
		if(tg==null){
			tg = new TaskGroup() ;
		}
		
		List<MainTask> mTask = tg.getMainTasksByArrayList();

		for(MainTask mainTask: mTask){
			mainTask.setStatus(MainTask.MAINTASK_STATUS_EXPIRE);
			mainTask.update() ;
		}
		
		LOGGER.debug("开始执行");
		
		//根据TaskRule生成具体的规则
		/**
		 * 每个任务实际执行的规则
		 * 如果MainTask和MainPeriodTask等实体中关于规则的字段不set值，也不是生成taskExecutedRule
		 * 则任务的执行规则全部都从每个任务对应的TaskRule任务中获取
		 * 若MainTask和MainPeriodTask等实体中关于规则的字段set值了，或者生成了taskExecutedRule
		 * 那么完成的任务和正在进行中的任务的执行规则，有可能和TaskRule中的规则不同（TaskRule的规则可能会变化）
		 * 则需要复写TaskRuleService中的updateAllUnexecutedTasks方法，标明在更新TaskRule的规则的时候，需不需要更改已经生成正在执行的任务中的执行规则
		 */
		Map<Long,String> mapTime = new HashMap<Long,String>();
		String[] timeAt = data.getAlertTimeAt().split(",");
		Date sendDate = new Date(data.getSendDate());
		for(String tempTime : timeAt){
			if(!StringUtils.isBlank(tempTime)){
				Calendar cal = Calendar.getInstance();
				//设置开始日期
				cal.setTime(sendDate); 
				String[] timeArr = tempTime.split(":");
				//根据rule中时间设置提醒时分秒
				cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeArr[0]));
				cal.set(Calendar.MINUTE, Integer.valueOf(timeArr[1]));
				cal.set(Calendar.SECOND, 0);
				//获取开始日期
				int startYear = cal.get(Calendar.YEAR);
				int startMonth = cal.get(Calendar.MONTH);
				int startDay = cal.get(Calendar.DAY_OF_MONTH);
				//设置2个月底
				cal.add(Calendar.MONTH, 2);
				//暂时不设置月底，否则会导致月底弹窗太多cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				mapTime.put(cal.getTimeInMillis(), data.getPersonName() + data.getCaseCode() + data.getCaseName() + "，监视居住已经2个月，请安排调查并作笔录");
				//设置4个月底     
				cal.add(Calendar.MONTH, 2);
				//暂时不设置月底，否则会导致月底弹窗太多cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				mapTime.put(cal.getTimeInMillis(), data.getPersonName() + data.getCaseCode() + data.getCaseName() + "，监视居住已经4个月，请安排调查并作笔录");
				//设置开始日期并调整到结束日期
				cal.set(Calendar.YEAR, startYear);
				cal.set(Calendar.MONTH, startMonth);
				cal.set(Calendar.DAY_OF_MONTH, startDay);
				cal.add(Calendar.MONTH, 6);
				//设置前1天
				cal.add(Calendar.DAY_OF_MONTH, -1);
				mapTime.put(cal.getTimeInMillis(), data.getPersonName() + data.getCaseCode() + data.getCaseName() + "，监视居住还有1天期满");
				//设置前7天
				cal.add(Calendar.DAY_OF_MONTH, -6);
				mapTime.put(cal.getTimeInMillis(), data.getPersonName() + data.getCaseCode() + data.getCaseName() + "，监视居住还有7天期满");
				//设置前15天
				cal.add(Calendar.DAY_OF_MONTH, -8);
				mapTime.put(cal.getTimeInMillis(), data.getPersonName() + data.getCaseCode() + data.getCaseName() + "，监视居住还有15天期满");
			}
		}
		
		tg.setName(Constant.TASK_TYPE_RESIDENTIALSURVEILLANCE);
		tg.setBusTargetId(data.getBusinessId());
		tg.setBusType(data.getBusinessType());
		
		tg.saveOrUpdate() ;
		
		Iterator<Entry<Long, String>> iter = mapTime.entrySet().iterator();

		while (iter.hasNext()) {
			Entry<Long, String> entry = (Entry<Long, String>) iter.next();
			//判断该预警是否过期，如果过期，不创建任务
			if(entry.getKey() <= new Date().getTime()){
				return true;
			}else{
				MainTask mt = new MainTask() ;
				//提醒时间
				mt.setStartTime(entry.getKey());
				//提醒处现实持续的时间，一般作为弹出框存在的时间
				mt.setExistingTime(Long.valueOf(data.getShowTime()*1000));
				ResidentialSurveillanceRule rule = new ResidentialSurveillanceRule();
				rule.setCaseCode(data.getCaseCode());
				rule.setPersonId(data.getPersonId());
				rule.setExecutingTime(mt.getStartTime());
				rule.setContext(entry.getValue());
				rule.setSuspectName(data.getPersonName());
				String[] arr = data.getWay().split(",");
				for(String way : arr){
					if(way.equals("0000000014001")){
						rule.setAlertForSys(true);
					}else if(way.equals("0000000014002")){
						rule.setAlertForPDA(true);
					}else if(way.equals("0000000014003")){
						rule.setAlertForText(true);
					}
				}
				mt.setTaskExecutedRule(TaskPojoJSONFmtUtil.getJSONStr(rule));
				//状态：启用1，停用0，完成2， 正式使用时改为字典项
				mt.setStatus(MainTask.MAINTASK_STATUS_ON);

				mt.setTaskGroup(tg);
				mt.save() ;
				MainPeriodTask mpt12 = new MainPeriodTask();
				mpt12.setMainTask(mt);

				mpt12.save() ;
			}
		}
		
		return true ;
	}

	@Override
	public boolean support(String taskRuleCode) {
		if(BUILDER_TASK_RULE_TYPE.equals(taskRuleCode + "_Builder")){
			return true ;
		}
		return false;
	}
	
	@Resource(name="taskBuilderService")
	@Override
	public void setTaskBuilderService(TaskBuilderService taskBuilderService) {
		this.taskBuilderService = taskBuilderService;
	}
	
}
