package com.zb.secondary_market.AsynImage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class AsynImageLoader_Message {
	private static final String TAG = "AsynImageLoader";
	// 缓存下载过的图片的Map
	private Map<String, SoftReference<Bitmap>> caches = new HashMap<String, SoftReference<Bitmap>>();
	// 任务队列
	private List<Task> taskQueue;
	private boolean isRunning = false;

//	private final static String ALBUM_PATH = Environment
//			.getExternalStorageDirectory() + "/download_test/";

	public AsynImageLoader_Message() {
		// 初始化变量
		// 这一caches怎么保证里面的东西不消失
		// caches = new HashMap<String, SoftReference<Bitmap>>();
		taskQueue = new ArrayList<AsynImageLoader_Message.Task>();
		// 启动图片下载线程
		isRunning = true;
		new Thread(runnable).start();
	}

	/**
	 * 
	 * @param imageView
	 *            需要延迟加载图片的对象
	 * @param url
	 *            图片的URL地址
	 * @param resId
	 *            图片加载过程中显示的图片资源
	 */
	public void showImageAsyn(ImageView imageView, String url, int resId) {
		Log.v(TAG + "a", "a");

		Log.v(TAG + "url", url);

		imageView.setTag(url);
		Bitmap bitmap = loadImageAsyn(url, getImageCallback(imageView, resId));

		// Bitmap bitmap1 = setScaleBitmap(bitmap, 20/1);

		if (bitmap == null) {
			imageView.setImageResource(resId);
		} else {
			imageView.setImageBitmap(bitmap);
		}

		// int a = getBitmapSize(bitmap1);// 获取大小并返回
		// Log.v(TAG + "a", a + "");
	}

	// 获取Bitmap图片的大小
	public int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
																			// 12
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
	}

	// 下面两个方法用于对图片进行压缩
	private Bitmap setScaleBitmap(Bitmap photo, int SCALE) {
		if (photo != null) {
			// 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
			// 这里缩小了1/2,但图片过大时仍然会出现加载不了,但系统中一个BITMAP最大是在10M左右,我们可以根据BITMAP的大小
			// 根据当前的比例缩小,即如果当前是15M,那如果定缩小后是6M,那么SCALE= 15/6
			Bitmap smallBitmap = zoomBitmap(photo, photo.getWidth() / SCALE,
					photo.getHeight() / SCALE);
			// 释放原始图片占用的内存，防止out of memory异常发生
			photo.recycle();
			return smallBitmap;
		}
		return null;
	}

	public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	public Bitmap loadImageAsyn(String path, ImageCallback callback) {
		// 判断缓存中是否已经存在该图片
		Log.v(TAG + "b", "b");

		Log.v(TAG + "true or false", caches.containsKey(path) + "");

		Log.v(TAG + "true or false", caches.size() + "");

		if (caches.containsKey(path)) {
			Log.v(TAG + "劳动节", "圣诞节");
			// 取出软引用
			SoftReference<Bitmap> rf = caches.get(path);
			// 通过软引用，获取图片
			Bitmap bitmap = rf.get();
			// 如果该图片已经被释放，则将该path对应的键从Map中移除掉
			if (bitmap == null) {
				caches.remove(path);
			} else {
				// 如果图片未被释放，直接返回该图片
				Log.i(TAG, "return image in cache" + path);
				return bitmap;
			}
		} else {
			// 如果缓存中不存在该图片，则创建图片下载任务
			Task task = new Task();
			task.path = path;
			task.callback = callback;
			Log.i(TAG, "new Task ," + path);
			if (!taskQueue.contains(task)) {
				taskQueue.add(task);
				// 唤醒任务下载队列
				synchronized (runnable) {
					runnable.notify();
				}
			}
		}

		// 缓存中没有图片则返回null
		return null;
	}

	/*private Runnable saveFileRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				saveFile(mBitmap, nicknameString);
				Log.v(TAG + "notification", "图片保存成功");
			} catch (IOException e) {
				Log.v(TAG + "notification", "图片保存失败");
				e.printStackTrace();
			}
		}

	};*/

	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 *//*
	public void saveFile(Bitmap bm, String fileName) throws IOException {
		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ALBUM_PATH + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}*/

	/**
	 * 
	 * @param imageView
	 * @param resId
	 *            图片加载完成前显示的图片资源ID
	 * @return
	 */
	private ImageCallback getImageCallback(final ImageView imageView,
			final int resId) {
		return new ImageCallback() {

			public void loadImage(String path, Bitmap bitmap) {
				// TODO Auto-generated method stub

				Log.v(TAG + "imageView.getId().toString()", imageView.getId()
						+ "");

				Log.v(TAG + "imageView.getTag().toString()", imageView.getTag()
						.toString() + "");
				Log.v(TAG + "path", path + "");
				if (path.equals(imageView.getTag().toString())) {
					imageView.setImageBitmap(bitmap);
				} else {
					imageView.setImageResource(resId);
				}
			}
		};
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 子线程中返回的下载完成的任务
			Task task = (Task) msg.obj;
			// 调用callback对象的loadImage方法，并将图片路径和图片回传给adapter
			task.callback.loadImage(task.path, task.bitmap);
		}

	};

	private Runnable runnable = new Runnable() {
		public void run() {
			while (isRunning) {
				// 当队列中还有未处理的任务时，执行下载任务
				while (taskQueue.size() > 0) {
					// 获取第一个任务，并将之从任务队列中删除
					Task task = taskQueue.remove(0);
					// 将下载的图片添加到缓存
					task.bitmap = PicUtil.getbitmap(task.path);
					Log.v(TAG + "范冰冰", "范冰冰");
					caches.put(task.path,
							new SoftReference<Bitmap>(task.bitmap));

//					new Thread(saveFileRunnable).start();

					Log.v(TAG + "ahsdihasidhasihdia", caches.size() + "");
					Log.v(TAG + "李冰冰", "李冰冰");
					if (handler != null) {
						// 创建消息对象，并将完成的任务添加到消息对象中
						Message msg = handler.obtainMessage();
						msg.obj = task;
						// 发送消息回主线程
						handler.sendMessage(msg);
					}
				}

				// 如果队列为空,则令线程等待
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	};

	// 回调接口
	public interface ImageCallback {
		void loadImage(String path, Bitmap bitmap);
	}

	class Task {
		// 下载任务的下载路径
		String path;
		// 下载的图片
		Bitmap bitmap;
		// 回调对象
		ImageCallback callback;

		@Override
		public boolean equals(Object o) {
			Task task = (Task) o;
			return task.path.equals(path);
		}
	}
}