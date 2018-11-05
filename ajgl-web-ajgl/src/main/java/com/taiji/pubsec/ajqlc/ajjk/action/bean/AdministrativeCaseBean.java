package com.taiji.pubsec.ajqlc.ajjk.action.bean;

public class AdministrativeCaseBean {
	/**
	 * 序号（页面查询不使用，导出excel使用）
	 */
	private String num;
	/**
	 * 案件编码
	 */
	private String caseCode;
	/**
	 * 案件名称
	 */
	private String CaseName;
	/**
	 * 办案单位
	 */
	private String sponsor;
	/**
	 * 主办侦查员
	 */
	private String investigator;
	/**
	 * 法制审核人
	 */
	private String verifier;
	/**
	 * 案件关注状态
	 */
	private String attention;
	/**
	 * 维护人/维护时间
	 */
	private String servicingInfo;
	/**
	 * 行政拘留
	 */
	private String xingzhengjuliu;
	/**
	 * 警告
	 */
	private String jinggao;
	/**
	 * 罚款
	 */
	private String fakuan;
	/**
	 * 社区戒毒
	 */
	private String shequjiedu;
	/**
	 * 强制隔离戒毒
	 */
	private String qiangzhigelijiedu; 
	/**
	 * 案件结案审批表
	 */
	private String anjianjieanshenpibiao;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getCaseName() {
		return CaseName;
	}
	public void setCaseName(String caseName) {
		CaseName = caseName;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public String getInvestigator() {
		return investigator;
	}
	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public String getServicingInfo() {
		return servicingInfo;
	}
	public void setServicingInfo(String servicingInfo) {
		this.servicingInfo = servicingInfo;
	}
	public String getXingzhengjuliu() {
		return xingzhengjuliu;
	}
	public void setXingzhengjuliu(String xingzhengjuliu) {
		this.xingzhengjuliu = xingzhengjuliu;
	}
	public String getJinggao() {
		return jinggao;
	}
	public void setJinggao(String jinggao) {
		this.jinggao = jinggao;
	}
	public String getFakuan() {
		return fakuan;
	}
	public void setFakuan(String fakuan) {
		this.fakuan = fakuan;
	}
	public String getShequjiedu() {
		return shequjiedu;
	}
	public void setShequjiedu(String shequjiedu) {
		this.shequjiedu = shequjiedu;
	}
	public String getQiangzhigelijiedu() {
		return qiangzhigelijiedu;
	}
	public void setQiangzhigelijiedu(String qiangzhigelijiedu) {
		this.qiangzhigelijiedu = qiangzhigelijiedu;
	}
	public String getAnjianjieanshenpibiao() {
		return anjianjieanshenpibiao;
	}
	public void setAnjianjieanshenpibiao(String anjianjieanshenpibiao) {
		this.anjianjieanshenpibiao = anjianjieanshenpibiao;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}
