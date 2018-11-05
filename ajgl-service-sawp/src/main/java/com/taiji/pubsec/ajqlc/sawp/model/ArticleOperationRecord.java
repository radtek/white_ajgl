package com.taiji.pubsec.ajqlc.sawp.model;

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
 * 涉案物品操作
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_sawp_sawpcz")
public class ArticleOperationRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	/**
	 * 操作人姓名
	 */
	@Column(name = "czrxm")
	private String operator;
	
	/**
	 * 操作内容
	 */
	@Column(name = "cznr")
	private String operation;
	
	/**
	 * 物品编号
	 */
	@Column(name = "wpbh")
	private String articleCode;
	
	/**
	 *操作时间 
	 */
	@Column(name = "czsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date OperationTime;
	
	/**
	 * 库单类型
	 */
	@Column(name = "bdlx")
	private String FormType;
	
	/**
	 *表单编码
	 */
	@Column(name = "bdbm")
	private String formCode;
	
	/**
	 * 表单数量
	 */
	@Column(name = "bdsl")
	private Double number;

	public String getId() {
		return id;
	}

	public String getOperator() {
		return operator;
	}

	public String getOperation() {
		return operation;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public Date getOperationTime() {
		return OperationTime;
	}

	public String getFormType() {
		return FormType;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public void setOperationTime(Date operationTime) {
		OperationTime = operationTime;
	}

	public void setFormType(String formType) {
		FormType = formType;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

}
