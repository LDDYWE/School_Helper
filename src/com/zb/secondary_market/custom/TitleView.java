package com.zb.secondary_market.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zb.secondary_market.R;

public class TitleView extends LinearLayout implements View.OnClickListener{
	
	private LinearLayout title_layout;
    private ImageButton imageLeftBtn;
	private ImageButton imageRightBtn;
	private TextView mTitle;


	private OnImageRightButtonClickListener imageOnRightButtonClickListener;
	private OnImageLeftButtonClickListener imageOnLeftButtonClickListener;

	public interface OnImageLeftButtonClickListener {
		public void onClick(View Imagebutton);
	}
	public interface OnImageRightButtonClickListener {
		public void onClick(View Imagebutton);
	}
	
	//��ImageButton
	public void setImageRightButton(int stringID, OnImageRightButtonClickListener listener) {
		imageRightBtn.setBackgroundResource(stringID);
		imageRightBtn.setVisibility(View.VISIBLE);
		imageOnRightButtonClickListener = listener;
	}
	
	public void setImageRightButton(int stringID) {
		imageRightBtn.setBackgroundResource(stringID);
		imageRightBtn.setVisibility(View.VISIBLE);
	}
	
	public void removeImageRightButton() {
		imageRightBtn.setVisibility(View.INVISIBLE);
		imageOnRightButtonClickListener = null;
	}
	
	public void hiddenImageRightButton() {
		imageRightBtn.setVisibility(View.INVISIBLE);
	}
	
	public void showImageRightButton() {
		imageRightBtn.setVisibility(View.VISIBLE);
	}
	
	//LeftImageButton
	public void setImageLeftButton(int stringID, OnImageLeftButtonClickListener listener) {
		imageLeftBtn.setBackgroundResource(stringID);
		imageLeftBtn.setVisibility(View.VISIBLE);
		imageOnLeftButtonClickListener = listener;
	}
	
	public void removeImageLeftButton() {
		imageLeftBtn.setVisibility(View.INVISIBLE);
		imageOnLeftButtonClickListener = null;
	}
	
	public void hiddenImageLeftButton() {
		imageLeftBtn.setVisibility(View.INVISIBLE);
	}
	
	public void showImageLeftButton() {
		imageLeftBtn.setVisibility(View.VISIBLE);
	}
	
	public TitleView(Context context) {
		this(context, null);
	}

	public TitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.titleview, this, true);
		
		title_layout=(LinearLayout)findViewById(R.id.title_layout);
         
		imageLeftBtn=(ImageButton)findViewById(R.id.titleview_image_leftbutton);
		imageLeftBtn.setVisibility(View.INVISIBLE);
		imageLeftBtn.setOnClickListener(this);
		
		imageRightBtn=(ImageButton)findViewById(R.id.titleview_image_rightbutton);
		imageRightBtn.setVisibility(View.INVISIBLE);
		imageRightBtn.setOnClickListener(this);
		
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
		case R.id.titleview_image_rightbutton:
			if(imageOnRightButtonClickListener!=null)
				imageOnRightButtonClickListener.onClick(v);
			break;
		case R.id.titleview_image_leftbutton:
			if(imageOnLeftButtonClickListener!=null)
				imageOnLeftButtonClickListener.onClick(v);
			break;
		}
	}

}
