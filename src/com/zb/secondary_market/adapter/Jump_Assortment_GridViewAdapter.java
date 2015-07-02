package com.zb.secondary_market.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.secondary_market.Personal_Setting_Activity;
import com.zb.secondary_market.R;
import com.zb.secondary_market.imageblur.BlurUtil;

public class Jump_Assortment_GridViewAdapter extends BaseAdapter {
	private static final String TAG = "Jump_Assortment_GridViewAdapter";
	private Activity mActivity;
	private GridView.LayoutParams itemViewLayoutParams;
	private ImageView icon;
	private TextView textView;
	private LinearLayout backgroundLayout;

	private int[] background_image = { R.drawable.secondary_bg_small,
			R.drawable.friend_bg_small, R.drawable.work_bg_small,
			R.drawable.activity_bg_small, R.drawable.compaigner_bg_small,
			R.drawable.personal_setting_bg_small };

	private int[] icon_image = { R.drawable.secondary, R.drawable.friend,
			R.drawable.work, R.drawable.activity, R.drawable.compaigner,
			R.drawable.personal_setting };

	private String[] text = { "跳蚤市场", "校园交友", "工作信息", "校园信息", "活动发起", "软件设置" };

	public Jump_Assortment_GridViewAdapter(Activity mActivity, int height) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
		itemViewLayoutParams = new GridView.LayoutParams(
				LayoutParams.MATCH_PARENT, height);
		
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
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = mActivity.getLayoutInflater();
		convertView = inflater.inflate(R.layout.jump_assortment_gridview_item,
				parent, false);

		convertView.setLayoutParams(itemViewLayoutParams);

		backgroundLayout = (LinearLayout) convertView
				.findViewById(R.id.id_jump_assortment_item_ll);
		icon = (ImageView) convertView
				.findViewById(R.id.id_jump_assortment_item_ll_ll_image);
		textView = (TextView) convertView
				.findViewById(R.id.id_jump_assortment_item_ll_ll_text);

		Log.v(TAG, "为甚么打不出来了");
		backgroundLayout.setBackgroundResource(background_image[position]);
//		setBackground(background_image[position]);
		Log.v(TAG, "判断setBackground方法是不是执行了");
		icon.setBackgroundResource(icon_image[position]);
		textView.setText(text[position]);

		return convertView;
	}
	
	/**
	 * 设置毛玻璃背景
	 * @param id 背景图片id
	 */
	@SuppressWarnings("deprecation")
	private void setBackground(int id)
    {		
		Log.v(TAG + "id 到底是什么", id + "");
    	Bitmap bmp = BitmapFactory.decodeResource(mActivity.getResources(),id);//从资源文件中得到图片，并生成Bitmap图片		
    	final Bitmap blurBmp = BlurUtil.fastblur(mActivity, bmp, 25);//0-25，表示模糊值	
    	final Drawable newBitmapDrawable = new BitmapDrawable(blurBmp); // 将Bitmap转换为Drawable 
    	backgroundLayout.post(new Runnable()  //调用UI线程
    	{			
			@Override			
    		public void run() 
    		{				
				backgroundLayout.setBackgroundDrawable(newBitmapDrawable);//设置背景
    		}		
    	});	
    }

}
