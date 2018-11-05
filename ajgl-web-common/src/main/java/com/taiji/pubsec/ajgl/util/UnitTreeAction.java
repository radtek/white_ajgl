package com.taiji.pubsec.ajgl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.bean.UnitSearchBean;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.web.action.BaseAction;
import com.taiji.pubsec.common.web.bean.ZTreeBean;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

@Controller("unitTreeAction")
@Scope("prototype")
public class UnitTreeAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 单位树节点是否为为父节点，否
	public static final String UNIT_ISPAR_FALSE = "False";
	// 单位树节点是否为为父节点，是
    public static final String UNIT_ISPAR_TRUE = "True";
    //单位
    public static final String ORGANIZATION_UNIT= "com.taiji.pubsec.businesscomponent.organization.model.Unit";
	
	private String id;
	private boolean flag;
	private String treeNodes;
	private List<UnitSearchBean> unitSearchBeans;
	private int totalNum = 0;
	private UnitSearchBean unitSearch;
	
	private String code;
	private List<ZTreeBean> unitTree = new ArrayList<ZTreeBean>();
	private List<Unit> unitList = new ArrayList<Unit>();
	
	@Resource(name = "unitService")
	private IUnitService unitService;

	// 查询来文单位
	public String searchInfoUnit() {
		setTreeNodes("[");
		if (StringUtils.isEmpty(id)) {
			String hql = " from Unit where superOrg is null";
			List<Unit> lst = unitService.findAllByParams(hql, new Object[] {});

			if (lst != null) {
				for (Unit item : lst) {
					setTreeNodes(getTreeNodes() + "{id:\"" + item.getCode()
							+ "\",");
					setTreeNodes(getTreeNodes() + "pId:\"" + "" + "\",");
					setTreeNodes(getTreeNodes() + "name:\""
							+ item.getShortName() + "\",");
					List<Organization> subOrg = item.getSubOrgs();
					if (subOrg != null && subOrg.size() > 0) {
						setTreeNodes(getTreeNodes() + "open:\"true\",");
						setTreeNodes(getTreeNodes() + "isParent:\"true\"},");
						for (Organization subItem : subOrg) {
							setTreeNodes(getTreeNodes() + "{id:\""
									+ subItem.getCode() + "\",");
							setTreeNodes(getTreeNodes() + "pId:\""
									+ subItem.getSuperOrg().getCode() + "\",");
							setTreeNodes(getTreeNodes() + "name:\""
									+ subItem.getShortName() + "\",");
							setTreeNodes(getTreeNodes() + "open:\"false\",");
							List<Organization> orgL = subItem.getSubOrgs();
							if (orgL != null && orgL.size() > 0) {
								setTreeNodes(getTreeNodes()
										+ "isParent:\"true\"},");
							} else {
								setTreeNodes(getTreeNodes()
										+ "isParent:\"false\"},");
							}
						}
					} else {
						setTreeNodes(getTreeNodes() + "open:\"false\",");
						setTreeNodes(getTreeNodes() + "isParent:\"false\"},");
					}
				}
			}
		} else {
			Unit unit = unitService.findUnitByCode(id);
			List<Organization> subOrg = unit.getSubOrgs();
			if (subOrg != null) {
				for (Organization item : subOrg) {
					setTreeNodes(getTreeNodes() + "{id:\"" + item.getCode()
							+ "\",");
					setTreeNodes(getTreeNodes() + "pId:\""
							+ item.getSuperOrg().getCode() + "\",");
					setTreeNodes(getTreeNodes() + "name:\""
							+ item.getShortName() + "\",");
					setTreeNodes(getTreeNodes() + "open:\"false\",");
					List<Organization> orgL = item.getSubOrgs();
					if (orgL != null && orgL.size() > 0) {
						setTreeNodes(getTreeNodes() + "isParent:\"true\"},");
					} else {
						setTreeNodes(getTreeNodes() + "isParent:\"false\"},");
					}
				}
			}
		}
		setTreeNodes(getTreeNodes() + "]");
		flag = true;
		return SUCCESS;
	}

	public String searchUnit() {
		@SuppressWarnings("unchecked")
		Map<String, String> mOrg = (Map<String, String>) SessionUserDetailsUtil
				.getMySecureUser().getUserMap().get("org");
		String unitCode = mOrg.get("code");
		unitSearchBeans = new ArrayList<UnitSearchBean>();
		if ("1".equals(unitSearch.getCode())) {// 获取当前单位的平级单位
			Unit bean = unitService.findUnitByCode(unitCode);
			if (bean != null) {
				Organization superOrg = bean.getSuperOrg();
				if (superOrg != null) {
					List<Organization> lst = superOrg.getSubOrgs();
					if (lst != null) {// 如果有上级单位，则查上级单位的下级单位过滤自己
						for (Organization org : lst) {
							if (org.getCode().equals(unitCode)) {
								continue;
							}
							UnitSearchBean temp = new UnitSearchBean();
							temp.setName(org.getShortName());
							temp.setFullName(org.getName());
							temp.setCode(org.getCode());
							unitSearchBeans.add(temp);
						}
					}
				} else {// 如果没有上级单位，则查顶级单位过滤自己
					String hql = " from Unit where superOrg is null";
					List<Unit> lst = unitService.findAllByParams(hql,
							new Object[] {});
					if (lst != null) {
						for (Unit org : lst) {
							if (org.getCode().equals(unitCode)) {
								continue;
							}
							UnitSearchBean temp = new UnitSearchBean();
							temp.setName(org.getShortName());
							temp.setFullName(org.getName());
							temp.setCode(org.getCode());
							unitSearchBeans.add(temp);
						}
					}
				}
			}
		} else {// 下级单位
			Unit bean = unitService.findUnitByCode(unitCode);
			if (bean != null) {
				List<Organization> lst = bean.getSubOrgs();
				if (lst != null) {
					for (Organization org : lst) {
						if (org instanceof Unit) {
							Unit u = (Unit) org;
							if ("SJ0012002".equals(u.getType())) {
								UnitSearchBean temp = new UnitSearchBean();
								temp.setName(u.getShortName());
								temp.setFullName(u.getName());
								temp.setCode(u.getCode());
								unitSearchBeans.add(temp);
							}
						}
					}
				}
			}
		}
		totalNum = unitSearchBeans == null ? 0 : unitSearchBeans.size();
		flag = true;
		return SUCCESS;
	}
	
	/**
	 * 初始化单位树
	 * @return
	 */
	public String initUnitTree() {
		String currentId="01";
		code = "520199000000";//查询所有单位
			
		String roothql = " from Unit where id=? and 1=? order by code";
		unitList = unitService.findAllByParams(roothql, new Object[] {currentId,1});
		currentId = unitList.get(0).getId();
		ZTreeBean ztbTemp = new ZTreeBean();
		ztbTemp.setId(unitList.get(0).getId());
		ztbTemp.setpId(null);
		ztbTemp.setName(unitList.get(0).getShortName());
		ztbTemp.setIsParent(UNIT_ISPAR_FALSE);
		ztbTemp.setIcon("../images/xtgl_icon/ztree-icon_dw.png");
		unitTree.add(ztbTemp);
		String hql = " from Unit where superOrg.id = ? order by code";
		unitList = unitService.findAllByParams(hql, new Object[] {currentId});
		if (unitList.isEmpty()) {
			return SUCCESS;
		} else {
			for (Unit unit : unitList) {
				ZTreeBean ztb = new ZTreeBean();
				ztb.setId(unit.getId());
				ztb.setpId(unit.getSuperOrg() == null ? null : unit.getSuperOrg().getId());
				ztb.setName(unit.getShortName());
				if(!unit.getSubOrgs().isEmpty()){//判断下级是单位还是部门
					if(unit.getSubOrgs().get(0).getClass().getName().equals(ORGANIZATION_UNIT)){
						ztb.setIsParent(UNIT_ISPAR_TRUE);
					}else {
						ztb.setIsParent(UNIT_ISPAR_FALSE);
					}
					
				}else{
					ztb.setIsParent(UNIT_ISPAR_FALSE);
				}
				ztb.setIcon("../images/xtgl_icon/ztree-icon_dw.png");
				unitTree.add(ztb);
			}
			System.out.println(unitTree.size());
			return SUCCESS;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(String treeNodes) {
		this.treeNodes = treeNodes;
	}

	public List<UnitSearchBean> getUnitSearchBeans() {
		return unitSearchBeans;
	}

	public void setUnitSearchBeans(List<UnitSearchBean> unitSearchBeans) {
		this.unitSearchBeans = unitSearchBeans;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public UnitSearchBean getUnitSearch() {
		return unitSearch;
	}

	public void setUnitSearch(UnitSearchBean unitSearch) {
		this.unitSearch = unitSearch;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ZTreeBean> getUnitTree() {
		return unitTree;
	}

	public void setUnitTree(List<ZTreeBean> unitTree) {
		this.unitTree = unitTree;
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Unit> unitList) {
		this.unitList = unitList;
	}

}
