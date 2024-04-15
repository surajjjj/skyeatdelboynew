package com.food.food_order_Delivaryboy.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.food.food_order_Delivaryboy.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class MyFirebaseMessagingService<v> extends FirebaseMessagingService {



    NotificationCompat.Builder notifiaction;
    Bitmap bitmap;
    String id="Default";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("title"));
        String title = "You Have New Order Please Check...";
        String body = remoteMessage.getData().get("body");
        getnotifiacation(title,body);
    }

    @Override
    public void onNewToken(String token) {

    }




    private void getnotifiacation(String title, String body)
    {

        Intent intent=new Intent(this,MyFirebaseMessagingService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        notifiaction=new NotificationCompat.Builder(this,id)
                .setAutoCancel(true)
                .setContentTitle("Hey" + title)
                .setContentText(body)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.skyeat_logo)
                .setVibrate(new long[]{100,200,300,400,500,600,700,800,900})
                .setContentIntent(pendingIntent);
        MediaPlayer mp;
        mp =MediaPlayer.create(MyFirebaseMessagingService.this, R.raw.land_line);
        mp.start();

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notifiaction.setSound(uri);

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id,"notifiaction",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100,200,300,400,500});
            notificationManager.createNotificationChannel(channel);

        }

        notificationManager.notify(0,notifiaction.build());

    }



    public static Bitmap getbitmap(String imgurl)
    {
        try
        {
            URL url=new URL(imgurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
            return  bitmap;

        }catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}