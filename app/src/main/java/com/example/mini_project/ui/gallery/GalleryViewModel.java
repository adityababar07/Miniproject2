package com.example.mini_project.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> humidityText;
    private final MutableLiveData<String> temperatureText;

    public GalleryViewModel() {
        humidityText = new MutableLiveData<>();
        temperatureText = new MutableLiveData<>();

        humidityText.setValue("Humidity: 0%");
        temperatureText.setValue("Temperature: 0°C");
    }

    public LiveData<String> getHumidityText() {
        return humidityText;
    }

    public LiveData<String> getTemperatureText() {
        return temperatureText;
    }
}
