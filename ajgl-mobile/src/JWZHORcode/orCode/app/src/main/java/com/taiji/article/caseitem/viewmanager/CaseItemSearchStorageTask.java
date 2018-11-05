package com.taiji.article.caseitem.viewmanager;

import android.content.Context;
import android.os.AsyncTask;


import com.taiji.article.caseitem.storageData.CaseAdjustmentData;
import com.taiji.article.caseitem.storageData.CaseAdjustmentStorageData;
import com.taiji.article.caseitem.storageData.CaseBackStorageData;
import com.taiji.article.caseitem.storageData.CaseInStorageData;
import com.taiji.article.caseitem.storageData.CaseOutStorageData;
import com.taiji.article.caseitem.storageData.CaseShelfData;
import com.taiji.article.caseitem.storageData.CaseStorageData;
import com.taiji.article.caseitem.storageData.CaseTemporaryInStorageData;
import com.taiji.article.caseitem.storageData.CaseTemporaryOutStorageData;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.MyTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import orcode.pubsec.taiji.com.business.caseitem.CaseItemBusiness;
import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;

/**
 * Created by z0 on 2016/3/21.
 */
public class CaseItemSearchStorageTask extends AsyncTask<Integer,Integer, String> {
    private Context context;
    private String progressId;
    protected static boolean isRefresh=false;
    protected static CaseItemSearchStorageTask  caseItemSearchStorageTask=null;
    protected static Object refreshLock=new Object();

    CaseStorageData caseStorageData;
   //List<CaseData> l_caseData=new  ArrayList<CaseData>();


    public CaseItemSearchStorageTask(Context context, String progressId, CaseStorageData caseStorageData) {
        this.context = context;
        this.progressId = progressId;
        this.caseStorageData=caseStorageData;
        caseItemSearchStorageTask=this;
        init();
    }

    public  void init()
    {
        if(refreshLock==null) {
            refreshLock = new Object();
        }

    }

    public static boolean getIsRefresh()
    {
        return isRefresh;
    }

    public static void doCancel()
    {
        try {
            if (caseItemSearchStorageTask != null)
                caseItemSearchStorageTask.cancel(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            isRefresh=false;
            caseItemSearchStorageTask=null;
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Integer... params) {
        if(context==null)
        {
            return "-10";//没有context
        }
        String resultCode="-1";//未知错误
        if(isRefresh)
        {
            return "-5";//正在刷新
        }
        isRefresh=true;
        if(caseStorageData==null||caseStorageData.DID==null||caseStorageData.DID.equals(""))
        {
            return "-1";
        }
        else if(caseStorageData.DataType>8 || caseStorageData.DataType<0)
        {
            return "-1";
        }

        try {

            JSONObject jo;
            if(caseStorageData.DataType==1)
            {
                jo = CaseItemBusiness.findStorage(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachInStorage),
                        MyTools.getDeviceId(context),
                        caseStorageData.formCode,
                        LoginBusiness.loginPersonID);

               // String json= "{\"adjustmentStorageItemBeanList\":[],\"ajustmentFormBean\":{\"formCode\":null,\"items\":[],\"lockers\":[],\"operateTime\":null,\"reason\":null},\"ajustmentFormItemBeans\":[],\"articleCode\":null,\"articleLockerCode\":null,\"backStorageItemBeanList\":[],\"flag\":null,\"formBean\":{\"caseCode\":\"A5201142100002016030007\",\"caseName\":\"A5201142100002016030007\",\"formCode\":\"RK2017030002\",\"items\":[{\"areaCode\":\"\",\"areaName\":\"\",\"articleCode\":\"WPD52011421000020160300042\",\"articleName\":\"iPhone手机\",\"backStorageRecordBeans\":[],\"feature\":\"银色，型号：iPhone6plus\",\"inStorageRecordBeans\":[],\"itemId\":\"7084081c-bd64-441f-aa62-72e7c13249ee\",\"measureUnit\":\"部\",\"numDesc\":0,\"numberReturned\":0,\"operatePerson\":null,\"operationNumber\":0,\"operations\":[],\"requestNumber\":1,\"storageBeans\":[],\"updatedTime\":1489656316000},{\"areaCode\":\"\",\"areaName\":\"\",\"articleCode\":\"WPD52011421000020160300041\",\"articleName\":\"冰毒疑似物\",\"backStorageRecordBeans\":[],\"feature\":\"白色晶体，净重：7.7克\",\"inStorageRecordBeans\":[],\"itemId\":\"392c2454-b587-4b03-878c-d93573f40dd3\",\"measureUnit\":\"包\",\"numDesc\":0,\"numberReturned\":0,\"operatePerson\":null,\"operationNumber\":0,\"operations\":[],\"requestNumber\":9999999999,\"storageBeans\":[],\"updatedTime\":1489656316000}],\"operateTime\":1489656316000,\"outItems\":[],\"outStorageFormCode\":null,\"papers\":\"筑经开公（禁）扣 字[2016]9号\",\"suspectName\":\"何亚丽\",\"type\":null},\"formCode\":\"RK2017030002\",\"formItemBeans\":[],\"inStorageItemBeanList\":[],\"invovledArticleBean\":null,\"items\":[],\"map\":{},\"outStorageItemBeanList\":[]}";
                //jo=new JSONObject(json);

                if(jo==null){
                    return "-1";
                }
                try
                {

                    JSONObject jo2=(JSONObject)jo.get("formBean");
                    if(jo2==null){
                        return "-1";
                    }
                    resultCode = readInStorageData(jo2, caseStorageData);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return "-1";
                }
            }
            else if(caseStorageData.DataType==2)
            {
                jo = CaseItemBusiness.findStorage(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachOutStorage),
                        MyTools.getDeviceId(context),
                        caseStorageData.formCode,
                        LoginBusiness.loginPersonID);
             //   String json= "{\"adjustmentStorageItemBeanList\":[],\"ajustmentFormBean\":{\"formCode\":null,\"items\":[],\"lockers\":[],\"operateTime\":null,\"reason\":null},\"ajustmentFormItemBeans\":[],\"articleCode\":null,\"articleLockerCode\":null,\"backStorageItemBeanList\":[],\"flag\":null,\"formBean\":{\"caseCode\":\"A5201142100002016030007\",\"caseName\":\"A5201142100002016030007\",\"formCode\":\"CK2017030029\",\"items\":[],\"operateTime\":1490064000000,\"outItems\":[{\"areaCode\":\"001\",\"areaName\":\"保管区1\",\"articleCode\":\"WPD52011421000020160300042\",\"articleId\":null,\"articleName\":\"iPhone手机\",\"feature\":\"银色，型号：iPhone6plus\",\"itemId\":\"4f022517-0679-4d92-9a7e-18f32e0b0aa2\",\"lockerCode\":\"10002\",\"lockerName\":\"2\",\"measureUnit\":\"部\",\"numberInStorage\":5,\"outcomingNumber\":1}],\"outStorageFormCode\":null,\"papers\":\"刑事、行政案件提醒表中数据说明2.bmp\",\"suspectName\":null,\"type\":\"借出\"},\"formCode\":\"CK2017030029\",\"formItemBeans\":[],\"inStorageItemBeanList\":[],\"invovledArticleBean\":null,\"items\":[],\"map\":{},\"outStorageItemBeanList\":[]}";
             //   jo=new JSONObject(json);
                if(jo==null){
                    return "-1";
                }
                try
                {

                    JSONObject jo2=(JSONObject)jo.get("formBean");
                    if(jo2==null){
                        return "-1";
                    }
                    resultCode = readOutStorageData(jo2, caseStorageData);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return "-1";
                }
            }
            else if(caseStorageData.DataType==3)
            {
                jo = CaseItemBusiness.findStorage(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachBackStorage),
                        MyTools.getDeviceId(context),
                        caseStorageData.formCode,
                        LoginBusiness.loginPersonID);
                if(jo==null){
                    return "-1";
                }
                try
                {
                    JSONObject jo2=(JSONObject)jo.get("formBean");
                    if(jo2==null){
                        return "-1";
                    }
                    resultCode = readBackStorageData(jo2, caseStorageData);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return "-1";
                }
            }
            else if(caseStorageData.DataType==4)
            {
                jo = CaseItemBusiness.findStorage(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachAdjustmentStorage),
                        MyTools.getDeviceId(context),
                        caseStorageData.formCode,
                        LoginBusiness.loginPersonID);
                //String json="{\"adjustmentStorageItemBeanList\":[],\"ajustmentFormBean\":{\"formCode\":\"TZ2017030002\",\"items\":[{\"adjustmentStorageRecordBeans\":[],\"areaCode\":\"1\",\"areaName\":\"1\",\"articleCode\":\"WPD52011421000020160300042\",\"articleName\":\"iPhone手机\",\"caseCode\":\"A5201142100002016030007\",\"feature\":\"银色，型号：iPhone6plus\",\"itemId\":\"6d6faf23-10fd-40be-b764-864277719554\",\"lockerCode\":\"10002\",\"lockerName\":\"2\",\"measureUnit\":\"部\",\"numDesc\":6,\"operatePerson\":null,\"updatedTime\":1489851318000},{\"adjustmentStorageRecordBeans\":[],\"areaCode\":\"1\",\"areaName\":\"1\",\"articleCode\":\"WPD52011421000020160300041\",\"articleName\":\"冰毒疑似物\",\"caseCode\":\"A5201142100002016030007\",\"feature\":\"白色晶体，净重：7.7克\",\"itemId\":\"44a72f2b-b63e-46ea-89a2-9aaee3ba3c15\",\"lockerCode\":\"10002\",\"lockerName\":\"2\",\"measureUnit\":\"包\",\"numDesc\":5,\"operatePerson\":null,\"updatedTime\":1489851318000}],\"lockers\":[{\"caseCode\":\"A5201142100002016030007\",\"lockerCode\":\"10002\",\"lockerName\":\"2\"}],\"operateTime\":1489851318000,\"reason\":\"ff\"},\"ajustmentFormItemBeans\":[],\"articleCode\":null,\"articleLockerCode\":null,\"backStorageItemBeanList\":[],\"flag\":null,\"formBean\":{\"caseCode\":null,\"caseName\":null,\"formCode\":null,\"items\":[],\"operateTime\":null,\"outItems\":[],\"outStorageFormCode\":null,\"papers\":null,\"suspectName\":null,\"type\":null},\"formCode\":\"TZ2017030002\",\"formItemBeans\":[],\"inStorageItemBeanList\":[],\"invovledArticleBean\":null,\"items\":[],\"map\":{},\"outStorageItemBeanList\":[]}";
              //  String json="{\"adjustmentStorageItemBeanList\":[],\"ajustmentFormBean\":{\"formCode\":\"TZ2017030012\",\"items\":[{\"adjustmentStorageRecordBeans\":[{\"adjustNumber\":1,\"id\":\"b83eafed-e774-4937-9f8b-fd2dac667632\",\"lockerCode\":\"12340001\",\"lockerName\":\"1\"}],\"areaCode\":\"123\",\"areaName\":\"测试保管区\",\"articleCode\":\"WPD52011421000020150700011\",\"articleName\":\"毒品疑似物\",\"caseCode\":\"A5201142100002015070001\",\"feature\":\"白色晶体，净重0.6克\",\"itemId\":\"c9ce0c28-f3af-43cd-b170-82ed0f277d3b\",\"lockerCode\":\"1230002\",\"lockerName\":\"第二个\",\"measureUnit\":\"包\",\"numDesc\":1,\"operatePerson\":null,\"updatedTime\":1490099477000}],\"lockers\":[{\"caseCode\":\"A5201142100002015070001\",\"lockerCode\":\"1230001\",\"lockerName\":\"第一个\"}],\"operateTime\":1490099477000,\"reason\":\"调整看看\"},\"ajustmentFormItemBeans\":[],\"articleCode\":null,\"articleLockerCode\":null,\"backStorageItemBeanList\":[],\"flag\":null,\"formBean\":{\"caseCode\":null,\"caseName\":null,\"formCode\":null,\"items\":[],\"operateTime\":null,\"outItems\":[],\"outStorageFormCode\":null,\"papers\":null,\"suspectName\":null,\"type\":null},\"formCode\":\"TZ2017030012\",\"formItemBeans\":[],\"inStorageItemBeanList\":[],\"invovledArticleBean\":null,\"items\":[],\"map\":{},\"outStorageItemBeanList\":[]}";
              //  jo=new JSONObject(json);


                if(jo==null){
                    return "-1";
                }
                try
                {
                    JSONObject jo2=(JSONObject)jo.get("ajustmentFormBean");
                    if(jo2==null){
                        return "-1";
                    }
                    resultCode = readAdjustmentStorageData(jo2, caseStorageData);

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return "-1";
                }
            }
            else if(caseStorageData.DataType==7)
            {
                jo = CaseItemBusiness.findTemporaryInStorage(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachTemporaryInAdjustment),
                        MyTools.getDeviceId(context),
                        caseStorageData.formCode,
                        LoginBusiness.loginPersonID);
                if(jo==null){
                    return "-1";
                }
                try
                {

                    JSONObject jo2=(JSONObject)jo.get("tempStorageInInfoBean");
                    if(jo2==null){
                        return "-1";
                    }
                    resultCode = readTemporaryInStorageData(jo2, caseStorageData);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return "-1";
                }
            }
            else if(caseStorageData.DataType==8)
            {
                jo = CaseItemBusiness.findTemporaryOutStorage(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachTemporaryOutAdjustment),
                        MyTools.getDeviceId(context),
                        caseStorageData.formCode,
                        LoginBusiness.loginPersonID);
                if(jo==null){
                    return "-1";
                }
                try
                {
                    JSONObject jo2=(JSONObject)jo.get("tempStorageInInfoBean");
                    if(jo2==null){
                        return "-1";
                    }
                    resultCode = readTemporaryOutStorageData(jo2, caseStorageData);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return "-1";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }


        return resultCode;
    }

    private String readInStorageData(JSONObject jo, CaseStorageData caseStorageData)
    {
        if(jo==null || caseStorageData==null)
        {
            return "-1";
        }
        CaseInStorageData tempData=(CaseInStorageData)caseStorageData;
        try {
            tempData.DID=jo.getString("formCode").toUpperCase();
            tempData.caseCode=jo.getString("caseCode");
            tempData.caseName=jo.getString("caseName");
            tempData.suspectName=jo.getString("suspectName");
            if(!jo.isNull("papers")) tempData.papers=StringFilter(jo.getString("papers"));
            tempData.DataCreateTime =getTimeStr(jo.getString("operateTime"), "yyyy-MM-dd HH:mm:ss");
            tempData.DataUpdateTime = tempData.DataCreateTime;

            JSONArray jIfbl = (JSONArray) jo.get("items");
            if(tempData.l_caseData==null)
            {
                tempData.l_caseData=new ArrayList<CaseData>();
            }
            CaseData rd=null;
            CaseData rd1=null;
            for (int j = 0; j < jIfbl.length(); j++)
            {
                JSONArray jOperate= (JSONArray)((JSONObject)jIfbl.get(j)).get("inStorageRecordBeans");
                if(jOperate==null || jOperate.length()==0)
                {
                    try {
                        rd=new CaseData();
                        rd.ViewState=1;
                        JSONObject itemDada = (JSONObject) jIfbl.get(j);
                        rd.DID=itemDada.getString("itemId");
                        rd.CItemID="";//itemDada.getString("itemId");
                        rd.caseCode=jo.getString("caseCode");
                        rd.caeItemName=itemDada.getString("articleName");
                        rd.caseItemNumber=itemDada.getString("articleCode").toUpperCase();
                        if(!itemDada.isNull("feature"))rd.earmark=itemDada.getString("feature");
                        rd.caseItemHasCount = itemDada.getDouble("requestNumber");//itemDada.getInt("numDesc");
                        if(!itemDada.isNull("measureUnit"))rd.unit = itemDada.getString("measureUnit");
                        rd.caseItemCountString= rd.getShowCaseItemHasCount()+rd.unit;
                        if(!itemDada.isNull("areaCode"))rd.areaCode = itemDada.getString("areaCode");
                        if(!itemDada.isNull("areaName"))rd.areaName = itemDada.getString("areaName");
                        //  rd.lockerName = itemDada.getString("lockerName");
                        //  rd.lockerCode = itemDada.getString("lockerCode");
                        rd.caseDoItemCount = itemDada.getDouble("operationNumber");
                        rd.operatePerson = itemDada.getString("operatePerson");
                        rd.DataCreateTime =getTimeStr(itemDada.getString("updatedTime"), "yyyy-MM-dd HH:mm:ss");
                        rd.DataUpdateTime = tempData.DataCreateTime;
                        if(rd!=null)
                            tempData.l_caseData.add(rd);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    //多信息拆分多条
                    for (int k = 0; k< jOperate.length(); k++)
                    {
                        try {
                            rd=new CaseData();
                            rd.ViewState=1;
                            JSONObject itemDada = (JSONObject) jIfbl.get(j);
                            rd.DID=itemDada.getString("itemId");
                            rd.CItemID=((JSONObject)jOperate.get(k)).getString("id");
                            rd.caseCode=jo.getString("caseCode");
                            rd.caeItemName=itemDada.getString("articleName");
                            rd.caseItemNumber=itemDada.getString("articleCode").toUpperCase();
                            if(!itemDada.isNull("feature"))rd.earmark=itemDada.getString("feature");
                            rd.caseItemHasCount = itemDada.getDouble("requestNumber");// itemDada.getInt("numDesc");
                            if(!itemDada.isNull("measureUnit")) rd.unit = itemDada.getString("measureUnit");
                            rd.caseItemCountString= rd.getShowCaseItemHasCount()+rd.unit;
                            if(!itemDada.isNull("areaCode"))rd.areaCode = itemDada.getString("areaCode");
                            if(!itemDada.isNull("areaName"))rd.areaName = itemDada.getString("areaName");
                            if(!((JSONObject)jOperate.get(k)).isNull("lockerName"))rd.lockerName = ((JSONObject)jOperate.get(k)).getString("lockerName");
                            if(!((JSONObject)jOperate.get(k)).isNull("lockerCode"))rd.lockerCode = ((JSONObject)jOperate.get(k)).getString("lockerCode");
                            rd.caseDoItemCount = ((JSONObject)jOperate.get(k)).getDouble("incomingNumber");
                            rd.operatePerson = itemDada.getString("operatePerson");
                            rd.DataCreateTime =getTimeStr(itemDada.getString("updatedTime"), "yyyy-MM-dd HH:mm:ss");
                            rd.DataUpdateTime = tempData.DataCreateTime;
                            if(rd!=null)
                                tempData.l_caseData.add(rd);
                        }catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "-1";
        }
        return "1";
    }



    private String readOutStorageData(JSONObject jo, CaseStorageData caseStorageData)
    {
        if(jo==null || caseStorageData==null)
        {
            return "-1";
        }
        CaseOutStorageData tempData=(CaseOutStorageData)caseStorageData;
        try {
            tempData.DID=jo.getString("formCode");
            tempData.caseCode=jo.getString("caseCode");
            tempData.caseName=jo.getString("caseName");
            tempData.outName=jo.getString("type");
            if(!jo.isNull("papers"))tempData.papers=StringFilter(jo.getString("papers"));
            tempData.DataCreateTime =getTimeStr(jo.getString("operateTime"), "yyyy-MM-dd HH:mm:ss");
            tempData.DataUpdateTime = tempData.DataCreateTime;

            JSONArray jIfbl = (JSONArray) jo.get("outItems");
            if(tempData.l_caseData==null)
            {
                tempData.l_caseData=new ArrayList<CaseData>();
            }
            CaseData rd=null;
            CaseData rd1=null;
            for (int j = 0; j < jIfbl.length(); j++)
            {
               // JSONArray jOperate= (JSONArray) jo.get("Operate");
                try {
                    rd=new CaseData();
                    rd.ViewState=2;
                    JSONObject itemDada = (JSONObject) jIfbl.get(j);
                    rd.DID=itemDada.getString("itemId");
                    rd.CItemID="";//((JSONObject)jOperate.get(k)).getString("id");
                    rd.caseCode=jo.getString("caseCode");
                    rd.caeItemName=itemDada.getString("articleName");
                    rd.caseItemNumber=itemDada.getString("articleCode").toUpperCase();
                    if(!itemDada.isNull("feature"))rd.earmark=itemDada.getString("feature");
                    rd.caseItemHasCount = itemDada.getDouble("numberInStorage");// itemDada.getInt("numDesc");//
                    if(!itemDada.isNull("measureUnit"))
                      rd.unit = itemDada.getString("measureUnit");
                    rd.caseItemCountString= rd.getShowCaseItemHasCount()+rd.unit;
                   // if(!itemDada.isNull("areaCode"))
                   //   rd.areaCode = itemDada.getString("areaCode");
                   // if(!itemDada.isNull("areaName"))
                   //   rd.areaName = itemDada.getString("areaName");
                    if(!itemDada.isNull("lockerName"))
                      rd.lockerName = itemDada.getString("lockerName");
                    if(!itemDada.isNull("lockerCode"))
                      rd.lockerCode = itemDada.getString("lockerCode");
                    rd.caseDoItemCount = itemDada.getDouble("outcomingNumber");
                    //rd.operatePerson = itemDada.getString("operatePerson");
                    //rd.DataCreateTime =getTimeStr(itemDada.getString("updatedTime"), "yyyy-MM-dd HH:mm:ss");
                    rd.DataUpdateTime = tempData.DataCreateTime;
                    if(rd!=null)
                        tempData.l_caseData.add(rd);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "-1";
        }
        return "1";
    }

    private String readBackStorageData(JSONObject jo, CaseStorageData caseStorageData)
    {
        if(jo==null || caseStorageData==null)
        {
            return "-1";
        }
        CaseBackStorageData tempData=(CaseBackStorageData)caseStorageData;
        try {
            tempData.DID=jo.getString("formCode");
            tempData.caseCode=jo.getString("caseCode");
            tempData.caseName=jo.getString("caseName");
            tempData.sourceStorageCode=jo.getString("outStorageFormCode");
            if(!jo.isNull("type"))tempData.outName=jo.getString("type");
            //tempData.papers=jo.getString("papers");
            tempData.DataCreateTime =getTimeStr(jo.getString("operateTime"), "yyyy-MM-dd HH:mm:ss");
            tempData.DataUpdateTime = tempData.DataCreateTime;

            JSONArray jIfbl = (JSONArray) jo.get("items");
            if(tempData.l_caseData==null)
            {
                tempData.l_caseData=new ArrayList<CaseData>();
            }
            CaseData rd=null;
            CaseData rd1=null;
            for (int j = 0; j < jIfbl.length(); j++)
            {
                JSONArray jOperate= (JSONArray)((JSONObject)jIfbl.get(j)).get("backStorageRecordBeans");
                if(jOperate==null || jOperate.length()==0)
                {
                    try {
                        rd=new CaseData();
                        rd.ViewState=3;
                        JSONObject itemDada = (JSONObject) jIfbl.get(j);
                        rd.DID=itemDada.getString("itemId");
                        rd.CItemID="";//((JSONObject)jOperate.get(k)).getString("id");
                        rd.caseCode=jo.getString("caseCode");
                        rd.caeItemName=itemDada.getString("articleName");
                        rd.caseItemNumber=itemDada.getString("articleCode").toUpperCase();
                        if(!itemDada.isNull("feature"))rd.earmark=itemDada.getString("feature");
                        rd.caseItemHasCount = itemDada.getDouble("requestNumber");// itemDada.getInt("numDesc");//
                        if(!itemDada.isNull("measureUnit")) rd.unit = itemDada.getString("measureUnit");
                        rd.caseItemCountString= rd.getShowCaseItemHasCount()+rd.unit;
                        if(!itemDada.isNull("areaCode"))rd.areaCode = itemDada.getString("areaCode");
                        if(!itemDada.isNull("areaName")) rd.areaName = itemDada.getString("areaName");
                        //  rd.lockerName = itemDada.getString("lockerName");
                        //  rd.lockerCode = itemDada.getString("lockerCode");
                        rd.caseDoItemCount = itemDada.getDouble("operationNumber");
                        rd.operatePerson = itemDada.getString("operatePerson");
                        rd.DataCreateTime =getTimeStr(itemDada.getString("updatedTime"), "yyyy-MM-dd HH:mm:ss");
                        rd.DataUpdateTime = tempData.DataCreateTime;
                        if(rd!=null)
                            tempData.l_caseData.add(rd);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    //多信息拆分多条
                    for (int k = 0; k< jOperate.length(); k++)
                    {
                        try {
                            rd=new CaseData();
                            rd.ViewState=3;
                            JSONObject itemDada = (JSONObject) jIfbl.get(j);
                            rd.DID=itemDada.getString("itemId");
                            rd.CItemID=((JSONObject)jOperate.get(k)).getString("id");
                            rd.caseCode=jo.getString("caseCode");
                            rd.caeItemName=itemDada.getString("articleName");
                            rd.caseItemNumber=itemDada.getString("articleCode").toUpperCase();
                            if(!itemDada.isNull("feature"))rd.earmark=itemDada.getString("feature");
                            rd.caseItemHasCount = itemDada.getDouble("requestNumber");// itemDada.getInt("numDesc");//
                            if(!itemDada.isNull("measureUnit"))rd.unit = itemDada.getString("measureUnit");
                            rd.caseItemCountString= rd.getShowCaseItemHasCount()+rd.unit;
                            if(!itemDada.isNull("areaCode"))rd.areaCode = itemDada.getString("areaCode");
                            if(!itemDada.isNull("areaName")) rd.areaName = itemDada.getString("areaName");
                            if(!((JSONObject)jOperate.get(k)).isNull("lockerName"))rd.lockerName = ((JSONObject)jOperate.get(k)).getString("lockerName");
                            if(!((JSONObject)jOperate.get(k)).isNull("lockerCode"))rd.lockerCode = ((JSONObject)jOperate.get(k)).getString("lockerCode");
                            rd.caseDoItemCount = ((JSONObject)jOperate.get(k)).getDouble("returnedNumber");
                            rd.operatePerson = itemDada.getString("operatePerson");
                            rd.DataCreateTime =getTimeStr(itemDada.getString("updatedTime"), "yyyy-MM-dd HH:mm:ss");
                            rd.DataUpdateTime = tempData.DataCreateTime;
                            if(rd!=null)
                                tempData.l_caseData.add(rd);
                        }catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "-1";
        }
        return "1";
    }

    private String readAdjustmentStorageData(JSONObject jo, CaseStorageData caseStorageData)
    {
        if(jo==null || caseStorageData==null)
        {
            return "-1";
        }
        CaseAdjustmentStorageData tempData=(CaseAdjustmentStorageData)caseStorageData;
        try {
            tempData.DID=jo.getString("formCode");
            tempData.ViewState=4;
           // tempData.caseCode=jo.getString("caseCode");
            //tempData.caseName=jo.getString("caseName");
            if(!jo.isNull("reason"))tempData.cause=jo.getString("reason");
            //tempData.papers=jo.getString("papers");
            tempData.DataCreateTime =getTimeStr(jo.getString("operateTime"), "yyyy-MM-dd HH:mm:ss");
            tempData.DataUpdateTime = tempData.DataCreateTime;

            JSONArray jIfbl = (JSONArray) jo.get("items");
            if(tempData.l_caseData==null)
            {
                tempData.l_caseData=new ArrayList<CaseData>();
            }
            CaseAdjustmentData rd=null;
            CaseAdjustmentData rd1=null;
            for (int j = 0; j < jIfbl.length(); j++)
            {
                JSONArray jOperate= (JSONArray)((JSONObject)jIfbl.get(j)).get("adjustmentStorageRecordBeans");
                if(jOperate==null|| jOperate.length()==0)
                {
                    try {
                        rd=new CaseAdjustmentData();
                        rd.ViewState=4;
                        JSONObject itemDada = (JSONObject) jIfbl.get(j);
                        rd.DID=itemDada.getString("itemId");
                        rd.CItemID="";//((JSONObject)jOperate.get(k)).getString("id");
                        rd.caseCode=itemDada.getString("caseCode");
                        rd.caeItemName=itemDada.getString("articleName");
                        rd.caseItemNumber=itemDada.getString("articleCode").toUpperCase();
                        if(!itemDada.isNull("feature"))rd.earmark=itemDada.getString("feature");
                        rd.caseItemHasCount = itemDada.getDouble("numDesc");//itemDada.getInt("adjustNumber");//
                        if(!itemDada.isNull("measureUnit"))rd.unit = itemDada.getString("measureUnit");
                        rd.caseItemCountString= rd.getShowCaseItemHasCount()+rd.unit;
                        if(!itemDada.isNull("areaCode"))rd.areaCode =  itemDada.getString("areaCode");//调整必须在同区
                        if(!itemDada.isNull("areaName"))rd.areaName = itemDada.getString("areaName");
                        if(!itemDada.isNull("areaCode"))rd.areaOldCode = itemDada.getString("areaCode");
                        if(!itemDada.isNull("areaName"))rd.areaOldName = itemDada.getString("areaName");
                        if(!itemDada.isNull("lockerName"))rd.lockerOldName = itemDada.getString("lockerName");
                        if(!itemDada.isNull("lockerCode"))rd.lockerOldCode = itemDada.getString("lockerCode");
                        //rd.caseDoItemCount = itemDada.getInt("operationNumber");
                        //rd.lockerName = itemDada.getString("lockerName");
                        //rd.lockerCode = itemDada.getString("lockerCode");
                        rd.caseDoItemCount = 0;//itemDada.getInt("count");//"operationNumber");
                                rd.operatePerson = itemDada.getString("operatePerson");
                        rd.DataCreateTime =getTimeStr(itemDada.getString("updatedTime"), "yyyy-MM-dd HH:mm:ss");
                        rd.DataUpdateTime = tempData.DataCreateTime;
                        if(rd!=null)
                            tempData.l_caseData.add(rd);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    //多信息拆分多条
                    for (int k = 0; k< jOperate.length(); k++)
                    {
                        try {
                            rd=new CaseAdjustmentData();
                            rd.ViewState=4;
                            JSONObject itemDada = (JSONObject) jIfbl.get(j);
                            rd.DID=itemDada.getString("itemId");
                            rd.CItemID=((JSONObject)jOperate.get(k)).getString("id");
                            rd.caseCode=itemDada.getString("caseCode");
                            rd.caeItemName=itemDada.getString("articleName");
                            rd.caseItemNumber=itemDada.getString("articleCode").toUpperCase();
                            if(!itemDada.isNull("feature"))rd.earmark=itemDada.getString("feature");
                            rd.caseItemHasCount = itemDada.getDouble("numDesc");//itemDada.getInt("adjustNumber");//
                            if(!itemDada.isNull("measureUnit"))rd.unit = itemDada.getString("measureUnit");
                            rd.caseItemCountString= rd.getShowCaseItemHasCount()+rd.unit;
                            if(!itemDada.isNull("areaCode"))rd.areaOldCode = itemDada.getString("areaCode");
                            if(!itemDada.isNull("areaName"))rd.areaOldName = itemDada.getString("areaName");
                            if(!itemDada.isNull("areaCode"))rd.areaCode =  itemDada.getString("areaCode");//itemDada.getString("areaCode");//((JSONObject)jOperate.get(k)).getString("areaCode");
                            if(!itemDada.isNull("areaName"))rd.areaName = itemDada.getString("areaName");
                            if(!itemDada.isNull("lockerName"))rd.lockerOldName = itemDada.getString("lockerName");
                            if(!itemDada.isNull("lockerCode"))rd.lockerOldCode = itemDada.getString("lockerCode");
                            if(!((JSONObject)jOperate.get(k)).isNull("lockerName"))rd.lockerName = ((JSONObject) jOperate.get(k)).getString("lockerName");
                            if(!((JSONObject)jOperate.get(k)).isNull("lockerCode"))rd.lockerCode = ((JSONObject)jOperate.get(k)).getString("lockerCode");
                            rd.caseDoItemCount = ((JSONObject)jOperate.get(k)).getDouble("adjustNumber");
                            rd.operatePerson = itemDada.getString("operatePerson");
                            rd.DataCreateTime =getTimeStr(itemDada.getString("updatedTime"), "yyyy-MM-dd HH:mm:ss");
                            rd.DataUpdateTime = tempData.DataCreateTime;
                            if(rd!=null)
                                tempData.l_caseData.add(rd);
                        }catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            //读取包含案件的所有货架
            JSONArray jlockers = (JSONArray) jo.get("lockers");
            if(tempData.l_case_shelf==null)
            {
                tempData.l_case_shelf=new ArrayList<CaseShelfData>();
            }
            CaseShelfData csd=null;
            for (int j = 0; j < jlockers.length(); j++)
            {
                // JSONArray jOperate= (JSONArray) jo.get("Operate");
                try {
                    csd=new CaseShelfData();
                    JSONObject itemDada = (JSONObject) jlockers.get(j);
                    csd.DID=itemDada.getString("lockerCode");
                    csd.caseCode=itemDada.getString("caseCode");
                    csd.lockerName=itemDada.getString("lockerName");
                    if(rd!=null)
                        tempData.l_case_shelf.add(csd);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                    return "-1";
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "-1";
        }
        return "1";
    }

    private String readTemporaryInStorageData(JSONObject jo, CaseStorageData caseStorageData)
    {
        if(jo==null || caseStorageData==null)
        {
            return "-1";
        }
        CaseTemporaryInStorageData tempData=(CaseTemporaryInStorageData)caseStorageData;
        try {
            tempData.DID=jo.getString("storageInCode").toUpperCase();
            tempData.DataCreateTime =getTimeStr(jo.getString("storageInDateTime"), "yyyy-MM-dd HH:mm:ss");
            tempData.DataUpdateTime = tempData.DataCreateTime;
            if(!jo.isNull("caseCode"))tempData.caseCode=jo.getString("caseCode");
            if(!jo.isNull("caseName"))tempData.caseName=jo.getString("caseName");
            if(!jo.isNull("objectOwnerPerson"))tempData.suspectName=jo.getString("objectOwnerPerson");
            JSONArray ja1=jo.getJSONArray("inSaveSelfList");
            if(ja1!=null)
            {
                try {
                    JSONObject tempObject=(JSONObject) (ja1.get(0));
                    if(!tempObject.isNull("address"))tempData.shelfName = tempObject.getString("address");
                    if(!tempObject.isNull("code"))tempData.shelfCode = tempObject.getString("code");
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                try {
                    JSONObject tempObject= ((JSONObject) (ja1.get(0))).getJSONObject("areaBean");
                    if(!tempObject.isNull("name"))tempData.areaName = tempObject.getString("name");
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            JSONArray ja2=jo.getJSONArray("tempObjectBeanList");
            if(ja2!=null)
            {
                CaseData rd=null;
                for (int j = 0; j < ja2.length(); j++)
                {
                    JSONObject itemDada=(JSONObject) (ja2.get(j));
                    rd=new CaseData();
                    rd.ViewState=1;
                    if(!itemDada.isNull("objectName"))rd.caeItemName=itemDada.getString("objectName");
                    if(!itemDada.isNull("objectCharacter"))rd.earmark=itemDada.getString("objectCharacter");
                    if(!itemDada.isNull("inThisNum")) rd.caseItemNumber=itemDada.getString("inThisNum");
                    if(!itemDada.isNull("measureUnit"))rd.unit = itemDada.getString("measureUnit");
                    rd.caseItemCountString= rd.getShowCaseItemHasCount()+rd.unit;
                    tempData.l_caseData.add(rd);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "-1";
        }
        return "1";
    }


    private String readTemporaryOutStorageData(JSONObject jo, CaseStorageData caseStorageData) {
        if (jo == null || caseStorageData == null) {
            return "-1";
        }
        CaseTemporaryOutStorageData tempData = (CaseTemporaryOutStorageData) caseStorageData;
        try {
            tempData.DID = jo.getString("storageInCode").toUpperCase();
            tempData.DataCreateTime = getTimeStr(jo.getString("storageInDateTime"), "yyyy-MM-dd HH:mm:ss");
            tempData.DataUpdateTime = tempData.DataCreateTime;
            if(!jo.isNull("caseCode"))tempData.caseCode = jo.getString("caseCode");
            if(!jo.isNull("caseName"))tempData.caseName = jo.getString("caseName");
            if(!jo.isNull("objectOwnerPerson"))tempData.suspectName=jo.getString("objectOwnerPerson");
            if(!jo.isNull("storageOutStatus"))
            {
                String tempStr= jo.getString("storageOutStatus");
                tempData.state = tempStr.equals("是")?"1":"0";//jo.getString("storageOutStatus");
            }
            else
            {
                tempData.state = "0";
            }

            JSONArray ja1 = jo.getJSONArray("inSaveSelfList");
            if (ja1 != null) {
                try {
                    JSONObject tempObject = (JSONObject) (ja1.get(0));
                    if (!tempObject.isNull("address"))
                        tempData.shelfName = tempObject.getString("address");
                    if (!tempObject.isNull("code"))
                        tempData.shelfCode = tempObject.getString("code");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
                    JSONObject tempObject = ((JSONObject) (ja1.get(0))).getJSONObject("areaBean");
                    if (!tempObject.isNull("name"))
                        tempData.areaName = tempObject.getString("name");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            JSONArray ja2 = jo.getJSONArray("tempObjectBeanList");
            if (ja2 != null) {
                CaseData rd = null;
                for (int j = 0; j < ja2.length(); j++) {
                    JSONObject itemDada = (JSONObject) (ja2.get(j));
                    rd = new CaseData();
                    rd.ViewState = 1;
                    if (!itemDada.isNull("objectName"))
                        rd.caeItemName = itemDada.getString("objectName");
                    if (!itemDada.isNull("objectCharacter"))
                        rd.earmark = itemDada.getString("objectCharacter");
                    if (!itemDada.isNull("inThisNum"))
                        rd.caseItemNumber = itemDada.getString("inThisNum");
                    if (!itemDada.isNull("measureUnit"))
                        rd.unit = itemDada.getString("measureUnit");
                    rd.caseItemCountString = rd.getShowCaseItemHasCount() + rd.unit;
                    tempData.l_caseData.add(rd);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "-1";
        }
        return "1";
    }

    protected void onPostExecute(String result) {

        isRefresh=false;
        String resultCode=result;
        if(completeBack!=null)
        {
            completeBack.completeBack(progressId, resultCode,caseStorageData);

        }

    }

    // 替换、过滤()字符
    private  String StringFilter(String str) throws PatternSyntaxException {
        //str=str.replaceAll("【","[").replaceAll("】","]").replaceAll("！","!");//替换中文标号
        //String regEx="[『』]"; // 清除掉特殊字符
        //Pattern p = Pattern.compile(regEx);
       // Matcher m = p.matcher(str);
        //return m.replaceAll("").trim();
        if(str==null)return "";
        return  str.replace("（", "(").replace("）", ")");
    }

    private Date getTimeStr(String time,String fmortStr)
    {
        Date date =null;
        try {
            long createdTime = Long.parseLong(time);
            date = new Date(createdTime);
            return date;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            String createdTime =time;
            String[] tempStrGroup = createdTime.split("T");
            createdTime = tempStrGroup[0] + " " + tempStrGroup[1];
            SimpleDateFormat sdf = new SimpleDateFormat(fmortStr);
            date = sdf.parse(createdTime);
            return date;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            String createdTime =time;
            SimpleDateFormat sdf = new SimpleDateFormat(fmortStr);
            date = sdf.parse(createdTime);
            return date;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;

    }


    @Override
    protected void onCancelled() {
        System.out.println("cancel");
    }

    public interface completeListener
    {
        public void completeBack(String progressId, String result,CaseStorageData caseStorageData);
    }

    private completeListener completeBack;
    public void setCompleteListener(completeListener l) {
        completeBack = l;
    }

}

