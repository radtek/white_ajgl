 package com.taiji.article.caseitem.storageData;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;

 public class CaseAdjustmentData extends CaseData {

     public String areaOldCode ="";//存储区域
     public String areaOldName ="";//存储区域
     public String lockerOldCode = "";//旧货架位置
     public String lockerOldName = "";//旧架位置

     @Override
     public CaseData createCopyData(CaseData d)
     {
         try {

             CaseAdjustmentData tempdata = (CaseAdjustmentData)(super.createCopyData(d));
             tempdata.areaOldCode = this.areaOldCode;
             tempdata.areaOldName = this.areaOldName;
             tempdata.lockerOldCode = this.lockerOldCode;
             tempdata.lockerOldName = this.lockerOldName;
             return tempdata;
         }catch (Exception ex)
         {
             ex.printStackTrace();
         }
         return d;
     }
 }
