package com.taiji.article.caseitem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taiji.pubsec.orcode.addressorcode.R;

import orcode.pubsec.taiji.com.business.util.validation.LoginBusiness;

public class UserInfoActivity extends Activity {  private static final String TAG = "CaseArticleActivity";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fg_user_info);

		LinearLayout scan_case_back_v3_lay=(LinearLayout) findViewById(R.id.scan_case_back_v3_lay);
		scan_case_back_v3_lay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		TextView login_name_tv=(TextView) findViewById(R.id.login_name_tv);
		setTextViewText(login_name_tv, LoginBusiness.loginPersonName);

		TextView login_unit_iv=(TextView) findViewById(R.id.login_unit_iv);
		setTextViewText(login_unit_iv, LoginBusiness.loginUnitName);

		TextView login_class_tv=(TextView) findViewById(R.id.login_class_tv);
		setTextViewText(login_class_tv, LoginBusiness.loginPersonClass);

		TextView login_sex_tv=(TextView) findViewById(R.id.login_sex_tv);
		setTextViewText(login_sex_tv, LoginBusiness.loginPersonSex);

		TextView login_phone_iv=(TextView) findViewById(R.id.login_phone_iv);
		setTextViewText(login_phone_iv, LoginBusiness.loginPersonPhone);

		TextView login_phone2_iv=(TextView) findViewById(R.id.login_phone2_iv);
		setTextViewText(login_phone2_iv, LoginBusiness.loginPersonPhone2);

		TextView login_mail_iv=(TextView) findViewById(R.id.login_mail_iv);
		setTextViewText(login_mail_iv, LoginBusiness.loginPersonMail);

		ImageView btn_login_out_iv=(ImageView) findViewById(R.id.btn_login_out_iv);
		btn_login_out_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent=new Intent();
				//setResult(999, intent);
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
	}

	private void setTextViewText(TextView tview,String text)
	{
		if(tview!=null ) {
			if (text.equals("")) {
				tview.setText("暂无");
			} else {
				tview.setText(text);
			}
		}
	}

	int Backnumber=0;
	@Override
	public void onBackPressed() {
		if(true)
		{
			Intent intent = new Intent(UserInfoActivity.this, CaseScanAllActivity.class);
			//  Bundle mBundle = new Bundle();
			// mBundle.putSerializable("showType", 1);//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
			// intent.putExtras(mBundle);
			//startActivityForResult(intent, 106);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

			finish();
			//System.exit(0);//正常退出App
		}
	}



}