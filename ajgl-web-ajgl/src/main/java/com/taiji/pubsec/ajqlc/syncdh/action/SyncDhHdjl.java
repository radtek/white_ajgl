package com.taiji.pubsec.ajqlc.syncdh.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.service.IActivityRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IActivationRecordService;


/**
 * 同步大华数据的活动记录
 * @author xinfan
 *
 */
public class SyncDhHdjl {
     @Resource
     private IActivationRecordService  activationRecordServiceImpl;
     @Resource
     private IActivityRecordService activityRecordService;
     @Resource 
     private IHandlingAreaReceiptService handlingAreaReceiptService;
     @SuppressWarnings("rawtypes")
 	 @Resource
 	 private Dao dao;
	 public void syncHdjlData() throws DahuaException{
		 Map<String,Object> params = new HashMap<String,Object>();
		 params.put("status", "0000000006001");
		 Integer pageNo = 0;
		 Integer pageSize = Integer.MAX_VALUE;
		 int count  = 0;
		 Pager<HandlingAreaReceipt> handlingAreaReceipts = null;
		 do{  
			 // 1  查找未完结的使用单
			  handlingAreaReceipts = handlingAreaReceiptService.findHandlingAreaReceiptByKey(params, pageNo, pageSize);
			  if(handlingAreaReceipts != null && handlingAreaReceipts.getPageList().size() > 0){
				  for(HandlingAreaReceipt handlingAreaReceiptTemp : handlingAreaReceipts.getPageList()){
					  String result = activationRecordServiceImpl.syncVideoRelayHistoryInfo(handlingAreaReceiptTemp.getBasicCase().getHandlingAreaReceiptNum(), "", "");
					  activityRecordService.saveActivationRecord(result, handlingAreaReceiptTemp.getBasicCase().getHandlingAreaReceiptNum());
					  count++;
				  }
			  }
		 }while(count < handlingAreaReceipts.getTotalNum());
	 }
}
