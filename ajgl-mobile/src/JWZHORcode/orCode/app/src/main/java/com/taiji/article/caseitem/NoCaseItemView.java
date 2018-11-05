package com.taiji.article.caseitem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.taiji.pubsec.orcode.addressorcode.R;


public class NoCaseItemView extends LinearLayout {

	public NoCaseItemView(Context context) {
		super(context);
		init();
	}

	public NoCaseItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.nocase_data_view, this);
	}


}
