package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.bean.HandlingAreaReceiptServiceBean;
import com.taiji.pubsec.ajqlc.baq.bean.PoliceInfoBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptToPoliceInfoService;
import com.taiji.pubsec.ajqlc.baq.service.IPoliceInfoService;
import com.taiji.pubsec.ajqlc.baq.util.ParamMapUtil;
import com.taiji.pubsec.ajqlc.generatenum.service.IGenerateNumService;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.spring.BeanCopierFmtUtil;
import com.taiji.pubsec.common.tool.sql.SQLTool;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * @author chengkai
 */
@Service("handlingAreaReceiptService")
@Transactional(rollbackFor = Exception.class)
public class HandlingAreaReceiptServiceImpl implements IHandlingAreaReceiptService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource(name="jpaSqlDao")
	private com.taiji.persistence.dao.SqlDao jpaSqlDao ;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HandlingAreaReceiptServiceImpl.class);
	
	public static final String FREEROOM = "0000000001001";
	private static final String IS_HAVERECORD_YES = "0000000007002";
	private static final String IS_HAVERECORD_NO = "0000000007001";
	private static final String HANDLINGAREARECEIPT_FINISHED = "0000000006002";	// 使用单状态：已完成
	public static final String HANDLINGAREARECEIPT_UNDERWAY = "0000000006001";	// 使用单状态：进行中
	public static final String GRID_TYPE_WAITASK = "11"; // 网格类型：待问室
	public static final String GRID_TYPE_ASK = "1"; // 网格类型：讯（询）问室
	private static final String UNIT_CODE_XUNTE = "5201995C0000"; //单位编码：巡特大队
	private static final String UNIT_CODE_XINGZHEN = "520199050000"; //单位编码：刑侦大队
	private static final String UNIT_CODE_JINDU = "520199210000"; //单位编码：禁毒大队
	private static final String UNIT_CODE_ZHIAN = "520199030000"; //单位编码：治安大队
	private static final String AY_QT = "0000000026003"; //其他案由

	
	@Resource
	private IGenerateNumService generateNumService;
	
	@Resource
	private IUnitService unitService;
	
	@Resource
	private IDictionaryItemService  dictionaryItemService;
	
	@Resource
	private IPersonService personService;
	
	@Resource
	private IPoliceInfoService policeInfoService;
	
	@Resource
	private IHandlingAreaReceiptToPoliceInfoService handlingAreaReceiptToPoliceInfoService;
	
	@SuppressWarnings("unchecked")
	@Override
	public String saveHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt) {
		this.dao.save(handlingAreaReceipt);
        return handlingAreaReceipt.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveHandlingAreaReceiptWithPoliceInfo(HandlingAreaReceipt handlingAreaReceipt, List<PoliceInfoBean> piBeanLst) {
		//保存使用单信息
		this.dao.save(handlingAreaReceipt);
		//遍历民警信息
		for(PoliceInfoBean piBean : piBeanLst){
			PoliceInfo pi = null;
			if(piBean.isRelationWithCard()){
				//如果标记为需要绑卡，则处理
				if(piBean.getId().contains("newPolice")){
					//前台已通过警号查询民警信息的uuid，如果没有则新增
					pi = new PoliceInfo();
				}else{
					//有信息则查询
					pi = policeInfoService.findById(piBean.getId());
				}
				//覆盖新警员名字（可能不更改）
				BeanCopierFmtUtil.copyBean(piBean, pi, null);
				if(piBean.getId().contains("newPolice")){
					pi.setId(null);
				}
				MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
				Map<String, Object> userMap = user.getUserMap() ;
				Map<String, String> mPerson = new HashMap<String, String>(0) ;
				if(userMap.get("person")!=null){
					mPerson = (Map<String, String>)userMap.get("person");
				}
				//新发卡信息
				pi.setSendCardPeople(personService.findById(mPerson.get("id")));
				pi.setSendCardTime(new Date());
				//保存或更新
				if(StringUtils.isBlank(pi.getId())){
					this.dao.save(pi);
				}else{
					this.dao.update(pi);
				}
			}else{
				//标记为不需要绑卡则直接查出来，为保存关系做准备
				pi = policeInfoService.findById(piBean.getId());
			}
			if(piBean.isRelationWithHar()){
				//标记为需要保存使用单关系时保存关系
				HandlingAreaReceiptToPoliceInfo htp = new HandlingAreaReceiptToPoliceInfo();
				htp.setHandlingAreaReceipt(handlingAreaReceipt);
				htp.setPoliceInfo(pi);
				htp.setIfMainPolice(piBean.getIfMainPolice());
				handlingAreaReceiptToPoliceInfoService.save(htp);
			}
		}
        return handlingAreaReceipt.getId();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteHandlingAreaReceipt(Serializable... ids) {
		this.dao.delete(HandlingAreaReceipt.class, ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt) {
		this.dao.update(handlingAreaReceipt);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HandlingAreaReceipt findHandlingAreaReceiptById(String id) {
		return (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, id);
	}

	@Override
	public String acquireNum() {
		String code = "SQ";
		String num = generateNumService.acquireNum(code);
		int iNum = Integer.parseInt(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String nowYear = sdf.format(new Date());
		return code + nowYear + String.format("%05d", iNum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<HandlingAreaReceipt> findHandlingAreaReceiptByKey(Map<String,Object> queryConditions, int pageNo,
			int pageSize) {
		StringBuilder xql = new StringBuilder("select distinct h from HandlingAreaReceipt as h left join h.basicCase as bc left join h.askRoomAllocationRecords as aa left join aa.askRoom as ar "
		+ " left join h.handleAreaActivityRecordInfo as haari left join h.handleAreaActivityRecordInfo.handlingAreaActivityRecords as haar where 1=1");

		Map<String, Object> xqlMap = new HashMap<String, Object>();
		 
		if(ParamMapUtil.isNotBlank(queryConditions.get("unitId"))){
			xql.append(" and h.createUnitId = :unitId");
			xqlMap.put("unitId", queryConditions.get("unitId"));
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("modifyPeopleName"))){
			xql.append(" and h.modifyPeople.id like :modifyPeopleName");
			xqlMap.put("modifyPeopleName", "%" + SQLTool.SQLSpecialChTranfer((String) queryConditions.get("modifyPeopleName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("handlingAreaReceiptNum"))){
			xql.append(" and bc.handlingAreaReceiptNum like :handlingAreaReceiptNum");
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("handlingAreaReceiptNum", "%" + SQLTool.SQLSpecialChTranfer((String) queryConditions.get("handlingAreaReceiptNum")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("askRoomName"))){
			xql.append(" and ar.id = :askRoomName");
			xqlMap.put("askRoomName", queryConditions.get("askRoomName"));
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("handlingPolice"))){
			xql.append(" and bc.handlingPolice like :handlingPolice ") ;
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("handlingPolice", "%" + SQLTool.SQLSpecialChTranfer((String) queryConditions.get("handlingPolice")) + "%");
		}
		if(IS_HAVERECORD_YES.equals(queryConditions.get("haveRecord"))){
			xql.append(" and (haari.recordCount !=null and haari.recordCount > 0) ");
		}else if(IS_HAVERECORD_NO.equals(queryConditions.get("haveRecord"))){
			xql.append(" and (haari.recordCount = null or haari.recordCount = 0) ");
		}
		
		if(ParamMapUtil.isNotBlank(queryConditions.get("status"))){
			xql.append( " and h.status = :status ");
			xqlMap.put("status", queryConditions.get("status"));
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("causeOfLawCase"))){
			xql.append(" and bc.causeOfLawCase = :causeOfLawCase ");
			xqlMap.put("causeOfLawCase", queryConditions.get("causeOfLawCase"));
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("byQuestioningPeopleName"))){
			xql.append(" and bc.byQuestioningPeopleName like :byQuestioningPeopleName ");
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("byQuestioningPeopleName", "%" + SQLTool.SQLSpecialChTranfer((String) queryConditions.get("byQuestioningPeopleName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("byQuestioningPeopleIdentifyNum"))){
			xql.append(" and bc.byQuestioningPeopleIdentifyNum like :byQuestioningPeopleIdentifyNum ");
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("byQuestioningPeopleIdentifyNum", "%" + SQLTool.SQLSpecialChTranfer((String) queryConditions.get("byQuestioningPeopleIdentifyNum")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("lawCase"))){
			xql.append(" and bc.lawCase like :lawCase ");
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("lawCase", queryConditions.get("lawCase"));
		}
		if (ParamMapUtil.isNotBlank(queryConditions.get("enterIntoTimeStart"))) {
			xql.append(" and bc.enterIntoTime >= :enterIntoTimeStart ");
			xqlMap.put("enterIntoTimeStart", queryConditions.get("enterIntoTimeStart"));
		}
		if (ParamMapUtil.isNotBlank(queryConditions.get("enterIntoTimeEnd"))) {
			xql.append( "and bc.enterIntoTime < :enterIntoTimeEnd ");
			xqlMap.put("enterIntoTimeEnd", queryConditions.get("enterIntoTimeEnd"));
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("ifRecord"))){
			xql.append( "and h.recordStatus = :recordStatus ");
			xqlMap.put("recordStatus", queryConditions.get("ifRecord"));
		}
		xql.append(" order by bc.enterIntoTime desc");
		Pager<HandlingAreaReceipt> page = this.dao.findByPage(HandlingAreaReceipt.class, xql.toString(), xqlMap, pageNo, pageSize); 

		
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HandlingAreaReceipt findHandlingAreaReceiptByRoomId(String roomId) {
		String xql = "select arar from AskRoomAllocationRecord as arar where arar.askRoom.id = ? order by arar.allocationTime desc";
		List<AskRoomAllocationRecord> ararList = this.dao.findAllByParams(AskRoomAllocationRecord.class, xql, new Object[]{roomId});
		if(ararList.isEmpty()){
			return null;
		}else{
			AskRoomAllocationRecord arar = ararList.get(0);
			if(FREEROOM.equals(arar.getActivityRoom().getStatus())){
				return null;
			}else{
				return (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, arar.getHandlingAreaReceipt().getId());
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Pager<HandlingAreaReceipt> findHandlingAreaReceiptByKey(String id, int pageNo,
			int pageSize) {
	
		List<Unit> units = this.unitService.findSubUnitsByUnitId(id);
		List<String> unitIds = new ArrayList<String> ();
		for (Unit unit : units) {
			unitIds.add(unit.getId());
		}
		String xql = "select a from HandlingAreaReceipt as a where a.createUnitId in (:unitIds) order by a.basicCase.enterIntoTime desc";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		xqlMap.put("unitIds", unitIds);
		return this.dao.findByPage(ActivityRoom.class, xql, xqlMap, pageNo, pageSize);
	}

	@Override
	public void finishHandlingAreaReceipt(String receiptId) {
		HandlingAreaReceipt har = this.findHandlingAreaReceiptById(receiptId);
		har.setStatus(HANDLINGAREARECEIPT_FINISHED);
		har.setDoneTime(new Date());
		this.updateHandlingAreaReceipt(har);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HandlingAreaReceipt> findHandlingAreaReceiptByConditions(Map<String, Object> queryConditions) {
		StringBuilder xql = new StringBuilder("select distinct h from HandlingAreaReceipt as h left join h.askRoomAllocationRecords as has left join h.finallyLeaveInfo as hi where 1 = 1 ");
		xql.append(" and (h.askRoomAllocationRecords.size <=0 or has.leaveTime is not null)");
//		xql.append(" and  has.leaveTime is not null");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xql.append(" and h.status = :status ");
		xqlMap.put("status", HANDLINGAREARECEIPT_UNDERWAY);
		if(ParamMapUtil.isNotBlank(queryConditions.get("unitId"))){
			xql.append(" and h.createUnitId = :unitId");
			xqlMap.put("unitId", queryConditions.get("unitId"));
		}
//		if(StringUtils.isNotBlank(queryConditions.get("handlingAreaReceiptNum"))){
//			xql.append(" and h.basicCase.handlingAreaReceiptNum like :handlingAreaReceiptNum");
//			xql = SQLTool.SQLAddEscape(xql);
//			xqlMap.put("handlingAreaReceiptNum", "%" + SQLTool.SQLSpecialChTranfer(queryConditions.get("handlingAreaReceiptNum")) + "%");
//		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("handlingPolice"))){
			xql.append(" and h.basicCase.handlingPolice like :handlingPolice ") ;
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("handlingPolice", "%" + SQLTool.SQLSpecialChTranfer((String) queryConditions.get("handlingPolice")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("modifyPeopleName"))){
			xql.append(" and h.modifyPeople = :modifyPeople");
			xqlMap.put("modifyPeople", queryConditions.get("modifyPeopleName"));
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("byQuestioningPeopleName"))){
			xql.append(" and h.basicCase.byQuestioningPeopleName like :byQuestioningPeopleName ");
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("byQuestioningPeopleName", "%" + SQLTool.SQLSpecialChTranfer((String) queryConditions.get("byQuestioningPeopleName")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("byQuestioningPeopleIdentifyNum"))){
			xql.append(" and h.basicCase.byQuestioningPeopleIdentifyNum like :byQuestioningPeopleIdentifyNum ");
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("byQuestioningPeopleIdentifyNum", "%" + SQLTool.SQLSpecialChTranfer((String) queryConditions.get("byQuestioningPeopleIdentifyNum")) + "%");
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("enterAreaReasons"))){
			xql.append(" and h.basicCase.enterAreaReasons = :enterAreaReasons ");
			xqlMap.put("enterAreaReasons", queryConditions.get("enterAreaReasons"));
		}
		if(ParamMapUtil.isNotBlank(queryConditions.get("lawCase"))){
			xql.append(" and h.basicCase.lawCase like :lawCase ");
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("lawCase", queryConditions.get("lawCase"));
		}
		
//		if(StringUtils.isNotBlank(queryConditions.get("askRoomName"))){
//			xql.append(" and ar.id = :askRoomName");
//			xqlMap.put("askRoomName", queryConditions.get("askRoomName"));
//		}
		if (ParamMapUtil.isNotBlank(queryConditions.get("enterIntoTimeStart"))) {
			xql.append(" and h.basicCase.enterIntoTime >= :enterIntoTimeStart ");
			xqlMap.put("enterIntoTimeStart", queryConditions.get("enterIntoTimeStart"));
		}
		if (ParamMapUtil.isNotBlank(queryConditions.get("enterIntoTimeEnd"))) {
			xql.append( "and h.basicCase.enterIntoTime < :enterIntoTimeEnd ");
			xqlMap.put("enterIntoTimeEnd", queryConditions.get("enterIntoTimeEnd"));
		}
		
		xql.append(" and ( hi is null or hi.finallyLeaveTime is null)");
		xql.append(" order by h.basicCase.enterIntoTime desc");
		
		List<HandlingAreaReceipt> harList = this.dao.findAllByParams(HandlingAreaReceipt.class, xql.toString(), xqlMap); 

		return harList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findHandlingAreaReceiptIdByBangleCode(String bangleCode) {
		String xql = "select b from BasicCase as b where b.bangleCode = ? and b.handlingAreaReceipt.status = ?";
		BasicCase baseCase = (BasicCase) dao.findByParams(BasicCase.class, xql, new Object[]{bangleCode, HANDLINGAREARECEIPT_UNDERWAY}); 
		if(baseCase == null){
			return null;
		}else{
			return baseCase.getHandlingAreaReceipt().getId();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findHandlingAreaReceiptIdByIcNum(String icNum) {
		String xql = "select htp from HandlingAreaReceiptToPoliceInfo as htp where htp.policeInfo.icNum = ? and htp.handlingAreaReceipt.status = ?";
		List<HandlingAreaReceiptToPoliceInfo> htpLst = (List<HandlingAreaReceiptToPoliceInfo>) dao.findAllByParams(HandlingAreaReceiptToPoliceInfo.class, xql, new Object[]{icNum, HANDLINGAREARECEIPT_UNDERWAY});
		if(htpLst == null){
			return new ArrayList<String>();
		}else{
			List<String> lst = new ArrayList<String>();
			for(HandlingAreaReceiptToPoliceInfo temp : htpLst){
				lst.add(temp.getHandlingAreaReceipt().getId());
			}
			return lst;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAccountsByRoleId(String roleId) {
		 Map<String, Object> arg1 = new HashMap<String, Object>();
		 String sql = "select distinct f.zt_id from t_at_fwkzzt_js as j, t_at_fwkzzt as f where j.fwkzzt_id = f.id and j.js_id = :jsId";
		 arg1.put("jsId", roleId);
		 List<String> str = jpaSqlDao.find(sql, arg1);
		 return str;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HandlingAreaReceiptServiceBean> findJudgingSuspectsByConditions(Map<String, Object> conditions) {
		String xql = "select ar.handlingAreaReceiptNum from ActivityRecord as ar where ar.gridType = ? and ar.endTime is not null ";
		List<String> WaitReceiptNums = dao.findAllByParams(ActivityRecord.class, xql, new Object[]{GRID_TYPE_WAITASK});
		
		String xql1 = "select ar.handlingAreaReceiptNum from ActivityRecord as ar where ar.gridType = ? and ar.endTime is not null ";
		List<String> askReceiptNums = dao.findAllByParams(ActivityRecord.class, xql1, new Object[]{GRID_TYPE_ASK});
		
		StringBuilder xql2 = new StringBuilder("select ar.handlingAreaReceiptNum from ActivityRecord as ar where 1 = 1 ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xql2.append(" and ar.endTime >= :queryStartTime ");
		xqlMap.put("queryStartTime", conditions.get("queryStartTime"));
		xql2.append(" and ar.endTime < :queryEndTime ");
		xqlMap.put("queryEndTime", conditions.get("queryEndTime"));
		List<String> finishedReceiptNums = dao.findAllByParams(ActivityRecord.class, xql2.toString(), xqlMap);
		
		List<HandlingAreaReceiptServiceBean> beans = new ArrayList<HandlingAreaReceiptServiceBean>();
		for(String str: WaitReceiptNums){
			HandlingAreaReceiptServiceBean bean = new HandlingAreaReceiptServiceBean();
			HandlingAreaReceipt receipt = this.findHandlingAreaReceiptByNum(str);
			if(receipt==null){
				continue;
			}
			bean.setHarCode(receipt.getBasicCase().getHandlingAreaReceiptNum());
			bean.setId(receipt.getId());
			bean.setByQuestioningPeopleName(receipt.getBasicCase().getByQuestioningPeopleName());
			bean.setCauseOfLawCase(changeToStr(receipt.getBasicCase()));
			bean.setEnterIntoTime(receipt.getBasicCase().getEnterIntoTime().getTime());
			bean.setLawCase(receipt.getBasicCase().getLawCase());
			bean.setSex(receipt.getBasicCase().getSex());
			bean.setSponsorUnitCode(changeUnitCodeToName(receipt.getBasicCase()));
			bean.setStatus("待问");
			String policeNames = "";
			Set<HandlingAreaReceiptToPoliceInfo> htpLst = receipt.getHandlingAreaReceiptToPoliceInfoList();
			for(HandlingAreaReceiptToPoliceInfo temp : htpLst){
				policeNames += temp.getPoliceInfo().getPoliceName();
				policeNames += ";";
			}
			bean.setMainPolices(changePoliceName(policeNames));
			beans.add(bean);
		}
		
		for(String str: askReceiptNums){
			HandlingAreaReceiptServiceBean bean = new HandlingAreaReceiptServiceBean();
			HandlingAreaReceipt receipt = this.findHandlingAreaReceiptByNum(str);
			if(receipt==null){
				continue;
			}
			bean.setHarCode(receipt.getBasicCase().getHandlingAreaReceiptNum());
			bean.setId(receipt.getId());
			bean.setByQuestioningPeopleName(receipt.getBasicCase().getByQuestioningPeopleName());
			bean.setCauseOfLawCase(changeToStr(receipt.getBasicCase()));
			bean.setEnterIntoTime(receipt.getBasicCase().getEnterIntoTime().getTime());
			bean.setLawCase(receipt.getBasicCase().getLawCase());
			bean.setSex(receipt.getBasicCase().getSex());
			bean.setSponsorUnitCode(changeUnitCodeToName(receipt.getBasicCase()));
			bean.setStatus("进行中");
			String policeNames = "";
			Set<HandlingAreaReceiptToPoliceInfo> htpLst = receipt.getHandlingAreaReceiptToPoliceInfoList();
			for(HandlingAreaReceiptToPoliceInfo temp : htpLst){
				policeNames += temp.getPoliceInfo().getPoliceName();
				policeNames += ";";
			}
			bean.setMainPolices(changePoliceName(policeNames));
			
			beans.add(bean);
		}
		
		for(String str: finishedReceiptNums){
			HandlingAreaReceiptServiceBean bean = new HandlingAreaReceiptServiceBean();
			HandlingAreaReceipt receipt = this.findHandlingAreaReceiptByNum(str);
			if(receipt==null){
				continue;
			}
			bean.setId(receipt.getId());
			if(receipt.getBasicCase()!=null){
				bean.setHarCode(receipt.getBasicCase().getHandlingAreaReceiptNum());
				bean.setByQuestioningPeopleName(receipt.getBasicCase().getByQuestioningPeopleName());
				bean.setCauseOfLawCase(changeToStr(receipt.getBasicCase()));
				bean.setEnterIntoTime(receipt.getBasicCase().getEnterIntoTime().getTime());
				bean.setLawCase(receipt.getBasicCase().getLawCase());
				bean.setSex(receipt.getBasicCase().getSex());
				bean.setSponsorUnitCode(changeUnitCodeToName(receipt.getBasicCase()));
			}
			bean.setStatus("已完成");
			String policeNames = "";
			Set<HandlingAreaReceiptToPoliceInfo> htpLst = receipt.getHandlingAreaReceiptToPoliceInfoList();
			for(HandlingAreaReceiptToPoliceInfo temp : htpLst){
				policeNames += temp.getPoliceInfo().getPoliceName();
				policeNames += ";";
			}
			bean.setMainPolices(changePoliceName(policeNames));
			
			beans.add(bean);
		}
		
		Collections.sort(beans, new Comparator<HandlingAreaReceiptServiceBean>() {

			@Override
			public int compare(HandlingAreaReceiptServiceBean o1, HandlingAreaReceiptServiceBean o2) {
				return o1.getEnterIntoTime().compareTo(o2.getEnterIntoTime());
			}
		});
		
		return beans;
	}
	/**
	 * 截取拼接后的主办民警名称
	 * @return
	 */
	private String changePoliceName(String name){
		if(!"".equals(name)){
			return name.substring(0,name.length()-1);
		}
		return "";
	}
	
	/**
	 * 将单位的字典项编码转换成名称
	 */
	private String changeUnitCodeToName(BasicCase s){
		if(s!=null){
			Unit ut=unitService.findUnitByCode(s.getSponsorUnitCode());
			if(ut!=null){
				return ut.getShortName();
			}
		}
    	return "";
	}
	
	
	/**
	 * 将案由字典项转换成string
	 * @return
	 */
	private String changeToStr(BasicCase s){
		if(s==null){
			return "";
		}
		if(AY_QT.equals(s.getCauseOfLawCase())){
			return s.getOtherCauseOfLawCase();
		}
		return CodeToName(s.getCauseOfLawCase());
	}
	/**
	 * 字典项编号转换成code
	 * @return
	 */
	private String CodeToName( String str){
		DictionaryItem dic=dictionaryItemService.findDicItemByCode(str);
		if(dic!=null){
			return dic.getName();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public HandlingAreaReceipt findHandlingAreaReceiptByNum(String receiptNum) {
		String xql = "select h from HandlingAreaReceipt as h where h.basicCase.handlingAreaReceiptNum = ?";
		return (HandlingAreaReceipt) dao.findByParams(HandlingAreaReceipt.class, xql, new Object[]{receiptNum});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> findVariousUnitsSuspectNums(Map<String, Object> conditions) {
		List<Unit> units = dao.findAllByParams(Unit.class, "select u from Unit as u where u.type = ?", new Object[]{"0"});
		units.add((Unit) dao.findByParams(Unit.class, "select u from Unit as u where u.code = ?", new Object[]{UNIT_CODE_XUNTE}));
		units.add((Unit) dao.findByParams(Unit.class, "select u from Unit as u where u.code = ?", new Object[]{UNIT_CODE_XINGZHEN}));
		units.add((Unit) dao.findByParams(Unit.class, "select u from Unit as u where u.code = ?", new Object[]{UNIT_CODE_JINDU}));
		units.add((Unit) dao.findByParams(Unit.class, "select u from Unit as u where u.code = ?", new Object[]{UNIT_CODE_ZHIAN}));
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		String xql = "select b from BasicCase as b where b.sponsorUnitCode = ? and b.enterIntoTime >= ? and b.enterIntoTime < ?";
		List<BasicCase> cases = new ArrayList<BasicCase>();
		for(Unit unit: units){
			cases = dao.findAllByParams(BasicCase.class, xql, new Object[]{unit.getCode(), conditions.get("startTime"), conditions.get("endTime")});
			map.put(unit.getShortName(), cases.size());
		}
		return map;
	}

	@Override
	public void updateHandlingAreaReceiptWithPoliceLst(HandlingAreaReceipt handlingAreaReceipt, Map<String, String> addMap,
			Map<String, String> delMap) {
		this.updateHandlingAreaReceipt(handlingAreaReceipt);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateHandlingAreaReceiptWithPoliceInfo(HandlingAreaReceipt handlingAreaReceipt,
			List<PoliceInfoBean> piBeanLst, List<HandlingAreaReceiptToPoliceInfo> htpTempLstForDelete) {
		this.updateHandlingAreaReceipt(handlingAreaReceipt);
		for(HandlingAreaReceiptToPoliceInfo htp : htpTempLstForDelete){
			handlingAreaReceiptToPoliceInfoService.delete(htp.getId());
		}
		//遍历民警信息
		for(PoliceInfoBean piBean : piBeanLst){
			PoliceInfo pi = null;
			if(piBean.isRelationWithCard()){
				//如果标记为需要绑卡，则处理
				if(piBean.getId().contains("newPolice")){
					//前台已通过警号查询民警信息的uuid，如果没有则新增
					pi = new PoliceInfo();
				}else{
					//有信息则查询
					pi = policeInfoService.findById(piBean.getId());
				}
				//覆盖新警员名字（可能不更改）
				BeanCopierFmtUtil.copyBean(piBean, pi, null);
				if(piBean.getId().contains("newPolice")){
					pi.setId(null);
				}
				MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
				Map<String, Object> userMap = user.getUserMap() ;
				Map<String, String> mPerson = new HashMap<String, String>(0) ;
				if(userMap.get("person")!=null){
					mPerson = (Map<String, String>)userMap.get("person");
				}
				//新发卡信息
				pi.setSendCardPeople(personService.findById(mPerson.get("id")));
				pi.setSendCardTime(new Date());
				//保存或更新
				if(StringUtils.isBlank(pi.getId())){
					this.dao.save(pi);
				}else{
					this.dao.update(pi);
				}
			}else{
				//标记为不需要绑卡则直接查出来，为保存关系做准备
				pi = policeInfoService.findById(piBean.getId());
			}
			if(piBean.isRelationWithHar()){
				//标记为需要保存使用单关系时保存关系
				HandlingAreaReceiptToPoliceInfo htp = new HandlingAreaReceiptToPoliceInfo();
				htp.setHandlingAreaReceipt(handlingAreaReceipt);
				htp.setPoliceInfo(pi);
				htp.setIfMainPolice(piBean.getIfMainPolice());
				handlingAreaReceiptToPoliceInfoService.save(htp);
			}
		}
	}
	@Override
	public boolean checkIcNumIsFree(String icNum) {
		String sql = "select h from PoliceInfo h where icNum = ?";
		List<PoliceInfo> polList = (List<PoliceInfo>)dao.findAllByParams(PoliceInfo.class, sql, new Object[]{icNum});
		if(polList != null && polList.size() > 0){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public boolean checkPoliceIsFree(String policNum) {
		String sql = "select h from PoliceInfo h where policeNum = ? and icNum is not null";
		List<PoliceInfo> polList = (List<PoliceInfo>)dao.findAllByParams(PoliceInfo.class, sql, new Object[]{policNum});
		if(polList != null && polList.size() > 0){
			return false;
		}else{
			return true;
		}
	}

}
