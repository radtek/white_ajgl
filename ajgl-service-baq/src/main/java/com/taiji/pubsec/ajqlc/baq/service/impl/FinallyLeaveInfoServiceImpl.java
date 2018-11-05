package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.FinallyLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.model.TmpLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.service.IFinallyLeaveInfoService;
import com.taiji.pubsec.ajqlc.baq.service.ITmpLeaveInfoService;

/**
 * @author chengkai
 */
@Service("finallyLeaveInfoService")
public class FinallyLeaveInfoServiceImpl implements IFinallyLeaveInfoService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private ITmpLeaveInfoService tmpLeaveInfoService;

	@SuppressWarnings("unchecked")
	@Override
	public void saveFinallyLeaveInfo(FinallyLeaveInfo finallyLeaveInfo, List<TmpLeaveInfo> tliList) {
		this.dao.save(finallyLeaveInfo);
		for(TmpLeaveInfo tli: tliList){
			tli.setFinallyLeaveInfo(finallyLeaveInfo);
			tmpLeaveInfoService.save(tli);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateFinallyLeaveInfo(FinallyLeaveInfo finallyLeaveInfo, List<TmpLeaveInfo> tliList, List<String> deleteIds) {
		List<TmpLeaveInfo> oldTliLista = new ArrayList<TmpLeaveInfo>();
		List<TmpLeaveInfo> oldTliListb = new ArrayList<TmpLeaveInfo>();
		oldTliLista.addAll(finallyLeaveInfo.getTmpLeaveInfos());
		oldTliListb.addAll(finallyLeaveInfo.getTmpLeaveInfos());
		List<TmpLeaveInfo> newTliLista = new ArrayList<TmpLeaveInfo>();
		List<TmpLeaveInfo> newTliListb = new ArrayList<TmpLeaveInfo>();
		newTliLista.addAll(tliList);
		newTliListb.addAll(tliList);

		newTliLista.removeAll(oldTliLista);
		for(TmpLeaveInfo tli: newTliLista){
			tli.setFinallyLeaveInfo(finallyLeaveInfo);
			tmpLeaveInfoService.save(tli);
		}
		
		newTliListb.retainAll(oldTliListb);
		for(TmpLeaveInfo tli: newTliListb){
			tmpLeaveInfoService.update(tli);
		}
		
		for(String id: deleteIds){
			tmpLeaveInfoService.delete(this.tmpLeaveInfoService.findById(id));
		}
		
		this.dao.update(finallyLeaveInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FinallyLeaveInfo findFinallyLeaveInfoById(String finallyLeaveInfoId) {
		return (FinallyLeaveInfo) this.dao.findById(FinallyLeaveInfo.class, finallyLeaveInfoId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FinallyLeaveInfo findFinallyLeaveInfoByHandlingAreaReceiptId(String handlingAreaReceiptId) {
		String xql = "select fli from FinallyLeaveInfo as fli where fli.handlingAreaReceipt.id = ?";
		return (FinallyLeaveInfo) this.dao.findByParams(FinallyLeaveInfo.class, xql, new Object[]{handlingAreaReceiptId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(FinallyLeaveInfo entity) {
		this.dao.delete(entity);
	}
	
}