package com.taiji.pubsec.ajqlc.util;
/**
 * 常量类
 * @author WL-PC
 *
 */
public class Constant {
	
	//储物柜使用状态-字典类型
	public static final String CWGZT = "cwgzt";
	//储物柜使用状态-空闲
	public static final String CWGZT_KX = "0000000027002";
	//储物柜使用状态-停用
	public static final String CWGZT_TY = "0000000027003";
	//储物柜使用状态-在用
	public static final String CWGZT_SYZ = "0000000027001";
	
	//储物柜使用状态-空闲(编码)
	public static final String CWGZT_CODE_KX = "002";
	//储物柜使用状态-在用(编码)
	public static final String CWGZT_CODE_SYZ = "001";

	//使用单刻录状态-否
	public static final String KLZT_F = "0000000007001";
	//使用单刻录状态-是
	public static final String KLZT_S = "0000000007002";
	//询问室使用状态-字典类型
	public static final String WXSZT = "wxszt";
	//询问室使用状态-空闲
	public static final String SYZT_KX = "0000000001001";
	//询问室使用状态-不可用
	public static final String SYZT_BKY = "0000000001002";
	//询问室使用状态-使用中
	public static final String SYZT_SYZ = "0000000001003";
	
	//房间类型-字典类型
	public static final String FJLX = "fjlx";
	//房间类型-问询室
	public static final String FJLX_WXS = "0000000011001";
	//房间类型-问询室
	public static final String FJLX_QT = "0000000011002";
	
	//使用单状态-字典类型
	public static final String SYDZT = "sydzt";
	//使用单状态-处理中
	public static final String SYDZT_QT = "0000000006001";
	//使用单状态-已完成
	public static final String SYDZT_YWC = "0000000006002";
	
	//是否-字典类型
	public static final String SF = "sf";
	//是否-字典项-是
	public static final String SF_S = "0000000007002";
	//是否-字典项-否
	public static final String SF_F = "0000000007001";
	
	
	//进入办案区原由-字典类型
	public static final String BAQYY = "baqyy";
	//进入办案区原由-其他
	public static final String BAQYY_QT = "0000000004011";
	
	//案由-字典类型
	public static final String AY = "ay";
	//案由-其他
	public static final String AY_QT = "0000000026003";
	
	//性别-字典类型
	public static final String XB = "xb";
	//性别-男
	public static final String XB_NAN = "0000000003001";
	//性别-女
	public static final String XB_NU = "0000000003002";
	//性别-未知
	public static final String XB_WEIZHI = "0000000003003";
	
	//证件类型-字典类型
	public static final String ZJLX = "zjlx";
	//证件类型-身份证
	public static final String ZJLX_SFZ = "0000000005001";
	//证件类型-军官证
	public static final String ZJLX_JGZ = "0000000005002";
	//证件类型-护照
	public static final String ZJLX_HZ = "0000000005003";
	
	//保管措施--字典类型
	public static final String BGCS = "bgcs";
	//物品状态--字典类型
	public static final String WPZT = "wpzt";
	
	//状态——字典类型
	public static final String ZT = "zt";
	//状态——启用
	public static final String ZT_QY = "0000000002002";
	//状态——停用
	public static final String ZT_TY = "0000000002001";
	
	//物品状态在库
	public static final String WPZT_ZK = "0000000010001";
	//物品状态临时取出
	public static final String WPZT_LSQC = "0000000010002";
	//物品状态本次返还&立即返还(快照)
	public static final String WPZT_YFH = "0000000010003";
	//物品状态移交暂存
	public static final String WPZT_YJZC = "0000000010004";
	//物品状态移交扣押
	public static final String WPZT_YJKY = "0000000010005";
	
	//活动类型-字典类型
	public static final String HDLX = "hdlx";
	//活动类型-讯问
	public static final String HDLX_X4W =  "0000000009001";
	//活动类型-询问
	public static final String HDLX_X2W =  "0000000009002";
	
	//其他活动类型-字典类型
	public static final String QTHDLX = "qthdlx";
	
	//预警规则类型-字典类型
	public static final String YJGZ = "yjgz";
	
	//预警方式-系统消息
	public static final String YJFS_XTXX = "0000000014001";
	//预警方式-PDA
	public static final String YJFS_PDA = "0000000014002";
	//预警方式-短息
	public static final String YJFS_DX = "0000000014003";
	
	//触发方式-动作触发
	public static final String CFFS_DZ = "0000000015001";
	//触发方式-系统自动
	public static final String CFFS_XT = "0000000015002";
	//退侦后补充侦查类型的id
	public static final String YJGZ_BCZC = "0000000013009"; 
	
	/**
	 * 所属模块常量
	 */
	public static final String BAQMK = "办案区模块";
	public static final String AJJKMK = "案件监控模块";
	public static final String SAWPMK = "涉案物品模块";
	
	//预警规则-传唤
	public static final String YJGZ_CH = "0000000013001";
	//预警规则-刑拘
	public static final String YJGZ_XJ = "0000000013002";
	//预警规则-传唤
	public static final String YJGZ_YCXJ = "0000000013003";
	//预警规则-传唤
	public static final String YJGZ_QB = "0000000013004";
	//预警规则-传唤
	public static final String YJGZ_JSJZ = "0000000013005";
	//预警规则-传唤
	public static final String YJGZ_YS = "0000000013006";
	//预警规则-办案区
	public static final String YJGZ_BA = "0000000013007";
	//预警规则-扣押物品
	public static final String YJGZ_KYWP = "0000000013008";
	//预警规则-暂存物品超时
	public static final String YJGZ_ZCWPCS = "0000000013010";
	//违规类型-其他情况
	public static final String WGLX_QT = "99";
	
	//涉案物品性质-字典类型
	public static final String SAWPXZ = "sawpxz";
	//涉案物品性质-作案工具
	public static final String SAWPXZ_ZAGJ = "0000000016001";
	
	//出库类型-字典类型
	public static final String CKLX = "cklx";
	//出库类型-字典项-随案移交
	public static final String CKLX_SAYJ = "0000000025001";
	//出库类型-字典项-责令发还
	public static final String CKLX_ZLFH = "0000000025002";
	//出库类型-字典项-借出
	public static final String CKLX_JC = "0000000025005";
	//出库类型-字典项-其它
	public static final String CKLX_QT = "0000000025006";
	
	// 单位树节点是否为为父节点，是
	public static final String UNIT_ISPAR_TRUE = "True";
	// 单位树节点是否为为父节点，否
	public static final String UNIT_ISPAR_FALSE = "False";
	// 人員在职状态
	public static final String WORK_STATUS = "zzzt";
	// 人员学历
	public static final String DIPLOMA = "xl";
	// 人员民族
	public static final String NATIONALITY = "mz";
	// 人员政治面貌
	public static final String POLITICSTYPE = "zzmm";
	// 人员初始化职务
	public static final String JOB = "zw";
	
	//活动大阶段描述
	public static final String ACTIVITY_STAGE_DESCRIPTION_JRBAQ = "进入办案区";//进入办案区
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_RSAQJC = "人身安全检查";//人身安全检查
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_XXCJ = "信息采集";//信息采集
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_SSWPBG = "随身物品保管";//随身物品保管
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_XXWGC = "讯询问过程";//讯询问过程
	
	public static final String ACTIVITY_STAGE_DESCRIPTION_LKDJ = "离开登记";//离开登记
	
	//活动记录小阶段
	public static final String ACTIVITY_STAGE_BIND_BANGLE = "发放并佩戴手环";//发放并佩戴手环(属于进入办案区大阶段)
	
	public static final String ACTIVITY_STAGE_HANDUP_BANGLE = "手环挂起";//挂起手环(属于离开登记大阶段)
	
	public static final String ACTIVITY_STAGE_CANCEL_BANGLE = "取消手环挂起";//取消手环挂起(属于进入办案区大阶段)
	
	public static final String ACTIVITY_STAGE_UNBIND_BANGLE = "解绑手环";//取消手环挂起(属于离开登记大阶段)
	
	public static final String ACTIVITY_STAGE_ALLOT_FPXXWS = "分配讯询问室";//分配讯询问室(属于讯询问过程大阶段)
	
	public static final String ACTIVITY_STAGE_TEMP_LSLK = "临时离开";//临时离开(属于离开登记大阶段)
	
	public static final String ACTIVITY_STAGE_LAST_ZZLK = "最终离开";//最终离开(属于离开登记大阶段)
	
	//预警消息类型
	public static final String YJ_WXS = "讯（询）问室";//
	
	public static final String HDJL_LXBM = "hdjlwglx";//活动记录网格类型的字典类型编码
	
	public static final String HDJL_YJLX = "hdjlyjlx";//活动记录预警类型的字典类型编码
    /**
     * excel导出临时文件夹
     */
    public static final String EXCELPATH = "excels/";
    
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	
	private Constant(){
		
	}
}
