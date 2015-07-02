package com.zb.secondary_market;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zb.secondary_market.adapter.Jump_Assortment_GridViewAdapter;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.exit.ActivityManager;
import com.zb.secondary_market.fragment.MenuLeftFragment;
import com.zb.secondary_market.fragment.MenuRightFragment;
import com.zb.secondary_market.service.ImessageService;

public class Jump_Activity extends SlidingFragmentActivity {
	private static final String TAG = "Jump_Activity";

	private Button secondaryButton, friendButton, workButton;
	private GridView jump_assortment_gridview;
	private Jump_Assortment_GridViewAdapter jump_Assortment_GridViewAdapter;
	private TitleView mTitle;

	private int item_height = 0;

	// private String nicknameString, passwordString;

	private boolean isregister = true;
	private boolean isregister_test;

	private ActivityManager exitM;

	private String requestString;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_jump);
		
		initSlidingMenu();

		startService(new Intent(this, ImessageService.class));

		/*
		 * //创建一个DatabaseHelper类的对象，该类是单独一个java文件,这里采用2个参数的构造函数，建立的数据
		 * //库的名字为tornadomeet.db DatabaseHelper database_helper = new
		 * DatabaseHelper(Jump_Activity.this, "tornadomeet.db4"); //
		 * helper.getReadableDatabase();//获取一个只读的数据库 只能查询 不能写入 不能更新
		 * SQLiteDatabase db = database_helper.getWritableDatabase();
		 * 
		 * Cursor c = db.rawQuery("select * from stutb", null); if (c!=null) {
		 * String [] cols = c.getColumnNames(); while (c.moveToNext()) { for
		 * (String ColumnName : cols) { Log.i("info",
		 * ColumnName+":"+c.getString(c.getColumnIndex(ColumnName))); } }
		 * c.close(); } db.close();
		 */

		/*
		 * Intent intent = getIntent(); requestString =
		 * intent.getStringExtra("request");
		 */

		/*
		 * 用于获取用户存储在本地的昵称、密码以及vid和图片地址
		 * 
		 * // 获取对象 SharedPreferences share = getSharedPreferences("jonny",
		 * Activity.MODE_WORLD_READABLE); isregister_test =
		 * share.getBoolean("isregister", false); requestString =
		 * share.getString("request", null);
		 * 
		 * Log.v(TAG, isregister + ""); Log.v(TAG, isregister_test + "");
		 * 
		 * String request_vid = requestString.split("@@")[0]; String
		 * request_imageurl = requestString.split("@@")[1];
		 * 
		 * Toast.makeText(Jump_Activity.this, request_vid + "*" +
		 * request_imageurl, Toast.LENGTH_SHORT) .show();
		 */

		// 添加Activity，为了后面可以直接退出程序
		exitM = ActivityManager.getInstance();
		exitM.addActivity(Jump_Activity.this);

		/*
		 * // 存储对象 SharedPreferences sharedPreferences =
		 * Jump_Activity.this.getSharedPreferences("jonny",
		 * Context.MODE_PRIVATE); // 私有数据 Editor editor =
		 * sharedPreferences.edit();// 获取编辑器 editor.putBoolean("isregister",
		 * isregister); editor.putString("nickname", nicknameString);
		 * editor.putString("password", passwordString); editor.commit();// 提交修改
		 */

		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		item_height = width * 77 / 160;
		// item_height = width * 88 / 160;

		mTitle = (TitleView) findViewById(R.id.id_jump_titleview);
		mTitle.setTitle("科大助手");

		mTitle.setImageLeftButton(R.drawable.menu1,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						getSlidingMenu().showMenu();
					}
				});

		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, height / 12);

		jump_assortment_gridview = (GridView) findViewById(R.id.id_jump_grid);
		jump_Assortment_GridViewAdapter = new Jump_Assortment_GridViewAdapter(
				Jump_Activity.this, item_height);

		jump_assortment_gridview.setAdapter(jump_Assortment_GridViewAdapter);

		jump_assortment_gridview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();

						switch (position) {
						// 跳蚤市场
						case 0:
							// intent.putExtra("index",
							// "normal_enter_secondary");
							intent.setClass(Jump_Activity.this,
									Secondary_Activity.class);
							startActivity(intent);
							break;

						// 校园交友
						case 1:
							intent.setClass(Jump_Activity.this,
									Friend_Activity.class);
							startActivity(intent);
							break;

						// 工作信息
						case 2:
							intent.setClass(Jump_Activity.this,
									Work_Activity.class);
							startActivity(intent);
							break;

						// 学校通知
						case 3:
							intent.setClass(Jump_Activity.this,
									School_Activity.class);
							startActivity(intent);
							break;

						// 活动发起
						case 4:
							intent.setClass(Jump_Activity.this,
									Campaigner_Activity.class);
							startActivity(intent);
							break;

						// 个人中心
						case 5:
							intent.setClass(Jump_Activity.this,
									Personal_Setting_Activity.class);
							startActivity(intent);
							break;
						}
					}
				});

		/*
		 * secondaryButton = (Button)findViewById(R.id.id_jump_btn1);
		 * friendButton = (Button)findViewById(R.id.id_jump_btn2); workButton =
		 * (Button)findViewById(R.id.id_jump_btn3);
		 * 
		 * secondaryButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent();
		 * intent.setClass(Jump_Activity.this, Secondary_Activity.class);
		 * startActivity(intent); } });
		 * 
		 * friendButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent();
		 * intent.setClass(Jump_Activity.this, Friend_Activity.class);
		 * startActivity(intent); } });
		 * 
		 * workButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent();
		 * intent.setClass(Jump_Activity.this, Work_Activity.class);
		 * startActivity(intent); } });
		 */

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v(TAG, "---onStart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "---onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(TAG, "---onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v(TAG, "---onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(TAG, "---onDestroy");
	}

	private void initSlidingMenu() {

		MenuLeftFragment leftMenuFragment = new MenuLeftFragment(Jump_Activity.this);
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
		SlidingMenu menu = getSlidingMenu();
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
		// 设置右边（二级）侧滑菜单
//		 menu.setSecondaryMenu(R.layout.right_menu_frame);
//		 MenuRightFragment rightMenuFragment = new MenuRightFragment();
//		 getSupportFragmentManager().beginTransaction()
//		 .replace(R.id.id_right_menu_frame, rightMenuFragment).commit();
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				// finish();

				exitM.exit();

				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("真的准备离开了吗？");
			// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);
			// 显示对话框
			isExit.show();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
