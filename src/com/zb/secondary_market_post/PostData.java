package com.zb.secondary_market_post;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class PostData {

	private static String URL_postdata = "http://202.38.73.228:8000/reverse";
	private static String ns;
	private static final int REQUEST_TIMEOUT = 10*1000;//��������ʱ10����    
    private static final int SO_TIMEOUT = 10*1000;  //���õȴ����ݳ�ʱʱ��10����   
  //  private static Handler mhandler=new Handler();
	private static URL url_postdata = null;
	private static  String data1 = "",data2 = "",data3 = "",data4 = "",data5= "";
	public  static String receive,result;
	//public static String ID = "999999";
	
public static void getData(Handler nhandler)  {
	Log.v("hhhh", "ע����Ϣ���ͳɹ�!");
	final Handler mhandler=nhandler;
	try {
		url_postdata = new URL(URL_postdata);
	} catch (MalformedURLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
	 if(url_postdata  != null) {
			new Thread(new Runnable() {
	            public void run()
	            {
	            	try {
	            		// str = URLEncoder.encode(str, "utf-8");
	        			String end = "\r\n";			//�س�+����
	        			HttpURLConnection urlConnection = (HttpURLConnection) url_postdata .openConnection();
	        			urlConnection.setDoInput(true);
	        			urlConnection.setDoOutput(true);
	        			urlConnection.setUseCaches(false);
	        			urlConnection.setRequestMethod("POST");
	        			urlConnection.setRequestProperty("Connection", "Keep-Alive");
	        			urlConnection.setRequestProperty("Charset","UTF-8");
	        			urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");	//����������ͷ���趨������������
	        			urlConnection.connect();
	        			OutputStream outputStream = urlConnection.getOutputStream();
	        			DataOutputStream dos = new DataOutputStream(outputStream);
	        			
	        			
	        			dos.writeBytes("Content-Disposition:form-data;flag="+data1+end);

	        			InputStream is = urlConnection.getInputStream();
        				InputStreamReader isr = new InputStreamReader(is, "utf-8");
        				BufferedReader br = new BufferedReader(isr);
        				result = br.readLine();

        				Log.i("TAG", "result:"+result);
        				is.close();
	        
        				 String[] surgerycontents=result.split("\\*");
	        			int responseCode = urlConnection.getResponseCode();
	        			if (responseCode == 200) {
	        				System.out.print("rrrrrrrrrrrrrrr");
	        			Log.v("hhhh", "���ͳɹ�");
	        			
	        			 Log.d("XXXXXXXXX-3333333", "before");
	       	          //return result;
	       	           Message msg = mhandler.obtainMessage();  
	                  //   msg.what = 0;
	                  //   msg.obj=result;
	       	         // Message msg = new Message();
	                     Bundle b = new Bundle();// �������
	                     b.putString("result",result);
	                    
	                 Log.d("XXXXXXXXX-55555555",result);   
	                     msg.setData(b);
	       			  mhandler.sendMessage(msg);
	       			  
	       	         Log.d("XXXXXXXXX-44444444","after");
	        			
	       	         
	        			}
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}
	            }
			}).start();
		}
		
		else {
			Log.i("", "urlΪ��!");
		}
	        // return ns;
       
	}


public static String getData(ArrayList<NameValuePair> params) { 
	String getInfoMsg = null; 
	String URL_postdata = "http://192.168.96.1:8000/wrap"; 
    //192.168.112.9  192.168.112.9  114.214.167.95
    HttpPost request = new HttpPost(URL_postdata); 
    try{  
        //�������������  
        request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
        BasicHttpParams httpParams = new BasicHttpParams();  
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
        HttpClient client = new DefaultHttpClient(httpParams);  
        //ִ�����󷵻���Ӧ  
        HttpResponse response = client.execute(request); 
        //�ж��Ƿ�����ɹ�  
        if(response.getStatusLine().getStatusCode()==200){   
        	getInfoMsg = EntityUtils.toString(response.getEntity());
        	System.out.println("get data from server:"+getInfoMsg);
        //	Log.i(tag,"<<<<<--------judge---------->>>>>");
        }
    }catch(Exception e){;
    	getInfoMsg = "network error";
        e.printStackTrace();  
    }      
	return getInfoMsg;
}}
	
	/* if(url_postdata  != null) {
			new Thread(new Runnable() {
	            public void run()
	            {
	            	try {
	        			String end = "\r\n";			//�س�+����
	        			HttpURLConnection urlConnection = (HttpURLConnection) url_postdata .openConnection();
	        			urlConnection.setDoInput(true);
	        			urlConnection.setDoOutput(true);
	        			urlConnection.setUseCaches(false);
	        			urlConnection.setRequestMethod("POST");
	        			urlConnection.setRequestProperty("Connection", "Keep-Alive");
	        			urlConnection.setRequestProperty("Charset","UTF-8");
	        			urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");	//����������ͷ���趨������������
	        			urlConnection.connect();
	        			OutputStream outputStream = urlConnection.getOutputStream();
	        			DataOutputStream dos = new DataOutputStream(outputStream);
	        			//dos.writeBytes("Content-Disposition:form-data)
	        			dos.writeBytes("Content-Disposition:form-data;"+data+end);
	        			Log.i("", "ע����Ϣ���ͳɹ�!");
	        			
	        			int responseCode = urlConnection.getResponseCode();
	        			if (responseCode == 200) {
	        				//ID = changeInputStream(urlConnection.getInputStream(),"utf-8");
	        			Log.i("", "���ͳɹ�");
	        				
	        				
	        			}
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}
	            }
			}).start();
		}
		
		else {
			Log.i("", "url_registerΪ��!");
		}*/
	//}
//}



//dos.writeBytes("Content-Disposition:form-data;"+data+end);


