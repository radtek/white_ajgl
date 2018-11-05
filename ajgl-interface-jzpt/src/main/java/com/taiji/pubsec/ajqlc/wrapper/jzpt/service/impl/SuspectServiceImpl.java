package com.taiji.pubsec.ajqlc.wrapper.jzpt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.SuspectService;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean.BasicPersonBean;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean.CriminalRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.CommonService;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.DataSyncServiceImpl;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Dom4JParaseXmlData;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.JZPTQueryIncrementData;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean.QqbwBean;
import com.taiji.persistence.dao.Pager;
/**
 * 嫌疑人相关信息接口实现，封装警综平台webservice接口。
 * @author dixiaofeng
 *
 */
@Service("suspectService")
@Transactional(rollbackFor = Exception.class)
public class SuspectServiceImpl extends JZPTQueryIncrementData implements SuspectService{
	@Resource 
	private CommonService  commonService;
	private QqbwBean qqbwBean;
	/**
	 * 根据身份证号查询人员基本信息。
	 * @param idcard 身份证号
	 * @return 人员基本信息
	 */
	public BasicPersonBean findBasicInfo(String idcard) {
		String searchConditicon = "<Item>"
				+ "<Data>SFZH ='"+idcard+"'</Data>"
				+ "<Data>1</Data>" //当前页
				+ "<Data>10</Data>" //每页大小
				+ "<Data>0</Data>"//返回字段名称形式(中文1/英文0)
		        + "</Item>";
        String resultFields = "<Row>"
            		+ "<Data>HKSZD</Data>" //户口所在地(可作为查询字段)
		            + "<Data>CSDXZ</Data>" //出生地详址
		            + "<Data>SFZH</Data>"  //身份证号码(可作为查询字段)
		            + "<Data>CSD</Data>"   //出生地
		            + "<Data>XP</Data>"    //相片
	                + "<Data>XM</Data>"    //姓名(可作为查询字段)
	                + "<Data>MZ</Data>"    //民族
                    + "<Data>CSRQ</Data>"  //出生日期(可作为查询字段)
		            + "<Data>XB</Data>"    //性别(可作为查询字段)
		            + "<Data>CSSSX</Data>" //籍贯省市县(可作为查询字段)
		            + "<Data>CYM</Data>"   //曾用名
		            + "<Data>BYQK</Data>"  //兵役情况
		            + "<Data>FWCS</Data>"  //服务处所
		            + "<Data>WHCD</Data>"  // 文化程度
		            + "<Data>SG</Data>"    //身高
		            + "<Data>HYZK</Data>"  // 婚姻状况(可作为查询字段)
		            + "<Data>ZZXZ</Data>"  // 住址详址</Row>";
                    + "</Row>";
    		String qqbw = CommonService.createQqbw(qqbwBean, this.getResourceid(), searchConditicon, resultFields);
            String result = "";
            Pager<HashMap> resultMapPager = null;
            BasicPersonBean basicPersonBean = null;
    		try {
    			result = DataSyncServiceImpl.getResource(qqbw,this.getWsUrl(),this.getTargetNameSpace(),this.getMethod());
    			resultMapPager = Dom4JParaseXmlData.getAllFieldMaps(result);
    			basicPersonBean = mapToBasicPersonBean(resultMapPager.getPageList().get(0));
    		    } catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		return basicPersonBean; 
	}
	
	/**
	 * 根据身份证号查询人员的前科记录。
	 * @param idcard 身份证号
	 * @return 前科基本信息列表
	 */
	public List<CriminalRecordBean> findCriminalRecord(String idcard) {
		String searchConditicon = "<Item>"
				+ "<Data>ZJHM ='"+idcard+"'</Data>"
				+ "<Data>1</Data>" //当前页
				+ "<Data>10</Data>" //每页大小
				+ "<Data>0</Data>"//返回字段名称形式(中文1/英文0)
		+ "</Item>";
            String resultFields = "<Row>"
            		+ "<Data>CASEID</Data>"
            		+ "<Data>CASENAME</Data>"
            		+ "<Data>INPUTTIME</Data>"
                    + "</Row>";
    		String qqbw = CommonService.createQqbw(qqbwBean, this.getResourceid(), searchConditicon, resultFields);
            String result = "";
            Pager<HashMap> resultMapPager = null;
            List<CriminalRecordBean> criminalRecordBeanLists = new ArrayList<CriminalRecordBean>();
    		try {
    			result = DataSyncServiceImpl.getResource(qqbw,this.getWsUrl(),this.getTargetNameSpace(),this.getMethod());
    			resultMapPager = Dom4JParaseXmlData.getAllFieldMaps(result);
    			for(int i = 0 ; i < resultMapPager.getPageList().size() ; i++){
    				criminalRecordBeanLists.add(mapToCriminalRecordBean(resultMapPager.getPageList().get(i)));
    			}
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		
		return criminalRecordBeanLists;
	}
	/**
	 * 将map字段封装到BasicPersonBean
	 * @param personMap
	 * @return
	 */
	private BasicPersonBean mapToBasicPersonBean(HashMap personMap){
		BasicPersonBean basicPersonBean = new BasicPersonBean();
		if (null == personMap || personMap.isEmpty()){
			return null;
		}
		basicPersonBean.setName(personMap.get("XM").toString());
		basicPersonBean.setDoorAddress(personMap.get("HKSZD")==null?null:personMap.get("HKSZD").toString());
		basicPersonBean.setLiveAddress(personMap.get("ZZXZ") == null?null:personMap.get("ZZXZ").toString());
		basicPersonBean.setPhone(personMap.get("DH") == null ?null :personMap.get("DH").toString());
		basicPersonBean.setPicture(personMap.get("XP") == null ?null :personMap.get("XP").toString());
		basicPersonBean.setSex(personMap.get("XB") == null ?null :personMap.get("XB").toString());
		return basicPersonBean;
	}
	/**
	 * 将Map转为前科记录bean CriminalRecordBean
	 * @param criminalRecord
	 * @return
	 */
	private CriminalRecordBean mapToCriminalRecordBean(HashMap criminalRecord){
		CriminalRecordBean criminalRecordBean =  new CriminalRecordBean();
		if (null == criminalRecord || criminalRecord.isEmpty()){
			return null;
		}
		criminalRecordBean.setCaseCode(criminalRecord.get("CASEID") == null ? null : criminalRecord.get("CASEID").toString());
		criminalRecordBean.setCaseName(criminalRecord.get("CASENAME") == null ? null : criminalRecord.get("CASENAME").toString());
		criminalRecordBean.setCaseTime(criminalRecord.get("INPUTTIME") == null ? null : criminalRecord.get("INPUTTIME").toString());
		criminalRecordBean.setPicture("");//??无照片字段
		criminalRecordBean.setPolice("");//？？无法确定取哪个字段
		return criminalRecordBean;
	}

	public QqbwBean getQqbwBean() {
		return qqbwBean;
	}

	public void setQqbwBean(QqbwBean qqbwBean) {
		this.qqbwBean = qqbwBean;
	}
	
}
