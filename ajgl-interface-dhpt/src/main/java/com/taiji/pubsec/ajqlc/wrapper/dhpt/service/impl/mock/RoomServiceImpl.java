package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.exceptionLog.model.ExceptionLog;
import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.util.ServiceConstant;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.RoomBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.CxfClientServiceImpl;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.JsonToBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IRoomService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.IRoomServiceSuspend;

import net.sf.json.JSONObject;

@Service("roomServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class RoomServiceImpl implements IRoomServiceSuspend{

	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public List<RoomBean> findAll() throws DahuaException {
		List<RoomBean> roomBeanList = new ArrayList<RoomBean>();
		RoomBean roomBean = new RoomBean();
		roomBean.setRoomIp("52.4.1.211");
		roomBean.setRoomIsOnline("0");
		roomBean.setRoomName("讯问室1");
		roomBean.setRoomPort("3777");
		roomBean.setRoomStatus("0");
		roomBean.setRoomType("2");
		roomBean.setUuid("f82fa053b5e843baa8a6b6fab7f6a92d");
		
		RoomBean roomBean1 = new RoomBean();
		roomBean1.setRoomIp("172.6.5.26");
		roomBean1.setRoomIsOnline("0");
		roomBean1.setRoomName("讯问室2");
		roomBean1.setRoomPort("3777");
		roomBean1.setRoomStatus("0");
		roomBean1.setRoomType("2");
		roomBean1.setUuid("a786d26cc2c4464aa1ec58998c7659f3");
		
		roomBeanList.add(roomBean);
		roomBeanList.add(roomBean1);
		return roomBeanList;
		
	}
	
  
}
