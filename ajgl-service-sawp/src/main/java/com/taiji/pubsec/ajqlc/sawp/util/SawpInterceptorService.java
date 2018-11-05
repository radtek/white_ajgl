package com.taiji.pubsec.ajqlc.sawp.util;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.type.Type;
import org.springframework.stereotype.Service;

import com.taiji.persistence.service.AbstractInterceptorService;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustgmentStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

@Service
public class SawpInterceptorService extends AbstractInterceptorService {
	
	@Resource
	private IPersonService personService ;
	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		if (null != user) {
			Map<String, Object> userMap = user.getUserMap() ;
			Map<String, Object> person = (Map<String, Object>)userMap.get("person") ;
			for(int i = 0; i < propertyNames.length; i++){
				if(propertyNames[i].equals("modifyPeople")){
					state[i] = personService.findById(String.valueOf(person.get("id")));
					state[i] = personService.findById(String.valueOf((null == person) ? ""  : person.get("id")));
					return true; 
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		if(null != user) {
			Map<String, Object> userMap = user.getUserMap() ;
			Map<String, Object> person = (Map<String, Object>)userMap.get("person") ;
			for(int i = 0; i < propertyNames.length; i++){
				if(propertyNames[i].equals("modifyPeople")){
					currentState[i] = personService.findById(String.valueOf(person.get("id")));
					currentState[i] = personService.findById(String.valueOf((null == person) ? ""  : person.get("id")));
					return true; 
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean support(String entityClassName) {
		
		if(OutStorageForm.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(InStorageForm.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(AdjustgmentStorageForm.class.getName().equals(entityClassName)){
			return true;
		}
		
		if(BackStorageForm.class.getName().equals(entityClassName)){
			return true;
		}
		
		return false;
	}
}
