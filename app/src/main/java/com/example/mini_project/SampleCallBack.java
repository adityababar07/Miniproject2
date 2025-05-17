package com.example.mini_project;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.mini_project.MQTTClient.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioManager;
import android.content.Context;

import java.util.Objects;
import java.util.TimerTask;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Timer;

public class SampleCallBack implements MqttCallback {

    private Ringtone ringtone; // To keep reference for stopping
    private Timer ringtoneTimer; // To cancel timer if needed

    private Context context;

    public SampleCallBack(Context context) {
        this.context = context;
    }


    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("connection lost：" + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        if (topic.equals("miniproject/home/intrusion")) {
            if (payload.equals("1")) {
                Log.i("MQTT", "Intrusion detected");
            playAlarmEvenIfSilent();
            }
            }
        MqttMessageBus.postMqttMessage(payload, topic);
        System.out.println("Received message: \n  topic：" + topic + "\n  Qos：" + message.getQos() + "\n  payload：" + payload);

    }

    private Context requireContext() {
        return null;
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete");
    }

    private void playAlarmEvenIfSilent() {
        try {
            // Get AudioManager
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            // Save current ringer mode
            int originalMode = audioManager.getRingerMode();

            // Set ringer to normal mode
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (notificationManager.isNotificationPolicyAccessGranted()) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }

            // Alarm sound
            Uri notification = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alarm);
            if (notification == null) {
                notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            }

            ringtone = RingtoneManager.getRingtone(context, notification);
            if (ringtone != null && !ringtone.isPlaying()) {
                ringtone.play();

                if (ringtoneTimer != null) ringtoneTimer.cancel();
                ringtoneTimer = new Timer();
                ringtoneTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (ringtone.isPlaying()) {
                            ringtone.stop();
                        }

                        // Restore original mode
                        audioManager.setRingerMode(originalMode);
                    }
                }, 10000);
            }

        } catch (Exception e) {
            Log.e("Alarm", "Error playing alarm: " + e.getMessage());
        }
    }
}
