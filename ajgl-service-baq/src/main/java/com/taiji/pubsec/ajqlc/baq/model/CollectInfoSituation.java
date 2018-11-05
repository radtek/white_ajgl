package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 采集信息情况
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_baq_cjxxqk")
public class CollectInfoSituation {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "sjxm")
	private String collectProject;	//收集项目,选择多个用逗号隔开

	@Column(name = "qtnr")
	private String collectProjectOther;	//其他的内容
	
	@Column(name = "xxrk")
	private String infoIntoString;	//信息入库，0:是；1:否
	
	@Column(name = "hcdb")
	private String inspectComparison;	//核查对比，0:是；1:否
	
	@Column(name = "sfsj")
	private String isCollect;	//是否收集，0:是；1:否
	
	@ManyToOne
	@JoinColumn(name = "zxxgr_id")
	private Person modifyPeople;	// 最新修改人
	
	@Column(name = "zxxgsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;	//最新修改时间
	
	@OneToOne
	@JoinColumn(name = "baqsyd_id")
	private HandlingAreaReceipt handlingAreaReceipt;	//办案区使用单
	
	@Column(name = "qq")
	private String qqNum;	//qq号码（，隔开多个）
	
	@Column(name = "wxh")
	private String weixinNum;	//微信号（，隔开多个）
	
	@Column(name = "sjxx")
	private String phoneInfo;	//手机信息（手机号，imei，mac；手机号，imei，mac）
	
	/**
	 * @return	id	id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return	collectProject	收集项目,选择多个用逗号隔开
	 */
	public String getCollectProject() {
		return collectProject;
	}

	public void setCollectProject(String collectProject) {
		this.collectProject = collectProject;
	}

	/**
	 * @return	collectProjectOther	其他的内容
	 */
	public String getCollectProjectOther() {
		return collectProjectOther;
	}

	public void setCollectProjectOther(String collectProjectOther) {
		this.collectProjectOther = collectProjectOther;
	}

	/**
	 * @return	infoIntoString	信息入库，0:是；1:否
	 */
	public String getInfoIntoString() {
		return infoIntoString;
	}

	public void setInfoIntoString(String infoIntoString) {
		this.infoIntoString = infoIntoString;
	}

	/**
	 * @return	inspectComparison	核查对比，0:是；1:否
	 */
	public String getInspectComparison() {
		return inspectComparison;
	}

	public void setInspectComparison(String inspectComparison) {
		this.inspectComparison = inspectComparison;
	}

	/**
	 * @return	isCollect	是否收集，0:是；1:否
	 */
	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	
	/**
	 * @return modifyPeople 最新修改人
	 */
	public Person getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(Person modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	/**
	 * @return	updatedTime	最新修改时间
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return	handlingAreaReceipt	办案区使用单
	 */
	public HandlingAreaReceipt getHandlingAreaReceipt() {
		return handlingAreaReceipt;
	}

	public void setHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt) {
		this.handlingAreaReceipt = handlingAreaReceipt;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getWeixinNum() {
		return weixinNum;
	}

	public void setWeixinNum(String weixinNum) {
		this.weixinNum = weixinNum;
	}

	public String getPhoneInfo() {
		return phoneInfo;
	}

	public void setPhoneInfo(String phoneInfo) {
		this.phoneInfo = phoneInfo;
	}
	
	
	
}
