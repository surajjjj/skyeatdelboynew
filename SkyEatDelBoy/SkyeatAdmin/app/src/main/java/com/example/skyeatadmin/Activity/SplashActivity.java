package com.example.skyeatadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.skyeatadmin.Activity.DelivaryBoy.DeliveryBoyMainActivity;
import com.example.skyeatadmin.Activity.Hotel.HotelMainActivity;
import com.example.skyeatadmin.CheckInternet;
import com.example.skyeatadmin.Constant;
import com.example.skyeatadmin.Model.Version;
import com.example.skyeatadmin.NetworkUtils;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    CheckInternet checkInternet;

    LinearLayout linLayout;
    public static String versionCode,delBoyVersionCode;
    public static String versionName,delBoyVersionName;
    String email,password,login;
    View rootView;
    private NetworkUtils networkUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        linLayout=findViewById(R.id.linLayout);
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);
//        networkUtils = new NetworkUtils(this);
//        networkUtils.registerNetworkCallback();
//        getDelBoyVersion();
      //  getVersion();



       checkInternet=new CheckInternet(getApplicationContext(),rootView);
        checkInternet.registerNetworkCallback();

       if(!checkInternet.isNetworkAvailable(getApplicationContext()))
       {
           new AlertDialog.Builder(SplashActivity.this)
                   .setMessage("Internet connection not found,\nPlease try again.")
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int i) {
                           dialog.dismiss();
                           finish();
                       }
                   }).setCancelable(true).show();
           checkInternet.registerNetworkCallback();

       }
        else
        {
            getDelBoyVersion();
       }
    }
    public void updateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Update Dialog!!!");
        builder.setMessage("Updates are available. Please update this application ");

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform any necessary actions and close the activity
                //super.onBackPressed();
               // dialog.dismiss();
                //finishAffinity();
                openPlayStoreForUpdate();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog and continue with the activity
                dialog.dismiss();
                finishAffinity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void getDelBoyVersion()
    {
        try
        {
            RetrofitClient.getInstance().getMyApi().getDeliveryBoyVersion("get_del_boy_version").enqueue(new Callback<Version>() {
                @Override
                public void onResponse(Call<Version> call, Response<Version> response) {
                    assert response.body() != null;
                    delBoyVersionCode=response.body().getData().get(0).getVersion_code();
                    delBoyVersionName=response.body().getData().get(0).getVersion_name();
                    if(delBoyVersionCode != null && delBoyVersionName != null)
                    {
                        getVersion();
                    }

                }

                @Override
                public void onFailure(Call<Version> call, Throwable t) {

                }
            });

        }
        catch (Exception e)
        {
            Log.d("",e.getMessage());
        }
    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        networkUtils.unregisterNetworkCallback();
//    }

    private void openPlayStoreForUpdate() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id="+ Constant.APP_PACKAGE_NAME));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // If the Play Store app is not installed, open the Play Store website
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+ Constant.APP_PACKAGE_NAME));
            startActivity(intent);
        }
    }
    public void getVersion()
    {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = String.valueOf(packageInfo.versionCode);
            versionName = packageInfo.versionName;

            // Use versionCode and versionName as needed
            // For example, you can display them in logs or set them on TextViews
            Log.d("AppVersion", "Version Code: " + versionCode);
            Log.d("AppVersion", "Version Name: " + versionName);

            if((versionCode.equals(delBoyVersionCode)) && (versionName.equals(delBoyVersionName)))
            {
                sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                if(sharedPreferences.contains("email"))
                {
                    email=sharedPreferences.getString("email",null);
                }

                if(sharedPreferences.contains("password"))
                {
                    password=sharedPreferences.getString("password",null);
                }

                if(sharedPreferences.contains("login"))
                {
                    login=sharedPreferences.getString("login",null);
                }
                if (email == null && password == null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Optional: Call finish() to close the splash activity and prevent the user from going back to it.
                        }
                    }, 1000);

                }
                else
                {
                    Intent intent=new Intent(SplashActivity.this, DeliveryBoyMainActivity.class);
                    startActivity(intent);
                    finish();
//                    if(Objects.equals(login, "Admin"))
//                    {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }, 1000);
//                    }
//                    else if (Objects.equals(login, "DeliveryBoy")) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Intent intent=new Intent(SplashActivity.this, DeliveryBoyMainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }, 1000);
//
//                    }
//
//                    else if (Objects.equals(login, "Hotel")) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Intent intent=new Intent(SplashActivity.this, HotelMainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }, 1000);
//
//                    }

                }
            }
            else {
                updateDialog();
            }



        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

}