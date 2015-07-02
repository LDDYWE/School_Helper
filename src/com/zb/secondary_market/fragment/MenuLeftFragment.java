package com.zb.secondary_market.fragment;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zb.secondary_market.R;
import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.custom.CircularImageView;

public class MenuLeftFragment extends Fragment implements OnClickListener {
	private static final String TAG = "MenuLeftFragment";
	private String requestString, nicknameString, passwordString;

	private String request_vid;
	private String request_imageurl;

	private View mView;
	private ListView mCategories;
	private List<String> mDatas = Arrays
			.asList("聊天", "发现", "通讯录", "朋友圈", "订阅号");
	private ListAdapter mAdapter;
	private FragmentActivity mActivity;

	private AsynImageLoader asynImageLoader;
	private CircularImageView cover_user_photo;
	
	private LinearLayout leftMenu_ll;

	// private TitleView mtTitleView;

	OnArticleSelectedListener mListener;

	private RelativeLayout secondaryLayout, friendLayout, activityLayout,
			workLayout, schoolLayout;
	
	private TextView t1, t2, t3, t4, t5;
	private ImageView i1, i2, i3, i4, i5;
	// private RelativeLayout settingLayout;

	private TextView nickname;
//	private TextView vid;
	
	private int tabheight;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_secondarylayout:
			secondaryLayout.setBackgroundResource(R.color.zhihu_content);
			
			t1.setTextColor(getResources().getColor(R.color.white));
			t2.setTextColor(getResources().getColor(R.color.black));
			t3.setTextColor(getResources().getColor(R.color.black));
			t4.setTextColor(getResources().getColor(R.color.black));
			t5.setTextColor(getResources().getColor(R.color.black));
			
			i1.setBackgroundResource(R.drawable.img_01);
			i2.setBackgroundResource(R.drawable.img_002);
			i3.setBackgroundResource(R.drawable.img_003);
			i4.setBackgroundResource(R.drawable.img_004);
			i5.setBackgroundResource(R.drawable.img_005);
			
			friendLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			activityLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			workLayout.setBackgroundResource(R.drawable.selector_category_item);
			schoolLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			// settingLayout.setBackgroundResource(R.drawable.selector_category_item);
			mListener.onArticleSelected(0);
			break;

		case R.id.id_friendlayout:
			friendLayout.setBackgroundResource(R.color.zhihu_content);
			
			t1.setTextColor(getResources().getColor(R.color.black));
			t2.setTextColor(getResources().getColor(R.color.white));
			t3.setTextColor(getResources().getColor(R.color.black));
			t4.setTextColor(getResources().getColor(R.color.black));
			t5.setTextColor(getResources().getColor(R.color.black));
			
			i1.setBackgroundResource(R.drawable.img_001);
			i2.setBackgroundResource(R.drawable.img_02);
			i3.setBackgroundResource(R.drawable.img_003);
			i4.setBackgroundResource(R.drawable.img_004);
			i5.setBackgroundResource(R.drawable.img_005);
			
			secondaryLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			activityLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			workLayout.setBackgroundResource(R.drawable.selector_category_item);
			schoolLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			// settingLayout.setBackgroundResource(R.drawable.selector_category_item);
			mListener.onArticleSelected(1);
			break;

		case R.id.id_activitylayout:
			activityLayout.setBackgroundResource(R.color.zhihu_content);
			
			t1.setTextColor(getResources().getColor(R.color.black));
			t2.setTextColor(getResources().getColor(R.color.black));
			t3.setTextColor(getResources().getColor(R.color.white));
			t4.setTextColor(getResources().getColor(R.color.black));
			t5.setTextColor(getResources().getColor(R.color.black));
			
			i1.setBackgroundResource(R.drawable.img_001);
			i2.setBackgroundResource(R.drawable.img_002);
			i3.setBackgroundResource(R.drawable.img_03);
			i4.setBackgroundResource(R.drawable.img_004);
			i5.setBackgroundResource(R.drawable.img_005);
			
			friendLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			secondaryLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			workLayout.setBackgroundResource(R.drawable.selector_category_item);
			schoolLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			// settingLayout.setBackgroundResource(R.drawable.selector_category_item);
			mListener.onArticleSelected(2);
			break;

		case R.id.id_worklayout:
			workLayout.setBackgroundResource(R.color.zhihu_content);
			
			t1.setTextColor(getResources().getColor(R.color.black));
			t2.setTextColor(getResources().getColor(R.color.black));
			t3.setTextColor(getResources().getColor(R.color.black));
			t4.setTextColor(getResources().getColor(R.color.white));
			t5.setTextColor(getResources().getColor(R.color.black));
			
			i1.setBackgroundResource(R.drawable.img_001);
			i2.setBackgroundResource(R.drawable.img_002);
			i3.setBackgroundResource(R.drawable.img_003);
			i4.setBackgroundResource(R.drawable.img_04);
			i5.setBackgroundResource(R.drawable.img_005);
			
			friendLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			activityLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			schoolLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			secondaryLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			// settingLayout.setBackgroundResource(R.drawable.selector_category_item);
			mListener.onArticleSelected(3);
			break;
			
		case R.id.id_schoollayout:
			schoolLayout.setBackgroundResource(R.color.zhihu_content);
			
			t1.setTextColor(getResources().getColor(R.color.black));
			t2.setTextColor(getResources().getColor(R.color.black));
			t3.setTextColor(getResources().getColor(R.color.black));
			t4.setTextColor(getResources().getColor(R.color.black));
			t5.setTextColor(getResources().getColor(R.color.white));
			
			i1.setBackgroundResource(R.drawable.img_001);
			i2.setBackgroundResource(R.drawable.img_002);
			i3.setBackgroundResource(R.drawable.img_003);
			i4.setBackgroundResource(R.drawable.img_004);
			i5.setBackgroundResource(R.drawable.img_05);
			
			friendLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			activityLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			secondaryLayout
					.setBackgroundResource(R.drawable.selector_category_item);
			workLayout.setBackgroundResource(R.drawable.selector_category_item);
			// settingLayout.setBackgroundResource(R.drawable.selector_category_item);
			mListener.onArticleSelected(4);
			break;

		/*
		 * case R.id.id_settinglayout:
		 * settingLayout.setBackgroundResource(R.color.zhihu_content);
		 * friendLayout.setBackgroundResource
		 * (R.drawable.selector_category_item);
		 * activityLayout.setBackgroundResource
		 * (R.drawable.selector_category_item);
		 * workLayout.setBackgroundResource(R.drawable.selector_category_item);
		 * schoolLayout
		 * .setBackgroundResource(R.drawable.selector_category_item);
		 * secondaryLayout
		 * .setBackgroundResource(R.drawable.selector_category_item);
		 * mListener.onArticleSelected(5); break;
		 */

		case R.id.id_personal_setting_info_ll_photo_leftMenu:
			/*
			 * settingLayout.setBackgroundResource(R.color.zhihu_content);
			 * friendLayout.setBackgroundResource
			 * (R.drawable.selector_category_item);
			 * activityLayout.setBackgroundResource
			 * (R.drawable.selector_category_item);
			 * workLayout.setBackgroundResource
			 * (R.drawable.selector_category_item);
			 * schoolLayout.setBackgroundResource
			 * (R.drawable.selector_category_item);
			 * secondaryLayout.setBackgroundResource
			 * (R.drawable.selector_category_item);
			 */
			mListener.onArticleSelected(5);
			break;

		default:
			break;
		}
	}

	public MenuLeftFragment(FragmentActivity mActivity) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		WindowManager vmManager = getActivity().getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 11;
		
		// 获取对象
		SharedPreferences share = mActivity.getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		// isregister_test = share.getBoolean("isregister", false);
		// section_type = share.getString("section_type", "secondary");
		requestString = share.getString("request", null);
		nicknameString = share.getString("nickname", null);
		passwordString = share.getString("password", null);

		Log.v(TAG + "requestString", requestString);

		request_vid = requestString.split("@@")[0];
		request_imageurl = requestString.split("@@")[1];

		Log.v(TAG + "request_imageurl", request_imageurl);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			initView(inflater, container);
		}
		return mView;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try {
			mListener = (OnArticleSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "must implement OnArticleSelectedListener");
		}
	}

	private void initView(LayoutInflater inflater, ViewGroup container) {
		mView = inflater.inflate(R.layout.left_menu, container, false);

		secondaryLayout = (RelativeLayout) mView
				.findViewById(R.id.id_secondarylayout);
		friendLayout = (RelativeLayout) mView
				.findViewById(R.id.id_friendlayout);
		workLayout = (RelativeLayout) mView.findViewById(R.id.id_worklayout);
		schoolLayout = (RelativeLayout) mView
				.findViewById(R.id.id_schoollayout);
		activityLayout = (RelativeLayout) mView
				.findViewById(R.id.id_activitylayout);
		
		t1 = (TextView)mView.findViewById(R.id.id_text1);
		t2 = (TextView)mView.findViewById(R.id.id_text2);
		t3 = (TextView)mView.findViewById(R.id.id_text3);
		t4 = (TextView)mView.findViewById(R.id.id_text4);
		t5 = (TextView)mView.findViewById(R.id.id_text5);
		
		i1 = (ImageView)mView.findViewById(R.id.id_img01);
		i2 = (ImageView)mView.findViewById(R.id.id_img02);
		i3 = (ImageView)mView.findViewById(R.id.id_img03);
		i4 = (ImageView)mView.findViewById(R.id.id_img04);
		i5 = (ImageView)mView.findViewById(R.id.id_img05);
		
		// settingLayout = (RelativeLayout) mView
		// .findViewById(R.id.id_settinglayout);
		
		leftMenu_ll = (LinearLayout)mView.findViewById(R.id.id_leftMenu_ll);

//		leftMenu_ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, tabheight));
		
		secondaryLayout.setBackgroundResource(R.color.zhihu_content);
		
		t1.setTextColor(getResources().getColor(R.color.white));
		t2.setTextColor(getResources().getColor(R.color.black));
		t3.setTextColor(getResources().getColor(R.color.black));
		t4.setTextColor(getResources().getColor(R.color.black));
		t5.setTextColor(getResources().getColor(R.color.black));
		
		i1.setBackgroundResource(R.drawable.img_01);
		i2.setBackgroundResource(R.drawable.img_002);
		i3.setBackgroundResource(R.drawable.img_003);
		i4.setBackgroundResource(R.drawable.img_004);
		i5.setBackgroundResource(R.drawable.img_005);

		nickname = (TextView) mView.findViewById(R.id.id_leftMenu_ll_ll_t1);
//		vid = (TextView) mView.findViewById(R.id.id_leftMenu_ll_ll_t2);

		nickname.setText(nicknameString);
//		vid.setText(request_vid);

		asynImageLoader = new AsynImageLoader(nicknameString);
		cover_user_photo = (CircularImageView) mView
				.findViewById(R.id.id_personal_setting_info_ll_photo_leftMenu);
		asynImageLoader.showImageAsyn(cover_user_photo, request_imageurl,
				R.drawable.login_photo_default);

		// mtTitleView = (TitleView)mView.findViewById(R.id.id_leftMenu_title);
		//
		// mtTitleView.setTitle("菜单");

		secondaryLayout.setOnClickListener(this);
		friendLayout.setOnClickListener(this);
		activityLayout.setOnClickListener(this);
		schoolLayout.setOnClickListener(this);
		workLayout.setOnClickListener(this);
		cover_user_photo.setOnClickListener(this);

		// mCategories = (ListView) mView
		// .findViewById(R.id.id_listview_categories);
		// mAdapter = new ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_list_item_1, mDatas);
		// mCategories.setAdapter(mAdapter);
	}

	public interface OnArticleSelectedListener {
		public void onArticleSelected(int n);
	}

}
