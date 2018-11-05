package com.taiji.pubsec.ajgl.dbtx.task.util;

public class Constant {

	public static final String TASK_TYPE_ENTERCASEAREA = "baqeight";//办案区八小时预警
	
	public static final String TASK_TYPE_SUBPOENA = "chdqtx";//传唤到期预警
	
	public static final String TASK_TYPE_ARREST = "dbdqtx";//逮捕后移送起诉预警
	
	public static final String TASK_TYPE_SUSOBJ = "sawptx";//涉案物品到期未入库预警
	
	public static final String TASK_TYPE_DETAINED = "xjdqtx";//刑拘到期提醒
	
	public static final String TASK_TYPE_EXTENDEDDETENTION = "ycjldqtx";//延长拘留到期提醒
	
	public static final String TASK_TYPE_BAIL = "qbhsdqtx";//取保到期提醒
	
	public static final String TASK_TYPE_RESIDENTIALSURVEILLANCE = "jsjzdqtx";//监视居住到期提醒
	
	public static final String TASK_TYPE_REPLENISHDETECT = "bczcdqtx"; //退侦后补充侦查到期提醒
	
	public static final String TASK_TYPE_TEMPORARYSTORAGE = "zcwpcswfhdqtx"; //暂存物品超时未返还到期提醒
	
	public static final String RULE_STATUS_ON = "0000000002001"; //预警规则状态：停用
	
	public static final String RULE_TYPE_ID_CH = "0000000013001"; //预警规则id：传唤
	
	public static final String RULE_TYPE_ID_XJ = "0000000013002"; //预警规则id：刑拘
	
	public static final String RULE_TYPE_ID_YCXJ = "0000000013003"; //预警规则id：延长刑拘
	
	public static final String RULE_TYPE_ID_QB = "0000000013004"; //预警规则id：取保
	
	public static final String RULE_TYPE_ID_JSJZ = "0000000013005"; //预警规则id：监视居住
	
	public static final String RULE_TYPE_ID_YSQS = "0000000013006"; //预警规则id：移送起诉
	
	public static final String RULE_TYPE_ID_JRBAQ = "0000000013007"; //预警规则id：进入办案区
	
	public static final String RULE_TYPE_ID_KYWPDQ = "0000000013008"; //预警规则id：扣押物品到期
	
	public static final String RULE_TYPE_ID_TZ = "0000000013009"; //预警规则id：退侦
	
	public static final String RULE_TYPE_ID_ZCWPCSWFH = "0000000013010"; //预警规则id：暂存物品超时未返还
}
