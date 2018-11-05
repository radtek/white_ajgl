package com.taiji.util;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by z0 on 2017/3/16.
 */
public class MyAnimationUtil {
    public void viewStartAnimation(ViewGroup v,Animation animation)
    {
        v.clearAnimation();
        v.startAnimation(animation);
    }

    /** 常用方法 */
//animation.setRepeatCount(int repeatCount);//设置重复次数
//animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态
//animation.setStartOffset(long startOffset);//执行前的等待时间
     /*
    start.setOnClickListener(new OnClickListener() {
        public void onClick(View arg0) {
            image.setAnimation(animation);
/** 开始动画 */
    /*
            animation.startNow();
        }
    });
    cancel.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
/** 结束动画 */
    /*
            animation.cancel();
        }
    });*/

    public Animation inFromLeftAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(Duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        listener.vg= v;
        return inFromRight;
    }

    public Animation outToLeftAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(Duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        return inFromRight;
    }

    public Animation inFromBottomAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(Duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        return inFromRight;
    }

    public Animation outToBottomAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f);
        inFromRight.setDuration(Duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        return inFromRight;
    }

    public Animation inFromRightAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(Duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        return inFromRight;
    }

    public Animation outToRightAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(Duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        return inFromRight;
    }

    public Animation inFromTopAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(Duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        return inFromRight;
    }

    public Animation outTTopAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f);
        inFromRight.setDuration(Duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        inFromRight.setAnimationListener(listener);
        return inFromRight;
    }

    //startX,startY指定当前动画扩散或收缩的中心点()建议范围0-1,
    public Animation sizeChangeAnimation(ViewGroup v,int Duration,MyAnimationListener listener,float startX,float startY) {
        ScaleAnimation animation =new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, startX, Animation.RELATIVE_TO_SELF, startY);

        animation.setDuration(Duration);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(animationShowlistener);
        return animation;
    }

    //startX,startY指定当前动画扩散的中心点建议范围0-1,
    public Animation showCenterToEdgeAnimation(ViewGroup v,int Duration,MyAnimationListener listener,float startX,float startY) {
        ScaleAnimation animation =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(Duration);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(animationShowlistener);
        return animation;
    }

    //startX,startY指定当前动画收缩的中心点建议范围0-1,
    public Animation hideEdgeToCenterAnimation(ViewGroup v,int Duration,MyAnimationListener listener,float startX,float startY) {
        ScaleAnimation animation =new ScaleAnimation(1.0f, 0.0f, 1.0f, 01.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(Duration);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(animationShowlistener);
        return animation;
    }

    public Animation showTopToBottomAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        ScaleAnimation animation =new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(Duration);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(animationShowlistener);
        return animation;
    }

    public Animation hideBottomToTopAnimation(ViewGroup v,int Duration,MyAnimationListener listener) {
        ScaleAnimation animation =new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(Duration);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(animationShowlistener);
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
            vg=null;
            // setCurText(curText);
        }
    }
    /***
     * 动画监听，动画完成后，动画恢复，设置文本
     */
    public MyAnimationListener animationShowlistener = new MyAnimationListener() {

        @Override
        public void onAnimationStart(Animation arg0) {
            super.onAnimationStart(arg0);
            if(vg==null)return;
            // vg.setVisibility(View.VISIBLE);//这里设置不管用
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

    public MyAnimationListener animationHidelistener = new MyAnimationListener() {

        @Override
        public void onAnimationStart(Animation arg0) {
            super.onAnimationStart(arg0);
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            // if(vg==null)return;
            // vg.setVisibility(View.GONE);
            super.onAnimationEnd(arg0);
        }
    };

    public MyAnimationListener animationInvisiblelistener = new MyAnimationListener() {
        public ViewGroup animationView;

        @Override
        public void onAnimationStart(Animation arg0) {
            super.onAnimationStart(arg0);
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            if(vg==null)return;
            //  vg.setVisibility(View.GONE);
            // super.onAnimationEnd(arg0);
        }
    };
}
