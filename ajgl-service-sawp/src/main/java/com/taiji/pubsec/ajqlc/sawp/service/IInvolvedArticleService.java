package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;

import com.taiji.persistence.service.BaseService;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;

/**
 * 涉案物品管理服务接口
 * @author wangfx
 *
 */
public interface IInvolvedArticleService extends BaseService<InvolvedArticle, String>{
	
	/**
	 * 新增涉案物品
	 * @param involvedArticle 涉案物品
	 */
	void createInvolvedArticle(InvolvedArticle involvedArticle);
	
	/**
	 * 根据id查询涉案物品
	 * @param involvedArticleId 涉案物品id
	 * @return
	 */
	InvolvedArticle findInvolvedArticleById(String involvedArticleId);
	
	/**
	 * 根据code查询涉案物品
	 * @param involvedArticleCode 涉案物品code
	 * @return
	 */
	InvolvedArticle findInvolvedArticleByCode(String involvedArticleCode);
	
	/**
	 * 根据案件编码查询涉案物品
	 * @param caseCode 案件编码
	 * @return 涉案物品列表（InvolvedArticle.storages 包含保管位置信息）
	 */
	List<InvolvedArticle> findInvolvedArticlesByCase(String caseCode);
	
	/**
	 * 删除涉案物品
	 * @param articleId 涉案物品id
	 */
	void delete(String articleId);
	
	/**
	 * 更新涉案物品
	 * @param article 涉案物品
	 */
	void update(InvolvedArticle article);
}
