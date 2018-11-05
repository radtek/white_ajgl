package com.taiji.pubsec.ajqlc.sawp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

/**
 * 储物柜
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_sawp_cwg")
public class ArticleLocker {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	//id
	
	@Column(name = "bm")
	private String code;	//编码
	
	@ManyToOne
	@JoinColumn(name = "bgq_id")
	private StorageArea area;	//保管区

	@Column(name = "wzms")
	private String location;	//位置描述
	
	@Column(name = "bz")
	private String remark;	//备注

	@Column(name = "zt")
	private String status;	//是否保险柜

	/**
	 * @return id id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return code 编码
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return	area	保管区
	 */
	public StorageArea getArea() {
		return area;
	}

	public void setArea(StorageArea area) {
		this.area = area;
	}
	
	/**
	 * @return	location	位置描述
	 */
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return	remark	备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
