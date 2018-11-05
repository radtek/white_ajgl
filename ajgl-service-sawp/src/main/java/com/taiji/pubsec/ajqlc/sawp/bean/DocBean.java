package com.taiji.pubsec.ajqlc.sawp.bean;

public class DocBean {
	
	private String paperId;	//文书id
	
	private String paperName;	//文书名称
	
	private String paperType;	//文书类型
	
	public DocBean(String paperId, String paperName, String paperType) {
		this.paperId = paperId;
		this.paperName = paperName;
		this.paperType = paperType;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}
	
	
	
}
