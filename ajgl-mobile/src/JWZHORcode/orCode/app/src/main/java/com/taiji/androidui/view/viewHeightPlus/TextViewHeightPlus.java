package com.taiji.androidui.view.viewHeightPlus;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by z0 on 2016/12/3.
 */

public class TextViewHeightPlus  extends TextView {
    /*
private boolean mEnabled = true;

public TextViewHeightPlus(Context context) {
    super(context);
}

public TextViewHeightPlus(Context context, AttributeSet attrs) {
    super(context, attrs);
}

public TextViewHeightPlus(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
}

public void setAutoSplitEnabled(boolean enabled) {
    mEnabled = enabled;
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY
            && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
            && getWidth() > 0
            && getHeight() > 0
            && mEnabled) {
        String newText = autoSplitText(this);
        if (!TextUtils.isEmpty(newText)) {
            setText(newText);
        }
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
}

private String autoSplitText(final TextView tv) {
    final String rawText = tv.getText().toString(); //原始文本
    final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
    final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度

    //将原始文本按行拆分
    String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
    StringBuilder sbNewText = new StringBuilder();
    for (String rawTextLine : rawTextLines) {
        if (tvPaint.measureText(rawTextLine) <= tvWidth) {
            //如果整行宽度在控件可用宽度之内，就不处理了
            sbNewText.append(rawTextLine);
        } else {
            //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
            float lineWidth = 0;
            for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                char ch = rawTextLine.charAt(cnt);
                lineWidth += tvPaint.measureText(String.valueOf(ch));
                if (lineWidth <= tvWidth) {
                    sbNewText.append(ch);
                } else {
                    sbNewText.append("\n");
                    lineWidth = 0;
                    --cnt;
                }
            }
        }
        sbNewText.append("\n");
    }

    //把结尾多余的\n去掉
    if (!rawText.endsWith("\n")) {
        sbNewText.deleteCharAt(sbNewText.length() - 1);
    }

    return sbNewText.toString();
}
}

*/
    private static final String TAG = "TextViewHeightPlus";
    private int actualHeight=0;

    public int getActualHeight() {
        return actualHeight;
    }


    public TextViewHeightPlus(Context context) {
        super(context);
    }

    public TextViewHeightPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setCustomFont(context, attrs);
    }

    public TextViewHeightPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //  actualHeight=0;

        //  actualHeight=(int) ((getLineCount()-1)*getTextSize());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Layout layout = getLayout();
        if (layout != null) {


            //   int height = (int) FloatMath.ceil(getMaxLineHeight(this.getText().toString()))
            //          + getCompoundPaddingTop() + getCompoundPaddingBottom();

            int height1 =getMeasuredHeight();//(int) FloatMath.ceil(getLineCount()*getTextSize());//+ getCompoundPaddingTop() + getCompoundPaddingBottom();
            //   int height2 =(int) FloatMath.ceil(getLineCount()*getTextSize())+ getCompoundPaddingTop() + getCompoundPaddingBottom();
            //   int height3 = (int) FloatMath.ceil((this.getPaint().getFontMetrics().descent-this.getPaint().getFontMetrics().ascent)*getLineCount())+ getCompoundPaddingTop() + getCompoundPaddingBottom();
            int width = getMeasuredWidth();
            setMeasuredDimension(width, height1);
            //  System.out.println("---------"+"text="+getText()+"  getLineCount()="+getLineCount()+"  getTextSize()="+getTextSize()+"  height1="+height1+"  height2="+height2+"  height3="+height3);
        }
    }






    private float getMaxLineHeight(String str) {

        float height = 0.0f;
        float screenW = ((Activity) super.getContext()).getWindowManager().getDefaultDisplay().getWidth();
        float paddingLeft = ((LinearLayout)this.getParent()).getPaddingLeft();
        float paddingReft = ((LinearLayout)this.getParent()).getPaddingRight();
//这里具体this.getPaint()要注意使用，要看你的TextView在什么位置，这个是拿TextView父控件的Padding的，为了更准确的算出换行
        int line = (int) Math.ceil( (this.getPaint().measureText(str)/(screenW-paddingLeft-paddingReft)));
        height = (this.getPaint().getFontMetrics().descent-this.getPaint().getFontMetrics().ascent)*line;
        return height;

    }


}
