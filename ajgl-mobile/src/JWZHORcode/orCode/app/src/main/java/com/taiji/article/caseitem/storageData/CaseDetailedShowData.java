 package com.taiji.article.caseitem.storageData;

import java.util.ArrayList;
import java.util.List;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;

 public class CaseDetailedShowData
{
    public List<CaseData> l_Detailed=new ArrayList<CaseData>();
    public String suspectName ="";//嫌疑人姓名
    public String caseCode="";//案件编号
    public String caseName="";
    public String papers ="";//文书名称
    public String caseItemNumber="";//code
    public String caseItemName ="";
    public String earmark="";

    /*
    @Override
    public void copyData(CaseData d)
    {
        if(d==null)return;
        super.copyData(d);
        try
        {
            CaseDetailedShowData tempData=(CaseDetailedShowData)d;
            if(tempData.l_Detailed==null)return;
            l_Detailed=  new  ArrayList<CaseData>();
            Collections.addAll(l_Detailed, new CaseData[tempData.l_Detailed.size()]);
            Collections.copy(l_Detailed, tempData.l_Detailed);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public CaseData createCopyData(CaseData d)
    {
        CaseData d1=super.createCopyData(d);
       // CaseDetailedShowData d=new CaseDetailedShowData();
        try
        {
            CaseDetailedShowData tempData=(CaseDetailedShowData)d1;
            if(l_Detailed==null)return d1;
            tempData.l_Detailed=  new  ArrayList<CaseData>();
            Collections.addAll(tempData.l_Detailed, new CaseData[l_Detailed.size()]);
            Collections.copy(tempData.l_Detailed,l_Detailed);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return d;

    }*/
}
