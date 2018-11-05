package com.taiji.pubsec.ajqlc.httpinterface.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajqlc.httpinterface.action.bean.InfoDetailBean;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Account;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IAccountService;
import com.taiji.pubsec.businesscomponent.organization.service.IOrganizationService;

/**
 * 供APP使用的登录相关接口
 * @author wangfx
 *
 */
@Controller("loginInterfaceAction")
@Scope("prototype")
public class LoginInterfaceAction extends InterfaceCommonAction{
	private static final Logger logger = LoggerFactory
			.getLogger(LoginInterfaceAction.class);
	
	private static final long serialVersionUID = 1L;
	private static final String VIRTUAL_PERSON_NAME = "虚拟人员";
	//职务字典类型 code
	private static final String POLITICAL_STATUS_DIC_TYPE = "0000000021";
	//性别字典类型 code
	private static final String DIC_SEX_TYPE = "0000000003";
	@Resource
	private IAccountService accountService;
	@Resource
	private IOrganizationService organizationService;
	@Resource
	private IDictionaryItemService dictionaryItemService;// 字典项接口
	
	
	private String accountName; //账号名称
	private String pdaId;	//pdaid
	private InfoDetailBean infoDetailBean; //账号相关信息bean
	
	/**
	 * 根据账号名称 返回相关信息   人的姓名、人的编码、单位名称。
	 * 输入： accountName 账号名， pdaId 设备id
	 * 输出： infoDetailBean 相关信息
	 */
	public String acquireLoginInfoDetail(){
		logger.debug("acquire accountInfo: {}", accountName);
		infoDetailBean = new InfoDetailBean();
		Account a = accountService.findAccountByName(accountName);
		if (a != null) {
			Person person = a.getPerson();
			if (person != null) {
				infoDetailBean.setPersonId(person.getId());
				if (person.getName().equals(VIRTUAL_PERSON_NAME)) {
					infoDetailBean.setPersonName(null);
					infoDetailBean.setPersonCode(null);
					infoDetailBean.setPersonSex(null);
					infoDetailBean.setPersonSexName(null);
				} else {
					infoDetailBean.setPersonId(person.getId());
					infoDetailBean.setPersonName(person.getName());
					infoDetailBean.setPersonCode(person.getCode());
					infoDetailBean.setPersonSex(person.getSex());
					if(StringUtils.isBlank(person.getSex())){
						infoDetailBean.setPersonSexName("");
					}else{
						DictionaryItem item = dictionaryItemService.findDictionaryItemByDicTypeAndItemCode(DIC_SEX_TYPE, person.getSex(), null);
						if(item != null){
							infoDetailBean.setPersonSexName(item.getName());
						}
					}
					infoDetailBean.setOfficePhone(person.getOfficePhone());
					infoDetailBean.setPosition(person.getPosition());
					//查询职务名称
					if(StringUtils.isBlank(person.getPosition())){
						infoDetailBean.setPositionName("");
					}else{
						DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeAndItemCode(POLITICAL_STATUS_DIC_TYPE, person.getPosition(), null);
						if(di != null){
							infoDetailBean.setPositionName(di.getName());
						}
					}
					infoDetailBean.setPhoneNum(person.getTelephone());
				}
				Organization o = person.getOrganization();
				if (o != null) {
					infoDetailBean.setOrganizationName(o.getShortName());
					infoDetailBean.setOrganizationCode(o.getCode());
				}
			}
		}
		
		setResult(SUCCESS);
		logger.debug("accountInfo: {}", infoDetailBean);
		return SUCCESS;
	}

	public InfoDetailBean getInfoDetailBean() {
		return infoDetailBean;
	}

	public void setInfoDetailBean(InfoDetailBean infoDetailBean) {
		this.infoDetailBean = infoDetailBean;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPdaId() {
		return pdaId;
	}

	public void setPdaId(String pdaId) {
		this.pdaId = pdaId;
	}
	
	
}
