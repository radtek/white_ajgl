package com.taiji.pubsec.ajqlc.sla.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * （刑事）取保候审决定书/执行通知书 DOC_XS010_NEW
 * @author chengkai
 */
@Entity
@Table(name = "t_sla_doc_qbhsjds_new")
public class DocQuBaoHouShenNew {
	
	@Id
	private String docid;	//文书编号					 							
	
	private String caseid;	//案件编号
	
	private String inputpsnId;	//录入人 警号
	
	private String a1;	//单位名称 第一联						
	
	private String a2;	//机关名称 第一联 					
	
	private String a3; //单位简称 第一联						
	
	private String a4;	//字号 第一联									
	
	private String a5;	//年份 第一联							
	
	private String a6;	//文书号 第一联							
	
	private String a7;	//案件名称 第一联				 					

	private String a8;	//案件编号 第一联										
	
	private String a9;	//被取保候审人 第一联										
	
	private String a10;	//被取保候审人性别 第一联											
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date a11;	//被取保候审出生日期(第一联										
	
	private String a12;	//取保原因(第一联)											
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date a13;	//起算时间(第一联)											
	
	private String a14;	//保证人(第一联)										
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date a16;	//保证人出生日期(第一联)												
	
	private String a15;	//保证人性别(第一联)										
	
	private String a17;	//住址(第一联)										
	
	private String a18;	//单位(第一联)										
	
	private String a19;	//保证金(第一联)									
	
	private String a20;	//办案单位(第一联)										
	
	private String a21;	//执行机关(第一联)				
	
	private String a22;	//批准人(第一联)					
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date a23;	//批准时间(第一联)				
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date a24;	//填发时间(第一联)					
	
	private String a25;	//填发人(第一联)					
	
	private String b1;	//单位名称 第二联								
	
	private String b2;	//机关名称 第二联 					
	
	private String b3;	//单位简称 第二联					
	
	private String b4;	//字号 第二联					
	
	private String b5;	//年份 第二联													
	
	@Column(length = 11)
	private Integer b6;	//文书号 第二联														
	
	private String b7;	//犯罪嫌疑人名称(第二联) 				 
	
	private String b8;	//性别(第二联) 						
	
	private String personid;	//人员编号											
	
	private String unitid;	//单位编号												
	
	@Column(length = 2000)
	private String multiid;	//多功能综合字段								
	
	private String inputpsn;	//录入人								
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date inputtime;	//录入时间											
	
	private String modifiedpsn;	//修改人									
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedtime;	//修改时间								
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date districttime;	//入地市库时间				
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date provincetime;	//入省库时间				
	
	private String deletag;	//删除标识						
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date hck_rksj;			//					
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date hck_gxsj;		//						
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date b9;	//出生日期(第二联)									
	
	private String b10;	//住址(第二联)							
	
	private String b11;	//单位及职业(第二联)							
	
	private String b12;	//联系方式(第二联)							
	
	private String b13;	//我局正 侦查的XXX案(第二联)							
	
	private String b14;	//犯罪嫌疑人B14(第二联)						
	
	private String b15;	//第XX条(第二联)						
	
	private String b16;	//第XX款(第二联)						
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date b17;	//期限从XX起(第二联)					
	
	private String b18;	//证人(第二联)						
	
	private String b19;	//交纳保证金(第二联)					
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date b20;	//日期(第二联)				
	
	private String c1;	//年份(第三联)																
	
	private String c2;	//机关名称(第三联)  				
	
	private String c3;	//单位简称(第三联)				
	
	private String c4;	//字号(第三联)				
	
	private String c5;	//年份(第三联)														
	
	@Column(length = 11)
	private Integer c6;	//文书号(第三联)															
	
	private String c7;	//犯罪嫌疑人(第三联) 				 
	
	private String c8;	//性别(第三联) 				 
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date c9;	//出生日期(第三联)				
	
	private String c10;	//住址(第三联)				
	
	private String c11;	//单位及职业(第三联)				

	private String c12;	//联系方式(第三联)				
	
	private String c13;	//我局正侦查XX案(第三联)				
	
	private String c14;	//单犯罪嫌疑人C14(第三联)				
	
	private String c15;	//第XXX条(第三联)				
	
	private String c16;	//第XX款(第三联)				
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date c17;	//期限从XX起(第三联)					
	
	private String c18;	//证人(第三联)				
	
	private String c19;	//交纳保证金(第三联)				
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date c20;	//日期(第三联)					
	
	private String d1;	//单位名称(第四联)				
	
	private String d2;	//机关名称(第四联)				
	
	private String d3;	//单位简称(第四联)				
	
	private String d4;	//字号(第四联)				
	
	private String d5;	//年份(第四联)				
	
	@Column(length = 11)
	private Integer d6;	//文书号(第四联)				
	
	private String d7;	//XX单位名称(第四联)				
	
	private String d8;	//因XXX(第四联)				
	
	private String d9;	//犯罪嫌疑人(第四联)			
	
	private String d10;	//性别(第四联)			
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date d11;	//文书号(第四联) 			
	
	private String d12;	//住址(第四联)			
	
	private String d13;	//单位及职业(第四联)		
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date d14;	//取保期限从XX起(第四联) 			
	
	private String d15;	//保证人(第四联)		
	
	private String d16;	//保证人(第四联)		
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date d17;	//日期(第四联)			
	
	private String d18;	//XX案(第四联)		
	
	private String d19;	//联系方式(第四联)		

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA5() {
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getA6() {
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA8() {
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public Date getA11() {
		return a11;
	}

	public void setA11(Date a11) {
		this.a11 = a11;
	}

	public String getA12() {
		return a12;
	}

	public void setA12(String a12) {
		this.a12 = a12;
	}

	public Date getA13() {
		return a13;
	}

	public void setA13(Date a13) {
		this.a13 = a13;
	}

	public String getA14() {
		return a14;
	}

	public void setA14(String a14) {
		this.a14 = a14;
	}

	public Date getA16() {
		return a16;
	}

	public void setA16(Date a16) {
		this.a16 = a16;
	}

	public String getA15() {
		return a15;
	}

	public void setA15(String a15) {
		this.a15 = a15;
	}

	public String getA17() {
		return a17;
	}

	public void setA17(String a17) {
		this.a17 = a17;
	}

	public String getA18() {
		return a18;
	}

	public void setA18(String a18) {
		this.a18 = a18;
	}

	public String getA19() {
		return a19;
	}

	public void setA19(String a19) {
		this.a19 = a19;
	}

	public String getA20() {
		return a20;
	}

	public void setA20(String a20) {
		this.a20 = a20;
	}

	public String getA21() {
		return a21;
	}

	public void setA21(String a21) {
		this.a21 = a21;
	}

	public String getA22() {
		return a22;
	}

	public void setA22(String a22) {
		this.a22 = a22;
	}

	public Date getA23() {
		return a23;
	}

	public void setA23(Date a23) {
		this.a23 = a23;
	}

	public Date getA24() {
		return a24;
	}

	public void setA24(Date a24) {
		this.a24 = a24;
	}

	public String getA25() {
		return a25;
	}

	public void setA25(String a25) {
		this.a25 = a25;
	}

	public String getB1() {
		return b1;
	}

	public void setB1(String b1) {
		this.b1 = b1;
	}

	public String getB2() {
		return b2;
	}

	public void setB2(String b2) {
		this.b2 = b2;
	}

	public String getB3() {
		return b3;
	}

	public void setB3(String b3) {
		this.b3 = b3;
	}

	public String getB4() {
		return b4;
	}

	public void setB4(String b4) {
		this.b4 = b4;
	}

	public String getB5() {
		return b5;
	}

	public void setB5(String b5) {
		this.b5 = b5;
	}

	public Integer getB6() {
		return b6;
	}

	public void setB6(Integer b6) {
		this.b6 = b6;
	}

	public String getB7() {
		return b7;
	}

	public void setB7(String b7) {
		this.b7 = b7;
	}

	public String getB8() {
		return b8;
	}

	public void setB8(String b8) {
		this.b8 = b8;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getMultiid() {
		return multiid;
	}

	public void setMultiid(String multiid) {
		this.multiid = multiid;
	}

	public String getInputpsn() {
		return inputpsn;
	}

	public void setInputpsn(String inputpsn) {
		this.inputpsn = inputpsn;
	}

	public Date getInputtime() {
		return inputtime;
	}

	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}

	public String getModifiedpsn() {
		return modifiedpsn;
	}

	public void setModifiedpsn(String modifiedpsn) {
		this.modifiedpsn = modifiedpsn;
	}

	public Date getModifiedtime() {
		return modifiedtime;
	}

	public void setModifiedtime(Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	public Date getDistricttime() {
		return districttime;
	}

	public void setDistricttime(Date districttime) {
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

	public Date getHck_rksj() {
		return hck_rksj;
	}

	public void setHck_rksj(Date hck_rksj) {
		this.hck_rksj = hck_rksj;
	}

	public Date getHck_gxsj() {
		return hck_gxsj;
	}

	public void setHck_gxsj(Date hck_gxsj) {
		this.hck_gxsj = hck_gxsj;
	}

	public Date getB9() {
		return b9;
	}

	public void setB9(Date b9) {
		this.b9 = b9;
	}

	public String getB10() {
		return b10;
	}

	public void setB10(String b10) {
		this.b10 = b10;
	}

	public String getB11() {
		return b11;
	}

	public void setB11(String b11) {
		this.b11 = b11;
	}

	public String getB12() {
		return b12;
	}

	public void setB12(String b12) {
		this.b12 = b12;
	}

	public String getB13() {
		return b13;
	}

	public void setB13(String b13) {
		this.b13 = b13;
	}

	public String getB14() {
		return b14;
	}

	public void setB14(String b14) {
		this.b14 = b14;
	}

	public String getB15() {
		return b15;
	}

	public void setB15(String b15) {
		this.b15 = b15;
	}

	public String getB16() {
		return b16;
	}

	public void setB16(String b16) {
		this.b16 = b16;
	}

	public Date getB17() {
		return b17;
	}

	public void setB17(Date b17) {
		this.b17 = b17;
	}

	public String getB18() {
		return b18;
	}

	public void setB18(String b18) {
		this.b18 = b18;
	}

	public String getB19() {
		return b19;
	}

	public void setB19(String b19) {
		this.b19 = b19;
	}

	public Date getB20() {
		return b20;
	}

	public void setB20(Date b20) {
		this.b20 = b20;
	}

	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}

	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	public String getC3() {
		return c3;
	}

	public void setC3(String c3) {
		this.c3 = c3;
	}

	public String getC4() {
		return c4;
	}

	public void setC4(String c4) {
		this.c4 = c4;
	}

	public String getC5() {
		return c5;
	}

	public void setC5(String c5) {
		this.c5 = c5;
	}

	public Integer getC6() {
		return c6;
	}

	public void setC6(Integer c6) {
		this.c6 = c6;
	}

	public String getC7() {
		return c7;
	}

	public void setC7(String c7) {
		this.c7 = c7;
	}

	public String getC8() {
		return c8;
	}

	public void setC8(String c8) {
		this.c8 = c8;
	}

	public Date getC9() {
		return c9;
	}

	public void setC9(Date c9) {
		this.c9 = c9;
	}

	public String getC10() {
		return c10;
	}

	public void setC10(String c10) {
		this.c10 = c10;
	}

	public String getC11() {
		return c11;
	}

	public void setC11(String c11) {
		this.c11 = c11;
	}

	public String getC12() {
		return c12;
	}

	public void setC12(String c12) {
		this.c12 = c12;
	}

	public String getC13() {
		return c13;
	}

	public void setC13(String c13) {
		this.c13 = c13;
	}

	public String getC14() {
		return c14;
	}

	public void setC14(String c14) {
		this.c14 = c14;
	}

	public String getC15() {
		return c15;
	}

	public void setC15(String c15) {
		this.c15 = c15;
	}

	public String getC16() {
		return c16;
	}

	public void setC16(String c16) {
		this.c16 = c16;
	}

	public Date getC17() {
		return c17;
	}

	public void setC17(Date c17) {
		this.c17 = c17;
	}

	public String getC18() {
		return c18;
	}

	public void setC18(String c18) {
		this.c18 = c18;
	}

	public String getC19() {
		return c19;
	}

	public void setC19(String c19) {
		this.c19 = c19;
	}

	public Date getC20() {
		return c20;
	}

	public void setC20(Date c20) {
		this.c20 = c20;
	}

	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public String getD2() {
		return d2;
	}

	public void setD2(String d2) {
		this.d2 = d2;
	}

	public String getD3() {
		return d3;
	}

	public void setD3(String d3) {
		this.d3 = d3;
	}

	public String getD4() {
		return d4;
	}

	public void setD4(String d4) {
		this.d4 = d4;
	}

	public String getD5() {
		return d5;
	}

	public void setD5(String d5) {
		this.d5 = d5;
	}

	public Integer getD6() {
		return d6;
	}

	public void setD6(Integer d6) {
		this.d6 = d6;
	}

	public String getD7() {
		return d7;
	}

	public void setD7(String d7) {
		this.d7 = d7;
	}

	public String getD8() {
		return d8;
	}

	public void setD8(String d8) {
		this.d8 = d8;
	}

	public String getD9() {
		return d9;
	}

	public void setD9(String d9) {
		this.d9 = d9;
	}

	public String getD10() {
		return d10;
	}

	public void setD10(String d10) {
		this.d10 = d10;
	}

	public Date getD11() {
		return d11;
	}

	public void setD11(Date d11) {
		this.d11 = d11;
	}

	public String getD12() {
		return d12;
	}

	public void setD12(String d12) {
		this.d12 = d12;
	}

	public String getD13() {
		return d13;
	}

	public void setD13(String d13) {
		this.d13 = d13;
	}

	public Date getD14() {
		return d14;
	}

	public void setD14(Date d14) {
		this.d14 = d14;
	}

	public String getD15() {
		return d15;
	}

	public void setD15(String d15) {
		this.d15 = d15;
	}

	public String getD16() {
		return d16;
	}

	public void setD16(String d16) {
		this.d16 = d16;
	}

	public Date getD17() {
		return d17;
	}

	public void setD17(Date d17) {
		this.d17 = d17;
	}

	public String getD18() {
		return d18;
	}

	public void setD18(String d18) {
		this.d18 = d18;
	}

	public String getD19() {
		return d19;
	}

	public void setD19(String d19) {
		this.d19 = d19;
	}

	public String getInputpsnId() {
		return inputpsnId;
	}

	public void setInputpsnId(String inputpsnId) {
		this.inputpsnId = inputpsnId;
	}

	
	
}
