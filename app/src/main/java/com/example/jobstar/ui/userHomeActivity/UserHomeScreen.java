package com.example.jobstar.ui.userHomeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.jobstar.databinding.ActivityUserHomeScreenBinding;

public class UserHomeScreen extends AppCompatActivity {

    private ActivityUserHomeScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserHomeScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}