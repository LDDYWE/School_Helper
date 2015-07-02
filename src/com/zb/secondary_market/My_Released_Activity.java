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
import com.zb.secondary_market.fragment.Friend_Me_Released_Fragment;
import com.zb.secondary_market.fragment.Secondary_Me_Released_Fragment;

public class My_Released_Activity extends FragmentActivity{
	private static final String TAG = "My_Released_Activity";
	private TitleView myreleasedTitleView;
	
	private Secondary_Me_Released_Fragment secondary_My_Released_Fragment;
	private Friend_Me_Released_Fragment friend_My_Released_Fragment;
	private Campaigner_Me_Released_Fragment campaigner_My_Released_Fragment;
	private RadioGroup myreleasedGroup;
	private RadioButton mysecondaryButton, myfriendButton, mycampaignerButton;
	
	private int tabheight = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_my_released);
		
		iniView();
		
		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 12;
		
		myreleasedTitleView = (TitleView)findViewById(R.id.id_my_released_title);
		
		myreleasedTitleView.setImageLeftButton(R.drawable.arrow_left_white_thin, new OnImageLeftButtonClickListener() {
			
			@Override
			public void onClick(View Imagebutton) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent();
				intent.setClass(My_Released_Activity.this, Personal_Setting_Activity.class);
				startActivity(intent);*/
				finish();
			}
		});
		
		myreleasedTitleView.setTitle("我的发布");
		myreleasedTitleView.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
	
		Replace_fragment(0);
	}
	
	private void iniView() {
		// TODO Auto-generated method stub
		myreleasedGroup = (RadioGroup) findViewById(R.id.id_my_released_rg);
		mysecondaryButton = (RadioButton) findViewById(R.id.id_my_released_rg_btn1);
		// secondary_me_finished = (RadioButton)
		// findViewById(R.id.id_secondary_me_rg_btn2);
		myfriendButton = (RadioButton) findViewById(R.id.id_my_released_rg_btn2);
		mycampaignerButton = (RadioButton)findViewById(R.id.id_my_released_rg_btn3);

		/*// 设置RadioButton前面的按钮消失
		mysecondaryButton.setButtonDrawable(android.R.color.transparent);
		// secondary_me_finished.setButtonDrawable(android.R.color.transparent);
		myfriendButton.setButtonDrawable(android.R.color.transparent);
		mycampaignerButton.setButtonDrawable(android.R.color.transparent);*/

		myreleasedGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.id_my_released_rg_btn1:
							Replace_fragment(0);
							break;

						case R.id.id_my_released_rg_btn2:
							Replace_fragment(1);
							break;
							
						case R.id.id_my_released_rg_btn3:
							Replace_fragment(2);
							break;
						}
					}
				});
	}
	
	public void Replace_fragment(int num) {
		switch (num) {
		case 0:
			// 实例化Fragment页面
			secondary_My_Released_Fragment = new Secondary_Me_Released_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_my_released_secondary = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_my_released_secondary.replace(
					R.id.id_my_released_fl, secondary_My_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_my_released_secondary.commit();

			break;

		case 1:

			// 实例化Fragment页面
			friend_My_Released_Fragment = new Friend_Me_Released_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_my_released_friend = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_my_released_friend.replace(
					R.id.id_my_released_fl, friend_My_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_my_released_friend.commit();

			break;
			
		case 2:

			// 实例化Fragment页面
			campaigner_My_Released_Fragment = new Campaigner_Me_Released_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_my_released_campaigner = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_my_released_campaigner.replace(
					R.id.id_my_released_fl, campaigner_My_Released_Fragment);
			// 事务管理提交
			fragmentTransaction_my_released_campaigner.commit();

			break;

		default:
			break;
		}
	}
}
