package com.taiji.pubsec.ajqlc.attachment.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.businesscomponent.attachment.AttachmentService;
import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.businesscomponent.attachment.DefaultAttachmentCopyImpl;
import com.taiji.pubsec.businesscomponent.attachment.DefaultAttachmentMetaImpl;
import com.taiji.pubsec.businesscomponent.attachment.databaseimpl.DatabaseAttachment;

@Service("attachmentCustomizedService")
public class AttachmentCustomizedServiceImpl implements IAttachmentCustomizedService{
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao jpaDao ;
	
	@Resource
	private AttachmentService attachmentService;
	
	@SuppressWarnings("unchecked")
	public Attachment findById(String id){
		return (Attachment)this.jpaDao.findById(Attachment.class, id) ;
	}
	
	
	@SuppressWarnings("unchecked")
	public void delete(String id){
		this.jpaDao.delete(Attachment.class, id); 
	}
	
	@SuppressWarnings("unchecked")
	public void delete(Attachment entity){
		this.jpaDao.delete(entity);
	}
	
	public void save(Attachment entity){
		this.jpaDao.saveOrUpdate(entity);
	}
	
	@SuppressWarnings("unchecked")
	public boolean create(Attachment entity, String fileName, File file, String targetId, String type){
		
		DefaultAttachmentMetaImpl am = new DefaultAttachmentMetaImpl();
		am.setName(fileName);
		
		DefaultAttachmentCopyImpl ac = new DefaultAttachmentCopyImpl();
		
		ac.setCreateTime(new Date());
		
        //按数据库系统的格式存
        ac.setStorageType(DatabaseAttachment.class.getName());
        am.getAttachmentCopys().add(ac);
        InputStream sin = null;
        try{
        	sin = new FileInputStream(file);
            attachmentService.create(am, sin);
            
            entity.setAttachmentMeta(am); 
            entity.setTargetId(targetId);
            entity.setType(type);
            
            this.jpaDao.saveOrUpdate(entity);
            
            return true ;
        }catch(FileNotFoundException e){
        	return false;
        }finally {
            if (null != sin) {
                try {
                    sin.close();
                } catch (IOException e) {
                	return false ;
                }
            }
        }
	}
	@SuppressWarnings("unchecked")
	public boolean create(Attachment entity, String fileName, InputStream fileStream, String targetId, String type){
		
		DefaultAttachmentMetaImpl am = new DefaultAttachmentMetaImpl();
		am.setName(fileName);
		
		DefaultAttachmentCopyImpl ac = new DefaultAttachmentCopyImpl();
		
		ac.setCreateTime(new Date());
		
        //按数据库系统的格式存
        ac.setStorageType(DatabaseAttachment.class.getName());
        am.getAttachmentCopys().add(ac);
        
        entity.setTargetId(targetId);
        entity.setType(type);

        attachmentService.create(am, fileStream);
        
        entity.setAttachmentMeta(am); 
        
        entity.setUploadingTime(new Date());
        this.jpaDao.saveOrUpdate(entity);
        
        return true ;		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> findByTargetIdAndType(String targetId, String type) {
		String xql = "select a from " + Attachment.class.getName() + " as a where 1=1"
				+ " and a.targetId=:targetId"
				+ " and a.type=:type"
				+" order by a.uploadingTime desc";
		
		Map<String, Object> xqlMap = new HashMap<String, Object>() ;
		xqlMap.put("targetId", targetId) ;
		xqlMap.put("type", type) ;
		
		List<Attachment> list = this.jpaDao.findAllByParams(Attachment.class, xql, xqlMap) ;
		
		return list ;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void deleteByTargetIdAndType(String targetId, String type) {
		List<Attachment> list = this.findByTargetIdAndType(targetId, type) ;
		if(list.size()>0){
			for(Attachment a : list){
				this.jpaDao.delete(a);
			}
		}
		
	}
}
