package com.taiji.pubsec.ajqlc.baq.bean;

import java.util.List;

public class LockerStockedArticleBean {
	
	private String lockerNum; //储物柜编号
	
	private String handlingAreaReceiptNum;	//使用单编号
	
	private String byQuestioningPeopleName;	//使用人
	
	private Long stockTime;	//存入时间
	
	private List<StoredGoodsBean> articles;	//在存物品信息
	
	private List<TemporaryRemovalBean> takeOutArticles;	//取出的物品信息

	public String getHandlingAreaReceiptNum() {
		return handlingAreaReceiptNum;
	}

	public void setHandlingAreaReceiptNum(String handlingAreaReceiptNum) {
		this.handlingAreaReceiptNum = handlingAreaReceiptNum;
	}

	public String getByQuestioningPeopleName() {
		return byQuestioningPeopleName;
	}

	public void setByQuestioningPeopleName(String byQuestioningPeopleName) {
		this.byQuestioningPeopleName = byQuestioningPeopleName;
	}

	public Long getStockTime() {
		return stockTime;
	}

	public void setStockTime(Long stockTime) {
		this.stockTime = stockTime;
	}

	public List<StoredGoodsBean> getArticles() {
		return articles;
	}

	public void setArticles(List<StoredGoodsBean> articles) {
		this.articles = articles;
	}

	public List<TemporaryRemovalBean> getTakeOutArticles() {
		return takeOutArticles;
	}

	public void setTakeOutArticles(List<TemporaryRemovalBean> takeOutArticles) {
		this.takeOutArticles = takeOutArticles;
	}

	public String getLockerNum() {
		return lockerNum;
	}

	public void setLockerNum(String lockerNum) {
		this.lockerNum = lockerNum;
	}

	
}
