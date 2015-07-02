package com.zb.secondary_market.fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zb.secondary_market.Content_Detail_Campaigner_Activity;
import com.zb.secondary_market.R;
import com.zb.secondary_market.adapter.CampaignerMeReleasedListViewAdapter;
import com.zb.secondary_market.custom.MyProgressBar;
import com.zb.secondary_market.custom.UsefulString;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;
import com.zb.secondary_market.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zb.secondary_market.pulltorefresh.PullToRefreshGridView_1;
import com.zb.secondary_market.pulltorefresh.PullToRefreshListView_1;

public class Campaigner_Other_Released_Fragment extends Fragment {
	private static final String TAG = "Campaigner_Other_Released_Fragment";
	private static final String IMAGE_CACHE_DIR = "thumbs_high";

	private FragmentActivity mActivity;
	private View mParent;
	private int tabheight = 0;
	private ListView campaigner_other_releasedListView;
	private CampaignerMeReleasedListViewAdapter campaigner_other_releasedListViewAdapter;
	private TextView textView;

	private List<Item_Detail> item_detail_List = new ArrayList<Item_Detail>();

	private PullToRefreshListView_1 mPullRefreshListView;

//	private String url = "http://222.195.78.112:8000/myinfo";
	private String url =  UsefulString.urlString + "myinfo";

	private DownloadVodInfo mtask;
	private ImageFetcher mImageFetcher;

	private int lastLastPosition = 0;
	private int lastLastPositionY = 0;

	private int loadjson_page = 1;
	private int loadjson_page_temp = 1;
	private int content_item_height = 0;

	private Dialog loadingDialog = null;

	private boolean isupdated = false;

	// private boolean ispull = true;
	private boolean isfirst = true;

	private String jsonContent_first;

	private String nicknameString, vidString;
	
	private String other_vid;
	
	public Campaigner_Other_Released_Fragment(String other_vid) {
		// TODO Auto-generated constructor stub
		this.other_vid = other_vid;
	}
	
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
		WindowManager vmManager = getActivity().getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		content_item_height = (int) width * 21 / 32;

		/**
		 * 
		 * 此处是为了从网络上获取图片
		 **/
		// set memory cache 25% of the app memory
		ImageCacheParams cacheParams = new ImageCacheParams(getActivity(),
				IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
													// app memory
		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(getActivity(), 500);
		mImageFetcher.setLoadingImage(R.drawable.load_failed);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(),
				cacheParams);
		mImageFetcher.setImageFadeIn(false);

		Log.i(TAG, "---onCreate");
		Log.v(TAG, "---onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Log.v(TAG, "---onCreateView");
		return inflater.inflate(R.layout.fragment_campaigner_other_released, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.v(TAG, "---onActivityCreated");

		mActivity = getActivity();
		mParent = getView();

		// 获取对象
		SharedPreferences share = mActivity.getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		nicknameString = share.getString("nickname", null);
		vidString = share.getString("vid", null);

		mPullRefreshListView = (PullToRefreshListView_1) mParent
				.findViewById(R.id.id_fragment_campaigner_other_released_tt_pull_refresh_lv);
		textView = (TextView) mParent
				.findViewById(R.id.id_fragment_campaigner_other_released_tt);

		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v(TAG, "执行到这里了2");
				// TODO Auto-generated method stub
				if (loadjson_page == 1) {

				} else {
					loadjson_page_temp = loadjson_page;
				}
				loadjson_page = 1;
				isupdated = true;
				mtask = new DownloadVodInfo();

				mtask.execute(url);
			}
		});
		
		campaigner_other_releasedListView = mPullRefreshListView
				.getRefreshableView();
		
//		campaigner_other_releasedListView.setHorizontalSpacing(5);
//		campaigner_other_releasedListView.setVerticalSpacing(5);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefreshD2U() {
				// TODO Auto-generated method stub
				
				if (isupdated == false) {
					if (loadjson_page == 1) {
						loadjson_page = loadjson_page_temp;
					}
				}
				
				loadjson_page++;
				// ispull = true;
				mtask = new DownloadVodInfo();
				mtask.execute(url);
			}

			@Override
			public void onRefreshU2D() {
				// ispull = false;
				
				if (isupdated == false) {
					if (loadjson_page == 1) {
						loadjson_page = loadjson_page_temp;
					}
				}
				
				loadjson_page = 1;
				mtask = new DownloadVodInfo();
				mtask.execute(url);
			}

			@Override
			public void onLoading() {
				// new GetDataTask().execute();
				Toast.makeText(mActivity, "zhibuzhidao", Toast.LENGTH_SHORT)
						.show();
			}
		});

		mtask = new DownloadVodInfo();
		mtask.execute(url);

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

	public void JsonDeal(String jsonContent) {
		try {
			JSONObject jsonObject = new JSONObject(jsonContent);
			JSONArray page = jsonObject.getJSONArray("page");

			Log.v(TAG + "page.length()", page.length() + "");

			for (int i = 0; i < page.length(); i++) {
				String s = page.getString(i);
				JSONObject data2 = new JSONObject(s);
				Item_Detail item_detail = new Item_Detail();

				// Pattern patt_sender =
				// Pattern.compile("\"datetime\": \"(.*?)\",");
				// Matcher matcher_sender = patt_sender.matcher(data2
				// .getString("content"));

				item_detail.setDateTime(data2.getString("datetime"));
				item_detail.setVid(data2.getString("vid"));
				item_detail.setTitle(data2.getString("title"));
				item_detail.setType(data2.getString("type"));
				item_detail.setPrice(data2.getString("cost"));
				// item_detail.setOriginalPrice(data2.getString("originalprice"));
				item_detail.setId(data2.getString("id"));
				item_detail.setLocation(data2.getString("location"));
				item_detail.setNickname(data2.getString("nickname"));
				// item_detail.setAmount(data2.getString("amount"));
				item_detail.setDiscription(data2.getString("declaration"));
				item_detail.setImagerul(data2.getString("picture"));

				Log.v(TAG + "item_detail.getImageurl()",
						item_detail.getImageurl());
				
				Log.v(TAG + "item_detail.getLocation()",
						item_detail.getLocation());

				// while (matcher_sender.find()) {
				// System.out.println("您输入的HTML地址正确");
				// // System.out.println(matcher.group(1));
				// item_detail.setAuthor(matcher_sender.group(1));
				// }

				// Pattern patt_title =
				// Pattern.compile("\"title\": \"(.*?)\",");
				// Matcher matcher_title = patt_title.matcher(data2
				// .getString("content"));
				//
				// while (matcher_title.find()) {
				// System.out.println("您输入的HTML地址正确");
				// // System.out.println(matcher.group(1));
				// item_detail.setTitle(matcher_title.group(1));
				// }
				//
				// Pattern patt_address =
				// Pattern.compile("\"location\": \"(.*?)\"");
				// Matcher matcher_address = patt_address.matcher(data2
				// .getString("content"));
				//
				// while (matcher_address.find()) {
				// System.out.println("您输入的HTML地址正确");
				// // System.out.println(matcher.group(1));
				// item_detail.setContentAddress(matcher_address.group(0));
				// }
				//
				// Pattern patt_detail =
				// Pattern.compile("\"discription\": \"(.*?)\",");
				// Matcher matcher_detail = patt_detail.matcher(data2
				// .getString("content"));
				//
				// while (matcher_detail.find()) {
				// System.out.println("您输入的HTML地址正确");
				// // System.out.println(matcher.group(1));
				// item_detail.setContentDetail(matcher_detail.group(1));
				// }

				/*
				 * Pattern patt_source = Pattern.compile("-- (.*?)\\]"); Matcher
				 * matcher_source = patt_source.matcher(data2
				 * .getString("content"));
				 * 
				 * while (matcher_source.find()) {
				 * System.out.println("您输入的HTML地址dsada正确"); //
				 * System.out.println(matcher.group(1));
				 * 
				 * item_detail.setContentSource(matcher_source.group(1)
				 * .replace("\n", "") + "]");
				 * 
				 * Log.e(TAG, item_detail.getContentSource()); }
				 */

				item_detail_List.add(item_detail);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private class DownloadVodInfo extends
			AsyncTask<String, Void, List<Item_Detail>> {

		public DownloadVodInfo() {
			// TODO Auto-generated constructor stub
			Log.v(TAG, "---DownloadVodInfo");
		}

		@Override
		protected void onPreExecute() {
			// dialog = MyProgressB
			loadingDialog = MyProgressBar.createLoadingDialog(mActivity, "");
			loadingDialog.show();

			Log.v(TAG, "---onPreExecute");
		}

		@Override
		protected List<Item_Detail> doInBackground(String... params) {
			// TODO Auto-generated method stub

			Log.v(TAG, "---doInBackground");
			try {
				Log.v(TAG, "---doInBackground1");
				String jsonContent = "";
				Log.v(TAG, "---doInBackground2");
				jsonContent = getHtmlContent(params[0] + "?vid=" + other_vid + "&type=activity" + "&page="
						+ loadjson_page, "utf-8");
				Log.v(TAG, "---doInBackground3");
				if (loadjson_page == 1) {
					Log.v(TAG, "---doInBackground4");
					if (isfirst == true) {
						jsonContent_first = jsonContent;

						Log.v(TAG, jsonContent);
						Log.v(TAG, jsonContent_first);
						JsonDeal(jsonContent);
					} else {
						Log.v(TAG, jsonContent);
						Log.v(TAG, jsonContent_first);

						if (jsonContent_first.equals(jsonContent)) {
							Log.v(TAG, "jsonContent = jsonContent_first");
							isupdated = false;

						} else {
							isupdated = true;
							item_detail_List = new ArrayList<Item_Detail>();
							JsonDeal(jsonContent);
						}
					}
				} else {
					JsonDeal(jsonContent);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

			} catch (Exception e) {
				// TODO: handle exception
			}

			// Log.e("testtest", item_detail_List.get(2).getTitle());
			// Log.e("testtest", item_detail_List.get(3).getTitle());

			Log.v(TAG, "---doInBackground");

			return item_detail_List;
		}

		@Override
		protected void onPostExecute(final List<Item_Detail> item_detail_list) {
			// Show the dummy content as text in a TextView.

			// if (ispull == false) {

			loadingDialog.dismiss();

			Log.v(TAG + "item_detail.size()", item_detail_list.size() + "");

			mPullRefreshListView.onRefreshComplete();
			// } else {
			// dialog.dismiss();
			// }

			if (item_detail_List.size() == 0) {
				Log.v(TAG + "test textview", "fuckfuck");
				mPullRefreshListView.setVisibility(View.GONE);
				textView.setVisibility(View.VISIBLE);
			} else {
				mPullRefreshListView.setVisibility(View.VISIBLE);
				textView.setVisibility(View.GONE);
				if (loadjson_page == 1) {
					Log.v(TAG + "isfirst", isfirst + "");
					if (isfirst == false) {
						Log.v(TAG + "isupdated", isupdated + "");
						if (isupdated == true) {
							campaigner_other_releasedListViewAdapter = new CampaignerMeReleasedListViewAdapter(
									mImageFetcher, getActivity(),item_detail_list);
							campaigner_other_releasedListView
									.setAdapter(campaigner_other_releasedListViewAdapter);
							campaigner_other_releasedListView
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub
											Intent in = new Intent();

											in.putExtra("type", "campaigner");
											in.putExtra(
													"item_detail",
													(Serializable) item_detail_list
															.get(position));
											in.putExtra("position", position);
											in.setClass(
													mActivity,
													Content_Detail_Campaigner_Activity.class);
											startActivity(in);
										}
									});
						} else {
							Log.v(TAG, "知不知道啊");
						}
					} else if (isfirst == true) {
						campaigner_other_releasedListViewAdapter = new CampaignerMeReleasedListViewAdapter(
								mImageFetcher, getActivity(),item_detail_list);
						campaigner_other_releasedListView
								.setAdapter(campaigner_other_releasedListViewAdapter);
						campaigner_other_releasedListView
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
										Intent in = new Intent();

										in.putExtra("type", "campaigner");
										in.putExtra("item_detail",
												(Serializable) item_detail_list
														.get(position));
										in.putExtra("position", position);
										in.setClass(mActivity,
												Content_Detail_Campaigner_Activity.class);
										startActivity(in);
									}
								});
						isfirst = false;
					}
				} else {
					campaigner_other_releasedListViewAdapter.notifyDataSetChanged();
				}
			}
			Log.v(TAG, "---onPostExecute");

		}
	}

	// 该方法是为了得到输入网址的网页源代码
	public static String getHtmlContent(String html, String encode) {
		String content = "";
		StringBuffer contentBuffer = new StringBuffer();
		URL url = null;
		HttpURLConnection con = null;
		try {
			url = new URL(html);
			con = (HttpURLConnection) url.openConnection();
			// con.setRequestProperty("User-Agent",
			// "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");//
			// IE代理进行下载
			con.setConnectTimeout(60000);
			con.setReadTimeout(60000);

			InputStream inStr = con.getInputStream();
			InputStreamReader istreamReader = new InputStreamReader(inStr,
					encode);
			BufferedReader buffStr = new BufferedReader(istreamReader);

			String str = null;
			while ((str = buffStr.readLine()) != null)
				contentBuffer.append(str);
			inStr.close();

			content = contentBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			contentBuffer = null;
			content = "";
			System.out.println("error: " + url.toString());
		} finally {
			con.disconnect();
		}
		return content;
	}

}
