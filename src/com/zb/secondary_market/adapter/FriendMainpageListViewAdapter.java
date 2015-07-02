package com.zb.secondary_market.adapter;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smackx.carbons.packet.CarbonExtension.Private;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zb.secondary_market.R;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;

public class FriendMainpageListViewAdapter extends BaseAdapter {
	private static final String TAG = "CampaignerMainpageListViewAdapter";
	
	private FragmentActivity mActivity;
	private List<Item_Detail> dataDetails;

	private ListView.LayoutParams itemViewLayoutParams;
	
	private ImageFetcher mImageFetcher;
	
	private List<String> imageurl;
	
	private String ssString;
	
	public FriendMainpageListViewAdapter(ImageFetcher imageFetcher, FragmentActivity mActivity,
			List<Item_Detail> dataDetails) {
		// TODO Auto-generated constructor stub
		Log.v(TAG , "fuckfuckfuck");
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
		Log.v(TAG , "fuckfuckfuck");
		LayoutInflater inflater = mActivity.getLayoutInflater();
		convertView = inflater.inflate(R.layout.friend_mainpage_list_item,
				parent, false); // initialize
		// convertView.setLayoutParams(itemViewLayoutParams);

		ImageView icon;
		TextView name;
		TextView nickname;
		ImageView sex;
		TextView declaration;
		TextView school;
		TextView datatime;
//		LinearLayout school_gps_ll;
		icon = (ImageView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_image_friend_mainpage);

		imageurl = new ArrayList<String>();
		
		for (int j = 0; j < dataDetails.get(position).getImageurl().split("@@").length; j++) {
			imageurl.add(dataDetails.get(position).getImageurl().split("@@")[j]);
			Log.v(TAG, imageurl.get(j));																			
		}
		
		WindowManager vmManager = mActivity.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		LayoutParams para;
		para = icon.getLayoutParams();
		//
		 para.height = height / 6;
		// para.width = para.height;
		 icon.setLayoutParams(para);

		name = (TextView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll0_title_friend_mainpage);
		nickname = (TextView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll1_nickname_friend_mainpage);
		sex = (ImageView)convertView.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll2_ll1_sex_friend_mainpage);
		declaration = (TextView)convertView.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll3_declaration_friend_mainpage);
		school = (TextView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll2_ll2_school_friend_mainpage);
		datatime = (TextView) convertView
				.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll1_datetime_friend_mainpage);
//		school_gps_ll = (LinearLayout)convertView.findViewById(R.id.id_fragment_assortment_variety_listview_item_ll_ll2_ll2_friend_mainpage);
		
		encode(imageurl.get(0));
		
		mImageFetcher.loadImage(ssString, icon);
//		icon.setImageResource(R.drawable.test);
		name.setText(dataDetails.get(position).getTitle());
		
		if(dataDetails.get(position).getSex().equals("男")){
			sex.setBackgroundResource(R.drawable.gender_boy);
		}else if(dataDetails.get(position).getSex().equals("女")){
			sex.setBackgroundResource(R.drawable.gender_girl);
		}
		
//		sex.setText(dataDetails.get(position).getSex());
		declaration.setText(dataDetails.get(position).getDeclaration());
		nickname.setText(dataDetails.get(position).getNickname());
		
		Log.v(TAG + "dataDetails.get(position).getLocation()", dataDetails.get(position).getLocation());
		
		if(dataDetails.get(position).getLocation().equals("null")){
			school.setText("未认证学校");
//			school_gps_ll.setBackgroundResource(R.drawable.corner_shape55);
		}else {
			school.setText(dataDetails.get(position).getLocation());
		}

		String datetimeString = dataDetails.get(position).getDateTime()
				.split("-")[0]
				+ "-"
				+ dataDetails.get(position).getDateTime().split("-")[1]
				+ "-" + dataDetails.get(position).getDateTime().split("-")[2];
		datatime.setText(datetimeString);

		return convertView;
	}
	
	public void encode(String imageurl) {
		try {
			int n = imageurl.split("/").length;
			String ss = imageurl.split("/")[n - 1];
			String sss = URLEncoder.encode(ss, "UTF-8");
			ssString = imageurl.replace(ss, sss);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
