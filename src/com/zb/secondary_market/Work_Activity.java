package com.zb.secondary_market;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zb.secondary_market.fragment.Work_Internal_Fragment;
import com.zb.secondary_market.fragment.Work_Parttime_Fragment;
import com.zb.secondary_market.fragment.Work_Recruit_Fragment;

public class Work_Activity extends FragmentActivity implements OnClickListener {
	private static final String TAG = "Work_Activity";
	
	private Work_Recruit_Fragment recruitFragment;
	private Work_Parttime_Fragment parttimeFragment;
	private Work_Internal_Fragment internalFragment;
	
	private FrameLayout recruitF1, internF1, part_timeF1;
	private ImageView recruitIv, internIv, part_timeIv;

	private boolean isRecruit = true;
	private boolean isIntern, isPartime, isMe;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_work);

		initView();

		initData();

		Replace_fragment(0);

	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		recruitF1 = (FrameLayout) findViewById(R.id.layout_recruit_temperary_work);
		internF1 = (FrameLayout) findViewById(R.id.layout_intern_temperary_work);
		part_timeF1 = (FrameLayout) findViewById(R.id.layout_parttime_temperary_work);
//		meF1 = (FrameLayout) findViewById(R.id.layout_me_temperary_work);

		// 实例化图片组件对象
		recruitIv = (ImageView) findViewById(R.id.image_recruit_temperary_work);
		internIv = (ImageView) findViewById(R.id.image_intern_temperary_work);
		part_timeIv = (ImageView) findViewById(R.id.image_parttime_temperary_work);
//		meIv = (ImageView) findViewById(R.id.image_me_temperary_work);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 给布局对象设置监听
		recruitF1.setOnClickListener(this);
		internF1.setOnClickListener(this);
		part_timeF1.setOnClickListener(this);
//		meF1.setOnClickListener(this);
	}

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

	public void Replace_fragment(int num) {
		switch (num) {
		case 0:
			// 实例化Fragment页面
			recruitFragment = new Work_Recruit_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_recruit = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_recruit.replace(
					R.id.frame_content_temperary_work, recruitFragment);
			// 事务管理提交
			fragmentTransaction_recruit.commit();

			recruitF1.setSelected(true);
			recruitIv.setSelected(true);

			internF1.setSelected(false);
			internIv.setSelected(false);

			part_timeF1.setSelected(false);
			part_timeIv.setSelected(false);

//			meF1.setSelected(false);
//			meIv.setSelected(false);

			break;

		case 1:
			
			Log.v(TAG, "点击了实习按钮");
			// 实例化Fragment页面
			internalFragment = new Work_Internal_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_intern = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_intern.replace(
					R.id.frame_content_temperary_work, internalFragment);
			// 事务管理提交
			fragmentTransaction_intern.commit();

			internF1.setSelected(true);
			internIv.setSelected(true);

			recruitF1.setSelected(false);
			recruitIv.setSelected(false);

			part_timeF1.setSelected(false);
			part_timeIv.setSelected(false);

//			meF1.setSelected(false);
//			meIv.setSelected(false);
			break;

		case 2:
			// 实例化Fragment页面
			parttimeFragment = new Work_Parttime_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_parttime = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_parttime.replace(
					R.id.frame_content_temperary_work,parttimeFragment);
			// 事务管理提交
			fragmentTransaction_parttime.commit();

			part_timeF1.setSelected(true);
			part_timeIv.setSelected(true);

			recruitF1.setSelected(false);
			recruitIv.setSelected(false);

			internF1.setSelected(false);
			internIv.setSelected(false);

//			meF1.setSelected(false);
//			meIv.setSelected(false);

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

	/**
	 * 点击了“招聘”按钮
	 */
	private void clickRecruitBtn() {

		if (isRecruit == true) {
		} else {
			Replace_fragment(0);
		}

		isRecruit = true;
		isIntern = false;
		isPartime = false;
		isMe = false;
	}

	/**
	 * 点击了“实习”按钮
	 */
	private void clickInternBtn() {
		if (isIntern == true) {

		} else {
			Replace_fragment(1);
		}

		isIntern = true;
		isRecruit = false;
		isPartime = false;
		isMe = false;
	}

	/**
	 * 点击了“兼职”按钮
	 */
	private void clickParttimeBtn() {
		if (isPartime == true) {

		} else {
			Replace_fragment(2);
		}

		isPartime = true;
		isRecruit = false;
		isIntern = false;
		isMe = false;
	}

	/**
	 * 点击了“我的”按钮
	 */
	/*private void clickMeBtn() {
		if (isMe == true) {

		} else {
			Replace_fragment(3);
		}

		isMe = true;
		isRecruit = false;
		isIntern = false;
		isPartime = false;
	}*/
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			/*// 存储对象
			SharedPreferences sharedPreferences = Work_Activity.this
					.getSharedPreferences("jonny",
							Context.MODE_PRIVATE); // 私有数据
			Editor editor = sharedPreferences.edit();// 获取编辑器
			editor.putString("section_type", "other");
			editor.commit();// 提交修改
*/			
			finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
