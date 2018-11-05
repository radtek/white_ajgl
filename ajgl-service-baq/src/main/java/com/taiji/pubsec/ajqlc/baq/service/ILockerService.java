package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.bean.LockerStockedArticleBean;
import com.taiji.pubsec.ajqlc.baq.model.Locker;

/**
 * 储物柜服务接口
 * @author chengkai
 *
 */
public interface ILockerService {

	/**
	 * 保存储物柜，并返回其主键
	 * @param locker 储物柜实体
	 */
	public String saveLocker(Locker locker);
	
	/**
	 * 删除储物柜
	 * @param lockerIds 多个储物柜id
	 * @return 不能删除的储物柜的名称list
	 */
	public List<String> deleteLocker(String... lockerIds); 
	
	/**
	 * 更新储物柜
	 * @param locker 储物柜实体
	 */
	public void updateLocker(Locker locker);

	/**
	 * 更新储物柜的状态
	 * @param id 储物柜id
	 * @param status 状态
	 */
	public void updateLockerStatus(String id, String status);
	
	/**
	 * 根据id查找储物柜
	 * @param id 储物柜id
	 * @return 储物柜实体
	 */
	public Locker findLockerById(String id);
	
	/**
	 * 根据编码查找储物柜
	 * @param code 储物柜编码
	 * @return 储物柜实体
	 */
	public Locker findLockerByCode(String code);
	
	/**
	 * 根据单位id和名称查找储物柜
	 * @param unitId 单位id
	 * @param position 位置
	 * @return 储物柜实体
	 */
	public Locker findLockerByUnitIdAndPosition(String unitId, String position);
	
	/**
	 * 根据单位id和编号查找储物柜
	 * @param unitId 单位id
	 * @param code 编码
	 * @return 储物柜实体
	 */
	public Locker findLockerByUnitIdAndCode(String unitId, String code);
	
	/**
	 * 分页查询储物柜
	 * @param code 编码
	 * @param status 状态
	 * @param unitId 单位id
	 * @param position 位置
	 * @param note 备注
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 储物柜分页信息
	 */
	public Pager<Locker> findLockerByKey(String code, String status, String unitId,
			String position, String note, int pageNo, int pageSize);
	
	/**
	 * 根据单位id和储物柜状态查询储物柜
	 * @param unitId 单位id
	 * @param status 储物柜状态
	 * @return 返回储物柜实体list
	 */
	public List<Locker> findLockerByUnitIdAndStatus(String unitId, String status);
	
	/**
	 * 根据储物柜id判断该储物柜是否被使用过
	 * @param lockerId 储物柜id
	 * @return 若该储物柜被储物柜使用过，返回true，否则返回false
	 */
	public boolean isLockerUsed(String lockerId);

	/**
	 * 按条件查询所有储物柜
	 * 
	 * @param name 储物柜名称
	 * @param unit 储物柜所在单位id
	 * @param areaCode 储物柜区号
	 * @param lockerCode 储物柜柜号
	 * @param status 储物柜状态
	 * @return "success",成功返回lockerBeanList:储物柜集合
	 */
	public List<Locker> findLockerByCondition(Map<String, Object> data);

	/**
	 * 查詢所有的區號
	 * @return
	 */
	public List<String> findAllLockerCode();

	/**
	 * 查询指定区号的柜号
	 * @param status
	 * @return
	 */
	public List<String> findAllAreaCodeSecond(String areaCode);

	/**
	 * 通过柜子id查询储物柜在库情况信息
	 * @param lockerId 柜子id
	 * @return 返回储物柜在库情况信息
	 */
	public LockerStockedArticleBean queryLockerStockedConditions(String lockerId);
	
	/**
	 * 随机获取一个空柜子
	 * @return 返回一个空柜子
	 */
	public Locker findOneRandomEmptyLocker();
	
}
