package com.taiji.androidui.view.paginglistview;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class PagingBaseAdapter<T> extends BaseAdapter {

	protected List<T> items;
//    private int pagesize = 7;
//    private int pagestart = 0;

	public PagingBaseAdapter() {
		this.items = new ArrayList<T>();
	}

	public PagingBaseAdapter(List<T> items) {
		this.items = items;
	}

	public void addMoreItems(List<T> newItems) {
		this.items.addAll(newItems);
		notifyDataSetChanged();
	}

	public void addMoreItemsFist(List<T> newItems) {
		this.items.addAll(0,newItems);
		notifyDataSetChanged();
	}

	public void removeAllItems() {
		this.items.clear();
		notifyDataSetChanged();
	}

}
