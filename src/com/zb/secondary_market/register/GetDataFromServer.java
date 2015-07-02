package com.zb.secondary_market.register;
import java.util.ArrayList;

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

public class GetDataFromServer {//implements Runnable
	private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟    
    private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟   
    private String head;
    private ArrayList<NameValuePair> params;
 
	public static String getData(String head, ArrayList<NameValuePair> params){
		String getInfoMsg = null; 
		String useridString, passwordString;
		
		String urlStr = "http://114.214.166.161:8000/"+head; //211.149.219.102  192.168.112.9
        HttpPost request = new HttpPost(urlStr); 
        try{  
            //设置请求参数项  
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
            BasicHttpParams httpParams = new BasicHttpParams();  
            HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
            HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
            HttpClient client = new DefaultHttpClient(httpParams);  
            //执行请求返回相应  
            HttpResponse response = client.execute(request); 
            //判断是否请求成功  
            if(response.getStatusLine().getStatusCode()==200){   
            	getInfoMsg = EntityUtils.toString(response.getEntity());
            }
        }catch(Exception e){;
        	getInfoMsg = "network error";
            e.printStackTrace();  
        }      
		return getInfoMsg;
	} 
}
