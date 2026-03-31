package com.example.priion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class PriionService extends NotificationListenerService {

    private static final String TAG = "PRIION_LOGS";

    // Channels
    private static final String CHANNEL_CRITICAL = "priion_critical";
    private static final String CHANNEL_IMPORTANT = "priion_important";
    private static final String CHANNEL_SILENT = "priion_silent";

    // Group Keys
    private static final String GROUP_KEY_CRITICAL = "com.example.priion.CRITICAL_GROUP";
    private static final String GROUP_KEY_IMPORTANT = "com.example.priion.IMPORTANT_GROUP";
    private static final String GROUP_KEY_SILENT = "com.example.priion.SILENT_GROUP";

    // 🧠 The Machine Learning Engine
    private PriionBrain aiBrain;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
        aiBrain = new PriionBrain(this);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals(getPackageName())) return;
        if (!shouldAnalyze(sbn.getPackageName())) return;

        if ((sbn.getNotification().flags & Notification.FLAG_GROUP_SUMMARY) != 0) {
            cancelNotification(sbn.getKey());
            return;
        }

        String text = sbn.getNotification().extras.getString("android.text");
        String title = sbn.getNotification().extras.getString("android.title");

        if (text == null) return;

        if (text.toLowerCase().contains("new messages")) {
            cancelNotification(sbn.getKey());
            return;
        }

        String category = analyze(text);

        if (category.equals("spam")) {
            cancelNotification(sbn.getKey());
            Log.d(TAG, "🚫 AI BLOCKED SPAM from " + title + ": " + text);
            return;
        }

        Log.d(TAG, "📩 " + title + ": " + text + " -> AI decided: " + category);
        cancelNotification(sbn.getKey());
        repostAsGrouped(sbn, title, text, category);
    }

    private boolean shouldAnalyze(String pkg) {
        return pkg.contains("whatsapp") || pkg.contains("android.gm") || pkg.contains("messaging") || pkg.contains("mms");
    }

    private void repostAsGrouped(StatusBarNotification sbn, String title, String text, String category) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId;
        String groupKey;
        String groupTitle;
        int priority;
        int iconRes;
        int color;

        switch (category) {
            case "critical":
                channelId = CHANNEL_CRITICAL;
                groupKey = GROUP_KEY_CRITICAL;
                groupTitle = "🚨 CRITICAL UPDATES";
                priority = NotificationCompat.PRIORITY_MAX;
                iconRes = android.R.drawable.ic_dialog_alert;
                color = Color.RED;
                break;
            case "important":
                channelId = CHANNEL_IMPORTANT;
                groupKey = GROUP_KEY_IMPORTANT;
                groupTitle = "⚠️ Important Updates";
                priority = NotificationCompat.PRIORITY_DEFAULT;
                iconRes = android.R.drawable.ic_dialog_info;
                color = Color.BLUE;
                break;
            default:
                channelId = CHANNEL_SILENT;
                groupKey = GROUP_KEY_SILENT;
                groupTitle = "💬 Casual Messages";
                priority = NotificationCompat.PRIORITY_LOW;
                iconRes = android.R.drawable.ic_dialog_email;
                color = Color.GRAY;
                break;
        }

        Bitmap largeIcon = drawableToBitmap(iconRes);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(iconRes)
                .setLargeIcon(largeIcon)
                .setColor(color)
                .setPriority(priority)
                .setGroup(groupKey)
                .setAutoCancel(true)
                .setContentIntent(sbn.getNotification().contentIntent);

        // 🚨 CRITICAL: Launch the Full-Screen Red Alert
        if (category.equals("critical")) {
            builder.setCategory(NotificationCompat.CATEGORY_ALARM);
            android.app.PendingIntent chatIntent = sbn.getNotification().contentIntent;

            // 1. Keep the standard Intent for when the screen is OFF
            android.content.Intent fullScreenIntent = new android.content.Intent(this, CriticalAlertActivity.class);
            fullScreenIntent.putExtra("title", title);
            fullScreenIntent.putExtra("text", text);
            fullScreenIntent.putExtra("chatIntent", chatIntent);
            fullScreenIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);

            android.app.PendingIntent fullScreenPendingIntent = android.app.PendingIntent.getActivity(this, 0,
                    fullScreenIntent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
            builder.setFullScreenIntent(fullScreenPendingIntent, true);

            // 🔥 2. NUCLEAR OVERLAY: Paint directly over YouTube if the screen is ON!
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                if (android.provider.Settings.canDrawOverlays(this)) {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        try {
                            android.view.WindowManager wm = (android.view.WindowManager) getSystemService(Context.WINDOW_SERVICE);

                            android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams(
                                    android.view.WindowManager.LayoutParams.MATCH_PARENT,
                                    android.view.WindowManager.LayoutParams.MATCH_PARENT,
                                    android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                    android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                                            android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                                            android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                                            android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                                    android.graphics.PixelFormat.TRANSLUCENT);

                            android.view.View overlayView = android.view.LayoutInflater.from(this).inflate(R.layout.activity_critical_alert, null);

                            android.widget.TextView titleView = overlayView.findViewById(R.id.alertTitle);
                            android.widget.TextView messageView = overlayView.findViewById(R.id.alertMessage);
                            titleView.setText(title);
                            messageView.setText(text);

                            // 🔥 THE UPGRADED ACKNOWLEDGE BUTTON 🔥
                            android.widget.Button dismissButton = overlayView.findViewById(R.id.dismissButton);
                            dismissButton.setOnClickListener(v -> {

                                // 1. Instantly remove the Red Screen
                                try {
                                    wm.removeView(overlayView);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                // 2. Force open the specific WhatsApp Chat
                                if (chatIntent != null) {
                                    try {
                                        if (android.os.Build.VERSION.SDK_INT >= 34) {
                                            // Android 14+ Override: Force OS to let us open chat from background
                                            android.app.ActivityOptions options = android.app.ActivityOptions.makeBasic();
                                            options.setPendingIntentBackgroundActivityStartMode(android.app.ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOWED);
                                            chatIntent.send(this, 0, null, null, null, null, options.toBundle());
                                        } else {
                                            // Older Android versions
                                            chatIntent.send();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            // BAM! Slap it on the screen!
                            wm.addView(overlayView, params);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }

        manager.notify((int) System.currentTimeMillis(), builder.build());

        Notification summary = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(groupTitle)
                .setContentText("Check updates")
                .setSmallIcon(iconRes)
                .setColor(color)
                .setGroup(groupKey)
                .setGroupSummary(true)
                .setPriority(priority)
                .setAutoCancel(true)
                .build();

        int summaryId = 0;
        if (category.equals("critical")) summaryId = 1;
        if (category.equals("important")) summaryId = 2;
        if (category.equals("low")) summaryId = 3;

        manager.notify(summaryId, summary);
    }

    private Bitmap drawableToBitmap(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableId);
        if (drawable == null) return null;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.setTint(Color.DKGRAY);
        drawable.draw(canvas);
        return bitmap;
    }

    private void createNotificationChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            NotificationChannel c1 = new NotificationChannel(CHANNEL_CRITICAL, "Critical Alerts", NotificationManager.IMPORTANCE_HIGH);
            c1.enableVibration(true);
            NotificationChannel c2 = new NotificationChannel(CHANNEL_IMPORTANT, "Work/Important", NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel c3 = new NotificationChannel(CHANNEL_SILENT, "Casual/Silent", NotificationManager.IMPORTANCE_DEFAULT);
            c3.setShowBadge(false);
            if (manager != null) {
                manager.createNotificationChannel(c1);
                manager.createNotificationChannel(c2);
                manager.createNotificationChannel(c3);
            }
        }
    }

    private String analyze(String text) {
        return aiBrain.classify(text);
    }
}