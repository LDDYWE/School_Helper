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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.zb.secondary_market.Content_Detail_Secondary_Activity;
import com.zb.secondary_market.R;
import com.zb.secondary_market.adapter.Assortment_Variety_ListViewAdapter;
import com.zb.secondary_market.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zb.secondary_market.pulltorefresh.PullToRefreshListView;

public class Assortment_Variety_Fragment extends Fragment {

	private static final String TAG = "Assortment_Variety_Fragment";

	private ListView variety_listView;
	private Assortment_Variety_ListViewAdapter assortment_Variety_ListViewAdapter;

	private View mParent;
	private FragmentActivity mActivity;

	private PullToRefreshListView mPullRefreshListView;

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

		Log.v(TAG, "---onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "---onCreateView");
		return inflater.inflate(R.layout.fragment_assortment_variety, null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		mParent = getView();
		mActivity = getActivity();

		initView();

		variety_listView.setAdapter(assortment_Variety_ListViewAdapter);

		Log.v(TAG, "---onActivityCreated");
	}

	private void initView() {
		// TODO Auto-generated method stub
		mPullRefreshListView = (PullToRefreshListView) mParent
				.findViewById(R.id.id_fragment_assortment_variety_lv);
		variety_listView = mPullRefreshListView.getRefreshableView();
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefreshD2U() {
				// TODO Auto-generated method stub
				Toast.makeText(mActivity, "D2U", Toast.LENGTH_SHORT)
				.show();
			}
			
			@Override
			public void onRefreshU2D() {
				// Do work to refresh the list here.
				// new GetDataTask().execute();

				Toast.makeText(mActivity, "U2D", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onLoading() {
				// TODO Auto-generated method stub

			}
		});

		// variety_listView =
		// (ListView)mParent.findViewById(R.id.id_fragment_assortment_variety_lv);
		assortment_Variety_ListViewAdapter = new Assortment_Variety_ListViewAdapter(
				mActivity);

		variety_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mActivity, Content_Detail_Secondary_Activity.class);
				startActivity(intent);
			}
		});
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
