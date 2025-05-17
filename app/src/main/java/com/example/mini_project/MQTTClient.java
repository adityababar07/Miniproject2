package com.example.mini_project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTClient extends Service {

    public static final String CHANNEL_ID = "mqtt_alarm_channel";
    public static final int NOTIF_ID = 1001;

    public static final int qos = 0;
    public static final String mqttBroker = "tcp://broker.emqx.io:1883";
    public static final String clientId = MqttClient.generateClientId();
    public static final String username = "adityababar";
    public static final String password = "9gT5bJhEzpAZAfW";
    public static final String mqttTopic = "miniproject/home";
    public static final String mqttTopic1 = "miniproject/home/dht/temperature";
    public static final String mqttTopic2 = "miniproject/home/dht/humidity";
    public static final String mqttTopic3 = "miniproject/home/intrusion";

    private static MqttClient mqttClient;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(NOTIF_ID, getNotification("MQTT Service Running"));
        connect();
    }

    private Notification getNotification(String content) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("MQTT Alarm")
                .setContentText(content)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "MQTT Alarm Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("MQTT Alarm Notifications");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public void connect() {
        new Thread(() -> {
            try {
                mqttClient = new MqttClient(mqttBroker, clientId, new MemoryPersistence());

                // callback
                mqttClient.setCallback(new SampleCallBack(getApplicationContext()));
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setUserName(username);
                connOpts.setPassword(password.toCharArray());
                connOpts.setKeepAliveInterval(60);
                connOpts.setAutomaticReconnect(true);
                connOpts.setCleanSession(false);

//                mqttClient.setCallback(new  SampleCallBack(getApplicationContext()));

                Log.i("MQTT", "Connecting to broker: " + mqttBroker);
                mqttClient.connect(connOpts);
                Log.i("MQTT", "Connected to broker: " + mqttBroker);

                mqttClient.subscribe(mqttTopic, qos);
                mqttClient.subscribe(mqttTopic1, qos);
                mqttClient.subscribe(mqttTopic2, qos);
                mqttClient.subscribe(mqttTopic3, qos);

                Log.i("MQTT", "Subscribed to topics.");
            } catch (MqttException e) {
                Log.e("MQTT", "Error connecting/subscribing", e);
            }
        }).start();
    }


    public static void sendMessage(String content) {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                mqttClient.publish(mqttTopic, message);
                System.out.println("Message published");
                Log.v("Message published", "");

            } else {
                System.out.println("Client not connected");
                Log.v("Client not connected", "");

            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Service will be restarted if killed
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                mqttClient.close();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
