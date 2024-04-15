package com.example.skyeatadmin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

public class CheckInternet
{
   private Context context;
   private View rootView;

    public CheckInternet(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    public boolean isNetworkConnected=false;

    public void registerNetworkCallback()
    {
        try {

            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(@NonNull Network network)
                {
                   if(isNetworkConnected)
                   {
                       Toast.makeText(context, "Online", Toast.LENGTH_SHORT).show();
//                       //CustomSnackbar.showSnackbar(context,rootView,"Your internet connection restored",true);
//                       Snackbar snackbar = Snackbar.make(rootView, "Your internet connection restored", Snackbar.LENGTH_SHORT);
//                       snackbar.show();
                   }

                }

                @Override
                public void onLost(@NonNull Network network) {
                    //   super.onLost(network);
                    isNetworkConnected=true;
                    Toast.makeText(context, "Offline", Toast.LENGTH_SHORT).show();
//                   // CustomSnackbar.showSnackbar(context,rootView,"Your internet connection lose",false);
//                    Snackbar snackbar = Snackbar.make(rootView, "Your internet connection lose", Snackbar.LENGTH_SHORT);
//                    snackbar.show();

                }
            });
        }
        catch (Exception e)
        {
            isNetworkConnected=false;
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnected();

    }
}
