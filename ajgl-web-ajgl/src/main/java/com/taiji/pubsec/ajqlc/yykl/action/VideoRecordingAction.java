package com.taiji.pubsec.ajqlc.yykl.action;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.HandlingAreaReceiptAction;
import com.taiji.pubsec.ajqlc.baq.action.bean.HarSearchBean;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.BurnRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBurnRecordService;
import com.taiji.pubsec.ajqlc.yykl.action.bean.RecordBean;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tool.util.FileFmtUtils;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;


/**
 * 影音刻录管理Action
 * 
 * @author 
 *
 */
@Controller("VideoRecordingAction")
@Scope("prototype")
public class VideoRecordingAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HandlingAreaReceiptAction.class);

	@Resource
	private IAskRoomService askRoomService;// 询问室接口
	
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;//相片附件
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;// 办案区使用单接口

	@Resource
	private IDictionaryItemService dictionaryItemService;// 字典项接口
	
	@Resource
	private IBurnRecordService burnRecordService;// 刻录详情接口
	
	private Map<String,Object> resultMap=new HashMap<String,Object>();
	
	private HarSearchBean harSearchBean = new HarSearchBean();
	
	private String  formId;
	
	private String  formCode;
	
	private Long  inTime;

	/**
	 * 分页查询本单位的办案区使用单，unitId为空或者null时查询所有单位,根据创建时间倒序排列
	 * @param queryConditions 查询条件，Map<String, String>类型，有：
	 * 		unitId 单位id
	 * 		handlingAreaReceiptNum 使用单编码
	 * 		askRoomName 使用房间名称
	 * 		handlingPolice 办案民警
	 * 		haveRecord 是否上传问询笔录,为“1”有笔录，为“0”无笔录
	 * 		status 使用单状态
	 * 		enterAreaReasons 进入办案区原由
	 * 		byQuestioningPeopleName 被讯问人姓名
	 * 		byQuestioningPeopleIdentifyNum 被讯问人证件号码
	 * 		lawCase 所属案件
	 * 		enterIntoTimeStart 开始时间
	 * 		enterIntoTimeEnd 结束时间
	 * 		modifyPeopleName 申请使用人
	 * 		recordStatus 是否已刻录
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 办案区使用单分页信息
	 */
	public String searchAllRecordFormListByCondition() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("handlingAreaReceiptNum", harSearchBean.getCode());
		map.put("askRoomName", harSearchBean.getRoom());
		map.put("handlingPolice", harSearchBean.getPolice());
//		map.put("haveRecord", harSearchBean.getRecord());
		map.put("status", harSearchBean.getState()); //是否刻录
		map.put("ifRecord", harSearchBean.getIfRecord()); //是否刻录
//		map.put("enterAreaReasons", harSearchBean.getEnterAreaReasons());
		map.put("byQuestioningPeopleName", harSearchBean.getAskPerson());
		map.put("byQuestioningPeopleIdentifyNum", harSearchBean.getIdNum());
		map.put("lawCase", harSearchBean.getAboutCase());
		map.put("enterIntoTimeStart", harSearchBean.getAccessDateStart());
		map.put("enterIntoTimeEnd", harSearchBean.getAccessDateEnd());
		if(!StringUtils.isBlank(harSearchBean.getAccessDateStart())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				map.put("enterIntoTimeStart",sdf.parse(harSearchBean.getAccessDateStart()+" 00:00:00"));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		if(!StringUtils.isBlank(harSearchBean.getAccessDateEnd())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				map.put("enterIntoTimeEnd",sdf.parse(harSearchBean.getAccessDateEnd()+" 23:59:59"));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		Pager<HandlingAreaReceipt> p = handlingAreaReceiptService.findHandlingAreaReceiptByKey(map, this.getStart() / this.getLength(), this.getLength());
		resultMap.put("totalNum", p.getTotalNum());
	    List<RecordBean> list=new ArrayList<RecordBean>();
		for (HandlingAreaReceipt har : p.getPageList()) {
			list.add(handlingAreaReceiptToHarSearchBean(har));
		}
		resultMap.put("resultList", list);
		
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null,
				"案件管理-办案区管理", "unitId="+this.findCurrentOrganization().getId()+
				",handlingAreaReceiptNum="+harSearchBean.getCode()+
				",askRoomName="+harSearchBean.getRoom()+
				",handlingPolice="+harSearchBean.getPolice()+
				",ifRecord="+harSearchBean.getState()+
				",enterAreaReasons="+harSearchBean.getEnterAreaReasons()+
				",byQuestioningPeopleName="+harSearchBean.getAskPerson()+
				",byQuestioningPeopleIdentifyNum="+harSearchBean.getIdNum()+
				",lawCase="+harSearchBean.getAboutCase()+
				",enterIntoTimeStart="+harSearchBean.getAccessDateStart()+
				",enterIntoTimeEnd="+harSearchBean.getAccessDateEnd());
		return SUCCESS;
	}

	/**
	 * 刻录详情
	 * @return
	 */
	public String findBasicCaseByFormId(){

		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(formId);
		if (har == null) {
			this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+formId);
			return SUCCESS;
		}
		RecordBean bean=handlingAreaReceiptToHarSearchBean(har);
		DictionaryItem di = dictionaryItemService.findById(har.getBasicCase().getSex());
		if (di != null) {
			bean.setSex(di.getName());
		} else {
			bean.setSex("");
		}
		List<Attachment> photoLst = attachmentCustomizedService.findByTargetIdAndType(har.getId(), HandlingAreaReceipt.class.getName());
		for(Attachment att : photoLst){
			InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
			try {
				byte[] buffer = new byte[is.available()];
				is.read(buffer, 0, is.available());
				bean.setPhoto(new String (Base64.encodeBase64(buffer, false)));
				bean.setPhotoId(att.getId());
			} catch (Exception e) {
				log.debug("照片转换失败");
			}
		}
		resultMap.put("result", bean);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "影音刻录", "id="+formId);
		return SUCCESS;
	
	}
	/**
	 * 查询刻录详情
	 * @return
	 */
	public String findRecordingByFormId(){
//		List<BurnRecordBean> listBean= burnRecordService.queryBurnRecord(formCode,changeTime(new Date(inTime)),changeTime(new Date()));
		List<BurnRecordBean> listBean;
		try {
			listBean = burnRecordService.queryBurnRecord(formCode,null,changeTime(new Date()));
			resultMap.put("result", listBean);
		} catch (DahuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 将Long类型的时间格式化为指定的时间
	 * @param time
	 * @return
	 */
	private String changeTime(Date time){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String str= sdf.format(time);
		 return str;
	}
	/**
	 * 刻录转换bean
	 * @param har
	 * @return
	 */
	private RecordBean handlingAreaReceiptToHarSearchBean(HandlingAreaReceipt har) {
		if(har==null){
			return null;
		}
		RecordBean bean=new RecordBean();
		bean.setId(har.getId());
		if(har.getBasicCase()==null){
			return bean;
		}
		bean.setPoliceName(har.getBasicCase().getHandlingPolice());
		bean.setFormCode(har.getBasicCase().getHandlingAreaReceiptNum());
		bean.setAskPerson(har.getBasicCase().getByQuestioningPeopleName());
		bean.setAskPersonID(har.getBasicCase().getByQuestioningPeopleIdentifyNum());
		bean.setCaseName(har.getBasicCase().getLawCase());
		bean.setIfRecord(har.getRecordStatus());
		if(har.getBasicCase().getEnterIntoTime()!=null){
			bean.setInTime(har.getBasicCase().getEnterIntoTime().getTime());
		}
		bean.setFormCode(har.getBasicCase().getHandlingAreaReceiptNum());
		return bean;
	}

	/**
	 * 通过字典项id查字典项名称
	 * @param dictionaryItemId 字典项id
	 * @return 字典项名称
	 */
	@SuppressWarnings("unused")
	private String findDictionaryItemNameById(String dictionaryItemId){
		if (!dictionaryItemId.isEmpty()) {
			DictionaryItem item = dictionaryItemService.findById(dictionaryItemId);
			if (item != null) {
				return item.getName();
			}
		}
		return "";
	}
	
	/**
	 * 查询所有的询问室房间
	 * 
	 * @return
	 */
	public String findRoomList() {
		List<ActivityRoom> arLst = askRoomService.findAllAskRooms();
		List<OptionItemBean> roomLst=new ArrayList<OptionItemBean>();
		for (ActivityRoom ar : arLst) {
			OptionItemBean bean = new OptionItemBean();
			bean.setId(ar.getId());
			bean.setName(ar.getName());
			roomLst.add(bean);
		}
		resultMap.put("roomLst", roomLst);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+this.findCurrentOrganization().getId());
		return SUCCESS;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public HarSearchBean getHarSearchBean() {
		return harSearchBean;
	}

	public void setHarSearchBean(HarSearchBean harSearchBean) {
		this.harSearchBean = harSearchBean;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public Long getInTime() {
		return inTime;
	}

	public void setInTime(Long inTime) {
		this.inTime = inTime;
	}

}
