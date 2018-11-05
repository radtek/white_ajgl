package com.taiji.pubsec.ajqlc.sawp.action;

import java.io.InputStream;
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

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.bean.HarSearchBean;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 案件查询Action
 * 
 * @author WangLei
 *
 */
@Controller("selectmakingSearchAction")
@Scope("prototype")
public class SelectmakingSearchAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SelectmakingSearchAction.class);
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	@Resource	//问询室分配服务接口
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	
	private String  MakingCode;// 使用单编号
	
	private String caseCode;// 案件编号
	
	private String suspectName;// 嫌疑人姓名
	
	private String suspectNum;// 身份证号
	
	private Long startTime;// 开始时间
	
	private Long endTime;// 结束时间
	
	private List<HarSearchBean> harSearchBeanlst = new ArrayList<HarSearchBean>();
	
	/**
	 * 根据自定义条件查询案件（分页）
	 * @return
	 */
	public String findMakingFromByPage(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("handlingAreaReceiptNum", MakingCode);
		map.put("lawCase", caseCode);
		map.put("byQuestioningPeopleName", suspectName);
		map.put("byQuestioningPeopleIdentifyNum", suspectNum);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
		try {
			if(null != startTime){
				map.put("enterIntoTimeStart", sdf.parse(sdf.format(new Date(startTime))));
			}else{
				map.put("enterIntoTimeStart", "");
			}
			if(null != endTime){
				map.put("enterIntoTimeEnd", sdf.parse(sdf.format(new Date(endTime))));
			}else{
				map.put("enterIntoTimeEnd", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		Pager<HandlingAreaReceipt> p = handlingAreaReceiptService.findHandlingAreaReceiptByKey(map, this.getStart() / this.getLength(), this.getLength());
		this.setTotalNum(p.getTotalNum());
		for (HandlingAreaReceipt har : p.getPageList()) {
			harSearchBeanlst.add(handlingAreaReceiptToHarSearchBean(har));
		}
		return SUCCESS;
	}
	
	private HarSearchBean handlingAreaReceiptToHarSearchBean(HandlingAreaReceipt har) {
		if (har == null) {
			return null;
		}
		HarSearchBean bean = new HarSearchBean();
		bean.setId(har.getId());
		bean.setState(har.getStatus());
		List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(har.getId(), HandlingAreaReceipt.class.getName());
		for(Attachment att : attLst){
			if(att.getAttachmentMeta().getName().contains(".png")){
				InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
				try {
					byte[] buffer = new byte[is.available()];
					is.read(buffer, 0, is.available());
					bean.setPhoto(new String (Base64.encodeBase64(buffer, false)));
				} catch (Exception e) {
					log.error("照片转换失败");
				}
			}
		}
		BasicCase bc = har.getBasicCase();
		if (bc != null) {
			bean.setCode(bc.getHandlingAreaReceiptNum());
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			try {
				bean.setAccessDateStr(sdf.format(bc.getEnterIntoTime()));
			} catch (Exception e) {
				bean.setAccessDateStr("");
				LOGGER.error(e.getMessage(), e);
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
			if(bc.getLawCase() != null){
				CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(bc.getLawCase());
				if(cbc != null){
					bean.setAboutCaseName(cbc.getCaseName());
					bean.setAboutCase(bc.getLawCase());
				}else{
					bean.setAboutCase("");
					bean.setAboutCaseName(bc.getLawCase());
				}
			}else{
				bean.setAboutCaseName("");
				bean.setAboutCase("");
			}
			bean.setAskPerson(bc.getByQuestioningPeopleName());
			bean.setIdNum((bc.getByQuestioningPeopleIdentifyNum() == null)? "": bc.getByQuestioningPeopleIdentifyNum());
		}
		List<AskRoomAllocationRecord> askRoom = askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId(har.getId());
		if(askRoom.isEmpty()){
			return bean;
		}
		AskRoomAllocationRecord aar = askRoom.get(askRoom.size()-1);
		if(aar.getLeaveTime() == null){
			bean.setRoomName(aar.getActivityRoom().getName());
		}
		return bean;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getMakingCode() {
		return MakingCode;
	}

	public void setMakingCode(String makingCode) {
		MakingCode = makingCode;
	}

	public String getSuspectNum() {
		return suspectNum;
	}

	public void setSuspectNum(String suspectNum) {
		this.suspectNum = suspectNum;
	}

	public List<HarSearchBean> getHarSearchBeanlst() {
		return harSearchBeanlst;
	}

	public void setHarSearchBeanlst(List<HarSearchBean> harSearchBeanlst) {
		this.harSearchBeanlst = harSearchBeanlst;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
}
