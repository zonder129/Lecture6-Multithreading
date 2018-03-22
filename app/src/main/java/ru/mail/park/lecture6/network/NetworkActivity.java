package ru.mail.park.lecture6.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.mail.park.lecture6.R;

public class NetworkActivity extends AppCompatActivity {

    private final NetworkManager.OnRequestCompleteListener listener =
            new NetworkManager.OnRequestCompleteListener() {
                @Override
                public void onRequestComplete(final String body) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NetworkActivity.this.body.setText(body);
                            progress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            };

    private TextView body;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        body = findViewById(R.id.body);
        progress = findViewById(R.id.progress);

        findViewById(R.id.user_agent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                body.setText("");
                progress.setVisibility(View.VISIBLE);
                NetworkManager.getInstance().get("https://httpbin.org/user-agent", listener);
            }
        });
    }
}
