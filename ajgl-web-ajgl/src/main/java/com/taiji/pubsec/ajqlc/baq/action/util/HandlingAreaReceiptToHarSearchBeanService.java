package com.taiji.pubsec.ajqlc.baq.action.util;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taiji.pubsec.ajqlc.baq.action.HandlingAreaReceiptAction;
import com.taiji.pubsec.ajqlc.baq.action.bean.HarSearchBean;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;

@Service("handlingAreaReceiptToHarSearchBeanService")
public class HandlingAreaReceiptToHarSearchBeanService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HandlingAreaReceiptAction.class);
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource	//问询室分配服务接口
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;
	
	public HarSearchBean handlingAreaReceiptToHarSearchBean(HandlingAreaReceipt har) {
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
			bean.setCauseOfLawCase(bc.getCauseOfLawCase());
			if (Constant.BAQYY_QT.equals(bc.getEnterAreaReasons())) {
				//bean.setEnterAreaReasonsName(bc.getOtherEnterAreaReasons());
			} else if (bc.getEnterAreaReasons() != null) {
				//bean.setEnterAreaReasonsName(dictionaryItemService.findById(bc.getEnterAreaReasons()).getName());
			} else {
				//bean.setEnterAreaReasonsName("");
			}
			bean.setAboutCaseName((bc.getLawCase() == null)? "": bc.getLawCase());
			bean.setAskPerson(bc.getByQuestioningPeopleName());
			bean.setIdNum((bc.getByQuestioningPeopleIdentifyNum() == null)? "": bc.getByQuestioningPeopleIdentifyNum());
		}
		List<AskRoomAllocationRecord> askRoom = askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId(har.getId());
		bean.setRoomName(!askRoom.isEmpty()?askRoom.get(0).getActivityRoom().getName():"");
		return bean;
	}
}
