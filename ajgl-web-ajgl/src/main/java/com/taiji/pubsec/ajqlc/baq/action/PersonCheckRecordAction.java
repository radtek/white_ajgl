package com.taiji.pubsec.ajqlc.baq.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.bean.PersonBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.PersonCheckRecordBean;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.model.PersonCheckRecord;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IPersonCheckRecordService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 人身检查记录Action
 * 
 * @author WL-PC
 *
 */
@Controller("personCheckRecordAction")
@Scope("prototype")
public class PersonCheckRecordAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonCheckRecordAction.class);

	private String handlingAreaReceiptId;// 办案区使用单id

	private PersonCheckRecordBean personCheckRecordBean;// 人身检查记录对象Bean
	
	private List<String> deleteFileIdList = new ArrayList<String>();// 需要删除的附件id

	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;// 办案区使用单接口

	@Resource
	private IPersonCheckRecordService personCheckRecordService;// 人身检查记录接口

	@Resource
	private IOperateRecordService operateRecordService;// 操作记录接口

	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;// 附件接口

	private String harId;
	
	private boolean modifyBtnFlag;
	
	private boolean printBtnFlag;
	
	private boolean finishBtnFlag;
	
	private String justShow;
	
	@SuppressWarnings("unchecked")
	public String showLookPersonCheckRecordPage(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		if("true".equals(justShow)){
			setModifyBtnFlag(false);
			setPrintBtnFlag(false);
			setFinishBtnFlag(false);
			return SUCCESS;
		}
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(harId);
		if(Constant.SYDZT_YWC.equals(har.getStatus())){
			setFinishBtnFlag(false);
			setModifyBtnFlag(false);
			setPrintBtnFlag(true);
		}else{
			setPrintBtnFlag(false);
			PersonCheckRecord pck = har.getPersonCheckRecord();
			mPerson = (Map<String, String>)userMap.get("person");
			if(pck !=null && !mPerson.get("code").equals(pck.getModifyPeople().getCode())){
				setModifyBtnFlag(false);
			}else{
				//TODO SessionUserDetailsUtil.isResourceAccess("/handlingAreaReceipt/*");
				setModifyBtnFlag(true);
			}
			setFinishBtnFlag(true);
		}
		List<Map<String, String>> roles = (List<Map<String, String>>) userMap
				.get("roles");
		for (Map<String, String> role : roles) {
			if (SUPER_ROLE_CODE.equals(role.get("code"))) {
				setModifyBtnFlag(true);
				break;
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+harId);
		return SUCCESS;
	}
	
	/**
	 * 新建人身检查记录
	 * 
	 * @param personCheckRecordBean 人身检查记录bean对象
	 * @return "success",成功返回handlingAreaReceiptId：办案区使用单id
	 */
	public String savePersonCheckRecord() {
		PersonCheckRecord pcr = personCheckRecordBeanToPersonCheckRecord(personCheckRecordBean);
		handlingAreaReceiptId = personCheckRecordService.savePersonCheckRecord(pcr);

		this.setModifyPerson(pcr.getModifyPeople().getName());
		this.setModifyTime(DateTimeUtil.formatDateTime(pcr.getUpdatedTime(), Constant.DATE_FORMAT));
		List<FileObjectBean> fileList = personCheckRecordBean.getFiles();
		if(fileList == null || fileList.isEmpty()){
			saveOperateRecord("","0","创建人身检查记录");
			return SUCCESS;
		}
		for(FileObjectBean fob : fileList){
			Attachment att = attachmentCustomizedService.findById(fob.getId());
			att.setTargetId(pcr.getId());
			att.setType(PersonCheckRecord.class.getName());
			attachmentCustomizedService.save(att);
		}
		//保存操作记录
		saveOperateRecord("","0","创建人身检查记录");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护人身检查记录页面(使用单ID:"+handlingAreaReceiptId+")--新建人身检查记录");
		return SUCCESS;
	}

	/**
	 * 修改人身检查记录
	 * 
	 * @param personCheckRecordBean 人身检查记录bean对象
	 * @return "success",成功无返回值
	 */
	public String updatePersonCheckRecord() {
		PersonCheckRecord pcr = personCheckRecordBeanToPersonCheckRecord(personCheckRecordBean);
		//更新附件
		List<FileObjectBean> foBeanLst = personCheckRecordBean.getFiles();
		if(foBeanLst != null){
			for(FileObjectBean foBean : foBeanLst){
				Attachment att = attachmentCustomizedService.findById(foBean.getId());
				att.setTargetId(personCheckRecordBean.getId());
				att.setType(PersonCheckRecord.class.getName());
				attachmentCustomizedService.save(att);
			}
		}
		for(String str : deleteFileIdList){
			attachmentCustomizedService.delete(str);
		}
		
		personCheckRecordService.updatePersonCheckRecord(pcr);
		//保存操作记录
		saveOperateRecord("","1","更新人身检查记录");
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(pcr.getHandlingAreaReceipt().getId());
		if(har!=null){
			PersonCheckRecord p = har.getPersonCheckRecord();
			if(p!=null){
				this.setModifyPerson(p.getModifyPeople().getName());
				this.setModifyTime(DateTimeUtil.formatDateTime(p.getUpdatedTime(),Constant.DATE_FORMAT));
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护人身检查记录页面(使用单ID:"+personCheckRecordBean.getHandlingAreaReceiptId()+")--修改人身检查记录");
		return SUCCESS;
	}

	/**
	 * 根据办案区使用单id查询人身检查记录
	 * 
	 * @param handlingAreaReceiptId 办案区使用单id
	 * @return "success",成功返回personCheckRecordBean：人身检查记录bean对象
	 */
	public String searchPersonCheckRecordByHandlingAreaReceiptId() {
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(handlingAreaReceiptId);
		PersonCheckRecord pcr = har.getPersonCheckRecord();
		if (pcr == null) {
			return SUCCESS;
		}
		personCheckRecordBean = personCheckRecordToPersonCheckRecordBean(pcr);
		
		List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(pcr.getId(),PersonCheckRecord.class.getName());
		if (attLst.isEmpty()) {
			return SUCCESS;
		}
		List<FileObjectBean> foBeanList = new ArrayList<FileObjectBean>();
		for(Attachment att : attLst){
			FileObjectBean b = new FileObjectBean();
			b.setId(att.getId());
			b.setName(att.getName());
			try {
				InputStream isTemp = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();

				byte[] buffer = new byte[isTemp.available()];
				isTemp.read(buffer, 0, isTemp.available());
				b.setBase64Str(new String(Base64.encodeBase64(buffer, false)));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			foBeanList.add(b);
		}
		personCheckRecordBean.setFiles(foBeanList);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+handlingAreaReceiptId);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打开维护人身检查记录页面(使用单ID:"+handlingAreaReceiptId+")");
		return SUCCESS;
	}
	
	/**
	 * 保存操作记录
	 * 
	 * @param noteText 备注内容
	 * @param noteType 备注类型
	 * @param operateContent 记录类容
	 */
	private void saveOperateRecord(String noteText, String noteType, String operateContent){
		OperateRecord operateRecord = new OperateRecord();
		operateRecord.setModelObject(HandlingAreaReceipt.class.getName());
		operateRecord.setModelObjectId(personCheckRecordBean.getHandlingAreaReceiptId());
		operateRecord.setNoteText(noteText);
		operateRecord.setNoteType(noteType);
		operateRecord.setOperateContent(operateContent);
		operateRecord.setOperateTime(new Date());
		operateRecord.setOperator(this.findCurrentPerson().getName());
		operateRecordService.saveOperateRecord(operateRecord);
	}

	/**
	 * 人身检查记录model转bean
	 * 
	 * @param personCheckRecord 人身检查记录model对象
	 * @return pcrb 人身检查记录bean对象
	 */
	private static PersonCheckRecordBean personCheckRecordToPersonCheckRecordBean(PersonCheckRecord personCheckRecord) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
		PersonCheckRecordBean pcrb = new PersonCheckRecordBean();
		if (personCheckRecord == null) {
			return null;
		}
		pcrb.setId(personCheckRecord.getId());
		pcrb.setSelfReportedSymptoms(personCheckRecord.getSelfReportedSymptoms());
		pcrb.setCheckCondition(personCheckRecord.getCheckCondition());
		pcrb.setCheckPolice(personCheckRecord.getCheckPolice());
		pcrb.setEyewitness(personCheckRecord.getEyewitness());
		pcrb.setIsCheckedPerson(personCheckRecord.getIsCheckedPerson());
		if(personCheckRecord.getModifyPeople() != null){
			PersonBean bean = new PersonBean();
			bean.setId(personCheckRecord.getModifyPeople().getId());
			bean.setName(personCheckRecord.getModifyPeople().getName());
			bean.setCode(personCheckRecord.getModifyPeople().getCode());
			pcrb.setModifyPeopleBean(bean);
		}
		if (personCheckRecord.getUpdatedTime() != null) {
			pcrb.setUpdateTime(sdf.format(personCheckRecord.getUpdatedTime()));
		}
		pcrb.setHandlingAreaReceiptId(personCheckRecord.getHandlingAreaReceipt() == null ? ""
				: personCheckRecord.getHandlingAreaReceipt().getId());
		return pcrb;
	}

	/**
	 * 人身检查记录bean转model
	 * 
	 * @param pcrb 人身检查记录bean对象
	 * @return personCheckRecord 人身检查记录model对象
	 */
	private PersonCheckRecord personCheckRecordBeanToPersonCheckRecord(PersonCheckRecordBean personCheckRecordBean) {
		if (personCheckRecordBean == null) {
			return null;
		}
		PersonCheckRecord pcr = new PersonCheckRecord();
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(personCheckRecordBean.getHandlingAreaReceiptId());
		if(har != null && har.getPersonCheckRecord() != null){
			pcr = har.getPersonCheckRecord();
		}
		pcr.setSelfReportedSymptoms(personCheckRecordBean.getSelfReportedSymptoms());
		pcr.setCheckCondition(personCheckRecordBean.getCheckCondition());
		pcr.setCheckPolice(personCheckRecordBean.getCheckPolice());
		pcr.setEyewitness(personCheckRecordBean.getEyewitness());
		pcr.setIsCheckedPerson(personCheckRecordBean.getIsCheckedPerson());
		pcr.setHandlingAreaReceipt(har);
		return pcr;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}

	public PersonCheckRecordBean getPersonCheckRecordBean() {
		return personCheckRecordBean;
	}

	public void setPersonCheckRecordBean(PersonCheckRecordBean personCheckRecordBean) {
		this.personCheckRecordBean = personCheckRecordBean;
	}

	public List<String> getDeleteFileIdList() {
		return deleteFileIdList;
	}

	public void setDeleteFileIdList(List<String> deleteFileIdList) {
		this.deleteFileIdList = deleteFileIdList;
	}

	public String getHarId() {
		return harId;
	}

	public void setHarId(String harId) {
		this.harId = harId;
	}

	public boolean isModifyBtnFlag() {
		return modifyBtnFlag;
	}

	public void setModifyBtnFlag(boolean modifyBtnFlag) {
		this.modifyBtnFlag = modifyBtnFlag;
	}

	public boolean isPrintBtnFlag() {
		return printBtnFlag;
	}

	public void setPrintBtnFlag(boolean printBtnFlag) {
		this.printBtnFlag = printBtnFlag;
	}

	public boolean isFinishBtnFlag() {
		return finishBtnFlag;
	}

	public void setFinishBtnFlag(boolean finishBtnFlag) {
		this.finishBtnFlag = finishBtnFlag;
	}

	public String getJustShow() {
		return justShow;
	}

	public void setJustShow(String justShow) {
		this.justShow = justShow;
	}
	

}
