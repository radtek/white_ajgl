package com.taiji.pubsec.ajqlc.sawp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.dbtx.task.util.temporaryStorage.pojo.TemporaryStorageData;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.alert.model.AlertRule;
import com.taiji.pubsec.ajqlc.alert.service.IAlertRuleService;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.sawp.action.bean.TransitStorageInBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.TransitStorageInExcelBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.TransitStoreManageExcelBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempObjectBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageInInfoBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.model.StoragePosition;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.ajqlc.sawp.service.ITempStorageSelfService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageInService;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.businesscomponent.organization.model.Account;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.common.tool.mission.client.core.TaskClient;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;
import com.taiji.pubsec.common.web.action.ExportInfoReq;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 暂管物品管理
 * @author lenovo
 *
 */
@Controller("transitStoreManageAction")
@Scope("prototype")
public class TransitStoreManageAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(TransitStoreManageAction.class);

	@Resource
	private ITemporaryStorageInService temporaryStorageInService;
	
	@Resource
	private ITempStorageSelfService tempStorageSelfService;
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	
	@Resource
	private IPersonService personService;
	
	@Resource	// 预警规则接口
	private IAlertRuleService alertRuleService;
	
	@Resource(name="dbtxTaskClient")
	private TaskClient taskClient;
	
	List<TempStorageInInfoBean> tempStorageInInfoBeanList =new ArrayList<TempStorageInInfoBean>();
	
	List<TransitStorageInExcelBean> exportExcelBean =new ArrayList<TransitStorageInExcelBean>();
	
	List<TransitStoreManageExcelBean> transitStoreManageExcelBean =new ArrayList<TransitStoreManageExcelBean>();
	
	List<TemporaryStorageShelfBean> storageShelfBeanList = new ArrayList<TemporaryStorageShelfBean>();
	
	List<TempObjectBean> tempObjectBeanList = new ArrayList<TempObjectBean>();
	
	private TransitStorageInBean transitStorageInBean; 
	
	private TempStorageInInfoBean tempStorageInInfoBean;
	
	private List<String> formIdList = new ArrayList<String>(); // 入库单id list
	
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	
	private String id;
	private String code;
	private String caseCode;
	private String suspectName;
	private String startDate;
	private String endDate;
	private String isShow;
	
	private String areaCode; //物品区编码
	private List<String> storageCodeList; //储物箱编码list
	private String suspectNameOrIdCard; //嫌疑人姓名或者身份证号
	private String policeNameOrNum; //办案民警警号或者姓名
	private String caseNameOrCode; //案件名称或者编码
	private String isFree; //储物箱是否空闲
	
	/**
	 * 暂存物品存储情况查询
	 * @return
	 */
	public String queryTransitStoreManageList(){
		
		Map<String, Object> map = beanByMap();
		Pager<TemporaryStorageShelfBean> pager= tempStorageSelfService.findAllTemporaryStorageInByConditions(
				map, this.getStart() / this.getLength(), this.getLength());
		if (pager == null) {
			this.setTotalNum(0);
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		storageShelfBeanList = pager.getPageList();
		if(storageShelfBeanList.isEmpty()){
			for(int i=0; i<storageShelfBeanList.size(); i++){
				TemporaryStorageShelfBean bean =storageShelfBeanList.get(i);
				if(null != bean){
					this.getTemporaryStorageShelfBean(bean);
				}
			}
		}
		return SUCCESS ;
	}

	public TemporaryStorageShelfBean getTemporaryStorageShelfBean(TemporaryStorageShelfBean bean){
		String storageInCode =bean.getStorageInCode();
		if(storageInCode==null){
			return bean;
		}
		TemporaryStorageIn storageIn = temporaryStorageInService.findTemporaryStorageInByCode(storageInCode);
		String harCode = storageIn.getHarCode();
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(harCode);
		Set<HandlingAreaReceiptToPoliceInfo> police = har.getHandlingAreaReceiptToPoliceInfoList();
		if(police.size()>=0){
			bean.setHandlingPolices(police.iterator().next().getPoliceInfo().getPoliceName());
		}else{
			bean.setHandlingPolices("");
		}
		BasicCase basicCase = har.getBasicCase();
		CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(basicCase.getLawCase());
		bean.setCaseCode(basicCase.getLawCase());
		if(null != cbc){
			bean.setCaseName(cbc.getCaseName());
		}
		bean.setSuspectName(basicCase.getByQuestioningPeopleName());
		bean.setSuspectIdCardNum(basicCase.getByQuestioningPeopleIdentifyNum());
		List<String> goodsList = bean.getGoodsList(); 
		List<TempObjectBean>  list = temporaryStorageInService.findTemporaryObjectByStorageByCode(harCode);
		if(list.isEmpty()){
			bean.setInStorageNum( list.size() + 1);
			for(int j =0; j<list.size(); j++){
				goodsList.add(list.get(j).getObjectName());
			}
		}
		bean.setGoodsList(goodsList);
		return bean;
	}
	
	/**
	 * 新增暂存物品入库界面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findTransitStoreManageInByMakingId(){
		tempStorageInInfoBean= new TempStorageInInfoBean();
		
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(code);
		
		TemporaryStorageIn temporary = temporaryStorageInService.findTemporaryStorageInByHarCode(code);
		if(null != temporary){
			this.setMsg("此使用单已进行入库操作，请勿重复操作！");
		}
		if(null == temporary){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Set<HandlingAreaReceiptToPoliceInfo> police = har.getHandlingAreaReceiptToPoliceInfoList();
			if(police.size()>=0){
				tempStorageInInfoBean.setSolvePolice(police.iterator().next().getPoliceInfo().getPoliceName());
			}else{
				tempStorageInInfoBean.setSolvePolice("");
			}
			BasicCase basicCase = har.getBasicCase();
			CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(basicCase.getLawCase());
			//tempStorageInInfoBean.setRemark(basicCase.getCauseOfLawCase());
			tempStorageInInfoBean.setCaseCode(basicCase.getLawCase());
			if(null != cbc){
				tempStorageInInfoBean.setCaseName(cbc.getCaseName());
			}
			tempStorageInInfoBean.setObjectOwnerPerson(basicCase.getByQuestioningPeopleName());
			tempStorageInInfoBean.setIdCard(basicCase.getByQuestioningPeopleIdentifyNum());
			tempStorageInInfoBean.setStorageInCode(temporaryStorageInService.acquireNum());
			tempStorageInInfoBean.setStorageInDateTime(sdf.format(har.getCreatedTime()));
			tempStorageInInfoBean.setModifyTime(sdf.format(har.getUpdatedTime()));
			MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
			Map<String, Object> userMap = user.getUserMap();
			if (userMap.get("person") != null) {
				Map<String, Object>  person= (Map<String, Object>) userMap.get("person");
				tempStorageInInfoBean.setModifyPerson(person.get("name").toString());
			}
		}
			
		return SUCCESS;
	}

	/**
	 * 暂存物品入库详情查看界面
	 * @return
	 */
	public String viewTransitStoreIn(){
		
		tempStorageInInfoBean = new TempStorageInInfoBean();
		
		tempStorageInInfoBean = temporaryStorageInService.findTemporaryStorageInDeatailByCode(code);
		
		if(null != tempStorageInInfoBean){
			CriminalBasicCase basicase= criminalCaseService.findCriminalCaseByCaseId(tempStorageInInfoBean.getCaseCode());
			tempStorageInInfoBean.setCaseName(basicase.getCaseName());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 入库单详情清单
	 * @return
	 */
	public String findTransitStoreInFormByInCode(){
		
		tempStorageInInfoBean = new TempStorageInInfoBean();
		
		tempStorageInInfoBean = temporaryStorageInService.findTemporaryStorageInDeatailByCode(code);
		
		List<TempObjectBean> bean = new ArrayList<TempObjectBean>();
		
		if(null != tempStorageInInfoBean.getTempObjectBeanList()){
			bean = tempStorageInInfoBean.getTempObjectBeanList();
		}
		
		tempObjectBeanList = new ArrayList<TempObjectBean>();
		if(null != bean && bean.size() >= 0){
			for(int i=0 ; i<bean.size(); i++){
				tempObjectBeanList.add(findPhoto(bean.get(i)));
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 暂存物品入库清单
	 * @return
	 */
	public String findTransitStoreInFormByCode(){
		
		List<TempObjectBean> bean = new ArrayList<TempObjectBean>();
		
		bean = temporaryStorageInService.findTemporaryObjectByStorageByCode(code);
		
		tempObjectBeanList = new ArrayList<TempObjectBean>();
		if(null != bean && bean.size() >= 0 ){
			for(int i=0 ; i<bean.size(); i++){
				
				tempObjectBeanList.add(findPhoto(bean.get(i)));
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 保存暂存物品入库单
	 * @return
	 */
	public String saveTransitStoreManageInfo(){
		
		if (null != transitStorageInBean) {
			id = temporaryStorageInService.saveTemporaryStorageIn(beanToModel(transitStorageInBean));
			
			//暂存物品超时未返还预警
			AlertRule ar = alertRuleService.findAlertRuleByCode(Constant.YJGZ_ZCWPCS);
		    if(Constant.ZT_QY.equals(ar.getStatus())){
				List<String> users = new ArrayList<String>();
				List<String> roles = new ArrayList<String>();
				
				if(ar.getAlertUsers() != null){
					Collections.addAll(users, ar.getAlertUsers().split(","));
				}
				if(ar.getAlertRoles() != null){
					Collections.addAll(roles, ar.getAlertRoles().split(","));
				}
		    	
		    	TemporaryStorageData data = new TemporaryStorageData();
		    	data.setAheadTime(ar.getAlertTimeBefore());
		    	data.setBusinessId(id);
		    	data.setBusinessType(TemporaryStorageIn.class.getName());
		    	data.setReceiveType(Account.class.getName());
		    	data.setShowTime(ar.getPopWindowDuaring());
		    	data.setStorageInCode(transitStorageInBean.getCode());
				data.setTemporaryStorageInCreatingTime(Long.valueOf(transitStorageInBean.getCreateDate()));
		    	data.setType(com.taiji.pubsec.ajgl.dbtx.task.util.Constant.TASK_TYPE_TEMPORARYSTORAGE);
		    	data.setWay(ar.getWay());
		    	
		    	HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(transitStorageInBean.getHarCode());
				Set<HandlingAreaReceiptToPoliceInfo> policeInfos = har.getHandlingAreaReceiptToPoliceInfoList();
		    	//提醒的用户
				Set<String> accountSet = new HashSet<String>();
				for(HandlingAreaReceiptToPoliceInfo policeInfo : policeInfos){
					Person p = personService.findPersonByCode(policeInfo.getPoliceInfo().getPoliceNum());
					if(p != null){
						if(p.getAccount() != null){
							accountSet.add(p.getAccount().getId());
						}
					}
				}
				for(String str: users){
					Person p = personService.findById(str);
					if(p != null){
						if(p.getAccount() != null){
							accountSet.add(p.getAccount().getId());
						}
					}
				}
				for(String str: roles){
					List<String> userList = handlingAreaReceiptService.findAccountsByRoleId(str);
					accountSet.addAll(userList);
				}
	    		data.setReceiveIdList(new ArrayList<String>(accountSet));
	    		taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
		    }
		}
		
		return SUCCESS;
	}
	
	/**
	 * 暂存物品入库查询
	 * @return
	 */
	public String queryTransitStoreInList(){
		Map<String, Object> paramMap = searchMap();
		Pager<TempStorageInInfoBean> pager = temporaryStorageInService.findTemporaryStorageInByPage(paramMap, this.getStart() / this.getLength(),
				this.getLength());
		if (pager == null) {
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		tempStorageInInfoBeanList = pager.getPageList();
		if(null != tempStorageInInfoBeanList){
			for(int i=0; i<tempStorageInInfoBeanList.size();i++){
				CriminalBasicCase basicase= criminalCaseService.findCriminalCaseByCaseId(tempStorageInInfoBeanList.get(i).getCaseCode());
				if(null != basicase){
					tempStorageInInfoBeanList.get(i).setCaseName(basicase.getCaseName());
				}
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 *批量删除入库单 
	 * @return
	 */
	public String deleteByIds(){
		if (null != formIdList && formIdList.size() > 0) {
			String str="";
			for (String id : formIdList) {
				
				str+=id+",";
			}
			str.substring(0,str.length()-1);
			this.creatCaseManagementLog("涉案物品管理 /暂存物品入库", "删除暂存物品入库单(暂存物品入库:"+str+")");
		}
		return SUCCESS;
	}
	
	public TempObjectBean findPhoto(TempObjectBean bean){
		//图片
		List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(bean.getObjectId(),bean.getObjectClassName());
		if (!attLst.isEmpty()) {
			for(Attachment att : attLst){
				InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
				try {
					byte[] buffer = new byte[is.available()];
					is.read(buffer, 0, is.available());
					bean.setObjectPicture(new String (Base64.encodeBase64(buffer, false)));
				} catch (Exception e) {
					logger.error("照片转换失败");
				}
			}
		}
		
		return bean;
	}
	
	/**
	 * 导出暂存物品存储情况查询表
	 * @return
	 * @throws Exception
	 */
	public String transitStoreManageExportExcel() throws Exception {
		Map<String, Object> map = beanByMap();
		Pager<TemporaryStorageShelfBean> pager= tempStorageSelfService.findAllTemporaryStorageInByConditions(
				map,  0, Integer.MAX_VALUE);
		if(pager!=null){
			storageShelfBeanList = pager.getPageList();
		}
		if(storageShelfBeanList.isEmpty()){
			for(int i=0; i<storageShelfBeanList.size(); i++){
				TemporaryStorageShelfBean bean =storageShelfBeanList.get(i);
				if(null != bean){
					this.getTemporaryStorageShelfBean(bean);
				}
			}
		}
		int i = 1;
		for (TemporaryStorageShelfBean form : storageShelfBeanList) {
			TransitStoreManageExcelBean bean = transitStoreManageExcelBean(form);
			bean.setCount(i + "");
			i++;
			transitStoreManageExcelBean.add(bean);
		}

		ExcelUtil<TransitStoreManageExcelBean> ex = new ExcelUtil<TransitStoreManageExcelBean>();
		String[] headers = { "序号", "储物箱", "是否空闲", "当前存放物品", "在库总件数","对应案件编号", "对应案件名称", "办案民警", "对应犯罪嫌疑人/物品持有人", "嫌疑人身份证号" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdf.format(new Date());
		name = name + ".xls";
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ex.exportExcel(headers, transitStoreManageExcelBean, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		return "done";
	}

	private TransitStoreManageExcelBean transitStoreManageExcelBean(TemporaryStorageShelfBean form) {

		TransitStoreManageExcelBean excelBean = new TransitStoreManageExcelBean();
		excelBean.setAddress(form.getAddress());
		excelBean.setCaseCode(form.getCaseCode());
		CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(form.getCaseCode());
		if(null != cbc){
			excelBean.setCaseName(cbc.getCaseName());
		}
		excelBean.setHandlingPolices(form.getHandlingPolices());
		excelBean.setInStorageNum(form.getInStorageNum());
		excelBean.setStatus(form.getStatus());
		excelBean.setSuspectIdCardNum(form.getSuspectIdCardNum());
		excelBean.setSuspectName(form.getSuspectName());
		StringBuilder str = new StringBuilder("");
		List<String> List = form.getGoodsList();
		if(List.isEmpty()){
			for (String string : List) {
				str.append(string+"  ");
			}
		}
		excelBean.setGoodsList(str.toString());
		return excelBean;
	}

	/**
	 * 暂存物品入库单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws Exception {
		Map<String, Object> map = searchMapExcel();
		Pager<TempStorageInInfoBean> pager = temporaryStorageInService.findTemporaryStorageInByPage(map,  0, Integer.MAX_VALUE);
		if (pager == null) {
			return "done";
		}
		int i = 1;
		if (null == pager.getPageList() || pager.getPageList().size() > 0) {
			for (TempStorageInInfoBean form : pager.getPageList()) {
				TransitStorageInExcelBean bean = tempStorageInInfoToExcelBean(form);
				bean.setCount(i + "");
				i++;
				exportExcelBean.add(bean);
			}
		}

		ExcelUtil<TransitStorageInExcelBean> ex = new ExcelUtil<TransitStorageInExcelBean>();
		String[] headers = { "序号", "入库单编号", "入库时间", "对应案件编号", "对应案件名称", "办案民警", "对应犯罪嫌疑人/物品持有人", "备注" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdf.format(new Date());
		name = name + ".xls";
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ex.exportExcel(headers, exportExcelBean, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		return "done";
	}
	
	private Map<String, Object> beanByMap() {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("areaId", areaCode);
		map.put("storageCodeList", storageCodeList);
		map.put("suspectNameOrIdCard",suspectNameOrIdCard);
		map.put("policeNameOrNum", policeNameOrNum);
		map.put("caseNameOrCode",caseNameOrCode );
		if("是".equals(isFree)){
			isFree=Constant.SF_S;
		} else if("否".equals(isFree)){
			isFree=Constant.SF_F;
		}else{
			isFree=null;
		}
		map.put("isFree", isFree);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> searchMapExcel() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("caseCode", caseCode);
		map.put("ownerName", suspectName);
		map.put("myid", "");
		if(isShow.equals("1")){
			MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
			Map<String, Object> userMap = user.getUserMap();
			if (userMap.get("person") != null) {
				Map<String, Object>  personMap= (Map<String, Object>) userMap.get("person");
				if(null != personMap.get("id")){
					map.put("myid", personMap.get("id").toString());
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!StringUtils.isBlank(startDate)) {
			try {
				map.put("rkStartDate", sdf.parse(startDate));
			} catch (ParseException e) {
				logger.error("时间转换出错：" + e);
			}
		}
		if (!StringUtils.isBlank(endDate)) {
			try {
				map.put("rkEndDate", sdf.parse(endDate));
			} catch (ParseException e) {
				logger.error("时间转换出错：" + e);
			}
		}
		return map;
	}
	
	private TransitStorageInExcelBean tempStorageInInfoToExcelBean(TempStorageInInfoBean bean) throws Exception{
		
		TransitStorageInExcelBean excelBean = new TransitStorageInExcelBean();
		excelBean.setCaseCode(bean.getCaseCode());
		excelBean.setCaseName(bean.getCaseName());
		excelBean.setObjectOwnerPerson(bean.getObjectOwnerPerson());
		excelBean.setRemark(bean.getRemark());
		excelBean.setSolvePolice(bean.getSolvePolice());
		excelBean.setStorageInCode(bean.getStorageInCode());
		excelBean.setStorageInDateTime(bean.getStorageInDateTime());
		
		return excelBean;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> searchMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", transitStorageInBean.getCode());
		map.put("caseCode", transitStorageInBean.getCaseCode());
		map.put("ownerName", transitStorageInBean.getOwnerName());
		
		map.put("myid", "");
		if(isShow.equals("1")){
			MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
			Map<String, Object> userMap = user.getUserMap();
			if (userMap.get("person") != null) {
				Map<String, Object>  personMap= (Map<String, Object>) userMap.get("person");
				if(null != personMap.get("id")){
					map.put("myid", personMap.get("id").toString());
				}
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (!StringUtils.isBlank(transitStorageInBean.getStartDate())) {
			try {
				map.put("rkStartDate", sdf.parse(transitStorageInBean.getStartDate()));
			} catch (ParseException e) {
				logger.error("时间转换出错：" + e);
			}
		}
		if (!StringUtils.isBlank(transitStorageInBean.getEndDate())) {
			try {
				map.put("rkEndDate", sdf.parse(transitStorageInBean.getEndDate()));
			} catch (ParseException e) {
				logger.error("时间转换出错：" + e);
			}
		}
		return map;
	}
	
	/**
	 * bean转model
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TemporaryStorageIn beanToModel(TransitStorageInBean bean) {
		TemporaryStorageIn  model =  new TemporaryStorageIn ();
		model.setCode(bean.getCode());
		model.setHarCode(bean.getHarCode());
		model.setRemark(bean.getRemark());
		long createDate = Long.parseLong(bean.getCreateDate());
		model.setCreateDate(new Date(createDate));
		List<StoragePosition> list = new ArrayList<StoragePosition>();
		StoragePosition storage = new StoragePosition();
		TemporaryStorageShelf temporary = new TemporaryStorageShelf();
		temporary.setAddress(bean.getTransitStoreLocker());
		storage.setBgg(temporary);
		model.setStoragePosition(list);
		model.setModifyTime(new Date());
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
		Map<String, Object> userMap = user.getUserMap();
		if (userMap.get("person") != null) {
			Map<String, Object>  map= (Map<String, Object>) userMap.get("person");
			Person person = new Person();
			if(null != map.get("id")){
				person.setId(map.get("id").toString());
			}
			if(null != map.get("name")){
				person.setName(map.get("name").toString());
			}
			if(null != map.get("code")){
				person.setCode(map.get("code").toString());
			}
			if(null != map.get("idNumber")){
				person.setIdNumber(map.get("idNumber").toString());
			}
			model.setModifyPerson(person);
			model.setCreatePerson(person);
		}
		return model;
	}

	public TransitStorageInBean getTransitStorageInBean() {
		return transitStorageInBean;
	}

	public void setTransitStorageInBean(TransitStorageInBean transitStorageInBean) {
		this.transitStorageInBean = transitStorageInBean;
	}

	public List<String> getFormIdList() {
		return formIdList;
	}

	public void setFormIdList(List<String> formIdList) {
		this.formIdList = formIdList;
	}


	public List<TransitStorageInExcelBean> getExportExcelBean() {
		return exportExcelBean;
	}


	public void setExportExcelBean(List<TransitStorageInExcelBean> exportExcelBean) {
		this.exportExcelBean = exportExcelBean;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getCaseCode() {
		return caseCode;
	}


	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}


	public String getSuspectName() {
		return suspectName;
	}


	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}


	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<TemporaryStorageShelfBean> getStorageShelfBeanList() {
		return storageShelfBeanList;
	}


	public void setStorageShelfBeanList(List<TemporaryStorageShelfBean> storageShelfBeanList) {
		this.storageShelfBeanList = storageShelfBeanList;
	}


	public List<TempObjectBean> getTempObjectBeanList() {
		return tempObjectBeanList;
	}


	public void setTempObjectBeanList(List<TempObjectBean> tempObjectBeanList) {
		this.tempObjectBeanList = tempObjectBeanList;
	}

	public List<TempStorageInInfoBean> getTempStorageInInfoBeanList() {
		return tempStorageInInfoBeanList;
	}

	public void setTempStorageInInfoBeanList(List<TempStorageInInfoBean> tempStorageInInfoBeanList) {
		this.tempStorageInInfoBeanList = tempStorageInInfoBeanList;
	}

	public TempStorageInInfoBean getTempStorageInInfoBean() {
		return tempStorageInInfoBean;
	}

	public void setTempStorageInInfoBean(TempStorageInInfoBean tempStorageInInfoBean) {
		this.tempStorageInInfoBean = tempStorageInInfoBean;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
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

	public String getPoliceNameOrNum() {
		return policeNameOrNum;
	}

	public void setPoliceNameOrNum(String policeNameOrNum) {
		this.policeNameOrNum = policeNameOrNum;
	}

	public String getCaseNameOrCode() {
		return caseNameOrCode;
	}

	public void setCaseNameOrCode(String caseNameOrCode) {
		this.caseNameOrCode = caseNameOrCode;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}
}
