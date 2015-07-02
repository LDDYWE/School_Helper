package com.zb.secondary_market.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.secondary_market.R;

public class AssortmentGridViewAdapter extends BaseAdapter {
	private FragmentActivity mActivity;
	private Context context;
	private int num = 0;
	private String[] types_name = { "数码", "衣服", "鞋子", "手机", "电脑", "图书", "运动",
			"工具", "化妆品", "健身", "苹果", "电器", "生活", "娱乐", "数码配件", "乐器" };

	private String[] types_name_detail = { "相机  游戏机", "上衣  裤子", "皮鞋  运动鞋",
			"苹果  三星", "惠普  戴尔", "教科书  小说", "篮球  足球", "锤子  螺丝刀", "口红  香水",
			"哑铃  弹簧圈", "imac ipad", "台灯  电饭锅", "日常  会员卡", "优惠券  KTV",
			"充电器  耳机", "吉他  贝司" };
	private int[] types_icons = { R.drawable.vod_type_love,
			R.drawable.vod_type_movie, R.drawable.vod_type_tv,
			R.drawable.vod_type_cartoon, R.drawable.vod_type_variety,
			R.drawable.vod_type_jilu, R.drawable.vod_type_love,
			R.drawable.vod_type_movie, R.drawable.vod_type_tv,
			R.drawable.vod_type_cartoon, R.drawable.vod_type_variety,
			R.drawable.vod_type_jilu, R.drawable.vod_type_love,
			R.drawable.vod_type_movie, R.drawable.vod_type_tv,
			R.drawable.vod_type_cartoon, R.drawable.vod_type_variety,
			R.drawable.vod_type_jilu };
	private int[] types_icons1 = { R.drawable.img_1, R.drawable.img_1,
			R.drawable.img_1, R.drawable.img_1, R.drawable.img_1,
			R.drawable.img_1, R.drawable.img_1, R.drawable.img_1,
			R.drawable.img_1 };
//	private int height, tabheight, statusBarHeight;

	public AssortmentGridViewAdapter(FragmentActivity mActivity, int num,
			int height, int tabheight, int statusBarHeight) {
		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
		this.num = num;
//		this.height = height;
//		this.tabheight = tabheight;
//		this.statusBarHeight = statusBarHeight;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return num;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position < 0 ? 0 : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		LayoutInflater inflater = mActivity.getLayoutInflater();
		rowView = inflater.inflate(R.layout.assortmentgrid_item, null);
		// AbsListView.LayoutParams param = new AbsListView.LayoutParams(
		// android.view.ViewGroup.LayoutParams.MATCH_PARENT,
		// (height-2*tabheight-statusBarHeight)/5);
		// rowView.setLayoutParams(param);
		ImageView icon;
		TextView name;
		TextView name_detail;
		icon = (ImageView) rowView.findViewById(R.id.id_type_item_image);

		WindowManager vmManager = mActivity.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		LayoutParams para;
		para = icon.getLayoutParams();

		para.height = height / 10;
		para.width = height / 10;
		icon.setLayoutParams(para);

		name = (TextView) rowView.findViewById(R.id.id_type_item_text);
		name_detail = (TextView) rowView
				.findViewById(R.id.id_type_item_text_detail);
		icon.setImageResource(types_icons[position]);
		name.setText(types_name[position]);

		name_detail.setText(types_name_detail[position]);
		return rowView;
	}

}
