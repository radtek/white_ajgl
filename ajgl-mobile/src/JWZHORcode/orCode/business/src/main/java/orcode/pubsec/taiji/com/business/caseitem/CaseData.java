 package orcode.pubsec.taiji.com.business.caseitem;

import java.io.Serializable;

 public class CaseData implements Serializable
{

    public CaseData()
    {

    }

    public String DID="";
    public String CItemID="";//由于后台数据结构导致的提交操作必须ID,同一物品多货架时的记录ID
    public String PID="";
    public String caeItemName="";
    public String earmark="";
    public String caseItemNumber="";//code
    public String caseItemCountString ="";//描述数量

    public String areaCode ="";//存储区域
    public String areaName ="";//存储区域
    public String caseCode ="";//所属案件

   // public String lockerOldCode ="";//货架位置
  //  public String lockerOldName ="";//货架位置
    public String lockerCode ="";//货架位置
    public String lockerName ="";//货架位置
    public double caseItemCount=0;//描述数量转换值(如 十一个 转换后保存 11)
    public String getShowCaseItemCount()
    {
        if(caseItemCount%1==0)
            return String.valueOf((int) caseItemCount);
        else
            return String.valueOf(caseItemCount);
    }

    public String unit ="";//单位
    public double caseItemHasCount=0;//基础数量,根据单据可能是库存数量或返还数量或(调整如时)调整出的数量
    public String getShowCaseItemHasCount()
    {
        if(caseItemHasCount%1==0)
            return String.valueOf((int) caseItemHasCount);
        else
            return String.valueOf(caseItemHasCount);
    }

    public double caseDoItemCount=0;//已操作数量
    public String getShowCaseDoItemCount()
    {
        if(caseDoItemCount%1==0)
            return String.valueOf((int) caseDoItemCount);
        else
            return String.valueOf(caseDoItemCount);
    }

    public String address="";
    public String operatePerson="";

    public int ViewState=-1;//表示单据展示类型/当前这个记录的业务单据是哪种，用于部分文字显示判断　//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
    public int DataType=-1;//1创建/新扫描到的  其他值暂无含义
    public double scanCount=0;//当上面状态为 新建 时,这个值才有意义,表示新建数量,不是本次操作的数量要保持0,可用于删除取消操作判断依据
    public String getShowScanCount()
    {
        if(scanCount%1==0)
            return String.valueOf((int) scanCount);
        else
            return String.valueOf(scanCount);
    }


    public int OrderPosition=0;

    public java.util.Date  DataCreateTime=new java.util.Date();
    public java.util.Date DataUpdateTime=new java.util.Date();
    public int uploadState=-1;//-1未提交,1提交成功 2提交失败

    public String getShowCaseItemNumber()
    {
        if(caseItemNumber!=null)
            return caseItemNumber.replaceAll("-","");
        else
            return "";
    }


    public String getDBCCaseItemNumber()
    {
        if(caseItemNumber!=null)
          return ToDBC(caseItemNumber);
        else
          return "";
    }

public void copyData(CaseData d)
{
    if(d==null)return;
    this.DID=d.DID;
    this.CItemID=d.CItemID;
    this.PID=d.PID;
    this.caeItemName=d.caeItemName;
    this.earmark=d.earmark;
    this.caseItemNumber=d.caseItemNumber;//code
    this.caseItemCountString =d.caseItemCountString;//描述数量
    this.areaCode =d.areaCode;//存储区域
    this.areaName =d.areaName;//存储区域
    this.caseCode =d.caseCode;
    this.lockerCode =d.lockerCode;//货架位置
    this.lockerName =d.lockerName;//货架位置
    this.caseItemCount=d.caseItemCount;//描述数量转换值
    this.caseItemHasCount=d.caseItemHasCount;//库存数量
    this.caseDoItemCount=d.caseDoItemCount;//已操作数量
    this.address=d.address;
    this.unit=d.unit;


    this.ViewState=d.ViewState;//表示单据展示类型/当前这个记录的业务单据是哪种，用于部分文字显示判断　//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
    this.DataType=d.DataType;//1创建/新扫描到的  其他值暂无含义
    this.scanCount=d.scanCount;//当上面状态为 新建 时,这个值才有意义,表示新建数量,不是本次操作的数量要保持0,可用于删除取消操作判断依据

    this.OrderPosition=d.OrderPosition;

    this.DataCreateTime=d.DataCreateTime;
    this.DataUpdateTime=d.DataUpdateTime;
    this.uploadState=d.uploadState;//-1未提交,1提交成功 2提交失败


}

    public CaseData createCopyData(CaseData d)
    {
        if(d==null)return d;
        d.DID=this.DID;
        d.CItemID=this.CItemID;
        d.PID=this.PID;
        d.caeItemName=this.caeItemName;
        d.earmark=this.earmark;
        d.caseItemNumber=this.caseItemNumber;//code
        d.caseItemCountString =this.caseItemCountString;//描述数量
        d.areaCode =this.areaCode;//存储区域
        d.areaName =this.areaName;//存储区域
        d.caseCode =this.caseCode;
        d.lockerCode =this.lockerCode;//货架位置
        d.lockerName =this.lockerName;//货架位置
        d.caseItemCount=this.caseItemCount;//描述数量转换值
        d.caseItemHasCount=this.caseItemHasCount;//库存数量
        d.caseDoItemCount=this.caseDoItemCount;//已操作数量
        d.address=this.address;
        d.unit=this.unit;

        d.ViewState=this.ViewState;//表示单据展示类型/当前这个记录的业务单据是哪种，用于部分文字显示判断　//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
        d.DataType=this.DataType;//1创建/新扫描到的  其他值暂无含义
        d.scanCount=this.scanCount;//当上面状态为 新建 时,这个值才有意义,表示新建数量,不是本次操作的数量要保持0,可用于删除取消操作判断依据

        d.OrderPosition=this.OrderPosition;

        d.DataCreateTime=this.DataCreateTime;
        d.DataUpdateTime=this.DataUpdateTime;
        d.uploadState=this.uploadState;//-1未提交,1提交成功 2提交失败
        return d;

    }

    public String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
