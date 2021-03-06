package ru.mail.park.lecture6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

public class IntentServiceActivity extends AppCompatActivity {

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final Intent intent = new Intent(IntentServiceActivity.this, DemoIntentService.class);
            intent.putExtra(DemoIntentService.EXTRA_STRING, "Ololo");
            intent.putExtra(DemoIntentService.EXTRA_FOREGROUND, foreground.isChecked());

            switch (view.getId()) {
                case R.id.to_lower:
                    intent.setAction(DemoIntentService.ACTION_TO_LOWER);
                    break;
                case R.id.to_upper:
                    intent.setAction(DemoIntentService.ACTION_TO_UPPER);
                    break;
                default:
                    throw new UnsupportedOperationException("Wrong view id " + view.getId());
            }

            startService(intent);
        }
    };

    private SwitchCompat foreground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        foreground = findViewById(R.id.foreground);

        findViewById(R.id.to_lower).setOnClickListener(clickListener);
        findViewById(R.id.to_upper).setOnClickListener(clickListener);
    }
}
