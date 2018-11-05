package com.taiji.pubsec.ajqlc.wrapper.szpt.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.util.ServiceConstant;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.szpt.bean.InstructionReceiveSubjectBean;
import com.taiji.pubsec.ajqlc.wrapper.szpt.bean.InstructionWsBean;
import com.taiji.pubsec.ajqlc.wrapper.szpt.exception.SzptException;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;

@Service("instructionService")
public class InstructionServiceImpl implements InstructionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstructionServiceImpl.class) ;
	
	public static final String SZPT_UNIT_TYPE = "com.taiji.pubsec.businesscomponents.organization.model.Unit" ;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IUnitService unitService;
	
	@Override
	public ResultBean create(String suspectIdCardNo, String formCode, String content, String type, String[] receiveUnits, String creatorCode,
			String creatorUnitCode) {
		ResultBean res = new ResultBean(true);
		if(StringUtils.isBlank(suspectIdCardNo)||"无".equals(suspectIdCardNo)){
			res.setResult(false);
			return null;
		}
		InstructionWsBean bean = new InstructionWsBean() ;
		bean.setContent(content);
		bean.setCreatePeopleCode(creatorCode);//创建人：案件管理固定
		bean.setCreatePeopleDepartmentCode(creatorUnitCode);//创建单位：案件管理固定
		bean.setCreateTime(new Date().getTime());
		bean.setIsNofityDepartmentLeader(1);
		bean.setRelatedObjectId(suspectIdCardNo);// 嫌疑人的身份证号
		bean.setRelatedObjectType("");//TODO
		bean.setRelateObjectContent("");//TODO
		bean.setRequireFeedbackTime(new Date().getTime() + 12 * 60 * 60 * 1000);//下发指令时间加12小时
		bean.setType(type);
		bean.setTypeContent("");//TODO
		bean.setTypeName("");
		List<InstructionReceiveSubjectBean> receiveSubjectBeans = new ArrayList<InstructionReceiveSubjectBean>();
		for(String receiveUnit: receiveUnits){
			InstructionReceiveSubjectBean receiveBean = new InstructionReceiveSubjectBean();
			receiveBean.setReceiveSubjectId(receiveUnit);
			receiveBean.setReceiveSubjectType(SZPT_UNIT_TYPE);
			receiveBean.setStatus("0000000012001");//状态：代签收
			receiveBean.setReceiveTime(new Date().getTime());
			
			receiveSubjectBeans.add(receiveBean);
		}
		bean.setSubs(receiveSubjectBeans);
		String url = "http://" + ServiceConstant.SZPT_IP + ":" + ServiceConstant.SZPT_PORT + "/szpt-web-all/interface/saveInstruction.action";
//		String url = "http://192.168.19.129:8081/szpt-web-all/interface/saveInstruction.action";
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		
		CloseableHttpResponse resp = null ;
	
		try {
			String jsonStr = getMapper().writeValueAsString(bean);
			StringEntity entity = new StringEntity(jsonStr, "utf-8");//解决中文乱码问题    
			entity.setContentEncoding("UTF-8");  
			entity.setContentType("application/json");    
			httpPost.setEntity(entity);
			
			resp = client.execute(httpPost);
			
			HttpEntity he = null ;
			
			if(resp.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				he = resp.getEntity();	
				String respContent = EntityUtils.toString(he, Charset.forName("UTF-8"));
				
				Map<String, Object> respMap = getMapper().readValue(respContent, Map.class) ;
				
				LOGGER.debug("respContent String：{}", respContent);
				
				LOGGER.debug("respContent map：{}", respMap);
			}else{
				LOGGER.debug("HttpStatus code：{}", resp.getStatusLine().getStatusCode());
				LOGGER.debug("HttpStatus reason：{}", resp.getStatusLine().getReasonPhrase());
			}
		
			if(he!=null){
				EntityUtils.consume(he);
			}
		} catch (RuntimeException | IOException e) {
			res.setResult(false);
			LOGGER.error("向实战平台发送指令失败", e);
		}finally {
			try {
				if(resp!=null){
					resp.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	private ObjectMapper getMapper(){
		return new ObjectMapper();
	}
	
}
