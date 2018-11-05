package com.taiji.pubsec.ajqlc.sawp.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.sawp.action.bean.ArticleLockerBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageAreaBean;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.ajqlc.sawp.service.ITempStorageSelfService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageAreaService;
import com.taiji.pubsec.ajqlc.util.Constant;

/**
 * 暂管物品储物箱
 * @author lenovo
 *
 */
@Controller("transitStoreLockerAction")
@Scope("prototype")
public class TransitStoreLockerAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransitStoreLockerAction.class);
	
	@Resource
	private ITempStorageSelfService tempStorageSelfService;
	
	@Resource
	private ITemporaryStorageAreaService temporaryStorageAreaService;
	
	private List<ArticleLockerBean> articleLockerBeanList;	//储物箱）BeanList
	
	private String storageAreaId;	//物证保管区id
	
	private List<String> ids;		//储物箱idList
	
	private String code;		//自动生成的code
	
	private ArticleLockerBean articleLockerBean;	//储物箱  model
	
	private String lockerId;		//储物箱id
	
	private String oldLocation;			//修改之前的储物箱具体位置
	
	private String storageAreaCode; //物证保管区编码
	
	/**
	 * 查询暂存物品储物箱
	 * @return	success
	 */
	public String queryTransitStoreLocker(){
		if(storageAreaId==null){
			TemporaryStorageArea sh=temporaryStorageAreaService.findTemporaryStorageAreaByCode(storageAreaCode);
			if(sh!=null){
				storageAreaId=sh.getId();
			}
		}
		Pager<TemporaryStorageShelf> p = tempStorageSelfService.findAllTemporaryStorageShelfByPage(storageAreaId, this.getStart() / this.getLength(), this.getLength());
		List<TemporaryStorageShelf> articleLocker = p.getPageList();
		articleLockerBeanList = new ArrayList<ArticleLockerBean>();
		for(TemporaryStorageShelf al : articleLocker){
			articleLockerBeanList.add(assembleArticleLockerBean(al));
		}
		this.setTotalNum(p.getTotalNum());
		return SUCCESS;
	}
	
	/**
	 * 查看储物箱
	 * @return	success
	 */
	public String lookTransitStoreLocker(){
		if(StringUtils.isNotEmpty(lockerId)){
			TemporaryStorageShelf articleLocker = tempStorageSelfService.findStorageSelfById(lockerId);
			if(articleLocker!=null){
				articleLockerBean = this.assembleArticleLockerBean(articleLocker);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 新增暂存物品储物箱
	 * @return	success
	 */
	public String newTransitStoreLocker(){
		TemporaryStorageShelf alName = tempStorageSelfService.findStorageSelfById(articleLockerBean.getArea().getId());
		if(alName==null){
			tempStorageSelfService.saveTemporaryStorageShelf(assembleArticleLocker(articleLockerBean));
			this.setMsg("保存成功");
		}else{
			this.setMsg("储物箱位置已存在");
		}
		return SUCCESS;
	}
	
	/**
	 * 修改储物箱
	 * @return	success
	 */
	public String modifyTransitStoreLocker(){
		TemporaryStorageShelf alName = tempStorageSelfService.findStorageSelfByName(articleLockerBean.getArea().getId(), articleLockerBean.getLocation());
		TemporaryStorageShelf al = tempStorageSelfService.findStorageSelfById(articleLockerBean.getId());
		if(alName == null || oldLocation.equals(al.getAddress())){
			tempStorageSelfService.updateTemporaryStorageSelf(this.assembleArticleLocker(articleLockerBean));
			this.setMsg("修改成功");
			this.setFlag("true");
		}else{
			this.setMsg("修改失败，储物箱位置已存在");
			this.setFlag("false");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除暂存物品储物箱
	 * @return	success
	 */
	public String deleteLocker(){
		for(String id : ids){
			TemporaryStorageShelf locker = tempStorageSelfService.findStorageSelfById(id);
			if(locker!=null){
				try{
					tempStorageSelfService.deleteTemporaryStorageSelf(locker.getId());
					this.setMsg("删除成功");
				}catch(DataIntegrityViolationException e){
					this.setMsg("储物箱正在使用不可删除！");
					LOGGER.debug("产生异常：", e);;
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 生成暂存物品储物箱code
	 * @return	success
	 */
	public String generateCode(){
		code=tempStorageSelfService.acquireNum();
		return SUCCESS;
	}
	
	/**
	 * 私有方法  储物箱Bean转model
	 * @param aLBean	需要转换的  储物箱Bean
	 * @return articleLocker	转换过的model
	 */
	private TemporaryStorageShelf assembleArticleLocker(ArticleLockerBean aLBean){
		TemporaryStorageShelf articleLocker = new TemporaryStorageShelf();
		articleLocker.setCode(aLBean.getCode());
		articleLocker.setAddress(aLBean.getLocation());
		articleLocker.setRemark(aLBean.getRemark());
		articleLocker.setId(aLBean.getId() == null?null:aLBean.getId());
		articleLocker.setStatus(Constant.CWGZT_CODE_KX);
		TemporaryStorageArea storageArea = temporaryStorageAreaService.findTemporaryStorageAreaById(aLBean.getArea().getId());
		if(storageArea!=null){
			articleLocker.setArea(storageArea);
		}
		return articleLocker;
	}
	
	/**
	 * 私有方法  储物箱）model 转 Bean
	 * @param articleLocker	储物箱）model
	 * @return	articleLockerBeanList	储物箱BeanList
	 */
	private ArticleLockerBean assembleArticleLockerBean(TemporaryStorageShelf articleLocker){
			ArticleLockerBean lockerBean = new ArticleLockerBean();
			lockerBean.setCode(articleLocker.getCode());
			lockerBean.setId(articleLocker.getId());
			lockerBean.setLocation(articleLocker.getAddress());
			lockerBean.setRemark(articleLocker.getRemark());
			TemporaryStorageArea storageArea = temporaryStorageAreaService.findTemporaryStorageAreaById(articleLocker.getArea().getId());
			StorageAreaBean storageAreaBean = new StorageAreaBean();
			storageAreaBean.setId(storageArea.getId());
			storageAreaBean.setCode(storageArea.getCode());
			storageAreaBean.setName(storageArea.getName());
			lockerBean.setArea(storageAreaBean);
		return lockerBean;
	}

	public List<ArticleLockerBean> getArticleLockerBeanList() {
		return articleLockerBeanList;
	}

	public void setArticleLockerBeanList(List<ArticleLockerBean> articleLockerBeanList) {
		this.articleLockerBeanList = articleLockerBeanList;
	}

	public String getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ArticleLockerBean getArticleLockerBean() {
		return articleLockerBean;
	}

	public void setArticleLockerBean(ArticleLockerBean articleLockerBean) {
		this.articleLockerBean = articleLockerBean;
	}

	public String getLockerId() {
		return lockerId;
	}

	public void setLockerId(String lockerId) {
		this.lockerId = lockerId;
	}

	public String getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(String oldLocation) {
		this.oldLocation = oldLocation;
	}

	public String getStorageAreaCode() {
		return storageAreaCode;
	}

	public void setStorageAreaCode(String storageAreaCode) {
		this.storageAreaCode = storageAreaCode;
	}
}
