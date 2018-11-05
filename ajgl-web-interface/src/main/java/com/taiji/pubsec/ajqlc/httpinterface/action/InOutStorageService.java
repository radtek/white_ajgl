package com.taiji.pubsec.ajqlc.httpinterface.action;

import java.util.List;

import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AjustmentFormItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AuthBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.FormItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.ResultBean;

public interface InOutStorageService {
	
	/**
	 * 获取返还单详情及细项信息
	 * @param formCode    返还单编码
	 */
	public ResultBean acquireBackStorageForm(AuthBean auth, String formCode);

	/**
	 * 获取入库单详情及细项信息
	 * @param formCode    入库单编码
	 */
	public ResultBean acquireInStorageForm(AuthBean auth, String formCode);

	/**
	 * 获取出库单详情及细项信息
	 * @param formCode    出库单编码
	 */
	public ResultBean acquireOutStorageForm(AuthBean auth, String formCode);

	/**
	 * 获取涉案物品在库详细信息
	 * @param articleCode
	 */
	public ResultBean acquireInvolvedArticle(AuthBean auth, String articleCode);

	/**
	 * 调整操作
	 * @param items
	 * @param formCode    调整单编号
	 */
	public ResultBean putAjustmentStorage(AuthBean auth, List<AjustmentFormItemBean> items, String formCode);

	/**
	 * 返还操作
	 * @param inStorageItems
	 * @param formCode    入库单编号
	 */
	public String putBackStorage(List<FormItemBean> inStorageItems, String formCode);

	/**
	 * 入库操作
	 * @param inStorageItems
	 * @param formCode    入库单编号
	 */
	public String putInStorage(List<FormItemBean> inStorageItems, String formCode);

	/**
	 * 出库操作
	 * @param items    出库单项
	 * @param formCode    出库单编号
	 */
	public String putOutStorage(List<FormItemBean> items, String formCode);

	/**
	 * 获取调整单详情及细项信息
	 * @param formCode    调整单号
	 */
	public ResultBean acquireAdjustmentForm(AuthBean auth, String formCode);
	
}
