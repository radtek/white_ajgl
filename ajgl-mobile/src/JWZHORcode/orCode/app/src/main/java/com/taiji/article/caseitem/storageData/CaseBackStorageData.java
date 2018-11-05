package com.taiji.article.caseitem.storageData;

import com.taiji.util.myArith;

import java.util.ArrayList;
import java.util.List;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;

public class CaseBackStorageData extends CaseStorageData
{

 public String sourceStorageCode="";//原始单据编码
 public String outName="";//出库描述
 //合并相同物品
 public List<CaseData> l_view_show_caseData=new ArrayList<CaseData>();

    @Override
    public List<CaseData> getList_caseData()
    {
        return l_caseData;
    }

 public CaseBackStorageData()
 {
  DataType=3;
 }

 @Override
 public void sortCaseData(List<CaseData> l_caseData) {
  l_view_show_caseData=  new  ArrayList<CaseData>();
 // Collections.addAll(l_view_show_caseData, new CaseData[l_caseData.size()]);
 // Collections.copy(l_view_show_caseData, l_caseData);
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
                    // tempData.caseDoItemCount = tempData.caseDoItemCount + tempData2.caseDoItemCount;
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
        //不是一条原记录时合并要返还数量
        if(!tempData.DID.equals(tempData2.DID)&&!tempData.DID.equals(""))
        {
            tempData.caseItemHasCount= myArith.add(tempData.caseItemHasCount, tempData2.caseItemHasCount);
           // tempData.caseItemHasCount = tempData.caseItemHasCount + tempData2.caseItemHasCount;
            tempData.caseItemCountString= tempData.getShowCaseItemHasCount()+ tempData.unit;
        }
     if(tempData.lockerCode!=tempData2.lockerCode)
     {
      tempData.lockerCode=tempData.lockerCode+","+tempData2.lockerCode;
      tempData.lockerName=tempData.lockerName+","+tempData2.lockerName;
     }
    // tempData=null;
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
                if(scanCode.trim().equals(allNewCaseItem.get(i).caseItemNumber)
                        && !shelfAreaCode.trim().equals(allNewCaseItem.get(i).areaCode)
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
 //入库不验证货架，在单据中就是true
 @Override
 public boolean checkItemInShelfStorage(String scanCode,String shelfCode,CaseData caseData)
 {
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
              //  itemAllScanCount+=l_itemAllScan.get(i).scanCount;
              //  if(tempStr.equals(""))tempStr+="本次返还\r\n";
               // tempStr+=l_itemAllScan.get(i).lockerName+" "+l_itemAllScan.get(i).scanCount+l_itemAllScan.get(i).unit+"\r\n";
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
 //返还单　单据中的这个物品　需返还数-已操作数-总扫描数＞=0
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
  //  itemAllScanCount+=l_itemAllScan.get(i).scanCount;
       if(tempStr.equals(""))tempStr+="本次返还\r\n";
       tempStr+="  "+l_itemAllScan.get(i).lockerName+" "+l_itemAllScan.get(i).scanCount+l_itemAllScan.get(i).unit+"\r\n";
   }
  }

  for (int i = 0; i < l_view_show_caseData.size(); i++) {
   if(scanItemCode.trim().equals(l_view_show_caseData.get(i).caseItemNumber))
   {
       double result= myArith.sub(l_view_show_caseData.get(i).caseItemHasCount, l_view_show_caseData.get(i).caseDoItemCount);
       result= myArith.sub(result, itemAllScanCount);
       //double result=l_view_show_caseData.get(i).caseItemHasCount-l_view_show_caseData.get(i).caseDoItemCount-itemAllScanCount;

       tempStr1="需返还  "+l_view_show_caseData.get(i).getShowCaseItemHasCount()+l_view_show_caseData.get(i).unit+"\r\n";
       String[] tempLockerCodeGroup=l_view_show_caseData.get(i).lockerCode.split(",");
       if(tempLockerCodeGroup.length>1)
       {
           tempStr1+="历史返还\r\n"+l_view_show_caseData.get(i).lockerName+" 共 "+l_view_show_caseData.get(i).caseDoItemCount+l_view_show_caseData.get(i).unit+"\r\n";
       }
       else  if(l_view_show_caseData.get(i).caseDoItemCount==0)
       {
           tempStr1+="历史返还"+"  "+l_view_show_caseData.get(i).lockerName+" "+l_view_show_caseData.get(i).caseDoItemCount+l_view_show_caseData.get(i).unit+"\r\n";
       }
       else
       {
           tempStr1+="历史返还\r\n"+"  "+l_view_show_caseData.get(i).lockerName+" "+l_view_show_caseData.get(i).caseDoItemCount+l_view_show_caseData.get(i).unit+"\r\n";
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
 //checkItemOperand验证,不再验证
 @Override
 public boolean  validateUpdateItem(List<CaseData> l_itemAllScan,CaseData noPassData,String[] outStr)
 {
  return true;
 }

 //整理提交前的数据
 //子类必须重写
 //主要处理操作采用物品为主的视角,后台以ID为准,例如 同一编码物品在不同货架,后台ID不同,但用户看到的物品没有区别
 //需整理
 @Override
 public List<CaseData>  cleanUpdateItem(List<CaseData> l_itemAllScan)
 {
  if(l_itemAllScan==null || l_caseData==null)return null;


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
      for(int j=0;j<l_caseData.size();j++)
      {
          if(l_caseData.get(j).caseItemNumber.equals(l_temp_caseData_code.get(i)))
          {
              l_temp_source_caseData.add(l_caseData.get(j).createCopyData(new CaseData()));
          }
      }
     for(int j=0;j<l_itemAllScan.size();j++)
     {
        if(l_itemAllScan.get(j).caseItemNumber.equals(l_temp_caseData_code.get(i)))
        {
           l_temp_new_caseData.add(l_itemAllScan.get(j).createCopyData(new CaseData()));
            all_clean_count= myArith.add(all_clean_count, l_itemAllScan.get(j).scanCount);
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
               if(tempdouble>=0)
             // if(source_caseData.caseItemHasCount-source_caseData.caseDoItemCount-new_caseData.scanCount>=0)
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
                  //source_caseData.caseItemHasCount-source_caseData.caseDoItemCount;
                 new_caseData.DID=source_caseData.DID;
                 new_caseData.scanCount=tempCount;
                  source_caseData.caseDoItemCount= myArith.add(source_caseData.caseDoItemCount, new_caseData.scanCount);
                 //source_caseData.caseDoItemCount+=new_caseData.scanCount;
                  all_do_clean_count= myArith.add(all_do_clean_count, new_caseData.scanCount);
                // all_do_clean_count+=new_caseData.scanCount;
                  new_copy_caseData.scanCount= myArith.sub(new_copy_caseData.scanCount, tempCount);
                 //new_copy_caseData.scanCount= new_copy_caseData.scanCount-tempCount;
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



  return l_clean_caseData;
 }

}
