package ru.mail.park.lecture6.network;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkManager {

    private static final NetworkManager INSTANCE = new NetworkManager();
    private static final String TAG = "NETWORK";

    private final OkHttpClient client = new OkHttpClient();
    private final Executor executor = Executors.newSingleThreadExecutor();

    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    public void get(final String url, final OnRequestCompleteListener listener) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        performRequest(request, listener);
    }

    private void performRequest(final Request request, final OnRequestCompleteListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final String body = getBody(request);
                listener.onRequestComplete(body);
            }
        });
    }

    private String getBody(final Request request) {
        try {
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                final ResponseBody body = response.body();
                if (body != null) {
                    try {
                        return body.string();
                    } finally {
                        body.close();
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Fail to perform request", e);
        }
        return null;
    }

    public interface OnRequestCompleteListener {
        void onRequestComplete(final String body);
    }
}
