package com.taiji.article.caseitem.viewmanager;

import android.content.Context;
import android.os.AsyncTask;

import com.taiji.article.caseitem.storageData.CaseDetailedShowData;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.MyTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import orcode.pubsec.taiji.com.business.caseitem.CaseItemBusiness;
import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;

/**
 * Created by z0 on 2016/3/21.
 */
public class CaseItemSearchTask extends AsyncTask<Integer,Integer, String> {
    private Context context;
    private String progressId;
    protected static boolean isRefresh=false;
    protected static CaseItemSearchTask caseItemSearchTask=null;
    protected static Object refreshLock=new Object();

    CaseDetailedShowData caseDetailedShowData;
   //List<CaseData> l_caseData=new  ArrayList<CaseData>();


    public CaseItemSearchTask(Context context, String progressId,  CaseDetailedShowData caseDetailedShowData) {
        this.context = context;
        this.progressId = progressId;
        this.caseDetailedShowData=caseDetailedShowData;
        caseItemSearchTask=this;
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
            if (caseItemSearchTask != null)
                caseItemSearchTask.cancel(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            isRefresh=false;
            caseItemSearchTask=null;
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
        if(caseDetailedShowData==null||caseDetailedShowData.caseItemNumber==null)
        {
            return "-1";
        }

        try {

            JSONObject jo;
           // {"ajustmentFormBean":{"formCode":null,"items":null,"operateTime":null,"reason":null},"ajustmentFormItemBeans":[],"articleCode":"WP-D52011421000020151100021","articleLockerCode":null,"flag":null,"formBean":{"caseCode":null,"caseName":null,"formCode":null,"items":[],"operateTime":null,"papers":null,"suspectName":null},"formCode":null,"formItemBeans":[],"invovledArticleBean":{"articleCode":"wp-D52011421000020151100021","articleFeature":"黑色小米手机","articleName":"通讯工具","caseCode":"A5201142100002015110001","caseName":"A5201142100002015110001","locations":[{"area":"健翔桥下","inTime":1477991465000,"locker":"B-001","measureUnit":"部","numDesc":2},{"area":"健翔桥西","inTime":1477991465000,"locker":"C-001","measureUnit":"部","numDesc":2},{"area":"健翔桥下","inTime":1477991465000,"locker":"B-002","measureUnit":"部","numDesc":2}],"papers":"筑经开公（刑）扣 字（2015）13号","suspectName":"李明明"},"map":{}}

                jo = CaseItemBusiness.findItem(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachCaseItem),
                        MyTools.getDeviceId(context),
                        caseDetailedShowData.caseItemNumber,
                        LoginBusiness.loginPersonID);
                try
                {
                    JSONObject jo2=(JSONObject)jo.get("invovledArticleBean");
                    resultCode = readData(jo2, caseDetailedShowData);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return resultCode;
    }

    private String readData(JSONObject jo, CaseDetailedShowData caseDetailedShowData)
    {

        if(jo==null || caseDetailedShowData==null)
        {
            return "-1";
        }
        try {
            if(!jo.isNull("caseCode")) caseDetailedShowData.caseCode=jo.get("caseCode").toString();
            if(!jo.isNull("caseName"))caseDetailedShowData.caseName=jo.get("caseName").toString();
            if(!jo.isNull("suspectName"))caseDetailedShowData.suspectName=jo.get("suspectName").toString();
            if(!jo.isNull("papers"))caseDetailedShowData.papers=jo.get("papers").toString();
            if(!jo.isNull("articleCode"))caseDetailedShowData.caseItemNumber=jo.get("articleCode").toString();
            if(!jo.isNull("articleName"))caseDetailedShowData.caseItemName =jo.get("articleName").toString();
            if(!jo.isNull("articleFeature"))caseDetailedShowData.earmark=jo.get("articleFeature").toString();
            JSONArray jIfbl = (JSONArray) jo.get("locations");
            if(caseDetailedShowData.l_Detailed==null)
            {
                caseDetailedShowData.l_Detailed=new ArrayList<CaseData>();
            }
            CaseData rd=null;
            for (int j = 0; j < jIfbl.length(); j++)
            {
                try {
                    rd=new CaseData();
                    JSONObject itemDada = (JSONObject) jIfbl.get(j);
                    rd.caseItemHasCount = itemDada.getDouble("numDesc");
                    rd.unit = itemDada.getString("measureUnit");
                    rd.caseItemCountString=  rd.getShowCaseItemHasCount()+ rd.unit;
                    rd.areaCode = itemDada.getString("areaCode");
                    rd.areaName = itemDada.getString("areaName");
                    rd.lockerName = itemDada.getString("lockerName");
                    rd.lockerCode = itemDada.getString("lockerCode");
                    rd.DataCreateTime =getTimeStr(itemDada.getString("inTime"), "yyyy-MM-dd HH:mm:ss");
                    rd.DataUpdateTime = rd.DataCreateTime;
                    /*
                    try {
                        String createdTime =itemDada.getString("inTime");
                        String[] tempStrGroup = createdTime.split("T");
                        createdTime = tempStrGroup[0] + " " + tempStrGroup[1];
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = sdf.parse(createdTime);
                        rd.DataCreateTime =date;
                        rd.DataUpdateTime = date;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }*/
                    if(rd!=null)
                        caseDetailedShowData.l_Detailed.add(rd);
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



    protected void onPostExecute(String result) {

        isRefresh=false;
        String resultCode=result;
        if(completeBack!=null)
        {
            completeBack.completeBack(progressId, resultCode,caseDetailedShowData);

        }

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
        return date;

    }
    @Override
    protected void onCancelled() {
        System.out.println("cancel");
    }

    public interface completeListener
    {
        public void completeBack(String progressId, String result, CaseDetailedShowData caseDetailedShowData);
    }

    private completeListener completeBack;
    public void setCompleteListener(completeListener l) {
        completeBack = l;
    }

}

