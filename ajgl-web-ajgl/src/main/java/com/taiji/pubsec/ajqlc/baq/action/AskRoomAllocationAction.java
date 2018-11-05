package com.taiji.pubsec.ajqlc.baq.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.action.bean.ActivityRoomBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.AskRoomAllocationRecordQueryBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.HarSearchBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.QueryAllAskRoomBean;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IActivityRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;

/**
 * 问询室分配
 * 
 * @author sunjd
 *
 */
@Controller("askRoomAllocation")
@Scope("prototype")
public class AskRoomAllocationAction extends ReturnMessageAction {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HandlingAreaReceiptAction.class);
	
	@Resource
	private IUnitService unitService;// 单位接口
	
	@Resource
	private IAskRoomService askRoomService;// 询问室接口
	
	@Resource	//问询室分配服务接口
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;		//使用单服务接口
	
	@Resource
	private IActivityRecordService activityRecordService;
	
	@Resource
	private IDictionaryItemService dictionaryItemService;
	
	private AskRoomAllocationRecordQueryBean askRoomAllocationRecordQueryBean = new AskRoomAllocationRecordQueryBean();	//询问室分配bean
	
	private List<AskRoomAllocationRecordQueryBean> askRoomAllocationRecordQueryBeanList = new ArrayList<AskRoomAllocationRecordQueryBean>();	//询问室分配记录bean list
	
	private String askRoomId;	//问询室id
	
	private String receiptId ; //使用单id
	
	private List<QueryAllAskRoomBean> queryAllAskRoomBeanList;
	
	private HarSearchBean harSearchBean = new HarSearchBean();
	
	private List<HarSearchBean> harSearchBeanlst = new ArrayList<HarSearchBean>();
	
	private HarSearchBean handlingAreaReceiptToHarSearchBean(HandlingAreaReceipt har) {
		if (har == null) {
			return null;
		}
		HarSearchBean bean = new HarSearchBean();
		bean.setId(har.getId());
		bean.setState(har.getStatus());
		BasicCase bc = har.getBasicCase();
		if (bc != null) {
			bean.setCode(bc.getHandlingAreaReceiptNum());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				bean.setAccessDateStr(sdf.format(bc.getEnterIntoTime()));
			} catch (Exception e) {
				bean.setAccessDateStr("");
				LOGGER.debug(e.getMessage(), e);
			}
			bean.setPolice(bc.getHandlingPolice());
			Person p = bc.getModifyPeople();
			if(p != null){
				bean.setProposer(p.getName());
			}
			if (Constant.AY_QT.equals(bc.getCauseOfLawCase())) {
				bean.setCauseOfLawCaseName(bc.getOtherCauseOfLawCase());
			} else if (bc.getCauseOfLawCase() != null) {
				bean.setCauseOfLawCaseName(dictionaryItemService.findById(bc.getCauseOfLawCase()).getName());
			} else {
				bean.setCauseOfLawCaseName("");
			}
			bean.setEnterAreaReasons(bc.getEnterAreaReasons());
			bean.setAboutCaseName((bc.getLawCase() == null)? "": bc.getLawCase());
			bean.setAskPerson(bc.getByQuestioningPeopleName());
			bean.setIdNum((bc.getByQuestioningPeopleIdentifyNum() == null)? "": bc.getByQuestioningPeopleIdentifyNum());
		}
		List<AskRoomAllocationRecord> askRoom = askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId(har.getId());
		bean.setRoomName(!askRoom.isEmpty()?askRoom.get(0).getActivityRoom().getName():"");
		return bean;
	}
	
	/**
	 * 查询所有问询室(包含所有状态)
	 * 
	 * @return	SUCCESS
	 */
	public String queryAllAskRoom(){
		queryAllAskRoomBeanList = new ArrayList<QueryAllAskRoomBean>();
		List<ActivityRoom> activityRoomList = askRoomService.findAskRoomByType(Constant.FJLX_WXS);
		QueryAllAskRoomBean qarb = new QueryAllAskRoomBean(); 
		List<ActivityRoomBean> activityRoomBeanList = new ArrayList<ActivityRoomBean>();
		qarb.setUnitName(this.findCurrentOrganization().getName());
		for(ActivityRoom ar : activityRoomList){
			ActivityRoomBean activityRoomBean = new ActivityRoomBean();
			activityRoomBean.setId(ar.getId());
			activityRoomBean.setName(ar.getName());
			activityRoomBean.setStatus(ar.getStatus());
			activityRoomBeanList.add(activityRoomBean);
		}
		qarb.setActivityRoomBean(activityRoomBeanList);
		queryAllAskRoomBeanList.add(qarb);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+this.findCurrentOrganization().getId()+",type="+Constant.FJLX_WXS);
		return SUCCESS;
	}
	
	/**
	 * 查询可分配使用单
	 * @return
	 */
	public String searchSelectedHandlingAreaReceiptByPage(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("unitId", this.findCurrentOrganization().getId());
		map.put("handlingAreaReceiptNum", harSearchBean.getCode());
		map.put("handlingPolice", harSearchBean.getPolice());
		map.put("enterAreaReasons", harSearchBean.getEnterAreaReasons());
		map.put("byQuestioningPeopleName", harSearchBean.getAskPerson());
		map.put("byQuestioningPeopleIdentifyNum", harSearchBean.getIdNum());
		map.put("lawCase", harSearchBean.getAboutCase());
		if(!StringUtils.isBlank(harSearchBean.getAccessDateStart())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				map.put("enterIntoTimeStart",sdf.parse(harSearchBean.getAccessDateStart()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		if(!StringUtils.isBlank(harSearchBean.getAccessDateEnd())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				map.put("enterIntoTimeEnd",sdf.parse(harSearchBean.getAccessDateEnd()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		map.put("modifyPeopleName", harSearchBean.getModifyPeopleName());
		List<HandlingAreaReceipt> harlst = handlingAreaReceiptService.findHandlingAreaReceiptByConditions(map);
		for (HandlingAreaReceipt har : harlst) {
			harSearchBeanlst.add(handlingAreaReceiptToHarSearchBean(har));
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+
				this.findCurrentOrganization().getId()+",handlingAreaReceiptNum="+harSearchBean.getCode()+
				",handlingPolice="+harSearchBean.getPolice()+",byQuestioningPeopleName="+harSearchBean.getAskPerson()+
				",byQuestioningPeopleIdentifyNum="+harSearchBean.getIdNum()+",lawCase="+harSearchBean.getAboutCase()+
				",enterIntoTimeStart="+harSearchBean.getAccessDateStart()+",enterIntoTimeStartEnd="+harSearchBean.getAccessDateEnd());
		return SUCCESS;
	}
	
	/**
	 * 查询问询室分配记录
	 * 
	 * @return SUCCESS
	 */
	public String queryAskRoomAllocationRecord(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("askRoomName", askRoomAllocationRecordQueryBean.getActivityRoomName());
		map.put("handlingAreaReceiptNum", askRoomAllocationRecordQueryBean.getHandlingAreaReceiptNum());
		map.put("enterAreaReasons", askRoomAllocationRecordQueryBean.getEnterAreaReasons());
		map.put("byQuestioningPeopleName", askRoomAllocationRecordQueryBean.getByQuestioningPeopleName());
		map.put("byQuestioningPeopleIdentifyNum", askRoomAllocationRecordQueryBean.getByQuestioningPeopleIdentifyNum());
		map.put("allocationStartTime", askRoomAllocationRecordQueryBean.getStartAllocationTime());
		map.put("allocationEndTime", askRoomAllocationRecordQueryBean.getEndAllocationTime());
		if(!StringUtils.isBlank(askRoomAllocationRecordQueryBean.getStartAllocationTime())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				map.put("allocationStartTime",sdf.parse(askRoomAllocationRecordQueryBean.getStartAllocationTime()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		if(!StringUtils.isBlank(askRoomAllocationRecordQueryBean.getEndAllocationTime())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				map.put("allocationEndTime",sdf.parse(askRoomAllocationRecordQueryBean.getEndAllocationTime()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		map.put("orderCondition", askRoomAllocationRecordQueryBean.getOrderCondition());
		Pager<AskRoomAllocationRecord> pager = askRoomAllocationRecordService
				.findAskRoomAllocationRecordByKeyForPage(map, this.getStart() / this.getLength(), this.getLength());
		List<AskRoomAllocationRecord> askRoomAllocationRecord = pager.getPageList();
		assembleAskRoomAllocationRecordQueryBean(askRoomAllocationRecord);
		this.setTotalNum(pager.getTotalNum());
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null,
				"案件管理-办案区管理", "askRoomName="+askRoomAllocationRecordQueryBean.getActivityRoomName()+
				",handlingAreaReceiptNum="+askRoomAllocationRecordQueryBean.getHandlingAreaReceiptNum()+
				",enterAreaReasons="+askRoomAllocationRecordQueryBean.getEnterAreaReasons()+
				",byQuestioningPeopleName="+askRoomAllocationRecordQueryBean.getByQuestioningPeopleName()+
				",byQuestioningPeopleIdentifyNum="+askRoomAllocationRecordQueryBean.getByQuestioningPeopleIdentifyNum()+
				",allocationStartTime="+askRoomAllocationRecordQueryBean.getStartAllocationTime()+
				",allocationEndTime="+askRoomAllocationRecordQueryBean.getEndAllocationTime()+
				",unit="+askRoomAllocationRecordQueryBean.getOrderCondition());
		return SUCCESS;
	}
	
	/**
	 * 私有方法  装配询问室分配记录bean
	 * @param askRoomAllocationRecord 询问室分配记录
	 * @return
	 */
	private void assembleAskRoomAllocationRecordQueryBean(List<AskRoomAllocationRecord> askRoomAllocationRecord){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(AskRoomAllocationRecord askRoom : askRoomAllocationRecord){
			if(askRoom.getHandlingAreaReceipt().getStatus().equals(Constant.SYDZT_QT)){
				AskRoomAllocationRecordQueryBean  askRoomRecord =  new AskRoomAllocationRecordQueryBean();
				askRoomRecord.setActivityRoomName(askRoom.getActivityRoom().getName());
				askRoomRecord.setAllocationPeople(askRoom.getAllocationPeople());
				askRoomRecord.setByQuestioningPeopleIdentifyNum(askRoom.getHandlingAreaReceipt().getBasicCase().getByQuestioningPeopleIdentifyNum());
				askRoomRecord.setHarId(askRoom.getHandlingAreaReceipt().getId());
				askRoomRecord.setByQuestioningPeopleName(askRoom.getHandlingAreaReceipt().getBasicCase().getByQuestioningPeopleName());
				askRoomRecord.setHandlingAreaReceiptNum(askRoom.getHandlingAreaReceipt().getBasicCase().getHandlingAreaReceiptNum());
				askRoomRecord.setHandlingPolice(askRoom.getHandlingAreaReceipt().getBasicCase().getHandlingPolice());
				askRoomRecord.setStartAllocationTime(sdf.format(askRoom.getAllocationTime()));
				askRoomAllocationRecordQueryBeanList.add(askRoomRecord);
			}
		}
	}
	
	/**
	 * 新增分配记录
	 * 
	 * @return SUCCESS
	 * 
	 */
	public String addAskRoomAllocationRecord(){
		try {
			HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(receiptId);
			askRoomAllocationRecordService.saveAskRoomAllocationRecord(askRoomAllocationConversionReceipt(askRoomId,receiptId));
			askRoomService.updateAskRoomStatus(askRoomId, Constant.SYZT_SYZ);
			
			ActivityRecord record1 = new ActivityRecord();
			record1.setDescription(Constant.ACTIVITY_STAGE_DESCRIPTION_XXWGC);
			record1.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
			record1.setStartTime(har.getBasicCase().getEnterIntoTime());
			record1.setEndTime(new Date());
			activityRecordService.save(record1);
			
			ActivityRecord record2 = new ActivityRecord();
			record2.setDescription(Constant.ACTIVITY_STAGE_ALLOT_FPXXWS);
			record2.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
			record2.setStartTime(new Date());
			if(null == record1.getStartTime()){
				record1.setStartTime(new Date());
			}
			if(null == record1.getEndTime()){
				record1.setEndTime(new Date());
			}
			if(null != record1.getEndTime()){
				if(record1.getEndTime().getTime()<record2.getEndTime().getTime()){
					record1.setEndTime(record2.getEndTime());
				}
			}
			record2.setSupRecordId(record1.getId());
			activityRecordService.save(record2);
			
			this.setMsg("分配成功");
		} catch (Exception e) {
			this.setMsg("分配失败！");
			LOGGER.debug("产生异常", e);
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	/**
	 * 私有方法  组装分配记录
	 * @param  roomId	问询室id
	 * @param  id 	使用单id
	 * @return askRoomAllocationRecord 问询室分配记录
	 */
	private AskRoomAllocationRecord askRoomAllocationConversionReceipt(String roomId ,String id){
		ActivityRoom askRoom = askRoomService.findAskRoomById(roomId);
		HandlingAreaReceipt handlingAreaReceipt = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		AskRoomAllocationRecord askRoomAllocationRecord = new AskRoomAllocationRecord();
		if(askRoom !=null && handlingAreaReceipt != null){
			askRoomAllocationRecord.setAllocationPeople(this.findCurrentPerson().getName());
			askRoomAllocationRecord.setAllocationTime(new Date());
			askRoomAllocationRecord.setAskRoom(askRoom);
			askRoomAllocationRecord.setHandlingAreaReceipt(handlingAreaReceipt);
			Set<HandlingAreaReceiptToPoliceInfo> hrtp = handlingAreaReceipt.getHandlingAreaReceiptToPoliceInfoList();
			String policeNum = null ;
			for (HandlingAreaReceiptToPoliceInfo set : hrtp) {
				PoliceInfo pollice = set.getPoliceInfo();
				policeNum = pollice.getPoliceNum() + "," ;
			}
			askRoomAllocationRecord.setPoliceNum(policeNum);
		}
		return askRoomAllocationRecord;
	}

	public AskRoomAllocationRecordQueryBean getAskRoomAllocationRecordQueryBean() {
		return askRoomAllocationRecordQueryBean;
	}

	public void setAskRoomAllocationRecordQueryBean(
			AskRoomAllocationRecordQueryBean askRoomAllocationRecordQueryBean) {
		this.askRoomAllocationRecordQueryBean = askRoomAllocationRecordQueryBean;
	}

	public String getAskRoomId() {
		return askRoomId;
	}

	public void setAskRoomId(String askRoomId) {
		this.askRoomId = askRoomId;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public List<AskRoomAllocationRecordQueryBean> getAskRoomAllocationRecordQueryBeanList() {
		return askRoomAllocationRecordQueryBeanList;
	}

	public void setAskRoomAllocationRecordQueryBeanList(
			List<AskRoomAllocationRecordQueryBean> askRoomAllocationRecordQueryBeanList) {
		this.askRoomAllocationRecordQueryBeanList = askRoomAllocationRecordQueryBeanList;
	}

	public List<QueryAllAskRoomBean> getQueryAllAskRoomBeanList() {
		return queryAllAskRoomBeanList;
	}

	public void setQueryAllAskRoomBeanList(
			List<QueryAllAskRoomBean> queryAllAskRoomBeanList) {
		this.queryAllAskRoomBeanList = queryAllAskRoomBeanList;
	}

	public HarSearchBean getHarSearchBean() {
		return harSearchBean;
	}

	public void setHarSearchBean(HarSearchBean harSearchBean) {
		this.harSearchBean = harSearchBean;
	}

	public List<HarSearchBean> getHarSearchBeanlst() {
		return harSearchBeanlst;
	}

	public void setHarSearchBeanlst(List<HarSearchBean> harSearchBeanlst) {
		this.harSearchBeanlst = harSearchBeanlst;
	}

}
