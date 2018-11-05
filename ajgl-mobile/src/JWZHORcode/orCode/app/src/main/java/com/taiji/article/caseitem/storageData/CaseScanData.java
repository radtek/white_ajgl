package com.taiji.article.caseitem.storageData;

import com.taiji.article.caseitem.viewmanager.CaseScanShelfAdapter;

import java.io.Serializable;

public class CaseScanData implements Serializable
{
 public CaseShelfData caseShelfData =null;
 public CaseScanShelfAdapter caseInScanShelfAdapter=null;

 public int ViewState=1;
 public int DataType=-1;//1临时创建,目前用于标记这个待扫描货架
 public int OrderPosition=0;

 public CaseScanData()
 {

 }

 public CaseScanData(CaseShelfData caseShelfData)
 {
  this.caseShelfData = caseShelfData;
  // this.caseInScanShelfAdapter=caseInScanShelfAdapter;
  /*
           if(caseShelfData!=null)
           {
            if(caseShelfData.list_CaseData==null ||caseShelfData.list_CaseData.size()==0)
            {

            }

           }*/
 }

}
