package com.example.miniproject;



//import android.content.Context;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;

public class MQTTClient {

    public static final int qos = 0;
    public static final String mqttBroker = "tcp://broker.emqx.io:1883";
    public static final String clientId = MqttClient.generateClientId();
    public static final String username = "adityababar";
    public static final String password = "9gT5bJhEzpAZAfW";
    public static final String mqttTopic = "miniproject/home";

    public static MemoryPersistence persistence = new MemoryPersistence();


    public static MqttClient mqttClient ;

    public static void connect() {
        // MQTT connect options
        MqttConnectOptions connOpts = new MqttConnectOptions();
        // authentication
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        connOpts.setKeepAliveInterval(60);
        connOpts.setAutomaticReconnect(true);
        connOpts.setCleanSession(true);
//        String content = "Hi from the Java application";

        try {
//            MqttClient mqttClient = new MqttClient(mqttBroker, clientId, persistence);
            mqttClient = new MqttClient(mqttBroker, clientId, persistence);

            // callback
            mqttClient.setCallback(new SampleCallback());
            System.out.println("Connecting to broker: " + mqttBroker);
            mqttClient.connect(connOpts);
            System.out.println("Connected to broker: " + mqttBroker);

            mqttClient.subscribe(mqttTopic, qos);
            System.out.println("Subscribed to topic: " + mqttTopic);
            Log.v("Subscribed to topic: " + mqttTopic, "");
            /* Keep the application open, so that the subscribe operation can tested */
//            System.out.println("Press Enter to disconnect");
//            System.in.read();
            /* Proceed with disconnecting */
//            mqttClient.disconnect();
//            mqttClient.close();

        } catch (MqttException e) {
            e.printStackTrace();
        }
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

}