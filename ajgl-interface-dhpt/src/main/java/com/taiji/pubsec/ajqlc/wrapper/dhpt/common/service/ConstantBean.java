package com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service;
/**
 * 用于定义常量
 * @author xinfan
 *
 */
public class ConstantBean {
	//手环或定位卡激活时 的人员类型 {1为嫌疑人  5 为民警}
	public static final String PERSON_POLICE = "5";
	public static final String PERSON_SUSPECT = "1";
	//手环或者定位卡绑定与解绑 { 1 绑定 2 解绑}
	public static final String  OPRTYPE_ACTIVE = "1";
	public static final String  OPRTYPE_RECLAIM = "2";
	//挂起和解挂
	public static final Integer OPRTYPE_SUSPEND = 1;
	public static final Integer OPRTYPE_RESUME = 2;
    //同步数据类型，{1：警员、2：嫌疑人、3：案件、4：组织机构}；
	
	public static final Integer DATATYPE_POLICE = 1;

	public static final Integer DATATYPE_SUSPECT = 2;
	
	public static final Integer DATATYPE_CASE = 3;
	
	public static final Integer DATATYPE_ORG = 4;
   
	//同步数据时的操作类型  oprType 同步操作类型，{1：add、2：edit、3：del}
	
	public static final Integer OPRTYPE_ADD = 1;
	
	public static final Integer OPRTYPE_EDIT = 2;
	
	public static final Integer OPRTYPE_DEL = 3;
	
	//报警类型 {1：手环报警信息,2:智能分析服务器报警，3：审讯室手报}；
	//每个审讯室中都有摄像头，利用摄像头的报警输入，来联动触发报警。
	
	public static final Integer YJLX_SHBJ = 1; 
	
	public static final Integer YJLX_ZNFX = 2; 
	
	public static final Integer YJLX_SXSSB = 3; 
	
	//网格类型，{4：走廊，7：办案中心门厅，8：人身安全检查室，9：信息采集室，10：随身物品保管区，11：待问室
	//12：值班监控区，1：讯（询）问室 ，13：心理疏导室， 14：辨认室， 15：值班登记室，16:卫生间
	
	public static final String GRIDTYPE_ZL = "4";//走廊
	
	public static final String GRIDTYPE_BAZXMT = "7";//办案中心门厅
	
	public static final String GRIDTYPE_RSANJCS = "8";//人身安全检查室
	
	public static final String GRIDTYPE_XXCJS = "9";//信息采集室
	
	public static final String GRIDTYPE_SSWPBGQ = "10";//随身物品保管区
	
	public static final String GRIDTYPE_DWS = "11";//待问室
	
	public static final String GRIDTYPE_ZBJKQ = "12";//值班监控区
	
	public static final String GRIDTYPE_XWS = "1";// 讯（询）问室
	
	public static final String GRIDTYPE_XLSDS = "13";//心理疏导室
	
	public static final String GRIDTYPE_BRS = "14";//辨认室
	
	public static final String GRIDTYPE_ZBDJS = "15";//值班登记室
	
	public static final String GRIDTYPE_WSJ = "16";//卫生间
	
	//活动大阶段描述
	public static final String ACTIVITY_STAGE_DESCRIPTION_JRBAQ = "进入办案区";//进入办案区
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_RSAQJC = "人身安全检查";//人身安全检查
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_XXCJ = "信息采集";//信息采集
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_SSWPBG = "随身物品保管";//随身物品保管
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_XXWGC = "讯询问过程";//讯询问过程
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_LKDJ = "离开登记";//离开登记
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_DWS = "待审室";//待审室
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_ZBJKQ = "值班监控区";//值班监控区
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_XWS = "讯（询）问室";//讯（询）问室
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_XLSDS = "心理疏导室";//心理疏导室
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_BRS = "辨认室";//辨认室
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_WSJ = "卫生间";//卫生间
	
	//大华房间类型{1 为询问室 2 为讯问室}  咱们这边不分此类型
	public static final String ROOMTYPE_ASKROOM1 = "1"; 
	public static final String ROOMTYPE_ASKROOM2 = "2";
	
	//审讯室在线状态{1在线  非1 为离线}
	
	public static final String ROOM_ONLINE = "1";
	public static final String ROOM_OFFLINE = "2";
	
	//审讯室使用情况{0：空闲  1:使用}
	public static final String ROOM_FREE = "0";
	public static final String ROOM_USING = "1";
	
	public static final String WSDL = "http://52.4.1.210/itc/ws/ExternalWebService?wsdl";//发布地址
	public static final String TARGET_NAME_SPACE = "http://external.webservice.dip.dahua.com";//命名空间
	
}
