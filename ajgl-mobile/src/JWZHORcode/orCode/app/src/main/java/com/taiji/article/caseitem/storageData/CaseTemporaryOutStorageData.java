 package com.taiji.article.caseitem.storageData;

 import java.util.ArrayList;
 import java.util.List;

 import orcode.pubsec.taiji.com.business.caseitem.CaseData;

 public class CaseTemporaryOutStorageData extends CaseStorageData
 {

  public String outName="";//出库描述
  public String shelfCode="";//所在货架
  public String shelfName="";//所在货架
  public String areaName="";//所在区
  public String areaCode="";//所在区
  public String state="";//0未出库 1出库
  public String suspectName ="";//嫌疑人姓名
  public CaseTemporaryOutStorageData()
  {
   DataType=8;
  }

  @Override
  public String getShelfCode()
  {
     return shelfCode;
  }

  @Override
  public String getShelfName()
  {
   return shelfName;
  }


  @Override
  public List<CaseData> getList_caseData()
  {
   return l_caseData;
  }

  @Override
  //出库单排序,库存0的放在最后,库存不为0操作大于1的中间,库存不为0操作0的最前
  public void sortCaseData(List<CaseData> l_caseData) {

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
   if(shelfCode.equals(scanCode))
   {
    caseShelfData.DID=scanCode;
    caseShelfData.lockerName=shelfName;
    return true;
   }
   return false;
  }

  //验证物品是否在单据中,子类需重写
  @Override
  public boolean checkItemInStorage(String scanCode,CaseData caseData)
  {
   return true;
  }

  //验证物品是否在单据中货架上,子类需重写,注意实现时同一物品可能属于多货架,不能break
  //入库不验证货架，在单据中就是true
  @Override
  public boolean checkItemInShelfStorage(String scanCode,String shelfCode,CaseData caseData)
  {
        return true;
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
   return -1;
  }

  //判断操作数量是否溢出(如 5个物品却扫了6个),子类需重写
  //itemAllScanCount 对应物品的总扫描数(如同一物品已放入货架A,B各一个,且这次扫了一个,这个值应为3)
  //false没溢出
  //出库单　所属货架上的物品的　库存-已操作数-此货架上的扫描数＞=0
  @Override
  public boolean   checkItemOperand(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan,String[] outStr)
  {
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
