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
import com.taiji.pubsec.businesscomponent.authority.model.Role;
import com.taiji.pubsec.businesscomponent.authority.service.IRoleService;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryType;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryTypeService;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IOrganizationService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;
import com.taiji.pubsec.common.tool.sql.SQLTool;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 预警规则 action
 * @author sunjd
 *
 */
@Controller("alertRulesAction")
@Scope("prototype")
public class AlertRulesAction extends ReturnMessageAction  {

	private static final long serialVersionUID = 1L;
	private static final String INVENTEND_PERSON = "虚拟人员";	
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AlertRulesAction.class);
	
    @Resource
	private IAlertRuleService alertRuleService;// 预警规则接口
    
    @Resource
	private IDictionaryItemService dictionaryItemService; //数据字典接口	
    
    @Resource
    private IPersonService personService; //人员接口
    
    @Resource
	private IRoleService roleServiceImpl;
    
    @Resource
	private IOrganizationService organizationServiceImpl;
    
    @Resource
	private IDictionaryTypeService dictionaryTypeServiceImpl;
    
    @Resource
	private IDictionaryTypeService dictionaryTypeService;
    
    private List<AlertRuleBean> alertRuleBeanList;  //预警规则BeanList
    
    private AlertRuleBean alertRuleBean;	//预警规则bean
    
    private PersonAdminPageBean personAdminPageBean;// 人员管理页面初始化对象
    
    private String theirModulu;
    
    private String id;
    
    private String status;
    
    private String orgId;// 组织机构id
	private String orgCode;// 组织机构code
	private Pager<PersonBean> personPager;// 后台返回的人员列表对象
	private PersonBean personBean;
	private List<PersonBean> personAdminBeanList;// 人员对象list
	private int totalNum ;
	private List<RoleBean> roleBeanList;
	private RoleBean roleBean;
	
    /**
     * 查询预警规则
     * @return SUCCESS
     */
    public String findRulesByTheirModulu(){
    	List<AlertRule> alertRule = alertRuleService.findRuleByTheirModulu(theirModulu);
    	alertRuleBeanList = new ArrayList<AlertRuleBean>();
    	for(AlertRule ale : alertRule){
    		alertRuleBeanList.add(alertRuleToBean(ale));
    	}
    	return SUCCESS;
    }
    
    /**
     * 私有方法 预警规则model转Bean
     * @param alertRule	预警规则model
     * @return	预警规则Bean
     */
    private AlertRuleBean alertRuleToBean(AlertRule alertRule){
    	AlertRuleBean bean = new AlertRuleBean();
    	bean.setName(alertRule.getName());// 预警规则名称
    	bean.setId(alertRule.getId());// 预警id
    	bean.setPopWindowDuaring(alertRule.getPopWindowDuaring());// 弹窗保持时间
    	if(Constant.YJGZ_BCZC.equals(alertRule.getId())){
    		bean.setTzyjData(changeTZ(alertRule.getAlertTimeAt()));
    	}else{
    		bean.setAlertTimeAt(alertRule.getAlertTimeAt());// 定点预警时间
    	}
    	bean.setAlertTimeBefore(alertRule.getAlertTimeBefore());// 提前预警时间
    	bean.setDescription(alertRule.getDescription());// 预警条件描述
    	DictionaryItem  dic = dictionaryItemService.findById(alertRule.getStatus());
    	bean.setStatusName(dic.getName());// 状态名称
    	bean.setUpdateTimeStr(DateTimeUtil.formatDateTimeWithStandardFormat(alertRule.getUpdateTime()));//更新时间
    	bean.setWay(alertRule.getWay());// 预警方式，字典项id
    	bean.setTrigger(alertRule.getTrigger());// 触发方式，字典项id
    	bean.setCode(alertRule.getCode());// 预警规则编码
    	bean.setAlertUsers(strToList(alertRule.getAlertUsers(),1));//要预警的用户id，多个用逗号隔开
    	bean.setAlertRoles(strToList(alertRule.getAlertRoles(),2));//要预警的角色id，多个用逗号隔开
    	return bean;
    }
    
   /**
    * 
    * @param alertTimeAt
    * @return
    */
    private List<List<String>> changeTZ(String str) {
    	
    	if(str==null||"".equals(str)){
    		return null;
    	}else{
    		String [] arrStrs= str.split(";"); //总集合
    		String [] arrStr1=arrStrs[0].split(","); //第一个集合
    		List<String> arrToList =new ArrayList<String>(); //第二个集合
			if(arrStrs.length>1&&arrStrs[1]!=""){  //判断存在第二个集合,并将arr 转成list 
			    String [] arrStr2=arrStrs[1].split(",");
			    for(int i=0;i<arrStr2.length;i++){
			    	arrToList.add(arrStr2[i]);
			    }
			    
		    }
			List<List<String>> listTal=new ArrayList<List<String>>();
			List<String> list1=new ArrayList<String>();
			if(!";".equals(str.substring(0,1))){ //第一组有数据时
				for(int i=0;i<arrStr1.length;i++){
					list1.add(arrStr1[i]);
				}
			}
			listTal.add(list1);
			List<String> list2=new ArrayList<String>();
			for(int i=0;i<arrToList.size();i++){
				list2.add(arrToList.get(i));
			}
			listTal.add(list2);
			return listTal;
    	}
    
	}

	/**
     * 启用或停用预警规则
     * @return	success
     */
    public String updateStatus() {
    	try {
    		alertRuleService.updateAlertRuleStatus(id,status);
    		if("0000000002002".equals(status)){
    			this.setMsg("启用成功!");
    		}else if("0000000002001".equals(status)){
    			this.setMsg("停用成功!");
    		}else{
    			this.setMsg("操作成功!");
    		}
		} catch (Exception e) {
			LOGGER.debug("停用启用操作产生异常：", e);
		}
		return SUCCESS;
	}
    
    /**
     * 新建或更新预警规则
     * @return
     */
    public String saveRule(){
    	AlertRule code = alertRuleService.findAlertRuleByCode(alertRuleBean.getCode());
    	AlertRule name = alertRuleService.findRuleByName(alertRuleBean.getName());
    	if(StringUtils.isEmpty(alertRuleBean.getId())){
    		if(code!= null || name != null){
    			this.setMsg("预警名称或编码重复");
    		}else{
    			alertRuleService.saveOrUpdateAlertRule(alertRuleBeanToAlertRule(alertRuleBean));
    		}
    		return SUCCESS;
    	}else{
    		if((code!=null && !code.getId().equals(alertRuleBean.getId())) || ((name!=null) && !name.getId().equals(alertRuleBean.getId()))){
    			this.setMsg("预警名称或编码重复");
    		}else{
    			AlertRule alertRule = alertRuleService.findByid(alertRuleBean.getId());
    			alertRule.setUpdateTime(new Date());
    			alertRule.setAlertTimeAt(alertRuleBean.getAlertTimeAt());
    			alertRule.setAlertTimeBefore(alertRuleBean.getAlertTimeBefore());
    			alertRule.setCode(alertRuleBean.getCode());
    			alertRule.setName(alertRuleBean.getName());
    			alertRule.setWay(alertRuleBean.getWay());
    			alertRule.setPopWindowDuaring(alertRuleBean.getPopWindowDuaring());
    			alertRule.setTrigger(alertRuleBean.getTrigger());
    			alertRule.setAlertRoles(alertRuleBean.getAlertRolesStr());
    			alertRule.setAlertUsers(alertRuleBean.getAlertUsersStr());
    			alertRuleService.saveOrUpdateAlertRule(alertRule);
    		}
    		return SUCCESS;
    	}
    }
    /**
	 * 初始化人员管理
	 * @return "success" personAdminPageBean 人员管理bean
	 */
	public String initPersonManagePage() {
		List<BasePersonData> sexList = initPersonStatusList(Constant.XB);
		List<BasePersonData> statusList = initPersonStatusList(Constant.WORK_STATUS);
		personAdminPageBean = new PersonAdminPageBean();
		personAdminPageBean.setSexList(sexList);
		personAdminPageBean.setStatusList(statusList);
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
		Map<String, Object> userMap = user.getUserMap();
		String currentId = "";
		if (userMap.get("org") != null) {
			Map<String, String> mOrg = (Map<String, String>) userMap.get("org");
			orgCode = mOrg.get("code");
			currentId = mOrg.get("id");
			personAdminPageBean.setUnitCode(orgCode);
			personAdminPageBean.setUnitId(currentId);
		}
		return SUCCESS;
	}
	/**
	 * 初始化人员的各个下拉列表
	 * 
	 * @param type
	 *            要初始化的类型
	 * @return List<BasePersonData> 人员基本信息List对象
	 */
	private List<BasePersonData> initPersonStatusList(String type) {
		List<DictionaryItem> dictionaryItemTempList = dictionaryItemService
				.findDicItemsByTypeCode(type, null);
		List<BasePersonData> newBaseDateList = new ArrayList<BasePersonData>();
		for (DictionaryItem di : dictionaryItemTempList) {
			BasePersonData newBasePersonData = new BasePersonData();
			newBasePersonData.setCode(di.getCode());
			newBasePersonData.setName(di.getName());
			newBaseDateList.add(newBasePersonData);
		}
		return newBaseDateList;
	}
	/**
	 * 查找人员
	 * 
	 * @param personBean
	 *            人员对象Bean
	 * @param start
	 *            开始项
	 * @param length
	 *            页面展示记录的数
	 * @return "success" List<PersonBean> personAdminBeanList 人员的list对象
	 */
	public String searchPersonList() {
		personPager = this.findAllPersonByConditions(personBean, this.getStart(),
				this.getLength());
		totalNum=personPager.getTotalNum();
		personAdminBeanList = personPager.getPageList();
		return SUCCESS;
	}
	
	/**
	 * 根据不同条件查询本人员
	 * 
	 * @param personBean
	 *            人员Bean
	 * @param pageStart
	 *            页面开始项
	 * @param pageSize
	 *            页面展示条数
	 * @return Pager<PersonBean> 人员的分页对象
	 */
	private Pager<PersonBean> findAllPersonByConditions(PersonBean personBean,
			int pageStart, int pageSize) {
		if (StringUtils.isEmpty(personBean.getUnitCode())) {
			MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
			Map<String, Object> userMap = user.getUserMap();
			if (userMap.get("org") != null) {
				Map<String, String> mOrg = (Map<String, String>) userMap.get("org");
				orgCode = mOrg.get("code");
				personBean.setUnitCode(orgCode);
			}
		}
		String hql = "from Person  where 1 = 1 and name != ?";
		List<Object> param = new ArrayList<Object>();
		param.add(INVENTEND_PERSON);
		if (!StringUtils.isEmpty(personBean.getUnitCode())) {
			Organization org = organizationServiceImpl
					.findOrganizationByCode(personBean.getUnitCode());
			hql = hql
					+ " and (organization.id = ? or organization.superOrg.id=?)";
			param.add(org.getId());
			param.add(org.getId());
		}
		if (!StringUtils.isEmpty(personBean.getName())) {
			hql += " and name like ?";
			hql = SQLTool.SQLAddEscape(hql);
			param.add("%" + SQLTool.SQLSpecialChTranfer(personBean.getName())
					+ "%");
		}
		if (!StringUtils.isEmpty(personBean.getSex())) {
			hql += " and sex = ?";
			param.add(personBean.getSex());
		}
		if (!StringUtils.isEmpty(personBean.getStatus())) {
			hql += " and status = ?";
			param.add(personBean.getStatus());
		}
		hql += " order by code";
		Pager<PersonBean> result = new Pager<PersonBean>();
		int pageNo = pageStart / pageSize;
		Pager<Person> personPagerTemp = personService.findByPage(hql,
				param.toArray(), pageNo, pageSize);
		result.setTotalNum(personPagerTemp.getTotalNum());
		List<PersonBean> personBeans = new ArrayList<PersonBean>();
		for (int i = 0; i < personPagerTemp.getPageList().size(); i++) {
			Person personTemp = personPagerTemp.getPageList().get(i);
			PersonBean personBeanTemp = initPersonBean(personTemp);
			personBeans.add(personBeanTemp);
		}
		result.setPageList(personBeans);
		personPager = result;
		return result;
	}
	
	/**
	 * 查找角色
	 *
	 * @param roleBean
	 * @return "success" 成功时返回roleBeanList 角色Bean的List对象 totalNum 总的记录条数
	 */
	public String queryRole() {
		Pager<RoleBean> roleBeanPager = new Pager<RoleBean>();
		Pager<Role> rolePager = roleServiceImpl.findRolesByNameAndStatus(roleBean.getRoleName(), roleBean.getStatus(),  this.getStart() / this.getLength(), this.getLength());
		roleBeanPager.setTotalNum(rolePager.getTotalNum());
		this.setTotalNum(rolePager.getTotalNum());
		roleBeanList = new ArrayList<RoleBean>();
		DictionaryType statuDictionaryType = dictionaryTypeService.findDicTypeByCode(Constant.ZT);
		for (Role roleTemp : rolePager.getPageList()) {
			RoleBean roleBeanTemp = ModelToBean.roleToRoleBean(roleTemp);
			DictionaryItem dictionaryItem = dictionaryItemService.findDictionaryItemByDicTypeAndItemCode(statuDictionaryType.getId(), roleBeanTemp.getStatus(), null);
			if(dictionaryItem != null){
				roleBeanTemp.setStatus(dictionaryItem.getName());
			}
			roleBeanList.add(roleBeanTemp);
		}
		return SUCCESS;
	}
	
	/**
	 * 初始化人员Bean 将对应的字典项编码转化为字典项名称
	 * @param personTemp
	 * @return PersonBean
	 */
	private PersonBean initPersonBean(Person personTemp) {
		PersonBean personBeanTemp = ModelToBean.personToPersonBean(personTemp);
		DictionaryType tmpDictionary = dictionaryTypeServiceImpl.findDicTypeByCode(Constant.WORK_STATUS);
		DictionaryItem dictStatus = dictionaryItemService.findDictionaryItemByDicTypeAndItemCode(tmpDictionary.getId(), personBeanTemp.getStatus(),null);
		               
		tmpDictionary = dictionaryTypeServiceImpl.findDicTypeByCode(Constant.XB);
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
     * 将由逗号和分号分隔的id和字符串解析成list集合
     * @param str
     * @param k  k 为1时是人员转换  2 是为角色转换
     * @return
     */
    private List<List<NameAndIdBean>> strToList(String str,int k){
    	
    	if(str==null||"".equals(str)){
    		return null;
    	}else{
    		String [] arrStrs= str.split(";"); //总集合
    		String [] arrStr1=arrStrs[0].split(","); //第一个集合
    		List<String> arrToList =new ArrayList<String>(); //第二个集合
			if(arrStrs.length>1&&arrStrs[1]!=""){
			    String [] arrStr2=arrStrs[1].split(",");
			    for(int i=0;i<arrStr2.length;i++){
			    	arrToList.add(arrStr2[i]);
			    }
			    
		    }
			List<List<NameAndIdBean>> listTal=new ArrayList<List<NameAndIdBean>>();
			List<NameAndIdBean> list1=new ArrayList<NameAndIdBean>();
			if(!";".equals(str.substring(0,1))){ //第一组有数据时
				for(int i=0;i<arrStr1.length;i++){
					NameAndIdBean bean=new NameAndIdBean();
					bean.setId(arrStr1[i]); //设置名称
					if(k==1){
						bean.setName(personService.findById(arrStr1[i]).getName());//设置人员名称
					}else if(k==2){
						bean.setName(roleServiceImpl.findById(arrStr1[i]).getRoleName());//角色名称
					}
					list1.add(bean);
				}
			}
			listTal.add(list1);
			List<NameAndIdBean> list2=new ArrayList<NameAndIdBean>();
			for(int i=0;i<arrToList.size();i++){
				NameAndIdBean bean=new NameAndIdBean();
		        bean.setId(arrToList.get(i)); //设置id
		        if(k==1){
		        	bean.setName(personService.findById(arrToList.get(i)).getName());//设置人员名称
		        }else if(k==2){
		        	bean.setName(roleServiceImpl.findById(arrToList.get(i)).getRoleName());//角色名称
		        }
				list2.add(bean);
			}
			listTal.add(list2);
			return listTal;
    	}
    }

    /**
     * 预警规则Bean转Model
     * @param bean预警规则Bean
     * @return alertRule 预警规则Model
     */
    private AlertRule alertRuleBeanToAlertRule(AlertRuleBean bean){
    	AlertRule alertRule = new AlertRule();
    	alertRule.setCode(bean.getCode());
    	alertRule.setName(bean.getName());
    	alertRule.setWay(bean.getWay());
    	alertRule.setAlertTimeAt(bean.getAlertTimeAt());
    	alertRule.setAlertTimeBefore(bean.getAlertTimeBefore());
    	alertRule.setDescription(bean.getDescription());
    	alertRule.setStatus(bean.getStatus());
    	alertRule.setTrigger(bean.getTrigger());
    	alertRule.setPopWindowDuaring(bean.getPopWindowDuaring());
    	alertRule.setTheirModulu(bean.getTheirModulu());
    	return alertRule;
    }

	public List<AlertRuleBean> getAlertRuleBeanList() {
		return alertRuleBeanList;
	}

	public void setAlertRuleBeanList(List<AlertRuleBean> alertRuleBeanList) {
		this.alertRuleBeanList = alertRuleBeanList;
	}

	public String getTheirModulu() {
		return theirModulu;
	}

	public void setTheirModulu(String theirModulu) {
		this.theirModulu = theirModulu;
	}

	public AlertRuleBean getAlertRuleBean() {
		return alertRuleBean;
	}

	public void setAlertRuleBean(AlertRuleBean alertRuleBean) {
		this.alertRuleBean = alertRuleBean;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PersonAdminPageBean getPersonAdminPageBean() {
		return personAdminPageBean;
	}

	public void setPersonAdminPageBean(PersonAdminPageBean personAdminPageBean) {
		this.personAdminPageBean = personAdminPageBean;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Pager<PersonBean> getPersonPager() {
		return personPager;
	}

	public void setPersonPager(Pager<PersonBean> personPager) {
		this.personPager = personPager;
	}

	public PersonBean getPersonBean() {
		return personBean;
	}

	public void setPersonBean(PersonBean personBean) {
		this.personBean = personBean;
	}

	public List<PersonBean> getPersonAdminBeanList() {
		return personAdminBeanList;
	}

	public void setPersonAdminBeanList(List<PersonBean> personAdminBeanList) {
		this.personAdminBeanList = personAdminBeanList;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public List<RoleBean> getRoleBeanList() {
		return roleBeanList;
	}

	public void setRoleBeanList(List<RoleBean> roleBeanList) {
		this.roleBeanList = roleBeanList;
	}

	public RoleBean getRoleBean() {
		return roleBean;
	}

	public void setRoleBean(RoleBean roleBean) {
		this.roleBean = roleBean;
	}


}
