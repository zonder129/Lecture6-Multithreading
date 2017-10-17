package ru.mail.park.lecture6;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

public class DemoIntentService extends IntentService {

    private static final String TAG = "DemoIntentService";

    public static final String ACTION_TO_UPPER = "TO_UPPER";
    public static final String ACTION_TO_LOWER = "TO_LOWER";

    public static final String EXTRA_STRING = "STRING";
    public static final String EXTRA_FOREGROUND = "FOREGROUND";

    public DemoIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }

        final String data = intent.getExtras().getString(EXTRA_STRING);
        final boolean isForeground = intent.getBooleanExtra(EXTRA_FOREGROUND, false);

        if (isForeground) {
            startForeground(1, getNotification());
        }

        Log.d(TAG, String.format("Start action '%s'", intent.getAction()));

        switch (intent.getAction()) {
            case ACTION_TO_UPPER:
                handleToUpper(data);
                break;
            case ACTION_TO_LOWER:
                handleToLower(data);
                break;
        }

        if (isForeground) {
            stopForeground(true);
        }
    }

    private void handleToUpper(final String data) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Result: " + data.toUpperCase());
    }

    private void handleToLower(final String data) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Result: " + data.toLowerCase());
    }

    private Notification getNotification() {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_foreground)
                .setContentTitle(getString(R.string.working))
                .build();
    }
}
