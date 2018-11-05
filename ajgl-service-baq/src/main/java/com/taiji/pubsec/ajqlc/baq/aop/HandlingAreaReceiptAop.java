package com.taiji.pubsec.ajqlc.baq.aop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taiji.pubsec.ajqlc.baq.acl.Constant;
import com.taiji.pubsec.ajqlc.baq.bean.PoliceInfoBean;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.CollectInfoSituation;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IPoliceInfoService;
import com.taiji.pubsec.ajqlc.baq.service.impl.HandlingAreaReceiptServiceImpl;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBasicDataService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ICaseFormService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IEntranceControlService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ILocationCardService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IWristbandService;
import com.taiji.pubsec.ajqlc.wrapper.szpt.service.InstructionService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

@Service("handlingAreaReceiptAop")
@Aspect
public class HandlingAreaReceiptAop {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String FA_ZHI_DA_DUI_UNIT_BM = "520199180000" ;//在实战里给案管办案区的虚拟人员属于法制大队
	
	public static final String BAQ_UNIT_PERSON_CODE = "999934";
	
	public static final String BAQ_UNIT_QTDW_ID = "d46886d6-5dd1-417a-8f1f-b0f08f9bb7bd"; //当用户在咱们库没有时，将零时登陆的用户绑定到其他单位
	public static final String YP_INSTRUCTION_TYPE_CODE = "0000000011004" ;
	
	public static final String QING_ZHI_ZHONG_XIN_UNIT_ID = "c1bc34ff-d881-4a5c-9fbe-e4e4048ae82c" ;
	public static final String WANG_AN_DA_DUI_UNIT_ID = "585fdd1b-4453-443d-8d28-305368c99728" ;
	
	public static final String FOR_OTHER_TO_SIGN_CODE = "0000000012001" ;

	
	@Resource
	private IWristbandService wristbandService;

	@Resource
	private ILocationCardService locationCardService;

	@Resource
	private ICaseFormService caseFormService;

	@Resource
	private ICriminalCaseService criminalCaseService;

	@Resource
	private IUnitService unitService;

	@Resource
	private IPersonService personService;

	@Resource
	private IEntranceControlService entranceControlService;

	@Resource
	private IBasicDataService basicDataService;

	@Resource
	private ICaseFormService caseFormServiceImpl;

	@Resource
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;

	@Resource
	private InstructionService instructionService;

	@Resource
	private IPoliceInfoService policeInfoService;
	
	// 保存更新使用单aop
	@Pointcut("execution(* com.taiji.pubsec.ajqlc.baq.service.impl.HandlingAreaReceiptServiceImpl.saveHandlingAreaReceiptWithPoliceInfo(..))")
	public void saveHandlingAreaReceiptAopPoint() {

	}

	@Pointcut("execution(* com.taiji.pubsec.ajqlc.baq.service.impl.HandlingAreaReceiptServiceImpl.updateHandlingAreaReceiptWithPoliceInfo(..))")
	public void updateHandlingAreaReceiptAopPoint() {

	}

	@After(value = "saveHandlingAreaReceiptAopPoint()")
	public void afterSaveHandlingAreaReceiptAopPoint(JoinPoint jp) throws DahuaException{
		Object[] args = jp.getArgs();
		HandlingAreaReceipt har = (HandlingAreaReceipt) args[0];
		@SuppressWarnings("unchecked")
		List<PoliceInfoBean> piBeanLst = (List<PoliceInfoBean>) args[1];
		caseFormService.syncAddSuspectInfo(har.getBasicCase()
					.getByQuestioningPeopleIdentifyNum(), har.getBasicCase()
					.getByQuestioningPeopleName(), har.getBasicCase()
					.getHandlingAreaReceiptNum());
			@SuppressWarnings("rawtypes")
			List<Map> polices = new ArrayList<Map>();
			for (PoliceInfoBean info : piBeanLst) {
				if(!info.isRelationWithCard()){
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userUUID", info.getPoliceNum());
				if (info.getIfMainPolice().equals("0000000007002")) {
					map.put("userRole", 1);
				} else {
					map.put("userRole", 2);
				}
				polices.add(map);
				Person p = personService.findPersonByCode(info.getPoliceNum());
				String orgId = "";
				if (p != null && p.getAccount()!=null) {
					orgId = p.getOrganization().getId();
					basicDataService.syncAddPersonInfo(
							p.getAccount().getAccountName(), p.getAccount().getPassword(), orgId,
							info.getPoliceName(), info.getPoliceNum());
				}else{
					basicDataService.syncAddPersonInfo(
							info.getPoliceNum(), info.getPoliceNum(), BAQ_UNIT_QTDW_ID,
							info.getPoliceName(), info.getPoliceNum());
				}
			
			}
			wristbandService.active(har.getBasicCase()
					.getBangleCode(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), har.getModifyPeople().getCode());
			for (PoliceInfoBean temp : piBeanLst) {
				//遍历传进来的民警信息列表，只绑定有绑定标记的
				if(temp.isRelationWithCard()){
					locationCardService.active(temp.getIcNum(),
							temp.getPoliceNum(), har.getModifyPeople().getCode());
				}
			}
			CriminalBasicCase cbc = criminalCaseService
					.findCriminalCaseByCaseId(har.getBasicCase().getLawCase());
			Unit u = unitService.findUnitByCode(har.getBasicCase()
					.getSponsorUnitCode());
			String unitId = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (u != null) {
				unitId = u.getId();
			}else{
				unitId = BAQ_UNIT_QTDW_ID;
			}
			
			// 如果使用单没有绑定案件信息 ，则用使用单号代替案件编号,"暂无"代替案件名称
			if (cbc != null) {
				 caseFormService.syncAddCaseInfo(har.getBasicCase()
						.getLawCase(), cbc.getCaseName(), unitId, sdf.format(har
								.getBasicCase().getUpdatedTime()));
				caseFormService.syncCaseFormInfo(har.getBasicCase()
						.getHandlingAreaReceiptNum(), har.getBasicCase()
						.getLawCase(), har.getBasicCase()
						.getHandlingAreaReceiptNum(), polices);
				
			} else {
			 caseFormService.syncAddCaseInfo(har.getBasicCase()
						.getHandlingAreaReceiptNum(), "暂无", unitId, sdf
						.format(new Date()));
				 caseFormService.syncCaseFormInfo(har.getBasicCase()
						.getHandlingAreaReceiptNum(), har.getBasicCase()
						.getHandlingAreaReceiptNum(), har.getBasicCase()
						.getHandlingAreaReceiptNum(), polices);
			}
			if (!StringUtils.isBlank(har.getBasicCase()
					.getByQuestioningPeopleIdentifyNum())) {
				instructionService.create(har.getBasicCase()
						.getByQuestioningPeopleIdentifyNum(), har.getBasicCase()
						.getHandlingAreaReceiptNum(), "嫌疑人"
								+ har.getBasicCase().getByQuestioningPeopleName()
								+ "的基础信息已采集，请部署并开展相关研判工作。", "0000000011004",
								new String[] {QING_ZHI_ZHONG_XIN_UNIT_ID}, BAQ_UNIT_PERSON_CODE, "520199180000"); // 创建人编码固定，创建人单位编码取法制大队编码
			}
	}

	@SuppressWarnings("unchecked")
	@After(value = "updateHandlingAreaReceiptAopPoint()")
	public void afterUpdateHandlingAreaReceiptAopPoint(JoinPoint jp)
			throws DahuaException {
		Object[] args = jp.getArgs();
		HandlingAreaReceipt har = (HandlingAreaReceipt) args[0];
		List<PoliceInfoBean> piBeanLst = (List<PoliceInfoBean>) args[1];
		List<HandlingAreaReceiptToPoliceInfo> htpTempLstForDelete = (List<HandlingAreaReceiptToPoliceInfo>) args[2];
		CriminalBasicCase cbc = criminalCaseService
				.findCriminalCaseByCaseId(har.getBasicCase().getLawCase());
		 caseFormService.syncUpdateSuspectInfo(har
				.getBasicCase().getByQuestioningPeopleIdentifyNum(), har
				.getBasicCase().getByQuestioningPeopleName(), har
				.getBasicCase().getHandlingAreaReceiptNum());
		List<Map> polices = new ArrayList<Map>();
		for (PoliceInfoBean info : piBeanLst) {
			if(!info.isRelationWithCard()){
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userUUID", info.getPoliceNum());
			if (info.getIfMainPolice().equals("0000000007002")) {
				map.put("userRole", 1);
			} else {
				map.put("userRole", 2);
			}
			polices.add(map);
			Person p = personService.findPersonByCode(info.getPoliceNum());
			String orgId = "";
			if (p != null && p.getAccount() != null) {
				orgId = p.getOrganization().getId();
				basicDataService.syncAddPersonInfo(//此方法在大华那边，如果已有该人员 则更新 否则新增
						p.getAccount().getAccountName(), p.getAccount().getPassword(), orgId,
						info.getPoliceName(), info.getPoliceNum());
			}else{
				 basicDataService.syncAddPersonInfo(
							info.getPoliceNum(), info.getPoliceNum(), BAQ_UNIT_QTDW_ID,
							info.getPoliceName(), info.getPoliceNum());
			}
			
		}
		// 绑定警员定位卡
		for(PoliceInfoBean temp : piBeanLst){
        	if(temp.isRelationWithCard()){
	            locationCardService.active(temp.getIcNum(),
        				temp.getPoliceNum(), har.getModifyPeople().getCode());
	            //(新绑定的卡)遍历此卡所属警员的使用单所占用的审讯室，为新卡赋予门禁卡权限
	            PoliceInfo pi = policeInfoService.findByPoliceCode(temp.getPoliceNum());
	            if(pi != null && pi.getHandlingAreaReceiptToPoliceInfoList() != null){
	            	for(HandlingAreaReceiptToPoliceInfo htp : pi.getHandlingAreaReceiptToPoliceInfoList()){
	            		 HandlingAreaReceipt tempHar = htp.getHandlingAreaReceipt();
	            		 if (HandlingAreaReceiptServiceImpl.HANDLINGAREARECEIPT_UNDERWAY.equals(tempHar.getStatus())) {
	             			List<AskRoomAllocationRecord> ararLst = askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId(tempHar.getId());
	             			if(ararLst != null && ararLst.size() > 0){
	             				AskRoomAllocationRecord lastArar = ararLst.get(ararLst.size()-1);
	             				if(lastArar.getLeaveTime() == null){
	             					entranceControlService.authorize(temp.getIcNum(), lastArar.getAskRoom().getDahuaRoomId());
	             				}
	             			}
	             		}
	 	            }
	            }
        	}else if(temp.isRelationWithHar()){
        		//(只是新关联的警员)查询本使用单所占用的审讯室，为新卡赋予门禁权限
        		if (HandlingAreaReceiptServiceImpl.HANDLINGAREARECEIPT_UNDERWAY.equals(har.getStatus())) {
        			List<AskRoomAllocationRecord> ararLst = askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId(har.getId());
        			if(ararLst != null && ararLst.size() > 0){
        				AskRoomAllocationRecord lastArar = ararLst.get(ararLst.size()-1);
        				if(lastArar.getLeaveTime() == null){
        					entranceControlService.authorize(temp.getIcNum(), lastArar.getAskRoom().getDahuaRoomId());
        				}
        			}
        		}
        	}
		}
		
		Unit u = unitService.findUnitByCode(har.getBasicCase()
				.getSponsorUnitCode());
		String unitId = "";
		if(u != null){
			unitId = u.getId();
		}else{
			unitId = BAQ_UNIT_QTDW_ID;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (cbc != null) {
			 caseFormService.syncAddCaseInfo(har.getBasicCase()
					.getLawCase(), cbc.getCaseName(), unitId, sdf.format(har
					.getBasicCase().getUpdatedTime()));
			 caseFormService.syncCaseFormInfo(har.getBasicCase()
					.getHandlingAreaReceiptNum(), har.getBasicCase()
					.getLawCase(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), polices);
		} else {
			 caseFormService.syncAddCaseInfo(har.getBasicCase()
					.getHandlingAreaReceiptNum(), "暂无", unitId, sdf
					.format(new Date()));
			 caseFormService.syncCaseFormInfo(har.getBasicCase()
					.getHandlingAreaReceiptNum(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), polices);
		}
		if (!StringUtils.isBlank(har.getBasicCase()
				.getByQuestioningPeopleIdentifyNum())) {
			 MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
			 Map<String, Object> userMap = user.getUserMap() ;
			 Map<String, String> mPerson = new HashMap<String, String>(0) ;
			 if(userMap.get("person")!=null){
			 mPerson = (Map<String, String>)userMap.get("person");
			 }
			 Person person = personService.findById(mPerson.get("id"));
			instructionService.create(har.getBasicCase()
					.getByQuestioningPeopleIdentifyNum(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), "嫌疑人"
					+ har.getBasicCase().getByQuestioningPeopleName()
					+ "的基础信息已采集，请部署并开展相关研判工作。", "0000000011004",
					new String[] { QING_ZHI_ZHONG_XIN_UNIT_ID }, BAQ_UNIT_PERSON_CODE, "520199180000"); // 创建人编码固定，创建人单位编码取法制大队编码
		}
	}

	// 保存分配记录aop
	@Pointcut("execution(* com.taiji.pubsec.ajqlc.baq.service.impl.AskRoomAllocationRecordServiceImpl.saveAskRoomAllocationRecord(..))")
	public void saveAskRoomAllocationRecordAopPoint() {

	}

	@After(value = "saveAskRoomAllocationRecordAopPoint()")
	public void afterSaveAskRoomAllocationRecordAopPoint(JoinPoint jp)
			throws DahuaException {
		Object[] args = jp.getArgs();
		AskRoomAllocationRecord aar = (AskRoomAllocationRecord) args[0];
		HandlingAreaReceipt har = aar.getHandlingAreaReceipt();
		String zbjh = "";// 主办民警警号
		String xbjh = "";// 协办民警警号
		for (HandlingAreaReceiptToPoliceInfo temp : har.getHandlingAreaReceiptToPoliceInfoList()) {
			PoliceInfo info = temp.getPoliceInfo();
			entranceControlService.authorize(info.getIcNum(), aar
					.getActivityRoom().getDahuaRoomId());
			if (temp.getIfMainPolice().equals("0000000007002")) {
				zbjh = info.getPoliceNum();
			} else {
				xbjh = info.getPoliceNum();
			}
		}
		String sxjlid = "";
		// 开始审讯(无案件时 案件编码传入使用单编码)
		if (null == har.getBasicCase().getLawCase()
				|| har.getBasicCase().getLawCase().isEmpty()) {
			ResultBean res = caseFormServiceImpl.allocateRoom(har
					.getBasicCase().getHandlingAreaReceiptNum(), har
					.getBasicCase().getHandlingAreaReceiptNum(), har
					.getBasicCase().getHandlingAreaReceiptNum(), aar
					.getActivityRoom().getDahuaRoomId(), zbjh, xbjh);
			sxjlid = res.getData();
		} else {
			ResultBean res = caseFormServiceImpl.allocateRoom(har
					.getBasicCase().getHandlingAreaReceiptNum(), har
					.getBasicCase().getLawCase(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), aar.getActivityRoom()
					.getDahuaRoomId(), zbjh, xbjh);
			sxjlid = res.getData();
		}

		// 根据使用单id 和 问询室id 查询分配记录 更新sxjlid
		AskRoomAllocationRecord roomUsing = askRoomAllocationRecordService
				.findAskRoomAllocationRecordByReceiptIdAndRoomId(har.getId(),
						aar.getAskRoom().getId());
		roomUsing.setTrialRecordId(sxjlid);
		askRoomAllocationRecordService.updateAskRoomAllocationRecord(roomUsing);
	}

	// 保存采集信息记录aop
	@Pointcut("execution(* com.taiji.pubsec.ajqlc.baq.service.impl.CollectInfoSituationServiceImpl.saveCollectInfoSituation(..))")
	public void saveCollectInfoSituationAopPoint() {

	}

	// 更新采集信息记录aop
	@Pointcut("execution(* com.taiji.pubsec.ajqlc.baq.service.impl.CollectInfoSituationServiceImpl.updateCollectInfoSituation(..))")
	public void updateCollectInfoSituationAopPoint() {

	}

	@After(value = "saveCollectInfoSituationAopPoint()")
	public void afterSaveCollectInfoSituationAopPoint(JoinPoint jp)
			throws DahuaException {
		Object[] args = jp.getArgs();
		CollectInfoSituation aar = (CollectInfoSituation) args[0];
		HandlingAreaReceipt har = aar.getHandlingAreaReceipt();
		if (!StringUtils.isBlank(har.getBasicCase()
				.getByQuestioningPeopleIdentifyNum())) {
			instructionService.create(har.getBasicCase()
					.getByQuestioningPeopleIdentifyNum(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), "嫌疑人"
					+ har.getBasicCase().getByQuestioningPeopleName()
					+ "的信息采集完毕，请部署并开展相关研判工作。", "0000000011004",
					new String[] { QING_ZHI_ZHONG_XIN_UNIT_ID }, BAQ_UNIT_PERSON_CODE, "520199180000"); // 创建人编码固定，创建人单位编码取法制大队编码
			instructionService.create(har.getBasicCase()
					.getByQuestioningPeopleIdentifyNum(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), "嫌疑人"
					+ har.getBasicCase().getByQuestioningPeopleName()
					+ "的QQ、微信已采集完毕，请部署并开展相关研判工作。", "0000000011004",
					new String[] { WANG_AN_DA_DUI_UNIT_ID }, BAQ_UNIT_PERSON_CODE, "520199180000");// 创建人编码固定，创建人单位编码取法制大队编码
			// 嫌疑人张三的手机信息已采集完毕，请部署并开展相关研判工作。
			if (!"无,无,无".equals(aar.getPhoneInfo())) {
				instructionService.create(har.getBasicCase()
						.getByQuestioningPeopleIdentifyNum(), har
						.getBasicCase().getHandlingAreaReceiptNum(), "嫌疑人"
						+ har.getBasicCase().getByQuestioningPeopleName()
						+ "的手机信息已采集完毕，请部署并开展相关研判工作。", "0000000011004",
						new String[] { QING_ZHI_ZHONG_XIN_UNIT_ID }, BAQ_UNIT_PERSON_CODE,
						"520199180000");
			}
		}
	}

	@After(value = "updateCollectInfoSituationAopPoint()")
	public void afterUpdateCollectInfoSituationAopPoint(JoinPoint jp)
			throws DahuaException {
		Object[] args = jp.getArgs();
		CollectInfoSituation aar = (CollectInfoSituation) args[0];
		HandlingAreaReceipt har = aar.getHandlingAreaReceipt();
		if (!StringUtils.isBlank(har.getBasicCase()
				.getByQuestioningPeopleIdentifyNum())) {
			instructionService.create(har.getBasicCase()
					.getByQuestioningPeopleIdentifyNum(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), "嫌疑人"
					+ har.getBasicCase().getByQuestioningPeopleName()
					+ "的信息采集完毕，请部署并开展相关研判工作。", "0000000011004",
					new String[] { QING_ZHI_ZHONG_XIN_UNIT_ID }, BAQ_UNIT_PERSON_CODE, "520199180000"); // 创建人编码固定，创建人单位编码取法制大队编码
			instructionService.create(har.getBasicCase()
					.getByQuestioningPeopleIdentifyNum(), har.getBasicCase()
					.getHandlingAreaReceiptNum(), "嫌疑人"
					+ har.getBasicCase().getByQuestioningPeopleName()
					+ "的QQ、微信已采集完毕，请部署并开展相关研判工作。", "0000000011004",
					new String[] { WANG_AN_DA_DUI_UNIT_ID }, BAQ_UNIT_PERSON_CODE, "520199180000"); // 创建人编码固定，创建人单位编码取法制大队编码
			// 嫌疑人张三的手机信息已采集完毕，请部署并开展相关研判工作。
			if (!"无,无,无".equals(aar.getPhoneInfo())) {
				instructionService.create(har.getBasicCase()
						.getByQuestioningPeopleIdentifyNum(), har
						.getBasicCase().getHandlingAreaReceiptNum(), "嫌疑人"
						+ har.getBasicCase().getByQuestioningPeopleName()
						+ "的手机信息已采集完毕，请部署并开展相关研判工作。", "0000000011004",
						new String[] { QING_ZHI_ZHONG_XIN_UNIT_ID }, BAQ_UNIT_PERSON_CODE,
						"520199180000");
			}
		}
	}
}
