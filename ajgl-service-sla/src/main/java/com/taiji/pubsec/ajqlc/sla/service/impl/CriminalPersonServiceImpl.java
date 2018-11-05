package com.taiji.pubsec.ajqlc.sla.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.sla.model.CaseSupectRelation;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.model.SufferCaseRelation;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalPersonService;

@Service("criminalPersonService")
public class CriminalPersonServiceImpl implements ICriminalPersonService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public CriminalPerson findById(String id) {
		return (CriminalPerson) this.dao.findById(CriminalPerson.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseSupectRelation> findCaseSupectRelationByPerson(String personId) {
		String xql = "select csr from CaseSupectRelation as csr where csr.person_id = ?";
		return this.dao.findAllByParams(CaseSupectRelation.class, xql, new Object[]{personId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public SufferCaseRelation findSufferCaseRelationByPerson(String personId) {
		String xql = "select csr from SufferCaseRelation as csr where csr.person_id = ?";
		return (SufferCaseRelation) this.dao.findByParams(SufferCaseRelation.class, xql, new Object[]{personId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SufferCaseRelation> findSufferCaseRelationsByPerson(String personId) {
		String xql = "select s from SufferCaseRelation as s where s.person_id = ?";
		return this.dao.findAllByParams(SufferCaseRelation.class, xql, new Object[]{personId});
	}

}
