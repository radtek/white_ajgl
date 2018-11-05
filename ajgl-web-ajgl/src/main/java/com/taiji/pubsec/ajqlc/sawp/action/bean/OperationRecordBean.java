package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 涉案物品操作记录Bean
 * 
 * @author WangLei
 *
 */
public class OperationRecordBean {

	private String id;

	private String articleCode;// 涉案物品编码

	private String formCode;// 表单编号

	private String formId;// 表单id

	private String formType;// 表单类型

	private String number;// 操作数量

	private String operation;// 操作内容

	private Long operationTime;// 操作时间

	private String operator;// 操作人

	private String paperName;// 对应文书名称

	private String paperId;// 对应文书id

	private String paperType;// 对应文书类型

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

	public Long getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Long operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

}
