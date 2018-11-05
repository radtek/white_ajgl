package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;

import com.taiji.persistence.dao.Dao;

import com.taiji.pubsec.ajqlc.baq.model.AskRoomIllegalRecord;
import com.taiji.pubsec.ajqlc.baq.model.IllegalType;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.service.IIllegalTypeService;

/**
 * @author XIEHF
 */
@Service("illegalTypeService")
@Transactional(rollbackFor = Exception.class)
public class IllegalTypeServiceImpl implements IIllegalTypeService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	public static final String STATUS = "status";	// 状态
	public static final String CODE = "code";	// code
	public static final String NAME = "name";	// 违规类型
	public static final String ILLEGALCAUSE = "illegalCause";	// 违规类型ID
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IllegalTypeServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public void createIllegalType(IllegalType illegalType) {
	    this.dao.save(illegalType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateIllegalType(IllegalType illegalType) {
	    this.dao.update(illegalType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String deleteIllegalType(String... ids) {
		StringBuffer mes = new StringBuffer();
		for(String id: ids) {
			String xql = "select entity from AskRoomIllegalRecord  as entity where entity.illegalCause = ? ";
			List<AskRoomIllegalRecord> list = this.dao.findAllByParams(AskRoomIllegalRecord.class, xql, new String[]{id});
		    if (null != list && list.size() > 0) {
		    	IllegalType entity = (IllegalType) this.dao.findById(IllegalType.class, list.get(0).getIllegalCause());
		    	mes.append(entity.getName()).append("，");
		    } else {
		    	this.dao.delete(IllegalType.class, id);
		    }
		}
		return mes.length() <= 0 ? "" : (mes.substring(0, mes.length()-1)).toString();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isDistinctIllegalTypeName(String name,String id) {
		Object[] values = new Object[]{name};
		String xql = "select illegal from IllegalType as illegal where illegal.name = ? ";
	    if(StringUtils.isNotEmpty(id)){ 
	    	xql += " and illegal.id != ? ";
	    	values = new Object[]{name, id};
	    }
	    List<IllegalType> list = this.dao.findAllByParams(IllegalType.class, xql, values);
	    return (null == list || list.size() <= 0);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isDistinctIllegalTypeCode(String code,String id) {
		Object[] values = new Object[]{code};
		String xql = "select illegal from IllegalType as illegal where illegal.code = ? ";
		if(StringUtils.isNotEmpty(id)){ 
	    	xql += " and illegal.id != ? ";
	    	values = new Object[]{code, id};
	    }
		List<IllegalType> list = this.dao.findAllByParams(IllegalType.class, xql, values);
		return (null == list || list.size() <= 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateIllegalTypeState(String id, String state,String operator) {
		IllegalType illegalType = (IllegalType) this.dao.findById(IllegalType.class, id);
		illegalType.setStatus(state);
		illegalType.setUpdatedTime(new Date());
		illegalType.setOperator(operator);
	    this.dao.update(illegalType);
	}

	@SuppressWarnings("unchecked")
	@Override
    public IllegalType findIllegalTypeByCode(String code) {
	    String xql = "select illegal from IllegalType as illegal where illegal.code = ? ";
	    Object[] values = new Object[]{code};
	    List<IllegalType> list = this.dao.findAllByParams(IllegalType.class, xql, values);
	    return (list == null ? null : list.get(0));
    }
	
	@SuppressWarnings("unchecked")
	@Override
    public IllegalType findIllegalTypeById(String id) {
	    return (IllegalType) this.dao.findById(IllegalType.class, id);
    }
	
	@SuppressWarnings("unchecked")
	@Override
    public List<IllegalType> findAllIllegalTypeByStatus(String status,boolean qtIncluded) {
	    String xql = "select illegalType from IllegalType as illegalType where 1=1  ";
	    Map<String, Object> xqlMap = new HashMap<String, Object>(0);
	    if(StringUtils.isNotEmpty(status)){ 
	    	xql += " and illegalType.status = :status ";
	    	xqlMap.put(STATUS, status);
	    }
	    if (!qtIncluded) {
	    	xql += " and illegalType.code != 99 ";
	    }
	    xql += "order by illegalType.isSystemData desc,illegalType.code";
		return this.dao.findAllByParams(IllegalType.class, xql, xqlMap);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public Pager<IllegalType> findAllIllegalTypeByStatusForPage(String status,int pageNo, int pageSize) {
		String xql = "select illegalType from IllegalType as illegalType where 1=1  ";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		if(StringUtils.isNotEmpty(status)){ 
	    	xql += " and illegalType.status = :status ";
	    	xqlMap.put(STATUS, status);
	    }
		xql += "order by illegalType.isSystemData desc,illegalType.code";
		return this.dao.findByPage(IllegalType.class, xql, xqlMap,pageNo, pageSize);
	}
	
}