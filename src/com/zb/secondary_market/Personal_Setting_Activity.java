package com.zb.secondary_market;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.MyRectangle;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.exit.ActivityManager;
import com.zb.secondary_market.imageblur.BlurUtil;
import com.zb.secondary_market.imagecache.ImageCache.ImageCacheParams;
import com.zb.secondary_market.imessage.IMClient;
import com.zb.secondary_market.register.HttpPostFile;
import com.zb.secondary_market.service.ImessageService;
import com.zb.secondary_market.update.UpdateInfo;
import com.zb.secondary_market.update.UpdateInfoParser;
import com.zb.secondary_market.update.UpdateManager;

public class Personal_Setting_Activity extends Activity implements
		OnClickListener {
	private static final String TAG = "Personal_Setting_Activity";
	private static final String IMAGE_CACHE_DIR = "thumbs";
	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/xiaoyuanzhushou_headphoto/";

	private static int RESULT_LOAD_IMAGE = 0;
	private static int CAMERA_REQUEST_CODE = 1;

	private String picPath = null;

	private IMClient client;

	private LinearLayout personal_info_background;
	private RelativeLayout my_released, user_complain, my_info, my_setting;
	private Button exitButton;

	private ActivityManager exitM;

	private UpdateTask updateTask;
	private UpdateManager mUpdateManager;

	private String UpdateNo = "当前为最新版本，无需升级！";
	private String UpdateYes = "发现新版本，即将升级！";
	private String update_url = "http://202.38.73.228/ustc_helper/update.xml";

	private int tabheight = 0;
	private Uri photoUri = null;

	private TextView nickname, vid;
	private TextView gps_school;
	private TitleView mTitle;
	// private RelativeLayout me_help_setting;
	private RelativeLayout me_aboutus_about, me_aboutus_update,
			me_aboutus_clear;
	private RelativeLayout me_help_gpsschool;

	private TextView me_aboutus_clear_text;
	private LinearLayout gps_school_ll;

	private Bitmap bitmap1;
	private boolean isregister;

	private PopupWindow pop;
	private LinearLayout ll_pop;

	private AsynImageLoader asynImageLoader;
	private CircularImageView cover_user_photo;

	private String requestString, nicknameString, passwordString,
			gps_schoolString;

	private Intent intent = new Intent();

	private static final int MB = 1024 * 1024;

	private String cache;

	private LocationClient locationClient = null;
	private static final int UPDATE_TIME = 5000;
	private static int LOCATION_COUTNS = 0;
	private static boolean flag = false;

	private View parentView;

	private String school_name_locatedString = "default";

	private String request_vid;
	private String request_imageurl;

	private boolean isheadphoto_changed = false;
	
	private MyServiceConnection conn;
	private ImessageService.MyBinder myBinder;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		/*
		 * case R.id.id_personal_setting_manage_ll_ll1:
		 * intent.setClass(Personal_Setting_Activity.this,
		 * Secondary_Me_Activity.class); startActivity(intent);
		 * 
		 * break;
		 * 
		 * case R.id.id_personal_setting_manage_ll_ll2:
		 * intent.setClass(Personal_Setting_Activity.this,
		 * Friend_Me_Activity.class); startActivity(intent);
		 * 
		 * break;
		 * 
		 * case R.id.id_personal_setting_manage_ll_ll3:
		 * intent.setClass(Personal_Setting_Activity.this,
		 * Campaigner_Me_Activity.class); startActivity(intent);
		 * 
		 * break;
		 */

		case R.id.id_personal_setting_help_ll_ll111:
			Intent intent_released = new Intent();
			intent_released.setClass(Personal_Setting_Activity.this,
					My_Released_Activity.class);
			startActivity(intent_released);
			break;

		case R.id.id_personal_setting_help_ll_ll1:
			if (gps_schoolString.equals("null")) {

				// 创建启动GPS定位对话框
				AlertDialog isExit = new AlertDialog.Builder(this).create();
				// 设置对话框标题
				isExit.setTitle("系统提示");
				// 设置对话框消息
				isExit.setMessage("为了保障您和其他用户的权益，我们希望启动GPS定位您所在的学校位置");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener_gps);
				isExit.setButton2("取消", listener_gps);
				// 显示对话框
				isExit.show();
			} else {
				Toast.makeText(Personal_Setting_Activity.this,
						"您已经完成了校区认证，不需要重新认证", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.id_personal_setting_help_ll_ll2:
			Intent intent_message = new Intent();
			intent_message.putExtra("intent_type", "normal");
			intent_message.setClass(Personal_Setting_Activity.this,
					My_Message_Activity.class);
			startActivity(intent_message);
			break;

		case R.id.id_personal_setting_aboutus_ll_ll2:
			ClearTask clearTask = new ClearTask();
			clearTask.execute(cache);
			break;

		case R.id.id_personal_setting_aboutus_ll_ll3:
			Intent intent_about = new Intent();
			intent_about.setClass(Personal_Setting_Activity.this,
					AboutUs_Activity.class);
			startActivity(intent_about);
			break;

		case R.id.id_personal_setting_aboutus_ll_ll1:
			if (updateTask != null
					&& updateTask.getStatus() == AsyncTask.Status.RUNNING) {
				updateTask.cancel(true);
			}
			updateTask = new UpdateTask();
			updateTask.execute();
			break;

		/*
		 * case R.id.id_personal_setting_help_ll_ll3: Intent intent_help_setting
		 * = new Intent(); break;
		 */

		case R.id.id_personal_setting_exit_btn:

			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("退出登录？");
			// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);
			// 显示对话框
			isExit.show();

			break;

		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		parentView = getLayoutInflater().inflate(
				R.layout.activity_personal_setting, null);

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

		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 12;

		locationClient = new LocationClient(this);
		// 设置定位条件
		LocationClientOption option = new LocationClientOption();

		// option.setAddrType("all");//返回的定位结果包含地址信息
		option.setIsNeedAddress(true);
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms

		option.setOpenGps(true); // 是否打开GPS

		option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
		// option.setPriority(LocationClientOption.NetWorkFirst); //设置定位优先级
		option.setProdName("LocationDemo"); // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setScanSpan(UPDATE_TIME); // 设置定时定位的时间间隔。单位毫秒
		// option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
		locationClient.setLocOption(option);

		// 注册位置监听器
		locationClient.registerLocationListener(new BDLocationListener() {

			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				if (location == null) {
					return;
				}
				StringBuffer sb = new StringBuffer(256);
				sb.append("Time : ");
				sb.append(location.getTime());
				sb.append("\nError code : ");
				sb.append(location.getLocType());
				sb.append("\nLatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nLontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nRadius : ");
				sb.append(location.getRadius());
				sb.append("\nAddress : ");
				sb.append(location.getAddrStr());

				LatLng mylocation = new LatLng(location.getLatitude(), location
						.getLongitude());
				justrec(mylocation);
				// LatLng a=new LatLng(117.304427,31.850605);

				if (location.getLocType() == BDLocation.TypeGpsLocation) {
					Log.v(TAG, "BDLocation.TypeGpsLocation");
					sb.append("\nSpeed : ");
					sb.append(location.getSpeed());
					sb.append("\nSatellite : ");
					sb.append(location.getSatelliteNumber());

				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
					Log.v(TAG, "BDLocation.TypeNetWorkLocation");
					sb.append("\n省：");
					sb.append(location.getProvince());
					sb.append("\n市：");
					sb.append(location.getCity());
					sb.append("\n区/县：");
					sb.append(location.getDistrict());
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
				} else {
					Log.v(TAG, "////");
				}

				LOCATION_COUTNS++;
				if (LOCATION_COUTNS > 2) {
					locationClient.stop();
				}

				sb.append("\n检查位置更新次数：");
				sb.append(String.valueOf(LOCATION_COUTNS));

				if (flag == true) {
					sb.append("\n学校名称：");
					sb.append("中国科学技术大学");

					school_name_locatedString = "中国科学技术大学";

					// if (school_name_locatedString == "default") {
					// register_infoString = nickName.getText().toString() + "*"
					// + passWord.getText().toString() + "*" + "null";
					// } else {
					Log.v(TAG + "school_name_locatedString",
							school_name_locatedString);
					String register_infoString = request_vid + "*"
							+ nicknameString + "*" + school_name_locatedString;

					String request = HttpPostFile
							.upload_only_gps_school(register_infoString);

					Log.v(TAG + "request", request);
					// }

					if (request.equals("true")) {
						gps_school.setText(school_name_locatedString);
						// gps_school_ll
						// .setBackgroundResource(R.drawable.corner_shape5);

						// 存储对象
						SharedPreferences sharedPreferences = Personal_Setting_Activity.this
								.getSharedPreferences("jonny",
										Context.MODE_PRIVATE); // 私有数据
						Editor editor = sharedPreferences.edit();// 获取编辑器
						editor.putString("gps_school",
								school_name_locatedString);
						editor.commit();// 提交修改
					}

				}

				// locationInfoTextView.setText(sb.toString());

			}

			// @Override
			// public void onReceivePoi(BDLocation location) {
			// }

		});

		bindServices();
		
		init_RL();

		personal_info_background = (LinearLayout) findViewById(R.id.id_personal_setting_info_ll);
		nickname = (TextView) findViewById(R.id.id_personal_setting_info_ll_ll1_nickname);
		vid = (TextView) findViewById(R.id.id_personal_setting_info_ll_ll1_id);
		gps_school = (TextView) findViewById(R.id.id_personal_setting_info_ll_ll1_gps_school);
		gps_school_ll = (LinearLayout) findViewById(R.id.id_personal_setting_info_ll_ll1_gps_ll);

		setBackground(R.drawable.personal_setting_background1);

		// 获取对象
		SharedPreferences share = getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		// isregister_test = share.getBoolean("isregister", false);
		requestString = share.getString("request", null);
		nicknameString = share.getString("nickname", null);
		passwordString = share.getString("password", null);
		gps_schoolString = share.getString("gps_school", null);

		// Log.v(TAG, isregister + "");
		// Log.v(TAG, isregister_test + "");

		request_vid = requestString.split("@@")[0];
		request_imageurl = requestString.split("@@")[1];

		Log.v(TAG, request_imageurl);

		/*
		 * Toast.makeText(Personal_Setting_Activity.this, request_vid + "*" +
		 * request_imageurl, Toast.LENGTH_SHORT) .show();
		 */

		// 此处设置nickname和user_id(vid)
		nickname.setText(nicknameString);
		vid.setText(request_vid);

		if (gps_schoolString.equals("null")) {
			gps_school.setText("未认证学校");
			// gps_school_ll.setBackgroundResource(R.drawable.corner_shape55);
		} else {
			gps_school.setText(gps_schoolString);
		}

		Log.v(TAG + "test request_imageurl", request_imageurl);

		// 此处设置我的里面的头像
		asynImageLoader = new AsynImageLoader(nicknameString);
		cover_user_photo = (CircularImageView) findViewById(R.id.id_personal_setting_info_ll_photo);
		asynImageLoader.showImageAsyn(cover_user_photo, request_imageurl,
				R.drawable.login_photo_default);
		// cover_user_photo.setImageResource(R.drawable.sssss);

		cover_user_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_pop.startAnimation(AnimationUtils.loadAnimation(
						Personal_Setting_Activity.this,
						R.anim.activity_translate_in));

				pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			}
		});

		mTitle = (TitleView) findViewById(R.id.id_personal_setting_title);
		// me_help_setting = (RelativeLayout)
		// findViewById(R.id.id_personal_setting_help_ll_ll3);
		me_help_gpsschool = (RelativeLayout) findViewById(R.id.id_personal_setting_help_ll_ll1);
		me_aboutus_about = (RelativeLayout) findViewById(R.id.id_personal_setting_aboutus_ll_ll3);
		me_aboutus_clear = (RelativeLayout) findViewById(R.id.id_personal_setting_aboutus_ll_ll2);
		me_aboutus_update = (RelativeLayout) findViewById(R.id.id_personal_setting_aboutus_ll_ll1);
		me_aboutus_clear_text = (TextView) findViewById(R.id.id_personal_setting_aboutus_ll_ll2_clear);

		// me_help_setting.setOnClickListener(this);
		me_help_gpsschool.setOnClickListener(this);
		me_aboutus_about.setOnClickListener(this);
		me_aboutus_clear.setOnClickListener(this);
		me_aboutus_update.setOnClickListener(this);

		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);
		mTitle.setTitle("个人中心");

		mTitle.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub

						/*
						 * // 存储对象 SharedPreferences sharedPreferences =
						 * Personal_Setting_Activity.this
						 * .getSharedPreferences("jonny", Context.MODE_PRIVATE);
						 * // 私有数据 Editor editor = sharedPreferences.edit();//
						 * 获取编辑器 editor.putString("section_type", "other");
						 * editor.commit();// 提交修改
						 */

						// 存储对象
						SharedPreferences sharedPreferences = Personal_Setting_Activity.this
								.getSharedPreferences("jonny",
										Context.MODE_PRIVATE); // 私有数据
						Editor editor = sharedPreferences.edit();// 获取编辑器
						editor.putString("section_type", "other");
						editor.putBoolean("isintent_onstart", true);

						Log.v(TAG + "isheadphoto_changed", isheadphoto_changed
								+ "");

						if (isheadphoto_changed == true) {
							editor.putBoolean("headphoto_is_changed", true);
						} else {
							editor.putBoolean("headphoto_is_changed", false);
						}
						editor.commit();// 提交修改

						finish();
					}
				});

		ImageCacheParams cacheParams = new ImageCacheParams(
				Personal_Setting_Activity.this, IMAGE_CACHE_DIR);
		cache = cacheParams.diskCacheDir.toString();

		CaculateTask ctask = new CaculateTask();
		ctask.execute(cache);

		initPop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// requestCode == RESULT_LOAD_IMAGE &&

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Log.v(TAG + "requestCode == RESULT_LOAD_IMAGE", "success");
			photoUri = data.getData();
			try {
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(photoUri,
						filePathColumn, null, null, null);
				if (cursor != null) {
					ContentResolver cr = this.getContentResolver();
					int colunm_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(colunm_index);

					cursor.close();
					/***
					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
					 * 这样的话， 我们判断文件的后缀名 如果是图片格式的话，那么才可以
					 */
					// if (path.endsWith("jpg") || path.endsWith("png") ||
					// path.endsWith("JPG") || path.endsWith("PNG")) {
					picPath = path;

					// BitmapFactory.Options options = new
					// BitmapFactory.Options();
					// options.inPreferredConfig = Config.RGB_565;
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap bitmap = BitmapFactory.decodeStream(
							cr.openInputStream(photoUri), null, options);

					// Bitmap bitmap = BitmapFactory.decodeStream(cr
					// .openInputStream(selectedImage), null, options);
					bitmap1 = setScaleBitmap(bitmap, 2 / 1);

					int a = bitmap1.getRowBytes() * bitmap.getHeight();// 获取大小并返回

					// 添加替换头像的代码

					String register_infoString;

					if (gps_schoolString != "default") {
						Log.v(TAG + "school_name_locatedString",
								school_name_locatedString);
						register_infoString = nicknameString + "*"
								+ passwordString + "*" + gps_schoolString;
					} else {
						register_infoString = nicknameString + "*"
								+ passwordString + "*" + "null";
					}

					File file = new File(picPath);
					if (file != null) {
						String request = HttpPostFile.change_headimg(
								register_infoString, file);
						Log.v(TAG, request);

						if (request.equals("true")) {
							cover_user_photo.setImageBitmap(bitmap1);

							File file2 = new File(ALBUM_PATH + nicknameString);
							if (file2.exists()) {
								file2.delete();
							}

							new Thread(saveFileRunnable).start();

							String login_infoString = nicknameString + "*"
									+ passwordString;
							request = HttpPostFile
									.upload_nm_pw(login_infoString);

							Toast.makeText(this, "更换头像成功！", Toast.LENGTH_SHORT)
									.show();

							// 存储对象
							SharedPreferences sharedPreferences = Personal_Setting_Activity.this
									.getSharedPreferences("jonny",
											Context.MODE_PRIVATE); // 私有数据
							Editor editor = sharedPreferences.edit();// 获取编辑器
							editor.putString("request", request);
							editor.commit();// 提交修改

							isheadphoto_changed = true;
						}
					}

					// } else {
					// alert();
					// }
				} else {
					String filePath = photoUri.toString();
					if (filePath.contains("file://")) {
						picPath = filePath.replace("file://", "");
						Log.i(TAG, "imagePath = " + picPath);
						// BitmapFactory.Options options = new
						// BitmapFactory.Options();
						// options.inPreferredConfig = Config.RGB_565;
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 2;
						ContentResolver cr = this.getContentResolver();
						Bitmap bitmap = BitmapFactory.decodeStream(
								cr.openInputStream(photoUri), null, options);

						// Bitmap bitmap = BitmapFactory.decodeStream(cr
						// .openInputStream(selectedImage), null, options);
						Bitmap bitmap1 = setScaleBitmap(bitmap, 2 / 1);

						int a = bitmap1.getRowBytes() * bitmap.getHeight();// 获取大小并返回

						// 添加替换头像的代码

						String register_infoString;

						if (gps_schoolString != "default") {
							Log.v(TAG + "school_name_locatedString",
									school_name_locatedString);
							register_infoString = nicknameString + "*"
									+ passwordString + "*" + gps_schoolString;
						} else {
							register_infoString = nicknameString + "*"
									+ passwordString + "*" + "null";
						}

						File file = new File(picPath);
						if (file != null) {
							String request = HttpPostFile.change_headimg(
									register_infoString, file);
							Log.v(TAG, request);

							if (request.equals("true")) {
								cover_user_photo.setImageBitmap(bitmap1);
								File file2 = new File(ALBUM_PATH
										+ nicknameString);
								if (file2.exists()) {
									file2.delete();
								}

								new Thread(saveFileRunnable).start();

								String login_infoString = nicknameString + "*"
										+ passwordString;
								request = HttpPostFile
										.upload_nm_pw(login_infoString);

								Toast.makeText(this, "更换头像成功！",
										Toast.LENGTH_SHORT).show();

								// 存储对象
								SharedPreferences sharedPreferences = Personal_Setting_Activity.this
										.getSharedPreferences("jonny",
												Context.MODE_PRIVATE); // 私有数据
								Editor editor = sharedPreferences.edit();// 获取编辑器
								editor.putString("request", request);
								editor.commit();// 提交修改

								isheadphoto_changed = true;

							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (requestCode == CAMERA_REQUEST_CODE
				&& resultCode == RESULT_OK) {
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

						// 添加替换头像的代码

						String register_infoString;

						if (gps_schoolString != "default") {
							Log.v(TAG + "school_name_locatedString",
									school_name_locatedString);
							register_infoString = nicknameString + "*"
									+ passwordString + "*" + gps_schoolString;
						} else {
							register_infoString = nicknameString + "*"
									+ passwordString + "*" + "null";
						}

						File file = new File(picPath);
						if (file != null) {
							String request = HttpPostFile.change_headimg(
									register_infoString, file);
							Log.v(TAG, request);

							if (request.equals("true")) {
								cover_user_photo.setImageBitmap(bitmap1);

								File file2 = new File(ALBUM_PATH
										+ nicknameString);
								if (file2.exists()) {
									file2.delete();
								}

								new Thread(saveFileRunnable).start();

								String login_infoString = nicknameString + "*"
										+ passwordString;
								request = HttpPostFile
										.upload_nm_pw(login_infoString);

								Toast.makeText(this, "更换头像成功！",
										Toast.LENGTH_SHORT).show();

								// 存储对象
								SharedPreferences sharedPreferences = Personal_Setting_Activity.this
										.getSharedPreferences("jonny",
												Context.MODE_PRIVATE); // 私有数据
								Editor editor = sharedPreferences.edit();// 获取编辑器
								editor.putString("request", request);
								editor.commit();// 提交修改

								isheadphoto_changed = true;

							}
						}

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

					// 添加替换头像的代码

					String register_infoString;

					if (gps_schoolString != "default") {
						Log.v(TAG + "school_name_locatedString",
								school_name_locatedString);
						register_infoString = nicknameString + "*"
								+ passwordString + "*" + gps_schoolString;
					} else {
						register_infoString = nicknameString + "*"
								+ passwordString + "*" + "null";
					}

					File file = new File(picPath);
					if (file != null) {
						String request = HttpPostFile.change_headimg(
								register_infoString, file);
						Log.v(TAG, request);

						if (request.equals("true")) {
							cover_user_photo.setImageBitmap(bitmap1);

							File file2 = new File(ALBUM_PATH + nicknameString);
							if (file2.exists()) {
								file2.delete();
							}

							new Thread(saveFileRunnable).start();

							String login_infoString = nicknameString + "*"
									+ passwordString;
							request = HttpPostFile
									.upload_nm_pw(login_infoString);

							Toast.makeText(this, "更换头像成功！", Toast.LENGTH_SHORT)
									.show();

							// 存储对象
							SharedPreferences sharedPreferences = Personal_Setting_Activity.this
									.getSharedPreferences("jonny",
											Context.MODE_PRIVATE); // 私有数据
							Editor editor = sharedPreferences.edit();// 获取编辑器
							editor.putString("request", request);
							editor.commit();// 提交修改

							isheadphoto_changed = true;

						}
					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		super.onActivityResult(requestCode, resultCode, data);

		Log.v(TAG, "---onActivityResult");
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener_gps = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序

				Toast.makeText(Personal_Setting_Activity.this,
						"正在获取您当前的位置，请稍等...", Toast.LENGTH_SHORT).show();

				if (locationClient == null) {
					return;
				}
				locationClient.start();
				// justrec();
				/*
				 * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation(
				 * )后，每隔设定的时间，定位SDK就会进行一次定位。如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，
				 * 返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
				 * 定时定位时，调用一次requestLocation，会定时监听到定位结果。
				 */
				locationClient.requestLocation();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				Toast.makeText(Personal_Setting_Activity.this,
						"由于无法获得您所在学校的位置，所以无法完成校区认证，您可以在个人设置中完成认证。",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	class ClearTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			Log.v("ClearTask", "onPreExecute");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			RemoveCache(params[0]);
			RemoveCache(params[0].replace("thumbs", "http"));
			RemoveCache(params[0].replace("thumbs", "thumbs_high"));
			RemoveCache(params[0].replace("thumbs", "thumbs_superhigh"));
			return true;
		}

		@Override
		protected void onPostExecute(Boolean isOK) {
			Log.e("ClearTask", "onPostExecute");
			Toast.makeText(Personal_Setting_Activity.this, "清除缓存成功！",
					Toast.LENGTH_SHORT).show();
			CaculateTask ctask = new CaculateTask();
			ctask.execute(cache);
		}
	}

	/*
	 * public static String CaculateCache(String dirPath) { File dir = new
	 * File(dirPath); File[] files = dir.listFiles(); if (files == null) {
	 * return "0.0 MB"; } int dirSize = 0; for (int i = 0; i < files.length;
	 * i++) { dirSize += files[i].length(); } float cacheSize = dirSize /
	 * (float) MB; float cacheSi = (float) (Math.round(cacheSize * 100)) / 100;
	 * return cacheSi + " MB"; }
	 */

	public static int CaculateCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return 0;
		}
		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			dirSize += files[i].length();
		}
		Log.v("dirSize", "" + dirSize);
		return dirSize;
	}

	class CaculateTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			Log.v("CaculateTask", "onPreExecute");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			int cacheSize1 = CaculateCache(params[0]);
			int cacheSize2 = CaculateCache(params[0].replace("thumbs", "http"));
			int cacheSize3 = CaculateCache(params[0].replace("thumbs",
					"thumbs_high"));
			int cacheSize4 = CaculateCache(params[0].replace("thumbs",
					"thumbs_superhigh"));
			int cache1 = cacheSize1 + cacheSize2 + cacheSize3 + cacheSize4;
			float cacheSize = cache1 / (float) MB;
			float cacheSi = (float) (Math.round(cacheSize * 100)) / 100;
			return cacheSi + "MB";
		}

		@Override
		protected void onPostExecute(String cacheSize) {
			Log.e("CaculateTask", "onPostExecute");
			me_aboutus_clear_text.setText("清除缓存(" + cacheSize + ")");
		}
	}

	class UpdateTask extends AsyncTask<Void, Integer, UpdateInfo> {
		@Override
		protected void onPreExecute() {
			Log.v("UpdateTask", "onPreExecute");
		}

		@Override
		protected UpdateInfo doInBackground(Void... params) {
			// TODO Auto-generated method stub
			UpdateInfo updateInfo = new UpdateInfo();
			UpdateInfoParser updateInfoParser = new UpdateInfoParser();
			updateInfo = updateInfoParser.getUpdateInfo(update_url);

			Log.v(TAG + "updateInfo", updateInfo.getUrl());

			return updateInfo;
		}

		@Override
		protected void onPostExecute(UpdateInfo updateInfo) {
			Log.e("UpdateTask", "onPostExecute");
			UpdateInfoHandler(updateInfo);
		}
	}

	public void UpdateInfoHandler(UpdateInfo updateInfo) {
		try {
			if ((updateInfo != null)
					&& (!updateInfo.getVersionName().equals(getVersionName()))) {
				Toast.makeText(this, UpdateYes, Toast.LENGTH_SHORT).show();
				mUpdateManager = new UpdateManager(this);
				mUpdateManager.setUrl(updateInfo.getUrl(),
						updateInfo.getDescription());
				mUpdateManager.checkUpdateInfo();
			} else {
				Toast.makeText(this, UpdateNo, Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void RemoveCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files != null) {
			int removeFactor = (int) ((files.length));
			for (int i = 0; i < removeFactor; i++) {
				files[i].delete();
			}
		}
	}

	private String getVersionName() throws Exception {
		PackageManager packageManager = Personal_Setting_Activity.this
				.getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(
				Personal_Setting_Activity.this.getPackageName(), 0);
		return packInfo.versionName;
	}

	public void justrec(LatLng a) {
		// Rectangle rec=new Rectangle();
		// 科大
		MyRectangle rec = new MyRectangle(31.847580, 117.257375, 0.021963,
				0.011197);
		// 工大
		// MyRectangle rec=new
		// MyRectangle(31.852288,117.297526,0.010133,0.009210);
		// LatLng a=new LatLng(117.304427,31.850605);

		flag = rec.isInside(a.longitude, a.latitude);

		// Toast.makeText(this, "/////" + flag, Toast.LENGTH_SHORT).show();
		// Toast.makeText(this, "/////" + a.longitude + "****" + a.latitude,
		// Toast.LENGTH_SHORT).show();

	}

	private void initPop() {
		// TODO Auto-generated method stub
		pop = new PopupWindow(Personal_Setting_Activity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_pop = (LinearLayout) view.findViewById(R.id.ll_popup);

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
				ll_pop.clearAnimation();
			}
		});

		// 这是拍照上传按钮（暂时未做）

		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				quizCamHandler();
				pop.dismiss();
				ll_pop.clearAnimation();
			}
		});

		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*
				 * Log.v(TAG + "add photo", "是否点击了添加图片的GridView"); Intent intent
				 * = new Intent(Register_Activity.this,
				 * AlbumSecondaryActivity.class);
				 * 
				 * intent.putExtra("section_type", "secondary");
				 * 
				 * startActivity(intent);
				 */
				// finish();

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
				startActivityForResult(i, RESULT_LOAD_IMAGE);

				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_pop.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_pop.clearAnimation();
			}
		});
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
			Toast.makeText(Personal_Setting_Activity.this, "请插入存储卡！",
					Toast.LENGTH_SHORT).show();
		}
	}

	//绑定服务
	private void bindServices() {
		// TODO Auto-generated method stub
		// 开启服务
		Intent service = new Intent(Personal_Setting_Activity.this,
		ImessageService.class);
		conn = new MyServiceConnection();
		// startService(service)只能单纯的开启一个服务，要想调用服务服务中的方法，必须用bindService和unbindService
		// startService(service);
		bindService(service, conn, Context.BIND_AUTO_CREATE);
		}
	
		/**
		* 自定义一个类，实现ServiceConnection接口，并重写其两个方法
		* @author Administrator
		*
		*/
		public class MyServiceConnection implements ServiceConnection
		{
		//当绑定服务成功的时候会调用此方法
		public void onServiceConnected(ComponentName name, IBinder service)
		{
		//得到MyService.MyBinder对象，我们通过这个对象来操作服务中的方法
		myBinder = (ImessageService.MyBinder) service;
		//调用服务中的getname()方法并把值设置到TextView上进行显示
		client = myBinder.getClientInstance();
		}
		public void onServiceDisconnected(ComponentName name){
		}	
	}
	
	// RelativeLayout的初始化
	private void init_RL() {
		// TODO Auto-generated method stub
		// my_release = (RelativeLayout)
		// findViewById(R.id.id_personal_setting_manage_ll_ll1);
		// my_friend = (RelativeLayout)
		// findViewById(R.id.id_personal_setting_manage_ll_ll2);
		// my_activity = (RelativeLayout)
		// findViewById(R.id.id_personal_setting_manage_ll_ll3);
		my_released = (RelativeLayout) findViewById(R.id.id_personal_setting_help_ll_ll111);
		user_complain = (RelativeLayout) findViewById(R.id.id_personal_setting_help_ll_ll1);
		my_info = (RelativeLayout) findViewById(R.id.id_personal_setting_help_ll_ll2);
		// my_setting = (RelativeLayout)
		// findViewById(R.id.id_personal_setting_help_ll_ll3);
		exitButton = (Button) findViewById(R.id.id_personal_setting_exit_btn);

		// my_release.setOnClickListener(this);
		// my_friend.setOnClickListener(this);
		// my_activity.setOnClickListener(this);
		my_released.setOnClickListener(this);
		user_complain.setOnClickListener(this);
		my_info.setOnClickListener(this);
		// my_setting.setOnClickListener(this);
		exitButton.setOnClickListener(this);
	}

	/**
	 * 设置毛玻璃背景
	 * 
	 * @param id
	 *            背景图片id
	 */
	@SuppressWarnings("deprecation")
	private void setBackground(int id) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);// 从资源文件中得到图片，并生成Bitmap图片
		final Bitmap blurBmp = BlurUtil.fastblur(
				Personal_Setting_Activity.this, bmp, 25);// 0-25，表示模糊值
		final Drawable newBitmapDrawable = new BitmapDrawable(blurBmp); // 将Bitmap转换为Drawable
		personal_info_background.post(new Runnable() // 调用UI线程
				{
					@Override
					public void run() {
						personal_info_background
								.setBackgroundDrawable(newBitmapDrawable);// 设置背景
					}
				});
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

	private void alert() {
		Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
				.setMessage("您选择的不是有效的图片")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						picPath = null;
					}
				}).create();
		dialog.show();
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

	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */

	private Runnable saveFileRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				saveFile(bitmap1, nicknameString);
				Log.v(TAG + "notification", "图片保存成功");
			} catch (IOException e) {
				Log.v(TAG + "notification", "图片保存失败");
				e.printStackTrace();
			}
		}

	};

	public void saveFile(Bitmap bm, String fileName) throws IOException {
		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ALBUM_PATH + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				// finish();

//				client = ImessageService.getClientInstance();

//				if (client != null)
//					client.Logout();
//				client = new IMClient(nicknameString, passwordString);
				
				isregister = false;
				// 存储对象
				SharedPreferences sharedPreferences = Personal_Setting_Activity.this
						.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
				Editor editor = sharedPreferences.edit();// 获取编辑器
				editor.putBoolean("isregister", isregister);
				editor.commit();// 提交修改

				intent.setClass(Personal_Setting_Activity.this,
						Login_Activity.class);
				startActivity(intent);

				if (client != null) {
					Log.v(TAG + "client != null", "yes");
					Log.v(TAG + "client.getUsername()", client.getUsername());
					/*
					 * try { client.login(); } catch (Exception e) { // TODO: handle
					 * exception }
					 */
					client.Logout();
					Log.v(TAG + "client != null", "yesnoyesno");
				}else{
					Log.v(TAG + "client == null", "no");
				}
				finish();
				
				// 此处关闭Service
				Intent intent = new Intent(Personal_Setting_Activity.this, ImessageService.class);
				stopService(intent);
				
				exitM = ActivityManager.getInstance();
				exitM.exit();

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

			/*
			 * // 存储对象 SharedPreferences sharedPreferences =
			 * Personal_Setting_Activity.this .getSharedPreferences("jonny",
			 * Context.MODE_PRIVATE); // 私有数据 Editor editor =
			 * sharedPreferences.edit();// 获取编辑器
			 * editor.putString("section_type", "other"); editor.commit();//
			 * 提交修改
			 */

			// 存储对象
			SharedPreferences sharedPreferences = Personal_Setting_Activity.this
					.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
			Editor editor = sharedPreferences.edit();// 获取编辑器
			editor.putString("section_type", "other");
			editor.putBoolean("isintent_onstart", true);
			if (isheadphoto_changed == true) {
				editor.putBoolean("headphoto_is_changed", true);
			} else {
				editor.putBoolean("headphoto_is_changed", false);
			}
			editor.commit();// 提交修改

			finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
