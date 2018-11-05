package com.taiji.pubsec.ajqlc.baq.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomIllegalRecord;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomIllegalRecordService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

/**
 * @author XIEHF
 */
@Service("askRoomIllegalRecordService")
@Transactional(rollbackFor = Exception.class)
public class AskRoomIlleaglRecordServiceImpl implements IAskRoomIllegalRecordService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	
	public static final String ROOMNAME = "roomName"; // 房间名
	public static final String HANDLINGAREARECEIPTNUM = "handlingAreaReceiptNum"; // 使用单编号
	public static final String COMMITPEOPLE = "commitPeople"; // 提交人
	public static final String UNITID = "unitId"; // 单位id
	public static final String STARTTIME = "startTime"; // 时间查询上限
	public static final String ENDTIME = "endTime"; // 时间查询下限
	public static final String ROOMID = "roomId"; // 房间ID
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AskRoomIlleaglRecordServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public void saveAskRoomIllegalRecord(AskRoomIllegalRecord askRoomIllegalRecord) {
		this.dao.saveOrUpdate(askRoomIllegalRecord);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pager<AskRoomIllegalRecord> findAskRoomUseAbnormalRecordByKey(Map<String, String> queryCondtidions,int pageNo, int pageSize) {
		String xql = "select arar from AskRoomIllegalRecord as arar where 1 = 1 ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		
		if(StringUtils.isNotEmpty(queryCondtidions.get(ROOMID))){
			xql += "and arar.askRoomAllocationRecord.askRoom.id = :roomId ";
			xqlMap.put(ROOMID, queryCondtidions.get(ROOMID));
		}
		if(StringUtils.isNotEmpty(queryCondtidions.get(ROOMNAME))){
			xql += "and arar.askRoomAllocationRecord.askRoom.name = :roomName ";
			xqlMap.put(ROOMNAME, queryCondtidions.get(ROOMNAME));
		}
		if(StringUtils.isNotEmpty(queryCondtidions.get(HANDLINGAREARECEIPTNUM))){
			xql += "and arar.askRoomAllocationRecord.handlingAreaReceipt.basicCase.handlingAreaReceiptNum like :handlingAreaReceiptNum ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put(HANDLINGAREARECEIPTNUM, "%" + SQLTool.SQLSpecialChTranfer(queryCondtidions.get(HANDLINGAREARECEIPTNUM)) + "%");
		}
		if(StringUtils.isNotEmpty(queryCondtidions.get(COMMITPEOPLE))){
			xql += "and arar.commitPeople = :commitPeople ";
			xqlMap.put(COMMITPEOPLE, queryCondtidions.get(COMMITPEOPLE));
		}
		if (StringUtils.isNotEmpty(queryCondtidions.get(UNITID))) {
			xql += "and arar.askRoomAllocationRecord.askRoom.unit.id = :unitId ";
			xqlMap.put(UNITID, queryCondtidions.get(UNITID));
		}
		try {
			if (StringUtils.isNotEmpty(queryCondtidions.get(STARTTIME))) {
				xql += "and arar.illegalTime >= :startTime ";
				xqlMap.put(STARTTIME, sdf.parse(queryCondtidions.get(STARTTIME)));
			}
			if (StringUtils.isNotEmpty(queryCondtidions.get(ENDTIME))) {
				xql += "and arar.illegalTime < :endTime ";
				xqlMap.put(ENDTIME, sdf.parse(queryCondtidions.get(ENDTIME)));
			}
		} catch (ParseException e) {
			LOGGER.debug("产生异常：", e);
		}
		xql += " order by arar.illegalTime desc";
		return this.dao.findByPage(AskRoomIllegalRecord.class, xql, xqlMap,pageNo, pageSize);
	}


	
	
}