package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsInfo;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsInfoService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * @author chengkai
 */
@Service("carryGoodsInfoService")
@Transactional(rollbackFor = Exception.class)
public class CarryGoodsInfoServiceImpl implements ICarryGoodsInfoService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IPersonService personService;

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public void saveCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo, List<CarryGoodsRecord> cgrList) {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		if(userMap.get("person")!=null){
			mPerson = (Map<String, String>)userMap.get("person");
			carryGoodsInfo.setRecordModifyPeople(personService.findById(mPerson.get("id")));
		}
		carryGoodsInfo.setRecordUpdatedTime(new Date());
		this.dao.save(carryGoodsInfo);
		carryGoodsInfo.setCarryGoodsRecords(cgrList);
		for(CarryGoodsRecord cgr: cgrList){
			cgr.setCarryGoodsInfo(carryGoodsInfo);
			this.dao.save(cgr);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo) {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		if(userMap.get("person")!=null){
			mPerson = (Map<String, String>)userMap.get("person");
			carryGoodsInfo.setRecordModifyPeople(personService.findById(mPerson.get("id")));
		}
		carryGoodsInfo.setRecordUpdatedTime(new Date());
		this.dao.save(carryGoodsInfo);
		return carryGoodsInfo.getId();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String saveCarryGoodsRecord(CarryGoodsRecord carryGoodsRecord){
		this.dao.save(carryGoodsRecord);
		return carryGoodsRecord.getId();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteCarryGoodsRecord(String id){
		this.dao.delete(CarryGoodsRecord.class, id);
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public CarryGoodsRecord findCarryGoodsRecordById(String id){
		return (CarryGoodsRecord)this.dao.findById(CarryGoodsRecord.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateCarryGoodsRecord(CarryGoodsRecord carryGoodsRecord){
		this.dao.update(carryGoodsRecord);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo){
		this.dao.update(carryGoodsInfo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo, List<CarryGoodsRecord> cgrList) {
		
		CarryGoodsInfo oldCarryGoodsInfo = (CarryGoodsInfo) this.dao.findById(CarryGoodsInfo.class, carryGoodsInfo.getId());
		List<CarryGoodsRecord> oldCgrLista = new ArrayList<CarryGoodsRecord>();
		List<CarryGoodsRecord> oldCgrListb = new ArrayList<CarryGoodsRecord>();
		List<CarryGoodsRecord> oldCgrListc = new ArrayList<CarryGoodsRecord>();
		oldCgrLista.addAll(oldCarryGoodsInfo.getCarryGoodsRecords());
		oldCgrListb.addAll(oldCarryGoodsInfo.getCarryGoodsRecords());
		oldCgrListc.addAll(oldCarryGoodsInfo.getCarryGoodsRecords());
		List<CarryGoodsRecord> newCgrLista = new ArrayList<CarryGoodsRecord>();
		List<CarryGoodsRecord> newCgrListb = new ArrayList<CarryGoodsRecord>();
		List<CarryGoodsRecord> newCgrListc = new ArrayList<CarryGoodsRecord>();
		newCgrLista.addAll(cgrList);
		newCgrListb.addAll(cgrList);
		newCgrListc.addAll(cgrList);
		
		oldCgrLista.removeAll(newCgrLista);
		for(CarryGoodsRecord cgr: oldCgrLista){
			this.dao.delete(cgr);
			carryGoodsInfo.getCarryGoodsRecords().remove(cgr);
		}
		
		newCgrListb.retainAll(oldCgrListb);
		for(CarryGoodsRecord cgr: newCgrListb){
			cgr.setCarryGoodsInfo(carryGoodsInfo);
			this.dao.update(cgr);
		}
		
		newCgrListc.removeAll(oldCgrListc);
		for(CarryGoodsRecord cgr: newCgrListc){
			cgr.setCarryGoodsInfo(carryGoodsInfo);
			this.dao.save(cgr);
		}
		
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		if(userMap.get("person")!=null){
			mPerson = (Map<String, String>)userMap.get("person");
			carryGoodsInfo.setRecordModifyPeople(personService.findById(mPerson.get("id")));
		}
		carryGoodsInfo.setRecordUpdatedTime(new Date());
		
		this.dao.update(carryGoodsInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CarryGoodsInfo findCarryGoodsInfoById(String carryGoodsInfoId) {
		return (CarryGoodsInfo) this.dao.findById(CarryGoodsInfo.class, carryGoodsInfoId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CarryGoodsInfo findCarryGoodsInfoByHandlingAreaReceiptId(String handlingAreaReceiptId) {
		String xql = "select cgi from CarryGoodsInfo as cgi where cgi.handlingAreaReceipt.id = ?";
		return (CarryGoodsInfo) this.dao.findByParams(CarryGoodsInfo.class, xql, new Object[]{handlingAreaReceiptId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarryGoodsRecord> findCarryGoodsRecordByReceiptIdAndStatus(String handlingAreaReceiptId,
			String status) {
		String xql = "select cgr from CarryGoodsRecord as cgr where cgr.carryGoodsInfo.handlingAreaReceipt.id = ?";
		if(status != null){
			xql += " and cgr.status = ?";
			return this.dao.findAllByParams(CarryGoodsRecord.class, xql, new Object[]{handlingAreaReceiptId, status});
		}else{
			return this.dao.findAllByParams(CarryGoodsRecord.class, xql, new Object[]{handlingAreaReceiptId});
		}
	}

	@Override
	public List<CarryGoodsRecord> findCarryGoodsRecordByLocker(String lockerId) {
		String xql = "select cgr from CarryGoodsRecord as cgr where cgr.locker.id = ?";
		return this.dao.findAllByParams(CarryGoodsRecord.class, xql, new Object[]{lockerId});
	}

	
	
}