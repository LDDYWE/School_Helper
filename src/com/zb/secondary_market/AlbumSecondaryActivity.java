package com.zb.secondary_market;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.zb.secondary_market.camera.AlbumGridViewAdapter;
import com.zb.secondary_market.camera.AlbumHelper;
import com.zb.secondary_market.camera.Bimp;
import com.zb.secondary_market.camera.ImageBucket;
import com.zb.secondary_market.camera.ImageItem;
import com.zb.secondary_market.camera.PublicWay;
import com.zb.secondary_market.camera.Res;
import com.zb.secondary_market.custom.TitleView_Text;
import com.zb.secondary_market.custom.TitleView_Text.OnTextLeftButtonClickListener;
import com.zb.secondary_market.custom.TitleView_Text.OnTextRightButtonClickListener;

/**
 * 这个是进入相册显示所有图片的界面
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日 下午11:47:15
 */
public class AlbumSecondaryActivity extends Activity {
	private static final String TAG = "AlbumActivity";

	// 显示手机里的所有图片的列表控件
	private GridView gridView;
	// 当手机里没有图片时，提示用户没有图片的控件
	private TextView tv;
	// gridView的adapter
	private AlbumGridViewAdapter gridImageAdapter;
	// 完成按钮
	private Button okButton;
	// 返回按钮
	// private Button back;
	// // 取消按钮
	// private Button cancel;
	private Intent intent;
	// 预览按钮
	private Button preview;
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	public static Bitmap bitmap;

	private TitleView_Text album_titleView;

//	private String section_type;

	private int titleHeight;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(Res.getLayoutID("plugin_camera_album"));
		PublicWay.activityList.add(this);
		mContext = this;
		// 注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		registerReceiver(broadcastReceiver, filter);
		bitmap = BitmapFactory.decodeResource(getResources(),
				Res.getDrawableID("plugin_camera_no_pictures"));

//		section_type = getIntent().getStringExtra("section_type");
//
//		Log.v(TAG + "section_type", section_type);

		init();
		initListener();
		// 这个函数主要用来控制预览和完成按钮的状态
		isShowOkBt();

		Log.v(TAG, "---onCreate");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v(TAG, "---onStart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "---onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// mContext.unregisterReceiver(this);
			// TODO Auto-generated method stub

			Log.v(TAG, "---broadcastreceiver---onReceive");

			gridImageAdapter.notifyDataSetChanged();
		}
	};

	// 预览按钮的监听
	private class PreviewListener implements OnClickListener {
		public void onClick(View v) {
			if (Bimp.tempSelectBitmap.size() > 0) {
				Log.v(TAG, "---PreviewListener");

				intent.putExtra("position", "1");
				intent.setClass(AlbumSecondaryActivity.this, GallerySecondaryActivity.class);
				startActivity(intent);
				
				finish();
			}
		}

	}

	// 完成按钮的监听
	private class AlbumSendListener implements OnClickListener {
		public void onClick(View v) {
			overridePendingTransition(R.anim.activity_translate_in,
					R.anim.activity_translate_out);

/*//			if (section_type.equals("secondary")) {
//				Log.v("XXXXXXXXXXXXXXXXXXXXX", "secondary");
				intent.setClass(AlbumSecondaryActivity.this,
						Secondary_PhotoGraph_Activity.class);
//			} else if (section_type.equals("friend")) {
//				intent.setClass(AlbumSecondaryActivity.this,
//						Friend_PhotoGraph_Activity.class);
//			} else if (section_type.equals("compaigner")) {
//				Log.v("XXXXXXXXXXXXXXXXXXXXX", "compaigner");
//				intent.setClass(AlbumSecondaryActivity.this,
//						Compaigner_PhotoGraph_Activity.class);
//			}
			startActivity(intent);*/
			finish();
		}

	}

	// 返回按钮监听
	private class BackListener implements OnClickListener {
		public void onClick(View v) {
			intent.setClass(AlbumSecondaryActivity.this, ImageFileSecondaryActivity.class);
			startActivity(intent);
		}
	}

	// 取消按钮的监听
	/*
	 * private class CancelListener implements OnClickListener { public void
	 * onClick(View v) { Log.v(TAG + "cancel", "是否执行了取消按钮");
	 * Bimp.tempSelectBitmap.clear(); Bimp.max = 0; intent.setClass(mContext,
	 * Secondary_PhotoGraph_Activity.class); startActivity(intent); finish(); }
	 * }
	 */

	// 初始化，给一些对象赋值
	private void init() {
		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		titleHeight = (int) height * 1 / 12;

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();
		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).imageList);
		}

		album_titleView = (TitleView_Text) findViewById(R.id.id_album_title);

		album_titleView.setTitle("照片");
		album_titleView.setLayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT, titleHeight);

		album_titleView.setTextLeftButton("相册",
				new OnTextLeftButtonClickListener() {

					@Override
					public void onClick(View TextView) {
						// TODO Auto-generated method stub
						intent.setClass(AlbumSecondaryActivity.this,
								ImageFileSecondaryActivity.class);
						startActivity(intent);
						
						finish();
					}
				});

		album_titleView.setTextRightButton("取消",
				new OnTextRightButtonClickListener() {

					@Override
					public void onClick(View TextView) {
						// TODO Auto-generated method stub
						Log.v(TAG + "cancel", "是否执行了取消按钮");
						Bimp.tempSelectBitmap.clear();
						Bimp.max = 0;
						/*intent.setClass(mContext,
								Secondary_PhotoGraph_Activity.class);
						startActivity(intent);*/
						finish();
					}
				});

		// back = (Button) findViewById(Res.getWidgetID("back"));
		// cancel = (Button) findViewById(Res.getWidgetID("cancel"));
		// cancel.setOnClickListener(new CancelListener());
//		back.setOnClickListener(new BackListener());
		preview = (Button) findViewById(Res.getWidgetID("preview"));
		preview.setOnClickListener(new PreviewListener());
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		gridView = (GridView) findViewById(Res.getWidgetID("myGrid"));

		// 此处设置gridview的Adapter 用于显示文件夹中的图片
		gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
				Bimp.tempSelectBitmap);
		gridView.setAdapter(gridImageAdapter);
		tv = (TextView) findViewById(Res.getWidgetID("myText"));
		gridView.setEmptyView(tv);
		okButton = (Button) findViewById(Res.getWidgetID("ok_button"));

		okButton.setText(Res.getString("finish") + "("
				+ Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
	}

	private void initListener() {

		gridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(final ToggleButton toggleButton,
							int position, boolean isChecked, Button chooseBt) {
						if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
							toggleButton.setChecked(false);
							chooseBt.setVisibility(View.GONE);
							if (!removeOneData(dataList.get(position))) {
								Toast.makeText(AlbumSecondaryActivity.this,
										Res.getString("only_choose_num"), 200)
										.show();
							}
							return;
						}

						if (isChecked) {
							chooseBt.setVisibility(View.VISIBLE);

							// 往要在上传图片界面中的gridview中显示出来的图片集合中加入图片
							Bimp.tempSelectBitmap.add(dataList.get(position));
							okButton.setText(Res.getString("finish") + "("
									+ Bimp.tempSelectBitmap.size() + "/"
									+ PublicWay.num + ")");
						} else {
							Bimp.tempSelectBitmap.remove(dataList.get(position));
							chooseBt.setVisibility(View.GONE);
							okButton.setText(Res.getString("finish") + "("
									+ Bimp.tempSelectBitmap.size() + "/"
									+ PublicWay.num + ")");
						}
						isShowOkBt();
					}
				});

		okButton.setOnClickListener(new AlbumSendListener());

	}

	private boolean removeOneData(ImageItem imageItem) {
		if (Bimp.tempSelectBitmap.contains(imageItem)) {
			Bimp.tempSelectBitmap.remove(imageItem);
			okButton.setText(Res.getString("finish") + "("
					+ Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			return true;
		}
		return false;
	}

	// 当选中至少一张图片之后，便将一些按钮的背景替换掉
	public void isShowOkBt() {
		if (Bimp.tempSelectBitmap.size() > 0) {
			okButton.setText(Res.getString("finish") + "("
					+ Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			preview.setPressed(true);
			okButton.setPressed(true);
			preview.setClickable(true);
			okButton.setClickable(true);
			okButton.setTextColor(Color.WHITE);
			preview.setTextColor(Color.WHITE);
		} else {
			okButton.setText(Res.getString("finish") + "("
					+ Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			preview.setPressed(false);
			preview.setClickable(false);
			okButton.setPressed(false);
			okButton.setClickable(false);
			okButton.setTextColor(Color.parseColor("#E1E0DE"));
			preview.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (Bimp.tempSelectBitmap.size() == 0) {
				Bimp.max = 0;
			}
//			intent.setClass(AlbumSecondaryActivity.this,
//					Secondary_PhotoGraph_Activity.class);
//			startActivity(intent);

			finish();
		}
		return false;

	}

	@Override
	protected void onRestart() {
		isShowOkBt();
		super.onRestart();
	}
}
