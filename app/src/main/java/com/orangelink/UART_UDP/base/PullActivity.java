package com.orangelink.UART_UDP.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.zhuxiyungu.autisticchildren.R;


public class PullActivity extends BaseActivity {

	private SwipeRefreshLayout mRefreshLayout = null;
	private boolean loadMoreEnbled;

	/**
	 * 设置是否可以手动下拉
	 * 
	 * @param enabled
	 */
	protected void setPullEnabled(boolean enabled) {
		if (mRefreshLayout != null) {
			mRefreshLayout.setEnabled(enabled);
		}
	}

	protected void setRefreshing(boolean refreshing) {
		if (mRefreshLayout != null) {
			mRefreshLayout.setRefreshing(refreshing);
			if (refreshing) {
				onRefresh();
			}
		}
	}

	protected void setLoading(boolean loading) {
		View view = findViewById(R.id.pull_layout_more);
		if (view != null) {
			view.setVisibility(loading ? View.VISIBLE : View.GONE);
		}
	}

	protected boolean isRefreshing() {
		return mRefreshLayout == null ? false : mRefreshLayout.isRefreshing();
	}

	/**
	 * 手动下拉事件，需复写
	 */
	protected void onRefresh() {
	}

	/**
	 * 加载更多，需复写
	 */
	protected void onLoadingMore() {
	}

	protected void setLoadMoreEnbled(boolean enabled) {
		loadMoreEnbled = enabled;
	}

	protected boolean isLoadingMore() {
		View view = findViewById(R.id.pull_layout_more);
		if (view != null) {
			return view.getVisibility() == View.VISIBLE;
		}
		return false;
	}

	protected void setRefreshView(View view) {
		if (view != null) {
			if (view instanceof ListView) {
				((ListView) view).setOnScrollListener(new OnScrollListener() {
					boolean isLastRow = false;
					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
						if (isLastRow && scrollState == OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() == (view.getCount() - 1)) {
							if (loadMoreEnbled && !isLoadingMore()) {
								setLoading(true);
								onLoadingMore();
								isLastRow = false; 
							}
						}
					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
						//判断是否滚到最后一行      
			            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {      
			                isLastRow = true;      
			            }
					}
				});
			}
			ViewParent parent = view.getParent();
			if (parent instanceof ViewGroup) {
				int chlidIndex = -1;
				for (int i = 0; i < ((ViewGroup) parent).getChildCount(); i++) {
					if (view.getId() == ((ViewGroup) parent).getChildAt(i)
							.getId()) {
						chlidIndex = i;
						break;
					}
				}
				View v = LayoutInflater.from(context).inflate(
						R.layout.pull_layout, null);
				mRefreshLayout = (SwipeRefreshLayout) v
						.findViewById(R.id.refresh_widget);
				mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
					@Override
					public void onRefresh() {
						PullActivity.this.onRefresh();
					}
				});
				if (chlidIndex != -1) {
					LayoutParams lp = view.getLayoutParams();
					((ViewGroup) parent).removeView(view);
					mRefreshLayout.removeAllViews();
					mRefreshLayout.addView(view);
					mRefreshLayout.setColorSchemeResources(R.color.color1,
							R.color.color2, R.color.color3, R.color.color4);
					((ViewGroup) parent).addView(v, chlidIndex, lp);
				}
			}
		}

	}

	public void closeRefreshLoading() {
		if (isRefreshing()) {
			setRefreshing(false);
		}
		if (isLoadingMore()) {
			setLoading(false);
		}
	}

	protected void setRefreshView(int viewId) {
		View view = findViewById(viewId);
		setRefreshView(view);
	}

}
