package com.example.priion;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CriticalAlertActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🚨 WAKE UP COMMANDS (Bypasses lock screens and forces screen ON)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );

        setContentView(R.layout.activity_critical_alert);

        // Get the message details from the notification
        String title = getIntent().getStringExtra("title");
        String text = getIntent().getStringExtra("text");
        android.app.PendingIntent chatIntent = getIntent().getParcelableExtra("chatIntent");

        TextView titleView = findViewById(R.id.alertTitle);
        TextView messageView = findViewById(R.id.alertMessage);

        if (title != null) titleView.setText(title);
        if (text != null) messageView.setText(text);

        // The WhatsApp Reply Button!
        Button dismissButton = findViewById(R.id.dismissButton);
        dismissButton.setOnClickListener(v -> {
            // Try to open WhatsApp instantly
            if (chatIntent != null) {
                try {
                    chatIntent.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Close the Red Screen
            finish();
        });
    }
}