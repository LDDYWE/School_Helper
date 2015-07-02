package com.zb.secondary_market.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zb.secondary_market.R;
import com.zb.secondary_market.Secondary_Me_Activity;
import com.zb.secondary_market.AsynImage.AsynImageLoader_Message;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.TitleView;

public class Secondary_Me_Fragment extends Fragment implements OnClickListener {
	private static final String TAG = "Secondary_Me_Fragment";

	private int tabheight = 0;

	private TextView nickname, vid;
	private TitleView mTitle;
	private RelativeLayout me_help_setting;

	private FragmentActivity mActivity;
	private View mParent;

	private RelativeLayout my_release, my_collect, my_message, user_complain,
			my_info, my_setting;

	AsynImageLoader_Message asynImageLoader = new AsynImageLoader_Message();
	
	private String requestString, nicknameString;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_me_manage_ll_ll1:
			Intent intent = new Intent();
			intent.setClass(mActivity, Secondary_Me_Activity.class);
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.i(TAG, "---onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		WindowManager vmManager = getActivity().getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 12;

		Log.i(TAG, "---onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "---onCreateView");
		return inflater.inflate(R.layout.fragment_me, null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "---onActivityCreated");

		mActivity = getActivity();
		mParent = getView();

		init_RL();
		
		nickname = (TextView) mParent
				.findViewById(R.id.id_me_info_ll_ll1_nickname);
		vid = (TextView) mParent.findViewById(R.id.id_me_info_ll_ll1_id);

		// 获取对象 
		SharedPreferences share = mActivity.getSharedPreferences("jonny", Activity.MODE_WORLD_READABLE);
//		isregister_test = share.getBoolean("isregister", false);
		requestString = share.getString("request", null);
		nicknameString = share.getString("nickname", null);
		
//		Log.v(TAG, isregister + "");
//		Log.v(TAG, isregister_test + "");

		String request_vid = requestString.split("@@")[0];
		String request_imageurl = requestString.split("@@")[1];

		Log.v(TAG, request_imageurl);
		
		Toast.makeText(mActivity,
				request_vid + "*" + request_imageurl, Toast.LENGTH_SHORT)
				.show();

		//此处设置nickname和user_id(vid)
		nickname.setText(nicknameString);
		vid.setText(request_vid);
		
		// 此处设置我的里面的头像
		CircularImageView cover_user_photo = (CircularImageView) mParent
				.findViewById(R.id.id_me_info_ll_photo);
		asynImageLoader.showImageAsyn(cover_user_photo,
				request_imageurl, R.drawable.ic_launcher);
		// cover_user_photo.setImageResource(R.drawable.sssss);

		mTitle = (TitleView) mParent.findViewById(R.id.id_me_title);
		me_help_setting = (RelativeLayout) mParent
				.findViewById(R.id.id_me_help_ll_ll3);

		me_help_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mActivity, "点击了我的设置", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
			}
		});

		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
		mTitle.setTitle("个人中心");

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(TAG, "---onStart");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "---onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "---onStop");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "---onDestroy");
	}

	// RelativeLayout的初始化
	private void init_RL() {
		// TODO Auto-generated method stub
		my_release = (RelativeLayout) mParent
				.findViewById(R.id.id_me_manage_ll_ll1);
		my_collect = (RelativeLayout) mParent
				.findViewById(R.id.id_me_manage_ll_ll2);
		my_message = (RelativeLayout) mParent
				.findViewById(R.id.id_me_manage_ll_ll3);
		user_complain = (RelativeLayout) mParent
				.findViewById(R.id.id_me_help_ll_ll1);
		my_info = (RelativeLayout) mParent.findViewById(R.id.id_me_help_ll_ll2);
		my_setting = (RelativeLayout) mParent
				.findViewById(R.id.id_me_help_ll_ll3);
		
		my_release.setOnClickListener(this);
		my_collect.setOnClickListener(this);
		my_message.setOnClickListener(this);
		user_complain.setOnClickListener(this);
		my_info.setOnClickListener(this);
		my_setting.setOnClickListener(this);
	}

}
