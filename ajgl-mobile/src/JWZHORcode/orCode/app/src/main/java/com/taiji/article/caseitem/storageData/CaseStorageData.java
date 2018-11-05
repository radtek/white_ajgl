 package com.taiji.article.caseitem.storageData;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;

 public class CaseStorageData implements Serializable
 {
     public String DID="";
     public String PID="";
     public String scanformCode="";//扫描号
     public String formCode="";//单号
     public String caseCode="";//案件编号
     public String caseName="";
     public String papers ="";//文书名称

     public List<CaseData> l_caseData=new ArrayList<CaseData>();
     public int ViewState=-1;//4调整出 5调整入 状态影响显示，验证逻辑等
     public int DataType=-1;//单据类型 1入 2出 3返还 4调整,7暂存入,8暂存出
     public int OrderPosition=0;

     public java.util.Date  DataCreateTime=new java.util.Date();
     public java.util.Date DataUpdateTime=new java.util.Date();
     public int uploadState=-1;//-1未提交,1提交成功 2提交失败

     public List<CaseData> getList_caseData()
     {
         return l_caseData;
     }

     public String changeDate(java.util.Date d)
     {
         try {
             SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             String timeStr = format.format(d);
             return  timeStr;
         }catch (Exception ex)
         {
             return  "";
         }

     }

     public int getDataType()
     {
         return DataType;
     }

     //返回单据的货架,目前只有暂存物品单据实现这个
     public String getShelfCode()
     {
         return "";
     }

     public String getShelfName()
     {
         return "";
     }
     //Collections.copy对象深拷贝没用,导致引用错,自己写
     protected List<CaseData> copyList(List<CaseData> l_source_caseData)
     {
         List<CaseData> l_caseData=new ArrayList<CaseData>();
         for(int i=0;i<l_source_caseData.size();i++)
         {
             CaseData c=l_source_caseData.get(i).createCopyData(new CaseData());
             l_caseData.add(c);
         }
         return l_caseData;

     }

     //排序物品,将操作数量为0的物品放在前面,有数量的物品尝试验证决定是否移到后面,子类需要可重写,默认实现针对入库单
     public void sortCaseData(List<CaseData> l_caseData)
     {
         if(l_caseData==null)return;
         CaseData tempData;
         int index0Number=-1;//记录排序完最后一个数量0的位置,下个排序使用,默认-1,解决如果没有0的,下个循环默认找+1的问题
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

         for(int i=index0Number+1;i<l_caseData.size();i++)
         {

             boolean isDoAll = tryCheckCaseDataIsDoAll(l_caseData.get(i));
             if(isDoAll)//全完往后扔
             {
                 for (int j = i + 1; j < l_caseData.size(); j++) {
                     isDoAll = tryCheckCaseDataIsDoAll(l_caseData.get(j));
                     if (!isDoAll) {
                         tempData = l_caseData.get(i);
                         l_caseData.set(i, l_caseData.get(j));
                         l_caseData.set(j, tempData);
                         break;
                     }
                 }
             }
         }
     }

     //验证货架是否在单据中,子类需重写,caseShelfData是输出参数,只赋值不new
     public boolean checkShelfInStorage(String scanCode,CaseShelfData caseShelfData)
     {
         return true;
     }

     //验证物品是否在单据中,子类需重写,caseData是输出参数,只赋值不new
     public boolean checkItemInStorage(String scanCode,CaseData caseData)
     {
         return true;
     }

     //验证物品是否在同一保管区,子类需重写
     //scanCode 扫到的物品码
     //shelfAreaCode选中架子区域
     //allNewCaseItem 列表里的新扫描数据
     //outStr 外面new好,只会写入一项,值为验证过程,可输出到客户端比如 可能是 "货架a 5个 货架b 5个 可操作数20",一般出现异常才用
     public boolean checkItemInSameArea(String scanCode, String shelfAreaCode, List<CaseData> allNewCaseItem, String[] outStr)
     {
         return true;
     }

     //验证货架是否可用（放置了同案件物品或者空柜子）
     public boolean checkInShelfInCaseStorage(String shelfCaseCode)
     {
        if(shelfCaseCode==null || shelfCaseCode.trim().equals(""))
        {
            //空架子
            return true;
        }
         if(shelfCaseCode.trim().equals(caseCode.trim()))
         {
             return true;
         }
         return false;
     }

     //验证物品是否在单据中货架上,子类需重写,注意实现时同一物品可能属于多货架,不能break,caseData是输出参数,只赋值不new
     public boolean checkItemInShelfStorage(String scanCode,String shelfCode,CaseData caseData)
     {
         return true;
     }

     //判断操作数量是否溢出(如 5个物品却扫了6个),子类需重写
     //scanItemCode 扫描物品码
     //shelfCode准备加入的货架
     //l_itemAllScan 所有新修改的物品集合
     //false没溢出
     //outStr 外面new好,只会写入一项,值为验证过程,可输出到客户端比如 可能是 "货架a 5个 货架b 5个 可操作数20",一般出现数量溢出才会用到
     public boolean  checkItemOperand(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan,String[] outStr)
     {
         return false;
     }

     //整体(提交数据前)验证是否符合提交标准,子类必须重写,否则不能通过验证
     //多数单据已由checkItemOperand扫描物品时验证,可以直接返回true(或者再验证一次),调整单存在绑组操作必须作后验证
     //noPassData作为输出参数,外面需要new好
     public boolean  validateUpdateItem(List<CaseData> l_itemAllScan,CaseData noPassData,String[] outStr)
     {
         return false;
     }


     //整理提交前的数据
     //子类必须重写
     //主要处理操作采用物品为主的视角,后台以ID为准,例如 同一编码物品在不同货架,后台ID不同,但用户看到的物品没有区别
     public List<CaseData>  cleanUpdateItem(List<CaseData> l_itemAllScan)
     {
         return null;
     }

     //根据物品创建货架结构物品集合,无货架物品不会加入,既入库前不显示
     public List<CaseShelfData> createShelfData()
     {
         if(l_caseData==null)return null;
         List<CaseData> l_new_caseData=  new  ArrayList<CaseData>();
         Collections.addAll(l_new_caseData, new CaseData[l_caseData.size()]);
         Collections.copy(l_new_caseData, l_caseData);
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


     //尝试判断操作数量已经达到文本数据描述的数量，如 五个 和 5 之间的判断
     //达到ture,无法确认或者未达到false
     private boolean tryCheckCaseDataIsDoAll(CaseData caseData)
     {
         if(caseData.caseItemCount==0)//没有转化过是０
         {
             double resultNumber =chn2digit(caseData.caseItemCountString);
             caseData.caseItemCount=resultNumber;//无法转换是-1,
         }

         if(caseData.caseItemCount==-1)
         {
             return false;
         }
         if(caseData.caseDoItemCount>=caseData.caseItemCount)
         {
             return true;
         }
         return false;
     }

     //根据单据判断临时操作项是删除,还是移出临时操作状态 true删除 false时修改状态(比如入库判断库存数量0则删,出库永不删)
     public boolean  removeTempItemByStorage(CaseData CaseData)
     {
         return false;
     }

     //查找可操作数量，例：要入库15个，架子A入库１个，架子B入库2个,返回12
     //子类实现
     public double  findItemMaxChangeCount(String scanItemCode,String shelfCode,List<CaseData> l_itemAllScan)
     {
         return -1;
     }


     static java.util.Map<String, Integer> unitMap = new java.util.HashMap<String, Integer>();
     static java.util.Map<String, Integer> numMap = new java.util.HashMap<String, Integer>();
     static {
         unitMap = new java.util.HashMap<String, Integer>();
         numMap = new java.util.HashMap<String, Integer>();
        // unitMap.put("元", 0);
         unitMap.put("十", 10);
         unitMap.put("拾", 10);
         unitMap.put("百", 100);
         unitMap.put("佰", 100);
         unitMap.put("千", 1000);
         unitMap.put("仟", 1000);
         unitMap.put("万", 10000);
         unitMap.put("亿", 100000000);

         numMap.put("零", 0);

         numMap.put("一", 1);
         numMap.put("壹", 1);

         numMap.put("二", 2);
         numMap.put("贰", 2);
         numMap.put("两", 2);
         numMap.put("俩", 2);

         numMap.put("三", 3);
         numMap.put("叁", 3);

         numMap.put("四", 4);
         numMap.put("肆", 4);

         numMap.put("五", 5);
         numMap.put("伍", 5);

         numMap.put("六", 6);
         numMap.put("陆", 6);

         numMap.put("七", 7);
         numMap.put("柒", 7);

         numMap.put("八", 8);
         numMap.put("捌", 8);

         numMap.put("九", 9);
         numMap.put("玖", 9);
     }
     //不限制自串,无视负数,只取最前面的,可以放 "大概十个或十一个",输出应为10;"大概10个或11个",输出应为10
     public  double chn2digit(String chnStr) {
         try {
             if(chnStr.trim().equals(""))
             {
                 return -1;
             }
             int resultNumber =getNumberInStr(chnStr);
             if(resultNumber==-1 ||resultNumber==0)
             {

             }
             else
             {
                 return resultNumber;
             }

             //队列
             List<Integer> queue = new ArrayList<Integer>();
             int tempNum = 0;
             boolean isStartNum=false;
            // boolean isEndNum=false;;
             for (int i = 0; i < chnStr.length(); i++) {
                 char bit = chnStr.charAt(i);
                 System.out.print(bit);
                 //数字
                 if (numMap.containsKey(bit + "")) {
                     isStartNum=true;
                     tempNum = tempNum + numMap.get(bit + "");

                     //一位数、末位数、亿或万的前一位进队列
                     if (chnStr.length() == 1
                             | i == chnStr.length() - 1
                             | (i + 1 < chnStr.length() && (chnStr.charAt(i + 1) == '亿' | chnStr
                             .charAt(i + 1) == '万'))) {
                         queue.add(tempNum);
                     }
                 }
                 //单位
                 else if (unitMap.containsKey(bit + "")) {
                     isStartNum=true;
                     //遇到十 转换为一十、临时变量进队列
                     if (bit == '十') {
                         if (tempNum != 0) {
                             tempNum = tempNum * unitMap.get(bit + "");
                         } else {
                             tempNum = 1 * unitMap.get(bit + "");
                         }
                         queue.add(tempNum);
                         tempNum = 0;
                     }

                     //遇到千、百 临时变量进队列
                     if (bit == '千' | bit == '百') {
                         if (tempNum != 0) {
                             tempNum = tempNum * unitMap.get(bit + "");
                         }
                         queue.add(tempNum);
                         tempNum = 0;
                     }

                     //遇到亿、万 队列中各元素依次累加*单位值、清空队列、新结果值进队列
                     if (bit == '亿' | bit == '万') {
                         int tempSum = 0;
                         if (queue.size() != 0) {
                             for (int j = 0; j < queue.size(); j++) {
                                 tempSum += queue.get(j);
                             }
                         } else {
                             tempSum = 1;
                         }
                         tempNum = tempSum * unitMap.get(bit + "");
                         queue.clear();//清空队列
                         queue.add(tempNum);//新结果值进队列
                         tempNum = 0;
                     }
                 }
                 else
                 {
                     //已经读取数字后读到不是数字则完成
                     if (isStartNum) {
                         queue.add(tempNum);
                         break;
                     }
                 }
             }
             int sum = 0;
             for (Integer i : queue) {
                 sum += i;
             }
             return sum;
         }catch (Exception ex)
         {
             //数字很大可能抱错,暂时不认为有大数字
             return -1;
         }
     }

     //"大概10个或11个",输出应为10
     public int  getNumberInStr( String chnStr) {
         try {
             //chnStr = "love23next234csdn3423javaeye";
             chnStr = chnStr.trim();
             String str2 = "";
             boolean isStartNum = false;
             //  boolean isEndNum=false;;
             if (chnStr != null && !"".equals(chnStr)) {
                 for (int i = 0; i < chnStr.length(); i++) {
                     if (chnStr.charAt(i) >= 48 && chnStr.charAt(i) <= 57) {
                         isStartNum = true;
                         str2 += chnStr.charAt(i);
                     } else {
                         //已经读取数字后读到不是数字则完成
                         if (isStartNum) {
                             break;
                         }
                     }
                 }

             }
             if (str2.equals("")) {
                 return -1;
             } else {
                 return Integer.valueOf(str2);
             }
         }catch (Exception ex)
         {
             return -1;
         }
     }


 }
