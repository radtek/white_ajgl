package com.taiji.pubsec.ajgl.attachment.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.common.web.action.BaseAction;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

@SuppressWarnings("serial")
@Controller("downloadAction")
@Scope("prototype")
public class DownloadAction extends BaseAction{
	
	private String attachmentId ;
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService ;
	
	public String download(){
		log.debug("download attachment id: {}", attachmentId);
		exportInfoReq = AttachmentHandler.makeExportInfoReqByAttachmentId(attachmentId);
		log.debug("download attachment done");
		return "done" ;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

	
	
}
