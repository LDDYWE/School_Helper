package com.zb.secondary_market;

import android.content.Intent;
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
import com.zb.secondary_market.fragment.Campaigner_My_Message_Fragment;
import com.zb.secondary_market.fragment.Friend_My_Message_Fragment;
import com.zb.secondary_market.fragment.Secondary_Me_Message_Fragment;

public class My_Message_Activity extends FragmentActivity{
	private static final String TAG = "My_Message_Activity";
	private TitleView mymessageTitleView;
	
	private Secondary_Me_Message_Fragment secondary_My_Message_Fragment;
	private Friend_My_Message_Fragment friend_My_Message_Fragment;
	private Campaigner_My_Message_Fragment campaigner_My_Message_Fragment;
	private RadioGroup mymessageGroup;
	private RadioButton mysecondaryButton, myfriendButton, mycampaignerButton;
	
	private int tabheight = 0;
	private String intent_typeString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_my_message);
		
		Intent intent = getIntent();
		intent_typeString = intent.getStringExtra("intent_type");
		
		iniView();
		
		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 12;
		
		mymessageTitleView = (TitleView)findViewById(R.id.id_my_message_title);
		
		mymessageTitleView.setImageLeftButton(R.drawable.arrow_left_white_thin, new OnImageLeftButtonClickListener() {
			
			@Override
			public void onClick(View Imagebutton) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent();
				intent.setClass(My_Message_Activity.this, Personal_Setting_Activity.class);
				startActivity(intent);*/
				finish();
			}
		});
		
		mymessageTitleView.setTitle("我的消息");
		mymessageTitleView.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
	
		if(intent_typeString.equals("friend")){
			myfriendButton.setChecked(true);
			Replace_fragment(1);
		}else if(intent_typeString.equals("secondary")){
			mysecondaryButton.setChecked(true);
			Replace_fragment(0);
		}else if(intent_typeString.equals("campaigner")){
			mycampaignerButton.setChecked(true);
			Replace_fragment(2);
		}else if(intent_typeString.equals("normal")){
			mysecondaryButton.setChecked(true);
			Replace_fragment(0);
		}
	}	
	private void iniView() {
		// TODO Auto-generated method stub
		mymessageGroup = (RadioGroup) findViewById(R.id.id_my_message_rg);
		mysecondaryButton = (RadioButton) findViewById(R.id.id_my_message_rg_btn1);
		// secondary_me_finished = (RadioButton)
		// findViewById(R.id.id_secondary_me_rg_btn2);
		myfriendButton = (RadioButton) findViewById(R.id.id_my_message_rg_btn2);
		mycampaignerButton = (RadioButton)findViewById(R.id.id_my_message_rg_btn3);

		/*// 设置RadioButton前面的按钮消失
		mysecondaryButton.setButtonDrawable(android.R.color.transparent);
		// secondary_me_finished.setButtonDrawable(android.R.color.transparent);
		myfriendButton.setButtonDrawable(android.R.color.transparent);
		mycampaignerButton.setButtonDrawable(android.R.color.transparent);*/

		mymessageGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.id_my_message_rg_btn1:
							Replace_fragment(0);
							break;

						case R.id.id_my_message_rg_btn2:
							Replace_fragment(1);
							break;
							
						case R.id.id_my_message_rg_btn3:
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
			secondary_My_Message_Fragment = new Secondary_Me_Message_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_my_message_secondary = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_my_message_secondary.replace(
					R.id.id_my_message_fl, secondary_My_Message_Fragment);
			// 事务管理提交
			fragmentTransaction_my_message_secondary.commit();

			break;

		case 1:

			// 实例化Fragment页面
			friend_My_Message_Fragment = new Friend_My_Message_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_my_message_friend = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_my_message_friend.replace(
					R.id.id_my_message_fl, friend_My_Message_Fragment);
			// 事务管理提交
			fragmentTransaction_my_message_friend.commit();

			break;
			
		case 2:

			// 实例化Fragment页面
			campaigner_My_Message_Fragment = new Campaigner_My_Message_Fragment();
			// 得到Fragment事务管理器
			FragmentTransaction fragmentTransaction_my_message_campaigner = this
					.getSupportFragmentManager().beginTransaction();
			// 替换当前的页面
			fragmentTransaction_my_message_campaigner.replace(
					R.id.id_my_message_fl, campaigner_My_Message_Fragment);
			// 事务管理提交
			fragmentTransaction_my_message_campaigner.commit();

			break;

		default:
			break;
		}
	}
}
