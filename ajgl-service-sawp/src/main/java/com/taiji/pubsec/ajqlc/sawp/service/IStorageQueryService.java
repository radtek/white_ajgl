package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.ArticleInvolvedServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.LockerStatisticsServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;

/**
 * @author chengkai
 */
public interface IStorageQueryService {

	/**
	 * 获取涉案物品的出库数量
	 * @param articleId 涉案物品id
	 * @return 返回该涉案物品的出库数量
	 */
	public int acquireOutcomingCount(String articleId);

	/**
	 * 获取涉案物品的返还数量
	 * @param articleId 涉案物品id
	 * @return 返回该涉案物品的返还数量
	 */
	public int acquireReturnedCount(String articleId);

	/**
	 * 判断储物柜中是否没有任何物品并且可用
	 * @param lockerId 储物柜id
	 * @return 当该储物柜没有任何物品且可用时返回true，否则返回false
	 */
	public boolean isAvailableWithoutAnything(String lockerId);

	/** 
	 * 查询涉案物品情况。出库数量和返还数量在按条件查询完后依次调用acquireOutcomingCount和acquireReturnedCount获取。
	 * 
	 * @param conditions    查询条件conditions包括：<br>
	 * 物品编号code，<br>
	 * 物品名称及特征nameOrFeature， <br>
	 * 对应案件编号/名称caseCodeOrName， <br>
	 * 对应犯罪嫌疑人姓名/身份证号suspectNameOrIdCardNo， <br>
	 * 办案民警姓名polices， <br>
	 * 对应入库文书paper， <br>
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回涉案物品分页信息
 	 */
	public Pager<ArticleInvolvedServiceBean> queryArticleInvolved(Map<String,Object> conditions, int pageNo, int pageSize);

	/**
	 * 查询涉案物品台账
	 * @param conditions	查询条件conditions包括：<br>
	 * 							包括物品名称nameAndFeature，<br>
	 * 							案件编号caseCode，<br>
	 * 							嫌疑人姓名suspectNameOrNumber，<br>
	 * 							所在涉案物品保管区id lockArea <br>
	 * 							保管柜lockerIds，List类型，<br>
	 * 							入库时间起始startTime，<br>
	 * 							入库时间结束endTime，<br>
	 * 							办案民警名称或警号（模糊匹配） disposePerson <br>
	 * 							是否包含在库数量为0的 isZero
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回涉案物品台账查询分页信息
	 */
	public Pager<Storage> queryArticleInvolvedInStorage(Map<String,Object> conditions, int pageNo, int pageSize);
	
	/**
	 * 查询储物柜存储情况
	 * 
	 * @param conditions    查询条件conditions包括：<br>
	 * 物证保管区id areaId，<br>
	 * 是否空闲isOrNotFree，<br>
	 * 对应案件编号/名称caseCodeOrName，<br>
	 * 办案民警姓名polices<br>
	 * 对应犯罪嫌疑人姓名/身份证号suspectNameOrIdCardNo，<br>
	 * 储物柜id lockerIds
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回储物柜存储情况分页信息
	 */
	public Pager<LockerStatisticsServiceBean> findLockersByConditions(Map<String,Object> conditions, int pageNo, int pageSize);
	
//	/**
//	 * 以Storage类为中心查询储物柜存储情况。是否空闲信息调用isAvailableWithoutAnything对初次查询结果进行再次判断
//	 * 
//	 * @param conditions    查询条件conditions包括：<br>
//	 * 物证保管区id areaId，<br>
//	 * 是否空闲isOrNotFree，<br>
//	 * 对应案件编号/名称caseCodeOrName，<br>
//	 * 办案民警姓名polices<br>
//	 * 对应犯罪嫌疑人姓名/身份证号suspectNameOrIdCardNo，<br>
//	 * 储物柜id lockerIds
//	 * @param pageNo 页码
//	 * @param pageSize 页面大小
//	 * @return 返回储物柜存储情况分页信息
//	 */
//	public Pager<StorageServiceBean> queryStorageInLocker(Map<String,Object> conditions, int pageNo, int pageSize);

}