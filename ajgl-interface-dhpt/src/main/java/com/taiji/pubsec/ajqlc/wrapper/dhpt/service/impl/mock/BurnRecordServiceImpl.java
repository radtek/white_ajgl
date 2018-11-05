package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.BurnRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBurnRecordService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.IBurnRecordServiceSuspend;

@Service("burnRecordServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class BurnRecordServiceImpl implements IBurnRecordServiceSuspend{
	
	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public List<BurnRecordBean> queryBurnRecord(String caseFormCode,
			String startTime, String endTime) throws DahuaException {
		List<BurnRecordBean> burnRecordBeanLists = new ArrayList<>();
		BurnRecordBean burnRecordBean = new BurnRecordBean();
		burnRecordBean.setCreateDate("2017-03-07 14:53:21");
		burnRecordBean.setOptContent("[2],案件：xxx；刻录审讯：xxx；刻录笔录：xxx");
		burnRecordBean.setUserName("警员接口");
		burnRecordBean.setUserUUID("034627");
		burnRecordBeanLists.add(burnRecordBean);
		return burnRecordBeanLists;
	}

}
