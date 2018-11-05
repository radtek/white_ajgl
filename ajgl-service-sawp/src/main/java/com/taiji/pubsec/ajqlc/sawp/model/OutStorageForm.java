package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.taiji.pubsec.ajqlc.sawp.service.IBackStorageFormService;
import com.taiji.pubsec.common.tool.spring.SpringContextUtil;


/**
 * 出库单
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_ckd")
public class OutStorageForm extends CommonForm {
	
	@Column(name = "ajbh")
	private String caseCode;	// 案件编号
	
	@Column(name = "sfqbfh")
	private String isReturned;	// 是否全部返回，只针对借出和其他类型出库单
	
	@Column(name = "wsmc")
	private String papers;	// 文书名称，各物品关联的文书名称集合
	
	@Column(name = "lqr")
	private String receiver;	// 领取人
	
	@Column(name = "cklx")
	private String type;	// 出库类型，字典项id
	
	@Column(name = "ajmc")
	private String caseName;	//案件名称
	
	@Column(name = "bamj")
	private String handlePolices;	//办案民警
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cksj", nullable = false)
	private Date outStorageTime;	// 出库时间
	
	@OneToMany(mappedBy = "form")
	private Set<OutStorageFormItem> outStorageFormItems = new HashSet<OutStorageFormItem>();	// 出库单项
	
	/**
	 * @return caseCode 案件编号
	 */
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
	/**
	 * @return isReturned 是否全部返回，只针对借出和其他类型出库单
	 */
	public String getIsReturned() {
		return isReturned;
	}

	public void setIsReturned(String isReturned) {
		this.isReturned = isReturned;
	}
	
	/**
	 * @return papers 文书名称，各物品关联的文书名称集合
	 */
	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}
	
	/**
	 * @return receiver 领取人
	 */
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * @return type 出库类型，字典项id
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return outStorageTime 出库时间
	 */
	public Date getOutStorageTime() {
		return outStorageTime;
	}

	public void setOutStorageTime(Date outStorageTime) {
		this.outStorageTime = outStorageTime;
	}

	/**
	 * @return outStorageFormItems 出库单项
	 */
	public Set<OutStorageFormItem> getOutStorageFormItems() {
		return outStorageFormItems;
	}
	
	/**
	 * @return caseName 案件名称
	 */
	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	/**
	 * @return handlePolices 办案民警
	 */
	public String getHandlePolices() {
		return handlePolices;
	}

	public void setHandlePolices(String handlePolices) {
		this.handlePolices = handlePolices;
	}

	public void setOutStorageFormItems(Set<OutStorageFormItem> outStorageFormItems) {
		this.outStorageFormItems = outStorageFormItems;
	}

	public BackStorageForm getBackStorageForm() {
		IBackStorageFormService backStorageFormService = (IBackStorageFormService) SpringContextUtil.getBean("backStorageFormService");
		BackStorageForm backStorageForm = backStorageFormService.findByOutStorageForm(getCode());
		return backStorageForm;
	}

}