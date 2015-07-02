package com.zb.secondary_market;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.zb.secondary_market.exit.ActivityManager;
import com.zb.secondary_market.otherclasses.Login_Return_Item;

public class Welcome_Activity extends Activity {
	private static final String TAG = "Welcome_Activity";
	private String update_url = "http://202.38.73.228/ustc_helper/update.xml";

	private boolean isregister = true;
	private boolean isfirst = true;
	private String nicknameString;

	// private UpdateTask updateTask;
	// private UpdateManager mUpdateManager;

	private Login_Return_Item login_Return_Item;

	private ActivityManager exitM;

	private boolean isNetworkConnected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_welcome);

		Log.v(TAG, "---onCreate");

		// 获取对象
		SharedPreferences share = getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		isregister = share.getBoolean("isregister", false);
		isfirst = share.getBoolean("isfirst", true);

		// nicknameString = share.getString("nickname", null);
		//
		// if(isregister == true && !nicknameString.equals(null)){
		// String request = HttpPostFile
		// .upload_nm_pw(nicknameString);
		// }

		Log.v(TAG + "isregister", isregister + "");
		Log.v(TAG + "isfirst", isfirst + "");

		exitM = ActivityManager.getInstance();
		exitM.addActivity(Welcome_Activity.this);

		/*
		 * isNetworkConnected = isNetworkConnected(this);
		 * 
		 * if (isNetworkConnected == true) { new Handler().postDelayed(new
		 * Runnable() { public void run() { // execute the task Log.v(TAG,
		 * isfirst + "");
		 * 
		 * if (isfirst == true) { Intent intent = new Intent();
		 * intent.setClass(Welcome_Activity.this, Guide_Activity.class);
		 * 
		 * startActivity(intent);
		 * 
		 * isfirst = false;
		 * 
		 * // 存储对象 SharedPreferences sharedPreferences = Welcome_Activity.this
		 * .getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据 Editor
		 * editor = sharedPreferences.edit();// 获取编辑器
		 * editor.putBoolean("isfirst", isfirst); editor.commit();// 提交修改
		 * 
		 * finish(); } else if (isfirst == false) { if (isregister == true) {
		 * Intent intent = new Intent(); intent.setClass(Welcome_Activity.this,
		 * MainActivity.class);
		 * 
		 * startActivity(intent); finish(); } else if (isregister == false) {
		 * Intent intent = new Intent(); intent.setClass(Welcome_Activity.this,
		 * Login_Activity.class);
		 * 
		 * startActivity(intent); finish(); } }
		 * 
		 * 
		 * if(isfirst == true){ Intent intent = new Intent();
		 * intent.setClass(Welcome_Activity.this, Guide_Activity.class);
		 * 
		 * startActivity(intent);
		 * 
		 * isfirst = false;
		 * 
		 * // 存储对象 SharedPreferences sharedPreferences = Welcome_Activity.this
		 * .getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据 Editor
		 * editor = sharedPreferences.edit();// 获取编辑器
		 * editor.putBoolean("isfirst", isfirst); editor.commit();// 提交修改
		 * 
		 * finish(); }else if(isfirst == false){ if (isregister == true) {
		 * Intent intent = new Intent(); intent.setClass(Welcome_Activity.this,
		 * MainActivity.class);
		 * 
		 * startActivity(intent); finish(); } else if (isregister == false) {
		 * Intent intent = new Intent(); intent.setClass(Welcome_Activity.this,
		 * Login_Activity.class);
		 * 
		 * startActivity(intent); finish(); } }
		 * 
		 * // Intent intent = new Intent(); //
		 * intent.setClass(Welcome_Activity.this, // Login_Activity.class); //
		 * // startActivity(intent); } }, 3000); } else { // 创建退出对话框 AlertDialog
		 * isExit = new AlertDialog.Builder(this).create(); // 设置对话框标题
		 * isExit.setTitle("系统提示"); // 设置对话框消息
		 * isExit.setMessage("当前未检测到网络，请确认网络连接！");
		 * isExit.setCanceledOnTouchOutside(false); // 添加选择按钮并注册监听
		 * isExit.setButton("确定", listener); isExit.setButton2("取消", listener);
		 * // 显示对话框 isExit.show(); }
		 */
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				// finish();

				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				startActivity(intent);

				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				finish();
				break;
			default:
				break;
			}
		}
	};

	/*
	 * class UpdateTask extends AsyncTask<Void, Integer, UpdateInfo> {
	 * 
	 * @Override protected void onPreExecute() { Log.v("UpdateTask",
	 * "onPreExecute"); }
	 * 
	 * @Override protected UpdateInfo doInBackground(Void... params) { // TODO
	 * Auto-generated method stub UpdateInfo updateInfo = new UpdateInfo();
	 * UpdateInfoParser updateInfoParser = new UpdateInfoParser(); updateInfo =
	 * updateInfoParser.getUpdateInfo(update_url);
	 * 
	 * Log.v(TAG + "updateInfo", updateInfo.getUrl());
	 * 
	 * return updateInfo; }
	 * 
	 * @Override protected void onPostExecute(UpdateInfo updateInfo) {
	 * Log.e("UpdateTask", "onPostExecute"); UpdateInfoHandler(updateInfo); } }
	 * 
	 * public void UpdateInfoHandler(UpdateInfo updateInfo) { try { if
	 * ((updateInfo != null) &&
	 * (!updateInfo.getVersionName().equals(getVersionName()))) { //
	 * Toast.makeText(this, UpdateYes, Toast.LENGTH_SHORT).show();
	 * mUpdateManager = new UpdateManager(this);
	 * mUpdateManager.setUrl(updateInfo.getUrl(), updateInfo.getDescription());
	 * mUpdateManager.checkUpdateInfo(); } else { // Toast.makeText(this,
	 * UpdateNo, Toast.LENGTH_SHORT).show(); if (isfirst == true) { Intent
	 * intent = new Intent(); intent.setClass(Welcome_Activity.this,
	 * Guide_Activity.class);
	 * 
	 * startActivity(intent);
	 * 
	 * isfirst = false;
	 * 
	 * // 存储对象 SharedPreferences sharedPreferences = Welcome_Activity.this
	 * .getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据 Editor
	 * editor = sharedPreferences.edit();// 获取编辑器 editor.putBoolean("isfirst",
	 * isfirst); editor.commit();// 提交修改
	 * 
	 * finish(); } else if (isfirst == false) { if (isregister == true) { Intent
	 * intent = new Intent(); intent.setClass(Welcome_Activity.this,
	 * MainActivity.class);
	 * 
	 * startActivity(intent); finish(); } else if (isregister == false) { Intent
	 * intent = new Intent(); intent.setClass(Welcome_Activity.this,
	 * Login_Activity.class);
	 * 
	 * startActivity(intent); finish(); } } } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 * 
	 * private String getVersionName() throws Exception { PackageManager
	 * packageManager = Welcome_Activity.this .getPackageManager(); PackageInfo
	 * packInfo = packageManager.getPackageInfo(
	 * Welcome_Activity.this.getPackageName(), 0); return packInfo.versionName;
	 * }
	 */

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v(TAG, "onStart");

		isNetworkConnected = isNetworkConnected(this);

		if (isNetworkConnected == true) {
			new Handler().postDelayed(new Runnable() {
				public void run() {
					// execute the task
					Log.v(TAG, isfirst + "");

					if (isfirst == true) {
						Intent intent = new Intent();
						intent.setClass(Welcome_Activity.this,
								Guide_Activity.class);

						startActivity(intent);

						isfirst = false;

						// 存储对象
						SharedPreferences sharedPreferences = Welcome_Activity.this
								.getSharedPreferences("jonny",
										Context.MODE_PRIVATE); // 私有数据
						Editor editor = sharedPreferences.edit();// 获取编辑器
						editor.putBoolean("isfirst", isfirst);
						editor.commit();// 提交修改

						finish();
					} else if (isfirst == false) {
						if (isregister == true) {
							Intent intent = new Intent();
							intent.setClass(Welcome_Activity.this,
									MainActivity.class);

							startActivity(intent);
							finish();
						} else if (isregister == false) {
							Intent intent = new Intent();
							intent.setClass(Welcome_Activity.this,
									Login_Activity.class);

							startActivity(intent);
							finish();
						}
					}

					/*
					 * if(isfirst == true){ Intent intent = new Intent();
					 * intent.setClass(Welcome_Activity.this,
					 * Guide_Activity.class);
					 * 
					 * startActivity(intent);
					 * 
					 * isfirst = false;
					 * 
					 * // 存储对象 SharedPreferences sharedPreferences =
					 * Welcome_Activity.this .getSharedPreferences("jonny",
					 * Context.MODE_PRIVATE); // 私有数据 Editor editor =
					 * sharedPreferences.edit();// 获取编辑器
					 * editor.putBoolean("isfirst", isfirst); editor.commit();//
					 * 提交修改
					 * 
					 * finish(); }else if(isfirst == false){ if (isregister ==
					 * true) { Intent intent = new Intent();
					 * intent.setClass(Welcome_Activity.this,
					 * MainActivity.class);
					 * 
					 * startActivity(intent); finish(); } else if (isregister ==
					 * false) { Intent intent = new Intent();
					 * intent.setClass(Welcome_Activity.this,
					 * Login_Activity.class);
					 * 
					 * startActivity(intent); finish(); } }
					 */
					// Intent intent = new Intent();
					// intent.setClass(Welcome_Activity.this,
					// Login_Activity.class);
					//
					// startActivity(intent);
				}
			}, 3000);
		} else {
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("当前未检测到网络，请确认网络连接！");
			isExit.setCanceledOnTouchOutside(false);
			// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);
			// 显示对话框
			isExit.show();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}

	// 判断是否有网络连接
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	// 判断WIFI网络是否可用
	public boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	// 判断MOBILE网络是否可用
	public boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

}
