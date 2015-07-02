package com.zb.secondary_market.custom;

import android.content.BroadcastReceiver;  
import android.content.Context;  
import android.content.Intent;  
import android.net.ConnectivityManager;  
  
public class NetCheckReceiver extends BroadcastReceiver{  
  
    //android 中网络变化时所发的Intent的名字  
    private static final String netACTION="android.net.conn.CONNECTIVITY_CHANGE";  
    @Override  
    public void onReceive(Context context, Intent intent) {  
  
        if(intent.getAction().equals(netACTION)){  
    //Intent中ConnectivityManager.EXTRA_NO_CONNECTIVITY这个关键字表示着当前是否连接上了网络  
    //true 代表网络断开   false 代表网络没有断开  
        	
//            if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)){  
//                aActivity.stopFlag = true;  
//            }else{  
//                aActivity.stopFlag = false;  
//            }  
        	
        }  
    }  
}  