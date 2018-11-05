package com.taiji.article.caseitem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taiji.Login.LoginActivity;
import com.taiji.pubsec.orcode.addressorcode.R;

import java.text.SimpleDateFormat;
import java.util.Date;



public class MainStartActivity extends Activity {


    LinearLayout cloud_right_lay,cloud_left_lay,person_lay,person_back_lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_start_animation);



        cloud_right_lay=(LinearLayout) findViewById(R.id.cloud_right_lay);
        cloud_left_lay=(LinearLayout) findViewById(R.id.cloud_left_lay);
        person_lay=(LinearLayout) findViewById(R.id.person_lay);
        person_back_lay=(LinearLayout) findViewById(R.id.person_back_lay);

        cloud_right_lay.clearAnimation();
        cloud_left_lay.clearAnimation();
        person_lay.clearAnimation();
        person_back_lay.clearAnimation();
        person_back_lay.setVisibility(View.GONE);
                // TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -mHeight);
                //  animation.setDuration(1500);

        Animation at=inFromRightAnimation(cloud_right_lay);
        at.setRepeatCount(-1);
        at.setRepeatMode(Animation.RESTART);
        at.setStartOffset(0);
        at.setInterpolator(new LinearInterpolator());
        cloud_right_lay.startAnimation(at);

        at=outToLeftAnimation(cloud_left_lay);
        at.setRepeatCount(Animation.INFINITE);
        at.setRepeatMode(Animation.RESTART);
        at.setStartOffset(0);
        at.setInterpolator(new LinearInterpolator());
        cloud_left_lay.startAnimation(at);
        personShow();
    }

    private void personShow()
    {
        if(person_lay==null)return;
        if(person_back_lay!=null)person_back_lay.setVisibility(View.GONE);
        Animation at=inFromTopAnimation(person_lay);
       // at.setRepeatCount(-1);
       // at.setRepeatMode(Animation.RESTART);
       // at.setStartOffset(10000);
        at.setInterpolator(new OvershootInterpolator());
        person_lay.clearAnimation();
        person_lay.startAnimation(at);
    }

    private void personBackShow()
    {
        if(person_back_lay==null)return;

        Animation at=sizeChangeAnimation(person_back_lay);
         // at.setRepeatCount(-1);
        // at.setRepeatMode(Animation.RESTART);
        // at.setStartOffset(10000);
       // at.setInterpolator(new OvershootInterpolator());
        person_back_lay.clearAnimation();
        person_back_lay.startAnimation(at);
        person_back_lay.setVisibility(View.VISIBLE);
    }

    protected Animation inFromRightAnimation(ViewGroup v) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(20000);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        listener.vg=v;
        return inFromRight;
    }

    protected Animation outToLeftAnimation(ViewGroup v) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(20000);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        listener.vg=v;
        return inFromRight;
    }

    protected Animation inFromTopAnimation(ViewGroup v) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(2000);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(blendlistener);
        blendlistener.vg=v;
        return inFromRight;
    }

    protected Animation sizeChangeAnimation(ViewGroup v) {
        ScaleAnimation animation =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.375f);

        animation.setDuration(400);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(blendlistener2);
        blendlistener2.vg=v;
        return animation;
    }
    private class MyAnimationListener implements Animation.AnimationListener {
        public  ViewGroup vg;
        private boolean savViewClickable=false;
        @Override
        public void onAnimationStart(Animation arg0) {
            if(vg==null)return;
            savViewClickable=vg.isClickable();
            vg.setClickable(false);
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            if(vg==null)return;
            vg.setClickable(savViewClickable);
            // setCurText(curText);
        }
    }
    /***
     * 动画监听，动画完成后，动画恢复，设置文本
     */
    public MyAnimationListener listener = new MyAnimationListener() {
        @Override
        public void onAnimationStart(Animation arg0) {
            super.onAnimationStart(arg0);
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            super.onAnimationEnd(arg0);
            // setCurText(curText);
        }
    };

    public MyAnimationListener blendlistener = new MyAnimationListener() {
        @Override
        public void onAnimationStart(Animation arg0) {
            super.onAnimationStart(arg0);
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            super.onAnimationEnd(arg0);
            personBackShow();
            // setCurText(curText);
        }
    };

    public MyAnimationListener blendlistener2 = new MyAnimationListener() {
        @Override
        public void onAnimationStart(Animation arg0) {
            super.onAnimationStart(arg0);
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            super.onAnimationEnd(arg0);
            Thread thread=new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainStartActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            });
            thread.start();


            // setCurText(curText);
        }
    };



    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }
}
