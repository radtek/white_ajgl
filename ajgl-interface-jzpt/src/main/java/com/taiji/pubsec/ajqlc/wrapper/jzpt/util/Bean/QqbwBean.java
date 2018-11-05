package com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean;



import org.springframework.beans.factory.annotation.Required;

/**
 * 此类Bean 主要用于以 <SRmessage>开头的请求报文
 * 请求报文Bean
 * @author xinfan
 *
 */
public class QqbwBean {
	/**
	 * 访问请求者名称 (必填)
	 */
	
	private String fwqqzmc; 
	/**
	 * 访问请求者标识 (必填)
	 */
	private String fwqqzbs;
	
	/**
	 * 访问请求者ip地址 (必填)
	 */
	private String fwqqzipdz;
	/**
	 * 访问请求者端口号 (必填)
	 */
    private String fwqqzdkh;
    /**
     * 访问请求者联系人姓名（必填）
     */
    private String lxrxm;
    /**
     * 访问请求者联系人电话 （必填）
     */
    private String lxrdh;
    /**
     * 访问请求者身份证号（必填）
     */
    private String lxrsfzh; 
    /**
     * 所申请的服务标识 如（SZNB52000000000000073） （必填）
     */
    private String fwbs;
    /**
     * 服务总线标识 （必填）
     */
    private String fwzxbs;
    /**
     * 服务请求时间 （可以随意填 建议填当前时间  如 20170116154640 ）（必填）
     */
    private String fwqqrqsj; 
    /**
     * 服务调用ID （个人理解为调用那个表） 。此id在 每一个服务的请求报文里面有  （必填）
     *  
     */
    private String  fwdyid;
    /**
     * 查询条件  格式如 ：<Item><Data>CASESTATE='案件办结归档</Data> </Item>。<Data></Data> 为添加的查询条件，至少有一个
     */
    private String cxtj;
    /**
     * 返回的结果字段定义,不填的话返回全部字段   格式如：<Row><Data>YXTBH</Data></Row>
     */
    private String resultFields;
    /**
     * 信息操作人员姓名 （必填）
     */
    private String xxczryxm;
    /**
     * 信息操作人员身份证号 （必填）
     */
    private String xxczrysfzh;
    /**
     * 信息操作人员机构代码 （必填）
     */
    private String xxczryjgdm;
    /**
     * 信息操作人员职务级别代码 （选填）
     */
    private String xxczryzwjbdm;
    /**
     * 信息操作人员岗位权级代码 （选填）
     */
    private String xxczrygwqjdm;
    /**
     * 信息操作人员行政区划代码 （选填）
     */
    private String xxczryxzqhdm;
    /**
     * 信息操作人员秘密等级代码 （选填）
     */
    private String xxczrymmdjdm;
    /**
     * 信息操作人员警种代码 （选填）
     */
    private String xxczryjzdm;
    /**
     * 异步请求标识   （选填）
     */
    private String ybqqbs;
    /**
     * 截至期限
     */
    private String jzqx;
    
    public String getFwqqzmc() {
		return fwqqzmc;
	}
    @Required
	public void setFwqqzmc(String fwqqzmc) {
		this.fwqqzmc = fwqqzmc;
	}
	
	public String getFwqqzipdz() {
		return fwqqzipdz;
	}
	@Required
	public void setFwqqzipdz(String fwqqzipdz) {
		this.fwqqzipdz = fwqqzipdz;
	}
	public String getFwqqzdkh() {
		return fwqqzdkh;
	}
	public void setFwqqzdkh(String fwqqzdkh) {
		this.fwqqzdkh = fwqqzdkh;
	}
	public String getLxrxm() {
		return lxrxm;
	}
	public void setLxrxm(String lxrxm) {
		this.lxrxm = lxrxm;
	}
	public String getLxrdh() {
		return lxrdh;
	}
	public void setLxrdh(String lxrdh) {
		this.lxrdh = lxrdh;
	}
	public String getLxrsfzh() {
		return lxrsfzh;
	}
	public void setLxrsfzh(String lxrsfzh) {
		this.lxrsfzh = lxrsfzh;
	}
	public String getFwbs() {
		return fwbs;
	}
	public void setFwbs(String fwbs) {
		this.fwbs = fwbs;
	}
	public String getFwzxbs() {
		return fwzxbs;
	}
	public void setFwzxbs(String fwzxbs) {
		this.fwzxbs = fwzxbs;
	}
	public String getFwqqrqsj() {
		return fwqqrqsj;
	}
	public void setFwqqrqsj(String fwqqrqsj) {
		this.fwqqrqsj = fwqqrqsj;
	}
	public String getFwdyid() {
		return fwdyid;
	}
	public void setFwdyid(String fwdyid) {
		this.fwdyid = fwdyid;
	}
	public String getCxtj() {
		return cxtj;
	}
	public void setCxtj(String cxtj) {
		this.cxtj = cxtj;
	}
	public String getResultFields() {
		return resultFields;
	}
	public void setResultFields(String resultFields) {
		this.resultFields = resultFields;
	}
	public String getXxczryxm() {
		return xxczryxm;
	}
	public void setXxczryxm(String xxczryxm) {
		this.xxczryxm = xxczryxm;
	}
	public String getXxczrysfzh() {
		return xxczrysfzh;
	}
	public void setXxczrysfzh(String xxczrysfzh) {
		this.xxczrysfzh = xxczrysfzh;
	}
	public String getXxczryjgdm() {
		return xxczryjgdm;
	}
	public void setXxczryjgdm(String xxczryjgdm) {
		this.xxczryjgdm = xxczryjgdm;
	}
	public String getXxczryzwjbdm() {
		return xxczryzwjbdm;
	}
	public void setXxczryzwjbdm(String xxczryzwjbdm) {
		this.xxczryzwjbdm = xxczryzwjbdm;
	}
	public String getXxczrygwqjdm() {
		return xxczrygwqjdm;
	}
	public void setXxczrygwqjdm(String xxczrygwqjdm) {
		this.xxczrygwqjdm = xxczrygwqjdm;
	}
	public String getXxczryxzqhdm() {
		return xxczryxzqhdm;
	}
	public void setXxczryxzqhdm(String xxczryxzqhdm) {
		this.xxczryxzqhdm = xxczryxzqhdm;
	}
	public String getXxczrymmdjdm() {
		return xxczrymmdjdm;
	}
	public void setXxczrymmdjdm(String xxczrymmdjdm) {
		this.xxczrymmdjdm = xxczrymmdjdm;
	}
	public String getXxczryjzdm() {
		return xxczryjzdm;
	}
	public void setXxczryjzdm(String xxczryjzdm) {
		this.xxczryjzdm = xxczryjzdm;
	}
	public String getYbqqbs() {
		return ybqqbs;
	}
	public void setYbqqbs(String ybqqbs) {
		this.ybqqbs = ybqqbs;
	}
	public String getJzqx() {
		return jzqx;
	}
	public void setJzqx(String jzqx) {
		this.jzqx = jzqx;
	}
	public String getFwqqzbs() {
		return fwqqzbs;
	}
	public void setFwqqzbs(String fwqqzbs) {
		this.fwqqzbs = fwqqzbs;
	}
}
