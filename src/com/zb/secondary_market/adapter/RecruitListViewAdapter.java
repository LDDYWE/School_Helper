package com.zb.secondary_market.adapter;

import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zb.secondary_market.R;
import com.zb.secondary_market.otherclasses.Item_Detail;

public class RecruitListViewAdapter extends BaseAdapter {
	private FragmentActivity mActivity;
	private List<Item_Detail> dataDetails;

	private ListView.LayoutParams itemViewLayoutParams;

	public RecruitListViewAdapter(FragmentActivity mActivity,
			List<Item_Detail> dataDetails) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
		this.dataDetails = dataDetails;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position < 0 ? 0 : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = mActivity.getLayoutInflater();
		convertView = inflater.inflate(R.layout.recruit_list_item, parent,
				false); // initialize
		// convertView.setLayoutParams(itemViewLayoutParams);

		ImageView icon;
		TextView name;
		TextView info;
		TextView place;
		TextView datatime;
		icon = (ImageView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_image_recruit);

		WindowManager vmManager = mActivity.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		LayoutParams para;
		para = icon.getLayoutParams();
//
		para.height = height / 7;
//		para.width = para.height;
		icon.setLayoutParams(para);

		name = (TextView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_title_recruit);
		info = (TextView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_info_recruit);
		place = (TextView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll_place_recruit);
		datatime = (TextView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll_price_recruit);

		icon.setImageResource(R.drawable.test);
		name.setText(dataDetails.get(position).getTitle());
		info.setText(dataDetails.get(position).getNickname());
		place.setText("科大");
		datatime.setText(dataDetails.get(position).getDateTime());

		return convertView;
	}

}
