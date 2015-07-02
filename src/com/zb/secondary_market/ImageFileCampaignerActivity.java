package com.zb.secondary_market;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.zb.secondary_market.camera.FolderCampaignerAdapter;
import com.zb.secondary_market.camera.PublicWay;
import com.zb.secondary_market.camera.Res;
import com.zb.secondary_market.custom.TitleView_Text;
import com.zb.secondary_market.custom.TitleView_Text.OnTextRightButtonClickListener;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 *
 * @author king
 * @QQ:595163260
 * 
 */
public class ImageFileCampaignerActivity extends Activity {
	private static final String TAG = "ImageFileCampaignerActivity";

	private FolderCampaignerAdapter folderAdapter;
//	private Button bt_cancel;
	private Context mContext;
	private TitleView_Text imagefile_titleView;
	
	private int titleHeight;

	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(Res.getLayoutID("plugin_camera_image_file"));
		PublicWay.activityList.add(this);
		mContext = this;
		
//		bt_cancel = (Button) findViewById(Res.getWidgetID("cancel"));
//		bt_cancel.setOnClickListener(new CancelListener());
		
		WindowManager vmManager = this.getWindowManager();
		int width = vmManager.getDefaultDisplay().getWidth();
		int height = vmManager.getDefaultDisplay().getHeight();

		titleHeight = (int) height * 1 / 12;
		
		imagefile_titleView = (TitleView_Text)findViewById(R.id.id_imagefile_title);
		
		imagefile_titleView.setTitle("选择相册");
		
		imagefile_titleView.setLayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT, titleHeight);
		
		imagefile_titleView.setTextRightButton("取消",
				new OnTextRightButtonClickListener() {

			@Override
			public void onClick(View TextView) {
				//清空选择的图片
//				Bimp.tempSelectBitmap.clear();
				Intent intent = new Intent();
				intent.setClass(mContext, AlbumCampaignerActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		GridView gridView = (GridView) findViewById(Res.getWidgetID("fileGridView"));
//		TextView textView = (TextView) findViewById(Res.getWidgetID("headerTitle"));
//		textView.setText(Res.getString("photo"));
		folderAdapter = new FolderCampaignerAdapter(this, ImageFileCampaignerActivity.this);
		gridView.setAdapter(folderAdapter);
	}

	/*private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			//清空选择的图片
			Bimp.tempSelectBitmap.clear();
			Intent intent = new Intent();
			intent.setClass(mContext, Campaigner_PhotoGraph_Activity.class);
			startActivity(intent);
			finish();
		}
	}*/

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			/*Intent intent = new Intent();
			intent.setClass(mContext, Campaigner_PhotoGraph_Activity.class);
			startActivity(intent);
			finish();*/
			Intent intent = new Intent();
			intent.setClass(mContext, AlbumCampaignerActivity.class);
			startActivity(intent);
			finish();
		}
		
		return true;
	}

}
