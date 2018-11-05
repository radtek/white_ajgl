package com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;

/**
 * 电子储物柜接口
 * @author dixiaofeng
 *
 */
public interface LockerService {
	
	/**
	 * 打开储物柜。
	 * @param areano 区号
	 * @param tankno 柜号
	 * @param boxno 箱号
	 * @return 打开成功返回true，否则返回false及原因
	 */
	ResultBean open(int areano, int tankno, int boxno);

}
