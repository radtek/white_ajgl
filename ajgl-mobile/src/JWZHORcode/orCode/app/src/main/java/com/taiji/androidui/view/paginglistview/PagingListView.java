package com.taiji.androidui.view.paginglistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.taiji.androidui.view.swipemenulistview.SwipeMenuAdapter;
import com.taiji.androidui.view.swipemenulistview.SwipeMenuListView;
import com.taiji.pubsec.orcode.addressorcode.R;

import java.util.List;


public class PagingListView extends SwipeMenuListView {

	public interface Pagingable {
		void onLoadMoreItems();
	}

	private boolean isLoading;
	private boolean hasMoreItems;
	private Pagingable pagingableListener;
	private LoadingView loadingView;
	private NoDataView noDataView;

    private OnScrollListener onScrollListener;

	public PagingListView(Context context) {
		super(context);
		init();
	}

	public PagingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PagingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public boolean isLoading() {
		return this.isLoading;
	}

	public void setIsLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	public void setPagingableListener(Pagingable pagingableListener) {
		this.pagingableListener = pagingableListener;
	}

	public void setHasMoreItems(boolean hasMoreItems) {
		this.hasMoreItems = hasMoreItems;
		if(!this.hasMoreItems) {
			removeFooterView(loadingView);
		}
		else if(findViewById(R.id.loading_view) == null){
			if(findViewById(R.id.nodata_view)!=null)
			{
				setHasItems();
			}
			addFooterView(loadingView);
			ListAdapter adapter = ((HeaderViewListAdapter)getAdapter()).getWrappedAdapter();
			setAdapter(adapter);
		}
	}

	public void setNoItems()
	{
		if(this.hasMoreItems)
		{
			removeFooterView(loadingView);
			this.hasMoreItems = false;
		}
		if(findViewById(R.id.nodata_view) == null){
			addFooterView(noDataView);
			ListAdapter adapter = ((HeaderViewListAdapter)getAdapter()).getWrappedAdapter();
			setAdapter(adapter);
		}
	}

	public void setHasItems()
	{
			removeFooterView(noDataView);
	}

	public boolean hasMoreItems() {
		return this.hasMoreItems;
	}


	public void onFinishLoading(boolean hasMoreItems,boolean isAddViewLast, List<? extends Object> newItems) {
		setHasMoreItems(hasMoreItems);
		setIsLoading(false);
		if (newItems != null && newItems.size() > 0) {
			ListAdapter adapter = ((HeaderViewListAdapter) getAdapter()).getWrappedAdapter();
			if (adapter instanceof PagingBaseAdapter) { //如果PagingListView从ListView继承，则是此类型，实际此处可删掉，因为这里是从SwipeMenuListView的
				if (isAddViewLast) {
					((PagingBaseAdapter) adapter).addMoreItems(newItems);
				} else {
					((PagingBaseAdapter) adapter).addMoreItemsFist(newItems);
				}
			} else if (adapter instanceof SwipeMenuAdapter) { //如果PagingListView从SwipeMenuListView继承，则是此类型
				PagingBaseAdapter pagingadapter = (PagingBaseAdapter) ((SwipeMenuAdapter) adapter).getWrappedAdapter();
				if (isAddViewLast) {
					pagingadapter.addMoreItems(newItems);
				} else {
					pagingadapter.addMoreItemsFist(newItems);
				}

			}
		}
	}


	private void init() {
		isLoading = false;
        loadingView = new LoadingView(getContext());
		noDataView = new NoDataView(getContext());
		addFooterView(loadingView);
		super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                if (!isLoading && hasMoreItems && (lastVisibleItem == totalItemCount)) {
                    if (pagingableListener != null) {
                        isLoading = true;
                        pagingableListener.onLoadMoreItems();
                    }

                }
            }
        });
	}

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }
}
