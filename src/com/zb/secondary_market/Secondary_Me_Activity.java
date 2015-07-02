package com.zb.secondary_market;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;

import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.fragment.Secondary_Me_Released_Fragment;

public class Secondary_Me_Activity extends FragmentActivity {
	private static final String TAG = "Secondary_Me_Activity";

	private TitleView mTitle;
//	private RadioGroup secondary_me_rg;
//	private RadioButton secondary_me_released, secondary_me_message;
	// private RadioButton secondary_me_finished

	private Secondary_Me_Released_Fragment secondary_Me_Released_Fragment;
	// private Secondary_Me_Message_Fragment secondary_Me_Message_Fragment;

	private int tabheight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_secondary_me);

		iniView();

		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
		mTitle.setTitle("我的二手");
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

		mTitle = (TitleView) findViewById(R.id.id_secondary_me_title);

		/*secondary_me_rg = (RadioGroup) findViewById(R.id.id_secondary_me_rg);
		secondary_me_released = (RadioButton) findViewById(R.id.id_secondary_me_rg_btn1);
		// secondary_me_finished = (RadioButton)
		// findViewById(R.id.id_secondary_me_rg_btn2);
		secondary_me_message = (RadioButton) findViewById(R.id.id_secondary_me_rg_btn2);

		// 设置RadioButton前面的按钮消失
		secondary_me_released.setButtonDrawable(android.R.color.transparent);
		// secondary_me_finished.setButtonDrawable(android.R.color.transparent);
		secondary_me_message.setButtonDrawable(android.R.color.transparent);

		secondary_me_rg
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.id_secondary_me_rg_btn1:
							Replace_fragment(0);
							break;

						case R.id.id_secondary_me_rg_btn2:
							Replace_fragment(1);
							break;
						}
					}
				});*/
	}

	public void Replace_fragment(int num) {
		switch (num) {
		case 0:
			// 实例化Fragment页面
			secondary_Me_Released_Fragment = new Secondary_Me_Released_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_secondary_me_released = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_secondary_me_released.replace(
					R.id.id_secondary_me_fl, secondary_Me_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_secondary_me_released.commit();

			break;

		case 1:

			// 实例化Fragment页面
			secondary_Me_Released_Fragment = new Secondary_Me_Released_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_secondary_me_message = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_secondary_me_message.replace(
					R.id.id_secondary_me_fl, secondary_Me_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_secondary_me_message.commit();

			break;

		default:
			break;
		}
	}
}
