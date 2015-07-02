package com.zb.secondary_market;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.AsynImage.AsynImageLoader_refresh;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;
import com.zb.secondary_market.register.HttpPostFile;

public class Content_Detail_Campaigner_Activity extends FragmentActivity {
	private static final String TAG = "Content_Detail_Campaigner_Activity";
	private static final String IMAGE_CACHE_DIR = "thumbs_high";

	private ImageFetcher mImageFetcher;

	private ImageView imageView;
	private CircularImageView nickname_image;
	private AsynImageLoader_refresh asynImageLoader;
			
	private TitleView mTitleView;

	private TextView typeTextView;
	private TextView titleTextView;
	private TextView nicknameTextView;
	private TextView schoolTextView;
	private TextView costTextView;
	private TextView placeTextView;
	private TextView datetimeTextView;
	private TextView discriptionTextView;

//	private LinearLayout gps_school_ll;

	private LinearLayout send_messageButton;

	private Item_Detail item_detail = new Item_Detail();
	private List<String> imageurl = new ArrayList<String>();
	private int position = 0;

	private String request;
	private String request_otherString;

	private String nicknameString;
	
	private String ssString;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_content_detail_compaigner);

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

		mTitleView = (TitleView) findViewById(R.id.id_campaigner_mainpage_list_item_title);
		mTitleView.setTitle("活动详情");

		mTitleView.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						// 存储对象
						SharedPreferences sharedPreferences = Content_Detail_Campaigner_Activity.this
								.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
						Editor editor = sharedPreferences.edit();// 获取编辑器
						editor.putString("section_type", "other");
						editor.commit();// 提交修改
						finish();
					}
				});

		init();

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
				+ "活动发起—查看" + "*" + item_detail.getTitle() + "*" + "0";

		String request_logs = HttpPostFile.upload_logs(log_infoString);
		// Log.v(TAG, request);

		// set memory cache 25% of the app memory
		ImageCacheParams cacheParams = new ImageCacheParams(this,
				IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory
		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously

		mImageFetcher = new ImageFetcher(this, 500);
		mImageFetcher.setLoadingImage(R.drawable.load_failed);
		mImageFetcher.addImageCache(Content_Detail_Campaigner_Activity.this
				.getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);

		if (item_detail.getImageurl().toString().equals("false")) {
			imageView.setImageResource(R.drawable.music_200_200);
		} else {
			
			encode(imageurl.get(0));
			
			mImageFetcher.loadImage(ssString, imageView);
		}

		typeTextView.setText(item_detail.getType());
		titleTextView.setText(item_detail.getTitle());
		nicknameTextView.setText(item_detail.getNickname());

		if (item_detail.getLocation().equals("null")) {
			schoolTextView.setText("未认证学校");
//			gps_school_ll.setBackgroundResource(R.drawable.corner_shape55);
		} else {
			schoolTextView.setText(item_detail.getLocation());
		}

		asynImageLoader  = new AsynImageLoader_refresh(item_detail.getNickname());
		nickname_image = (CircularImageView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_nickname_image);
		asynImageLoader.showImageAsyn(nickname_image, request_otherString,
				R.drawable.login_photo_default);
		
		nickname_image.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//如果是自己的帖子，则跳转到个人中心界面
				if(item_detail.getNickname().equals(nicknameString)){
					Intent intent = new Intent();
					intent.setClass(Content_Detail_Campaigner_Activity.this,
							Personal_Setting_Activity.class);
					startActivity(intent);
				}else{//如果是别人的帖子，则点击头像跳转到别人的发布界面
					Intent intent = new Intent();
					intent.putExtra("other_nickname", item_detail.getNickname());
					intent.putExtra("other_vid", item_detail.getVid());
					intent.putExtra("other_school", item_detail.getLocation());
					intent.putExtra("other_photo", request_otherString);
					intent.setClass(Content_Detail_Campaigner_Activity.this,
							Campaigner_Other_Released_Activity.class);
					startActivity(intent);
				}
			}
		});
		
		String datetimeString = item_detail.getDateTime().split("-")[0] + "-"
				+ item_detail.getDateTime().split("-")[1] + "-"
				+ item_detail.getDateTime().split("-")[2];

		datetimeTextView.setText(datetimeString);
		costTextView.setText(item_detail.getPrice());
		placeTextView.setText(item_detail.getLocation());
		discriptionTextView.setText(item_detail.getDiscription());

		/*
		 * String vid_otherString = item_detail.getNickname() + "*" +
		 * item_detail.getVid();
		 * 
		 * request = HttpPostFile.upload_chat(vid_otherString);
		 * 
		 * Log.v(TAG + "奔跑吧兄弟", request);
		 */
	}

	private void init() {
		// TODO Auto-generated method stub
		typeTextView = (TextView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_type);
		titleTextView = (TextView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_title);
		nicknameTextView = (TextView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_nickname);
		schoolTextView = (TextView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_schoolll_school);
		datetimeTextView = (TextView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_datetime);
		costTextView = (TextView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_cost);
		placeTextView = (TextView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_place);
		discriptionTextView = (TextView) findViewById(R.id.id_campaigner_mainpage_list_item_textll_detail);
//		gps_school_ll = (LinearLayout) findViewById(R.id.id_campaigner_mainpage_list_item_textll_schoolll);

		nickname_image = (CircularImageView)findViewById(R.id.id_campaigner_mainpage_list_item_textll_nickname_image);
		imageView = (ImageView) findViewById(R.id.id_campaigner_mainpage_list_item_image);

		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("imageurl", (Serializable) imageurl);
				intent.setClass(Content_Detail_Campaigner_Activity.this, ViewPager_ImageDisplay_Activity.class);
				startActivity(intent);
			}
		});
		
		WindowManager vmManager = Content_Detail_Campaigner_Activity.this
				.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		LayoutParams para;
		para = imageView.getLayoutParams();
		//
		para.height = height / 3;
		// para.width = para.height;
		imageView.setLayoutParams(para);

		send_messageButton = (LinearLayout) findViewById(R.id.id_detail_content_temporary_campaigner_linearlayout_message_ll);

		send_messageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (item_detail.getNickname().equals(nicknameString)) {
					Toast.makeText(Content_Detail_Campaigner_Activity.this,
							"您无法和自己进行聊天", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.putExtra("type", "0");
					intent.putExtra("nickname_other", item_detail.getNickname());
					// intent.putExtra("request_other", request);
					intent.setClass(Content_Detail_Campaigner_Activity.this,
							Campaigner_Imessage_Activity.class);
					startActivity(intent);
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			// 存储对象
			SharedPreferences sharedPreferences = Content_Detail_Campaigner_Activity.this
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
