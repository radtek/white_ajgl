package com.taiji.article.caseitem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.Result;
import com.taiji.pubsec.orcode.addressorcode.CaptureBaseActivity;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.pubsec.orcode.addressorcode.camera.MyCameraManager;
import com.taiji.util.MyAnimationUtil;

public class CaseScanAllActivity extends CaptureBaseActivity
{

	/** Called when the activity is first created. */
	CaseScanAllActivity caseScanAllActivity;
	ImageView scan_case_light_iv;
	LinearLayout id_more_tool_lay,id_write_code_lay;
	TextView scan_case_item_title_tv;
	TextView scan_case_item_name_tv;
	EditText code_text_et;
	int scanShowType=-1;
	protected boolean isShowMoreTools=false;
	LayoutAnimationController controller = null;

	boolean isWriteCode=false;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}


	@Override
	protected void onCreateDo()
	{
		setContentView(R.layout.main2);
		/*网上拔的初始化控制左右滑动进入的代码,效果豆B
		LayoutInflater factory = LayoutInflater
				.from(CaseScanAllActivity.this);
		FrameLayout dialogEntryView = (FrameLayout) factory.inflate(
				R.layout.main2, null);
		controller = AnimationUtils.loadLayoutAnimation(this,
				R.anim.layout_right_in);
		dialogEntryView.setLayoutAnimation(controller);
	    setContentView(dialogEntryView);
	    */


	//overridePendingTransition(R.anim.layout_right_in, R.anim.layout_left_in);
		//setContentView(R.layout.main2);
		caseScanAllActivity=this;
		super.initCameraManager(null);

		LinearLayout scan_case_back_v3_lay=(LinearLayout) findViewById(R.id.scan_case_back_v3_lay);
		scan_case_back_v3_lay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		scan_case_light_iv=(ImageView) findViewById(R.id.scan_case_light_iv);
		scan_case_light_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLight) {
					scan_case_light_iv.setImageDrawable(getResources().getDrawable(R.drawable.light_on2x));
					MyCameraManager.openLight();
					isLight = !isLight;
				} else {
					scan_case_light_iv.setImageDrawable(getResources().getDrawable(R.drawable.light_off2x));
					MyCameraManager.closeLight();
					isLight = !isLight;
				}
			}
		});


		scan_case_item_title_tv=(TextView) findViewById(R.id.scan_case_item_title_tv);
		scan_case_item_name_tv=(TextView) findViewById(R.id.scan_case_item_name_tv);
		setTextViewText(scan_case_item_title_tv,"信息查询");
		setTextViewText(scan_case_item_name_tv, "扫描单据/物品二维码");

		id_more_tool_lay=(LinearLayout) findViewById(R.id.id_more_tool_lay);
		ImageView scan_case_item_more_tools_iv=(ImageView) findViewById(R.id.scan_case_item_more_tools_iv);
		scan_case_item_more_tools_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isShowMoreTools) {
					if(id_more_tool_lay!=null) {
						  MyAnimationUtil mau=new MyAnimationUtil();
						mau.viewStartAnimation(id_more_tool_lay, mau.showTopToBottomAnimation(id_more_tool_lay,200,mau.animationShowlistener));
						id_more_tool_lay.setVisibility(View.VISIBLE);
						isShowMoreTools = !isShowMoreTools;
					}
				} else {
					if(id_more_tool_lay!=null) {
						id_more_tool_lay.setVisibility(View.GONE);
						isShowMoreTools = !isShowMoreTools;
					}
				}
				if(id_write_code_lay!=null) {
					id_write_code_lay.setVisibility(View.GONE);
					if(code_text_et!=null) {
						code_text_et.clearFocus();
						//	code_text_et.setFocusable(false);
					}
				}
			}
		});

		LinearLayout main_item_more_tool_write_lay=(LinearLayout) findViewById(R.id.main_item_more_tool_write_lay);
		id_write_code_lay=(LinearLayout) findViewById(R.id.id_write_code_lay);
		code_text_et=(EditText) findViewById(R.id.code_text_et);
		main_item_more_tool_write_lay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(id_more_tool_lay!=null) {
					id_more_tool_lay.setVisibility(View.GONE);
					isShowMoreTools = false;
				}
				if(id_write_code_lay!=null) {
					MyAnimationUtil mau=new MyAnimationUtil();
					mau.viewStartAnimation(id_write_code_lay, mau.inFromRightAnimation(id_write_code_lay, 200, mau.animationShowlistener));
					id_write_code_lay.setVisibility(View.VISIBLE);
				}

			}
		});

		LinearLayout main_item_more_tool_loginoutv3_lay=(LinearLayout) findViewById(R.id.main_item_more_tool_loginoutv3_lay);
		main_item_more_tool_loginoutv3_lay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//CaseScanAllActivity.this.finish();
				Intent intent = new Intent(CaseScanAllActivity.this, UserInfoActivity.class);
				//Bundle mBundle = new Bundle();
				startActivity(intent);
				finish();
			}
		});

		RelativeLayout id_write_code_end=(RelativeLayout) findViewById(R.id.id_write_code_end);
		id_write_code_end.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(code_text_et!=null)
				{
					isWriteCode=true;
					String tempStr=code_text_et.getText().toString().toUpperCase();
					if(tempStr==null || tempStr.trim().equals(""))
					{

					}
					else {
						Result obj = new Result(tempStr, null, null, null, 0);
						handleDecode(obj, null);
					}
				}
			}
		});

		RelativeLayout id_write_code_canecl=(RelativeLayout) findViewById(R.id.id_write_code_canecl);
		id_write_code_canecl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(id_write_code_lay!=null) {
					id_write_code_lay.setVisibility(View.GONE);
					if(code_text_et!=null) {
						code_text_et.clearFocus();
					//	code_text_et.setFocusable(false);
					}
				}
			}
		});
	}

	@Override
	protected void onResume() {
		//super.initRect(null);
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		try {
			if (waitReScanThread != null && waitReScanThread.isAlive()) {
				waitReScanThread.interrupt();
				waitReScanThread = null;
			}
		}catch(Exception ex)
		{

		}
		super.onPause();
	}

	int Backnumber=1;
	@Override
	public void onBackPressed() {
		if(Backnumber==1)
		{
			//   super.onBackPressed();
			Bundle mBundle = new Bundle();
			//  mBundle.putSerializable("userTaskAdapterData", userTaskAdapterData);
			// Intent intent=new Intent();
			// intent.putExtras(mBundle);
			// setResult(106, intent);
			finish();
		}
	}
    @Override
	public void handleDecode(final Result obj, Bitmap barcode)
	{
		if(inactivityTimer!=null)inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		if (barcode == null)
		{
			dialog.setIcon(null);
		}
		else
		{

			Drawable drawable = new BitmapDrawable(barcode);
			dialog.setIcon(drawable);
		}
		dialog.setTitle("扫描结果");
	//	dialog.setMessage(obj.getText());
		String tempStr=setShowType(obj.getText().toUpperCase());
		if(tempStr==null || tempStr.equals(""))
		{
			dialog.setMessage("您扫描的二维码无法识别，请重新扫描");//"无法识别的编码" + obj.getText().toUpperCase());
			dialog.setNegativeButton("重扫描", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
			dialog.create().show();
		}
		else {
			//dialog.setMessage(tempStr+" " + obj.getText().toUpperCase());
			//dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			//	@Override
			//	public void onClick(DialogInterface dialog, int which) {
			//		//用默认浏览器打开扫描得到的地址

					Intent intent = new Intent(CaseScanAllActivity.this, CaseArticleActivity.class);
					Bundle mBundle = new Bundle();
			mBundle.putSerializable("showType", scanShowType);//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
			mBundle.putSerializable("scanCode", obj.getText().toUpperCase());
			intent.putExtras(mBundle);
					//startActivityForResult(intent, 106);
			startActivity(intent);
					finish();
			       return;
			//	}
			//});
			//dialog.setPositiveButton("重扫描", new DialogInterface.OnClickListener() {
			//	@Override
			//	public void onClick(DialogInterface dialog, int which) {

			//		waitReScan();
					//onPauseDo();
					//	onResumeDo();
					//finish();
			//	}
		//	});
		}

	}

	//
	private String setShowType(String scanCode)
	{
		scanShowType=-1;
		if(scanCode==null || scanCode.trim().equals("") || scanCode.length()<4)
		{
			return null;
		}
		String tempStr=scanCode.length()<4 ? scanCode : scanCode.toUpperCase().trim().substring(0, 4);
		//String tempStr=scanCode.trim().substring(0,2).toUpperCase();
		if(tempStr.indexOf("RK")==0)
		{
			scanShowType=1;
			return "入库单";
		}
		else if(tempStr.indexOf("CK")==0)
		{
			scanShowType=2;
			return "出库单";
		}
		else if(tempStr.indexOf("FH")==0)
		{
			scanShowType=3;
			return "再入库单";
		}
		else if(tempStr.indexOf("TZ")==0)
		{
			scanShowType=4;
			return "调整单";
		}
		else if(tempStr.indexOf("WP")==0)
		{
			scanShowType=6;
			return "涉案物品";
		}
		else if(tempStr.indexOf("ZCRK")==0)
		{
			//暂时没有储物架查询
			scanShowType=7;//7;
			return "7";//"储物架";
		}
		else if(tempStr.indexOf("ZCCK")==0)
		{
			//暂时没有储物架查询
			scanShowType=8;//8;
			return "8";//"储物架";
		}
		else if(tempStr.indexOf("CWJ")>-1)
		{
			//暂时没有储物架查询
			scanShowType=-1;//;
			return "";//"储物架";
		}
		return null;
		/*
		else
		{

			tempStr=tempStr.substring(0,1);
			if(tempStr.equals("D"))
			{
				scanShowType=6;
				return "涉案物品";
			}
			return null;
		}
		*/
	}

	private void setTextViewText(TextView tview,String text)
	{
		if(tview!=null )
		{
			if(text==null ||text.trim().equals("null"))
			{
				tview.setText("");
			}
		    else
			{
				tview.setText(text);
			}
		}
	}

	Thread waitReScanThread;
    int waitReScanShowNumber=5;
	protected void waitReScan()
	{
		if(isWriteCode)
		{
			isWriteCode=false;
			return;
		}
		if(waitReScanThread!=null &&waitReScanThread.isInterrupted())
		{
			waitReScanThread.interrupt();
			waitReScanThread=null;
		}

		waitReScanThread = new Thread(new Runnable() {
			@Override
			public void run() {
			//	Handler mainHandler = new Handler(Looper.getMainLooper());
				//mainHandler.post(new Runnable() {
			//		@Override
			//		public void run() {
				waitReScanShowNumber=3;
				for (int i = 0; i < 3; i++) {
					if(Thread.interrupted())return;
					try {
						Thread.sleep(1000);
						if(Thread.interrupted())return;
						waitReScanShowNumber--;
						Handler myHandler = new Handler(Looper.getMainLooper());
						myHandler.post(new Runnable() {
							@Override
							public void run() {
								setTextViewText(scan_case_item_name_tv, "距离下次扫描还有"+waitReScanShowNumber +"秒");
							}
						});

					} catch (Exception ex) {

					}
					continue;
				}
				Handler mainHandler = new Handler(Looper.getMainLooper());
				mainHandler.post(new Runnable() {
					@Override
					public void run() {
						setTextViewText(scan_case_item_name_tv, "扫描单据/物品二维码");
						//onPauseDo();
						//onResumeDo();

						reScan();
					}
				});

			}
		});
		waitReScanThread.start();
	}

}