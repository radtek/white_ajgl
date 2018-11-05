package com.taiji.pubsec.ajqlc.sawp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;

/**
 * 暂存入库单和暂存保管柜中间表
 * @author xinfan
 *
 */
@Entity
@Table(name = "t_sawp_zcrkd_zcwpg")
public class StoragePosition {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	@ManyToOne
	@JoinColumn(name = "bgg_id")
	private TemporaryStorageShelf bgg;	//暂存保管柜
	
	@ManyToOne
	@JoinColumn(name = "rkd_id")
	private TemporaryStorageIn rkd;	//暂存入库单

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TemporaryStorageShelf getBgg() {
		return bgg;
	}

	public void setBgg(TemporaryStorageShelf bgg) {
		this.bgg = bgg;
	}

	public TemporaryStorageIn getRkd() {
		return rkd;
	}

	public void setRkd(TemporaryStorageIn rkd) {
		this.rkd = rkd;
	}
}
