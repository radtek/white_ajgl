package com.taiji.pubsec.ajqlc.baq.util;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.type.Type;
import org.springframework.stereotype.Service;

import com.taiji.persistence.service.AbstractInterceptorService;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsInfo;
import com.taiji.pubsec.ajqlc.baq.model.CollectInfoSituation;
import com.taiji.pubsec.ajqlc.baq.model.FinallyLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.model.HandleAreaActivityRecordInfo;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.PersonCheckRecord;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

@Service
public class BaqInterceptorService extends AbstractInterceptorService {
	
	@Resource
	private IPersonService personService ;
	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		if(user==null){
			return false ;
		}
		
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, Object> person = (Map<String, Object>)userMap.get("person") ;
		for(int i = 0; i < propertyNames.length; i++){
			if(propertyNames[i].equals("modifyPeople")){
				state[i] = personService.findById(String.valueOf(person.get("id")));
				return true; 
			}
		}
		return false;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		if(user==null){
			return false ;
		}
		
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, Object> person = (Map<String, Object>)userMap.get("person") ;
		for(int i = 0; i < propertyNames.length; i++){
			if(propertyNames[i].equals("modifyPeople")){
				currentState[i] = personService.findById(String.valueOf(person.get("id")));
				return true; 
			}
		}
		return false;
	}

	@Override
	public boolean support(String entityClassName) {
		if(FinallyLeaveInfo.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(BasicCase.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(HandlingAreaReceipt.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(CarryGoodsInfo.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(CollectInfoSituation.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(HandleAreaActivityRecordInfo.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(PersonCheckRecord.class.getName().equals(entityClassName)){
			return true;
		}
		
		return false;
	}
}
