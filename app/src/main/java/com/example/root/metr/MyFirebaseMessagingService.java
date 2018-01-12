package com.example.root.metr;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.root.metr.root.App;
import com.example.root.metr.root.DialogMangerSingle;
import com.example.root.metr.root.MainActivity;
import com.example.root.metr.utils.PermissionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    PermissionManager permissionManager;

    {
        permissionManager=new PermissionManager();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(permissionManager.checkLocationEnabled(App.INSTANCE)) {
            if (remoteMessage.getData().size() > 0) {
                handleNow();
            }

            if (remoteMessage.getNotification() != null) {
                sendNotification(remoteMessage.getNotification().getBody());
                createOrder();
            }
        }
    }

    private void handleNow() {
    }



    private void createOrder(){
        Observable.fromCallable(() -> {
            DialogMangerSingle.getInstance().show();
            return new Object();
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {},Throwable::printStackTrace);

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

       // String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "")
                        .setSmallIcon(android.R.drawable.ic_notification_overlay)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
