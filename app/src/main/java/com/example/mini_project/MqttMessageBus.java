// MqttMessageBus.java
package com.example.mini_project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MqttMessageBus {
    private static final MutableLiveData<MqttPayload> mqttMessage = new MutableLiveData<>();

    public static LiveData<MqttPayload> getMqttMessage() {
        return mqttMessage;
    }

    public static void postMqttMessage(String message, String topic) {
        mqttMessage.postValue(new MqttPayload(message, topic));
    }
}
