package com.zb.secondary_market.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

public class CustomGridView extends GridView {
	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;

	public CustomGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Log.v("Math.abs(distanceY) > Math.abs(distanceX) + CustomGridView", "true");
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	public CustomGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev)
				&& mGestureDetector.onTouchEvent(ev);
	}

	// Return false if we're scrolling in the x direction
	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.v("Math.abs(distanceY) > Math.abs(distanceX)", "true");
			if (Math.abs(distanceY) > Math.abs(distanceX)) {
				Log.v("Math.abs(distanceY) > Math.abs(distanceX)", "true");
				return true;
			}
			return false;
		}
	}
}