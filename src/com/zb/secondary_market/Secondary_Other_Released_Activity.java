package com.zb.secondary_market;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.fragment.Secondary_Other_Released_Fragment;
import com.zb.secondary_market.imageblur.BlurUtil;
import com.zb.secondary_market.imessage.IMClient;

public class Secondary_Other_Released_Activity extends FragmentActivity {
	private static final String TAG = "Other_Released_Activity";
	private static final String IMAGE_CACHE_DIR = "thumbs";
	
	private static int RESULT_LOAD_IMAGE = 0;
	private static int CAMERA_REQUEST_CODE = 1;

	private String picPath = null;
	
	private IMClient client;

	private LinearLayout personal_info_background;
	private RelativeLayout my_released, user_complain, my_info, my_setting;
	private Button exitButton;

	private String UpdateNo = "当前为最新版本，无需升级！";
	private String UpdateYes = "发现新版本，即将升级！";
	private String update_url = "暂时未知";

	private int tabheight = 0;
	private Uri photoUri = null;

	private Secondary_Other_Released_Fragment secondary_Other_Released_Fragment;
	
	private TextView vid;
	private TextView gps_school;
	private TitleView mTitle;
//	private RelativeLayout me_help_setting;
//	private RelativeLayout me_aboutus_about, me_aboutus_update,
//			me_aboutus_clear;
//	private RelativeLayout me_help_gpsschool;

	private TextView me_aboutus_clear_text;
	private LinearLayout gps_school_ll;

	private boolean isregister;
	
	private Button message_btnButton;
	private PopupWindow pop;
	private LinearLayout ll_pop;

	private AsynImageLoader asynImageLoader;
	private CircularImageView cover_user_photo;

//	private String requestString, nicknameString, gps_schoolString;

	private Intent intent = new Intent();

	private static final int MB = 1024 * 1024;

	private String cache;

	private LocationClient locationClient = null;
	private static final int UPDATE_TIME = 5000;
	private static int LOCATION_COUTNS = 0;
	private static boolean flag = false;
	
	private View parentView;
	
	private ScrollView scrollView;

	private String school_name_locatedString = "default";

//	private String request_vid;
//	private String request_imageurl;
	
	private String other_nickname, other_vid, other_photo, other_school;
	
	/*@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		 * case R.id.id_other_released_manage_ll_ll1:
		 * intent.setClass(Other_Released_Activity.this,
		 * Secondary_Me_Activity.class); startActivity(intent);
		 * 
		 * break;
		 * 
		 * case R.id.id_other_released_manage_ll_ll2:
		 * intent.setClass(Other_Released_Activity.this,
		 * Friend_Me_Activity.class); startActivity(intent);
		 * 
		 * break;
		 * 
		 * case R.id.id_other_released_manage_ll_ll3:
		 * intent.setClass(Other_Released_Activity.this,
		 * Campaigner_Me_Activity.class); startActivity(intent);
		 * 
		 * break;
		 

		case R.id.id_other_released_help_ll_ll111:
			Intent intent_released = new Intent();
			intent_released.setClass(Other_Released_Activity.this,
					My_Released_Activity.class);
			startActivity(intent_released);
			break;

		case R.id.id_other_released_help_ll_ll1:
			if (gps_schoolString.equals("null")) {

				// 创建启动GPS定位对话框
				AlertDialog isExit = new AlertDialog.Builder(this).create();
				// 设置对话框标题
				isExit.setTitle("系统提示");
				// 设置对话框消息
				isExit.setMessage("为了保障您和其他用户的权益，我们希望启动GPS定位您所在的学校位置");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener_gps);
				isExit.setButton2("取消", listener_gps);
				// 显示对话框
				isExit.show();
			} else {
				Toast.makeText(Other_Released_Activity.this,
						"您已经完成了校区认证，不需要重新认证", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.id_other_released_help_ll_ll2:
			Intent intent_message = new Intent();
			intent_message.putExtra("intent_type", "normal");
			intent_message.setClass(Other_Released_Activity.this,
					My_Message_Activity.class);
			startActivity(intent_message);
			break;

		case R.id.id_other_released_aboutus_ll_ll2:
			ClearTask clearTask = new ClearTask();
			clearTask.execute(cache);
			break;

		case R.id.id_other_released_aboutus_ll_ll3:
			Intent intent_about = new Intent();
			intent_about.setClass(Other_Released_Activity.this,
					AboutUs_Activity.class);
			startActivity(intent_about);
			break;

		case R.id.id_other_released_aboutus_ll_ll1:
			if (updateTask != null
					&& updateTask.getStatus() == AsyncTask.Status.RUNNING) {
				updateTask.cancel(true);
			}
			updateTask = new UpdateTask();
			updateTask.execute();
			break;

		case R.id.id_other_released_help_ll_ll3:
			Intent intent_help_setting = new Intent();
			break;

		case R.id.id_other_released_exit_btn:

			client = Login_Activity.getClientInstance();

			isregister = false;
			// 存储对象
			SharedPreferences sharedPreferences = Other_Released_Activity.this
					.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
			Editor editor = sharedPreferences.edit();// 获取编辑器
			editor.putBoolean("isregister", isregister);
			editor.commit();// 提交修改

			intent.setClass(Other_Released_Activity.this,
					Login_Activity.class);
			startActivity(intent);

			if (client != null) {
				Log.v(TAG + "client != null", "yes");
				Log.v(TAG + "client.getUsername()", client.getUsername());
				
				 * try { client.login(); } catch (Exception e) { // TODO: handle
				 * exception }
				 
				client.Logout();
				Log.v(TAG + "client != null", "no");
			}
			finish();

			// 此处关闭Service
			Intent intent = new Intent(this, ImessageService.class);
			stopService(intent);

		default:
			break;
		}
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		parentView = getLayoutInflater().inflate(R.layout.activity_other_released,
				null);

		setContentView(parentView);
		
		Intent intent = getIntent();
		other_nickname = intent.getStringExtra("other_nickname");
		other_vid = intent.getStringExtra("other_vid");
		other_school = intent.getStringExtra("other_school");
		other_photo = intent.getStringExtra("other_photo");
		
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

		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 12;


			// @Override
			// public void onReceivePoi(BDLocation location) {
			// }

		personal_info_background = (LinearLayout) findViewById(R.id.id_other_released_info_ll);
//		nickname = (TextView) findViewById(R.id.id_other_released_info_ll_ll1_nickname);
		vid = (TextView) findViewById(R.id.id_other_released_info_ll_ll1_id);
		gps_school = (TextView) findViewById(R.id.id_other_released_info_ll_ll1_gps_school);
		gps_school_ll = (LinearLayout) findViewById(R.id.id_other_released_info_ll_ll1_gps_ll);
		message_btnButton = (Button)findViewById(R.id.id_other_released_info_ll_ll1_message_btn);
		scrollView = (ScrollView)findViewById(R.id.id_other_released_scroll);
		
		scrollView.smoothScrollTo(0, 0);
		
		message_btnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("type", "0");
				intent.putExtra("nickname_other", other_nickname);
//				intent.putExtra("request_other", request);
				intent.setClass(Secondary_Other_Released_Activity.this,
						Secondary_Imessage_Activity.class);
				startActivity(intent);
			}
		});
		
		setBackground(R.drawable.personal_setting_background1);

//		// 获取对象
//		SharedPreferences share = getSharedPreferences("jonny",
//				Activity.MODE_WORLD_READABLE);
//		// isregister_test = share.getBoolean("isregister", false);
//		requestString = share.getString("request", null);
//		nicknameString = share.getString("nickname", null);
//		gps_schoolString = share.getString("gps_school", null);
//
//		// Log.v(TAG, isregister + "");
//		// Log.v(TAG, isregister_test + "");
//
//		request_vid = requestString.split("@@")[0];
//		request_imageurl = requestString.split("@@")[1];

		/*
		 * Toast.makeText(Other_Released_Activity.this, request_vid + "*" +
		 * request_imageurl, Toast.LENGTH_SHORT) .show();
		 */

		// 此处设置nickname和user_id(vid)
//		nickname.setText(other_nickname);
		vid.setText(other_vid);

		if (other_school.equals("null")) {
			gps_school.setText("未认证学校");
			// gps_school_ll.setBackgroundResource(R.drawable.corner_shape55);
		} else {
			gps_school.setText(other_school);
		}

		// 此处设置我的里面的头像
		asynImageLoader = new AsynImageLoader(other_nickname);
		cover_user_photo = (CircularImageView) findViewById(R.id.id_other_released_info_ll_photo);
		asynImageLoader.showImageAsyn(cover_user_photo, other_photo,
				R.drawable.login_photo_default);
		// cover_user_photo.setImageResource(R.drawable.sssss);

		mTitle = (TitleView) findViewById(R.id.id_other_released_title);
//		me_help_setting = (RelativeLayout) findViewById(R.id.id_other_released_help_ll_ll3);
//		me_help_gpsschool = (RelativeLayout) findViewById(R.id.id_other_released_help_ll_ll1);
//		me_aboutus_about = (RelativeLayout) findViewById(R.id.id_other_released_aboutus_ll_ll3);
//		me_aboutus_clear = (RelativeLayout) findViewById(R.id.id_other_released_aboutus_ll_ll2);
//		me_aboutus_update = (RelativeLayout) findViewById(R.id.id_other_released_aboutus_ll_ll1);
//		me_aboutus_clear_text = (TextView) findViewById(R.id.id_other_released_aboutus_ll_ll2_clear);

//		me_help_setting.setOnClickListener(this);
//		me_help_gpsschool.setOnClickListener(this);
//		me_aboutus_about.setOnClickListener(this);
//		me_aboutus_clear.setOnClickListener(this);
//		me_aboutus_update.setOnClickListener(this);

		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
		mTitle.setTitle(other_nickname);

		mTitle.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		
		Replace_fragment(0);
	}
	
	/**
	 * 设置毛玻璃背景
	 * 
	 * @param id
	 *            背景图片id
	 */
	@SuppressWarnings("deprecation")
	private void setBackground(int id) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);// 从资源文件中得到图片，并生成Bitmap图片
		final Bitmap blurBmp = BlurUtil.fastblur(
				Secondary_Other_Released_Activity.this, bmp, 25);// 0-25，表示模糊值
		final Drawable newBitmapDrawable = new BitmapDrawable(blurBmp); // 将Bitmap转换为Drawable
		personal_info_background.post(new Runnable() // 调用UI线程
				{
					@Override
					public void run() {
						personal_info_background
								.setBackgroundDrawable(newBitmapDrawable);// 设置背景
					}
				});
	}
	
	public void Replace_fragment(int num) {
		switch (num) {
		case 0:
			// 实例化Fragment页面
			secondary_Other_Released_Fragment = new Secondary_Other_Released_Fragment(other_vid);
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_secondary_me_released = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_secondary_me_released.replace(
					R.id.id_other_released_fl, secondary_Other_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_secondary_me_released.commit();

			break;

		/*case 1:

			// 实例化Fragment页面
			secondary_Other_Released_Fragment = new Secondary_Other_Released_Fragment(other_vid);
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_secondary_me_message = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_secondary_me_message.replace(
					R.id.id_other_released_fl, secondary_Other_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_secondary_me_message.commit();

			break;*/

		default:
			break;
		}
	}
}

