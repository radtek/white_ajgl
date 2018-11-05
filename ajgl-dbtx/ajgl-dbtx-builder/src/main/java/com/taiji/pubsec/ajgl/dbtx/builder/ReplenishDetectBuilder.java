package com.taiji.pubsec.ajgl.dbtx.builder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajgl.dbtx.task.util.Constant;
import com.taiji.pubsec.ajgl.dbtx.task.util.DbtxComponent;
import com.taiji.pubsec.ajgl.dbtx.task.util.replenishDetectBuilder.pojo.ReplenishDetectBuilderData;
import com.taiji.pubsec.ajgl.dbtx.task.util.replenishDetectBuilder.pojo.ReplenishDetectBuilderRule;
import com.taiji.pubsec.common.tool.mission.builder.custom.AbstractTaskBuilder;
import com.taiji.pubsec.common.tool.mission.task.core.MainPeriodTask;
import com.taiji.pubsec.common.tool.mission.task.core.MainTask;
import com.taiji.pubsec.common.tool.mission.task.core.TaskBuilderService;
import com.taiji.pubsec.common.tool.mission.task.core.TaskGroup;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;

import net.sf.json.JSONObject;

@Service
@Transactional
public class ReplenishDetectBuilder extends AbstractTaskBuilder implements DbtxComponent  {

	public static final String BUILDER_TASK_RULE_TYPE = Constant.TASK_TYPE_REPLENISHDETECT + "_Builder";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplenishDetectBuilder.class);
	
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
		
		ReplenishDetectBuilderData data = (ReplenishDetectBuilderData)TaskPojoJSONFmtUtil.getPojo(businessDataObj, ReplenishDetectBuilderData.class) ;
		
		//判断该案件是否已经生成任务，把之前的任务状态改为不可用，重新生成任务 
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
		String[] alertTimeAt = data.getAlertTimeAt().split(",");
		Integer dayTime = Integer.valueOf(alertTimeAt[0]) - Integer.valueOf(alertTimeAt[1]);
		String dayAtTime = alertTimeAt[2];
		String[] hourMinute = dayAtTime.split(":");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(data.getSendDate()));
		cal.add(Calendar.DAY_OF_MONTH, dayTime);
		cal.add(Calendar.HOUR, Integer.valueOf(hourMinute[0]));
		cal.add(Calendar.MINUTE, Integer.valueOf(hourMinute[1]));
		
		tg.setName(Constant.TASK_TYPE_REPLENISHDETECT);
		tg.setBusTargetId(data.getBusinessId());
		tg.setBusType(data.getBusinessType());
		tg.saveOrUpdate() ;
		
		//判断该预警是否过期，如果过期，不创建任务
		if(cal.getTime().getTime() <= new Date().getTime()){
			return true;
		}else{
			MainTask mt = new MainTask() ;
			//提醒时间
			mt.setStartTime(cal.getTime().getTime());
			//提醒处现实持续的时间，一般作为弹出框存在的时间
			mt.setExistingTime(Long.valueOf(data.getShowTime()*1000));
			ReplenishDetectBuilderRule rule = new ReplenishDetectBuilderRule();
			rule.setExecutingTime(cal.getTimeInMillis());
			rule.setCaseCode(data.getCaseCode());
			rule.setCaseName(data.getCaseName());
			rule.setContext("案件" + data.getCaseCode() + data.getCaseName() + "，退侦还有" + dayTime + "天到期，请确认后续工作，如已安排请忽略本消息");
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
			mt.save();
			MainPeriodTask mpt = new MainPeriodTask();
			mpt.setMainTask(mt);
			mpt.save() ;
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
