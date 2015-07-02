package com.zb.secondary_market.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zb.secondary_market.R;
import com.zb.secondary_market.Secondary_PhotoGraph_Activity;

public class Main_Fragment extends Fragment implements OnClickListener {
	private static final String TAG = "MainFragment";
	private LayoutInflater layoutInflater;
	private FragmentActivity mActivity;
	private View mparent;
	private FragmentTabHost mTabHost;
	private Class<?> fragmentArray[] = { Secondary_Mainpage_Fragment.class,
			Assortment_Fragment.class, Photograph_Fragment.class,
			Content_Fragment.class, Content_Fragment.class };
	private int mImageViewArray[] = { R.drawable.tab_mainpage_image,
			R.drawable.tab_assortment_image, R.drawable.tab_release_image,
			R.drawable.tab_discover_image, R.drawable.tab_me_image };
	private String mTextviewArray[] = { "首页", "分类", "发布", "发现", "我" };
	private int mainHeight;

	private Secondary_Mainpage_Fragment fragmentPage1;
	private Assortment_Fragment fragmentPage2;
	private Content_Fragment fragmentPage3;
	private Content_Fragment fragmentPage4;

	private RelativeLayout secondaryLayout, friendLayout, worklLayout,
			notificationLayout, settingLayout;

	// 定义布局对象
	private FrameLayout friendfeedFl, myfeedFl, homeFl, moreFl;

	// 定义图片组件对象
	private ImageView friendfeedIv, myfeedIv, homeIv, moreIv;

	// 定义按钮图片组件
	private ImageView toggleImageView, plusImageView;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		Log.v(TAG, "---onAttach");
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		Log.v(TAG, "---onActivityCreated");
		mActivity = getActivity();
		mparent = getView();

		initView();
		
		initData();
		
		clickFriendfeedBtn();
		Log.v(TAG, "---onActivityCreated");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Log.v(TAG, "---onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Log.v(TAG, "---onCreateView");

		return inflater.inflate(R.layout.main_test, null);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v(TAG, "---onStart");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(TAG, "---onPause");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "---onResume");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v(TAG, "---onStop");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(TAG, "---onDestroy");
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		friendfeedFl = (FrameLayout) mparent
				.findViewById(R.id.layout_friendfeed);
		myfeedFl = (FrameLayout) mparent.findViewById(R.id.layout_myfeed);
		homeFl = (FrameLayout) mparent.findViewById(R.id.layout_home);
		moreFl = (FrameLayout) mparent.findViewById(R.id.layout_more);

		// 实例化图片组件对象
		friendfeedIv = (ImageView) mparent.findViewById(R.id.image_friendfeed);
		myfeedIv = (ImageView) mparent.findViewById(R.id.image_myfeed);
		homeIv = (ImageView) mparent.findViewById(R.id.image_home);
		moreIv = (ImageView) mparent.findViewById(R.id.image_more);

		// 实例化按钮图片组件
		toggleImageView = (ImageView) mparent.findViewById(R.id.toggle_btn);
		// plusImageView = (ImageView) findViewById(R.id.plus_btn);

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 给布局对象设置监听
		friendfeedFl.setOnClickListener(this);
		myfeedFl.setOnClickListener(this);
		homeFl.setOnClickListener(this);
		moreFl.setOnClickListener(this);

		// 给按钮图片设置监听
		toggleImageView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击动态按钮
		case R.id.layout_friendfeed:
			clickFriendfeedBtn();
			break;
		// 点击与我相关按钮
		case R.id.layout_myfeed:
			clickMyfeedBtn();
			break;
		// 点击我的空间按钮
		case R.id.layout_home:
			clickHomeBtn();
			break;
		// 点击更多按钮
		case R.id.layout_more:
			clickMoreBtn();
			break;
		// 点击中间按钮
		case R.id.toggle_btn:
			clickToggleBtn();
			break;
		}
	}

	/**
	 * 点击了“动态”按钮
	 */
	private void clickFriendfeedBtn() {
		// 实例化Fragment页面
		fragmentPage1 = new Secondary_Mainpage_Fragment();
		// 得到Fragment事务管理器
		FragmentTransaction fragmentTransaction = mActivity
				.getSupportFragmentManager().beginTransaction();
		// 替换当前的页面
		fragmentTransaction.replace(R.id.frame_content, fragmentPage1);
		// 事务管理提交
		fragmentTransaction.commit();

		friendfeedFl.setSelected(true);
		friendfeedIv.setSelected(true);

		myfeedFl.setSelected(false);
		myfeedIv.setSelected(false);

		homeFl.setSelected(false);
		homeIv.setSelected(false);

		moreFl.setSelected(false);
		moreIv.setSelected(false);
	}

	/**
	 * 点击了“与我相关”按钮
	 */
	private void clickMyfeedBtn() {
		// 实例化Fragment页面
		fragmentPage2 = new Assortment_Fragment();
		// 得到Fragment事务管理器
		FragmentTransaction fragmentTransaction = 
				mActivity.getSupportFragmentManager().beginTransaction();
		// 替换当前的页面
		fragmentTransaction.replace(R.id.frame_content, fragmentPage2);
		// 事务管理提交
		fragmentTransaction.commit();

		friendfeedFl.setSelected(false);
		friendfeedIv.setSelected(false);

		myfeedFl.setSelected(true);
		myfeedIv.setSelected(true);

		homeFl.setSelected(false);
		homeIv.setSelected(false);

		moreFl.setSelected(false);
		moreIv.setSelected(false);
	}

	/**
	 * 点击了“我的空间”按钮
	 */
	private void clickHomeBtn() {
		// 实例化Fragment页面
		fragmentPage3 = new Content_Fragment();
		// 得到Fragment事务管理器
		FragmentTransaction fragmentTransaction = 
				mActivity.getSupportFragmentManager().beginTransaction();
		// 替换当前的页面
		fragmentTransaction.replace(R.id.frame_content, fragmentPage3);
		// 事务管理提交
		fragmentTransaction.commit();

		friendfeedFl.setSelected(false);
		friendfeedIv.setSelected(false);

		myfeedFl.setSelected(false);
		myfeedIv.setSelected(false);

		homeFl.setSelected(true);
		homeIv.setSelected(true);

		moreFl.setSelected(false);
		moreIv.setSelected(false);
	}

	/**
	 * 点击了“更多”按钮
	 */
	private void clickMoreBtn() {
		// 实例化Fragment页面
		fragmentPage4 = new Content_Fragment();
		// 得到Fragment事务管理器
		FragmentTransaction fragmentTransaction = 
				mActivity.getSupportFragmentManager().beginTransaction();
		// 替换当前的页面
		fragmentTransaction.replace(R.id.frame_content, fragmentPage4);
		// 事务管理提交
		fragmentTransaction.commit();

		friendfeedFl.setSelected(false);
		friendfeedIv.setSelected(false);

		myfeedFl.setSelected(false);
		myfeedIv.setSelected(false);

		homeFl.setSelected(false);
		homeIv.setSelected(false);

		moreFl.setSelected(true);
		moreIv.setSelected(true);
	}

	/**
	 * 点击了中间按钮
	 */
	private void clickToggleBtn() {
//		Toast.makeText(this, "中间按钮被点击了", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(mActivity, Secondary_PhotoGraph_Activity.class);
		startActivity(intent);
		// showPopupWindow(toggleImageView);
		// //改变按钮显示的图片为按下时的状态
		// plusImageView.setImageResource(R.drawable.toolbar_plusback);
		// toggleImageView.setImageResource(R.drawable.toolbar_btn_pressed);
	}

	/**
	 * 改变显示的按钮图片为正常状态
	 */
	private void changeButtonImage() {
		// plusImageView.setImageResource(R.drawable.toolbar_plus);
		// toggleImageView.setImageResource(R.drawable.toolbar_btn_normal);
	}

	/*
	 * public void initView() { layoutInflater = LayoutInflater.from(mActivity);
	 * mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
	 * mTabHost.setup(mActivity, mActivity.getSupportFragmentManager(),
	 * R.id.realtabcontent); int count = fragmentArray.length; for (int i = 0; i
	 * < count; i++) { TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
	 * .setIndicator(getTabItemView(i)); Bundle bundle = new Bundle();
	 * bundle.putInt("tabheight", getTabHostHeight());
	 * bundle.putInt("statusBarHeight", getStatusBarHeight());
	 * bundle.putInt("height", getHeight()); mTabHost.addTab(tabSpec,
	 * fragmentArray[i], bundle);
	 * 
	 * // mTabHost.getTabWidget().getChildAt(i).getLayoutParams().width = 60; //
	 * mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 60; }
	 * mainHeight = getHeight() - getTabHostHeight() - getStatusBarHeight(); //
	 * myPopupWindow=new // MainPopupWindow(this,getTabHostHeight(),mainHeight);
	 * 
	 * Log.e("dasdasdas", "dasda"); }
	 * 
	 * private View getTabItemView(int index) { View view =
	 * layoutInflater.inflate(R.layout.tab_item_view, null);
	 * 
	 * ImageView imageView = (ImageView) view
	 * .findViewById(R.id.tab_item_imageview);
	 * imageView.setImageResource(mImageViewArray[index]);
	 * 
	 * TextView textView = (TextView) view
	 * .findViewById(R.id.tab_item_textview);
	 * textView.setText(mTextviewArray[index]);
	 * 
	 * return view; }
	 * 
	 * public int getTabHostHeight() { mTabHost.measure(0, 0); int height =
	 * mTabHost.getMeasuredHeight(); return height; }
	 * 
	 * // ��ȡ�ֻ�״̬���߶� public int getStatusBarHeight() { Class<?> c = null;
	 * Object obj = null; Field field = null; int x = 0, statusBarHeight = 50;
	 * try { c = Class.forName("com.android.internal.R$dimen"); obj =
	 * c.newInstance(); field = c.getField("status_bar_height"); x =
	 * Integer.parseInt(field.get(obj).toString()); statusBarHeight =
	 * mActivity.getResources() .getDimensionPixelSize(x); } catch (Exception
	 * e1) { e1.printStackTrace(); } return statusBarHeight; }
	 * 
	 * public int getWidth() { DisplayMetrics dm = new DisplayMetrics();
	 * mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm); int
	 * width = dm.widthPixels; return width; }
	 * 
	 * public int getHeight() { DisplayMetrics dm = new DisplayMetrics();
	 * mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm); int
	 * width = dm.heightPixels; return width; }
	 */

}
