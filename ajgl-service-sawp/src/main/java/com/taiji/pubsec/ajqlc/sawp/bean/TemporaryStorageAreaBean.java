package com.taiji.pubsec.ajqlc.sawp.bean;



public class TemporaryStorageAreaBean {
		private String id;
		private String address;	//详细位置
		
		private String code;	//编码
		
		private String name;	//名称
		
		private String remark;	//备注
		
		private String status;	//状态，字典项id
		
		private String orgName;	//组织机构名称
		
		private String orgId; //组织机构Id

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getOrgId() {
			return orgId;
		}

		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}
}
