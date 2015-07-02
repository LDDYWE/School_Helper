package com.zb.secondary_market;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.zb.secondary_market.camera.Bimp;
import com.zb.secondary_market.camera.ImageItem;
import com.zb.secondary_market.camera.PublicWay;
import com.zb.secondary_market.camera.Res;
import com.zb.secondary_market.custom.AddAndSubView;
import com.zb.secondary_market.custom.MyProgressBar;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.otherclasses.Item_Detail;
import com.zb.secondary_market.register.HttpPostFile;

public class Secondary_PhotoGraph_Activity extends Activity {
	private static final String TAG = "Secondary_PhotoGraph_Activity";
	private static int CAMERA_REQUEST_CODE = 1;

	private TitleView mTitle;
	private PopupWindow mPopupWindow;
	private ScrollView photograph_scrollview;

	private Released_Task mTask;

	// 商品基本信息的控件定义
	private EditText title_edit;
	private Spinner type_spinner;
	private EditText price_edit;
	private EditText original_price_edit;
	// private Spinner amount_spinner;
	private EditText discription_edit;
	private Button submit_btn;

	private int titleHeight;

	private String picPath = null;

	private String requestString1;

	private Uri photoUri = null;

	private Button shot_btn;

	private PopupWindow pop = null;
	private LinearLayout ll_popup;

	private GridView noScrollgridview;
	private GridAdapter adapter;

	private LinearLayout linearLayout;

	public static Bitmap bimap;

	private View parentView;

	private Dialog loadingDialog = null;

	private AddAndSubView addAndSubView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "---onCreate");
		super.onCreate(savedInstanceState);
		Log.v(TAG, "---onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		parentView = getLayoutInflater().inflate(
				R.layout.activity_secondary_photograph, null);
		setContentView(parentView);

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;

		Log.v(TAG, currentapiVersion + "");

		if (currentapiVersion > 8) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());

			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
					.build());
		}

		Initwidget();

		Log.v(TAG, "---onCreate");
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// mContext.unregisterReceiver(this);
			// TODO Auto-generated method stub

			Log.v(TAG, "---broadcastreceiver---onReceive");

			adapter.notifyDataSetChanged();
		}
	};

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		// 注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
		IntentFilter filter = new IntentFilter("data.broadcast.action1");
		registerReceiver(broadcastReceiver, filter);

		super.onStart();
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);

		Init();
		Log.v(TAG, "---onStart");
	}

	// 处理拍完照和选完图片之后对图片进行的操作
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
			// Uri selectedImage = data.getData();
			try {
				if (data != null && data.getData() != null) {
					photoUri = data.getData();
				}

				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(photoUri,
						filePathColumn, null, null, null);
				if (cursor != null) {
					ContentResolver cr = this.getContentResolver();
					int colunm_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(colunm_index);

					Log.v(TAG + "path", path);

					picPath = path;

					cursor.close();
				}

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;

				if (photoUri == null) {
					Bundle bundle = data.getExtras();
					if (bundle != null) {
						Bitmap bitmap = (Bitmap) bundle.get("data"); // get
																		// bitmap
						Bitmap bitmap1 = setScaleBitmap(bitmap, 2 / 1);

						ImageItem iiImageItem = new ImageItem();
						iiImageItem.setBitmap(bitmap1);
						iiImageItem.setImagePath(picPath);

						Bimp.tempSelectBitmap.add(iiImageItem);

						adapter.notifyDataSetChanged();

						// spath :生成图片取个名字和路径包含类型
					} else {
						Toast.makeText(getApplicationContext(), "获取照片出错",
								Toast.LENGTH_LONG).show();
						return;
					}
				} else {
					ContentResolver cr = this.getContentResolver();
					Bitmap bitmap = BitmapFactory.decodeStream(
							cr.openInputStream(photoUri), null, options);
					// Bitmap bitmap = BitmapFactory.decodeStream(cr
					// .openInputStream(selectedImage), null, options);
					Bitmap bitmap1 = setScaleBitmap(bitmap, 2 / 1);

					ImageItem iiImageItem = new ImageItem();
					iiImageItem.setBitmap(bitmap1);
					iiImageItem.setImagePath(picPath);

					Bimp.tempSelectBitmap.add(iiImageItem);

					adapter.notifyDataSetChanged();

					// 添加替换头像的代码
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		super.onActivityResult(requestCode, resultCode, data);

		Log.v(TAG, "---onActivityResult");
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 4) {
				return 4;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				// 设置那张带有加号的默认图片的
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				// 如果gridview中已经有四张图片了，那么默认图片便会消失
				if (position == 4) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				Log.v(TAG, "nizguvyzgu");
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Log.v(TAG
									+ "Bimp.max == Bimp.tempSelectBitmap.size()",
									Bimp.tempSelectBitmap.size() + "");
							Log.v(TAG
									+ "Bimp.max == Bimp.tempSelectBitmap.size()",
									Bimp.max + "");
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Log.v(TAG
									+ "Bimp.max != Bimp.tempSelectBitmap.size()",
									Bimp.tempSelectBitmap.size() + "");
							Log.v(TAG
									+ "Bimp.max != Bimp.tempSelectBitmap.size()",
									Bimp.max + "");
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	private static final int TAKE_PICTURE = 0x000001;

	// 跳转到照相机界面，此处还需进行定制（自定义界面）
	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	// 初始化商品信息的控件
	public void Initwidget() {
		title_edit = (EditText) findViewById(R.id.id_secondary_photograph_scrollview_ll2_title);
		type_spinner = (Spinner) findViewById(R.id.id_secondary_photograph_scrollview_ll3_spinner);
		price_edit = (EditText) findViewById(R.id.id_secondary_photograph_scrollview_ll4_price);
		original_price_edit = (EditText) findViewById(R.id.id_secondary_photograph_scrollview_ll5_originalprice);
		// amount_spinner = (Spinner)
		// findViewById(R.id.id_secondary_photograph_scrollview_ll6_spinner);
		discription_edit = (EditText) findViewById(R.id.id_secondary_photograph_scrollview_ll7_detail);
		submit_btn = (Button) findViewById(R.id.id_secondary_photograph_scrollview_submmit_btn);
		linearLayout = (LinearLayout) findViewById(R.id.id_secondary_photograph_scrollview_ll6_linearlayout);

		addAndSubView1 = new AddAndSubView(Secondary_PhotoGraph_Activity.this,
				1);

		linearLayout.addView(addAndSubView1);

		submit_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Bimp.tempSelectBitmap.size() != 0
						&& !title_edit.getText().toString().equals("")
						&& !type_spinner.getSelectedItem().toString()
								.equals("选择分类")
						&& !price_edit.getText().toString().equals("")
						&& addAndSubView1.getNum() != 0
						&& !discription_edit.getText().toString().equals("")) {
					Log.v(TAG + "title_edit.getText().toString()", title_edit
							.getText().toString());

					mTask = new Released_Task();
					mTask.execute();

				} else {
					Toast.makeText(Secondary_PhotoGraph_Activity.this,
							"很抱歉, 您有部分内容没有填写, 请确认之后再发布", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	public void Init() {

		// TODO Auto-generated method stub
		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		titleHeight = (int) height * 1 / 12;

		mTitle = (TitleView) findViewById(R.id.secondary_photograph_title);
		// shot_btn = (Button)
		// findViewById(R.id.id_secondary_photograph_scrollview_ll1_btn);
		photograph_scrollview = (ScrollView) findViewById(R.id.id_secondary_photograph_scrollview);

		mTitle.setLayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
				titleHeight);
		mTitle.setTitle("发布商品");
		mTitle.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						// 创建退出对话框
						AlertDialog isExit = new AlertDialog.Builder(
								Secondary_PhotoGraph_Activity.this).create();
						// 设置对话框标题
						isExit.setTitle("系统提示");
						// 设置对话框消息
						isExit.setMessage("确定要放弃编辑吗？");
						// 添加选择按钮并注册监听
						isExit.setButton("确定", listener);
						isExit.setButton2("取消", listener);
						// 显示对话框
						isExit.show();
					}
				});

		// 此处可以用于发布（或者把发布做在最下面）
		/*
		 * mTitle.setImageRightButton(R.drawable.arrow_right, new
		 * OnImageRightButtonClickListener() {
		 * 
		 * @Override public void onClick(View Imagebutton) { // TODO
		 * Auto-generated method stub // Toast.makeText(this, "已完成上传工作", //
		 * Toast.LENGTH_LONG).show(); } });
		 */

		pop = new PopupWindow(Secondary_PhotoGraph_Activity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		// 这是拍照上传按钮（暂时未做）
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				quizCamHandler();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v(TAG + "add photo", "是否点击了添加图片的GridView");
				Intent intent = new Intent(Secondary_PhotoGraph_Activity.this,
						AlbumSecondaryActivity.class);

				intent.putExtra("section_type", "secondary");

				startActivity(intent);
				// finish();
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		Log.v(TAG + "noScrollgridview.setOnItemClickListener",
				"noScrollgridview.setOnItemClickListener");

		noScrollgridview = (GridView) findViewById(R.id.id_secondary_photograph_scrollview_ll1_gridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);

		Log.v(TAG + "noScrollgridview.setOnItemClickListener",
				"noScrollgridview.setOnItemClickListener");

		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Log.v(TAG + "arg2", arg2 + "");
				Log.v(TAG + "Bimp.tempSelectBitmap.size()",
						Bimp.tempSelectBitmap.size() + "");

				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							Secondary_PhotoGraph_Activity.this,
							R.anim.activity_translate_in));

					// 隐藏软键盘
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(
									Secondary_PhotoGraph_Activity.this
											.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);

					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(
							Secondary_PhotoGraph_Activity.this,
							GallerySecondaryActivity.class);
					// intent.putExtra("section_type", "secondary");
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
					// finish();
				}
			}
		});

	}

	private class Released_Task extends AsyncTask<Void, Void, String> {

		public Released_Task() {
			// TODO Auto-generated constructor stub
			Log.v(TAG, "---DownloadVodInfo");
		}

		@Override
		protected void onPreExecute() {
			// dialog = MyProgressB
			loadingDialog = MyProgressBar.createLoadingDialog(
					Secondary_PhotoGraph_Activity.this, "正在上传...");
			loadingDialog.setCanceledOnTouchOutside(false);
			loadingDialog.show();

			Log.v(TAG, "---onPreExecute");
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				if (Bimp.tempSelectBitmap.get(0).getImagePath() != null) {

					// 获取对象
					SharedPreferences share = getSharedPreferences("jonny",
							Activity.MODE_WORLD_READABLE);
					String nicknameString = share.getString("nickname", null);
					String requestString = share.getString("request", null);
					String vidString = share.getString("vid", null);
					String gps_schoolString = share.getString("gps_school",
							null);

					Log.v(TAG, vidString);

					String goods_infoString = vidString + "*"
							+ title_edit.getText().toString() + "*"
							+ nicknameString + "*"
							+ price_edit.getText().toString() + "*"
							+ original_price_edit.getText().toString() + "*"
							+ type_spinner.getSelectedItem().toString() + "*"
							+ discription_edit.getText().toString() + "*"
							// + amount_spinner.getSelectedItem().toString()
							+ addAndSubView1.getNum() + "*" + gps_schoolString;

					List<File> file = new ArrayList<File>();

					Log.v(TAG + "Bimp.tempSelectBitmap.size()",
							Bimp.tempSelectBitmap.size() + "");

					for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
						file.add(new File(Bimp.tempSelectBitmap.get(i)
								.getImagePath()));
					}

					Log.v(TAG, file.size() + "");

					if (file != null) {
						String request = HttpPostFile.uploadFile_secondary(
								goods_infoString, file);
						Log.v(TAG, request);

						Bimp.tempSelectBitmap.clear();
						Bimp.max = 0;

						requestString1 = request;

						// 上传log日志至服务器端

						Time local = new Time();
						local.set(System.currentTimeMillis());
						String dateString = local.format2445();

						String log_infoString = vidString + "*"
								+ nicknameString + "*" + "跳蚤市场—发布" + "*"
								+ title_edit.getText().toString() + "*" + "1";

						String request_log = HttpPostFile
								.upload_logs(log_infoString);
						// Log.v(TAG, request);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			return requestString1;
		}

		@Override
		protected void onPostExecute(final String request) {
			// Show the dummy content as text in a TextView.
			new Handler().postDelayed(new Runnable() {
				public void run() {
					// execute the task
					if (request.equals("true")) {
						/*
						 * Intent intent = new Intent(); //
						 * intent.putExtra("index", "upload_finished");
						 * intent.setClass( Secondary_PhotoGraph_Activity.this,
						 * MainActivity.class); startActivity(intent);
						 */

						// 存储对象
						SharedPreferences sharedPreferences = Secondary_PhotoGraph_Activity.this
								.getSharedPreferences("jonny",
										Context.MODE_PRIVATE); // 私有数据
						Editor editor = sharedPreferences.edit();// 获取编辑器
						editor.putString("section_type", "secondary");
						editor.commit();// 提交修改

						finish();
					}
				}
			}, 2000);
		}
	}

	// 实现拍照上传
	public void quizCamHandler() {
		if (isSdcardExisting()) {
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			String filename = "headphoto.jpg";
			ContentValues values = new ContentValues();
			values.put(Media.TITLE, filename);
			photoUri = getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
		} else {
			Toast.makeText(Secondary_PhotoGraph_Activity.this, "请插入存储卡！",
					Toast.LENGTH_SHORT).show();
		}
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				Bimp.tempSelectBitmap.clear();
				Bimp.max = 0;

				/*
				 * Intent intent = new Intent(); // intent.putExtra("index",
				 * "upload_finished");
				 * intent.setClass(Secondary_PhotoGraph_Activity.this,
				 * MainActivity.class); startActivity(intent);
				 */
				// 存储对象
				SharedPreferences sharedPreferences = Secondary_PhotoGraph_Activity.this
						.getSharedPreferences("jonny",
								Context.MODE_PRIVATE); // 私有数据
				Editor editor = sharedPreferences.edit();// 获取编辑器
				editor.putString("section_type", "other");
				editor.commit();// 提交修改
				
				finish();
				// Log.e("指示当前Activity退出", "下一步要执行SendInfo()");
				// SendInfo();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (mPopupWindow != null && mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
				return super.onKeyDown(keyCode, event);
			} else {
				// 创建退出对话框
				AlertDialog isExit = new AlertDialog.Builder(this).create();
				// 设置对话框标题
				isExit.setTitle("系统提示");
				// 设置对话框消息
				isExit.setMessage("确定要放弃编辑吗？");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener);
				isExit.setButton2("取消", listener);
				// 显示对话框
				isExit.show();
			}
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 判断SD卡是否存在
	public static boolean isSdcardExisting() {
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	// 下面两个方法用于对图片进行压缩
	private Bitmap setScaleBitmap(Bitmap photo, int SCALE) {
		if (photo != null) {
			// 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
			// 这里缩小了1/2,但图片过大时仍然会出现加载不了,但系统中一个BITMAP最大是在10M左右,我们可以根据BITMAP的大小
			// 根据当前的比例缩小,即如果当前是15M,那如果定缩小后是6M,那么SCALE= 15/6
			Bitmap smallBitmap = zoomBitmap(photo, photo.getWidth() / SCALE,
					photo.getHeight() / SCALE);
			// 释放原始图片占用的内存，防止out of memory异常发生
			photo.recycle();
			return smallBitmap;
		}
		return null;
	}

	public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}
}
