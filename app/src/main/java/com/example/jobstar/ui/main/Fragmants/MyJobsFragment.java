package com.example.jobstar.ui.main.Fragmants;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobstar.SqlLite.DBHelperApplication;
import com.example.jobstar.databinding.MyJobsFragmentBinding;
import com.example.jobstar.ui.main.JobAdapter;
import com.example.jobstar.ui.main.myJobsAdapter;
import com.example.jobstar.ui.splash.splashActivity;

import java.util.ArrayList;

public class MyJobsFragment extends Fragment {


    DBHelperApplication DB;
    RecyclerView recyclerView;
    ArrayList<String> Title, Company;
    myJobsAdapter adapter;
    private MyJobsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyJobsFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        DB = new DBHelperApplication(getContext());
        Title = new ArrayList<>();
        Company = new ArrayList<>();


        adapter = new myJobsAdapter(getContext(), Title, Company);
        binding.myJobsUser.setAdapter(adapter);
        binding.myJobsUser.setLayoutManager(new LinearLayoutManager(getContext()));
        viewMyAllApplication();

        return view;


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void viewMyAllApplication(){
        Cursor cursor = DB.getAllApplicationByUser(splashActivity.userName);
        if (cursor.getCount()==0){
            Toast.makeText(getContext(), "No Job Available", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                Title.add(cursor.getString(2));
                Company.add(cursor.getString(3));
            }
        }

            binding.myJobsCount.setText("YOU HAVE APPLIED ON "+cursor.getCount()+ " JOBS");

    }


}
