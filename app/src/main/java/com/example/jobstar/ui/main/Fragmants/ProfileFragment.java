package com.example.jobstar.ui.main.Fragmants;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jobstar.SqlLite.DBHelper;
import com.example.jobstar.SqlLite.DBHelperJobs;
import com.example.jobstar.databinding.ProfileFragmentBinding;
import com.example.jobstar.ui.splash.splashActivity;

public class ProfileFragment extends Fragment {

    private ProfileFragmentBinding binding;
    DBHelper DB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        DB = new DBHelper(getContext());

        Log.e("",""+splashActivity.userName);
        Cursor cursor = DB.getUserProfile(splashActivity.userName);
        if (cursor.getCount()==0){
            Log.e("Error in ProfileFrag","  getUserProfile");
        }else {
            while (cursor.moveToNext()) {
                binding.idTvUsernameInProfile.setText(cursor.getString(0));
                binding.idTvEmailInProfile.setText(cursor.getString(2));
                binding.idTvNumberInProfile.setText(cursor.getString(3));
            }
        }


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
