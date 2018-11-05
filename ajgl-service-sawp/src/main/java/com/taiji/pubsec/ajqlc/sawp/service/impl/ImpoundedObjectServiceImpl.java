package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.sawp.bean.CriminalBasicCaseBean;
import com.taiji.pubsec.ajqlc.sawp.bean.DocBean;
import com.taiji.pubsec.ajqlc.sawp.bean.SuspectPersonBean;
import com.taiji.pubsec.ajqlc.sawp.model.ImpoundedObject;
import com.taiji.pubsec.ajqlc.sawp.service.IImpoundedObjectService;

@Service("impoundedObjectService")
public class ImpoundedObjectServiceImpl extends AbstractBaseService<ImpoundedObject, String> implements IImpoundedObjectService {
	
	@Autowired
	public ImpoundedObjectServiceImpl(Dao<ImpoundedObject, String> dao) {
		setDao(dao);
	}
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@Override
	public ImpoundedObject findCaseNameByCaseId(String caseId) {
		String hql = "select io from ImpoundedObject as io where io.caseId = :caseId";
		Map<String, Object> hqlMap = new HashMap<String, Object>();
		hqlMap.put("caseId", caseId);
		List<ImpoundedObject> list = this.findAllByParams(hql, hqlMap);
		return list.isEmpty() ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CriminalBasicCaseBean findCriminalBasicCaseBeanForInStorageByCaseId(String caseId) {
		CriminalBasicCaseBean caseBean = new CriminalBasicCaseBean();
		String xql = "select i from ImpoundedObject as i where i.caseId = ?";
		List<ImpoundedObject> impoundedObjects = new ArrayList<ImpoundedObject>();
		impoundedObjects = this.dao.findAllByParams(ImpoundedObject.class, xql, new Object[]{caseId});
		if(impoundedObjects.size() != 0){
			caseBean.setCaseCode(impoundedObjects.get(0).getCaseId());
			caseBean.setCaseName(impoundedObjects.get(0).getCaseName());
			caseBean.setHandlePolices(impoundedObjects.get(0).getPolice());
			caseBean.setPoliceNumber1(impoundedObjects.get(0).getPoliceNumber1());
			caseBean.setPoliceNumber2(impoundedObjects.get(0).getPoliceNumber2());
			List<SuspectPersonBean> suspectPersonBeans = new ArrayList<SuspectPersonBean>();
			List<String> suspectIds = new ArrayList<String>();
			suspectIds.add(impoundedObjects.get(0).getSuspectId());
			SuspectPersonBean suspectPersonBean = new SuspectPersonBean();
			suspectPersonBean.setSuspectId(impoundedObjects.get(0).getSuspectId());
			suspectPersonBean.setSuspectName(impoundedObjects.get(0).getSuspectName());
			suspectPersonBeans.add(suspectPersonBean);
			for(ImpoundedObject impoundedObject: impoundedObjects){
				if(suspectIds.contains(impoundedObject.getSuspectId())){
					continue;
				}else{
					SuspectPersonBean suspectPersonBean1 = new SuspectPersonBean();
					suspectPersonBean1.setSuspectId(impoundedObject.getSuspectId());
					suspectPersonBean1.setSuspectName(impoundedObject.getSuspectName());
					suspectPersonBeans.add(suspectPersonBean1);
					suspectIds.add(impoundedObject.getSuspectId());
				}
			}
			caseBean.setSuspectPersons(suspectPersonBeans);
			
			return caseBean;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImpoundedObject> findImpodundedObjectsByCaseIdAndSuspectId(String caseId, String suspectId) {
		String xql = "select i from ImpoundedObject as i where i.caseId = ? and i.suspectId = ?";
		List<ImpoundedObject> impoundedObjects = this.dao.findAllByParams(ImpoundedObject.class, xql, new Object[]{caseId, suspectId});
		return impoundedObjects;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocBean> findArchivedFilesByCaseIdAndSuspectId(String caseId, String suspectId) {
		String hql = "select distinct new " + DocBean.class.getName() + "(i.paper, i.paperName, i.paperType) from ImpoundedObject as i where i.caseId = :caseId and i.suspectId = :suspectId order by paperName asc ";
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xqlMap.put("caseId", caseId);
		xqlMap.put("suspectId", suspectId);
		return dao.findAllByParams(ImpoundedObject.class, hql, xqlMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<ImpoundedObject>> findImpoundedObjectsByCaseId(String caseId) {
		Map<String, List<ImpoundedObject>> suspectMap = new HashMap<String, List<ImpoundedObject>>();
		String xql = "select distinct io.suspectId from ImpoundedObject as io where io.caseId = ?";
		List<String> suspectList = this.dao.findAllByParams(ImpoundedObject.class, xql, new Object[]{caseId});
		for(String suspectId: suspectList){
			List<ImpoundedObject> ioList = new ArrayList<ImpoundedObject>();
			String xql1 = "select io from ImpoundedObject as io where io.caseId = ? and io.suspectId = ?";
			ioList = this.dao.findAllByParams(ImpoundedObject.class, xql1, new Object[]{caseId, suspectId});
			suspectMap.put(suspectId, ioList);
		}
		return suspectMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ImpoundedObject findImpoundedObjectByCode(String code) {
		String xql = "select i from ImpoundedObject as i where i.code = ?";
		return (ImpoundedObject) this.dao.findByParams(ImpoundedObject.class, xql, new Object[]{code});
	}

}
