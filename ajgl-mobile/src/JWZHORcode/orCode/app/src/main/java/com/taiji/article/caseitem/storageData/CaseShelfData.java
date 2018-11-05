package com.taiji.article.caseitem.storageData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;

public class CaseShelfData implements Serializable
{
 public String DID="";
 public String scanId="";
 public String PID="";
 public String caseCode="";
 public String areaCode ="";
 public String areaName ="";
 public String lockerName ="";
 public List<CaseData> list_CaseData=new ArrayList<CaseData>();

 public int ViewState=1;
 public int DataType=-1;//0储存无价 1保险柜
 public int OrderPosition=0;

 public java.util.Date  DataCreateTime=new java.util.Date();
 public java.util.Date DataUpdateTime=new java.util.Date();
 public int uploadState=-1;//-1未提交,1提交成功 2提交失败



 //排序DataType为1的(新建的)排在前面
 public void sortCaseData()
 {
  if(list_CaseData==null)return;
  CaseData tempData=null;
  for(int i=0;i<list_CaseData.size();i++)
  {
   if(list_CaseData.get(i).DataType!=1)
   {
    for(int j=i+1;j<list_CaseData.size();j++)
    {
     if(list_CaseData.get(j).DataType==1)
     {
      tempData=list_CaseData.get(i);
      list_CaseData.set(i,list_CaseData.get(j));
      list_CaseData.set(j,tempData);
      break;
     }
    }
   }
  }
 }


 public static void sortCaseData(List<CaseData> list_CaseData)
 {
  if(list_CaseData==null)return;
  CaseData tempData=null;
  for(int i=0;i<list_CaseData.size();i++)
  {
   if(list_CaseData.get(i).DataType!=1)
   {
    for(int j=i+1;j<list_CaseData.size();j++)
    {
     if(list_CaseData.get(j).DataType==1)
     {
      tempData=list_CaseData.get(i);
      list_CaseData.set(i,list_CaseData.get(j));
      list_CaseData.set(j,tempData);
      break;
     }
    }
   }
  }
 }


}
