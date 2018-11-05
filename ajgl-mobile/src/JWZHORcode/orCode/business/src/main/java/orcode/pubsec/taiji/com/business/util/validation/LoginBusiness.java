package orcode.pubsec.taiji.com.business.util.validation;

import com.taiji.log.httpLogUtil;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import orcode.pubsec.taiji.com.business.util.httputil.HttpJsonConnect;
import orcode.pubsec.taiji.com.business.util.httputil.HttpKeepSession;

public class LoginBusiness {

    public static String  loginUserName=null;
    public static String  loginPwd=null;
    static String  firstUrl=null;
    static String  secondUrl=null;
    static String  getMQTLoginInfoUrl=null;
    static String deviceId ="";
    static String sesstionId = null;

    public static String loginPersonName ="";
    public static String  loginPersonCode="";
    public static String  loginPersonID="";
    public static String loginUnitCode ="";
    public static String loginUnitName ="";
    public static String loginPersonClass ="";
    public static String loginPersonSex ="";
    public static String loginPersonPhone ="";
    public static String loginPersonPhone2 ="";
    public static String loginPersonMail ="";
    public static String loginIp="192.168.19.30";
    public static String loginPort="8080";
    public static String pdaComIp="";
    public static String pdaComPass="";
    public static String pdaComNum="";
    public static String pdaComPort="";
    public static String arcgisBaseServer="";
    public static String szptBaseUrl="http://192.168.61.118:9003/szpt-web-wifi/interface/";

    String ischeckDeviceId="0";

    public LoginBusiness(String loginUserName, String loginPwd, String deviceId, String firstUrl, String secondUrl, String getMQTLoginInfoUrl) {
        this.loginUserName = loginUserName;
        this.loginPwd = loginPwd;
        this.firstUrl = firstUrl;
        this.secondUrl = secondUrl;
        this.getMQTLoginInfoUrl = getMQTLoginInfoUrl;
        this.deviceId = deviceId;
        //this.userName = userName;
        //this.pwd = pwd;
    }

    public LoginBusiness(String loginUserName, String loginPwd, String deviceId, String firstUrl, String secondUrl, String getMQTLoginInfoUrl, String ischeckDeviceId) {
        this.loginUserName = loginUserName;
        this.loginPwd = loginPwd;
        this.firstUrl = firstUrl;
        this.secondUrl = secondUrl;
        this.deviceId = deviceId;
        this.getMQTLoginInfoUrl = getMQTLoginInfoUrl;
        this.ischeckDeviceId=ischeckDeviceId;
    }

    public String doLogin() {
        String responseCode = "-1";
        try {
            if(loginUserName==null || loginUserName.trim().equals(""))
            {
                return "-1";
            }

            //HttpJsonConnect注册session验证方法
            HttpJsonConnect.setCompleteListener(new HttpJsonConnect.CheckSessionOutInterface() {
                @Override
                public int checkSessionOut(HttpResponse response) {
                    return LoginBusiness.checkSessionOut(response);
                }

                @Override
                public int checkSessionOut(String responseStr) {
                    return LoginBusiness.checkSessionOut2(responseStr);
                }
            });

            responseCode = firstLogin();
            if(responseCode=="0"){
                responseCode = secondLogin();
            }
            if(responseCode=="0") {
                responseCode = getMQTLoginInfo(true);
            }

            // if(responseCode==0){
            //     responseCode = testSessionLogin();
            // }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    public static int checkLogin(HttpResponse  response)
    {
        try {
            Header[] hds = response.getHeaders("loginSuccess") ;
            HeaderElement[] hes = hds[0].getElements();
            String isSuccess = hes[0].getValue() ;
            // System.out.println("loginSuccess:"+isSuccess);
            if(isSuccess.equals("no"))
            {
                return -1;
            }
            else
            {
                return 0;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            //没找到值也认为没有退回登陆页
            return 0;
        }
    }

    public static int checkLogin2(HttpResponse  response)
    {
        try {
            String  httptext=EntityUtils.toString(response.getEntity(), "utf-8");
            if(httptext.indexOf(">请输入密码！</p></div>")>-1)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            //没找到值也认为没有退回登陆页
            return 0;
        }
    }

    public static int checkSessionOut2(String  httptext)
    {
        try {
            if(httptext.indexOf(">请输入密码！</p></div>")>-1)
            {
                String responseCode = "-1";
                try {
                    if(loginUserName==null || loginUserName.trim().equals(""))
                    {
                        return -1;
                    }
                    //  responseCode = firstLogin();
                    // if(responseCode== "0"){
                    responseCode = secondLogin();
                    //   }
                    if(responseCode=="0")
                    {
                        return 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return -1;
            }
            else
            {
                return 0;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            //没找到值也认为没有退回登陆页
            return 0;
        }
    }

    //-1 登陆失败  0 没过期  1 过期已重登陆
    public static int checkSessionOut(HttpResponse  response)
    {
        try {
            Header[] hds = response.getHeaders("loginSuccess") ;
            HeaderElement[] hes = hds[0].getElements();
            String isSuccess = hes[0].getValue() ;
            // System.out.println("loginSuccess:"+isSuccess);
            if(isSuccess.equals("no"))
            {
                String responseCode = "-1";
                try {
                    if(loginUserName==null || loginUserName.trim().equals(""))
                    {
                        return -1;
                    }
                    responseCode = firstLogin();
                    if(responseCode== "0"){
                        responseCode = secondLogin();
                    }
                    if(responseCode=="0")
                    {
                        return 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return -1;
            }
            else
            {
                return 0;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            //没找到值也认为没有退回登陆页
            return 0;
        }
    }

    public static void setSesstionId(HttpResponse httpResponse){
        try {
            Header[] hds = httpResponse.getHeaders("Set-Cookie");

            HeaderElement[] hes = hds[0].getElements();
            String sid = hes[0].getValue();
            sesstionId = sid;
            // System.out.println("SESSTIONID:" + sesstionId);
        }
        catch (Exception ex)
        {}
    }

    private static String firstLogin()
    {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        //设置通信协议版本
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
        HttpPost httppost = new HttpPost(firstUrl);
        int responseCode = -1;
        //  HttpKeepSession.setSessionID(httppost);
        //  HttpKeepSession.setSessionCook(httpclient);
        try {
            HttpResponse response = httpclient.execute(httppost);
            responseCode = response.getStatusLine().getStatusCode();
            if(response.getStatusLine().getStatusCode()!=200)
            {
                return "errCode:"+response.getStatusLine().getStatusCode();
            }
            setSesstionId(response);
            HttpKeepSession.savSessionID(firstUrl,httpclient);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                if (resEntity != null) {
                    resEntity.consumeContent();
                }
                httpclient.getConnectionManager().shutdown();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            HttpJsonConnect.showTextToView(firstUrl, null, "type=post," + "resCode=" + responseCode);
            return "-10";//连接异常
        }
        return "0";
    }

    private static String secondLogin()
    {
        int responseCode = -1;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //  CloseableHttpClient httpclient = HttpClients.createDefault();
            //设置通信协议版本
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
            HttpPost httppost = new HttpPost(secondUrl);
            HttpKeepSession.setSessionID(secondUrl,httppost);
            // HttpKeepSession.setSessionCook(httpclient);
            httppost.setHeader("isSuccess", "");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("userName",loginUserName));
           // nvps.add(new BasicNameValuePair("password", getMD5Str(loginPwd + sesstionId)));
            //nvps.add(new BasicNameValuePair("password", getMD5Str(loginPwd + sesstionId)));
            nvps.add(new BasicNameValuePair("password", loginPwd));
            nvps.add(new BasicNameValuePair("passEncrypt", "no"));
          //  httppost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            httppost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            try {
                HttpResponse  response = httpclient.execute(httppost);
                //HttpEntity resEntity = response.getEntity();
              //  String json = "";
               // if (resEntity != null) {

              //      json = EntityUtils.toString(resEntity, "utf-8");
              //  }
                responseCode=response.getStatusLine().getStatusCode();
                if(response.getStatusLine().getStatusCode()!=200)
                {
                    return "errCode:"+response.getStatusLine().getStatusCode();
                }
                setSesstionId(response);
                HttpKeepSession.savSessionID(secondUrl,httpclient);
                // HttpKeepSession.savSessionID((HttpClient)httpclient);

                try {
                    if(checkLogin(response)==0 && checkLogin2(response)==0 )
                    {
                        return "0";
                    }
                    else
                    {
                        return "-20";//登陆无效,Session过期
                    }


                } catch (Exception e2) {
                    e2.printStackTrace();
                    HttpJsonConnect.showTextToView(secondUrl, null, "type=post," + "resCode=" + responseCode);
                    return "-99";
                }

                /*
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String p= EntityUtils.toString(resEntity, "utf-8");
                    if (resEntity != null) {
                        resEntity.consumeContent();
                    }
                    httpclient.getConnectionManager().shutdown();
                }*/

            } catch (Exception e1) {
                e1.printStackTrace();
                HttpJsonConnect.showTextToView(secondUrl, null, "type=post," + "resCode=" + responseCode);
                return "-10";
            }
            finally {
                httpLogUtil.i(secondUrl, "", responseCode, nvps);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "-20";
        }
        //return 0;
    }

    // sessionOutReSend 发现登陆过期重新登陆后是否再尝试一次提交,否直接返回提交失败
    private String getMQTLoginInfo(boolean sessionOutReSend)
    {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        //设置通信协议版本
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpJsonConnect.httpTimeOut);
        HttpPost httppost = new HttpPost(getMQTLoginInfoUrl);
        HttpKeepSession.setSessionID(getMQTLoginInfoUrl,httppost);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accountName", loginUserName));
        nvps.add(new BasicNameValuePair("pdaId", deviceId));
        int responseCode = -1;
        //  HttpKeepSession.setSessionCook(httpclient);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response = httpclient.execute(httppost);
            responseCode=response.getStatusLine().getStatusCode();
            if(responseCode!=200)
            {
                return "errCode:"+response.getStatusLine().getStatusCode();
            }
            setSesstionId(response);
            HttpKeepSession.savSessionID(getMQTLoginInfoUrl,httpclient);

            int sessionOut =checkSessionOut(response);
            if(sessionOut==-1)
            {
                return "-20";
            }
            else  if(sessionOut==1)
            {
                if(sessionOutReSend)
                {
                    httpclient.getConnectionManager().shutdown();
                    return getMQTLoginInfo(false);
                }
                else
                {
                    return "-20";
                }
            }
            else  if(sessionOut==0)
            {

            }

            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String  resultStr = EntityUtils.toString(resEntity, "utf-8");
                // {"accountName":"ZL","infoDetailBean":{"pdaComIp":"58.42.241.251","pdaComNum":"6028","pdaComPass":"6028","pdaComPort":"7080","pdaDeviceNum":"a0000055eb10dea","personCode":"zhangling","personId":"de88436f-21b3-4cb5-ade7-dea5cfaea4d6","personName":"张零","unitName":"测试(北京)"}}
                JSONObject p = new JSONObject(resultStr);
                JSONObject infoDetailBean = p.getJSONObject("infoDetailBean");
                //arcgisBaseServer="http://58.42.241.253:6080/";//"http://52.4.1.204:6080/";//infoDetailBean.getString("arcgisBaseMapServer").trim();
                //szptBaseUrl=infoDetailBean.getString("szptBaseUrl").trim();
                if(!infoDetailBean.isNull("personCode"))
                loginPersonCode=infoDetailBean.getString("personCode").trim();
                if(!infoDetailBean.isNull("personId"))
                loginPersonID=infoDetailBean.getString("personId").trim();
                if(!infoDetailBean.isNull("personName"))
                loginPersonName= infoDetailBean.getString("personName").trim();
                if(!infoDetailBean.isNull("personSexName"))
                loginPersonSex=infoDetailBean.getString("personSexName").trim();
                if(!infoDetailBean.isNull("phoneNum"))
                loginPersonPhone=infoDetailBean.getString("phoneNum").trim();
                if(!infoDetailBean.isNull("officePhone"))
                    loginPersonPhone2=infoDetailBean.getString("officePhone").trim();
                if(!infoDetailBean.isNull("personSexName"))
                    loginPersonSex=infoDetailBean.getString("personSexName").trim();
                if(!infoDetailBean.isNull("positionName"))
                    loginPersonClass=infoDetailBean.getString("positionName").trim();

                try {
                    if(!infoDetailBean.isNull("orgCode"))
                    loginUnitCode =infoDetailBean.getString("orgCode").trim();
                    if(!infoDetailBean.isNull("orgName"))
                    loginUnitName=infoDetailBean.getString("orgName").trim();
                }
                catch (Exception ex)
                {
                    loginUnitCode ="520114000000";
                }
            //    if(infoDetailBean.isNull("pdaDeviceNum"))
             //   {
             //       return "-50";//非法设备
            //    }
                if(ischeckDeviceId.equals("1")){
                    String pdaDeviceNum=infoDetailBean.getString("pdaDeviceNum").trim();
                    if (deviceId.equals(pdaDeviceNum))
                    {
                        /*
                        if(infoDetailBean.isNull("pdaComIp"))
                        {
                            return "-40";//验证设备或获取通信信息异常
                        }
                        pdaComIp=infoDetailBean.getString("pdaComIp").trim();
                        pdaComPass=infoDetailBean.getString("pdaComPass").trim();
                        pdaComNum=infoDetailBean.getString("pdaComNum").trim();
                        pdaComPort=infoDetailBean.getString("pdaComPort").trim();
                        try {
                            //固定分局
                            loginUnitCode ="520114000000";// infoDetailBean.getString("organizationCode").trim();
                        }
                        catch (Exception ex)
                        {
                            loginUnitCode ="520114000000";
                        }
                        */
                    }
                    else
                    {
                        return "-30";//设备ID和用户不匹配
                    }
                }
                else
                {
                    /*
                    if(infoDetailBean.isNull("pdaComIp"))
                    {
                        return "-40";//验证设备或获取通信信息异常
                    }
                    pdaComIp=infoDetailBean.getString("pdaComIp").trim();
                    pdaComPass=infoDetailBean.getString("pdaComPass").trim();
                    pdaComNum=infoDetailBean.getString("pdaComNum").trim();
                    pdaComPort=infoDetailBean.getString("pdaComPort").trim();
                    */

                }


                if (resEntity != null) {
                    resEntity.consumeContent();
                }
                httpclient.getConnectionManager().shutdown();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            HttpJsonConnect.showTextToView(getMQTLoginInfoUrl, null, "type=post," + "resCode=" + responseCode);
            return "-40";//验证设备或获取通信信息异常
        }
        finally {
            httpLogUtil.i(getMQTLoginInfoUrl, "", responseCode, nvps);
        }
        return "0";
    }

    private static String getMD5Str(String s) throws Exception{
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}

