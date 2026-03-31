package com.example.priion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if PRIION has notification access
        String listeners = android.provider.Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (listeners == null || !listeners.contains(getPackageName())) {
            // Open settings so user can enable PRIION
            startActivity(new android.content.Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }
}