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

import com.zb.secondary_market.fragment.School_Activity_Fragment;
import com.zb.secondary_market.fragment.School_Conference_Fragment;
import com.zb.secondary_market.fragment.School_Notice_Fragment;

public class School_Activity extends FragmentActivity implements
		OnClickListener {
	private static final String TAG = "School_Activity";

	private School_Notice_Fragment noticeFragment;
	private School_Conference_Fragment conferenceFragment;
	private School_Activity_Fragment activityFragment;

	private FrameLayout noticeF1, activityF1, conferenceF1;
	private ImageView noticeIv, activityIv, conferenceIv;

	private boolean isNotice = true;
	private boolean isConference, isActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_school);

		initView();

		initData();

		Replace_fragment(0);

		Log.v(TAG, "youmeiyou");

	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		noticeF1 = (FrameLayout) findViewById(R.id.layout_notice_temperary_school);
		conferenceF1 = (FrameLayout) findViewById(R.id.layout_conference_temperary_school);
		activityF1 = (FrameLayout) findViewById(R.id.layout_activity_temperary_school);
		// meF1 = (FrameLayout) findViewById(R.id.layout_me_temperary_work);

		// 实例化图片组件对象
		noticeIv = (ImageView) findViewById(R.id.image_notice_temperary_school);
		conferenceIv = (ImageView) findViewById(R.id.image_conference_temperary_school);
		activityIv = (ImageView) findViewById(R.id.image_activity_temperary_school);
		// meIv = (ImageView) findViewById(R.id.image_me_temperary_work);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 给布局对象设置监听
		noticeF1.setOnClickListener(this);
		conferenceF1.setOnClickListener(this);
		activityF1.setOnClickListener(this);
		// meF1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击招聘按钮
		case R.id.layout_notice_temperary_school:
			clickNoticeBtn();
			break;
		case R.id.layout_conference_temperary_school:
			clickConferenceBtn();
			break;
		// 点击兼职按钮
		case R.id.layout_activity_temperary_school:
			clickActivityBtn();
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
			noticeFragment = new School_Notice_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_recruit = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_recruit.replace(
					R.id.frame_content_temperary_school, noticeFragment);
			// 事务管理提交
			fragmentTransaction_recruit.commit();

			noticeF1.setSelected(true);
			noticeIv.setSelected(true);

			conferenceF1.setSelected(false);
			conferenceIv.setSelected(false);

			activityF1.setSelected(false);
			activityIv.setSelected(false);

			// meF1.setSelected(false);
			// meIv.setSelected(false);

			break;

		case 1:

			Log.v(TAG, "点击了会议按钮");
			// 实例化Fragment页面
			conferenceFragment = new School_Conference_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_intern = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_intern.replace(
					R.id.frame_content_temperary_school, conferenceFragment);
			// 事务管理提交
			fragmentTransaction_intern.commit();

			conferenceF1.setSelected(true);
			conferenceIv.setSelected(true);

			noticeF1.setSelected(false);
			noticeIv.setSelected(false);

			activityF1.setSelected(false);
			activityIv.setSelected(false);

			// meF1.setSelected(false);
			// meIv.setSelected(false);
			break;

		case 2:
			// 实例化Fragment页面
			activityFragment = new School_Activity_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_parttime = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_parttime.replace(
					R.id.frame_content_temperary_school, activityFragment);
			// 事务管理提交
			fragmentTransaction_parttime.commit();

			activityF1.setSelected(true);
			activityIv.setSelected(true);

			noticeF1.setSelected(false);
			noticeIv.setSelected(false);

			conferenceF1.setSelected(false);
			conferenceIv.setSelected(false);

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

	/**
	 * 点击了“通知”按钮
	 */
	private void clickNoticeBtn() {

		if (isNotice == true) {
		} else {
			Replace_fragment(0);
		}

		isNotice = true;
		isConference = false;
		isActivity = false;
	}

	/**
	 * 点击了“会议”按钮
	 */
	private void clickConferenceBtn() {
		if (isConference == true) {

		} else {
			Replace_fragment(1);
		}

		isConference = true;
		isNotice = false;
		isActivity = false;
	}

	/**
	 * 点击了“活动”按钮
	 */
	private void clickActivityBtn() {
		if (isActivity == true) {

		} else {
			Replace_fragment(2);
		}

		isActivity = true;
		isNotice = false;
		isConference = false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			/*// 存储对象
			SharedPreferences sharedPreferences = School_Activity.this
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
