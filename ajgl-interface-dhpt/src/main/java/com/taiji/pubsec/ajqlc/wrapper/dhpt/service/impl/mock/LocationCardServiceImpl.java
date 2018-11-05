package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ILocationCardService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.ILocationCardServiceSuspend;
@Service("locationCardServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class LocationCardServiceImpl implements ILocationCardServiceSuspend {

	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public ResultBean active(String id,String suspectPersonId, String operaterId) throws DahuaException {
		return activeOrReclaim(suspectPersonId,operaterId,id,ConstantBean.OPRTYPE_ACTIVE,ConstantBean.PERSON_POLICE);
	}

	@Override
	public ResultBean suspend(String locationCardId) throws DahuaException {
		return suspendOrResume(locationCardId,ConstantBean.OPRTYPE_SUSPEND);
	}

	@Override
	public ResultBean resume(String locationCardId) throws DahuaException {
		return suspendOrResume(locationCardId,ConstantBean.OPRTYPE_RESUME);
	}

	@Override
	public ResultBean reclaim(String id,String policeId, String operaterId) throws DahuaException {
		 	return activeOrReclaim(policeId,operaterId,id,ConstantBean.OPRTYPE_RECLAIM,ConstantBean.PERSON_POLICE);
	}
	/**
	 * 挂起或取消挂起
	 * @param locationCardId 定位卡id
	 * @param operater 操作类型
	 * @return
	 * @throws DahuaException 
	 */
   private ResultBean suspendOrResume(String locationCardId, Integer operater) throws DahuaException{

		return  new ResultBean(true);
   }
   /**
    * 
    * @param suspectPersonId 嫌疑人id
    * @param operaterId  操作人id
    * @param locationCardId  定位卡id
    * @param operaterType 操作类型
    * @return
 * @throws DahuaException 
    */
   private ResultBean activeOrReclaim(String suspectPersonId, String operaterId,
			String locationCardId ,String operaterType,String personType) throws DahuaException{
        
	return new ResultBean(true);
   }
}
