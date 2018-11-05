package com.taiji.pubsec.ajqlc.baq.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.LoginInfoAction;
import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.bean.OperateRecordBean;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;

/**
 * 办案区使用单Action
 * 
 * @author huangda
 *
 */
@Controller("operateRecordAction")
@Scope("prototype")
public class OperateRecordAction extends LoginInfoAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(OperateRecordAction.class);

	@Resource
	private IOperateRecordService operateRecordService;
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;

	private String id;

	private List<OperateRecordBean> orbLst = new ArrayList<OperateRecordBean>();

	/**
	 * 初始化页面数据
	 * 
	 * @return
	 */
	public String searchOperateRecordByTargetId() {
		List<OperateRecord> orLst = operateRecordService.findOperateRecordByModelObjectId(id,
				HandlingAreaReceipt.class.getName());
		if (orLst.isEmpty()) {
			return SUCCESS;
		}
		for (OperateRecord or : orLst) {
			OperateRecordBean bean = new OperateRecordBean();
			try {
				ConvertUtils.register(new DateConverter(null), java.util.Date.class);
				BeanUtils.copyProperties(bean, or);
			} catch (Exception e) {
				LOGGER.debug("beanCopy错误", e);
			}
			if ("1".equals(or.getNoteType())) {
				List<FileObjectBean> fobLst = new ArrayList<FileObjectBean>();
				String ids = or.getHandlingAreaActivityRecordIds();
				if(ids != null){
					String[] arr = ids.split(",");
					for(String activityRecordId : arr){
						List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(activityRecordId,
								HandlingAreaActivityRecord.class.getName());
						for (Attachment att : attLst) {
							FileObjectBean fob = new FileObjectBean();
							fob.setId(att.getId());
							fob.setName(att.getName());
							fob.setSize(String.valueOf(att.getSize()));
							fobLst.add(fob);
						}
						bean.setAttLst(fobLst);
					}
				}
			}
			orbLst.add(bean);
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "modelObjectId="+id+",modelObject="+HandlingAreaReceipt.class.getName());
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<OperateRecordBean> getOrbLst() {
		return orbLst;
	}

	public void setOrbLst(List<OperateRecordBean> orbLst) {
		this.orbLst = orbLst;
	}
}
