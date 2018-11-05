/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taiji.pubsec.orcode.addressorcode.view;

import com.google.zxing.ResultPoint;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.pubsec.orcode.addressorcode.camera.CameraManager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public  class ViewfinderView extends View {

  private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
  private static final long ANIMATION_DELAY = 50L;
  private static final int OPAQUE = 0xFF;

  private final Paint paint;
  private Bitmap resultSourceBitmap;
  private Bitmap lineSourceBitmap;
  private boolean isHasUseBitmap=false;
  private Bitmap resultBitmap;
  private Bitmap lineBitmap;
  private final int maskColor;
  private final int resultColor;
  private final int frameColor;
  private final int laserColor;
  private final int resultPointColor;
  private int scannerAlpha;
  private int scannerLineMoveStep;
  private Collection<ResultPoint> possibleResultPoints;
  private Collection<ResultPoint> lastPossibleResultPoints;

  // This constructor is used when the class is built from an XML resource.
  public ViewfinderView(Context context, AttributeSet attrs) {
    super(context, attrs);

    // Initialize these once for performance rather than calling them every time in onDraw().
    paint = new Paint();
    Resources resources = getResources();
    maskColor = resources.getColor(R.color.viewfinder_mask);
    resultColor = resources.getColor(R.color.result_view);
    frameColor = resources.getColor(R.color.wangge_sanjiangpaichusuo);//扫描区边框色
    laserColor = resources.getColor(R.color.viewfinder_laser);//扫描线
    resultPointColor = resources.getColor(R.color.wangge_sanjiangpaichusuo);//扫描界面对焦动画的点点颜色
    scannerAlpha = 0;
    possibleResultPoints = new HashSet<ResultPoint>(5);
  }

  public CameraManager getCameraManager()
  {
    return  CameraManager.get();
  }

  @Override
  public void onDraw(Canvas canvas) {
    Rect frame = getCameraManager().getFramingRect();
    DrawView(canvas,frame);
  }

  Long oldDate = System.currentTimeMillis();
  Long newDate = System.currentTimeMillis();
  int stepCount=9;
  protected void DrawView(Canvas canvas,Rect frame)
  {
    if (frame == null) {
      return;
    }/*
    stepCount++;
    if(stepCount>10)
    {
      stepCount=0;
      if(lineBitmap!=null)
      {
        scannerLineMoveStep =(scannerLineMoveStep + 1) % 120;
        int middle = ((int)(frame.height()* 0.0083) * scannerLineMoveStep)+50 + frame.top;
        canvas.drawBitmap(lineBitmap, frame.left, middle, paint);

      }
      else
      {
        paint.setColor(laserColor);
        paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
        scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
        int middle = frame.height() / 2 + frame.top;
        canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
      }
      postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
      return;
    }*/
    int width = canvas.getWidth();
    int height = canvas.getHeight();

    // Draw the exterior (i.e. outside the framing rect) darkened
    paint.setColor(resultBitmap != null ? resultColor : maskColor);
    canvas.drawRect(0, 0, width, frame.top, paint);
    canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
    canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
    canvas.drawRect(0, frame.bottom + 1, width, height, paint);


    if(isHasUseBitmap)
    {

      //System.out.println("isHasUseBitmap=" + String.valueOf(isHasUseBitmap) + "\r\n");
      boolean isDraw = drawResultBitmap(resultSourceBitmap,lineSourceBitmap);
      if(isDraw)
      {
      //  System.out.println("isDraw=" + String.valueOf(isDraw) + "\r\n");
        isHasUseBitmap=false;
        resultSourceBitmap=null;
        lineSourceBitmap=null;
      }

    }

    if (resultBitmap != null) {
      // Draw the opaque result bitmap over the scanning rectangle
      paint.setAlpha(OPAQUE);
      canvas.drawBitmap(resultBitmap, frame.left-7, frame.top-7, paint);

    } else {

      // Draw a two pixel solid black border inside the framing rect
      paint.setColor(frameColor);
      canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top + 2, paint);
      canvas.drawRect(frame.left, frame.top + 2, frame.left + 2, frame.bottom - 1, paint);
      canvas.drawRect(frame.right - 1, frame.top, frame.right + 1, frame.bottom - 1, paint);
      canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1, frame.bottom + 1, paint);
    }
    if(lineBitmap!=null)
    {
      //int middle = frame.height() / 2 + frame.top;
      scannerLineMoveStep =(scannerLineMoveStep + 1) % 120;
      int middle = ((int)(frame.height()* 0.0083) * scannerLineMoveStep)+50 + frame.top;
      //canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
      //paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
      //scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
      canvas.drawBitmap(lineBitmap, frame.left, middle, paint);

    }
    else
    {
      // Draw a red "laser scanner" line through the middle to show decoding is active
      paint.setColor(laserColor);
      paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
      scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
      int middle = frame.height() / 2 + frame.top;
      canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
    }


    stepCount++;
    if(stepCount>4) {
      stepCount = 0;
      Collection<ResultPoint> currentPossible = possibleResultPoints;
      Collection<ResultPoint> currentLast = lastPossibleResultPoints;
      if (currentPossible.isEmpty()) {
        lastPossibleResultPoints = null;
      } else {
        possibleResultPoints = new HashSet<ResultPoint>(5);
        if (lastPossibleResultPoints != null)
          lastPossibleResultPoints.clear();
        lastPossibleResultPoints = currentPossible;
        paint.setAlpha(OPAQUE);
        paint.setColor(resultPointColor);
        for (ResultPoint point : currentPossible) {
          canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, paint);
        }
      }
      if (currentLast != null) {
        paint.setAlpha(OPAQUE / 2);
        paint.setColor(resultPointColor);
        for (ResultPoint point : currentLast) {
          canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 3.0f, paint);
        }
      }
    }


    // Request another update at the animation interval, but only repaint the laser line,
    // not the entire viewfinder mask.

/*
    if(scannerLineMoveStep%20==0) {
       oldDate = newDate;
       newDate = System.currentTimeMillis();
       System.out.println("t=" + (newDate-oldDate) + "  " + scannerLineMoveStep + "\r\n");
    }*/

    postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
  }

  public void drawViewfinder() {
    resultBitmap = null;
    lineBitmap=null;
    invalidate();
  }

  /**
   * Draw a bitmap with the result points highlighted instead of the live scanning display.
   *
   * @param barcode An image of the decoded barcode.
   */
  public void drawResultBitmap(Bitmap barcode) {
    resultBitmap = barcode;
    invalidate();
  }

  public void drawSourceResultBitmap(Bitmap barcode,Bitmap line) {
    //最开始没有生成界面所以调不到,用标志位标记,后面处理
    isHasUseBitmap=true;
    resultSourceBitmap=barcode;
    lineSourceBitmap=line;

  }



  public boolean drawResultBitmap(Bitmap barcode,Bitmap line) {
    Rect frame =getCameraManager().getFramingRect();
    return drawResultBitmapView(barcode, line, frame);
  }

  protected boolean drawResultBitmapView(Bitmap barcode,Bitmap line,Rect frame)
  {

    if (frame == null) {
      return false;
    }
    Bitmap  bitmap =null;
    Bitmap temp=null;
    if(barcode==null)
    {
      bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.scan2x);
      temp =Bitmap.createScaledBitmap(bitmap, frame.width()+14, frame.height()+14, true);
      bitmap.recycle();
    }
    else
    {
      temp =Bitmap.createScaledBitmap(barcode, frame.width()+14, frame.height()+14, true);
      barcode.recycle();
    }
    bitmap=resultBitmap;
    resultBitmap=temp;
    if(bitmap!=null)bitmap.recycle();

    if(line==null)
    {
      bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.scan_line2x);
      temp =Bitmap.createScaledBitmap(bitmap, frame.width(), bitmap.getHeight(), true);
      bitmap.recycle();
    }
    else
    {
      temp =Bitmap.createScaledBitmap(line, frame.width(), bitmap.getHeight(), true);
      line.recycle();
    }
    bitmap=lineBitmap;
    lineBitmap=temp;
    if(bitmap!=null)bitmap.recycle();
    invalidate();
    return true;
  }

  public void addPossibleResultPoint(ResultPoint point) {
    possibleResultPoints.add(point);
  }

}
