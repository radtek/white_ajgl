package com.taiji.pubsec.ajqlc.baq.action.bean;

import java.util.List;

/**
 * 随身物品Bean
 * @author sunjd
 *
 */
public class CarryGoodsInfoBean {
	
    private String id;
	
	private PersonBean modifyPeopleBean;	// 最新修改人
	
	private String updatedTime;		// 最新修改时间
	
	private PersonBean recordModifyPeopleBean;	// 随身物品记录维护最新修改人
	
	private String recordUpdatedTime;	// 随身物品记录维护最新修改时间
	
	private PersonBean returnModifyPeopleBean;	// 随身物品返还维护最新修改人
	
	private String returnUpdatedTime;	// 随身物品返还维护最新修改时间
	
	private String handlingAreaReceiptId; 	//办案区使用单id

	private	List<CarryGoodsRecordBean> carryGoodsRecordBeans;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PersonBean getModifyPeopleBean() {
		return modifyPeopleBean;
	}

	public void setModifyPeopleBean(PersonBean modifyPeopleBean) {
		this.modifyPeopleBean = modifyPeopleBean;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public PersonBean getRecordModifyPeopleBean() {
		return recordModifyPeopleBean;
	}

	public void setRecordModifyPeopleBean(PersonBean recordModifyPeopleBean) {
		this.recordModifyPeopleBean = recordModifyPeopleBean;
	}

	public String getRecordUpdatedTime() {
		return recordUpdatedTime;
	}

	public void setRecordUpdatedTime(String recordUpdatedTime) {
		this.recordUpdatedTime = recordUpdatedTime;
	}

	public PersonBean getReturnModifyPeopleBean() {
		return returnModifyPeopleBean;
	}

	public void setReturnModifyPeopleBean(PersonBean returnModifyPeopleBean) {
		this.returnModifyPeopleBean = returnModifyPeopleBean;
	}

	public String getReturnUpdatedTime() {
		return returnUpdatedTime;
	}

	public void setReturnUpdatedTime(String returnUpdatedTime) {
		this.returnUpdatedTime = returnUpdatedTime;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}

	public List<CarryGoodsRecordBean> getCarryGoodsRecordBeans() {
		return carryGoodsRecordBeans;
	}

	public void setCarryGoodsRecordBeans(List<CarryGoodsRecordBean> carryGoodsRecordBeans) {
		this.carryGoodsRecordBeans = carryGoodsRecordBeans;
	}
	
}
