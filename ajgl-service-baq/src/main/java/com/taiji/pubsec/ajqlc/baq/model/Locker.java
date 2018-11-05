package com.taiji.pubsec.ajqlc.baq.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 储物柜
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_cwg")
public class Locker extends CommonFileds {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "mc", nullable = false, length = 80)
	private String name;	// 名称

//	@Column(name = "wz", nullable = false, length = 80)
//	private String position;	// 位置
	
	@Column(name = "qh", length = 80)
	private String areaCode;	//区号
	
	@Column(name = "gh", length = 80)
	private String lockerCode;	//柜号
	
	/**
	 * @return id id
	 */	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	/**
//	 * @return department 位置
//	 */	
//	public String getPosition() {
//		return position;
//	}
//
//	public void setPosition(String position) {
//		this.position = position;
//	}
	
	
	
}