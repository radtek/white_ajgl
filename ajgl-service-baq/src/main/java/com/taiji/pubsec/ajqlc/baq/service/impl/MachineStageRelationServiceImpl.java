package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.MachineStageRelation;
import com.taiji.pubsec.ajqlc.baq.service.IMachineStageRelationService;

/**
 * @author chengkai
 */
@Service("machineStageRelationService")
public class MachineStageRelationServiceImpl implements IMachineStageRelationService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MachineStageRelation> findMachineStageRelationsByMachineId(String machineId) {
		String xql = "select m from MachineStageRelation as m where m.machineId = ? order by m.stageUrl";
		List<MachineStageRelation> relations = dao.findAllByParams(MachineStageRelation.class, xql, new Object[]{machineId});
		return relations;
	}

}
