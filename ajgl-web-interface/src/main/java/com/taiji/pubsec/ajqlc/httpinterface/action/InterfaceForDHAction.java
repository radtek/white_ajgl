package com.taiji.pubsec.ajqlc.httpinterface.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.PageCommonAction;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.wrapper.szpt.service.InstructionService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.common.tool.base64.Base64CryptFmtUtil;

import net.sf.json.JSONArray;

/**
 * 给大华的接口Action
 * 
 * @author huangda
 */
@Controller("interfaceForDHAction")
@Scope("prototype")
public class InterfaceForDHAction extends PageCommonAction {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(InterfaceForDHAction.class);
	
	private String caseFormCode;//办案区使用单编码
	
	private String fileName;//文件名称带后缀
	
	private String sxId;//审讯id
	
	private File fileObj;//笔录文件
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	@Resource
	private IPersonService personService;
	@Resource
	private InstructionService instructionService;
	@Resource
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;
	
	public void queryInvolvedArticle(){
		   HttpServletResponse rep = ServletActionContext.getResponse();
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(caseFormCode);
		List<Map<String,String>>  result = new ArrayList<Map<String,String>>();
		if(har.getCarryGoodsInfo()!=null){
			List<CarryGoodsRecord> cgrLst = har.getCarryGoodsInfo().getCarryGoodsRecords();
		   
			for(CarryGoodsRecord temp : cgrLst){
				Map<String,String> map = new HashMap<String,String>();
				map.put("code", temp.getNum());
				;
				List<Attachment> att = attachmentCustomizedService.findByTargetIdAndType(temp.getId(), CarryGoodsRecord.class.getName());
				if(att.size() > 0){
					String attName = att.get(0).getName();
					String returnName = attName.substring(attName.lastIndexOf('.'),attName.length());
					String finalName = temp.getGoodsName()+returnName;
					map.put("name", finalName);
					map.put("url", "http://" + request.getLocalAddr() + ":" +
							request.getLocalPort() + request.getContextPath() + "/dhinterface/downloadFile.action?attachmentId="+att.get(0).getId());
				}
				result.add(map);
			}
		}
		JSONArray json = JSONArray.fromObject(result);
	try {
		String	base64Str = Base64CryptFmtUtil.encode(json.toString().getBytes(), false);
		this.actionOut(base64Str, "text/javascript;charset=UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	public void sendNoteFile(){
		Map<String,Object> map = new HashMap<String,Object>();
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(caseFormCode);
		try {
			
			InputStream is = request.getInputStream();
			String hz = fileName.substring(fileName.lastIndexOf('.')+1, fileName.length());
			if(hz.equals("txt") || hz.equals("doc")){	
				List<AskRoomAllocationRecord> rlst= askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId(har.getId());
				if(rlst != null && rlst.size() > 0){
					AskRoomAllocationRecord arar = rlst.get(rlst.size()-1);
					attachmentCustomizedService.create(new Attachment(), fileName, is, arar.getId(), AskRoomAllocationRecord.class.getName());
					map.put("result", true);
					map.put("error", "");
					JSONArray json = JSONArray.fromObject(map);
					this.outJSON(json.toString());
					logger.debug("办案区使用单单号："+caseFormCode+"接收笔录成功");
				}else{
					map.put("result", false);
					map.put("error", "接收笔录失败，未查询到分配记录");
					this.outJSON(json.toString());
					logger.debug("办案区使用单单号："+caseFormCode+"接收笔录失败，未查询到分配记录");
				}
			}
		} catch (Exception e) {
			map.put("result", false);
			map.put("error", e.getMessage());
			JSONArray json = JSONArray.fromObject(map);
			this.outJSON(json.toString());
			logger.error("办案区使用单单号："+caseFormCode+"接收笔录失败");
		}
		
		if(!StringUtils.isBlank(har.getBasicCase().getByQuestioningPeopleIdentifyNum())){
        	instructionService.create(har.getBasicCase().getByQuestioningPeopleIdentifyNum(), har.getBasicCase().getHandlingAreaReceiptNum(), "嫌疑人" + har.getBasicCase().getByQuestioningPeopleName() + "的讯问结束，笔录已经生成，请部署并开展相关研判工作。", "0000000011004", new String[]{"520199310000"}, "999934", "520199180000"); //创建人编码固定，创建人单位编码取法制大队编码
		}
	}
	
	@JSON(serialize=false)
	public void getCaseFormAttachment(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(caseFormCode);
			String url = "http://" + request.getLocalAddr() + ":" +
					request.getLocalPort() + request.getContextPath() + "/harPrint/printHandlingAreaReceipt.action?id=" + har.getId();
			map.put("result", url);
			map.put("error", "");
			JSONArray json = JSONArray.fromObject(map);
			this.outJSON(json.toString());
		} catch (Exception e) {
			map.put("result", false);
			map.put("error", e.getMessage());
			JSONArray json = JSONArray.fromObject(map);
			this.outJSON(json.toString());
		}
	}
	
	public String getCaseFormCode() {
		return caseFormCode;
	}
	
	public void setCaseFormCode(String caseFormCode) {
		this.caseFormCode = caseFormCode;
	}

	public File getFileObj() {
		return fileObj;
	}

	public void setFileObj(File fileObj) {
		this.fileObj = fileObj;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSxId() {
		return sxId;
	}

	public void setSxId(String sxId) {
		this.sxId = sxId;
	}
	
}