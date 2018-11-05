package com.taiji.UpdateApp.viewmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class GetBroadcast extends BroadcastReceiver {
	private static GetBroadcast mReceiver = new GetBroadcast();
	private static IntentFilter mIntentFilter;

	public static void registerReceiver(Context context) {
		mIntentFilter = new IntentFilter();
		mIntentFilter.addDataScheme("package");
		mIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		// mIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		// context.registerReceiver(mReceiver, mIntentFilter);

		//mIntentFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);  
		//mIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);  
		mIntentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
		mIntentFilter.addDataScheme("package");
	}

	public static void unregisterReceiver(Context context) {
		context.unregisterReceiver(mReceiver);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		String packageName = intent.getData().getSchemeSpecificPart();
		if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
			Toast.makeText(context, "添加了新的应用", Toast.LENGTH_LONG).show();
			PackageManager pm = context.getPackageManager();
			Intent intent1 = new Intent();
			intent1 = pm.getLaunchIntentForPackage(packageName);
			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent1);
		} // else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
		// // Toast.makeText(context, "有应用被删除", Toast.LENGTH_LONG).show();
		// } 
		else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
			Toast.makeText(context, "有应用被替换", Toast.LENGTH_LONG).show();
		}
	}
}