package com.zb.secondary_market.register;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.zb.secondary_market.custom.UsefulString;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpPostFile {
	private static final String TAG = "HttpPostFile";
	private static final int TIME_OUT = 10 * 1000; // ��ʱʱ��
	private static final String CHARSET = "utf-8"; // ���ñ���
	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";

	public static String change_headimg(String params, File file) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/changeheadimage";
		String RequestURL =  UsefulString.urlString + "changeheadimage";
		String filename = file.getName();

		String info = params;

		Log.v(TAG, filename);

		String picturename = filename.split("/")[filename.split("/").length - 1];

		Log.v(TAG, picturename);

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputStream = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputStream);

			Log.v(TAG, info);

			if (info != null) {
				// 构建图片信息上传
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);
			}

			if (file != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key
				 * �ſ��Եõ���Ӧ���ļ� filename���ļ������֣�������׺���� ����:abc.png
				 */

				sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ picturename + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[102400];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
				 */
				int res = conn.getResponseCode();
				if (res == 200) {
					// return SUCCESS;
					// 获服务器取数据
					InputStream inputStream = conn.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(
							inputStream);
					BufferedReader reader = new BufferedReader(
							inputStreamReader);// 读字符串用的。
					String inputLine = reader.readLine();

					Log.v(TAG + "inputline", inputLine);
					// // 使用循环来读取获得的数据，把数据都村到result中了
					// while (((inputLine = reader.readLine()) != null)) {
					// // 我们在每一行后面加上一个"\n"来换行
					// result += inputLine + "\n";
					// }
					reader.close();// 关闭输入流

					return inputLine;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
	
	public static String uploadFile(String params, File file) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/register";
		String RequestURL =  UsefulString.urlString + "register";
		String filename = file.getName();

		String info = params;

		Log.v(TAG, filename);

		String picturename = filename.split("/")[filename.split("/").length - 1];

		Log.v(TAG, picturename);

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputStream = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputStream);

			Log.v(TAG, info);

			if (info != null) {
				// 构建图片信息上传
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);
			}

			if (file != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key
				 * �ſ��Եõ���Ӧ���ļ� filename���ļ������֣�������׺���� ����:abc.png
				 */

				sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ picturename + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[102400];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
				 */
				int res = conn.getResponseCode();
				if (res == 200) {
					// return SUCCESS;
					// 获服务器取数据
					InputStream inputStream = conn.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(
							inputStream);
					BufferedReader reader = new BufferedReader(
							inputStreamReader);// 读字符串用的。
					String inputLine = reader.readLine();

					Log.v(TAG + "inputline", inputLine);
					// // 使用循环来读取获得的数据，把数据都村到result中了
					// while (((inputLine = reader.readLine()) != null)) {
					// // 我们在每一行后面加上一个"\n"来换行
					// result += inputLine + "\n";
					// }
					reader.close();// 关闭输入流

					return inputLine;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static String uploadFile_secondary(String params, List<File> file) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/secondrelease";
		String RequestURL =  UsefulString.urlString + "secondrelease";
		String[] filename = new String[4];
		String[] picturename = new String[4];

		// Log.v("传入文件数组的长度", file.length + "");

		for (int i = 0; i < file.size(); i++) {
			filename[i] = file.get(i).getName();
			Log.v(TAG, filename[i]);

			picturename[i] = filename[i].split("/")[filename[i].split("/").length - 1];
			Log.v(TAG, picturename[i]);
		}

		String info = params;

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//分块协议
//			conn.setChunkedStreamingMode(0);
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputSteam = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputSteam);

			Log.v(TAG, info);

			// 构建图片信息上传
			if (info != null) {
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);
			}

			for (int i = 0; i < file.size(); i++) {

				if (file.get(i) != null) {
					dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
					StringBuffer sb = new StringBuffer();
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"file\" ; filename=\""
							+ picturename[i] + "\"" + LINE_END);
					sb.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINE_END);
					sb.append(LINE_END);

					dos.write(sb.toString().getBytes());

//					Bitmap bitmap = BitmapFactory.decodeFile(file.get(i)
//							.getPath());

//					Log.v(TAG + "bitmap size", bitmap.getRowBytes() * bitmap.getHeight() + "");
					
//					Bitmap bitmap1 = comp(bitmap);
					
//					Log.v(TAG + "bitmap size", bitmap1.getRowBytes() * bitmap1.getHeight() + "");
					
					InputStream is = new FileInputStream(file.get(i));
//					Bitmap2Bytes(bitmap);
					
					byte[] bytes = new byte[819200];
					int len = 0;
					
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					Log.v(TAG, len + "");
					
					is.close();
					dos.write(LINE_END.getBytes());

				}
			}
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();
			dos.write(end_data);
			dos.flush();

			int res = conn.getResponseCode();
			if (res == 200) {
				// return SUCCESS;
				// 获服务器取数据
				InputStream inputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
				String inputLine = reader.readLine();

				// // 使用循环来读取获得的数据，把数据都村到result中了
				// while (((inputLine = reader.readLine()) != null)) {
				// // 我们在每一行后面加上一个"\n"来换行
				// result += inputLine + "\n";
				// }
				reader.close();// 关闭输入流

				return inputLine;
			}
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}

	public static String uploadFile_friend(String params, List<File> file) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/friendrelease";
		String RequestURL =  UsefulString.urlString + "friendrelease";
		String[] filename = new String[4];
		String[] picturename = new String[4];

		// Log.v("传入文件数组的长度", file.length + "");

		for (int i = 0; i < file.size(); i++) {
			filename[i] = file.get(i).getName();
			Log.v(TAG, filename[i]);

			picturename[i] = filename[i].split("/")[filename[i].split("/").length - 1];
			Log.v(TAG, picturename[i]);
		}

		String info = params;

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//分块协议
//			conn.setChunkedStreamingMode(0);
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputSteam = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputSteam);

			Log.v(TAG, info);

			// 构建图片信息上传
			if (info != null) {
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);
			}

			for (int i = 0; i < file.size(); i++) {

				if (file.get(i) != null) {
					dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
					StringBuffer sb = new StringBuffer();
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"file\" ; filename=\""
							+ picturename[i] + "\"" + LINE_END);
					sb.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINE_END);
					sb.append(LINE_END);

					dos.write(sb.toString().getBytes());

//					Bitmap bitmap = BitmapFactory.decodeFile(file.get(i)
//							.getPath());

//					Log.v(TAG + "bitmap size", bitmap.getRowBytes() * bitmap.getHeight() + "");
					
//					Bitmap bitmap1 = comp(bitmap);
					
//					Log.v(TAG + "bitmap size", bitmap1.getRowBytes() * bitmap1.getHeight() + "");
					
					InputStream is = new FileInputStream(file.get(i));
//					Bitmap2Bytes(bitmap);
					
					byte[] bytes = new byte[819200];
					int len = 0;
					
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					Log.v(TAG, len + "");
					
					is.close();
					dos.write(LINE_END.getBytes());

				}
			}
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();
			dos.write(end_data);
			dos.flush();

			int res = conn.getResponseCode();
			if (res == 200) {
				// return SUCCESS;
				// 获服务器取数据
				InputStream inputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
				String inputLine = reader.readLine();

				// // 使用循环来读取获得的数据，把数据都村到result中了
				// while (((inputLine = reader.readLine()) != null)) {
				// // 我们在每一行后面加上一个"\n"来换行
				// result += inputLine + "\n";
				// }
				reader.close();// 关闭输入流

				return inputLine;
			}
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
	
	public static String uploadFile_compaigner(String params) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/activityrelease";
		String RequestURL =  UsefulString.urlString + "activityrelease";
		String[] filename = new String[4];
		String[] picturename = new String[4];

		// Log.v("传入文件数组的长度", file.length + "");

//		for (int i = 0; i < file.size(); i++) {
//			filename[i] = file.get(i).getName();
//			Log.v(TAG, filename[i]);
//
//			picturename[i] = filename[i].split("/")[filename[i].split("/").length - 1];
//			Log.v(TAG, picturename[i]);
//		}

		String info = params;

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//分块协议
//			conn.setChunkedStreamingMode(0);
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputSteam = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputSteam);

			Log.v(TAG, info);

			// 构建图片信息上传
			if (info != null) {
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);
			}

			/*for (int i = 0; i < file.size(); i++) {

				if (file.get(i) != null) {
					dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
					StringBuffer sb = new StringBuffer();
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"file\" ; filename=\""
							+ picturename[i] + "\"" + LINE_END);
					sb.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINE_END);
					sb.append(LINE_END);

					dos.write(sb.toString().getBytes());

//					Bitmap bitmap = BitmapFactory.decodeFile(file.get(i)
//							.getPath());

//					Log.v(TAG + "bitmap size", bitmap.getRowBytes() * bitmap.getHeight() + "");
					
//					Bitmap bitmap1 = comp(bitmap);
					
//					Log.v(TAG + "bitmap size", bitmap1.getRowBytes() * bitmap1.getHeight() + "");
					
					InputStream is = new FileInputStream(file.get(i));
//					Bitmap2Bytes(bitmap);
					
					byte[] bytes = new byte[819200];
					int len = 0;
					
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					Log.v(TAG, len + "");
					
					is.close();
					dos.write(LINE_END.getBytes());

				}
			}*/
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();
			dos.write(end_data);
			dos.flush();

			int res = conn.getResponseCode();
			if (res == 200) {
				// return SUCCESS;
				// 获服务器取数据
				InputStream inputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
				String inputLine = reader.readLine();

				// // 使用循环来读取获得的数据，把数据都村到result中了
				// while (((inputLine = reader.readLine()) != null)) {
				// // 我们在每一行后面加上一个"\n"来换行
				// result += inputLine + "\n";
				// }
				reader.close();// 关闭输入流

				return inputLine;
			}
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
	
	public static String uploadFile_compaigner(String params, List<File> file) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/activityrelease";
		String RequestURL =  UsefulString.urlString + "activityrelease";
		String[] filename = new String[4];
		String[] picturename = new String[4];

		// Log.v("传入文件数组的长度", file.length + "");

		for (int i = 0; i < file.size(); i++) {
			filename[i] = file.get(i).getName();
			Log.v(TAG, filename[i]);

			picturename[i] = filename[i].split("/")[filename[i].split("/").length - 1];
			Log.v(TAG, picturename[i]);
		}

		String info = params;

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//分块协议
//			conn.setChunkedStreamingMode(0);
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputSteam = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputSteam);

			Log.v(TAG, info);

			// 构建图片信息上传
			if (info != null) {
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);
			}

			for (int i = 0; i < file.size(); i++) {

				if (file.get(i) != null) {
					dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
					StringBuffer sb = new StringBuffer();
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINE_END);
					sb.append("Content-Disposition: form-data; name=\"file\" ; filename=\""
							+ picturename[i] + "\"" + LINE_END);
					sb.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINE_END);
					sb.append(LINE_END);

					dos.write(sb.toString().getBytes());

//					Bitmap bitmap = BitmapFactory.decodeFile(file.get(i)
//							.getPath());

//					Log.v(TAG + "bitmap size", bitmap.getRowBytes() * bitmap.getHeight() + "");
					
//					Bitmap bitmap1 = comp(bitmap);
					
//					Log.v(TAG + "bitmap size", bitmap1.getRowBytes() * bitmap1.getHeight() + "");
					
					InputStream is = new FileInputStream(file.get(i));
//					Bitmap2Bytes(bitmap);
					
					byte[] bytes = new byte[819200];
					int len = 0;
					
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					Log.v(TAG, len + "");
					
					is.close();
					dos.write(LINE_END.getBytes());

				}
			}
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();
			dos.write(end_data);
			dos.flush();

			int res = conn.getResponseCode();
			if (res == 200) {
				// return SUCCESS;
				// 获服务器取数据
				InputStream inputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
				String inputLine = reader.readLine();

				// // 使用循环来读取获得的数据，把数据都村到result中了
				// while (((inputLine = reader.readLine()) != null)) {
				// // 我们在每一行后面加上一个"\n"来换行
				// result += inputLine + "\n";
				// }
				reader.close();// 关闭输入流

				return inputLine;
			}
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
	
	public static String upload_nm_pw(String params) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/login";
		String RequestURL =  UsefulString.urlString + "login";

		String info = params;

		// String picturename =
		// filename.split("/")[filename.split("/").length-1];
		// Log.v(TAG, picturename);

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputSteam = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputSteam);

			Log.v(TAG, info);

			if (info != null) {
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);

				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();

				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();
				String res1 = conn.getResponseMessage();

				Log.v(TAG, res + "");

				if (res == 200) {
					// return SUCCESS;
					// 获服务器取数据
					InputStream inputStream = conn.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(
							inputStream);
					BufferedReader reader = new BufferedReader(
							inputStreamReader);// 读字符串用的。
					String inputLine = reader.readLine();

					Log.v(TAG + "inputline", inputLine);

					// // 使用循环来读取获得的数据，把数据都村到result中了
					// while (((inputLine = reader.readLine()) != null)) {
					// // 我们在每一行后面加上一个"\n"来换行
					// result += inputLine + "\n";
					// }
					reader.close();// 关闭输入流

					return inputLine;
				}
			}

			// if (file != null) {
			// StringBuffer sb = new StringBuffer();
			// sb.append(PREFIX);
			// sb.append(BOUNDARY);
			// sb.append(LINE_END);
			// /**
			// * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
			// * filename���ļ������֣�������׺���� ����:abc.png
			// */
			//
			// sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
			// + picturename + "\"" + LINE_END);
			// sb.append("Content-Type: application/octet-stream; charset="
			// + CHARSET + LINE_END);
			// sb.append(LINE_END);
			// dos.write(sb.toString().getBytes());
			// InputStream is = new FileInputStream(file);
			// byte[] bytes = new byte[1024];
			// int len = 0;
			// while ((len = is.read(bytes)) != -1) {
			// dos.write(bytes, 0, len);
			// }
			// is.close();
			// dos.write(LINE_END.getBytes());
			// byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
			// .getBytes();
			// dos.write(end_data);
			// dos.flush();
			// /**
			// * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
			// */
			// int res = conn.getResponseCode();
			// if (res == 200) {
			// return SUCCESS;
			// }
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
	
	public static String upload_only_gps_school(String params) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/gpsidentification";
		String RequestURL =  UsefulString.urlString + "gpsidentification";

		String info = params;

		// String picturename =
		// filename.split("/")[filename.split("/").length-1];
		// Log.v(TAG, picturename);

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputSteam = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputSteam);

			Log.v(TAG, info);

			if (info != null) {
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);

				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();

				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();
				String res1 = conn.getResponseMessage();

				Log.v(TAG, res + "");

				if (res == 200) {
					// return SUCCESS;
					// 获服务器取数据
					InputStream inputStream = conn.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(
							inputStream);
					BufferedReader reader = new BufferedReader(
							inputStreamReader);// 读字符串用的。
					String inputLine = reader.readLine();

					Log.v(TAG + "inputline", inputLine);

					// // 使用循环来读取获得的数据，把数据都村到result中了
					// while (((inputLine = reader.readLine()) != null)) {
					// // 我们在每一行后面加上一个"\n"来换行
					// result += inputLine + "\n";
					// }
					reader.close();// 关闭输入流

					return inputLine;
				}
			}

			// if (file != null) {
			// StringBuffer sb = new StringBuffer();
			// sb.append(PREFIX);
			// sb.append(BOUNDARY);
			// sb.append(LINE_END);
			// /**
			// * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
			// * filename���ļ������֣�������׺���� ����:abc.png
			// */
			//
			// sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
			// + picturename + "\"" + LINE_END);
			// sb.append("Content-Type: application/octet-stream; charset="
			// + CHARSET + LINE_END);
			// sb.append(LINE_END);
			// dos.write(sb.toString().getBytes());
			// InputStream is = new FileInputStream(file);
			// byte[] bytes = new byte[1024];
			// int len = 0;
			// while ((len = is.read(bytes)) != -1) {
			// dos.write(bytes, 0, len);
			// }
			// is.close();
			// dos.write(LINE_END.getBytes());
			// byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
			// .getBytes();
			// dos.write(end_data);
			// dos.flush();
			// /**
			// * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
			// */
			// int res = conn.getResponseCode();
			// if (res == 200) {
			// return SUCCESS;
			// }
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}

	//上传用户的log日志
		public static String upload_logs(String logs) {
			String BOUNDARY = "******"; //  ߽  ʶ        
			String PREFIX = "--", LINE_END = "\r\n";
			String CONTENT_TYPE = "multipart/form-data"; //         
//			String RequestURL = "http://222.195.78.112:8000/logs";
			String RequestURL =  UsefulString.urlString + "logs";

			String info = logs;

			try {
				URL url = new URL(RequestURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(TIME_OUT);
				conn.setConnectTimeout(TIME_OUT);
				conn.setDoInput(true); //           
				conn.setDoOutput(true); //          
				conn.setUseCaches(false); //       ʹ û   
				conn.setRequestMethod("POST"); //     ʽ
				conn.setRequestProperty("Charset", CHARSET); //    ñ   
				conn.setRequestProperty("connection", "keep-alive");
				conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
						+ BOUNDARY);

				OutputStream outputSteam = conn.getOutputStream();
				DataOutputStream dos = new DataOutputStream(outputSteam);

				Log.v(TAG, info);

				if (info != null) {
					dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
					StringBuffer ss = new StringBuffer("");
					ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
							+ "quiz" + "\"" + LINE_END);
					ss.append(LINE_END);
					dos.writeBytes(ss.toString());
					dos.write(logs.getBytes());
					dos.writeBytes(LINE_END);

					dos.write(LINE_END.getBytes());
					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
							.getBytes();
					dos.write(end_data);
					dos.flush();

					/**
					 * 获取响应码 200=成功 当响应成功，获取响应的流
					 */
					int res = conn.getResponseCode();
					String res1 = conn.getResponseMessage();

					Log.v(TAG, res + "");

					if (res == 200) {
						// return SUCCESS;
						// 获服务器取数据
						InputStream inputStream = conn.getInputStream();
						InputStreamReader inputStreamReader = new InputStreamReader(
								inputStream);
						BufferedReader reader = new BufferedReader(
								inputStreamReader);// 读字符串用的。
						String inputLine = reader.readLine();

						Log.v(TAG + "inputline", inputLine);

						// // 使用循环来读取获得的数据，把数据都村到result中了
						// while (((inputLine = reader.readLine()) != null)) {
						// // 我们在每一行后面加上一个"\n"来换行
						// result += inputLine + "\n";
						// }
						reader.close();// 关闭输入流

						return inputLine;
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return FAILURE;
		}
	
	public static String upload_chat(String params) {
		String BOUNDARY = "******"; // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������
//		String RequestURL = "http://222.195.78.112:8000/chat";
		String RequestURL =  UsefulString.urlString + "chat";

		String info = params;

		// String picturename =
		// filename.split("/")[filename.split("/").length-1];
		// Log.v(TAG, picturename);

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			OutputStream outputSteam = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputSteam);

			Log.v(TAG, info);

			if (info != null) {
				dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
				StringBuffer ss = new StringBuffer("");
				ss.append("Content-Disposition: form-data; name=\"info\";filename=\""
						+ "quiz" + "\"" + LINE_END);
				ss.append(LINE_END);
				dos.writeBytes(ss.toString());
				dos.write(params.getBytes());
				dos.writeBytes(LINE_END);

				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();

				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();
				String res1 = conn.getResponseMessage();

				Log.v(TAG, res + "");

				if (res == 200) {
					// return SUCCESS;
					// 获服务器取数据
					InputStream inputStream = conn.getInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(
							inputStream);
					BufferedReader reader = new BufferedReader(
							inputStreamReader);// 读字符串用的。
					String inputLine = reader.readLine();

					Log.v(TAG + "inputline", inputLine);

					// // 使用循环来读取获得的数据，把数据都村到result中了
					// while (((inputLine = reader.readLine()) != null)) {
					// // 我们在每一行后面加上一个"\n"来换行
					// result += inputLine + "\n";
					// }
					reader.close();// 关闭输入流

					return inputLine;
				}
			}

			// if (file != null) {
			// StringBuffer sb = new StringBuffer();
			// sb.append(PREFIX);
			// sb.append(BOUNDARY);
			// sb.append(LINE_END);
			// /**
			// * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
			// * filename���ļ������֣�������׺���� ����:abc.png
			// */
			//
			// sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
			// + picturename + "\"" + LINE_END);
			// sb.append("Content-Type: application/octet-stream; charset="
			// + CHARSET + LINE_END);
			// sb.append(LINE_END);
			// dos.write(sb.toString().getBytes());
			// InputStream is = new FileInputStream(file);
			// byte[] bytes = new byte[1024];
			// int len = 0;
			// while ((len = is.read(bytes)) != -1) {
			// dos.write(bytes, 0, len);
			// }
			// is.close();
			// dos.write(LINE_END.getBytes());
			// byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
			// .getBytes();
			// dos.write(end_data);
			// dos.flush();
			// /**
			// * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
			// */
			// int res = conn.getResponseCode();
			// if (res == 200) {
			// return SUCCESS;
			// }
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}
	
	private static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
}
