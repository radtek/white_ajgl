package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsInfo;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsReturnRecord;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsReturnRecordService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * @author chengkai
 */
@Service("carryGoodsReturnRecordService")
public class CarryGoodsReturnRecordServiceImpl implements ICarryGoodsReturnRecordService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IPersonService personService;
	
	public static final String HASRETURNED = "0000000010003";	// 随身物品本次返还状态
	public static final String LEAVELOCKER = "0000000010002"; 	// 随身物品记录状态离库

	@SuppressWarnings("unchecked")
	@Override
	public CarryGoodsReturnRecord findReturnRecordByGoodsRecordId(String carryGoodsRecordId) {
		String xql = "select cgrr from CarryGoodsReturnRecord as cgrr where cgrr.carryGoodsRecord.id = ? order by cgrr.operateTime desc";
		List<CarryGoodsReturnRecord> cgrrList = this.dao.findAllByParams(CarryGoodsReturnRecord.class, xql, new Object[]{carryGoodsRecordId});
		if(cgrrList.isEmpty()){
			return null;
		}else{
			return cgrrList.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCarryGoodsReturnRecord(List<CarryGoodsReturnRecord> cgrrList) {
		for(CarryGoodsReturnRecord cgrr: cgrrList){
			this.dao.save(cgrr);
			if(HASRETURNED.equals(cgrr.getReturnStatus())){
				CarryGoodsRecord cgr = (CarryGoodsRecord) this.dao.findById(CarryGoodsRecord.class, cgrr.getCarryGoodsRecord().getId());
				cgr.setStatus(LEAVELOCKER);
				this.dao.update(cgr); 
				CarryGoodsInfo carryGoodsInfo = cgr.getCarryGoodsInfo();
				MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
				Map<String, Object> userMap = user.getUserMap() ;
				Map<String, String> mPerson = new HashMap<String, String>(0) ;
				if(userMap.get("person")!=null){
					mPerson = (Map<String, String>)userMap.get("person");
					carryGoodsInfo.setReturnModifyPeople(personService.findById(mPerson.get("id")));
				}
				carryGoodsInfo.setReturnUpdatedTime(new Date());
				
				this.dao.save(carryGoodsInfo);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CarryGoodsReturnRecord findById(String id) {
		return (CarryGoodsReturnRecord) this.dao.findById(CarryGoodsReturnRecord.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(CarryGoodsReturnRecord carryGoodsReturnRecord) {
		this.dao.update(carryGoodsReturnRecord);
	}
	
}