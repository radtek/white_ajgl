package orcode.pubsec.taiji.com.business.upgraded;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import orcode.pubsec.taiji.com.business.util.httputil.HttpJsonConnect;


public class UpgradedVersionBusiness {

    public static  String checkVersion(String url,String devId,String VersionText) {
        int responseCode = -1;
        try {
            NameValuePair[] p=new NameValuePair[2];
            NameValuePair p1 = new BasicNameValuePair("versionNum", VersionText);
            NameValuePair p2 = new BasicNameValuePair("pdaId", devId);


            p[0]=p1;
            p[1]=p2;


            String result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSONStr(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

