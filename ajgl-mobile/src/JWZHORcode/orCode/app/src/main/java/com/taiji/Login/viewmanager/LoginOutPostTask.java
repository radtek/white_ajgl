package com.taiji.Login.viewmanager;

import android.content.Context;
import android.os.AsyncTask;

import com.taiji.Login.LoginActivity;

import com.taiji.pubsec.orcode.addressorcode.R;

import orcode.pubsec.taiji.com.business.util.httputil.HttpJsonConnect;
import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;


public class LoginOutPostTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private long totalSize;
    private String progressId;


    public LoginOutPostTask(Context context, String progressId) {
        this.context = context;
        this.progressId = progressId;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        int responseCode = -1;
        try {
            String jsStr= HttpJsonConnect.httpResponseCodeNameValuePostGetJSONStr(
                    context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_GetLoginOut), null, true);
            return jsStr;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(String result) {
        if(httpLoginOutBack!=null)
        {
            httpLoginOutBack.httpLoginOutBack(progressId, result);
        }
    }

    @Override
    protected void onCancelled() {
        System.out.println("cancle");
    }

    public interface httpLoginOutListener
    {
        public void httpLoginOutBack(String progressId, String result);
    }

    private httpLoginOutListener httpLoginOutBack;
    public void setHttpLoginOutListener(httpLoginOutListener l) {
        httpLoginOutBack = l;
    }

}

