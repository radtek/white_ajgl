package com.taiji.pubsec.ajqlc.baq.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.action.bean.ActivityRoomBean;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;

/**
 * 问（询）讯室管理Action
 * 
 * @author WL-PC
 *
 */
@Controller("activityRoomManageAction")
@Scope("prototype")
public class ActivityRoomManageAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	private ActivityRoomBean askRoomBean;// 询问室bean

	private List<ActivityRoomBean> askRoomBeanList = new ArrayList<ActivityRoomBean>();// 询问室bean集合

	private String askRoomId;// 询问室对象id

	private List<String> askRoomIdList = new ArrayList<String>();// 询问室对象id集合

	private List<String> askRoomNameList = new ArrayList<String>();// 询问室对象name集合
	
	private List<String> otherRoomIdList = new ArrayList<String>();// 其他房间对象id集合

	private List<String> otherRoomNameList = new ArrayList<String>();// 其他房间对象name集合

	private String status;// 询问室状态

	private boolean isOnly;// 是否唯一

	@Resource
	private IAskRoomService askRoomService;// 询问室接口

	@Resource
	private IUnitService unitService;// 单位接口

	@Resource
	private IDictionaryItemService dictionaryItemService;// 字典项接口

	/**
	 * 新建询问室
	 * 
	 * @param askRoomBean 询问室bean对象
	 * @return "success",成功返回askRoomId：询问室id
	 */
	public String saveAskRoom() {
		//验证编号是否重复
		verifyCodeIsOnly();
		if(!isOnly){
			this.setMsg("编码重复");
			return SUCCESS;
		}
		//验证名称是否重复
		verifyNameIsOnly();
		if(!isOnly){
			this.setMsg("名称重复");
			return SUCCESS;
		}
		ActivityRoom ar = askRoomBeanToaskRoom(askRoomBean);
		askRoomId = askRoomService.saveAskRoom(ar);
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 修改询问室
	 * 
	 * @param askRoomBean 询问室bean对象
	 * @return "success",成功无返回值
	 */
	public String updateAskRoom() {
		//验证编号是否重复
		verifyCodeIsOnly();
		if(!isOnly){
			this.setMsg("编码重复");
			return SUCCESS;
		}
		//验证名称是否重复
		verifyNameIsOnly();
		if(!isOnly){
			this.setMsg("名称重复");
			return SUCCESS;
		}
		ActivityRoom ar = askRoomBeanToaskRoom(askRoomBean);
		askRoomService.updateAskRoom(ar);
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 修改询问室状态
	 * 
	 * @param askRoomIdList 询问室id集合
	 * @param status 状态
	 * @return "success",成功无返回值
	 */
	public String updateAskRoomStatus() {
		for (String arId : askRoomIdList) {
			askRoomService.updateAskRoomStatus(arId, status);
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 按条件查询所有询问室（分页查询）
	 * 
	 * @param code 询问室编码
	 * @param name 询问室名称
	 * @param status 询问室状态id
	 * @param unit 询问室所在单位id
	 * @param note 备注
	 * @param type 房间类型
	 * @return "success",成功返回askRoomBeanList:询问室集合
	 */
	public String searchAllAskRoomListByCondition() {
		Map<String,String> queryConditions = new HashMap<String,String>();
		queryConditions.put("code", askRoomBean.getCode());
		queryConditions.put("name", askRoomBean.getName());
		queryConditions.put("status", askRoomBean.getStatus());
		queryConditions.put("unitId", askRoomBean.getUnit());
		queryConditions.put("note", askRoomBean.getNote());
		queryConditions.put("type", askRoomBean.getType());
		Pager<ActivityRoom> pager = askRoomService.findAskRoomByKey(queryConditions,this.getStart() / this.getLength(), this.getLength());
		if (pager == null) {
			this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null,
					"案件管理-办案区管理", "code="+askRoomBean.getCode()+",name="+askRoomBean.getName()+",status="+askRoomBean.getStatus()
					+",unitId="+askRoomBean.getUnit()+",note="+askRoomBean.getNote()+",type="+askRoomBean.getType());
			return SUCCESS;
		}
		this.setTotalNum(pager.getTotalNum());
		for (ActivityRoom ar : pager.getPageList()) {
			ActivityRoomBean arb = askRoomToAskRoomBean(ar);
			askRoomBeanList.add(arb);
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null,
				"案件管理-办案区管理", "code="+askRoomBean.getCode()+",name="+askRoomBean.getName()+",status="+askRoomBean.getStatus()
				+",unitId="+askRoomBean.getUnit()+",note="+askRoomBean.getNote()+",type="+askRoomBean.getType());
		return SUCCESS;
	}

	/**
	 * 根据id查询询问室
	 * 
	 * @param askRoomId 询问室id
	 * @return "success",成功返回askRoomBean:询问室bean对象
	 */
	public String searchAskRoomById() {
		ActivityRoom ar = askRoomService.findAskRoomById(askRoomId);
		askRoomBean = askRoomToAskRoomBean(ar);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+askRoomId);

		return SUCCESS;
	}

	/**
	 * 根据id集合删除询问室
	 * 
	 * @param askRoomIdList 询问室id集合
	 * @return "success",成功返回askRoomNameList:删除失败的询问室名称集合
	 */
	public String deleteAskRoomByIds() {
		String[] askRoomIdArray = askRoomIdList.toArray(new String[askRoomIdList.size()]);
		askRoomNameList = askRoomService.deleteAskRoom(askRoomIdArray);
		String ids = "";
		for(String str: askRoomIdArray){
			ids += str;
			ids += " ";
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_DELETE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id in "+ids);
		return SUCCESS;
	}
	
	/**
	 * 根据id集合删除其他房间
	 * 
	 * @param otherRoomIdList 其他房间id集合
	 * @return "success",成功返回otherRoomNameList:删除失败的其他房间名称集合
	 */
	public String deleteOtherRoomByIds(){
		String[] otherRoomIdArray = otherRoomIdList.toArray(new String[otherRoomIdList.size()]);
		otherRoomNameList = askRoomService.deleteOtherRoom(otherRoomIdArray);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_DELETE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 验证编号是否唯一【567】
	 * 
	 * @param id 询问室id
	 * @param unit 单位id
	 * @param code 询问室编码
	 * @return "success",成功返回isOnly:编码是否唯一  
	 */
	public String verifyCodeIsOnly() {
		ActivityRoom ar = askRoomService.findAskRoomByUnitIdAndCode(askRoomBean.getUnit(), askRoomBean.getCode());
		if(ar==null){
			isOnly = true ;
			this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+askRoomBean.getUnit()+",code="+askRoomBean.getCode());
			return SUCCESS;
		}
		if(askRoomBean.getId() == null){//新增
			isOnly = ar == null;
		}else{//修改
			isOnly = ar.getId().equals(askRoomBean.getId());
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+askRoomBean.getUnit()+",code="+askRoomBean.getCode());
		return SUCCESS;
	}

	/**
	 * 验证名称是否唯一
	 * 
	 * @param id 询问室id
	 * @param unit 单位id
	 * @param name 询问室名称
	 * @return "success",成功返回isOnly:名称是否唯一 
	 */
	public String verifyNameIsOnly() {
		ActivityRoom ar = askRoomService.findAskRoomByUnitIdAndName(askRoomBean.getUnit(), askRoomBean.getName());
		if(ar==null){
			isOnly = true ;
			this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+askRoomBean.getUnit()+",name="+askRoomBean.getName());
			return SUCCESS;
		}
		if(askRoomBean.getId() == null){//新增
			isOnly = ar == null;
		}else{//修改
			isOnly = ar.getId().equals(askRoomBean.getId());
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+askRoomBean.getUnit()+",name="+askRoomBean.getName());
		return SUCCESS;
	}

	/**
	 * 询问室bean转model
	 * 
	 * @param askRoomBean 询问室bean对象
	 * @return ar 询问室model对象
	 */
	private ActivityRoom askRoomBeanToaskRoom(ActivityRoomBean askRoomBean) {
		if (askRoomBean == null) {
			return null;
		}
		ActivityRoom ar = new ActivityRoom();
		ar.setId(askRoomBean.getId());
		Unit unit = unitService.findById(askRoomBean.getUnit());
		ar.setUnit(unit);
		ar.setMonitorNum(askRoomBean.getMonitorNum());
		ar.setName(askRoomBean.getName());
		ar.setNote(askRoomBean.getNote());
		ar.setCode(askRoomBean.getCode());
		ar.setStatus(askRoomBean.getStatus());
		ar.setType(askRoomBean.getType());
		return ar;
	}

	/**
	 * 询问室model转bean
	 * 
	 * @param askRoom 询问室model对象
	 * @return arb 询问室bean对象
	 */
	private ActivityRoomBean askRoomToAskRoomBean(ActivityRoom askRoom) {
		if (askRoom == null) {
			return null;
		}
		ActivityRoomBean arb = new ActivityRoomBean();
		arb.setId(askRoom.getId());
		arb.setUnit(askRoom.getUnit().getId());
		arb.setUnitName(askRoom.getUnit().getName());
		arb.setMonitorNum(askRoom.getMonitorNum());
		arb.setName(askRoom.getName());
		arb.setNote(askRoom.getNote());
		arb.setCode(askRoom.getCode());
		arb.setStatus(askRoom.getStatus());
		arb.setStatusName(findDictionaryItemNameById(askRoom.getStatus()));
		arb.setType(askRoom.getType());
		arb.setTypeName(findDictionaryItemNameById(askRoom.getType()));
		arb.setUseRecord(askRoomService.isAskRoomUsed(askRoom.getId()));
		return arb;
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

	public ActivityRoomBean getAskRoomBean() {
		return askRoomBean;
	}

	public void setAskRoomBean(ActivityRoomBean askRoomBean) {
		this.askRoomBean = askRoomBean;
	}

	public String getAskRoomId() {
		return askRoomId;
	}

	public void setAskRoomId(String askRoomId) {
		this.askRoomId = askRoomId;
	}

	public List<String> getAskRoomIdList() {
		return askRoomIdList;
	}

	public void setAskRoomIdList(List<String> askRoomIdList) {
		this.askRoomIdList = askRoomIdList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getIsOnly() {
		return isOnly;
	}

	public void setIsOnly(boolean isOnly) {
		this.isOnly = isOnly;
	}

	public List<ActivityRoomBean> getAskRoomBeanList() {
		return askRoomBeanList;
	}

	public void setAskRoomBeanList(List<ActivityRoomBean> askRoomBeanList) {
		this.askRoomBeanList = askRoomBeanList;
	}

	public List<String> getAskRoomNameList() {
		return askRoomNameList;
	}

	public void setAskRoomNameList(List<String> askRoomNameList) {
		this.askRoomNameList = askRoomNameList;
	}

	public List<String> getOtherRoomIdList() {
		return otherRoomIdList;
	}

	public void setOtherRoomIdList(List<String> otherRoomIdList) {
		this.otherRoomIdList = otherRoomIdList;
	}

	public List<String> getOtherRoomNameList() {
		return otherRoomNameList;
	}

	public void setOtherRoomNameList(List<String> otherRoomNameList) {
		this.otherRoomNameList = otherRoomNameList;
	}

}
