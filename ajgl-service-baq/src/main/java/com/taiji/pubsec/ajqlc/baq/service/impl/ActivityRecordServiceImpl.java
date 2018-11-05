package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;
import com.taiji.pubsec.ajqlc.baq.service.IActivityRecordService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;

import net.sf.json.JSONObject;

/**
 * @author chengkai
 */
@Service("activityRecordService")
public class ActivityRecordServiceImpl implements IActivityRecordService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRecordServiceImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityRecord> findActivityRecordsByReceiptCode(String receiptCode) {
		String xql = "select ar from ActivityRecord as ar where ar.handlingAreaReceiptNum = ? order by ar.startTime, ar.supRecordId";
		return dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptCode});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityRecord> findActivityRecordsByReceiptCodeAndSupRecordId(String receiptCode, String supRecordId) {
		if(supRecordId==null){
			String xql = "select ar from ActivityRecord as ar where ar.handlingAreaReceiptNum = ? and ar.supRecordId is null order by ar.startTime";
			return dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptCode});
		}else{
			String xql = "select ar from ActivityRecord as ar where ar.handlingAreaReceiptNum = ? and ar.supRecordId=? order by ar.startTime";
			return dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptCode,supRecordId});
		}
		
	}

	@Override
	public String saveActivationRecord(String result, String receiptNum) {
    	List<ActivityRecord> activationRecordList = this.json2ActivationRecord(result);
		for(ActivityRecord activationRecord:activationRecordList){
    		if(ConstantBean.GRIDTYPE_ZL.equals(activationRecord.getGridType()) || 
    				ConstantBean.GRIDTYPE_BAZXMT.equals(activationRecord.getGridType())){
    			//“进入办案区”大阶段
    			this.saveOrUpdateRecordOfEnterCaseArea(activationRecord, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_JRBAQ, receiptNum);
    			
    		}else if(ConstantBean.GRIDTYPE_RSANJCS.equals(activationRecord.getGridType())){
    			//“人身安全检查”大阶段，无下级活动记录
    			this.saveOrUpdateRecordWithNoSubRecord(activationRecord, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_RSAQJC, receiptNum);
    			
    		}else if(ConstantBean.GRIDTYPE_XXCJS.equals(activationRecord.getGridType())){
    			//“信息采集”大阶段，无下级活动记录
    			this.saveOrUpdateRecordWithNoSubRecord(activationRecord, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_XXCJ, receiptNum);

    		}else if(ConstantBean.GRIDTYPE_SSWPBGQ.equals(activationRecord.getGridType())){
    			//查询“随身物品保管”大阶段，无下级活动记录
    			this.saveOrUpdateRecordWithNoSubRecord(activationRecord, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_SSWPBG, receiptNum);

    		}else if(ConstantBean.GRIDTYPE_DWS.equals(activationRecord.getGridType()) || 
    				ConstantBean.GRIDTYPE_ZBJKQ.equals(activationRecord.getGridType()) || 
    				ConstantBean.GRIDTYPE_XWS.equals(activationRecord.getGridType()) || 
    				ConstantBean.GRIDTYPE_XLSDS.equals(activationRecord.getGridType()) || 
    				ConstantBean.GRIDTYPE_BRS.equals(activationRecord.getGridType()) || 
    				ConstantBean.GRIDTYPE_WSJ.equals(activationRecord.getGridType())){
    			//“讯询问过程”大阶段
    			this.saveOrUpdateRecordOfAskProcess(activationRecord, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_XXWGC, receiptNum);

    		}else if(ConstantBean.GRIDTYPE_ZBDJS.equals(activationRecord.getGridType())){
    			//“离开登记”大阶段
    			this.saveOrUpdateRecordWithSubRecord(activationRecord, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_LKDJ, receiptNum);
    			
    		}
    	}
		return null;
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public void updateActivationRecord(ActivityRecord activationRecord) {
    	 this.dao.update(activationRecord);
	}
    
    /**
	 * 返回AlarmInfoBean的list
	 * @param json
	 * @return
	 */
	private List<ActivityRecord> json2ActivationRecord(String result){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = new GsonBuilder().create();
		List<ActivityRecord> retList = new ArrayList<ActivityRecord>();
		JSONObject  jasonObject = JSONObject.fromObject(result);
		if("false".equals(jasonObject.get("success").toString())){
			return null;
		}
		JsonParser parser = new JsonParser();  
		JsonObject jsonObject = parser.parse(result).getAsJsonObject();  
	    //将data节点下的内容转为JsonArray  
	    JsonArray jsonArray = jsonObject.getAsJsonArray("data");  
	    for (int i = 0; i < jsonArray.size(); i++) {  
	    	ActivityRecord record = new ActivityRecord();
	    	JsonElement el = jsonArray.get(i);  
	    	try {
	    		String startTime = "";
	    		JsonElement objectStartTime = el.getAsJsonObject().get("startTime");
	    		startTime = objectStartTime.toString();
	    		startTime = startTime.substring(startTime.indexOf("\"") + 1, startTime.lastIndexOf("\""));
	    		if(!objectStartTime.isJsonNull()){
	    			record.setStartTime(sdf.parse(startTime));
	    		}else{
	    			record.setStartTime(null);
	    		}
	    		
	    		String endTime = "";
	    		JsonElement objectEndTime = el.getAsJsonObject().get("endTime");
	    		endTime = objectEndTime.toString();
	    		endTime = endTime.substring(endTime.indexOf("\"") + 1, endTime.lastIndexOf("\""));
	    		if(StringUtils.isNotBlank(endTime)){
	    			record.setEndTime(sdf.parse(endTime));
	    		}else{
	    			record.setEndTime(null);
	    		}
	    	} catch (ParseException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	String gridId = el.getAsJsonObject().get("gridUUID").toString();
	    	record.setGridId(gridId.substring(gridId.indexOf("\"") + 1, gridId.lastIndexOf("\"")));
	    	String gridName = el.getAsJsonObject().get("gridName").toString();
	    	record.setGridName(gridName.substring(gridName.indexOf("\"") + 1, gridName.lastIndexOf("\"")));
	    	String gridType = el.getAsJsonObject().get("gridType").toString();
	    	record.setGridType(gridType.substring(gridType.indexOf("\"") + 1, gridType.lastIndexOf("\"")));
	    	String passageId = el.getAsJsonObject().get("channelCode").toString();
	    	record.setPassageId(passageId.substring(passageId.indexOf("\"") + 1, passageId.lastIndexOf("\"")));

	    	retList.add(record);
	    }
        return retList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(ActivityRecord record) {
		dao.save(record);
		
	}
	/**
	 * 新增或更新大阶段活动记录（无下级活动记录）
	 * @param activationRecord 活动记录
	 * @param bigStageDescription 大阶段活动记录名称
	 * @param receiptNum 使用单编号
	 */
	@SuppressWarnings("unchecked")
	private void saveOrUpdateRecordWithNoSubRecord(ActivityRecord activationRecord, String bigStageDescription, String receiptNum){
//		String sql = " from ActivityRecord where handlingAreaReceiptNum = ? and gridType = ? and startTime = ?";
//		ActivityRecord record = null;
		String xql = "from ActivityRecord where handlingAreaReceiptNum = ? and description = ? order by startTime desc";

		//查询“进入办案区”大阶段个数
		List<ActivityRecord> enterAreaRecords = dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptNum, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_JRBAQ});
		//查询“xx”大阶段个数
		List<ActivityRecord> processRecords = dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptNum, bigStageDescription});
		if(enterAreaRecords.size() > processRecords.size()){
			ActivityRecord record = new ActivityRecord();
			record.setDescription(bigStageDescription);
			record.setEndTime(activationRecord.getEndTime());
			record.setHandlingAreaReceiptNum(receiptNum);
			record.setStartTime(activationRecord.getStartTime());
			record.setGridId(activationRecord.getGridId());
			record.setGridName(activationRecord.getGridName());
			record.setGridType(activationRecord.getGridType());
			record.setPassageId(activationRecord.getPassageId());
			dao.save(record);
		}else{
			ActivityRecord record = processRecords.get(0);
			record.setEndTime(activationRecord.getEndTime());
			dao.update(record);
		}
		
	}
	
	/**
	 * 新增或更新大阶段活动记录（有下级活动记录）
	 * @param activationRecord 活动记录
	 * @param bigStageDescription 大阶段活动记录名称
	 * @param receiptNum 使用单编号
	 */
	@SuppressWarnings("unchecked")
	private void saveOrUpdateRecordWithSubRecord(ActivityRecord activationRecord, String bigStageDescription, String receiptNum){
		String sql = " from ActivityRecord where handlingAreaReceiptNum = ? and description = ? order by startTime desc";
		String sql1 = " from ActivityRecord where handlingAreaReceiptNum = ? and gridType = ? and startTime = ?";
		String sql2 = "from ActivityRecord where supRecordId = ?";
		List<ActivityRecord> records = new ArrayList<ActivityRecord>();
		ActivityRecord subRecord = new ActivityRecord();
		List<ActivityRecord> subRecords = new ArrayList<ActivityRecord>();
		
		//查询大阶段
		records = dao.findAllByParams(ActivityRecord.class, sql, new Object[]{receiptNum, bigStageDescription});
		ActivityRecord supRecord = records.get(0);
		//查询该大阶段是否已有下级活动记录
		subRecords = dao.findAllByParams(ActivityRecord.class, sql2, new Object[]{supRecord.getId()});
		//更新大阶段
		if(subRecords.size() == 0){
			supRecord.setStartTime(activationRecord.getStartTime());
		}
		supRecord.setEndTime(activationRecord.getEndTime());
		dao.update(supRecord);
		
		//查询是否存在该下级活动目录，新增或更新该记录
		subRecord = (ActivityRecord) dao.findByParams(ActivityRecord.class, sql1, new Object[]{receiptNum, activationRecord.getGridType(), activationRecord.getStartTime()});
		if(subRecord == null){
			activationRecord.setHandlingAreaReceiptNum(receiptNum);
			activationRecord.setSupRecordId(supRecord.getId());
			dao.save(activationRecord);
		}else{
			subRecord.setEndTime(activationRecord.getEndTime());
			dao.update(subRecord);
		}
	}
	
	/**
	 * 新增或更新“进入办案区”大阶段的下级活动记录
	 * @param activationRecord 活动记录
	 * @param bigStageDescription 大阶段名称
	 * @param receiptNum 使用单编号
	 */
	@SuppressWarnings("unchecked")
	private void saveOrUpdateRecordOfEnterCaseArea(ActivityRecord activationRecord, String bigStageDescription, String receiptNum){
		String sql = " from ActivityRecord where handlingAreaReceiptNum = ? and description = ? order by startTime desc";
		//查询“进入办案区”大阶段
		List<ActivityRecord> records = dao.findAllByParams(ActivityRecord.class, sql, new Object[]{receiptNum, bigStageDescription});
		if(records != null && records.size() > 0){
			ActivityRecord supRecord = records.get(0);
			supRecord.setEndTime(activationRecord.getEndTime());
			dao.update(supRecord);
			
			//查询是否存在该下级活动目录，新增或更新该记录
			String xql = "from ActivityRecord where handlingAreaReceiptNum = ? and gridType = ? and startTime = ?";
			ActivityRecord subRecord = (ActivityRecord) dao.findByParams(ActivityRecord.class, xql, new Object[]{receiptNum, activationRecord.getGridType(), activationRecord.getStartTime()});
			if(subRecord == null){
				activationRecord.setHandlingAreaReceiptNum(receiptNum);
				activationRecord.setSupRecordId(supRecord.getId());
				dao.save(activationRecord);
			}else{
				subRecord.setEndTime(activationRecord.getEndTime());
				dao.update(subRecord);
			}
		}
	}
	
	/**
	 * 新增或更新“讯询问过程”大阶段的下级活动记录
	 * @param activationRecord 活动记录
	 * @param bigStageDescription 大阶段名称
	 * @param receiptNum 使用单编号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ActivityRecord saveOrUpdateRecordOfAskProcess(ActivityRecord activationRecord, String bigStageDescription, String receiptNum){
		String xql = "from ActivityRecord where handlingAreaReceiptNum = ? and description = ? order by startTime desc";
		//查询相应的“讯询问过程”大阶段
		//查询“进入办案区”大阶段个数
		ActivityRecord supRecord;
		List<ActivityRecord> enterAreaRecords = dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptNum, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_JRBAQ});
		//查询“讯询问过程”大阶段个数
		List<ActivityRecord> askProcessRecords = dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptNum, bigStageDescription});
		if(enterAreaRecords.size() > askProcessRecords.size()){
			supRecord = new ActivityRecord();
			supRecord.setDescription(bigStageDescription);
			supRecord.setEndTime(activationRecord.getEndTime());
			supRecord.setHandlingAreaReceiptNum(receiptNum);
			supRecord.setStartTime(activationRecord.getStartTime());
			dao.save(supRecord);
		}else{
			supRecord = askProcessRecords.get(0);
			supRecord.setEndTime(activationRecord.getEndTime());
			dao.update(supRecord);
		}
		
		//查询是否存在该下级活动目录，新增或更新该记录
		String xql1 = "from ActivityRecord where handlingAreaReceiptNum = ? and gridType = ? and startTime = ?";
		ActivityRecord subRecord = (ActivityRecord) dao.findByParams(ActivityRecord.class, xql1, new Object[]{receiptNum, activationRecord.getGridType(), activationRecord.getStartTime()});
		if(subRecord == null){
			activationRecord.setHandlingAreaReceiptNum(receiptNum);
			activationRecord.setSupRecordId(supRecord.getId());
			if(ConstantBean.GRIDTYPE_DWS.equals(activationRecord.getGridType())){
				activationRecord.setDescription(ConstantBean.ACTIVITY_STAGE_DESCRIPTION_DWS);
			}else
			if(ConstantBean.GRIDTYPE_ZBJKQ.equals(activationRecord.getGridType())){
				activationRecord.setDescription(ConstantBean.ACTIVITY_STAGE_DESCRIPTION_ZBJKQ);
			}else
			if(ConstantBean.GRIDTYPE_XWS.equals(activationRecord.getGridType())){
				activationRecord.setDescription(ConstantBean.ACTIVITY_STAGE_DESCRIPTION_XWS);
			}else
			if(ConstantBean.GRIDTYPE_XLSDS.equals(activationRecord.getGridType())){
				activationRecord.setDescription(ConstantBean.ACTIVITY_STAGE_DESCRIPTION_XLSDS);
			}else
			if(	ConstantBean.GRIDTYPE_BRS.equals(activationRecord.getGridType())){
				activationRecord.setDescription(ConstantBean.ACTIVITY_STAGE_DESCRIPTION_BRS);
			}else
			if(ConstantBean.GRIDTYPE_WSJ.equals(activationRecord.getGridType())){
				activationRecord.setDescription(ConstantBean.ACTIVITY_STAGE_DESCRIPTION_WSJ);
			}
			dao.save(activationRecord);
			
		}else{
			subRecord.setEndTime(activationRecord.getEndTime());
			dao.update(subRecord);
		}
		return supRecord;
	}

	/**
	 * 
	 * @param activationRecord 子阶段记录
	 * @param bigStageDescription 大阶段描述
	 * @param receiptNum 使用单号
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdateRecordOfLeaveRegister(ActivityRecord activationRecord, String bigStageDescription, String receiptNum){
		String xql = "from ActivityRecord where handlingAreaReceiptNum = ? and description = ? order by startTime desc";
		//查询“进入办案区”大阶段个数
		ActivityRecord supRecord;
		List<ActivityRecord> enterAreaRecords = dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptNum, ConstantBean.ACTIVITY_STAGE_DESCRIPTION_JRBAQ});
		//查询“离开过程”大阶段个数
		List<ActivityRecord> leaveRegisterRecords = dao.findAllByParams(ActivityRecord.class, xql, new Object[]{receiptNum, bigStageDescription});
		if(enterAreaRecords.size() > leaveRegisterRecords.size()){
			supRecord = new ActivityRecord();
			supRecord.setDescription(bigStageDescription);
			supRecord.setEndTime(activationRecord.getEndTime());
			supRecord.setHandlingAreaReceiptNum(receiptNum);
			supRecord.setStartTime(activationRecord.getStartTime());
			
			dao.save(supRecord);
		}else{
			supRecord = leaveRegisterRecords.get(0);
			supRecord.setEndTime(activationRecord.getEndTime());
//			supRecord.setGridId(activationRecord.getGridId());
//			supRecord.setGridType(activationRecord.getGridType());;
//			supRecord.setGridName(activationRecord.getGridName());;
//			supRecord.setPassageId(activationRecord.getPassageId());;
			dao.update(supRecord);
		}
		
		//查询是否存在该下级活动目录，新增或更新该记录
		String xql1 = "from ActivityRecord where handlingAreaReceiptNum = ? and gridType = ? and startTime = ?";
		ActivityRecord subRecord = (ActivityRecord) dao.findByParams(ActivityRecord.class, xql1, new Object[]{receiptNum, activationRecord.getGridType(), activationRecord.getStartTime()});
		if(subRecord == null){
			activationRecord.setHandlingAreaReceiptNum(receiptNum);
			activationRecord.setSupRecordId(supRecord.getId());
			dao.save(activationRecord);
		}else{
			subRecord.setEndTime(activationRecord.getEndTime());
			dao.update(subRecord);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityRecord findActivityRecordsByHarCodeAndBigStageDescription(String receiptNum,
			String bigStageDescription) {
		String sql = " from ActivityRecord where handlingAreaReceiptNum = ? and description = ? order by startTime desc";
		List<ActivityRecord> recordsList = dao.findAllByParams(ActivityRecord.class, sql, new Object[]{receiptNum, bigStageDescription});
		if(!recordsList.isEmpty()){
			return recordsList.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findActivityRecordsGridId() {
		String sql = " select DISTINCT g.gridId from ActivityRecord as g where 1=1 order by g.gridId desc";
		List<String> List = dao.queryList(sql);
		if(!List.isEmpty()){
			return List;
		}else{
			return null;
		}
	}
}
