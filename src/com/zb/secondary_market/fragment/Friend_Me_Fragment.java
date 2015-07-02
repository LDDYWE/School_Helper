package com.zb.secondary_market.fragment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.zb.secondary_market.R;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.TitleView;

public class Friend_Me_Fragment extends Fragment {
	private static final String TAG = "Friend_Me_Fragment";

	private int tabheight = 0;

	private TitleView mTitle;
	private RelativeLayout me_help_setting;

	private FragmentActivity mActivity;
	private View mParent;

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

		CircularImageView cover_user_photo = (CircularImageView) mParent
				.findViewById(R.id.id_me_info_ll_photo);
		cover_user_photo.setImageResource(R.drawable.sssss);

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

}
