package com.example.jobstar.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobstar.R;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {

    private Context context;
    private ArrayList title,company,location,salary;

    public JobAdapter(Context context, ArrayList title, ArrayList company, ArrayList location, ArrayList salary) {
        this.context = context;
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_job,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(String.valueOf(title.get(position)));
        holder.company.setText(String.valueOf(company.get(position)));
        holder.location.setText(String.valueOf(location.get(position)));
        holder.salary.setText(String.valueOf(salary.get(position)));

    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,company,location,salary;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.job_title_item);
            company = itemView.findViewById(R.id.company_name_item);
            location = itemView.findViewById(R.id.company_location_item);
            salary = itemView.findViewById(R.id.salary_range_item);
        }
    }
}
