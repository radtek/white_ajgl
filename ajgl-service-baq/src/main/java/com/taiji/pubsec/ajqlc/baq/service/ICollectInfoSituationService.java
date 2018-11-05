package com.taiji.pubsec.ajqlc.baq.service;

import com.taiji.pubsec.ajqlc.baq.model.CollectInfoSituation;

/**
 * 信息采集服务接口
 * @author wangfx
 *
 */
public interface ICollectInfoSituationService {
	
	/**
	 * 保存信息采集情况
	 * @param collectInfoSituation 信息采集情况，
	 * 		  handlingAreaReceipt 需要办案区使用单信息
	 * @return 办案区使用单id
	 */
	public String saveCollectInfoSituation (CollectInfoSituation collectInfoSituation);
	
	/**
	 * 修改信息采集情况，同时修改办案区使用单最新修改人和最新修改时间
	 * @param collectInfoSituation
	 */
	public void updateCollectInfoSituation(CollectInfoSituation collectInfoSituation);

}
