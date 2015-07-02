package com.zb.secondary_market;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.zb.secondary_market.custom.TitleView;
import com.zb.secondary_market.custom.TitleView.OnImageLeftButtonClickListener;

public class AboutUs_Activity extends Activity{

	private int title_height;
	private TitleView mTitle;
	private TextView mine_about_rjjj,mine_about_bqss,mineabout_bqss_con,textbrief,textversion;
	private String brief="    科大助手的详细介绍！";
	private String version="1.0.0";
	private ImageView mine_about_rjjj_img,mine_about_bqss_img;
	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
        
        WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		title_height = (int) height * 1 / 12;
        
		//标题栏
		mTitle=(TitleView)findViewById(R.id.mineabout_title);
		mTitle.setLayoutParams(LayoutParams.MATCH_PARENT, title_height);
		mTitle.setTitle("软件相关");		
		mTitle.setTitleTextSize(24);
		mTitle.setImageLeftButton(R.drawable.arrow_left_white_thin, new OnImageLeftButtonClickListener(){

			@Override
			public void onClick(View Imagebutton) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		
		LayoutParams para;  
		mine_about_rjjj_img=(ImageView)findViewById(R.id.mine_about_rjjj_img);
        para = mine_about_rjjj_img.getLayoutParams();
        para.height = (int) (200);  
        para.width = (int) (200);  
        mine_about_rjjj_img.setLayoutParams(para); 
		
        mine_about_bqss_img=(ImageView)findViewById(R.id.mine_about_bqss_img);
        para = mine_about_bqss_img.getLayoutParams();
        para.height = (int) (240);  
        para.width = (int) (230);  
        mine_about_bqss_img.setLayoutParams(para); 
        
		mine_about_rjjj=(TextView)findViewById(R.id.mine_about_rjjj);
		mine_about_rjjj.setTextSize(20);
		mine_about_bqss=(TextView)findViewById(R.id.mine_about_bqss);
		mine_about_bqss.setTextSize(20);
		mineabout_bqss_con=(TextView)findViewById(R.id.mineabout_bqss_con);
		mineabout_bqss_con.setTextSize(17);
		textbrief=(TextView)findViewById(R.id.mine_about_textbrief);
		textbrief.setTextSize(18);
		textversion=(TextView)findViewById(R.id.mine_about_textversion);
		textversion.setTextSize(16);
		textbrief.setText(brief);
		try {
			version="V"+getVersionName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			version="获取版本信息错误";
		}
		textversion.setText(version);
	}
	
	private String getVersionName() throws Exception{ 
	    PackageManager packageManager = this.getPackageManager();
	    PackageInfo packInfo = packageManager.getPackageInfo(this.getPackageName(), 0);  
	    return packInfo.versionName;   
	} 
}
