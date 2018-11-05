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
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageAreaService;

/**
 * 涉案物品-储物箱维护
 * @author sunjd
 *
 */
@Controller("lockerMaintainAction")
@Scope("prototype")
public class LockerMaintainAction extends ReturnMessageAction{
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LockerMaintainAction.class);
	
	/**
	 * 储物箱服务接口
	 */
	@Resource
	private IArticleLockerService articleLockerService;
	/**
	 * 保管区服务接口
	 */
	@Resource
	private IStorageAreaService storageAreaService;
	
	private String storageAreaId;	//物证保管区id
	private List<ArticleLockerBean> articleLockerBeanList;	//储物箱）BeanList
	private ArticleLockerBean articleLockerBean;	//储物箱  model
	private String lockerId;		//储物箱id
	private List<String> ids;		//储物箱idList
	private String oldLocation;			//修改之前的储物箱具体位置
	private String code;		//自动生成的code
	
	/**
	 * 查询储物箱
	 * @return	success
	 */
	public String queryLocker(){
		Pager<ArticleLocker> p = articleLockerService.findArticleLockersByStorageAreaIdForPage(storageAreaId, this.getStart() / this.getLength(), this.getLength());
		List<ArticleLocker> articleLocker = p.getPageList();
		articleLockerBeanList = new ArrayList<ArticleLockerBean>();
		for(ArticleLocker al : articleLocker){
			articleLockerBeanList.add(assembleArticleLockerBean(al));
		}
		this.setTotalNum(p.getTotalNum());
		return SUCCESS;
	}
	
	/**
	 * 新增储物箱
	 * @return	success
	 */
	public String newLocker(){
		ArticleLocker alName = articleLockerService.findByName(articleLockerBean.getArea().getId(), articleLockerBean.getLocation());
		if(alName==null){
			ArticleLocker alCode = articleLockerService.findByCode(articleLockerBean.getArea().getId(), articleLockerBean.getCode());
			if(alCode==null){
				articleLockerService.save(assembleArticleLocker(articleLockerBean));
				this.setMsg("保存成功");
				this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "新增储物箱(保管区id:"+articleLockerBean.getArea().getId()+"储物箱位置:"+articleLockerBean.getLocation()+")");
			}else{
				this.setMsg("储物箱架号+箱号已存在");
				this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "新增储物箱(保管区id:"+articleLockerBean.getArea().getId()+"储物箱位置:"+articleLockerBean.getLocation()+"),新增失败-储物箱架号+箱号已存在");
			}
			
		}else{
			this.setMsg("储物箱位置已存在");
			this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "新增储物箱(保管区id:"+articleLockerBean.getArea().getId()+"储物箱位置:"+articleLockerBean.getLocation()+"),新增失败-储物箱位置已存在");
		}
		return SUCCESS;
	}
	
	/**
	 * 修改储物箱
	 * @return	success
	 */
	public String modifyLocker(){
		if(!oldLocation.equals(articleLockerBean.getLocation())){
			List<ArticleLocker> lst=articleLockerService.findLockersByStorageAreaId(articleLockerBean.getArea().getId());
			for(ArticleLocker lock:lst){
				if(articleLockerBean.getLocation().equals(lock.getLocation())){
					this.setMsg("修改失败，储物箱位置已存在");
					this.setFlag("false");
					this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "修改储物箱(保管区id:"+articleLockerBean.getArea().getId()+"储物箱位置:"+articleLockerBean.getLocation()+"),修改失败-储物箱位置已存在");
					return SUCCESS;
				}
			}
		}
		articleLockerService.update(this.assembleArticleLocker(articleLockerBean));
		this.setMsg("修改成功");
		this.setFlag("true");
		this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "修改储物箱(保管区id:"+articleLockerBean.getArea().getId()+"储物箱位置:"+articleLockerBean.getLocation()+")");
		return SUCCESS;
		
//		ArticleLocker alName = articleLockerService.findByName(articleLockerBean.getArea().getId(), articleLockerBean.getLocation());
//		ArticleLocker al = articleLockerService.findById(articleLockerBean.getId());
//		if(alName == null || oldLocation.equals(al.getLocation())){
//			articleLockerService.update(this.assembleArticleLocker(articleLockerBean));
//			this.setMsg("修改成功");
//			this.setFlag("true");
//			this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "修改储物箱(保管区id:"+articleLockerBean.getArea().getId()+"储物箱位置:"+articleLockerBean.getLocation()+")");
//		}else{
//			this.setMsg("修改失败，储物箱位置已存在");
//			this.setFlag("false");
//			this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "修改储物箱(保管区id:"+articleLockerBean.getArea().getId()+"储物箱位置:"+articleLockerBean.getLocation()+"),修改失败-储物箱位置已存在");
//		}
//		return SUCCESS;
	}
	
	/**
	 * 查看储物箱
	 * @return	success
	 */
	public String lookLocker(){
		if(StringUtils.isNotEmpty(lockerId)){
			ArticleLocker articleLocker = articleLockerService.findById(lockerId);
			if(articleLocker!=null){
				articleLockerBean = this.assembleArticleLockerBean(articleLocker);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 删除储物箱
	 * @return	success
	 */
	public String deleteLocker(){
		String delSuccess="";
		String delFalse="";
		for(String id : ids){
			ArticleLocker locker = articleLockerService.findById(id);
			if(locker!=null){
				try{
					articleLockerService.delete(locker);
					delSuccess+=locker.getCode()+",";					
					this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "删除储物箱(储物箱id:"+id+")");
				}catch(DataIntegrityViolationException e){
					delFalse+=locker.getCode()+",";
					this.creatCaseManagementLog("涉案物品管理 /基本档案/ 储物箱维护", "删除储物箱(储物箱id:"+id+"),删除失败-储物箱正在使用不可删除！");
					LOGGER.debug("产生异常：", e);;
				}
			}
		}
		if(!"".equals(delSuccess)){
			delSuccess=delSuccess.substring(0, delSuccess.length()-1)+"删除成功!";
		}
		if(!"".equals(delFalse)){
			delSuccess+=delFalse.substring(0, delFalse.length()-1)+"储物箱正在使用不可删除！";
		}
		this.setMsg(delSuccess);
		return SUCCESS;
	}
	
	/**
	 * 生成储物箱code
	 * @return	success
	 */
	public String generateCode(){
		List<ArticleLocker> articleLocker = articleLockerService.findLockersByStorageAreaId(storageAreaId);
		if(articleLocker.isEmpty()){
			code = "0001";
		}else{
			code = articleLocker.get(articleLocker.size()-1).getCode();
			int begin = code.length()-4;
			int number = Integer.parseInt(code.substring(begin))+1;
			code = String.valueOf(number);
			int index = 4-code.length();
			if(code.length()<4){
				for(int i=0;i<index;i++){
					code = "0"+code;
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 私有方法  储物箱）model 转 Bean
	 * @param articleLocker	储物箱）model
	 * @return	articleLockerBeanList	储物箱BeanList
	 */
	private ArticleLockerBean assembleArticleLockerBean(ArticleLocker articleLocker){
			ArticleLockerBean lockerBean = new ArticleLockerBean();
			lockerBean.setCode(articleLocker.getCode());
			lockerBean.setId(articleLocker.getId());
			lockerBean.setLocation(articleLocker.getLocation());
			lockerBean.setRemark(articleLocker.getRemark());
			StorageArea storageArea = storageAreaService.findById(articleLocker.getArea().getId());
			StorageAreaBean storageAreaBean = new StorageAreaBean();
			storageAreaBean.setId(storageArea.getId());
			storageAreaBean.setCode(storageArea.getCode());
			storageAreaBean.setName(storageArea.getName());
			lockerBean.setArea(storageAreaBean);
			lockerBean.setStatus(articleLocker.getStatus());
		return lockerBean;
	}
	
	/**
	 * 私有方法  储物箱Bean转model
	 * @param aLBean	需要转换的  储物箱Bean
	 * @return articleLocker	转换过的model
	 */
	private ArticleLocker assembleArticleLocker(ArticleLockerBean aLBean){
		ArticleLocker articleLocker = new ArticleLocker();
		articleLocker.setCode(aLBean.getCode());
		articleLocker.setLocation(aLBean.getLocation());
		articleLocker.setRemark(aLBean.getRemark());
		articleLocker.setStatus(aLBean.getStatus());//状态
		articleLocker.setId(aLBean.getId() == null?null:aLBean.getId());
		StorageArea storageArea = storageAreaService.findById(aLBean.getArea().getId());
		if(storageArea!=null){
			articleLocker.setArea(storageArea);
		}
		return articleLocker;
	}

	
	public String getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public List<ArticleLockerBean> getArticleLockerBeanList() {
		return articleLockerBeanList;
	}

	public void setArticleLockerBeanList(
			List<ArticleLockerBean> articleLockerBeanList) {
		this.articleLockerBeanList = articleLockerBeanList;
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

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(String oldLocation) {
		this.oldLocation = oldLocation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
