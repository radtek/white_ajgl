package com.taiji.pubsec.ajqlc.baq.service;

import com.taiji.pubsec.ajqlc.baq.model.BasicCase;

/**
 * 进入办案区人员基本情况服务接口
 * @author wangfx
 *
 */
public interface IBasicCaseService {
	
	/**
	 * 新增办案区使用单基本情况
	 * @param basicCase 基本情况
	 * 		      需要包含办案区使用单信息 handlingAreaReceipt
	 * @return 办案区使用单id
	 */
	public String createBasicCase(BasicCase basicCase);
	
	/**
	 * 修改办案区使用单基本情况
	 * @param basicCase 基本情况
	 * 		  需要包含办案区使用单信息 handlingAreaReceipt,
	 * 		handlingAreaReceipt需要赋值最新修改人信息，最新修改时间
	 */
	public void updateBasicCase(BasicCase basicCase);
	
	/**
	 * 通过id查询基本情况
	 * @param basicCaseId 基本情况id
	 */
	public BasicCase findById(String basicCaseId);
	
	
	
}
