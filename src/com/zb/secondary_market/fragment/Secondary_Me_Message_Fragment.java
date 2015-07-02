package com.zb.secondary_market.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.zb.secondary_market.Content_Detail_Campaigner_Activity;
import com.zb.secondary_market.R;
import com.zb.secondary_market.Secondary_Imessage_Activity;
import com.zb.secondary_market.adapter.CampaignerMeReleasedListViewAdapter;
import com.zb.secondary_market.adapter.Secondary_My_MessageListViewAdapter;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.database.DatabaseHelper;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zb.secondary_market.pulltorefresh.PullToRefreshListView;
import com.zb.secondary_market.service.ImessageService;

public class Secondary_Me_Message_Fragment extends Fragment {
	private static final String TAG = "Secondary_My_Message_Fragment";
	private static final String IMAGE_CACHE_DIR = "thumbs";

	private ImageFetcher mImageFetcher;

	private FragmentActivity mActivity;
	private View mParent;

	private List<String> nickname_stringList;
	private List<String> headphotourl_stringList;

	private Secondary_My_MessageListViewAdapter secondary_My_MessageListViewAdapter;

	private ListView secondarymymessageListView;
	// private PullToRefreshListView mPullToRefreshListView;
	private TextView textView;

	private String nicknameString;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_secondary_my_message, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "---onActivityCreated");
		mActivity = getActivity();
		mParent = getView();

		// 获取对象
		SharedPreferences share = mActivity.getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		nicknameString = share.getString("nickname", null);

		/**
		 * 
		 * 此处是为了从网络上获取图片
		 **/
		// set memory cache 25% of the app memory
		ImageCacheParams cacheParams = new ImageCacheParams(getActivity(),
				IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory
		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(getActivity(), 1000);
		mImageFetcher.setLoadingImage(R.drawable.load_failed);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(),
				cacheParams);
		mImageFetcher.setImageFadeIn(false);

		secondarymymessageListView = (ListView) mParent
				.findViewById(R.id.pull_refresh_list_secondary_my_message);
		textView = (TextView) mParent
				.findViewById(R.id.pull_null_text_secondary_my_message);

		// Set a listener to be invoked when the list should be refreshed.
		/*
		 * mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
		 * 
		 * @Override public void onRefreshD2U() { // TODO Auto-generated method
		 * stub
		 * 
		 * Log.v(TAG, "D2U");
		 * 
		 * Log.v(TAG + "isupdated xxxxxxxxxxxxxx", isupdated + "");
		 * 
		 * if (isupdated == false) { if (loadjson_page == 1) { loadjson_page =
		 * loadjson_page_temp; } }
		 * 
		 * Log.v(TAG + "loadjson_pagexxxxxxxxx", loadjson_page + "");
		 * 
		 * loadjson_page++; // ispull = true;
		 * 
		 * mtask = new DownloadVodInfo(); mtask.execute(url);
		 * 
		 * }
		 * 
		 * @Override public void onRefreshU2D() { // Do work to refresh the list
		 * here.
		 * 
		 * // new GetDataTask().execute();
		 * 
		 * Log.v(TAG, "U2D");
		 * 
		 * // ispull = false; if (loadjson_page == 1) {
		 * 
		 * } else { loadjson_page_temp = loadjson_page; } loadjson_page = 1;
		 * 
		 * mtask = new DownloadVodInfo();
		 * 
		 * mtask.execute(url);
		 * 
		 * Log.v(TAG, "执行到这里了2");
		 * 
		 * }
		 * 
		 * @Override public void onLoading() { // new GetDataTask().execute();
		 * Toast.makeText(mActivity, "zhibuzhidao", Toast.LENGTH_SHORT) .show();
		 * } });
		 */

		// 此处建立一张关于联系人的名字和头像url的表
		DatabaseHelper database_helper_contactDatabaseHelper = new DatabaseHelper(
				mActivity, nicknameString);
		SQLiteDatabase db_contact = database_helper_contactDatabaseHelper
				.getWritableDatabase();// 这里是获得可写的数据库
		db_contact
				.execSQL("create table if not exists secondary_contact_list(_id integer primary key autoincrement,nickname text not null,headphoto_url not null)");

		Cursor c_contact = db_contact.rawQuery(
				"select * from secondary_contact_list", null);

		nickname_stringList = new ArrayList<String>();
		headphotourl_stringList = new ArrayList<String>();

		if (c_contact != null) {
			String[] cols = c_contact.getColumnNames();
			while (c_contact.moveToNext()) {
				for (String ColumnName : cols) {
					Log.i("info",
							ColumnName
									+ ":"
									+ c_contact.getString(c_contact
											.getColumnIndex(ColumnName)));

				}
				nickname_stringList.add(c_contact.getString(c_contact
						.getColumnIndex("nickname")));
				headphotourl_stringList.add(c_contact.getString(c_contact
						.getColumnIndex("headphoto_url")));
			}
			c_contact.close();
		}
		db_contact.close();

		secondary_My_MessageListViewAdapter = new Secondary_My_MessageListViewAdapter(
				mImageFetcher, getActivity(), nicknameString, nickname_stringList,
				headphotourl_stringList);

		if (nickname_stringList.size() == 0) {
			textView.setVisibility(View.VISIBLE);
		} else {
			textView.setVisibility(View.GONE);
			secondarymymessageListView
					.setAdapter(secondary_My_MessageListViewAdapter);
		}

		secondarymymessageListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra("type", "2");
						intent.putExtra("nickname_other", nickname_stringList.get(position));
						intent.setClass(mActivity, Secondary_Imessage_Activity.class);
						startActivity(intent);
						// TODO Auto-generated method stub
						// Intent in = new Intent();
						//
						// in.putExtra("type", "secondary");
						// in.putExtra("item_detail",
						// (Serializable) item_detail_list.get(position));
						// in.putExtra("position", position);
						// in.setClass(mActivity,
						// Content_Detail_Campaigner_Activity.class);
						// startActivity(in);
					}
				});

	}
}
