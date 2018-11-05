package com.taiji.pubsec.ajqlc.wrapper.jzpt.util;
import javax.annotation.Resource;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.JZPTQueryIncrementInterface;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean.QqbwBean;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean.QqbwSinoBean;
/**
 * 
 * @author xinfan
 *
 */
public abstract class JZPTQueryIncrementData implements JZPTQueryIncrementInterface{
	@Resource 
	public CommonService  commonService ;
	/**
	 * 请求报文以<SinoMesssage>开头 根据HCK_GXSJ做查询
	 * @param fromDate
	 * @param toDate
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	
	public String searchIncrementData(String fromDate, String toDate, String currentPage, String pageSize,QqbwSinoBean qqbwSinoBean) {
	    return searchDataSinoMessage("HCK_GXSJ",fromDate,toDate,currentPage,pageSize,qqbwSinoBean);
	}
	/**
	 * 请求报文以<SinoMesssage>开头 根据BZK_RKSJ做查询
	 * @param fromDate
	 * @param toDate
	 * @param currentPage
	 * @param pageSize
	 * @param qqbwSinoBean
	 * @return
	 */
	public String searchIncrementDataByBZK_RKSJ(String fromDate, String toDate, String currentPage, String pageSize,QqbwSinoBean qqbwSinoBean) {
	    return searchDataSinoMessage("BZK_RKSJ",fromDate,toDate,currentPage,pageSize,qqbwSinoBean);
	}
	/**
	 * 请求报文以<SRMesssage>开头的查询方法  以HCK_GXSJ时间查询
	 * @param fromDate
	 * @param toDate
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public String searchIncrementDataSRMessage(String fromDate, String toDate, String currentPage, String pageSize,QqbwBean qqbwBean) {
			return searchDataSRMessage("HCK_GXSJ",fromDate,toDate,currentPage,pageSize,qqbwBean);
  	}
	
	/**
	 * 请求报文以<SRMesssage>开头的查询方法  以BZK_RKSJ时间查询
	 * @param fromDate
	 * @param toDate
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public String searchIncrementDataSRMessageByBZK_RKSJ(String fromDate, String toDate, String currentPage, String pageSize,QqbwBean qqbwBean) {
		return searchDataSRMessage("BZK_RKSJ",fromDate,toDate,currentPage,pageSize, qqbwBean);
	}
	/**
	 * 公有的查询方法 以<SRMessage>开头的报文
	 * @param param 查询条件 
	 * @param fromDate
	 * @param toDate
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	protected String searchDataSRMessage(String param,String fromDate, String toDate, String currentPage, String pageSize,QqbwBean qqbwBean ){
		
		String searchConditicon = "<Item>"
										+ "<Data> "+param+" between to_date('"+fromDate+"','yyyy-MM-dd HH24:mi:ss') and to_date('"+toDate+"','yyyy-MM-dd HH24:mi:ss')</Data>"
										+ "<Data>"+currentPage+"</Data>" //当前页
										+ "<Data>"+pageSize+"</Data>" //每页大小
										+ "<Data>0</Data>"//返回字段名称形式(中文1/英文0)
								+ "</Item>";
		String resultFields = "<Row></Row>";
		String qqbw = CommonService.createQqbw(qqbwBean, this.getResourceid(), searchConditicon, resultFields);
		//获取查询数据
		String result = "";
		try {
			result = DataSyncServiceImpl.getResource(qqbw,this.getWsUrl(),this.getTargetNameSpace(),this.getMethod());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 公有的查询方法 以<SinoMessage>开头的报文
	 * @param param 查询条件 
	 * @param fromDate
	 * @param toDate
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	private String searchDataSinoMessage(String param,String fromDate, String toDate, String currentPage, String pageSize,QqbwSinoBean qqbwSinoBean ){
		
		String searchConditicon = "<Data> "+param+" between to_date('"+fromDate+"','yyyy-MM-dd HH24:mi:ss') and to_date('"+toDate+"','yyyy-MM-dd HH24:mi:ss')</Data>"
								+ "<Data>"+currentPage+"</Data>" //当前页
								+ "<Data>"+pageSize+"</Data>" //每页大小
								+ "<Data>0</Data>";//每页大小
		String resultFields = "<Row></Row>";
		String qqbw = CommonService.createQqbwSino(qqbwSinoBean, this.getResourceid(), searchConditicon, resultFields);
		//获取查询数据
		String result = "";
		try {
			result = DataSyncServiceImpl.getResource(qqbw,this.getWsUrl(),this.getTargetNameSpace(),this.getMethod());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//webservice 发布地址
	private String wsUrl ;
	//命名空间
	private String targetNameSpace ;
	//调用的方法名
	private String method ;
	//资源id  在服务的请求报文看到
	private String resourceid;
	public String getWsUrl() {
		return wsUrl;
	}
	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}
	public String getTargetNameSpace() {
		return targetNameSpace;
	}
	public void setTargetNameSpace(String targetNameSpace) {
		this.targetNameSpace = targetNameSpace;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getResourceid() {
		return resourceid;
	}
	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

}
