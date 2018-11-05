package com.taiji.pubsec.ajqlc.wrapper.jzpt.util;


/**
 * 返回的报文出现202内部错误时抛出此异常
 * @author xinfan
 *
 */
public class ResultReturnException extends Exception{
    public  ResultReturnException(String msg){
    	super(msg);
    }
}
