package com.taiji.pubsec.ajqlc.wrapper.jzpt.service;

import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean.QqbwSinoBean;





/**
 * 增量查询接口
 * @author xinfan
 *
 */
public interface JZPTQueryIncrementInterface {
	  /**
     * 查询某一时间段内的所有数据
     * @param fromDate  查询的起始时间
     * @param toDate   查询的结束时间
     * @param currentPage  当前页
     * @param pageSize  每页大小
     * @return 返回xml字符串
     */
	public String searchIncrementData(String fromDate,String toDate,String currentPage,String pageSize,QqbwSinoBean qqbwSinoBean);
}
