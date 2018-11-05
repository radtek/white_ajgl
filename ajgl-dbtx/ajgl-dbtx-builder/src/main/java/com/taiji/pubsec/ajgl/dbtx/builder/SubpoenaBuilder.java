package com.taiji.pubsec.ajgl.dbtx.builder;

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
import com.taiji.pubsec.ajgl.dbtx.task.util.subpoena.pojo.SubpoenaData;
import com.taiji.pubsec.ajgl.dbtx.task.util.subpoena.pojo.SubpoenaRule;
import com.taiji.pubsec.common.tool.mission.builder.custom.AbstractTaskBuilder;
import com.taiji.pubsec.common.tool.mission.task.core.MainPeriodTask;
import com.taiji.pubsec.common.tool.mission.task.core.MainTask;
import com.taiji.pubsec.common.tool.mission.task.core.TaskBuilderService;
import com.taiji.pubsec.common.tool.mission.task.core.TaskGroup;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;

import net.sf.json.JSONObject;

@Service
@Transactional
public class SubpoenaBuilder extends AbstractTaskBuilder implements DbtxComponent{
	
	public static final String BUILDER_TASK_RULE_TYPE = Constant.TASK_TYPE_SUBPOENA + "_Builder";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubpoenaBuilder.class);

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
		
		SubpoenaData data = (SubpoenaData)TaskPojoJSONFmtUtil.getPojo(businessDataObj, SubpoenaData.class) ;
		
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
		Long executedTime12 = data.getSendTime() + 12*60*60*1000 - data.getAheadTime()*60*1000;
		Long executedTime24 = data.getSendTime() + 24*60*60*1000 - data.getAheadTime()*60*1000;
		
		tg.setName(Constant.TASK_TYPE_SUBPOENA);
		tg.setBusTargetId(data.getBusinessId());
		tg.setBusType(data.getBusinessType());
		tg.saveOrUpdate() ;
		String[] arr = data.getWay().split(",");
		//判断该预警是否过期，如果过期，不创建任务
		if(executedTime12 <= new Date().getTime()){
			;
		}else{
			MainTask mt12 = new MainTask() ;
			//提醒时间
			mt12.setStartTime(executedTime12);
			//提醒处现实持续的时间，一般作为弹出框存在的时间
			mt12.setExistingTime(Long.valueOf(data.getShowTime()*1000));
			SubpoenaRule rule12 = new SubpoenaRule();
			rule12.setExecutingTime(mt12.getStartTime());
			rule12.setContext(data.getPersonName() + data.getCaseCode() + data.getCaseName() + "，传唤12小时预警。");
			rule12.setCaseCode(data.getCaseCode());
			rule12.setPersonId(data.getSuspectId());
			rule12.setSuspectName(data.getPersonName());
			for(String way : arr){
				if(way.equals("0000000014001")){
					rule12.setAlertForSys(true);
				}else if(way.equals("0000000014002")){
					rule12.setAlertForPDA(true);
				}else if(way.equals("0000000014003")){
					rule12.setAlertForText(true);
				}
			}
			mt12.setTaskExecutedRule(TaskPojoJSONFmtUtil.getJSONStr(rule12));
			//状态：启用1，停用0，完成2， 正式使用时改为字典项
			mt12.setStatus(MainTask.MAINTASK_STATUS_ON);
			
			mt12.setTaskGroup(tg);
			
			mt12.save();
			
			MainPeriodTask mpt12 = new MainPeriodTask();

			mpt12.setMainTask(mt12);
			
			mpt12.save() ;
		}
		
		//判断该预警是否过期，如果过期，不创建任务
		if(executedTime24 <= new Date().getTime()){
			;
		}else{
			MainTask mt24 = new MainTask() ;
			//提醒时间
			mt24.setStartTime(executedTime24);
			//提醒处现实持续的时间，一般作为弹出框存在的时间
			mt24.setExistingTime(Long.valueOf(data.getShowTime()*1000));
			SubpoenaRule rule24 = new SubpoenaRule();
			rule24.setExecutingTime(mt24.getStartTime());
			rule24.setContext(data.getPersonName() + data.getCaseCode() + data.getCaseName() + "，传唤24小时预警。");
			rule24.setCaseCode(data.getCaseCode());
			rule24.setPersonId(data.getSuspectId());
			rule24.setSuspectName(data.getPersonName());
			for(String way : arr){
				if(way.equals("0000000014001")){
					rule24.setAlertForSys(true);
				}else if(way.equals("0000000014002")){
					rule24.setAlertForPDA(true);
				}else if(way.equals("0000000014003")){
					rule24.setAlertForText(true);
				}
			}
			mt24.setTaskExecutedRule(TaskPojoJSONFmtUtil.getJSONStr(rule24));
			//状态：启用1，停用0，完成2， 正式使用时改为字典项
			mt24.setStatus(MainTask.MAINTASK_STATUS_ON);
			
			mt24.setTaskGroup(tg);
			
			mt24.save();
			
			MainPeriodTask mpt24 = new MainPeriodTask();

			mpt24.setMainTask(mt24);
			
			mpt24.save() ;
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
