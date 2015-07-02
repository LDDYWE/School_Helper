package com.zb.secondary_market;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.AsynImage.AsynImageLoader_Message;
import com.zb.secondary_market.AsynImage.AsynImageLoader_refresh;
import com.zb.secondary_market.adapter.Content_Image_ViewPagerAdapter;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.custom.TitleView.OnImageRightButtonClickListener;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;
import com.zb.secondary_market.register.HttpPostFile;

public class Content_Detail_Secondary_Activity extends FragmentActivity
		implements OnClickListener, OnPageChangeListener {
	private static final String TAG = "Content_Detail_Activity";
	private static final String IMAGE_CACHE_DIR = "thumbs_high";

//	private LinearLayout school_gps_ll;
	private TitleView contenTitleView;
	private int title_height = 0;
	private ViewPager vp;
	private Content_Image_ViewPagerAdapter vpAdapter;
	private List<View> views;

	private ImageFetcher mImageFetcher;
	private ImageView icon_message;

	private TextView discription_text;
	private TextView title_text, price_text, originalprice_text, type_text,
			amount_text, datetime_text, nickname_text, gps_school_text;

	// 引导图片资源
	private static final int[] pics = { R.drawable.test, R.drawable.test,
			R.drawable.test, R.drawable.test };

	// 底部小点图片
	private ImageView[] dots;
	private CircularImageView nickname_image;
	private AsynImageLoader_refresh asynImageLoader;

	// 记录当前选中位置
	private int currentIndex;

	private Item_Detail item_detail = new Item_Detail();
	private List<String> imageurl = new ArrayList<String>();

	private int position = 0;
	private String type = null;

	private String request;
	
	private String nicknameString;
	
	private String request_otherString;
	
	private String ssString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_content_detail);

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;

		Log.v(TAG, currentapiVersion + "");

		if (currentapiVersion > 8) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());

			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
					.build());
		}

		Intent intent = new Intent();
		item_detail = (Item_Detail) getIntent().getSerializableExtra(
				"item_detail");
		position = intent.getIntExtra("position", 0);

		for (int i = 0; i < item_detail.getImageurl().split("@@").length; i++) {
			imageurl.add(item_detail.getImageurl().split("@@")[i]);
		}
		
		request_otherString = HttpPostFile.upload_chat(item_detail.getNickname());

		// 上传log日志至服务器端
		// 获取对象
		SharedPreferences share = getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		nicknameString = share.getString("nickname", null);
		// String requestString = share.getString("request", null);
		String vidString = share.getString("vid", null);
		Time local = new Time();
		local.set(System.currentTimeMillis());
		String dateString = local.format2445();

		// Log.v(TAG, vidString);

		String log_infoString = vidString + "*" + nicknameString + "*"
				+ "跳蚤市场—查看" + "*" + item_detail.getTitle() + "*" + "0";

		String request_logs = HttpPostFile.upload_logs(log_infoString);
		Log.v(TAG + "request_other", request_logs);

		// set memory cache 25% of the app memory
		ImageCacheParams cacheParams = new ImageCacheParams(this,
				IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory
		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously

		mImageFetcher = new ImageFetcher(this, 500);
		mImageFetcher.setLoadingImage(R.drawable.load_failed);
		mImageFetcher.addImageCache(Content_Detail_Secondary_Activity.this
				.getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);

		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		// 初始化引导图片列表
		for (int i = 0; i < imageurl.size(); i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setScaleType(ScaleType.FIT_XY);

			encode(imageurl.get(i));
			
			mImageFetcher.loadImage(ssString, iv);

			views.add(iv);
		}
		vp = (ViewPager) findViewById(R.id.viewpager1);
		// 初始化Adapter
		vpAdapter = new Content_Image_ViewPagerAdapter(
				Content_Detail_Secondary_Activity.this, views, imageurl);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);

		init();

		// 初始化底部小点
		initDots();

		titleViewInit();
		title_text.setText(item_detail.getTitle());
		price_text.setText("￥" + item_detail.getPrice());
		
		if(!item_detail.getOriginalPrice().equals("")){
			originalprice_text.setText("￥" + item_detail.getOriginalPrice());
			//给TextView中的文字中间加横线
			originalprice_text.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); 
			originalprice_text.setVisibility(View.VISIBLE);
		}else{
			originalprice_text.setVisibility(View.GONE);
			}
		
		type_text.setText(item_detail.getType());
		amount_text.setText(item_detail.getAmount());

		asynImageLoader  = new AsynImageLoader_refresh(item_detail.getNickname());
		nickname_image = (CircularImageView) findViewById(R.id.id_personal_setting_info_ll_photo);
		asynImageLoader.showImageAsyn(nickname_image, request_otherString,
				R.drawable.login_photo_default);
		
		nickname_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//如果是自己的帖子，则跳转到个人中心界面
				if(item_detail.getNickname().equals(nicknameString)){
					Intent intent = new Intent();
					intent.setClass(Content_Detail_Secondary_Activity.this,
							Personal_Setting_Activity.class);
					startActivity(intent);
				}else{//如果是别人的帖子，则点击头像跳转到别人的发布界面
					Intent intent = new Intent();
					intent.putExtra("other_nickname", item_detail.getNickname());
					intent.putExtra("other_vid", item_detail.getVid());
					intent.putExtra("other_school", item_detail.getLocation());
					intent.putExtra("other_photo", request_otherString);
					intent.setClass(Content_Detail_Secondary_Activity.this,
							Secondary_Other_Released_Activity.class);
					startActivity(intent);
				}
			}
		});
		
		String datetimeString = item_detail.getDateTime().split("-")[0] + "-"
				+ item_detail.getDateTime().split("-")[1] + "-"
				+ item_detail.getDateTime().split("-")[2];
		datetime_text.setText(datetimeString);
		discription_text.setText(item_detail.getDiscription());
		nickname_text.setText(item_detail.getNickname());
		
		if(item_detail.getLocation().equals("null")){
			gps_school_text.setText("未认证学校");
//			school_gps_ll.setBackgroundResource(R.drawable.corner_shape55);
		}else{
			gps_school_text.setText(item_detail.getLocation());
		}
		
		/*String vid_otherString = item_detail.getNickname() + "*"
				+ item_detail.getVid();

		request = HttpPostFile.upload_chat(vid_otherString);

		Log.v(TAG + "奔跑吧兄弟", request);*/
	}

	private void init() {
		// TODO Auto-generated method stub
		title_text = (TextView) findViewById(R.id.id_detail_content_ll1_name);
		price_text = (TextView) findViewById(R.id.id_detail_content_ll1_ll_price);
		originalprice_text = (TextView) findViewById(R.id.id_detail_content_ll1_ll_originalprice);
		type_text = (TextView) findViewById(R.id.id_detail_content_ll3_ll1_type);
		amount_text = (TextView) findViewById(R.id.id_detail_content_ll3_ll2_amount);
		datetime_text = (TextView) findViewById(R.id.id_detail_content_ll3_ll3_datetime);
		discription_text = (TextView) findViewById(R.id.id_detail_content_ll4_goodsdetail);
		nickname_text = (TextView) findViewById(R.id.id_detail_content_ll5_nickName);
		gps_school_text = (TextView)findViewById(R.id.id_detail_content_ll5_gps_school);
//		school_gps_ll = (LinearLayout)findViewById(R.id.id_detail_content_school_gps);
		
		icon_message = (ImageView) findViewById(R.id.id_detail_content_ll55_message_icon);

		icon_message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(item_detail.getNickname().equals(nicknameString)){
					Toast.makeText(Content_Detail_Secondary_Activity.this, "您无法和自己进行聊天", Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent();
					intent.putExtra("type", "0");
					intent.putExtra("nickname_other", item_detail.getNickname());
//					intent.putExtra("request_other", request);
					intent.setClass(Content_Detail_Secondary_Activity.this,
							Secondary_Imessage_Activity.class);
					startActivity(intent);
				}
			}
		});
	}

	public void titleViewInit() {
		WindowManager vmManager = getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();
		contenTitleView = (TitleView) findViewById(R.id.id_detail_content_title);

		title_height = height / 12;
		contenTitleView
				.setLayoutParams(LayoutParams.MATCH_PARENT, title_height);

		contenTitleView.setTitle("商品详情");

		contenTitleView.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						
						// 存储对象
						SharedPreferences sharedPreferences = Content_Detail_Secondary_Activity.this
								.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
						Editor editor = sharedPreferences.edit();// 获取编辑器
						editor.putString("section_type", "other");
						editor.commit();// 提交修改
						
						finish();
					}
				});

		/*contenTitleView.setImageRightButton(R.drawable.arrow_right,
				new OnImageRightButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						Toast.makeText(Content_Detail_Secondary_Activity.this,
								"选择您所想分享的平台", Toast.LENGTH_SHORT).show();
					}
				});*/
	}

	// 动态设置小点
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll1);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			// 存储对象
			SharedPreferences sharedPreferences = Content_Detail_Secondary_Activity.this
					.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
			Editor editor = sharedPreferences.edit();// 获取编辑器
			editor.putString("section_type", "other");
			editor.commit();// 提交修改
			
			finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
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
