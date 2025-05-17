package com.example.mini_project.ui.home;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mini_project.MQTTClient;
import com.example.mini_project.R;
import com.example.mini_project.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Switch onOffSwitch = (Switch)  root.findViewById(R.id.switch1);
        onOffSwitch.getThumbDrawable().setColorFilter(
                ContextCompat.getColor(this.getContext(), R.color.purple_200), PorterDuff.Mode.SRC_IN);
        onOffSwitch.getTrackDrawable().setColorFilter(
                ContextCompat.getColor(this.getContext(), R.color.purple_500), PorterDuff.Mode.SRC_IN);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if (isChecked){
                    Log.v("on", ""+isChecked);
                    MQTTClient.sendMessage("on");
                }
                else{
                    Log.v("off", ""+isChecked);
                    MQTTClient.sendMessage("off");

                }
            }

        });

        Switch onOffSwitch1 = (Switch)  root.findViewById(R.id.switch2);
        onOffSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if (isChecked){
                    Log.v("on1", ""+isChecked);
                    MQTTClient.sendMessage("on1");
                }
                else{
                    Log.v("off1", ""+isChecked);
                    MQTTClient.sendMessage("off1");

                }
            }

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}