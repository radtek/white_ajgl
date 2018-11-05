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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.taiji.article.caseitem.storageData.CaseShelfData;
import com.taiji.article.caseitem.storageData.CaseStorageData;
import com.taiji.article.caseitem.viewmanager.CaseItemPutStorageTask;
import com.taiji.article.caseitem.viewmanager.CaseShelfSearchTask;
import com.taiji.pubsec.orcode.addressorcode.CaptureBaseActivity;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.pubsec.orcode.addressorcode.camera.MyCameraManager;
import com.taiji.util.MyAnimationUtil;

public class CaseScanShelfActivity extends CaptureBaseActivity
{

	/** Called when the activity is first created. */
	CaseScanShelfActivity caseScanShelfActivity;
	ImageView scan_case_light_iv;
	LinearLayout id_write_code_lay;
	EditText code_text_et;
	protected boolean isShowMoreTools=false;
	boolean isWriteCode=false;

	int showType=-1;
	CaseStorageData caseStorageData;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onCreateDo()
	{
		setContentView(R.layout.mainshelf);
		//setContentView(R.layout.main2);
		caseScanShelfActivity=this;
		super.initCameraManager(null);

		Intent intent = getIntent();
		try{
			showType = (int)intent.getSerializableExtra("showType");
			caseStorageData = (CaseStorageData)intent.getSerializableExtra("CaseStorageData");
		}catch(Exception e)
		{}

		TextView scan_case_title_v3_tv;
		TextView scan_case_item_title_tv;
		TextView scan_case_item_name_tv;
		scan_case_item_title_tv=(TextView) findViewById(R.id.scan_case_item_title_tv);
		scan_case_item_name_tv=(TextView) findViewById(R.id.scan_case_item_name_tv);
		if(caseStorageData!=null && showType !=-1)
		{
			//scan_case_title_v3_tv=(TextView) findViewById(R.id.scan_case_title_v3_tv);
			//setTextViewText(scan_case_title_v3_tv,"货架扫描");
			if(showType==1)
			{
				setTextViewText(scan_case_item_title_tv,caseStorageData.formCode+"入库单");
				setTextViewText(scan_case_item_name_tv,"扫描存放储物箱");
			}
			else if(showType==2)
			{
				setTextViewText(scan_case_item_title_tv,caseStorageData.formCode+"出库单");
				setTextViewText(scan_case_item_name_tv,"扫描存放储物箱");
			}
			else if(showType==3)
			{
				setTextViewText(scan_case_item_title_tv,caseStorageData.formCode+"再入库单");
				setTextViewText(scan_case_item_name_tv,"扫描存放储物箱");
			}
			else if(showType==4)
			{
				setTextViewText(scan_case_item_title_tv,caseStorageData.formCode+"位置调整单");
				setTextViewText(scan_case_item_name_tv,"扫描调整前原储物箱");
			}
			else if(showType==5)
			{
				setTextViewText(scan_case_item_title_tv,caseStorageData.formCode+"位置调整单");
				setTextViewText(scan_case_item_name_tv,"扫描调整后的储物箱");
			}
			else if(showType==7)
			{
				setTextViewText(scan_case_item_title_tv,caseStorageData.formCode+"暂存入库单");
				setTextViewText(scan_case_item_name_tv,"扫描物品保管箱");
			}
			else if(showType==8)
			{
				setTextViewText(scan_case_item_title_tv,caseStorageData.formCode+"暂存出库返还单");
				setTextViewText(scan_case_item_name_tv,"扫描物品保管箱");
			}
		}
		else
		{
			setTextViewText(scan_case_item_title_tv,"未知单据");
			setTextViewText(scan_case_item_name_tv," ");
		}

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

		ImageView scan_case_item_more_tools_iv=(ImageView) findViewById(R.id.scan_case_item_more_tools_iv);
		id_write_code_lay=(LinearLayout) findViewById(R.id.id_write_code_lay);
		code_text_et=(EditText) findViewById(R.id.code_text_et);
		scan_case_item_more_tools_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isShowMoreTools) {
					if(id_write_code_lay!=null) {

						MyAnimationUtil mau=new MyAnimationUtil();
						mau.viewStartAnimation(id_write_code_lay, mau.inFromRightAnimation(id_write_code_lay, 200, mau.animationShowlistener));
						id_write_code_lay.setVisibility(View.VISIBLE);
						isShowMoreTools = !isShowMoreTools;
					}
				} else {
					if(id_write_code_lay!=null) {
						id_write_code_lay.setVisibility(View.GONE);
						if(code_text_et!=null) {
							code_text_et.clearFocus();
						}
						isShowMoreTools = !isShowMoreTools;
					}
				}
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
					Result obj=new Result(tempStr,null,null,null,0);
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
					isShowMoreTools = false;
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
	protected void onPause() {
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
			Intent intent = new Intent(CaseScanShelfActivity.this, CaseArticleActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("showType", showType);//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
			mBundle.putSerializable("scanCode", caseStorageData.formCode.toUpperCase());
			intent.putExtras(mBundle);
			//startActivityForResult(intent, 106);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			finish();
		}
	}

	AlertDialog.Builder dialog=null;
    @Override
	public void handleDecode(final Result obj, Bitmap barcode)
	{
		if(inactivityTimer!=null)inactivityTimer.onActivity();
		playBeepSoundAndVibrate();

		dialog = new AlertDialog.Builder(this);
		//验证
		boolean isPass =false;
		if(caseStorageData==null)
		{
			dialog.setTitle("系统错误");
			dialog.setMessage("无法识别单据类型!");
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();;
				}
			});
			dialog.create().show();
			return;
		}
		if(this.getResources().getString(R.string.isTestShelfSearch).equals("1"))
		{
			CaseShelfData caseShelfData = new CaseShelfData();
			caseShelfData.DID = changeShelfScanCode(obj.getText().toUpperCase().trim());
			caseShelfData.lockerName="测试储物箱"+changeShelfScanCode(obj.getText().toUpperCase());
			sendSuc(dialog, caseShelfData);
			return;
		}/*else {
			CaseShelfData caseShelfData = new CaseShelfData();
			caseShelfData.DID = obj.getText().toUpperCase().trim();
			sendSuc(dialog, caseShelfData);
		}*/

		if (showType == 1|| showType == 3) {
			checkShelf(obj);
			return;
		//	dialog.setTitle("警告");
		//	dialog.setMessage("货架不在于出库单中!");
		//	dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
		//		@Override
		//		public void onClick(DialogInterface dialog, int which) {
		//			onResumeDo();
		//		}
		//	});
		//	dialog.create().show();
		//	return;
		} else if (showType == 2  || showType == 4  || showType == 8) {
			//目前这个界面只与扫货架,且只在上一个界面点下一次时出来,所以货架验证逻辑没有区别
			if (caseStorageData != null) {
				CaseShelfData caseShelfData=new CaseShelfData();
				isPass = caseStorageData.checkShelfInStorage(changeShelfScanCode(obj.getText().toUpperCase()),caseShelfData);
				if (!isPass) {
					dialog.setTitle("警告");
					dialog.setMessage("您扫描的储物箱不在于单据中!");
					dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							waitReScan();;
						}
					});
					dialog.create().show();
					return;
				}
				sendSuc(dialog,caseShelfData);
			}
		}
		else if(showType==5)
		{
			//5相当于入库,不验证架子
			checkShelf(obj);
			return;
		}
		else if(showType == 7 )
		{
			//5相当于入库,不验证架子
			checkShelf(obj);
			return;
		}
		else
		{
			dialog.setTitle("系统错误");
			dialog.setMessage("无法识别单据类型!");
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();;
				}
			});
			dialog.create().show();
			return;
		}

		if (barcode == null)
		{

			dialog.setIcon(null);
		}
		else
		{

			Drawable drawable = new BitmapDrawable(barcode);
			dialog.setIcon(drawable);
		}

	}

	private String changeShelfScanCode(CaseShelfData tempdata)
	{
		return changeShelfScanCode(tempdata.DID);
	}

	private String changeShelfScanCode(String shelfCode)
	{
		try {
			String tempStr = shelfCode.toUpperCase().trim();
			if (tempStr.indexOf("CWJ") > -1) {
				tempStr = tempStr.length() >= 3 ? tempStr.substring(3, tempStr.length()) : tempStr;
			}
			return tempStr;
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return shelfCode;
	}


	private void checkShelf(Result obj)
	{
		CaseShelfData tempdata = new CaseShelfData();
		String tempstr=obj.getText().toUpperCase().trim();
		String tempCode=tempstr.length()<3 ?tempstr : tempstr.substring(0, 3);
		if(!tempCode.equals("CWJ"))
		{
			dialog.setTitle("提示");
			String tempStr=getErrText(tempstr);
			dialog.setMessage(tempStr);
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
			dialog.create().show();
			return;
		}
		tempdata.DID = changeShelfScanCode(tempstr);
		CaseShelfSearchTask ft = null;

		if(showType==7)
		{
			ft = new CaseShelfSearchTask(this, "", tempdata,2);
			ft.setCompleteListener(new CaseShelfSearchTask.completeListener() {
				@Override
				public void completeBack(String progressId, String result, CaseShelfData caseShelfData) {
					try {
						if (result.equals("1") && caseShelfData!=null) {
								sendSuc(dialog, caseShelfData);
						} else {
							sendTemporaryFail(dialog, caseShelfData);
						}
						//CaseScanItemActivity.this.dialog.create().show();

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						CaseScanShelfActivity.this.dialog = null;
					}
				}
			});
			ft.execute();
		}
		else {
			ft = new CaseShelfSearchTask(this, "", tempdata);
			ft.setCompleteListener(new CaseShelfSearchTask.completeListener() {
				@Override
				public void completeBack(String progressId, String result, CaseShelfData caseShelfData) {
					try {
						if (result.equals("1")) {
							if (caseShelfData.DataType==1 ||
									caseStorageData.caseCode == null ||
									caseStorageData.caseCode.trim().equals("") ||
									caseShelfData.caseCode.trim().equals("") ||
									caseStorageData.caseCode.trim().equals(caseShelfData.caseCode)) {
								//没有案件(调整单)或者案件匹配的单据可用或货架没被用
								sendSuc(dialog, caseShelfData);
							} else {
								sendFailShelfIsUse(dialog, caseShelfData);
							}
						} else {
							sendFail(dialog, caseShelfData);
						}
						//CaseScanItemActivity.this.dialog.create().show();

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						CaseScanShelfActivity.this.dialog = null;
					}
				}
			});
			ft.execute();
		}

	}

	CaseShelfData caseShelfTempData =null;
	private void sendSuc(AlertDialog.Builder dialog,CaseShelfData caseShelfData)
	{
		if(dialog==null || caseShelfData==null)return;
		if(showType==1||showType==2||showType==3||showType==4||showType==5) {
			caseShelfTempData = caseShelfData;
			//dialog.setTitle("扫描结果");
			//dialog.setMessage("编码 "+caseShelfTempData.DID);// + "\r\n" + caseShelfTempData.lockerName);
			//dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			//	@Override
			//	public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent(CaseScanShelfActivity.this, CaseScanItemActivity.class);

			Bundle mBundle = new Bundle();
			mBundle.putSerializable("scanCode", caseShelfTempData.DID);
			//mBundle.putSerializable("caseShelfData", caseShelfTempData);
			mBundle.putSerializable("showType", showType);//1入 2出 3返 4调整出 5调整入 6物品详细
			if (caseStorageData != null)
				mBundle.putSerializable("CaseStorageData", caseStorageData);//1入 2出 3返 4调整
			intent.putExtras(mBundle);
			startActivity(intent);
			finish();
		}
		else 	if(showType==7)
		{
			caseShelfTempData=caseShelfData;
			dialog.setTitle("扫描结果");
			dialog.setMessage("储物箱 " + caseShelfData.lockerName);// + "\r\n" + caseShelfTempData.lockerName);
			dialog.setNegativeButton("入库", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String storageCode = caseStorageData.scanformCode;
					int caseStorageDataType = caseStorageData.DataType;
					String shelfCode = caseShelfTempData.DID;
					CaseItemPutStorageTask ft = new CaseItemPutStorageTask(CaseScanShelfActivity.this,"",storageCode,shelfCode,caseStorageDataType);
					ft.setCompleteListener(new CaseItemPutStorageTask.completeListener() {
						@Override
						public void completeBack(String progressId, String result) {
							try {
								if (result.equals("1")) {
									sendSuc();
								} else {
									sendFail();
								}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
							}
						}
					});
					ft.execute();
				}
			});
			dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
			dialog.create().show();
		}
		else 	if(showType==8)
		{
			dialog.setTitle("扫描结果");
			caseShelfTempData=caseShelfData;
			dialog.setMessage("储物箱 " + caseStorageData.getShelfName());// + "\r\n" + caseShelfTempData.lockerName);
			dialog.setNegativeButton("出库", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String storageCode = caseStorageData.scanformCode;
					int caseStorageDataType = caseStorageData.DataType;
					String shelfCode = caseShelfTempData.DID;
					CaseItemPutStorageTask ft = new CaseItemPutStorageTask(CaseScanShelfActivity.this,"",storageCode,shelfCode,caseStorageDataType);
					ft.setCompleteListener(new CaseItemPutStorageTask.completeListener() {
						@Override
						public void completeBack(String progressId, String result) {
							try {
								if (result.equals("1")) {
									sendSuc();
								} else {
									sendFail();
								}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
							}
						}
					});
					ft.execute();
				}
			});
			dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
			dialog.create().show();
		}
		//	}
	//	});
	//	dialog.setPositiveButton("重扫描", new DialogInterface.OnClickListener()
		//{
		//	@Override
		//	public void onClick(DialogInterface dialog, int which)
		//	{

		//		waitReScan();
				//finish();
		//	}
		//});
		//dialog.create().show();
	}

	private void sendSuc() {
		Toast.makeText(this, "信息已保存", Toast.LENGTH_LONG).show();
		onBackPressed();
	}

	private void sendFail() {
		Toast.makeText(this, "信息保存失败", Toast.LENGTH_LONG).show();
	}


	private void sendFailShelfIsUse(AlertDialog.Builder dialog,CaseShelfData caseShelfData)
	{
		if(dialog==null)return;
		dialog.setTitle("提示");
		//dialog.setMessage("货架" + caseShelfData.lockerName + "不可用" + "\r\n" + "货架已用于案件\r\n" + caseShelfData.caseCode);
		dialog.setMessage("您扫描的储物箱" + caseShelfData.lockerName + "不可用" + "\r\n" + "储物箱已用于其它案件");//\r\n" + caseShelfData.caseCode);
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				waitReScan();
			}
		});
		dialog.create().show();
	}

	private void sendFail(AlertDialog.Builder dialog,CaseShelfData caseShelfData)
	{
		if(dialog==null)return;
		dialog.setTitle("提示");
		String tempStr=getErrText(caseShelfData.DID);
		dialog.setMessage(tempStr);
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				waitReScan();
			}
		});
		dialog.create().show();
	}

	private void sendTemporaryFail(AlertDialog.Builder dialog,CaseShelfData caseShelfData)
	{
		if(dialog==null)return;
		dialog.setTitle("提示");
		String tempStr="";
		tempStr="您扫描的二维码无法识别，请重新扫描";
		dialog.setMessage(tempStr);
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				waitReScan();
			}
		});
		dialog.create().show();
	}

	private String getErrText(String scanCode)
	{
		String tepmpStr="";
		String tempCode=scanCode.length()<3 ? scanCode : scanCode.toUpperCase().trim().substring(0, 2);
		if(tempCode.equals("RK"))
		{
			tepmpStr="您扫描的是入库单 "+"\r\n请扫描储物箱二维码！";
		}
		else if(tempCode.equals("CK"))
		{
			tepmpStr="您扫描的是出库单 "+"\r\n请扫描储物箱二维码！";
		}
		else if(tempCode.equals("FH"))
		{
			tepmpStr="您扫描的是再入库单 "+"\r\n请扫描储物箱二维码！";
		}
		else if(tempCode.equals("TZ"))
		{
			tepmpStr="您扫描的是调整单 "+"\r\n请扫描储物箱二维码！";
		}
		//else if(tempCode.equals("CW"))
		//{
		//	tepmpStr="您扫描的是货架 "+tempCode;
		//}
		else
		{
			tepmpStr="您扫描的二维码无法识别，请重新扫描";
			//dialog.setMessage("编码" + caseShelfData.DID +"\r\n货架信息查询错误！");
		}
		return tepmpStr;
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
		if(waitReScanThread!=null &&waitReScanThread.isAlive())
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
				waitReScanShowNumber=5;
				for (int i = 0; i < 5; i++) {
					if(Thread.interrupted())return;
					try {
						Thread.sleep(1000);
						if(Thread.interrupted())return;
						waitReScanShowNumber--;
						Handler myHandler = new Handler(Looper.getMainLooper());
						myHandler.post(new Runnable() {
							@Override
							public void run() {
								//	setTextViewText(scan_case_item_name_tv, "扫描等待 "+waitReScanShowNumber);
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
						//setTextViewText(scan_case_item_name_tv, "扫描单据二维码");
						reScan();
					}
				});

			}
		});
		waitReScanThread.start();
	}
}