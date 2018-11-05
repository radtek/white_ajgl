/**
 * @by wainiwann
 *
 */
package com.taiji.UpdateApp;


import com.taiji.Login.SavLogin;
import com.taiji.UpdateApp.Util.DialogHelper;
import com.taiji.UpdateApp.viewmanager.UpdateManager;
import com.taiji.pubsec.orcode.addressorcode.R;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class Update_AppActivity extends Activity
{
	private UpdateManager updateMan;
	private ProgressDialog updateProgressDialog;
	boolean forceUpdate=false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_main);

		Intent intent = getIntent();
		String forceUpdateStr = intent.getStringExtra("forceUpdate");
		if(forceUpdateStr.equals("Y"))
		{
			forceUpdate=true;
		}

		//GetBroadcast.registerReceiver(getApplicationContext());//注册广播，用于监听应用是否安装完成

		//没有判断网路是否连接的提示
		//检查是否有更新
		//如果有更新提示下载
		updateMan = new UpdateManager(Update_AppActivity.this, appUpdateCb,forceUpdate);
		updateMan.checkUpdate();

		/*
		Button btnExit = (Button) this.findViewById(R.id.updateapp_btn_exit);
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
				//am.killBackgroundProcesses(this.getPackageName());
				if(forceUpdate) {
					System.exit(0);
				}
				else
				{
					Update_AppActivity.this.finish();
				}
			}
		});*/

	}



	int Backnumber=0;
	@Override
	public void onBackPressed() {

		if(Backnumber==0)
		{
			Toast.makeText(this, "再次点击将退出", Toast.LENGTH_LONG).show();
			Backnumber++;
		}
		else if(Backnumber==1)
		{
			Backnumber++;
		}
		else if(Backnumber>=2){
			Backnumber=0;
			if(forceUpdate) {
				System.exit(0);
			}
			else
			{
				Intent intent = new Intent(Update_AppActivity.this,com.taiji.article.caseitem.CaseScanAllActivity.class);
				Update_AppActivity.this.startActivity(intent);
				finish();
				//super.onBackPressed();
			}
		}
	}


	// 自动更新回调函数
	UpdateManager.UpdateCallback appUpdateCb = new UpdateManager.UpdateCallback()
	{

		public void downloadProgressChanged(int progress) {
			if (updateProgressDialog != null
					&& updateProgressDialog.isShowing()) {
				updateProgressDialog.setProgress(progress);
			}

		}

		public void downloadCompleted(Boolean sucess, CharSequence errorMsg) {
			if (updateProgressDialog != null
					&& updateProgressDialog.isShowing()) {
				updateProgressDialog.dismiss();
			}
			if (sucess) {
				SavLogin.savUpdateCompletedIP(Update_AppActivity.this,
						getResources().getString(R.string.case_Ip),
						getResources().getString(R.string.case_post)
				);
				updateMan.update();

			} else {
				DialogHelper.Confirm(Update_AppActivity.this,
						R.string.dialog_error_title,
						R.string.dialog_downfailed_msg,
						R.string.dialog_downfailed_btndown,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {
								updateMan.downloadPackage();

							}
						}, R.string.dialog_downfailed_btnnext,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {
								if (forceUpdate) {
									System.exit(0);
								} else {
									Intent intent = new Intent(Update_AppActivity.this,com.taiji.article.caseitem.CaseScanAllActivity.class);
									Update_AppActivity.this.startActivity(intent);
									finish();
									//Update_AppActivity.this.finish();
								}

							}
						});
			}
		}

		public void downloadCanceled()
		{
			// TODO Auto-generated method stub

		}

		public void checkUpdateCompleted(Boolean hasUpdate,
										 CharSequence updateInfo) {

			try{
				Button btnExit = (Button) Update_AppActivity.this.findViewById(R.id.updateapp_btn_exit);
				btnExit.setText(R.string.exit_button);
				btnExit.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (forceUpdate) {
							//ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
							//am.restartPackage(getPackageName());
							//系统会(www.111cn.net)将，该包下的 ，所有进程，服务，全部杀掉，就可以杀干净了，要注意加上
							//<uses-permission android:name=\"android.permission.RESTART_PACKAGES\"></uses-permission>
							android.os.Process.killProcess(android.os.Process.myPid());
							System.exit(0);
						} else {
							Intent intent = new Intent(Update_AppActivity.this, com.taiji.article.caseitem.CaseScanAllActivity.class);
							Update_AppActivity.this.startActivity(intent);
							finish();
							//Update_AppActivity.this.finish();
						}
					}
				});
			}catch (Exception e) {
				e.printStackTrace();
			}

			if (hasUpdate) {
				DialogHelper.Confirm(Update_AppActivity.this,
						getText(R.string.dialog_update_title),
						getText(R.string.dialog_update_msg).toString()
								+updateInfo+
								getText(R.string.dialog_update_msg2).toString(),
						getText(R.string.dialog_update_btnupdate),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
												int which) {
								updateProgressDialog = new ProgressDialog(
										Update_AppActivity.this);
								updateProgressDialog
										.setMessage(getText(R.string.dialog_downloading_msg));
								updateProgressDialog.setIndeterminate(false);
								updateProgressDialog
										.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
								updateProgressDialog.setMax(100);
								updateProgressDialog.setProgress(0);
								updateProgressDialog.show();
								updateProgressDialog.setCancelable(false);

								updateMan.downloadPackage();
							}
						},getText( R.string.dialog_update_btnnext), null);
			}

		}
	};
}