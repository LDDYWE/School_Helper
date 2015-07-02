package com.zb.secondary_market.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.zb.secondary_market.R;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;

public class Photograph_Fragment extends Fragment{
	private static final String TAG = "Photograph_Fragment";
	private FragmentActivity mActivity;
	private View mParent;
	
	private TitleView mTitle;
	
	private int titleHeight;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.v(TAG, "---onAttach");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		WindowManager vmManager = getActivity().getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();
		
		titleHeight = (int)height * 1 / 12;
		
		Log.v(TAG, "---onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "---onCreateView");
		return inflater.inflate(R.layout.fragment_photograph, null);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		mActivity = getActivity();
		mParent = getView();
		
		mTitle = (TitleView)mParent.findViewById(R.id.photograph_title);
		mTitle.setLayoutParams(WindowManager.LayoutParams.MATCH_PARENT, titleHeight);
		mTitle.setTitle("发布商品");
		mTitle.setImageLeftButton(R.drawable.arrow_left_white_thin, new OnImageLeftButtonClickListener() {
			
			@Override
			public void onClick(View Imagebutton) {
				// TODO Auto-generated method stub
			}
		});
		
		Log.v(TAG, "---onActivityCreated");
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v(TAG, "---onStart");
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "---onResume");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(TAG, "---onPause");
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
}
