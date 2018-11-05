package com.taiji.pubsec.ajgl.util;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.common.tool.base64.Base64CryptFmtUtil;
import com.taiji.pubsec.common.tool.util.FileFmtUtils;
import com.taiji.pubsec.common.web.action.BaseAction;

@Controller("showFileAction")
@Scope("prototype")
public class ShowFileAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShowFileAction.class);

	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;

	private String id;

	private String base64Str;

	/**
	 * 根据字典类型Code获取字典项
	 * 
	 * @return
	 */
	public String findBase64ByAttachId() {
		Attachment att = attachmentCustomizedService.findById(id);
		try {
			base64Str = Base64CryptFmtUtil.encode(
					FileFmtUtils.readByByteBuffer(att.getAttachmentMeta()
							.getAttachmentCopys().get(0).getInputStream()),
					false);
		} catch (UnsupportedEncodingException e) {
			base64Str = "";
			LOGGER.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBase64Str() {
		return base64Str;
	}

	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}
}
