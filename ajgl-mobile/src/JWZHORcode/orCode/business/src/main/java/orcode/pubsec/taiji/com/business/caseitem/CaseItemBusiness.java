package orcode.pubsec.taiji.com.business.caseitem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.List;

import orcode.pubsec.taiji.com.business.util.httputil.HttpJsonConnect;

/**
 * Created by z0 on 2016/8/30.
 */
public class CaseItemBusiness {

    public static JSONObject findStorage(String url,String devId,String StorageCode,String loginUseId) {
        int responseCode = -1;
        try {
            NameValuePair[] p=new NameValuePair[2];
            NameValuePair p1 = new BasicNameValuePair("formCode", StorageCode);
            NameValuePair p2 = new BasicNameValuePair("pdaId", devId);


            p[0]=p1;
            p[1]=p2;


            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject putStorageIn(String url,String devId,String StorageCode,List<CaseData> l_CaseData,String loginUseId) {
        int responseCode = -1;
        NameValuePair[] p =null;
        try
        {
            int addCount=l_CaseData.size()*4;
             p =  new NameValuePair[1+addCount];
             p[0] = new BasicNameValuePair("formCode", StorageCode);
            for(int i=0;i<l_CaseData.size();i++)
            {
                p[i*4+1]= new BasicNameValuePair("inStorageItemBeanList["+i+"].itemId", l_CaseData.get(i).DID);
            //    p[i*5+2]= new BasicNameValuePair("items["+i+"].articleCode", l_CaseData.get(i).caseItemNumber);
              //  p[i*5+3]= new BasicNameValuePair("items["+i+"].area", l_CaseData.get(i).areaCode);
                p[i*4+2]= new BasicNameValuePair("inStorageItemBeanList["+i+"].storageId", l_CaseData.get(i).CItemID);
                p[i*4+3]= new BasicNameValuePair("inStorageItemBeanList["+i+"].lockerCode", l_CaseData.get(i).lockerCode);
                p[i*4+4]= new BasicNameValuePair("inStorageItemBeanList["+i+"].incomingNumber",String.valueOf(l_CaseData.get(i).scanCount));
            }
            for(int i=0;i<p.length;i++)
            {
                System.out.print("name= "+p[i].getName()+" value= "+p[i].getValue());
            }
            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject putStorageOut(String url,String devId,String StorageCode,List<CaseData> l_CaseData,String loginUseId) {
        int responseCode = -1;
        NameValuePair[] p =null;
        try
        {
            int addCount=l_CaseData.size()*3;
            p =  new NameValuePair[1+addCount];
            p[0] = new BasicNameValuePair("formCode", StorageCode);
            for(int i=0;i<l_CaseData.size();i++)
            {
                p[i*3+1]= new BasicNameValuePair("outStorageItemBeanList["+i+"].itemId", l_CaseData.get(i).DID);
                //    p[i*5+2]= new BasicNameValuePair("items["+i+"].articleCode", l_CaseData.get(i).caseItemNumber);
                //  p[i*5+3]= new BasicNameValuePair("items["+i+"].area", l_CaseData.get(i).areaCode);
             //   p[i*4+2]= new BasicNameValuePair("outStorageItemBeanList["+i+"].storageId", l_CaseData.get(i).CItemID);
                p[i*3+2]= new BasicNameValuePair("outStorageItemBeanList["+i+"].lockerCode", l_CaseData.get(i).lockerCode);
                p[i*3+3]= new BasicNameValuePair("outStorageItemBeanList["+i+"].outcomingNumber",String.valueOf(l_CaseData.get(i).scanCount));
            }
            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject putStorageBack(String url,String devId,String StorageCode,List<CaseData> l_CaseData,String loginUseId) {
        int responseCode = -1;
        NameValuePair[] p =null;
        try
        {
            int addCount=l_CaseData.size()*4;
            p =  new NameValuePair[1+addCount];
            p[0] = new BasicNameValuePair("formCode", StorageCode);
            for(int i=0;i<l_CaseData.size();i++)
            {
                p[i*4+1]= new BasicNameValuePair("backStorageItemBeanList["+i+"].itemId", l_CaseData.get(i).DID);
                //    p[i*5+2]= new BasicNameValuePair("items["+i+"].articleCode", l_CaseData.get(i).caseItemNumber);
                //  p[i*5+3]= new BasicNameValuePair("items["+i+"].area", l_CaseData.get(i).areaCode);
                p[i*4+2]= new BasicNameValuePair("backStorageItemBeanList["+i+"].storageId", l_CaseData.get(i).CItemID);
                p[i*4+3]= new BasicNameValuePair("backStorageItemBeanList["+i+"].lockerCode", l_CaseData.get(i).lockerCode);
                p[i*4+4]= new BasicNameValuePair("backStorageItemBeanList["+i+"].returnNumber",String.valueOf(l_CaseData.get(i).scanCount));
            }
            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject putStorageAdjustment(String url,String devId,String StorageCode,List<CaseData> l_CaseData,String loginUseId) {
        int responseCode = -1;
        NameValuePair[] p =null;
        try
        {
            int addCount=l_CaseData.size()*4;
            p =  new NameValuePair[1+addCount];
            p[0] = new BasicNameValuePair("formCode", StorageCode);
            for(int i=0;i<l_CaseData.size();i++)
            {
                p[i*4+1]= new BasicNameValuePair("adjustmentStorageItemBeanList["+i+"].itemId", l_CaseData.get(i).DID);
                //    p[i*5+2]= new BasicNameValuePair("items["+i+"].articleCode", l_CaseData.get(i).caseItemNumber);
                //  p[i*5+3]= new BasicNameValuePair("items["+i+"].area", l_CaseData.get(i).areaCode);
                p[i*4+2]= new BasicNameValuePair("adjustmentStorageItemBeanList["+i+"].storageId", l_CaseData.get(i).CItemID);
                p[i*4+3]= new BasicNameValuePair("adjustmentStorageItemBeanList["+i+"].lockerCode", l_CaseData.get(i).lockerCode);
                p[i*4+4]= new BasicNameValuePair("adjustmentStorageItemBeanList["+i+"].adjustNumber",String.valueOf(l_CaseData.get(i).scanCount));
            }
            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject findItem(String url,String devId,String ItemCode,String loginUseId) {
        int responseCode = -1;
        try {
            NameValuePair[] p=new NameValuePair[2];
            NameValuePair p1 = new BasicNameValuePair("articleCode", ItemCode);
            NameValuePair p2 = new BasicNameValuePair("pdaId", devId);


            p[0]=p1;
            p[1]=p2;


            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject findShelf(String url,String devId,String shelfCode,String loginUseId) {
        int responseCode = -1;
        try {
            NameValuePair[] p=new NameValuePair[2];
            NameValuePair p1 = new BasicNameValuePair("articleLockerCode", shelfCode);
            NameValuePair p2 = new BasicNameValuePair("pdaId", devId);


            p[0]=p1;
            p[1]=p2;


            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //暂存
    public static JSONObject findTemporaryInStorage(String url,String devId,String StorageCode,String loginUseId) {
        int responseCode = -1;
        try {
            NameValuePair[] p=new NameValuePair[1];
            NameValuePair p1 = new BasicNameValuePair("storageInCode", StorageCode);
          //  NameValuePair p2 = new BasicNameValuePair("pdaId", devId);

            p[0]=p1;
          //  p[1]=p2;

            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject findTemporaryOutStorage(String url,String devId,String StorageCode,String loginUseId) {
        int responseCode = -1;
        try {
            NameValuePair[] p=new NameValuePair[1];
            NameValuePair p1 = new BasicNameValuePair("storageOutCode", StorageCode);
            //  NameValuePair p2 = new BasicNameValuePair("pdaId", devId);

            p[0]=p1;
            //  p[1]=p2;

            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JSONObject putTemporaryInStorage(String url,String devId,String StorageCode,String shelfCode,String loginUseId) {
        int responseCode = -1;
        NameValuePair[] p =null;
        try
        {
             p=new NameValuePair[2];
            NameValuePair p1 = new BasicNameValuePair("storageInCode", StorageCode);
            NameValuePair p2 = new BasicNameValuePair("tempSaveSelfCode", shelfCode);

            p[0]=p1;
            p[1]=p2;

            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject putTemporaryOutStorage(String url,String devId,String StorageCode,String shelfCode,String loginUseId) {
        int responseCode = -1;
        NameValuePair[] p =null;
        try
        {
            p=new NameValuePair[2];
            NameValuePair p1 = new BasicNameValuePair("storageOutCode", StorageCode);
            NameValuePair p2 = new BasicNameValuePair("tempSaveSelfCode", shelfCode);

            p[0]=p1;
            p[1]=p2;

            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject findTemporaryShelf(String url,String devId,String shelfCode,String loginUseId) {
        int responseCode = -1;
        try {
            NameValuePair[] p=new NameValuePair[1];
            NameValuePair p1 = new BasicNameValuePair("tempSaveSelfCode", shelfCode);
            //NameValuePair p2 = new BasicNameValuePair("pdaId", devId);


            p[0]=p1;
           // p[1]=p2;


            JSONObject result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSON(
                    url, p, true);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
