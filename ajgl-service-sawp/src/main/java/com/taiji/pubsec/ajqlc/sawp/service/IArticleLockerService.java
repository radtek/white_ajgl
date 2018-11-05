package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;

/**
 * 储物柜服务接口
 * @author chengkai
 */
public interface IArticleLockerService{
	
	/**
	 * 新增储物柜
	 * @param locker 储物柜实体
	 */
	public void save(ArticleLocker locker);
	
	/**
	 * 修改储物柜
	 * @param locker 储物柜实体
	 */
	public void update(ArticleLocker locker);
	
	/**
	 * 删除储物柜
	 * @param locker 储物柜实体
	 */
	public void delete(ArticleLocker locker);
	
	/**
	 * 查询一个物证保管区的所有储物柜
	 * @param storageAreaId 保管区id
	 * @return 返回该保管区的所有储物柜list
	 */
	public List<ArticleLocker> findLockersByStorageAreaId(String storageAreaId);
	
	/**
	 * 通过保管区id查询该保管区的储物柜的分页信息
	 * @param storageAreaId 保管区id
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回储物柜分页信息
	 */
	public Pager<ArticleLocker> findArticleLockersByStorageAreaIdForPage(String storageAreaId, int pageNo, int pageSize);
	
	/**
	 * 根据储物柜id查询储物柜
	 * @param lockerId 储物柜id
	 * @return 返回储物柜实体信息
	 */
	public ArticleLocker findById(String lockerId);
	
	/**
	 * 通过名称查找储物柜
	 * @param areaId 保管区id
	 * @param name 储物柜位置
	 * @return 返回储物柜信息
	 */
	public ArticleLocker findByName(String areaId, String name);
	
	/**
	 * 通过保管区id和储物架编码查找储物柜
	 * @param areaId 保管区id
	 * @param code 储物架编码
	 * @return 返回储物架信息
	 */
	public ArticleLocker findByCode(String areaId, String code);
	
	/**
	 * 通过储物架编号查找储物架
	 * @param code 储物架编号
	 * @return 返回储物架信息
	 */
	public ArticleLocker findByCode(String code);
	
	/**
	 * 通过 案件编号和保管区id查询柜子list
	 * @param caseCode 案件编号
	 * @param areaId 保管区id
	 * @return 返回柜子list
	 */
	public List<ArticleLocker> findLockersByCaseCodeAndAreaId(String caseCode, String areaId); 
	
	/**
	 * 通过案件编号list查询所有这些案件在用的储物柜
	 * @param caseCodes 案件编号list
	 * @return 返回所有在用的柜子和相应的
	 */
	Map<ArticleLocker, String> findUsedLockersByCaseCodes(List<String> caseCodes);
	
	/**
	 * 通过柜子的code查询正在使用该柜子的案件的编号
	 * @param lockerCode 柜子code
	 * @return 返回案件编号
	 */
	String findCaseCodeByLockerCode(String lockerCode);
}
