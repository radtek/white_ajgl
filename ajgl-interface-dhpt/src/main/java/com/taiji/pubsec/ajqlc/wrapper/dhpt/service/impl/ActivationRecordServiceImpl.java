package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.exceptionLog.model.ExceptionLog;
import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.util.ServiceConstant;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.CxfClientServiceImpl;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IActivationRecordService;

import net.sf.json.JSONObject;

@Service("activationRecordServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivationRecordServiceImpl implements IActivationRecordService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivationRecordServiceImpl.class);

	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
    
	@Resource
	private IExceptionLogService exceptionLogService;

	protected void execute() {
		long ms = System.currentTimeMillis();
		System.out.println("\t\t" + new Date(ms));
		
	}
	/**
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println("Test start.");
        ApplicationContext ctx=new FileSystemXmlApplicationContext(new String[] {"classpath:applicationContext-scheduler.xml"});
        //如果配置文件中将startQuertz bean的lazy-init设置为false 则不用实例化
        ActivationRecordServiceImpl qj=(ActivationRecordServiceImpl)ctx.getBean("activationRecordServiceImpl");
        System.out.println("请输入信息：");
        Scanner input=new Scanner(System.in);
        int x=input.nextInt();
        System.out.println(x);
        System.out.print("Test end..");
        

    }
    
	@Override
	public String syncVideoRelayHistoryInfo(String registerUUID,
			String startTime, String endTime) {
//		String wsdl = "http://52.4.1.210/itc/ws/ExternalWebService?wsdl";//发布地址
//		String targetNameSpace = "http://external.webservice.dip.dahua.com";//命名空间
		String method = "getVideoRelayHistoryInfo";
		String result = "";
		List <Object> params = new ArrayList<Object>();
		params.add(registerUUID);
		params.add(startTime);
		params.add(endTime);
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, params);
		} catch (Exception e) {
			LOGGER.error("同步活动记录接口调用错误", e);
			throw new DahuaException(e);
		}
		JSONObject  jasonObject = JSONObject.fromObject(result);
		if("false".equals(jasonObject.get("success").toString())){
			ExceptionLog log = new ExceptionLog();
			log.setHappeningTime(new Date());
			log.setExceptionInfo(jasonObject.get("msg").toString());
			log.setFunctionName(this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
			exceptionLogService.save(log);
			throw new DahuaException("大华接口调用失败："+jasonObject.get("msg").toString()+"; 使用单UUID:" +registerUUID);
		}
		return result;
	}
}
