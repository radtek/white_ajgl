package com.taiji.androidui.view.viewHeightPlus;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by z0 on 2016/12/3.
 */

public class ListViewHeightPlus extends ListView {

    public ListViewHeightPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ListViewHeightPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewHeightPlus(Context context) {
        super(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
