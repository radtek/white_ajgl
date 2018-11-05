/**
 * Copyright 2017 Taiji
 * All right reserved.
 * Created on 2017年2月20日 下午3:02:40
 */
package com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UDP连接发送帮助类
 * 
 * @author yucy
 *
 */
public class UDPSocketProvider {
	private static final Logger logger = LoggerFactory.getLogger(UDPSocketProvider.class);
	
	private static UDPSocketProvider instance = null;
	private DatagramSocket localUDPSocket = null;

	private UDPSocketProvider(){
	}
	
	public static UDPSocketProvider getInstance() {
		if (instance == null){
			instance = new UDPSocketProvider();
		}
		return instance;
	}

	/**
	 * 获得DatagramSocket对象
	 * 
	 * @return 单例的DatagramSocket对象
	 * @throws SocketException
	 */
	public DatagramSocket getLocalUDPSocket() throws SocketException {
		if( null == localUDPSocket ){
			logger.info("创建DatagramSocket...");
			localUDPSocket = new DatagramSocket();
		}
		if( localUDPSocket.isClosed() || localUDPSocket.isConnected() ){
			localUDPSocket.disconnect();
			localUDPSocket.close();
			logger.info("重新创建DatagramSocket...");
			localUDPSocket = new DatagramSocket();
		}
		return this.localUDPSocket;
	}
	
	/**
	 * 向服务器发送udp数据包，不等待返回信息
	 * 
	 * @param soServerBytes 要发送的数据包
	 * @param length 数据包长度
	 * @param serverip 服务器IP
	 * @param serverport 服务器端口
	 * @return 服务器地址不正确或者发送udp包例外返回false，否则true
	 */
	public boolean send(byte[] soServerBytes, int length, String serverip, int serverport) {
		InetAddress address;
		try {
			address = InetAddress.getByName(serverip);
		} catch (UnknownHostException e) {
			logger.error("创建服务器地址错误", e);
			return false;
		}
		
		DatagramPacket packet = new DatagramPacket(soServerBytes, length, address, serverport);
		
		try {
			UDPSocketProvider.getInstance().getLocalUDPSocket().send(packet);
		} catch (IOException e) {
			logger.error("发送数据包错误", e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 向服务器发送udp数据包，等待返回信息
	 * 
	 * @param soServerBytes 要发送的数据包
	 * @param length 发送数据包长度
	 * @param retlength 返回数据包的长度
	 * @param serverip 服务器IP
	 * @param serverport 服务器端口
	 * @return 服务器地址不正确或者发送udp包例外返回null，否则返回服务器应答数据
	 */
	public byte[] sendAndWaitReturn(byte[] soServerBytes, int length, int retlength, String serverip, int serverport) {
		InetAddress address;
		try {
			address = InetAddress.getByName(serverip);
		} catch (UnknownHostException e) {
			logger.error("创建服务器地址错误", e);
			return null;
		}
		
		DatagramPacket packet = new DatagramPacket(soServerBytes, length, address, serverport);
		
		try {
			UDPSocketProvider.getInstance().getLocalUDPSocket().send(packet);
			
			byte[] data = new byte[retlength];
			// 接收数据报的包
			DatagramPacket returnpacket = new DatagramPacket(data, data.length);
			DatagramSocket localUDPSocket = UDPSocketProvider.getInstance().getLocalUDPSocket();
			
			//设置超时时间
			localUDPSocket.setSoTimeout(1000);
			localUDPSocket.receive(returnpacket);
			byte[] returnbytes = returnpacket.getData();
			
			return returnbytes;
		} catch (IOException e) {
			logger.error("发送数据包错误", e);
		}
		
		return null;
	}

}
