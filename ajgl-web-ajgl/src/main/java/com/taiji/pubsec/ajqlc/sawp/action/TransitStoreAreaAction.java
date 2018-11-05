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
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageAreaService;
import com.taiji.pubsec.ajqlc.util.Constant;

/**
 * 暂管物品保管区
 * @author lenovo
 *
 */
@Controller("transitStoreAreaAction")
@Scope("prototype")
public class TransitStoreAreaAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private ITemporaryStorageAreaService temporaryStorageAreaService;
	
	private StorageAreaBean storageAreaBean;// 物证保管区Bean
	
	private List<StorageAreaBean> storageAreaBeanList = new ArrayList<StorageAreaBean>();// 暂存物品保管区list
	
	@Resource
	private SawpModelBeanTransformUtil sawpModelBeanTransformUtil;// model和bean互转类
	
	private String state;// 状态字典项id
	
	private String storageAreaId;// 物证保管区id
	
	private boolean only;// 是否唯一，true:唯一；false:不唯一
	
	private List<String> saIdList = new ArrayList<String>();// 物品保管区id集合
	
	private List<String> saNameList = new ArrayList<String>();// 物品保管区名称集合
	
	/**
	 * 分页查询暂存物品保管区
	 * 
	 * @return
	 */
	public String findtransitStoreAreaByPaging(){
		Pager<TemporaryStorageArea> page = temporaryStorageAreaService.findAllTemporaryStorageAreaByPage(this.getStart()/this.getLength(), this.getLength());
		if(page == null){
			return SUCCESS;
		}
		this.setTotalNum(page.getTotalNum());
		for(TemporaryStorageArea sa : page.getPageList()){
			storageAreaBeanList.add(sawpModelBeanTransformUtil.transitStoreAreaToStorageAreaBean(sa));
		}
		return SUCCESS;
	}
	
	/**
	 * 新建暂存物品包管区
	 * 
	 * @param storageAreaBean 物品保管区Bean
	 * @return "success",成功返回flag:是否保存成功；msg:失败原因
	 */
	public String newTransitStoreArea(){
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
		TemporaryStorageArea sa = sawpModelBeanTransformUtil.storageAreaBeanToTransitStoreArea(storageAreaBean);
		temporaryStorageAreaService.saveTemporaryStorageArea(sa);
		this.setFlag("true");
		return SUCCESS;
	}
	
	
	/**
	 * 修改暂存物品保管区
	 * 
	 * @param storageAreaBean 物品保管区Bean
	 * @return "success",成功返回flag:是否保存成功；msg:失败原因
	 */
	public String updateTransitStoreArea(){
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
		TemporaryStorageArea sa = sawpModelBeanTransformUtil.storageAreaBeanToTransitStoreArea(storageAreaBean);
		temporaryStorageAreaService.updateTemporaryStorageArea(sa);
		this.setFlag("true");
		return SUCCESS;
	}
	
	/**
	 * 修改暂存物品保管区区状态
	 * 
	 * @param state 状态字典项id
	 * @return "success",成功无返回值
	 */
	public String updateTransitStoreAreaState(){
		for(String saId : saIdList){
			temporaryStorageAreaService.updateTemporaryStorageAreaStatus(saId, state);
		}
		return SUCCESS;
	}
	
	/**
	 * 储物箱维护页面显示保管区
	 * 
	 * @return
	 */
	public String findTransitStoreAreaByFrame(){
		List<TemporaryStorageArea> list = temporaryStorageAreaService.findAllTemporaryStorageArea( Constant.ZT_QY );
		if(list == null){
			return SUCCESS;
		}
		this.setTotalNum(list.size());
		for(TemporaryStorageArea sa : list){
			storageAreaBeanList.add(sawpModelBeanTransformUtil.transitStoreAreaToStorageAreaBean(sa));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除物品保管区
	 * 
	 * @param 
	 * @return
	 */
	public String deleteTransitStoreAreaByIds(){
//		String[] saIdArray = saIdList.toArray(new String[saIdList.size()]);
//		saNameList = temporaryStorageAreaService.deleteStorageAreas(saIdArray);
		return SUCCESS;
	}
	
	/**
	 * 根据id查询暂存物品报关区
	 * 
	 * @param storageAreaId 物证管理区id
	 * @return "success",成功返回storageAreaBean:物证管理区bean对象
	 */
	public String findTransitStoreAreaById(){
		TemporaryStorageArea sa = temporaryStorageAreaService.findTemporaryStorageAreaById(storageAreaId);
		storageAreaBean = sawpModelBeanTransformUtil.transitStoreAreaToStorageAreaBean(sa);
		return SUCCESS;
	}
	
	/**
	 * 验证物证保管区编码是否唯一
	 * 
	 * @return
	 */
	public String verifyCodeIsOnly(){
		TemporaryStorageArea sa = temporaryStorageAreaService.findTemporaryStorageAreaByCode(storageAreaBean.getCode());
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
		TemporaryStorageArea sa = temporaryStorageAreaService.findTemporaryStorageAreaByName(storageAreaBean.getName());
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

	public List<StorageAreaBean> getStorageAreaBeanList() {
		return storageAreaBeanList;
	}

	public void setStorageAreaBeanList(List<StorageAreaBean> storageAreaBeanList) {
		this.storageAreaBeanList = storageAreaBeanList;
	}

	public String getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public boolean isOnly() {
		return only;
	}

	public void setOnly(boolean only) {
		this.only = only;
	}

	public List<String> getSaIdList() {
		return saIdList;
	}

	public void setSaIdList(List<String> saIdList) {
		this.saIdList = saIdList;
	}

	public List<String> getSaNameList() {
		return saNameList;
	}

	public void setSaNameList(List<String> saNameList) {
		this.saNameList = saNameList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
