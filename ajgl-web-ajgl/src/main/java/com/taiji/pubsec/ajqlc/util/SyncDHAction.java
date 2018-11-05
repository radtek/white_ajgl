package com.taiji.pubsec.ajqlc.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.alert.model.AlertRule;
import com.taiji.pubsec.ajqlc.alert.service.IAlertRuleService;
import com.taiji.pubsec.ajqlc.util.bean.AlertRuleBean;
import com.taiji.pubsec.ajqlc.util.bean.BasePersonData;
import com.taiji.pubsec.ajqlc.util.bean.ModelToBean;
import com.taiji.pubsec.ajqlc.util.bean.NameAndIdBean;
import com.taiji.pubsec.ajqlc.util.bean.PersonAdminPageBean;
import com.taiji.pubsec.ajqlc.util.bean.PersonBean;
import com.taiji.pubsec.ajqlc.util.bean.RoleBean;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBasicDataService;
import com.taiji.pubsec.businesscomponent.authority.model.Role;
import com.taiji.pubsec.businesscomponent.authority.service.IRoleService;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryType;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryTypeService;
import com.taiji.pubsec.businesscomponent.organization.model.Account;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IAccountService;
import com.taiji.pubsec.businesscomponent.organization.service.IOrganizationService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;
import com.taiji.pubsec.common.tool.sql.SQLTool;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 同步大华action
 * @author 
 *
 */
@Controller("syncDHAction")
@Scope("prototype")
public class SyncDHAction extends ReturnMessageAction  {
	
	@Resource 
	private  IUnitService  unitService;
	@Resource
	private  IAccountService accountService;
	@Resource
	private  IBasicDataService  basicDataServiceImpl;
	
	public String syncDHORGData(){
		List<Unit> unitLists = unitService.findAll();
		for(Unit unitTemp :unitLists){
			if(unitTemp.getId().equals("01")){
				continue;
			}
			ResultBean  res= basicDataServiceImpl.syncAddOrgnizationInfo(unitTemp.getId(), unitTemp.getShortName(), "01");
		    System.out.println(res.isResult());
		}
		return SUCCESS;
	}
	
	public String syncDHPersonData(){
		List<Account>  accounts = accountService.findAll();
		for(Account accoutTemp: accounts ){
		   if(accoutTemp.getPerson().getOrganization().getId().equals("01")){
			ResultBean res = basicDataServiceImpl.syncAddPersonInfo(accoutTemp.getAccountName(), 
					accoutTemp.getPassword(), accoutTemp.getPerson().getOrganization().getId(), 
					accoutTemp.getPerson().getName(), accoutTemp.getPerson().getCode());
			System.out.println(res.isResult());
		}
		
		}
		return SUCCESS;
	}
	
}
