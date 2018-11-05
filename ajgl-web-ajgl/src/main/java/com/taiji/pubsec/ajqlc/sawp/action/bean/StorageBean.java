package com.taiji.pubsec.ajqlc.sawp.action.bean;

import java.util.ArrayList;
import java.util.List;

import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;

/**
 * 保管位置Bean
 * 
 * @author WangLei
 *
 */
public class StorageBean {

	private String id;

	private Double existingNumber;// 在库数量

	private Double incomingNumber;// 保存数量

	private String lockerId;// 储物箱id

	private String lockerCode;// 储物箱编号

	private String lockerLocation;// 储物箱位置描述

	private String lockerRemark;// 储物箱备注
	
	private String areaId; // 保管区Id

	private String areaName; // 保管区域名称

	private InvolvedArticleBean article;// 涉案物品

	private ArticleLockerBean locker;// 储物箱

	private String itemId; // 入库单项ID
	
	private List<ArticleLocker> articleLockerList = new ArrayList<ArticleLocker>();
	
	private String policeName;//办案民警

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getExistingNumber() {
		return existingNumber;
	}

	public void setExistingNumber(Double existingNumber) {
		this.existingNumber = existingNumber;
	}

	public Double getIncomingNumber() {
		return incomingNumber;
	}

	public void setIncomingNumber(Double incomingNumber) {
		this.incomingNumber = incomingNumber;
	}

	public InvolvedArticleBean getArticle() {
		return article;
	}

	public void setArticle(InvolvedArticleBean article) {
		this.article = article;
	}

	public ArticleLockerBean getLocker() {
		return locker;
	}

	public void setLocker(ArticleLockerBean locker) {
		this.locker = locker;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getLockerId() {
		return lockerId;
	}

	public void setLockerId(String lockerId) {
		this.lockerId = lockerId;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public String getLockerLocation() {
		return lockerLocation;
	}

	public void setLockerLocation(String lockerLocation) {
		this.lockerLocation = lockerLocation;
	}

	public String getLockerRemark() {
		return lockerRemark;
	}

	public void setLockerRemark(String lockerRemark) {
		this.lockerRemark = lockerRemark;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<ArticleLocker> getArticleLockerList() {
		return articleLockerList;
	}

	public void setArticleLockerList(List<ArticleLocker> articleLockerList) {
		this.articleLockerList = articleLockerList;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

}
