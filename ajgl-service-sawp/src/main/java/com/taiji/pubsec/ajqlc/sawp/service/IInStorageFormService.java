package com.taiji.pubsec.ajqlc.sawp.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.persistence.service.BaseService;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;

/**
 * 入库单服务接口
 * @author chengkai
 */
public interface IInStorageFormService extends BaseService<InStorageForm, String>{

	/**
	 * 创建入库单
	 * @param form 入库单bean
	 * @param items 入库单项list
	 * @return 返回新增的入库单的id
	 */
	public String createForm(InStorageFormServiceBean form, List<InStorageFormItemServiceBean> items) throws ParseException;

	/**
	 * 根据id获取入库单，内部查询每个入库单项对应的保存记录，设置到入库单项对象上。
	 * @param formId 入库单id
	 */
	public InStorageForm findById(String formId);

	/**
	 * 根据条件查询入库单信息
	 * @param conditions 查询条件包括:<br>
	 * 						入库单编号code，<br>
	 * 						入库时间起始startTime，<br>
	 * 						入库时间结束endTime，<br>
	 * 						案件编号caseCode，<br>
	 * 						嫌疑人姓名suspectName，<br>
	 * 						文书名称papers，<br>
	 * 						备注remark，<br>
	 * 						是否显示我的的入库单isShowMyForm，显示我的入库单时，:loginUser必须赋值，<br>
	 * 						当前登录人 loginUser 
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 入库单分页信息
	 */
	public Pager<InStorageForm> queryByCondition(Map<String,Object> conditions, int pageNo, int pageSize);

	/**
	 * 更新入库单信息，添加入库单项
	 * @param form 入库单bean
	 * @param items 入库单项list
	 */
	public void updateForm(InStorageFormServiceBean form, List<InStorageFormItemServiceBean> items) throws ParseException;
	
	/**
	 * 获取入库单编号
	 * @return
	 */
	public String acquireNum();
	
	/**
	 * 入库单转换为Bean
	 * @param inStorageForm 入库单model
	 *  
	 * @return InStorageFormBean 入库单Bean
	 */
	public InStorageFormServiceBean inStorageFormToBean(InStorageForm inStorageForm);
	
	/**
	 * 入库单项转换为Bean
	 * @param inStorageFormItem 入库单项model
	 *  
	 * @return InStorageFormItemBean
	 */
	public InStorageFormItemServiceBean inStorageFormItemToBean(InStorageFormItem inStorageFormItem);
	
	/**
	 * 检查入库单是否有后续操作（出库，调整）
	 * @param inStorageFormId 入库单Id
	 * @return boolean，当有后续操作时返回true，否则，返回false
	 */
	public boolean inStorageFormEditOrNot(String inStorageFormId); 
	
	/**
	 * 通过入库单id删除入库单
	 * @param formId 入库单id
	 */
	public void deleteFormById(String formId);
	
	/**
	 * 通过入库单id查询入库单项分页信息
	 * @param formId 入库单id
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回入库单项分页信息
	 */
	public Pager<InStorageFormItem> findInStorageFormItemsByFormIdForPage(String formId, int pageNo, int pageSize);
	
	/**
	 * 通过入库单id查询入库单项信息
	 * @param formId 入库单id
	 * @return 返回入库单项信息
	 */
	public List<InStorageFormItem> findInStorageFormItemsByFormId(String formId);
	
	/**
	 * 通过案件编号和嫌疑人id查入库单。
	 * @param caseCode 案件编号  suspectId 嫌疑人id
	 * @return 返回入库单信息
	 */
	public InStorageForm findInStorageFormByCaseCodeAndSuspectId(String caseCode,String suspectId);
	
	/**
	 * 通过物品编号和储物架编号查找入库单项信息
	 * @param articleCode 物品编号
	 * @param lockerId 储物架id
	 * @return 返回入库单项信息
	 */
	public InStorageFormItem findInStorageFormItemsByArticleCodeAndLockerId(String articleCode,String lockerId);
	
	/**
	 * 通过入库单code获取入库单详情及细项信息
	 * @param formCode 入库单编号
	 * @return 返回入库单详情及细项信息
	 */
	public InStorageForm findInStorageFormByFormCode(String formCode);
	
	/**
	 * 通过文书id和文书类型查询入库单
	 * @param docId 文书id
	 * @param docType 文书类型
	 * @return 返回入库单信息
	 */
	public InStorageForm findInStorageFormByDocIdAndDocType(String docId, String docType);
	
}