package com.example.startupconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycleLogin extends RecyclerView.Adapter<AdapterRecycleLogin.Holder> {

    Context context;
    ArrayList<ContentRecylelogin> arr;
    public static final String EXTRA_USERNAME="com.example.startupconnect.example.EXTRA_USERNAME";
    public static final String EXTRA_SPORT="com.example.startupconnect.example.EXTRA_SPORT";
    public AdapterRecycleLogin(Context context, ArrayList<ContentRecylelogin> arr){
        this.context=context;
        this.arr=arr;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_row,parent,false);
        Holder viewHolder=new Holder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String userEmail=LoginActivity.getData();
        String sport=arr.get(position).s;
        holder.t1.setText(arr.get(position).s);
        holder.t1.setBackgroundResource(arr.get(position).img);
        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity2.class);
                intent.putExtra(EXTRA_USERNAME,userEmail);
                intent.putExtra(EXTRA_SPORT,sport);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView t1;
        public Holder(@NonNull View itemView) {
            super(itemView);

            t1=itemView.findViewById(R.id.text1);
        }
    }
}
