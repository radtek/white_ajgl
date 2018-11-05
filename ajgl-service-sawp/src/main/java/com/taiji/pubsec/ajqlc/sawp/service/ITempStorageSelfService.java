package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;

/**
 * 暂存储物柜 service
 * @author xinfan
 *
 */

public interface ITempStorageSelfService {
	/**
	 * 展示储物柜情况
	 * areaId 物品区编码
	 * storageCodeList 储物柜编码list
	 * suspectNameOrIdCard 嫌疑人姓名或者身份证号
	 * policeNameOrNum 办案民警警号或者姓名
	 * caseNameOrCode 案件名称或者编码
	 * isFree 储物柜是否空闲
	 * @param paramMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pager<TemporaryStorageShelfBean> findAllTemporaryStorageInByConditions(
			Map<String, Object> paramMap, int pageNo, int pageSize);
    /**
     * 保存 暂存储物柜  状态status 存为字典项 
     * @param tempStorgeIn
     * @return
     */
  String  saveTemporaryStorageShelf(TemporaryStorageShelf tempStorgeSelf);
  /**
   * 更新 暂存储物柜
   * @param tempStorgeIn
   * @return
   */
  void updateTemporaryStorageSelf(TemporaryStorageShelf tempStorgeSelf);
  /**
   * 根据Id查找 储物柜
   * @param id
   * @return
   */
  TemporaryStorageShelf findStorageSelfById(String id);
  
  /**根据编码查找 储物柜
   * 
   * @param code
   * @return
   */
  TemporaryStorageShelf findStorageSelfByCode(String code);
  /**根据保管区id和详细位置查找 储物柜
   * @param storeAreaId
   * @param address
   * @return
   */
  TemporaryStorageShelf findStorageSelfByName(String storeAreaId, String address);
 /**
  * 分页查找所有的储物柜 按编码升序排
  * @param storageAreaId
  * @param pageNo
  * @param pageSize
  * @return
  */
  Pager<TemporaryStorageShelf> findAllTemporaryStorageShelfByPage(String storageAreaId, int pageNo, int pageSize);
  
  /**
	  * 删除储物柜
	  * @param id
	  */
  void deleteTemporaryStorageSelf(String  id);
  
  /**
   * 根据暂存物品保管区id查询暂存物品储物柜list
   * @param storageAreaId
   * @return
   */
  List<TemporaryStorageShelf> findStorageSelfByStorageAreaId(String storageAreaId); 
  
  /**
	 * 获取储物架编号
	 * @return
	 */
	String acquireNum();

}
