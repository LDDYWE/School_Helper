package com.zb.secondary_market;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zb.secondary_market.fragment.Assortment_Fragment;
import com.zb.secondary_market.fragment.Content_Fragment;
import com.zb.secondary_market.fragment.Friend_Fragment;
import com.zb.secondary_market.fragment.Main_Fragment;
import com.zb.secondary_market.fragment.Secondary_Mainpage_Fragment;

public class MainActivity1 extends FragmentActivity implements OnClickListener {

	private Secondary_Mainpage_Fragment fragmentPage1;
	private Assortment_Fragment fragmentPage2;
	private Content_Fragment fragmentPage3;
	private Content_Fragment fragmentPage4;

//	private SlidingMenu slidingMenu;
	private Main_Fragment mainFragment = new Main_Fragment();
	private Friend_Fragment friendFragment = new Friend_Fragment();

	private RelativeLayout secondaryLayout, friendLayout, worklLayout,
			notificationLayout, settingLayout;

	// 定义布局对象
	private FrameLayout friendfeedFl, myfeedFl, homeFl, moreFl;

	// 定义图片组件对象
	private ImageView friendfeedIv, myfeedIv, homeIv, moreIv;

	// 定义按钮图片组件
	private ImageView toggleImageView, plusImageView;

	private boolean isCurrent_Secondary, isCurrent_Friend, isCurrent_Work,
			isCurrent_Notification, isCurrent_Setting;

	private Button btnButton;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

		initView();
		initData();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.id_mainframelayout, mainFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();

		isCurrent_Secondary = true;
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
//		slidingMenu = (SlidingMenu) findViewById(R.id.id_slidemenu);
		secondaryLayout = (RelativeLayout) findViewById(R.id.id_secondarylayout);
		friendLayout = (RelativeLayout) findViewById(R.id.id_friendlayout);
		worklLayout = (RelativeLayout) findViewById(R.id.id_worklayout);
		notificationLayout = (RelativeLayout) findViewById(R.id.id_schoollayout);
//		settingLayout = (RelativeLayout) findViewById(R.id.id_settinglayout);
		
		btnButton = (Button)findViewById(R.id.id_switch_btn);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		secondaryLayout.setOnClickListener(this);
		friendLayout.setOnClickListener(this);
		worklLayout.setOnClickListener(this);
		notificationLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
//		btnButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.id_switch_btn:
//			slidingMenu.toggle();
//			break;
			
		case R.id.id_secondarylayout:
			Toast.makeText(MainActivity1.this, "Secondary", Toast.LENGTH_SHORT)
					.show();

			if (isCurrent_Secondary == true) {
//				slidingMenu.toggle();
			} else if (isCurrent_Secondary == false) {
				FragmentTransaction ft_main = getSupportFragmentManager()
						.beginTransaction();
				ft_main.replace(R.id.id_mainframelayout, mainFragment);
				ft_main.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft_main.commit();

//				slidingMenu.toggle();
			}

			isCurrent_Secondary = true;
			isCurrent_Friend = isCurrent_Notification = isCurrent_Setting = isCurrent_Work = false;

			break;
		case R.id.id_friendlayout:
			Toast.makeText(MainActivity1.this, "Friend", Toast.LENGTH_SHORT)
					.show();

			if (isCurrent_Friend == true) {
//				slidingMenu.toggle();
			} else if (isCurrent_Friend == false) {
				FragmentTransaction ft_friend = getSupportFragmentManager()
						.beginTransaction();
				ft_friend.replace(R.id.id_mainframelayout, friendFragment);
				ft_friend
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft_friend.commit();

//				slidingMenu.toggle();
			}

			isCurrent_Friend = true;
			isCurrent_Secondary = isCurrent_Notification = isCurrent_Setting = isCurrent_Work = false;

			break;
		case R.id.id_worklayout:
			Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

			if (isCurrent_Work == true) {
//				slidingMenu.toggle();
			} else if (isCurrent_Work == false) {
				FragmentTransaction ft_friend = getSupportFragmentManager()
						.beginTransaction();
				ft_friend.replace(R.id.id_mainframelayout, friendFragment);
				ft_friend
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft_friend.commit();

//				slidingMenu.toggle();
			}

			isCurrent_Work = true;
			isCurrent_Secondary = isCurrent_Notification = isCurrent_Setting = isCurrent_Friend = false;
			break;
		case R.id.id_schoollayout:
			Toast.makeText(this, "notification", Toast.LENGTH_SHORT).show();

			if (isCurrent_Notification == true) {
//				slidingMenu.toggle();
			} else if (isCurrent_Notification == false) {
				FragmentTransaction ft_friend = getSupportFragmentManager()
						.beginTransaction();
				ft_friend.replace(R.id.id_mainframelayout, friendFragment);
				ft_friend
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft_friend.commit();

//				slidingMenu.toggle();
			}

			isCurrent_Notification = true;

			isCurrent_Secondary = isCurrent_Friend = isCurrent_Setting = isCurrent_Work = false;

			break;
		/*case R.id.id_settinglayout:
			Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();

			if (isCurrent_Setting == true) {
//				slidingMenu.toggle();
			} else if (isCurrent_Setting == false) {
				FragmentTransaction ft_friend = getSupportFragmentManager()
						.beginTransaction();
				ft_friend.replace(R.id.id_mainframelayout, friendFragment);
				ft_friend
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft_friend.commit();

//				slidingMenu.toggle();
			}

			isCurrent_Setting = true;
			isCurrent_Secondary = isCurrent_Notification = isCurrent_Friend = isCurrent_Work = false;

			break;*/
		}
	}
	/*
	 * private static final String TAG = "MainActivity";
	 * 
	 * private LayoutInflater layoutInflater; private FragmentTabHost mTabHost;
	 * private Class<?> fragmentArray[] = { Mainpage_Fragment.class,
	 * Assortment_Fragment.class, Photograph_Fragment.class,
	 * Content_Fragment.class, Content_Fragment.class }; private int
	 * mImageViewArray[] = { R.drawable.tab_mainpage_image,
	 * R.drawable.tab_assortment_image, R.drawable.tab_release_image,
	 * R.drawable.tab_discover_image, R.drawable.tab_me_image }; private String
	 * mTextviewArray[] = { "首页", "分类", "发布", "发现", "我" }; private int
	 * mainHeight; private SlidingMenu slidingMenu; private Main_Fragment
	 * mainFragment = new Main_Fragment(); private Friend_Fragment
	 * friendFragment = new Friend_Fragment(); private Null_Fragment
	 * nullFragment = new Null_Fragment();
	 * 
	 * private RelativeLayout secondaryLayout, friendLayout, worklLayout,
	 * notificationLayout, settingLayout;
	 * 
	 * // private UpdateManager mUpdateManager;
	 * 
	 * @Override protected void onCreate(Bundle arg0) { // TODO Auto-generated
	 * method stub super.onCreate(arg0);
	 * 
	 * requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * setContentView(R.layout.main);
	 * setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	 * 
	 * slidingMenu = (SlidingMenu) findViewById(R.id.id_slidemenu);
	 * secondaryLayout = (RelativeLayout) findViewById(R.id.id_secondarylayout);
	 * friendLayout = (RelativeLayout) findViewById(R.id.id_friendlayout);
	 * 
	 * FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	 * ft.add(R.id.id_mainframelayout, mainFragment);
	 * ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); ft.commit();
	 * 
	 * Log.v(TAG, "onCreate");
	 * 
	 * secondaryLayout.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub Toast.makeText(MainActivity1.this, "Secondary",
	 * Toast.LENGTH_SHORT).show(); slidingMenu.toggle();
	 * 
	 * // FragmentTransaction ft =
	 * getSupportFragmentManager().beginTransaction(); //
	 * ft.replace(R.id.id_mainframelayout, mainFragment); //
	 * ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //
	 * ft.commit();
	 * 
	 * // FragmentTransaction ft =
	 * getSupportFragmentManager().beginTransaction(); //
	 * ft.replace(R.id.id_mainframelayout, mainFragment); //
	 * ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //
	 * ft.commit();
	 * 
	 * // MainFragment mainFragment1 = new MainFragment(); // //
	 * FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	 * // ft.replace(R.id.id_mainframelayout, mainFragment1); //
	 * ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //
	 * ft.commit();
	 * 
	 * } });
	 * 
	 * friendLayout.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub Toast.makeText(MainActivity1.this, "Friend", Toast.LENGTH_SHORT)
	 * .show(); slidingMenu.toggle();
	 * 
	 * FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	 * ft.replace(R.id.id_mainframelayout, friendFragment);
	 * ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); ft.commit();
	 * } });
	 * 
	 * // initView(); }
	 * 
	 * public void initView() { layoutInflater = LayoutInflater.from(this);
	 * mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
	 * mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
	 * int count = fragmentArray.length; for (int i = 0; i < count; i++) {
	 * TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
	 * .setIndicator(getTabItemView(i)); Bundle bundle = new Bundle();
	 * bundle.putInt("tabheight", getTabHostHeight());
	 * bundle.putInt("statusBarHeight", getStatusBarHeight());
	 * bundle.putInt("height", getHeight()); mTabHost.addTab(tabSpec,
	 * fragmentArray[i], bundle);
	 * 
	 * // mTabHost.getTabWidget().getChildAt(i).getLayoutParams().width = // 60;
	 * // mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = //
	 * 60; } mainHeight = getHeight() - getTabHostHeight() -
	 * getStatusBarHeight(); // myPopupWindow=new //
	 * MainPopupWindow(this,getTabHostHeight(),mainHeight);
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
	 * MainActivity1.this.getResources() .getDimensionPixelSize(x); } catch
	 * (Exception e1) { e1.printStackTrace(); } return statusBarHeight; }
	 * 
	 * public int getWidth() { DisplayMetrics dm = new DisplayMetrics();
	 * getWindowManager().getDefaultDisplay().getMetrics(dm); int width =
	 * dm.widthPixels; return width; }
	 * 
	 * public int getHeight() { DisplayMetrics dm = new DisplayMetrics();
	 * getWindowManager().getDefaultDisplay().getMetrics(dm); int width =
	 * dm.heightPixels; return width; }
	 */
}
