package com.zb.secondary_market;

import java.io.File;
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
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.MyProgressBar;
import com.zb.secondary_market.custom.MyRectangle;
import com.zb.secondary_market.exit.ActivityManager;
import com.zb.secondary_market.imessage.IMClient;
import com.zb.secondary_market.register.HttpPostFile;

public class Register_Activity extends Activity implements OnClickListener {
	private static final String TAG = "Register_Activity";

	private static int RESULT_LOAD_IMAGE = 0;
	private static int CAMERA_REQUEST_CODE = 1;

	private EditText nickName, passWord, passWord_Verify;
	// private Spinner universitySpinner;
	private CircularImageView photoImage;
	private Button registerButton;
	private ImageView closeImageView;
	private PopupWindow pop;
	private LinearLayout ll_pop;
	private View parentView;

	private Uri photoUri = null;

	private int photoImage_id = 0;
	// private boolean isfirst_login;
	private boolean isregister;

	private String macInfo, sim, phoneType, phoneNum;
	private String nicknameString, passwordString;
	private String nickname, password;
	private String head;

	private String picPath = null;
	private String register_infoString = null;
	// private String requestURL = "";

	private ArrayList<NameValuePair> params;

	private SharedPreferences sp;

	private ActivityManager exitM;

	private Dialog loadingDialog = null;

	private LocationClient locationClient = null;
	private static final int UPDATE_TIME = 5000;
	private static int LOCATION_COUTNS = 0;
	private static boolean flag = false;

	private String school_name_locatedString = "default";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		parentView = getLayoutInflater().inflate(R.layout.activity_register,
				null);

		setContentView(parentView);

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
				}

				// locationInfoTextView.setText(sb.toString());

			}

			// @Override
			// public void onReceivePoi(BDLocation location) {
			// }

		});

		exitM = ActivityManager.getInstance();
		exitM.addActivity(Register_Activity.this);

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

		nickName = (EditText) findViewById(R.id.id_register_ll_ll1_edit);
		passWord = (EditText) findViewById(R.id.id_register_ll_ll2_edit);
		passWord_Verify = (EditText) findViewById(R.id.id_register_ll_ll3_edit);
		// universitySpinner = (Spinner)
		// findViewById(R.id.id_register_ll_spinner);
		photoImage = (CircularImageView) findViewById(R.id.id_register_ll_photo);
		registerButton = (Button) findViewById(R.id.id_register_ll_registerBtn);
		closeImageView = (ImageView) findViewById(R.id.id_register_ll_rl_close);

		// CircularImageView cover_user_photo = (CircularImageView)
		// findViewById(R.id.id_register_ll_photo);
		// cover_user_photo.setImageResource(R.drawable.photo_default);

		photoImage.setOnClickListener(this);
		closeImageView.setOnClickListener(this);
		registerButton.setOnClickListener(this);

		initDialog();
		initPop();
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener_gps = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序

				Toast.makeText(Register_Activity.this, "正在获取您当前的位置，请稍等...",
						Toast.LENGTH_SHORT).show();
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
				Toast.makeText(Register_Activity.this,
						"由于无法获得您所在学校的位置，所以无法完成校区认证，您可以在个人设置中完成认证。",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

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

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// requestCode == RESULT_LOAD_IMAGE &&

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Log.v(TAG + "requestCode == RESULT_LOAD_IMAGE", "success");
			photoUri = data.getData();

			if (photoUri != null) {
				Log.v("photoUri", photoUri.toString());
			} else {

			}

			try {
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Log.v(TAG + "filePathColum.length", filePathColumn.length + "");

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
					if (path.endsWith("jpg") || path.endsWith("png")) {
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
						Bitmap bitmap1 = setScaleBitmap(bitmap, 2 / 1);

						int a = bitmap1.getRowBytes() * bitmap.getHeight();// 获取大小并返回

						photoImage.setImageBitmap(bitmap1);
					} else {
						alert();
					}
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

						photoImage.setImageBitmap(bitmap1);
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
						photoImage.setImageBitmap(bitmap1);
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

					// int a = bitmap1.getRowBytes() * bitmap.getHeight();//
					// 获取大小并返回
					photoImage.setImageBitmap(bitmap1);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		super.onActivityResult(requestCode, resultCode, data);

		Log.v(TAG, "---onActivityResult");
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

	RegisterTask registerTask = null;
	static IMClient client = null;

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class RegisterTask extends AsyncTask<Void, Void, Integer> {
		private int success = 0, usr_dup_err = 1, failure = 2;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			try {
				if (client != null)
					client.register();
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
				if (e.getMessage().equals("XMPPError: conflict - cancel"))
					return usr_dup_err;
				else {
					e.printStackTrace();
					return failure;
				}
			}

			return success;
		}

		@Override
		protected void onPostExecute(final Integer code) {
			registerTask = null;
			// showProgress(false);

			try {
				loadingDialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (code == success) {
				Toast.makeText(Register_Activity.this, "注册成功",
						Toast.LENGTH_SHORT).show();
			} else if (code == usr_dup_err) {
				// mUsernameView.requestFocus();
				// mUsernameView
				// .setError(getString(""));
			}
		}

		@Override
		protected void onCancelled() {
			registerTask = null;
			// showProgress(false);
		}
	}

	public void attemptRegister() {
		if (registerTask != null) {
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

		// Reset errors.
		// nickName.setError(null);
		// passWord.setError(null);
		// Check for a valid password.
		// if (TextUtils.isEmpty(password)) {
		// passWord.setError(getString(R.string.error_field_required));
		// focusView = passWord;
		// cancel = true;
		// } else if (password.length() < 6) {
		// passWord.setError(getString(R.string.error_invalid_password));
		// focusView = passWord;
		// cancel = true;
		// }
		//
		// // Check for a valid username.
		// if (TextUtils.isEmpty(nickname)) {
		// nickName.setError(getString(R.string.error_field_required));
		// focusView = nickName;
		// cancel = true;
		// } else if (nickname.contains("@")) {
		// nickName.setError(getString(R.string.error_invalid_username));
		// focusView = nickName;
		// cancel = true;
		// }
		//
		// if (cancel) {
		// // There was an error; don't attempt login and focus the first
		// // form field with an error.
		// focusView.requestFocus();
		// } else {
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		// mLoginStatusMessageView
		// .setText(R.string.login_progress_registering);
		// showProgress(true);
		client = new IMClient(nickname, password);
		registerTask = new RegisterTask();
		registerTask.execute((Void) null);
		// }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_register_ll_photo:
			/*
			 * Intent i = new Intent( Intent.ACTION_PICK,
			 * android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//
			 * 调用android的图库 startActivityForResult(i, RESULT_LOAD_IMAGE);
			 */
			ll_pop.startAnimation(AnimationUtils.loadAnimation(
					Register_Activity.this, R.anim.activity_translate_in));

			// 隐藏软键盘
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(Register_Activity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);

			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			break;

		case R.id.id_register_ll_rl_close:
			finish();
			break;

		case R.id.id_register_ll_registerBtn:
			/*
			 * if (nickName.getText() != null && passWord.getText() != null &&
			 * passWord_Verify.getText() != null &&
			 * universitySpinner.getSelectedItem().toString() != null)
			 */

			try {
				loadingDialog = MyProgressBar
						.createLoadingDialog(
								Register_Activity.this,
								"正在注册...");
				loadingDialog
						.setCanceledOnTouchOutside(false);
				loadingDialog.show();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			if (picPath != null) {
				if (!nickName.getText().toString().equals("")) {
					if (!passWord.getText().toString().equals("")) {
						if (!passWord_Verify.getText().toString().equals("")) {
							if (passWord
									.getText()
									.toString()
									.equals(passWord_Verify.getText()
											.toString())) {
								
								//
								new Handler().postDelayed(new Runnable() {
									public void run() {
										// execute the task

										String register_infoString;

										if (school_name_locatedString == "default") {
											register_infoString = nickName
													.getText().toString()
													+ "*"
													+ passWord.getText()
															.toString()
													+ "*"
													+ "null";
										} else {
											Log.v(TAG
													+ "school_name_locatedString",
													school_name_locatedString);
											register_infoString = nickName
													.getText().toString()
													+ "*"
													+ passWord.getText()
															.toString()
													+ "*"
													+ school_name_locatedString;
										}

										Log.v(TAG + "register_infoString",
												register_infoString);

										File file = new File(picPath);
										if (file != null) {
											String request = HttpPostFile
													.uploadFile(
															register_infoString,
															file);
											Log.v(TAG, request);

											if (request.equals("true")) {
												//
												nicknameString = nickName
														.getText().toString();
												passwordString = passWord
														.getText().toString();
												//
												Log.v(TAG + "user + pass",
														nicknameString
																+ passwordString);

												attemptRegister();

												Intent intent = new Intent();

												// intent.putExtra("image_id",
												// photoImage_id);
												// intent.putExtra("user_id",
												// nicknameString);
												// intent.putExtra("password",
												// passwordString);

												intent.setClass(
														Register_Activity.this,
														Login_Activity.class);

												// 存储对象
												SharedPreferences sharedPreferences = Register_Activity.this
														.getSharedPreferences(
																"jonny",
																Context.MODE_PRIVATE); // 私有数据
												Editor editor = sharedPreferences
														.edit();// 获取编辑器
												isregister = true;
												editor.putBoolean("isregister",
														isregister);
												editor.putString("nickname",
														nicknameString);
												editor.putString("password",
														passwordString);
												editor.commit();// 提交修改

												startActivity(intent);

												finish();
											} else if (request.equals("false")) {
												Toast.makeText(
														Register_Activity.this,
														"Sorry, 该昵称已被注册, 请重新输入",
														Toast.LENGTH_SHORT)
														.show();
											} else if (request.equals("0")) {
												Toast.makeText(
														Register_Activity.this,
														"Sorry, 服务器暂时没有相应, 请稍后再试 ",
														Toast.LENGTH_SHORT)
														.show();
											}
										}

									}
								}, 2000);

							} else {
								Toast.makeText(Register_Activity.this,
										"两次输入密码不同，请重新输入", Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							Toast.makeText(Register_Activity.this, "请再次输入密码",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(Register_Activity.this, "密码不能为空",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(Register_Activity.this, "昵称不能为空",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(Register_Activity.this, "为了您更好的使用，请上传一张照片",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	private void initDialog() {
		// TODO Auto-generated method stub
		// 创建启动GPS定位对话框
		AlertDialog isExit = new AlertDialog.Builder(this).create();
		// 设置对话框标题
		isExit.setTitle("系统提示");
		// 调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
		isExit.setCanceledOnTouchOutside(false);
		// 调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
		isExit.setCancelable(false);
		// 设置对话框消息
		isExit.setMessage("为了保障您和其他用户的权益，我们希望启动GPS定位您所在的学校位置");
		// 添加选择按钮并注册监听
		isExit.setButton("确定", listener_gps);
		isExit.setButton2("取消", listener_gps);
		// 显示对话框
		isExit.show();
	}

	private void initPop() {
		// TODO Auto-generated method stub
		pop = new PopupWindow(Register_Activity.this);

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
			Toast.makeText(Register_Activity.this, "请插入存储卡！",
					Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * @Override public void onActivityResult(int requestCode, int resultCode,
	 * Intent data){ super.onActivityResult(requestCode, resultCode, data);
	 * Log.e("onActivityResult",requestCode+" "+resultCode); if(resultCode ==
	 * RESULT_OK){ photoStartquiz(requestCode,data); }else if(resultCode ==
	 * QuizActivity.BACK_MESSAGE){
	 * if(updatePointsTask!=null&&updatePointsTask.getStatus
	 * ()==AsyncTask.Status.RUNNING){ updatePointsTask.cancel(true); }
	 * updatePointsTask = new UpdatePointsTask();
	 * updatePointsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); }else
	 * if(resultCode == QuizActivity.QuizOK_BACK_MESSAGE){
	 * updateQuelist.UpdateQueList();
	 * if(updatePointsTask!=null&&updatePointsTask
	 * .getStatus()==AsyncTask.Status.RUNNING){ updatePointsTask.cancel(true); }
	 * updatePointsTask = new UpdatePointsTask();
	 * updatePointsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	 * }else{ return ; } }
	 */

	/*
	 * private void photoStartquiz(int requestCode,Intent data){ String picPath
	 * = ""; if(requestCode==IMAGE_REQUEST_CODE){ if(data == null) {
	 * Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show(); return; }
	 * photoUri = data.getData(); if(photoUri == null ){ Toast.makeText(this,
	 * "选择图片文件出错", Toast.LENGTH_LONG).show(); return; } }else
	 * if(requestCode==CAMERA_REQUEST_CODE){ if (data != null && data.getData()
	 * != null) { photoUri = data.getData(); } } String[] pojo =
	 * {MediaStore.Images.Media.DATA}; Cursor cursor =
	 * getContentResolver().query(photoUri, pojo, null, null,null);
	 * if(cursor.moveToFirst()){ int column_index =
	 * cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); picPath =
	 * cursor.getString(column_index); cursor.close(); } Log.i(TAG,
	 * "imagePath = " + picPath);
	 * 
	 * Intent intent = new Intent(MainActivity.this, QuizActivity.class); Bundle
	 * bundle = new Bundle();
	 * bundle.putSerializable(StartActivity.LOGIN_KEY_SER, loginStuInfo);
	 * bundle.putString("picPath", picPath); bundle.putInt("menuheight",
	 * menuHeight); intent.putExtras(bundle); startActivityForResult(intent, 2);
	 * }
	 */

	public static boolean isSdcardExisting() {
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}
