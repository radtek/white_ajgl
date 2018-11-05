package com.taiji.pubsec.ajqlc.wrapper.jzpt.util;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean.QqbwBean;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean.QqbwSinoBean;


/**
 * 主要用于初始化一些基本信息
 * @author Administrator
 *
 */
@Service("commonService")
@Transactional(rollbackFor = Exception.class)
public class CommonService {
  private static  QqbwBean qqbwBean ;
  private static  QqbwSinoBean qqbwSinoBean;
  /**
   * 获取<SRMessage>开头的请求报文Bean 
   * @return
   */
  public  QqbwBean getQqbwBean(){
	  return qqbwBean;
  }
  /**
   *  获取<SinoMessage>开头的请求报文Bean 
   */
  public  QqbwSinoBean getQqbwSinoBean(){
	  return qqbwSinoBean;
  }
  /**
   * 生成请求报文
   * @param qqbwBean 请求报文Bean 
   * @param fwdyid   服务调用id 主要在服务大厅的给出的请求报文可以看到
   * @param searchConditicon 查询条件  <Item><Data>CASESTATE='案件办结归档</Data> </Item>。<Data></Data> 为添加的查询条件，至少有一个
   * @param resultFields   返回结果的字段定义如果位空 则返回 表中全部字段   格式如：<Row><Data>YXTBH</Data></Row>
   * @return String qqbw 生成的请求报文
   */
  public static String createQqbw(QqbwBean qqbwBean,String fwdyid,String searchConditicon,String resultFields){
	  if(qqbwBean == null){
		  qqbwBean = CommonService.qqbwBean;
	  }
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	  qqbwBean.setFwqqrqsj(sdf.format(new Date()));
	  qqbwBean.setFwdyid(fwdyid);
	  qqbwBean.setCxtj(searchConditicon);
	  qqbwBean.setResultFields(resultFields);
	  String qqbw = QqbwService.initQqbw(qqbwBean);
	  return qqbw;
  }
  /**
   * 
   * @param qqbwSinoBean 请求报文Bean 
   * @param resourceId 服务调用id 可以在服务大厅的给出的请求报文可以看到
   * @param searchConditicon 查询条件 <Data>CASESTATE='案件办结归档</Data><Data>currentpage</Data><Data>pagesize</Data> 。currentpage 当前页 pagesize 每页大小
   * @param resultFields 返回结果的字段定义如果位空 则返回 表中全部字段   格式如：<Row><Data>YXTBH</Data></Row>
   * @return String qqbwSino 生成的请求报文
   */
  public static String createQqbwSino(QqbwSinoBean qqbwSinoBean,String resourceId,String searchConditicon,String resultFields){
	  if(qqbwSinoBean == null){
		  qqbwSinoBean = CommonService.qqbwSinoBean;
	  }
	  qqbwSinoBean.setResourceID(resourceId);
	  qqbwSinoBean.setSql(searchConditicon);
	  qqbwSinoBean.setResult(resultFields);
	  String qqbwSino = QqbwService.initQqbwSino(qqbwSinoBean);
	  return qqbwSino;
  }
public static void setQqbwBean(QqbwBean qqbwBean) {
	CommonService.qqbwBean = qqbwBean;
}
public static void setQqbwSinoBean(QqbwSinoBean qqbwSinoBean) {
	CommonService.qqbwSinoBean = qqbwSinoBean;
}
 
}
