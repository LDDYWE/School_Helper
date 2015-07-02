package com.zb.secondary_market.adapter;

import java.io.Serializable;
import java.util.List;

import com.zb.secondary_market.Content_Detail_Secondary_Activity;
import com.zb.secondary_market.ViewPager_ImageDisplay_Activity;
import com.zb.secondary_market.otherclasses.Item_Detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

public class Content_Image_ViewPagerAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;
	private Activity mActivity;
	private List<String> imageurl;

	public Content_Image_ViewPagerAdapter(Activity mActivity, List<View> views,
			List<String> imageurl) {
		this.views = views;
		this.mActivity = mActivity;
		this.imageurl = imageurl;
	}

	// 销毁arg1位置的界面
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	// 获得当前界面数
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}

		return 0;
	}

	// 初始化arg1位置的界面
	@Override
	public Object instantiateItem(View arg0, int arg1) {

		((ViewPager) arg0).addView(views.get(arg1), 0);

		views.get(arg1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(mActivity, ViewPager_ImageDisplay_Activity.class);
				in.putExtra("imageurl", (Serializable) imageurl);
				mActivity.startActivity(in);
			}
		});

		return views.get(arg1);
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}
}
