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

package com.taiji.pubsec.orcode.addressorcode.camera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * This object wraps the Camera service object and expects to be the only one talking to it. The
 * implementation encapsulates the steps needed to take preview-sized images, which are used for
 * both preview and decoding.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public  class MyCameraManager extends CameraManager{

  private static final String TAG = MyCameraManager.class.getSimpleName();

 // private static MyCameraManager cameraManager;

  protected Rect viewRect=null;

  private MyCameraManager(Context context) {

    super(context);
    //this.context = context;
  //  this.configManager = new CameraConfigurationManager(context);

    // Camera.setOneShotPreviewCallback() has a race condition in Cupcake, so we use the older
    // Camera.setPreviewCallback() on 1.5 and earlier. For Donut and later, we need to use
    // the more efficient one shot callback, as the older one can swamp the system and cause it
    // to run out of memory. We can't use SDK_INT because it was introduced in the Donut SDK.
    //useOneShotPreviewCallback = Integer.parseInt(Build.VERSION.SDK) > Build.VERSION_CODES.CUPCAKE;
  //  useOneShotPreviewCallback = Integer.parseInt(Build.VERSION.SDK) > 3; // 3 = Cupcake

  //  previewCallback = new PreviewCallback(configManager, useOneShotPreviewCallback);
  //  autoFocusCallback = new AutoFocusCallback();
  }

  private MyCameraManager(Context context,Rect viewRect) {
    super(context);
    this.setViewRect(viewRect);
   // this.viewRect = viewRect;
  }


  public static void init(Context context) {
    if (cameraManager == null) {
        cameraManager = new MyCameraManager(context,null);
    }
  }
  /**
   * Initializes this static object with the Context of the calling Activity.
   *
   * @param context The Activity which wants to use the camera.
   */
  public static void init(Context context,Rect viewRect) {
    if (cameraManager == null) {
     // if(viewRect==null)
      //  cameraManager = new MyCameraManager(context);
    //  else
        cameraManager = new MyCameraManager(context,viewRect);
    }
    else
    {
      //if(viewRect!=null)
        ((MyCameraManager)cameraManager).setViewRect(viewRect);
        //((MyCameraManager)cameraManager).viewRect=viewRect;
    }
  }

  public static void initRect(Rect viewRect) {
      if(viewRect!=null && cameraManager == null)
        ((MyCameraManager)cameraManager).setViewRect(viewRect);
  }

  /**
   * Gets the CameraManager singleton instance.
   *
   * @return A reference to the CameraManager singleton.
   */
  public static MyCameraManager get() {
    return  (MyCameraManager)cameraManager;
  }

  private  void setViewRect(Rect viewRect) {
    if(cameraManager==null)
    {
      return;
    }
    ((MyCameraManager)cameraManager).viewRect=viewRect;
    ((MyCameraManager)cameraManager).framingRectInPreview=null;
  }


  /**
   * Calculates the framing rect which the UI should draw to show the user where to place the
   * barcode. This target helps with alignment as well as forces the user to hold the device
   * far enough away to ensure the image will be in focus.
   *
   * @return The rectangle to draw on screen in window coordinates.
   */
  @Override
  public Rect getFramingRect() {
   // if(viewRect!=null)
    //{

  //  }
 //   else {
      framingRect=viewRect;
      Point screenResolution = configManager.getScreenResolution();
      if (framingRect == null) {
        if (camera == null) {
          return null;
        }
        int width = screenResolution.x * 3 / 4;
        if (width < MIN_FRAME_WIDTH) {
          width = MIN_FRAME_WIDTH;
        } else if (width > MAX_FRAME_WIDTH) {
          width = MAX_FRAME_WIDTH;
        }
        int height = screenResolution.y * 3 / 4;
        if (height < MIN_FRAME_HEIGHT) {
          height = MIN_FRAME_HEIGHT;
        } else if (height > MAX_FRAME_HEIGHT) {
          height = MAX_FRAME_HEIGHT;
        }
        int leftOffset = (screenResolution.x - width) / 2;
        int topOffset = (screenResolution.y - height) / 2;
        // framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
        framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
        //framingRect = new Rect(100, 100, 500, 500);
        //  framingRect = new Rect(0, topOffset, leftOffset+leftOffset + width, topOffset + height);
        Log.d(TAG, "Calculated framing rect: " + framingRect);
      }
 //   }
    return framingRect;
  }

  /**
   * Like {@link #getFramingRect} but coordinates are in terms of the preview frame,
   * not UI / screen.
   */
  @Override
  public Rect getFramingRectInPreview() {
    if (framingRectInPreview == null) {
      Rect rect = new Rect(getFramingRect());
      Point cameraResolution = configManager.getCameraResolution();
      Point screenResolution = configManager.getScreenResolution();
      rect.left = rect.left * cameraResolution.y / screenResolution.x;
      rect.right = rect.right * cameraResolution.y / screenResolution.x;
      rect.top = rect.top * cameraResolution.x / screenResolution.y;
      rect.bottom = rect.bottom * cameraResolution.x / screenResolution.y;
    //  rect.left = rect.left * cameraResolution.x / screenResolution.x;
     // rect.right = rect.right * cameraResolution.x / screenResolution.x;
    //  rect.top = rect.top * cameraResolution.y / screenResolution.y;
     // rect.bottom = rect.bottom * cameraResolution.y / screenResolution.y;
      framingRectInPreview = rect;
    }
    return framingRectInPreview;
  }



}
