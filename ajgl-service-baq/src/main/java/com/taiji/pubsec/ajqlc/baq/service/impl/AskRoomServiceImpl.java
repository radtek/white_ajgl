package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.bean.ActivityRoomServiceBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

/**
 * @author chengkai
 */
@Service("askRoomService")
public class AskRoomServiceImpl implements IAskRoomService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IUnitService unitService;
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	public static final String CODE = "code"; // 编码
	public static final String NAME = "name"; // 名称
	public static final String STATUS = "status"; // 状态
	public static final String UNITID = "unitId"; // 单位id
	public static final String NOTE = "note"; // 备注
	public static final String TYPE = "type"; // 类型
	public static final String ROOM_TYPE_ASK = "0000000011001"; // 房间类型：询问室
	public static final String ROOM_TYPE_WAITASK = "0000000011003"; // 房间类型：待问室
	public static final String ROOM_TYPE_YSS = "0000000011004"; // 房间类型：待问室
	public static final String GRID_TYPE_WAITASK = "待问室"; // 网格类型：待问室
	
	@SuppressWarnings("unchecked")
	@Override
	public String saveAskRoom(ActivityRoom askRoom){
		this.dao.save(askRoom);
		return askRoom.getId();
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<String> deleteAskRoom(String... askRoomIds){
		List<String> cannotDeleteAskRoomNames = new ArrayList<String>();
		for(String id: askRoomIds){
			String xql = "select a from AskRoomAllocationRecord as a where a.askRoom.id = ?";
			List<AskRoomAllocationRecord> list = this.dao.findAllByParams(AskRoomAllocationRecord.class, xql, new Object [] {id});
			String xql1 = "select haar from HandlingAreaActivityRecord as haar where haar.askRoom.id = ?";
			List<HandlingAreaActivityRecord> haarList = this.dao.findAllByParams(HandlingAreaActivityRecord.class, xql1, new Object[]{id});
			if (list.isEmpty() && haarList.isEmpty()) {
				this.dao.delete(ActivityRoom.class, id);
			} else {
				ActivityRoom askRoom = (ActivityRoom) this.dao.findById(ActivityRoom.class, id);
				cannotDeleteAskRoomNames.add(askRoom.getName());
			}
		}
		return cannotDeleteAskRoomNames;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> deleteOtherRoom(String... otherIds) {
		List<String> cannotDeleteRoomNames = new ArrayList<String>();
		for(String id: otherIds){
			String xql = "select haar from HandlingAreaActivityRecord as haar where haar.askRoom.id = ?";
			List<HandlingAreaActivityRecord> haarList = this.dao.findAllByParams(HandlingAreaActivityRecord.class, xql, new Object[]{id});
			if(haarList.isEmpty()){
				this.dao.delete(ActivityRoom.class, id);
			}else{
				ActivityRoom ar = (ActivityRoom) this.dao.findById(ActivityRoom.class, id);
				cannotDeleteRoomNames.add(ar.getName());
			}
		}
		return cannotDeleteRoomNames;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void updateAskRoom(ActivityRoom askRoom){
		this.dao.update(askRoom);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAskRoomStatus(String id, String status){
		ActivityRoom askRoom = (ActivityRoom) this.dao.findById(ActivityRoom.class, id);
		askRoom.setStatus(status);
		this.dao.update(askRoom);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ActivityRoom findAskRoomById(String id) {
		return (ActivityRoom) this.dao.findById(ActivityRoom.class, id);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public ActivityRoom findAskRoomByCode(String code) {
		String xql = "select ar from ActivityRoom as ar where ar.code = ?"; 
		return (ActivityRoom) this.dao.findByParams(ActivityRoom.class, xql, new Object[]{code});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ActivityRoom findAskRoomByUnitIdAndName(String unitId, String name){
		String xql = "select ar from ActivityRoom as ar where ar.unit.id = ? and ar.name = ?";
		return (ActivityRoom) this.dao.findByParams(ActivityRoom.class, xql, new Object[]{unitId, name});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityRoom findAskRoomByUnitIdAndCode(String unitId, String code){
		String xql = "select ar from ActivityRoom as ar where ar.unit.id = ? and ar.code = ?";
		return (ActivityRoom) this.dao.findByParams(ActivityRoom.class, xql, new Object[]{unitId, code});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pager<ActivityRoom> findAskRoomByKey(Map<String, String> queryConditions, int pageNo, int pageSize) {
		String xql = "select ar from ActivityRoom as ar where 1 = 1 ";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		
		if(StringUtils.isNotEmpty(queryConditions.get(CODE))){
			xql += "and ar.code like :code ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(CODE, "%" + SQLTool.SQLSpecialChTranfer(queryConditions.get(CODE)) + "%");
		}
		if(StringUtils.isNotEmpty(queryConditions.get(NAME))){
			xql += "and ar.name like :name ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(NAME, "%" + SQLTool.SQLSpecialChTranfer(queryConditions.get(NAME)) + "%");
		}
		if(StringUtils.isNotEmpty(queryConditions.get(STATUS))){
			xql += "and ar.status = :status ";
			xqlMap.put(STATUS, queryConditions.get(STATUS));
		}
		if(StringUtils.isNotEmpty(queryConditions.get(UNITID))){
			xql += "and ar.unit.id = :unitId ";
			xqlMap.put(UNITID, queryConditions.get(UNITID));
		}
		if(StringUtils.isNotEmpty(queryConditions.get(NOTE))){
			xql += "and ar.note like :note ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(NOTE, "%" + SQLTool.SQLSpecialChTranfer(queryConditions.get(NOTE)) + "%");
		}
		if(StringUtils.isNotEmpty(queryConditions.get(TYPE))){
			xql += "and ar.type = :type	";
			xqlMap.put(TYPE, queryConditions.get(TYPE));
		}
		xql += "order by ar.code asc";
		return this.dao.findByPage(ActivityRoom.class, xql, xqlMap, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityRoom> findAskRoomByType(String type) {
		List<ActivityRoom> arList = new ArrayList<ActivityRoom>();
		String xql = "select ar from ActivityRoom as ar where 1=1 ";
		List params = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(type)){
			xql += " and ar.type = ? ";
			params.add(type);
		}
		xql += " order by ar.code";
		arList.addAll(this.dao.findAllByParams(ActivityRoom.class, xql, params.toArray()));
		return arList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityRoom> findAskRoomAll() {
		List<ActivityRoom> arList = new ArrayList<ActivityRoom>();
		String xql = "select ar from ActivityRoom as ar order by ar.status desc,ar.name";
		arList.addAll(this.dao.findAllByParams(ActivityRoom.class,xql, new Object[]{}));
		
		return arList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isAskRoomUsed(String askRoomId) {
		String xql = "select arar from AskRoomAllocationRecord as arar where arar.askRoom.id = ?";
		List<AskRoomAllocationRecord> ararList = this.dao.findAllByParams(AskRoomAllocationRecord.class, xql, new Object[]{askRoomId});
		String xql1 = "select haar from HandlingAreaActivityRecord as haar where haar.askRoom.id = ?";
		List<HandlingAreaActivityRecord> haarList = this.dao.findAllByParams(HandlingAreaActivityRecord.class, xql1, new Object[]{askRoomId});
		if(ararList.isEmpty() && haarList.isEmpty()){
			return false;
		}else{
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityRoom> findAllAskRooms() {
		String xql = "select ar from ActivityRoom as ar where ar.type = ?";
		return dao.findAllByParams(ActivityRoom.class, xql, new Object[]{ROOM_TYPE_ASK});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityRoomServiceBean> findVariousRooms(String type, String typeName) {
		String xql = "select ar from ActivityRoom as ar where ar.type = ?";
		List<ActivityRoom> rooms = dao.findAllByParams(ActivityRoom.class, xql, new Object[]{type});
		
		List<ActivityRoomServiceBean> roomBeans = new ArrayList<ActivityRoomServiceBean>();
		for(ActivityRoom room: rooms){
			ActivityRoomServiceBean bean = new ActivityRoomServiceBean();
			String xql1 = "select ar from ActivityRecord as ar where ar.gridType = ? and ar.gridName = ? and ar.endTime is null";
			List<ActivityRecord> records = dao.findAllByParams(ActivityRecord.class, xql1, new Object[]{typeName, room.getName()});
			bean.setCode(room.getCode());
			bean.setId(room.getId());
			bean.setName(room.getName());
			bean.setNumber(records.size());
			roomBeans.add(bean);
		}
		return roomBeans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> findSuspectsByConditions(String roomTypeName, String roomName) {
		Map<String, String> result = new HashMap<String, String>();
		String xql = "select ar from ActivityRecord as ar where ar.gridType = ? and ar.gridName = ? and ar.endTime is null";
		List<ActivityRecord> records = dao.findAllByParams(ActivityRecord.class, xql, new Object[]{roomTypeName, roomName});
		
		for(ActivityRecord record: records){
			HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(record.getHandlingAreaReceiptNum());
			result.put(record.getHandlingAreaReceiptNum(), har.getBasicCase().getByQuestioningPeopleName());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findRoomIdByRoomName(String roomName) {
		String xql = "select ar.id from ActivityRoom as ar where ar.name = ?";
		return (String) dao.findByParams(ActivityRoom.class, xql, new Object[]{roomName});
	}
	

}