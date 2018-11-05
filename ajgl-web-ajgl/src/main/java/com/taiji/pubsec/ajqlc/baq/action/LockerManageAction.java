package com.taiji.pubsec.ajqlc.baq.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.baq.action.bean.LockerBean;
import com.taiji.pubsec.ajqlc.baq.bean.LockerStockedArticleBean;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.Locker;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsInfoService;
import com.taiji.pubsec.ajqlc.baq.service.ILockerService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service.LockerService;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

import net.sf.json.JSONObject;

/**
 * 储物柜管理Action
 * 
 * @author WL-PC
 *
 */
@Controller("lockerManageAction")
@Scope("prototype")
public class LockerManageAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ILockerService lockerService;// 储物柜接口
	
	@Resource
	private IUnitService unitService;// 单位接口
	
	@Resource
	private IDictionaryItemService dictionaryItemService;// 字典项接口
	
	@Resource
	private ICarryGoodsInfoService carryGoodsInfoService;
	
	@Resource
	private LockerService cwgService;
	
	private LockerBean lockerBean;// 储物柜bean

	private List<LockerBean> lockerBeanList = new ArrayList<LockerBean>();// 储物柜bean集合

	private String lockerId;// 储物柜对象id

	private List<String> lockerIdList = new ArrayList<String>();// 储物柜对象id集合

	private List<String> lockerNameList = new ArrayList<String>();// 储物柜对象name集合

	private String status;// 储物柜状态

	private boolean isOnly;// 是否唯一
	
	private LockerStockedArticleBean lockerStockedArticleBean;

	private  String code ; //储物柜code
	
	private boolean  bn=false; //用户角色 1为管理员
	
	/**
	 * 开箱操作
	 * @return
	 */
	public String openLocker(){
		if(code==null){
			return null;
		}else{
			String[] arr = code.split("-");
			cwgService.open(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));
		}
		return SUCCESS;
	}

	/**
	 * 新建储物柜
	 * 
	 * @param lockerBean 储物柜bean对象
	 * @return "success",成功返回lockerId：储物柜id
	 */
	public String saveLocker() {
		//验证编码是否重复
		verifyCodeIsOnly();
		if(!isOnly){
			this.setMsg("编码重复");
			return SUCCESS;
		}
		Locker l = lockerBeanToLocker(lockerBean);
		lockerId = lockerService.saveLocker(l);
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 修改储物柜
	 * 
	 * @param lockerBean 储物柜bean对象
	 * @return "success",成功无返回值
	 */
	public String updateLocker() {
		//验证编码是否重复
		verifyCodeIsOnly();
		if(!isOnly){
			this.setMsg("编码重复");
			return SUCCESS;
		}
		Locker l = lockerBeanToLocker(lockerBean);
		lockerService.updateLocker(l);
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 修改储物柜状态
	 * 
	 * @param lockerIdList 储物柜id集合
	 * @param status 状态
	 * @return "success",成功无返回值
	 */
	public String updateLockerStatus() {
		String id=""; 
		for (String lId : lockerIdList) {
			lockerService.updateLockerStatus(lId, status);
			id+=lId+",";
		}
		id=id.substring(0, id.length()-1);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		if(Constant.CWGZT_KX.equals(status)){
			this.creatCaseManagementLog("办案区管理 /基本档案/储物柜管理", "对储物柜进行启用操作(储物柜ID:"+id+")");
		}else if(Constant.CWGZT_TY.equals(status)){
			this.creatCaseManagementLog("办案区管理 /基本档案/储物柜管理", "对储物柜进行停用操作(储物柜ID:"+id+")");
		}
		return SUCCESS;
	}

	/**
	 * 按条件查询所有储物柜
	 * 
	 * @param name 储物柜名称
	 * @param unit 储物柜所在单位id
	 * @param areaCode 储物柜区号
	 * @param lockerCode 储物柜柜号
	 * @param status 储物柜状态
	 * @return "success",成功返回lockerBeanList:储物柜集合
	 */
	@SuppressWarnings("unchecked")
	public String searchAllLockerListByCondition() {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		List<Map<String, String>> roles = (List<Map<String, String>>) userMap.get("roles");
		for (Map<String, String> role : roles) {
			if("1301".equals(role.get("code"))){
				bn=true; 
		       break;
			}
		}
		Map<String, String> mPerson = new HashMap<String, String>(0);
		if (userMap.get("person") != null) {
			mPerson = (Map<String, String>) userMap.get("person");
		}
        if("xnry02".equals(mPerson.get("id"))){
        	bn=true; 
        }
		
		lockerBeanList = new ArrayList<LockerBean>();
		Map<String,Object> data = JSONObject.fromObject(read());
		List<Locker> lockerList=lockerService.findLockerByCondition(data);
		if (lockerList == null) {
			return SUCCESS;
		}
		for (Locker l :lockerList) {
			LockerBean lb = lockerToLockerBean(l);
			lockerBeanList.add(lb);
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null,
				"案件管理-办案区管理", "areaCode="+String.valueOf(data.get("areaCode"))+",unitId="+String.valueOf(data.get("unit"))+
				",lockerCode in ("+String.valueOf(data.get("lockerCodeText"))+")");
		return SUCCESS;
	}

	public String findEmptyAndHarLocker(){
		Map<String,Object> data = JSONObject.fromObject(read());
		List<Locker> lockerList=lockerService.findLockerByCondition(new HashMap<String, Object>());
		for (Locker l :lockerList) {
			if(l.getStatus().equals(Constant.CWGZT_KX)){
				LockerBean lb = lockerToLockerBean(l);
				lockerBeanList.add(lb);
			}else{
				String harId = (String)data.get("harId");
				List<CarryGoodsRecord> cgrLst = carryGoodsInfoService.findCarryGoodsRecordByLocker(l.getId());
				String cgrHarId = null;
				if(cgrLst != null && cgrLst.size() > 0){
					for(CarryGoodsRecord cgr : cgrLst){
						if(cgr.getStatus().equals(Constant.WPZT_ZK) || cgr.getStatus().equals(Constant.WPZT_LSQC)){
							cgrHarId = cgr.getCarryGoodsInfo().getHandlingAreaReceipt().getId();
							break;
						}
					}
				}
				if(harId.equals(cgrHarId)){
					LockerBean lb = lockerToLockerBean(l);
					lockerBeanList.add(lb);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 通过柜子id查询储物柜在库情况信息
	 * @return
	 */
	public String findMessageByLockerRoomId(){
		lockerStockedArticleBean = lockerService.queryLockerStockedConditions(lockerId);
		return SUCCESS;
	}
	/**
	 * 查询所有储物柜的区号
	 * @return
	 */
	public  String findAllAreaCode(){
		lockerNameList=lockerService.findAllLockerCode();
		return SUCCESS;
	}
	/**
	 * 查询所有储物柜的柜号
	 * @return
	 */
	public  String findAllAreaCodeSecond(){
		lockerNameList=lockerService.findAllAreaCodeSecond(status);
		return SUCCESS;
	}
	/**
	 * 根据id查询储物柜
	 * 
	 * @param lockerId 储物柜id
	 * @return "success",成功返回lockerBean:储物柜bean对象
	 */
	public String searchLockerById() {
		Locker l = lockerService.findLockerById(lockerId);
		lockerBean = lockerToLockerBean(l);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+lockerId);
		return SUCCESS;
	}

	/**
	 * 根据id集合删除储物柜
	 * 
	 * @param lockerIdList 储物柜id集合
	 * @return "success",成功返回lockerNameList:删除失败的储物柜名称集合
	 */
	public String deleteLockerByIds() {
		String[] lockerIdArray = lockerIdList.toArray(new String[lockerIdList.size()]);
		lockerNameList = lockerService.deleteLocker(lockerIdArray);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_DELETE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 验证编号是否唯一
	 * 
	 * @param id 询问室id
	 * @param unit 单位id
	 * @param code 储物柜编码 
	 * @return "success",成功返回isOnly:编码是否唯一  
	 */
	public String verifyCodeIsOnly() {
		Locker l = lockerService.findLockerByUnitIdAndCode(lockerBean.getUnit(), lockerBean.getCode());
		if(l==null){
			isOnly = true ;
			return SUCCESS;
		}
		if(lockerBean.getId() == null){//新建
			isOnly = l == null;
		}else{
			isOnly = l.getId().equals(lockerBean.getId());
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+lockerBean.getUnit()+",code="+lockerBean.getCode());
		return SUCCESS;
	}

	/**
	 * 储物柜bean转model
	 * 
	 * @param lockerBean 储物柜bean对象
	 * @return l 储物柜model对象
	 */
	private Locker lockerBeanToLocker(LockerBean lockerBean) {
		if (lockerBean == null) {
			return null;
		}
		Locker l = new Locker();
		l.setId(lockerBean.getId());
		Unit unit = unitService.findById(lockerBean.getUnit());
		l.setUnit(unit);
		l.setNote(lockerBean.getNote());
		l.setCode(lockerBean.getCode());
		//TODO l.setPosition(lockerBean.getPosition());
		l.setAreaCode(lockerBean.getAreaCode());
		l.setLockerCode(lockerBean.getLockerCode());
		l.setStatus(lockerBean.getStatus());
		return l;
	}

	/**
	 * 储物柜model转bean
	 * 
	 * @param locker 储物柜model对象
	 * @return lb 储物柜bean对象
	 */
	private LockerBean lockerToLockerBean(Locker locker) {
		if (locker == null) {
			return null;
		}
		LockerBean lb = new LockerBean();
		lb.setId(locker.getId());
		lb.setUnit(locker.getUnit().getId());
		lb.setUnitName(locker.getUnit().getName());
		lb.setNote(locker.getNote());
		lb.setCode(locker.getCode());
		lb.setName(locker.getName());
		//TODO lb.setPosition(locker.getPosition());
		lb.setAreaCode(locker.getAreaCode());
		lb.setLockerCode(locker.getLockerCode());
		lb.setStatus(locker.getStatus());
		if (locker.getStatus() != null) {
			DictionaryItem item = dictionaryItemService.findById(locker.getStatus());
			if (item != null) {
				lb.setStatusName(item.getName());
			}
		}
		lb.setUseRecord(lockerService.isLockerUsed(locker.getId()));
		return lb;
	}
	

	public LockerBean getLockerBean() {
		return lockerBean;
	}

	public void setLockerBean(LockerBean lockerBean) {
		this.lockerBean = lockerBean;
	}

	public List<LockerBean> getLockerBeanList() {
		return lockerBeanList;
	}

	public void setLockerBeanList(List<LockerBean> lockerBeanList) {
		this.lockerBeanList = lockerBeanList;
	}

	public String getLockerId() {
		return lockerId;
	}

	public void setLockerId(String lockerId) {
		this.lockerId = lockerId;
	}

	public List<String> getLockerIdList() {
		return lockerIdList;
	}

	public void setLockerIdList(List<String> lockerIdList) {
		this.lockerIdList = lockerIdList;
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

	public List<String> getLockerNameList() {
		return lockerNameList;
	}

	public void setLockerNameList(List<String> lockerNameList) {
		this.lockerNameList = lockerNameList;
	}

	public LockerStockedArticleBean getLockerStockedArticleBean() {
		return lockerStockedArticleBean;
	}

	public void setLockerStockedArticleBean(LockerStockedArticleBean lockerStockedArticleBean) {
		this.lockerStockedArticleBean = lockerStockedArticleBean;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isBn() {
		return bn;
	}

	public void setBn(boolean bn) {
		this.bn = bn;
	}


}
