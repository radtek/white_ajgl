package com.taiji.pubsec.ajgl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.web.action.BaseAction;
import com.taiji.pubsec.common.web.bean.ZTreeBean;
@Controller("dictionaryItemAction")
@Scope("prototype")
public class DictionaryItemAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 启用状态
	 */
	private static final String ENABLED = "1";
	
	@Resource
	private IDictionaryItemService dictionaryItemService;
	/**
	 * 字典项 查询结果列表
	 */
	private List<OptionItemBean> dictionaryItemLst = new ArrayList<OptionItemBean>();
	/**
	 * 字典项 查询结果树
	 */
	private List<ZTreeBean> ztreeList = new ArrayList<ZTreeBean>();
	/**
	 * 字典类型code
	 */
	private String dictionaryType;
	
	/**
	 * 根据字典类型Code获取字典项
	 * @return
	 */
	public String findDictionaryItemsByType(){
		List<DictionaryItem> rlt = dictionaryItemService.findDicItemsByTypeCode(dictionaryType, ENABLED);
		for (DictionaryItem tmp : rlt)
		{
			OptionItemBean bean = new OptionItemBean();
			bean.setId(tmp.getId());
			bean.setCode(tmp.getCode());
			bean.setName(tmp.getName());
			this.dictionaryItemLst.add(bean);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据字典类型Code获取字典项树数据
	 * @return
	 */
	public String findDictionaryItemsByTypeForTree(){
		List<DictionaryItem> dicLst = dictionaryItemService.findAllSubDictionaryItemsByTypeCode(dictionaryType);
		for(DictionaryItem dic : dicLst){
			ZTreeBean bean = new ZTreeBean();
			bean.setId(dic.getId());
			Map<Object,Object> diyMap = new HashMap<Object,Object>();
			diyMap.put("code", dic.getCode());
			bean.setDiyMap(diyMap);
			if(dic.getParentItem() != null){
				bean.setpId(dic.getParentItem().getId());
			}else{
				bean.setpId("");
			}
			if(dic.getSubItems() != null && dic.getSubItems().size() > 0){
				bean.setIsParent("true");
			}else{
				bean.setIsParent("false");
			}
			bean.setName(dic.getName());
			bean.setIcon("../images/xtgl_icon/ztree-icon_bm.png");
			ztreeList.add(bean);
		}
		return SUCCESS;
	}

	public List<OptionItemBean> getDictionaryItemLst() {
		return dictionaryItemLst;
	}

	public void setDictionaryItemLst(List<OptionItemBean> dictionaryItemLst) {
		this.dictionaryItemLst = dictionaryItemLst;
	}

	public String getDictionaryType() {
		return dictionaryType;
	}

	public void setDictionaryType(String dictionaryType) {
		this.dictionaryType = dictionaryType;
	}

	public List<ZTreeBean> getZtreeList() {
		return ztreeList;
	}

	public void setZtreeList(List<ZTreeBean> ztreeList) {
		this.ztreeList = ztreeList;
	}
	
}
