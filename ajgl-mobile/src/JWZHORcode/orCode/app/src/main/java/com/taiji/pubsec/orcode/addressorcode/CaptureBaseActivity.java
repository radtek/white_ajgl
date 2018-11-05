package com.taiji.pubsec.orcode.addressorcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.taiji.pubsec.orcode.addressorcode.camera.CameraManager;
import com.taiji.pubsec.orcode.addressorcode.camera.MyCameraManager;
import com.taiji.pubsec.orcode.addressorcode.decoding.ActivtyBaseHandler;
import com.taiji.pubsec.orcode.addressorcode.decoding.CaptureActivityHandler;
import com.taiji.pubsec.orcode.addressorcode.decoding.InactivityTimer;
import com.taiji.pubsec.orcode.addressorcode.view.MyViewfinderView;
import com.taiji.pubsec.orcode.addressorcode.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

public class CaptureBaseActivity extends Activity implements Callback
{

	private ActivtyBaseHandler handler;
	protected MyViewfinderView myViewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	protected InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	protected boolean isLight=false;

	ImageView scan_case_light_iv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		onCreateDo();

	}

	protected void onCreateDo()
	{
		setContentView(R.layout.main2);
		initCameraManager(null);
	}

	protected void initCameraManager(Rect viewRect)
	{
		// 初始化 CameraManager

		//if (viewRect != null) {
			MyCameraManager.init(getApplication(), viewRect);
		//} else {
		//	MyCameraManager.init(getApplication(), null);
		//}
		if(myViewfinderView==null)
			myViewfinderView = (MyViewfinderView) findViewById(R.id.viewfinder_view);
		myViewfinderView.drawSourceResultBitmap(null, null);
			hasSurface = false;

		//这个用来长时间不动则关闭扫描界面,目前需求要一直开着
		/*
		if(inactivityTimer==null)
			inactivityTimer = new InactivityTimer(this);
			*/
	}

	protected  void initRect(Rect viewRect) {
		MyCameraManager.initRect(viewRect);
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
	protected void onResume()
	{
		super.onResume();
		onResumeDo();
	}

	protected void onResumeDo()
	{
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface)
		{
			initCamera(surfaceHolder);
			myViewfinderView.drawSourceResultBitmap(null, null);
		}
		else
		{
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
		{
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	protected void reScan()
	{
		if (handler != null)
		{
			handler.restartPreviewAndDecode();
		}
		//if(myViewfinderView!=null)
		//{
			myViewfinderView.drawSourceResultBitmap(null, null);
		//}
	}


	@Override
	protected void onPause()
	{
		super.onPause();
		onPauseDo();
	}

	protected void onPauseDo()
	{
		if (handler != null)
		{
			handler.quitSynchronously();
			handler = null;
		}
		MyCameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy()
	{
		if(inactivityTimer!=null)
		   inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder)
	{
		try
		{
			MyCameraManager.get().openDriver(surfaceHolder);
		}
		catch (IOException ioe)
		{
			return;
		}
		catch (RuntimeException e)
		{
			return;
		}
		if (handler == null)
		{
			handler = new ActivtyBaseHandler(this, decodeFormats, characterSet);
		}
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		if (!hasSurface)
		{
			hasSurface = true;
			initCamera(holder);
			myViewfinderView.drawSourceResultBitmap(null, null);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		hasSurface = false;

	}

	public MyViewfinderView getViewfinderView()
	{
		return myViewfinderView;
	}

	public Handler getHandler()
	{
		return handler;
	}

	public void drawViewfinder()
	{
		myViewfinderView.drawViewfinder();

	}

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
		dialog.setMessage(obj.getText());
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//用默认浏览器打开扫描得到的地址
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(obj.getText());
				intent.setData(content_url);
				startActivity(intent);
				finish();
			}
		});
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				finish();
			}
		});
		dialog.create().show();
	}

	private void initBeepSound()
	{
		if (playBeep && mediaPlayer == null)
		{
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try
			{
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			}
			catch (IOException e)
			{
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	protected void playBeepSoundAndVibrate()
	{
		if (playBeep && mediaPlayer != null)
		{
			mediaPlayer.start();
		}
		if (vibrate)
		{
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener()
	{
		public void onCompletion(MediaPlayer mediaPlayer)
		{
			mediaPlayer.seekTo(0);
		}
	};

}