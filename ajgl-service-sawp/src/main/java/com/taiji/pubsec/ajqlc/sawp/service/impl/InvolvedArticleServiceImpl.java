package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;

@Service("involvedArticleService")
public class InvolvedArticleServiceImpl extends AbstractBaseService<InvolvedArticle, String> implements IInvolvedArticleService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public void createInvolvedArticle(InvolvedArticle involvedArticle) {
		this.dao.save(involvedArticle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public InvolvedArticle findInvolvedArticleById(String involvedArticleId) {
		return (InvolvedArticle) this.dao.findById(InvolvedArticle.class, involvedArticleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public InvolvedArticle findInvolvedArticleByCode(String involvedArticleCode) {
		String xql = "select i from InvolvedArticle as i where i.code = :involvedArticleCode";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		xqlMap.put("involvedArticleCode", involvedArticleCode);
		List<InvolvedArticle> list = this.dao.findAllByParams(InvolvedArticle.class, xql, xqlMap);
		return list.isEmpty() ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvolvedArticle> findInvolvedArticlesByCase(String caseCode) {
		String xql = "select i from InvolvedArticle as i where i.caseCode = :caseCode";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		xqlMap.put("caseCode", caseCode);
		return this.dao.findAllByParams(InvolvedArticle.class, xql, xqlMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(String articleId) {
		this.dao.delete(InvolvedArticle.class, articleId);
	}
	
	@SuppressWarnings("unchecked")
	public void update(InvolvedArticle article){
		this.dao.update(article);
	}

}
