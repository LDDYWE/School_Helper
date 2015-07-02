package com.zb.secondary_market.adapter;

import com.zb.secondary_market.R;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Assortment_Price_ListViewAdapter extends BaseAdapter{
	private FragmentActivity mActivity;
	
	
	public Assortment_Price_ListViewAdapter(FragmentActivity mActivity) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position < 0 ? 0 : position ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		LayoutInflater inflater = mActivity.getLayoutInflater();
		rowView = inflater.inflate(R.layout.fragment_assortment_price_listview_item, null);
		
		ImageView icon;
		TextView name;
		TextView info;
		TextView place;
		TextView price;
		icon = (ImageView) rowView.findViewById(R.id.id_fragment_assortment_price_listview_item_image);
		
		WindowManager vmManager = mActivity.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		LayoutParams para;
		para = icon.getLayoutParams();

		para.height = height / 7;
		para.width = height / 7;
		icon.setLayoutParams(para);

		name = (TextView) rowView.findViewById(R.id.id_fragment_assortment_price_listview_item_ll_title);
		info = (TextView) rowView
				.findViewById(R.id.id_fragment_assortment_price_listview_item_ll_info);
		place = (TextView)rowView.findViewById(R.id.id_fragment_assortment_price_listview_item_ll_ll_place);
		price = (TextView)rowView.findViewById(R.id.id_fragment_assortment_price_listview_item_ll_ll_price);
		
		icon.setImageResource(R.drawable.test);
		name.setText("iphone7");
		info.setText("14级自动化 SA14010116");
		place.setText("科大南区");
		price.setText("$30");

		return rowView;
	}

}
