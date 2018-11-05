package com.taiji.androidui.view.paginglistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.taiji.pubsec.orcode.addressorcode.R;


public class NoView extends LinearLayout {

	public NoView(Context context) {
		super(context);
		init();
	}

	public NoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.no_view, this);
	}


}
