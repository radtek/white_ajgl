package com.taiji.pubsec.ajqlc.sawp.action.bean;

import java.util.HashSet;
import java.util.Set;



/**
 * 入库&再入库单项 快照Bean--负责打印
 * 
 * @author 
 *
 */
public class InComingSnapshootBean {
	
	private String id; // id
	
	private Long snapshotTime;	//快照时间
	
	private String formId;	//入库单/再入库单id
	
	private String  transactor;	//办理人
	
	private Set<InStorageSnapshotBean> inStorageSnapshots = new HashSet<InStorageSnapshotBean>();	//	入库快照记录
	
	private Set<BackStorageSnapshotBean> backStorageSnapshots = new HashSet<BackStorageSnapshotBean>();	//	再入库快照记录

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Long snapshotTime) {
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

	public Set<InStorageSnapshotBean> getInStorageSnapshots() {
		return inStorageSnapshots;
	}

	public void setInStorageSnapshots(Set<InStorageSnapshotBean> inStorageSnapshots) {
		this.inStorageSnapshots = inStorageSnapshots;
	}

	public Set<BackStorageSnapshotBean> getBackStorageSnapshots() {
		return backStorageSnapshots;
	}

	public void setBackStorageSnapshots(Set<BackStorageSnapshotBean> backStorageSnapshots) {
		this.backStorageSnapshots = backStorageSnapshots;
	}


}
