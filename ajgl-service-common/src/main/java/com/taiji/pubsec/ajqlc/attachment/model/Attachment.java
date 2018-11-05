package com.taiji.pubsec.ajqlc.attachment.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.attachment.AttachmentMeta;
import com.taiji.pubsec.businesscomponent.attachment.DefaultAttachmentMetaImpl;

@Entity
@Table(name = "t_ywfj")
public class Attachment {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id ;
	
	@OneToOne(targetEntity = DefaultAttachmentMetaImpl.class)
	@JoinColumn(name="fjy_id", unique=true)
	private AttachmentMeta attachmentMeta ;
	
	@Column(name="mbid")
	private String targetId ;
	
	@Column(name="lx")
	private String type ;
	
	@Column(name = "scsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadingTime; // 上传时间
	
	public String getName(){
		if(this.getAttachmentMeta()!=null){
			return this.getAttachmentMeta().getName() ;
		}
		return null;
	}
	
	public Long getSize(){
		if(this.getAttachmentMeta()!=null && this.getAttachmentMeta().getAttachmentCopys().size()>0){
			return Long.valueOf(this.getAttachmentMeta().getAttachmentCopys().get(0).getProperty("filelenth").toString()) ;
		}else{
			return Long.valueOf(0) ;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AttachmentMeta getAttachmentMeta() {
		return attachmentMeta;
	}

	public void setAttachmentMeta(AttachmentMeta attachmentMeta) {
		this.attachmentMeta = attachmentMeta;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUploadingTime() {
		return uploadingTime;
	}

	public void setUploadingTime(Date uploadingTime) {
		this.uploadingTime = uploadingTime;
	}
	
	
}
