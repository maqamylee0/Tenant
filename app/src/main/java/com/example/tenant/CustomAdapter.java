package com.example.tenant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList tenant_id,tenant_name,tenant_balance,tenant_cell,tenant_amount;
   // int position;
    Activity activty;

    CustomAdapter(Activity activity,Context context,ArrayList tenant_id,
                  ArrayList tenant_name,
                  ArrayList tenant_balance,
                  ArrayList tenant_cell,
                  ArrayList  tenant_amount){
        this.activty=activity;
        this.context=context;
        this.tenant_id=tenant_id;
        this.tenant_name=tenant_name;
        this.tenant_balance=tenant_balance;
        this.tenant_cell=tenant_cell;
        this.tenant_amount=tenant_amount;

    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CustomAdapter.MyViewHolder holder, int position) {
       // this.position=position;
//        holder.tenant_id_txt.setText(String.valueOf(tenant_id.get(position)));
        holder.tenant_name_txt.setText(String.valueOf(tenant_name.get(position)));
        //holder.tenant_balance_txt.setText(String.valueOf(tenant_balance.get(position)));
        //holder.tenant_cell_txt.setText(String.valueOf(tenant_cell.get(position)));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(context,Tenant_Detail.class);
            intent.putExtra("id",String.valueOf(tenant_id.get(position)));
                intent.putExtra("name",String.valueOf(tenant_name.get(position)));
                intent.putExtra("balance",String.valueOf(tenant_balance.get(position)));
                intent.putExtra("cell",String.valueOf(tenant_cell.get(position)));
                intent.putExtra("amount",String.valueOf(tenant_amount.get(position)));

                activty.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {

        return tenant_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenant_id_txt,tenant_name_txt,tenant_balance_txt,tenant_cell_txt,tenant_amount_txt;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemview) {
            super(itemview);
            tenant_name_txt=itemview.findViewById(R.id.tenant_name_txt);
            //tenant_balance_txt=itemview.findViewById(R.id.tenant_balance_txt);
           // tenant_cell_txt=itemview.findViewById(R.id.tenant_cell_txt);
           // tenant_amount_txt=itemview.findViewById(R.id.tenant_amount_txt);
            linearLayout=itemview.findViewById(R.id.mainLayout);

        }
    }
}
