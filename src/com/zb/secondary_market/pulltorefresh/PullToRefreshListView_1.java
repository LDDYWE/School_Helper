package com.zb.secondary_market.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.MeasureSpec;
import android.view.View;
import android.widget.ListView;

public class PullToRefreshListView_1 extends PullToRefreshAdapterViewBase<ListView> {

//	private LoadingLayout headerLoadingView;
//	private LoadingLayout footerLoadingView;

	class InternalListView extends ListView implements EmptyViewMethodAccessor {

		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshListView_1.this.setEmptyView(emptyView);
		}
		
		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public PullToRefreshListView_1(Context context) {
		super(context);
		this.setDisableScrollingWhileRefreshing(false);
	}
	
	public PullToRefreshListView_1(Context context, int mode) {
		super(context, mode);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView_1(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setDisableScrollingWhileRefreshing(false);
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}

	/*//禁止滑动
    @Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
        // TODO Auto-generated method stub  
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {  
            return true;  
        }  
        return super.dispatchTouchEvent(ev);  
    }*/
    
    @Override
    /**
     * 重写该方法，达到使GridView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
	
//	public void setReleaseLabel(String releaseLabel) {
//		super.setReleaseLabel(releaseLabel);
//		if (null != headerLoadingView) {
//			headerLoadingView.setReleaseLabel(releaseLabel);
//		}
//		if (null != footerLoadingView) {
//			footerLoadingView.setReleaseLabel(releaseLabel);
//		}
//	}
//
//	public void setPullLabel(String pullLabel) {
//		super.setPullLabel(pullLabel);
//
//		if (null != headerLoadingView) {
//			headerLoadingView.setPullLabel(pullLabel);
//		}
//		if (null != footerLoadingView) {
//			footerLoadingView.setPullLabel(pullLabel);
//		}
//	}
//
//	public void setRefreshingLabel(String refreshingLabel) {
//		super.setRefreshingLabel(refreshingLabel);
//
//		if (null != headerLoadingView) {
//			headerLoadingView.setRefreshingLabel(refreshingLabel);
//		}
//		if (null != footerLoadingView) {
//			footerLoadingView.setRefreshingLabel(refreshingLabel);
//		}
//	}

	@Override
	protected final ListView createRefreshableView(Context context, AttributeSet attrs) {
		ListView lv = new InternalListView(context, attrs);
//		final int mode = this.getMode();
//
//		// Loading View Strings
//		String pullLabel = context.getString(R.string.pull_to_refresh_pull_label);
//		String refreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_label);
//		String releaseLabel = context.getString(R.string.pull_to_refresh_release_label);

		// Add Loading Views
//		if (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH) {
//			FrameLayout frame = new FrameLayout(context);
//			headerLoadingView = new LoadingLayout(context, MODE_PULL_DOWN_TO_REFRESH, releaseLabel, pullLabel,
//					refreshingLabel);
//			frame.addView(headerLoadingView, FrameLayout.LayoutParams.FILL_PARENT,
//					FrameLayout.LayoutParams.WRAP_CONTENT);
//			headerLoadingView.setVisibility(View.GONE);
//			lv.addHeaderView(frame);
//		}
//		if (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH) {
//			FrameLayout frame = new FrameLayout(context);
//			footerLoadingView = new LoadingLayout(context, MODE_PULL_UP_TO_REFRESH, releaseLabel, pullLabel,
//					refreshingLabel);
//			frame.addView(footerLoadingView, FrameLayout.LayoutParams.FILL_PARENT,
//					FrameLayout.LayoutParams.WRAP_CONTENT);
//			footerLoadingView.setVisibility(View.GONE);
//			lv.addFooterView(frame);
//		}

		// Set it to this so it can be used in ListActivity/ListFragment
		lv.setId(android.R.id.list);
		return lv;
	}

//	@Override
//	protected void setRefreshingInternal(boolean doScroll) {
//		super.setRefreshingInternal(false);
//
//		final LoadingLayout originalLoadingLayout, listViewLoadingLayout;
//		final int selection, scrollToY;
//
//		switch (getCurrentMode()) {
//			case MODE_PULL_UP_TO_REFRESH:
//				originalLoadingLayout = this.getFooterLayout();
//				listViewLoadingLayout = this.footerLoadingView;
//				selection = refreshableView.getCount() - 1;
//				scrollToY = getScrollY() - getHeaderHeight();
//				break;
//			case MODE_PULL_DOWN_TO_REFRESH:
//			default:
//				originalLoadingLayout = this.getHeaderLayout();
//				listViewLoadingLayout = this.headerLoadingView;
//				selection = 0;
//				scrollToY = getScrollY() + getHeaderHeight();
//				break;
//		}
//
//		if (doScroll) {
//			// We scroll slightly so that the ListView's header/footer is at the
//			// same Y position as our normal header/footer
//			this.setHeaderScroll(scrollToY);
//		}
//
//		// Hide our original Loading View
//		originalLoadingLayout.setVisibility(View.INVISIBLE);
//
//		// Show the ListView Loading View and set it to refresh
//		listViewLoadingLayout.setVisibility(View.VISIBLE);
//		listViewLoadingLayout.refreshing();
//
//		if (doScroll) {
//			// Make sure the ListView is scrolled to show the loading
//			// header/footer
//			refreshableView.setSelection(selection);
//
//			// Smooth scroll as normal
//			smoothScrollTo(0);
//		}
//	}

//	@Override
//	protected void resetHeader() {
//
//		LoadingLayout originalLoadingLayout;
//		LoadingLayout listViewLoadingLayout;
//
//		int scrollToHeight = getHeaderHeight();
//		final boolean doScroll;
//
//		switch (getCurrentMode()) {
//			case MODE_PULL_UP_TO_REFRESH:
//				originalLoadingLayout = this.getFooterLayout();
//				listViewLoadingLayout = footerLoadingView;
//				doScroll = this.isReadyForPullUp();
//				break;
//			case MODE_PULL_DOWN_TO_REFRESH:
//			default:
//				originalLoadingLayout = this.getHeaderLayout();
//				listViewLoadingLayout = headerLoadingView;
//				scrollToHeight *= -1;
//				doScroll = this.isReadyForPullDown();
//				break;
//		}
//
//		// Set our Original View to Visible
//		originalLoadingLayout.setVisibility(View.VISIBLE);
//
//		// Scroll so our View is at the same Y as the ListView header/footer,
//		// but only scroll if the ListView is at the top/bottom
//		if (doScroll) {
//			this.setHeaderScroll(scrollToHeight);
//		}
//
//		// Hide the ListView Header/Footer
//		listViewLoadingLayout.setVisibility(View.GONE);
//
//		super.resetHeader();
//	}

}

