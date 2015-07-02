package com.zb.secondary_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zb.secondary_market.fragment.Friend_Mainpage_Fragment;
import com.zb.secondary_market.fragment.Friend_Me_Fragment;

public class Friend_Activity extends FragmentActivity implements
		OnClickListener {
	private static final String TAG = "Friend_Activity";

	// private LayoutInflater layoutInflater;
	// private FragmentActivity mActivity;
	// private View mparent;
	// private FragmentTabHost mTabHost;
	// private Class<?> fragmentArray[] = { Mainpage_Fragment.class,
	// Assortment_Fragment.class, Photograph_Fragment.class,
	// Content_Fragment.class, Content_Fragment.class };
	// private int mImageViewArray[] = { R.drawable.tab_mainpage_image,
	// R.drawable.tab_assortment_image, R.drawable.tab_release_image,
	// R.drawable.tab_discover_image, R.drawable.tab_me_image };
	// private String mTextviewArray[] = { "首页", "分类", "发布", "发现", "我" };
	// private int mainHeight;

	private Friend_Mainpage_Fragment fragmentPage1;
	// private Friend_Mainpage_Fragment fragmentPage2;
	// private Friend_Mainpage_Fragment fragmentPage3;
	// private Friend_Me_Fragment fragmentPage4;

	// private RelativeLayout secondaryLayout, friendLayout, worklLayout,
	// notificationLayout, settingLayout;

	// 定义布局对象
	private FrameLayout mainpageF1, meFl;

	// 定义图片组件对象
	private ImageView mainpageIv, meIv;

	// 定义按钮图片组件
	private ImageView toggleImageView, plusImageView;

	private boolean isMainpage = true;
	private boolean isMe = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_friend);

		// initView();
		Log.v(TAG, "1");

		initData();
		Log.v(TAG, "2");
		
		Replace_mainpagefragment();
		Log.v(TAG, "3");

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	/**
	 * 初始化组件
	 */
	/*
	 * private void initView() { // 实例化布局对象 mainpageF1 = (FrameLayout)
	 * findViewById(R.id.layout_mainpage_temperary_friend); // myfeedF1 =
	 * (FrameLayout) // findViewById(R.id.layout_myfeed_temperary_friend); //
	 * homeF1 = (FrameLayout) //
	 * findViewById(R.id.layout_home_temperary_friend); meFl = (FrameLayout)
	 * findViewById(R.id.layout_me_temperary_friend);
	 * 
	 * // 实例化图片组件对象 mainpageIv = (ImageView)
	 * findViewById(R.id.image_mainpage_temperary_friend); // myfeedIv =
	 * (ImageView) // findViewById(R.id.image_myfeed_temperary_friend); //
	 * homeIv = (ImageView) findViewById(R.id.image_home_temperary_friend); meIv
	 * = (ImageView) findViewById(R.id.image_me_temperary_friend);
	 * 
	 * }
	 */

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 给布局对象设置监听
		// mainpageF1.setOnClickListener(this);
		// myfeedF1.setOnClickListener(this);
		// homeF1.setOnClickListener(this);
		// meFl.setOnClickListener(this);

		// 给按钮图片设置监听
//		toggleImageView = (ImageView) findViewById(R.id.toggle_btn_temperary_friend);
//		toggleImageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击动态按钮
		// case R.id.layout_mainpage_temperary_friend:
		// clickFriendfeedBtn();
		// break;

		// // 点击与我相关按钮
		// case R.id.layout_myfeed:
		// clickMyfeedBtn();
		// break;

		// // 点击我的空间按钮
		// case R.id.layout_home:
		// clickHomeBtn();
		// break;

		// 点击更多按钮
		// case R.id.layout_me_temperary_friend:
		// clickMoreBtn();
		// break;

//		case R.id.toggle_btn_temperary_friend:
//			clickToggleBtn();
//			break;
		}
	}

	public void Replace_mainpagefragment() {
		// 实例化Fragment页面
		fragmentPage1 = new Friend_Mainpage_Fragment();
		// 得到Fragment事务管理器
		FragmentTransaction fragmentTransaction = this
				.getSupportFragmentManager().beginTransaction();
		// 替换当前的页面
		fragmentTransaction.replace(R.id.frame_content_temperary_friend,
				fragmentPage1);
		// 事务管理提交
		fragmentTransaction.commit();

		// mainpageF1.setSelected(true);
		// mainpageIv.setSelected(true);

		// myfeedF1.setSelected(false);
		// myfeedIv.setSelected(false);
		//
		// myfeedF1.setSelected(false);
		// homeIv.setSelected(false);

		// meFl.setSelected(false);
		// meIv.setSelected(false);
	}

	/**
	 * 点击了“动态”按钮
	 */
	/*
	 * private void clickFriendfeedBtn() { if (isMainpage == true) {
	 * 
	 * } else { Replace_mainpagefragment(); }
	 * 
	 * isMe = false; isMainpage = true; }
	 */

	/**
	 * 点击了“与我相关”按钮
	 */
	/*
	 * private void clickMyfeedBtn() { // 实例化Fragment页面 fragmentPage2 = new
	 * Assortment_Fragment(); // 得到Fragment事务管理器 FragmentTransaction
	 * fragmentTransaction =
	 * mActivity.getSupportFragmentManager().beginTransaction(); // 替换当前的页面
	 * fragmentTransaction.replace(R.id.frame_content, fragmentPage2); // 事务管理提交
	 * fragmentTransaction.commit();
	 * 
	 * friendfeedFl.setSelected(false); friendfeedIv.setSelected(false);
	 * 
	 * // myfeedFl.setSelected(true); // myfeedIv.setSelected(true); // //
	 * homeFl.setSelected(false); // homeIv.setSelected(false);
	 * 
	 * moreFl.setSelected(false); moreIv.setSelected(false); }
	 */

	/**
	 * 点击了“我的空间”按钮
	 */

	/*
	 * private void clickHomeBtn() { // 实例化Fragment页面 fragmentPage3 = new
	 * Content_Fragment(); // 得到Fragment事务管理器 FragmentTransaction
	 * fragmentTransaction = mActivity.getSupportFragmentManager()
	 * .beginTransaction(); // 替换当前的页面
	 * fragmentTransaction.replace(R.id.frame_content, fragmentPage3); // 事务管理提交
	 * fragmentTransaction.commit();
	 * 
	 * friendfeedFl.setSelected(false); friendfeedIv.setSelected(false);
	 * 
	 * // myfeedFl.setSelected(false); // myfeedIv.setSelected(false); // //
	 * homeFl.setSelected(true); // homeIv.setSelected(true);
	 * 
	 * moreFl.setSelected(false); moreIv.setSelected(false); }
	 */

	/*
	 * public void Replace_mefragment() { Log.v(TAG, "clickMoreBtn");
	 * 
	 * // 实例化Fragment页面 fragmentPage4 = new Friend_Me_Fragment(); //
	 * 得到Fragment事务管理器 FragmentTransaction fragmentTransaction = this
	 * .getSupportFragmentManager().beginTransaction(); // 替换当前的页面
	 * fragmentTransaction .replace(R.id.frame_content_temperary_friend,
	 * fragmentPage4); // 事务管理提交 fragmentTransaction.commit();
	 * 
	 * mainpageF1.setSelected(false); mainpageIv.setSelected(false);
	 * 
	 * // myfeedFl.setSelected(false); // myfeedIv.setSelected(false); // //
	 * homeFl.setSelected(false); // homeIv.setSelected(false);
	 * 
	 * meFl.setSelected(true); meIv.setSelected(true); }
	 */

	/**
	 * 点击了“更多”按钮
	 */
	/*
	 * private void clickMoreBtn() {
	 * 
	 * if (isMe == true) {
	 * 
	 * } else { Replace_mefragment(); }
	 * 
	 * isMainpage = false; isMe = true; }
	 */

	/**
	 * 点击了中间按钮
	 */
	private void clickToggleBtn() {
		// Toast.makeText(this, "中间按钮被点击了", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(Friend_Activity.this, Friend_PhotoGraph_Activity.class);
		startActivity(intent);
		// showPopupWindow(toggleImageView);
		// //改变按钮显示的图片为按下时的状态
		// plusImageView.setImageResource(R.drawable.toolbar_plusback);
		// toggleImageView.setImageResource(R.drawable.toolbar_btn_pressed);
	}
}
