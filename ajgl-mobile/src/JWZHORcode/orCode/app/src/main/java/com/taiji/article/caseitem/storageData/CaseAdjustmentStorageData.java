package com.taiji.article.caseitem.storageData;

import com.taiji.util.myArith;

import java.util.ArrayList;
import java.util.List;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;

public class CaseAdjustmentStorageData extends CaseStorageData
{
   // public int viewType=4;//4调整出 5调整入 状态影响显示，验证逻辑等

    public String cause="";
    //viewType 为5时有效,记录上一部出库的物品
    public List<CaseData> l_in_caseData=new ArrayList<CaseData>();

    //合并相同物品
    public List<CaseData> l_view_shelf_show_caseData=new ArrayList<CaseData>();//用于调整单出库入库操作界面展示,合并调整时分开的项目

    //单据中所包含的物品对应的案件,这些案件有关系的所有物品所存放的所有货柜
    public List<CaseShelfData> l_case_shelf=new ArrayList<CaseShelfData>();
    @Override
    public List<CaseData> getList_caseData()
    {
        if(ViewState==4)
        {
            return l_caseData;
        }
        else if(ViewState==5)
        {
            return l_in_caseData;
        }
        return null;
    }

    public CaseAdjustmentStorageData()
    {
        DataType=4;
        ViewState=4;
    }


    @Override
    //出库单排序,库存0的放在最后,库存不为0操作大于1的中间,库存不为0操作0的最前
    public void sortCaseData(List<CaseData> l_caseData) {
        if(ViewState==4) {
            sortCaseDataByOutType(l_caseData);
        }
        else if(ViewState==5)
        {
         //   System.out.print("5---------");
            l_view_shelf_show_caseData.clear();
            l_view_shelf_show_caseData=copyList(l_in_caseData);
            sortCaseDataByInType(l_view_shelf_show_caseData);
        //    System.out.print(l_in_caseData.size()+"------------");
        }
    }


    private  void sortCaseDataByInType(List<CaseData> l_caseData) {
        //在用户眼中出库位置不同的物品没有区别,所以合并同物品ID的调出数量
        if (l_view_shelf_show_caseData == null) return;
        CaseData tempData;
        CaseData tempData2;
        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            tempData = l_view_shelf_show_caseData.get(i);
            tempData.CItemID="";
            for (int j = i + 1; j < l_view_shelf_show_caseData.size(); j++) {
                tempData2 = l_view_shelf_show_caseData.get(j);
                if (tempData.caseItemNumber.equals(tempData2.caseItemNumber)) {
                    tempData.caseItemHasCount= myArith.add(tempData.caseItemHasCount, tempData2.caseItemHasCount);
                    //tempData.caseItemHasCount = tempData.caseItemHasCount + tempData2.caseItemHasCount;
                    tempData.caseItemCountString= tempData.getShowCaseItemHasCount()+ tempData.unit;
                    tempData2 = null;
                    l_view_shelf_show_caseData.remove(j);
                    j--;
                }
            }
            tempData = null;
        }

    }

    private  void sortCaseDataByOutType(List<CaseData> l_caseData)
    {
        if (l_caseData == null) return;

        CaseAdjustmentData tempData;

        int index0Number = 0;//记录排序完最后一个操作0的位置,下个排序使用
        //先把操作数0的移动到前面
        for (int i = 0; i < l_caseData.size(); i++) {
            if (l_caseData.get(i).caseDoItemCount != 0)//不是0,往后扔
            {
                for (int j = i + 1; j < l_caseData.size(); j++) {
                    if (l_caseData.get(j).caseDoItemCount == 0) {
                        tempData = (CaseAdjustmentData)l_caseData.get(i);
                        l_caseData.set(i, l_caseData.get(j));
                        l_caseData.set(j, tempData);
                        index0Number = i;
                        break;
                    }
                }
            }
        }

        //库存0的放后面
        for (int i = index0Number + 1; i < l_caseData.size(); i++) {
            double tempdouble= myArith.sub(l_caseData.get(i).caseItemHasCount, l_caseData.get(i).caseDoItemCount);
            if (tempdouble<=0)//是0,往后扔
            {
                for (int j = i + 1; j < l_caseData.size(); j++) {
                    double tempdouble1= myArith.sub(l_caseData.get(j).caseItemHasCount, l_caseData.get(i).caseDoItemCount);
                    if (tempdouble1> 0)
                    {
                        tempData = (CaseAdjustmentData)l_caseData.get(i);
                        l_caseData.set(i, l_caseData.get(j));
                        l_caseData.set(j, tempData);
                        break;
                    }
                }
            }
        }
        l_view_shelf_show_caseData.clear();
        l_view_shelf_show_caseData=copyList(l_caseData);
        if (l_view_shelf_show_caseData == null) return;
        CaseAdjustmentData tempData2;
        //  合并同ID(同一ID物品操作后多货架导致多条)
        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            tempData = (CaseAdjustmentData)l_view_shelf_show_caseData.get(i);
            for (int j = i + 1; j < l_view_shelf_show_caseData.size(); j++) {
                tempData2 = (CaseAdjustmentData)l_view_shelf_show_caseData.get(j);
                if (tempData.caseItemNumber.equals(tempData2.caseItemNumber))
                {
                    if(tempData.DID.equals(tempData2.DID)&&!tempData.DID.equals(""))
                    {
                        tempData.caseDoItemCount = myArith.add(tempData.caseDoItemCount, tempData2.caseDoItemCount);
                      //  tempData.caseDoItemCount = tempData.caseDoItemCount + tempData2.caseDoItemCount;
                        //后操作导致多记录不合并要求数量,这里理论上不会出现ID相同却货架不同的记录
                        if(tempData.lockerOldCode!=tempData2.lockerOldCode)
                        {
                            tempData.lockerOldCode=tempData.lockerOldCode+","+tempData2.lockerOldCode;
                            tempData.lockerOldName=tempData.lockerOldName+","+tempData2.lockerOldName;
                        }
                        tempData2=null;
                        l_view_shelf_show_caseData.remove(j);
                        j--;
                    }
                }
            }
            tempData=null;
        }

    }





    //根据物品创建货架结构物品集合,无货架物品不会加入,既入库前不显示
    @Override
    public List<CaseShelfData> createShelfData()
    {
        if(ViewState==4) {
           return createShelfDataByOutType();
        }
        else if(ViewState==5)
        {
            return createShelfDataByInType();
        }
        return null;
    }

    public List<CaseShelfData> createShelfDataByInType()
    {
        if(l_view_shelf_show_caseData==null)return null;

            List<CaseData> l_new_caseData = new ArrayList<CaseData>();
            //使用上一部出库数据作为原数据,如果后面需要看到已完成的,可以遍历l_caseData获取已完成的,add到l_new_caseData里
        try {
           // Collections.addAll(l_new_caseData, new CaseData[l_in_caseData.size()]);
           // Collections.copy(l_new_caseData, l_in_caseData);
            l_new_caseData=copyList(l_view_shelf_show_caseData);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        List<CaseShelfData> l_caseShelfData = new ArrayList<CaseShelfData>();
        boolean isFine=false;
        CaseShelfData caseShelfData;
        for(int i=0;i<l_new_caseData.size();i++)
        {
            caseShelfData=null;
            if(!l_new_caseData.get(i).lockerCode.trim().equals(""))
            {
                isFine=false;
                for(int j=0;j<l_caseShelfData.size();j++)
                {
                    if(l_caseShelfData.get(j).DID.equals(l_new_caseData.get(i).lockerCode.trim()))
                    {
                        caseShelfData=l_caseShelfData.get(j);
                        isFine=true;
                        break;
                    }
                }
                if(!isFine)
                {
                    caseShelfData =new CaseShelfData();
                    caseShelfData.DID = l_new_caseData.get(i).lockerCode.trim();
                    caseShelfData.lockerName = l_new_caseData.get(i).lockerName.trim();
                    caseShelfData.list_CaseData=new ArrayList<CaseData>();
                    caseShelfData.areaCode= l_new_caseData.get(i).areaCode.trim();
                    caseShelfData.areaName= l_new_caseData.get(i).areaName.trim();
                    l_caseShelfData.add(caseShelfData);
                }
            }
            if(caseShelfData!=null)
            {
                caseShelfData.list_CaseData.add(l_new_caseData.get(i));
            }

        }
        return l_caseShelfData;
    }

    public List<CaseShelfData> createShelfDataByOutType()
    {
        if(l_view_shelf_show_caseData==null)return null;
        List<CaseData> l_new_caseData=  new  ArrayList<CaseData>();
        try {
          //  Collections.addAll(l_new_caseData, new CaseData[l_caseData.size()]);
           // Collections.copy(l_new_caseData, l_caseData);
            l_new_caseData=copyList(l_view_shelf_show_caseData);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        List<CaseShelfData> l_caseShelfData = new ArrayList<CaseShelfData>();
        boolean isFine=false;
        CaseShelfData caseShelfData;
        for(int i=0;i<l_new_caseData.size();i++)
        {
           // if( l_new_caseData.get(i).caseDoItemCount == l_new_caseData.get(i).caseItemHasCount)
          //  {
                //已完成操作的不再显示
          //      continue;
          //  }
            caseShelfData=null;
            CaseAdjustmentData tempData=(CaseAdjustmentData)l_new_caseData.get(i);
            if(!tempData.lockerOldCode.trim().equals(""))
            {
                isFine=false;
                for(int j=0;j<l_caseShelfData.size();j++)
                {
                    if(l_caseShelfData.get(j).DID.equals(tempData.lockerOldCode.trim()))
                    {
                        caseShelfData=l_caseShelfData.get(j);
                        isFine=true;
                        break;
                    }
                }
                if(!isFine)
                {
                    caseShelfData =new CaseShelfData();
                    caseShelfData.DID =tempData.lockerOldCode.trim();
                    caseShelfData.lockerName = tempData.lockerOldName.trim();
                    caseShelfData.areaCode= l_new_caseData.get(i).areaCode.trim();
                    caseShelfData.areaName= l_new_caseData.get(i).areaName.trim();
                    caseShelfData.list_CaseData=new ArrayList<CaseData>();
                    l_caseShelfData.add(caseShelfData);
                }
            }
            if(caseShelfData!=null)
            {
                caseShelfData.list_CaseData.add(l_new_caseData.get(i));
            }

        }
        return l_caseShelfData;
    }

    //出库验证货架
    @Override
    public boolean checkShelfInStorage(String scanCode,CaseShelfData caseShelfData)
    {
        if(ViewState==4)
        {
            return checkShelfInStorageByOutType(scanCode,caseShelfData);
        }
        else    if(ViewState==5)
        {
            return checkShelfInStorageByInType(scanCode, caseShelfData);
        }
        return false;
    }

    //调整入逻辑  //入库不验证货架
    private  boolean checkShelfInStorageByInType(String scanCode,CaseShelfData caseShelfData)
    {
        return true;
    }

    //调整出逻辑  //出库验证货架
    private  boolean checkShelfInStorageByOutType(String scanCode,CaseShelfData caseShelfData)
    {
        if(l_view_shelf_show_caseData==null || scanCode==null)return false;

        for(int i=0;i<l_view_shelf_show_caseData.size();i++)
        {
            if(scanCode.trim().equals(((CaseAdjustmentData)(l_view_shelf_show_caseData.get(i))).lockerOldCode))
            {
                if(caseShelfData!=null)
                {
                    caseShelfData.DID = ((CaseAdjustmentData)(l_view_shelf_show_caseData.get(i))).lockerOldCode.trim();
                    caseShelfData.lockerName =((CaseAdjustmentData)(l_view_shelf_show_caseData.get(i))).lockerOldName.trim();
                    caseShelfData.list_CaseData=new ArrayList<CaseData>();
                }
                return true;
            }
        }
        return false;
    }

    //验证物品是否在单据中,子类需重写
    @Override
    public boolean checkItemInStorage(String scanCode,CaseData caseData)
    {
        if(ViewState==4)
        {
            return checkItemInStorageOutType(scanCode,caseData);
        }
        else    if(ViewState==5)
        {
            return checkItemInStorageInType(scanCode, caseData);
        }
        return false;
    }

    //调整入逻辑
    private boolean checkItemInStorageInType(String scanCode,CaseData caseData)
    {
        if (l_view_shelf_show_caseData == null) return false;

        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            if(scanCode.trim().equals(l_view_shelf_show_caseData.get(i).caseItemNumber))
            {
                if(caseData!=null)
                    caseData.copyData(l_view_shelf_show_caseData.get(i));
                return true;
            }
        }
        return false;
    }

    //调整出逻辑
    private boolean checkItemInStorageOutType(String scanCode,CaseData caseData)
    {
        if (l_view_shelf_show_caseData == null) return false;

        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            if(scanCode.trim().equals(l_view_shelf_show_caseData.get(i).caseItemNumber))
            {
                if(caseData!=null)
                    caseData.copyData(l_view_shelf_show_caseData.get(i));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkItemInSameArea(String scanCode, String shelfAreaCode, List<CaseData> allNewCaseItem, String[] outStr)
    {
        if(ViewState==4)
        {
            return checkItemInSomeAreaOutType(scanCode, shelfAreaCode, allNewCaseItem,outStr);
        }
        else    if(ViewState==5)
        {
            return checkItemInSomeAreaInType(scanCode, shelfAreaCode, allNewCaseItem,outStr);
        }
        return false;
    }


    public boolean checkItemInSomeAreaInType(String scanCode,String shelfAreaCode,List<CaseData> allNewCaseItem,String[] outStr)
    {
        if (l_view_shelf_show_caseData == null) return false;
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

        //调整不能改变区域-这个限制暂时不要了
        /*
        for (int i = 0; i < l_caseData.size(); i++) {
            //编码同,区域不同
            CaseAdjustmentData tempData=(CaseAdjustmentData)l_caseData.get(i);
            if(scanCode.trim().equals(tempData.caseItemNumber) && !shelfAreaCode.trim().equals(tempData.areaOldCode)
                    && tempData.areaOldCode!=null
                    &&!tempData.areaOldCode.equals(""))
            {
                if(tempStr.equals(""))tempStr+="物品应放在 "+tempData.areaOldName+"\r\n"+"调整时请不要更换物品保管区!";
                if(outStr!=null && outStr.length>0)
                {
                    outStr[0]=tempStr;
                }
                return false;
            }
        }*/
        //调整不不会分开物品，只验证新物品就行了
        /*
        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            //编码同,区域不同
            if(scanCode.trim().equals(l_view_shelf_show_caseData.get(i).caseItemNumber) && !shelfAreaCode.trim().equals(l_view_shelf_show_caseData.get(i).areaCode))
            {
                if(tempStr.equals(""))tempStr+="物品已放在"+l_view_shelf_show_caseData.get(i).areaCode+"\r\n"+"请将同类物品放入相同保管区!";
                if(outStr!=null && outStr.length>0)
                {
                    outStr[0]=tempStr;
                }
                return false;
            }
        }*/

        return true;
    }

    public boolean checkItemInSomeAreaOutType(String scanCode,String shelfAreaCode,List<CaseData> allNewCaseItem,String[] outStr)
    {

        return true;
    }


    @Override
    //验证货架是否可用（放置了同案件物品或者空柜子）
    //调整单
    public boolean checkInShelfInCaseStorage(String shelfCaseCode)
    {
        if(shelfCaseCode==null || shelfCaseCode.trim().equals(""))
        {
            //空架子
            return true;
        }
        for(int i=0;i<l_case_shelf.size();i++)
        {
            if(shelfCaseCode.trim().equals(l_case_shelf.get(i).caseCode.trim()))
            {
                return true;
            }
        }
        return false;
    }


    //验证物品是否在单据中货架上,子类需重写,注意实现时同一物品可能属于多货架,不能break
    @Override
    public boolean checkItemInShelfStorage(String scanCode,String shelfCode,CaseData caseData)
    {
        if(ViewState==4)
        {
            return checkItemInShelfStorageOutType(scanCode, shelfCode,caseData);
        }
        else    if(ViewState==5)
        {
            return checkItemInShelfStorageInType(scanCode, shelfCode,caseData);
        }
        return false;
    }
    private boolean checkItemInShelfStorageInType(String scanCode,String shelfCode,CaseData caseData)
    {
        return checkItemInStorage( scanCode, caseData);
    }
    private boolean checkItemInShelfStorageOutType(String scanCode,String shelfCode,CaseData caseData)
    {
        if (l_view_shelf_show_caseData == null) return false;

        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            CaseAdjustmentData tempData=(CaseAdjustmentData)l_view_shelf_show_caseData.get(i);
            if(scanCode.trim().equals(tempData.caseItemNumber) && shelfCode.trim().equals(tempData.lockerOldCode))
            {
                if(caseData!=null)
                    caseData.copyData(tempData);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean  removeTempItemByStorage(CaseData CaseData)
    {
        if(ViewState==4)
        {
            return false;
        }
        else    if(ViewState==5)
        {
            if(CaseData.DataType!=1)return false;
            //调整入的时候
            if(CaseData.caseDoItemCount>0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public double  findItemMaxChangeCount(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan)
    {
        if(ViewState==4)
        {
            return findItemMaxChangeCountOutType(scanItemCode, shelfCode, l_itemAllScan);
        }
        else    if(ViewState==5)
        {
            return findItemMaxChangeCountInType(scanItemCode, shelfCode, l_itemAllScan);
        }
        return -1;
    }

    public double  findItemMaxChangeCountOutType(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan)
    {
        if (l_view_shelf_show_caseData == null || l_itemAllScan==null||scanItemCode==null || shelfCode==null) return -1;
        String tempStr="";
        double itemAllScanCount=0;
        for (int i = 0; i < l_itemAllScan.size(); i++)
        {
            CaseAdjustmentData tempData=(CaseAdjustmentData)l_itemAllScan.get(i);
            if(scanItemCode.trim().equals(tempData.caseItemNumber) && shelfCode.trim().equals(tempData.lockerOldCode))
            {
                itemAllScanCount= myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
               // itemAllScanCount+=l_itemAllScan.get(i).scanCount;
            }
        }

        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            CaseAdjustmentData tempData=(CaseAdjustmentData)l_view_shelf_show_caseData.get(i);
            if(scanItemCode.trim().equals(tempData.caseItemNumber) && shelfCode.trim().equals(tempData.lockerOldCode))
            {
                double result= myArith.sub(tempData.caseItemHasCount, tempData.caseDoItemCount);
                result= myArith.sub(result, itemAllScanCount);
               // double result=tempData.caseItemHasCount-tempData.caseDoItemCount-itemAllScanCount;
                return result;
            }
        }
        return -1;
    }


    public double  findItemMaxChangeCountInType(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan)
    {
        if (l_in_caseData == null || l_itemAllScan==null||scanItemCode==null || shelfCode==null) return -1;

        double itemAllScanCount=0;
        for (int i = 0; i < l_itemAllScan.size(); i++)
        {
            // if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber) && shelfCode.trim().equals(l_itemAllScan.get(i).lockerCode))
            if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber)) {
                itemAllScanCount = myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
                //itemAllScanCount += l_itemAllScan.get(i).scanCount;
               // if (tempStr.equals("")) tempStr += "本次调整入\r\n";
            }
        }

        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            if(scanItemCode.trim().equals(l_view_shelf_show_caseData.get(i).caseItemNumber))
            {
                double result= myArith.sub(l_view_shelf_show_caseData.get(i).caseItemHasCount, l_view_shelf_show_caseData.get(i).caseDoItemCount);
                result= myArith.sub(result, itemAllScanCount);
                //double result=l_view_shelf_show_caseData.get(i).caseItemHasCount-l_view_shelf_show_caseData.get(i).caseDoItemCount-itemAllScanCount;
                return result;
            }
        }
        return -1;
    }

    //判断操作数量是否溢出(如 5个物品却扫了6个),子类需重写
    //itemAllScanCount 对应物品的总扫描数(如同一物品已放入货架A,B各一个,且这次扫了一个,这个值应为3)
    //false没溢出
    //调整出　所属货架上的物品的　库存-已操作数-扫描数＞=0
    //调整入　所属原货架上的这个物品　 库存/可操作数-已操作数-扫描数＞=0
    @Override
    public boolean   checkItemOperand(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan,String[] outStr)
    {
        if(ViewState==4)
        {
            return checkItemOperandOutType(scanItemCode, shelfCode, l_itemAllScan,outStr);
        }
        else    if(ViewState==5)
        {
            return checkItemOperandInType(scanItemCode, shelfCode, l_itemAllScan,outStr);
        }
        return false;
    }
    private boolean   checkItemOperandInType(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan,String[] outStr)
    {
        if (l_in_caseData == null || l_itemAllScan==null||scanItemCode==null || shelfCode==null) return true;
        String tempStr="";
        String tempStr1="";

        boolean isPassShelf=checkItemShelfOperand(scanItemCode,shelfCode,l_itemAllScan,outStr);
        if(!isPassShelf)
        {
            return !isPassShelf;
        }

        double itemAllScanCount=0;
        for (int i = 0; i < l_itemAllScan.size(); i++)
        {
           // if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber) && shelfCode.trim().equals(l_itemAllScan.get(i).lockerCode))
            if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber))
            {
                itemAllScanCount = myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
                //itemAllScanCount+=l_itemAllScan.get(i).scanCount;
                if(tempStr.equals(""))tempStr+="本次调整入\r\n";
                tempStr+="  "+l_itemAllScan.get(i).lockerName+" "+l_itemAllScan.get(i).getShowScanCount()+l_itemAllScan.get(i).unit+"\r\n";
            }
        }

        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            if(scanItemCode.trim().equals(l_view_shelf_show_caseData.get(i).caseItemNumber))
            {
                double result= myArith.sub(l_view_shelf_show_caseData.get(i).caseItemHasCount, l_view_shelf_show_caseData.get(i).caseDoItemCount);
                result= myArith.sub(result, itemAllScanCount);
               // double result=l_view_shelf_show_caseData.get(i).caseItemHasCount-l_view_shelf_show_caseData.get(i).caseDoItemCount-itemAllScanCount;
                tempStr1="需调整入"+l_view_shelf_show_caseData.get(i).getShowCaseItemHasCount()+l_view_shelf_show_caseData.get(i).unit+"\r\n";
               // tempStr1+="历史返还 "+l_in_caseData.get(i).lockerName+" "+l_in_caseData.get(i).caseDoItemCount+l_view_show_caseData.get(i).unit+"\r\n";

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

    //验证货架数量是否溢出,调整时新货架数量不能大于旧货架数量
    private boolean   checkItemShelfOperand(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan,String[] outStr)
    {
        int itemAllScanShelf=0;
        boolean isFind=false;
        int newShelfCount=0;
        int oldShelfCount=0;
        String tempStr="";

        List<String> allOldShelfCode=new ArrayList<String>();
        List<String> allNewShelfCode=new ArrayList<String>();

        //找新货架总数
        for (int i = 0; i < l_itemAllScan.size(); i++)
        {
            if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber))
            {
                isFind=false;
                for (int j = 0; j < allNewShelfCode.size(); j++)
                {

                    if(allNewShelfCode.get(j).equals(l_itemAllScan.get(i).lockerCode))
                    {
                        isFind=true;
                        break;
                    }
                }
                if(!isFind)
                {
                    //找没有的货架
                    allNewShelfCode.add(l_itemAllScan.get(i).lockerCode);
                }
            }
        }
        newShelfCount=allNewShelfCode.size();


        //如果扫描的在新货架已存在,不验证
        isFind=false;
        for (int j = 0; j < allNewShelfCode.size(); j++)
        {
            if(allNewShelfCode.get(j).equals(shelfCode))
            {
                return true;
            }
        }
        //找旧货架总数
        for (int i = 0; i < l_caseData.size(); i++)
        {
            if(scanItemCode.trim().equals(l_caseData.get(i).caseItemNumber))
            {
                CaseAdjustmentData tempData=(CaseAdjustmentData)l_caseData.get(i);
                isFind=false;
                for (int j = 0; j < allOldShelfCode.size(); j++)
                {

                    if(allOldShelfCode.get(j).equals(tempData.lockerOldCode))
                    {
                        isFind=true;
                        break;
                    }
                }
                if(!isFind)
                {
                    //找没有的货架
                    allOldShelfCode.add(tempData.lockerOldCode);
                }
            }
        }
        oldShelfCount=allOldShelfCode.size();


        //如果扫描货架是个新货架比较+1,如果已存在直接新旧比
        if(oldShelfCount<=newShelfCount)
        {
            tempStr="物品原放置于 "+oldShelfCount+" 个储物箱中\r\n"+"现被调整到 "+(newShelfCount+1)+" 个储物箱中\r\n"+"请遵守调整物品集中存放的原则!";
            if(outStr!=null && outStr.length>0)
            {
                outStr[0]=tempStr;
            }

            return false;
        }
        else
        {
            tempStr="物品原放置于 "+oldShelfCount+" 个储物箱中\r\n"+"现被调整到 "+(newShelfCount+1)+" 个储物箱中\r\n";
            if(outStr!=null && outStr.length>0)
            {
                outStr[0]=tempStr;
            }
            return true;
        }
    }

    private boolean   checkItemOperandOutType(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan,String[] outStr)
    {
        if (l_view_shelf_show_caseData == null || l_itemAllScan==null||scanItemCode==null || shelfCode==null) return true;
        String tempStr="";
        double itemAllScanCount=0;
        for (int i = 0; i < l_itemAllScan.size(); i++)
        {
            CaseAdjustmentData tempData=(CaseAdjustmentData)l_itemAllScan.get(i);
            if(scanItemCode.trim().equals(tempData.caseItemNumber) && shelfCode.trim().equals(tempData.lockerOldCode))
            {
                itemAllScanCount = myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
              //  itemAllScanCount+=l_itemAllScan.get(i).scanCount;
            }
        }

        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            CaseAdjustmentData tempData=(CaseAdjustmentData)l_view_shelf_show_caseData.get(i);
            if(scanItemCode.trim().equals(tempData.caseItemNumber) && shelfCode.trim().equals(tempData.lockerOldCode))
            {
                double result= myArith.sub(tempData.caseItemHasCount, tempData.caseDoItemCount);
                result= myArith.sub(result, itemAllScanCount);
               // double result=tempData.caseItemHasCount-tempData.caseDoItemCount-itemAllScanCount;
                tempStr+=tempData.lockerOldName+" 共 "+tempData.getShowCaseItemHasCount()+tempData.unit+"\r\n";
                tempStr+="已调整 "+tempData.caseDoItemCount+tempData.unit+"\r\n";
                tempStr+="本次调整 "+itemAllScanCount+tempData.unit+"\r\n";

                if(outStr!=null && outStr.length>0)
                {
                    outStr[0]=tempStr;
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
    //绑组验证物品,出库必全出，入库必全入
    //需在cleanUpdateItem前调用
    @Override
    public boolean  validateUpdateItem(List<CaseData> l_itemAllScan,CaseData noPassData,String[] outStr)
    {
        if(ViewState==4) {
            return  validateUpdateItemOutType(l_itemAllScan, noPassData,outStr);
        }
        else if(ViewState==5)
        {
            return  validateUpdateItemInType(l_itemAllScan, noPassData,outStr);
        }
        return false;
    }

    private boolean  validateUpdateItemOutType(List<CaseData> l_itemAllScan,CaseData noPassData,String[] outStr)
    {
        String tempStr="";
        for(int i=0;i<l_itemAllScan.size();i++)
        {
            CaseAdjustmentData tempData=(CaseAdjustmentData)l_itemAllScan.get(i);
            if(tempData.scanCount>0 && tempData.caseItemHasCount>tempData.scanCount)
            {
                if(noPassData!=null)
                {
                    noPassData.copyData(l_itemAllScan.get(i));
                    tempStr+="储物箱 "+tempData.lockerOldName+"\r\n";
                    tempStr+="原数量 "+tempData.getShowCaseItemHasCount()+tempData.unit+" 本次调整 "+tempData.getShowScanCount()+tempData.unit+"\r\n";
                    tempStr+="请集中调整物品!";
                    if(outStr!=null && outStr.length>0)
                    {
                        outStr[0]=tempStr;
                    }
                }
                return false;
            }
        }
        return true;
    }

    //验证是否全入了
    private boolean  validateUpdateItemInType(List<CaseData> l_itemAllScan,CaseData noPassData,String[] outStr)
    {
        double itemAllScanCount=0;
        String tempStr="";
        for (int i = 0; i < l_view_shelf_show_caseData.size(); i++) {
            itemAllScanCount=0;
            for (int j = 0; j < l_itemAllScan.size(); j++)
            {
                if(l_view_shelf_show_caseData.get(i).caseItemNumber.trim().equals(l_itemAllScan.get(j).caseItemNumber)) {
                    itemAllScanCount = myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
                   // itemAllScanCount += l_itemAllScan.get(j).scanCount;
                }
            }
            if(itemAllScanCount<l_view_shelf_show_caseData.get(i).caseItemHasCount)
            {
                noPassData=l_view_shelf_show_caseData.get(i).createCopyData(noPassData);
                tempStr+="调整出库 "+l_view_shelf_show_caseData.get(i).getShowCaseItemHasCount()+l_view_shelf_show_caseData.get(i).unit+"\r\n";
                tempStr+="调整入库 "+itemAllScanCount+l_view_shelf_show_caseData.get(i).unit+"\r\n";
                tempStr+="请核对物品数量!";
                if(outStr!=null && outStr.length>0)
                {
                    outStr[0]=tempStr;
                }
                return false;
            }
        }
        return true;
    }


    //整理提交前的数据
    //子类必须重写
    //主要处理操作采用物品为主的视角,后台以ID为准,例如 同一编码物品在不同货架,后台ID不同,但用户看到的物品没有区别
    //出库不需要整理,入库需整理
    @Override
    public List<CaseData>  cleanUpdateItem(List<CaseData> l_itemAllScan)
    {
        if(ViewState==4) {
            return  cleanUpdateItemOutType(l_itemAllScan);
        }
        else if(ViewState==5)
        {
            return  cleanUpdateItemInType(l_itemAllScan);
        }
        return null;
    }
    private List<CaseData>  cleanUpdateItemOutType(List<CaseData> l_itemAllScan)
    {
        if(l_in_caseData==null)
           l_in_caseData=new ArrayList<CaseData>();
        else
            l_in_caseData.clear();
        //把临时记录换成入库记录
        for(int i=0;i<l_itemAllScan.size();i++){
            CaseAdjustmentData cd=new CaseAdjustmentData();
           // cd.DID= l_itemAllScan.get(i).DID;

            l_itemAllScan.get(i).createCopyData(cd);
            cd.caseItemCountString=cd.getShowScanCount()+cd.unit;
            cd.caseItemHasCount=cd.scanCount;
            cd.caseDoItemCount=0;
            cd.scanCount=0;
            cd.ViewState=5;
            cd.DataType=-1;
            //cd.areaCode =this.areaCode;//存储区域//貌似调整不能换区域
           // cd.areaName =this.areaName;//存储区域
            cd.lockerCode ="";
            cd.lockerName ="";
            l_in_caseData.add(cd);
        }
        return l_in_caseData;
    }

    private List<CaseData>  cleanUpdateItemInType(List<CaseData> l_itemAllScan)
    {
        if(l_itemAllScan==null || l_in_caseData==null)return null;


        //找到新扫描的项中所有物品
        List<String> l_temp_caseData_code=  new  ArrayList<String>();
        boolean isfind=false;
        for(int i=0;i<l_itemAllScan.size();i++)
        {
            isfind=false;
            for(int j=0;j<l_temp_caseData_code.size();j++) {
                if (l_itemAllScan.get(i).caseItemNumber.equals(l_temp_caseData_code.get(j))) {
                    isfind = true;
                    break;
                }
            }
            if(!isfind)
            {
                l_temp_caseData_code.add(l_itemAllScan.get(i).caseItemNumber);
            }
        }

        if(l_temp_caseData_code.size()==0) return l_itemAllScan;
        List<CaseData> l_temp_source_caseData=null;
        List<CaseData> l_temp_new_caseData=null;
        List<CaseData> l_clean_caseData=new ArrayList<CaseData>();//最终结果
        double all_clean_count=0;//要塞的物品总个数
        double all_do_clean_count=0;//塞的物品总个数,最后完成要和总数对上
        CaseData source_caseData=null;
        CaseData new_caseData=null;
        CaseData new_copy_caseData=null;
        for(int i=0;i<l_temp_caseData_code.size();i++)
        {
            l_temp_source_caseData=new ArrayList<CaseData>();
            l_temp_new_caseData=new ArrayList<CaseData>();
            all_clean_count=0;
            all_do_clean_count=0;
            for(int j=0;j<l_in_caseData.size();j++)
            {
                if(l_in_caseData.get(j).caseItemNumber.equals(l_temp_caseData_code.get(i)))
                {
                    l_temp_source_caseData.add(l_in_caseData.get(j).createCopyData(new CaseData()));
                }
            }
            for(int j=0;j<l_itemAllScan.size();j++)
            {
                if(l_itemAllScan.get(j).caseItemNumber.equals(l_temp_caseData_code.get(i)))
                {
                    l_temp_new_caseData.add(l_itemAllScan.get(j).createCopyData(new CaseData()));
                    all_clean_count = myArith.add(all_clean_count, l_itemAllScan.get(i).scanCount);
                   // all_clean_count+=l_itemAllScan.get(j).scanCount;
                }
            }
            //塞数据
            source_caseData=null;
            new_caseData=null;
            new_copy_caseData=null;
            for(int j=0,k=0;j<l_temp_source_caseData.size();j++)
            {
                //有空塞数据
                source_caseData=l_temp_source_caseData.get(j);
                if(source_caseData.caseItemHasCount>source_caseData.caseDoItemCount)
                {
                    for(;k<l_temp_new_caseData.size();k++)
                    {
                        new_caseData=l_temp_new_caseData.get(k);
                        double tempdouble= myArith.sub(source_caseData.caseItemHasCount, source_caseData.caseDoItemCount);
                        tempdouble= myArith.sub(tempdouble, new_caseData.scanCount);
                      //  if(source_caseData.caseItemHasCount-source_caseData.caseDoItemCount-new_caseData.scanCount>=0)
                        if(tempdouble>=0)
                        {
                            new_caseData.DID=source_caseData.DID;
                            all_do_clean_count= myArith.add(all_do_clean_count, new_caseData.scanCount);
                           // all_do_clean_count+=new_caseData.scanCount;
                            source_caseData.caseDoItemCount= myArith.add(source_caseData.caseDoItemCount, new_caseData.scanCount);
                            //source_caseData.caseDoItemCount+=new_caseData.scanCount;
                        }
                        else
                        {
                            //不够只塞一部分,换下个原数据
                            new_copy_caseData=new_caseData.createCopyData(new CaseData());
                            double tempCount= myArith.sub(source_caseData.caseItemHasCount, source_caseData.caseDoItemCount);
                            //double tempCount=source_caseData.caseItemHasCount-source_caseData.caseDoItemCount;
                            new_caseData.DID=source_caseData.DID;
                            new_caseData.scanCount=tempCount;
                            source_caseData.caseDoItemCount= myArith.add(source_caseData.caseDoItemCount, new_caseData.scanCount);
                           // source_caseData.caseDoItemCount+=new_caseData.scanCount;
                            all_do_clean_count= myArith.add(all_do_clean_count, new_caseData.scanCount);
                           // all_do_clean_count+=new_caseData.scanCount;
                            new_copy_caseData.scanCount= myArith.sub(new_copy_caseData.scanCount, tempCount);
                           // new_copy_caseData.scanCount= new_copy_caseData.scanCount-tempCount;
                            l_temp_new_caseData.add(k+1,new_copy_caseData);
                            k++;
                            break;
                        }
                    }
                }
                else
                {
                    continue;
                }
            }
            //存在数据错误
            if(all_clean_count!=all_do_clean_count)
            {
                return null;
            }
            else
            {
                l_clean_caseData.addAll(l_temp_new_caseData);
            }

        }
        //把原数据的所有相关项取出来


      //  l_in_caseData=l_clean_caseData;
        return l_clean_caseData;
    }

    @Override
    protected List<CaseData> copyList(List<CaseData> l_source_caseData)
    {
        List<CaseData> l_caseData=new ArrayList<CaseData>();
        for(int i=0;i<l_source_caseData.size();i++)
        {
            CaseData c=l_source_caseData.get(i).createCopyData(new CaseAdjustmentData());
            l_caseData.add(c);
        }
        return l_caseData;

    }
}
