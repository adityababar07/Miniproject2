package com.example.miniproject;



//import android.content.Context;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;

public class MQTTClient {

    public static void main(String[] args) {
        String mqttBroker = "tcp://broker.emqx.io:1883";
        String clientId = MqttClient.generateClientId();
        String mqttTopic = "miniproject/home";
        String username = "adityababar";
        String password = "9gT5bJhEzpAZAfW";
        MemoryPersistence persistence = new MemoryPersistence();
        // MQTT connect options
        MqttConnectOptions connOpts = new MqttConnectOptions();
        // authentication
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        connOpts.setKeepAliveInterval(60);
        connOpts.setAutomaticReconnect(true);
        connOpts.setCleanSession(true);
        String content = "Hi from the Java application";
        int qos = 0;

        try {
            MqttClient mqttClient = new MqttClient(mqttBroker, clientId, persistence);
            // callback
            mqttClient.setCallback(new SampleCallback());
            System.out.println("Connecting to broker: " + mqttBroker);
            mqttClient.connect(connOpts);
            System.out.println("Connected to broker: " + mqttBroker);

            mqttClient.subscribe(mqttTopic, qos);
            System.out.println("Subscribed to topic: " + mqttTopic);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            mqttClient.publish(mqttTopic, message);
            System.out.println("Message published");
            /* Keep the application open, so that the subscribe operation can tested */
            System.out.println("Press Enter to disconnect");
            System.in.read();
            /* Proceed with disconnecting */
            mqttClient.disconnect();
            mqttClient.close();

        } catch (MqttException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}