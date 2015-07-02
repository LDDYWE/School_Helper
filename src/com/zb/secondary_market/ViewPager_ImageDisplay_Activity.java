package com.zb.secondary_market;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zb.secondary_market.adapter.Display_Image_ViewPagerAdapter;
import com.zb.secondary_market.camera.PhotoView;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;

public class ViewPager_ImageDisplay_Activity extends FragmentActivity implements
		OnClickListener, OnPageChangeListener {
	private static final String TAG = "ViewPager_ImageDisplay_Activity";
	private static final String IMAGE_CACHE_DIR = "thumbs_superhigh";

	private ImageFetcher mImageFetcher;

	private ViewPager vp;
	private Display_Image_ViewPagerAdapter vpAdapter;
	private List<View> views;

	private Item_Detail item_detail = new Item_Detail();
	private List<String> imageurl = new ArrayList<String>();

	private String ssString;
	
	// 引导图片资源
	// private static final int[] pics = { R.drawable.test,
	// R.drawable.test, R.drawable.test,
	// R.drawable.test };

	// 底部小店图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.view_pager);

		Intent intent = new Intent();
		imageurl = (List<String>) getIntent().getSerializableExtra("imageurl");

		// set memory cache 25% of the app memory
		ImageCacheParams cacheParams = new ImageCacheParams(this,
				IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory
		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously

		mImageFetcher = new ImageFetcher(this, 1000);
		mImageFetcher.setLoadingImage(R.drawable.load_failed);
		mImageFetcher.addImageCache(ViewPager_ImageDisplay_Activity.this
				.getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);

		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// 初始化引导图片列表
		for (int i = 0; i < imageurl.size(); i++) {
			PhotoView iv = new PhotoView(this);
			iv.setLayoutParams(mParams);

			encode(imageurl.get(i));
			
			mImageFetcher.loadImage(ssString, iv);

			views.add(iv);
		}

		vp = (ViewPager) findViewById(R.id.viewpager);
		// 初始化Adapter
		vpAdapter = new Display_Image_ViewPagerAdapter(
				ViewPager_ImageDisplay_Activity.this, views);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);

		// 初始化底部小点
		initDots();

	}

	// 动态设置小点
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		// ll.setOrientation(LinearLayout.HORIZONTAL);

		ImageView[] image_dot = new ImageView[4];

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		ll.setGravity(Gravity.CENTER_HORIZONTAL);
		params.setMargins(10, 10, 10, 10);

		for (int i = 0; i < 4; i++) {
			image_dot[i] = new ImageView(this);
			image_dot[i].setClickable(true);
			// image_dot[i].setPadding(5, 5, 5, 5);
			image_dot[i].setLayoutParams(params);
			image_dot[i].setBackgroundResource(R.drawable.dot);
		}

		dots = new ImageView[imageurl.size()];

		// 循环取得小点图片
		for (int i = 0; i < imageurl.size(); i++) {
			ll.addView(image_dot[i]);

			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为黄色
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= imageurl.size()) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > imageurl.size() - 1
				|| currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurDot(arg0);
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}
	
	public void encode(String imageurl) {
		try {
			int n = imageurl.split("/").length;
			String ss = imageurl.split("/")[n - 1];
			String sss = URLEncoder.encode(ss, "UTF-8");
			ssString = imageurl.replace(ss, sss);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}