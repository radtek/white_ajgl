package com.taiji.UpdateApp.viewmanager;

import android.content.Context;
import android.os.AsyncTask;

import com.taiji.Login.LoginActivity;
import  com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.MyTools;

import  orcode.pubsec.taiji.com.business.upgraded.UpgradedVersionBusiness;
import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;

public class CheckAppVersionTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private String progressId;
    private String curVersion;


    public CheckAppVersionTask(Context context, String progressId,String curVersion) {
        this.context = context;
        this.progressId = progressId;
        this.curVersion = curVersion;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        int responseCode = -1;
        try {
            String result = UpgradedVersionBusiness.checkVersion(
                    context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.updateApp_checkNew),
                    MyTools.getDeviceId(context),
                    curVersion
            );
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

        //pd.setProgress((int) (progress[0]));
    }

    @Override
    protected void onPostExecute(String result) {
       // System.out.println("result: " + result);
      //  pd.dismiss();
        if(httpCheckAppBack!=null)
        {
            httpCheckAppBack.httpCheckAppBack(progressId, result);
        }
    }

    @Override
    protected void onCancelled() {
        System.out.println("cancle");
    }

    public interface httpCheckAppListener
    {
        public void httpCheckAppBack(String progressId, String result);
    }

    private httpCheckAppListener httpCheckAppBack;
    public void setHttpCheckAppListener(httpCheckAppListener l) {
        httpCheckAppBack = l;
    }

}

