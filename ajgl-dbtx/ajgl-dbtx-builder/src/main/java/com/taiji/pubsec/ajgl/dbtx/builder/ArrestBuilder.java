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
import com.taiji.pubsec.ajgl.dbtx.task.util.arrest.pojo.ArrestData;
import com.taiji.pubsec.ajgl.dbtx.task.util.arrest.pojo.ArrestRule;
import com.taiji.pubsec.common.tool.mission.builder.custom.AbstractTaskBuilder;
import com.taiji.pubsec.common.tool.mission.task.core.MainPeriodTask;
import com.taiji.pubsec.common.tool.mission.task.core.MainTask;
import com.taiji.pubsec.common.tool.mission.task.core.TaskBuilderService;
import com.taiji.pubsec.common.tool.mission.task.core.TaskGroup;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;

import net.sf.json.JSONObject;

@Service
@Transactional
public class ArrestBuilder extends AbstractTaskBuilder implements DbtxComponent{
	
	public static final String BUILDER_TASK_RULE_TYPE = Constant.TASK_TYPE_ARREST + "_Builder";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArrestBuilder.class);

	@SuppressWarnings("rawtypes")
	@Resource 
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean builderTask(String businessData) {
		
		JSONObject businessDataObj = TaskPojoJSONFmtUtil.getJsonObject(businessData);
		
		//判断任务是否属于该类型
		if(!support((String)businessDataObj.get("type"))){
			//返回true chain会终止，返回false时chain继续执行
			return false ;
		}
		
		ArrestData data = (ArrestData)TaskPojoJSONFmtUtil.getPojo(businessDataObj, ArrestData.class) ;
		
		//判断该文书是否已经生成任务，把之前的任务状态改为不可用，重新生成任务
		String xql1 = "select t from TaskGroup as t where t.busTargetId = ? and t.busType = ?";
		TaskGroup tg = (TaskGroup) this.dao.findByParams(TaskGroup.class, xql1, new Object[]{data.getBusinessId(), data.getBusinessType()});
		
		if(tg==null){
			tg = new TaskGroup() ;
		}
		
		//查看该文书之前是否已生成任务，若已生成任务，将任务状态置为“终止”
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
		Date sendDate = new Date(data.getSendTime());
		for(String tempTime : timeAt){
			if(!StringUtils.isBlank(tempTime)){
				Calendar cal = Calendar.getInstance();
				cal.setTime(sendDate); 
				String[] timeArr = tempTime.split(":");
				cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeArr[0]));
				cal.set(Calendar.MINUTE, Integer.valueOf(timeArr[1]));
				cal.set(Calendar.SECOND, 0);
				cal.add(Calendar.DAY_OF_MONTH, 50);
				mapTime.put(cal.getTimeInMillis(), "50");
				cal.add(Calendar.DAY_OF_MONTH, 9);
				mapTime.put(cal.getTimeInMillis(), "59");
			}
		}
		
		tg.setName(Constant.TASK_TYPE_ARREST);
		tg.setBusTargetId(data.getBusinessId());
		tg.setBusType(data.getBusinessType());
		tg.saveOrUpdate() ;
		Iterator<Entry<Long, String>> iter = mapTime.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Long, String> entry = (Entry<Long, String>) iter.next();
			//判断该预警是否过期，如果过期，不创建任务
			if(entry.getKey() <= new Date().getTime()){
				continue;
			}else{
				MainTask mt = new MainTask() ;
				//提醒时间
				mt.setStartTime(entry.getKey());
				//提醒处现实持续的时间，一般作为弹出框存在的时间
				mt.setExistingTime(Long.valueOf(data.getShowTime()*1000));
				ArrestRule rule = new ArrestRule();
				rule.setExecutingTime(mt.getStartTime());
				rule.setContext("" + data.getPersonName() + data.getCaseCode() + data.getCaseName() + "，被逮捕已达" + entry.getValue() + "天，请安排调查并作笔录，如已安排请忽略本消息。");
				rule.setCaseCode(data.getCaseCode());
				rule.setSuspectId(data.getPersonId());
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
				mt.save();
				MainPeriodTask mpt12 = new MainPeriodTask();
				mpt12.setMainTask(mt);
				mpt12.save();
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
