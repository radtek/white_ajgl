package com.taiji.article.caseitem.viewmanager;

import android.content.Context;
import android.os.AsyncTask;

import com.taiji.article.caseitem.storageData.CaseShelfData;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.MyTools;

import org.json.JSONObject;

import orcode.pubsec.taiji.com.business.caseitem.CaseItemBusiness;
import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;

/**
 * Created by z0 on 2016/3/21.
 */
public class CaseShelfSearchTask extends AsyncTask<Integer,Integer, String> {
    protected Context context;
    protected String progressId;
    protected static boolean isRefresh=false;
    protected static CaseShelfSearchTask CaseShelfSearchTask=null;
    protected static Object refreshLock=new Object();

    protected CaseShelfData caseShelfData;
    protected int seachMode=1;//1查询涉案相关 2查询暂存相关
   //List<CaseData> l_caseData=new  ArrayList<CaseData>();


    public CaseShelfSearchTask(Context context, String progressId, CaseShelfData caseShelfData) {
        this.context = context;
        this.progressId = progressId;
        this.caseShelfData=caseShelfData;
        CaseShelfSearchTask=this;
        init();
    }

    public CaseShelfSearchTask(Context context, String progressId, CaseShelfData caseShelfData,int seachMode) {
        this.context = context;
        this.progressId = progressId;
        this.caseShelfData=caseShelfData;
        this.seachMode= seachMode;
        CaseShelfSearchTask=this;
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
            if (CaseShelfSearchTask != null)
                CaseShelfSearchTask.cancel(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            isRefresh=false;
            CaseShelfSearchTask=null;
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
        if(caseShelfData==null||caseShelfData.DID==null)
        {
            return "-1";
        }

        try {

            JSONObject jo;
            if(seachMode==1) {
                jo = CaseItemBusiness.findShelf(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachCaseShelf),
                        MyTools.getDeviceId(context),
                        caseShelfData.DID,
                        LoginBusiness.loginPersonID);
                try {
                    if (jo != null) {
                        resultCode = readData(jo, caseShelfData);
                        ;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(seachMode==2) {
                jo = CaseItemBusiness.findTemporaryShelf(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_sreachTemporaryShelf),
                        MyTools.getDeviceId(context),
                        caseShelfData.DID,
                        LoginBusiness.loginPersonID);
                try {
                    if (jo != null) {
                        JSONObject jo2=(JSONObject)jo.get("temporaryStorageShelfBean");
                        resultCode = readTemporaryData(jo2, caseShelfData);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return resultCode;
    }

    private String readData(JSONObject jo, CaseShelfData caseShelfData)
    {

       if(jo==null || caseShelfData==null)
        {
            return "-1";
        }
        try {
            //JSONArray jmap = (JSONArray) jo.get("map");
           // if(jmap.length()>0)
          //  {
            //    caseShelfData.lockerName=((JSONObject)jmap.get(0)).getString("location");
            //    return "1";
          //  }
            JSONObject jmap = (JSONObject) jo.get("map");
            caseShelfData.caseCode=jmap.getString("caseCode");
            if(!jmap.isNull("status"))
                caseShelfData.DataType=jmap.getInt("status");
            caseShelfData.lockerName=jmap.getString("location");
            caseShelfData.areaCode=jmap.getString("areaCode");
            caseShelfData.areaName=jmap.getString("areaName");
            return "1";
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return "-1";

    }

    private String readTemporaryData(JSONObject jo, CaseShelfData caseShelfData)
    {

        if(jo==null || caseShelfData==null)
        {
            return "-1";
        }
        try {
            caseShelfData.DID=jo.getString("code");
            caseShelfData.lockerName=jo.getString("address");
            if(!jo.isNull("status"))
                caseShelfData.DataType=jo.getInt("status");;
            //caseShelfData.areaCode=jo.getString("temporaryStorageShelfBean. address");
            JSONObject jo2=(JSONObject)jo.get("areaBean");
            caseShelfData.areaName=jo2.getString("name");
            return "1";
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return "-1";

    }

    protected void onPostExecute(String result) {

        isRefresh=false;
        String resultCode=result;
        if(completeBack!=null)
        {
            completeBack.completeBack(progressId, resultCode,caseShelfData);

        }

    }


    @Override
    protected void onCancelled() {
        System.out.println("cancel");
    }

    public interface completeListener
    {
        public void completeBack(String progressId, String result, CaseShelfData caseShelfData);
    }

    private completeListener completeBack;
    public void setCompleteListener(completeListener l) {
        completeBack = l;
    }

}

