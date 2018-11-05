package com.taiji.pubsec.ajqlc.syncdh.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.RoomBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IRoomService;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;

public class SyncAskRoomInfo {
	/**
	 * roomIsOnline {非1：离线，1：在线}
	 * roomStatus  {0：空闲，1：使用}
	 */
	
	@Resource
	private Dao dao;
    @Resource
    private IRoomService roomServiceImpl;
    @Resource 
    private IAskRoomService askRoomService;
    @Resource
	private IDictionaryItemService dictionaryItemService;
	public void syncRoomInfo() throws DahuaException{
		List<RoomBean> roomList = roomServiceImpl.findAll();
		System.out.println("大华房间数=========================="+roomList.size());
		String sql  = "from ActivityRoom where dahuaRoomId = ?";
		for(RoomBean roomTemp : roomList){
			ActivityRoom isHasRoom = (ActivityRoom) this.dao.findByParams(ActivityRoom.class,sql, new Object[]{roomTemp.getUuid()});
		
			if(isHasRoom == null ) {
				isHasRoom = new ActivityRoom();
				}
			isHasRoom.setName(roomTemp.getRoomName());
			isHasRoom.setType(Constant.FJLX_WXS);
			if(ConstantBean.ROOM_ONLINE.equals(roomTemp.getRoomIsOnline()) && ConstantBean.ROOM_FREE.equals(roomTemp.getRoomStatus())){
				isHasRoom.setStatus(Constant.SYZT_KX);
			}else if(ConstantBean.ROOM_ONLINE.equals(roomTemp.getRoomIsOnline()) && ConstantBean.ROOM_USING.equals(roomTemp.getRoomStatus())){
				isHasRoom.setStatus(Constant.SYZT_SYZ);
			}else {
				isHasRoom.setStatus(Constant.SYZT_BKY);
			}
			isHasRoom.setUpdatedTime(new Date());
			isHasRoom.setCode(roomTemp.getUuid());
			isHasRoom.setDahuaRoomId(roomTemp.getUuid());
			if(isHasRoom.getId() != null){
				askRoomService.updateAskRoom(isHasRoom);
			}else{
				askRoomService.saveAskRoom(isHasRoom);
			
			}
					
		}
	}
}
