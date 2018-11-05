package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IPoliceInfoService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

/**
 * @author huangda
 */
@Service("policeInfoService")
@Transactional(rollbackFor = Exception.class)
public class PoliceInfoServiceImpl implements IPoliceInfoService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public String save(PoliceInfo info) {
		dao.save(info);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(String id) {
		PoliceInfo pi = (PoliceInfo)dao.findById(PoliceInfo.class, id);
		if(pi != null){
			dao.delete(pi);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PoliceInfo findById(String id) {
		return (PoliceInfo)dao.findById(PoliceInfo.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PoliceInfo findByPoliceCode(String code) {
		PoliceInfo pi = (PoliceInfo)dao.findByParams(PoliceInfo.class, "from PoliceInfo as pi where pi.policeNum = ?", new Object[]{code});
		return pi;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String update(PoliceInfo info) {
		dao.update(info);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PoliceInfo findByCardId(String cardId) {
		return (PoliceInfo)dao.findByParams(PoliceInfo.class, "from PoliceInfo as pi where pi.icNum = ?", new Object[]{cardId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoliceInfo> findPoliceWhoHasCardByCondition(PoliceInfo pi) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(pi == null){
			return (List<PoliceInfo>)dao.findAllByParams(PoliceInfo.class, "from PoliceInfo as pi where pi.icNum is not null", map);
		}else{
			String sql = "";
			if(pi.getIcNum() != null){
				sql += " and pi.icNum like :icNum";
				sql = SQLTool.SQLAddEscape(sql);
				map.put("icNum", "%" + SQLTool.SQLSpecialChTranfer((String) pi.getIcNum()) + "%");
			}else{
				sql += " and pi.icNum is not null";
			}
			if(pi.getPoliceNum() != null){
				sql += " and pi.policeNum like :policeNum";
				map.put("policeNum", "%" + SQLTool.SQLSpecialChTranfer((String) pi.getPoliceNum()) + "%");
			}
			if(pi.getPoliceName() != null){
				sql += " and pi.policeName like :policeName";
				map.put("policeName", "%" + SQLTool.SQLSpecialChTranfer((String) pi.getPoliceName()) + "%");
			}
			return (List<PoliceInfo>)dao.findAllByParams(PoliceInfo.class, "from PoliceInfo as pi where 1 = 1" + sql, map);
		}
	}

}
