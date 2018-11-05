package com.taiji.pubsec.ajqlc.wrapper.jzpt.util;

import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean.QqbwBean;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean.QqbwSinoBean;


public class QqbwService {
	/**
	 * 初始化失败返回-1
	 */
	private static final String FAIL_INIT = "-1";
	//以SRmessage开头的请求报文
	private static  String	qqbwstatic = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<SRmessage>"
            + "<FWQQ_BWBH></FWQQ_BWBH>"	//报文编号
            + "<FWQQZ_ZCXX>"	//服务请求者注册信息
            + 		"<FWQQZMC>FWQQZMC_DATA</FWQQZMC>"	//服务请求者名称
            + 		"<FWQQZBS>FWQQZBS_DATA</FWQQZBS>"	//服务请求者标识
            + 		"<FWQQZRKDZLB>"	//服务请求者入口地址
            + 			"<FWQQZ_IPDZ>FWQQZ_IPDZ_DATA</FWQQZ_IPDZ>"	//ip地址
            + 			"<FWQQZ_ZXDKHM>FWQQZ_ZXDKHM_DATA</FWQQZ_ZXDKHM>"	//服务请求者端口号
            + 		"</FWQQZRKDZLB>"	//入口地址结束
            + 		"<FWQQZ_LXFS>"	//联系方式
            + 			"<LXR_XM>LXR_XM_DATA</LXR_XM>"	//联系人姓名
            + 			"<LXR_DH>LXR_DH_DATA</LXR_DH>"  //服务请求者_电话
            + 			"<LXR_GMSFHM>LXR_GMSFHM_DATA</LXR_GMSFHM>"	//服务请求者_身份证号
            + 		 "</FWQQZ_LXFS>  </FWQQZ_ZCXX>"
            + "<FWBS>FWBS_DATA</FWBS>"	//服务标识
            + "<FW_ZXBS>FW_ZXBS_DATA</FW_ZXBS>"	//服务_总线标识
            + "<FWQQ_RQSJ>FWQQ_RQSJ_DATA</FWQQ_RQSJ>"	//服务请求_日期时间
            + "<FWQQ_BZ>"		  //服务请求_内容
            + 		"<FWDYID>FWDYID_DATA</FWDYID>"	//公安部常住人口数据查询服务（无表码）
            + 			"<Items>"    
            +              "CXTJ_DATA"
            +				"<Item>"
            +              "RESULT_DATA"
            +				"</Item>"
            +			"</Items>"
            +	"</FWQQ_BZ>"
            + "<XXCZRY_XM>XXCZRY_XM_DATA</XXCZRY_XM>" //<!--信息操作人员_姓名-->
            + "<XXCZRY_GMSFHM>XXCZRY_GMSFHM_DATA</XXCZRY_GMSFHM>" //  <!--信息操作人员_编号-->
            + "<XXCZRY_GAJGJGDM>XXCZRY_GAJGJGDM_DATA</XXCZRY_GAJGJGDM>" //<!--信息操作人员_机构代码-->
            + "<XXCZRY_ZWJBDM>XXCZRY_ZWJBDM_DATA</XXCZRY_ZWJBDM>" //<!--信息操作人员_职务级别代码-->
            + "<XXCZRY_GWQJDM>XXCZRY_GWQJDM_DATA</XXCZRY_GWQJDM>" //<!--信息操作人员_岗位权级代码-->
            + "<XXCZRY_XZQHDM>XXCZRY_XZQHDM_DATA</XXCZRY_XZQHDM>" // <!--信息操作人员_行政区划代码-->
            + "<XXCZRY_MMDJDM>XXCZRY_MMDJDM_DATA</XXCZRY_MMDJDM>" //<!--信息操作人员_秘密等级代码-->
            + "<XXCZRY_JZDM>XXCZRY_JZDM_DATA</XXCZRY_JZDM>" //<!--信息操作人员_警种代码-->
            + "<YBQQ_BZDM>YBQQ_BZDM_DATA</YBQQ_BZDM>" //<!--异步请求_标识-->
            + "<JZQX>JZQX_DATA</JZQX>" //<!--截止期限-->
            + "</SRmessage>" ;
	//以SinoMessage 开头的请求报文
	private static  String	qqbwSinostatic = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
            + "<SinoMessage>"
            + "<ResourceID>RESOURCEID_DATA</ResourceID>"	//资源调用ID
            + "<ServiceID>SERVICEID_DATA</ServiceID>"//请求方ID
            + "<Method>"
            +"<Name>QUERY</Name>"//数据查询服务
            +"<Security>"
	        +"<Signature></Signature>"//请求方密码(可从平台请求方注册获取,可为空
	        +"<Encrypt>IP_DATA</Encrypt>"//请求方IP
            +"</Security>"
            + 		"<Items>"    
            +			"<Item>"
            +               "SQL_DATA"
            +               " <Data>FWYHXM_DATA</Data>"   //访问用户姓名（中文）
            +               " <Data>FWYHSFZH_DATA</Data>"   //访问用户公民身份证号
            +               " <Data>FWYHSZDWDM_DATA</Data>"   //访问用户所在单位12位代码
            +               "</Item>"      
            +				"<Item>"
            +               "RESULT_DATA"
            +				"</Item>"
            +			"</Items>"
            + "</Method>"
            + "</SinoMessage>" ;
	/**
	 * 初始化请求报文 用于以<SRmessage>开头报文的初始化
	 * @return
	 */
    public static String initQqbw(QqbwBean qqbwBean){
    	String qqbw = qqbwstatic;
    	if(null == qqbwBean.getFwqqzmc() || qqbwBean.getFwqqzmc().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("FWQQZMC_DATA", qqbwBean.getFwqqzmc());
    	}
    	
    	if(null == qqbwBean.getFwqqzbs() || qqbwBean.getFwqqzbs().isEmpty() ){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("FWQQZBS_DATA", qqbwBean.getFwqqzbs());
    	}
    	
    	if(null == qqbwBean.getFwqqzipdz() || qqbwBean.getFwqqzipdz().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("FWQQZ_IPDZ_DATA", qqbwBean.getFwqqzipdz());
    	}
    	
    	if(null == qqbwBean.getFwqqzdkh() || qqbwBean.getFwqqzdkh().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("FWQQZ_ZXDKHM_DATA", qqbwBean.getFwqqzdkh());
    	}
    	
    	if(null == qqbwBean.getLxrxm() || qqbwBean.getLxrxm().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("LXR_XM_DATA", qqbwBean.getLxrxm());
    	}
    	
    	if(null == qqbwBean.getLxrdh() || qqbwBean.getLxrdh().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("LXR_DH_DATA", qqbwBean.getLxrdh());
    	}
    	
    	if(null == qqbwBean.getLxrsfzh() || qqbwBean.getLxrsfzh().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("LXR_GMSFHM_DATA", qqbwBean.getLxrsfzh());
    	}
    	
    	if(null == qqbwBean.getFwbs() || qqbwBean.getFwbs().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("FWBS_DATA", qqbwBean.getFwbs());
    	}
    	
    	if(null == qqbwBean.getFwzxbs() || qqbwBean.getFwzxbs().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("FW_ZXBS_DATA", qqbwBean.getFwzxbs());
    	}
    	
    	if(null == qqbwBean.getFwqqrqsj() || qqbwBean.getFwqqrqsj().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("FWQQ_RQSJ_DATA", qqbwBean.getFwqqrqsj());
    	}
    	
    	if(null == qqbwBean.getFwdyid() || qqbwBean.getFwdyid().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("FWDYID_DATA", qqbwBean.getFwdyid());
    	}
    	if(null == qqbwBean.getCxtj() || qqbwBean.getCxtj().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("CXTJ_DATA", qqbwBean.getCxtj());
    	}
    	if(null == qqbwBean.getResultFields() || qqbwBean.getResultFields().isEmpty()){
    		qqbw = qqbw.replace("RESULT_DATA", "");
    	}else{
    		qqbw = qqbw.replace("RESULT_DATA", qqbwBean.getResultFields());
    	}
    	if(null == qqbwBean.getXxczryxm() || qqbwBean.getXxczryxm().isEmpty() ){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("XXCZRY_XM_DATA", qqbwBean.getXxczryxm());
    	}
    	
    	if(null == qqbwBean.getXxczrysfzh() || qqbwBean.getXxczrysfzh().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("XXCZRY_GMSFHM_DATA", qqbwBean.getXxczrysfzh());
    	}
    	if(null == qqbwBean.getXxczryjgdm() || qqbwBean.getXxczryjgdm().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbw = qqbw.replace("XXCZRY_GAJGJGDM_DATA", qqbwBean.getXxczryjgdm());
    	}
    	qqbw = qqbw.replace("XXCZRY_ZWJBDM_DATA", qqbwBean.getXxczryzwjbdm() == null? "":qqbwBean.getXxczryzwjbdm());
    	qqbw = qqbw.replace("XXCZRY_GWQJDM_DATA", qqbwBean.getXxczrygwqjdm() == null? "":qqbwBean.getXxczrygwqjdm());
    	qqbw = qqbw.replace("XXCZRY_XZQHDM_DATA", qqbwBean.getXxczryxzqhdm() == null? "":qqbwBean.getXxczryxzqhdm());
    	qqbw = qqbw.replace("XXCZRY_MMDJDM_DATA", qqbwBean.getXxczrymmdjdm() == null? "":qqbwBean.getXxczrymmdjdm());
    	qqbw = qqbw.replace("XXCZRY_JZDM_DATA",   qqbwBean.getXxczryjzdm() == null? "":qqbwBean.getXxczryjzdm());
    	qqbw = qqbw.replace("YBQQ_BZDM_DATA",     qqbwBean.getYbqqbs()== null? "":qqbwBean.getYbqqbs());
    	qqbw = qqbw.replace("JZQX_DATA",          qqbwBean.getJzqx()== null? "":qqbwBean.getJzqx());
    	return qqbw;
    };
    /**
     * 初始化请求报文 用于以<Sinomessage>开头报文的初始化
     * @param qqbwSinoBean
     * @return
     */
    public static String initQqbwSino(QqbwSinoBean qqbwSinoBean){
    	String qqbwSino = qqbwSinostatic;
    	if(null == qqbwSinoBean.getResourceID() || qqbwSinoBean.getResourceID().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbwSino = qqbwSino.replace("RESOURCEID_DATA",qqbwSinoBean.getResourceID());
    	}
    	if(null == qqbwSinoBean.getServiceID() || qqbwSinoBean.getServiceID().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbwSino = qqbwSino.replace("SERVICEID_DATA", qqbwSinoBean.getServiceID());
    	}
    	if(null == qqbwSinoBean.getIp() || qqbwSinoBean.getIp().isEmpty()){
    		return FAIL_INIT;
    	}
    	else{
    		qqbwSino = qqbwSino.replace("IP_DATA", qqbwSinoBean.getIp());
    	}
    	if(null == qqbwSinoBean.getFwyhxm() || qqbwSinoBean.getFwyhxm().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbwSino = qqbwSino.replace("FWYHXM_DATA", qqbwSinoBean.getFwyhxm());
    	}
    	if(null == qqbwSinoBean.getFwyhsfzh() || qqbwSinoBean.getFwyhsfzh().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbwSino = qqbwSino.replace("FWYHSFZH_DATA", qqbwSinoBean.getFwyhsfzh());
    	}
    	if(null == qqbwSinoBean.getFwyhdwdm() || qqbwSinoBean.getFwyhdwdm().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbwSino = qqbwSino.replace("FWYHSZDWDM_DATA", qqbwSinoBean.getFwyhdwdm());
    	}
    	if(null == qqbwSinoBean.getSql() || qqbwSinoBean.getSql().isEmpty()){
    		return FAIL_INIT;
    	}else{
    		qqbwSino = qqbwSino.replace("SQL_DATA", qqbwSinoBean.getSql());
    	}
    	if(null == qqbwSinoBean.getResult() || qqbwSinoBean.getResult().isEmpty()){
    		qqbwSino = qqbwSino.replace("RESULT_DATA", "");
    	}else{
    		qqbwSino = qqbwSino.replace("RESULT_DATA",qqbwSinoBean.getResult());
    	}
    	return qqbwSino;
    }
}
