package com.food.food_order_Delivaryboy.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    private Context _context;
    static String current_connection="";
    public ConnectionDetector(Context context){
        this._context = context;
    }
 
    /**
     * Checking for all possible internet providers
     * **/
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null){
              NetworkInfo info = connectivity.getActiveNetworkInfo();
              if (info != null){
                  if (info.getType()== ConnectivityManager.TYPE_WIFI){
                      current_connection="wifi";
                      return true;
                  }else if(info.getType()== ConnectivityManager.TYPE_MOBILE){
                      current_connection="mobile";
                      return true;
                  }else {
                      current_connection=null;
                      return false;
                  }
              }
          }
          return false;
    }

    public static String getConnectivityType(){
        return current_connection;
    }
}
