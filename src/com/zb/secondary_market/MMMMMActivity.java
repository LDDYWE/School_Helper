package com.zb.secondary_market;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MMMMMActivity extends FragmentActivity{
	private static final String TAG = "MMMMMActivity";
	
	private TextView textView;
	
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	
	private LinearLayout center_Layout;
	
	private View parentView;
	
	private ImageView up_down_icon;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		parentView = getLayoutInflater().inflate(
				R.layout.activity_mmmmm, null);
		setContentView(parentView);
		
		center_Layout = (LinearLayout)findViewById(R.id.id_aa);
		textView = (TextView)findViewById(R.id.id_a_textview);
		up_down_icon = (ImageView)findViewById(R.id.id_aa_image);
		
		MMMMM();
		
		center_Layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				up_down_icon.setImageResource(R.drawable.arrow_up_mini_blue);
				
				ll_popup.startAnimation(AnimationUtils.loadAnimation(
						MMMMMActivity.this,
						R.anim.activity_translate_in));
				pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			}
		});
	}
	
	private void MMMMM() {
		// TODO Auto-generated method stub
		pop = new PopupWindow(MMMMMActivity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		// Button bt1 = (Button)
		// view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				up_down_icon.setImageResource(R.drawable.arrow_down_mini_blue);
				
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MMMMMActivity.this,
						AlbumSecondaryActivity.class);
				String section_type = "friend";
				intent.putExtra("section_type", section_type);
				startActivity(intent);

				finish();
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}
}
