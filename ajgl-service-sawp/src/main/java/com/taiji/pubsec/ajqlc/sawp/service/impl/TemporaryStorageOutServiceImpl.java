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
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageOutInfoBean;
import com.taiji.pubsec.ajqlc.sawp.model.StoragePosition;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageOut;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.ajqlc.sawp.service.ITempStorageSelfService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageOutService;
import com.taiji.pubsec.ajqlc.sawp.util.ParamMapUtil;
import com.taiji.pubsec.common.tool.sql.SQLTool;

@Service("temporaryStorageOutService")
public class TemporaryStorageOutServiceImpl implements ITemporaryStorageOutService {

	@Resource
	private Dao<TemporaryStorageOut, String> dao;
	
	@Resource
	private JpaSqlDaoImpl jpaSqlDao;
	
	@Resource
	private IGenerateNumService generateNumService;
	
	@Resource
	private ITempStorageSelfService tempStorageSelfService;
	
	private static final String USE_STATUS_FREE = "002";	//使用状态：空闲
	
	@Override
	public String acquireNum() {
		String code = "ZCCK";
		String num = generateNumService.acquireNum(code);
		int iNum = Integer.parseInt(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String nowYear = sdf.format(new Date());
		return code + nowYear + String.format("%04d", iNum);
	}

	@Override
	public String save(TemporaryStorageOut temporaryStorageOut) {
		this.dao.save(temporaryStorageOut);
		return temporaryStorageOut.getId();
	}

	@Override
	public void update(TemporaryStorageOut temporaryStorageOut) {
		//更新出库返还单
		dao.update(temporaryStorageOut);
		
		//查看查看相应入库单使用的柜子
		List<StoragePosition> storagePositions = temporaryStorageOut.getStorageIn().getStoragePosition();
		for(StoragePosition storagePosition: storagePositions){
			TemporaryStorageShelf shelf = storagePosition.getBgg();
			List<StoragePosition> positions = shelf.getStoragePosition();
			
			for(StoragePosition position: positions){
				TemporaryStorageIn storageIn = position.getRkd();
				String xql = "select o from TemporaryStorageOut as o where o.storageIn.id = ?";
				TemporaryStorageOut storageOut = dao.findByParams(TemporaryStorageOut.class, xql, new Object[]{storageIn.getId()});
				if(storageOut == null || storageOut.getStatus() == "0"){
					return ;
				}
			}
			
			//更新储物架的状态
			shelf.setStatus(USE_STATUS_FREE);
			tempStorageSelfService.updateTemporaryStorageSelf(shelf);
		}
	}

	@Override
	public TemporaryStorageOut findById(String temporaryStorageOutId) {
		return  dao.findById(TemporaryStorageOut.class, temporaryStorageOutId);
	}

	@Override
	public void delete(String temporaryStorageOutId) {
		dao.delete(TemporaryStorageOut.class, temporaryStorageOutId);;
	}

	@Override
	public Pager<TempStorageOutInfoBean> findTempStorageOutInfosByQueryConditions(Map<String, Object> paramMap,
			int pageNo, int pageSize) {
		Pager<TempStorageOutInfoBean> beanPager = new Pager<TempStorageOutInfoBean>();
		
		StringBuilder sql = new StringBuilder("select c.bm, c.cksj, j.ssaj, j.bamj, j.bwxrxm, c.zt, c.bz from t_sawp_zcckd as c, t_sawp_zcrkd as r, t_baq_jbqk as j "
				+ "where c.zcrkd_id = r.id and r.sydbh = j.sydbh  ");
		StringBuilder sqlCount = new StringBuilder("select count(*) from t_sawp_zcckd as c, t_sawp_zcrkd as r, t_baq_jbqk as j "
				+ "where c.zcrkd_id = r.id and r.sydbh = j.sydbh ");
		
		StringBuilder sqlWhere = new StringBuilder("");
		List<Object> sqlParams = new ArrayList<Object>();
		// 出库返还单编号
		if(ParamMapUtil.isNotBlank(paramMap.get("temporaryStorageOutCode"))){
			sqlWhere.append("and  c.bm like ? ");
			SQLTool.SQLAddEscape(sqlWhere);
			sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("temporaryStorageOutCode")) + "%");
		}
		// 查询出库返还日期开始 
		if(ParamMapUtil.isNotBlank(paramMap.get("queryOutDateStart"))){
			sqlWhere.append("and c.cksj >= ? ");
			sqlParams.add(paramMap.get("queryOutDateStart"));
		}
		// 查询出库返还日期结束
		if(ParamMapUtil.isNotBlank(paramMap.get("queryOutDateEnd"))){
			sqlWhere.append("and c.cksj < ? ");
			sqlParams.add(paramMap.get("queryOutDateEnd"));
		}
		// 案件编号
		if(ParamMapUtil.isNotBlank(paramMap.get("caseCode"))){
			sqlWhere.append("and j.ssaj like ? ");
			SQLTool.SQLAddEscape(sqlWhere);
			sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("caseCode")) + "%");
		}
		// 物品持有人
		if(ParamMapUtil.isNotBlank(paramMap.get("ownerName"))){
			sqlWhere.append("and j.bwxrxm like ? ");
			SQLTool.SQLAddEscape(sqlWhere);
			sqlParams.add("%" + SQLTool.SQLSpecialChTranfer((String)paramMap.get("ownerName")) + "%");
		}
		// 显示我的出库返还单
		if(ParamMapUtil.isNotBlank(paramMap.get("myId"))){
			sqlWhere.append("and c.cjr_id = ? ");
			sqlParams.add(paramMap.get("myId"));
		}
		
		int totalNum = this.jpaSqlDao.getJdbcTemplate().queryForObject(sqlCount.append(sqlWhere).toString(), sqlParams.toArray(),Integer.class);
		
		sqlWhere.append("order by c.bm limit ?,?");
		sqlParams.add(pageNo * pageSize);
		sqlParams.add(pageSize);
		List<TempStorageOutInfoBean> beanList =  this.jpaSqlDao.getJdbcTemplate().query(
				sql.append(sqlWhere).toString(), sqlParams.toArray(), new RowMapper<TempStorageOutInfoBean>(){  
                    @Override  
                    public TempStorageOutInfoBean mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    	TempStorageOutInfoBean bean  = new TempStorageOutInfoBean();  
                    	bean.setCaseCode(rs.getString("ssaj"));
                    	bean.setCaseName("");//TODO 案件名称另查
                    	bean.setIfAllOut(rs.getString("zt"));
                    	bean.setObjectOwnerPerson(rs.getString("bwxrxm"));
                    	bean.setRemark(rs.getString("bz"));
                    	bean.setSolvePolice(rs.getString("bamj"));
                    	bean.setStorageOutCode(rs.getString("bm"));
                    	bean.setStorageOutDateTime(sdf.format(rs.getTimestamp("cksj")));
                    	
                        return bean;  
                    }  
        });
		
		beanPager.getPageList().addAll(beanList);
		beanPager.setTotalNum(totalNum);
		return beanPager;
	}

	@Override
	public TemporaryStorageOut findByCode(String temporaryStorageOutCode) {
		String sql = "from TemporaryStorageOut where code = ?";
		return  this.dao.findByParams(TemporaryStorageOut.class, sql, new Object[]{temporaryStorageOutCode});
		
	}
	
	@Override
	public TemporaryStorageOut findByHarCode(String harCode) {
		String sql = "select o from TemporaryStorageOut as o where o.storageIn.harCode = ?";
		return  this.dao.findByParams(TemporaryStorageOut.class, sql, new Object[]{harCode});
	}
	
	@Override
	public TemporaryStorageOut findTemporaryStorageOutByStorageInCode(String storageInCode) {
		String xql = "select o from TemporaryStorageOut as o where o.storageIn.code = ?";
		return dao.findByParams(TemporaryStorageOut.class, xql, new Object[]{storageInCode});
	}

}
