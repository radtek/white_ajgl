package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;



public interface IBasicDataServiceSuspend {
	/**
	 * 同步新增组织机构信息
	 * @param id 组织id
	 * @param name 名称
	 * @param parentId 上级id
	 * @return 【success : true 同步成功;
	 *                       success : false 同步失败，msg:失败原因;】
	 */
 public ResultBean syncAddOrgnizationInfo(String id,String name, String parentId) throws DahuaException;
 /**
	 * 同步更新组织机构信息
	 * @param id 组织id
	 * @param name 名称
	 * @param parentId 上级id
	 * @return 【success : true 同步成功;
	 *                       success : false 同步失败，msg:失败原因;】
	 */
 public ResultBean syncUpdateOrgnizationInfo(String id,String name, String parentId) throws DahuaException;
 /**
  * 同步新增人员账户信息
  * @param loginName 登陆名称
  * @param loginPass 登陆密码
  * @param orgId 单位Id
  * @param personName 用户姓名
  * @param id  警号
  * @return 【success : true 同步成功;
  *           success : false 同步失败，msg:失败原因;】
  */
 public ResultBean syncAddPersonInfo(String loginName ,String loginPass, String orgId,String personName,String id) throws DahuaException;
 /**
  * 同步更新人员账户信息
  * @param loginName 登陆名称
  * @param loginPass 登陆密码
  * @param orgId 单位Id
  * @param personName 用户姓名
  * @param id  警号
  * @return 【success : true 同步成功;
  *           success : false 同步失败，msg:失败原因;】
  */
 public ResultBean syncUpdatePersonInfo(String loginName ,String loginPass, String orgId,String personName,String id) throws DahuaException;
 
}
