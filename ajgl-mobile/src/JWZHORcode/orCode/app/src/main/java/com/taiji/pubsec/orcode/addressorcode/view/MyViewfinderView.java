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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.taiji.pubsec.orcode.addressorcode.camera.CameraManager;
import com.taiji.pubsec.orcode.addressorcode.camera.MyCameraManager;
import com.taiji.pubsec.orcode.addressorcode.view.ViewfinderView;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public  class MyViewfinderView extends ViewfinderView {


  // This constructor is used when the class is built from an XML resource.
  public MyViewfinderView(Context context, AttributeSet attrs) {
    super(context, attrs);


  }

  @Override
  public MyCameraManager getCameraManager()
  {
    return  MyCameraManager.get();
  }

  Thread waitLoadCameraManager;
  @Override
  public void onDraw(Canvas canvas) {
    if(getCameraManager()==null)
    {
      return;
    }
    Rect frame = getCameraManager().getFramingRect();
    DrawView(canvas,frame);
  }

  @Override
  public void drawViewfinder() {
   // resultBitmap = null;
  //  lineBitmap=null;
    invalidate();
  }

  @Override
  public boolean drawResultBitmap(Bitmap barcode,Bitmap line) {
    Rect frame = getCameraManager().getFramingRect();
    return drawResultBitmapView(barcode,line,frame);
  }
}


