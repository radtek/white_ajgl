package com.taiji.pubsec.ajqlc.wrapper.jzpt.util;



import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
/**
 * 
 * @author xinfan
 *
 */
public class DataSyncServiceImpl {
	/**
	 * 用于向远程服务请求数据
	 * @param qqbw  请求报文
	 * @param wsUrl 发布地址 不带?wsdl
	 * @param targetNameSpace  命名空间
	 * @param method  调用方法
	 * @return String  json字符串
	 * @throws Exception
	 */
	public  static String getResource(String qqbw,String wsUrl,String targetNameSpace,String method) throws Exception{
				//使用RPC方式调用WebService
				//RPCServiceClient serviceClient = new RPCServiceClient();
				//Options options = serviceClient.getOptions();
				//设置200秒超时
				//options.setTimeOutInMilliSeconds(300000L);
				//指定调用WebService的URL
				EndpointReference targetEPR = new EndpointReference(wsUrl);
				//options.setTo(targetEPR);
				//指定接口方法的参数值
				Object[] opAddEntryArgs = new Object[] { qqbw };
				//指定方法返回值的数据类型的Class对象
				Class[] classes = new Class[] { String.class };
				//指定调用的方法及WSDL文件的命名空间
				QName opAddEntry = new QName(targetNameSpace,method);
				//调用queryService方法并输出该方法的返回值,
				//返回对象是一个Object的数组,拿数组的第一个值，转换强转即可
					//	String jgbw = serviceClient.invokeBlocking(opAddEntry, 
//				opAddEntryArgs, classes)[0].toString();
				return null;
	}
}
