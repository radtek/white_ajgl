package com.taiji.pubsec.ajqlc.sawp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.sawp.action.bean.TransitStorageReturnTimeOutExcelBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempObjectBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageInInfoBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageInService;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.common.web.action.ExportInfoReq;


/**
 * 涉案物品--暂存物品管理-超时未返还
 * 
 * @author 
 *
 */
@Controller("transitStorageReturnTimeOutAction")
@Scope("prototype")
public class TransitStorageReturnTimeOutAction extends ReturnMessageAction {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TransitStorageReturnTimeOutAction.class);
	
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	@Resource
	private ITemporaryStorageInService temporaryStorageInService;
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	
	
	
	private List<TempStorageInInfoBean> returnTimeOutList =new ArrayList<TempStorageInInfoBean>();
	
	private List<TransitStorageReturnTimeOutExcelBean> lstExecl =new ArrayList<TransitStorageReturnTimeOutExcelBean>();

	private String areaCode; //物品区编码
	private List<String> storageCodeList=new ArrayList<String>(); //储物箱编码list
	private String suspectNameOrIdCard; //嫌疑人姓名或者身份证号
	private String code; //入库单编号
	private String caseNameOrCode; //案件名称或者编码
	
		
	/**
	 * 查询超时未返还物品清单
	 * 
	 * @return SUCCESS
	 */
	public String queryTransitStorageReturnTimeOut() {
		Pager<TempStorageInInfoBean> pager = temporaryStorageInService.findTimeoutTemporaryInByPage(
				searchMap(), this.getStart() / this.getLength(),
				this.getLength());
		if(pager==null){
			this.setTotalNum(0);
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		changeForCaseName(pager.getPageList());
		return SUCCESS;
	}
	

	/**
	 * 超时未返还物品清单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		Map<String, Object> map = searchMap();
		map.put("excel", "1");
		Pager<TempStorageInInfoBean> pager = temporaryStorageInService.findTimeoutTemporaryInByPage(
				map, 0, Integer.MAX_VALUE);
        if(pager==null){
        	this.setTotalNum(0);
        }else{
        	this.setTotalNum(pager.getTotalNum());
        }
		int i = 1;
		if (null == pager.getPageList() || pager.getPageList().size() > 0) {
			for (TempStorageInInfoBean form : pager.getPageList()) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TransitStorageReturnTimeOutExcelBean bean = transitStorageOutBackToExcelBean(form,fmt);
				bean.setCount(i + "");
				i++;
				lstExecl.add(bean);
			}
		}
		ExcelUtil<TransitStorageReturnTimeOutExcelBean> ex = new ExcelUtil<TransitStorageReturnTimeOutExcelBean>();
		String[] headers = { "序号", "超时时长", "对应案件编号",  "对应案件名称", "办案民警",
				"对应犯罪嫌疑人/物品持有人", "物品名称","所在物证保管区","所在储物箱", "备注" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdf.format(new Date());
		name = name + ".xls";
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, lstExecl, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		this.creatCaseManagementLog("涉案物品管理/暂存物品管理/超时未返还物品清单", "导出超时未返还物品清单excel,查询条件(入库单编号:"+code+"对应案件编号/名称："+caseNameOrCode+"对应犯罪嫌疑人姓名/身份证号:"+suspectNameOrIdCard+"储物区code:"+areaCode+"储物箱code集合:"+lstToStr(storageCodeList)+")");
		return "done";
	}

	/**
	 * 判断文件路径是否存在，不存在则创建路径
	 * 
	 * @param path
	 */
	public static void mkdir(String path) {
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}
	
	private Map<String, Object> searchMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storageInCode", code);//入库单编号
		map.put("caseCodeOrName", caseNameOrCode);//对应案件编号/名称
		map.put("suspectIdOrName",suspectNameOrIdCard ); //对应犯罪嫌疑人姓名/身份证号
		map.put("areaCode",areaCode ); //储物区code
		map.put("shelfCodeList",storageCodeList ); //储物箱list
		return map;
	}
	
	/**
	 * 将前台的list 转换成逗号分隔的string 
	 * @param lst
	 * @return
	 */
	private String lstToStr(List<String> lst){
		StringBuffer bf=new StringBuffer();
		bf.append("");
		for(String str:lst){
			bf.append(str+",");
		}
		if(bf.length()<1){
			return "";
		}
		bf.substring(0, bf.length()-1);
		return bf.toString();
	}

	/**
	 * 转换打印的bean
	 * @param form
	 * @return
	 */
	private TransitStorageReturnTimeOutExcelBean transitStorageOutBackToExcelBean(TempStorageInInfoBean form,SimpleDateFormat fmt) {
		TransitStorageReturnTimeOutExcelBean bean = new TransitStorageReturnTimeOutExcelBean();
		bean.setStorageInDateTime(findOverTime(bean.getStorageInDateTime(),fmt));
		List<TempObjectBean> lst=temporaryStorageInService.findTemporaryObjectByStorageByCode(form.getHarCode());
		String inventory="";
		for(TempObjectBean tn:lst){
			inventory+=tn.getObjectName()+" "+tn.getObjectCharacter()+" "+tn.getInThisNum()+tn.getMeasureUnit()+";";
		}
		bean.setInventory(inventory);//物品清单
		List<TemporaryStorageShelfBean> lt= temporaryStorageInService.findStorageShelfByCode(form.getStorageInCode());
	    for(TemporaryStorageShelfBean tb:lt){
	    	bean.setInStorageSelf(tb.getAddress());//储物箱
	    	bean.setInStorageArea(tb.getAreaBean().getAddress());//储物区
	    }
	    bean.setRemark(form.getRemark());
		HandlingAreaReceipt har=handlingAreaReceiptService.findHandlingAreaReceiptByNum(form.getHarCode());
		if(har==null){
			return bean;
		}
		BasicCase bc=har.getBasicCase();
		if(bc==null){
			return bean;
		}
		bean.setCaseCode(bc.getLawCase());//案件编号;
		bean.setCaseName(findCaseNameByCode(bc.getLawCase()));//案件名称
		bean.setSolvePolice(bc.getHandlingPolice());//办案民警
		bean.setObjectOwnerPerson(bc.getByQuestioningPeopleName());//物品持有人
		return bean;
	}

	/**
	 * 查询出案件名称,并添加到list里
	 * @param pageList
	 * @return
	 */
	private void changeForCaseName(List<TempStorageInInfoBean> pageList) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(TempStorageInInfoBean bean: pageList){
			bean.setStorageInDateTime(findOverTime(bean.getStorageInDateTime(),fmt));
			List<TempObjectBean> lst=temporaryStorageInService.findTemporaryObjectByStorageByCode(bean.getHarCode());
			bean.setTempObjectBeanList(lst);//物品清单
			List<TemporaryStorageShelfBean> lt= temporaryStorageInService.findStorageShelfByCode(bean.getStorageInCode());
		    for(TemporaryStorageShelfBean tb:lt){
		    	bean.setInStorageSelf(tb.getAddress());//储物箱
		    	bean.setInStorageArea(tb.getAreaBean().getName());//储物区
		    }
			HandlingAreaReceipt har=handlingAreaReceiptService.findHandlingAreaReceiptByNum(bean.getHarCode());
			if(har==null){
			    continue;
			}
			BasicCase bc=har.getBasicCase();
			if(bc==null){
				continue;
			}
			bean.setCaseCode(bc.getLawCase());//案件编号;
			bean.setCaseName(findCaseNameByCode(bc.getLawCase()));//案件名称
			bean.setSolvePolice(bc.getHandlingPolice());//办案民警
			bean.setObjectOwnerPerson(bc.getByQuestioningPeopleName());//物品持有人
		    returnTimeOutList.add(bean);
		}
	}
	
	private String findOverTime(String str,SimpleDateFormat fmt){
		if(str==null){
			return "";
		}
		Long time = null;
		try {
			time = new Date().getTime()-fmt.parse(str).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String st="";
		st+=time/(60*60*1000)+"小时"+(time%(60*60*1000))/(60*1000)+"分钟";
		return st;
	}
	
	/**
	 * 通过案件编号查询案件名称
	 * @param code
	 * @return
	 */
	private String findCaseNameByCode(String code){
		CriminalBasicCase cri=criminalCaseService.findCriminalCaseByCaseId(code);
		if(cri==null){
			return "--";
		}
		return cri.getCaseName();
	}
	

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}



	public String getAreaCode() {
		return areaCode;
	}





	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}





	public List<String> getStorageCodeList() {
		return storageCodeList;
	}





	public void setStorageCodeList(List<String> storageCodeList) {
		this.storageCodeList = storageCodeList;
	}





	public String getSuspectNameOrIdCard() {
		return suspectNameOrIdCard;
	}





	public void setSuspectNameOrIdCard(String suspectNameOrIdCard) {
		this.suspectNameOrIdCard = suspectNameOrIdCard;
	}





	public String getCaseNameOrCode() {
		return caseNameOrCode;
	}





	public void setCaseNameOrCode(String caseNameOrCode) {
		this.caseNameOrCode = caseNameOrCode;
	}





	public String getCode() {
		return code;
	}





	public void setCode(String code) {
		this.code = code;
	}


	public List<TempStorageInInfoBean> getReturnTimeOutList() {
		return returnTimeOutList;
	}


	public void setReturnTimeOutList(List<TempStorageInInfoBean> returnTimeOutList) {
		this.returnTimeOutList = returnTimeOutList;
	}


	public List<TransitStorageReturnTimeOutExcelBean> getLstExecl() {
		return lstExecl;
	}


	public void setLstExecl(List<TransitStorageReturnTimeOutExcelBean> lstExecl) {
		this.lstExecl = lstExecl;
	}


}
