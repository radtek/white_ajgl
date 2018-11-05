package com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.BurnRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.RoomBean;

import net.sf.json.JSONObject;

public class JsonToBean {
	/**
	 * 返回ResultBean
	 * @param json
	 * @return
	 */
	public static ResultBean json2ResultBean(String json) {
		JSONObject  jasonObject = JSONObject.fromObject(json);
		Map map = (Map)jasonObject;
        System.out.println(jasonObject.get("success"));
        ResultBean res =  new ResultBean(jasonObject.get("success")==null?false:Boolean.parseBoolean(jasonObject.get("success").toString()));
        res.setErrorMsg(jasonObject.get("msg")==null?"":jasonObject.get("msg").toString());
        res.setCode(jasonObject.get("code")==null?"":jasonObject.get("code").toString());
        res.setData(jasonObject.get("data")==null?"":jasonObject.get("data").toString());
        return res;
	}
	/**
	 * 返回RoomBean的list
	 * @param json
	 * @return
	 */
	public static List<RoomBean> json2RoomBean(String json){
		Gson gson = new GsonBuilder().create();
		JSONObject  jasonObject = JSONObject.fromObject(json);
		if("false".equals(jasonObject.get("success").toString())){
			return null;
		}
		List<RoomBean> retList = new ArrayList<RoomBean>();
		JsonParser parser = new JsonParser();  
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();  
	    //将data节点下的内容转为JsonArray  
	    JsonArray jsonArray = jsonObject.getAsJsonArray("data");  
	    for (int i = 0; i < jsonArray.size(); i++) {  
	    	  JsonElement el = jsonArray.get(i);  
	    	  RoomBean  data = gson.fromJson(el, RoomBean.class); 
	    	  retList.add(data);
	    }
        return retList;
	}
	/**
	 * 返回BurnRecordBean的list
	 * @param json
	 * @return
	 */
	public static List<BurnRecordBean> json2BurnReordBean(String json){
		Gson gson = new GsonBuilder().create();
		JSONObject  jasonObject = JSONObject.fromObject(json);
		if("false".equals(jasonObject.get("success").toString())){
			return null;
		}
		List<BurnRecordBean> retList = new ArrayList<BurnRecordBean>();
		JsonParser parser = new JsonParser();  
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();  
	    //将data节点下的内容转为JsonArray  
	    JsonArray jsonArray = jsonObject.getAsJsonArray("data");  
	    for (int i = 0; i < jsonArray.size(); i++) {  
	    	  JsonElement el = jsonArray.get(i);  
	    	  BurnRecordBean  data = gson.fromJson(el, BurnRecordBean.class); 
	    	  retList.add(data);
	    }
        return retList;
	}
	/**
	 * 返回AlarmInfoBean的list
	 * @param json
	 * @return
	 */
	public static List<AlarmInfoBean> json2AlarmBean(String json){
		Gson gson = new GsonBuilder().create();
		List<AlarmInfoBean> retList = new ArrayList<AlarmInfoBean>();
		JSONObject  jasonObject = JSONObject.fromObject(json);
		if("false".equals(jasonObject.get("success").toString())){
			return null;
		}
		JsonParser parser = new JsonParser();  
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();  
	    //将data节点下的内容转为JsonArray  
	    JsonArray jsonArray = jsonObject.getAsJsonArray("data");  
	    for (int i = 0; i < jsonArray.size(); i++) {  
	    	  JsonElement el = jsonArray.get(i);  
	    	  AlarmInfoBean  data = gson.fromJson(el, AlarmInfoBean.class); 
	    	  retList.add(data);
	    }
        return retList;
	}
//	/**
//	 * 返回AlarmInfoBean的list
//	 * @param json
//	 * @return
//	 */
//	public static List<ActivityRecord> json2ActivationRecord(String json){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Gson gson = new GsonBuilder().create();
//		List<ActivityRecord> retList = new ArrayList<ActivityRecord>();
//		JSONObject  jasonObject = JSONObject.fromObject(json);
//		if("false".equals(jasonObject.get("success").toString())){
//			return null;
//		}
//		JsonParser parser = new JsonParser();  
//		JsonObject jsonObject = parser.parse(json).getAsJsonObject();  
//	    //将data节点下的内容转为JsonArray  
//	    JsonArray jsonArray = jsonObject.getAsJsonArray("data");  
//	    for (int i = 0; i < jsonArray.size(); i++) {  
//	    	ActivityRecord record = new ActivityRecord();
//	    	JsonElement el = jsonArray.get(i);  
//	    	JSONObject jasonObject1 = JSONObject.fromObject(el);
//	    	try {
//	    		record.setEndTime(sdf.parse(jasonObject1.getString("endTime")));
//	    		record.setStartTime(sdf.parse(jasonObject1.getString("startTime")));
//	    	} catch (ParseException e) {
//	    		// TODO Auto-generated catch block
//	    		e.printStackTrace();
//	    	}
//	    	record.setGridId(jasonObject1.getString("gridUUID"));
//	    	record.setGridName(jasonObject1.getString("gridName"));
//	    	record.setGridType(jasonObject1.getString("gridType"));
//	    	record.setPassageId(jasonObject1.getString("channelCode"));
////	    		ActivityRecord  data = gson.fromJson(el, ActivityRecord.class); 
//	    	retList.add(record);
//	    }
//        return retList;
//	}
}
