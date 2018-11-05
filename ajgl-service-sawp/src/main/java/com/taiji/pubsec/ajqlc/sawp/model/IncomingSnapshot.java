package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 入库/再入库本次入库数量快照记录
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_bcrkslkzjl")
public class IncomingSnapshot {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; // id
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "kzsj")
	private Date snapshotTime;	//快照时间
	
	@Column(name = "form_id")
	private String formId;	//入库单/再入库单id

	@Column(name = "blr")
	private String  transactor;	//办理人
	
	@OneToMany(mappedBy = "incomingSnapshot")
	private Set<InStorageSnapshot> inStorageSnapshots = new HashSet<InStorageSnapshot>();	//	入库快照记录
	
	@OneToMany(mappedBy = "incomingSnapshot")
	private Set<BackStorageSnapshot> backStorageSnapshots = new HashSet<BackStorageSnapshot>();	//	再入库快照记录

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Date snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getTransactor() {
		return transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	public Set<InStorageSnapshot> getInStorageSnapshots() {
		return inStorageSnapshots;
	}

	public void setInStorageSnapshots(Set<InStorageSnapshot> inStorageSnapshots) {
		this.inStorageSnapshots = inStorageSnapshots;
	}

	public Set<BackStorageSnapshot> getBackStorageSnapshots() {
		return backStorageSnapshots;
	}

	public void setBackStorageSnapshots(Set<BackStorageSnapshot> backStorageSnapshots) {
		this.backStorageSnapshots = backStorageSnapshots;
	}
	
}
