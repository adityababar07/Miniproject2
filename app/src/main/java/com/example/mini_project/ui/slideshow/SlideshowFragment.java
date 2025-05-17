package com.example.mini_project.ui.slideshow;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mini_project.MqttMessageBus;
import com.example.mini_project.R;
import com.example.mini_project.databinding.FragmentSlideshowBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private Ringtone ringtone; // To keep reference for stopping
    private Timer ringtoneTimer; // To cancel timer if needed

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MqttMessageBus.getMqttMessage().observe(getViewLifecycleOwner(), payload -> {
            if (payload.topic.equals("miniproject/home/intrusion")) {
                if (payload.message.equals("1")) {
                    binding.textSlideshow.setText(getString(R.string.intrusion_detected));

                } else if (payload.message.equals("0")) {
                    binding.textSlideshow.setText(getString(R.string.intrusion_monitoring));
                    // Stop the ringtone and timer if intrusion stops
                    if (ringtone != null && ringtone.isPlaying()) {
                        ringtone.stop();
                    }
                    if (ringtoneTimer != null) {
                        ringtoneTimer.cancel();
                        ringtoneTimer = null;
                    }
                }
            }
        });

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Stop ringtone and timer if still playing when fragment is destroyed
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
        if (ringtoneTimer != null) {
            ringtoneTimer.cancel();
            ringtoneTimer = null;
        }
        binding = null;
    }
}

