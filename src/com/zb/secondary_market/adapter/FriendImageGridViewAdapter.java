package com.zb.secondary_market.adapter;

import java.net.URLEncoder;
import java.util.List;

import android.R.integer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.zb.secondary_market.R;
import com.zb.secondary_market.imagecache.ImageFetcher;

public class FriendImageGridViewAdapter extends BaseAdapter{
	private static final String TAG = "FriendImageGridViewAdapter";
	
	private FragmentActivity mActivity;
	private ImageFetcher mImageFetcher;
	private List<String> imageurl;
	
	private GridView.LayoutParams itemViewLayoutParams;
	
	private ImageView imageView;
	
	private String ssString;

	public FriendImageGridViewAdapter(FragmentActivity mActivity, ImageFetcher mImageFetcher, List<String> imageurl, int height) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
		this.mImageFetcher = mImageFetcher;
		this.imageurl = imageurl;
		
		itemViewLayoutParams = new GridView.LayoutParams(
				height, height);
		
		Log.v(TAG + "imageurl.size()", imageurl.size() + "");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageurl.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = mActivity.getLayoutInflater();
		convertView = inflater.inflate(R.layout.friend_image_gridview_item,
				parent, false);	
		
//		convertView.setLayoutParams(itemViewLayoutParams);

		imageView = (ImageView)convertView.findViewById(R.id.friend_image_gridview_item_imageview);
		
		encode(imageurl.get(position));
		
		mImageFetcher.loadImage(ssString, imageView);
		
//		convertView.setLayoutParams(itemViewLayoutParams);

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
