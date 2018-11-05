package com.taiji.log;

import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import cn.com.cybertech.pdk.OperationLog;

/**
 * Created by z0 on 2016/12/1.
 */
public  class httpLogUtil {
    public static Context mContext;
    public static void v(String url,String content,int result,List<NameValuePair> nvps)
    {
        try {
        String tempStr=getContentLogStr(url,content,result,nvps);
            if(tempStr==null)return;
            Log.i(url, tempStr);
            String moduleName=getUrlName(url);
            OperationLog.saveLog(mContext, "com.taiji.pubsec.orcode.addressorcode", moduleName, 1, result, 1, tempStr);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void i(String url,String content,int result,List<NameValuePair> nvps)
    {
        try {
        String tempStr=getContentLogStr(url,content,result,nvps);
        if(tempStr==null)return;
        Log.i(url, tempStr);
        String moduleName=getUrlName(url);
        OperationLog.saveLog(mContext, "com.taiji.pubsec.orcode.addressorcode", moduleName, 1, result, 1, tempStr);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void e(String url,String content,int result,List<NameValuePair> nvps)
    {
        try {
        String tempStr=getContentLogStr(url,content,result,nvps);
            if(tempStr==null)return;
            Log.i(url, tempStr);
            String moduleName=getUrlName(url);
            OperationLog.saveLog(mContext, "com.taiji.pubsec.orcode.addressorcode", moduleName, 1, result, 1, tempStr);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void w(String url,String content,int result,List<NameValuePair> nvps)
    {
        try {
        String tempStr=getContentLogStr(url,content,result,nvps);
            if(tempStr==null)return;
            Log.i(url, tempStr);
            String moduleName=getUrlName(url);
            OperationLog.saveLog(mContext, "com.taiji.pubsec.orcode.addressorcode", moduleName, 1, result, 1, tempStr);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void d(String url,String content,int result,List<NameValuePair> nvps)
    {
        try {
            String tempStr=getContentLogStr(url,content,result,nvps);
            if(tempStr==null)return;
            Log.i(url, tempStr);
            String moduleName=getUrlName(url);
            OperationLog.saveLog(mContext, "com.taiji.pubsec.orcode.addressorcode", moduleName, 1, result, 1, tempStr);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private static String getUrlName(String url)
    {
        try {
            if(url==null ||url.equals(""))return "";
            int lastIndex =url.lastIndexOf("/");
            String tempStr = url.substring(lastIndex,url.length()-1);
            return tempStr;
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private static String getContentLogStr(String url,String content,int result,List<NameValuePair> nvps)
    {
        try {
            StringBuffer sbf = new StringBuffer("");
            if (url != null) sbf.append("url=" + url + ";\r\n");
            else sbf.append("url=" + "null;\r\n");
            if (content != null) sbf.append("content=" + content + ";");
            else sbf.append("content=" + "null;\r\n");
            sbf.append("result=" + result + ";\r\n");


            if (nvps != null) {
                for (int i = 0; i < nvps.size(); i++) {
                    sbf.append(nvps.get(i).getName().toString() + "=" + nvps.get(i).getValue().toString() + ";");
                }
                sbf.append("\r\n");
            } else
                sbf.append("parm=" + "null;\r\n");
            return sbf.toString();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
