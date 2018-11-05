package com.taiji.pubsec.ajqlc.sawp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleOperationRecord;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleOperationRecordService;

@Service("articleOperationRecordService")
public class ArticleOperationRecordServiceImpl extends AbstractBaseService<ArticleOperationRecord, String>
		implements IArticleOperationRecordService {

	@Autowired
	public ArticleOperationRecordServiceImpl (Dao<ArticleOperationRecord, String> dao) {
		setDao(dao);
	}

	
}
