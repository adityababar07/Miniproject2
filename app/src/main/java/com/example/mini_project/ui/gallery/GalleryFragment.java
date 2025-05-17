package com.example.mini_project.ui.gallery;

import android.media.MediaRouter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.example.mini_project.MqttMessageBus;
import com.example.mini_project.R;
import com.example.mini_project.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observe the singleton LiveData
        MqttMessageBus.getMqttMessage().observe(getViewLifecycleOwner(), payload -> {
            if (payload.topic.equals("miniproject/home/dht/temperature")) {
                binding.textGallery.setText(getString(R.string.degree_celsius_value, payload.message));

            } else if (payload.topic.equals("miniproject/home/dht/humidity")) {
                binding.textGallery1.setText(getString(R.string.percent_value, payload.message));
            }
        });
        final TextView textView = binding.textGallery;
        final TextView textView1 = binding.textGallery1;
        galleryViewModel.getTemperatureText().observe(getViewLifecycleOwner(), textView::setText);
        galleryViewModel.getHumidityText().observe(getViewLifecycleOwner(), textView1::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}