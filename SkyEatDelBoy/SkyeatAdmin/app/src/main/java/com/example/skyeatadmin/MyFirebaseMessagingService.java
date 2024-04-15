package com.example.skyeatadmin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "NewOrder";
    private static final int NOTIFICATION_ID = 100;
    Notification notification;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        getFirebaseMessage(message.getNotification().getTitle(), message.getNotification().getBody());

    }

    public void getFirebaseMessage(String title, String msg) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "NewOrder")
//                .setSmallIcon(R.drawable.skyeat_logo)
//                .setContentTitle(title)
//                .setContentText(msg)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setAutoCancel(true);
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        managerCompat.notify(101, builder.build());


        Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.skyeat_logo,null);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) drawable;
        Bitmap largeIcon=bitmapDrawable.getBitmap();

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification=new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                   .setSmallIcon(R.drawable.skyeat_logo)
                    .setContentText(title)
                    .setSubText(msg)
                    .setChannelId(CHANNEL_ID)
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"My Channel",NotificationManager.IMPORTANCE_HIGH));
        }
        else {
            notification=new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.skyeat_logo)
                    .setContentText(title)
                    .setSubText(msg)
                    .build();
        }
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}
