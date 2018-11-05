package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.HandleAreaActivityRecordInfo;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;
import com.taiji.pubsec.ajqlc.baq.service.IHandleAreaActivityRecordInfoService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaActivityRecordService;

/**
 * @author chengkai
 */
@Service("handleAreaActivityRecordInfoService")
public class HandleAreaActivityRecordInfoServiceImpl implements IHandleAreaActivityRecordInfoService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource 
	private IHandlingAreaActivityRecordService handlingAreaActivityRecordService;

	private static final Logger LOGGER = LoggerFactory.getLogger(HandleAreaActivityRecordInfoServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveHandleAreaActivityRecordInfo(HandleAreaActivityRecordInfo handleAreaActivityRecordInfo,
			List<HandlingAreaActivityRecord> haarList) {
		this.dao.save(handleAreaActivityRecordInfo);
		for(HandlingAreaActivityRecord harr: haarList){
			harr.setHandleAreaActivityRecordInfo(handleAreaActivityRecordInfo);
			handlingAreaActivityRecordService.save(harr);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateHandleAreaActivityRecordInfo(HandleAreaActivityRecordInfo handleAreaActivityRecordInfo,
			List<HandlingAreaActivityRecord> haarList, List<String> deleteIds) {
		List<HandlingAreaActivityRecord> oldHarrLista = new ArrayList<HandlingAreaActivityRecord>();
		List<HandlingAreaActivityRecord> oldHarrListb = new ArrayList<HandlingAreaActivityRecord>();
		oldHarrLista.addAll(handleAreaActivityRecordInfo.getHandlingAreaActivityRecords());
		oldHarrListb.addAll(handleAreaActivityRecordInfo.getHandlingAreaActivityRecords());
		List<HandlingAreaActivityRecord> newHarrLista = new ArrayList<HandlingAreaActivityRecord>();
		List<HandlingAreaActivityRecord> newHarrListb = new ArrayList<HandlingAreaActivityRecord>();
		newHarrLista.addAll(haarList);
		newHarrListb.addAll(haarList);
		
		newHarrLista.removeAll(oldHarrLista);
		for(HandlingAreaActivityRecord harr: newHarrLista){
			harr.setHandleAreaActivityRecordInfo(handleAreaActivityRecordInfo);
			handlingAreaActivityRecordService.save(harr);
		}
		
		newHarrListb.retainAll(oldHarrListb);
		for(HandlingAreaActivityRecord harr: newHarrListb){
			handlingAreaActivityRecordService.update(harr);
		}
		
		for(String id: deleteIds){
			handlingAreaActivityRecordService.delete(id);
		}
		
		this.dao.update(handleAreaActivityRecordInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HandleAreaActivityRecordInfo findHandleAreaActivityRecordInfoById(String activityRecordInfoId) {
		return (HandleAreaActivityRecordInfo) this.dao.findById(HandleAreaActivityRecordInfo.class, activityRecordInfoId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HandleAreaActivityRecordInfo findHandleAreaActivityRecordInfoByHandlingAreaReceiptId(
			String handlingAreaReceiptId) {
		String xql = "select haari from HandleAreaActivityRecordInfo as haari where haari.handlingAreaReceipt.id = ?";
		return (HandleAreaActivityRecordInfo) this.dao.findByParams(HandleAreaActivityRecordInfo.class, xql, new Object[]{handlingAreaReceiptId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void changeRecordCount(String activityRecordInfoId, int num) {
		HandleAreaActivityRecordInfo haari = this.findHandleAreaActivityRecordInfoById(activityRecordInfoId);
		try{
			int numChanged = haari.getRecordCount() + num;
			haari.setRecordCount(numChanged);
			this.dao.update(haari);
		}catch (Exception e) {
			LOGGER.debug("ID为"+ haari + "的活动记录已删除，不再更新笔录数量。");
		}
	}

	
	
}