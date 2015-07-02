package com.zb.secondary_market;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.fragment.Campaigner_Me_Released_Fragment;

public class Campaigner_Me_Activity extends FragmentActivity {
	private static final String TAG = "Campaigner_Me_Activity";

	private TitleView mTitle;
//	private RadioGroup campaigner_me_rg;
//	private RadioButton campaigner_me_released, campaigner_me_message;
	// private RadioButton secondary_me_finished

	private Campaigner_Me_Released_Fragment campaigner_Me_Released_Fragment;
	// private Secondary_Me_Message_Fragment secondary_Me_Message_Fragment;

	private int tabheight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_activity_me);

		iniView();

		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
		mTitle.setTitle("我的发布");
		mTitle.setImageLeftButton(R.drawable.arrow_left,
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
	 * 初始化组件
	 */
	private void iniView() {
		// TODO Auto-generated method stub
		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 12;

		mTitle = (TitleView) findViewById(R.id.id_activity_me_title);

		/*campaigner_me_rg = (RadioGroup) findViewById(R.id.id_activity_me_rg);
		campaigner_me_released = (RadioButton) findViewById(R.id.id_activity_me_rg_btn1);
		// secondary_me_finished = (RadioButton)
		// findViewById(R.id.id_secondary_me_rg_btn2);
		campaigner_me_message = (RadioButton) findViewById(R.id.id_activity_me_rg_btn2);*/

		// 设置RadioButton前面的按钮消失
//		campaigner_me_released.setButtonDrawable(android.R.color.transparent);
//		// secondary_me_finished.setButtonDrawable(android.R.color.transparent);
//		campaigner_me_message.setButtonDrawable(android.R.color.transparent);
//
//		campaigner_me_rg
//				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(RadioGroup group, int checkedId) {
//						// TODO Auto-generated method stub
//						switch (checkedId) {
//						case R.id.id_activity_me_rg_btn1:
//							Replace_fragment(0);
//							break;
//
//						case R.id.id_activity_me_rg_btn2:
//							Replace_fragment(1);
//							break;
//						}
//					}
//				});
	}

	public void Replace_fragment(int num) {
		switch (num) {
		case 0:
			// 实例化Fragment页面
			campaigner_Me_Released_Fragment = new Campaigner_Me_Released_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_campaigner_me_released = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_campaigner_me_released.replace(
					R.id.id_activity_me_fl, campaigner_Me_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_campaigner_me_released.commit();

			break;

		case 1:

			// 实例化Fragment页面
			campaigner_Me_Released_Fragment = new Campaigner_Me_Released_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_activity_me_message = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_activity_me_message.replace(
					R.id.id_activity_me_fl, campaigner_Me_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_activity_me_message.commit();

			break;

		default:
			break;
		}
	}
}
