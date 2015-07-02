package com.zb.secondary_market.adapter;

import java.net.URLEncoder;
import java.util.List;

import android.R.integer;
import android.graphics.Paint;
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
import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;
import com.zb.secondary_market.register.HttpPostFile;

public class SecondaryMeReleasedGridViewAdapter extends BaseAdapter {
	private FragmentActivity mActivity;
	private List<Item_Detail> dataDetails;
	private ImageFetcher mImageFetcher;
	
	private String ssString;
	
//	private CircularImageView nickname_image;
//	private AsynImageLoader asynImageLoader;
//	private int content_item_height;
	
//	private String request_otherString;
	
	private ListView.LayoutParams itemViewLayoutParams;

	public SecondaryMeReleasedGridViewAdapter(ImageFetcher imageFetcher, FragmentActivity mActivity,
			int content_item_height, List<Item_Detail> dataDetails) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
		this.dataDetails = dataDetails;
		this.mImageFetcher = imageFetcher;
//		this.content_item_height = content_item_height;
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
		convertView = inflater.inflate(R.layout.contentgrid_item, parent,
				false); // initialize
		// convertView.setLayoutParams(itemViewLayoutParams);

		ImageView icon;
		TextView name;
		TextView nickname;
		TextView price;
//		TextView originalPrice;
		TextView datatime;
		icon = (ImageView) convertView
				.findViewById(R.id.id_content_image);

		WindowManager vmManager = mActivity.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		LayoutParams para;
		para = icon.getLayoutParams();
//
		para.height = height / 4;
//		para.width = para.height;
		icon.setLayoutParams(para);

//		nickname_image = (CircularImageView)convertView.findViewById(R.id.id_content_rl2_nickname_image);
		nickname = (TextView)convertView.findViewById(R.id.id_content_rl2_nickname);
		name = (TextView) convertView
				.findViewById(R.id.id_content_title);
		price = (TextView)convertView.findViewById(R.id.id_content_rl1_ll_price);
//		originalPrice = (TextView)convertView.findViewById(R.id.id_content_rl1_ll_originalprice);
//		info = (TextView) convertView
//				.findViewById(R.id.id_content_nickname);
//		place = (TextView) convertView
//				.findViewById(R.id.id_secondary_me_released_content_place);
		datatime = (TextView) convertView
				.findViewById(R.id.id_content_rl2_datetime);

//		icon.setImageResource(R.drawable.test);
		
		String imageurl = dataDetails.get(position).getImageurl().split("@@")[0];
		
		encode(imageurl);

		// 此处
		mImageFetcher.loadImage(ssString, icon);
		
//		mImageFetcher.loadImage(imageurl, icon);
		
//		request_otherString = HttpPostFile.upload_chat(dataDetails.get(position).getNickname());
		
//		asynImageLoader  = new AsynImageLoader(dataDetails.get(position).getNickname());
//		asynImageLoader.showImageAsyn(nickname_image, request_otherString,
//				R.drawable.login_photo_default);
		
		nickname.setText(dataDetails.get(position).getNickname());
		name.setText(dataDetails.get(position).getTitle());
		price.setText("￥" + dataDetails.get(position).getPrice());
		
		/*if(!dataDetails.get(position).getOriginalPrice().equals("")){
		originalPrice.setText(dataDetails.get(position).getOriginalPrice());
		originalPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
		originalPrice.setVisibility(View.VISIBLE);
		}else{
			originalPrice.setVisibility(View.GONE);
		}*/
		
//		info.setText(dataDetails.get(position).getNickname());
//		place.setText(dataDetails.get(position).getLocation());
		datatime.setText(dataDetails.get(position).getDateTime().split("-")[0]
				+ "-" + dataDetails.get(position).getDateTime().split("-")[1]
				+ "-" + dataDetails.get(position).getDateTime().split("-")[2]);

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
