package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageOutInfoBean;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageOut;

public interface ITemporaryStorageOutService {
	
	/**
	 * 获取出库单编号
	 * @return
	 */
	public String acquireNum();
	
	/**
	 * 新增暂存物品出库返还单
	 * @param temporaryStorageOut 暂存物品出库返还单
	 * @return 返回新增的暂存物品出库返还单的id
	 */
	public String save(TemporaryStorageOut temporaryStorageOut);
	
	/**
	 * 更新暂存物品出库返还单，同时查看相应入库单使用的柜子是否被其他入库单占用，若没有，将柜子的状态置为“空闲”
	 * @param temporaryStorageOut 暂存物品出库返还单
	 */
	public void update(TemporaryStorageOut temporaryStorageOut);
	
	/**
	 * 根据暂存物品出库返还单id查询暂存物品出库返还单
	 * @param temporaryStorageOutId 暂存物品出库返还单id
	 * @return 返回暂存物品出库返还单，未查到返回null
	 */
	public TemporaryStorageOut findById(String temporaryStorageOutId);
	
	/**
	 * 根据暂存物品出库返还单code查询暂存物品出库返还单
	 * @param temporaryStorageOutCode 暂存物品出库返还单code
	 * @return 返回暂存物品出库返还单，未查到返回null
	 */
	public TemporaryStorageOut findByCode(String temporaryStorageOutCode);
	/**
	 * 根据使用单code查询暂存物品出库返还单
	 * @param temporaryStorageOutCode 暂存物品出库返还单code
	 * @return 返回暂存物品出库返还单，未查到返回null
	 */
	public TemporaryStorageOut findByHarCode(String harCode);
	
	/**
	 * 根据暂存物品出库返还单id删除暂存物品出库返还单
	 * @param temporaryStorageOutId 暂存物品出库返还单id
	 */
	public void delete(String temporaryStorageOutId);
	
	/**
	 * 根据查询条件查询暂存物品出库返还单
	 * @param paramMap 查询条件map，包括：
	 * <br> temporaryStorageOutCode 出库返还单编号
	 * <br> queryOutDateStart 查询出库返还日期开始
	 * <br> queryOutDateEnd 查询出库返还日期结束
	 * <br> caseCode 案件编号
	 * <br> ownerName 对应嫌疑人/物品持有人
	 * <br> myId 如果显示我的出库返还单：我的id，如果不选择显示我的出库返还单，为空
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回暂存物品出库返还单分页信息
	 */
	public Pager<TempStorageOutInfoBean> findTempStorageOutInfosByQueryConditions(Map<String, Object> paramMap, int pageNo, int pageSize);
	
	/**
	 * 通过暂存入库单编码查询对应的暂存出库返还单
	 * @param storageInCode 暂存入库单编码
	 * @return 返回暂存出库返还单，如果查不到返回null
	 */
	public TemporaryStorageOut findTemporaryStorageOutByStorageInCode(String storageInCode);
}
