package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.JpaSqlDaoImpl;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.generatenum.service.IGenerateNumService;
import com.taiji.pubsec.ajqlc.sawp.bean.TempObjectBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageInInfoBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.model.StoragePosition;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageInService;
import com.taiji.pubsec.ajqlc.sawp.util.ParamMapUtil;
import com.taiji.pubsec.ajqlc.sawp.util.TempObjectModelToBean;
import com.taiji.pubsec.common.tool.sql.SQLTool;

@Service("temporaryStorageInServiceImpl")
public class TemporaryStorageInServiceImpl  implements ITemporaryStorageInService{
	@Resource
	private Dao<TemporaryStorageIn, String> dao;
	
	@Resource
	private JpaSqlDaoImpl jpaSqlDao;
	
	@Resource
	private IGenerateNumService generateNumService;

	@Override
	public String saveTemporaryStorageIn(TemporaryStorageIn temporaryStorageIn) {
		this.dao.save(temporaryStorageIn);
		return temporaryStorageIn.getId();
	}
	
	@Override
	public void updateTemporaryStorageIn(TemporaryStorageIn temporaryStorageIn) {
		 this.dao.update(temporaryStorageIn);
	}
	
	@Override
	public Pager<TempStorageInInfoBean> findTemporaryStorageInByPage(
			Map<String, Object> paramMap, int pageNo, int pageSize) {
		String hql = "select ti.bm , ti.rksj , bc.ssaj , bc.bamj, bc.bwxrxm ,ti.bz from t_sawp_zcrkd ti , t_baq_jbqk bc "
				+ " where ti.sydbh = bc.sydbh  ";
		String sqlCount = "select Count(*) from t_sawp_zcrkd ti , t_baq_jbqk bc "
				+ " where ti.sydbh = bc.sydbh  ";
		StringBuilder xql = new StringBuilder(hql);
		StringBuilder xqlCount = new StringBuilder(sqlCount);
		List<Object> params = new ArrayList<Object>();
		// 入库单编码
		if(ParamMapUtil.isNotBlank(paramMap.get("code"))){
			xql.append(" and  ti.bm like ?");
			xqlCount.append(" and  ti.bm like ?");
			SQLTool.SQLAddEscape(xql);
			params.add("%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("code")) + "%");
		}
		// 起始时间
		if(ParamMapUtil.isNotBlank(paramMap.get("rkStartDate"))){
			xql.append(" and ti.rksj >= ?");
			xqlCount.append(" and ti.rksj >= ?");
			SQLTool.SQLAddEscape(xql);
			params.add(paramMap.get("rkStartDate"));
		}
		// 结束时间
		if(ParamMapUtil.isNotBlank(paramMap.get("rkEndDate"))){
			xql.append(" and ti.rksj <= ?");
			xqlCount.append(" and ti.rksj <= ?");
			SQLTool.SQLAddEscape(xql);
			params.add(paramMap.get("rkEndDate"));
		}
		// 案件编号
		if(ParamMapUtil.isNotBlank(paramMap.get("caseCode"))){
			xql.append(" and bc.ssaj like ?");
			xqlCount.append(" and bc.ssaj like ?");
			SQLTool.SQLAddEscape(xql);
			params.add("%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("caseCode")) + "%");
		}
		// 物品持有人
		if(ParamMapUtil.isNotBlank(paramMap.get("ownerName"))){
			xql.append(" and bc.bwxrxm like ?");
			xqlCount.append(" and bc.bwxrxm like ?");
			SQLTool.SQLAddEscape(xql);
			params.add("%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("ownerName")) + "%");
		}
		//显示我的入库单
		if(ParamMapUtil.isNotBlank(paramMap.get("myid"))){
			xql.append(" and ti.cjr_id = ? ");
			xqlCount.append(" and ti.cjr_id = ?");
			SQLTool.SQLAddEscape(xql);
			params.add(paramMap.get("myid"));
		}
		int count = this.jpaSqlDao.getJdbcTemplate().queryForObject(xqlCount.toString(), params.toArray(),Integer.class);
		xql.append(" order by ti.bm limit ?,?");
		params.add(pageNo*pageSize);
		params.add(pageSize);
		List<TempStorageInInfoBean> objecPager =  this.jpaSqlDao.getJdbcTemplate().query(
				xql.toString(), params.toArray(), new RowMapper<TempStorageInInfoBean>(){  
                    @Override  
                    public TempStorageInInfoBean mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    	TempStorageInInfoBean tib  = new TempStorageInInfoBean();  
                    	tib.setStorageInCode(rs.getString("bm"));
                    	Date date = rs.getTimestamp("rksj");
            			tib.setStorageInDateTime(sdf.format(date));
            			tib.setCaseCode(rs.getString("ssaj"));
            			tib.setSolvePolice(rs.getString("bamj"));
            			tib.setObjectOwnerPerson(rs.getString("bwxrxm"));
            			tib.setRemark(rs.getString("bz"));
                        return tib;  
                    }  
        });
		Pager<TempStorageInInfoBean> tempStoragePager = new Pager<TempStorageInInfoBean>();
		tempStoragePager.getPageList().addAll(objecPager);
		tempStoragePager.setTotalNum(count);
		return tempStoragePager;
	}
	
	@Override
	public TempStorageInInfoBean findTemporaryStorageInDeatailByCode(
			String code) {
		String xql = "select ti.bm , ti.rksj , bc.ssaj , bc.bamj,bc.sfzjhm, bc.bwxrxm ,ti.bz ,ti.czsj, ti.czr_id,ti.sydbh,ti.cjr_id from t_sawp_zcrkd ti , t_baq_jbqk bc , t_baq_sswpxx cgi"
				+ " where ti.sydbh = bc.sydbh  and  bc.baqsyd_id = cgi.baqsyd_id  and ti.bm = ? ";
		List<String> params = new ArrayList<String>();
		if (code == null ){
			return null;
		}
		params.add(code);
		List<TempStorageInInfoBean> tempStorageInInfoList = this.jpaSqlDao.getJdbcTemplate().query(
				xql.toString(), params.toArray(), new RowMapper<TempStorageInInfoBean>(){  
                    @Override  
                    public TempStorageInInfoBean mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    	TempStorageInInfoBean tib  = new TempStorageInInfoBean();  
                    	tib.setStorageInCode(rs.getString("bm"));
                    	Date date = rs.getTimestamp("rksj");
            			tib.setStorageInDateTime(sdf.format(date));
            			tib.setCaseCode(rs.getString("ssaj"));
            			tib.setSolvePolice(rs.getString("bamj"));
            			tib.setObjectOwnerPerson(rs.getString("bwxrxm"));
            			tib.setRemark(rs.getString("bz"));
            			Date date1 = rs.getTimestamp("czsj");
            			tib.setModifyTime(sdf.format(date1));
            			tib.setModifyPerson(rs.getString("czr_id"));
            			tib.setCreatePerson(rs.getString("cjr_id"));
            			tib.setIdCard(rs.getString("sfzjhm"));
            			tib.setHarCode(rs.getString("sydbh"));
                        return tib;  
                    }  
        });
		//查找随身物品
		TempStorageInInfoBean tib = null;
		if(tempStorageInInfoList.size()>0){
			tib = tempStorageInInfoList.get(0);
			tib.setTempObjectBeanList(findTemporaryObjectByStorageByCode(tib.getHarCode()));
			tib.setInSaveSelfList(findStorageShelfByCode(code));//储物架
		}
		return tib;
	}
	
	@Override
	public List<TempObjectBean> findTemporaryObjectByStorageByCode(String harCode) {
		List<String> params = new ArrayList<String>();
		params.add(harCode);
		String sql = "select tbsr.id ,tbsr.wpmc,tbsr.tz,tbsr.sl,tbsr.jldw,tbsr.bz,tbsr.zt"
				+ " from t_baq_sswpxx tbs, t_baq_sswprkxx tbsr ,t_baq_jbqk bc "
				+ " where tbs.baqsyd_id=bc.baqsyd_id and  tbs.id=tbsr.sswpxx_id  and bc.sydbh = ?";
		List<TempObjectBean> tempObjecList = this.jpaSqlDao.getJdbcTemplate().query(
				sql,params.toArray(), new RowMapper<TempObjectBean>(){  
                    @Override  
                    public TempObjectBean mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	TempObjectBean tib  = new TempObjectBean();  
                    	tib.setObjectId(rs.getString("id"));
                    	tib.setObjectName(rs.getString("wpmc"));
                    	tib.setObjectCharacter(rs.getString("tz"));
                    	tib.setInThisNum(rs.getString("sl"));
                    	tib.setMeasureUnit(rs.getString("jldw"));
                    	tib.setRemark(rs.getString("bz"));
                    	tib.setStatus(rs.getString("zt"));
                    	tib.setObjectClassName("com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord");
                        return tib;  
                    }  
        });
		
		return tempObjecList;
	}
	
	@Override
	public TemporaryStorageIn findTemporaryStorageInById(String storageId) {
		return this.dao.findById(TemporaryStorageIn.class,storageId);
	}
	
	@Override
	public List<TemporaryStorageShelfBean> findStorageShelfByCode(String code) {
		TemporaryStorageIn ti = findTemporaryStorageInByCode(code);
		List<TemporaryStorageShelfBean> temporaryStorageShelfBeanList = new ArrayList<TemporaryStorageShelfBean>();
		if(ti != null){
			for(StoragePosition sp: ti.getStoragePosition()){
				if(sp.getBgg() != null){
					temporaryStorageShelfBeanList.add(
							TempObjectModelToBean.tempStorageSelfToBean(sp.getBgg()));

				}
			}
		}
		return temporaryStorageShelfBeanList;
	}
	
	@Override
	public TemporaryStorageIn findTemporaryStorageInByCode(String code) {
		String sql = "from TemporaryStorageIn where code = ?";
		return  this.dao.findByParams(TemporaryStorageIn.class, sql, new Object[]{code});
	}
	
	@Override
	public TemporaryStorageIn findTemporaryStorageInByHarCode(String harCode) {
		String sql = "from TemporaryStorageIn where harCode = ?";
		return  this.dao.findByParams(TemporaryStorageIn.class, sql, new Object[]{harCode});
	}
	
	@Override
	public String acquireNum() {
		String code = "ZCRK";
		String num = generateNumService.acquireNum(code);
		int iNum = Integer.parseInt(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String nowYear = sdf.format(new Date());
		return code + nowYear + String.format("%05d", iNum);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Pager<TempStorageInInfoBean> findTimeoutTemporaryInByPage(
			Map<String, Object> paramMap, int pageNo, int pageSize) {
		
		StringBuilder sqlSelect = new StringBuilder("select t.rksj, t.sydbh, t.bm,t.bz");
		StringBuilder sqlCount = new StringBuilder("select count(t.id)");
		StringBuilder sqlFrom = new StringBuilder(" from t_sawp_zcrkd t ");
		StringBuilder sqlwhere =  new StringBuilder(" where  t.rksj < date_sub(NOW(),interval 1 day) " //超时
				//找出未出库的入库单 包括两种情况 1 出库单没创建 2 出库单创建了 状态为未出库
				+ " and ( t.id not in (select c.zcrkd_id from t_sawp_zcckd c  ) or t.id in ( select c.zcrkd_id from t_sawp_zcckd c where c.zt = '0'))");
		List<Object> params = new ArrayList<Object>();
		//储物柜编码 查询
		if(ParamMapUtil.isNotBlank(paramMap.get("shelfCodeList"))){
			sqlFrom.append(" ,t_sawp_zcrkd_zcwpg zz , t_sawp_zcwpg g");
			List<String> bmList	= (List<String>)paramMap.get("shelfCodeList");
			sqlwhere.append("  and t.id = zz.rkd_id and zz.bgg_id = g.id and g.bm in ").append(translateInParameter(bmList)).append(" ");
		}
		//储物区编码 查询
		if(ParamMapUtil.isNotBlank(paramMap.get("areaCode"))){
			//如果储物柜编码不为空
			if(ParamMapUtil.isNotBlank(paramMap.get("shelfCodeList"))){
				sqlFrom.append(" ,t_sawp_zcwpq q");
				sqlwhere.append(" and q.id = g.area_id and q.bm = ?");
				params.add(paramMap.get("areaCode").toString());
			}else{//如果储物柜编码为空
				sqlFrom.append(" ,t_sawp_zcrkd_zcwpg zz , t_sawp_zcwpg g , t_sawp_zcwpq q");
				sqlwhere.append("  and t.id = zz.rkd_id and zz.bgg_id = g.id and q.id = g.area_id and q.bm = ?");
				params.add(paramMap.get("areaCode").toString());
			}
		} 
		//入库单编码
		if(ParamMapUtil.isNotBlank(paramMap.get("storageInCode"))){
			sqlwhere.append(" and t.bm like ? ");
			SQLTool.SQLAddEscape(sqlwhere);
			params.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("storageInCode")) + "%");
		}
		//案件编码或者名称
		if(ParamMapUtil.isNotBlank(paramMap.get("caseCodeOrName"))){
			sqlFrom.append(" t_baq_jbqk j ,t_sla_ajjbxx aj");
			sqlwhere.append(" and j.sydbh = t.sydbh and t.ssaj = aj.caseId and (aj.caseId like ? ");
			SQLTool.SQLAddEscape(sqlwhere);
			params.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseCodeOrName")) + "%");
			sqlwhere.append(" or aj.caseName like ? ");
			SQLTool.SQLAddEscape(sqlwhere);
			params.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseCodeOrName")) + "%");
		}
		//嫌疑人身份证号 或者名称
		if(ParamMapUtil.isNotBlank(paramMap.get("suspectIdOrName"))){
			if(ParamMapUtil.isNotBlank(paramMap.get("caseCodeOrName"))){
				sqlwhere.append(" and (j.sfzjhm like ? ");
				SQLTool.SQLAddEscape(sqlwhere);
				params.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("suspectIdOrName")) + "%");
				sqlwhere.append(" or j.bwxrxm like ?)");
				SQLTool.SQLAddEscape(sqlwhere);
				params.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("suspectIdOrName")) + "%");
			}else{
				sqlFrom.append(" t_baq_jbqk j ");
				sqlwhere.append(" and j.sydbh = t.sydbh and (j.sfzjhm like ?");
				SQLTool.SQLAddEscape(sqlwhere);
				params.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("suspectIdOrName")) + "%");
				sqlwhere.append(" or j.bwxrxm like ? )");
				params.add("%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("suspectIdOrName")) + "%");
			}
			
		}
		RowMapper<TempStorageInInfoBean> rowMapper	= new RowMapper<TempStorageInInfoBean>(){  
            @Override  
            public TempStorageInInfoBean mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	TempStorageInInfoBean tib  = new TempStorageInInfoBean();  
            	tib.setStorageInCode(rs.getString("bm"));
            	Date date = rs.getTimestamp("rksj");
    			tib.setStorageInDateTime(sdf.format(date));
    			tib.setRemark(rs.getString("bz"));
    			tib.setHarCode(rs.getString("sydbh"));
                return tib;  
            }  
         };
		String xql = sqlSelect.append(sqlFrom ) 
				              .append(sqlwhere).toString() ;
		
		String xqlcount = sqlCount.append(sqlFrom ) 
	                              .append(sqlwhere).toString() ;
		int count =  this.jpaSqlDao.getJdbcTemplate()
				.queryForObject(xqlcount, params.toArray(),Integer.class);
		xql += " limit ?,?";
		params.add(pageNo*pageSize);
		params.add(pageSize);
		List<TempStorageInInfoBean> objecPager =  this.jpaSqlDao.getJdbcTemplate().query(
				xql.toString(), params.toArray(),rowMapper );
		Pager<TempStorageInInfoBean> tempStoragePager = new Pager<TempStorageInInfoBean>();
		tempStoragePager.setPageList(objecPager);
		tempStoragePager.setTotalNum(count);
		return tempStoragePager;
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
}
