package com.taiji.pubsec.ajqlc.sawp.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;

/**
 * 出库单管理服务接口
 * @author wangfx
 *
 */
public interface IOutStorageFormService {
	
	/**
	 * 新建出库单
	 * @param outStorageFormBean 出库单表单数据
	 * @param outStorageFormItemList 出库单项信息列表
	 * @return 返回出库单的id
	 */
	String createForm(OutStorageFormServiceBean outStorageFormBean, List<OutStorageFormItemServiceBean> outStorageFormItemBeanList);
	
	/**
	 * 根据id查询出库单
	 * @param outStorageFormId 出门库id
	 * @return 出库单信息
	 */
	OutStorageForm findById(String outStorageFormId);
	
	/**
	 * 根据code查询出库单
	 * @param outStorageFormCode 出库单code
	 * @return 出库单信息
	 */
	OutStorageForm findByCode(String outStorageFormCode);
	
	/**
	 * 根据条件查询出库单信息
	 * @param paramMap 查询条件map
	 * </br>:code 出库单编号
	 * </br>:outStorageDateStart 出库日期开始时间(yyyy-MM-dd 00:00:00)
	 * </br>outStorageDateEnd 出库日期结束时间(yyyy-MM-dd 23:59:59)
	 * </br>:caseCode 案件编号
	 * </br>:suspectName 嫌疑人姓名
	 * </br>:isReturned 借出或其他出库物品是否返还
	 * </br>:papers 对应出库文书
	 * </br>:remark 备注
	 * </br>:type 出库类型
	 * </br>:isShowMyOutStorage 是否显示我的出库单（显示我的返还单时，:loginUser必须赋值）
	 * </br>:loginUser 当前登录人
	 * @param pageNo 页数
	 * @param pageSize 条数
	 * @return 出库单分页信息
	 * @throws ParseException 
	 */
	Pager<OutStorageForm> queryByCondition(Map<String, Object> paramMap, int pageNo, int pageSize);
	
	/**
	 * 获取出库单编号
	 * @return
	 */
	public String acquireNum();
	
	/**
	 * 根据出库单id删除出库单
	 * @param formId 出库单id
	 */
	public void deleteForm(String formId);
	
	/**
	 * 检查出库单是否有后续操作（返还）
	 * @param outStorageFormId 入库单Id
	 * @return boolean，当有后续操作时返回true，否则，返回false
	 */
	public boolean outStorageFormEditOrNot(String outStorageFormId); 
	
	/**
	 * 通过出库单id查询出库单项分页信息
	 * @param formId 出库单id
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回出库单项分页信息
	 */
	public Pager<OutStorageFormItem> findOutStorageFormItemsByFormIdForPage(String formId, int pageNo, int pageSize);
	
	/**
	 * 通过案件编号和出库类型查询没全部再入库的出库单list
	 * @param caseCode 案件编号
	 * @param types 出库类型list
	 * @return 返回出库单list
	 */
	public List<OutStorageForm> findOutStorageFormsByType(String caseCode, List<String> types);
	
	/**
	 * 更新出库单信息
	 * @param formCode 出库单编号 
 	 * @param outStorageFormItemServiceBeans 出库单项beanList
	 */
	public void updateForm(String formCode, List<OutStorageFormItemServiceBean> outStorageFormItemServiceBeans);
	
	/**
	 * 查询可出库的入库单的对应的案件编号list
	 * @return 返回案件编号list
	 */
	public List<String> findCaseCodesForOutStorage();
	
	/**
	 * 通过出库单项id查找出库单项
	 * @param itemId 出库单项id
	 * @return 返回出库单项信息
	 */
	public OutStorageFormItem findItemById(String itemId);
	
}
