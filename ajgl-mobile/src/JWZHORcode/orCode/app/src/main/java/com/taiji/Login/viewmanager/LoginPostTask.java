package com.taiji.Login.viewmanager;

import android.content.Context;
import android.os.AsyncTask;

import com.taiji.Login.LoginActivity;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.MyTools;

import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;


public class LoginPostTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private String progressId;

    public LoginPostTask(Context context, String progressId) {
        this.context = context;
        this.progressId = progressId;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        String responseCode = "-1";
        LoginBusiness hl=new LoginBusiness
                (
                        LoginActivity.loginUserName,
                        LoginActivity.loginPwd,
                        MyTools.getDeviceId(context),
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_GetLoginStep1),
                        context.getResources().getString(R.string.http) + LoginBusiness.loginIp + ":" + LoginBusiness.loginPort + context.getResources().getString(R.string.url_GetLoginStep2),
                        context.getResources().getString(R.string.http)+LoginBusiness.loginIp+":"+LoginBusiness.loginPort+context.getResources().getString(R.string.url_GetLoginStep3),
                        context.getResources().getString(R.string.isCheackDeviceNumAndUser)
                );
        try {
            if(hl.loginUserName==null || hl.loginUserName.trim().equals(""))
            {
                return "-1";
            }
            responseCode =hl.doLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(responseCode);
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(String result) {
        if(httpLoginPostBack!=null)
        {
            httpLoginPostBack.httpLoginPostBack(progressId, result);
        }
    }

    @Override
    protected void onCancelled() {
        System.out.println("cancel");
    }

    public interface httpLoginListener
    {
        public void httpLoginPostBack(String progressId, String result);
    }

    private httpLoginListener httpLoginPostBack;
    public void setHttpLoginListener(httpLoginListener l) {
        httpLoginPostBack = l;
    }

}

