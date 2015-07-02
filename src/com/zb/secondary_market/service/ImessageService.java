package com.zb.secondary_market.service;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.zb.secondary_market.Campaigner_Imessage_Activity;
import com.zb.secondary_market.Friend_Imessage_Activity;
import com.zb.secondary_market.Login_Activity;
import com.zb.secondary_market.R;
import com.zb.secondary_market.Secondary_Imessage_Activity;
import com.zb.secondary_market.database.DatabaseHelper;
import com.zb.secondary_market.imessage.IMClient;
import com.zb.secondary_market.imessage.IMClient.IMessageListener;

@SuppressWarnings("deprecation")
public class ImessageService extends Service {

	private static final String TAG = "MyService";
	private static Handler handler = new Handler();
	NotificationManager manager;// 通知控制类
	int notification_ID = 1;
	int notification_ID_num = 1;
	private int isfirst = 1;
	private Intent friIntent;
	private String nickname = "";
	private String password = "";
	private static IMClient client;

	private final IBinder binder = new MyBinder();

	private Login_Activity login_Activity = new Login_Activity();

	private List<MyMessage> messages = new ArrayList<MyMessage>();
	// private ChatAdapter chatAdapter =new ChatAdapter();
	private final int MSG_RCV = 1;
	private LoginTask loginTask = null;

	private String msg_type;

	/*@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	@Override
	public IBinder onBind(Intent intent)
	{
	return new MyBinder();
	}

	public class MyBinder extends Binder {
//		public ImessageService getService() {
//			return ImessageService.this;
//		}
		public IMClient getClientInstance() {
			return client;
		}
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// 获取对象
		SharedPreferences share = getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		nickname = share.getString("nickname", null);
		// String requestString = share.getString("request", null);
		// String vidString = share.getString("vid", null);
		password = share.getString("password", null);

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();

	}

	@SuppressLint("HandlerLeak")
	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");

		if (client != null)
			client.Logout();

		// client = login_Activity.getClientInstance();

		client = new IMClient(nickname, password);

		client.setIMessageListener(new IMessageListener() {

			@Override
			public void onMessageReceive(String name, String msg) {
				// TODO Auto-generated method stub
				// messages.add(new MyMessage(name, msg));

				msg_type = msg.split("_")[0];
				String msg_new = msg.split("_")[1];

				messages.add(new MyMessage(name, msg_new));

				// 此处给后台发送信息 添加该用户名的用户信息
				// ******

				if (msg_type.equals("secondary")) {
					// 生成contentvallues对象，该对象用来存数据的
					ContentValues values = new ContentValues();
					// values.put("id", 1);//注意值的类型要匹配
					values.put("send", name);
					values.put("receive", nickname);
					values.put("message", msg_new);
					values.put("datetime", gettime());
					DatabaseHelper database_helper = new DatabaseHelper(
							ImessageService.this, nickname);
					SQLiteDatabase db = database_helper.getWritableDatabase();// 这里是获得可写的数据库
					db.execSQL("create table if not exists "
							+ "secondary_"
							+ nickname
							+ "_"
							+ name
							+ "(_id integer primary key autoincrement,send text not null,receive text not null,message text not null,datetime text not null)");
					db.insert("secondary_" + nickname + "_" + name, null,
							values);

					Cursor c = db.rawQuery("select * from " + "secondary_"
							+ nickname + "_" + name, null);
					if (c != null) {
						String[] cols = c.getColumnNames();
						while (c.moveToNext()) {
							for (String ColumnName : cols) {
								Log.i("info",
										ColumnName
												+ ":"
												+ c.getString(c
														.getColumnIndex(ColumnName)));
							}
						}
						c.close();
					}
					db.close();
				} else if (msg_type.equals("friend")) {
					// 生成contentvallues对象，该对象用来存数据的
					ContentValues values = new ContentValues();
					// values.put("id", 1);//注意值的类型要匹配
					values.put("send", name);
					values.put("receive", nickname);
					values.put("message", msg_new);
					values.put("datetime", gettime());
					DatabaseHelper database_helper = new DatabaseHelper(
							ImessageService.this, nickname);
					SQLiteDatabase db = database_helper.getWritableDatabase();// 这里是获得可写的数据库
					db.execSQL("create table if not exists "
							+ "friend_"
							+ nickname
							+ "_"
							+ name
							+ "(_id integer primary key autoincrement,send text not null,receive text not null,message text not null,datetime text not null)");
					db.insert("friend_" + nickname + "_" + name, null, values);

					Cursor c = db.rawQuery("select * from " + "friend_"
							+ nickname + "_" + name, null);
					if (c != null) {
						String[] cols = c.getColumnNames();
						while (c.moveToNext()) {
							for (String ColumnName : cols) {
								Log.i("info",
										ColumnName
												+ ":"
												+ c.getString(c
														.getColumnIndex(ColumnName)));
							}
						}
						c.close();
					}
					db.close();
				} else if (msg_type.equals("campaigner")) {
					// 生成contentvallues对象，该对象用来存数据的
					ContentValues values = new ContentValues();
					// values.put("id", 1);//注意值的类型要匹配
					values.put("send", name);
					values.put("receive", nickname);
					values.put("message", msg_new);
					values.put("datetime", gettime());
					DatabaseHelper database_helper = new DatabaseHelper(
							ImessageService.this, nickname);
					SQLiteDatabase db = database_helper.getWritableDatabase();// 这里是获得可写的数据库
					db.execSQL("create table if not exists "
							+ "campaigner_"
							+ nickname
							+ "_"
							+ name
							+ "(_id integer primary key autoincrement,send text not null,receive text not null,message text not null,datetime text not null)");
					db.insert("campaigner_" + nickname + "_" + name, null,
							values);

					Cursor c = db.rawQuery("select * from " + "campaigner_"
							+ nickname + "_" + name, null);
					if (c != null) {
						String[] cols = c.getColumnNames();
						while (c.moveToNext()) {
							for (String ColumnName : cols) {
								Log.i("info",
										ColumnName
												+ ":"
												+ c.getString(c
														.getColumnIndex(ColumnName)));
							}
						}
						c.close();
					}
					db.close();
				}
			}
		});

		attempLogin();

		// 开启线程
		MessageThread thread = new MessageThread();
		thread.isRunning = true;
		thread.start();

		super.onStart(intent, startId);

	}

	/**
	 * 构造notification并发送到通知栏
	 */
	private void sendNotification(List<MyMessage> msg) {

		Log.v(TAG + "nickname_other", msg.get(0).getName());

		String msg_nameString = msg.get(0).getName();
		String msg_messageString = msg.get(0).getMessage();

		Builder builder = new Notification.Builder(this);
		builder.setSmallIcon(R.drawable.aaaa);// 设置图标
		// Bitmap icon=BitmapFactory.decodeResource(getResources(),
		// R.drawable.newmessage);
		// builder.setLargeIcon(icon);
		builder.setTicker("收到一条新私信");// 手机状态栏的提示；

		if (msg_type.equals("secondary")) {
			Intent secIntent = new Intent(this,
					Secondary_Imessage_Activity.class);
			secIntent.putExtra("type", "1");
			secIntent.putExtra("nickname_other", msg_nameString);
			secIntent.putExtra("msg_message", msg_messageString);
			PendingIntent pintent = PendingIntent.getActivity(this, 0,
					secIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			builder.setContentIntent(pintent);// 点击后的意图
		} else if (msg_type.equals("friend")) {
			Intent secIntent = new Intent(this, Friend_Imessage_Activity.class);
			secIntent.putExtra("type", "1");
			secIntent.putExtra("nickname_other", msg_nameString);
			secIntent.putExtra("msg_message", msg_messageString);
			PendingIntent pintent = PendingIntent.getActivity(this, 0,
					secIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			builder.setContentIntent(pintent);// 点击后的意图
		} else if (msg_type.equals("campaigner")) {
			Intent secIntent = new Intent(this,
					Campaigner_Imessage_Activity.class);
			secIntent.putExtra("type", "1");
			secIntent.putExtra("nickname_other", msg_nameString);
			secIntent.putExtra("msg_message", msg_messageString);
			PendingIntent pintent = PendingIntent.getActivity(this, 0,
					secIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			builder.setContentIntent(pintent);// 点击后的意图
		}

		// builder.setWhen(System.currentTimeMillis());//设置时间
		// builder.setContentTitle("新私信");//设置标题
		// builder.setContentText("来自二手市场的新的私信通知");//设置通知内容

		builder.setAutoCancel(true);
		// builder.setDefaults(Notification.DEFAULT_SOUND);//设置提示声音
		// builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置指示灯
		// builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动
		builder.setDefaults(Notification.DEFAULT_ALL);// 设置震动
		// Notification notification = builder.build();//4.1以上
		Notification notification = builder.getNotification();
		// notification.flags = Notification.FLAG_ONGOING_EVENT;

		// 自定义notification的显示界面
		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.imessage_service_layout);
		contentView.setImageViewResource(R.id.id_imessage_service_layout_image,
				R.drawable.aaaa);

		String datetime = gettime().split("-")[3];
		contentView.setTextViewText(R.id.id_imessage_service_layout_ll_rl_time,
				datetime);
		// contentView.setTextViewText(
		// R.id.id_imessage_service_layout_ll_ll_content, msg_messageString);
		contentView.setTextViewText(
				R.id.id_imessage_service_layout_ll_ll_content,
				notification_ID_num + "条新消息");
		contentView.setTextViewText(
				R.id.id_imessage_service_layout_ll_rl_title, msg_nameString
						+ " ( " + msg_type + " )");
		notification.contentView = contentView;

		// notification.icon = R.drawable.newmessage;
		manager.notify(notification_ID, notification);
		notification_ID_num++;
	}

	/*
	 * private String gettime() { Calendar c = Calendar.getInstance();
	 * 
	 * String time = formatTime(c.get(Calendar.HOUR_OF_DAY)) + ":" +
	 * formatTime(c.get(Calendar.MINUTE)); // 日 // 秒 System.out.println(time);
	 * return time; // 输出 }
	 */

	// 获得当前时间的方法
	private String gettime() {
		Calendar c = Calendar.getInstance();

		String time = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DAY_OF_MONTH) + "-"
				+ formatTime(c.get(Calendar.HOUR_OF_DAY)) + ":"
				+ formatTime(c.get(Calendar.MINUTE)); // 日
		// 秒
		System.out.println(time);
		return time; // 输出
	}

	private String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
	}

	/***
	 * 从服务端获取消息
	 */
	class MessageThread extends Thread {

		// 运行状态
		public boolean isRunning = true;

		@Override
		public void run() {
			while (isRunning) {
				try {
					// 休息100秒
					Thread.sleep(300);

					/*
					 * if (client != null) client.Logout();
					 * 
					 * client = new IMClient(nickname, password);
					 * 
					 * client.setIMessageListener(new IMessageListener() {
					 * 
					 * @Override public void onMessageReceive(String name,
					 * String msg) { // TODO Auto-generated method stub //
					 * messages.add(new MyMessage(name, msg)); Log.v(TAG +
					 * "onMessageReceive", "执行了"); messages.add(new
					 * MyMessage(name,msg)); } });
					 */

					// sendNotification();
					// List<MyMessage> recMsg=getServerMessage();
					if (messages.size() != 0) {
						// List<MyMessage> recMsg=getServerMessage();
						Log.d(TAG, nickname + password + messages.size());
						sendNotification(messages);

						messages.clear();

					} else {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void attempLogin() {
		// TODO Auto-generated method stub

		if (loginTask != null) {
			return;
		}
		loginTask = new LoginTask();
		loginTask.execute((Void) null);
	}

	public class LoginTask extends AsyncTask<Void, Void, Integer> {
		private int success = 0, usr_pwd_err = 1, failure = 2;

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			try {
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

			}

			return success;
		}

		@Override
		protected void onPostExecute(final Integer code) {
			loginTask = null;
			// showProgress(false);

			if (code == success) {
			}
		}

		@Override
		protected void onCancelled() {
			loginTask = null;
			// showProgress(false);
		}
	}

	/***
	 * 获得服务端的消息message
	 */
	/*
	 * public List<MyMessage> getServerMessage() { if (client != null)
	 * client.Logout();
	 * 
	 * client = new IMClient(nickname, password);
	 * 
	 * client.setIMessageListener(new IMessageListener() {
	 * 
	 * @Override public void onMessageReceive(String name, String msg) { // TODO
	 * Auto-generated method stub // messages.add(new MyMessage(name, msg));
	 * 
	 * String msg_new = msg.split("_")[1];
	 * 
	 * messages.add(new MyMessage(name, msg_new)); } });
	 * 
	 * return messages; }
	 */

	/**
	 * 判断Service是否正在运行
	 * 
	 * @param mContext
	 * @param className
	 * @return
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);

		if (!(serviceList.size() > 0)) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 判断App是否正在运行
	 * 
	 * @param context
	 * @return
	 */
	// Method 1:
	// need permission: <uses-permission
	// android:name="android.permission.GET_TASKS" />
	public static boolean isAppInForeground2(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	// Method 2:
	public static boolean isAppInForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				return appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
			}
		}
		return false;
	}

	/**
	 * 判断Activity是不是正在运行
	 * 
	 * @param mContext
	 * @param activityClassName
	 * @return
	 */
	public static boolean isActivityRunning(Context mContext,
			String activityClassName) {
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> info = activityManager.getRunningTasks(1);
		if (info != null && info.size() > 0) {
			ComponentName component = info.get(0).topActivity;
			if (activityClassName.equals(component.getClassName())) {
				return true;
			}
		}
		return false;
	}

	private class MyMessage implements Serializable {

		private static final long serialVersionUID = -1010711775392052966L;
		public String name;
		public String message;

		public MyMessage(String name, String message) {
			this.name = name;
			this.message = message;
		}

		public String getName() {
			return name;
		}

		public String getMessage() {
			return message;
		}
	}

	public static IMClient getClientInstance() {
		return client;
	}

	// private Handler msgHandler=new Handler(){
	// @Override
	// public void handleMessage(Message msg) {
	// switch(msg.what){
	// case MSG_RCV:
	// chatAdapter.notifyDataSetChanged();
	// break;
	// default:
	// break;
	// }
	// }
	// };
}
