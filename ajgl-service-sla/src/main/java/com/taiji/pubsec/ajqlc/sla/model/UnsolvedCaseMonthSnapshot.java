package com.taiji.pubsec.ajqlc.sla.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 未破案件月快照记录
 * @author chengkai
 *
 */
@Entity
@Table(name = "t_sla_wpajykzjl")
public class UnsolvedCaseMonthSnapshot {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	String id;
	
	/**
	 * 快照时间（yyyy-MM），如2017-2存的是2017-2-1 00:00:00 之前的快照，也就是1月份的快照记录
	 */
	private String snapshotTime;
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
	 * 案件类别编码
	 */
	private String caseSortCode;
	/**
	 * 案件类型
	 */
	private String caseClass;
	/**
	 * 办理单位
	 */
	private String dqbldw;
	/**
	 * 办理单位编码
	 */
	private String dqbldwCode;
	/**
	 * 主办侦查员
	 */
	private String investigator;
	/**
	 * 案件状态
	 */
	private String caseState;
	/**
	 * 发案时间
	 */
	private String caseTimeStart;
	/**
	 * 发案地点
	 */
	private String caseAddress;
	/**
	 * 简要案情
	 */
	private String details;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSnapshotTime() {
		return snapshotTime;
	}
	public void setSnapshotTime(String snapshotTime) {
		this.snapshotTime = snapshotTime;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
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
	public String getCaseSortCode() {
		return caseSortCode;
	}
	public void setCaseSortCode(String caseSortCode) {
		this.caseSortCode = caseSortCode;
	}
	public String getCaseClass() {
		return caseClass;
	}
	public void setCaseClass(String caseClass) {
		this.caseClass = caseClass;
	}
	public String getDqbldw() {
		return dqbldw;
	}
	public void setDqbldw(String dqbldw) {
		this.dqbldw = dqbldw;
	}
	public String getDqbldwCode() {
		return dqbldwCode;
	}
	public void setDqbldwCode(String dqbldwCode) {
		this.dqbldwCode = dqbldwCode;
	}
	public String getInvestigator() {
		return investigator;
	}
	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}
	public String getCaseState() {
		return caseState;
	}
	public void setCaseState(String caseState) {
		this.caseState = caseState;
	}
	public String getCaseTimeStart() {
		return caseTimeStart;
	}
	public void setCaseTimeStart(String caseTimeStart) {
		this.caseTimeStart = caseTimeStart;
	}
	public String getCaseAddress() {
		return caseAddress;
	}
	public void setCaseAddress(String caseAddress) {
		this.caseAddress = caseAddress;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
