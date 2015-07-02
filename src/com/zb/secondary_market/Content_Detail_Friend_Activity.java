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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.zb.secondary_market.adapter.FriendImageGridViewAdapter;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;
import com.zb.secondary_market.register.HttpPostFile;

public class Content_Detail_Friend_Activity extends FragmentActivity {
	private static final String TAG = "Content_Detail_Temporary_Friend_Activity";
	private static final String IMAGE_CACHE_DIR = "thumbs_high";

	private TitleView contenTitleView;
	private int title_height = 0;

	private int item_height = 0;

	private LinearLayout send_messageLatout;
	private LinearLayout report_messageLayout;

	private GridView mGridView;
	private FriendImageGridViewAdapter mFriendImageGridViewAdapter;

	private ImageFetcher mImageFetcher;

	private TextView sex_text, title_text, nickname_text, id_text,
			location_text, Signature_text, hometown_text, hobby_text,
			more_text;

	// 引导图片资源
	private static final int[] pics = { R.drawable.test, R.drawable.test,
			R.drawable.test, R.drawable.test };

	// 底部小点图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;

	private Item_Detail item_detail = new Item_Detail();
	private List<String> imageurl = new ArrayList<String>();

	private int position = 0;
	private String type = null;

	private String request;

	private String nicknameString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_content_detail_friend);

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
				+ "校园交友—查看" + "*" + item_detail.getTitle() + "*" + "0";

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
		mImageFetcher
				.addImageCache(Content_Detail_Friend_Activity.this
						.getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);

		init();

		// 初始化底部小点
		// initDots();

		titleViewInit();

		mFriendImageGridViewAdapter = new FriendImageGridViewAdapter(
				Content_Detail_Friend_Activity.this, mImageFetcher, imageurl,
				item_height);

		mGridView.setAdapter(mFriendImageGridViewAdapter);

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Content_Detail_Friend_Activity.this,
						ViewPager_ImageDisplay_Activity.class);
				intent.putExtra("imageurl", (Serializable) imageurl);

				startActivity(intent);
			}
		});

		title_text.setText(item_detail.getTitle());
		nickname_text.setText(item_detail.getNickname());
		if (item_detail.getLocation().equals("null")) {
			location_text.setText("未认证学校");
		} else {
			location_text.setText(item_detail.getLocation());
		}

		Signature_text.setText(item_detail.getDeclaration());
		hobby_text.setText(item_detail.getHobby());
		hometown_text.setText(item_detail.getHometown());
		more_text.setText(item_detail.getDiscription());
		sex_text.setText(item_detail.getSex());
		id_text.setText(item_detail.getVid());

		String datetimeString = item_detail.getDateTime().split("-")[0] + "-"
				+ item_detail.getDateTime().split("-")[1] + "-"
				+ item_detail.getDateTime().split("-")[2];
		// datetime_text.setText(datetimeString);

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
		mGridView = (GridView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll0_gv);
		sex_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll000_sex);
		title_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll00_title);
		nickname_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll1_nickname);
		id_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll2_id);
		location_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll3_authentication);
		Signature_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll4_signature);
		hometown_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll5_hometown);
		hobby_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll6_hobby);
		more_text = (TextView) findViewById(R.id.id_detail_content_temporary_friend_scrollview_ll_ll7_more);
		send_messageLatout = (LinearLayout) findViewById(R.id.id_detail_content_temporary_friend_linearlayout_message_ll);
		report_messageLayout = (LinearLayout)findViewById(R.id.id_detail_content_temporary_friend_linearlayout_report_ll);
		
		send_messageLatout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (item_detail.getNickname().equals(nicknameString)) {
					Toast.makeText(Content_Detail_Friend_Activity.this,
							"您无法和自己进行聊天", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.putExtra("type", "0");
					intent.putExtra("nickname_other", item_detail.getNickname());
					// intent.putExtra("request_other", request);
					intent.setClass(Content_Detail_Friend_Activity.this,
							Friend_Imessage_Activity.class);
					startActivity(intent);
				}
			}
		});
		
		report_messageLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void titleViewInit() {
		WindowManager vmManager = getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();
		contenTitleView = (TitleView) findViewById(R.id.id_detail_content_temporary_friend_title);

		title_height = height / 12;

		item_height = width / 4;

		contenTitleView
				.setLayoutParams(LayoutParams.MATCH_PARENT, title_height);

		contenTitleView.setTitle("帖子详情");

		contenTitleView.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						// 存储对象
						SharedPreferences sharedPreferences = Content_Detail_Friend_Activity.this
								.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
						Editor editor = sharedPreferences.edit();// 获取编辑器
						editor.putString("section_type", "other");
						editor.commit();// 提交修改
						finish();
					}
				});

	/*	contenTitleView.setImageRightButton(R.drawable.arrow_right,
				new OnImageRightButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						Toast.makeText(Content_Detail_Friend_Activity.this,
								"选择您所想分享的平台", Toast.LENGTH_SHORT).show();
					}
				});*/
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			// 存储对象
			SharedPreferences sharedPreferences = Content_Detail_Friend_Activity.this
					.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
			Editor editor = sharedPreferences.edit();// 获取编辑器
			editor.putString("section_type", "other");
			editor.commit();// 提交修改
			
			finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
