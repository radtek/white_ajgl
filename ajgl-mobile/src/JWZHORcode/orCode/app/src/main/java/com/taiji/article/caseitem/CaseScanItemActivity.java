package com.taiji.article.caseitem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.taiji.androidui.view.paginglistview.LoadingView;
import com.taiji.androidui.view.paginglistview.NoDataView;
import com.taiji.androidui.view.paginglistview.NoView;
import com.taiji.article.caseitem.storageData.CaseAdjustmentStorageData;
import com.taiji.article.caseitem.storageData.CaseBackStorageData;
import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import com.taiji.article.caseitem.storageData.CaseInStorageData;
import com.taiji.article.caseitem.storageData.CaseOutStorageData;
import com.taiji.article.caseitem.storageData.CaseScanData;
import com.taiji.article.caseitem.storageData.CaseShelfData;
import com.taiji.article.caseitem.storageData.CaseStorageData;
import com.taiji.article.caseitem.viewmanager.CaseItemPutStorageTask;
import com.taiji.article.caseitem.viewmanager.CaseScanAdapter;
import com.taiji.article.caseitem.viewmanager.CaseScanShelfAdapter;
import com.taiji.article.caseitem.viewmanager.CaseShelfSearchByItemViewTask;
import com.taiji.article.caseitem.viewmanager.CaseShelfSearchTask;
import com.taiji.pubsec.orcode.addressorcode.CaptureBaseActivity;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.pubsec.orcode.addressorcode.camera.MyCameraManager;
import com.taiji.util.myArith;
import com.taiji.util.MyAnimationUtil;

import java.util.ArrayList;
import java.util.List;

public class CaseScanItemActivity extends CaptureBaseActivity {
	/**
	 * Called when the activity is first created.
	 */
	CaseScanItemActivity caseInItemActivity;
	ImageView btn_scan_iv;
	LinearLayout id_write_code_lay;
	EditText code_text_et;
	ImageView btn_scan_light_iv;
	protected boolean isShowMoreTools = false;
	boolean isWriteCode=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//取消标题
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//取消状态栏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		//setContentView(R.layout.fg_case_item_in_scan_v2);

		//Rect viewRect=new Rect();
		//viewRect.s
		createBusiness();
	}

	@Override
	protected void onCreateDo() {
		setContentView(R.layout.fg_case_item_in_scan_v2);
		//setContentView(R.layout.main2);
		caseInItemActivity = this;

		btn_scan_iv = (ImageView) findViewById(R.id.btn_scan_iv);
		//	ViewGroup.LayoutParams para;
		//para = btn_scan_iv.getLayoutParams();
		//para.width = btn_scan_iv.getHeight();
		//btn_scan_iv.setLayoutParams(para);


		LinearLayout scan_case_back_v3_lay = (LinearLayout) findViewById(R.id.scan_case_back_v3_lay);
		scan_case_back_v3_lay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		btn_scan_light_iv= (ImageView) findViewById(R.id.btn_scan_light_iv);
		btn_scan_light_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLight) {
					btn_scan_light_iv.setImageDrawable(getResources().getDrawable(R.drawable.light_on2x));
					MyCameraManager.openLight();
					isLight = !isLight;
				} else {
					btn_scan_light_iv.setImageDrawable(getResources().getDrawable(R.drawable.light_off2x_b));
					MyCameraManager.closeLight();
					isLight = !isLight;
				}
			}
		});

		ImageView scan_case_item_more_tools_iv = (ImageView) findViewById(R.id.scan_case_item_more_tools_iv);
		id_write_code_lay = (LinearLayout) findViewById(R.id.id_write_code_lay);
		code_text_et = (EditText) findViewById(R.id.code_text_et);
		scan_case_item_more_tools_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isShowMoreTools) {
					isShowMoreTools = !isShowMoreTools;
					if (id_write_code_lay != null) {

						MyAnimationUtil mau=new MyAnimationUtil();
						mau.viewStartAnimation(id_write_code_lay, mau.inFromRightAnimation(id_write_code_lay, 200, mau.animationShowlistener));
						id_write_code_lay.setVisibility(View.VISIBLE);

					}
				} else {
					isShowMoreTools = !isShowMoreTools;
					if (id_write_code_lay != null) {
						id_write_code_lay.setVisibility(View.GONE);
						if (code_text_et != null) {
							code_text_et.clearFocus();
						}
					}
				}
			}
		});

		RelativeLayout id_write_code_end = (RelativeLayout) findViewById(R.id.id_write_code_end);
		id_write_code_end.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (code_text_et != null) {
					isWriteCode=true;
					String tempStr = code_text_et.getText().toString().toUpperCase();
					if(tempStr==null || tempStr.trim().equals(""))
					{

					}
					else {
						Result obj = new Result(tempStr, null, null, null, 0);
						handleDecode(obj, null);
					}
				}
				if (id_write_code_lay != null) {
					id_write_code_lay.setVisibility(View.GONE);
					isShowMoreTools=false;
					if (code_text_et != null) {
						code_text_et.clearFocus();
						//	code_text_et.setFocusable(false);
					}
				}
			}
		});

		RelativeLayout id_write_code_canecl = (RelativeLayout) findViewById(R.id.id_write_code_canecl);
		id_write_code_canecl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (id_write_code_lay != null) {
					id_write_code_lay.setVisibility(View.GONE);
					if (code_text_et != null) {
						code_text_et.clearFocus();
						//	code_text_et.setFocusable(false);
					}
				}
			}
		});

		initCameraManager(null);
	}

	boolean isLoadView = true;

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {
			if (isLoadView) {
				isLoadView = false;
				loadCameraManager();
			}
		}
	}


	Thread waitLoadView;

	@Override
	protected void initCameraManager(Rect viewRect) {
		//if(isLoadView) return;
		super.initCameraManager(viewRect);
		//waitLoadView.start();
	}

	private void loadCameraManager() {
		if (btn_scan_iv != null) {
			;

			int[] location = new int[2];
			//btn_scan_iv.getLocationOnScreen(location);//包含了通知栏
			btn_scan_iv.getLocationInWindow(location);
			//取状态栏高度
			Rect rectangle = new Rect();
			getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);

			int x = location[0];
			int y = location[1] - rectangle.top;
			Rect viewRect = new Rect(x, y, x + btn_scan_iv.getWidth(), y + btn_scan_iv.getHeight());
			initCameraManager(viewRect);
		} else {
			initCameraManager(null);
		}
	}

	int Backnumber = 1;

	@Override
	public void onBackPressed() {
		if (Backnumber == 1) {
			//   super.onBackPressed();
			Intent intent = new Intent(CaseScanItemActivity.this, CaseArticleActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("showType", getCaseStorageData().getDataType());//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
			mBundle.putSerializable("scanCode", getCaseStorageData().formCode.toUpperCase());
			intent.putExtras(mBundle);
			//startActivityForResult(intent, 106);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			finish();
		}
	}

	@Override
	protected void onResume() {
		initRect(null);
		super.onResume();

	}

	@Override
	protected void onPause() {
		try {
			if (waitReScanThread != null && waitReScanThread.isAlive()) {
				waitReScanThread.interrupt();
				waitReScanThread = null;
			}
		} catch (Exception ex) {

		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (caseScanAdapter == null) {
			caseScanAdapter.UnCaseInScanAdapter();
			//caseScanAdapter=null;
		}
	}

	@Override
	protected void initRect(Rect viewRect) {
		if (btn_scan_iv != null) {
			int[] location = new int[2];
			//btn_scan_iv.getLocationOnScreen(location);//包含了通知栏
			btn_scan_iv.getLocationInWindow(location);
			//取状态栏高度
			Rect rectangle = new Rect();
			getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);

			int x = location[0];
			int y = location[1] - rectangle.top;
			viewRect = new Rect(x, y, x + btn_scan_iv.getWidth(), y + btn_scan_iv.getHeight());
			super.initRect(viewRect);
		}
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		super.surfaceChanged(holder, format, width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		super.surfaceDestroyed(holder);
	}

	AlertDialog.Builder dialog = null;

	@Override
	public void handleDecode(final Result obj, Bitmap barcode) {
		if(inactivityTimer!=null)inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		dialog = new AlertDialog.Builder(this);
		if (barcode == null) {
			dialog.setIcon(null);
		} else {

			Drawable drawable = new BitmapDrawable(barcode);
			dialog.setIcon(drawable);
		}
		/*
		final float scale = this.getResources().getDisplayMetrics().density;
		String aaa=(197 / scale + 0.5f)+"";
		String aaa2=(271 / scale + 0.5f)+"";
		Toast.makeText(this, aaa+"-"+aaa2, Toast.LENGTH_LONG).show();
		*/
		if (isAddShelfMode) {
			//货架处理
			handleDecodeShelf(obj, dialog);
		} else {
			String tempStr=obj.getText().length()<3 ? obj.getText() : obj.getText().toUpperCase().trim().substring(0, 2);
			//String tempStr=obj.getText().toUpperCase().trim().substring(0,2);
			if(tempStr.equals("WP")
					||getResources().getString(R.string.isTestCaseItemIn).equals("1")
					||getResources().getString(R.string.isTestCaseItemOut).equals("1")
					||getResources().getString(R.string.isTestCaseItemBack).equals("1")
					||getResources().getString(R.string.isTestCaseItemBack).equals("1")
					)
			{
				handleDecodeItem(obj, dialog);
			}
			else {
				tryHandleDecode(obj, dialog);
			}

			//物品处理
		}
		/*
		dialog.setTitle("扫描结果");
		dialog.setMessage(obj.getText());
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//用默认浏览器打开扫描得到的地址
				Intent intent = new Intent(CaseScanItemActivity.this, CaseScanShelfActivity.class);
				startActivity(intent);
				finish();
			}
		});
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				onResumeDo();
				//finish();
			}
		});
		dialog.create().show();
		*/
	}


	private void setTextViewText(TextView tview, String text) {
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
	final int waitReScanNumber = 2;
	int waitReScanShowNumber = 2;

	protected void waitReScan() {
		if(isWriteCode)
		{
			isWriteCode=false;
			return;
		}
		if (waitReScanThread != null && waitReScanThread.isAlive()) {
			waitReScanThread.interrupt();
			waitReScanThread = null;
		}
		waitReScanThread = new Thread(new Runnable() {
			@Override
			public void run() {
				//	Handler mainHandler = new Handler(Looper.getMainLooper());
				//mainHandler.post(new Runnable() {
				//		@Override
				//		public void run() {
				waitReScanShowNumber = waitReScanNumber;
				for (int i = 0; i < waitReScanNumber; i++) {
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


	ListView mListView;
	CaseScanAdapter caseScanAdapter;
	CaseScanData useSelectScanData;//当前选择的货架
	private NoDataView noDataView;
	private LoadingView loadingView;

	NoView noVIew;
	int showType = -99;
	String scanText;
	CaseInStorageData caseInStorageData;
	CaseOutStorageData caseOutStorageData;
	CaseBackStorageData caseBackStorageData;
	CaseAdjustmentStorageData caseAdjustmentStorageData;
	CaseShelfData scanShelfData = null;
	boolean isAddShelfMode = false;//当前扫描是否扫货架

	private void createBusiness() {
		Intent intent = getIntent();
		try {
			scanText = (String) intent.getSerializableExtra("scanCode");
			showType = (int) intent.getSerializableExtra("showType");
			/*
			try {
				scanShelfData = (CaseShelfData) intent.getSerializableExtra("caseShelfData");
			}catch (Exception ex)
			{

			}
			*/
			CaseStorageData caseStorageData = (CaseStorageData) intent.getSerializableExtra("CaseStorageData");
			TextView scan_case_item_Storage_name_tv = (TextView) findViewById(R.id.scan_case_item_Storage_name_tv);
			if (showType == 1) {
				caseInStorageData = (CaseInStorageData) caseStorageData;
				scan_case_item_Storage_name_tv.setText(caseStorageData.scanformCode+"入库单");
			} else if (showType == 2) {
				caseOutStorageData = (CaseOutStorageData) caseStorageData;
				scan_case_item_Storage_name_tv.setText(caseStorageData.scanformCode+"出库单");
			} else if (showType == 3) {
				caseBackStorageData = (CaseBackStorageData) caseStorageData;
				scan_case_item_Storage_name_tv.setText(caseStorageData.scanformCode+"再入库单");
			} else if (showType == 4 || showType == 5) {
				caseAdjustmentStorageData = (CaseAdjustmentStorageData) caseStorageData;
				if(showType == 4)
				  scan_case_item_Storage_name_tv.setText(caseStorageData.scanformCode+"调整单-出库");
				else
                  scan_case_item_Storage_name_tv.setText(caseStorageData.scanformCode+"调整单-入库");
			}
		} catch (Exception e) {
		}

		if (caseScanAdapter == null) {
			caseScanAdapter = new CaseScanAdapter(this);
			caseScanAdapter.setViewReshListener(new CaseScanAdapter.myViewReshListener() {
				@Override
				public void myViewResh(int position, CaseScanData caseScanData) {
					CaseScanItemActivity.this.myViewResh(position, caseScanData);
				}

				@Override
				public View myViewGetItemView(CaseScanData caseScanData) {
					if (mListView != null && caseScanData != null && caseScanData.caseShelfData != null) {
						int showItemCount = mListView.getChildCount();
						View v = null;
						for (int i = 0; i < showItemCount; i++) {
							try {
								v = mListView.getChildAt(i);
								if (v != null) {
									CaseScanData tempData = (CaseScanData) v.getTag();
									if (tempData != null && tempData.caseShelfData != null) {
										if (tempData.caseShelfData.DID.equals(caseScanData.caseShelfData.DID)) {
											return v;
										}
									}
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
					return null;
				}

				@Override
				public void removeTempItem(CaseScanShelfAdapter caseScanShelfAdapter, CaseData CaseData,boolean isShowDialog) {
					CaseScanItemActivity.this.removeTempItem(caseScanShelfAdapter, CaseData, isShowDialog);
				}

				//找本操作对象的可操作最大数量
				public double  findTempItemMaxChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,String shelfCode,CaseScanAdapter caseScanAdapter)
				{
					double surplusCount = CaseScanItemActivity.this.findTempItemMaxChangeCount(caseScanShelfAdapter, CaseData,shelfCode,caseScanAdapter);
					return surplusCount;
				}

				//修改数量,true修改成功 false修改异常
				public boolean  itemChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,double newCount,String shelfCode,CaseScanAdapter caseScanAdapter)
				{
					boolean isPass = CaseScanItemActivity.this.itemChangeCount(caseScanShelfAdapter, CaseData, newCount,shelfCode, caseScanAdapter);
					return isPass;
				}
			});
		}
		noVIew = new NoView(this);
		mListView = (ListView) findViewById(R.id.case_item_info_scan_lv);
		mListView.addFooterView(noVIew);
		mListView.setAdapter(caseScanAdapter);
		noDataView = new NoDataView(this);
		loadingView = new LoadingView(this);
		refreshOldData();

		ImageView btn_in_item_new_shelf_iv = (ImageView) findViewById(R.id.btn_in_item_new_shelf_iv);
		btn_in_item_new_shelf_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isAddShelfMode) {
					Toast.makeText(CaseScanItemActivity.this, "请扫描货架", Toast.LENGTH_LONG).show();
					return;
				}
				isAddShelfMode = true;
				CaseScanData tempc3 = new CaseScanData();
				tempc3.caseShelfData = new CaseShelfData();
				tempc3.caseShelfData.DataType = 1;
				tempc3.caseShelfData.DID = "test" + (caseScanAdapter.getCount() + 1);
				tempc3.caseShelfData.lockerName = "待扫描货架" + (caseScanAdapter.getCount() + 1);
				tempc3.DataType = 1;
				tempc3.caseShelfData.list_CaseData = new ArrayList<CaseData>();
				List<CaseScanData> newItems = new ArrayList<CaseScanData>();
				newItems.add(tempc3);
				//	noVIew.getHeight();
				//	mListView.smoothScrollByOffset(99999);
				//mListView.scrollTo(0, 0);//mListView.get

				//新加货架放到最上面,放到下面需要滚动并刷新数据,ListView两个异步操作导致数据异常无法解决
				caseScanAdapter.addMoreItemsFist(newItems);
				mListView.smoothScrollToPositionFromTop(0, 0);//这个方法如果list 有header,也算position一部分
				//mListView.setSelection(mListView.getBottom());

			}
		});

		ImageView btn_fin_in_item_iv = (ImageView) findViewById(R.id.btn_fin_in_item_iv);
		if (showType == 1) {
			btn_fin_in_item_iv.setImageResource(R.drawable.btn_fin_in_item);
		} else if (showType == 2) {
			btn_fin_in_item_iv.setImageResource(R.drawable.btn_fin_out_item);
			//btn_in_item_new_shelf_iv.setVisibility(View.GONE);
		} else if (showType == 3) {
			btn_fin_in_item_iv.setImageResource(R.drawable.btn_fin_back_item);
		} else if (showType == 4 || showType == 5) {
			if(showType == 4) {
				btn_fin_in_item_iv.setImageResource(R.drawable.btn_fin_adjustment_out_item);
				//btn_in_item_new_shelf_iv.setVisibility(View.GONE);
			}
			else
				btn_fin_in_item_iv.setImageResource(R.drawable.btn_fin_adjustment_in_item);
		}
		btn_fin_in_item_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				AlertDialog.Builder dialog = new AlertDialog.Builder(CaseScanItemActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("提交本次操作信息?");
				dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						doStorageUpdate();
					}
				});
				dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				dialog.create().show();

			}
		});
	}

	public void refreshOldData() {
		if (getResources().getString(R.string.isTestScanCaseItem).equals("1")) {

			refreshTestData();
			return;
		} else {
			mListView.addHeaderView(loadingView);
			List<CaseShelfData> l_CaseShelfData = null;
			CaseStorageData tempCaseStorageData = getCaseStorageData();
			if (tempCaseStorageData != null) {
				l_CaseShelfData = tempCaseStorageData.createShelfData();
			}
			/*
			if(showType==1)
			{
				l_CaseShelfData = caseInStorageData.createShelfData();
			}
			else if(showType==2)
			{
				l_CaseShelfData = caseOutStorageData.createShelfData();
			}
			else if(showType==3)
			{
				l_CaseShelfData = caseBackStorageData.createShelfData();
			}
			else if(showType==4 || showType==5)
			{
				l_CaseShelfData = caseAdjustmentStorageData.createShelfData();
			}
			*/
			List<CaseScanData> l_CaseScanData = new ArrayList<CaseScanData>();
			if (l_CaseShelfData != null) {
				CaseScanData caseScanData;
				for (int i = 0; i < l_CaseShelfData.size(); i++) {
					caseScanData = new CaseScanData(l_CaseShelfData.get(i));
					l_CaseScanData.add(caseScanData);
				}
			}

			boolean isFindscan = false;
			CaseScanData scanCaseScanData = null;
			if (scanText != null && !scanText.trim().equals(""))
			{
				for (int i = 0; i < l_CaseScanData.size(); i++) {
					if (l_CaseScanData.get(i).caseShelfData.DID.equals(scanText.trim())) {
						isFindscan = true;
						if (i == 0) {
							scanCaseScanData = l_CaseScanData.get(0);
							break;
						}
						CaseScanData tempData = l_CaseScanData.get(i);
						l_CaseScanData.set(i, l_CaseScanData.get(0));
						l_CaseScanData.set(0, tempData);
						scanCaseScanData = l_CaseScanData.get(0);
						break;
					}
				}
				if (!isFindscan) {
					if (this.getResources().getString(R.string.isTestShelfSearch).equals("1")) {
						//查询...
						CaseScanData tempc3 = new CaseScanData();
						tempc3.caseShelfData = new CaseShelfData();
						tempc3.caseShelfData.DataType = 1;
						tempc3.caseShelfData.DID = scanText;
						tempc3.caseShelfData.lockerName = "新储物箱" + scanText;
						tempc3.caseShelfData.list_CaseData = new ArrayList<CaseData>();
						l_CaseScanData.add(0, tempc3);
						scanCaseScanData = tempc3;
						useSelectScanData = tempc3;
					} else {
						CaseShelfData caseShelfData = new CaseShelfData();
						caseShelfData.DID = scanText;
						CaseShelfSearchByItemViewTask ft = new CaseShelfSearchByItemViewTask(this, "", caseShelfData, l_CaseScanData, caseScanAdapter);
						ft.setCompleteListener(new CaseShelfSearchByItemViewTask.completeListener() {
							@Override
							public void completeBack(String progressId, String result, CaseShelfData caseShelfData, List<CaseScanData> l_CaseScanData, CaseScanAdapter caseScanAdapter) {
								try {
									if (result.equals("1")) {
										CaseScanData tempc3 = new CaseScanData();
										tempc3.caseShelfData = caseShelfData;
										tempc3.caseShelfData.DataType = 1;
										tempc3.caseShelfData.DID = scanText;
										tempc3.caseShelfData.list_CaseData = new ArrayList<CaseData>();
										l_CaseScanData.add(0, tempc3);
										useSelectScanData = tempc3;

										caseScanAdapter.addMoreItems(l_CaseScanData);
										mListView.removeHeaderView(loadingView);
										caseScanAdapter.notifyDataSetChanged();
										if (tempc3 != null) {
											//if (isFindscan) {
											caseScanAdapter.setSelectShelf(tempc3);
										}
									} else {
										Toast.makeText(CaseScanItemActivity.this, "储物箱信息加载错误!", Toast.LENGTH_LONG).show();
										caseScanAdapter.addMoreItems(l_CaseScanData);
										mListView.removeHeaderView(loadingView);
										caseScanAdapter.notifyDataSetChanged();
									}

								} catch (Exception e) {
									e.printStackTrace();
								} finally {
								}
							}
						});
						ft.execute();
					}
				}
				else if (this.getResources().getString(R.string.isTestShelfSearch).equals("0")) {
                    //不需要查询直接加载
					WaitTask task = new WaitTask(scanCaseScanData, l_CaseScanData, caseScanAdapter);
					task.execute(5, 5);
				}
				//查询
			}
			if (this.getResources().getString(R.string.isTestShelfSearch).equals("1")) {

				WaitTask task = new WaitTask(scanCaseScanData, l_CaseScanData, caseScanAdapter);
				task.execute(5, 5);
				/*
				caseScanAdapter.addMoreItems(l_CaseScanData);
				mListView.removeHeaderView(loadingView);
				caseScanAdapter.notifyDataSetChanged();
				if (scanCaseScanData != null) {
					//if (isFindscan) {
					caseScanAdapter.setSelectShelf(scanCaseScanData);
					//mListView.smoothScrollToPosition(0);
					//mListView.smoothScrollToPositionFromTop(0,0);
					////} else {
					//caseScanAdapter.setSelectShelf(0);
					//}
				}
				*/
			}
		}

	}

	private CaseStorageData getCaseStorageData() {
		if (showType == 1) {
			return caseInStorageData;
		} else if (showType == 2) {
			return caseOutStorageData;
		} else if (showType == 3) {
			return caseBackStorageData;
		} else if (showType == 4 || showType == 5) {
			return caseAdjustmentStorageData;
		}
		return null;
	}

	private void refreshTestData() {
		if (getResources().getString(R.string.isTestScanCaseItem).equals("1")) {
			mListView.addHeaderView(loadingView);
			FetchItemsTask task = new FetchItemsTask();
			task.execute(5, 5);
		}
	}

	//不是物品编码时尝试单据或货架,如果是货架又在列表中,则选中货架
	private void tryHandleDecode(final Result obj, AlertDialog.Builder dialog)
	{
		//验证货架
		String tempStr=obj.getText().length()<3 ? obj.getText() : obj.getText().toUpperCase().trim().substring(0, 2);
		dialog.setTitle("扫描结果");
		if(tempStr.equals("RK"))
		{
			showStorageDecode("您扫描的是入库单 "+"\r\n请扫描物品二维码!",dialog);
		}
		else if(tempStr.equals("CK"))
		{
			showStorageDecode("您扫描的是出库单 "+"\r\n请扫描物品二维码!",dialog);
		}
		else if(tempStr.equals("FH"))
		{
			showStorageDecode("您扫描的是再入库单 "+"\r\n请扫描物品二维码!",dialog);
		}
		else if(tempStr.equals("TZ"))
		{
			showStorageDecode("您扫描的是调整单 "+"\r\n请扫描物品二维码!",dialog);
		}
		else if(tempStr.equals("CW")){
			boolean isFindShelf = false;
			CaseShelfData caseShelfData = new CaseShelfData();
			String tempShelfCode=changeShelfScanCode(obj.getText().toUpperCase());
			isFindShelf = getCaseStorageData().checkShelfInStorage(tempShelfCode, caseShelfData);
			if (isFindShelf && caseScanAdapter != null && caseShelfData != null) {
				//不使用上面操作返回的货架caseShelfData,因为原数据来的货架没有物品,所以从listview里取
				int checkShelfPosition = caseScanAdapter.checkShelf(tempShelfCode);
				if (checkShelfPosition > -1) {
					dialog.setMessage("是否将储物箱 " + caseScanAdapter.getItem(caseScanAdapter.checkShelf(tempShelfCode)).caseShelfData.lockerName+ " 指定为放置物品的储物箱?");
					dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								//因为上面删了一个要重算索引
								caseScanAdapter.setSelectShelf(caseScanAdapter.getItem(caseScanAdapter.checkShelf(changeShelfScanCode(obj.getText().toUpperCase()))));

							} catch (Exception ex) {
								ex.printStackTrace();
							}
							waitReScan();
						}
					});
					dialog.setPositiveButton("重扫描", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							waitReScan();
							//finish();
						}
					});
					dialog.create().show();
				} else {
					dialog.setMessage("您扫描的储物箱编码不在单据中!");
					dialog.setNegativeButton("重扫描", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							waitReScan();
						}
					});
					dialog.create().show();
				}
			}
		}
		else
		{
			dialog.setMessage("您扫描的二维码无法识别，请重新扫描");//未知的物品编码 " + obj.getText().toUpperCase()+"\r\n请确认扫描的物品是否在单据中!");
			dialog.setNegativeButton("重扫描", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
			dialog.create().show();
		}
	}

	private void showStorageDecode(String  textStr, AlertDialog.Builder dialog)
	{
		dialog.setMessage(textStr);
		dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				waitReScan();
			}
		});
		dialog.create().show();
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

	private void handleDecodeShelf(final Result obj, AlertDialog.Builder dialog) {
		if (dialog == null) return;
		dialog.setTitle("储物箱扫描结果");
		//验证货架
		//1已在列表 删除新增临时项 ,并焦点打开存在
		String shelfCode = obj.getText().toUpperCase().trim();
		String tempCode=shelfCode.length()<3 ?shelfCode : shelfCode.substring(0, 2);
		if (!tempCode.equals("CW"))//判断编码是否为货架
		{
			dialog.setMessage("您扫描的不是储物箱编码，请重新扫描");//obj.getText().toUpperCase() + " 不是货架编码!");
			dialog.setNegativeButton("重扫描", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
			dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						caseScanAdapter.removeTempShelf();
						isAddShelfMode = false;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					waitReScan();
				}
			});
			dialog.create().show();
		} else {
			//验证货架
			boolean isFindShelf = false;
			CaseShelfData caseShelfData = new CaseShelfData();
			String tempShelfCode=changeShelfScanCode(obj.getText().toUpperCase());
			isFindShelf = getCaseStorageData().checkShelfInStorage(tempShelfCode, caseShelfData);
			if (!isFindShelf) {
				dialog.setMessage("您扫描的储物箱不在单据中");//" + obj.getText().toUpperCase() + "
				dialog.setNegativeButton("重扫描", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						waitReScan();
					}
				});
				dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							caseScanAdapter.removeTempShelf();
							isAddShelfMode = false;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						waitReScan();
					}
				});
				dialog.create().show();
			} else if (caseScanAdapter != null) {
				//不使用上面操作返回的货架caseShelfData,因为原数据来的货架没有物品,所以从listview里取
				int checkShelfPosition = caseScanAdapter.checkShelf(tempShelfCode);
				if (checkShelfPosition > -1) {
					dialog.setMessage("您扫描的储物箱已在列表中");//" + obj.getText().toUpperCase() + "
					dialog.setNegativeButton("选中储物箱", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								caseScanAdapter.removeTempShelf();
								isAddShelfMode = false;
								//因为上面删了一个要重算索引
								caseScanAdapter.setSelectShelf(caseScanAdapter.getItem(caseScanAdapter.checkShelf(changeShelfScanCode(obj.getText().toUpperCase()))));

							} catch (Exception ex) {
								ex.printStackTrace();
							}
							waitReScan();
							//int ii=0;
							//ii=1;
						}
					});
					dialog.setPositiveButton("重扫描", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							waitReScan();
							//finish();
						}
					});
					dialog.create().show();
				} else {
					//String shelfname = obj.getText().toUpperCase();
					//查询货架task
					CaseShelfData tempdata = new CaseShelfData();
					tempdata.DID = tempShelfCode;//obj.getText().toUpperCase().trim();
					if (this.getResources().getString(R.string.isTestShelfSearch).equals("1"))
					{
						tempdata.lockerName="新储物箱"+tempShelfCode;//obj.getText().toUpperCase();
						sendSuc(CaseScanItemActivity.this.dialog, tempdata);
					}
					else {
						CaseShelfSearchTask ft = new CaseShelfSearchTask(this, "", tempdata);
						ft.setCompleteListener(new CaseShelfSearchTask.completeListener() {
							@Override
							public void completeBack(String progressId, String result, CaseShelfData caseShelfData) {
								try {

									if (result.equals("1")) {
										if(caseShelfData.DataType==1 ||
												getCaseStorageData().caseCode==null ||
												getCaseStorageData().caseCode.trim().equals("")||
												caseShelfData.caseCode.trim().equals("")||
												getCaseStorageData().caseCode.trim().equals(caseShelfData.caseCode))
										{
											//没有案件(调整单)或者案件匹配的单据可用或货架没被用
											sendSuc(CaseScanItemActivity.this.dialog, caseShelfData);
										}
										else {
											sendFailShelfIsUse(CaseScanItemActivity.this.dialog, caseShelfData);
										}

									} else {
										sendFail(CaseScanItemActivity.this.dialog, caseShelfData);
									}
									//CaseScanItemActivity.this.dialog.create().show();

								} catch (Exception e) {
									e.printStackTrace();
									sendErr(CaseScanItemActivity.this.dialog);
								} finally {
									//CaseScanItemActivity.this.dialog = null;
								}
							}
						});
						ft.execute();

					}

					/*
					dialog.setPositiveButton("重扫描", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							waitReScan();
							//finish();
						}
					});
					*/

				}
			}
		}

	}

	CaseShelfData caseShelfTempData = null;

	private void sendSuc(AlertDialog.Builder dialog, CaseShelfData caseShelfData) {
		if (dialog == null || caseShelfData == null) return;
		caseShelfTempData = caseShelfData;
		//dialog.setMessage(caseShelfTempData.DID + "\r\n" + "货架 " + caseShelfTempData.lockerName);
		dialog.setMessage("您扫描的是储物箱 " + caseShelfTempData.lockerName);
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					int dataIndex = caseScanAdapter.UpdateTempShelf(caseShelfTempData.DID, caseShelfTempData.lockerName,caseShelfTempData.caseCode,caseShelfTempData.areaCode,caseShelfTempData.areaName);
					if (dataIndex > -1) {
						caseScanAdapter.setSelectShelf(caseScanAdapter.getItem(dataIndex));
						isAddShelfMode = false;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					caseShelfTempData = null;
					waitReScan();
				}
			}
		});
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					caseScanAdapter.removeTempShelf();
					isAddShelfMode = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					caseShelfTempData = null;
				}
				waitReScan();
			}
		});
		dialog.create().show();
	}

	private void sendFailShelfIsUse(AlertDialog.Builder dialog,CaseShelfData caseShelfData)
	{
		if(dialog==null)dialog = new AlertDialog.Builder(this);;
		dialog.setTitle("提示");
		//dialog.setMessage("您扫描的货架" + caseShelfData.DID + "不可用" + "\r\n" + "货架已用于案件\r\n" + caseShelfData.caseCode);
		dialog.setMessage("您扫描的储物箱不可用" + "\r\n" + "储物箱已用于其它案件\r\n");// + caseShelfData.caseCode);
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				waitReScan();
			}
		});
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					caseScanAdapter.removeTempShelf();
					isAddShelfMode = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				waitReScan();
			}
		});
		dialog.create().show();
	}

	private void sendErr(AlertDialog.Builder dialog) {
		if (dialog == null) dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提示");
		dialog.setMessage("系统异常！");
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				waitReScan();
			}
		});
		dialog.create().show();
	}

	private void sendFail(AlertDialog.Builder dialog, CaseShelfData caseShelfData) {
		if (dialog == null) return;
		dialog.setTitle("提示");
		//dialog.setMessage("编码" + caseShelfData.DID + "\r\n货架信息查询错误！");
		dialog.setMessage("系统异常,您扫描的二维码无法识别");
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				waitReScan();
			}
		});
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					caseScanAdapter.removeTempShelf();
					isAddShelfMode = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				waitReScan();
			}
		});
		dialog.create().show();
	}


	private void handleDecodeItem(final Result obj, AlertDialog.Builder dialog) {
		if (dialog == null) return;
		dialog.setTitle("物品扫描结果");
		String shelfCode = obj.getText().trim().toUpperCase();
		String tempCode=shelfCode.length()<3 ?shelfCode : shelfCode.substring(0, 2);
		if (tempCode.equals("CW"))//判断编码是否为货架
		{
			dialog.setMessage("您扫描的是储物箱编码，请扫描物品！");//obj.getText().toUpperCase() + " 不是货架编码!");
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
		} else if (useSelectScanData == null || useSelectScanData.caseShelfData == null) {
			dialog.setMessage("请先选择一个储物箱,再扫描物品！");
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
		} else {
			//验证物品是否在单子里
			//验证货架
			boolean isFindItem = false;
			CaseData caseData = null;
			isFindItem = getCaseStorageData().checkItemInStorage(obj.getText().toUpperCase(), null);
			if (!isFindItem) {
				//dialog.setMessage("物品 " + obj.getText().toUpperCase() + " 不在单据中");
				dialog.setMessage("您扫描的物品不在单据中！");
				dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						waitReScan();
					}
				});
			} else
			{
					caseData = new CaseData();
					caseData.ViewState=getCaseStorageData().getDataType();
					isFindItem = getCaseStorageData().checkItemInShelfStorage(obj.getText().toUpperCase(), useSelectScanData.caseShelfData.DID, caseData);
					if (!isFindItem) {
						//dialog.setMessage("物品 " + obj.getText().toUpperCase() + " 不属于当前货架");
						dialog.setMessage("您扫描的物品不属于当前储物箱！");
						dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								waitReScan();
							}
						});
					} else if (caseScanAdapter != null) {
						String[] outStr=new String[]{""};
						boolean isMaxCount =getCaseStorageData().checkItemOperand( obj.getText().toUpperCase(), useSelectScanData.caseShelfData.DID,caseScanAdapter.getAllNewCaseItem(),outStr);
						if(isMaxCount)
						{
							String tempShowStr="";
							if( caseData.caeItemName.equals(""))
							{
								tempShowStr="物品 数量超过最大值！";
							}
							else
							{
								tempShowStr=caseData.caeItemName + " 数量超过最大值！";
							}
							if(outStr!=null && !outStr[0].equals(""))
							{

								dialog.setMessage(tempShowStr+"\r\n"+outStr[0]);
							}
							else
							{
								dialog.setMessage(tempShowStr);
							}

							dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									waitReScan();
								}
							});
						}
						else
						{
                            //是否同区
							isFindItem = getCaseStorageData().checkItemInSameArea(obj.getText().toUpperCase(), useSelectScanData.caseShelfData.areaCode, caseScanAdapter.getAllNewCaseItem(), outStr);

							if(getResources().getString(R.string.isTestCaseItemIn).equals("1")
									||getResources().getString(R.string.isTestCaseItemOut).equals("1")
									||getResources().getString(R.string.isTestCaseItemBack).equals("1")
									||getResources().getString(R.string.isTestCaseItemBack).equals("1")
									||getResources().getString(R.string.isTestShelfSearch).equals("1")
									)
							{
								isFindItem=true;//测试强制设置true
							}

							if (!isFindItem) {
								dialog.setMessage("物品 "+ caseData.caeItemName + "\r\n"
										+"无法放入储物箱 "+useSelectScanData.caseShelfData.lockerName+ "\r\n"
										+ outStr[0]
								);
								dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										waitReScan();
									}
								});
							}
							else if (showType == 1 || showType == 3 || showType == 5) {
								//验证物品是否放在同案件的物品所在货架或空货架
								isFindItem =checkItemSameCase(caseData);

								if(getResources().getString(R.string.isTestCaseItemIn).equals("1")
										||getResources().getString(R.string.isTestCaseItemOut).equals("1")
										||getResources().getString(R.string.isTestCaseItemBack).equals("1")
										||getResources().getString(R.string.isTestCaseItemBack).equals("1")
										)
								{
									isFindItem=true;//测试强制设置true
								}

								if (!isFindItem) {
									dialog.setMessage("物品 "+ caseData.caeItemName + "\r\n"
													+"无法放入储物箱\r\n"
													+"储物箱已存放其它案件的物品\r\n"
													+"请勿将不同案件的物品混合存放！"
									);
									dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											waitReScan();
										}
									});
								}
								else{
									scanItemIn(obj, dialog, caseData);
								}


							} else if (showType == 2 || showType == 4) {
								scanItemOut(obj, dialog);
							}
						}
					}
				}
		}

		dialog.create().show();
	}
	private boolean checkItemSameCase(CaseData caseData)
	{
		if(caseData==null)
		{
			return false;
		}
		if(useSelectScanData!=null)
		{
			if(useSelectScanData.caseShelfData.DataType==1||
					useSelectScanData.caseShelfData.caseCode==null||
					useSelectScanData.caseShelfData.caseCode.trim().equals("")||
					useSelectScanData.caseShelfData.caseCode.trim().equals(caseData.caseCode))
			{
				//System.out.print("ShelfcaseCode="+useSelectScanData.caseShelfData.caseCode+"|ItemcaseCode="+caseData.caseCode);
				//System.out.println();
              return true;
			}
		}
		return false;
	}

	CaseData scanItemTempData = null;

	private void scanItemIn(final Result obj, AlertDialog.Builder dialog, CaseData caseData) {
		CaseData tempData = caseScanAdapter.checkShelfItem(obj.getText().toUpperCase(), useSelectScanData);
		scanItemTempData = tempData;
     	if (tempData != null) {
			//dialog.setMessage("物品编码 " + obj.getText());
			String tempStr=null;
			if(useSelectScanData!=null &&useSelectScanData.caseShelfData!=null)
			{
				dialog.setMessage("您要将物品 " + tempData.caeItemName +"\r\n"+"放入 "+useSelectScanData.caseShelfData.lockerName);
				tempStr="取消";
			}
			else
			{
				dialog.setMessage("您要将物品 " + tempData.caeItemName);
				tempStr="重扫描";
			}
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {

						CaseData tempData = caseScanAdapter.checkShelfItem(obj.getText().toUpperCase(), useSelectScanData);
						if (tempData != null) {
							//tempData.scanCount += 1;
							//tempData.DataType = 1;
							double surplusCount = getCaseStorageData().findItemMaxChangeCount(obj.getText().toUpperCase(), useSelectScanData.caseShelfData.DID, caseScanAdapter.getAllNewCaseItem());
							caseScanAdapter.UpdateShelfItem(tempData, useSelectScanData, false,surplusCount);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					waitReScan();
				}
			});
			dialog.setPositiveButton(tempStr, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
					//finish();
				}
			});
		} else {

			scanItemTempData = caseData;
			//	scanItemTempData=new CaseData();
			//	scanItemTempData.DID = obj.getText().trim();
			//	scanItemTempData.caseItemName = "新手机手机";
			//	scanItemTempData.earmark =  "花色BOOB";
			//	scanItemTempData.caseItemNumber = obj.getText().trim();
			//	scanItemTempData.ViewState= showType;
			//	scanItemTempData.caseItemCountString ="5个";
			//	scanItemTempData.caseDoItemCount=1;
			scanItemTempData.lockerCode = useSelectScanData.caseShelfData.DID;
			scanItemTempData.lockerName = useSelectScanData.caseShelfData.lockerName;
			scanItemTempData.areaCode=useSelectScanData.caseShelfData.areaCode;
			scanItemTempData.areaName=useSelectScanData.caseShelfData.areaName;
			if (scanItemTempData != null) {
				String tempStr=null;
				if(useSelectScanData!=null &&useSelectScanData.caseShelfData!=null)
				{
					dialog.setMessage("您要将物品 " + scanItemTempData.caeItemName +"\r\n"+"放入 "+useSelectScanData.caseShelfData.lockerName);
					tempStr="取消";
				}
				else
				{
					dialog.setMessage("您要将物品 " + scanItemTempData.caeItemName);
					tempStr="重扫描";
				}
				//dialog.setMessage("物品 " + scanItemTempData.caseItemName);
				dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							if (scanItemTempData != null) {
								//scanItemTempData.scanCount += 1;
								//scanItemTempData.DataType = 1;
								double surplusCount = getCaseStorageData().findItemMaxChangeCount(obj.getText().toUpperCase(), useSelectScanData.caseShelfData.DID, caseScanAdapter.getAllNewCaseItem());
								caseScanAdapter.UpdateShelfItem(scanItemTempData, useSelectScanData, false,surplusCount);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						waitReScan();
					}
				});
				dialog.setPositiveButton(tempStr, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						waitReScan();
						//finish();
					}
				});
			}
		}

	}

	private void scanItemOut(final Result obj, AlertDialog.Builder dialog) {
		CaseData tempData = caseScanAdapter.checkShelfItem(obj.getText().toUpperCase(), useSelectScanData);
		scanItemTempData = tempData;
		String[] outStr=new String[]{""};
	    if (tempData != null) {
			//dialog.setMessage("物品编码 " + obj.getText());
			String tempStr=null;
			if(useSelectScanData!=null &&useSelectScanData.caseShelfData!=null)
			{
				dialog.setMessage("您正从储物箱 "+useSelectScanData.caseShelfData.lockerName+"\r\n"+"取出 " + scanItemTempData.caeItemName );
				tempStr="取消";
			}
			else
			{
				dialog.setMessage("物品 " + scanItemTempData.caeItemName);
				tempStr="重扫描";
			}
			//dialog.setMessage("物品 " + tempData.caseItemName);
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {

						CaseData tempData = caseScanAdapter.checkShelfItem(obj.getText().toUpperCase(), useSelectScanData);
						if (tempData != null) {
							//tempData.scanCount += 1;
							//tempData.DataType = 1;
							double surplusCount = getCaseStorageData().findItemMaxChangeCount(obj.getText().toUpperCase(), useSelectScanData.caseShelfData.DID, caseScanAdapter.getAllNewCaseItem());
							caseScanAdapter.UpdateShelfItem(tempData, useSelectScanData, false,surplusCount);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					waitReScan();
				}
			});
			dialog.setPositiveButton(tempStr, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
					//finish();
				}
			});
		} else {
			dialog.setMessage("您扫描的物品编码 " + obj.getText().toUpperCase() + "不在选择的储物箱！");
			//dialog.setMessage("请先选择货架,再扫描物品!");
			dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					waitReScan();
				}
			});
		}
	}

	private void myViewResh(int position, CaseScanData caseScanData) {
		mListView.smoothScrollToPositionFromTop(position, 0);
		useSelectScanData = caseScanData;
	}

	private void myViewRemoveOrtherClick(int position, CaseScanData caseScanData) {
		if (position == -1) {
			//用户取消选择
			useSelectScanData = null;
			return;
		}
		View v = mListView.getChildAt(position);
		mListView.smoothScrollToPosition(position);
		useSelectScanData = caseScanData;
		//便利移出其它
	}

	//修改删除操作弹框前记录临时变量
	CaseScanShelfAdapter updateTempItemCaseScanShelfAdapter = null;
	CaseData updateTempItemCaseData = null;

	public void removeTempItem(CaseScanShelfAdapter caseScanShelfAdapter, CaseData CaseData,boolean isShowDialog) {
		if (CaseData == null || caseScanShelfAdapter == null) return;
		updateTempItemCaseScanShelfAdapter = caseScanShelfAdapter;
		updateTempItemCaseData = CaseData;
		if(isShowDialog)
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setIcon(null);
			dialog.setTitle("提示");
			dialog.setMessage("是否要删除 " + CaseData.caeItemName + " 的本次操作信息");
			dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					removeTempItemOK();
					//waitReScan();
				}
			});
			dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					updateTempItemCaseScanShelfAdapter = null;
					updateTempItemCaseData = null;
				}
			});

			dialog.create().show();
		}
		else
		{
			removeTempItemOK();
		}

	}
	public void removeTempItemOK()
	{
		boolean isDelete = getCaseStorageData().removeTempItemByStorage(updateTempItemCaseData);
		if (isDelete) {
			updateTempItemCaseScanShelfAdapter.deleteItem(updateTempItemCaseData);
			if(updateTempItemCaseScanShelfAdapter.getCount()==0)
			{
				caseScanAdapter.removeTempShelfCaseCode(updateTempItemCaseData.lockerCode);
			}
			caseScanAdapter.notifyDataSetChanged();
		} else {
			updateTempItemCaseData.DataType = -1;
			updateTempItemCaseData.scanCount = 0;
			updateTempItemCaseScanShelfAdapter.updateItem(updateTempItemCaseData);
			CaseShelfData.sortCaseData(updateTempItemCaseScanShelfAdapter.getDataSource());
			//items.get(i).caseShelfData.sortCaseData();
			updateTempItemCaseScanShelfAdapter.notifyDataSetChanged();

		}
		updateTempItemCaseScanShelfAdapter = null;
		updateTempItemCaseData = null;
	}


	public double findTempItemMaxChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData caseData,String shelfCode,CaseScanAdapter caseScanAdapter) {
		if (caseData == null || caseScanShelfAdapter == null || caseScanAdapter==null) return -1;
		double surplusCount = getCaseStorageData().findItemMaxChangeCount(caseData.caseItemNumber, shelfCode, caseScanAdapter.getAllNewCaseItem());
		return surplusCount;
	}

	public boolean itemChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData caseData,double newCount,String shelfCode,CaseScanAdapter caseScanAdapter) {
		if (caseData == null || caseScanShelfAdapter == null || caseScanAdapter==null) return false;
		updateTempItemCaseScanShelfAdapter = caseScanShelfAdapter;
		updateTempItemCaseData = caseData;
		try {
			double surplusCount = getCaseStorageData().findItemMaxChangeCount(caseData.caseItemNumber, shelfCode, caseScanAdapter.getAllNewCaseItem());
			double tempdouble= myArith.add(caseData.scanCount, surplusCount);
			if (tempdouble < newCount)
			//if (caseData.scanCount + surplusCount < newCount)
			{
				return false;
			}

			updateTempItemCaseData.DataType = 1;
			updateTempItemCaseData.scanCount = newCount;
			updateTempItemCaseScanShelfAdapter.updateItem(updateTempItemCaseData);
			CaseShelfData.sortCaseData(updateTempItemCaseScanShelfAdapter.getDataSource());
			//items.get(i).caseShelfData.sortCaseData();
			updateTempItemCaseScanShelfAdapter.notifyDataSetChanged();
			updateTempItemCaseScanShelfAdapter = null;
			updateTempItemCaseData = null;
			return true;
		}catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}




	private void removeTempItemByStorage(CaseScanShelfAdapter caseScanShelfAdapter, CaseData CaseData) {
		boolean isDelete = getCaseStorageData().removeTempItemByStorage(CaseData);
		if (isDelete) {
			updateTempItemCaseScanShelfAdapter.deleteItem(updateTempItemCaseData);
		} else {
			updateTempItemCaseScanShelfAdapter.updateItem(updateTempItemCaseData);

		}

	}

	private void doStorageUpdate() {
		String storageCode = getCaseStorageData().scanformCode;
		int caseStorageDataType = getCaseStorageData().DataType;
		List<CaseData> l_CaseData = caseScanAdapter.getAllNewCaseItem();
        if (l_CaseData==null || l_CaseData.size()==0)
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setIcon(null);
			dialog.setTitle("提示");
			dialog.setMessage("没有找到本次扫描的物品！");
			dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			dialog.create().show();
			return;
		}
		CaseData cd=new CaseData();
		String[] outStr=new String[]{""};
		boolean isPass = getCaseStorageData().validateUpdateItem(l_CaseData, cd,outStr);
		if(!isPass)
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setIcon(null);
			dialog.setTitle("提示");
		//	dialog.setMessage("物品 " + cd.caeItemName + " 数量错误!");
			if(outStr!=null && !outStr[0].equals(""))
			{
				dialog.setMessage("物品 " + cd.caeItemName + " 数量错误!\r\n"+outStr[0]);
			}
			else
			{
				dialog.setMessage("物品 " + cd.caeItemName + " 数量错误!");
			}
			dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			dialog.create().show();
			return;
		}

		l_CaseData = getCaseStorageData().cleanUpdateItem(l_CaseData);
		/*
		System.out.print("l_CaseData.size"+l_CaseData.size()+"\r\n");
		for(int i=0;i<l_CaseData.size();i++)
		{
			System.out.print("CaseData"+i+" lockerCode"+l_CaseData.get(i).lockerCode+" count"+l_CaseData.get(i).scanCount+"\r\n");
		}*/

		if(showType==4)
		{

			//调整出完成进入调整入
			//Intent intent = new Intent(CaseScanItemActivity.this, CaseScanItemActivity.class);
			Intent intent = new Intent(CaseScanItemActivity.this, CaseScanShelfActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("scanCode","");// caseShelfTempData.DID);
			//mBundle.putSerializable("caseShelfData", caseShelfTempData);
			mBundle.putSerializable("showType", 5);//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
			if (getCaseStorageData() != null) {
				getCaseStorageData().ViewState = 5;
				getCaseStorageData().sortCaseData(null);//合并数据
				mBundle.putSerializable("CaseStorageData", getCaseStorageData());//1入 2出 3返 4调整 5物品详细 6货架详细
			}
			intent.putExtras(mBundle);
			startActivity(intent);
			finish();
		}
		else {
			sendStorageUpdate(storageCode, caseStorageDataType, l_CaseData);
		}
	}

	private void sendStorageUpdate(String storageCode, int caseStorageDataType, List<CaseData> l_CaseData) {
		if (this.getResources().getString(R.string.isTestFin).equals("1"))
		{
			sendSuc();
			return;
		}
		CaseItemPutStorageTask ft = new CaseItemPutStorageTask(this, "", storageCode, caseStorageDataType, l_CaseData);
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


	private void sendSuc() {
		Toast.makeText(this, "信息已保存", Toast.LENGTH_LONG).show();
		onBackPressed();
	}

	private void sendFail() {
		Toast.makeText(this, "信息保存失败", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		if (resultCode == 0 && requestCode == 151) {

		} else if (resultCode == 1 && requestCode == 151) {

		} else if (requestCode == 2) {

		}
	}

	boolean fetchItemsTaskRun = false;

	private class FetchItemsTask extends AsyncTask<Integer, Void, List<CaseScanData>> {

		@Override
		protected List<CaseScanData> doInBackground(Integer... params) {
			fetchItemsTaskRun = true;
			List<CaseScanData> result = new ArrayList<CaseScanData>();
			List<CaseShelfData> l_CaseShelfData = null;
			if (showType == 1) {
				l_CaseShelfData = caseInStorageData.createShelfData();
			} else if (showType == 2) {
				l_CaseShelfData = caseOutStorageData.createShelfData();
			} else if (showType == 3) {
				l_CaseShelfData = caseBackStorageData.createShelfData();
			} else if (showType == 4 || showType == 5) {
				l_CaseShelfData = caseAdjustmentStorageData.createShelfData();
			}
			List<CaseScanData> l_CaseScanData = new ArrayList<CaseScanData>();
			if (l_CaseShelfData != null) {
				CaseScanData caseScanData;
				for (int i = 0; i < l_CaseShelfData.size(); i++) {
					caseScanData = new CaseScanData(l_CaseShelfData.get(i));
					result.add(caseScanData);
				}
			}

			if (scanText != null && !scanText.trim().equals("")) {
				boolean isFind = false;
				for (int i = 0; i < result.size(); i++) {
					if (result.get(i).caseShelfData.DID.equals(scanText.trim())) {
						isFind = true;
						if (i == 0) {
							break;
						}
						CaseScanData tempData = result.get(i);
						result.set(i, result.get(0));
						result.set(0, tempData);
						break;
					}
				}
				if (!isFind) {
					CaseScanData tempc3 = new CaseScanData();
					tempc3.caseShelfData = new CaseShelfData();
					tempc3.caseShelfData.DataType = 1;
					tempc3.caseShelfData.DID = scanText;
					tempc3.caseShelfData.lockerName = "新仓库" + scanText;
					tempc3.caseShelfData.list_CaseData = new ArrayList<CaseData>();
					result.add(0, tempc3);
				}
				//查询

			}
			/*
			Integer count = 0;//params[0];

			//增加3条固定数据
			CaseScanData tempc1=new CaseScanData();
			tempc1.caseShelfData =new CaseShelfData();
			tempc1.caseShelfData.DID = "test"+String.valueOf( count+1);
			tempc1.caseShelfData.lockerName = "小型物品藏仓库 三排一层-1";
			tempc1.caseShelfData.list_CaseData=new ArrayList<CaseData>();

			//增加5条固定数据
			CaseData tempData1=new CaseData();
			tempData1.DID = "test"+String.valueOf( count+1);
			tempData1.caseItemName = "海洛因";
			tempData1.earmark =  "";
			tempData1.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData1.ViewState= 1;
			tempData1.caseItemCountString ="两包";
			tempc1.caseShelfData.list_CaseData.add(tempData1);

			CaseData tempData2=new CaseData();
			tempData2.DID = "test"+String.valueOf( count+1);
			tempData2.caseItemName = "手机";
			tempData2.earmark =  "绿色VIVO";
			tempData2.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData2.ViewState= 1;
			tempData2.caseItemCountString ="一部";
			tempc1.caseShelfData.list_CaseData.add(tempData2);

			CaseData tempData3=new CaseData();
			tempData3.DID = "test"+String.valueOf( count+1);
			tempData3.caseItemName = "手机";
			tempData3.earmark =  "蓝色VIVO";
			tempData3.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData3.ViewState= 1;
			tempData3.caseItemCountString ="一部";
			tempc1.caseShelfData.list_CaseData.add(tempData3);

			CaseData tempData4=new CaseData();
			tempData4.DID = "test"+String.valueOf( count+1);
			tempData4.caseItemName = "手机";
			tempData4.earmark =  "白色VIVO";
			tempData4.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData4.ViewState= 1;
			tempData4.caseItemCountString ="一部";
			tempc1.caseShelfData.list_CaseData.add(tempData4);

			CaseData tempData5=new CaseData();
			tempData5.DID = "test"+String.valueOf( count+1);
			tempData5.caseItemName = "手机";
			tempData5.earmark =  "黑色VIVO";
			tempData5.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData5.ViewState= 1;
			tempData5.caseItemCountString ="一部";
			tempc1.caseShelfData.list_CaseData.add(tempData5);

			result.add(tempc1);

			//增加3条固定数据
			CaseScanData tempc2=new CaseScanData();
			tempc2.caseShelfData =new CaseShelfData();
			tempc2.caseShelfData.DID = "test"+String.valueOf( count+1);
			tempc2.caseShelfData.lockerName = "小型物品藏仓库 三排一层-2";
			tempc2.caseShelfData.list_CaseData=new ArrayList<CaseData>();

			//增加5条固定数据
			CaseData tempData12=new CaseData();
			tempData12.DID = "test"+String.valueOf( count+1);
			tempData12.caseItemName = "海洛因2";
			tempData12.earmark =  "";
			tempData12.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData12.ViewState= 1;
			tempData12.caseItemCountString ="两包";
			tempc2.caseShelfData.list_CaseData.add(tempData12);

			CaseData tempData22=new CaseData();
			tempData22.DID = "test"+String.valueOf( count+1);
			tempData22.caseItemName = "手机2";
			tempData22.earmark =  "绿色VIVO2";
			tempData22.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData22.ViewState= 1;
			tempData22.caseItemCountString ="一部";
			tempc2.caseShelfData.list_CaseData.add(tempData22);

			CaseData tempData32=new CaseData();
			tempData32.DID = "test"+String.valueOf( count+1);
			tempData32.caseItemName = "手机2";
			tempData32.earmark =  "蓝色VIVO2";
			tempData32.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData32.ViewState= 1;
			tempData32.caseItemCountString ="一部";
			tempc2.caseShelfData.list_CaseData.add(tempData32);

			result.add(tempc2);


			//增加3条固定数据
			CaseScanData tempc3=new CaseScanData();
			tempc3.caseShelfData =new CaseShelfData();
			tempc3.caseShelfData.DID = "test"+String.valueOf( count+1);
			tempc3.caseShelfData.lockerName = "小型物品藏仓库 三排一层-3";
			tempc3.caseShelfData.list_CaseData=new ArrayList<CaseData>();

			//增加5条固定数据
			CaseData tempData13=new CaseData();
			tempData13.DID = "test"+String.valueOf( count+1);
			tempData13.caseItemName = "海洛因3";
			tempData13.earmark =  "";
			tempData13.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData13.ViewState= 1;
			tempData13.caseItemCountString ="两包";
			// tempc3.caseShelfData.list_CaseData.add(tempData13);

			CaseData tempData23=new CaseData();
			tempData23.DID = "test"+String.valueOf( count+1);
			tempData23.caseItemName = "手机3";
			tempData23.earmark =  "绿色VIVO2";
			tempData23.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData23.ViewState= 1;
			tempData23.caseItemCountString ="一部";
			//   tempc3.caseShelfData.list_CaseData.add(tempData23);

			CaseData tempData33=new CaseData();
			tempData33.DID = "test"+String.valueOf( count+1);
			tempData33.caseItemName = "手机3";
			tempData33.earmark =  "蓝色VIVO3";
			tempData33.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
			tempData33.ViewState= 1;
			tempData33.caseItemCountString ="一部";
			//  tempc3.caseShelfData.list_CaseData.add(tempData33);

			result.add(tempc3);
*/

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return result;
		}

		protected void onPostExecute(List<CaseScanData> result) {
			fetchItemsTaskRun = false;
			mListView.removeHeaderView(loadingView);
			caseScanAdapter.addMoreItems(result);


			caseScanAdapter.notifyDataSetChanged();

		}
	}

	private class WaitTask extends AsyncTask<Integer, Void, String> {

		List<CaseScanData> l_CaseScanData = null;
		CaseScanAdapter caseScanAdapter = null;
		CaseScanData scanCaseScanData = null;

		public WaitTask(CaseScanData scanCaseScanData, List<CaseScanData> l_CaseScanData, CaseScanAdapter caseScanAdapter) {
			this.scanCaseScanData = scanCaseScanData;
			this.l_CaseScanData = l_CaseScanData;
			this.caseScanAdapter = caseScanAdapter;
		}

		@Override
		protected String doInBackground(Integer... params) {


			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "";
		}

		protected void onPostExecute(String result) {
			caseScanAdapter.addMoreItems(l_CaseScanData);
			mListView.removeHeaderView(loadingView);
			caseScanAdapter.notifyDataSetChanged();
			if (scanCaseScanData != null) {
				//if (isFindscan) {
				caseScanAdapter.setSelectShelf(scanCaseScanData);

			}
		}

	}
}