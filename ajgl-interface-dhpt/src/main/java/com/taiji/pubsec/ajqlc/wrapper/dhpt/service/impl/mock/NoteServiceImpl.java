package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.INoteService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.INoteServiceSuspend;

@Service("noteServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class NoteServiceImpl implements INoteServiceSuspend {

	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public String produceOcxParam(String ip, String userCode) throws DahuaException {
		List<Object> params = new ArrayList<Object>();
		params.add(ip);
		params.add(userCode);
		return initOcx(params,"getTrialingInfoByIp").getData();//应该返回data
	}

	@Override
	public ResultBean syncTrialNoteStat(String id, String stat, String strTime) throws DahuaException {
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		params.add(stat);
		params.add(strTime);
		return initOcx(params,"syncTrialNoteStat");
	}
   private ResultBean  initOcx(List<Object> params,String method) throws DahuaException{

	 		return new ResultBean(true);
   }
}
