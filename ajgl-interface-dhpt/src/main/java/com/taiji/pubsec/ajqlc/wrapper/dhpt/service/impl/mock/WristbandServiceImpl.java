package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IWristbandService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.IWristbandServiceSuspend;

@Service("wristbandServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class WristbandServiceImpl implements IWristbandServiceSuspend{

	@Resource
	private IExceptionLogService exceptionLogService;
	@Override
	public ResultBean active(String wristId,String suspectId, String operatorId) throws DahuaException {
		return activeOrReclaim(suspectId,operatorId,wristId,ConstantBean.OPRTYPE_ACTIVE,ConstantBean.PERSON_SUSPECT);
		
	}

	@Override
	public ResultBean suspend(String wristId) throws DahuaException {
		return suspendOrResume(wristId,ConstantBean.OPRTYPE_SUSPEND);
	}

	@Override
	public ResultBean resume(String wristId) throws DahuaException {
		return suspendOrResume(wristId,ConstantBean.OPRTYPE_RESUME);
	}

	@Override
	public ResultBean reclaim(String wristId,String suspectId, String operatorId) throws DahuaException {
		 	return activeOrReclaim(suspectId,operatorId,wristId,ConstantBean.OPRTYPE_RECLAIM,ConstantBean.PERSON_SUSPECT);
	}
	/**
	 * 挂起或取消挂起
	 * @param wristId 手环id
	 * @param operater 操作类型
	 * @return
	 * @throws DahuaException 
	 */
   private ResultBean suspendOrResume(String wristId, Integer operater) throws DahuaException{
		return new ResultBean(true);
   }
   /**
    * 
    * @param suspectPersonId 嫌疑人id
    * @param operaterId  操作人id
    * @param wristId  手环id
    * @param operaterType 操作类型
    * @param personType 人员类型  1为嫌疑人 5 为民警
    * @return
 * @throws DahuaException 
    */
   private ResultBean activeOrReclaim(String suspectPersonId, String operaterId,
			String wristId ,String operaterType,String personType) throws DahuaException{
		return new ResultBean(true);
   }

@Override
public ResultBean getBraceletInfoByPcId(String braceletPhysicalId) throws DahuaException {
	ResultBean bean=new ResultBean(true);
	bean.setData("{\"isFreeze\":\"0\"}");
	return bean;
}
}
