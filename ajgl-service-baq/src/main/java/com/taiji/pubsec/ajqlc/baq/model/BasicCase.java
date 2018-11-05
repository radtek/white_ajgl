package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
 * 基本情况
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_jbqk")
public class BasicCase {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "csrq")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;	// 出生日期
	
	@Column(name = "jtzz", length = 160)
	private String byQuestioningPeopleAddress;	// 现住址
	
	@Column(name = "sfzjhm", length = 80)
	private String byQuestioningPeopleIdentifyNum;	// 身份证件号码

	@Column(name = "sfzjzl", length = 80)
	private String byQuestioningPeopleIdentifyType;	// 身份证件种类
	
	@Column(name = "bwxrxm", nullable = false, length = 80)
	private String byQuestioningPeopleName;	// 被问讯人姓名
	
	@Column(name = "bwxrid", length = 30)
	private String byQuestioningPeopleId;	//被问讯人id
	
	@Column(name = "bwxrlxfs", length = 80)
	private String byQuestioningPeopleTelphoneNumber;	// 被讯问人联系方式
	
	@Column(name = "ay", length = 80)
	private String causeOfLawCase;	// 案由
	
	@Column(name = "jrbaqyy")
	private String enterAreaReasons;// 进入办案区原由
	
	@Column(name = "qtay", length = 80)
	private String otherCauseOfLawCase;	// 其他案由
	
	@Column(name = "jrbaqsj", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date enterIntoTime;	// 进入办案区时间
	
	@Column(name = "bamj", length = 80)
	private String handlingPolice;	// 办案民警
	
	@Column(name = "xb", nullable = false, length = 20)
	private String sex;	// 性别
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "baqsyd_id", unique=true)
	private HandlingAreaReceipt handlingAreaReceipt;	// 办案区使用单
	
	@Column(name = "sydbh")
	private String handlingAreaReceiptNum;	// 使用单编号
	
	@Column(name = "ssaj", length = 80)
	private String lawCase;	// 所属案件
	
	@ManyToOne
	@JoinColumn(name = "zxxgr_id")
	private Person modifyPeople;	// 最新修改人

	@Column(name = "xgsj", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;	// 最新修改时间
	
	@Column(name = "shbh", length = 80)
	private String bangleCode;	//手环编号
	
	@Column(name = "hj", length = 160)
	private String door;	//户籍
	
	@Column(name = "zbdwbm", length = 80)
	private String sponsorUnitCode;	//主办单位编码
	
	/**
	 * @return id id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getByQuestioningPeopleId() {
		return byQuestioningPeopleId;
	}

	public void setByQuestioningPeopleId(String byQuestioningPeopleId) {
		this.byQuestioningPeopleId = byQuestioningPeopleId;
	}

	/**
	 * @return birthday 出生日期
	 */
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	/**
	 * @return byQuestioningPeopleAddress 家庭住址
	 */
	public String getByQuestioningPeopleAddress() {
		return byQuestioningPeopleAddress;
	}

	public void setByQuestioningPeopleAddress(String byQuestioningPeopleAddress) {
		this.byQuestioningPeopleAddress = byQuestioningPeopleAddress;
	}
	
	/**
	 * @return byQuestioningPeopleIdentifyNum 身份证件号码
	 */
	public String getByQuestioningPeopleIdentifyNum() {
		return byQuestioningPeopleIdentifyNum;
	}

	public void setByQuestioningPeopleIdentifyNum(String byQuestioningPeopleIdentifyNum) {
		this.byQuestioningPeopleIdentifyNum = byQuestioningPeopleIdentifyNum;
	}
	
	/**
	 * @return byQuestioningPeopleIdentifyType 身份证件种类
	 */
	public String getByQuestioningPeopleIdentifyType() {
		return byQuestioningPeopleIdentifyType;
	}

	public void setByQuestioningPeopleIdentifyType(String byQuestioningPeopleIdentifyType) {
		this.byQuestioningPeopleIdentifyType = byQuestioningPeopleIdentifyType;
	}
	
	/**
	 * @return byQuestioningPeopleName 被问讯人姓名
	 */
	public String getByQuestioningPeopleName() {
		return byQuestioningPeopleName;
	}

	public void setByQuestioningPeopleName(String byQuestioningPeopleName) {
		this.byQuestioningPeopleName = byQuestioningPeopleName;
	}
	
	/**
	 * @return byQuestioningPeopleTelphoneNumber 被讯问人联系方式
	 */
	public String getByQuestioningPeopleTelphoneNumber() {
		return byQuestioningPeopleTelphoneNumber;
	}

	public void setByQuestioningPeopleTelphoneNumber(String byQuestioningPeopleTelphoneNumber) {
		this.byQuestioningPeopleTelphoneNumber = byQuestioningPeopleTelphoneNumber;
	}
	
	/**
	 * @return causeOfLawCase 案由
	 */
	public String getCauseOfLawCase() {
		return causeOfLawCase;
	}

	public void setCauseOfLawCase(String causeOfLawCase) {
		this.causeOfLawCase = causeOfLawCase;
	}
	
	/**
	 * @return enterAreaReasons 进入办案区原由
	 */
	public String getEnterAreaReasons() {
		return enterAreaReasons;
	}

	public void setEnterAreaReasons(String enterAreaReasons) {
		this.enterAreaReasons = enterAreaReasons;
	}
	
	public String getOtherCauseOfLawCase() {
		return otherCauseOfLawCase;
	}

	public void setOtherCauseOfLawCase(String otherCauseOfLawCase) {
		this.otherCauseOfLawCase = otherCauseOfLawCase;
	}

	/**
	 * @return enterIntoTime 进入办案区时间
	 */
	public Date getEnterIntoTime() {
		return enterIntoTime;
	}

	public void setEnterIntoTime(Date enterIntoTime) {
		this.enterIntoTime = enterIntoTime;
	}
	
	/**
	 * @return handlingPolice 办案民警
	 */
	public String getHandlingPolice() {
		return handlingPolice;
	}

	public void setHandlingPolice(String handlingPolice) {
		this.handlingPolice = handlingPolice;
	}
	
	/**
	 * @return sex 性别
	 */
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * @return handlingAreaReceipt 办案区使用单id
	 */
	public HandlingAreaReceipt getHandlingAreaReceipt() {
		return handlingAreaReceipt;
	}

	public void setHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt) {
		this.handlingAreaReceipt = handlingAreaReceipt;
	}
	

	/**
	 * @return	handlingAreaReceiptNum	使用单编号
	 */
	public String getHandlingAreaReceiptNum() {
		return handlingAreaReceiptNum;
	}

	public void setHandlingAreaReceiptNum(String handlingAreaReceiptNum) {
		this.handlingAreaReceiptNum = handlingAreaReceiptNum;
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
	 * @return	lawCase	所属案件
	 */
	public String getLawCase() {
		return lawCase;
	}

	public void setLawCase(String lawCase) {
		this.lawCase = lawCase;
	}

	public String getBangleCode() {
		return bangleCode;
	}

	public void setBangleCode(String bangleCode) {
		this.bangleCode = bangleCode;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getSponsorUnitCode() {
		return sponsorUnitCode;
	}

	public void setSponsorUnitCode(String sponsorUnitCode) {
		this.sponsorUnitCode = sponsorUnitCode;
	}
	
	

}