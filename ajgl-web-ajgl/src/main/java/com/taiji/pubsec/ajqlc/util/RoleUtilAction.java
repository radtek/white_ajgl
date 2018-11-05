package com.taiji.pubsec.ajqlc.util;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.util.bean.ZTreeBean;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Department;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IDepartmentService;
import com.taiji.pubsec.businesscomponent.organization.service.IOrganizationService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.businesscomponent.organization.service.UnitServiceImpl;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;


/**
 * 弹框人员和角色
 * 
 * @author 
 *
 */
@Controller("RoleUtilAction")
@Scope("prototype")
public class RoleUtilAction  extends ReturnMessageAction{
	private static final long serialVersionUID = 1L;
	private static final String UNIT_LOGO = "../images/icon/ztree-icon_dw.png";
	private static final String DEP_LOGO = "../images/icon/ztree-icon_bm.png";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UnitServiceImpl.class);
	@Resource(name = "departmentService")
	private IDepartmentService departmentService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource(name = "unitService")
	private IUnitService unitService;
	@Resource
	private IOrganizationService organizationServiceImpl;
	@Resource
	private IPersonService personService;
	private List<Unit> unitList;
	private List<ZTreeBean> departmentTree;
	private List<Department> departmentList;
//	private DepartmentBean departmentBean;
	private String unitId;

	/**
	 * 初始化部门树
	 * 
	 * @param unitId 单位Id
	 *            
	 * @return "success" List<ZTreeBean> 部门树
	 */
	public String initDepartmentTree() {
		departmentTree = new ArrayList<ZTreeBean>();
		String currentUnittId = "";
		if (unitId == null) {
			MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
			Map<String, Object> userMap = user.getUserMap();
			Map<String, String> mOrg = (Map<String, String>) userMap.get("org");
			if (mOrg != null) {
				ZTreeBean ztbTemp = new ZTreeBean();
				currentUnittId = mOrg.get("id");
				ztbTemp.setId(currentUnittId);
				ztbTemp.setParentId(null);
				ztbTemp.setName(mOrg.get("name"));
				ztbTemp.setCode(mOrg.get("code"));
				ztbTemp.setOpen("true");
				ztbTemp.setIsParent(Constant.UNIT_ISPAR_TRUE);
				ztbTemp.setIcon(UNIT_LOGO);
				departmentTree.add(ztbTemp);
			}
		} else {
			currentUnittId = unitId;
		}
		initSubUnitTree(departmentTree, currentUnittId);
		initSubDeparTree(departmentTree, currentUnittId);
		return SUCCESS;
	}

	/**
	 * 此方法包含两种情况 1 查找当前单位的第一个部门, 2 根据Id查找部门
	 * @param departmentBean 部门bean
	 * @return "success" 成功时返回 departmentBean 部门Bean
	 */
//	public String findDeparmentById() {
//		if (departmentBean.getId().equals(Constant.ORGANIZATION_INIT)) {
//			MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
//			Map<String, Object> userMap = user.getUserMap();
//			Map<String, String> mOrg = (Map<String, String>) userMap.get("org");
//			if (userMap.get("org") != null) {
//				String hql = "from Department where superOrg.id = ? order by code";
//				List<Department> departmentTempList = departmentService
//						.findAllByParams(hql, new Object[] { mOrg.get("id") });
//				if (!departmentTempList.isEmpty()) {
//					departmentBean = ModelToBean
//							.departmentToDepartmentBean(departmentTempList
//									.get(0));
//					this.setFlag(Boolean.TRUE.toString());
//				} else {
//					this.setFlag(Boolean.FALSE.toString());
//					return SUCCESS;
//				}
//			}
//		} else {
//			Department department = departmentService.findById(departmentBean
//					.getId());
//			if (department == null) {
//				this.setFlag(Boolean.FALSE.toString());
//				return SUCCESS;
//			}
//			departmentBean = ModelToBean.departmentToDepartmentBean(department);
//			this.setFlag(Boolean.TRUE.toString());
//		}
//		return SUCCESS;
//	}




	

	

	/**
	 * 初始化子单位树
	 * 
	 * @param departmentTreeTemp
	 *            部门树
	 * @param orgId
	 *            部门Id
	 * @return
	 */
	private void initSubUnitTree(List<ZTreeBean> departmentTreeTemp,
			String orgId) {
		String hqlunit = " from Unit where superOrg.id = ? order by code";
		unitList = unitService.findAllByParams(hqlunit, new Object[] { orgId });
		for (Unit unit : unitList) {
			ZTreeBean ztb = new ZTreeBean();
			ztb.setId(unit.getId());
			ztb.setParentId(unit.getSuperOrg() == null ? null : unit
					.getSuperOrg().getId());
			ztb.setName(unit.getShortName());
			ztb.setCode(unit.getCode());
			ztb.setIsParent(unit.getSubOrgs().isEmpty() ? Constant.UNIT_ISPAR_FALSE
					: Constant.UNIT_ISPAR_TRUE);
			ztb.setIsUnit("unit");
			ztb.setIcon(UNIT_LOGO);
			departmentTreeTemp.add(ztb);
		}
	}

	/**
	 * 初始化子部门
	 * 
	 * @param departmentTreeTemp
	 *            部门树
	 * @param orgId
	 *            部门Id
	 * @return
	 */
	private void initSubDeparTree(List<ZTreeBean> departmentTreeTemp,
			String orgId) {
		String hql = " from Department where superOrg.id = ? order by code";
		departmentList = departmentService.findAllByParams(hql,
				new Object[] { orgId });
		for (Department department : departmentList) {
			ZTreeBean ztb = new ZTreeBean();
			ztb.setId(department.getId());
			ztb.setParentId(department.getSuperOrg() == null ? null
					: department.getSuperOrg().getId());
			ztb.setName(department.getShortName());
			ztb.setCode(department.getCode());
			ztb.setIsParent(department.getSubOrgs().isEmpty() ? Constant.UNIT_ISPAR_FALSE
					: Constant.UNIT_ISPAR_TRUE);
			ztb.setIsUnit("department");
			ztb.setIcon(DEP_LOGO);
			departmentTreeTemp.add(ztb);
		}
	}

	public List<ZTreeBean> getDepartmentTree() {
		return departmentTree;
	}

	public void setDepartmentTree(List<ZTreeBean> departmentTree) {
		this.departmentTree = departmentTree;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	/*public DepartmentBean getDepartmentBean() {
		return departmentBean;
	}

	public void setDepartmentBean(DepartmentBean departmentBean) {
		this.departmentBean = departmentBean;
	}
*/
	public List<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Unit> unitList) {
		this.unitList = unitList;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

}
