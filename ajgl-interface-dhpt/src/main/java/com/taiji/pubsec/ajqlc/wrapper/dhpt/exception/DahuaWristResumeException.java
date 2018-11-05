package com.taiji.pubsec.ajqlc.wrapper.dhpt.exception;
/**
 * 当手环处于解绑状态，再去解绑时抛出此异常
 * @author xinfan
 *
 */
public class DahuaWristResumeException extends DahuaException{

	public DahuaWristResumeException(String errorMessage) {
		super(errorMessage);
		
	}

}
