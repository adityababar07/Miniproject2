package com.example.mini_project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedMqttViewModel extends ViewModel {
    private final MutableLiveData<String> mqttMessage = new MutableLiveData<>();

    public void setMqttMessage(String message) {
        mqttMessage.postValue(message); // postValue is safe for background threads
    }

    public LiveData<String> getMqttMessage() {
        return mqttMessage;
    }
}
