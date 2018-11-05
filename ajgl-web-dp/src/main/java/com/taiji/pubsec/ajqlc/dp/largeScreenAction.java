package com.taiji.pubsec.ajqlc.dp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.bean.ActivityRoomServiceBean;
import com.taiji.pubsec.ajqlc.baq.bean.HandlingAreaReceiptServiceBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.service.IActivityRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IHandleAreaActivityRecordInfoService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.impl.AskRoomServiceImpl;
import com.taiji.pubsec.ajqlc.dp.bean.ActivityRecordBean;
import com.taiji.pubsec.ajqlc.dp.bean.AskRoomBean;
import com.taiji.pubsec.ajqlc.sla.bean.AdministrationPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.bean.PenalPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.service.ICaseAttachedInfoService;
import com.taiji.pubsec.ajqlc.sla.service.impl.CaseAttachedInfoServiceImpl;
import com.taiji.pubsec.businesscomponent.organization.service.UnitServiceImpl;

/**
 * 大屏
 * 
 * @author lyp
 *
 */
@Controller("largeScreenAction")
@Scope("prototype")
public class largeScreenAction extends PageCommonSysManageAction {
	private static final Logger LOGGER = LoggerFactory.getLogger(UnitServiceImpl.class);

	@Resource
	private ICaseAttachedInfoService caseAttachedInfoService;
	
	@Resource
	private IAskRoomService askRoomService;

	@Resource
	private IHandleAreaActivityRecordInfoService handleAreaActivityRecordInfoService;
	
	@Resource
	private IHandlingAreaReceiptService handingAreaReceiptService;
	
	@Resource
	private IActivityRecordService activityRecordService;
	
	private Map<String, Object> resultMap = new HashMap<String, Object>();
	
	private String harId;
	
	private String harCode;

	/**
	 * 查询刑事案件到期提醒列表
	 * 
	 * @param txType
	 *            提醒类型： <br>
	 *            xj 刑拘到期提醒 <br>
	 *            bgjy 变更羁押到期提醒 <br>
	 *            tqdb 提请逮捕到期提醒 <br>
	 *            pb 批捕到期提醒 <br>
	 *            ysqs 移送起诉到期提醒 <br>
	 *            qbhs 取保候审到期提醒 <br>
	 *            jsjz 监视居住到期提醒
	 * @param from
	 *            查询开始时间
	 * @param to
	 *            查询结束时间
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页面大小
	 * @return 返回刑拘到期提醒列表
	 * @throws ParseException
	 */
	public String findCrownCaseList() {
		Pager<PenalPersonServiceBean> xjList = caseAttachedInfoService.findPenalDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_XJ, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		Pager<PenalPersonServiceBean> bgjyList = caseAttachedInfoService.findPenalDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_BGJY, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		Pager<PenalPersonServiceBean> tqdbList = caseAttachedInfoService.findPenalDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_TQDB, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		Pager<PenalPersonServiceBean> pbList = caseAttachedInfoService.findPenalDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_PB, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		Pager<PenalPersonServiceBean> ysqsList = caseAttachedInfoService.findPenalDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_YSQS, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		Pager<PenalPersonServiceBean> qbhsList = caseAttachedInfoService.findPenalDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_QBHS, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		Pager<PenalPersonServiceBean> jsjzList = caseAttachedInfoService.findPenalDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_JSJZ, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		resultMap.put("xjList", xjList.getPageList());
		resultMap.put("bgjyList", bgjyList.getPageList());
		resultMap.put("tqdbList", tqdbList.getPageList());
		resultMap.put("pbList", pbList.getPageList());
		resultMap.put("ysqsList", ysqsList.getPageList());
		resultMap.put("qbhsList", qbhsList.getPageList());
		resultMap.put("jsjzList", jsjzList.getPageList());

		Set<String> set = new HashSet<String>();
		set = changeSet(jsjzList.getPageList(), changeSet(qbhsList.getPageList(),
				changeSet(ysqsList.getPageList(), changeSet(pbList.getPageList(), changeSet(tqdbList.getPageList(),
						changeSet(bgjyList.getPageList(), changeSet(xjList.getPageList(), new HashSet<String>())))))));
		resultMap.put("caseCount", set.size());
		resultMap.put("date", findDateAndWeeker());
		return SUCCESS;
	}

	/**
	 * 刑政案件到期提醒list查询
	 * 
	 * @return
	 */
	public String findAdministrativeCaseList() {
		Pager<AdministrationPersonServiceBean> xzjlList = caseAttachedInfoService.findAdminDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_XZJL, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		Pager<AdministrationPersonServiceBean> sqjdList = caseAttachedInfoService.findAdminDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_SQJD, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		Pager<AdministrationPersonServiceBean> qzjdList = caseAttachedInfoService.findAdminDqtxByConditions(
				CaseAttachedInfoServiceImpl.TX_TYPE_QZJD, this.findDate().get(0), this.findDate().get(1), 0, 9999);
		resultMap.put("xzjlList", xzjlList.getPageList());
		resultMap.put("sqjdList", sqjdList.getPageList());
		resultMap.put("qzjdList", qzjdList.getPageList());
		Set<String> set = new HashSet<String>();
		set = changeXZSet(qzjdList.getPageList(),
				changeXZSet(sqjdList.getPageList(), changeXZSet(xzjlList.getPageList(), new HashSet<String>())));
		resultMap.put("caseCount", set.size());
		resultMap.put("date", findDateAndWeeker());
		return SUCCESS;
	}

	/**
	 * 今日讯(询)问情况list查询
	 * 
	 * @return
	 */
	public String findAskRoomList() {
		List<ActivityRoom> actRoom= askRoomService.findAllAskRooms();
        resultMap.put("askRoomList",changeBeanAskRoom(actRoom));
        List<ActivityRoomServiceBean> actRoomService= askRoomService.findVariousRooms(AskRoomServiceImpl.ROOM_TYPE_WAITASK,"待问室");
        resultMap.put("waitingRoomList", actRoomService);
        List<ActivityRoomServiceBean> ysRoomService= askRoomService.findVariousRooms(AskRoomServiceImpl.ROOM_TYPE_YSS,"约束室 ");
        resultMap.put("ysRoomService", ysRoomService);
        resultMap.put("tomorrow", findTomorrow().getTime());
        resultMap.put("today",findToday().getTime());
        Map<String ,Object > dateMap=new HashMap<String, Object>();
        dateMap.put("queryStartTime",findToday() );
        dateMap.put("queryEndTime", findTomorrow());
        List<HandlingAreaReceiptServiceBean> SuspectsList=handingAreaReceiptService.findJudgingSuspectsByConditions(dateMap);
       /* for(int i=0;i<10;i++){
        	HandlingAreaReceiptServiceBean bean =new HandlingAreaReceiptServiceBean();
        	bean.setByQuestioningPeopleName("小马哥"+i);
        	bean.setCauseOfLawCase("抢劫案"+i);
        	bean.setEnterIntoTime(new Date().getTime());
        	bean.setId("001"+i);
        	bean.setLawCase("001"+i);
        	bean.setMainPolices("民警"+i);
        	bean.setSex("0000000003001");
        	bean.setSponsorUnitCode("dnawie"+i);
        	bean.setHarCode("使用单code");
        	bean.setStatus("待问");
        	SuspectsList.add(bean);
        }*/
        resultMap.put("suspectsList", SuspectsList);//嫌疑人列表
        dateMap.put("startTime",findWeekBegin());
        dateMap.put("endTime", findWeekEnd());
        Map<String, Integer> unitMap= handingAreaReceiptService.findVariousUnitsSuspectNums(dateMap);//柱状图数据
        resultMap.put("startTime",findWeekBegin().getTime());//星期的第一天
        resultMap.put("endTime",findWeekEnd().getTime());//星期的最后一天
        Set<String> keyset=unitMap.keySet();
        List<String> keyList=new ArrayList<String>();
        List<Integer> valueList=new ArrayList<Integer>();
        for (String key : keyset) {  
            keyList.add(key);
            valueList.add(unitMap.get(key));
      }  
        resultMap.put("keyList", keyset);
        resultMap.put("valueList", valueList);
        resultMap.put("nowDateLong", new Date().getTime());
		return SUCCESS;
	}

	/**
	 * 人员详情list 活动记录查询
	 * 
	 * @return
	 */
	public String findActivationRecord() {
		List<ActivityRecord> list= activityRecordService.findActivityRecordsByReceiptCode(harCode);
		List<ActivityRecordBean> listBean=changeRecord(list);
		resultMap.put("activationRecordList", listBean);
		resultMap.put("todayLong", new Date().getTime());
		return SUCCESS;
	}

	/**
	 * 人员活动记录转换bean
	 * @param list
	 * @return
	 */
	private List<ActivityRecordBean> changeRecord(List<ActivityRecord> list) {
		List<ActivityRecordBean> listBean=new ArrayList<ActivityRecordBean>();
		for(int i=0;i<list.size();i++){
			ActivityRecordBean bean=new ActivityRecordBean();
			bean.setDescription(list.get(i).getDescription());
			bean.setGridId(list.get(i).getGridId());
			bean.setGridName(list.get(i).getGridName());
			bean.setGridType(list.get(i).getGridType());
			bean.setHandlingAreaReceiptNum(list.get(i).getHandlingAreaReceiptNum());
			bean.setId(list.get(i).getId());
			bean.setPassageId(list.get(i).getPassageId());
			if(list.get(i).getStartTime()!=null){
				bean.setStartTime(list.get(i).getStartTime().getTime());
			}
			if(list.get(i).getEndTime()!=null){
				bean.setEndTime(list.get(i).getEndTime().getTime());
			}
			bean.setSupRecordId(list.get(i).getSupRecordId());
			listBean.add(bean);
		}
		return listBean;
	}

	/**
	 * askroom转换Bean
	 * @param roomList
	 * @return
	 */
	private List<AskRoomBean>  changeBeanAskRoom(List<ActivityRoom> roomList){
		if(roomList==null){
			return null;
		}
		List<AskRoomBean>  list=new ArrayList<AskRoomBean>();
		for(int i=0;i<roomList.size();i++){
			AskRoomBean bean=new AskRoomBean();
			bean.setName(roomList.get(i).getName());
			bean.setStatus(roomList.get(i).getStatus());
			list.add(bean);
		}
		return  list;
	}
	/**
	 * 行政案件 剔除list里的重复code
	 * 
	 * @return
	 */
	private HashSet<String> changeXZSet(List<AdministrationPersonServiceBean> list, HashSet<String> set) {
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i).getCaseInfo().getCaseCode());
		}
		return set;
	}

	/**
	 * 剔除list里的重复code
	 * 
	 * @return
	 */
	private HashSet<String> changeSet(List<PenalPersonServiceBean> list, HashSet<String> set) {
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i).getCaseInfo().getCaseCode());
		}
		return set;
	}
	
	/**
	 * 获取上周的星期日  08:00
	 * @return
	 */
	private Date findWeekBegin(){
		 Calendar cal =Calendar.getInstance();
		 cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);		
		 return changeDateTime(cal.getTime());
	}
	/**
	 * 获取本周的最后一天  08:00
	 * @return
	 */
	private Date findWeekEnd(){
		Calendar cal =Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return changeDateTime(cal.getTime());
	}
	
	/**
	 * 转换日期为 年月日  08:00:00
	 * @return
	 */
	private Date changeDateTime(Date date){
		String today=new SimpleDateFormat("yyyy年MM月dd日 08:00").format(date);
		try {
			return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").parse(today);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取今天的日期并格式化
	 * @return
	 */
	private Date formatToday(){
		String today=new SimpleDateFormat("yyyy年MM月dd日 09:00:00").format(new Date());
		try {
			return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").parse(today);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取昨天的日期并格式化
	 * @return
	 */
	private Date formatYesterday(){
		String s=new SimpleDateFormat("yyyy年MM月dd日 09:00:00").format(new Date(new Date().getTime()-86400000L));
		try {
			return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取明天的日期并格式化
	 * @return
	 */
	private Date formatTomorrow(){
		
		String s=new SimpleDateFormat("yyyy年MM月dd日 09:00:00").format(new Date(new Date().getTime()+86400000L));
		try {
			return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取今天的时间
	 * @return
	 */
	private Date findToday(){
		String s=new SimpleDateFormat("HHmmss").format(new Date());
		int i=Integer.parseInt(s);
		if(i>90000&&i<=235959){
			return formatToday();
		}else{
			return formatYesterday();
		}
	}
	
	/**
	 * 获取明天的时间
	 * @return
	 */
	private Date findTomorrow(){
		String s=new SimpleDateFormat("HHmmss").format(new Date());
		int i=Integer.parseInt(s);
		if(i>90000&&i<=235959){
			return formatTomorrow();
		}else{
			return formatToday();
		}
	}

	/**
	 * 格式化当前日期 (年月日 星期)
	 * 
	 * @return
	 */
	private String findDateAndWeeker() {
		Locale localeCN = Locale.SIMPLIFIED_CHINESE;
		return new SimpleDateFormat("yyyy年MM月dd日 E",localeCN).format(new Date());
	}

	/**
	 * 获取当天的0点和24点
	 * 
	 * @return
	 * @throws ParseException
	 */
	private List<Date> findDate() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date start;
		Date end;
		List<Date> list = new ArrayList<Date>();
		try {
			start = formater2.parse(formater.format(new Date()) + " 00:00:00");
			end = formater2.parse(formater.format(new Date()) + " 23:59:59");
			list.add(start);
			list.add(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public String getHarId() {
		return harId;
	}

	public void setHarId(String harId) {
		this.harId = harId;
	}

	public String getHarCode() {
		return harCode;
	}

	public void setHarCode(String harCode) {
		this.harCode = harCode;
	}
}
