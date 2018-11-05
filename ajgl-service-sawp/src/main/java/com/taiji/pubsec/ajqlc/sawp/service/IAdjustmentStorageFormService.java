package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustgmentStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustmentStorageFormItem;

/**
 * 调整单管理服务接口
 * @author chengkai
 */
public interface IAdjustmentStorageFormService {

	/**
	 * 创建调整单，添加调整单项，更新保管信息
	 * @param form 调整单数据信息
	 * @param items 调整单项列表信息
	 * @return 返回新增的调整单的id
	 */
	public String createForm(AdjustmentStorageFormServiceBean form, List<AdjustmentStorageFormItemServiceBean> items);

	/**
	 * 根据id获取调整单
	 * @param formId 调整单id
	 * @return 调整单实体信息 
	 */
	public AdjustgmentStorageForm findById(String formId);

	/**
	 * 根据条件查询调整单信息
	 * 
	 * @param conditions    查询条件conditions包括：
	 * 							调整单编号code，<br>
	 * 							调整开始日期startTime，<br>
	 * 							调整结束日期endTime 
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回调整单分页信息
	 */
	public Pager<AdjustgmentStorageForm> queryByCondition(Map<String,Object> conditions, int pageNo, int pageSize);
	
	/**
	 * 获取调整单编号
	 * @return
	 */
	public String acquireNum();
	
	/**
	 * 通过调整单编码获取调整单详情及细项信息
	 * @param formCode 调整单编码
	 * @return 返回获取调整单详情及细项信息
	 */
	public AdjustgmentStorageForm findByCode(String formCode);
	
	/**
	 * 更新调整单及其单项内容
	 * @param formCode 调整单编码
	 * @param adjustmentStorageFormItemServiceBeans 调整单项list
	 */
	public void updateForm(String formCode, List<AdjustmentStorageFormItemServiceBean> adjustmentStorageFormItemServiceBeans);
	
	/**
	 * 通过调整单项查找调整单项
	 * @param itemId 调整单项id
	 * @return 返回调整单项信息
	 */
	public AdjustmentStorageFormItem findItemById(String itemId);

}