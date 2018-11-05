package com.taiji.pubsec.ajqlc.syncdh.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AlarmRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.service.IActivityRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAlarmRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IAlarmService;

public class SyncAlarmRecord {
	
	@Resource
	private IAlarmService alarmService;
	
	@Resource
	private  IAlarmRecordService  alarmRecordService;

	@Resource
	private  IAskRoomService  askRoomService;
	
	@Resource
	private IActivityRecordService activityRecordService;
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	private static final String HANDLINGAREARECEIPT_UNDERWAY = "0000000006001";	// 使用单状态：进行中
	
	public void alarmRecord() throws DahuaException{
		//查询并保存手环预警
		saveWristRecord();
		//查询并审讯室预警
		saveroomRecord();
		//查询并保存智能分析器预警
		saveZNFXRecord();
	}
	/**
	 * 保存手环预警记录
	 */
	public void saveWristRecord(){
		try {
			//查询手环绑定预警消息
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("status", HANDLINGAREARECEIPT_UNDERWAY);
			Pager<HandlingAreaReceipt> pager = handlingAreaReceiptService.findHandlingAreaReceiptByKey(map, 0, Integer.MAX_VALUE);
			List<HandlingAreaReceipt> list = pager.getPageList() ;
			List<AlarmInfoBean> bangleList = alarmRecordService.queryMyAlarmRecordByalarmType(String.valueOf(ConstantBean.YJLX_SHBJ));
			Date start = null ;
			String startDate = "";
			if(bangleList != null && !bangleList.isEmpty()){
				start = new Date(Long.parseLong(String.valueOf(bangleList.get(0).getAlarmDate())));
				startDate = fmtTime(start);
			}
			if(list != null && !list.isEmpty()){
				for (HandlingAreaReceipt handlingAreaReceipt : list) {
					String  braceletId = handlingAreaReceipt.getBasicCase().getBangleCode();
					List<AlarmInfoBean> alarmInfoList = alarmService.queryAlarmWirstInfo(startDate, "",braceletId);
					if(alarmInfoList != null && !alarmInfoList.isEmpty()){
						saveAlarmRecord(alarmInfoList,String.valueOf(ConstantBean.YJLX_SHBJ),braceletId);
					}
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	//查询讯（询）问室预警消息
	public void saveroomRecord(){
		try {
			List<AlarmInfoBean> roomAlarmList = alarmRecordService.queryMyAlarmRecordByalarmType(String.valueOf(ConstantBean.YJLX_SXSSB));
			Date roomStart = null ;
			String startDate = "";
			if(roomAlarmList != null && !roomAlarmList.isEmpty()){
				roomStart = new Date(Long.parseLong(String.valueOf(roomAlarmList.get(0).getAlarmDate())));
				startDate = fmtTime(roomStart);
			}
			List<ActivityRoom> roomList = askRoomService.findAllAskRooms();
			if(roomList != null && !roomList.isEmpty()){
				for (ActivityRoom activityRoom : roomList) {
					List<AlarmInfoBean> alarmInfoList = alarmService.queryAlarmInquestInfo(startDate, "",activityRoom.getId());
					if(alarmInfoList != null && !alarmInfoList.isEmpty()){
						saveAlarmRecord(alarmInfoList,String.valueOf(ConstantBean.YJLX_SXSSB),activityRoom.getId());
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 查询并保存智能分析服务器预警消息
	 */
	public void saveZNFXRecord(){
		try {
			//查询智能分析服务器预警消息
			List<AlarmInfoBean> analyzeList = alarmRecordService.queryMyAlarmRecordByalarmType(String.valueOf(ConstantBean.YJLX_ZNFX));
			Date analyzeStart = null ;
			String startDate = "";
			if(analyzeList != null && !analyzeList.isEmpty()){
				analyzeStart = new Date(Long.parseLong(String.valueOf(analyzeList.get(0).getAlarmDate())));
				startDate = fmtTime(analyzeStart);
			}
			 List<String> gridIdList =  activityRecordService.findActivityRecordsGridId();
			if(gridIdList != null && !gridIdList.isEmpty()){
				for(int i=0; i<gridIdList.size(); i++){
					List<AlarmInfoBean> analyze = alarmService.queryAlarmAnalyzeInfo(startDate, "",gridIdList.get(i));
					if(analyze != null && !analyze.isEmpty()){
						saveAlarmRecord(analyze,String.valueOf(ConstantBean.YJLX_ZNFX),gridIdList.get(i));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param list 预警记录
	 * @param typecode 类型编码 1为手环 2为网格 3为审讯室
	 * @param askDeviceId 请求设备Id
	 * @return
	 * @throws Exception
	 */
	private String saveAlarmRecord(List<AlarmInfoBean> list,String typecode ,String askDeviceId) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (AlarmInfoBean alarmInfoBean : list) {
			List<AlarmInfoBean>  tep = alarmRecordService.queryMyAlarmRecord(formatter.parse(alarmInfoBean.getAlarmDate()), null, askDeviceId, typecode);
			if(tep != null && tep.size() > 0){
				continue;
			}
			AlarmRecord alarmRecord = beanByModel(alarmInfoBean);
			alarmRecord.setAskDeviceId(askDeviceId);
			alarmRecord.setTypeCode(typecode);
			if(null != alarmRecord){
				String id = alarmRecordService.saveAlarmRecord(alarmRecord);
				return id ; 
			}
		}
		return null;
	}

	private AlarmRecord beanByModel(AlarmInfoBean alarmInfoBean) throws Exception {
	
		AlarmRecord alarmRecord = new AlarmRecord();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		alarmRecord.setAlarmDate(sdf.parse(alarmInfoBean.getAlarmDate()));
		alarmRecord.setAlarmType(alarmInfoBean.getAlarmType());
		alarmRecord.setChannelCode(alarmInfoBean.getChannelCode());
		alarmRecord.setChannelName(alarmInfoBean.getChannelName());
		alarmRecord.setDeviceCode(alarmInfoBean.getDeviceCode());
		alarmRecord.setDeviceName(alarmInfoBean.getDeviceName());
		alarmRecord.setTypeCode(alarmInfoBean.getBraceletPhysicalId());
		
		return alarmRecord;
	}
	
	/**
	 * 格式化日期--开始&结束
	 */
	private String fmtTime(Date dateTime){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(dateTime==null){
			dateTime=new Date();
		}
		String dateString = formatter.format(dateTime);
		return dateString;
	}
}
