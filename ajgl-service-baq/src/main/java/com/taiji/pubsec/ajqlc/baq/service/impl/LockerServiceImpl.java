package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.bean.LockerStockedArticleBean;
import com.taiji.pubsec.ajqlc.baq.bean.StoredGoodsBean;
import com.taiji.pubsec.ajqlc.baq.bean.TemporaryRemovalBean;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.Locker;
import com.taiji.pubsec.ajqlc.baq.service.ILockerService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

/**
 * @author chengkai
 */
@Service("lockerService")
@Transactional(rollbackFor = Exception.class)
public class LockerServiceImpl implements ILockerService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	
	private static final String ARTICLE_STATUS_ZAIKU = "0000000010001"; //物品状态：在箱
	private static final String ARTICLE_STATUS_LINSHIQUCHU = "0000000010002"; //物品状态：临时取出
	private static final Logger LOGGER = LoggerFactory.getLogger(LockerServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public String saveLocker(Locker locker) {
		this.dao.save(locker);
		return locker.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> deleteLocker(String... lockerIds) {
		List<String> cannotDeleteLockerPositions = new ArrayList<String>();
		for(String id: lockerIds){
			String xql = "select a from CarryGoodsRecord as a where a.locker.id = ?";
			List<CarryGoodsRecord> cgrrList = this.dao.findAllByParams(CarryGoodsRecord.class, xql, new Object [] {id});
			if (cgrrList.isEmpty()) {
				this.dao.delete(Locker.class, id);
			} else {
				Locker locker = (Locker) this.dao.findById(Locker.class, id);
				cannotDeleteLockerPositions.add(locker.getLockerCode());
			}
		}
		return cannotDeleteLockerPositions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateLocker(Locker locker) {
		this.dao.update(locker);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateLockerStatus(String id, String status) {
		Locker locker = (Locker) this.dao.findById(Locker.class, id);
		locker.setStatus(status);
		this.dao.update(locker);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Locker findLockerById(String id) {
		return (Locker) this.dao.findById(Locker.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Locker findLockerByCode(String code) {
		String xql = "select l from Locker as l where l.code = ?";
		return (Locker) this.dao.findByParams(Locker.class, xql, new Object[]{code});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Locker findLockerByUnitIdAndPosition(String unitId, String position) {
		String xql = "select l from Locker as l where l.unit.id = ? and l.position = ?";
		return (Locker) this.dao.findByParams(Locker.class, xql, new Object[]{unitId, position});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Locker findLockerByUnitIdAndCode(String unitId, String code) {
		String xql = "select l from Locker as l where l.unit.id = ? and l.code = ?";
		return (Locker) this.dao.findByParams(Locker.class, xql, new Object[]{unitId, code});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<Locker> findLockerByKey(String code, String status, String unitId, String position, 
			String note, int pageNo, int pageSize) {
		String xql = "select l from Locker as l where 1 = 1 ";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		if(StringUtils.isNotEmpty(code)){
			xql += "and l.code like :code ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("code", "%" + SQLTool.SQLSpecialChTranfer(code) + "%");
		}
		if(StringUtils.isNotEmpty(status)){
			xql += "and l.status = :status ";
			xqlMap.put("status", status);
		}
		if(StringUtils.isNotEmpty(unitId)){
			xql += "and l.unit.id = :unitId ";
			xqlMap.put("unitId", unitId);
		}
		if(StringUtils.isNotEmpty(position)){
			xql += "and l.position like :position ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("position", "%" + SQLTool.SQLSpecialChTranfer(position) + "%");
		}
		if(StringUtils.isNotEmpty(note)){
			xql += "and l.note like :note ";
			xql = SQLTool.SQLAddEscape(xql);
			xqlMap.put("note", "%" + SQLTool.SQLSpecialChTranfer(note) + "%");
		}
		xql += "order by l.code asc";
		return this.dao.findByPage(Locker.class, xql, xqlMap, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Locker> findLockerByUnitIdAndStatus(String unitId, String status) {
		String xql = "select l from Locker as l where l.unit.id = ?";
		if(status != null){
			xql += " and l.status = ?";
			return this.dao.findAllByParams(Locker.class, xql, new Object[]{unitId, status});
		}else{
			return this.dao.findAllByParams(Locker.class, xql, new Object[]{unitId});
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isLockerUsed(String lockerId) {
		String xql = "select cgr from CarryGoodsRecord as cgr where cgr.locker.id = ?";
		List<CarryGoodsRecord> cgrList = this.dao.findAllByParams(CarryGoodsRecord.class, xql, new Object[]{lockerId});
		if(cgrList.isEmpty()){
			return false;
		}else{
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Locker> findLockerByCondition(Map<String, Object> data) {
		String xql = "select l from Locker as l where 1 = 1 ";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		
		if(StringUtils.isNotEmpty(String.valueOf(data.get("areaCode")))&&data.get("areaCode")!=null){// 区号
			xql += "and l.areaCode = :areaCode ";
			xqlMap.put("areaCode", String.valueOf(data.get("areaCode")));
		}
		if(StringUtils.isNotEmpty(String.valueOf(data.get("lockerCode")))&&data.get("lockerCode")!=null){// 柜号
			xql += "and l.lockerCode = :lockerCode ";
			xqlMap.put("lockerCode", String.valueOf(data.get("lockerCode")));
		}
		if(StringUtils.isNotEmpty(String.valueOf(data.get("unit")))&&data.get("unit")!=null){ //單位
			xql += "and l.unit.id = :unitId ";
			xqlMap.put("unitId", String.valueOf(data.get("unit")));
		}
		if(StringUtils.isNotEmpty(String.valueOf(data.get("status")))&&data.get("status")!=null){ //储物柜状态
			xql += "and l.status = :status ";
			xqlMap.put("status", String.valueOf(data.get("status")));
		}
		List<String> list=new ArrayList<String>();
		if(data.get("name")==null){
			list=null;
		}else{
			list=(List<String>) data.get("name");//名称
			int m=0;
			for(int i=0;i<list.size();i++){
				if(m==0){
					if(StringUtils.isNotEmpty(list.get(i))){
						xql += " and (l.name like :name" + i;
						xql = SQLTool.SQLAddEscape(xql);
						xqlMap.put("name" + i, "%" + SQLTool.SQLSpecialChTranfer(list.get(i)) + "%");
						m++;
					}
				}else{
					if(StringUtils.isNotEmpty(list.get(i))){
						xql += " or l.name like :name" + i;
						xql = SQLTool.SQLAddEscape(xql);
						xqlMap.put("name" + i, "%" + SQLTool.SQLSpecialChTranfer(list.get(i)) + "%");
					}
				}
				xql += " ) ";
			}
		}
		xql += "order by l.areaCode asc, l.lockerCode asc, l.name asc";
		return dao.findAllByParams(Locker.class, xql, xqlMap);
	
	}

	/**
	 * 将逗号分隔的字符串解析成list集合
	 * @param str
	 * @return
	 */
	public List<String> stringToList(String str){
		List<String> list=new ArrayList<String>();
		String [] strArr=str.split(",");
		for(int i=0;i<strArr.length;i++){
			list.add(strArr[i]);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllLockerCode() {
		String xql = "select DISTINCT l.areaCode from Locker as l where 1 = ? order by l.areaCode asc ";
		return dao.findAllByParams(String.class, xql, new Object[]{1});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllAreaCodeSecond(String areaCode) {
		String xql = "";
		if(StringUtils.isNotEmpty(areaCode)){
			xql+="select DISTINCT l.lockerCode from Locker as l where  l.areaCode = ?  order by l.areaCode asc";
			return dao.findAllByParams(String.class, xql, new Object[]{areaCode});
		}else{
			xql+="select DISTINCT l.lockerCode from Locker as l where  1 = ?  order by l.areaCode asc";
			return dao.findAllByParams(String.class, xql, new Object[]{1});
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public LockerStockedArticleBean queryLockerStockedConditions(String lockerId) {
		LockerStockedArticleBean bean = new LockerStockedArticleBean();
		String xql = "select c from CarryGoodsRecord as c where c.locker.id = ? and c.status = ?";
		List<CarryGoodsRecord> carryGoodsRecords = dao.findAllByParams(CarryGoodsRecord.class, xql, new Object[]{lockerId, ARTICLE_STATUS_ZAIKU});
		String xql1 = "select c from CarryGoodsRecord as c where c.locker.id = ? and c.status = ?";
		List<CarryGoodsRecord> carryGoodsRecords1 = dao.findAllByParams(CarryGoodsRecord.class, xql1, new Object[]{lockerId, ARTICLE_STATUS_LINSHIQUCHU});
		if(carryGoodsRecords != null&&carryGoodsRecords.size()!=0){
			bean.setHandlingAreaReceiptNum(carryGoodsRecords.get(0).getCarryGoodsInfo().getHandlingAreaReceipt().getBasicCase().getHandlingAreaReceiptNum());
			bean.setByQuestioningPeopleName(carryGoodsRecords.get(0).getCarryGoodsInfo().getHandlingAreaReceipt().getBasicCase().getByQuestioningPeopleName());
			bean.setStockTime(carryGoodsRecords.get(0).getCarryGoodsInfo().getUpdatedTime().getTime());
			bean.setLockerNum(carryGoodsRecords.get(0).getNum());
		}else if(carryGoodsRecords1 != null&&!carryGoodsRecords.isEmpty()){
			bean.setHandlingAreaReceiptNum(carryGoodsRecords1.get(0).getCarryGoodsInfo().getHandlingAreaReceipt().getBasicCase().getHandlingAreaReceiptNum());
			bean.setByQuestioningPeopleName(carryGoodsRecords1.get(0).getCarryGoodsInfo().getHandlingAreaReceipt().getBasicCase().getByQuestioningPeopleName());
			bean.setStockTime(carryGoodsRecords1.get(0).getCarryGoodsInfo().getUpdatedTime().getTime());
			bean.setLockerNum(carryGoodsRecords1.get(0).getNum());
		}
		List<StoredGoodsBean>articles = new ArrayList<StoredGoodsBean>();
		for(CarryGoodsRecord record: carryGoodsRecords){
			StoredGoodsBean stroebean=new StoredGoodsBean();
			stroebean.setName(record.getGoodsName());
			List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(record.getId(), CarryGoodsRecord.class.getName());
			stroebean.setNum(attLst.get(0).getName());
			for(Attachment att : attLst){
				InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
				try {
					byte[] buffer = new byte[is.available()];
					is.read(buffer, 0, is.available());
					stroebean.setCode(new String (Base64.encodeBase64(buffer, false)));
				} catch (Exception e) {
					LOGGER.error("照片转换失败");
				}
			}
			articles.add(stroebean);
		}
		List<TemporaryRemovalBean> takeOutArticles = new ArrayList<TemporaryRemovalBean>(); 
		for(CarryGoodsRecord record: carryGoodsRecords1){
			TemporaryRemovalBean temBean=new TemporaryRemovalBean();
			temBean.setName(record.getGoodsName());
			temBean.setPoliceName(record.getCarryGoodsReturnRecordList().get(0).getReceiver());
			temBean.setRemovalTime(record.getCarryGoodsReturnRecordList().get(0).getOperateTime().getTime());
			takeOutArticles.add(temBean);
		}
		bean.setArticles(articles);
		bean.setTakeOutArticles(takeOutArticles);
		return bean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Locker findOneRandomEmptyLocker() {
		List<Locker> lockers = (List<Locker>)dao.findAllByParams(Locker.class, "from Locker where status =?", new Object[]{"0000000027002"});
		Random random = new Random(new Date().getTime());
		int i = random.nextInt(lockers.size());
		return lockers.get(i);
	}

}
