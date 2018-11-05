package com.taiji.UpdateApp.viewmanager;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;




import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;

import orcode.pubsec.taiji.com.business.util.httputil.HttpJsonConnect;
import orcode.pubsec.taiji.com.business.util.httputil.HttpKeepSession;
import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;

import com.taiji.Login.LoginActivity;
import com.taiji.pubsec.orcode.addressorcode.R;

public class UpdateManager {

	private String curVersion;
	private String newVersion;
	private int curVersionCode;
	private int newVersionCode;
	private String newInfoId;
	private String updateInfo;
	private UpdateCallback callback;
	private Context ctx;

	private int progress;
	private Boolean hasNewVersion;
	private Boolean canceled;

	//存放更新APK文件的路径
	//public static final String UPDATE_DOWNURL = "http://192.168.2.8:8081/com.dongrensm.shgpw/update/f.apk";
	//存放更新APK文件相应的版本说明路径
	//http://192.168.2.8:8081/com.dongrensm.shgpw/update/update_version.txt
	//public static final String UPDATE_CHECKURL = "http://192.168.2.8:8081/com.dongrensm.shgpw/update/update_version.txt";
	//public static  String UPDATE_XMLURL = "http://192.168.2.8:8081/com.dongrensm.cnpc/update/update.xml";
	//public static  String UPDATE_APKNAME = "com.drkj.updata.apk";
	//public static final String UPDATE_VERJSON = "ver.txt";
	//public static final String UPDATE_SAVENAME = "com.drkj.updata.apk";
	private static final int UPDATE_CHECKCOMPLETED = 1;
	private static final int UPDATE_DOWNLOADING = 2;
	private static final int UPDATE_DOWNLOAD_ERROR = 3;
	private static final int UPDATE_DOWNLOAD_COMPLETED = 4;
	private static final int UPDATE_DOWNLOAD_CANCELED = 5;

	public static String downUrl="";
	public static String checkNewnUrl="";
	public static String downApkName="jwzh.apk";
	boolean forceUpdate=false;

	//从服务器上下载apk存放文件夹
	//private String savefolder = "/mnt/innerDisk/";
	//private String savefolder = "/sdcard/";
	//public static final String SAVE_FOLDER =Storage. // "/mnt/innerDisk";
	public UpdateManager(Context context, UpdateCallback updateCallback,boolean forceUpdate) {
		ctx = context;
		downUrl=context.getResources().getString(R.string.http)+ LoginBusiness.loginIp+":"+LoginBusiness.loginPort+context.getResources().getString(R.string.updateApp_downUrl);
		checkNewnUrl=context.getResources().getString(R.string.http)+LoginBusiness.loginIp+":"+ LoginBusiness.loginPort+context.getResources().getString(R.string.updateApp_checkNew);
		this.forceUpdate=forceUpdate;
		callback = updateCallback;
		//savefolder = context.getFilesDir();
		canceled = false;
		getCurVersion();
	}

	public String getNewVersionName()
	{
		return newVersion;
	}

	public String getUpdateInfo()
	{
		return updateInfo;
	}

	private void getCurVersion() {
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			curVersion = pInfo.versionName;
			curVersionCode = pInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.e("update", e.getMessage());
			curVersion = "1.1.1000";
			curVersionCode = 111000;
		}

	}

	public void checkUpdate() {
		hasNewVersion = false;
		new Thread(){
			// ***************************************************************
			/**
			 * @by wainiwann add
			 *
			 */
			@Override
			public void run() {
				try {
					//从资源文件获取服务器 地址
					String path = checkNewnUrl;
					//包装成url的对象
					String result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSONStr(path,null,true);

					JSONObject p= new JSONObject(result);
					JSONObject jp=(JSONObject)p.get("appVersionBean");
					String versionName="";
					String attachId="";
					if(!jp.isNull("versionNum"))
					{
						versionName=jp.getString("versionNum");
					}
					if(!jp.isNull("attachId"))
					{
						attachId=jp.getString("attachId");
					}

					if( curVersion.compareTo(versionName) < 0)
					{
						try {
							//newVersionCode = info.getVersion();
							newVersion = versionName;
							newInfoId = attachId;
							updateInfo = "";
							hasNewVersion = true;
							//Log.i("newVerCode", newVersionCode
							//		+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							//Log.i("newVerName", newVersion
							//		+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							//if(!newVersion.equals(curVersion)){
							//	hasNewVersion = true;
						//	}
						} catch (Exception e) {
							newVersionCode = -1;
							newVersion = "";
							updateInfo = "";

						}
					}
				} catch (Exception e) {
					// 待处理
					newVersionCode = -1;
					newVersion = "";
					updateInfo = "";
				}

				updateHandler.sendEmptyMessage(UPDATE_CHECKCOMPLETED);



			};
			// ***************************************************************
		}.start();

	}




//  //安装apk
//    Intent intent = new Intent(Intent.ACTION_VIEW);
//    intent.setDataAndType(Uri.fromFile(new File(fileURL)),
//    "application/vnd.android.package-archive");
//    startActivity(intent);

	public void update() {

		Intent intent = new Intent(Intent.ACTION_VIEW);

		intent.setDataAndType(
				Uri.fromFile(new File(Environment.getExternalStorageDirectory(), downApkName)),
				"application/vnd.android.package-archive");
		ctx.startActivity(intent);
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void downloadPackage()
	{


		new Thread() {
			@Override
			public void run() {
				try {

//		                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
					//URL url = new URL(path);
					String path = downUrl+newInfoId;
					//包装成url的对象
				//	String result = HttpJsonConnect.httpResponseCodeNameValuePostGetJSONStr(path,null);
					URL url = new URL(path);
					HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
					HttpKeepSession.setSessionID(downUrl,conn);
					conn.setConnectTimeout(5000);
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();
					//获取到文件的大小
					//pd.setMax(conn.getContentLength());

					File ApkFile = new File(Environment.getExternalStorageDirectory(), downApkName);
					if(ApkFile.exists())
					{
						ApkFile.delete();
					}

					FileOutputStream fos = new FileOutputStream(ApkFile);

					int count = 0;
					byte buf[] = new byte[512];

					do{

						int numread = is.read(buf);
						count += numread;
						progress =(int)(((float)count / length) * 100);

						updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOADING));
						if(numread <= 0){

							updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED);
							break;
						}
						fos.write(buf,0,numread);
					}while(!canceled);
					if(canceled)
					{
						updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_CANCELED);
					}
					fos.close();
					is.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();

					updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR,e.getMessage()));
				} catch(IOException e){
					e.printStackTrace();

					updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR,e.getMessage()));
				}


			}

		}.start();
	}

	public void cancelDownload()
	{
		canceled = true;
	}

	Handler updateHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case UPDATE_CHECKCOMPLETED:

					callback.checkUpdateCompleted(hasNewVersion, newVersion);
					break;
				case UPDATE_DOWNLOADING:

					callback.downloadProgressChanged(progress);
					break;
				case UPDATE_DOWNLOAD_ERROR:

					callback.downloadCompleted(false, msg.obj.toString());
					break;
				case UPDATE_DOWNLOAD_COMPLETED:

					callback.downloadCompleted(true, "");
					break;
				case UPDATE_DOWNLOAD_CANCELED:

					callback.downloadCanceled();
				default:
					break;
			}
		}
	};

	public interface UpdateCallback {
		public void checkUpdateCompleted(Boolean hasUpdate,
										 CharSequence updateInfo);

		public void downloadProgressChanged(int progress);
		public void downloadCanceled();
		public void downloadCompleted(Boolean sucess, CharSequence errorMsg);
	}

}
