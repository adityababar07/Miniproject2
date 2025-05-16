package com.example.mini_project.ui.slideshow;

import android.os.Bundle;
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

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

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
        binding = null;
    }
}