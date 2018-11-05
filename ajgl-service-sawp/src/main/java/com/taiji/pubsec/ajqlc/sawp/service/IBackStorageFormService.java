package com.taiji.pubsec.ajqlc.sawp.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageFormItem;

/**
 * 返还单服务接口
 * @author wangfx
 *
 */
public interface IBackStorageFormService {
	
	/**
	 * 创建返还单，添加返还单项，保管记录、物品信息及保管信息和返还单项的关系记录。
	 * @param backStorageFormBean 返还单信息
	 * @param backStorageFormItemBeanList 返还单项信息列表
	 * @throws ParseException 时间str2date格式转换异常
	 * @return 返回新增的返还单的id
	 */
	String createForm(BackStorageFormServiceBean backStorageFormBean, List<BackStorageFormItemServiceBean> backStorageFormItemBeanList);

	/**
	 * 根据id查询返还单
	 * @param backStorageFormId 返还单id
	 * @return
	 */
	BackStorageForm findById(String backStorageFormId);
	/**
	 * 根据出库单查询对应的返还单
	 * @param outStorageFormCode 出库单编码
	 * @return 返还单
	 */ 
	BackStorageForm findByOutStorageForm(String outStorageFormCode);
	/**
	 * 根据条件查询返还单信息。
	 * @param paramMap 查询条件map
	 * </br>:backStorageFormCode 返还单编码
	 * </br>:backTimeStart 返还日期开始时间("yyyy-MM-dd 00:00:00")
	 * </br>:backTimeEnd 返还日期结束时间("yyyy-MM-dd 23:59:59")
	 * </br>:outStorageFormCode 对应的出库单编号
	 * </br>:caseCode 案件编号
	 * </br>:remark 备注
	 * </br>:type 出库类型
	 * </br>:isShowMyBackStorage 是否显示我的返还单（显示我的返还单时，loginUser必须赋值）
	 * </br>:loginUser 当前登录人
	 * @param pageNo 页数
	 * @param pageSize 条数
	 * @return 返还单分页信息
	 * @throws ParseException 时间str2date格式转换异常
	 */
	Pager<BackStorageForm> queryByCondition(Map<String, Object> paramMap, int pageNo, int pageSize);
	
	/**
	 * 更新返还单信息，添加返还单项，保管记录、物品信息及保管信息和返还单项的关系记录。
	 * @param backStorageFormBean 返还单信息
	 * @param backStorageFormItemList 返还单项信息列表
	 * @throws ParseException 时间str2date格式转换异常
	 */
	void updateForm(BackStorageFormServiceBean backStorageFormBean, List<BackStorageFormItemServiceBean> backStorageFormItemList) ;
	
	/**
	 * 获取返还单编号
	 * @return
	 */
	public String acquireNum();
	
	/**
	 * 通过出库单项id查找相应的返还单项list
	 * @param outStorageFormItemId 出库单项
	 * @return 返还单项list
	 */
	public List<BackStorageFormItem> findBackStorageFormItemsByOutStorageFormItemId(String outStorageFormItemId);
	
	/**
	 * 删除返还单
	 * @param formId 返还单id
	 */
	public void deleteForm(String formId);
	
	/**
	 * 通过出库单id查询返还单项beanList
	 * @param outFormId 出库单id
	 * @return 返还单项beanList
	 */
	public List<BackStorageFormItemServiceBean> findBackStorageFormItemServiceBeanByOutFormId(String outFormId);
	
	/**
	 * 通过出库单编码查询返还单
	 * @param outFormCode 出库单编码
	 * @return 返回返还单信息
	 */
	public BackStorageForm findBackStorageFormByOutFormCode(String outFormCode);
	
	/**
	 * 通过涉案物品id和储物柜id查询最近时间的返还单项
	 * @param articleId 涉案物品id
	 * @param lockerId 储物柜id
	 * @return 返回返还单项信息
	 */
	public BackStorageFormItem findBackStorageFormItemByArticleIdAndLockerId(String articleId, String lockerId);
	
	/**
	 * 通过返还单编号查找返还单详情及细项信息
	 * @param formCode 返还单编号
	 * @return 返还单详情及细项信息
	 */
	public BackStorageForm findBackStorageFormsByFormCode(String formCode);
	
	/**
	 * 通过返还单项id查找返还单项
	 * @param itemId 返还单项id
	 * @return 返回返还单项信息
	 */
	public BackStorageFormItem findItemById(String itemId);
}
