package com.zb.secondary_market.update;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.zb.secondary_market.net.NetApi;

public class UpdateInfoParser {

	public UpdateInfo getUpdateInfo(String url){
		UpdateInfo updateInfo = new UpdateInfo();
		try{
			InputStream is = NetApi.getInputStreamByUrl(url);
			updateInfo = parseUpdataInfo(is);
		}catch(Exception e){
			e.printStackTrace();
			updateInfo = null;
		}
		return updateInfo;
	}
	
	public  UpdateInfo parseUpdataInfo(InputStream is) throws Exception{  
	    XmlPullParser  parser = Xml.newPullParser();    
	    parser.setInput(is, "utf-8");//设置解析的数据源   
	    int type = parser.getEventType();  
	    UpdateInfo info = new UpdateInfo();//实体  
	    while(type != XmlPullParser.END_DOCUMENT ){  
	        switch (type) {  
	        case XmlPullParser.START_TAG:  
	            if("version".equals(parser.getName())){  
	                info.setVersionName(parser.nextText()); //获取版本号  
	            }else if ("url".equals(parser.getName())){  
	                info.setUrl(parser.nextText()); //获取要升级的APK文件URL  
	            }else if ("description".equals(parser.getName())){  
	                info.setDescription(parser.nextText()); //获取该文件的描述信息  
	            }  
	            break;  
	        }  
	        type = parser.next();  
	    }  
	    return info;  
	}  
}
