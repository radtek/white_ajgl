package com.taiji.pubsec.ajqlc.ajjk.action.bean;

public class PenalCaseBean {
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
	 * 拘传
	 */
	private String juchuan;
	/**
	 * 刑拘
	 */
	private String xingju;
	/**
	 * 延期
	 */
	private String yanqi;
	/**
	 * 提请逮捕
	 */
	private String tiqingdaibu;
	/**
	 * 取保
	 */
	private String qubao;
	/**
	 * 监视居住
	 */
	private String jianshijuzhu;
	/**
	 * 释放
	 */
	private String shifang;
	/**
	 * 逮捕
	 */
	private String daibu;
	/**
	 * 移送起诉
	 */
	private String yisongqisu;
	/**
	 * 是否已归档
	 */
	private String placeOnFile;
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

	public String getJuchuan() {
		return juchuan;
	}

	public void setJuchuan(String juchuan) {
		this.juchuan = juchuan;
	}

	public String getXingju() {
		return xingju;
	}

	public void setXingju(String xingju) {
		this.xingju = xingju;
	}

	public String getYanqi() {
		return yanqi;
	}

	public void setYanqi(String yanqi) {
		this.yanqi = yanqi;
	}

	public String getTiqingdaibu() {
		return tiqingdaibu;
	}

	public void setTiqingdaibu(String tiqingdaibu) {
		this.tiqingdaibu = tiqingdaibu;
	}

	public String getQubao() {
		return qubao;
	}

	public void setQubao(String qubao) {
		this.qubao = qubao;
	}

	public String getJianshijuzhu() {
		return jianshijuzhu;
	}

	public void setJianshijuzhu(String jianshijuzhu) {
		this.jianshijuzhu = jianshijuzhu;
	}

	public String getShifang() {
		return shifang;
	}

	public void setShifang(String shifang) {
		this.shifang = shifang;
	}

	public String getDaibu() {
		return daibu;
	}

	public void setDaibu(String daibu) {
		this.daibu = daibu;
	}

	public String getYisongqisu() {
		return yisongqisu;
	}

	public void setYisongqisu(String yisongqisu) {
		this.yisongqisu = yisongqisu;
	}

	public String getPlaceOnFile() {
		return placeOnFile;
	}

	public void setPlaceOnFile(String placeOnFile) {
		this.placeOnFile = placeOnFile;
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
