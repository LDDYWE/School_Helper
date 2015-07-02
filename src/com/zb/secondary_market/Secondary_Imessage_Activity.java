package com.zb.secondary_market;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;
import com.zb.secondary_market.database.DatabaseHelper;
import com.zb.secondary_market.imessage.IMClient;
import com.zb.secondary_market.imessage.IMClient.IMessageListener;
import com.zb.secondary_market.otherclasses.DB_Message_Item;
import com.zb.secondary_market.register.HttpPostFile;
import com.zb.secondary_market.service.ImessageService;
import com.zb.secondary_market.util.VibratorUtil;

public class Secondary_Imessage_Activity extends Activity {
	private static final String TAG = "Secondary_Imessage_Activity";
	public final static int OTHER = 1;
	public final static int ME = 0;

	ArrayList<HashMap<String, Object>> chatList = null;
	String[] from = { "image", "text" };
	int[] to = { R.id.chatlist_image_me, R.id.chatlist_text_me,
			R.id.chatlist_image_other, R.id.chatlist_text_other };
	int[] layout = { R.layout.imessage_item_me, R.layout.imessage_item_other };
	String userQQ = null;

	protected MyChatAdapter adapter = null;

	private LinearLayout linearLayout;

	private TextView helloView;
	// private ListView chatList;
	// private EditText userView;
	// private EditText msgView;
	private IMClient client;
	// private List<MyMessage> messages = new ArrayList<MyMessage>();
	// private ChatAdapter chatAdapter = new ChatAdapter();
	private final int MSG_RCV = 1;

	private LoginTask loginTask = null;

	private String nicknameString, passwordString;
	private String requestString;

	private String nickname_otherString, message_otherString;
	private String request_otherString;

	private TitleView mTitleView;

	private Button chatSendButton;
	private EditText editText;
	private ImageView chat_voice;

	private ListView chatListView;

	private String myWord = null;
	private String request_imageurl;
	private String intent_type;
	private String request_real_otherString;

	private DB_Message_Item db_Message_Item;

	private List<DB_Message_Item> list = new ArrayList<DB_Message_Item>();

	private int tabheight;

	AsynImageLoader asynImageLoader;

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.v(TAG + "onNewIntent", "------");
		super.onNewIntent(intent);
		setIntent(intent);
		// 在这里取数据
		nickname_otherString = getIntent().getStringExtra("nickname_other");
		message_otherString = getIntent().getStringExtra("msg_message");
		intent_type = getIntent().getStringExtra("type");

		Log.v(TAG + "奔跑吧兄弟", nickname_otherString);
		Log.v(TAG + "奔跑吧兄弟", message_otherString);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG + "onCreate", "------");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_imessage);

		Intent intent = new Intent(this, ImessageService.class);
		stopService(intent);

		// 获取对象
		SharedPreferences share = getSharedPreferences("jonny",
				Activity.MODE_WORLD_READABLE);
		nicknameString = share.getString("nickname", null);
		passwordString = share.getString("password", null);
		requestString = share.getString("request", null);

		request_imageurl = requestString.split("@@")[1];

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

		Log.v(TAG + "onCreate", "------");

		nickname_otherString = getIntent().getStringExtra("nickname_other");
		message_otherString = getIntent().getStringExtra("msg_message");
		intent_type = getIntent().getStringExtra("type");

		// request_otherString = getIntent().getStringExtra("request_other");

		// Toast.makeText(this, nickname_otherString,
		// Toast.LENGTH_SHORT).show();

		request_otherString = HttpPostFile.upload_chat(nickname_otherString);

		Log.v(TAG + "奔跑吧兄弟", request_otherString);

		// Log.v(TAG + "request_otherString", request_otherString);

		chatList = new ArrayList<HashMap<String, Object>>();
		// addTextToList("不管你是谁", ME);
		// addTextToList("群发的我不回\n  ^_^", OTHER);
		// addTextToList("哈哈哈哈", ME);
		// addTextToList("新年快乐！", OTHER);

		// 此处判断是否已经存在存放“我”和对方之间通话记录的表
		DatabaseHelper database_helper_contactDatabaseHelper = new DatabaseHelper(
				Secondary_Imessage_Activity.this, nicknameString);
		SQLiteDatabase db_contact = database_helper_contactDatabaseHelper
				.getWritableDatabase();// 这里是获得可写的数据库

		db_contact
				.execSQL("create table if not exists "
						+ "secondary_"
						+ nicknameString
						+ "_"
						+ nickname_otherString
						+ "(_id integer primary key autoincrement,send text not null,receive text not null,message text not null,datetime text not null)");

		Log.v(TAG + "Important", "secondary_" + nicknameString + "_"
				+ nickname_otherString);

		Cursor ccc = db_contact.query("secondary_" + nicknameString + "_"
				+ nickname_otherString, new String[] { "_id", "send",
				"receive", "message", "datetime" }, null, null, null, null,
				"_id asc");

		if (ccc != null) {
			Log.v(TAG + "success", "ccc != null");
			while (ccc.moveToNext()) {
				db_Message_Item = new DB_Message_Item();
				db_Message_Item.idString = ccc.getString(0);
				db_Message_Item.datetimeString = ccc.getString(4);
				db_Message_Item.messageString = ccc.getString(3);
				db_Message_Item.sendString = ccc.getString(1);
				db_Message_Item.receiveString = ccc.getString(2);

				list.add(db_Message_Item);
				// addTextToList(ccc_me.getString(3), ME);
			}
			ccc.close();
		} else {
			Log.v(TAG + "erro", "ccc == null");
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).sendString.equals(nicknameString)) {
				addTextToList(list.get(i).messageString, ME);
			} else if (list.get(i).sendString.equals(nickname_otherString)) {
				addTextToList(list.get(i).messageString, OTHER);
			}
		}

		/*
		 * Cursor ccc_other = db_contact.query("secondary_" + nicknameString +
		 * "_" + nickname_otherString, null, "send=?", new String[] {
		 * nickname_otherString }, null, null, "_id asc");
		 * 
		 * if(ccc_other != null){ while(ccc_other.moveToNext()){
		 * List<DB_Message_Item> list_other = new ArrayList<DB_Message_Item>();
		 * 
		 * db_Message_Item = new DB_Message_Item();
		 * db_Message_Item.datetimeString = ccc_other.getString(4);
		 * db_Message_Item.messageString = ccc_other.getString(3);
		 * 
		 * list_other.add(db_Message_Item);
		 * 
		 * // addTextToList(ccc_other.getString(3), OTHER); } ccc_other.close();
		 * }
		 */

		if (intent_type.equals("1"))// 从notification跳转到Imessage_Activity来的时候
		{
			// 此处建立一张关于联系人的名字和头像url的表
			ContentValues values_contact = new ContentValues();
			values_contact.put("nickname", nickname_otherString);
			values_contact.put("headphoto_url", request_otherString);
			/*
			 * DatabaseHelper database_helper_contactDatabaseHelper = new
			 * DatabaseHelper( Secondary_Imessage_Activity.this,
			 * nicknameString);
			 */
			/*
			 * SQLiteDatabase db_contact = database_helper_contactDatabaseHelper
			 * .getWritableDatabase();// 这里是获得可写的数据库
			 */db_contact
					.execSQL("create table if not exists secondary_contact_list(_id integer primary key autoincrement,nickname text not null,headphoto_url not null)");

			Log.v(TAG + "values_contact.get(\"nickname\").toString()",
					values_contact.get("nickname").toString());

			Cursor cc = db_contact.query("secondary_contact_list", null,
					"nickname=?", new String[] { values_contact.get("nickname")
							.toString() }, null, null, null);

			if (cc != null) {
				if (cc.moveToFirst()) {

				} else {
					db_contact.insert("secondary_contact_list", null,
							values_contact);
				}
				cc.close();
			}

			Cursor c_contact = db_contact.rawQuery(
					"select * from secondary_contact_list", null);
			if (c_contact != null) {
				String[] cols = c_contact.getColumnNames();
				while (c_contact.moveToNext()) {
					for (String ColumnName : cols) {
						Log.i("info",
								ColumnName
										+ ":"
										+ c_contact.getString(c_contact
												.getColumnIndex(ColumnName)));
					}
				}
				c_contact.close();
			}
			db_contact.close();

			// addTextToList(message_otherString, OTHER);
			//
			// msgHandler.sendEmptyMessage(MSG_RCV);
		}

		// 此处设置我的里面的头像
		// CircularImageView cover_user_photo = (CircularImageView)
		// findViewById(R.id.id_personal_setting_info_ll_photo);

		if (client != null)
			client.Logout();
		client = new IMClient(nicknameString, passwordString);

		client.setIMessageListener(new IMessageListener() {

			@Override
			public void onMessageReceive(String name, String msg) {
				// TODO Auto-generated method stub
				// messages.add(new MyMessage(name, msg));

				VibratorUtil.Vibrate(Secondary_Imessage_Activity.this, 100); // 震动100ms

				Log.v(TAG + "dsadas 99999", msg);

				String msg_first = msg.split("_")[0];
				String msg_new = msg.split("_")[1];

				Log.v(TAG + "dsadas 8888899999", msg_first);
				Log.v(TAG + "dsadas 9999988888", msg_new);

				if (nickname_otherString.equals(name) && msg_first.equals("secondary")) {
					addTextToList(msg_new, OTHER);
					msgHandler.sendEmptyMessage(MSG_RCV);
				} else {
					// Toast.makeText(Secondary_Imessage_Activity.this, "您有来自" +
					// name + "的消息", Toast.LENGTH_SHORT).show();
				}

				// 此处建立一张关于联系人的名字和头像url的表
				ContentValues values_contact = new ContentValues();
				values_contact.put("nickname", name);

				request_real_otherString = HttpPostFile.upload_chat(name);

				values_contact.put("headphoto_url", request_real_otherString);
				DatabaseHelper database_helper_contactDatabaseHelper = new DatabaseHelper(
						Secondary_Imessage_Activity.this, nicknameString);
				SQLiteDatabase db_contact = database_helper_contactDatabaseHelper
						.getWritableDatabase();// 这里是获得可写的数据库
				db_contact
						.execSQL("create table if not exists secondary_contact_list(_id integer primary key autoincrement,nickname text not null,headphoto_url not null)");

				Log.v(TAG + "values_contact.get(\"nickname\").toString()",
						values_contact.get("nickname").toString());

				Cursor cc = db_contact.query("secondary_contact_list", null,
						"nickname=?",
						new String[] { values_contact.get("nickname")
								.toString() }, null, null, null);

				if (cc != null) {
					if (cc.moveToFirst()) {

					} else {
						db_contact.insert("secondary_contact_list", null,
								values_contact);
					}
					cc.close();
				}

				Cursor c_contact = db_contact.rawQuery(
						"select * from secondary_contact_list", null);
				if (c_contact != null) {
					String[] cols = c_contact.getColumnNames();
					while (c_contact.moveToNext()) {
						for (String ColumnName : cols) {
							Log.i("info",
									ColumnName
											+ ":"
											+ c_contact.getString(c_contact
													.getColumnIndex(ColumnName)));
						}
					}
					c_contact.close();
				}
				db_contact.close();

				// 生成contentvallues对象，该对象用来存数据的
				ContentValues values = new ContentValues();
				// values.put("id", 1);//注意值的类型要匹配
				values.put("send", name);
				values.put("receive", nicknameString);
				values.put("message", msg_new);
				values.put("datetime", gettime());
				DatabaseHelper database_helper = new DatabaseHelper(
						Secondary_Imessage_Activity.this, nicknameString);
				SQLiteDatabase db = database_helper.getWritableDatabase();// 这里是获得可写的数据库
				db.execSQL("create table if not exists "
						+ "secondary_"
						+ nicknameString
						+ "_"
						+ name
						+ "(_id integer primary key autoincrement,send text not null,receive not null,message text not null,datetime text not null)");

				Log.v(TAG + "Important", "secondary_" + nicknameString + "_"
						+ name);

				db.insert("secondary_" + nicknameString + "_" + name, null,
						values);

				Cursor c = db.rawQuery("select * from " + "secondary_"
						+ nicknameString + "_" + name, null);
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
		});

		attempLogin();

		mTitleView = (TitleView) findViewById(R.id.id_imessage_title);

		mTitleView.requestFocus();
		
		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		tabheight = (int) height * 1 / 12;

		mTitleView.setLayoutParams(LayoutParams.MATCH_PARENT, tabheight);

		Log.v(TAG + "nickname_otherString", nickname_otherString);

		mTitleView.setTitle(nickname_otherString);

		mTitleView.setImageLeftButton(R.drawable.arrow_left_white_thin,
				new OnImageLeftButtonClickListener() {

					@Override
					public void onClick(View Imagebutton) {
						// TODO Auto-generated method stub
						/*if (intent_type.equals("1")) {
							Intent intent = new Intent();
							intent.putExtra("intent_type", "secondary");
							intent.setClass(Secondary_Imessage_Activity.this,
									My_Message_Activity.class);
							startActivity(intent);
						} else {

						}*/
						finish();

						startService(new Intent(
								Secondary_Imessage_Activity.this,
								ImessageService.class));
					}
				});

		chatSendButton = (Button) findViewById(R.id.chat_bottom_sendbutton);
		editText = (EditText) findViewById(R.id.chat_bottom_edittext);
		
		chat_voice = (ImageView) findViewById(R.id.chat_bottom_look);

//		chatSendButton.setFocusable(true);
//		chatSendButton.setFocusableInTouchMode(true);
//		chatSendButton.requestFocus();
//		chatSendButton.requestFocusFromTouch();
		
//		/** 隐藏软键盘 **/
//		
//		View view = getWindow().peekDecorView();
//		if (view != null) {
//			InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
//		}

		chatListView = (ListView) findViewById(R.id.chat_list);

		adapter = new MyChatAdapter(this, chatList, layout, from, to);

//		chatListView.requestFocus();
		// chatListView.setEnabled(false);

		chatSendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/**
				 * 这是一个发送消息的监听器，注意如果文本框中没有内容，那么getText()的返回值可能为
				 * null，这时调用toString()会有异常！所以这里必须在后面加上一个""隐式转换成String实例
				 * ，并且不能发送空消息。
				 */

				myWord = "secondary_" + editText.getText().toString();

				Log.v(TAG + "myWord", myWord);

				if (myWord.length() == 0)
					return;

				sendMessage();

			}
		});

		chatListView.setAdapter(adapter);
		chatListView.setSelection(adapter.getCount());
		// chatList = (ListView) findViewById(R.id.chat_list);
		// chatList.setAdapter(chatAdapter);
		// userView = (EditText) findViewById(R.id.send_usr);
		// msgView = (EditText) findViewById(R.id.send_msg);
	}

	private class MyChatAdapter extends BaseAdapter {

		Context context = null;
		ArrayList<HashMap<String, Object>> chatList = null;
		int[] layout;
		String[] from;
		int[] to;

		public MyChatAdapter(Context context,
				ArrayList<HashMap<String, Object>> chatList, int[] layout,
				String[] from, int[] to) {
			super();
			this.context = context;
			this.chatList = chatList;
			this.layout = layout;
			this.from = from;
			this.to = to;
		}

		@Override
		public boolean isEnabled(int position) {
			// TODO Auto-generated method stub
			return false;// 当前行是否可以点击
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return chatList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder {
			public CircularImageView imageView = null;
			public TextView textView = null;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			Log.v(TAG + "noft2", "notify2");

			ViewHolder holder = null;
			int who = (Integer) chatList.get(position).get("person");

			Log.v(TAG + "who", who + "");
			convertView = LayoutInflater.from(context).inflate(
					layout[who == ME ? 0 : 1], null);

			holder = new ViewHolder();
			holder.imageView = (CircularImageView) convertView
					.findViewById(to[who * 2 + 0]);
			holder.textView = (TextView) convertView
					.findViewById(to[who * 2 + 1]);

			System.out.println(holder);
			System.out.println("WHYWHYWHYWHYW");
			System.out.println(holder.imageView);
			// holder.imageView.setBackgroundResource((Integer) chatList.get(
			// position).get(from[0]));

			switch (holder.imageView.getId()) {
			case R.id.chatlist_image_me:
				asynImageLoader = new AsynImageLoader(nicknameString);
				asynImageLoader.showImageAsyn(holder.imageView,
						request_imageurl, R.drawable.load_failed);
				break;

			case R.id.chatlist_image_other:
				asynImageLoader = new AsynImageLoader(nickname_otherString);
				asynImageLoader.showImageAsyn(holder.imageView,
						request_otherString, R.drawable.load_failed);
				break;
			}

			holder.textView.setText(chatList.get(position).get(from[1])
					.toString());
			return convertView;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_logout:
			if (client != null)
				client.Logout();
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void addTextToList(String text, int who) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("person", who);
		map.put("image", who == ME ? R.drawable.photo_default_gray
				: R.drawable.photo_default_black);
		map.put("text", text);
		chatList.add(map);

		Log.v(TAG + "addTextToList...malegebi", chatList.size() + "");
	}

	/*
	 * private class ChatAdapter extends BaseAdapter {
	 * 
	 * @Override public int getCount() { // TODO Auto-generated method stub
	 * return messages.size(); }
	 * 
	 * @Override public Object getItem(int position) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public long getItemId(int position) { // TODO Auto-generated
	 * method stub return 0; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { // TODO Auto-generated method stub convertView = (View)
	 * getLayoutInflater().inflate( R.layout.chat_list_item, null); TextView
	 * name = (TextView) convertView.findViewById(R.id.name); TextView msg =
	 * (TextView) convertView.findViewById(R.id.message);
	 * name.setText(messages.get(position).name + ":");
	 * msg.setText(messages.get(position).message); return convertView; }
	 * 
	 * }
	 */
	/*
	 * private class MyMessage { public String name; public String message;
	 * 
	 * public MyMessage(String name, String message) { this.name = name;
	 * this.message = message; } }
	 */

	public void sendMessage() {

		if (editText.getText().toString().equals("")) {
			Toast.makeText(this, "发送消息不能为空!", Toast.LENGTH_SHORT)
					.show();
		} else {
			addTextToList(editText.getText().toString(), ME);

			msgHandler.sendEmptyMessage(MSG_RCV);

			new SendMsgTask().execute(nickname_otherString, myWord);
		}
	}

	private String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
	}

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

	private class SendMsgTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				client.sendMessage(params[0], params[1]);
			} catch (NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				// 此处建立一张关于联系人的名字和头像url的表
				ContentValues values_contact = new ContentValues();
				values_contact.put("nickname", nickname_otherString);
				values_contact.put("headphoto_url", request_otherString);
				DatabaseHelper database_helper_contactDatabaseHelper = new DatabaseHelper(
						Secondary_Imessage_Activity.this, nicknameString);
				SQLiteDatabase db_contact = database_helper_contactDatabaseHelper
						.getWritableDatabase();// 这里是获得可写的数据库
				db_contact
						.execSQL("create table if not exists secondary_contact_list(_id integer primary key autoincrement,nickname text not null,headphoto_url not null)");

				Log.v(TAG + "values_contact.get(\"nickname\").toString()",
						values_contact.get("nickname").toString());

				Cursor cc = db_contact.query("secondary_contact_list", null,
						"nickname=?",
						new String[] { values_contact.get("nickname")
								.toString() }, null, null, null);

				if (cc != null) {
					if (cc.moveToFirst()) {

					} else {
						db_contact.insert("secondary_contact_list", null,
								values_contact);
					}
					cc.close();
				}

				Cursor c_contact = db_contact.rawQuery(
						"select * from secondary_contact_list", null);
				if (c_contact != null) {
					String[] cols = c_contact.getColumnNames();
					while (c_contact.moveToNext()) {
						for (String ColumnName : cols) {
							Log.i("info",
									ColumnName
											+ ":"
											+ c_contact.getString(c_contact
													.getColumnIndex(ColumnName)));
						}
					}
					c_contact.close();
				}
				db_contact.close();

				// 生成contentvallues对象，该对象用来存数据的
				ContentValues values = new ContentValues();
				// values.put("id", 1);//注意值的类型要匹配
				values.put("send", nicknameString);
				values.put("receive", nickname_otherString);
				values.put("message", editText.getText().toString());
				values.put("datetime", gettime());
				DatabaseHelper database_helper = new DatabaseHelper(
						Secondary_Imessage_Activity.this, nicknameString);
				SQLiteDatabase db = database_helper.getWritableDatabase();// 这里是获得可写的数据库
				db.execSQL("create table if not exists "
						+ "secondary_"
						+ nicknameString
						+ "_"
						+ nickname_otherString
						+ "(_id integer primary key autoincrement,send text not null,receive not null,message text not null,datetime text not null)");
				db.insert("secondary_" + nicknameString + "_"
						+ nickname_otherString, null, values);

				Cursor c = db.rawQuery("select * from " + "secondary_"
						+ nicknameString + "_" + nickname_otherString, null);
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

				editText.setText("");
			} else
				Toast.makeText(Secondary_Imessage_Activity.this,
						"信息发送失败,请检查网络设置!", Toast.LENGTH_SHORT).show();
		}
	}

	private Handler msgHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_RCV:
				/**
				 * 更新数据列表，并且通过setSelection方法使ListView始终滚动在最底端
				 */
				Log.v(TAG + "noft1", "notify1");
				adapter.notifyDataSetChanged();
				Log.v(TAG + "noft1", "notify1");
				chatListView.setSelection(adapter.getCount());
				break;
			default:
				break;
			}
		}
	};

	private void attempLogin() {
		// TODO Auto-generated method stub

		if (loginTask != null) {
			return;
		}

		/*
		 * if (client != null) client.Logout(); client = new
		 * IMClient(nicknameString, passwordString);
		 * 
		 * client.setIMessageListener(new IMessageListener() {
		 * 
		 * @Override public void onMessageReceive(String name, String msg) { //
		 * TODO Auto-generated method stub // messages.add(new MyMessage(name,
		 * msg)); Log.v(TAG + "dsadas 99999", msg); addTextToList(msg, OTHER);
		 * 
		 * Log.v(TAG + "xiaxiaixia", chatList.get(0).get("text") .toString());
		 * 
		 * msgHandler.sendEmptyMessage(MSG_RCV); } });
		 */

		loginTask = new LoginTask();
		loginTask.execute((Void) null);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
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

				Log.v(TAG + "fuck", code + "fuck");
			}

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			/*if (intent_type.equals("1")) {
				Intent intent = new Intent();
				intent.putExtra("intent_type", "secondary");
				intent.setClass(Secondary_Imessage_Activity.this,
						My_Message_Activity.class);
				startActivity(intent);
			} else {

			}*/
			finish();
			startService(new Intent(Secondary_Imessage_Activity.this,
					ImessageService.class));
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
