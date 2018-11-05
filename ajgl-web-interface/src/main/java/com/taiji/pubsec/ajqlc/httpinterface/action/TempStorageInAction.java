package com.taiji.pubsec.ajqlc.httpinterface.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.PageCommonAction;
import com.taiji.pubsec.ajqlc.httpinterface.action.util.ConstantTempStorage;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageInInfoBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.model.StoragePosition;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageOut;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.ajqlc.sawp.service.ITempStorageSelfService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageAreaService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageInService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageOutService;
import com.taiji.pubsec.ajqlc.sawp.util.TempObjectModelToBean;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
/**
 * 暂存物品入库单 （手机APP端调用）
 * 
 * @author xinfan
 */
@Controller("tempStorageInAction")
@Scope("prototype")
public class TempStorageInAction extends PageCommonAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ITemporaryStorageInService temporaryStorageInServiceImpl;
	
	@Resource
	private ITemporaryStorageAreaService temporaryStorageAreaServiceImpl;
	
	@Resource
	private ITempStorageSelfService tempStorageShelfServiceImpl;
	
	@Resource
	private ITemporaryStorageOutService temporaryStorageOutService;
	
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	@Resource
	private IDictionaryItemService dictionaryItemService;

	private String storageInCode;// 暂存入库单编号
	
	private String tempSaveAreaCode;//暂存物品保管区
	
	private String tempSaveSelfCode;//暂存物品储物架
	
	private String storageOutCode; //暂存出库单编号

	private TempStorageInInfoBean tempStorageInInfoBean;
	
	private TemporaryStorageShelfBean temporaryStorageShelfBean;
	
	
	
	
	
	private boolean flag =false;
    /**
     * 查询暂存入库单信息
     * @return
     */
	public String findTempStorageInDetail() {
		tempStorageInInfoBean = temporaryStorageInServiceImpl
				.findTemporaryStorageInDeatailByCode(storageInCode);
		if(tempStorageInInfoBean == null) return SUCCESS;
		if(tempStorageInInfoBean.getCaseCode() != null && 
				!tempStorageInInfoBean.getCaseCode().isEmpty()){
			CriminalBasicCase cbc = criminalCaseService.
					findCriminalCaseByCaseId(tempStorageInInfoBean.getCaseCode());
			tempStorageInInfoBean.setCaseName(cbc.getCaseName());
		}
		List<TemporaryStorageShelfBean> shelflist = temporaryStorageInServiceImpl.findStorageShelfByCode(storageInCode);
		if(shelflist != null){
			 for(TemporaryStorageShelfBean tss: shelflist){
				 tempStorageInInfoBean.setInStorageSelf(tss.getAddress());
				 tempStorageInInfoBean.setInStorageArea(tss.getAreaBean().getName());
			 }
		}
		return SUCCESS;
	}
	/**
	 * 更新入库单信息
	 * @return
	 */
	public String updateTempStorageIn() {
		TemporaryStorageShelf shelf = tempStorageShelfServiceImpl
				.findStorageSelfByCode(tempSaveSelfCode);
		TemporaryStorageIn  in = temporaryStorageInServiceImpl
				.findTemporaryStorageInByCode(storageInCode);
		StoragePosition positiion = new StoragePosition();
		positiion.setBgg(shelf);
		positiion.setRkd(in);
		List<StoragePosition> storagePositionList = new ArrayList<StoragePosition>();
		storagePositionList.add(positiion);
		in.setStoragePosition(storagePositionList);
		shelf.setStatus(ConstantTempStorage.TEMPSTORAGE_STATUS_USING);
		tempStorageShelfServiceImpl.updateTemporaryStorageSelf(shelf);
		temporaryStorageInServiceImpl.updateTemporaryStorageIn(in);
		this.setFlag(true);
		return SUCCESS;
	}
	/**
	 * 查询储物柜
	 * @return
	 */
	public String findStorageSelf(){
		TemporaryStorageShelf  shelf = tempStorageShelfServiceImpl
		.findStorageSelfByCode(tempSaveSelfCode);
		temporaryStorageShelfBean = TempObjectModelToBean.tempStorageSelfToBean(shelf);
		DictionaryItem dictionaryItem = dictionaryItemService.
				findDictionaryItemByDicTypeAndItemCode(
						ConstantTempStorage.TEMPSTORAGE_STATUS, temporaryStorageShelfBean.getStatus(), null);
		if(dictionaryItem != null){
			temporaryStorageShelfBean.setStatus(dictionaryItem.getName());
		}
		return SUCCESS;
	}
	/**
	 * 查找出库返还单信息
	 * @return
	 */
	public String findTempStorageOut(){
		TemporaryStorageOut storageOut = temporaryStorageOutService.findByCode(storageOutCode);
		tempStorageInInfoBean = temporaryStorageInServiceImpl
				.findTemporaryStorageInDeatailByCode(storageOut.getStorageIn().getCode());
		if(tempStorageInInfoBean.getCaseCode() != null && 
				!tempStorageInInfoBean.getCaseCode().isEmpty()){
			CriminalBasicCase cbc = criminalCaseService.
					findCriminalCaseByCaseId(tempStorageInInfoBean.getCaseCode());
			tempStorageInInfoBean.setCaseName(cbc.getCaseName());
		}
		DictionaryItem dictionaryItem = dictionaryItemService.
				findDictionaryItemByDicTypeAndItemCode(
						ConstantTempStorage.TEMPSTORAGE_SF, storageOut.getStatus(), null);
		if(dictionaryItem != null){
			tempStorageInInfoBean.setStorageOutStatus(dictionaryItem.getName());
		}
		tempStorageInInfoBean.setInStorageSelf(tempStorageInInfoBean
				.getInSaveSelfList().get(0).getAddress());
		tempStorageInInfoBean.setInStorageSelf(tempStorageInInfoBean
				.getInSaveSelfList().get(0).getAreaBean().getName());
		return SUCCESS;
	}
	/**
	 * 更新出库返还单
	 * @return
	 */
	public String updateTempStorageOut(){
		TemporaryStorageOut storageOut = temporaryStorageOutService.findByCode(storageOutCode);
		storageOut.setStatus(ConstantTempStorage.TEMPSTORAGE_OUT);
		temporaryStorageOutService.update(storageOut);
		this.setFlag(true);
		return SUCCESS;
	}

	public TempStorageInInfoBean getTempStorageInInfoBean() {
		return tempStorageInInfoBean;
	}

	public void setTempStorageInInfoBean(
			TempStorageInInfoBean tempStorageInInfoBean) {
		this.tempStorageInInfoBean = tempStorageInInfoBean;
	}


	public String getStorageInCode() {
		return storageInCode;
	}


	public void setStorageInCode(String storageInCode) {
		this.storageInCode = storageInCode;
	}
	public String getTempSaveAreaCode() {
		return tempSaveAreaCode;
	}
	public void setTempSaveAreaCode(String tempSaveAreaCode) {
		this.tempSaveAreaCode = tempSaveAreaCode;
	}
	public String getTempSaveSelfCode() {
		return tempSaveSelfCode;
	}
	public void setTempSaveSelfCode(String tempSaveSelfCode) {
		this.tempSaveSelfCode = tempSaveSelfCode;
	}
	public TemporaryStorageShelfBean getTemporaryStorageShelfBean() {
		return temporaryStorageShelfBean;
	}
	public void setTemporaryStorageShelfBean(
			TemporaryStorageShelfBean temporaryStorageShelfBean) {
		this.temporaryStorageShelfBean = temporaryStorageShelfBean;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getStorageOutCode() {
		return storageOutCode;
	}
	public void setStorageOutCode(String storageOutCode) {
		this.storageOutCode = storageOutCode;
	}
}
