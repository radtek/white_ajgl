package com.taiji.androidui.view.paginglistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.taiji.pubsec.orcode.addressorcode.R;


public class NoDataView extends LinearLayout {

	public NoDataView(Context context) {
		super(context);
		init();
	}

	public NoDataView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.nodata_view, this);
	}


}
