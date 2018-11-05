package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;

import java.util.Date;
import java.util.Scanner;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IActivationRecordService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.IActivationRecordServiceSuspend;
@Service("activationRecordServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class ActivationRecordServiceImpl implements IActivationRecordServiceSuspend {
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

		String result = "";
		result += "{\"success\":true,\"data\":[{\"startTime\":\"2017-02-10 10:34:01\",\"endTime\":\"2017-02-10 10:40:41\",\"gridName\":\"大厅\",\"gridType\":\"1\",\"rfDevName\":\"大厅RFID\",\"channelCode\":\"1000010$1$0$0\",\"gridUUID\":\"7\"},"
				
                                          +"{\"startTime\":\"2017-02-10 10:40:41\",\"endTime\":\"2017-02-10 10:41:01\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
                                          +"{\"startTime\":\"2017-02-10 10:41:01\",\"endTime\":\"2017-02-10 10:41:11\",\"gridName\":\"审讯室\",\"gridType\":\"1\",\"rfDevName\":\"审讯室RFID\",\"channelCode\":\"1000009$1$0$0\",\"gridUUID\":\"9\"},"
		                                  +"{\"startTime\":\"2017-02-10 10:41:11\",\"endTime\":\"2017-02-10 10:42:41\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000010$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 10:42:41\",\"endTime\":\"2017-02-10 10:42:51\",\"gridName\":\"大厅\",\"gridType\":\"1\",\"rfDevName\":\"大厅RFID\",\"channelCode\":\"1000005$1$0$0\",\"gridUUID\":\"7\"},"
		                                  +"{\"startTime\":\"2017-02-10 10:42:51\",\"endTime\":\"2017-02-10 10:45:01\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 10:45:01\",\"endTime\":\"2017-02-10 10:45:22\",\"gridName\":\"大厅\",\"gridType\":\"1\",\"rfDevName\":\"大厅RFID\",\"channelCode\":\"1000005$1$0$0\",\"gridUUID\":\"7\"},"
		                                  +"{\"startTime\":\"2017-02-10 10:45:22\",\"endTime\":\"2017-02-10 11:12:53\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:12:53\",\"endTime\":\"2017-02-10 11:38:48\",\"gridName\":\"审讯室\",\"gridType\":\"1\",\"rfDevName\":\"审讯室RFID\",\"channelCode\":\"1000009$1$0$0\",\"gridUUID\":\"9\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:38:48\",\"endTime\":\"2017-02-10 11:39:38\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:39:38\",\"endTime\":\"2017-02-10 11:40:08\",\"gridName\":\"大厅\",\"gridType\":\"1\",\"rfDevName\":\"大厅RFID\",\"channelCode\":\"1000009$1$0$0\",\"gridUUID\":\"7\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:40:08\",\"endTime\":\"2017-02-10 11:41:15\",\"gridName\":\"审讯室\",\"gridType\":\"1\",\"rfDevName\":\"审讯室RFID\",\"channelCode\":\"1000005$1$0$0\",\"gridUUID\":\"9\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:41:15\",\"endTime\":\"2017-02-10 11:45:04\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:45:04\",\"endTime\":\"2017-02-10 11:45:22\",\"gridName\":\"审讯室\",\"gridType\":\"1\",\"rfDevName\":\"审讯室RFID\",\"channelCode\":\"1000005$1$0$0\",\"gridUUID\":\"9\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:45:22\",\"endTime\":\"2017-02-10 11:45:34\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:45:34\",\"endTime\":\"2017-02-10 11:45:44\",\"gridName\":\"大厅\",\"gridType\":\"1\",\"rfDevName\":\"大厅RFID\",\"channelCode\":\"1000009$1$0$0\",\"gridUUID\":\"7\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:45:44\",\"endTime\":\"2017-02-10 11:46:33\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000010$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:46:33\",\"endTime\":\"2017-02-10 11:46:48\",\"gridName\":\"审讯室\",\"gridType\":\"1\",\"rfDevName\":\"审讯室RFID\",\"channelCode\":\"1000005$1$0$0\",\"gridUUID\":\"9\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:46:48\",\"endTime\":\"2017-02-10 11:46:57\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:46:57\",\"endTime\":\"2017-02-10 11:47:14\",\"gridName\":\"大厅\",\"gridType\":\"1\",\"rfDevName\":\"大厅RFID\",\"channelCode\":\"1000009$1$0$0\",\"gridUUID\":\"7\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:47:14\",\"endTime\":\"2017-02-10 11:50:55\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000010$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:50:55\",\"endTime\":\"2017-02-10 11:51:11\",\"gridName\":\"审讯室\",\"gridType\":\"1\",\"rfDevName\":\"审讯室RFID\",\"channelCode\":\"1000005$1$0$0\",\"gridUUID\":\"9\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:51:11\",\"endTime\":\"2017-02-10 11:51:27\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:51:27\",\"endTime\":\"2017-02-10 11:51:41\",\"gridName\":\"审讯室\",\"gridType\":\"1\",\"rfDevName\":\"审讯室RFID\",\"channelCode\":\"1000005$1$0$0\",\"gridUUID\":\"9\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:51:41\",\"endTime\":\"2017-02-10 11:51:52\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:51:52\",\"endTime\":\"2017-02-10 11:52:07\",\"gridName\":\"大厅\",\"gridType\":\"1\",\"rfDevName\":\"大厅RFID\",\"channelCode\":\"1000009$1$0$0\",\"gridUUID\":\"7\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:52:07\",\"endTime\":\"2017-02-10 11:54:00\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000010$1$0$0\",\"gridUUID\":\"8\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:54:00\",\"endTime\":\"2017-02-10 11:54:07\",\"gridName\":\"审讯室\",\"gridType\":\"1\",\"rfDevName\":\"审讯室RFID\",\"channelCode\":\"1000005$1$0$0\",\"gridUUID\":\"9\"},"
		                                  +"{\"startTime\":\"2017-02-10 11:54:07\",\"endTime\":\"2017-02-10 11:54:16\",\"gridName\":\"信息采集室\",\"gridType\":\"1\",\"rfDevName\":\"信息采集RFID\",\"channelCode\":\"1000008$1$0$0\",\"gridUUID\":\"8\"}";
		return result;
//		JSONObject  jasonObject = JSONObject.fromObject(result);
//		if(\"false\".equals(jasonObject.get(\"success\").toString())){
//			ExceptionLog log = new ExceptionLog();
//			log.setHappeningTime(new Date());
//			log.setExceptionInfo(jasonObject.get(\"msg\").toString());
//			log.setFunctionName(this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
//			exceptionLogService.save(log);
//			throw new DahuaException(\"大华接口调用失败：\"+jasonObject.get(\"msg\").toString());
//		}
//		return JsonToBean.json2ActivationRecord(result);
	}
}
