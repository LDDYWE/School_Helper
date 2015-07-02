package com.zb.secondary_market.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

import com.zb.secondary_market.R;
import com.zb.secondary_market.custom.CustomGridView;

public class PullToRefreshGridView extends PullToRefreshAdapterViewBase<CustomGridView> {
	private GestureDetector mGestureDetector;
	

	class InternalGridView extends CustomGridView implements EmptyViewMethodAccessor {

		public InternalGridView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshGridView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		@Override
		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}

	}

	public PullToRefreshGridView(Context context) {
		super(context);
	}

	public PullToRefreshGridView(Context context, int mode) {
		super(context, mode);
	}

	public PullToRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
	}

	 // Return false if we're scrolling in the x direction  
    class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
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
	protected final CustomGridView createRefreshableView(Context context, AttributeSet attrs) {
		CustomGridView gv = new InternalGridView(context, attrs);
		// Use Generated ID (from res/values/ids.xml)
		gv.setId(R.id.gridview);
		return gv;
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalGridView) getRefreshableView()).getContextMenuInfo();
	}
}
