package com.zb.secondary_market.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zb.secondary_market.R;
import com.zb.secondary_market.adapter.SecondaryMainpageGridViewAdapter;

public class Content_Fragment2 extends Fragment implements OnItemClickListener{
//	private GridView gv_content;
	private SecondaryMainpageGridViewAdapter contentGridViewAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.contentfragment2, container, false);

		initView(view);
		return view;
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

	public void initView(View view) {
//		gv_content = (GridView) view.findViewById(R.id.id_gv_content);
//		gv_content.setAdapter(contentGridViewAdapter);
//		gv_content.setOnItemClickListener(this);
//		gv_content.setEnabled(true);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.e("asdasdasd", "jdiasjdiasj");
//		String text = gv_content.getItemAtPosition(position) + "";
//		Toast.makeText(getActivity(),
//				"position = " + position + "text = " + text, Toast.LENGTH_SHORT)
//				.show();
	}
}
