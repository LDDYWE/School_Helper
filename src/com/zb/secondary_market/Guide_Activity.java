package com.zb.secondary_market;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zb.secondary_market.adapter.Display_Image_ViewPagerAdapter;
import com.zb.secondary_market.camera.PhotoView;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;

public class Guide_Activity extends Activity implements
		OnClickListener, OnPageChangeListener {
	private static final String TAG = "ViewPager_ImageDisplay_Activity";
	private static final String IMAGE_CACHE_DIR = "thumbs_superhigh";

	private ViewPager vp;
	private Display_Image_ViewPagerAdapter vpAdapter;
	private List<View> views;

	private ImageFetcher mImageFetcher;
	private Button startButton;
	
	// 到达最后一张   
    private static final int TO_THE_END = 0;     
    // 离开最后一张   
    private static final int LEAVE_FROM_END = 1;

	private Item_Detail item_detail = new Item_Detail();
	// private List<String> imageurl = new ArrayList<String>();

	private int[] guide_Image = { R.drawable.guide01, R.drawable.guide03,
			R.drawable.guide04 };

	// 引导图片资源
	// private static final int[] pics = { R.drawable.test,
	// R.drawable.test, R.drawable.test,
	// R.drawable.test };

	// 底部小点图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_guide);

		/*// set memory cache 25% of the app memory
		ImageCacheParams cacheParams = new ImageCacheParams(this,
				IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
		// app memory
		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously

		// mImageFetcher = new ImageFetcher(this, 1000);
		// mImageFetcher.setLoadingImage(R.drawable.load_failed);
		// mImageFetcher.addImageCache(Guide_Activity.this
		// .getSupportFragmentManager(), cacheParams);
		// mImageFetcher.setImageFadeIn(false);
*/
		
	startButton = (Button)findViewById(R.id.guide_open);
	
	startButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		//进入登陆界面
			Intent intent = new Intent();
			intent.setClass(Guide_Activity.this, Login_Activity.class);
			startActivity(intent);
			
			finish();
		}
	});
		
		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// 初始化引导图片列表
		for (int i = 0; i < guide_Image.length; i++) {
			PhotoView iv = new PhotoView(this);
			iv.setLayoutParams(mParams);

			Bitmap bitmap = readBitMap(this, guide_Image[i]);
			iv.setImageBitmap(bitmap);
//			iv.setImageResource(guide_Image[i]);

			views.add(iv);
		}

		vp = (ViewPager) findViewById(R.id.guide_viewpager);
		// 初始化Adapter
		vpAdapter = new Display_Image_ViewPagerAdapter(Guide_Activity.this,
				views);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);

		// 初始化底部小点
		initDots();

	}

	// 动态设置小点
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.guide_ll);
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

		dots = new ImageView[guide_Image.length];

		// 循环取得小点图片
		for (int i = 0; i < guide_Image.length; i++) {
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
		if (position < 0 || position >= guide_Image.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */

	private void setCurDot(int positon) {
		if (positon < 0 || positon > guide_Image.length - 1
				|| currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	// 当滑动状态改变时调用

	@Override
	public void onPageScrollStateChanged(int arg0) { // TODO
	// Auto-generated method stub

	}

	// 当当前页面被滑动时调用

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) { //
	// TODO Auto-generated method stub

	}

	// 当新的页面被选中时调用

	@Override
	public void onPageSelected(int arg0) { // 设置底部小点选中状态
		setCurDot(arg0);
		
		int pos = arg0 % guide_Image.length;  
        
//        moveCursorTo(pos);  
          
        if (pos == guide_Image.length-1) {// 到最后一张了   
//            handler.sendEmptyMessageDelayed(TO_THE_END, 500); 
        	startButton.setVisibility(View.VISIBLE);
              
        } else{  
//            handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);  
        	startButton.setVisibility(View.GONE);
        }  
//        curPos = pos;  
	}

	Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            if (msg.what == TO_THE_END)  
                startButton.setVisibility(View.VISIBLE);  
            else if (msg.what == LEAVE_FROM_END)  
                startButton.setVisibility(View.GONE);  
        }  
    }; 
	
	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

}