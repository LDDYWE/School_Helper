package com.zb.secondary_market.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.secondary_market.R;

public class TitleView_Text extends LinearLayout implements View.OnClickListener{
	
	private LinearLayout title_layout;
    private TextView textLeftBtn;
	private TextView textRightBtn;
	private TextView mTitle;


	private OnTextRightButtonClickListener textOnRightButtonClickListener;
	private OnTextLeftButtonClickListener textOnLeftButtonClickListener;


	public interface OnTextLeftButtonClickListener {
		public void onClick(View TextView);
	}
	public interface OnTextRightButtonClickListener {
		public void onClick(View TextView);
	}
	
	//��ImageButton
	public void setTextRightButton(String right_string, OnTextRightButtonClickListener listener) {
		textRightBtn.setText(right_string);
		textRightBtn.setVisibility(View.VISIBLE);
		textOnRightButtonClickListener = listener;
	}
	
	public void setTextRightButton(int stringID) {
		textRightBtn.setText(stringID);
		textRightBtn.setVisibility(View.VISIBLE);
	}
	
	public void removeTextRightButton() {
		textRightBtn.setVisibility(View.INVISIBLE);
		textOnRightButtonClickListener = null;
	}
	
	public void hiddenTextRightButton() {
		textRightBtn.setVisibility(View.INVISIBLE);
	}
	
	public void showImageRightButton() {
		textRightBtn.setVisibility(View.VISIBLE);
	}
	
	//LeftImageButton
	public void setTextLeftButton(String left_string, OnTextLeftButtonClickListener listener) {
		textLeftBtn.setText(left_string);
		textLeftBtn.setVisibility(View.VISIBLE);
		textOnLeftButtonClickListener = listener;
	}
	
	public void removeTextLeftButton() {
		textLeftBtn.setVisibility(View.INVISIBLE);
		textOnLeftButtonClickListener = null;
	}
	
	public void hiddenTextLeftButton() {
		textLeftBtn.setVisibility(View.INVISIBLE);
	}
	
	public void showTextLeftButton() {
		textLeftBtn.setVisibility(View.VISIBLE);
	}
	
	public TitleView_Text(Context context) {
		this(context, null);
	}

	public TitleView_Text(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public TitleView_Text(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.titleview_text, this, true);
		
		title_layout=(LinearLayout)findViewById(R.id.title_layout_text);
         
		textLeftBtn=(TextView)findViewById(R.id.titleview_text_leftbutton);
		textLeftBtn.setVisibility(View.INVISIBLE);
		textLeftBtn.setOnClickListener(this);
		
		textRightBtn=(TextView)findViewById(R.id.titleview_text_rightbutton);
		textRightBtn.setVisibility(View.INVISIBLE);
		textRightBtn.setOnClickListener(this);
		
		mTitle = (TextView) findViewById(R.id.titleview_titletext);
		mTitle.setVisibility(View.INVISIBLE);
	}
	
	public void setLayoutParams(int width,int height){
		title_layout.setLayoutParams(new LinearLayout.LayoutParams(width,height));
	}
	
	public void setTitleTextSize(float size){
		mTitle.setTextSize(size);
	}
	
	public void setTitleBack(int background){
		title_layout.setBackgroundResource(background);
	}
	
	public void setTitle(String text) {
		mTitle.setVisibility(View.VISIBLE);
		mTitle.setText(text);
	}
	
	public void setTitle(int stringID) {
		mTitle.setVisibility(View.VISIBLE);
		mTitle.setText(stringID);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleview_text_rightbutton:
			if(textOnRightButtonClickListener!=null)
				textOnRightButtonClickListener.onClick(v);
			break;
		case R.id.titleview_text_leftbutton:
			if(textOnLeftButtonClickListener!=null)
				textOnLeftButtonClickListener.onClick(v);
			break;
		}
	}

}
