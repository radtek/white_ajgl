package orcode.pubsec.taiji.com.business.util.httputil;

import android.annotation.SuppressLint;


import com.taiji.log.httpLogUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;


/**
 * Created by z0 on 2016/2/22.
 */
public class HttpJsonConnect {

    //public static String urlfeedBack="http://zhihui2016.com:10725/jwzh-web-business/demo/sendInstructionFeedback.action";
    // public static String urlUploadFile="http://zhihui2016.com:10725/jwzh-web-business/demo/uploadFile.action";
    // public static String urlGetOrder="http://zhihui2016.com:10725/jwzh-web-business/demo/getNewInstruction.action";
    //   public static String urlGetPoliceRes="http://192.168.19.151:8080/jwzh-web-business/demo/getPoliceAndCarPosition.action";
    //   public static String urlGetLogin1="http://192.168.19.151:8080/jwzh-web-business/index.jsp";
    //  public static String urlGetLogin2="http://192.168.19.151:8080/jwzh-web-business/login";
    // public static String urlGetLogin="http://192.168.19.151:8080/jwzh-web-business/demo/login.action";
    //  public static String urlGetLoginOut="http://zhihui2016.com:10725/jwzh-web-business/demo/loginOut.action";

    public static int httpTimeOut=60000;
    public static boolean isShowHttpErr=false;
    public static boolean isSavHttpErr=false;


    /**
     * 发送 http 请求
     *
     * @param
     */
    @SuppressLint("DefaultLocale")
    public int httpResponseCodeJsonPost(String urlServer,
                                        PostStringParameter[] postParams,boolean sessionOutReSend)
    {
        int responseCode = -1;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //设置通信协议版本
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
            HttpPost httppost = new HttpPost(urlServer);
            JSONObject param = new JSONObject();
            if(postParams!=null){
                for (int j = 0; j < postParams.length; j++) {
                    param.put(postParams[j].name,
                            postParams[j].value);//设置参数
                }
            }

            try {
                //   MultipartEntity mpEntity = new MultipartEntity()
                StringEntity se = new StringEntity(param.toString(),"utf-8");//防止乱码
                httppost.setEntity(se);
                HttpResponse response = httpclient.execute(httppost);

                HttpKeepSession.savSessionID(urlServer,httpclient);

                if(checkSessionOutInterface!=null) {
                    int sessionOut = checkSessionOutInterface.checkSessionOut(response);//LoginBusiness.checkSessionOut(response);
                    if (sessionOut == -1) {
                        return -1;
                    } else if (sessionOut == 1) {
                        if (sessionOutReSend) {
                            httpclient.getConnectionManager().shutdown();
                            return httpResponseCodeJsonPost(urlServer, postParams, false);
                        } else {
                            return -1;
                        }
                    } else if (sessionOut == 0) {

                    }
                }

                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //System.out.println(EntityUtils.toString(resEntity,"utf-8"));
                    String json="";
                    String path="";
                    json= EntityUtils.toString(resEntity, "utf-8");

                    if(checkSessionOutInterface!=null) {
                        int sessionOut = checkSessionOutInterface.checkSessionOut(json);//LoginBusiness.checkSessionOut(response);
                        if (sessionOut == -1) {
                            return -1;
                        } else if (sessionOut == 1) {
                            if (sessionOutReSend) {
                                httpclient.getConnectionManager().shutdown();
                                return httpResponseCodeJsonPost(urlServer, postParams, false);
                            } else {
                                return -1;
                            }
                        } else if (sessionOut == 0) {

                        }
                    }

                    JSONObject p=null;
                    if (resEntity != null) {
                        resEntity.consumeContent();
                    }
                    httpclient.getConnectionManager().shutdown();
                    try{
                        p=new JSONObject(json);
                        //   path=(String) p.get("path");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try{
                        p=new JSONObject(json);
                        path=(String) p.get("path");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                if (resEntity != null) {
                    resEntity.consumeContent();
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseCode;
    }



    @SuppressLint("DefaultLocale")
    public static int httpResponseCodeNameValuePost(String urlServer,
                                                    NameValuePair[] nameValuePair,boolean sessionOutReSend)
    {
        int responseCode = -1;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //设置通信协议版本
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
            HttpPost httppost = new HttpPost(urlServer);
            HttpKeepSession.setSessionID(urlServer,httppost);
            // HttpKeepSession.setSessionCook(httpclient);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if(nameValuePair!=null){
                for (int j = 0; j < nameValuePair.length; j++) {
                    if(nameValuePair[j]!=null){
                        nvps.add(nameValuePair[j]);
                    }
                }
            }
            try {
                // httppost.setEntity(new UrlEncodedFormEntity(nvps));
                httppost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
                HttpResponse response = httpclient.execute(httppost);
                responseCode=response.getStatusLine().getStatusCode();
                if(checkSessionOutInterface!=null)
                {
                    int sessionOut = checkSessionOutInterface.checkSessionOut(response);//LoginBusiness.checkSessionOut(response);
                    if(sessionOut==-1)
                    {
                        if (!sessionOutReSend) {
                            showTextToView(urlServer, nameValuePair, "session过期重登录失败"   +",errCode="+responseCode);
                        }

                        return -1;
                    }
                    else  if(sessionOut==1)
                    {
                        if (sessionOutReSend) {
                            showTextToView(urlServer, nameValuePair, "重登录成功"   +",errCode="+responseCode);
                            httpclient.getConnectionManager().shutdown();
                            return httpResponseCodeNameValuePost(urlServer, nameValuePair, false);
                        }
                        else
                        {
                            return -1;
                        }
                    }
                    else  if(sessionOut==0)
                    {

                    }
                }

                HttpKeepSession.savSessionID(urlServer,httpclient);
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String json="";
                    json= EntityUtils.toString(resEntity, "utf-8");

                    if(checkSessionOutInterface!=null)
                    {
                        int sessionOut = checkSessionOutInterface.checkSessionOut(json);//LoginBusiness.checkSessionOut(response);
                        if(sessionOut==-1)
                        {
                            if(!sessionOutReSend)
                            {
                                showTextToView(urlServer, nameValuePair, "session过期重登录失败"   +",errCode="+responseCode);
                            }

                            return -1;
                        }
                        else  if(sessionOut==1)
                        {
                            if(sessionOutReSend)
                            {
                                showTextToView(urlServer, nameValuePair, "重登录成功"   +",errCode="+responseCode);
                                httpclient.getConnectionManager().shutdown();
                                return httpResponseCodeNameValuePost(urlServer, nameValuePair, false);
                            }
                            else
                            {
                                return -1;
                            }
                        }
                        else  if(sessionOut==0)
                        {

                        }
                    }

                    //  JSONObject p=null;
                    if (resEntity != null) {
                        resEntity.consumeContent();
                    }
                    httpclient.getConnectionManager().shutdown();
                    try{
                        //   p=new JSONObject(json);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                if (resEntity != null) {
                    resEntity.consumeContent();
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                showTextToView(urlServer, nameValuePair, "type=post urlstr,"+"errCode=" + responseCode + "," + e1.getMessage());

            }
            catch (Exception e2) {
                e2.printStackTrace();
                showTextToView(urlServer, nameValuePair, "type=post urlstr,"+"errCode=" + responseCode + "," + e2.getMessage());

            }
        } catch (Exception e) {
            e.printStackTrace();
            showTextToView(urlServer, nameValuePair, "type=post,urlstr,"+"errCode=" + responseCode + "," + e.getMessage());

        }
        return responseCode;
    }

    @SuppressLint("DefaultLocale")
    public static JSONObject httpResponseCodeNameValuePostGetJSON(String urlServer,
                                                                  NameValuePair[] nameValuePair,boolean sessionOutReSend)
    {
        int responseCode = -1;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //设置通信协议版本
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
            HttpPost httppost = new HttpPost(urlServer);
            HttpKeepSession.setSessionID(urlServer,httppost);
            //HttpKeepSession.setSessionCook(httpclient);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if(nameValuePair!=null){
                for (int j = 0; j < nameValuePair.length; j++) {
                    if(nameValuePair[j]!=null){
                        nvps.add(nameValuePair[j]);
                    }
                }
            }
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));

                HttpResponse response = httpclient.execute(httppost);
                responseCode=response.getStatusLine().getStatusCode();
                HttpKeepSession.savSessionID(urlServer,httpclient);

                if(checkSessionOutInterface!=null)
                {
                    int sessionOut = checkSessionOutInterface.checkSessionOut(response);//LoginBusiness.checkSessionOut(response);
                    if(sessionOut==-1)
                    {
                        if(!sessionOutReSend)
                        {
                            showTextToView(urlServer, nameValuePair, "session过期重登录失败"   +",errCode="+responseCode);
                        }
                        return null;
                    }
                    else  if(sessionOut==1)
                    {
                        if(sessionOutReSend)
                        {
                            showTextToView(urlServer, nameValuePair, "重登录成功"   +",errCode="+responseCode);
                            httpclient.getConnectionManager().shutdown();
                            return httpResponseCodeNameValuePostGetJSON(urlServer, nameValuePair, false);
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else  if(sessionOut==0)
                    {

                    }
                }


                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String json="";
                    json= EntityUtils.toString(resEntity, "utf-8");

                    if(checkSessionOutInterface!=null)
                    {
                        int sessionOut = checkSessionOutInterface.checkSessionOut(json);//LoginBusiness.checkSessionOut(response);
                        if(sessionOut==-1)
                        {
                            if(!sessionOutReSend)
                            {
                                showTextToView(urlServer, nameValuePair, "session过期重登录失败"   +",errCode="+responseCode);
                            }
                            return null;
                        }
                        else  if(sessionOut==1)
                        {
                            if(sessionOutReSend)
                            {
                                showTextToView(urlServer, nameValuePair, "重登录成功"   +",errCode="+responseCode);
                                httpclient.getConnectionManager().shutdown();
                                return httpResponseCodeNameValuePostGetJSON(urlServer, nameValuePair, false);
                            }
                            else
                            {
                                return null;
                            }
                        }
                        else  if(sessionOut==0)
                        {

                        }
                    }

                    JSONObject p=null;
                    if (resEntity != null) {
                        resEntity.consumeContent();
                    }
                    httpclient.getConnectionManager().shutdown();
                    try{
                        p=new JSONObject(json);
                        return p;
                    }catch(Exception e){
                        e.printStackTrace();
                        showTextToView(urlServer, nameValuePair, "type=post urlstr,"+"errCode=" + responseCode + ",data=" + json);
                    }
                }
                if (resEntity != null) {
                    resEntity.consumeContent();
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                showTextToView(urlServer, nameValuePair, "type=post urlstr," + "errCode=" + responseCode + "," + e1.getMessage());
            }
            catch (Exception e2) {
                e2.printStackTrace();
                showTextToView(urlServer, nameValuePair, "type=post urlstr," + "errCode=" + responseCode + "," + e2.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showTextToView(urlServer, nameValuePair, "type=post urlstr," + "errCode=" + responseCode + "," + e.getMessage());
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    public static JSONObject httpResponseCodeNameValueGetToJSON(String urlServer,
                                                                NameValuePair[] nameValuePair,boolean sessionOutReSend)
    {
        int responseCode = -1;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //设置通信协议版本
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
            //   HttpPost httppost = new HttpPost(urlServer);

            //HttpKeepSession.setSessionCook(httpclient);
            String tempStr="";
            URI uri=null;
            if(nameValuePair!=null){
                for (int j = 0; j < nameValuePair.length; j++) {
                    if(nameValuePair[j]!=null){
                        if(j==0)
                            tempStr+="?";//"%3F";//
                        else
                            tempStr+="&";//"%26";//
                        tempStr+=nameValuePair[j].getName()+"="+nameValuePair[j].getValue();
                    }
                }
                // tempStr = tempStr.replaceAll(" ", "%20");
                urlServer+=tempStr;
                URL url = new URL(urlServer);
                uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
            }
            HttpGet httpget= new HttpGet(uri);

            HttpKeepSession.setSessionID(urlServer,httpget);
            try {
                // httpget.setParams();
                //   httpget.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));

                HttpResponse response = httpclient.execute(httpget);
                responseCode=response.getStatusLine().getStatusCode();


                HttpKeepSession.savSessionID(urlServer,httpclient);

                if(checkSessionOutInterface!=null)
                {
                    int sessionOut = checkSessionOutInterface.checkSessionOut(response);//LoginBusiness.checkSessionOut(response);
                    if(sessionOut==-1)
                    {
                        if(!sessionOutReSend)
                        {
                            showTextToView(urlServer, nameValuePair, "session过期重登录失败"   +",errCode="+responseCode);
                        }
                        return null;
                    }
                    else  if(sessionOut==1)
                    {
                        if(sessionOutReSend)
                        {
                            showTextToView(urlServer, nameValuePair, "重登录成功"   +",errCode="+responseCode);
                            httpclient.getConnectionManager().shutdown();
                            return httpResponseCodeNameValuePostGetJSON(urlServer, nameValuePair, false);
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else  if(sessionOut==0)
                    {

                    }
                }


                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String json="";
                    json= EntityUtils.toString(resEntity, "utf-8");
                    JSONObject p=null;
                    if (resEntity != null) {
                        resEntity.consumeContent();
                    }
                    httpclient.getConnectionManager().shutdown();
                    try{
                        p=new JSONObject(json);
                        return p;
                    }catch(Exception e){
                        e.printStackTrace();
                        if(uri!=null)
                            showTextToView(uri.toURL().toString(), nameValuePair,"type=get uri,"+"errCode="+responseCode+",data="+ json);
                        else
                            showTextToView(urlServer, nameValuePair,"type=get urlstr,"+ "errCode="+responseCode+",data="+ json);
                    }
                }
                if (resEntity != null) {
                    resEntity.consumeContent();
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                if(uri!=null)
                    showTextToView(uri.toURL().toString(), nameValuePair,"type=get uri,"+"errCode="+responseCode+","+e1.getMessage());
                else
                    showTextToView(urlServer, nameValuePair,"type=get urlstr,"+  "errCode="+responseCode+","+e1.getMessage());
            }
            catch (Exception e2) {
                e2.printStackTrace();
                if(uri!=null)
                    showTextToView(uri.toURL().toString(), nameValuePair,"type=get uri,"+ "errCode="+responseCode+","+e2.getMessage());
                else
                    showTextToView(urlServer, nameValuePair, "type=get urlstr,"+"errCode="+responseCode+","+e2.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showTextToView(urlServer, nameValuePair, "errCode="+responseCode+","+e.getMessage());
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    public static String httpResponseCodeNameValuePostGetJSONStr(String urlServer,
                                                                 NameValuePair[] nameValuePair,boolean sessionOutReSend)
    {
        int responseCode = -1;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //设置通信协议版本
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
            HttpPost httppost = new HttpPost(urlServer);
            HttpKeepSession.setSessionID(urlServer,httppost);
            // HttpKeepSession.setSessionCook(httpclient);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if(nameValuePair!=null){
                for (int j = 0; j < nameValuePair.length; j++) {
                    if(nameValuePair[j]!=null){
                        nvps.add(nameValuePair[j]);
                    };
                }
            }
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                HttpResponse response = httpclient.execute(httppost);
                responseCode=response.getStatusLine().getStatusCode();
                HttpKeepSession.savSessionID(urlServer,httpclient);
                if(checkSessionOutInterface!=null)
                {
                    int sessionOut = checkSessionOutInterface.checkSessionOut(response);//LoginBusiness.checkSessionOut(response);
                    if(sessionOut==-1)
                    {
                        if(!sessionOutReSend)
                        {
                            showTextToView(urlServer, nameValuePair, "session过期重登录失败"   +",errCode="+responseCode);
                        }
                        return "-104";
                    }
                    else  if(sessionOut==1)
                    {
                        if(sessionOutReSend)
                        {
                            showTextToView(urlServer, nameValuePair, "重登录成功"   +",errCode="+responseCode);
                            httpclient.getConnectionManager().shutdown();
                            return httpResponseCodeNameValuePostGetJSONStr(urlServer, nameValuePair, false);
                        }
                        else
                        {
                            return "-104";
                        }
                    }
                    else  if(sessionOut==0)
                    {

                    }
                }


                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String json="";
                    json= EntityUtils.toString(resEntity, "utf-8");

                    if(checkSessionOutInterface!=null)
                    {
                        int sessionOut = checkSessionOutInterface.checkSessionOut(json);//LoginBusiness.checkSessionOut(response);
                        if(sessionOut==-1)
                        {
                            if(!sessionOutReSend)
                            {
                                showTextToView(urlServer, nameValuePair, "session过期重登录失败"   +",errCode="+responseCode);
                            }
                            return "-104";
                        }
                        else  if(sessionOut==1)
                        {
                            if(sessionOutReSend)
                            {
                                showTextToView(urlServer, nameValuePair, "重登录成功"   +",errCode="+responseCode);
                                httpclient.getConnectionManager().shutdown();
                                return httpResponseCodeNameValuePostGetJSONStr(urlServer, nameValuePair, false);
                            }
                            else
                            {
                                return "-104";
                            }
                        }
                        else  if(sessionOut==0)
                        {

                        }
                    }


                    if (resEntity != null) {
                        resEntity.consumeContent();
                    }
                    httpclient.getConnectionManager().shutdown();
                    try{

                        if(json!=null && json.indexOf("权限不足")>0) {
                            return "-406";
                        }


                        return json;
                    }catch(Exception e){
                        e.printStackTrace();
                        showTextToView(urlServer, nameValuePair,"type=post,urlstr,"+"errCode="+responseCode+",data="+ json);
                    }
                }
                if (resEntity != null) {
                    resEntity.consumeContent();
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                showTextToView(urlServer, nameValuePair, "type=post,urlstr,"+"errCode="+responseCode+","+e1.getMessage());
            }
            catch (Exception e2) {
                e2.printStackTrace();
                showTextToView(urlServer, nameValuePair, "type=post,urlstr,"+"errCode="+responseCode+","+e2.getMessage());
            }
            finally {
                httpLogUtil.i(urlServer, "", responseCode, nvps);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showTextToView(urlServer, nameValuePair, "type=post,urlstr," + "errCode=" + responseCode + "," + e.getMessage());
        }
        return null;
    }


    public static  String UploadAppendFile(CustomMultipartEntity multipartContent, String filePath,String fileName,String url,boolean sessionOutReSend) {
        if(multipartContent==null || url==null ||filePath==null ||fileName==null ||url.trim().equals("")||filePath.trim().equals("")||fileName.trim().equals(""))
        {
            return "参数错误";
        }
        String serverResponse = null;

        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
        HttpContext httpContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(url);

        HttpKeepSession.setSessionID(url,httpPost);
        //HttpKeepSession.setSessionCook(httpClient);
        try {
            // Send it
            if(multipartContent!=null) {
                httpPost.setEntity(multipartContent);
            }
            HttpResponse response = httpClient.execute(httpPost, httpContext);
            HttpKeepSession.savSessionID(url, httpClient);

            if(checkSessionOutInterface!=null) {
                int sessionOut = checkSessionOutInterface.checkSessionOut(response);//LoginBusiness.checkSessionOut(response);
                if (sessionOut == -1) {
                    return null;
                } else if (sessionOut == 1) {
                    if (sessionOutReSend) {
                        httpClient.getConnectionManager().shutdown();
                        return UploadAppendFile(multipartContent,filePath,fileName,url,false);
                    } else {
                        return null;
                    }
                } else if (sessionOut == 0) {

                }
            }

            //serverResponse = EntityUtils.toString(response.getEntity());
            serverResponse= EntityUtils.toString(response.getEntity(), "utf-8");

            if(checkSessionOutInterface!=null) {
                int sessionOut = checkSessionOutInterface.checkSessionOut(serverResponse);//LoginBusiness.checkSessionOut(response);
                if (sessionOut == -1) {
                    return null;
                } else if (sessionOut == 1) {
                    if (sessionOutReSend) {
                        httpClient.getConnectionManager().shutdown();
                        return UploadAppendFile(multipartContent,filePath,fileName,url,false);
                    } else {
                        return null;
                    }
                } else if (sessionOut == 0) {

                }
            }

            httpClient.getConnectionManager().shutdown();

        } catch (Exception e) {
            e.printStackTrace();
            showTextToView(url, null, "filePath="+filePath+","+e.getMessage());
        }

        return serverResponse;
    }

    public static void showTextToView(String urlServer, NameValuePair[] p,String messageText)
    {
        if(!isShowHttpErr && !isSavHttpErr)return;

        try {
            String tempStr = "";
            tempStr += "urlServer=" + urlServer;
            if (p != null) {
                for (int i = 0; i < p.length; i++) {
                    try {
                        tempStr += "," + p[i].getName() + "=" + p[i].getValue();
                    } catch (Exception ex) {
                        // p参数可能空
                    }
                }
            }
            tempStr += "," + "messageText=" + messageText;
            if(isShowHttpErr) {
                if(wirteLogInterface!=null)wirteLogInterface.showLog(tempStr);
            }
            if(isSavHttpErr)
            {
                if(wirteLogInterface!=null)wirteLogInterface.wirteLog(tempStr);
            }
        } catch (Exception ex) {
            // ex.printStackTrace();
        }

    }

    public interface WirteLogInterface
    {
        public int showLog(String logText);
        public int wirteLog(String logText);
    }

    private static WirteLogInterface  wirteLogInterface;
    public static void setWirteLogListener(WirteLogInterface w) {
        wirteLogInterface = w;
    }

    public interface CheckSessionOutInterface
    {
        public int checkSessionOut(HttpResponse response);
        public int checkSessionOut(String responseStr);
    }

    private static CheckSessionOutInterface  checkSessionOutInterface;
    public static void setCompleteListener(CheckSessionOutInterface l) {
        checkSessionOutInterface = l;
    }
}
