/**
 * Copyright 2017 Taiji
 * All right reserved.
 * Created on 2017年2月27日 下午3:49:11
 */
package com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;

/**
 * @author yucy
 *
 */
public class LockerServiceImpl implements LockerService {
	private static final Logger logger = LoggerFactory.getLogger(LockerServiceImpl.class);
	
	//储物柜地址配置，格式：area=x, [tank=x,] ip=x, port=x
	private List<String> cwgAdressCfg = new ArrayList<>();

	private static Map<Integer, Adress> areaAdress = new HashMap<>();
	
	private static Map<String, Adress> tankAdress = new HashMap<>();
	
	public void init(){
		for(String cfg : cwgAdressCfg){
			putCfg(cfg);
		}
	}
	
	private void putCfg(String cfg){
		String c[] = cfg.split(",");
		Integer area = null;
		Integer tank = null;
		String ip = null;
		Integer port = null;
		
		for(String kvstr : c){
			String[] kvarray = kvstr.split("=");
			String k = kvarray[0].trim();
			String v = kvarray[1].trim();
			
			if("area".equals(k)){
				area = Integer.valueOf(v);
			}
			if("tank".equals(k)){
				tank = Integer.valueOf(v);
			}
			if("ip".equals(k)){
				ip = v;
			}
			if("port".equals(k)){
				port = Integer.valueOf(v);
			}
		}
		
		if(null != area && null != tank && null != ip && null != port){
			tankAdress.put(area + "-" + tank, new Adress(ip, port));
			return;
		}
		if(null != area && null == tank && null != ip && null != port){
			areaAdress.put(area, new Adress(ip, port));
			return;
		}
		logger.error("配置错误：{}", cfg);
	}
	
	/* (non-Javadoc)
	 * @see com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service.LockerService#open(java.lang.String)
	 */
	@Override
	public ResultBean open(int areano, int tankno, int boxno) {
		Adress adress = getAdressOfCwg(areano, tankno);
		
		if ( null == adress ){
			logger.error("不能找到储物柜{}-{}的IP设置！", areano, tankno);
			return new ResultBean(false, "不能找到储物柜的IP设置！");
		}
				
		byte[] pack = new byte[7];
		ByteUtil.copyByteArray(pack, 0, CwgConstants.CMD_FLAG_PC, 0, 2);
		pack[2] = CwgConstants.CMD_CODE_OPENSINGLEDORE;
		pack[3] = (byte) areano;
		pack[4] = (byte) tankno;
		pack[5] = (byte) boxno;
		int verify = 0;
		for(int i=0; i<6; i++){
			verify = verify + pack[i];
		}
		pack[6] = (byte) verify;
		logger.trace("打开箱门数据包 : {}", ByteUtil.toHexString(pack));
//		System.out.println("打开箱门数据包 : " + ByteUtil.toHexString(pack));
		
		byte[] r = UDPSocketProvider.getInstance().sendAndWaitReturn(pack, pack.length, 7, adress.getIp(), adress.getPort());
		logger.trace("储物柜返回数据包 : {}", ByteUtil.toHexString(r));
		if( null == r ){
			logger.error("打开箱门{}-{}失败", areano, tankno + "-" + boxno);
			return new ResultBean(false, "储物柜没有响应");
		}
		return new ResultBean(true);
	}
	
	/**
	 * 查询储物柜的状态。
	 * 注意：由于当前储物柜返回的状态只有数目没有每个箱门的状态信息，与其文档定义的协议不符合，并不能实际获得状态
	 * @param areano
	 * @param tankno
	 * @return
	 */
	public Map<String, Integer> lookupStatusOfDoors(int areano, int tankno){
		Map<String, Integer> status = new HashMap<String, Integer>();
		Adress adress = getAdressOfCwg(areano, tankno);
		
		if ( null == adress ){
			logger.error("不能找到储物柜{}-{}的IP设置！", areano, tankno);
			return status;
		}
		
		byte[] pack = new byte[6];
		ByteUtil.copyByteArray(pack, 0, CwgConstants.CMD_FLAG_PC, 0, 2);
		pack[2] = CwgConstants.CMD_CODE_LOOKUPSTATUS;
		pack[3] = (byte) areano;
		pack[4] = (byte) tankno;
		int verify = 0;
		for(int i=0; i<6; i++){
			verify = verify + pack[i];
		}
		pack[5] = (byte) verify;
		logger.trace("存物柜状态查询数据包 : {}", ByteUtil.toHexString(pack));
		System.out.println("存物柜状态查询数据包 : " + ByteUtil.toHexString(pack));
		byte[] r = UDPSocketProvider.getInstance().sendAndWaitReturn(pack, pack.length, 7, adress.getIp(), adress.getPort());
		logger.trace("储物柜返回数据包 : {}", ByteUtil.toHexString(r));
		System.out.println("储物柜返回数据包 : " + ByteUtil.toHexString(r));
		if( null == r ){
			logger.error("存物柜状态查询{}-{}失败", areano, tankno);
			System.out.println("存物柜状态查询" + areano + "-" + tankno + "失败");
			return status;
		}
		
		logger.trace("储物柜{}状态 : {}", areano + "-" + tankno, ByteUtil.toHexString(r));
		System.out.println("储物柜" + areano + "-" + tankno + "状态 : " + ByteUtil.toHexString(r));
		int rareano = r[3];
		int rtankno = r[4];
		int sum = r[5];
		int rverify = r[r.length-1];
		if( 7 + rverify != r.length){
			logger.trace("校验储物柜状态数据包错误！数据包长度为{}，校验长度{}", r.length, 7 + rverify);
			System.out.println("校验储物柜状态数据包错误！数据包长度为" + r.length + "，校验应为" + (7 + rverify));
			return status;
		}
		logger.trace("共获得{}个箱门状态。", sum);
		System.out.println("共获得" + sum + "个箱门状态。");
		String pre = rareano + "-" + rtankno + "-";
		for(int i=0; i<sum; i++){
			Integer stat = getStatusOfDoor(r[6 + i]);
			status.put(pre + (i + 1), stat);
		}
		return status;
	}
	
	private Integer getStatusOfDoor(byte b){
		byte[] statusarray = ByteUtil.getBooleanArray(b);
		int used = statusarray[0];
		int locked = statusarray[3];
		if( used == 0 && locked == 0 ){
			return CwgConstants.STATUS_UNUSED_NORMAL;
		}
		if( used == 0 && locked == 1 ){
			return CwgConstants.STATUS_UNUSED_LOCKED;
		}
		if( used == 1 && locked == 0 ){
			return CwgConstants.STATUS_USED_NORMAL;
		}
		if( used == 1 && locked == 1 ){
			return CwgConstants.STATUS_USED_LOCKED;
		}
		return -1;
	}
	
	public ResultBean openAllDores(int areano, int tankno) {
		Adress adress = getAdressOfCwg(areano, tankno);
		
		if ( null == adress ){
			logger.error("不能找到储物柜{}-{}的IP设置！", areano, tankno);
			return new ResultBean(false, "不能找到储物柜的IP设置！");
		}
		
		byte[] pack = new byte[6];
		ByteUtil.copyByteArray(pack, 0, CwgConstants.CMD_FLAG_PC, 0, 2);
		pack[2] = CwgConstants.CMD_CODE_OPENALLDORE;
		pack[3] = (byte) areano;
		pack[4] = (byte) tankno;
		int verify = 0;
		for(int i=0; i<5; i++){
			verify = verify + pack[i];
		}
		pack[5] = (byte) verify;
		logger.trace("打开箱门数据包 : {}", ByteUtil.toHexString(pack));
//		System.out.println(ByteUtil.toHexString(pack));
		
		byte[] r = UDPSocketProvider.getInstance().sendAndWaitReturn(pack, pack.length, 6, adress.getIp(), adress.getPort());
		logger.trace("储物柜返回数据包 : {}", ByteUtil.toHexString(r));
		if( null == r ){
			logger.error("打开所有箱门{}-{}失败", areano, tankno);
			return new ResultBean(false, "储物柜没有响应");
		}
		return new ResultBean(true);
	}

	/*
	 * 先从tankAdress里查找有区号柜号的地址，如果没有则从areaAdress里查找区号的地址
	 */
	private Adress getAdressOfCwg(int areano, int tankno){
		Adress adress = tankAdress.get(areano + "-" + tankno);
		if( null != adress ){
			return adress;
		}
		return areaAdress.get(areano);
	}

	public List<String> getCwgAdressCfg() {
		return cwgAdressCfg;
	}

	/**
	 * 储物柜地址配置，格式：area=x, [tank=x,] ip=x, port=x
	 * @param cwgAdressCfg
	 */
	public void setCwgAdressCfg(List<String> cwgAdressCfg) {
		this.cwgAdressCfg = cwgAdressCfg;
	}
	
	private class Adress {
		String ip;
		Integer port;
		
		private Adress(String ip, Integer port) {
			super();
			this.ip = ip;
			this.port = port;
		}
		public String getIp() {
			return ip;
		}
		public Integer getPort() {
			return port;
		}
	}
}
