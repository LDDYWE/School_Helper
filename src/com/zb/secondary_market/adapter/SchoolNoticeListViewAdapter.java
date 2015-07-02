package com.zb.secondary_market.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;

public class SchoolNoticeListViewAdapter extends BaseAdapter {
	private static final String TAG = "SchoolNoticeListViewAdapter";

	private FragmentActivity mActivity;
	private List<Item_Detail> dataDetails;

	private ListView.LayoutParams itemViewLayoutParams;

	private ImageFetcher mImageFetcher;

	private List<String> imageurl;

	public SchoolNoticeListViewAdapter(ImageFetcher imageFetcher,
			FragmentActivity mActivity, List<Item_Detail> dataDetails) {
		// TODO Auto-generated constructor stub
		Log.v(TAG, "fuckfuckfuck");
		this.mActivity = mActivity;
		this.dataDetails = dataDetails;
		this.mImageFetcher = imageFetcher;
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
		Log.v(TAG, "fuckfuckfuck");
		LayoutInflater inflater = mActivity.getLayoutInflater();
		convertView = inflater.inflate(R.layout.school_list_item,
				parent, false); // initialize
		// convertView.setLayoutParams(itemViewLayoutParams);

//		ImageView icon;
		TextView name;
//		TextView nickname;
//		TextView sex;
		TextView discription;
//		TextView type;
		TextView datatime;
//		icon = (ImageView) convertView
//				.findViewById(R.id.id_fragment_assortment_variety_listview_item_image_school_withtitle);

		imageurl = new ArrayList<String>();

		// for (int j = 0; j <
		// dataDetails.get(position).getImageurl().split("@@").length; j++) {
		// imageurl.add(dataDetails.get(position).getImageurl().split("@@")[j]);
		// Log.v(TAG, imageurl.get(j));
		// }

		WindowManager vmManager = mActivity.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

//		LayoutParams para;
//		para = icon.getLayoutParams();
//		//
//		para.height = height / 5;
//		// para.width = para.height;
//		icon.setLayoutParams(para);

		name = (TextView) convertView
				.findViewById(R.id.id_school_ll1_title);
//		nickname = (TextView) convertView
//				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_school_ll_nickname_withtitle);
		discription = (TextView) convertView
				.findViewById(R.id.id_school_ll2_content);
//		type = (TextView) convertView
//				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll_type_school_withtitle);
		datatime = (TextView) convertView
				.findViewById(R.id.id_school_ll2_datetime);

		// mImageFetcher.loadImage(imageurl.get(0), icon);
		// icon.setImageResource(R.drawable.test);

		Log.v(TAG + "huranzhijian", dataDetails.get(position).getTitle());

		name.setText((dataDetails.get(position).getTitle().equals("")) ? "未知"
				: dataDetails.get(position).getTitle());

		// sex.setText(dataDetails.get(position).getSex());

		Pattern patt_detail = Pattern.compile("WWWPOST (.*?) ※ ");
		Matcher matcher_detail = patt_detail.matcher(dataDetails.get(position).getContent());

		while (matcher_detail.find()) {
			System.out.println("您输入的HTML地址正确"); //
			System.out.println(matcher_detail.group(1));
			discription.setText((matcher_detail.group(1).equals(""))?"未知":matcher_detail.group(1));
		}
		
//		discription
//				.setText((dataDetails.get(position).getContent().equals("")) ? "未知"
//						: dataDetails.get(position).getContent());
//		nickname.setText((dataDetails.get(position).getNickname().equals("")) ? "未知"
//				: dataDetails.get(position).getNickname());
//		type.setText((dataDetails.get(position).getType().equals("")) ? "未知"
//				: dataDetails.get(position).getType());
		datatime.setText((dataDetails.get(position).getDateTime().equals("")) ? "未知"
				: dataDetails.get(position).getDateTime());

		Log.v(TAG, "fuckfuckfuck");

		// String datetimeString = dataDetails.get(position).getDateTime()
		// .split("-")[0]
		// + "-"
		// + dataDetails.get(position).getDateTime().split("-")[1]
		// + "-" + dataDetails.get(position).getDateTime().split("-")[2];
		// datatime.setText(datetimeString);

		return convertView;
	}

}
