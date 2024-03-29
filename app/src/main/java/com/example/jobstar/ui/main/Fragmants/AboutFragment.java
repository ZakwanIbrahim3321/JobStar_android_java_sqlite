package com.example.jobstar.ui.main.Fragmants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jobstar.databinding.AboutFragmentBinding;


public class AboutFragment extends Fragment {

    private AboutFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AboutFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
