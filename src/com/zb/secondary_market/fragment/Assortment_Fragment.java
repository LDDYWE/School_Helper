package com.zb.secondary_market.fragment;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;

import com.zb.secondary_market.R;
import com.zb.secondary_market.Type_Detail_Activity;
import com.zb.secondary_market.adapter.AssortmentGridViewAdapter;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageRightButtonClickListener;

public class Assortment_Fragment extends Fragment{
	private static final String TAG = "AssortmentFragment";
	private FragmentActivity mActivity;
	private View mParent;
	private TitleView mTitle;
	private int tabheight = 0;
	private int tabheight1 = 0;
	private int height1, mainHeight, statusBarHeight;
	private GridView assortmentGridView;
	private AssortmentGridViewAdapter assortmentGridViewAdapter;
	private int content_item_height = 0;
	private int[] types_icons = { R.drawable.vod_type_love,
			R.drawable.vod_type_movie, R.drawable.vod_type_tv,
			R.drawable.vod_type_cartoon, R.drawable.vod_type_variety,
			R.drawable.vod_type_jilu, R.drawable.vod_type_love,
			R.drawable.vod_type_movie, R.drawable.vod_type_tv,
			R.drawable.vod_type_cartoon, R.drawable.vod_type_variety,
			R.drawable.vod_type_jilu, R.drawable.vod_type_love,
			R.drawable.vod_type_movie, R.drawable.vod_type_tv};
	
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
		int width = vmManager.getDefaultDisplay()	.getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();
		
		tabheight = (int)height * 1 / 12;
		content_item_height = (int)height * 2 / 5;
		
//		tabheight1 = getArguments().getInt("tabheight1");
//        statusBarHeight = getArguments().getInt("statusBarHeight",50);
//        height1 = getArguments().getInt("height",1280);
//        mainHeight = height1 - tabheight - statusBarHeight;
		
		Log.i(TAG, "---onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "---onCreateView");  
		return inflater.inflate(R.layout.fragment_assortment, null);	
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "---onActivityCreated");
		mActivity = getActivity();
		mParent = getView();
		
		mTitle = (TitleView) mParent.findViewById(R.id.assortment_title);
		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
		mTitle.setTitle("科大二手");
		mTitle.setImageRightButton(R.drawable.search, new OnImageRightButtonClickListener() {
			
			@Override
			public void onClick(View Imagebutton) {
				// TODO Auto-generated method stub
				
			}
		});
		
		assortmentGridView = (GridView)mParent.findViewById(R.id.assortment_gridview);
		assortmentGridViewAdapter = new AssortmentGridViewAdapter(getActivity(), types_icons.length, height1, tabheight1, statusBarHeight);
		assortmentGridView.setAdapter(assortmentGridViewAdapter);
		assortmentGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
//				in.putExtra("position", position);
//				in.putExtra("title_width", tabheight);
//				in.putExtra("mainHeight", mainHeight);
				in.setClass(mActivity, Type_Detail_Activity.class);
				startActivity(in);
				Log.e("dianjie", "jieda");
			}
		});
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
