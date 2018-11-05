package com.taiji.article.caseitem.storageData;

import com.taiji.util.myArith;

import java.util.ArrayList;
import java.util.List;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;

public class CaseInStorageData extends CaseStorageData
{

    public CaseInStorageData()
    {
        DataType=1;
    }

    public String suspectName ="";//嫌疑人姓名
    //合并相同物品
    public List<CaseData> l_view_show_caseData=new ArrayList<CaseData>();

    @Override
    public List<CaseData> getList_caseData()
    {
        return l_caseData;
    }

    @Override
    public void sortCaseData(List<CaseData> l_caseData) {
        //l_view_show_caseData=  new  ArrayList<CaseData>();
        //Collections.addAll(l_view_show_caseData, new CaseData[l_caseData.size()]);
        //Collections.copy(l_view_show_caseData, l_caseData);
        l_view_show_caseData=super.copyList(l_caseData);
        //入库单合并分货架导致多条的物品
        if (l_view_show_caseData == null) return;
        CaseData tempData;
        CaseData tempData2;
        //  合并同ID(同一ID物品操作后多货架导致多条)
        for (int i = 0; i < l_view_show_caseData.size(); i++) {
            tempData = l_view_show_caseData.get(i);
            tempData.CItemID="";//清除分项ID
            for (int j = i + 1; j < l_view_show_caseData.size(); j++) {
                tempData2 = l_view_show_caseData.get(j);
                if (tempData.caseItemNumber.equals(tempData2.caseItemNumber))
                {
                    if(tempData.DID.equals(tempData2.DID)&&!tempData.DID.equals(""))
                    {
                        tempData.caseDoItemCount= myArith.add(tempData.caseDoItemCount, tempData2.caseDoItemCount);
                        //tempData.caseDoItemCount = tempData.caseDoItemCount + tempData2.caseDoItemCount;
                        //后操作导致多记录不合并要求数量
                        //tempData.caseItemHasCount = tempData.caseItemHasCount + tempData2.caseItemHasCount;
                        //  tempData.caseItemCountString= tempData.caseItemHasCount+ tempData.unit;
                        if(tempData.lockerCode!=tempData2.lockerCode)
                        {
                            tempData.lockerCode=tempData.lockerCode+","+tempData2.lockerCode;
                            tempData.lockerName=tempData.lockerName+","+tempData2.lockerName;
                        }
                        tempData2=null;
                        l_view_show_caseData.remove(j);
                        j--;
                    }
                }
            }
            tempData=null;
        }
        //合并不同ID(因为原货架不同导致的多记录)
        for (int i = 0; i < l_view_show_caseData.size(); i++) {
            tempData = l_view_show_caseData.get(i);
            for (int j = i + 1; j < l_view_show_caseData.size(); j++) {
                tempData2 = l_view_show_caseData.get(j);
                if (tempData.caseItemNumber.equals(tempData2.caseItemNumber)) {
                    tempData.caseDoItemCount= myArith.add(tempData.caseDoItemCount, tempData2.caseDoItemCount);
                    //tempData.caseDoItemCount = tempData.caseDoItemCount + tempData2.caseDoItemCount;
                    //不是一条原记录时合并要数量
                    if(!tempData.DID.equals(tempData2.DID)&&!tempData.DID.equals(""))
                    {
                        tempData.caseItemHasCount= myArith.add(tempData.caseItemHasCount, tempData2.caseItemHasCount);
                        //tempData.caseItemHasCount = tempData.caseItemHasCount + tempData2.caseItemHasCount;
                        tempData.caseItemCountString= tempData.getShowCaseItemHasCount()+ tempData.unit;
                       // tempData.caseItemCount=tempData.caseItemHasCount;
                    }
                    if(tempData.lockerCode!=tempData2.lockerCode)
                    {
                        tempData.lockerCode=tempData.lockerCode+","+tempData2.lockerCode;
                        tempData.lockerName=tempData.lockerName+","+tempData2.lockerName;
                    }

                    tempData2=null;
                    l_view_show_caseData.remove(j);
                    j--;
                }
            }
            tempData=null;
        }
        super.sortCaseData(l_view_show_caseData);
    }

    //根据物品创建货架结构物品集合,无货架物品不会加入,既入库前不显示
    @Override
    public List<CaseShelfData> createShelfData()
    {
        return  super.createShelfData();
    }

    //入库不验证货架
    @Override
    public boolean checkShelfInStorage(String scanCode,CaseShelfData caseShelfData)
    {
        return true;
    }

    //验证物品是否在单据中,子类需重写
    @Override
    public boolean checkItemInStorage(String scanCode,CaseData caseData)
    {
        if (l_view_show_caseData == null) return false;

        for (int i = 0; i < l_view_show_caseData.size(); i++) {
            if(scanCode.trim().equals(l_view_show_caseData.get(i).caseItemNumber))
            {
                if(caseData!=null)
                   caseData.copyData(l_view_show_caseData.get(i));
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkItemInSameArea(String scanCode, String shelfAreaCode, List<CaseData> allNewCaseItem, String[] outStr)
    {
        if (l_view_show_caseData == null) return false;
        String tempStr="";
        if(shelfAreaCode==null ||shelfAreaCode.equals(""))
        {
            if(tempStr.equals(""))tempStr+="数据错误!无法识别当前保管区";
            if(outStr!=null && outStr.length>0)
            {
                outStr[0]=tempStr;
            }
            return false;
        }

        if(allNewCaseItem!=null)
        {
            for (int i = 0; i < allNewCaseItem.size(); i++) {
                //编码同,区域不同
                if(scanCode.trim().equals(allNewCaseItem.get(i).caseItemNumber) && !shelfAreaCode.trim().equals(allNewCaseItem.get(i).areaCode)
                        && allNewCaseItem.get(i).areaCode!=null
                        &&!allNewCaseItem.get(i).areaCode.equals(""))
                {
                    if(tempStr.equals(""))tempStr+="物品已放在 "+allNewCaseItem.get(i).areaName+"\r\n"+"请将同类物品放入相同保管区!";
                    if(outStr!=null && outStr.length>0)
                    {
                        outStr[0]=tempStr;
                    }
                    return false;
                }
            }
        }

        for (int i = 0; i < l_view_show_caseData.size(); i++) {
            //编码同,区域不同
            if(scanCode.trim().equals(l_view_show_caseData.get(i).caseItemNumber) && !shelfAreaCode.trim().equals(l_view_show_caseData.get(i).areaCode)
                    && l_view_show_caseData.get(i).areaCode!=null
                    &&!l_view_show_caseData.get(i).areaCode.equals(""))
            {
                if(tempStr.equals(""))tempStr+="物品已放在 "+l_view_show_caseData.get(i).areaName+"\r\n"+"请将同类物品放入相同保管区!";
                if(outStr!=null && outStr.length>0)
                {
                    outStr[0]=tempStr;
                }
                return false;
            }
        }

        return true;
    }


    //验证物品是否在单据中货架上,子类需重写,注意实现时同一物品可能属于多货架,不能break
    @Override
    public boolean checkItemInShelfStorage(String scanCode,String shelfCode,CaseData caseData)
    {
        /*
        if (l_caseData == null) return false;

        for (int i = 0; i < l_caseData.size(); i++) {
            if(scanCode.trim().equals(l_caseData.get(i).caseItemNumber) && shelfCode.trim().equals(l_caseData.get(i).lockerCode))
            {
                if(caseData!=null)
                    caseData.copyData(l_caseData.get(i));
                return true;
            }
        }
        */
        // 已存在的货架上摘找不到
        return checkItemInStorage( scanCode, caseData);
                /*
        if (l_caseData == null) return false;

        for (int i = 0; i < l_caseData.size(); i++) {
            if(scanCode.trim().equals(l_caseData.get(i).caseItemNumber) && shelfCode.trim().equals(l_caseData.get(i).lockerCode))
            {
                if(caseData!=null)
                    caseData.copyData(l_caseData.get(i));
                return true;
            }
        }
        return false;
        */
    }

    @Override
    public boolean  removeTempItemByStorage(CaseData CaseData)
    {
        //for(int i=0;i<)
        if(CaseData.DataType!=1)return false;
        if(CaseData.caseDoItemCount>0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    @Override
    public double  findItemMaxChangeCount(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan)
    {
        if (l_caseData == null || l_itemAllScan==null||scanItemCode==null || shelfCode==null) return -1;
        double itemAllScanCount=0;
        for (int i = 0; i < l_itemAllScan.size(); i++)
        {
            if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber))
            {
                itemAllScanCount= myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
                //itemAllScanCount+=l_itemAllScan.get(i).scanCount;
               // if(tempStr.equals(""))tempStr+="本次入库\r\n";
            }
        }

        for (int i = 0; i < l_view_show_caseData.size(); i++) {
            if(scanItemCode.trim().equals(l_view_show_caseData.get(i).caseItemNumber))
            {
                double result= myArith.sub(l_view_show_caseData.get(i).caseItemHasCount, l_view_show_caseData.get(i).caseDoItemCount);
                result= myArith.sub(result, itemAllScanCount);
               // double result=l_view_show_caseData.get(i).caseItemHasCount-l_view_show_caseData.get(i).caseDoItemCount-itemAllScanCount;
                return result;
            }
        }
        return -1;
    }

    //判断操作数量是否溢出(如 5个物品却扫了6个),子类需重写
    //itemAllScanCount 对应物品的总扫描数(如同一物品已放入货架A,B各一个,且这次扫了一个,这个值应为3)
    //false没溢出
    //入库单要验证数量 合并的显示集合规定数-合并的显示集合历史数-扫描数>0
    @Override
    public boolean   checkItemOperand(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan,String[] outStr)
    {
        if (l_caseData == null || l_itemAllScan==null||scanItemCode==null || shelfCode==null) return true;
        String tempStr="";
        String tempStr1="";
        double itemAllScanCount=0;
        for (int i = 0; i < l_itemAllScan.size(); i++)
        {
            if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber))
            {
                itemAllScanCount= myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
               // itemAllScanCount+=l_itemAllScan.get(i).scanCount;
                if(tempStr.equals(""))tempStr+="本次入库\r\n";
                tempStr+="  "+l_itemAllScan.get(i).lockerName+" "+l_itemAllScan.get(i).scanCount+l_itemAllScan.get(i).unit+"\r\n";
            }
        }

        for (int i = 0; i < l_view_show_caseData.size(); i++) {
            if(scanItemCode.trim().equals(l_view_show_caseData.get(i).caseItemNumber))
            {
                double result= myArith.sub(l_view_show_caseData.get(i).caseItemHasCount, l_view_show_caseData.get(i).caseDoItemCount);
                result= myArith.sub(result, itemAllScanCount);
               // double result=l_view_show_caseData.get(i).caseItemHasCount-l_view_show_caseData.get(i).caseDoItemCount-itemAllScanCount;

                tempStr1="需入库  "+l_view_show_caseData.get(i).getShowCaseItemHasCount()+l_view_show_caseData.get(i).unit+"\r\n";
                String[] tempLockerCodeGroup=l_view_show_caseData.get(i).lockerCode.split(",");
                if(tempLockerCodeGroup.length>1)
                {
                    tempStr1+="历史入库\r\n"+l_view_show_caseData.get(i).lockerName+" 共 "+l_view_show_caseData.get(i).getShowCaseDoItemCount()+l_view_show_caseData.get(i).unit+"\r\n";
                }
                else if(l_view_show_caseData.get(i).caseDoItemCount==0)
                {
                    tempStr1+="历史入库"+"  "+l_view_show_caseData.get(i).lockerName+" "+l_view_show_caseData.get(i).getShowCaseDoItemCount()+l_view_show_caseData.get(i).unit+"\r\n";
                }
                else
                {
                    tempStr1+="历史入库\r\n"+"  "+l_view_show_caseData.get(i).lockerName+" "+l_view_show_caseData.get(i).getShowCaseDoItemCount()+l_view_show_caseData.get(i).unit+"\r\n";
                }
                tempStr1=tempStr1+tempStr;
                if(outStr!=null && outStr.length>0)
                {
                    outStr[0]=tempStr1;
                }
                if(result>0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        return true;
    }

    //整体(提交数据前)验证是否符合提交标准,子类必须重写,否则不能通过验证
    //多数单据已由checkItemOperand扫描物品时验证,可以直接返回true(或者再验证一次),调整单存在绑组操作必须作后验证
    //入库单无限制
    @Override
    public boolean  validateUpdateItem(List<CaseData> l_itemAllScan,CaseData noPassData,String[] outStr)
    {
        return true;
    }

    //整理提交前的数据
    //子类必须重写
    //主要处理操作采用物品为主的视角,后台以ID为准,例如 同一编码物品在不同货架,后台ID不同,但用户看到的物品没有区别
    //入库不需要整理
    @Override
    public List<CaseData>  cleanUpdateItem(List<CaseData> l_itemAllScan)
    {
        return l_itemAllScan;
    }
}
