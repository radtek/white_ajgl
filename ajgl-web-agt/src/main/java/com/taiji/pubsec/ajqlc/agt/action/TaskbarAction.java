package com.taiji.pubsec.ajqlc.agt.action;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.agt.action.bean.BATBasicCaseBean;
import com.taiji.pubsec.ajqlc.agt.action.bean.MessageAlertBean;
import com.taiji.pubsec.ajqlc.agt.action.bean.QueryLoginDataBean;
import com.taiji.pubsec.ajqlc.alert.model.AlertMessage;
import com.taiji.pubsec.ajqlc.alert.service.IAlertMessageService;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IBasicCaseService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.wrapper.szpt.service.InstructionService;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tool.comet.model.DefaultHintMsg;
import com.taiji.pubsec.common.tool.comet.service.CometdPushService;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;

/**
 * 办案通任务栏action
 * 
 * @author 
 *
 */
@Controller("TaskbarAction")
@Scope("prototype")
public class TaskbarAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskbarAction.class);
	
	public static final String QING_ZHI_ZHONG_XIN_UNIT_ID = "c1bc34ff-d881-4a5c-9fbe-e4e4048ae82c" ;
	
	@Resource
	private IDictionaryItemService dictionaryItemService;
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	@Resource
	private IBasicCaseService basicCaseService;
	
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	
	@Resource
	private IOperateRecordService operateRecordService;

	@Resource
	private CometdPushService cometdPushService;
	
	@Resource
	private IAlertMessageService alertMessageService;
	
	@Resource
	private InstructionService instructionService;
	
	@Resource
	private IAskRoomService askRoomService;
	
	private BATBasicCaseBean basicCaseBean = new BATBasicCaseBean();
	
	private String formId; //使用单id
	
	private String formCode; //使用单编码
	
	private boolean bn=false;
	
	private String jyName;
	
	private Map<String,Object> resultMap=new HashMap<String,Object>();
	
	/**
	 * 任务栏条数查询
	 * @return
	 */
	public  String findMessageCount(){
		List<AlertMessage> mesList=alertMessageService.findAlertMessagesByReceiptCode(formCode);
		resultMap.put("count",mesList.size());
		return SUCCESS;
	}
	
	/**
	 * 加载预警信息
	 * @return
	 */
	public String findMessageAlert(){
		List<AlertMessage> mesList=alertMessageService.findAlertMessagesByReceiptCode(formCode);
		List<MessageAlertBean> list=new ArrayList<MessageAlertBean>();
		for(int i=0;i<mesList.size();i++){
			MessageAlertBean bean=new MessageAlertBean();
			bean.setMessageAlertContent(mesList.get(i).getContent());
			bean.setMessageAlertTime(toString(mesList.get(i).getCreatedTime()));
			list.add(bean);
		}
		resultMap.put("result", list);
		return SUCCESS;
	}
	
	public String findMessageTS(){
		DefaultHintMsg msg = new DefaultHintMsg("{businessType:\"AlertMessage\", businessData:\"\", existingTime:\"\"}") ;
		cometdPushService.pushHint("06fd74d7-d914-4f4c-b357-7edbacfacc7a", msg);
		return SUCCESS;
	}
	
	/**
	 * 更新嫌疑人信息
	 * @return
	 */
	public String updateBasicCase(){

		if (basicCaseBean == null) {
			return SUCCESS;
		}
		BasicCase bc = basicCaseService.findById(basicCaseBean.getId());
		try {
			bc=changeClass(bc,basicCaseBean);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}		
		basicCaseService.updateBasicCase(bc);
		instructionService.create(bc.getByQuestioningPeopleIdentifyNum(), 
				bc.getHandlingAreaReceiptNum(), "嫌疑人"
				+ bc.getByQuestioningPeopleName()
				+ "的基础信息已采集，请部署并开展相关研判工作。", "0000000011004",
				new String[] { QING_ZHI_ZHONG_XIN_UNIT_ID }, "999934", "520199180000"); // 创建人编码固定，创建人单位编码取法制大队编码
		if (basicCaseBean.getUploadNew()) {
			List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(bc.getId(),
					BasicCase.class.getName());
			if (!attLst.isEmpty()) {
				attachmentCustomizedService.delete(attLst.get(0));
			}
			if (basicCaseBean.getAttach() != null && basicCaseBean.getAttach().getId() != null) {
				Attachment att = attachmentCustomizedService.findById(basicCaseBean.getAttach().getId());
				att.setTargetId(bc.getId());
				att.setType(BasicCase.class.getName());
				attachmentCustomizedService.save(att);
			}
		}
		OperateRecord operateRecord = new OperateRecord();
		operateRecord.setModelObject(HandlingAreaReceipt.class.getName());
		operateRecord.setModelObjectId(bc.getHandlingAreaReceipt().getId());
		operateRecord.setNoteText("");
		operateRecord.setNoteType("0");
		operateRecord.setOperateContent("更新人员基本情况");
		operateRecord.setOperateTime(new Date());
		operateRecord.setOperator(basicCaseBean.getUpdataPersonByAgt());//更新人没有
		operateRecordService.saveOperateRecord(operateRecord);
		this.setFlag("true");
//		HandlingAreaReceipt har = bc.getHandlingAreaReceipt();
//		handlingAreaReceiptService.updateHandlingAreaReceipt(har);
		bn=true;
//		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		
		return SUCCESS;
	}
	
	public BasicCase changeClass(BasicCase bc, BATBasicCaseBean basicCaseBean) {
		bc.setByQuestioningPeopleName(basicCaseBean.getByQuestioningPeopleName());//嫌疑人姓名
		bc.setSex(basicCaseBean.getSex());//性别
		bc.setByQuestioningPeopleIdentifyNum(basicCaseBean.getByQuestioningPeopleIdentifyNum());//身份证号
		bc.setByQuestioningPeopleTelphoneNumber(basicCaseBean.getByQuestioningPeopleTelphoneNumber());//电话
		bc.setByQuestioningPeopleAddress(basicCaseBean.getByQuestioningPeopleAddress());//地址
		bc.setDoor(basicCaseBean.getDoor());
		bc.setLawCase(basicCaseBean.getLawCase());
		if(bc!=null){
			this.setModifyPerson(basicCaseBean.getUpdataPersonByAgt());//最新修改人
			this.setModifyTime(DateTimeUtil.formatDateTime(new Date(), Constant.DATE_FORMAT));//最新修改时间
		}
		bc.setCauseOfLawCase(basicCaseBean.getCauseOfLawCase());//案由
		return bc;
	}
	
	/**
	 * 加载研判结果信息
	 * @return
	 */
	public String findResult(){
		return SUCCESS;
	}
	
	// 日期转化为字符串形式  
    public static String toString(Date date) 
    {  
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        return format.format(date);  
    }
    
	
	/**
	 * 前台展示Bean
	 * @return
	 */
    public  QueryLoginDataBean	changeBean(HandlingAreaReceipt hand){
    	QueryLoginDataBean bean=new QueryLoginDataBean();
	    return bean; 
   }
	public String getformId() {
		return formId;
	}
	public void setformId(String formId) {
		this.formId = formId;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	public BATBasicCaseBean getBasicCaseBean() {
		return basicCaseBean;
	}
	public void setBasicCaseBean(BATBasicCaseBean basicCaseBean) {
		this.basicCaseBean = basicCaseBean;
	}
	public boolean isBn() {
		return bn;
	}
	public void setBn(boolean bn) {
		this.bn = bn;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getFormCode() {
		return formCode;
	}
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getJyName() {
		return jyName;
	}

	public void setJyName(String jyName) {
		this.jyName = jyName;
	};
	
	
}
