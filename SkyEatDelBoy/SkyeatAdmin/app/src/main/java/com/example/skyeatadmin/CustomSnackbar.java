package com.example.skyeatadmin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class CustomSnackbar
{
    public static void showSnackbar(Context context, View view, String message,boolean isConnected) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.custom_snackbar, null);

        TextView textView = customView.findViewById(R.id.txtMsg);
        textView.setText(message);
        ImageView imageView = customView.findViewById(R.id.imgWifi);
        if(isConnected)
        {
            imageView.setImageResource(R.drawable.baseline_wifi_24);
        }
        else {
            imageView.setImageResource(R.drawable.baseline_wifi_off_24);
        }


        layout.setBackgroundColor(Color.BLACK);
        layout.addView(customView, 0);
        snackbar.show();
    }
}
