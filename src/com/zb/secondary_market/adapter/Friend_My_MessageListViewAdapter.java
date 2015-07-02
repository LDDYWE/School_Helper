package com.zb.secondary_market.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.secondary_market.R;
import com.zb.secondary_market.AsynImage.AsynImageLoader;
import com.zb.secondary_market.custom.CircularImageView;
import com.zb.secondary_market.database.DatabaseHelper;
import com.zb.secondary_market.imagecache.ImageFetcher;
import com.zb.secondary_market.otherclasses.DB_Message_Item;

public class Friend_My_MessageListViewAdapter extends BaseAdapter {
	private static final String TAG = "Friend_My_MessageListViewAdapter";
	private Activity mActivity;
	private Context mContext;
	private String nicknameString;
	private String last_messageString, last_datetimeString, last_messageYearString, last_messageMonthString, last_messageDayString;	
	private List<String> nicknameList;
	private List<String> imageurlList;
	private ImageFetcher mImageFetcher;
	private DB_Message_Item db_Message_Item;
	private List<DB_Message_Item> list = new ArrayList<DB_Message_Item>();
	
	AsynImageLoader asynImageLoader;

	public Friend_My_MessageListViewAdapter(ImageFetcher mImageFetcher, Activity mActivity,
			String nicknameString, List<String> nickname_otherList, List<String> imageurl) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
		this.nicknameString = nicknameString;
		this.nicknameList = nickname_otherList;
		this.imageurlList = imageurl;
		this.mImageFetcher = mImageFetcher;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return nicknameList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		LayoutInflater inflater = mActivity.getLayoutInflater();
		rowView = inflater.inflate(R.layout.friend_my_message_listview_item, null);
		
//		ImageView icon;
		TextView nickname;
		TextView datetime;
		TextView content;
//		icon = (ImageView) rowView.findViewById(R.id.id_secondary_my_message_listview_item_image);
		
		WindowManager vmManager = mActivity.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

//		LayoutParams para;
//		para = icon.getLayoutParams();
//
//		para.height = height / 10;
//		para.width = height / 10;
//		icon.setLayoutParams(para);

		// 此处判断是否已经存在存放“我”和对方之间通话记录的表
		DatabaseHelper database_helper_contactDatabaseHelper = new DatabaseHelper(
				mActivity, nicknameString);
		SQLiteDatabase db_contact = database_helper_contactDatabaseHelper
				.getWritableDatabase();// 这里是获得可写的数据库

		db_contact
				.execSQL("create table if not exists "
						+ "friend_"
						+ nicknameString
						+ "_"
						+ nicknameList.get(position)
						+ "(_id integer primary key autoincrement,send text not null,receive text not null,message text not null,datetime text not null)");

		Log.v(TAG + "Important", "friend_" + nicknameString + "_"
				+ nicknameList.get(position));

		Cursor ccc = db_contact.query("friend_" + nicknameString + "_"
				+ nicknameList.get(position), new String[] { "_id", "send",
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

		last_messageString = list.get(list.size() - 1).messageString;
		last_messageYearString = list.get(list.size() - 1).datetimeString.split("-")[0];
		last_messageMonthString = list.get(list.size() - 1).datetimeString.split("-")[1];
		last_messageDayString = list.get(list.size() - 1).datetimeString.split("-")[2];
		last_datetimeString = list.get(list.size() - 1).datetimeString.split("-")[3];
		
		// 此处设置我的里面的头像
		asynImageLoader  = new AsynImageLoader(nicknameList.get(position));
		CircularImageView cover_user_photo = (CircularImageView) rowView.findViewById(R.id.id_friend_my_message_listview_item_image);
		asynImageLoader.showImageAsyn(cover_user_photo, imageurlList.get(position),
				R.drawable.ic_launcher);
		
		nickname = (TextView) rowView.findViewById(R.id.id_friend_my_message_listview_item_ll_rl_nickname);
		datetime = (TextView) rowView
				.findViewById(R.id.id_friend_my_message_listview_item_ll_rl_time);
		content = (TextView)rowView.findViewById(R.id.id_friend_my_message_listview_item_ll_ll_content);
		
//		mImageFetcher.loadImage(imageurlList.get(position), icon);
		nickname.setText(nicknameList.get(position));
		
String currentTimeString = gettime();
		
		Log.v(TAG + "time", currentTimeString);
		
		Log.v(TAG + "time1", currentTimeString.split("-")[0]);
		Log.v(TAG + "time2", last_messageDayString);
		
		if(currentTimeString.split("-")[0].equals(last_messageDayString)){
			Log.v(TAG + "true", "true");
			datetime.setText(last_datetimeString);
		}else{
			Log.v(TAG + "false", "false");
			datetime.setText(last_messageYearString + "-" + last_messageMonthString + "-" + last_messageDayString);
		}
		
		content.setText(last_messageString);

		return rowView;
	}

	// 获得当前时间的方法
	private String gettime() {
		Calendar c = Calendar.getInstance();

		String time = c.get(Calendar.DAY_OF_MONTH) + "-"
				+ formatTime(c.get(Calendar.HOUR_OF_DAY)) + ":"
				+ formatTime(c.get(Calendar.MINUTE)); // 日
		// 秒
		System.out.println(time);
		return time; // 输出
	}

	private String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
	}
}
