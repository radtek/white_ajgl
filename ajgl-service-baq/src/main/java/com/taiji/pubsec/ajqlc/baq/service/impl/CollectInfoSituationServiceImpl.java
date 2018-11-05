package com.taiji.pubsec.ajqlc.baq.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.CollectInfoSituation;
import com.taiji.pubsec.ajqlc.baq.service.ICollectInfoSituationService;

@Service("collectInfoSituationService")
@Transactional(rollbackFor = Exception.class)
public class CollectInfoSituationServiceImpl implements ICollectInfoSituationService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public String saveCollectInfoSituation(CollectInfoSituation collectInfoSituation) {
		this.dao.save(collectInfoSituation);
		return collectInfoSituation.getHandlingAreaReceipt().getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateCollectInfoSituation(CollectInfoSituation collectInfoSituation) {
		this.dao.update(collectInfoSituation);
	}

}
