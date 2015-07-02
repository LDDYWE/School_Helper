package com.zb.secondary_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.zb.secondary_market.camera.PhotoView;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.imagecache.ImageFetcher;

public class Campaigner_Image_Detail_Activity extends FragmentActivity {
	private static final String TAG = "Campaigner_Image_Detail_Activity";
	private static final String IMAGE_CACHE_DIR = "thumbs_superhigh";
	
	private PhotoView photoView;

	private String image_url;

	private ImageFetcher mImageFetcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_campaigner_image_detail);

		Intent intent = getIntent();
		image_url = intent.getStringExtra("imageurl");
		Log.v(TAG, image_url);
		
		photoView = (PhotoView) findViewById(R.id.id_campaigner_image_detail_image);

		// set memory cache 25% of the app memory
		ImageCacheParams cacheParams = new ImageCacheParams(this,
				IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory
		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously

		mImageFetcher = new ImageFetcher(this, 1000);
		mImageFetcher.setLoadingImage(R.drawable.load_failed);
		mImageFetcher.addImageCache(Campaigner_Image_Detail_Activity.this
				.getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);
		
		if(image_url.equals("false")) {
			photoView.setImageResource(R.drawable.music_200_200);
		} else {
			mImageFetcher.loadImage(image_url, photoView);
		}
	}
}
