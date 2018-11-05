package com.taiji.pubsec.ajqlc.operationrecord.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 涉案物品，物品操作记录
 * 
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_sawp_czjl")
public class OperationRecord {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "wpbh")
	private String articleCode; // 物品编号

	@Column(name = "bdbh")
	private String formCode; // 表单编号

	@Column(name = "bdid")
	private String formId; // 表单id

	@Column(name = "bdlx")
	private String formType; // 表单类型

	@Column(name = "czsl")
	private String number; // 操作数量

	@Column(name = "cznr")
	private String operation; // 操作内容

	@Column(name = "czsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operationTime; // 操作时间

	@Column(name = "czrxm")
	private String operator; // 操作人姓名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

}
