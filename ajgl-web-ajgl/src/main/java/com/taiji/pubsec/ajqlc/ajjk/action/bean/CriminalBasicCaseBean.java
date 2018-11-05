package com.taiji.pubsec.ajqlc.ajjk.action.bean;

public class CriminalBasicCaseBean {
	/**
	 * id
	 */
	private String id;
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 案件名称
	 */
	private String caseName;
	/**
	 * 案件类别
	 */
	private String caseSort;
	/**
	 * 案件类型
	 */
	private String caseClass;
	/**
	 * 案件性质
	 */
	private String caseKind;
	/**
	 * 案件状态
	 */
	private String caseState;
	/**
	 * 发案时间起
	 */
	private Long caseTimeStart;
	/**
	 * 发案辖区 alarmInfo.popedom
	 */
	private String popedom;
	/**
	 * 发案社区
	 */
	private String community;
	/**
	 * 发案地点
	 */
	private String caseAddress;
	/**
	 * 发案时段
	 */
	private String caseTimeFrame;
	/**
	 * 主案标识
	 */
	private String principal;
	/**
	 * 办案单位
	 */
	private String handingUnit;
	/**
	 * 办案民警
	 */
	private String disposePerson;
	/**
	 * 嫌疑人Id
	 */
	private String suspectPersonId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseSort() {
		return caseSort;
	}

	public void setCaseSort(String caseSort) {
		this.caseSort = caseSort;
	}

	public String getCaseKind() {
		return caseKind;
	}

	public void setCaseKind(String caseKind) {
		this.caseKind = caseKind;
	}

	public String getCaseState() {
		return caseState;
	}

	public void setCaseState(String caseState) {
		this.caseState = caseState;
	}

	public Long getCaseTimeStart() {
		return caseTimeStart;
	}

	public void setCaseTimeStart(Long caseTimeStart) {
		this.caseTimeStart = caseTimeStart;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getCaseAddress() {
		return caseAddress;
	}

	public void setCaseAddress(String caseAddress) {
		this.caseAddress = caseAddress;
	}

	public String getCaseTimeFrame() {
		return caseTimeFrame;
	}

	public void setCaseTimeFrame(String caseTimeFrame) {
		this.caseTimeFrame = caseTimeFrame;
	}

	public String getPopedom() {
		return popedom;
	}

	public void setPopedom(String popedom) {
		this.popedom = popedom;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getSuspectPersonId() {
		return suspectPersonId;
	}

	public void setSuspectPersonId(String suspectPersonId) {
		this.suspectPersonId = suspectPersonId;
	}

	public String getDisposePerson() {
		return disposePerson;
	}

	public void setDisposePerson(String disposePerson) {
		this.disposePerson = disposePerson;
	}

	public String getHandingUnit() {
		return handingUnit;
	}

	public void setHandingUnit(String handingUnit) {
		this.handingUnit = handingUnit;
	}

	public String getCaseClass() {
		return caseClass;
	}

	public void setCaseClass(String caseClass) {
		this.caseClass = caseClass;
	}

}
