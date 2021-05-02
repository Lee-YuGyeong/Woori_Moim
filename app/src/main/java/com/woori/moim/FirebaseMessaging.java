package com.woori.moim;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FirebaseMessaging extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessage";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: ");

        if (remoteMessage.getData().size() > 0) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String clickAction = remoteMessage.getData().get("clickAction");
            if (clickAction.equals("Send_MoneyActivity")) {
                String divide = remoteMessage.getData().get("money");
                String address = remoteMessage.getData().get("address");
                String name = remoteMessage.getData().get("name");
                String id = remoteMessage.getData().get("id");
                String request = remoteMessage.getData().get("request");
                String moim_name = remoteMessage.getData().get("moim_name");
                String total = remoteMessage.getData().get("total");
                String request_token=remoteMessage.getData().get("request_token");
                String pk=remoteMessage.getData().get("pk");
                sendTransferNotification(title, body, clickAction, divide, address, name, id, request, moim_name, total, request_token, pk);
            } else if (clickAction.equals("MoimRequest")) {
                String name = remoteMessage.getData().get("name");
                String id = remoteMessage.getData().get("id");
                String moim_name = remoteMessage.getData().get("moim_name");
                sendTransferNotification(title, body, clickAction,name, id, moim_name);
            }else if(clickAction.equals("HomeActivity")){
                String test=remoteMessage.getData().get("test");
                if(test.equals("transfer")){
                    sendNotification(title, body, clickAction, test);
                }
            }
            //String test=remoteMessage.getData().get("test");
            //sendNotification(title, body, clickAction, test);
        } else if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            sendNotification(title, body, "HomeActivity", "nothing");
        }
    }


    public void sendNotification(String title, String text, String clickAction, String test) {
        Log.d(TAG, "sendNotification: ");

        Intent intent = null;
        if (clickAction.equals("MainActivity2")) {
            intent = new Intent(this, MainActivity2.class);
            intent.putExtra("test", test);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // String channelId = getString(R.string.default_notification_channel_id);
        String channelId = "testChannelId";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void sendTransferNotification(String title, String text, String clickAction, String divide, String address, String name, String id, String request, String moim_name, String total, String request_token, String pk) {//for transfer money
        Intent intent = null;
        if (clickAction.equals("Send_MoneyActivity")) {
            intent = new Intent(this, Send_MoneyActivity.class);
            intent.putExtra("divide", divide);
            intent.putExtra("address", address);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            intent.putExtra("request", request);
            intent.putExtra("moim_name", moim_name);
            intent.putExtra("total", total);
            intent.putExtra("request_token", request_token);
            intent.putExtra("pk", pk);
            //System.out.println(request_token);
            //System.out.println("FirebaseMessaging");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "testChannelId";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void sendTransferNotification(String title, String text, String clickAction, String name, String id, String moim_name) {
        Intent intent = null;
        if (clickAction.equals("MoimRequest")) {
            intent = new Intent(this, MoimRequest.class);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            intent.putExtra("moim_name", moim_name);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "testChannelId";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

//
}