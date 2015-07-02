package com.zb.secondary_market;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.MyProgressBar;
import com.zb.secondary_market.exit.ActivityManager;
import com.zb.secondary_market.imageblur.BlurUtil;
import com.zb.secondary_market.imessage.IMClient;
import com.zb.secondary_market.register.HttpPostFile;
import com.zb.secondary_market.service.ImessageService;

public class Login_Activity extends Activity {
	private static final String TAG = "Login_Activity";

	private LocationClient locationClient = null;
	private static final int UPDATE_TIME = 5000;
	private static int LOCATION_COUTNS = 0;
	private static boolean flag = false;

	private Button loginButton, registerButton;

	// private PopupWindow mPopupWindow;

	private LinearLayout login_ll;

	private EditText nickName, passWord;
	private ImageView closeImageView;
	private CircularImageView photoImage;

	// private int photoImage_id = 0;
	private String nicknameString, passwordString;
	private String nickname, password;
	private String head;

	private boolean isLogin, isRegister;

	private ProgressDialog mDialog;

	private ArrayList<NameValuePair> params;

	private SharedPreferences sp;

	private ActivityManager exitM;

	private AsynImageLoader asynImageLoader;

	private LoginTask loginTask = null;
//	private static IMClient client = null;

	private String school_name_locatedString = "default";

	private ImessageService myservice = null;// 绑定的service对象

	private String request;
	
	private Dialog loadingDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_login);

		/*// 开始绑定
		Intent intent = new Intent(Login_Activity.this, ImessageService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);*/

		// Intent intent = new Intent();
		// intent.getStringExtra("image_id");
		// nicknameString = intent.getStringExtra("user_id");
		// passwordString = intent.getStringExtra("password");

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

		exitM = ActivityManager.getInstance();
		exitM.addActivity(Login_Activity.this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initView();

		// 获取对象
		SharedPreferences share = getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		nicknameString = share.getString("nickname", null);
		passwordString = share.getString("password", null);
		isRegister = share.getBoolean("isregister", false);
		// photoImage_id = share.getInt("image_id", 0);

		Log.v(TAG + "nickname + password", nicknameString + passwordString);
		Log.v(TAG, "---onStart");

		// if(passWord.isFocused()){
		// Toast.makeText(Login_Activity.this, "Fuck you",
		// Toast.LENGTH_SHORT).show();
		// }

		if (nicknameString != null) {
			nickName.setText(nicknameString);

			String headphoto_url = HttpPostFile.upload_chat(nicknameString);

			asynImageLoader = new AsynImageLoader(nicknameString);

			// 此处设置我的里面的头像
			CircularImageView cover_user_photo = (CircularImageView) findViewById(R.id.id_login_photo);
			asynImageLoader.showImageAsyn(cover_user_photo, headphoto_url,
					R.drawable.login_photo_default);

		}
		passWord.requestFocus();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(TAG, "onResume");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.stop();
			locationClient = null;
		}
	}

//	// 连接对象，重写OnserviceDisconnected和OnserviceConnected方法
//	public ServiceConnection conn = new ServiceConnection() {
//
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			Log.i(TAG, "onServiceDisconnected>>>>>>>>");
//			myservice = null;
//		}
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			Log.i(TAG, "onServiceConnected>>>>>>>>");
//			myservice = ((ImessageService.MyBinder) service).getService();
//			Log.i(TAG, myservice + ">>>>>>>>");
//		}
//	};

	/*
	 * //登录 private void login(){
	 * Log.i(TAG,"<<<<<--------login()---------->>>>>"); user_idString =
	 * nickName.getText().toString(); passwordString =
	 * passWord.getText().toString();
	 * 
	 * if(user_idString==null||user_idString.equals("")||passwordString==null||
	 * passwordString.equals("")){//||login_pwd==null||login_pwd.equals("")
	 * Toast.makeText(getApplicationContext(), "用户名和密码不能为空！请重新输入",
	 * Toast.LENGTH_SHORT).show(); }else{ sp.edit().putString("userid",
	 * user_idString).commit(); sp.edit().putString("password",
	 * passwordString).commit(); head = "login"; params = new
	 * ArrayList<NameValuePair>(); params.add(new
	 * BasicNameValuePair("userid",user_idString)); params.add(new
	 * BasicNameValuePair("password",passwordString)); mDialog = new
	 * ProgressDialog(Login_Activity.this); mDialog.setTitle("登陆");
	 * mDialog.setMessage("正在登陆服务器，请稍后..."); mDialog.show(); Thread loginThread
	 * = new Thread(){ public void run(){ Message msg = handler.obtainMessage();
	 * String message = GetDataFromServer.getData(head, params);
	 * if(message.equals("network error")){ msg.what = 1; mDialog.dismiss(); }
	 * else{ responseMsg = message; msg.what = 2; } handler.sendMessage(msg); }
	 * }; loginThread.start(); } }
	 */

	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { super.onActivityResult(requestCode,
	 * resultCode, data);
	 * 
	 * if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null
	 * != data) { Uri selectedImage = data.getData(); String[] filePathColumn =
	 * { MediaStore.Images.Media.DATA };
	 * 
	 * Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
	 * null, null, null); cursor.moveToFirst();
	 * 
	 * int columnIndex = cursor.getColumnIndex(filePathColumn[0]); String
	 * picturePath = cursor.getString(columnIndex); cursor.close();
	 * 
	 * photoImage.setImageBitmap(BitmapFactory.decodeFile(picturePath)); } }
	 */

	/*
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub switch (v.getId()) { case R.id.id_popup_window_signup_ll_photo:
	 * Intent i = new Intent( Intent.ACTION_PICK,
	 * android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//
	 * 调用android的图库 startActivityForResult(i, RESULT_LOAD_IMAGE); break;
	 * 
	 * case R.id.id_popup_window_signup_ll_rl_close: mPopupWindow.dismiss();
	 * default: break; } }
	 */

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class LoginTask extends AsyncTask<Void, Void, Integer> {
		private int success = 0, usr_pwd_err = 1, failure = 2,
				failure_noNickname = 3, failure_noPassword = 4,
				failure_nickname_password_erro = 5, failure_service_erro = 6;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			/*loadingDialog = MyProgressBar.createLoadingDialog(
					Login_Activity.this, "正在登录...");
			loadingDialog.show();*/
			
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			if (!nickName.getText().toString().equals("")
					&& !passWord.getText().toString().equals("")) {
				String login_infoString = nickName.getText().toString() + "*"
						+ passWord.getText().toString();

				Log.v(TAG + "login_infoString", login_infoString);

				request = HttpPostFile.upload_nm_pw(login_infoString);

				Log.v(TAG + "request", request);

				if (request.equals("false")) {
					return failure_nickname_password_erro;

				} else if (request.equals("0")) {
					return failure_service_erro;
				} else {
					/*try {
						if (client != null)
							client.login();
					} catch (KeyManagementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return failure;
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return failure;
					} catch (SmackException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return failure;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return failure;
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						if (e.getMessage().equals(
								"SASLError using DIGEST-MD5: not-authorized")) {
							return usr_pwd_err;
						} else {
							e.printStackTrace();
							return failure;
						}
					}*/
				}
			} else if (nickName.getText().toString().equals("")) {
				Log.v(TAG + "nickName.getText().toString().equals(\"\")",
						"bbbb");
				return failure_noNickname;
			} else if (passWord.getText().toString().equals("")) {
				Log.v(TAG + "passWord.getText().toString().equals(\"\")",
						"cccc");
				return failure_noPassword;
			}
			return success;
		}

		@Override
		protected void onPostExecute(final Integer code) {
			loginTask = null;
			// showProgress(false);

			Log.v(TAG + "code", code + "");

			loadingDialog.dismiss();
			
			if (code == failure) {
				// Toast.makeText(Login_Activity.this,
				// "当前账号已在别的设备登陆，请退出之后再重新登陆",
				// Toast.LENGTH_SHORT).show();
			} else if (code == failure_noNickname) {
				Toast.makeText(Login_Activity.this, "请输入昵称", Toast.LENGTH_SHORT)
						.show();
			} else if (code == failure_noPassword) {
				Toast.makeText(Login_Activity.this, "请输入密码", Toast.LENGTH_SHORT)
						.show();
			} else if (code == failure_nickname_password_erro) {
				Toast.makeText(Login_Activity.this, "Sorry, 您的昵称或密码不正确, 请重新输入",
						Toast.LENGTH_SHORT).show();
			} else if (code == failure_service_erro) {
				Toast.makeText(Login_Activity.this, "Sorry, 服务器暂时没有相应, 请稍后再试 ",
						Toast.LENGTH_SHORT).show();
			} else if (code == success) {
				Log.v(TAG + "success", "你已经正常登录了Message系统");
				// if (!nickName.getText().toString().equals("")
				// && !passWord.getText().toString().equals("")) {
				// Log.v(TAG + "!nickName.getText().toString().equals(\"\")",
				// "aaaa");
				//
				// String login_infoString = nickName.getText().toString() + "*"
				// + passWord.getText().toString();
				//
				// Log.v(TAG + "login_infoString", login_infoString);
				//
				// String request = HttpPostFile.upload_nm_pw(login_infoString);
				//
				// Log.v(TAG + "request", request);
				//
				// if (request.equals("false")) {
				// Toast.makeText(Login_Activity.this,
				// "Sorry, 您的昵称或密码不正确, 请重新输入", Toast.LENGTH_SHORT)
				// .show();
				//
				// } else if (request.equals("0")) {
				// Toast.makeText(Login_Activity.this,
				// "Sorry, 服务器暂时没有相应, 请稍后再试 ", Toast.LENGTH_SHORT)
				// .show();
				// } else {
				Log.v(TAG + "fuck", code + "fuck");
				// if (code == success) {

				Log.v(TAG + "fuck", code + "fuck");
				String vidString = request.split("@@")[0];
				String image_urlString = request.split("@@")[1];
				String gps_schoolString = request.split("@@")[2];

				Log.v(TAG + "sad", request);

				/*
				 * Toast.makeText(Login_Activity.this, request,
				 * Toast.LENGTH_SHORT).show();
				 */
				// 存储对象
				SharedPreferences sharedPreferences = Login_Activity.this
						.getSharedPreferences("jonny", Context.MODE_PRIVATE); // 私有数据
				Editor editor = sharedPreferences.edit();// 获取编辑器
				isRegister = true;
				editor.putBoolean("isregister", isRegister);
				editor.putString("request", request);
				editor.putString("vid", vidString);
				editor.putString("image_url", image_urlString);
				editor.putString("gps_school", gps_schoolString);
				editor.putString("nickname", nickName.getText().toString());
				editor.putString("password", passWord.getText().toString());
				editor.commit();// 提交修改

				Intent intent = new Intent();
				intent.putExtra("request", request);
				intent.setClass(Login_Activity.this, MainActivity.class);
				startActivity(intent);
				finish();
				// }
			}
			// } else if (nickName.getText().toString().equals("")) {
			// Log.v(TAG + "nickName.getText().toString().equals(\"\")",
			// "bbbb");
			// Toast.makeText(Login_Activity.this, "请输入昵称",
			// Toast.LENGTH_SHORT).show();
			// } else if (passWord.getText().toString().equals("")) {
			// Log.v(TAG + "passWord.getText().toString().equals(\"\")",
			// "cccc");
			// Toast.makeText(Login_Activity.this, "请输入密码",
			// Toast.LENGTH_SHORT).show();
			// }
			// }

			/*
			 * if (code == success) { Intent intent = new
			 * Intent(Login_Activity.this, MainActivity.class);
			 * startActivity(intent); } else if (code == usr_pwd_err) {
			 * passWord.requestFocus(); Toast.makeText(Login_Activity.this,
			 * getString(R.string.error_username_or_password),
			 * Toast.LENGTH_SHORT).show(); } else if (code == failure) {
			 * Toast.makeText(Login_Activity.this,
			 * getString(R.string.error_login_general),
			 * Toast.LENGTH_SHORT).show(); }
			 */

		}

		@Override
		protected void onCancelled() {
			loginTask = null;
			// showProgress(false);
		}
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid password, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (loginTask != null) {
			return;
		}
		// Store values at the time of the login attempt.
		nickname = nickName.getText().toString();
		password = passWord.getText().toString();
		SharedPreferences sharedPrefs = getSharedPreferences(
				getString(R.string.imessage_prefs), 0);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString("username", nickname);
		editor.putString("password", password);
		editor.commit();

		boolean cancel = false;
		View focusView = null;

		/*
		 * // Reset errors. nickName.setError(null); passWord.setError(null); //
		 * Check for a valid password. if (TextUtils.isEmpty(password)) {
		 * passWord.setError(getString(R.string.error_field_required));
		 * focusView = passWord; cancel = true; } else if (password.length() <
		 * 6) { passWord.setError(getString(R.string.error_invalid_password));
		 * focusView = passWord; cancel = true; }
		 * 
		 * // Check for a valid username. if (TextUtils.isEmpty(nickname)) {
		 * nickName.setError(getString(R.string.error_field_required));
		 * focusView = nickName; cancel = true; } else if
		 * (nickname.contains("@")) {
		 * nickName.setError(getString(R.string.error_invalid_username));
		 * focusView = nickName; cancel = true; }
		 * 
		 * if (cancel) { // There was an error; don't attempt login and focus
		 * the first // form field with an error. focusView.requestFocus(); }
		 * else { // Show a progress spinner, and kick off a background task to
		 * // perform the user login attempt. //
		 * mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
		 * // showProgress(true);
		 */
//		if (client != null)
//			client.Logout();
//		client = new IMClient(nickname, password);
		loginTask = new LoginTask();
		loginTask.execute((Void) null);
	}

	// }

	public void initView() {
		loginButton = (Button) findViewById(R.id.id_login_btn_ll_login);
		registerButton = (Button) findViewById(R.id.id_login_btn_ll_register);
		nickName = (EditText) findViewById(R.id.id_login_id);
		passWord = (EditText) findViewById(R.id.id_login_password);
		photoImage = (CircularImageView) findViewById(R.id.id_login_photo);
		// setEditTextTypeStyle();

		login_ll = (LinearLayout)findViewById(R.id.id_login_ll);
		
		setBackground(R.drawable.login_backgroud1);
		
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					loadingDialog = MyProgressBar.createLoadingDialog(
							Login_Activity.this, "正在登录...");
					loadingDialog.setCanceledOnTouchOutside(false);
					loadingDialog.show();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				//延迟2秒，为了有足够时间显示"正在登陆"的loadingDialog
				new Handler().postDelayed(new Runnable() {
					public void run() {
						// execute the task
						attemptLogin();
					}
				}, 2000);
			}
		});

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent.setClass(Login_Activity.this, Register_Activity.class);
				startActivity(intent);
			}
		});
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				// finish();

				exitM.exit();

				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

//	public static IMClient getClientInstance() {
////		return client;
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("我们还未开始，就要这样结束了吗？");
			// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);
			// 显示对话框
			isExit.show();
			break;
		}
		return super.onKeyDown(keyCode, event);
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
				Login_Activity.this, bmp, 25);// 0-25，表示模糊值
		final Drawable newBitmapDrawable = new BitmapDrawable(blurBmp); // 将Bitmap转换为Drawable
		login_ll.post(new Runnable() // 调用UI线程
				{
					@Override
					public void run() {
						login_ll
								.setBackgroundDrawable(newBitmapDrawable);// 设置背景
					}
				});
	}

}
