package com.zb.secondary_market.fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zb.secondary_market.Content_Detail_School_Activity;
import com.zb.secondary_market.Content_Detail_Work_Activity;
import com.zb.secondary_market.R;
import com.zb.secondary_market.adapter.SchoolNoticeListViewAdapter;
import com.zb.secondary_market.custom.MyProgressBar;
import com.zb.secondary_market.custom.UsefulString;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.Item_Detail;
import com.zb.secondary_market.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zb.secondary_market.pulltorefresh.PullToRefreshListView;

public class School_Notice_Fragment extends Fragment {
	private static final String TAG = "School_Notice_Fragment";
	private static final String IMAGE_CACHE_DIR = "thumbs";

	private FragmentActivity mActivity;
	private View mParent;
//	private TitleView mTitle;
	private int tabheight = 0;
	// private GridView mainpageGridView;
	private ListView schoolNoticeListView;
	// private MainpageGridViewAdapter mainpageGridViewAdapter;
	private SchoolNoticeListViewAdapter schoolNoticeListViewAdapter;
	private int content_item_height = 0;
//	private SlidingMenu slidingMenu;
	private TextView textView;

	private List<Item_Detail> item_detail_List = new ArrayList<Item_Detail>();

	// private PullToRefreshGridView mPullRefreshGridView;
	private PullToRefreshListView mPullToRefreshListView;
	private ImageFetcher mImageFetcher;

	// private String url = "http://222.195.78.112:8000/second";
//	private String url = "http://222.195.78.112:8000/notice";
	private String url =  UsefulString.urlString + "notice";

	private DownloadVodInfo mtask;

	private int lastLastPosition = 0;
	private int lastLastPositionY = 0;

	private int loadjson_page = 1;
	private int loadjson_page_temp = 1;

	private Dialog loadingDialog = null;

	// private boolean ispull = true;
	private boolean isfirst = true;

	private String jsonContent_first;

	private boolean isupdated = false;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.i(TAG, "---onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		WindowManager vmManager = getActivity().getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 12;
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
		mImageFetcher = new ImageFetcher(getActivity(), 300);
		mImageFetcher.setLoadingImage(R.drawable.load_failed);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(),
				cacheParams);
		mImageFetcher.setImageFadeIn(false);

		Log.i(TAG, "---onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "---onCreateView");
		return inflater.inflate(R.layout.fragment_school_notice, null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "---onActivityCreated");
		mActivity = getActivity();
		mParent = getView();

		/*mTitle = (TitleView) mParent
				.findViewById(R.id.pull_school_notice_title);

		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
		mTitle.setTitle("通知信息");

		// LayoutInflater inflater = LayoutInflater.from(mActivity);
		// View layout = inflater.inflate(R.layout.activity_main, null);
		// slidingMenu = (SlidingMenu) layout.findViewById(R.id.id_slidemenu);

		mTitle.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						mActivity.finish();
					}
				});	*/
		
		/*//设置右边的上传按钮（功能上暂时还没有实现）
				mTitle.setImageRightButton(R.drawable.edit, new OnImageRightButtonClickListener() {
					
					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(mActivity, Friend_PhotoGraph_Activity.class);
							startActivity(intent);
							
//							mActivity.finish();
					}
				});*/

		// mPullRefreshGridView = (PullToRefreshGridView) mParent
		// .findViewById(R.id.pull_refresh_grid);
		mPullToRefreshListView = (PullToRefreshListView) mParent
				.findViewById(R.id.pull_refresh_list_school_notice);
		textView = (TextView) mParent
				.findViewById(R.id.pull_null_text_school_notice);

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
		
		// mainpageGridView = mPullRefreshGridView.getRefreshableView();
		schoolNoticeListView = mPullToRefreshListView.getRefreshableView();

		// Set a listener to be invoked when the list should be refreshed.
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefreshD2U() {
				// TODO Auto-generated method stub
				Log.v(TAG, "D2U");

				Log.v(TAG + "isupdated xxxxxxxxxxxxxx", isupdated + "");

				if (isupdated == false) {
					if (loadjson_page == 1) {
						loadjson_page = loadjson_page_temp;
					}
				}

				Log.v(TAG + "loadjson_pagexxxxxxxxx", loadjson_page + "");

				loadjson_page++;
				// ispull = true;

				mtask = new DownloadVodInfo();
				mtask.execute(url);
			}

			@Override
			public void onRefreshU2D() {
				// Do work to refresh the list here.
				// new GetDataTask().execute();

				Log.v(TAG, "U2D");

				// ispull = false;
				if (loadjson_page == 1) {

				} else {
					loadjson_page_temp = loadjson_page;
				}
				loadjson_page = 1;

				mtask = new DownloadVodInfo();

				mtask.execute(url);

				Log.v(TAG, "执行到这里了2");
			}

			@Override
			public void onLoading() {
				// new GetDataTask().execute();
				Toast.makeText(mActivity, "zhibuzhidao", Toast.LENGTH_SHORT)
						.show();
			}
		});

		Log.v(TAG + "IUNI", "IUNI");
		mtask = new DownloadVodInfo();
		mtask.execute(url);

	}

	public void toggleMenu(View view) {
//		slidingMenu.toggle();
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

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/*
	 * public void JsonDeal(String jsonContent) { try {
	 * 
	 * JSONObject jsonObject = new JSONObject(jsonContent); JSONArray page =
	 * jsonObject.getJSONArray("page"); for (int i = 0; i < page.length(); i++)
	 * { String s = page.getString(i); JSONObject data2 = new JSONObject(s);
	 * Item_Detail item_detail = new Item_Detail();
	 * 
	 * Pattern patt_sender = Pattern.compile("发信人: (.*?) "); Matcher
	 * matcher_sender = patt_sender.matcher(data2 .getString("content"));
	 * 
	 * item_detail.setDateTime(data2.getString("datatime"));
	 * 
	 * while (matcher_sender.find()) { System.out.println("您输入的HTML地址正确"); //
	 * System.out.println(matcher.group(1));
	 * item_detail.setAuthor(matcher_sender.group(1)); }
	 * 
	 * Pattern patt_title = Pattern.compile("标  题: (.*?) "); Matcher
	 * matcher_title = patt_title.matcher(data2 .getString("content"));
	 * 
	 * while (matcher_title.find()) { System.out.println("您输入的HTML地址正确"); //
	 * System.out.println(matcher.group(1));
	 * item_detail.setTitle(matcher_title.group(1)); }
	 * 
	 * Pattern patt_address = Pattern.compile("瀚海星云(.*?)WWWPOST"); Matcher
	 * matcher_address = patt_address.matcher(data2 .getString("content"));
	 * 
	 * while (matcher_address.find()) { System.out.println("您输入的HTML地址正确"); //
	 * System.out.println(matcher.group(1));
	 * item_detail.setContentAddress(matcher_address.group(0)); }
	 * 
	 * Pattern patt_detail = Pattern.compile("WWWPOST (.*?) ※ "); Matcher
	 * matcher_detail = patt_detail.matcher(data2 .getString("content"));
	 * 
	 * while (matcher_detail.find()) { System.out.println("您输入的HTML地址正确"); //
	 * System.out.println(matcher.group(1));
	 * item_detail.setContentDetail(matcher_detail.group(1)); }
	 * 
	 * Pattern patt_source = Pattern.compile("-- (.*?)\\]"); Matcher
	 * matcher_source = patt_source.matcher(data2 .getString("content"));
	 * 
	 * while (matcher_source.find()) { System.out.println("您输入的HTML地址dsada正确");
	 * // System.out.println(matcher.group(1));
	 * 
	 * item_detail.setContentSource(matcher_source.group(1) .replace("\n", "") +
	 * "]");
	 * 
	 * Log.e(TAG, item_detail.getContentSource()); }
	 * item_detail_List.add(item_detail); } } catch (Exception e) { // TODO:
	 * handle exception } }
	 */

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

				item_detail.setDateTime(data2.getString("datatime"));
//				item_detail.setVid(data2.getString("vid"));
				item_detail.setTitle(data2.getString("title"));
//				item_detail.setSex(data2.getString("sex"));
//				item_detail.setSchool(data2.getString("school"));
				item_detail.setContent(data2.getString("content"));
				// item_detail.setType(data2.getString("type"));
				// item_detail.setPrice(data2.getString("price"));
				// item_detail.setOriginalPrice(data2.getString("originalprice"));
//				item_detail.setId(data2.getString("id"));
				// item_detail.setLocation(data2.getString("location"));
//				item_detail.setNickname(data2.getString("author"));
				// item_detail.setAmount(data2.getString("amount"));
//				item_detail.setDiscription(data2.getString("more"));
				item_detail.setType(data2.getString("type"));

				Pattern patt_sender = Pattern.compile("发信人: (.*?) ");
				Matcher matcher_sender = patt_sender.matcher(data2
						.getString("content"));

				while (matcher_sender.find()) {
					System.out.println("您输入的HTML地址正确"); //
					System.out.println(matcher_sender.group(1));
					item_detail.setNickname(matcher_sender.group(1));
				}

				Pattern patt_address = Pattern.compile("瀚海星云(.*?)WWWPOST");
				Matcher matcher_address = patt_address.matcher(data2
						.getString("content"));

				while (matcher_address.find()) {
					System.out.println("您输入的HTML地址正确"); //
					System.out.println(matcher_address.group(1));
					item_detail.setContentAddress(matcher_address.group(0));
				}

				Pattern patt_detail = Pattern.compile("WWWPOST (.*?) ※ ");
				Matcher matcher_detail = patt_detail.matcher(data2
						.getString("content"));

				while (matcher_detail.find()) {
					System.out.println("您输入的HTML地址正确"); //
					System.out.println(matcher_detail.group(1));
					item_detail.setContentDetail(matcher_detail.group(1));
				}

				Pattern patt_source = Pattern.compile("-- (.*?)\\]");
				Matcher matcher_source = patt_source.matcher(data2
						.getString("content"));

				while (matcher_source.find()) {
					System.out.println("您输入的HTML地址dsada正确"); //
					System.out.println(matcher_source.group(1));

					item_detail.setContentSource(matcher_source.group(1)
							.replace("\n", "") + "]");

					Log.e(TAG, item_detail.getContentSource());
				}
				
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
			loadingDialog = MyProgressBar.createLoadingDialog(mActivity, UsefulString.is_loading);
			loadingDialog.setCanceledOnTouchOutside(false);
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
				jsonContent = getHtmlContent(params[0] + "?page="
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

			mPullToRefreshListView.onRefreshComplete();
			// } else {
			// dialog.dismiss();
			// }

			if (item_detail_List.size() == 0) {
				Log.v(TAG + "test textview", "fuckfuck");
				mPullToRefreshListView.setVisibility(View.GONE);
				textView.setVisibility(View.VISIBLE);
			} else {
				mPullToRefreshListView.setVisibility(View.VISIBLE);
				textView.setVisibility(View.GONE);
				if (loadjson_page == 1) {
					Log.v(TAG + "isfirst", isfirst + "");
					if (isfirst == false) {
						Log.v(TAG + "isupdated", isupdated + "");
						if (isupdated == true) {
							
							Log.v(TAG + "zhibuzhidao", item_detail_list.size() + "");
							
							schoolNoticeListViewAdapter = new SchoolNoticeListViewAdapter(
									mImageFetcher, getActivity(),
									// content_item_height,
									item_detail_list);
							schoolNoticeListView
									.setAdapter(schoolNoticeListViewAdapter);
							schoolNoticeListView
									.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {
											// TODO Auto-generated method stub
											Intent in = new Intent();

											in.putExtra("type", "notice");
											in.putExtra(
													"item_detail",
													(Serializable) item_detail_list
															.get(position));
											in.putExtra("position", position);
											in.setClass(
													mActivity,
													Content_Detail_Work_Activity.class);
											startActivity(in);
										}
									});
						} else {
							Log.v(TAG, "知不知道啊");
						}
					} else if (isfirst == true) {
						Log.v(TAG + "zhibuzhidao", item_detail_list.size() + "");
						schoolNoticeListViewAdapter = new SchoolNoticeListViewAdapter(
								mImageFetcher, getActivity(),
								// content_item_height,
								item_detail_list);
						schoolNoticeListView
								.setAdapter(schoolNoticeListViewAdapter);
						schoolNoticeListView
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
										Intent in = new Intent();

										in.putExtra("type", "notice");
										in.putExtra("item_detail",
												(Serializable) item_detail_list
														.get(position));
										in.putExtra("position", position);
										in.setClass(mActivity,
												Content_Detail_School_Activity.class);
										startActivity(in);
									}
								});
						isfirst = false;
					}
				} else {
					schoolNoticeListViewAdapter.notifyDataSetChanged();
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
