package com.taiji.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.widget.Toast;


import com.taiji.myContext;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by z0 on 2016/5/4.
 */
public class MyTools {
    public static String deviceId;
    public static String tel;
    public static String getDeviceId(Context context) {
        if (deviceId == null || deviceId.equals("")) {
            if(context==null)
            {
                return "";
            }
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //  String imsi = TelephonyManager.getSubscriberId();
            //String imei = mTelephonyMgr.getDeviceId();
            deviceId = tm.getDeviceId();
            tel = tm.getLine1Number();
        }
        return deviceId;
    }


    public static void showText(final String text)
    {
        try {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(myContext.mContext, text, Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static String fileLogPath="/sdcard/ajgl/log/";
    private static String fileLogName="ajgllog.txt";
    private static boolean isNewCreateFile=false;//以删除旧文件标记程序每次启动重新写日志
    private static List<String> logQueue;
    public static  Thread wirteLogThread;
    public static void wirteLogText(String text)
    {
        if(logQueue==null) logQueue=new ArrayList<String>();
        synchronized(logQueue) {
            String time ="";
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
                time = format.format(new Date())+ "\r\n";
            }catch (Exception ex)
            {

            }
            logQueue.add(time+text);
        }
        if (wirteLogThread != null && wirteLogThread.isAlive() &&!wirteLogThread.isInterrupted())
        {
        }
        else
        {
            try {
                if (wirteLogThread != null) {
                    wirteLogThread.interrupt();
                    //wirteLogThread.stop();
                }
            }catch (Exception ex)
            {

            }
            wirteLogThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (logQueue.size()>0) {
                        try{
                            String strContent = "";
                            if (logQueue != null && logQueue.size() > 0) {
                                synchronized(logQueue) {
                                    for (int i = 0; i < 2; i++) {
                                        if (logQueue.size() > 0) {
                                            strContent+=logQueue.get(0) + "\r\n"+ "\r\n"+ "\r\n";
                                            logQueue.remove(0);
                                        }
                                    }
                                }
                            }
                            wirteLogTextDo(strContent);
                        }
                        catch (Exception ex)
                        {

                        }
                    }
                }
            });
            wirteLogThread.start();
        }

    }
    private static void wirteLogTextDo(String text)
    {
        try {
            File destDir = new File(fileLogPath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            File flog = new File(fileLogPath+fileLogName);
            if (flog.exists()) {
                if(!isNewCreateFile)
                {
                    flog.delete();
                    isNewCreateFile=true;
                }
            }
            //String strContent = text + "\r\n";
            RandomAccessFile raf = new RandomAccessFile(flog, "rwd");
            raf.seek(flog.length());
            raf.write(text.getBytes());
            raf.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public static boolean match(String regex, String str) {
        if(regex==null || str==null)
        {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    public static Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
                .floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
