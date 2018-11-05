package com.taiji.pubsec.ajqlc.baq.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.bean.CarryGoodsInfoBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.CarryGoodsRecordBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.CarryGoodsReturnRecordBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.LockerBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.PersonBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.ReturnDataBean;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsInfo;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsManageSnapshot;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsReturnRecord;
import com.taiji.pubsec.ajqlc.baq.model.GoodsSnapshot;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.Locker;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsInfoService;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsManageSnapshotService;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsReturnRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.ILockerService;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service.LockerService;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.common.tool.base64.Base64CryptFmtUtil;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 随身物品维护
 * 
 * @author sunjd
 *
 */
@Controller("carryGoodsInfoAction")
@Scope("prototype")
public class CarryGoodsInfoAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;
	
	public static final String DATE_FOMAT_STR = "yyyy-MM-dd HH:mm";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CarryGoodsInfoAction.class);
	
	@Resource
	private ICarryGoodsInfoService carryGoodsInfoService;		//随身物品入库信息服务接口
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;		//使用单服务接口
	
	@Resource
	private IOperateRecordService operateRecordService;  //操作记录接口
	
	@Resource
	private ILockerService lockerService;// 储物柜接口

	@Resource
	private LockerService cwgService;// 储物柜接口LockerService
	
	@Resource
	private IDictionaryItemService dictionaryItemService;// 字典项接口
	
	@Resource
	private ICarryGoodsReturnRecordService carryGoodsReturnRecordService;		//随身物品返还记录接口
	
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService; //附件接口
	
	@Resource
	private ICarryGoodsManageSnapshotService carryGoodsManageSnapshotService; //快照
	
	private CarryGoodsInfoBean carryGoodsInfoBean ;	//随身物品入库信息及检查记录list
				
	private List<CarryGoodsInfoBean> carryGoodsInfoBeanModifyList ;	//随身物品入库信息及检查记录(修改)list
	
	private String handlingAreaReceiptId;	//使用单id
	
	private String state;
	
	private List<LockerBean> lockerBeanList;		//储物柜BeanList
	
	private String updateTime;
	
	private List<CarryGoodsReturnRecordBean> carryBeanList ;	//物品返还记录list
	
	private List<CarryGoodsRecordBean> carryGoodsRecordBeanList;	//随身物品记录
	
	private List<CarryGoodsReturnRecord> modifyReceiver = new ArrayList<CarryGoodsReturnRecord>(); //修改领取人列表
	
	private Person modifyPeople;
	
	private List<String> delLst = new ArrayList<String>();
	
	private String carryGoodsId;		//随身物品记录id
	
	private String harId;
	
	private boolean modifyBtnFlag;
	
	private boolean printBtnFlag;
	
	private boolean finishBtnFlag;
	
	private String justShow;
	
	private String handlingPolice;
	
	private ReturnDataBean returnMap = new ReturnDataBean();
	
	private String boxName;
	
	private String boxCode;
	
	private String boxId;
	
	private String photo;

	private String photoName;
	
	private String photoId;//相片的附件ID
	
	private String photoBase64;//相片附件的base64位码
	
	public String saveBase64Photo(){
		byte[] file = Base64CryptFmtUtil.decodeToByte(photo.getBytes());
		InputStream is = new ByteArrayInputStream(file);
		Attachment att = new Attachment();
		attachmentCustomizedService.create(att, photoName + "照片.png", is, null, null);
		photo = att.getId();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String showCarryGoodsInfo(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		if("true".equals(justShow)){
			setModifyBtnFlag(false);
			setPrintBtnFlag(false);
			setFinishBtnFlag(false);
			return SUCCESS;
		}
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(harId);
		if(Constant.SYDZT_YWC.equals(har.getStatus())){
			setFinishBtnFlag(false);
			setModifyBtnFlag(false);
			setPrintBtnFlag(true);
		}else{
			setPrintBtnFlag(false);
			CarryGoodsInfo cgInfo = har.getCarryGoodsInfo();
			mPerson = (Map<String, String>)userMap.get("person");
			if(cgInfo !=null && cgInfo.getRecordModifyPeople() != null &&!mPerson.get("code").equals(cgInfo.getRecordModifyPeople().getCode())){
				setModifyBtnFlag(false);
			}else{
				//TODO SessionUserDetailsUtil.isResourceAccess("/handlingAreaReceipt/*");
				setModifyBtnFlag(true);
			}
			setFinishBtnFlag(true);
		}
		List<Map<String, String>> roles = (List<Map<String, String>>) userMap
				.get("roles");
		for (Map<String, String> role : roles) {
			if (SUPER_ROLE_CODE.equals(role.get("code"))) {
				setModifyBtnFlag(true);
				break;
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+harId);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String showCarryGoodsReturnRecord(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		if("true".equals(justShow)){
			setModifyBtnFlag(false);
			setPrintBtnFlag(false);
			setFinishBtnFlag(false);
			return SUCCESS;
		}
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(harId);
		if(Constant.SYDZT_YWC.equals(har.getStatus())){
			setFinishBtnFlag(false);
			setModifyBtnFlag(false);
			setPrintBtnFlag(true);
		}else{
			setPrintBtnFlag(false);
			CarryGoodsInfo cgInfo = har.getCarryGoodsInfo();
			mPerson = (Map<String, String>)userMap.get("person");
			if(cgInfo !=null && cgInfo.getReturnModifyPeople() != null  &&!mPerson.get("code").equals(cgInfo.getReturnModifyPeople().getCode())){
				setModifyBtnFlag(false);
			}else{
				//TODO SessionUserDetailsUtil.isResourceAccess("/handlingAreaReceipt/*");
				setModifyBtnFlag(true);
			}
			setFinishBtnFlag(true);
		}
		List<Map<String, String>> roles = (List<Map<String, String>>) userMap
				.get("roles");
		for (Map<String, String> role : roles) {
			if (SUPER_ROLE_CODE.equals(role.get("code"))) {
				setModifyBtnFlag(true);
				break;
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+harId);
		return SUCCESS;
	}
	
	/**
	 * 获取空箱子
	 * 
	 * @return SUCCESS
	 */
	public String findAnEmptyBox(){
		Locker locker = lockerService.findOneRandomEmptyLocker();
		boxName = locker.getName();
		boxCode = locker.getCode();
		setBoxId(locker.getId());
		return SUCCESS;
	}
	
	/**
	 * 开箱
	 * 
	 * @return SUCCESS
	 */
	public String openBox(){
		Locker locker = lockerService.findLockerById(boxCode);
		String[] arr = locker.getCode().split("-");
		cwgService.open(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));
		return SUCCESS;
	}
	
	/**
	 * 查询随身物品记录 
	 * 
	 * @return SUCCESS
	 */
	public String queryCarryGoodsReceipt(){
		CarryGoodsInfo cgf = carryGoodsInfoService.findCarryGoodsInfoByHandlingAreaReceiptId(handlingAreaReceiptId);
		carryGoodsInfoBean = carryGoodsInfoSwitchCarryGoodsInfoBean(cgf);
		if(cgf == null){
			this.setFlag("false");
		}
		if(cgf != null){
			handlingPolice = cgf.getHandlingAreaReceipt().getBasicCase().getHandlingPolice();
			this.setFlag("true");
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+harId);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打开维护随身物品保管页面(使用单ID:"+handlingAreaReceiptId+")");
		return SUCCESS;
	}
	
	/**
	 * 新增随身物品信息及记录
	 * 
	 * @return SUCCESS
	 */
	public String addCarryGoodsInfo(){
		CarryGoodsInfo carryGoodsInfo = new CarryGoodsInfo();
		carryGoodsInfo.setHandlingAreaReceipt(handlingAreaReceiptService.findHandlingAreaReceiptById(carryGoodsInfoBean.getHandlingAreaReceiptId()));
		carryGoodsInfoService.saveCarryGoodsInfo(carryGoodsInfo);
		for(CarryGoodsRecordBean bean : carryGoodsInfoBean.getCarryGoodsRecordBeans()){
			CarryGoodsRecord carryGoodsRecord = assembleCarryGoodsRecordBean(bean, carryGoodsInfo);
			carryGoodsInfoService.saveCarryGoodsRecord(carryGoodsRecord);
			changeBoxStatusUse(carryGoodsRecord);
			if(!StringUtils.isBlank(bean.getPhotoBase64())){
				Attachment att = attachmentCustomizedService.findById(bean.getPhotoBase64());
				att.setTargetId(carryGoodsRecord.getId());
				att.setType(CarryGoodsRecord.class.getName());
				attachmentCustomizedService.save(att);
			}else if(!StringUtils.isBlank(bean.getPhotoId())){
				Attachment att = attachmentCustomizedService.findById(bean.getPhotoId());
				att.setTargetId(carryGoodsRecord.getId());
				att.setType(CarryGoodsRecord.class.getName());
				attachmentCustomizedService.save(att);
			}
		}
		createReceipt("", "0", "新增随身物品", carryGoodsInfoBean.getHandlingAreaReceiptId());
		if(carryGoodsInfo.getRecordModifyPeople() != null){
			this.setModifyPerson(carryGoodsInfo.getRecordModifyPeople().getName());
			this.setModifyTime(DateTimeUtil.formatDateTime(carryGoodsInfo.getRecordUpdatedTime(), Constant.DATE_FORMAT));
		}else{
			this.setModifyPerson("");
			this.setModifyTime("");
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护随身物品保管页面(使用单ID:"+carryGoodsInfoBean.getHandlingAreaReceiptId()+")");
		return SUCCESS;
	}
	
	/**
	 * 修改随身物品记录
	 * 
	 * @return SUCCESS
	 */
	public String modifyCreateReceipt(){
		CarryGoodsInfo carryGoodsInfo = carryGoodsInfoService.findCarryGoodsInfoById(carryGoodsInfoBean.getId());
		for(String str : delLst){
			try{
				attachmentCustomizedService.deleteByTargetIdAndType(str, CarryGoodsRecord.class.getName());
			} catch (Exception e) {
				// handle exception
			}
			changeBoxStatusUnuse(carryGoodsInfo, str);
			CarryGoodsRecord cgr = carryGoodsInfoService.findCarryGoodsRecordById(str);
			carryGoodsInfo.getCarryGoodsRecords().remove(cgr);
			carryGoodsInfoService.deleteCarryGoodsRecord(str);
		}
		if(carryGoodsInfoBean.getCarryGoodsRecordBeans() != null){
			for(CarryGoodsRecordBean bean : carryGoodsInfoBean.getCarryGoodsRecordBeans()){
				if(StringUtils.isBlank(bean.getId())){
					CarryGoodsRecord carryGoodsRecord = assembleCarryGoodsRecordBean(bean, carryGoodsInfo);
					carryGoodsInfoService.saveCarryGoodsRecord(carryGoodsRecord);
					changeBoxStatusUse(carryGoodsRecord);
					if(!StringUtils.isBlank(bean.getPhotoBase64())){
						Attachment att = attachmentCustomizedService.findById(bean.getPhotoBase64());
						att.setTargetId(carryGoodsRecord.getId());
						att.setType(CarryGoodsRecord.class.getName());
						attachmentCustomizedService.save(att);
					}else if(!StringUtils.isBlank(bean.getPhotoId())){
						Attachment att = attachmentCustomizedService.findById(bean.getPhotoId());
						att.setTargetId(carryGoodsRecord.getId());
						att.setType(CarryGoodsRecord.class.getName());
						attachmentCustomizedService.save(att);
					}
				}else{
					CarryGoodsRecord carryGoodsRecord = carryGoodsInfoService.findCarryGoodsRecordById(bean.getId());
					changeBoxStatusUnuse(carryGoodsInfo, carryGoodsRecord.getId());
					if(StringUtils.isBlank(bean.getStrongboxNum())){
						carryGoodsRecord.setPosition(bean.getPosition());
					}else{
						Locker locker = lockerService.findLockerById(bean.getStrongboxNum());
						carryGoodsRecord.setLocker(locker);
					}
					carryGoodsInfoService.updateCarryGoodsRecord(carryGoodsRecord);
					changeBoxStatusUse(carryGoodsRecord);
				}
			}
		}
		carryGoodsInfo.setCarryGoodsRecords(null);
		carryGoodsInfoService.updateCarryGoodsInfo(carryGoodsInfo);
		createReceipt("", "0", "修改随身物品", carryGoodsInfoBean.getHandlingAreaReceiptId());
		CarryGoodsInfo cgi = carryGoodsInfoService.findCarryGoodsInfoById(carryGoodsInfo.getId());
		if(cgi!=null && cgi.getRecordModifyPeople() != null){
			this.setModifyPerson(cgi.getRecordModifyPeople().getName());
			this.setModifyTime(DateTimeUtil.formatDateTime(cgi.getRecordUpdatedTime(), Constant.DATE_FORMAT));
		}else{
			this.setModifyPerson("");
			this.setModifyTime("");
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护随身物品保管页面(使用单ID:"+carryGoodsInfoBean.getHandlingAreaReceiptId()+")");
		return SUCCESS;
	}
	
	/**
	 * 查询随身物品返还记录
	 * 根据随身物品的状态和使用单id
	 * @return SUCCESS
	 */
	public String queryReturnRecordByGoodsRecordId(){
		carryGoodsRecordBeanList = new ArrayList<CarryGoodsRecordBean>();
		List<CarryGoodsRecord> carry = carryGoodsInfoService.findCarryGoodsRecordByReceiptIdAndStatus(handlingAreaReceiptId, state);
		boolean isAdmin = false;
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		@SuppressWarnings("unchecked")
		List<Map<String, String>> roles = (List<Map<String, String>>) userMap
				.get("roles");
		for (Map<String, String> role : roles) {
			if (SUPER_ROLE_CODE.equals(role.get("code"))) {
				isAdmin = true;
				break;
			}
		}
		for(CarryGoodsRecord cgr : carry){
			CarryGoodsRecordBean  cgrb = carryGoodsRecordSwitchcarryGoodsInfoBean(cgr ,true, isAdmin, this.findCurrentPerson().getCode());
			carryGoodsRecordBeanList.add(cgrb);
		}
		CarryGoodsInfo cgf = carryGoodsInfoService.findCarryGoodsInfoByHandlingAreaReceiptId(handlingAreaReceiptId);
		carryGoodsInfoBean = new CarryGoodsInfoBean();
		HandlingAreaReceipt h = handlingAreaReceiptService.findHandlingAreaReceiptById(handlingAreaReceiptId);
		if(h!=null){
			CarryGoodsInfo  c = h.getCarryGoodsInfo();
			if(c!=null && c.getReturnUpdatedTime() != null){
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FOMAT_STR);
				this.setModifyTime(sdf.format(c.getReturnUpdatedTime()));
				this.setModifyPerson(c.getReturnModifyPeople().getName());
			}else{
				this.setModifyPerson("");
				this.setModifyTime("");
			}
		}
		if(cgf == null){
			return SUCCESS;
		}
		List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(cgf.getId(),
				CarryGoodsInfo.class.getName());
		if (attLst.isEmpty()) {
			return SUCCESS;
		}
//		List<FileObjectBean> foBeanList = new ArrayList<FileObjectBean>();
//		for(Attachment att : attLst){
//			FileObjectBean b = new FileObjectBean();
//			b.setId(att.getId());
//			b.setName(att.getName());
//			b.setSize(String.valueOf(att.getSize()));
//			try {
//				b.setBase64Str(Base64CryptFmtUtil.encode(FileFmtUtils.readByByteBuffer(att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream()), false));
//			} catch (UnsupportedEncodingException e) {
//				LOGGER.error(e.getMessage(), e);
//			}
//			foBeanList.add(b);
//		}
//		carryGoodsInfoBean.setFiles(foBeanList);
		
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "handlingAreaReceiptId="+handlingAreaReceiptId+",status="+state);
		return SUCCESS;
	}
	
	public String saveCarryGoodsManagement(){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FOMAT_STR);
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(handlingAreaReceiptId);
		//遍历在库id
		List<String> idLst = returnMap.getGoods();
		if(idLst != null){
			for(String str : idLst){
				CarryGoodsRecord cgr = carryGoodsInfoService.findCarryGoodsRecordById(str);
				cgr.setStatus(Constant.WPZT_ZK);
				carryGoodsInfoService.updateCarryGoodsRecord(cgr);
				CarryGoodsReturnRecord cgrr = cgr.getCarryGoodsReturnRecordList().get(cgr.getCarryGoodsReturnRecordList().size()-1);
				cgrr.setReturnStatus("0");
				carryGoodsReturnRecordService.update(cgrr);
			}
		}
		//需要保存的新返还单
		List<CarryGoodsReturnRecord> cgrrNewLst = new ArrayList<CarryGoodsReturnRecord>();
		//遍历临时取出id
		idLst = returnMap.getTemporary();
		if(idLst != null){
			for(String str : idLst){
				CarryGoodsRecord cgr = carryGoodsInfoService.findCarryGoodsRecordById(str);
				cgr.setStatus(Constant.WPZT_LSQC);
				cgr.setRemark("临时取出" + " " + handlingPolice + " " + sdf.format(new Date()));
				carryGoodsInfoService.updateCarryGoodsRecord(cgr);
				CarryGoodsReturnRecord cgrr = new CarryGoodsReturnRecord();
				if(cgr.getCarryGoodsReturnRecordList() != null && cgr.getCarryGoodsReturnRecordList().size() != 0){
					cgrr = cgr.getCarryGoodsReturnRecordList().get(cgr.getCarryGoodsReturnRecordList().size()-1);
				}
				cgrr.setCarryGoodsRecord(cgr);
				cgrr.setReceiver(handlingPolice);
				cgrr.setOperater(findCurrentPerson().getName());
				cgrr.setOperateTime(new Date());
				cgrr.setReturnStatus("1");
				cgrrNewLst.add(cgrr);
			}
		}
		//遍历移交扣押id
		idLst = returnMap.getDetain();
		if(idLst != null){
			for(String str : idLst){
				CarryGoodsRecord cgr = carryGoodsInfoService.findCarryGoodsRecordById(str);
				cgr.setStatus(Constant.WPZT_YJKY);
				cgr.setRemark("移交扣押" + " " + handlingPolice + " " + sdf.format(new Date()));
				carryGoodsInfoService.updateCarryGoodsRecord(cgr);
				changeBoxStatusUnuse(har.getCarryGoodsInfo(), str);
				CarryGoodsReturnRecord cgrr = new CarryGoodsReturnRecord();
				cgrr.setCarryGoodsRecord(cgr);
				cgrr.setReceiver(handlingPolice);
				cgrr.setOperater(findCurrentPerson().getName());
				cgrr.setOperateTime(new Date());
				cgrr.setReturnStatus("0");
				cgrrNewLst.add(cgrr);
			}
		}
		//遍历移交暂存id
		idLst = returnMap.getKeep();
		if(idLst != null){
			for(String str : idLst){
				CarryGoodsRecord cgr = carryGoodsInfoService.findCarryGoodsRecordById(str);
				cgr.setStatus(Constant.WPZT_YJZC);
				cgr.setRemark("移交暂存" + " " + handlingPolice + " " + sdf.format(new Date()));
				carryGoodsInfoService.updateCarryGoodsRecord(cgr);
				changeBoxStatusUnuse(har.getCarryGoodsInfo(), str);
				CarryGoodsReturnRecord cgrr = new CarryGoodsReturnRecord();
				cgrr.setCarryGoodsRecord(cgr);
				cgrr.setReceiver(handlingPolice);
				cgrr.setOperater(findCurrentPerson().getName());
				cgrr.setOperateTime(new Date());
				cgrr.setReturnStatus("0");
				cgrrNewLst.add(cgrr);
			}
		}
		//遍历立即返还id
		idLst = returnMap.getReturnObj();
		if(idLst != null){
			for(String str : idLst){
				CarryGoodsRecord cgr = carryGoodsInfoService.findCarryGoodsRecordById(str);
				cgr.setStatus(Constant.WPZT_YFH);
				cgr.setRemark("立即返还" + " " + handlingPolice + " " + sdf.format(new Date()));
				carryGoodsInfoService.updateCarryGoodsRecord(cgr);
				changeBoxStatusUnuse(har.getCarryGoodsInfo(), str);
				CarryGoodsReturnRecord cgrr = new CarryGoodsReturnRecord();
				cgrr.setCarryGoodsRecord(cgr);
				cgrr.setReceiver(handlingPolice);
				cgrr.setOperater(findCurrentPerson().getName());
				cgrr.setOperateTime(new Date());
				cgrr.setReturnStatus("0");
				cgrrNewLst.add(cgrr);
			}
		}
		if(cgrrNewLst.size() > 0){
			carryGoodsReturnRecordService.saveCarryGoodsReturnRecord(cgrrNewLst);
		}
		har.getCarryGoodsInfo().setReturnModifyPeople(this.findCurrentPerson());
		har.getCarryGoodsInfo().setReturnUpdatedTime(new Date());
		carryGoodsInfoService.saveCarryGoodsInfo(har.getCarryGoodsInfo());
		createReceipt("", "0", "新增物品返还记录", handlingAreaReceiptId);
		if(har != null && har.getCarryGoodsInfo().getReturnModifyPeople() != null){
			this.setModifyPerson(har.getCarryGoodsInfo().getReturnModifyPeople().getName());
			this.setModifyTime(DateTimeUtil.formatDateTime(har.getCarryGoodsInfo().getReturnUpdatedTime(), Constant.DATE_FORMAT));
		}else{
			this.setModifyPerson("");
			this.setModifyTime("");
		}
		List<GoodsSnapshot> lstqc	=new ArrayList<GoodsSnapshot>();
		//快照--临时取出
		lstqc.addAll(addKZList(returnMap.getTemporary(),Constant.WPZT_LSQC));
		//快照--移交扣押取出		
		lstqc.addAll(addKZList(returnMap.getDetain(),Constant.WPZT_YJKY));
		//快照--立即返还取出	
		lstqc.addAll(addKZList(returnMap.getReturnObj(),Constant.WPZT_YFH));
		//快照--移交暂存取出		
		lstqc.addAll(addKZList(returnMap.getKeep(),Constant.WPZT_YJZC));
		
		saveKZ(lstqc);
		
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护随身物品保管页面(使用单ID:"+handlingAreaReceiptId+")");
		return SUCCESS;
	}
	
	
	private void saveKZ(List<GoodsSnapshot> lstqc){
		CarryGoodsManageSnapshot cst=new CarryGoodsManageSnapshot();
		cst.setHandlingAreaReceiptId(handlingAreaReceiptId);
		cst.setSnapshotTime(new Date());
		if(lstqc.size()>0){
			carryGoodsManageSnapshotService.saveCarryGoodsManageSnapshot(cst, lstqc);
		}
	}
	
	//保存快照的公共方法
	private List<GoodsSnapshot> addKZList(List<String> lst, String stu) {		
		List<GoodsSnapshot> lstqc=new ArrayList<GoodsSnapshot>();
		for(String str:lst){
			GoodsSnapshot g=new GoodsSnapshot();
			g.setCarryGoodsRecordId(str);
			g.setOperationType(stu);
			lstqc.add(g);
		}
		return lstqc;
	}

	//设置箱子占用
	private void changeBoxStatusUse(CarryGoodsRecord cgr){
		if((Constant.WPZT_ZK.equals(cgr.getStatus()) || Constant.WPZT_LSQC.equals(cgr.getStatus())) && cgr.getLocker() != null && Constant.CWGZT_KX.equals(cgr.getLocker().getStatus())){
			cgr.getLocker().setStatus(Constant.CWGZT_SYZ);
			lockerService.updateLocker(cgr.getLocker());
		}
	}
	
	//设置箱子空闲
	private void changeBoxStatusUnuse(CarryGoodsInfo cgi, String cgrId){
		List<CarryGoodsRecord> cgrLst = cgi.getCarryGoodsRecords();
		boolean unusedFlag = true;
		CarryGoodsRecord cgr = null;
		for(CarryGoodsRecord temp : cgrLst){
			if(temp.getId().equals(cgrId)){
				if(temp.getLocker() == null){
					return;
				}else{
					cgr = temp;
					break;
				}
			}
		}
		for(CarryGoodsRecord temp : cgrLst){
			//遍历物品记录，查找不是自己且和自己用一个箱子的记录
			if(cgr.getLocker() != null && temp.getLocker() != null //自己和正在遍历的物品都是存在箱子里的
					&& !temp.getId().equals(cgrId) //正在遍历的物品不是自己
					&& temp.getLocker().getId().equals(cgr.getLocker().getId()) //这两个物品用的一个箱子，temp是在库状态
					&& (Constant.WPZT_ZK.equals(temp.getStatus()) || Constant.WPZT_LSQC.equals(temp.getStatus()))){
				unusedFlag = false;//说明此柜子依然在使用，不设置空闲
				break;
			}
		}
		if(unusedFlag){
			cgr.getLocker().setStatus(Constant.CWGZT_KX);
			lockerService.updateLocker(cgr.getLocker());
		}
	}
	
	/**
	 * 新增物品返还记录
	 * 
	 * @return SUCCESS
	 * @throws ParseException 
	 */
	 @Deprecated
	public String addReturnRecord() throws ParseException{
		List<CarryGoodsReturnRecord> carryGoodsReturnRecordList = new ArrayList<CarryGoodsReturnRecord>();
		if(carryBeanList != null && carryBeanList.size() > 0){
			for(CarryGoodsReturnRecordBean c : carryBeanList){
				CarryGoodsReturnRecord cgr = CarryGoodsReturnRecordBeanSwitchCarryGoodsReturnRecord(c);
				carryGoodsReturnRecordList.add(cgr);
			}
			carryGoodsReturnRecordService.saveCarryGoodsReturnRecord(carryGoodsReturnRecordList);
			createReceipt("", "0", "新增物品返还记录", handlingAreaReceiptId);
		}
		if(modifyReceiver != null && modifyReceiver.size() > 0){
			for(CarryGoodsReturnRecord cgrr : modifyReceiver){
				CarryGoodsReturnRecord temp = carryGoodsReturnRecordService.findById(cgrr.getId());
				temp.setOperater(this.findCurrentPerson().getCode());
				temp.setOperateTime(new Date());
				temp.setReceiver(cgrr.getReceiver());
				temp.setReceiverIdCard(cgrr.getReceiverIdCard());
				carryGoodsReturnRecordService.update(temp);
			}
			createReceipt("", "0", "修改物品领取人", handlingAreaReceiptId);
		}
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(handlingAreaReceiptId);
		if(har != null && har.getCarryGoodsInfo().getReturnModifyPeople() != null){
			this.setModifyPerson(har.getCarryGoodsInfo().getReturnModifyPeople().getName());
			this.setModifyTime(DateTimeUtil.formatDateTime(har.getCarryGoodsInfo().getReturnUpdatedTime(), Constant.DATE_FORMAT));
		}else{
			this.setModifyPerson("");
			this.setModifyTime("");
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	/**
	 * 根据随身物品Id查询随身物品记录
	 * @return SUCCESS
	 */
	public String queryCarryGoodsById(){
		CarryGoodsReturnRecord  cgrr = carryGoodsReturnRecordService.findReturnRecordByGoodsRecordId(carryGoodsId);
		if(cgrr!=null){
			carryGoodsId = cgrr.getId();
		}else{
			carryGoodsId = null;
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+carryGoodsId);
		return SUCCESS;
	}
		
	public String findBase64Byid(){
		Attachment att= attachmentCustomizedService.findById(photoId);
		if (att!=null) {
			try{
				InputStream isTemp = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
				byte[] buffer = new byte[isTemp.available()];
				isTemp.read(buffer, 0, isTemp.available());
				byte[] bytes = Base64.encodeBase64(buffer, false);
				photoBase64=new String(bytes);
			}catch (Exception e) {
				// handle exception
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 私有方法  随身物品Bean转model
	 * @return carryGoodsInfo 随身物品model
	 */
	private CarryGoodsRecord assembleCarryGoodsRecordBean(CarryGoodsRecordBean cgrb, CarryGoodsInfo cgi){
		CarryGoodsRecord carryGoodsRecord = new CarryGoodsRecord(); 
		carryGoodsRecord.setId(StringUtils.isEmpty(cgrb.getId())?null:cgrb.getId());
		carryGoodsRecord.setCarryGoodsInfo(cgi);
		carryGoodsRecord.setGoodsName(cgrb.getGoodsName());
		carryGoodsRecord.setFeatures(cgrb.getFeatures());
		carryGoodsRecord.setUnitOfMeasurement(cgrb.getUnitOfMeasurement());
		carryGoodsRecord.setNum(cgrb.getNum());
		carryGoodsRecord.setQuantity(cgrb.getQuantity());
		carryGoodsRecord.setStatus(cgrb.getStatus());
		if(StringUtils.isBlank(cgrb.getStrongboxNum())){
			carryGoodsRecord.setPosition(cgrb.getPosition());
		}else{
			Locker locker = lockerService.findLockerById(cgrb.getStrongboxNum());
			carryGoodsRecord.setLocker(locker);
		}
		return carryGoodsRecord;
	}
	
	/**
	 * 私有方法  随身物品信息model转换随身物品信息Bean
	 * @param cgi 随身物品信息
	 * @param returnReceipty 是否查询返还记录
	 * @return cgib	随身物品信息Bean
	 */
	private CarryGoodsInfoBean carryGoodsInfoSwitchCarryGoodsInfoBean(CarryGoodsInfo cgi){
		if(cgi==null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FOMAT_STR);
		carryGoodsInfoBean = new CarryGoodsInfoBean();
		List<CarryGoodsRecordBean> carryGoodsRecordBean = new ArrayList<CarryGoodsRecordBean>();
		carryGoodsInfoBean.setHandlingAreaReceiptId(cgi.getHandlingAreaReceipt().getId());
		carryGoodsInfoBean.setId(cgi.getId());
		if(cgi.getModifyPeople() != null){
			PersonBean bean = new PersonBean();
			bean.setId(cgi.getModifyPeople().getId());
			bean.setName(cgi.getModifyPeople().getName());
			bean.setCode(cgi.getModifyPeople().getCode());
			carryGoodsInfoBean.setModifyPeopleBean(bean);
		}
		if(cgi.getRecordUpdatedTime() != null){
			carryGoodsInfoBean.setUpdatedTime(sdf.format(cgi.getRecordUpdatedTime()));
			PersonBean pb = new PersonBean();
			pb.setId(cgi.getRecordModifyPeople().getId());
			pb.setCode(cgi.getRecordModifyPeople().getCode());
			pb.setName(cgi.getRecordModifyPeople().getName());
			carryGoodsInfoBean.setModifyPeopleBean(pb);
		}
		for(CarryGoodsRecord cgr : cgi.getCarryGoodsRecords()){
			CarryGoodsRecordBean ca = carryGoodsRecordSwitchcarryGoodsInfoBean(cgr ,false, false, null);
			carryGoodsRecordBean.add(ca);
		}
		carryGoodsInfoBean.setCarryGoodsRecordBeans(carryGoodsRecordBean);
		return carryGoodsInfoBean;
	}
	
	/**
	 * 私有方法  随身物品记录model转bean
	 * @return
	 */
	private CarryGoodsRecordBean carryGoodsRecordSwitchcarryGoodsInfoBean(CarryGoodsRecord cgr ,boolean returnReceipty, boolean isAdmin, String userCode){
			CarryGoodsRecordBean cgrb = new CarryGoodsRecordBean();
			cgrb.setId(cgr.getId());
			cgrb.setRemark(cgr.getRemark() == null ? "" : cgr.getRemark());
			cgrb.setGoodsName(cgr.getGoodsName());
			cgrb.setFeatures(cgr.getFeatures());
			cgrb.setQuantity(cgr.getQuantity());
			cgrb.setUnitOfMeasurement(cgr.getUnitOfMeasurement());
			DictionaryItem di = dictionaryItemService.findById(cgr.getStatus());
			cgrb.setStatus(di==null?"":di.getId());   
			cgrb.setStatusStr(di==null?"":di.getName());
			cgrb.setNum(cgr.getNum());
			if(!StringUtils.isBlank(cgr.getPosition())){
				cgrb.setPosition(cgr.getPosition());
			}else{
				Locker locker = lockerService.findLockerById(cgr.getLocker().getId());
				cgrb.setStrongboxNum(locker==null?"":locker.getId());
				cgrb.setStrongboxNumStr(locker==null?"":locker.getCode());
				cgrb.setStrongboxName(locker==null?"":locker.getName());
			}
			if(cgr.getCarryGoodsReturnRecordList() != null && cgr.getCarryGoodsReturnRecordList().size() > 0){
				cgrb.setOperateFlag("false");
			}else{
				cgrb.setOperateFlag("true");
			}
			//图片
			List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(cgr.getId(),
					CarryGoodsRecord.class.getName());
			if (!attLst.isEmpty()) {
				for(Attachment att : attLst){
					cgrb.setPhotoId(att.getId());
//					try{
//						cgrb.setPhotoId(att.getId());
//						InputStream isTemp = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
//
//						byte[] buffer = new byte[isTemp.available()];
//						isTemp.read(buffer, 0, isTemp.available());
//						byte[] bytes = Base64.encodeBase64(buffer, false);
//						cgrb.setPhotoBase64(new String(bytes));
//					}catch (Exception e) {
//						// handle exception
//					}
				}
			}

		return cgrb;
	}
	
	/**
	 * 私有方法  物品返还记录bean转model
	 * @return
	 * @throws ParseException 
	 */
	private CarryGoodsReturnRecord CarryGoodsReturnRecordBeanSwitchCarryGoodsReturnRecord(CarryGoodsReturnRecordBean cgrrb) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FOMAT_STR);
		CarryGoodsReturnRecord cgrr = new CarryGoodsReturnRecord();
		cgrr.setNoReturnReason(cgrrb.getNoReturnReason());
		cgrr.setOperater(cgrrb.getOperater());
		cgrr.setOperateTime(sdf.parse(cgrrb.getOperateTime()));
		cgrr.setReceiver(cgrrb.getReceiver());
		cgrr.setReceiverIdCard(cgrrb.getReceiverIdCard());
		cgrr.setReceiveTime(sdf.parse(cgrrb.getReceiveTime()));
		cgrr.setReturnStatus(cgrrb.getReturnStatus());
		CarryGoodsRecord cgr = new CarryGoodsRecord();
		cgr.setId(cgrrb.getId());
		cgrr.setCarryGoodsRecord(cgr);
		return cgrr;
	}
	
	/**
	 * 私有方法  操作记录
	 * @param noteText	说明
	 * @param noteType	是否有附件
	 * @param operateContent	操作内容
	 * @param carryGoodsRecord	操作对象id
	 */
	private void createReceipt(String noteText,String noteType,String operateContent,String carryGoodsRecordId){
		OperateRecord operateRecord = new OperateRecord();
		operateRecord.setModelObject(HandlingAreaReceipt.class.getName());
		operateRecord.setModelObjectId(carryGoodsRecordId);
		operateRecord.setNoteText(noteText);
		operateRecord.setNoteType(noteType);
		operateRecord.setOperateContent(operateContent);
		operateRecord.setOperateTime(new Date());
		operateRecord.setOperator(this.findCurrentPerson().getName());
		operateRecordService.saveOperateRecord(operateRecord);
	}


	public CarryGoodsInfoBean getCarryGoodsInfoBean() {
		return carryGoodsInfoBean;
	}

	public void setCarryGoodsInfoBean(CarryGoodsInfoBean carryGoodsInfoBean) {
		this.carryGoodsInfoBean = carryGoodsInfoBean;
	}

	public List<CarryGoodsInfoBean> getCarryGoodsInfoBeanModifyList() {
		return carryGoodsInfoBeanModifyList;
	}

	public void setCarryGoodsInfoBeanModifyList(
			List<CarryGoodsInfoBean> carryGoodsInfoBeanModifyList) {
		this.carryGoodsInfoBeanModifyList = carryGoodsInfoBeanModifyList;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}
	
	public Person getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(Person modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	public List<LockerBean> getLockerBeanList() {
		return lockerBeanList;
	}

	public void setLockerBeanList(List<LockerBean> lockerBeanList) {
		this.lockerBeanList = lockerBeanList;
	}

	public List<CarryGoodsReturnRecordBean> getCarryBeanList() {
		return carryBeanList;
	}

	public void setCarryBeanList(List<CarryGoodsReturnRecordBean> carryBeanList) {
		this.carryBeanList = carryBeanList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<CarryGoodsRecordBean> getCarryGoodsRecordBeanList() {
		return carryGoodsRecordBeanList;
	}

	public void setCarryGoodsRecordBeanList(List<CarryGoodsRecordBean> carryGoodsRecordBeanList) {
		this.carryGoodsRecordBeanList = carryGoodsRecordBeanList;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<String> getDelLst() {
		return delLst;
	}

	public void setDelLst(List<String> delLst) {
		this.delLst = delLst;
	}

	public String getCarryGoodsId() {
		return carryGoodsId;
	}

	public void setCarryGoodsId(String carryGoodsId) {
		this.carryGoodsId = carryGoodsId;
	}

	public boolean isModifyBtnFlag() {
		return modifyBtnFlag;
	}

	public void setModifyBtnFlag(boolean modifyBtnFlag) {
		this.modifyBtnFlag = modifyBtnFlag;
	}

	public boolean isPrintBtnFlag() {
		return printBtnFlag;
	}

	public void setPrintBtnFlag(boolean printBtnFlag) {
		this.printBtnFlag = printBtnFlag;
	}

	public boolean isFinishBtnFlag() {
		return finishBtnFlag;
	}

	public void setFinishBtnFlag(boolean finishBtnFlag) {
		this.finishBtnFlag = finishBtnFlag;
	}

	public String getHarId() {
		return harId;
	}

	public void setHarId(String harId) {
		this.harId = harId;
	}

	public String getJustShow() {
		return justShow;
	}

	public void setJustShow(String justShow) {
		this.justShow = justShow;
	}

	public List<CarryGoodsReturnRecord> getModifyReceiver() {
		return modifyReceiver;
	}

	public void setModifyReceiver(List<CarryGoodsReturnRecord> modifyReceiver) {
		this.modifyReceiver = modifyReceiver;
	}

	public String getHandlingPolice() {
		return handlingPolice;
	}

	public void setHandlingPolice(String handlingPolice) {
		this.handlingPolice = handlingPolice;
	}

	public ReturnDataBean getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(ReturnDataBean returnMap) {
		this.returnMap = returnMap;
	}

	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getPhotoBase64() {
		return photoBase64;
	}

	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}
	
}
