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

public class myJobsAdapter extends RecyclerView.Adapter<myJobsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList title,company;

    public myJobsAdapter(Context context, ArrayList title, ArrayList company) {
        this.context = context;
        this.title = title;
        this.company = company;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_my_jobs,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(String.valueOf(title.get(position)));
        holder.company.setText(String.valueOf(company.get(position)));

    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,company;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.job_title_);
            company = itemView.findViewById(R.id.company_name_);
        }
    }
}
