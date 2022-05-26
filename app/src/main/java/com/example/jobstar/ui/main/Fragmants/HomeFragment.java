package com.example.jobstar.ui.main.Fragmants;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobstar.R;
import com.example.jobstar.SqlLite.DBHelperApplication;
import com.example.jobstar.SqlLite.DBHelperJobs;
import com.example.jobstar.databinding.HomeFragmentBinding;
import com.example.jobstar.ui.main.JobAdapter;
import com.example.jobstar.ui.main.RecyclerItemClickListener;
import com.example.jobstar.ui.splash.splashActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    DBHelperJobs DB;
    DBHelperApplication DBApplications;
    RecyclerView recyclerView;
    ArrayList<String> Title, Company, Location, Salary;
    JobAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        DB = new DBHelperJobs(getContext());
        DBApplications = new DBHelperApplication(getContext());
        Title = new ArrayList<>();
        Company = new ArrayList<>();
        Location = new ArrayList<>();
        Salary = new ArrayList<>();

        binding.searchBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Optional Not Implemented", Toast.LENGTH_SHORT).show();
            }
        });

        if (splashActivity.role.equals("admin")){
            binding.welcomeAdmin.setVisibility(View.VISIBLE);
            binding.postJob.setVisibility(View.VISIBLE);
        }else{
            binding.welcomeAdmin.setVisibility(View.GONE);
            binding.postJob.setVisibility(View.GONE);
        }


        binding.postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 postJobDialog(view);
            }
        });

        adapter = new JobAdapter(getContext(), Title, Company, Location, Salary);
        binding.jobRecyclerView.setAdapter(adapter);
        binding.jobRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewAllJobs();


        binding.jobRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (splashActivity.role.equals("admin")){
                            adminActionDialog(view, Title.get(position), Company.get(position), Location.get(position), Salary.get(position));
                        }else {
                            applyDialog(view, Title.get(position),Company.get(position));
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void postJobDialog(View view){
        Dialog dialog = new Dialog(view.getContext(),R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_job_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        EditText title = dialog.findViewById(R.id.jobTitleDialog);
        EditText company = dialog.findViewById(R.id.jobCompanyDialog);
        EditText location = dialog.findViewById(R.id.jobLocationDialog);
        EditText salary = dialog.findViewById(R.id.jobSalaryDialog);
        LinearLayout postJob = dialog.findViewById(R.id.LL_post);
        LinearLayout cancel = dialog.findViewById(R.id.cancel_dialog_);


        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title_ = title.getText().toString();
                String company_ = company.getText().toString();
                String location_ = location.getText().toString();
                String salary_ = salary.getText().toString();

                if (insetJob(title_,company_,location_,salary_)){
                    Log.e("Job Posted",": true");
                    Title.clear();
                    Location.clear();
                    Company.clear();
                    Salary.clear();
                    viewAllJobs();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Log.e("Job Not Posted",": false");
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void adminActionDialog(View view,String title,String company,String location,String salary){
        Dialog dialog = new Dialog(view.getContext(), R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_delete_job_item);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        TextView title_ = dialog.findViewById(R.id.job_title_item_admin);
        TextView hint = dialog.findViewById(R.id.hint_item_admin);
        LinearLayout updateFields = dialog.findViewById(R.id.update_fields_admin);
        EditText company_ = dialog.findViewById(R.id.jobCompanyDialogAdmin);
        EditText location_ = dialog.findViewById(R.id.jobLocationDialogAdmin);
        EditText salary_ = dialog.findViewById(R.id.jobSalaryDialogAdmin);
        LinearLayout update = dialog.findViewById(R.id.update_item_admin);
        LinearLayout delete = dialog.findViewById(R.id.delete_item_admin);
        LinearLayout cancel = dialog.findViewById(R.id.cancel_item_admin);

        title_.setText(title);
        company_.setText(company);
        location_.setText(location);
        salary_.setText(salary);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateJob(title.toString(),company_.getText().toString(),location_.getText().toString(),salary_.getText().toString())){
                    Log.e("Updated  :","true");
                    Title.clear();
                    Company.clear();
                    Location.clear();
                    Salary.clear();
                    viewAllJobs();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteJob(title)){
                    Log.e("Deleted  :","true");
                    Title.clear();
                    Company.clear();
                    Location.clear();
                    Salary.clear();
                    viewAllJobs();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        dialog.show();
    }


    public void applyDialog(View view,String jobTitle,String companyX){

        Dialog dialog = new Dialog(view.getContext(), R.style.DialogCustomTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.apply_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        TextView apply = dialog.findViewById(R.id.apply_dialog);
        TextView cancel = dialog.findViewById(R.id.cancel_dialog);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!DBApplications.checkResubmission(splashActivity.userName, jobTitle)){
                    if (DBApplications.addApplication(splashActivity.userName,jobTitle,companyX)){
                        Toast.makeText(getContext(), "Your Application Submitted", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Sorry Your Application Not Submitted", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Already Applied for this job", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private Boolean validatePostJobEntries(String title, String company, String location, String salary){
        return !title.isEmpty() && !company.isEmpty() && !location.isEmpty() && !salary.isEmpty();
    }

    public Boolean insetJob(String title,String company,String location,String salary){
        if (validatePostJobEntries(title,company,location,salary) && splashActivity.role.equals("admin")){

            Boolean checkJobInsertData = DB.insertJobData(title,company,location,salary);
            if (checkJobInsertData){
                Toast.makeText(getContext(), "New Job Posted", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                Toast.makeText(getContext(), "Error in Job Posting", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else {
            Toast.makeText(getContext(), "Please fill all the field", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



    public Boolean updateJob(String title,String company,String location,String salary){

        if (validatePostJobEntries(title,company,location,salary)){
            Boolean checkJobUpdate = DB.updateJobData(title,company,location,salary);
            if (checkJobUpdate){
                Toast.makeText(getContext(), "Job Updated", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                Toast.makeText(getContext(), "Job Not Updated", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(getContext(), "Entries are not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public Boolean deleteJob(String title){
        if (!title.isEmpty()){
            Boolean checkJobDelete = DB.deleteJobData(title);
            if (checkJobDelete){
                Toast.makeText(getContext(), "Job Deleted", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                Toast.makeText(getContext(), "Job Not Deleted", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(getContext(), "Title is null", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void viewAllJobs(){
//        Cursor allJobs = DB.getJobData();
//        if (allJobs.getCount()>0){
//            StringBuffer buffer = new StringBuffer();
//            while (allJobs.moveToNext()){
//                buffer.append("Title :"+allJobs.getString(0)+"\n");
//                buffer.append("Company :"+allJobs.getString(1)+"\n");
//                buffer.append("Location :"+allJobs.getString(2)+"\n");
//                buffer.append("Salary :"+allJobs.getString(3)+"\n");
//            }
//
//            Log.e("JOBS LIST    :",""+buffer.toString());
//        }else {
//            Toast.makeText(getContext(), "You Don't have any Job data in DB", Toast.LENGTH_SHORT).show();
//        }

        Cursor cursor = DB.getJobData();
        if (cursor.getCount()==0){
            Toast.makeText(getContext(), "No Job Available", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                Title.add(cursor.getString(0));
                Company.add(cursor.getString(1));
                Location.add(cursor.getString(2));
                Salary.add(cursor.getString(3));
            }
        }
        if (splashActivity.role.equals("admin")){
            binding.noOfJobsAdmin.setText("YOU HAVE POSTED "+cursor.getCount()+ " JOBS SO FAR");
        }else {
            binding.noOfJobsAdmin.setText("YOU "+cursor.getCount()+ " ACTIVE JOBS AVAILABLE");
        }
    }


}