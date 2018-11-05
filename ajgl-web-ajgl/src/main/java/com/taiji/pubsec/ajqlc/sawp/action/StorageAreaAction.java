package com.taiji.pubsec.ajqlc.sawp.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageAreaBean;
import com.taiji.pubsec.ajqlc.sawp.action.util.SawpModelBeanTransformUtil;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageAreaService;
import com.taiji.pubsec.ajqlc.util.Constant;

/**
 * 物品保管区Action
 * @author WangLei
 *
 */
@Controller("storageAreaAction")
@Scope("prototype")
public class StorageAreaAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private SawpModelBeanTransformUtil sawpModelBeanTransformUtil;// model和bean互转类
	
	@Resource
	private IStorageAreaService storageAreaService;// 物品保管区接口
	
	private StorageAreaBean storageAreaBean;// 物证保管区Bean
	
	private String storageAreaId;// 物证保管区id
	
	private List<StorageAreaBean> storageAreaBeanList = new ArrayList<StorageAreaBean>();// 物品保管区list
	
	private List<String> saIdList = new ArrayList<String>();// 物品保管区id集合
	
	private List<String> saNameList = new ArrayList<String>();// 物品保管区名称集合
	
	private String state;// 状态字典项id
	
	private boolean only;// 是否唯一，true:唯一；false:不唯一

	/**
	 * 新建物品保管区
	 * 
	 * @param storageAreaBean 物品保管区Bean
	 * @return "success",成功返回flag:是否保存成功；msg:失败原因
	 */
	public String newStorageArea(){
		verifyCodeIsOnly();//验证编码是否重复
		if(!only){
			this.setFlag("false");
			this.setMsg("编码已存在！");
			return SUCCESS;
		}
		verifyNameIsOnly();//验证名称是否重复
		if(!only){
			this.setFlag("false");
			this.setMsg("名称已存在！");
			return SUCCESS;
		}
		StorageArea sa = sawpModelBeanTransformUtil.storageAreaBeanToStorageArea(storageAreaBean);
		storageAreaService.save(sa);
		this.setFlag("true");
		this.creatCaseManagementLog("涉案物品管理/ 基本档案 /物证保管区维护", "新建物品保管区(编号:"+storageAreaBean.getCode()+")");
		return SUCCESS;
	}
	
	/**
	 * 修改物品保管区
	 * 
	 * @param storageAreaBean 物品保管区Bean
	 * @return "success",成功返回flag:是否保存成功；msg:失败原因
	 */
	public String updateStorageArea(){
		verifyCodeIsOnly();//验证编码是否重复
		if(!only){
			this.setFlag("false");
			this.setMsg("编码已存在！");
			return SUCCESS;
		}
		verifyNameIsOnly();//验证名称是否重复
		if(!only){
			this.setFlag("false");
			this.setMsg("名称已存在！");
			return SUCCESS;
		}
		StorageArea sa = sawpModelBeanTransformUtil.storageAreaBeanToStorageArea(storageAreaBean);
		storageAreaService.update(sa);
		this.setFlag("true");
		this.creatCaseManagementLog("涉案物品管理/ 基本档案 /物证保管区维护", "修改物品保管区(编号:"+storageAreaBean.getCode()+")");
		return SUCCESS;
	}
	
	/**
	 * 删除物品保管区
	 * 
	 * @param 
	 * @return
	 */
	public String deleteStorageAreaByIds(){
		String[] saIdArray = saIdList.toArray(new String[saIdList.size()]);
		saNameList = storageAreaService.deleteStorageAreas(saIdArray);
		String str="";
		for(String s:saIdList){
			str+=s+",";
		}
		str.substring(0, str.length()-1);
		this.creatCaseManagementLog("涉案物品管理/ 基本档案 /物证保管区维护", "删除物品保管区(保管区名称:"+str+")");
		return SUCCESS;
	}
	
	/**
	 * 分页查询物证管理区
	 * 
	 * @return
	 */
	public String findStorageAreaByPaging(){
		Pager<StorageArea> page = storageAreaService.findAllStorageAreas(this.getStart()/this.getLength(), this.getLength());
		if(page == null){
			return SUCCESS;
		}
		this.setTotalNum(page.getTotalNum());
		for(StorageArea sa : page.getPageList()){
			storageAreaBeanList.add(sawpModelBeanTransformUtil.storageAreaToStorageAreaBean(sa));
		}
		return SUCCESS;
	}
	/**
	 * 储物箱维护页面显示保管区
	 * 
	 * @return
	 */
	public String findStorageAreaByFrame(){
		List<StorageArea> list = storageAreaService.findStorageAreasByStatus(null,Constant.ZT_QY );
		if(list == null){
			return SUCCESS;
		}
		this.setTotalNum(list.size());
		for(StorageArea sa : list){
			storageAreaBeanList.add(sawpModelBeanTransformUtil.storageAreaToStorageAreaBean(sa));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据id查询物证管理区
	 * 
	 * @param storageAreaId 物证管理区id
	 * @return "success",成功返回storageAreaBean:物证管理区bean对象
	 */
	public String findStorageAreaById(){
		StorageArea sa = storageAreaService.findById(storageAreaId);
		storageAreaBean = sawpModelBeanTransformUtil.storageAreaToStorageAreaBean(sa);
		return SUCCESS;
	}
	
	/**
	 * 修改物证管理区状态
	 * 
	 * @param state 状态字典项id
	 * @return "success",成功无返回值
	 */
	public String updateStorageAreaState(){
		String str="";
		for(String saId : saIdList){
			storageAreaService.updateStatus(saId, state);
			str+=saId+",";
		}
		str.substring(0, str.length()-1);
		if(Constant.ZT_QY.equals(state)){
			this.creatCaseManagementLog("涉案物品管理 /基本档案/ 物证保管区维护", "启用物证保管区(保管区id:"+str+")");
		}else if(Constant.ZT_TY.equals(state)){
			this.creatCaseManagementLog("涉案物品管理 /基本档案/ 物证保管区维护", "停用物证保管区(保管区id:"+str+")");
		}
		return SUCCESS;
	}
	
	
	/**
	 * 验证物证保管区编码是否唯一
	 * 
	 * @return
	 */
	public String verifyCodeIsOnly(){
		StorageArea sa = storageAreaService.findByUnitIdAndCode(storageAreaBean.getUnitId(), storageAreaBean.getCode());
		if(storageAreaBean.getId() == null || sa == null){//新增
			only = sa == null;
		}else{//修改
			only = storageAreaBean.getId().equals(sa.getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 验证物证保管区名称是否唯一
	 * @return
	 */
	public String verifyNameIsOnly(){
		StorageArea sa = storageAreaService.findByUnitIdAndName(storageAreaBean.getUnitId(), storageAreaBean.getName());
		if(storageAreaBean.getId() == null || sa == null){//新增
			only = sa == null;
		}else{//修改
			only = storageAreaBean.getId().equals(sa.getId());
		}
		return SUCCESS;
	}

	public StorageAreaBean getStorageAreaBean() {
		return storageAreaBean;
	}

	public void setStorageAreaBean(StorageAreaBean storageAreaBean) {
		this.storageAreaBean = storageAreaBean;
	}

	public String getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public List<StorageAreaBean> getStorageAreaBeanList() {
		return storageAreaBeanList;
	}

	public void setStorageAreaBeanList(List<StorageAreaBean> storageAreaBeanList) {
		this.storageAreaBeanList = storageAreaBeanList;
	}

	public List<String> getSaIdList() {
		return saIdList;
	}

	public void setSaIdList(List<String> saIdList) {
		this.saIdList = saIdList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isOnly() {
		return only;
	}

	public void setOnly(boolean only) {
		this.only = only;
	}

	public List<String> getSaNameList() {
		return saNameList;
	}

	public void setSaNameList(List<String> saNameList) {
		this.saNameList = saNameList;
	}

}
