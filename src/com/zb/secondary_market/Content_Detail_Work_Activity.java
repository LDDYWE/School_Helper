package com.zb.secondary_market;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.custom.TitleView.OnImageRightButtonClickListener;
import com.zb.secondary_market.otherclasses.Item_Detail;
import com.zb.secondary_market.register.HttpPostFile;

public class Content_Detail_Work_Activity extends Activity
		implements OnClickListener {
	private static final String TAG = "Content_Detail_Temporary_WithAddress_Activity";

	private int position = 0;
	private Item_Detail item_detail;

	private TextView detail_title, detail_author, detail_datatime,
			detail_contentaddress, detail_contentdetail, detail_contentsource;

	private String typeString;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private TitleView contenTitleView;
	private int title_height = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_content_detail_temporary_withaddress);

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
		
		Intent intent = getIntent();

		item_detail = (Item_Detail) getIntent().getSerializableExtra(
				"item_detail");
		position = intent.getIntExtra("position", 0);

		typeString = intent.getStringExtra("type");

		// 上传log日志至服务器端
		// 获取对象
		SharedPreferences share = getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		String nicknameString = share.getString("nickname", null);
		// String requestString = share.getString("request", null);
		String vidString = share.getString("vid", null);
		Time local = new Time();
		local.set(System.currentTimeMillis());
		String dateString = local.format2445();

		// Log.v(TAG, vidString);

		String log_infoString = vidString + "*" + nicknameString + "*" + "工作信息—查看"
				+ "*" + item_detail.getTitle() + "*" + "0";

		String request = HttpPostFile.upload_logs(log_infoString);
		// Log.v(TAG, request);

		Log.e("fuck", position + "");

		titleViewInit(typeString);
	}

	public void titleViewInit(String typeString) {
		WindowManager vmManager = getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();
		contenTitleView = (TitleView) findViewById(R.id.id_detail_content_title_temporary_address);
		detail_title = (TextView) findViewById(R.id.id_detail_content_ll_ll1_name_temporary_address);
		detail_author = (TextView) findViewById(R.id.id_detail_content_ll_ll2_author_temporary_address);
		detail_contentaddress = (TextView) findViewById(R.id.id_detail_content_ll_ll2_content_ll_address_temporary_address);
		detail_contentdetail = (TextView) findViewById(R.id.id_detail_content_ll_ll2_content_ll_detail_temporary_address);
		detail_contentsource = (TextView) findViewById(R.id.id_detail_content_ll_ll2_content_ll_source_temporary_address);

		title_height = height / 12;
		contenTitleView
				.setLayoutParams(LayoutParams.MATCH_PARENT, title_height);

		if (typeString.equals("recruit")) {
			contenTitleView.setTitle("招聘详情");
		} else if (typeString.equals("internal")) {
			contenTitleView.setTitle("实习详情");
		} else if (typeString.equals("parttime")) {
			contenTitleView.setTitle("兼职详情");
		} else if (typeString.equals("secondary")) {
			contenTitleView.setTitle("商品详情");
		} else if (typeString.equals("notice")) {
			contenTitleView.setTitle("通知详情");
		} else if (typeString.equals("conference")) {
			contenTitleView.setTitle("会议详情");
		} else if (typeString.equals("activity")) {
			contenTitleView.setTitle("活动详情");
		} else if (typeString.equals("friend")) {
			contenTitleView.setTitle("朋友详情");
		}

		detail_title.setText(item_detail.getTitle());

		if (item_detail.getNickname().toString().equals("")) {
			detail_author.setText("未知");
		} else {
			detail_author.setText(item_detail.getNickname());
		}

//		Log.v(TAG + "item_detail.getContentAddress()",
//				item_detail.getContentAddress());
//		Log.v(TAG + "item_detail.getContentDetail()",
//				item_detail.getContentDetail());
//		Log.v(TAG + "item_detail.getContentSource()",
//				item_detail.getContentSource());

		detail_contentaddress.setText(item_detail.getContentAddress());
		detail_contentdetail.setText(item_detail.getContentDetail());
		detail_contentsource.setText(item_detail.getContentSource());
		// detail_content.setText(item_detail.getContent());

		contenTitleView.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						// 存储对象
						SharedPreferences sharedPreferences = Content_Detail_Work_Activity.this
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
						Toast.makeText(
								Content_Detail_Work_Activity.this,
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
			SharedPreferences sharedPreferences = Content_Detail_Work_Activity.this
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
