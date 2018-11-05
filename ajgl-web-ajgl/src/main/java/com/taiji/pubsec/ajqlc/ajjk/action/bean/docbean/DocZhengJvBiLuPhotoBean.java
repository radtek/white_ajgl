package com.taiji.pubsec.ajqlc.ajjk.action.bean.docbean;

import java.util.Date;

/**
 * 证据笔录photo note_s_upphoto
 */
public class DocZhengJvBiLuPhotoBean {
	
	private String id;	//							
	
	private String caseid;	//案件编号	
	
	private String baseName;	//
	
	private String noteid;	//
	
	private byte[] photo;	//
	
	private String photoCopy;  //base64
	
	private String photodescribe;	//				
	
	private String sort;	//		
	
	private String inputpsn;	//录入人			
	
	private Long inputtime;	//录入时间						
	
	private String modifiedpsn;	//修改人				
	
	private Long modifiedtime;	//修改时间			
	
	private Long districttime;	//入地市库时间			
	
	private Date provincetime;	//入省库时间			
	
	private String deletag;	//删除标识				
	
	private Long hck_rksj;			//			
	
	private Long hck_gxsj;		//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getNoteid() {
		return noteid;
	}

	public void setNoteid(String noteid) {
		this.noteid = noteid;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhotodescribe() {
		return photodescribe;
	}

	public void setPhotodescribe(String photodescribe) {
		this.photodescribe = photodescribe;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getInputpsn() {
		return inputpsn;
	}

	public void setInputpsn(String inputpsn) {
		this.inputpsn = inputpsn;
	}

	public Long getInputtime() {
		return inputtime;
	}

	public void setInputtime(Long inputtime) {
		this.inputtime = inputtime;
	}

	public String getModifiedpsn() {
		return modifiedpsn;
	}

	public void setModifiedpsn(String modifiedpsn) {
		this.modifiedpsn = modifiedpsn;
	}

	public Long getModifiedtime() {
		return modifiedtime;
	}

	public void setModifiedtime(Long modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	public Long getDistricttime() {
		return districttime;
	}

	public void setDistricttime(Long districttime) {
		this.districttime = districttime;
	}

	public Date getProvincetime() {
		return provincetime;
	}

	public void setProvincetime(Date provincetime) {
		this.provincetime = provincetime;
	}

	public String getDeletag() {
		return deletag;
	}

	public void setDeletag(String deletag) {
		this.deletag = deletag;
	}

	public Long getHck_rksj() {
		return hck_rksj;
	}

	public void setHck_rksj(Long hck_rksj) {
		this.hck_rksj = hck_rksj;
	}

	public Long getHck_gxsj() {
		return hck_gxsj;
	}

	public void setHck_gxsj(Long hck_gxsj) {
		this.hck_gxsj = hck_gxsj;
	}

	public String getPhotoCopy() {
		return photoCopy;
	}

	public void setPhotoCopy(String photoCopy) {
		this.photoCopy = photoCopy;
	}
	
	
}
