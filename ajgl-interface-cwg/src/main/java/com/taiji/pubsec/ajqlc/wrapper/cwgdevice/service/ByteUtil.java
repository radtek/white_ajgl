/**
 * Copyright 2017 Taiji
 * All right reserved.
 * Created on 2017年2月23日 下午1:59:29
 */
package com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 字节处理工具类
 * 
 * @author yucy
 *
 */
public class ByteUtil {

	/**
	 * 将字节数组的十六进制表示转换为字符串
	 * @param b
	 * @return
	 */
	public static String toHexString(byte[] b) {
		if(null == b){
			return "";
		}
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase() + " ");
		}
		return sb.toString();
	}

	/**
	 * 将字节数组转换为double
	 * @param b
	 * @return
	 */
	public static double bytes2Double(byte[] b) {
		long value = 0;
		for (int i = 0; i < 8; i++) {
			value |= ((long) (b[i] & 0xff)) << (8 * i);
		}
		return Double.longBitsToDouble(value);
	}

	/**
	 * 将double类型转换为字节数组
	 * @param d
	 * @return
	 */
	public static byte[] double2Bytes(double d) {
		long value = Double.doubleToRawLongBits(d);
		byte[] byteRet = new byte[8];
		for (int i = 0; i < 8; i++) {
			byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
		}
		return byteRet;
	}

	/**
	 * 将字节数组转换为int类型
	 * @param byteArray 长度不超过4的字节数组
	 * @return int类型的数值
	 */
	public static int byteArrayToInt(byte[] byteArray) {
		if( null == byteArray || byteArray.length > 4 ){
			return 0;
		}
		byte[] b = new byte[4];
		for(int i=0; i<byteArray.length; i++){
			if(byteArray.length -1 - i >= 0){
				b[3-i] = byteArray[ byteArray.length -1 - i ];
			}else{
				b[3-i] = 0x0;
			}
		}
		int n = 0;
		try {
			ByteArrayInputStream byteInput = new ByteArrayInputStream(b);
			DataInputStream dataInput = new DataInputStream(byteInput);
			n = dataInput.readInt();
		} catch (IOException e) {
			return 0;
		}
		return n;
	}
	
	/**
	 * 将字节表示的日期转换为日期类型
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date byteToDate(byte[] year, byte month, byte day, byte hour, byte minute, byte second){
		int intyear = ByteUtil.byteArrayToInt(year);
		if( intyear == 0 ){
			return null;
		}
		int intmonth = month;
		int intday = day;
		int inthour = hour;
		int intminute = minute;
		int intsecond = second;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		StringBuffer sb = new StringBuffer();
		sb.append(intyear).append("-").append(intmonth).append("-").append(intday).append(" ").append(inthour).append(":").append(intminute).append(":").append(intsecond);
		try {
			return sdf.parse( sb.toString() );
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 判断两个字节数组是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean byteArrayEquals(byte[] a, byte[] b){
		if( null == a || null == b ){
			return false;
		}
		if( a.length != b.length ){
			return false;
		}
		for(int i=0; i<a.length; i++){
			if(a[i] != b[i]){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 将字节数组的一部分拷贝到目标字节数组的指定位置
	 * 
	 * @param dest 目标字节数组
	 * @param destindex 目标字节数组的起始索引
	 * @param src 源字节数组
	 * @param srcindex 源字节数组要拷贝的起始索引
	 * @param length 源字节数组要拷贝的长度
	 */
	public static void copyByteArray(byte[] dest, int destindex, byte[] src, int srcindex, int length){
		if(dest.length < length || src.length < length){
			return;
		}
		
		for(int i=0; i<length; i++){
			dest[destindex+i] = src[srcindex+i];
		}
	}
	
	/**
	 * int转字节数组
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray1(int i) {   
	  byte[] result = new byte[4];   
	  result[0] = (byte)((i >> 24) & 0xFF);
	  result[1] = (byte)((i >> 16) & 0xFF);
	  result[2] = (byte)((i >> 8) & 0xFF); 
	  result[3] = (byte)(i & 0xFF);
	  return result;
	}
	
	/** 
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit 
     */  
    public static byte[] getBooleanArray(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }
}
