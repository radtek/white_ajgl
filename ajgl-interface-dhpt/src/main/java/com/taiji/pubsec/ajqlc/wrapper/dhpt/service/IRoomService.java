package com.taiji.pubsec.ajqlc.wrapper.dhpt.service;
import java.util.List;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.RoomBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
/**
 * 房间信息接口
 * @author xinfan
 *
 */
public interface IRoomService {
  public List<RoomBean> findAll() throws DahuaException;
}
