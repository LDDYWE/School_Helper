package com.zb.secondary_market.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseHelper";

	private static final int VERSON = 1;// 默认的数据库版本
	private String qufen = null;

	// 继承SQLiteOpenHelper类的类必须有自己的构造函数
	// 该构造函数4个参数，直接调用父类的构造函数。其中第一个参数为该类本身；第二个参数为数据库的名字；
	// 第3个参数是用来设置游标对象的，这里一般设置为null；参数四是数据库的版本号。
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int verson) {
		super(context, name, factory, verson);
	}

	// 该构造函数有3个参数，因为它把上面函数的第3个参数固定为null了
	public DatabaseHelper(Context context, String name, int verson) {
		this(context, name, null, verson);
	}

	// 该构造函数只有2个参数，在上面函数 的基础山将版本号固定了
	public DatabaseHelper(Context context, String name) {
		this(context, name, VERSON);
	}

	// 该构造函数只有2个参数，在上面函数 的基础山将版本号固定了
	public DatabaseHelper(Context context, String name, String qufen) {
		this(context, name, VERSON);
		this.qufen = qufen;
	}

	// 该函数在数据库第一次被建立时调用
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
		Log.v(TAG + "DB", "create a sqlite database");
		
		System.out.println("create a sqlite database");
		// execSQL()为执行参数里面的SQL语句，因此参数中的语句需要符合SQL语法,这里是创建一个表
		// arg0.execSQL("create table user1(id int, name varchar(20))");
		/*if (qufen != null) {
			arg0.execSQL("create table if not exists "
					+ qufen
					+ "(_id integer primary key autoincrement,send text not null,receive not null,message text not null,datetime text not null)");
		} else {
			arg0.execSQL("create table if not exists contact_list(_id integer primary key autoincrement,nickname text not null,headphoto_url not null)");
		}*/
		// arg0.execSQL("insert into stutb(send,receive,message,datetime)values('张三','女','18','21:20')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("update a sqlite database");
	}

}
