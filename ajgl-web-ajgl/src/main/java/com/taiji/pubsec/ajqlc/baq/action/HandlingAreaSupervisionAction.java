package com.taiji.pubsec.ajqlc.baq.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.bean.ActivityRoomBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.AskRoomAllocationBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.AskRoomIllegalRecordBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.AskRoomUseRecordQueryBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.BasicCaseBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.QueryAllAskRoomBean;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomIllegalRecord;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.IllegalType;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomIllegalRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IIllegalTypeService;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.base64.Base64CryptFmtUtil;
import com.taiji.pubsec.common.tool.spring.BeanCopierFmtUtil;
import com.taiji.pubsec.common.tool.util.FileFmtUtils;

/**
 * 办案区督查
 * 
 * @author XIEHF
 *
 */
@Controller("handlingAreaSupervision")
@Scope("prototype")
public class HandlingAreaSupervisionAction extends ReturnMessageAction {
	
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HandlingAreaSupervisionAction.class);
	
	@Resource
	private IIllegalTypeService illegalTypeService;
	
	@Resource
	private IUnitService unitService;// 单位接口	
	@Resource
	private IAskRoomService askRoomService;// 询问室接口	
	@Resource
	private IDictionaryItemService dictionaryItemService; //数据字典接口	
	@Resource
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;// 询问室分配记录接口	
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	@Resource
	private IOperateRecordService operateRecordService;
	@Resource
	private IAskRoomIllegalRecordService askRoomIllegalRecordService;
	
	
    private AskRoomUseRecordQueryBean askRoomUseRecordQueryBean = new AskRoomUseRecordQueryBean();	//询问室使用记录bean
	
	private List<AskRoomUseRecordQueryBean> askRoomUseRecordQueryBeanList = new ArrayList<AskRoomUseRecordQueryBean>();	//询问室使用记录bean list
	
	private String askRoomId;	//问询室id
	
	private List<QueryAllAskRoomBean> queryAllAskRoomBeanList;
	
	private List<AskRoomIllegalRecordBean> askRoomIllegalRecordBeanList;
	
	private AskRoomAllocationRecord  askRoomAllocation;
	
	private BasicCaseBean basicCaseBean;
	
	private ActivityRoomBean activityRoomBean;
	private List<ActivityRoomBean> activityRoomBeanList = new ArrayList<ActivityRoomBean>();;
	private List<Unit> unitLeftList = new ArrayList<Unit>(); //带有询问室或其他房间的单位。
	
	private AskRoomIllegalRecordBean askRoomIllegalRecordBean;
	private String bz;
	private String attachmentId;	
	private String startTime;
	private String endTime;
	private String commitPeople;
	private String birthDay;
	private String time;
	private String pageFlag;
	private String tabHaveFlag;
	private Integer pageNum;
	private String orderCondition;
	private String id;
	private FileObjectBean b = new FileObjectBean();
	
	
	/**
	 * 查询所有问询室及其他办案区其他房间(包含所有状态)
	 * 
	 * @return	SUCCESS
	 */
	public String queryAllRooms(){
		queryAllAskRoomBeanList = new ArrayList<QueryAllAskRoomBean>();
		List<Unit> unitList = unitService.findSubUnitsByUnitId(this.findCurrentOrganization().getId());
		for(Unit u : unitList){
			List<ActivityRoom> activityRoomList = askRoomService.findAskRoomByType(null);
			if (null != activityRoomList && activityRoomList.size() > 0) {
				unitLeftList.add(u);
				QueryAllAskRoomBean qarb = new QueryAllAskRoomBean(); 
				List<ActivityRoomBean> activityRoomBeanList = new ArrayList<ActivityRoomBean>();
				qarb.setUnitName(u.getName());
				for(ActivityRoom ar : activityRoomList){
					ActivityRoomBean activityRoomBean = new ActivityRoomBean();
					activityRoomBean.setId(ar.getId());
					activityRoomBean.setName(ar.getName());
					activityRoomBean.setStatus(ar.getStatus());
					activityRoomBean.setType(ar.getType());
					activityRoomBeanList.add(activityRoomBean);
				}
				qarb.setActivityRoomBean(activityRoomBeanList);
				queryAllAskRoomBeanList.add(qarb);
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	/**
	 * 查询所有问询室及其他办案区其他房间(包含所有状态)
	 * 
	 * @return	SUCCESS
	 */
	public String queryRoomsByUnitId(){
		List<ActivityRoom> activityRoomList = askRoomService.findAskRoomByType(null);
		if (null != activityRoomList && activityRoomList.size() > 0) {
			for(ActivityRoom ar : activityRoomList){
				ActivityRoomBean activityRoomBean = new ActivityRoomBean();
				activityRoomBean.setId(ar.getId());
				activityRoomBean.setName(ar.getName());
				activityRoomBean.setStatus(ar.getStatus());
				activityRoomBean.setType(ar.getType());
				activityRoomBeanList.add(activityRoomBean);
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+id);
		return SUCCESS;
	}
	
	/**
	 * 查询有问讯室或其他房间的单位
	 * 
	 * @return	SUCCESS
	 */
	public String queryUnits(){
		List<Unit> unitList = unitService.findSubUnitsByUnitId(this.findCurrentOrganization().getId());
		for(Unit u : unitList){
			List<ActivityRoom> activityRoomList = askRoomService.findAskRoomByType(null);
			if (null != activityRoomList && activityRoomList.size() > 0) {
				unitLeftList.add(u);
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	/**
	 * 询问室使用记录查询
	 * 
	 * @return
	 */
	public String queryAskRoomUseRecord(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("askRoomId", askRoomId);
		map.put("handlingAreaReceiptNum", askRoomUseRecordQueryBean.getHandlingAreaReceiptNum());
		map.put("LawCase", askRoomUseRecordQueryBean.getLawCase());
		map.put("byQuestioningPeopleName", askRoomUseRecordQueryBean.getByQuestioningPeopleName());
		map.put("byQuestioningPeopleIdentifyNum", bz);
		map.put("unit", askRoomUseRecordQueryBean.getUnitId());
		map.put("handlingPolice", askRoomUseRecordQueryBean.getHandlingPolice());
		map.put("orderCondition", orderCondition);
		if(!StringUtils.isBlank(askRoomUseRecordQueryBean.getStartAllocationTime())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				map.put("allocationStartTime",sdf.parse(askRoomUseRecordQueryBean.getStartAllocationTime()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		if(!StringUtils.isBlank(askRoomUseRecordQueryBean.getEndAllocationTime())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				map.put("allocationEndTime",sdf.parse(askRoomUseRecordQueryBean.getEndAllocationTime()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		Pager<AskRoomAllocationRecord> pager = askRoomAllocationRecordService.findAskRoomAllocationRecordByKeyForPage(map,this.getStart() / this.getLength(), this.getLength());
		if (pager == null) {
			return SUCCESS;
		}
		pageNum = pager.getTotalNum();
		List<AskRoomAllocationRecord> list = pager.getPageList();
		assembleAskRoomUseRecordQueryBean(list);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null,
				"案件管理-办案区管理", "handlingAreaReceiptNum="+askRoomUseRecordQueryBean.getHandlingAreaReceiptNum()+
				",byQuestioningPeopleName="+askRoomUseRecordQueryBean.getByQuestioningPeopleName()+
				",byQuestioningPeopleIdentifyNum="+bz+
				",unit="+askRoomUseRecordQueryBean.getUnitId()+
				",handlingPolice="+askRoomUseRecordQueryBean.getHandlingPolice()+
				",allocationStartTime"+askRoomUseRecordQueryBean.getStartAllocationTime()+
				",allocationEndTime="+askRoomUseRecordQueryBean.getEndAllocationTime());
		return SUCCESS;
	}
	/**
	 * 异常记录查询
	 * 
	 * @return
	 */
	public String queryRoomUseAbnormalList(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("commitPeople", commitPeople);
		map.put("roomName", askRoomUseRecordQueryBean.getActivityRoomName());
		map.put("handlingAreaReceiptNum", askRoomUseRecordQueryBean.getHandlingAreaReceiptNum());
		map.put("unitId", askRoomUseRecordQueryBean.getUnitId());
		map.put("roomId", askRoomId);
		Pager<AskRoomIllegalRecord> pager = askRoomIllegalRecordService.findAskRoomUseAbnormalRecordByKey(map,this.getStart() / this.getLength(), this.getLength());
		if (pager == null) {
			return SUCCESS;
		}
		pageNum = pager.getTotalNum();
		List<AskRoomIllegalRecord> list = pager.getPageList();
		assembleAskRoomIllegalRecordBean(list);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null,
				"案件管理-办案区管理", ",startTime="+startTime+",endTime="+endTime+",commitPeople="+commitPeople+
				",roomName="+askRoomUseRecordQueryBean.getActivityRoomName()+
				",handlingAreaReceiptNum="+askRoomUseRecordQueryBean.getHandlingAreaReceiptNum()+
				",unitId="+askRoomUseRecordQueryBean.getUnitId()+
				",roomId="+askRoomId);
		return SUCCESS;
	}
	
	/**
	 * 查询附件
	 * 
	 * @return
	 */
	public String queryAttach(){
		Attachment att = attachmentCustomizedService.findById(attachmentId);
		try {
			b.setBase64Str(Base64CryptFmtUtil.encode(FileFmtUtils.readByByteBuffer(att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream()), false));   
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+attachmentId);
		return SUCCESS;
	}
	
	
	/**
	 * 私有方法  装配询问室使用记录bean
	 * @param askRoomUseRecord 询问室使用记录
	 * @return
	 */
	private void assembleAskRoomUseRecordQueryBean(List<AskRoomAllocationRecord> askRoomAllocationRecord){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(AskRoomAllocationRecord askRoom : askRoomAllocationRecord){
			AskRoomUseRecordQueryBean  askRoomRecord =  new AskRoomUseRecordQueryBean();
			askRoomRecord.setActivityRoomName(askRoom.getActivityRoom().getName());
			askRoomRecord.setAllocationPeople(askRoom.getAllocationPeople());
			askRoomRecord.setByQuestioningPeopleIdentifyNum(askRoom.getHandlingAreaReceipt().getBasicCase().getByQuestioningPeopleIdentifyNum());
			askRoomRecord.setByQuestioningPeopleName(askRoom.getHandlingAreaReceipt().getBasicCase().getByQuestioningPeopleName());
			askRoomRecord.setHandlingAreaReceiptNum(askRoom.getHandlingAreaReceipt().getBasicCase().getHandlingAreaReceiptNum());
			askRoomRecord.setHandlingPolice(askRoom.getHandlingAreaReceipt().getBasicCase().getHandlingPolice());
			askRoomRecord.setStartAllocationTime(sdf.format(askRoom.getAllocationTime()));
			askRoomRecord.setUnitName(askRoom.getActivityRoom().getUnit().getName());
			askRoomRecord.setLawCase(askRoom.getHandlingAreaReceipt().getBasicCase().getLawCase());
			askRoomRecord.setLeaveTime(null == askRoom.getLeaveTime() ? "" : sdf.format(askRoom.getLeaveTime()));
			List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(askRoom.getHandlingAreaReceipt().getBasicCase().getId(),BasicCase.class.getName());
			if (attLst.isEmpty()) {
				askRoomRecord.setUnitId("");                     //借用这个字段保存附件ID
			} else {				
				askRoomRecord.setUnitId(attLst.get(0).getId());  //借用这个字段保存附件ID
			}
			
			askRoomUseRecordQueryBeanList.add(askRoomRecord);
		}
	}
	
	/**
	 * 私有方法  装配异常记录bean
	 * @param askRoomUseRecord 异常记录
	 * @return  askRoomIllegalRecordBeanList 异常记录Bean List
	 */
	private List<AskRoomIllegalRecordBean> assembleAskRoomIllegalRecordBean(List<AskRoomIllegalRecord> askRoomIllegalRecord){
		askRoomIllegalRecordBeanList = new ArrayList<AskRoomIllegalRecordBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(AskRoomIllegalRecord entity : askRoomIllegalRecord){
			AskRoomIllegalRecordBean  illegalRecord =  new AskRoomIllegalRecordBean();
			illegalRecord.setIllegalCause(entity.getIllegalCause());
			illegalRecord.setIllegalCauseStr(getIllegalInfo(entity.getIllegalCause()));
			illegalRecord.setIllegalTime(sdf.format(entity.getIllegalTime()));
			illegalRecord.setCommitPeople(entity.getCommitPeople());
			illegalRecord.setHandlingAreaReceiptNum(entity.getAskRoomAllocationRecord() == null ? "" : entity.getAskRoomAllocationRecord().getHandlingAreaReceipt().getBasicCase().getHandlingAreaReceiptNum());
			illegalRecord.setBz(entity.getBz());
			
			AskRoomAllocationBean askRoomAllocationBean = new AskRoomAllocationBean();
			ActivityRoomBean activityRoomBean = new ActivityRoomBean();
			ActivityRoom room = entity.getActivityRoom();
			activityRoomBean.setId(room.getId());
			activityRoomBean.setName(room.getName());
			askRoomAllocationBean.setAskRoom(activityRoomBean);
			illegalRecord.setAskRoomAllocationBean(askRoomAllocationBean);
			illegalRecord.setActivityRoomBean(activityRoomBean);
			askRoomIllegalRecordBeanList.add(illegalRecord);
		}
		return askRoomIllegalRecordBeanList;
	}
	
	private String getIllegalInfo(String id) {
		IllegalType entity = illegalTypeService.findIllegalTypeById(id);
		return entity == null ? "" : entity.getName();
	}
	
	/**
	 * 询问室详情
	 * 
	 * @return
	 */
	public String showAskRoomInfoDetail(){
		ActivityRoom askRoom = askRoomService.findAskRoomById(askRoomId);
		String zt = askRoom.getStatus();
		activityRoomBean = new ActivityRoomBean();
		activityRoomBean.setName(askRoom.getName());
		activityRoomBean.setStatus(zt);
		activityRoomBean.setStatusName(findDictionaryItemNameById(zt));
		tabHaveFlag = "one";
		if (zt.equals(Constant.SYZT_SYZ)) { //询问室使用中，则需要获取当前在用信息
			askRoomAllocation = askRoomAllocationRecordService.getLastAskRoomAllocation(askRoomId);
			if (null != askRoomAllocation ) {
				tabHaveFlag = "have";
				basicCaseBean = new BasicCaseBean();
				try {
					BeanCopierFmtUtil.copyBean(askRoomAllocation.getHandlingAreaReceipt().getBasicCase(), basicCaseBean, null);
				} catch (Exception e) {
					LOGGER.debug(e.getMessage(), e);
				}
				if (basicCaseBean.getByQuestioningPeopleIdentifyType() != null) {
					basicCaseBean.setByQuestioningPeopleIdentifyTypeStr(findDictionaryItemNameById(basicCaseBean.getByQuestioningPeopleIdentifyType()));
				} else {
					basicCaseBean.setByQuestioningPeopleIdentifyTypeStr("");
				}
				basicCaseBean.setSexStr(findDictionaryItemNameById(basicCaseBean.getSex()));
				
				if (Constant.AY_QT.equals(basicCaseBean.getCauseOfLawCase())) {
					basicCaseBean.setCauseOfLawCaseStr(basicCaseBean.getOtherCauseOfLawCase());
				} else if (basicCaseBean.getCauseOfLawCase() != null) {
					basicCaseBean.setCauseOfLawCaseStr(dictionaryItemService.findById(basicCaseBean.getCauseOfLawCase()).getName());
				} else {
					basicCaseBean.setCauseOfLawCaseStr("");
				}
				basicCaseBean.setEnterAreaReasons(basicCaseBean.getEnterAreaReasons());
				
				List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(basicCaseBean.getId(),
						BasicCase.class.getName());
				if (attLst.isEmpty()) {
					basicCaseBean.setUploadNew(false); //借用这个字段标示是否存在附件。
				} else {				
					FileObjectBean b = new FileObjectBean();
					b.setId(attLst.get(0).getId());
					b.setName(attLst.get(0).getName());
					try {
						b.setBase64Str(Base64CryptFmtUtil.encode(FileFmtUtils.readByByteBuffer(attLst.get(0).getAttachmentMeta().getAttachmentCopys().get(0).getInputStream()), false));
					} catch (UnsupportedEncodingException e) {
						LOGGER.error(e.getMessage(), e);
					}
					basicCaseBean.setAttach(b);
					basicCaseBean.setUploadNew(true); //借用这个字段标示是否存在附件。
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf_time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date birth = askRoomAllocation.getHandlingAreaReceipt().getBasicCase().getBirthday();
				birthDay = (null == birth ? "" : sdf.format(birth));
				time = sdf_time.format(askRoomAllocation.getAllocationTime());
			}
		}
		return pageFlag;
	}
	
	
	/**
	 * 保存违规信息
	 * 
	 * @return
	 */
	public String saveorUpdateIllegalRecord() {
		AskRoomAllocationRecord askRoomAllocation = new AskRoomAllocationRecord();
		AskRoomIllegalRecord askRoomIllegalRecord = new AskRoomIllegalRecord();
		askRoomAllocation = askRoomAllocationRecordService.findAskRoomAllocationRecordById(askRoomIllegalRecordBean.getAskRoomAllocationBean().getId());
		ActivityRoom room = askRoomService.findAskRoomById(askRoomIllegalRecordBean.getActivityRoomBean().getId());
		
		
		askRoomIllegalRecord.setIllegalCause(askRoomIllegalRecordBean.getIllegalCause());
		askRoomIllegalRecord.setAskRoomAllocationRecord(askRoomAllocation);
		askRoomIllegalRecord.setActivityRoom(room);
		askRoomIllegalRecord.setIllegalTime(new Date());
		askRoomIllegalRecord.setCommitPeople(this.findCurrentPerson().getName());
		askRoomIllegalRecord.setBz(askRoomIllegalRecordBean.getBz());
		askRoomIllegalRecordService.saveAskRoomIllegalRecord(askRoomIllegalRecord);
		
		OperateRecord operateRecord = new OperateRecord();
		operateRecord.setModelObject(AskRoomIllegalRecord.class.getName());
		operateRecord.setModelObjectId(askRoomIllegalRecord.getId());
		operateRecord.setNoteText(askRoomIllegalRecordBean.getBz());
		operateRecord.setNoteType("1");
		operateRecord.setOperateContent("维护了问讯（询）违规记录");
		operateRecord.setOperateTime(new Date());
		operateRecord.setOperator(this.findCurrentPerson().getName());
		operateRecordService.saveOperateRecord(operateRecord);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.setFlag("true");
		
		return SUCCESS;
	}
	
	/**
	 * 通过字典项id查字典项名称
	 * @param dictionaryItemId 字典项id
	 * @return 字典项名称
	 */
	private String findDictionaryItemNameById(String dictionaryItemId){
		if (!dictionaryItemId.isEmpty()) {
			DictionaryItem item = dictionaryItemService.findById(dictionaryItemId);
			if (item != null) {
				return item.getName();
			}
		}
		return "";
	}

	public List<QueryAllAskRoomBean> getQueryAllAskRoomBeanList() {
		return queryAllAskRoomBeanList;
	}


	public void setQueryAllAskRoomBeanList(List<QueryAllAskRoomBean> queryAllAskRoomBeanList) {
		this.queryAllAskRoomBeanList = queryAllAskRoomBeanList;
	}


	public AskRoomUseRecordQueryBean getAskRoomUseRecordQueryBean() {
		return askRoomUseRecordQueryBean;
	}

	public void setAskRoomUseRecordQueryBean(AskRoomUseRecordQueryBean askRoomUseRecordQueryBean) {
		this.askRoomUseRecordQueryBean = askRoomUseRecordQueryBean;
	}

	public List<AskRoomUseRecordQueryBean> getAskRoomUseRecordQueryBeanList() {
		return askRoomUseRecordQueryBeanList;
	}

	public void setAskRoomUseRecordQueryBeanList(List<AskRoomUseRecordQueryBean> askRoomUseRecordQueryBeanList) {
		this.askRoomUseRecordQueryBeanList = askRoomUseRecordQueryBeanList;
	}

	public String getAskRoomId() {
		return askRoomId;
	}

	public void setAskRoomId(String askRoomId) {
		this.askRoomId = askRoomId;
	}

	public AskRoomAllocationRecord getAskRoomAllocation() {
		return askRoomAllocation;
	}

	public void setAskRoomAllocation(AskRoomAllocationRecord askRoomAllocation) {
		this.askRoomAllocation = askRoomAllocation;
	}

	public BasicCaseBean getBasicCaseBean() {
		return basicCaseBean;
	}

	public void setBasicCaseBean(BasicCaseBean basicCaseBean) {
		this.basicCaseBean = basicCaseBean;
	}

	public ActivityRoomBean getActivityRoomBean() {
		return activityRoomBean;
	}

	public void setActivityRoomBean(ActivityRoomBean activityRoomBean) {
		this.activityRoomBean = activityRoomBean;
	}

	public AskRoomIllegalRecordBean getAskRoomIllegalRecordBean() {
		return askRoomIllegalRecordBean;
	}

	public void setAskRoomIllegalRecordBean(AskRoomIllegalRecordBean askRoomIllegalRecordBean) {
		this.askRoomIllegalRecordBean = askRoomIllegalRecordBean;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCommitPeople() {
		return commitPeople;
	}

	public void setCommitPeople(String commitPeople) {
		this.commitPeople = commitPeople;
	}

	public List<AskRoomIllegalRecordBean> getAskRoomIllegalRecordBeanList() {
		return askRoomIllegalRecordBeanList;
	}

	public void setAskRoomIllegalRecordBeanList(List<AskRoomIllegalRecordBean> askRoomIllegalRecordBeanList) {
		this.askRoomIllegalRecordBeanList = askRoomIllegalRecordBeanList;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getTabHaveFlag() {
		return tabHaveFlag;
	}

	public void setTabHaveFlag(String tabHaveFlag) {
		this.tabHaveFlag = tabHaveFlag;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public FileObjectBean getB() {
		return b;
	}

	public void setB(FileObjectBean b) {
		this.b = b;
	}

	public String getOrderCondition() {
		return orderCondition;
	}

	public void setOrderCondition(String orderCondition) {
		this.orderCondition = orderCondition;
	}

	public List<ActivityRoomBean> getActivityRoomBeanList() {
		return activityRoomBeanList;
	}

	public void setActivityRoomBeanList(List<ActivityRoomBean> activityRoomBeanList) {
		this.activityRoomBeanList = activityRoomBeanList;
	}

	public List<Unit> getUnitLeftList() {
		return unitLeftList;
	}

	public void setUnitLeftList(List<Unit> unitLeftList) {
		this.unitLeftList = unitLeftList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
