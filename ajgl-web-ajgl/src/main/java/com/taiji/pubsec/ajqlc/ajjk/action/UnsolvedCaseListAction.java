package com.taiji.pubsec.ajqlc.ajjk.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.UnsolvedCaseBean;
import com.taiji.pubsec.ajqlc.sla.bean.UnsolvedCaseServiceBean;
import com.taiji.pubsec.ajqlc.sla.service.IUnsolvedCaseQueryService;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

/**
 * 未破案件月报表查询Action
 * 
 * @author huangda
 *
 */
@Controller("unsolvedCaseListAction")
@Scope("prototype")
public class UnsolvedCaseListAction extends ReturnMessageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private IUnsolvedCaseQueryService unsolvedCaseQueryService;
	
	private Long caseTimeStart;
	
	private Long caseTimeEnd;
	/**
	 * 案件编号或名称
	 */
	private String caseCodeOrName;
	/**
	 * 办案单位
	 */
	private String sponsor;
	/**
	 * 案件类别
	 */
	private String caseSort;
	
	/**
	 * 刑事立案数量
	 */
	private Long xingshilian;
	
	/**
	 * 刑事破案数量
	 */
	private Long xingshipoan;
	
	/**
	 * 行政立案数量
	 */
	private Long xingzhenglian;
	
	/**
	 * 行政破案数量
	 */
	private Long xingzhengpoan;
	
	/**
	 * 查询月份
	 */
	private String queryTime;
	/**
	 * 民警名称或警号
	 */
	private String disposePerson;
	/**
	 * 民警名称或警号
	 */
	private String ifSolved;
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	private List<UnsolvedCaseBean> unsolvedCaseBeanLst = new ArrayList<UnsolvedCaseBean>();
	
	
	public String searchPenalCaseByPage(){
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("caseClass", "刑事案件");
		if(caseTimeStart != null){
			map.put("caseTimeStart", new Date(caseTimeStart));
		}
		if(caseTimeStart != null){
			map.put("caseTimeEnd", new Date(caseTimeEnd));
		}
		if(queryTime != null){
//			map.put("queryTime", selectToDate(queryTime) );
			map.put("queryTimeStart", selectToDateStart(queryTime));
			map.put("queryTimeEnd", selectToDateEnd(queryTime));
		}
		map.put("caseCodeOrName", caseCodeOrName);
		map.put("attendUnit", sponsor);
		map.put("caseSort", caseSort);
		
		map.put("disposePerson", disposePerson);
		
		map.put("ifSolved", ifSolved);
		
		Map<Pager<UnsolvedCaseServiceBean>, Long> map1 = unsolvedCaseQueryService.findUnsolvedCaseByConditions(map, this.getStart() / this.getLength(), this.getLength());
		
		Pager<UnsolvedCaseServiceBean> casePager = (Pager<UnsolvedCaseServiceBean>) map1.entrySet().iterator().next().getKey();
		xingshipoan = map1.get(casePager);
		xingshilian = (long) casePager.getTotalNum();
		
		//unsolvedCaseBeanLst = casePager.getPageList();
		this.beanTransition(casePager.getPageList());
		
		this.setTotalNum(casePager.getTotalNum());
		return SUCCESS;
	}
	
	public String searchAdministrativeCaseByPage(){
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("caseClass", "行政案件");
		if(caseTimeStart != null){
			map.put("caseTimeStart", new Date(caseTimeStart));
		}
		if(caseTimeStart != null){
			map.put("caseTimeEnd", new Date(caseTimeEnd));
		}
		if(queryTime != null){
//			map.put("queryTime", selectToDate(queryTime) );
			map.put("queryTimeStart", selectToDateStart(queryTime));
			map.put("queryTimeEnd", selectToDateEnd(queryTime));
		}
		map.put("caseCodeOrName", caseCodeOrName);
		map.put("attendUnit", sponsor);
		map.put("caseSort", caseSort);
		
		map.put("disposePerson", disposePerson);
		
		map.put("ifSolved", ifSolved);

		Map<Pager<UnsolvedCaseServiceBean>, Long> map1 = unsolvedCaseQueryService.findUnsolvedCaseByConditions(map, this.getStart() / this.getLength(), this.getLength());
		
		Pager<UnsolvedCaseServiceBean> casePager = map1.entrySet().iterator().next().getKey();
		xingzhengpoan = map1.get(casePager);
		xingzhenglian= (long) casePager.getTotalNum();
		
		//unsolvedCaseBeanLst = casePager.getPageList();
		this.beanTransition(casePager.getPageList());
		
		this.setTotalNum(casePager.getTotalNum());
		return SUCCESS;
	}
	
	public String exportExcelPenal() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("caseClass", "刑事案件");
		if(caseTimeStart != null){
			map.put("caseTimeStart", new Date(caseTimeStart));
		}
		if(caseTimeStart != null){
			map.put("caseTimeEnd", new Date(caseTimeEnd));
		}
		if(queryTime != null){
//			map.put("queryTime", selectToDate(queryTime) );
			map.put("queryTimeStart", selectToDateStart(queryTime));
			map.put("queryTimeEnd", selectToDateEnd(queryTime));
		}
		map.put("caseCodeOrName", caseCodeOrName);
		map.put("attendUnit", sponsor);
		map.put("caseSort", caseSort);
		
        map.put("disposePerson", disposePerson);
		
		map.put("ifSolved", ifSolved);
		
		Map<Pager<UnsolvedCaseServiceBean>, Long> map1 = unsolvedCaseQueryService.findUnsolvedCaseByConditions(map, 0, Integer.MAX_VALUE);
		
		Pager<UnsolvedCaseServiceBean> casePager = map1.entrySet().iterator().next().getKey();
		xingshilian =(long) casePager.getTotalNum();
		xingshipoan = xingshilian - map1.get(casePager);
	
		//unsolvedCaseBeanLst = casePager.getPageList();
		this.beanTransition(casePager.getPageList());
		
		return exportExcel("是否破案");
	}
	
	public String exportExcelAdministrative() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("caseClass", "行政案件");
		if(caseTimeStart != null){
			map.put("caseTimeStart", new Date(caseTimeStart));
		}
		if(caseTimeStart != null){
			map.put("caseTimeEnd", new Date(caseTimeEnd));
		}
		if(queryTime != null){
//			map.put("queryTime", selectToDate(queryTime) );
			map.put("queryTimeStart", selectToDateStart(queryTime));
			map.put("queryTimeEnd", selectToDateEnd(queryTime));
		}
		map.put("caseCodeOrName", caseCodeOrName);
		map.put("attendUnit", sponsor);
		map.put("caseSort", caseSort);
		
        map.put("disposePerson", disposePerson);
		
		map.put("ifSolved", ifSolved);
		
		Map<Pager<UnsolvedCaseServiceBean>, Long> map1 = unsolvedCaseQueryService.findUnsolvedCaseByConditions(map, 0, Integer.MAX_VALUE);
		
		Pager<UnsolvedCaseServiceBean> casePager = map1.entrySet().iterator().next().getKey();
		
		xingzhenglian =(long) casePager.getTotalNum();
		xingzhengpoan = xingzhenglian - map1.get(casePager);
		
		//unsolvedCaseBeanLst = casePager.getPageList();
		this.beanTransition(casePager.getPageList());
		
//		xingzhenglian = map1.get(casePager);
//		xingzhengpoan = xingzhenglian - casePager.getTotalNum();
		
		return exportExcel("是否结案");
	}

	public String exportExcel(String str) throws IOException {
		ExcelUtil<UnsolvedCaseBean> ex = new ExcelUtil<UnsolvedCaseBean>();
		String[] headers = { "序号", "案件编号", "案件名称", "案件类别", "办案单位", "主办侦查员",
				"案件状态", "发案时间", "发案地点", "简要案情",str };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdf.format(new Date());
		name = name + ".xls";
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, unsolvedCaseBeanLst, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		if("是否结案".equals(str)){
			this.creatCaseManagementLog("案件监控管理/破/结案情况月报表", "导出行政案件excel");
		}
		if("是否破案".equals(str)){
			this.creatCaseManagementLog("案件监控管理/破/结案情况月报表", "导出刑事案件excel");
		}
		return "done";
	}

	private void beanTransition(List<UnsolvedCaseServiceBean> pageList) {
		int i = 1;
		for(UnsolvedCaseServiceBean unSolved: pageList){
			UnsolvedCaseBean bean = new UnsolvedCaseBean();
			bean.setCaseCode(unSolved.getCaseCode());
			bean.setCaseName(unSolved.getCaseName());
			bean.setCaseSort(unSolved.getCaseSort());
			bean.setDetail(unSolved.getDetail());
			bean.setHappenedAddress(unSolved.getHappenedAddress());
			if(unSolved.getHappenedTime()!=null){
				bean.setHappenedTime(unSolved.getHappenedTime().replace(".0", ""));
			}else{
				bean.setHappenedTime("");
			}
			bean.setInvestigator(unSolved.getInvestigator());
			bean.setSponsor(unSolved.getSponsor());
			bean.setState(unSolved.getState());
			bean.setNum(String.valueOf(i++)); 
			bean.setIfSolved(unSolved.getIfSolved()); 
			unsolvedCaseBeanLst.add(bean);
		}
	}
	
	/**
	 * string 转换成月初 年月日
	 * @return
	 */
	public Object  selectToDateStart(String str){
		String year=str.substring(0,4);
		String month=str.substring(5,7);
		String day="01";
		String dateStr=year+month+day+" 00:00:00";
        return  StrToDate(dateStr);
	}
	
	/**
	 * string 转换成月尾 年月日
	 * @return
	 */
	public Object  selectToDateEnd(String str){
		String year=str.substring(0,4);
		String month=str.substring(5,7);
		 Calendar cal = Calendar.getInstance();
        // 年  
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        // 月，因为Calendar里的月是从0开始，所以要-1  
         cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);  
        // 日，设为一号  
        cal.set(Calendar.DATE, 1);
        // 月份加一，得到下个月的一号  
        cal.add(Calendar.MONTH, 1); 
        // 下一个月减一为本月最后一天  
        cal.add(Calendar.DATE, -1);
        String day= String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号 
        String dateStr=year+month+day+" 23:59:59";
        return  StrToDate(dateStr);
	}
	
	
	
	/**
	 * String 转换成date;
	 * @param dateStr
	 * @return
	 */
	public Date StrToDate(String dateStr){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		try {
			date=sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public Object selectToDate(String queryTime) {
		if( queryTime.length() == 0){
			return null;
		}
		String str = queryTime;
		String year = str.substring(0, str.indexOf("-"));
		String month = str.substring(str.indexOf("-") + 1, str.length());
		Date date = new Date();
		Integer i =  Integer.parseInt(year);
		date.setYear(  i - 1900 );
		date.setMonth(Integer.parseInt(month));
		Integer  a = date.getMonth();
		date.setMonth(a);
		date.setDate(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		if(date.getTime() > new Date().getTime()){
			date = new Date();
		}
		return date;
	}
	
	

	public List<UnsolvedCaseBean> getUnsolvedCaseBeanLst() {
		return unsolvedCaseBeanLst;
	}

	public void setUnsolvedCaseBeanLst(List<UnsolvedCaseBean> unsolvedCaseBeanLst) {
		this.unsolvedCaseBeanLst = unsolvedCaseBeanLst;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

	public String getCaseCodeOrName() {
		return caseCodeOrName;
	}

	public void setCaseCodeOrName(String caseCodeOrName) {
		this.caseCodeOrName = caseCodeOrName;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getCaseSort() {
		return caseSort;
	}

	public void setCaseSort(String caseSort) {
		this.caseSort = caseSort;
	}

	public Long getCaseTimeStart() {
		return caseTimeStart;
	}

	public void setCaseTimeStart(Long caseTimeStart) {
		this.caseTimeStart = caseTimeStart;
	}

	public Long getCaseTimeEnd() {
		return caseTimeEnd;
	}

	public void setCaseTimeEnd(Long caseTimeEnd) {
		this.caseTimeEnd = caseTimeEnd;
	}

	public Long getXingshilian() {
		return xingshilian;
	}

	public void setXingshilian(Long xingshilian) {
		this.xingshilian = xingshilian;
	}

	public Long getXingshipoan() {
		return xingshipoan;
	}

	public void setXingshipoan(Long xingshipoan) {
		this.xingshipoan = xingshipoan;
	}

	public Long getXingzhenglian() {
		return xingzhenglian;
	}

	public void setXingzhenglian(Long xingzhenglian) {
		this.xingzhenglian = xingzhenglian;
	}

	public Long getXingzhengpoan() {
		return xingzhengpoan;
	}

	public void setXingzhengpoan(Long xingzhengpoan) {
		this.xingzhengpoan = xingzhengpoan;
	}

	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

	public String getDisposePerson() {
		return disposePerson;
	}

	public void setDisposePerson(String disposePerson) {
		this.disposePerson = disposePerson;
	}

	public String getIfSolved() {
		return ifSolved;
	}

	public void setIfSolved(String ifSolved) {
		this.ifSolved = ifSolved;
	}
}
