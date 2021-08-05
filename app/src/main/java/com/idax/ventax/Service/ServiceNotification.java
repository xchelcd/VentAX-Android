package com.idax.ventax.Service;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.idax.ventax.Activity.Login.LoginActivity;
import com.idax.ventax.Entity.Account;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.R;

import java.util.List;
import java.util.Objects;

import static com.idax.ventax.Extra.Constansts.NOTIFICATION_EXTRA_DATA;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class ServiceNotification extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getBody(),
                remoteMessage.getNotification().getTitle(),
                remoteMessage.getData().get(NOTIFICATION_EXTRA_DATA));
    }

    private void showNotification(String body, String title, Object extra) {
        int userId;
        try {
            userId = ((Integer)Integer.parseInt(extra.toString()));
        } catch (NumberFormatException e) {
            userId = 0;
        }
        //Intent intent = new Intent(this, LoginActivity.class);
        Intent intent = new Intent(this, LauncherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_foregound_vax)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_foregound_vax))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent);

        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(userId, notificationBuilder.build());
    }
}
