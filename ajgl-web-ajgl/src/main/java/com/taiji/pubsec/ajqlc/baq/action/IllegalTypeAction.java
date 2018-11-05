package com.taiji.pubsec.ajqlc.baq.action;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajqlc.baq.action.bean.IllegalTypeBean;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.baq.model.IllegalType;
import com.taiji.pubsec.ajqlc.baq.service.IIllegalTypeService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tool.spring.BeanCopierFmtUtil;


/**
 * 违规类型管理
 * 
 * @author XIEHF
 *
 */
@Controller("illegalTypeAction")
@Scope("prototype")
public class IllegalTypeAction extends ReturnMessageAction {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(IllegalTypeAction.class);
	
	@Resource
	private IIllegalTypeService illegalTypeService;
	
	@Resource
	private IDictionaryItemService dictionaryItemService; //数据字典接口	
	
	private IllegalType illegalType;                        // 违规类型Bean
	private IllegalTypeBean illegalTypeBean;                // 违规类型Bean
	private List<IllegalType> illegalTypeList = new ArrayList<IllegalType>();      // 违规类型 List
	private List<IllegalTypeBean> illegalTypeBeanList = new ArrayList<IllegalTypeBean>();      // 违规类型Bean List
	private String id;                                      // 违规类型id
    private String code;
    private String name;
    private String status;
    private List<String> idList = new ArrayList<String>();  // id集合
	
	/**
	 * 保存违规类型
	 * 
	 * @param illegalType
	 * @return "success" 返回flag,msg 成功时flag为"true" 失败时为"false"；msg为失败时的提示信息
	 */
	public String saveIllegalType() {
		if (!illegalTypeService.isDistinctIllegalTypeName(illegalType.getName(),"")) {
			this.setFlag(Boolean.FALSE.toString());
			this.setMsg("名称重复");
			return SUCCESS;
		}
		if (!illegalTypeService.isDistinctIllegalTypeCode(illegalType.getCode(),"")) {
			this.setFlag(Boolean.FALSE.toString());
			this.setMsg("编码重复");
			return SUCCESS;
		}
		illegalType.setOperator(this.findCurrentPerson().getName());
		illegalType.setUpdatedTime(new Date());
		illegalType.setIsSystemData(Constant.SF_F);
		illegalTypeService.createIllegalType(illegalType);
		this.setFlag(Boolean.TRUE.toString());
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	/**
	 * 查询违规类型记录
	 * 
	 * @return illegalTypeBean 集合
	 */
	public String queryIllegalTypeList() {		
		Pager<IllegalType> pager = illegalTypeService.findAllIllegalTypeByStatusForPage("",this.getStart() / this.getLength(), this.getLength());
		if (pager == null) {
			return SUCCESS;
		}
		
		illegalTypeList = pager.getPageList();
		if (null != illegalTypeList && illegalTypeList.size() > 0) {
			for (IllegalType entity : illegalTypeList) {
				IllegalTypeBean bean = illegalTypeToBean(entity);
				illegalTypeBeanList.add(bean);
			}
			this.setTotalNum(pager.getTotalNum());
		} 	
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	/**
	 * 查询违规类型记录
	 * 
	 * @return illegalTypeBean 集合
	 */
	public String queryIllegalTypeForQyList() {		
		illegalTypeList = illegalTypeService.findAllIllegalTypeByStatus(Constant.ZT_QY,false);
		if (null != illegalTypeList && illegalTypeList.size() > 0) {
			for (IllegalType entity : illegalTypeList) {
				IllegalTypeBean bean = illegalTypeToBean(entity);
				illegalTypeBeanList.add(bean);
			}
		} 
		IllegalType qt = illegalTypeService.findIllegalTypeByCode(Constant.WGLX_QT);
		if (null != qt) {
			illegalTypeBeanList.add(illegalTypeToBean(qt));
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询违规类型记录
	 * 
	 * @return illegalType 
	 */
	public String queryIllegalType() {		
		illegalType = illegalTypeService.findIllegalTypeById(id);
		illegalTypeBean = illegalTypeToBean(illegalType);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+id);
		return SUCCESS;
	}


	/**
	 * 启用/停用
	 * 
	 * @param illegalTypeBean 
	 * @return "success"
	 */
	public String updateIllegalTypeStatus() {
		for (String id : idList) {
			illegalTypeService.updateIllegalTypeState(id,status,this.findCurrentPerson().getName());
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+id+",status="+status+",operator="+this.findCurrentPerson().getName());
		return SUCCESS;
	}
	
	/**
	 * 删除违规类型。
	 * 
	 * @param idList    违规类型id 集合
	 * @return "success"
	 */
	public String deleteIllegalTypeByIds() {
		String[] idArr = idList.toArray(new String[idList.size()]);
		name = illegalTypeService.deleteIllegalType(idArr);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_DELETE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "ids="+idArr);
		return SUCCESS;
	}

	/**
	 * 更新违规类型
	 * 
	 * @param: illegalType 违规类型对象
	 * @return "success" 返回flag、msg 成功时flag为"true"，失败时flag为"false"；msg为失败时的提示信息
	 */
	public String updateIllegalType() {
		if (!illegalTypeService.isDistinctIllegalTypeName(illegalType.getName(),illegalType.getId())) {
			this.setFlag(Boolean.FALSE.toString());
			this.setMsg("名称重复");
			return SUCCESS;
		}
		if (!illegalTypeService.isDistinctIllegalTypeCode(illegalType.getCode(),illegalType.getId())) {
			this.setFlag(Boolean.FALSE.toString());
			this.setMsg("编码重复");
			return SUCCESS;
		}
		illegalType.setOperator(this.findCurrentPerson().getName());
		illegalType.setUpdatedTime(new Date());
		illegalType.setIsSystemData(Constant.SF_F);
		illegalTypeService.updateIllegalType(illegalType);
		this.setFlag(Boolean.TRUE.toString());
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	
	/**
	 * 违规类型model转bean
	 * 
	 * @param IllegalType 违规类型model对象
	 * @return bean 违规类型bean对象
	 */
	private IllegalTypeBean illegalTypeToBean(IllegalType illegalType) {
		if (illegalType == null) {
			return null;
		}
		IllegalTypeBean bean = new IllegalTypeBean();
		BeanCopierFmtUtil.copyBean(illegalType, bean, null);
		bean.setStatusName(findDictionaryItemNameById(illegalType.getStatus()));
		bean.setIsSystemDataStr(findDictionaryItemNameById(illegalType.getIsSystemData()));
		bean.setUpdatedTimeStr(dateToStr(illegalType.getUpdatedTime(),""));
		return bean;
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
	
	/**
	 * Date时间转化成打印字符串
	 * @param Date 毫秒数
	 * @param fmt 输出格式，默认为精确到分钟
	 * @return 
	 */
	private String dateToStr(Date date, String fmt){
		String str = "";
		if(date == null){
			return str;
		}
		if(StringUtils.isBlank(fmt)){
			fmt = "yyyy-MM-dd HH:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			str = sdf.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return str;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IllegalType getIllegalType() {
		return illegalType;
	}

	public void setIllegalType(IllegalType illegalType) {
		this.illegalType = illegalType;
	}

	public IllegalTypeBean getIllegalTypeBean() {
		return illegalTypeBean;
	}

	public void setIllegalTypeBean(IllegalTypeBean illegalTypeBean) {
		this.illegalTypeBean = illegalTypeBean;
	}

	

	public List<IllegalType> getIllegalTypeList() {
		return illegalTypeList;
	}

	public void setIllegalTypeList(List<IllegalType> illegalTypeList) {
		this.illegalTypeList = illegalTypeList;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<IllegalTypeBean> getIllegalTypeBeanList() {
		return illegalTypeBeanList;
	}

	public void setIllegalTypeBeanList(List<IllegalTypeBean> illegalTypeBeanList) {
		this.illegalTypeBeanList = illegalTypeBeanList;
	}

}
