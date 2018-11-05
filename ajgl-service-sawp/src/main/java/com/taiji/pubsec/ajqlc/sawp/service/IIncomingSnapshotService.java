package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.sawp.model.BackStorageSnapshot;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageSnapshot;
import com.taiji.pubsec.ajqlc.sawp.model.IncomingSnapshot;

/**
 * 入库/再入库本次入库数量快照记录服务接口
 * @author chengkai
 *
 */
public interface IIncomingSnapshotService {
	
	/**
	 * 新增入库记录list
	 * @param incomingSnapshot 本次入库数量快照记录
	 * @param inStorageSnapshots 入库快照记录list
	 */
	public void saveInStorageSnapshots(IncomingSnapshot incomingSnapshot, List<InStorageSnapshot> inStorageSnapshots);
	
	/**
	 * 新增再入库记录list
	 * @param incomingSnapshot 本次入库数量快照记录
	 * @param backStorageSnapshots 再入库快照记录list
	 */
	public void saveBackStorageSnapshots(IncomingSnapshot incomingSnapshot, List<BackStorageSnapshot> backStorageSnapshots);
	
	/**
	 * 通过入库单/再入库单id查询最新的一次快照
	 * @param formId 入库单/再入库单id
	 * @return 返回快照list
	 */
	public IncomingSnapshot findLatestIncomingSnapshotByFormId(String formId);
	
}
