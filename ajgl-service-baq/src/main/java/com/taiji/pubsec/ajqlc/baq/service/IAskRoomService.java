package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.bean.ActivityRoomServiceBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;

/**
 * 问询室服务接口
 * @author chengkai
 *
 */
public interface IAskRoomService {
	
	/**
	 * 保存问询室，并返回其主键
	 * @param askRoom 问询室实体
	 * @return 返回刚保存的问询室的主键
	 */
	public String saveAskRoom(ActivityRoom askRoom);
	
	/**
	 * 删除问询室,参数为ids数组
	 * @param askRoomIds 多个问询室id
	 * @return 不能删除的问询室的名称list
	 */
	public List<String> deleteAskRoom(String... askRoomIds); 
	
	/**
	 * 删除其他房间，参数为ids数组
	 * @param otherIds 多个房间id
	 * @return 不能删除的房间的名称list
	 */
	public List<String> deleteOtherRoom(String... otherIds);
	
	/**
	 * 更新问询室
	 * @param askRoom 问询室实体
	 */
	public void updateAskRoom(ActivityRoom askRoom);

	/**
	 * 更新问询室的状态
	 * @param id 问询室id
	 * @param status 状态
	 */
	public void updateAskRoomStatus(String id, String status);
	
	/**
	 * 根据id查找问询室
	 * @param id 问询室id
	 * @return 问询室实体
	 */
	public ActivityRoom findAskRoomById(String id);
	
	/**
	 * 根据编码查找问询室
	 * @param code 问询室编码
	 * @return 问询室实体
	 */
	public ActivityRoom findAskRoomByCode(String code);
	
	/**
	 * 根据单位id和名称查找问询室
	 * @param unitId 单位id
	 * @param name 名称
	 * @return 问询室实体
	 */
	public ActivityRoom findAskRoomByUnitIdAndName(String unitId, String name);
	
	/**
	 * 根据单位和编码查找问询室
	 * @param unitId 单位id
	 * @param code 编码
	 * @return 问询室实体
	 */
	public ActivityRoom findAskRoomByUnitIdAndCode(String unitId, String code);
	/**
	 * 查找所有单位的所有询问室
	
	 * @return 问询室List order by status
	 */
	public List<ActivityRoom> findAskRoomAll();
	
	/**
	 * 根据问询室类型查找所有该类型的问询室
	 * @param type 问询室类型
	 * @return 问询室实体list
	 */
	public List<ActivityRoom> findAskRoomByType(String type);
	
	/**
	 * 分页查询问询室
	 * @param queryConditions 查询条件，Map<String, String>类型，查询条件有：
	 * 		 code 编码
	 * 		 name 名称
	 * 		 status 状态
	 * 		 unitId 单位id
	 * 		 note 备注
	 * 		 type 类型
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 问询室分页信息
	 */
	public Pager<ActivityRoom> findAskRoomByKey(Map<String, String> queryConditions, int pageNo, int pageSize);
	
	/**
	 * 根据问询室id判断该问询室是否被分配或使用过
	 * @param askRoomId 问询室id
	 * @return 若该问询室被分配使用过，返回true，否则返回false
	 */
	public boolean isAskRoomUsed(String askRoomId);
	
	/**
	 * 查询所有房间类型为询问室的房间
	 * @return 返回房间list
	 */
	public List<ActivityRoom> findAllAskRooms();
	
	/**
	 * 查询相应房间类的房间list
	 * @param type 房间类型
	 * @param typeName 房间类型名称
	 * @return 返回房间list
	 */
	public List<ActivityRoomServiceBean> findVariousRooms(String type, String typeName);
	
	/**
	 * 通过房间类型名称和房间名称查询当前在该房间中的人
	 * @param roomType 房间类型名称
	 * @param roomName 房间名称
	 * @return 返回Map<使用单编号, 嫌疑人姓名>
	 */
	public Map<String, String> findSuspectsByConditions(String roomTypeName, String roomName);
	
	/**
	 * 通过房间名称查询房间id
	 * @param roomName 房间名称
	 * @return 返回相应的房间id
	 */
	public String findRoomIdByRoomName(String roomName);
}