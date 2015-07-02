package com.zb.secondary_market.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetApi {

	public static String post(String url,String params){
		String result = "";
		PrintWriter pw = null; 
		HttpURLConnection conn = null;
        BufferedReader br = null;
        try{
        	URL realUrl = new URL(url);
            conn = (HttpURLConnection)realUrl.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            pw = new PrintWriter(conn.getOutputStream());
            pw.print(params);
            pw.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        	conn.disconnect();
        }catch(Exception e){
        	e.printStackTrace();
        	result = "failed";
        }finally{
            try{
                if(pw!=null)
                	pw.close();
                if(br!=null)
                	br.close();
            }
            catch(IOException ex){
                ex.printStackTrace();               
            }
        }
    	return result;
	}
	
	public static InputStream getInputStreamByUrl(String connurl){
		InputStream is = null;
		try{
			URL url=new URL(connurl);
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(30*1000);
			conn.setDoInput(true);
			conn.connect();
			is=conn.getInputStream();
		}catch(Exception e){
			e.printStackTrace();
		}
		return is;
	}
	
	public static String getHtmlContent(String surl, String encode) {  
		String content="";
        StringBuffer contentBuffer = new StringBuffer();  
        URL url = null;        
        HttpURLConnection con = null;  
        try {  
        	url = new URL(surl);
            con = (HttpURLConnection) url.openConnection();  
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");// IE�����������  
            con.setConnectTimeout(60000);  
            con.setReadTimeout(60000);  
            
            InputStream inStr = con.getInputStream();  
            InputStreamReader istreamReader = new InputStreamReader(inStr, encode);  
            BufferedReader buffStr = new BufferedReader(istreamReader);  
  
            String str = null;  
            while ((str = buffStr.readLine()) != null)  
                contentBuffer.append(str);  
            inStr.close(); 
            
            content = contentBuffer.toString();
        } catch (Exception e) {  
            e.printStackTrace();  
            contentBuffer = null;  
            content = "";
            System.out.println("error: " + url.toString());  
        } finally {  
            con.disconnect();  
        }  
        return content;  
    }  
}
