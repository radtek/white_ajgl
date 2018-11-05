package com.taiji.pubsec.ajqlc.wrapper.dhpt.exception;

public class DahuaException extends RuntimeException {
	public DahuaException(String errorMessage) {
		super(errorMessage);
	}
	
	public DahuaException(Exception e) {
		super(e);
	}
}
