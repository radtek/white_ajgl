package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.JpaSqlDaoImpl;
import com.taiji.persistence.dao.Pager;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.generatenum.service.IGenerateNumService;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageOutInfoBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.model.StoragePosition;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageOut;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.ajqlc.sawp.service.ITempStorageSelfService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageOutService;
import com.taiji.pubsec.ajqlc.util.ParamMapUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

@Service("tempStorageShelfServiceImpl")
public class TempStorageShelfServiceImpl extends AbstractBaseService<TemporaryStorageShelf, String> implements ITempStorageSelfService{
	@Autowired
	public TempStorageShelfServiceImpl (Dao<TemporaryStorageShelf, String> dao) {
		setDao(dao);
	}
	
	@Resource
	private Dao<TemporaryStorageShelf, String> dao;
	@Resource
	private JpaSqlDaoImpl jpaSqlDao;
	
	@Resource
	private ITemporaryStorageOutService temporaryStorageOutService;
	
	@Resource
	private IDictionaryItemService dictionaryItemService;
	
	@Resource
	private IGenerateNumService generateNumService;
	
	private static final String DICT_CODE_IF_IS = "0000000007002";//字典项编码（是否）：是
	private static final String DICT_CODE_IF_ISNOT = "0000000007001";//字典项编码（是否）：否
	private static final String DICT_CODE_USESTATUS_USE = "001"; //字典项编码（储物架状态）：在用
	private static final String DICT_CODE_USESTATUS_NOTUSE = "002"; //字典项编码（储物架状态）：空闲
	private static final String DICT_TYPE_ID_USESTATUS = "0000000027"; //字典类型id：储物架状态

	@Override
	public String saveTemporaryStorageShelf(TemporaryStorageShelf tempStorgeIn) {
		this.dao.save(tempStorgeIn);
		return tempStorgeIn.getId();
	}
	
	@Override
	public void updateTemporaryStorageSelf(TemporaryStorageShelf tempStorgeIn) {
		this.dao.update(tempStorgeIn);
	}
	
	@Override
	public TemporaryStorageShelf findStorageSelfById(String id){
		 return this.dao.findById(TemporaryStorageShelf.class, id);
	 }
	 
	@Override
	public TemporaryStorageShelf findStorageSelfByCode(String code) {
		String xql = "select tss from TemporaryStorageShelf as tss where tss.code = ?";
		return  this.dao.findByParams(TemporaryStorageShelf.class, xql, new String[] {code});
	}
	@Override
	public Pager<TemporaryStorageShelf> findAllTemporaryStorageShelfByPage(
			String storageAreaId, int pageNo, int pageSize) {
		String xql = "select tsa from TemporaryStorageShelf as tsa where 1 = 1 and tsa.area.id = ?  order by code";
		return this.dao.findByPage(TemporaryStorageShelf.class, new String[]{storageAreaId}, xql, pageNo, pageSize);
	}
	
	@Override
	public void deleteTemporaryStorageSelf(String id) {
		this.dao.delete(TemporaryStorageShelf.class,id);
	}
	
	@Override
	public List<TemporaryStorageShelf> findStorageSelfByStorageAreaId(String storageAreaId) {
		String xql = "select tss from TemporaryStorageShelf as tss where tss.area.id = ?";
		return (List<TemporaryStorageShelf>) this.dao.findAllByParams(
				TemporaryStorageShelf.class, xql, new String[]{storageAreaId});
	}
	
	@Override
	public TemporaryStorageShelf findStorageSelfByName(String storeAreaId, String address) {
		String xql = "select tss from TemporaryStorageShelf as tss where tss.area.id= ?  and tss.address= ?";
		return  this.dao.findByParams(TemporaryStorageShelf.class, xql, new String[] {storeAreaId, address});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pager<TemporaryStorageShelfBean> findAllTemporaryStorageInByConditions(
			Map<String, Object> paramMap, int pageNo, int pageSize) {
		Pager<TemporaryStorageShelfBean> beanPager = new Pager<TemporaryStorageShelfBean>();
		
		StringBuilder sql = new StringBuilder("select tsz.id, tsz.xxwz, tsz.bm, tsz.zt ");
		StringBuilder sqlCount = new StringBuilder("select count(tsz.id) ");
		StringBuilder sqlFrom = new StringBuilder("from t_sawp_zcwpg as tsz ");
		StringBuilder sqlFromOn = new StringBuilder("where 1 = 1 ");
		StringBuilder sqlWhere = new StringBuilder(" ");
		List<Object> sqlParams = new ArrayList<Object>();
		
		//物品区编码
		if(ParamMapUtil.isNotBlank(paramMap.get("areaId"))){
			sqlFrom.append(", t_sawp_zcwpq as wpq ");
			sqlFromOn.append("and tsz.area_id = wpq.id ");
			sqlWhere.append("and wpq.id = ? ");
			sqlParams.add(paramMap.get("areaId"));
		}
		//储物柜编码
		if(ParamMapUtil.isNotBlank(paramMap.get("storageCodeList"))){
			List<String> codeLst = (List<String>) paramMap.get("storageCodeList");
			String bms = translateInParameter(codeLst);
			sqlWhere.append("and tsz.bm in ").append(bms).append(" ");
		}
		
		if(ParamMapUtil.isNotBlank(paramMap.get("suspectNameOrIdCard")) || 
				ParamMapUtil.isNotBlank(paramMap.get("policeNameOrNum")) || 
				ParamMapUtil.isNotBlank(paramMap.get("caseNameOrCode"))){
			sqlFrom.append(", t_sawp_zcrkd_zcwpg as tszz, t_sawp_zcrkd as tsr, t_baq_jbqk as tbj ");
			sqlFromOn.append("and tsz.id = tszz.bgg_id and tszz.rkd_id = tsr.id and tsr.sydbh = tbj.sydbh ");
			
			//对应犯罪嫌疑人姓名/身份证号
			if(ParamMapUtil.isNotBlank(paramMap.get("suspectNameOrIdCard"))){
				sqlWhere.append("and (tbj.bwxrxm like ? ");
				SQLTool.SQLAddEscape(sqlWhere);
				sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("suspectNameOrIdCard")) + "%");
				sqlWhere.append("or tbj.sfzjhm like ? ");
				SQLTool.SQLAddEscape(sqlWhere);
				sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("suspectNameOrIdCard")) + "%");
				sqlWhere.append(") ");
			}
			//办案民警警号或者姓名
			if(ParamMapUtil.isNotBlank(paramMap.get("policeNameOrNum"))){
				sqlFrom.append(", t_baq_sydbamjxx as tbs ");
				sqlFromOn.append("and tbj.baqsyd_id = tbs.baqsyd_id ");
				sqlWhere.append("and (tbs.xm like ? ");
				SQLTool.SQLAddEscape(sqlWhere);
				sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("policeNameOrNum")) + "%");
				sqlWhere.append("or tbs.jh like ? ");
				SQLTool.SQLAddEscape(sqlWhere);
				sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("policeNameOrNum")) + "%");
				sqlWhere.append(") ");
			}
			//案件名称或者编码
			if(ParamMapUtil.isNotBlank(paramMap.get("caseNameOrCode"))){
				sqlFrom.append(", t_sla_ajjbxx as tsa ");
				sqlFromOn.append("and tbj.ssaj = tsa.caseId ");
				sqlWhere.append("and (tsa.caseId like ? ");
				SQLTool.SQLAddEscape(sqlWhere);
				sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseNameOrCode")) + "%");
				sqlWhere.append("or tsa.caseName like ? ");
				SQLTool.SQLAddEscape(sqlWhere);
				sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseNameOrCode")) + "%");
				sqlWhere.append(") ");
			}
		}
		
		
		if(ParamMapUtil.isNotBlank(paramMap.get("isFree"))){
			if(DICT_CODE_IF_IS.equals(paramMap.get("isFree"))){
				sqlWhere.append("and tsz.zt = ? ");
				sqlParams.add(DICT_CODE_USESTATUS_NOTUSE);
			}else if(DICT_CODE_IF_ISNOT.equals(paramMap.get("isFree"))){
				sqlWhere.append("and tsz.zt = ? ");
				sqlParams.add(DICT_CODE_USESTATUS_USE);
			}
		}
		
		int totalNum = this.jpaSqlDao.getJdbcTemplate().queryForObject(sqlCount.append(sqlFrom).append(sqlFromOn).append(sqlWhere).toString(), sqlParams.toArray(),Integer.class);
		
		sqlWhere.append("order by tsz.bm limit ?,? ");
		sqlParams.add(pageSize * pageNo);
		sqlParams.add(pageSize);
		
		List<TemporaryStorageShelfBean> beanList =  this.jpaSqlDao.getJdbcTemplate().query(
				sql.append(sqlFrom).append(sqlFromOn).append(sqlWhere).toString(), sqlParams.toArray(), new RowMapper<TemporaryStorageShelfBean>(){  
                    @Override  
                    public TemporaryStorageShelfBean mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	TemporaryStorageShelfBean bean  = new TemporaryStorageShelfBean();  
                    	bean.setId(rs.getString("id"));
                    	bean.setCode(rs.getString("bm"));
                    	bean.setAddress(rs.getString("xxwz"));
                    	bean.setStatus(rs.getString("zt"));
                    	
                        return bean;  
                    }  
        });
		
		for(TemporaryStorageShelfBean bean: beanList){
			if(DICT_CODE_USESTATUS_NOTUSE.equals(bean.getStatus())){
				bean.setStatus("是");
			}else{
				bean.setStatus("否");
				
				TemporaryStorageShelf shelf = dao.findById(TemporaryStorageShelf.class, bean.getId());
				List<StoragePosition> positions = shelf.getStoragePosition();
				for(StoragePosition position: positions){
					TemporaryStorageIn storageIn = position.getRkd();
					TemporaryStorageOut storageOut = temporaryStorageOutService.findTemporaryStorageOutByStorageInCode(storageIn.getCode());
					if(storageOut == null || DICT_CODE_IF_ISNOT.equalsIgnoreCase(storageOut.getStatus())){
						bean.setStorageInCode(storageIn.getCode());
						break ;
					}else{
						continue ;
					}
				}
			}
		}
		
		beanPager.getPageList().addAll(beanList);
		beanPager.setTotalNum(totalNum);
		
		return beanPager;
	}
	
	private String translateInParameter(List<String> list){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i=0; i<list.size(); i++){
			sb.append("\"").append(list.get(i)).append("\"");
			if(i<list.size()-1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String acquireNum() {
		String code = "CWG";
		String num = generateNumService.acquireNum(code);
		int iNum = Integer.parseInt(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String nowYear = sdf.format(new Date());
		return code + nowYear + String.format("%05d", iNum);
	}

}
