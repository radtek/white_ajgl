package com.taiji.pubsec.ajqlc.sawp.util;

import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageAreaBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;

/**
 * 暂存物品的model 转Bean
 * @author xinfan
 *
 */
public class TempObjectModelToBean {
     /**
      * 将暂存物品储物区转为bean 
      * @param tsa
      * @return
      */
	 public static TemporaryStorageAreaBean tempStorageAreaToBean(TemporaryStorageArea tsa){
		 TemporaryStorageAreaBean tsaBean = new TemporaryStorageAreaBean();
		 tsaBean.setAddress(tsa.getAddress());
		 tsaBean.setCode(tsa.getCode());
		 tsaBean.setId(tsa.getId());
		 tsaBean.setName(tsa.getName());
		 if(tsa.getOrg() != null){
			 tsaBean.setOrgId(tsa.getOrg().getId());
			 tsaBean.setOrgName(tsa.getOrg().getShortName());
		 }
		 tsaBean.setRemark(tsa.getRemark());
		 tsaBean.setStatus(tsa.getStatus());
		 return tsaBean;
	 }
	/**
	 * 将暂存物品储物区柜为bean 
	 * @param tss
	 * @return
	 */
	 public static TemporaryStorageShelfBean tempStorageSelfToBean(TemporaryStorageShelf tss){
		 TemporaryStorageShelfBean tssBean = new TemporaryStorageShelfBean();
		 tssBean.setAddress(tss.getAddress());
		 if(tss.getArea() != null ){
			 tssBean.setAreaBean(tempStorageAreaToBean(tss.getArea()));
		 }
		 tssBean.setCode(tss.getCode());
		 tssBean.setId(tss.getId());
		 tssBean.setRemark(tss.getRemark());
		 tssBean.setStatus(tss.getStatus());
		 return tssBean;
	 }
}
