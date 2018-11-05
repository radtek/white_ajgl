package com.taiji.pubsec.ajgl.personinfo.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryType;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryTypeService;
import com.taiji.pubsec.businesscomponent.organization.model.Account;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IAccountService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.ajgl.util.Constant;
import com.taiji.pubsec.ajgl.util.bean.PersonBean;
import com.taiji.pubsec.common.web.action.BaseAction;
@Controller("personInfoAction")
@Scope("prototype")
public class PersonInfoAction extends BaseAction {
	@Resource
	private IPersonService personService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private IDictionaryTypeService dictionaryTypeServiceImpl;
	@Resource
	private IAccountService accountService;
	private String userLoginName;
	private String newPassWord;
	private String originPassWord;
	private String message;
	private String flag;
	private PersonBean personAdminBean;
	public String findCurrentPersonInfo(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
		Map<String, Object> userMap = user.getUserMap();
		if (userMap.get("person") != null){
			Map<String, String> mPerson = new HashMap<String, String>(0) ;
			mPerson = (Map<String, String>) userMap.get("person");
			Person person = personService.findPersonByCode(mPerson.get("code"));
			personAdminBean = initPersonBean(person);
			personAdminBean.setUserName((userMap.get("userName")).toString());
		} 
		return SUCCESS;
	}
	public String changePasswordAction(){
		String sql = "from Account where  accountName = ? and password = ?";
		Account accountTemp = accountService.findByParams(sql, new Object[]{userLoginName,originPassWord});
		if(accountTemp == null) {
			flag = "false";
			message = "原始密码错误";
		}else{
			accountTemp.setPassword(newPassWord);
			accountService.updateAccount(accountTemp, accountTemp.getPerson()
					.getId());
			flag = "true";
			message = "密码修改成功";
		}
		return SUCCESS;
	}
	/**
	 * 初始化人员Bean 将对应的字典项编码转化为字典项名称
	 * @param personTemp
	 * @return PersonBean
	 */
	private PersonBean initPersonBean(Person personTemp) {
		PersonBean personBeanTemp = personToPersonBean(personTemp);
		DictionaryType tmpDictionary = dictionaryTypeServiceImpl.findDicTypeByCode(Constant.WORK_STATUS);
		DictionaryItem dictStatus = dictionaryItemService.findDictionaryItemByDicTypeAndItemCode(tmpDictionary.getId(), personBeanTemp.getStatus(),null);
		               
		tmpDictionary = dictionaryTypeServiceImpl.findDicTypeByCode(Constant.SEX);
		DictionaryItem dictSex = dictionaryItemService.findDictionaryItemByDicTypeAndItemCode(tmpDictionary.getId(),personBeanTemp.getSex(),null);
		
		tmpDictionary = dictionaryTypeServiceImpl.findDicTypeByCode(Constant.DIPLOMA);
		DictionaryItem dictDiploma = dictionaryItemService
				.findDictionaryItemByDicTypeAndItemCode(tmpDictionary.getId(),personBeanTemp.getDiploma(),null);
		
		tmpDictionary = dictionaryTypeServiceImpl.findDicTypeByCode(Constant.NATIONALITY);
		DictionaryItem dictNationality = dictionaryItemService
				.findDictionaryItemByDicTypeAndItemCode(tmpDictionary.getId(),personBeanTemp.getNationality(),null);
		
		tmpDictionary = dictionaryTypeServiceImpl.findDicTypeByCode(Constant.POLITICSTYPE);
		DictionaryItem dictPoliticsType = dictionaryItemService
				.findDictionaryItemByDicTypeAndItemCode(tmpDictionary.getId(),personBeanTemp.getPoliticsType(),null);
		
		tmpDictionary = dictionaryTypeServiceImpl.findDicTypeByCode(Constant.JOB);
		DictionaryItem diJob = dictionaryItemService
				.findDictionaryItemByDicTypeAndItemCode(tmpDictionary.getId(),personBeanTemp.getJob(),null);
		if (dictDiploma != null) {
			personBeanTemp.setDiploma(dictDiploma.getName());
		}
		if (dictNationality != null) {
			personBeanTemp.setNationality(dictNationality.getName());
		}
		if (dictPoliticsType != null) {
			personBeanTemp.setPoliticsType(dictPoliticsType.getName());
		}
		if (dictStatus != null) {
			personBeanTemp.setStatus(dictStatus.getName());
		}
		if (dictSex != null) {
			personBeanTemp.setSex(dictSex.getName());
		}
		if (diJob != null) {
			personBeanTemp.setJob(diJob.getName());
		}
		return personBeanTemp;
	}
	/**
	 * 人员Model到人员Bean
	 * 
	 * @param person
	 * @return PersonBean
	 */
	private static PersonBean personToPersonBean(Person person) {
		PersonBean personBean = new PersonBean();
		personBean.setDiploma(person.getDegree());
		personBean.setJob(person.getPosition());
		personBean.setMovePhone(person.getTelephone());
		personBean.setName(person.getName());
		personBean.setNationality(person.getNationality());
		personBean.setOfficePhone(person.getOfficePhone());
		personBean.setPersonCode(person.getCode());
		personBean.setPoliceNo(person.getCode());
		personBean.setPersonId(person.getId());
		personBean.setPoliticsType(person.getPoliticalStatus());
		personBean.setSex(person.getSex());
		personBean.setStatus(person.getStatus());
		personBean.setStatusCardNo(person.getIdNumber());
		personBean.setUnitCode(person.getOrganization().getCode());
		personBean.setUnitFullName(person.getOrganization().getName());
		personBean.setUnitId(person.getOrganization().getId());
		return personBean;
	}
	public PersonBean getPersonAdminBean() {
		return personAdminBean;
	}
	public void setPersonAdminBean(PersonBean personAdminBean) {
		this.personAdminBean = personAdminBean;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getNewPassWord() {
		return newPassWord;
	}
	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}
	public String getOriginPassWord() {
		return originPassWord;
	}
	public void setOriginPassWord(String originPassWord) {
		this.originPassWord = originPassWord;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
