package com.zb.secondary_market.fragment;

import com.zb.secondary_market.R;
import com.zb.secondary_market.adapter.Assortment_Credit_ListViewAdapter;
import com.zb.secondary_market.adapter.Assortment_Variety_ListViewAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Assortment_Credit_Fragment extends Fragment {
	private static final String TAG = "Assortment_Credit_Fragment";

	private FragmentActivity mActivity;
	private View mParent;

	private ListView credit_listView;
	private Assortment_Credit_ListViewAdapter assortment_Credit_ListViewAdapter;

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
		return inflater.inflate(R.layout.fragment_assortment_credit, null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		mActivity = getActivity();
		mParent = getView();

		initView();

		credit_listView.setAdapter(assortment_Credit_ListViewAdapter);
		Log.v(TAG, "---onActivityCreated");
	}

	private void initView() {
		// TODO Auto-generated method stub
		credit_listView = (ListView) mParent
				.findViewById(R.id.id_fragment_assortment_credit_lv);
		assortment_Credit_ListViewAdapter = new Assortment_Credit_ListViewAdapter(
				mActivity);
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
