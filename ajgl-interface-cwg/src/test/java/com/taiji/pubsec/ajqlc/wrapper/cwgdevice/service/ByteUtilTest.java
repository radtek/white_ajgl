/**
 * Copyright 2017 Taiji
 * All right reserved.
 * Created on 2017年2月23日 下午5:21:35
 */
package com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yucy
 *
 */
public class ByteUtilTest {

	@Test
	public void testByteToDate(){
		byte[] year = {(byte) 0x07, (byte) 0xe1};
		byte month = (byte)0x2;
		byte day = 0x9;
		byte hour = 0x1;
		byte minute = 0x1;
		byte second = 0x1;
		Date date = ByteUtil.byteToDate(year, month, day, hour, minute, second);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		Assert.assertEquals("2017-02-09 01:01:01", sdf.format(date));
	}
	
	@Test
	public void testGetBooleanArray(){
		byte b = 0x9;
		byte[] bs = ByteUtil.getBooleanArray(b);
		for(int i=0; i<bs.length; i++){
			System.out.println(bs[i]);
		}
//		System.out.println((char)0x54);
//		System.out.println((char)0x4B);
	}
}
