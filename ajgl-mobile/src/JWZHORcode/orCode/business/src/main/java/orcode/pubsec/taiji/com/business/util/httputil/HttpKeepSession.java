package orcode.pubsec.taiji.com.business.util.httputil;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by z0 on 2016/3/11.
 */
public class HttpKeepSession {
    // private static String testJSESSIONID;
    static CookieStore cookieStore;
    //static String sesstionId = null;
    static String myLock = "";
    static Map<String,String> keepSessionMap=new HashMap<String,String>();
    public static boolean isLogJSESSIONID=true;

    private static String urlToKey(String urlStr)
    {
        try {
            if (urlStr == null || urlStr.trim().length() < 8) {
                return "";
            }
            int startIndex = 0;

            if (urlStr.trim().length() > 9) {
                String tempStr = urlStr.substring(0, 8).toLowerCase();
                if (tempStr.indexOf("http://") > -1) {
                    startIndex = 7;
                } else if (tempStr.indexOf("https://") > -1) {
                    startIndex = 8;
                } else if (tempStr.indexOf("ftp://") > -1) {
                    startIndex = 6;
                }
                tempStr = urlStr.substring(startIndex, urlStr.length());
                int endIndex = tempStr.indexOf("/");
                if (endIndex > 0) {
                    String urlKey = tempStr.substring(0, endIndex);
                    return urlKey;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return "";
    }

    public static String ShowJSESSIONID(String url)
    {
        String tempStr=urlToKey(url);
        if(tempStr==null ||tempStr.equals(""))
            return "";
        else
        {
            return getJSESSIONID(tempStr);
        }
    }

    private static String getJSESSIONID(String urlKey)
    {
        //  return testJSESSIONID;

        if(urlKey==null ||urlKey.equals("")) return "";

        if(keepSessionMap!=null && keepSessionMap.containsKey(urlKey))
        {
            return keepSessionMap.get(urlKey);
        }
        return "";
    }

    private static int setJSESSIONID(String urlKey,String JSESSIONID)
    {
        // testJSESSIONID=JSESSIONID;

        if(urlKey==null ||urlKey.equals("")) return 0;

        if(keepSessionMap!=null)
        {
            if(keepSessionMap.containsKey(urlKey))
            {
                keepSessionMap.put(urlKey,JSESSIONID);
                return 2;
            }
            else
            {
                keepSessionMap.put(urlKey,JSESSIONID);
                return 1;
            }
        }
        else
        {
            return 0;
        }

        //return 0;
    }

    public static HttpPost setSessionID(String url,HttpPost httpPost)
    {
        String urlKey=urlToKey(url);
        String JSESSIONID=getJSESSIONID(urlKey);
        if(JSESSIONID==null ||JSESSIONID.equals(""))
        {

        }
        else {
            httpPost.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
            if (isLogJSESSIONID) {
                HttpJsonConnect.showTextToView(url, null, "urlKey=" + urlKey + ",setJSESSIONID=" + JSESSIONID);
            }
        }
        return httpPost;
    }

    public static HttpGet setSessionID(String url,HttpGet httpGet)
    {
        String urlKey=urlToKey(url);
        String JSESSIONID=getJSESSIONID(urlKey);
        if(JSESSIONID==null ||JSESSIONID.equals(""))
        {

        }
        else
        {
            httpGet.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
            if (isLogJSESSIONID) {
                HttpJsonConnect.showTextToView(url, null, "urlKey=" + urlKey + ",setJSESSIONID=" + JSESSIONID);
            }
            // System.out.println("setJSESSIONID="+JSESSIONID);
        }
        return httpGet;
    }

    public static HttpURLConnection setSessionID(String url,HttpURLConnection conn)
    {
        String urlKey=urlToKey(url);
        String JSESSIONID=getJSESSIONID(urlKey);
        if(JSESSIONID==null ||JSESSIONID.equals(""))
        {

        }
        else
        {
            conn.setRequestProperty("Cookie", "JSESSIONID=" + JSESSIONID);
            if (isLogJSESSIONID) {
                HttpJsonConnect.showTextToView(url, null, "urlKey=" + urlKey + ",setJSESSIONID=" + JSESSIONID);
            }
        }
        return conn;
    }


    public static void savSessionID(String url,DefaultHttpClient httpClient)
    {
        synchronized(myLock) {

            //cookieStore  = httpClient.getCookieStore();
            // httpClient.setCookieStore(null);
            String JSESSIONID=null;
            CookieStore cookieStore = httpClient.getCookieStore();
            List<Cookie> cookies = cookieStore.getCookies();
            for(int i=0;i<cookies.size();i++){
                if("JSESSIONID".equals(cookies.get(i).getName())){
                    JSESSIONID = cookies.get(i).getValue();
                    String urlKey=urlToKey(url);
                    setJSESSIONID(urlKey, JSESSIONID);
                    if (isLogJSESSIONID) {
                        HttpJsonConnect.showTextToView(url, null, "urlKey=" + urlKey + ",savJSESSIONID=" + JSESSIONID);
                    }
                    break;
                }
            }
            if(JSESSIONID==null)
            {
                //非登陆时没有这个值,打印太多
                //  if (isLogJSESSIONID) {

                //    HttpbusinessConnect.showTextToView(url, null, "savJSESSIONID时未找到JSESSIONID");
                //}
            }
            // System.out.println("setJSESSIONID="+JSESSIONID);
        }
    }

    public static void setSesstionId(HttpResponse httpResponse){
        try {
            Header[] hds = httpResponse.getHeaders("Set-Cookie");

            HeaderElement[] hes = hds[0].getElements();
            String sid = hes[0].getValue();
            //  sesstionId = sid;
            // System.out.println("SESSTIONID:" + sesstionId);
        }
        catch (Exception ex)
        {}
    }
}
