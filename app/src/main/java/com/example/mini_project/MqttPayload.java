package com.example.mini_project;

public class MqttPayload {
    public final String message;
    public final String topic;

    public MqttPayload(String message, String topic) {
        this.message = message;
        this.topic = topic;
    }
}

