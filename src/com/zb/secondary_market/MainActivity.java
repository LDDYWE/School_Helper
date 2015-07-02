package com.zb.secondary_market;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.exit.ActivityManager;
import com.zb.secondary_market.fragment.Campaigner_Mainpage_Fragment;
import com.zb.secondary_market.fragment.Friend_Mainpage_Fragment;
import com.zb.secondary_market.fragment.MenuLeftFragment;
import com.zb.secondary_market.fragment.MenuLeftFragment.OnArticleSelectedListener;
import com.zb.secondary_market.fragment.School_Activity_Fragment;
import com.zb.secondary_market.fragment.School_Conference_Fragment;
import com.zb.secondary_market.fragment.School_Notice_Fragment;
import com.zb.secondary_market.fragment.Secondary_Mainpage_Fragment;
import com.zb.secondary_market.fragment.Work_Internal_Fragment;
import com.zb.secondary_market.fragment.Work_Parttime_Fragment;
import com.zb.secondary_market.fragment.Work_Recruit_Fragment;
import com.zb.secondary_market.imessage.IMClient;
import com.zb.secondary_market.service.ImessageService;
import com.zb.secondary_market.update.UpdateInfo;
import com.zb.secondary_market.update.UpdateInfoParser;
import com.zb.secondary_market.update.UpdateManager;

public class MainActivity extends SlidingFragmentActivity implements
		OnArticleSelectedListener, OnClickListener {
	private static final String TAG = "MainActivity";

	private LayoutInflater layoutInflater;
	private ActivityManager exitM;

	// private FragmentTabHost mTabHost;
	// private Class<?> fragmentArray[] = { Secondary_Mainpage_Fragment.class,
	// Assortment_Fragment.class, Photograph_Fragment.class,
	// Content_Fragment.class, Content_Fragment.class };
	// private int mImageViewArray[] = { R.drawable.tab_mainpage_image,
	// R.drawable.tab_assortment_image, R.drawable.tab_release_image,
	// R.drawable.tab_discover_image, R.drawable.tab_me_image };
	// private String mTextviewArray[] = { "首页", "分类", "发布", "发现", "我" };
	private int mainHeight;
	// private SlidingMenu slidingMenu;
	private SlidingMenu menu;

	private TextView nickname, vid;
	// private AsynImageLoader asynImageLoader;
	// private CircularImageView cover_user_photo;

	private TitleView mTitleView;

	private FrameLayout frameLayout;

	private Secondary_Mainpage_Fragment secondary_Mainpage_Fragment = new Secondary_Mainpage_Fragment();
	private Campaigner_Mainpage_Fragment campaigner_Mainpage_Fragment = new Campaigner_Mainpage_Fragment();
	private Friend_Mainpage_Fragment friend_Mainpage_Fragment = new Friend_Mainpage_Fragment();
	private Work_Recruit_Fragment work_Recruit_Fragment = new Work_Recruit_Fragment();
	private Work_Internal_Fragment work_Internal_Fragment = new Work_Internal_Fragment();
	private Work_Parttime_Fragment work_Parttime_Fragment = new Work_Parttime_Fragment();
	private School_Notice_Fragment school_Notice_Fragment = new School_Notice_Fragment();
	private School_Conference_Fragment school_Conference_Fragment = new School_Conference_Fragment();
	private School_Activity_Fragment school_Activity_Fragment = new School_Activity_Fragment();

	private RelativeLayout secondaryLayout, friendLayout, worklLayout,
			notificationLayout, settingLayout;

	private FrameLayout recruitF1, internF1, part_timeF1;
	private ImageView recruitIv, internIv, part_timeIv;
	private TextView recruitTv, internTv, part_timeTv;
	private Button release_btn;

	private String section_type = "secondary";
	private boolean isintent_onstart = false;

	private boolean isRecruit = true;
	private boolean isIntern, isPartime;
	private boolean isOpen = false;
	private boolean headphoto_is_changed = false;

	private boolean iswork;

	private boolean isfirst = true;

	private UpdateTask updateTask;
	private UpdateManager mUpdateManager;

	private String requestString, nicknameString, passwordString;

	private IMClient client;

	private String request_vid;
	private String request_imageurl;

	private String update_url = "http://202.38.73.228/ustc_helper/update.xml";

	private MyServiceConnection conn;
	private ImessageService.MyBinder myBinder;

	private int tabheight;

	// private UpdateManager mUpdateManager;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击招聘按钮
		case R.id.layout_recruit_temperary_work:
			clickRecruitBtn();
			break;
		case R.id.layout_intern_temperary_work:
			clickInternBtn();
			break;
		// 点击兼职按钮
		case R.id.layout_parttime_temperary_work:
			clickParttimeBtn();
			break;
		// 点击我的按钮
		// case R.id.layout_me_temperary_work:
		// clickMeBtn();
		// break;
		}
	}

	@Override
	public void onArticleSelected(int n) {
		// TODO Auto-generated method stub
		switch (n) {
		case 0:
			frameLayout.setVisibility(View.GONE);

			menu.toggle();

			mTitleView.setTitle("二手市场");

			release_btn.setVisibility(View.VISIBLE);

			release_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(MainActivity.this,
							Secondary_PhotoGraph_Activity.class);
					startActivity(intent);
				}
			});

			/*
			 * mTitleView.setImageRightButton(R.drawable.edit, new
			 * OnImageRightButtonClickListener() {
			 * 
			 * @Override public void onClick(View Imagebutton) { // TODO
			 * Auto-generated method stub Intent intent = new Intent();
			 * intent.setClass(MainActivity.this,
			 * Secondary_PhotoGraph_Activity.class); startActivity(intent);
			 * 
			 * // finish(); } });
			 */

			new Handler().postDelayed(new Runnable() {
				public void run() {
					// execute the task

					secondary_Mainpage_Fragment = new Secondary_Mainpage_Fragment();
					//
					FragmentTransaction ft_secondary = getSupportFragmentManager()
							.beginTransaction();
					ft_secondary.replace(R.id.id_main_mainframelayout,
							secondary_Mainpage_Fragment);
					ft_secondary
							.setTransition(FragmentTransaction.TRANSIT_NONE);
					ft_secondary.commit();

				}
			}, 400);

			break;

		case 1:
			frameLayout.setVisibility(View.GONE);
			menu.toggle();

			mTitleView.setTitle("校园交友");

			release_btn.setVisibility(View.VISIBLE);

			release_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(MainActivity.this,
							Friend_PhotoGraph_Activity.class);
					startActivity(intent);
				}
			});

			/*
			 * mTitleView.setImageRightButton(R.drawable.edit, new
			 * OnImageRightButtonClickListener() {
			 * 
			 * @Override public void onClick(View Imagebutton) { // TODO
			 * Auto-generated method stub Intent intent = new Intent();
			 * intent.setClass(MainActivity.this,
			 * Friend_PhotoGraph_Activity.class); startActivity(intent);
			 * 
			 * // finish(); } });
			 */

			new Handler().postDelayed(new Runnable() {
				public void run() {
					// execute the task

					friend_Mainpage_Fragment = new Friend_Mainpage_Fragment();
					//
					FragmentTransaction ft_friend = getSupportFragmentManager()
							.beginTransaction();
					ft_friend.replace(R.id.id_main_mainframelayout,
							friend_Mainpage_Fragment);
					ft_friend
							.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
					ft_friend.commit();

				}
			}, 400);

			break;

		case 2:
			frameLayout.setVisibility(View.GONE);
			menu.toggle();

			mTitleView.setTitle("活动发起");

			release_btn.setVisibility(View.VISIBLE);

			release_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(MainActivity.this,
							Campaigner_PhotoGraph_Activity.class);
					startActivity(intent);
				}
			});

			/*
			 * mTitleView.setImageRightButton(R.drawable.edit, new
			 * OnImageRightButtonClickListener() {
			 * 
			 * @Override public void onClick(View Imagebutton) { // TODO
			 * Auto-generated method stub Intent intent = new Intent();
			 * intent.setClass(MainActivity.this,
			 * Campaigner_PhotoGraph_Activity.class); startActivity(intent);
			 * 
			 * // finish(); } });
			 */

			new Handler().postDelayed(new Runnable() {
				public void run() {
					// execute the task

					campaigner_Mainpage_Fragment = new Campaigner_Mainpage_Fragment();
					//
					FragmentTransaction ft_campaigner = getSupportFragmentManager()
							.beginTransaction();
					ft_campaigner.replace(R.id.id_main_mainframelayout,
							campaigner_Mainpage_Fragment);
					ft_campaigner
							.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
					ft_campaigner.commit();

				}
			}, 400);

			break;

		case 3:
			iswork = true;

			frameLayout.setVisibility(View.VISIBLE);

			menu.toggle();

			mTitleView.setTitle("招聘信息");

			mTitleView.setImageRightButton(R.color.transparent);

			release_btn.setVisibility(View.GONE);

			/*
			 * mTitleView.setImageRightButton(R.drawable.edit, new
			 * OnImageRightButtonClickListener() {
			 * 
			 * @Override public void onClick(View Imagebutton) { // TODO
			 * Auto-generated method stub Intent intent = new Intent();
			 * intent.setClass(MainActivity.this,
			 * Campaigner_PhotoGraph_Activity.class); startActivity(intent);
			 * 
			 * // finish(); } });
			 */

			/*
			 * work_Recruit_Fragment = new Work_Recruit_Fragment();
			 * FragmentTransaction ft_job = getSupportFragmentManager()
			 * .beginTransaction(); ft_job.replace(R.id.id_main_mainframelayout,
			 * work_Recruit_Fragment);
			 * ft_job.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
			 * ft_job.commit();
			 */

			initView();
			initData();

			recruitTv.setText("招聘");
			internTv.setText("实习");
			part_timeTv.setText("兼职");

			new Handler().postDelayed(new Runnable() {
				public void run() {
					// execute the task

					Replace_fragment_Work(0);

				}
			}, 400);

			/*
			 * Intent intent_work = new Intent();
			 * intent_work.setClass(MainActivity.this, Work_Activity.class);
			 * startActivity(intent_work);
			 */

			break;

		case 4:
			/*
			 * Intent intent_school = new Intent();
			 * intent_school.setClass(MainActivity.this, School_Activity.class);
			 * startActivity(intent_school);
			 */
			iswork = false;
			frameLayout.setVisibility(View.VISIBLE);

			menu.toggle();

			mTitleView.setTitle("通知信息");

			mTitleView.setImageRightButton(R.color.transparent);

			release_btn.setVisibility(View.GONE);

			/*
			 * mTitleView.setImageRightButton(R.drawable.edit, new
			 * OnImageRightButtonClickListener() {
			 * 
			 * @Override public void onClick(View Imagebutton) { // TODO
			 * Auto-generated method stub Intent intent = new Intent();
			 * intent.setClass(MainActivity.this,
			 * Campaigner_PhotoGraph_Activity.class); startActivity(intent);
			 * 
			 * // finish(); } });
			 */

			/*
			 * work_Recruit_Fragment = new Work_Recruit_Fragment();
			 * FragmentTransaction ft_job = getSupportFragmentManager()
			 * .beginTransaction(); ft_job.replace(R.id.id_main_mainframelayout,
			 * work_Recruit_Fragment);
			 * ft_job.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
			 * ft_job.commit();
			 */

			initView();
			initData();

			recruitTv.setText("通知");
			internTv.setText("会议");
			part_timeTv.setText("活动");

			new Handler().postDelayed(new Runnable() {
				public void run() {
					// execute the task
					Replace_fragment_School(0);

				}
			}, 400);

			/*
			 * Intent intent_work = new Intent();
			 * intent_work.setClass(MainActivity.this, Work_Activity.class);
			 * startActivity(intent_work);
			 */

			break;

		case 5:
			Intent intent_setting = new Intent();
			intent_setting.setClass(MainActivity.this,
					Personal_Setting_Activity.class);
			startActivity(intent_setting);

			// menu.toggle();
			break;

		default:
			break;
		}
	}

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		WindowManager vmManager = MainActivity.this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 11;

		if (updateTask != null
				&& updateTask.getStatus() == AsyncTask.Status.RUNNING) {
			updateTask.cancel(true);
		}
		updateTask = new UpdateTask();
		updateTask.execute();

		// Intent intent = getIntent();
		// section_type = intent.getStringExtra("section_type");

		/*
		 * // 获取对象 SharedPreferences share = getSharedPreferences("jonny",
		 * Activity.MODE_WORLD_READABLE); // isregister_test =
		 * share.getBoolean("isregister", false); // section_type =
		 * share.getString("section_type", "secondary"); requestString =
		 * share.getString("request", null); nicknameString =
		 * share.getString("nickname", null); passwordString =
		 * share.getString("password", null);
		 * 
		 * Log.v(TAG + "requestString", requestString);
		 * 
		 * request_vid = requestString.split("@@")[0]; request_imageurl =
		 * requestString.split("@@")[1];
		 * 
		 * Log.v(TAG + "request_imageurl", request_imageurl);
		 */

		initSlidingMenu();

		init();

		startService(new Intent(this, ImessageService.class));

		bindServices();

		// 存储对象
		SharedPreferences sharedPreferences = MainActivity.this
				.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString("section_type", "secondary");
		editor.commit();// 提交修改

		// 添加Activity，为了后面可以直接退出程序
		exitM = ActivityManager.getInstance();
		exitM.addActivity(MainActivity.this);

		// slidingMenu = (SlidingMenu) findViewById(R.id.id_slidemenu);
		// secondaryLayout = (RelativeLayout)
		// findViewById(R.id.id_secondarylayout);
		// friendLayout = (RelativeLayout) findViewById(R.id.id_friendlayout);
		mTitleView = (TitleView) findViewById(R.id.main_title);
		frameLayout = (FrameLayout) findViewById(R.id.id_main_mainframelayout2);
		release_btn = (Button) findViewById(R.id.id_release_btn);

		// mTitleView.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);

		mTitleView.setTitle("二手市场");

		mTitleView.setImageLeftButton(R.drawable.menu1,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						getSlidingMenu().showMenu();
					}
				});

		release_btn.setVisibility(View.VISIBLE);

		release_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,
						Secondary_PhotoGraph_Activity.class);
				startActivity(intent);
			}
		});

		/*
		 * mTitleView.setImageRightButton(R.drawable.edit, new
		 * OnImageRightButtonClickListener() {
		 * 
		 * @Override public void onClick(View Imagebutton) { // TODO
		 * Auto-generated method stub Intent intent = new Intent();
		 * intent.setClass(MainActivity.this,
		 * Secondary_PhotoGraph_Activity.class); startActivity(intent);
		 * 
		 * // finish(); } });
		 */
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		// 获取对象
		SharedPreferences share = getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		// isregister_test = share.getBoolean("isregister", false);
		section_type = share.getString("section_type", "secondary");
		headphoto_is_changed = share.getBoolean("headphoto_is_changed", false);
		isintent_onstart = share.getBoolean("isintent_onstart", false);

		// 存储对象
		SharedPreferences sharedPreferences = MainActivity.this
				.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putBoolean("headphoto_is_changed", false);
		editor.commit();// 提交修改

		Log.v(TAG + "headphoto_is_changed", headphoto_is_changed + "");

		// requestString = share.getString("request", null);
		// nicknameString = share.getString("nickname", null);
		// passwordString = share.getString("password", null);
		//
		// Log.v(TAG + "requestString", requestString);
		//
		// request_vid = requestString.split("@@")[0];
		// request_imageurl = requestString.split("@@")[1];

		Log.v(TAG + "section_type", section_type);

		if (isintent_onstart == true) {
			// 这一步实现发布之后对fragment进行更新
			if (section_type.equals("secondary")) {
				secondary_Mainpage_Fragment = new Secondary_Mainpage_Fragment();

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				ft.replace(R.id.id_main_mainframelayout,
						secondary_Mainpage_Fragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			} else if (section_type.equals("friend")) {
				friend_Mainpage_Fragment = new Friend_Mainpage_Fragment();

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				ft.replace(R.id.id_main_mainframelayout,
						friend_Mainpage_Fragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			} else if (section_type.equals("campaigner")) {
				campaigner_Mainpage_Fragment = new Campaigner_Mainpage_Fragment();

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				ft.replace(R.id.id_main_mainframelayout,
						campaigner_Mainpage_Fragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			} else if (section_type.equals("other")) {

			}

			// 存储对象
			SharedPreferences sharedPreferences1 = MainActivity.this
					.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
			Editor editor1 = sharedPreferences.edit();// 获取编辑器
			editor.putBoolean("isintent_onstart", false);

		} else {
			if (isfirst == true) {
				// 这一步实现发布之后对fragment进行更新
				if (section_type.equals("secondary")) {
					secondary_Mainpage_Fragment = new Secondary_Mainpage_Fragment();

					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					ft.replace(R.id.id_main_mainframelayout,
							secondary_Mainpage_Fragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				} else if (section_type.equals("friend")) {
					friend_Mainpage_Fragment = new Friend_Mainpage_Fragment();

					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					ft.replace(R.id.id_main_mainframelayout,
							friend_Mainpage_Fragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				} else if (section_type.equals("campaigner")) {
					campaigner_Mainpage_Fragment = new Campaigner_Mainpage_Fragment();

					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					ft.replace(R.id.id_main_mainframelayout,
							campaigner_Mainpage_Fragment);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				} else if (section_type.equals("other")) {

				}
			} else {

			}

		}

		// 判断是否修改了头像，若修改了，则调接口通知MenuLeftFragment修改

		Log.v(TAG + "headphoto_is_changed", headphoto_is_changed + "");

		if (headphoto_is_changed == true) {
			MenuLeftFragment leftMenuFragment = new MenuLeftFragment(
					MainActivity.this);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.id_left_menu_frame, leftMenuFragment)
					.commit();
		}
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		recruitF1 = (FrameLayout) findViewById(R.id.layout_recruit_temperary_work);
		internF1 = (FrameLayout) findViewById(R.id.layout_intern_temperary_work);
		part_timeF1 = (FrameLayout) findViewById(R.id.layout_parttime_temperary_work);
		// meF1 = (FrameLayout) findViewById(R.id.layout_me_temperary_work);

		// 实例化图片组件对象
		recruitIv = (ImageView) findViewById(R.id.image_recruit_temperary_work);
		internIv = (ImageView) findViewById(R.id.image_intern_temperary_work);
		part_timeIv = (ImageView) findViewById(R.id.image_parttime_temperary_work);
		// meIv = (ImageView) findViewById(R.id.image_me_temperary_work);

		// 实例化TextView组件对象
		recruitTv = (TextView) findViewById(R.id.text_recruit_temperary_work);
		internTv = (TextView) findViewById(R.id.text_intern_temperary_work);
		part_timeTv = (TextView) findViewById(R.id.text_parttime_temperary_work);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 给布局对象设置监听
		recruitF1.setOnClickListener(this);
		internF1.setOnClickListener(this);
		part_timeF1.setOnClickListener(this);
		// meF1.setOnClickListener(this);
	}

	/**
	 * 点击了“招聘”按钮
	 */
	private void clickRecruitBtn() {

		if (iswork == true) {
			if (isRecruit == true) {
			} else {
				Replace_fragment_Work(0);
			}
		} else {
			if (isRecruit == true) {
			} else {
				Replace_fragment_School(0);
			}
		}

		isRecruit = true;
		isIntern = false;
		isPartime = false;
	}

	/**
	 * 点击了“实习”按钮
	 */
	private void clickInternBtn() {
		Log.v(TAG + "1", "1");
		if (iswork == true) {
			Log.v(TAG + "2", "2");
			if (isIntern == true) {
				Log.v(TAG + "3", "3");
			} else {
				Log.v(TAG + "4", "4");
				Replace_fragment_Work(1);
				Log.v(TAG + "5", "5");
			}
		} else {
			if (isRecruit == true) {
			} else {
				Replace_fragment_School(1);
			}
		}

		isIntern = true;
		isRecruit = false;
		isPartime = false;
	}

	/**
	 * 点击了“兼职”按钮
	 */
	private void clickParttimeBtn() {
		if (iswork == true) {
			if (isPartime == true) {

			} else {
				Replace_fragment_Work(2);
			}
		} else {
			if (isRecruit == true) {
			} else {
				Replace_fragment_School(2);
			}
		}

		isPartime = true;
		isRecruit = false;
		isIntern = false;
	}

	public void Replace_fragment_School(int num) {
		switch (num) {
		case 0:
			mTitleView.setTitle("通知信息");
			// 实例化Fragment页面
			school_Notice_Fragment = new School_Notice_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_recruit = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_recruit.replace(R.id.id_main_mainframelayout,
					school_Notice_Fragment);
			// 事务管理提交
			fragmentTransaction_recruit.commit();

			recruitF1.setSelected(true);
			recruitIv.setSelected(true);

			internF1.setSelected(false);
			internIv.setSelected(false);

			part_timeF1.setSelected(false);
			part_timeIv.setSelected(false);

			// meF1.setSelected(false);
			// meIv.setSelected(false);

			break;

		case 1:
			mTitleView.setTitle("会议信息");
			Log.v(TAG, "点击了会议按钮");
			// 实例化Fragment页面
			school_Conference_Fragment = new School_Conference_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_intern = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_intern.replace(R.id.id_main_mainframelayout,
					school_Conference_Fragment);
			// 事务管理提交
			fragmentTransaction_intern.commit();

			recruitF1.setSelected(false);
			recruitIv.setSelected(false);

			internF1.setSelected(true);
			internIv.setSelected(true);

			part_timeF1.setSelected(false);
			part_timeIv.setSelected(false);

			// meF1.setSelected(false);
			// meIv.setSelected(false);
			break;

		case 2:
			mTitleView.setTitle("活动信息");
			// 实例化Fragment页面
			school_Activity_Fragment = new School_Activity_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_parttime = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_parttime.replace(R.id.id_main_mainframelayout,
					school_Activity_Fragment);
			// 事务管理提交
			fragmentTransaction_parttime.commit();

			recruitF1.setSelected(false);
			recruitIv.setSelected(false);

			internF1.setSelected(false);
			internIv.setSelected(false);

			part_timeF1.setSelected(true);
			part_timeIv.setSelected(true);

			// meF1.setSelected(false);
			// meIv.setSelected(false);

			break;

		/*
		 * case 3: // 实例化Fragment页面 recruitFragment = new
		 * Work_Recruit_Fragment(); // 得到Fragment事务管理器 FragmentTransaction
		 * fragmentTransaction_me = this
		 * .getSupportFragmentManager().beginTransaction(); // 替换当前的页面
		 * fragmentTransaction_me.replace(R.id.frame_content_temperary_work,
		 * recruitFragment); // 事务管理提交 fragmentTransaction_me.commit();
		 * 
		 * // meF1.setSelected(true); // meIv.setSelected(true);
		 * 
		 * recruitF1.setSelected(false); recruitIv.setSelected(false);
		 * 
		 * part_timeF1.setSelected(false); part_timeIv.setSelected(false);
		 * 
		 * internF1.setSelected(false); internIv.setSelected(false);
		 * 
		 * break;
		 */
		default:
			break;
		}
	}

	public void Replace_fragment_Work(int num) {
		switch (num) {
		case 0:
			mTitleView.setTitle("招聘信息");

			// 实例化Fragment页面
			work_Recruit_Fragment = new Work_Recruit_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_recruit = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_recruit.replace(R.id.id_main_mainframelayout,
					work_Recruit_Fragment);
			// 事务管理提交
			fragmentTransaction_recruit.commit();

			recruitF1.setSelected(true);
			recruitIv.setSelected(true);

			internF1.setSelected(false);
			internIv.setSelected(false);

			part_timeF1.setSelected(false);
			part_timeIv.setSelected(false);

			// meF1.setSelected(false);
			// meIv.setSelected(false);

			break;

		case 1:
			mTitleView.setTitle("实习信息");
			Log.v(TAG, "点击了实习按钮");
			// 实例化Fragment页面
			work_Internal_Fragment = new Work_Internal_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_intern = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_intern.replace(R.id.id_main_mainframelayout,
					work_Internal_Fragment);
			// 事务管理提交
			fragmentTransaction_intern.commit();

			internF1.setSelected(true);
			internIv.setSelected(true);

			recruitF1.setSelected(false);
			recruitIv.setSelected(false);

			part_timeF1.setSelected(false);
			part_timeIv.setSelected(false);

			// meF1.setSelected(false);
			// meIv.setSelected(false);
			break;

		case 2:
			mTitleView.setTitle("兼职信息");
			// 实例化Fragment页面
			work_Parttime_Fragment = new Work_Parttime_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_parttime = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_parttime.replace(R.id.id_main_mainframelayout,
					work_Parttime_Fragment);
			// 事务管理提交
			fragmentTransaction_parttime.commit();

			part_timeF1.setSelected(true);
			part_timeIv.setSelected(true);

			recruitF1.setSelected(false);
			recruitIv.setSelected(false);

			internF1.setSelected(false);
			internIv.setSelected(false);

			// meF1.setSelected(false);
			// meIv.setSelected(false);

			break;

		/*
		 * case 3: // 实例化Fragment页面 recruitFragment = new
		 * Work_Recruit_Fragment(); // 得到Fragment事务管理器 FragmentTransaction
		 * fragmentTransaction_me = this
		 * .getSupportFragmentManager().beginTransaction(); // 替换当前的页面
		 * fragmentTransaction_me.replace(R.id.frame_content_temperary_work,
		 * recruitFragment); // 事务管理提交 fragmentTransaction_me.commit();
		 * 
		 * // meF1.setSelected(true); // meIv.setSelected(true);
		 * 
		 * recruitF1.setSelected(false); recruitIv.setSelected(false);
		 * 
		 * part_timeF1.setSelected(false); part_timeIv.setSelected(false);
		 * 
		 * internF1.setSelected(false); internIv.setSelected(false);
		 * 
		 * break;
		 */
		default:
			break;
		}
	}

	class UpdateTask extends AsyncTask<Void, Integer, UpdateInfo> {
		@Override
		protected void onPreExecute() {
			Log.v("UpdateTask", "onPreExecute");
		}

		@Override
		protected UpdateInfo doInBackground(Void... params) {
			// TODO Auto-generated method stub
			UpdateInfo updateInfo = new UpdateInfo();
			UpdateInfoParser updateInfoParser = new UpdateInfoParser();
			updateInfo = updateInfoParser.getUpdateInfo(update_url);

			Log.v(TAG + "updateInfo", updateInfo.getUrl());

			return updateInfo;
		}

		@Override
		protected void onPostExecute(UpdateInfo updateInfo) {
			Log.e("UpdateTask", "onPostExecute");
			UpdateInfoHandler(updateInfo);
		}
	}

	public void UpdateInfoHandler(UpdateInfo updateInfo) {
		try {
			if ((updateInfo != null)
					&& (!updateInfo.getVersionName().equals(getVersionName()))) {
				// Toast.makeText(this, UpdateYes, Toast.LENGTH_SHORT).show();
				mUpdateManager = new UpdateManager(this);
				mUpdateManager.setUrl(updateInfo.getUrl(),
						updateInfo.getDescription());
				mUpdateManager.checkUpdateInfo();
			} else {
				// Toast.makeText(this, UpdateNo, Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getVersionName() throws Exception {
		PackageManager packageManager = MainActivity.this.getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(
				MainActivity.this.getPackageName(), 0);
		return packInfo.versionName;
	}

	private void initSlidingMenu() {

		MenuLeftFragment leftMenuFragment = new MenuLeftFragment(
				MainActivity.this);
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
		menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// menu.setBehindWidth()
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		// menu.setBehindScrollScale(1.0f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);

		menu.setOnClosedListener(new OnClosedListener() {

			@Override
			public void onClosed() {
				// TODO Auto-generated method stub
				isOpen = false;
			}
		});

		menu.setOnOpenedListener(new OnOpenedListener() {

			@Override
			public void onOpened() {
				// TODO Auto-generated method stub
				isOpen = true;
			}
		});
		// 设置右边（二级）侧滑菜单
		// menu.setSecondaryMenu(R.layout.right_menu_frame);
		// MenuRightFragment rightMenuFragment = new MenuRightFragment();
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.id_right_menu_frame, rightMenuFragment).commit();
	}

	private void init() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		View mView = inflater.inflate(R.layout.left_menu, null);

		secondaryLayout = (RelativeLayout) mView
				.findViewById(R.id.id_secondarylayout);

		// nickname = (TextView) mView.findViewById(R.id.id_leftMenu_ll_ll_t1);
		// vid = (TextView) mView.findViewById(R.id.id_leftMenu_ll_ll_t2);
		//
		// nickname.setText(nicknameString);
		// vid.setText(request_vid);

		// asynImageLoader = new AsynImageLoader(nicknameString);
		// cover_user_photo = (CircularImageView) mView
		// .findViewById(R.id.id_personal_setting_info_ll_photo_leftMenu);
		// asynImageLoader.showImageAsyn(cover_user_photo, request_imageurl,
		// R.drawable.login_photo_default);
	}

	// 绑定服务
	private void bindServices() {
		// TODO Auto-generated method stub
		// 开启服务
		Intent service = new Intent(MainActivity.this, ImessageService.class);
		conn = new MyServiceConnection();
		// startService(service)只能单纯的开启一个服务，要想调用服务服务中的方法，必须用bindService和unbindService
		// startService(service);
		bindService(service, conn, Context.BIND_AUTO_CREATE);
	}

	/**
	 * 自定义一个类，实现ServiceConnection接口，并重写其两个方法
	 * 
	 * @author Administrator
	 * 
	 */
	public class MyServiceConnection implements ServiceConnection {
		// 当绑定服务成功的时候会调用此方法
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 得到MyService.MyBinder对象，我们通过这个对象来操作服务中的方法
			myBinder = (ImessageService.MyBinder) service;
			// 调用服务中的getname()方法并把值设置到TextView上进行显示
			client = myBinder.getClientInstance();
		}

		public void onServiceDisconnected(ComponentName name) {
		}
	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isOpen == true) {
				isOpen = false;
			} else {
				exitBy2Click(); // 调用双击退出函数
			}
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}
}
