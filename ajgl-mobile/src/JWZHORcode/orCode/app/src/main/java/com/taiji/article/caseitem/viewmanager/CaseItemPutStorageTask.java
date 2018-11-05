package com.taiji.article.caseitem.viewmanager;

import android.content.Context;
import android.os.AsyncTask;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;

import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.MyTools;

import org.json.JSONObject;

import java.util.List;

import orcode.pubsec.taiji.com.business.caseitem.CaseItemBusiness;
import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;

/**
 * Created by z0 on 2016/3/21.
 */
public class CaseItemPutStorageTask extends AsyncTask<Integer,Integer, String> {
    private Context context;
    private String progressId;
    protected static boolean isRefresh=false;
    protected static CaseItemPutStorageTask caseItemSearchStorageTask=null;
    protected static Object refreshLock=new Object();

    List<CaseData> l_CaseData;
    int caseStorageDataType=0;
    String storageCode="";
    String shelfCode="";

   //List<CaseData> l_caseData=new  ArrayList<CaseData>();


    public CaseItemPutStorageTask(Context context, String progressId,String storageCode,int caseStorageDataType, List<CaseData> l_CaseData) {
        this.context = context;
        this.progressId = progressId;
        this.caseStorageDataType = caseStorageDataType;
        this.l_CaseData=l_CaseData;
        this.storageCode=storageCode;
        caseItemSearchStorageTask=this;
        init();
    }

    public CaseItemPutStorageTask(Context context, String progressId,String storageCode,String shelfCode,int caseStorageDataType) {
        this.context = context;
        this.progressId = progressId;
        this.caseStorageDataType = caseStorageDataType;
        this.storageCode=storageCode;
        this.shelfCode = shelfCode;
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
        if((l_CaseData==null||l_CaseData.size()<0) && caseStorageDataType!=7 && caseStorageDataType!=8)
        {
            return "-1";
        }
        else if(caseStorageDataType>8 || caseStorageDataType<0)
        {
            return "-1";
        }

        try {

            JSONObject jo=null;
            if(caseStorageDataType==1)
            {
                jo = CaseItemBusiness.putStorageIn(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_putInStorage),
                        MyTools.getDeviceId(context),
                        storageCode,
                        l_CaseData,
                        LoginBusiness.loginPersonID);
            }
            else if(caseStorageDataType==2)
            {
                jo = CaseItemBusiness.putStorageOut(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_putOutStorage),
                        MyTools.getDeviceId(context),
                        storageCode,
                        l_CaseData,
                        LoginBusiness.loginPersonID);
            }
            else if(caseStorageDataType==3)
            {
                jo = CaseItemBusiness.putStorageBack(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_putBackStorage),
                        MyTools.getDeviceId(context),
                        storageCode,
                        l_CaseData,
                        LoginBusiness.loginPersonID);
            }
            else if(caseStorageDataType==4) {
                jo = CaseItemBusiness.putStorageAdjustment(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_putAdjustmentStorage),
                        MyTools.getDeviceId(context),
                        storageCode,
                        l_CaseData,
                        LoginBusiness.loginPersonID);
            }
            else if(caseStorageDataType==7) {
                jo = CaseItemBusiness.putTemporaryInStorage(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_putTemporaryInAdjustment),
                        MyTools.getDeviceId(context),
                        storageCode,
                        shelfCode,
                        LoginBusiness.loginPersonID);
            }
            else if(caseStorageDataType==8) {
                jo = CaseItemBusiness.putTemporaryOutStorage(
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_putTemporaryOutAdjustment),
                        MyTools.getDeviceId(context),
                        storageCode,
                        shelfCode,
                        LoginBusiness.loginPersonID);
            }

            if(jo!=null) {
                String resultSytr=jo.getString("flag");
                System.out.printf(resultSytr);
                if(resultSytr.equals("success") || resultSytr.equals("true"))
                {
                    resultCode="1";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
      //  resultCode="1";

        return resultCode;
    }

    protected void onPostExecute(String result) {

        isRefresh=false;
        String resultCode=result;
        if(completeBack!=null)
        {
            completeBack.completeBack(progressId, resultCode);

        }

    }


    @Override
    protected void onCancelled() {
        System.out.println("cancel");
    }

    public interface completeListener
    {
        public void completeBack(String progressId, String result);
    }

    private completeListener completeBack;
    public void setCompleteListener(completeListener l) {
        completeBack = l;
    }

}

