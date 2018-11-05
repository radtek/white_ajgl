package com.taiji.pubsec.ajqlc.syncdh.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.taiji.persistence.dao.SqlDao;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.BurnRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBurnRecordService;

public class SyncHarBurningInfo {
	
	@Resource
	private SqlDao jpaSqlDao;
    @Resource
    private IBurnRecordService burnRecordService;
    @Resource
    private IHandlingAreaReceiptService handlingAreaReceiptService;
    
	@SuppressWarnings("unchecked")
	public void syncBurningInfo() throws DahuaException{
		System.out.println("===================同步使用单刻录信息===================");
		String sql  = "select sydbh from t_baq_jbqk,t_baq_baqsyd where t_baq_baqsyd.id = t_baq_jbqk.baqsyd_id and zt = :zt and klzt = :klzt";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zt", Constant.SYDZT_YWC);
		map.put("klzt", Constant.KLZT_F);
		List<String> result = jpaSqlDao.find(sql, map);
		for(String temp : result){
			List<BurnRecordBean> beanLst = burnRecordService.queryBurnRecord(temp, "", "");
			if(beanLst != null && beanLst.size() > 0){
				HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(temp);
				if(har != null){
					har.setRecordStatus(Constant.KLZT_S);
					handlingAreaReceiptService.updateHandlingAreaReceipt(har);
				}
			}
		}
	}
}
