package com.example.asus.findyourhomemate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class Adapter extends RecyclerView.Adapter<Adapter.VHolder> {
    ArrayList<common.Announcement> data;
    Context context;
    public Adapter(ArrayList<common.Announcement> data, Context context){
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder vHolder, final int i) {
        vHolder.textView.setText(data.get(i).owner);
        vHolder.textView2.setText(data.get(i).city);
        vHolder.textView3.setText(data.get(i).neighbour);
        vHolder.textView4.setText(data.get(i).explanation);
        vHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String position = Integer.toString(i);
                Toast.makeText(context.getApplicationContext(),position, LENGTH_LONG).show();
                Intent launchActivity= new Intent(context,Announcement.class);
                launchActivity.putExtra("owner", data.get(i).owner);
                launchActivity.putExtra("city", data.get(i).city);
                launchActivity.putExtra("neighbour", data.get(i).neighbour);
                launchActivity.putExtra("explanation", data.get(i).explanation);
                launchActivity.putExtra("country", data.get(i).country);
                launchActivity.putExtra("street", data.get(i).street);
                launchActivity.putExtra("buildingnumber", data.get(i).buildingnumber);
                launchActivity.putExtra("zipcode", data.get(i).zipcode);
                launchActivity.putExtra("userid", data.get(i).id);
                launchActivity.putExtra("annoucementid", data.get(i).announcementid);
                context.startActivity(launchActivity);
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        LinearLayout ll;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtItem);
            textView2 = itemView.findViewById(R.id.txtItem2);
            textView3 = itemView.findViewById(R.id.txtItem3);
            textView4 = itemView.findViewById(R.id.txtItem4);
            ll = itemView.findViewById(R.id.items);

        }
    }

}
