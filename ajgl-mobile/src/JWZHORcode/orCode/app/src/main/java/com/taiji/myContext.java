package com.taiji;

import android.app.Application;
import android.content.Context;

import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.MyTools;

import orcode.pubsec.taiji.com.business.util.contextUtil;
import orcode.pubsec.taiji.com.business.util.httputil.HttpJsonConnect;


public class myContext extends Application {//android.support.multidex.MultiDexApplication {
	public static Context mContext = null;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		try {
			contextUtil.setContext(mContext);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			HttpJsonConnect.isShowHttpErr=Boolean.valueOf(mContext.getResources().getString(R.string.IsShowHttpErr));
			HttpJsonConnect.isSavHttpErr=Boolean.valueOf(mContext.getResources().getString(R.string.IsSavHttpErr));
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		try {
			HttpJsonConnect.setWirteLogListener(new HttpJsonConnect.WirteLogInterface() {
				public int showLog(String logText) {
					MyTools.showText(logText);
					return 1;
				}

				public int wirteLog(String logText) {
					MyTools.wirteLogText(logText);
					return 1;
				}
			});
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
