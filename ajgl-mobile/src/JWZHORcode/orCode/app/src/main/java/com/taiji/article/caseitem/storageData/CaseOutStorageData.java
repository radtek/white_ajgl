 package com.taiji.article.caseitem.storageData;

 import com.taiji.util.myArith;

 import java.util.ArrayList;
 import java.util.List;

 import orcode.pubsec.taiji.com.business.caseitem.CaseData;

 public class CaseOutStorageData extends CaseStorageData
 {

  public String outName="";//出库描述

  public CaseOutStorageData()
  {
   DataType=2;
  }

  @Override
  public List<CaseData> getList_caseData()
  {
   return l_caseData;
  }

  @Override
  //出库单排序,库存0的放在最后,库存不为0操作大于1的中间,库存不为0操作0的最前
  public void sortCaseData(List<CaseData> l_caseData) {
   if(l_caseData==null)return;
   CaseData tempData;
   int index0Number=0;//记录排序完最后一个操作0的位置,下个排序使用
   //先把操作数0的移动到前面
   for(int i=0;i<l_caseData.size();i++)
   {
    if(l_caseData.get(i).caseDoItemCount!=0)//不是0,往后扔
    {
     for (int j = i + 1; j < l_caseData.size(); j++) {
      if (l_caseData.get(j).caseDoItemCount == 0) {
       tempData = l_caseData.get(i);
       l_caseData.set(i, l_caseData.get(j));
       l_caseData.set(j, tempData);
       index0Number=i;
       break;
      }
     }
    }
   }

   //库存0的放后面
   for(int i=index0Number+1;i<l_caseData.size();i++)
   {
    double tempdouble= myArith.sub(l_caseData.get(i).caseItemHasCount, l_caseData.get(i).caseDoItemCount);
    if(tempdouble<=0)//是0,往后扔
    //if(l_caseData.get(i).caseItemHasCount-l_caseData.get(i).caseDoItemCount<=0)//是0,往后扔
    {
     for (int j = i + 1; j < l_caseData.size(); j++) {
      double tempdouble1= myArith.sub(l_caseData.get(j).caseItemHasCount, l_caseData.get(i).caseDoItemCount);
      if (tempdouble1 > 0)//是0,往后扔
     // if (l_caseData.get(j).caseItemHasCount-l_caseData.get(i).caseDoItemCount > 0)//是0,往后扔
      {
       tempData = l_caseData.get(i);
       l_caseData.set(i, l_caseData.get(j));
       l_caseData.set(j, tempData);
       break;
      }
     }
    }
   }
  }

  //根据物品创建货架结构物品集合,无货架物品不会加入,既入库前不显示
  @Override
  public List<CaseShelfData> createShelfData()
  {
   return  super.createShelfData();
  }

  //出库验证货架
  @Override
  public boolean checkShelfInStorage(String scanCode,CaseShelfData caseShelfData)
  {
   if(l_caseData==null || scanCode==null)return false;

   for(int i=0;i<l_caseData.size();i++)
   {
    if(scanCode.trim().equals(l_caseData.get(i).lockerCode))
    {
     if(caseShelfData!=null)
     {
      caseShelfData.DID = l_caseData.get(i).lockerCode.trim();
      caseShelfData.lockerName = l_caseData.get(i).lockerName.trim();
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
   if (l_caseData == null) return false;

   for (int i = 0; i < l_caseData.size(); i++) {
    if(scanCode.trim().equals(l_caseData.get(i).caseItemNumber))
    {
     if(caseData!=null)
      caseData.copyData(l_caseData.get(i));
     return true;
    }
   }
   return false;
  }

  //验证物品是否在单据中货架上,子类需重写,注意实现时同一物品可能属于多货架,不能break
  //入库不验证货架，在单据中就是true
  @Override
  public boolean checkItemInShelfStorage(String scanCode,String shelfCode,CaseData caseData)
  {
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
  }

  @Override
  //出库不删除原数据
  public boolean  removeTempItemByStorage(CaseData CaseData)
  {
   return false;
  }

  @Override
  public double  findItemMaxChangeCount(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan)
  {
   if (l_caseData == null || l_itemAllScan==null||scanItemCode==null || shelfCode==null) return -1;
   double itemAllScanCount=0;
   for (int i = 0; i < l_itemAllScan.size(); i++)
   {
    if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber) && shelfCode.trim().equals(l_itemAllScan.get(i).lockerCode))
    {
     itemAllScanCount= myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
    // itemAllScanCount+=l_itemAllScan.get(i).scanCount;
     // tempStr+=l_itemAllScan.get(i).lockerName+" 本次操作 "+l_itemAllScan.get(i).scanCount+l_itemAllScan.get(i).unit+"\r\n";
    }
   }

   for (int i = 0; i < l_caseData.size(); i++) {
    if(scanItemCode.trim().equals(l_caseData.get(i).caseItemNumber) && shelfCode.trim().equals(l_caseData.get(i).lockerCode))
    {
     double result= myArith.sub(l_caseData.get(i).caseItemHasCount, l_caseData.get(i).caseDoItemCount);
     result= myArith.sub(result, itemAllScanCount);
     //double result=l_caseData.get(i).caseItemHasCount-l_caseData.get(i).caseDoItemCount-itemAllScanCount;
     return result;
    }
   }
   return -1;
  }

  //判断操作数量是否溢出(如 5个物品却扫了6个),子类需重写
  //itemAllScanCount 对应物品的总扫描数(如同一物品已放入货架A,B各一个,且这次扫了一个,这个值应为3)
  //false没溢出
  //出库单　所属货架上的物品的　库存-已操作数-此货架上的扫描数＞=0
  @Override
  public boolean   checkItemOperand(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan,String[] outStr)
  {
   if (l_caseData == null || l_itemAllScan==null||scanItemCode==null || shelfCode==null) return true;
   String tempStr="";
   double itemAllScanCount=0;
   for (int i = 0; i < l_itemAllScan.size(); i++)
   {
      if(scanItemCode.trim().equals(l_itemAllScan.get(i).caseItemNumber) && shelfCode.trim().equals(l_itemAllScan.get(i).lockerCode))
      {
       itemAllScanCount= myArith.add(itemAllScanCount, l_itemAllScan.get(i).scanCount);
       //itemAllScanCount+=l_itemAllScan.get(i).scanCount;
      // tempStr+=l_itemAllScan.get(i).lockerName+" 本次操作 "+l_itemAllScan.get(i).scanCount+l_itemAllScan.get(i).unit+"\r\n";
      }
   }

   for (int i = 0; i < l_caseData.size(); i++) {
    if(scanItemCode.trim().equals(l_caseData.get(i).caseItemNumber) && shelfCode.trim().equals(l_caseData.get(i).lockerCode))
    {
     double result= myArith.sub(l_caseData.get(i).caseItemHasCount, l_caseData.get(i).caseDoItemCount);
     result= myArith.sub(result, itemAllScanCount);
     //double result=l_caseData.get(i).caseItemHasCount-l_caseData.get(i).caseDoItemCount-itemAllScanCount;
     tempStr+=l_caseData.get(i).lockerName+" 共 "+l_caseData.get(i).getShowCaseItemHasCount()+l_caseData.get(i).unit+"\r\n";
     tempStr+="历史出库　"+l_caseData.get(i).getShowCaseDoItemCount()+l_caseData.get(i).unit+"\r\n";
     tempStr+="本次出库　"+itemAllScanCount+l_caseData.get(i).unit+"\r\n";
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
  //checkItemOperand验证,不再验证
  @Override
  public boolean  validateUpdateItem(List<CaseData> l_itemAllScan,CaseData noPassData,String[] outStr)
  {
   return true;
  }

  //整理提交前的数据
  //子类必须重写
  //主要处理操作采用物品为主的视角,后台以ID为准,例如 同一编码物品在不同货架,后台ID不同,但用户看到的物品没有区别
  //出库不需要整理
  @Override
  public List<CaseData>  cleanUpdateItem(List<CaseData> l_itemAllScan)
  {
   return l_itemAllScan;
  }

 }
