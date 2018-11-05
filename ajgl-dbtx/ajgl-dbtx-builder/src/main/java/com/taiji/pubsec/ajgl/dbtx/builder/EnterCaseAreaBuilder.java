package com.taiji.pubsec.ajgl.dbtx.builder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
	


import com.taiji.pubsec.ajgl.dbtx.task.util.Constant;
import com.taiji.pubsec.ajgl.dbtx.task.util.DbtxComponent;
import com.taiji.pubsec.ajgl.dbtx.task.util.entercasearea.pojo.EnterCaseAreaData;
import com.taiji.pubsec.ajgl.dbtx.task.util.entercasearea.pojo.EnterCaseAreaRule;
import com.taiji.pubsec.common.tool.mission.builder.custom.AbstractTaskBuilder;
import com.taiji.pubsec.common.tool.mission.task.core.MainPeriodTask;
import com.taiji.pubsec.common.tool.mission.task.core.MainTask;
import com.taiji.pubsec.common.tool.mission.task.core.ReceiveSubject;
import com.taiji.pubsec.common.tool.mission.task.core.TaskBuilderService;
import com.taiji.pubsec.common.tool.mission.task.core.TaskGroup;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;

@Service
@Transactional
public class EnterCaseAreaBuilder extends AbstractTaskBuilder implements DbtxComponent{
	
	public static final String BUILDER_TASK_RULE_TYPE = Constant.TASK_TYPE_ENTERCASEAREA + "_Builder";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnterCaseAreaBuilder.class);

	@Override
	public boolean builderTask(String businessData) {
		
		JSONObject businessDataObj = TaskPojoJSONFmtUtil.getJsonObject(businessData);
		
		if(!support((String)businessDataObj.get("type"))){
			//返回true chain会终止，返回false时chain继续执行
			return false ;
		}
		
		EnterCaseAreaData data = (EnterCaseAreaData)TaskPojoJSONFmtUtil.getPojo(businessDataObj, EnterCaseAreaData.class) ;
		
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
		double yjTime = data.getFromEnterIntoTime();
		Long executedTime = data.getEnterIntoTime() + (int)(yjTime*60*60*1000) - data.getAheadTime()*60*1000;
		
		
		TaskGroup tg = new TaskGroup() ;
		
		tg.setName(Constant.TASK_TYPE_ENTERCASEAREA);
		tg.setBusTargetId(data.getBusinessId());
		tg.setBusType(data.getBusinessType());
		tg.save();
		
		//判断该预警是否过期，如果过期，不创建任务
		if(executedTime <= new Date().getTime()){
			return true;
		}else{
			MainTask mt = new MainTask() ;
			//接收主体, 从TaskRule的规则中获得
			List<ReceiveSubject> rsLst = new ArrayList<ReceiveSubject>();
			for(String str: data.getReceiveIdLst()){
				ReceiveSubject rs = new ReceiveSubject();
				rs.setTargetId(str);
				rs.setType(data.getReceiveType());
				rs.save();
				rsLst.add(rs);
			}
			mt.setReceiveSubjectsByArrayList(rsLst);
			//提醒时间
			mt.setStartTime(executedTime);
			//提醒处现实持续的时间，一般作为弹出框存在的时间
			mt.setExistingTime(Long.valueOf(data.getShowTime()*1000));
			DecimalFormat df = new java.text.DecimalFormat("#.##"); 
			double t = yjTime*60 - data.getAheadTime();
			String tempTime = df.format(t/60);
			EnterCaseAreaRule rule = new EnterCaseAreaRule();
			rule.setExecutingTime(mt.getStartTime());
			rule.setContext(data.getPersonName() + "被讯问人员（办案区使用单：" + data.getHarCode() + "）已进入办案区达 " + tempTime + "小时，请及时进行后续业务处理。");
			rule.setCaseCode(data.getCaseCode());
			rule.setSuspectId(data.getSuspectId());
			rule.setSuspectName(data.getPersonName());
			rule.setReceiptNum(data.getHarCode());
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
			//只在八小时后触发一次
			mpt.setExecutedTimes(1);
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
