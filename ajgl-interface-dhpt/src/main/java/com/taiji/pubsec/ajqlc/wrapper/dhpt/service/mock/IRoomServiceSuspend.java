package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock;
import java.util.List;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.RoomBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
/**
 * 房间信息接口
 * @author xinfan
 *
 */
public interface IRoomServiceSuspend {
  public List<RoomBean> findAll() throws DahuaException;
}
