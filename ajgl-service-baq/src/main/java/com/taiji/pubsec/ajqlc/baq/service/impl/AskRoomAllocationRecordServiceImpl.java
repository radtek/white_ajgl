package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.bean.RoomUseInformationBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.util.ParamMapUtil;
import com.taiji.pubsec.common.tool.sql.SQLTool;

/**
 * @author chengkai
 */
@Service("askRoomAllocationRecordService")
@Transactional(rollbackFor = Exception.class)
public class AskRoomAllocationRecordServiceImpl implements IAskRoomAllocationRecordService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	public static final String OCCUPIEDASKROOMSTATUS = "0000000001003";	// 房间使用中的状态
	public static final String FREEASKROOMSTATUS = "0000000001001";	// 房间空闲中的状态
	public static final String ASKROOMNAME = "askRoomName";	// 房间名称
	public static final String ASKROOMID = "askRoomId";	// 房间ID
	public static final String HANDLINGAREARECEIPTNUM = "handlingAreaReceiptNum";	// 使用单编号
	public static final String ENTERAREAREASONS = "enterAreaReasons";	// 案由
	public static final String BYQUESTIONINGPEOPLENAME = "byQuestioningPeopleName";	// 被问讯人姓名
	public static final String BYQUESTIONINGPEOPLEIDENTIFYNUM = "byQuestioningPeopleIdentifyNum";	// 被问讯人证件号码
	public static final String ALLOCATIONSTARTTIME = "allocationStartTime";	// 开始时间
	public static final String ALLOCATIONENDTIME = "allocationEndTime";	// 结束时间
	public static final String UNIT = "unit";	// 所属单位
	public static final String HANDLINGPOLICE = "handlingPolice";	// 办案民警
	public static final String LAWCASE = "lawCase";	// 所属案件
	public static final String IF_IS = "0000000007002";//是否：是
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AskRoomAllocationRecordServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public void saveAskRoomAllocationRecord(AskRoomAllocationRecord askRoomAllocationRecord) {
		this.dao.save(askRoomAllocationRecord);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AskRoomAllocationRecord findAskRoomAllocationRecordById(String id) {
		return (AskRoomAllocationRecord) this.dao.findById(AskRoomAllocationRecord.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AskRoomAllocationRecord> findAskRoomAllocationRecordByKey(Map<String, Object> queryCondtidions) {
		String xql = "select arar from AskRoomAllocationRecord as arar where 1 = 1 ";
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(ASKROOMNAME))){
			xql += "and arar.askRoom.name like :askRoomName ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(ASKROOMNAME, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(ASKROOMNAME)) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(HANDLINGAREARECEIPTNUM))){
			xql += "and arar.handlingAreaReceipt.basicCase.handlingAreaReceiptNum like :handlingAreaReceiptNum ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(HANDLINGAREARECEIPTNUM, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(HANDLINGAREARECEIPTNUM)) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(ENTERAREAREASONS))){
			xql += "and arar.handlingAreaReceipt.basicCase.enterAreaReasons = :enterAreaReasons ";
			xqlMap.put(ENTERAREAREASONS, queryCondtidions.get(ENTERAREAREASONS));
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(BYQUESTIONINGPEOPLENAME))){
			xql += "and arar.handlingAreaReceipt.basicCase.byQuestioningPeopleName like :byQuestioningPeopleName ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(BYQUESTIONINGPEOPLENAME, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(BYQUESTIONINGPEOPLENAME)) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(BYQUESTIONINGPEOPLEIDENTIFYNUM))){
			xql += "and arar.handlingAreaReceipt.basicCase.byQuestioningPeopleIdentifyNum like :byQuestioningPeopleIdentifyNum ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(BYQUESTIONINGPEOPLEIDENTIFYNUM, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(BYQUESTIONINGPEOPLEIDENTIFYNUM)) + "%");
		}
		if (ParamMapUtil.isNotBlank(queryCondtidions.get(UNIT))) {
			xql += "and arar.askRoom.unit.id = :unit ";
			xqlMap.put(UNIT, queryCondtidions.get(UNIT));
		}
		if (ParamMapUtil.isNotBlank(queryCondtidions.get(HANDLINGPOLICE))) {
			xql += "and arar.handlingAreaReceipt.basicCase.handlingPolice like :handlingPolice ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(HANDLINGPOLICE, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(HANDLINGPOLICE)) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(LAWCASE))){
			xql += "and arar.handlingAreaReceipt.basicCase.lawCase = :lawCase ";
			xqlMap.put(LAWCASE, queryCondtidions.get(LAWCASE));
		}
		
		if (ParamMapUtil.isNotBlank(queryCondtidions.get(ALLOCATIONSTARTTIME))) {
			xql += "and arar.allocationTime >= :allocationStartTime ";
			xqlMap.put(ALLOCATIONSTARTTIME, queryCondtidions.get(ALLOCATIONSTARTTIME));
		}
		if (ParamMapUtil.isNotBlank(queryCondtidions.get(ALLOCATIONENDTIME))) {
			xql += "and arar.allocationTime < :allocationEndTime ";
			xqlMap.put(ALLOCATIONENDTIME, queryCondtidions.get(ALLOCATIONENDTIME));
		}
		
		return this.dao.findAllByParams(AskRoomAllocationRecord.class, xql, xqlMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pager<AskRoomAllocationRecord> findAskRoomAllocationRecordByKeyForPage(Map<String, Object> queryCondtidions,int pageNo, int pageSize) {
		String xql = "select arar from AskRoomAllocationRecord as arar where 1 = 1 ";
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(ASKROOMNAME))){
			xql += "and arar.askRoom.name like :askRoomName ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(ASKROOMNAME, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(ASKROOMNAME)) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(ASKROOMID))){
			xql += "and arar.askRoom.id = :askRoomId ";
			xqlMap.put(ASKROOMID, queryCondtidions.get(ASKROOMID));
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(HANDLINGAREARECEIPTNUM))){
			xql += "and arar.handlingAreaReceipt.basicCase.handlingAreaReceiptNum like :handlingAreaReceiptNum ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(HANDLINGAREARECEIPTNUM, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(HANDLINGAREARECEIPTNUM)) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(ENTERAREAREASONS))){
			xql += "and arar.handlingAreaReceipt.basicCase.enterAreaReasons = :enterAreaReasons ";
			xqlMap.put(ENTERAREAREASONS, queryCondtidions.get(ENTERAREAREASONS));
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(BYQUESTIONINGPEOPLENAME))){
			xql += "and arar.handlingAreaReceipt.basicCase.byQuestioningPeopleName like :byQuestioningPeopleName ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(BYQUESTIONINGPEOPLENAME, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(BYQUESTIONINGPEOPLENAME)) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(BYQUESTIONINGPEOPLEIDENTIFYNUM))){
			xql += "and arar.handlingAreaReceipt.basicCase.byQuestioningPeopleIdentifyNum like :byQuestioningPeopleIdentifyNum ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(BYQUESTIONINGPEOPLEIDENTIFYNUM, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(BYQUESTIONINGPEOPLEIDENTIFYNUM)) + "%");
		}
		if (ParamMapUtil.isNotBlank(queryCondtidions.get(UNIT))) {
			xql += "and arar.askRoom.unit.id = :unit ";
			xqlMap.put(UNIT, queryCondtidions.get(UNIT));
		}
		if (ParamMapUtil.isNotBlank(queryCondtidions.get(HANDLINGPOLICE))) {
			xql += "and arar.handlingAreaReceipt.basicCase.handlingPolice like :handlingPolice ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(HANDLINGPOLICE, "%" + SQLTool.SQLSpecialChTranfer((String) queryCondtidions.get(HANDLINGPOLICE)) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryCondtidions.get(LAWCASE))){
			xql += "and arar.handlingAreaReceipt.basicCase.lawCase = :lawCase ";
			xqlMap.put(LAWCASE, queryCondtidions.get(LAWCASE));
		}
		
		if (ParamMapUtil.isNotBlank(queryCondtidions.get(ALLOCATIONSTARTTIME))) {
			xql += "and arar.allocationTime >= :allocationStartTime ";
			xqlMap.put(ALLOCATIONSTARTTIME, queryCondtidions.get(ALLOCATIONSTARTTIME));
		}
		if (ParamMapUtil.isNotBlank(queryCondtidions.get(ALLOCATIONENDTIME))) {
			xql += "and arar.allocationTime < :allocationEndTime ";
			xqlMap.put(ALLOCATIONENDTIME, queryCondtidions.get(ALLOCATIONENDTIME));
		}
		if("allocationTime".equals(queryCondtidions.get("orderCondition"))){
			xql += " order by arar.allocationTime desc";
		}else if("askRoomName".equals(queryCondtidions.get("orderCondition"))){
			xql += " order by arar.askRoom.name";
		}else if("handlingAreaReceiptNum".equals(queryCondtidions.get("orderCondition"))){
			xql += " order by arar.handlingAreaReceipt.basicCase.handlingAreaReceiptNum";
		}else if("byQuestioningPeopleName".equals(queryCondtidions.get("orderCondition"))){
			xql += " order by arar.handlingAreaReceipt.basicCase.byQuestioningPeopleName";
		}
		
		return this.dao.findByPage(AskRoomAllocationRecord.class, xql, xqlMap,pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AskRoomAllocationRecord> findAskRoomAllocationRecordByHandlingAreaReceiptId(
			String handlingAreaReceiptId) {
		String xql = "select arar from AskRoomAllocationRecord as arar where arar.handlingAreaReceipt.id = ? order by arar.allocationTime";
		return this.dao.findAllByParams(AskRoomAllocationRecord.class,xql, new Object[]{handlingAreaReceiptId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityRoom findAskRoomByHandlingAreaReceiptId(String handlingAreaReceiptId) {
		String xql = "select arar from AskRoomAllocationRecord as arar where arar.handlingAreaReceipt.id = ? order by arar.allocationTime desc";
		List<AskRoomAllocationRecord> ararList =  this.dao.findAllByParams(AskRoomAllocationRecord.class, xql, new Object[]{handlingAreaReceiptId});
		if(!ararList.isEmpty()){
			if(ararList.get(0).getLeaveTime() == null){
				return ararList.get(0).getActivityRoom();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAskRoomStatusByHandlingAreaReceiptId(String handlingAreaReceiptId) {
		ActivityRoom askRoom = this.findAskRoomByHandlingAreaReceiptId(handlingAreaReceiptId);
		if(askRoom != null){
			askRoom.setStatus(FREEASKROOMSTATUS);
			this.dao.update(askRoom);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public AskRoomAllocationRecord getLastAskRoomAllocation(String activityRoomId) {
		List<AskRoomAllocationRecord> askRoomAllocationRecordList = new ArrayList<AskRoomAllocationRecord>();
		String xql = "select rec from AskRoomAllocationRecord as rec where rec.askRoom.id=? order by rec.allocationTime desc";
		askRoomAllocationRecordList = this.dao.findAllByParams(AskRoomAllocationRecord.class, xql, new Object[]{activityRoomId});
		return ((askRoomAllocationRecordList == null || askRoomAllocationRecordList.size() <= 0) ? null : askRoomAllocationRecordList.get(0));
	}

	@Override
	public RoomUseInformationBean getRoomInformationByRoomId(String roomId) {
		RoomUseInformationBean bean = new RoomUseInformationBean();
		String xql = "select rec from AskRoomAllocationRecord as rec where rec.askRoom.id=? and rec.askRoom.status = ? order by rec.allocationTime desc";
		List<AskRoomAllocationRecord> askRoomAllocationRecordList = dao.findAllByParams(AskRoomAllocationRecord.class, xql, new Object[]{roomId, OCCUPIEDASKROOMSTATUS});
		if(askRoomAllocationRecordList.size() == 0 || askRoomAllocationRecordList == null){
			bean = null;
		}else{
			HandlingAreaReceipt handlingAreaReceipt = askRoomAllocationRecordList.get(0).getHandlingAreaReceipt();
			bean.setHandlingAreaReceiptId(handlingAreaReceipt.getId());
			bean.setRoomName(askRoomAllocationRecordList.get(0).getAskRoom().getName());
			bean.setHandlingAreaReceiptNum(handlingAreaReceipt.getBasicCase().getHandlingAreaReceiptNum());
			bean.setByQuestioningPeopleIdentifyNum(handlingAreaReceipt.getBasicCase().getByQuestioningPeopleIdentifyNum());
			bean.setByQuestioningPeopleName(handlingAreaReceipt.getBasicCase().getByQuestioningPeopleName());
			List<String> listName= new ArrayList<String>();
			List<String> listNum=new ArrayList<String>();
			Set<HandlingAreaReceiptToPoliceInfo> htpLst = handlingAreaReceipt.getHandlingAreaReceiptToPoliceInfoList();
			for(HandlingAreaReceiptToPoliceInfo temp : htpLst){
				PoliceInfo policeInfo = temp.getPoliceInfo();
				if(IF_IS.equalsIgnoreCase(temp.getIfMainPolice())){
					listName.add(policeInfo.getPoliceName());
					listNum.add(policeInfo.getPoliceNum());
				}
			}
			bean.setMainPoliceNameLst(listName);
			bean.setMainPoliceNumLst(listNum);
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AskRoomAllocationRecord findAskRoomAllocationRecordByReceiptIdAndRoomId(String receiptId, String roomId) {
		String xql = "select r from AskRoomAllocationRecord as r where r.handlingAreaReceipt.id = ? and r.askRoom.id = ? order by r.allocationTime desc";
		List<AskRoomAllocationRecord> lst = dao.findAllByParams(AskRoomAllocationRecord.class, xql, new Object[]{receiptId, roomId});
		if(lst != null && lst.size() > 0){
			return lst.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAskRoomAllocationRecord(AskRoomAllocationRecord askRoomAllocationRecord) {
		dao.update(askRoomAllocationRecord);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AskRoomAllocationRecord> findAskRoomAllocationRecordByPoliceNum(String policeNum) {
		String xql = "select arar from AskRoomAllocationRecord as arar where arar.policeNum like ? order by arar.allocationTime";
		return this.dao.findAllByParams(AskRoomAllocationRecord.class,xql, new Object[]{"%"+policeNum+"%"});
	}
	
}