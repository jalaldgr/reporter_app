package com.ageny.yadegar.sirokhcms.update;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager manager;
    private static String CHANNEL_ID = "dxy_app_update";
    private static final int NOTIFICATION_ID = 0;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "بروزرسانی", NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription("بروزرسانی اپلیکیشن");
            mChannel.enableLights(true);
            getManager().createNotificationChannel(mChannel);
        }
    }
    /**
     * Show Notification
     */
    public void showNotification(String content, String apkUrl) {
        Intent myIntent = new Intent(this, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra("downloadurl", apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        androidx.core.app.NotificationCompat.Builder builder = getNofity(content)
                .setContentIntent(pendingIntent);

        getManager().notify(NOTIFICATION_ID, builder.build());
    }

    public void updateProgress(int progress) {
        String text = "در حال دانلود دانلود";
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        androidx.core.app.NotificationCompat.Builder builder = getNofity(text)
                .setProgress(100, progress, false)
                .setContentIntent(pendingintent);

        getManager().notify(NOTIFICATION_ID, builder.build());
    }
    private androidx.core.app.NotificationCompat.Builder getNofity(String text) {
        return new androidx.core.app.NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setTicker("آپدیت خودکار")
                .setContentTitle("در حال دانلود")
                .setContentText(text)
                .setSmallIcon(getSmallIcon())
                .setLargeIcon(getLargeIcon())
                .setAutoCancel(true)
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_LOW);

    }

    public void cancel() {
        getManager().cancel(NOTIFICATION_ID);
    }
    private int getSmallIcon() {
        int icon = getResources().getIdentifier("mipush_small_notification", "drawable", getPackageName());
        if (icon == 0) {
            icon = getApplicationInfo().icon;
        }

        return icon;
    }

    private Bitmap getLargeIcon() {
        int bigIcon = getResources().getIdentifier("mipush_notification", "drawable", getPackageName());
        if (bigIcon != 0) {
            return BitmapFactory.decodeResource(getResources(), bigIcon);
        }
        return null;
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

  public androidx.core.app.NotificationCompat.Builder filenotfound() {
        return new androidx.core.app.NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setTicker("404 فایل پیدا نشد")
                .setContentTitle("ffff")
                .setSmallIcon(getSmallIcon())
                .setLargeIcon(getLargeIcon())
                .setAutoCancel(true)
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_LOW);
    }
}