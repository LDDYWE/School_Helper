package com.zb.secondary_market.adapter;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.secondary_market.R;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.otherclasses.Item_Detail;

public class SecondaryMainpageGridViewAdapter extends BaseAdapter {
	private static final String TAG = "MainpageGridViewAdapter";

	private Context mContext;
	private FragmentActivity mActivity;
	private int mItemHeight = 0;
	private int mNumColumns = 0;
	private List<Item_Detail> item_detail;
	private GridView.LayoutParams itemViewLayoutParams;
	private ImageFetcher mImageFetcher;
	private ImageView imageView;
	private TextView titleTextView, gps_schoolTextView, datetimeTextView;
	// private TextView originalPriceTextView;
	private TextView priceTextView;
	private TextView nicknameTextView;
	// private CircularImageView nickname_image;
	// private AsynImageLoader asynImageLoader;

	private List<String> imageurl;

	private String ssString;

	// private String request_otherString;

	public SecondaryMainpageGridViewAdapter(ImageFetcher imageFetcher,
			FragmentActivity mActivity, Context context, int height,
			List<Item_Detail> item_detail) {
		super();
		this.mActivity = mActivity;
		this.mImageFetcher = imageFetcher;
		mContext = context;
		itemViewLayoutParams = new GridView.LayoutParams(
				LayoutParams.MATCH_PARENT, height);
		this.item_detail = item_detail;
	}

	// public ContentGridViewAdapter() {
	// // TODO Auto-generated constructor stub
	// }

	@Override
	public int getCount() {
		// Size + number of columns for top empty row
		// return vodList.size() + mNumColumns;
		return item_detail.size();
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

	// @Override
	// public Object getItem(int position) {
	// return position < mNumColumns ? null : vodList.get(
	// position - mNumColumns).getTitle();
	// }

	// @Override
	// public long getItemId(int position) {
	// return position < mNumColumns ? 0 : position - mNumColumns;
	// }

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	// @Override
	// public int getItemViewType(int position) {
	// return (position < mNumColumns) ? 1 : 0;
	// }

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getView(final int position, View convertView,
			ViewGroup container) {
		Log.e("test", "whether we are here");
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) { // if it's not recycled, instantiate and
			convertView = inflater.inflate(R.layout.contentgrid_item,
					container, false); // initialize
		}
		convertView.setLayoutParams(itemViewLayoutParams);
		// Check the height matches our calculated column width
		if (convertView.getLayoutParams().height != mItemHeight) {
			convertView.setLayoutParams(itemViewLayoutParams);
		}

		imageurl = new ArrayList<String>();

		Log.v(TAG + "item_detail.size()", item_detail.size() + "");
		// for (int i = 0; i < item_detail.size(); i++) {
		// Log.v(TAG + "item_detail.get(i).getImageurl().split(\"@@\").length",
		// item_detail.get(i).getImageurl().split("@@").length+ "");
		for (int j = 0; j < item_detail.get(position).getImageurl().split("@@").length; j++) {
			imageurl.add(item_detail.get(position).getImageurl().split("@@")[j]);
			Log.v(TAG, imageurl.get(j));
		}
		// }

		imageView = (ImageView) convertView.findViewById(R.id.id_content_image);
		// Finally load the image asynchronously into the ImageView, this
		// also takes care of
		// setting a placeholder image while the background thread runs

		// mImageFetcher.loadImage(R.drawable.test,
		// imageView);

		// imageView.setImageResource(R.drawable.test);
		// asynImageLoader.showImageAsyn(imageView,
		// imageurl.get(0), R.drawable.ic_launcher);

		Log.v(TAG, "数不清的数码产品");

		/**
		 * 
		 * 此处是为了从网络上获取图片
		 **/
		/*
		 * // set memory cache 25% of the app memory ImageCacheParams
		 * cacheParams = new ImageCacheParams(mContext, IMAGE_CACHE_DIR);
		 * cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25%
		 * of // app memory // The ImageFetcher takes care of loading images
		 * into our ImageView // children asynchronously mImageFetcher = new
		 * ImageFetcher(mContext, 1000);
		 * 
		 * Log.i(TAG, "---onCreate");
		 * 
		 * mImageFetcher.setLoadingImage(R.drawable.load_failed);
		 * mImageFetcher.addImageCache(mActivity.getSupportFragmentManager(),
		 * cacheParams); mImageFetcher.setImageFadeIn(false);
		 */

		encode(imageurl.get(0));

		// 此处
		mImageFetcher.loadImage(ssString, imageView);

		// nickname_image = (CircularImageView) convertView
		// .findViewById(R.id.id_content_rl2_nickname_image);
		nicknameTextView = (TextView) convertView
				.findViewById(R.id.id_content_rl2_nickname);
		titleTextView = (TextView) convertView
				.findViewById(R.id.id_content_title);
		priceTextView = (TextView) convertView
				.findViewById(R.id.id_content_rl1_ll_price);
		// originalPriceTextView = (TextView) convertView
		// .findViewById(R.id.id_content_rl1_ll_originalprice);

		// infoTextView = (TextView) convertView
		// .findViewById(R.id.id_content_nickname );
		// gps_schoolTextView =
		// (TextView)convertView.findViewById(R.id.id_content_school_gps);
		// placeTextView = (TextView) convertView
		// .findViewById(R.id.id_content_place);
		datetimeTextView = (TextView) convertView
				.findViewById(R.id.id_content_rl2_datetime);

		String datatime = item_detail.get(position).getDateTime().split("-")[0]
				+ "-" + item_detail.get(position).getDateTime().split("-")[1]
				+ "-" + item_detail.get(position).getDateTime().split("-")[2];

		// request_otherString = HttpPostFile.upload_chat(item_detail
		// .get(position).getNickname());
		//
		// asynImageLoader = new AsynImageLoader(item_detail.get(position)
		// .getNickname());
		// asynImageLoader.showImageAsyn(nickname_image, request_otherString,
		// R.drawable.login_photo_default);

		nicknameTextView.setText(item_detail.get(position).getNickname());
		priceTextView.setText("￥" + item_detail.get(position).getPrice());

		/*
		 * if(!item_detail.get(position).getOriginalPrice().equals("")){
		 * originalPriceTextView.setText("￥" +
		 * item_detail.get(position).getOriginalPrice()); //给TextView中的文字中间加横线
		 * originalPriceTextView.getPaint().setFlags(Paint.
		 * STRIKE_THRU_TEXT_FLAG );
		 * originalPriceTextView.setVisibility(View.VISIBLE); }else{
		 * originalPriceTextView.setVisibility(View.GONE); }
		 */

		titleTextView.setText(item_detail.get(position).getTitle());

		// infoTextView.setText(item_detail.get(position).getNickname());
		// gps_schoolTextView.setText(item_detail.get(position).getLocation());
		// placeTextView.setText("科大");
		datetimeTextView.setText(datatime);
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

	public void setItemHeight(int height) {
		if (height == mItemHeight) {
			return;
		}
		mItemHeight = height;
		itemViewLayoutParams = new GridView.LayoutParams(
				LayoutParams.MATCH_PARENT, mItemHeight);
		mImageFetcher.setImageSize(height);
		notifyDataSetChanged();
	}
}
