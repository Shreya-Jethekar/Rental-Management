package com.example.rentalmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Admin_home_adapter extends RecyclerView.Adapter<Admin_home_adapter.adminviewHolder> {
    Context context;
    ArrayList<DATA> shopList;
    public Admin_home_adapter(Context context, ArrayList<DATA> shopList) {
        this.context=context;
        this.shopList=shopList;
    }

    @NonNull
    @Override
    public adminviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_admin,parent,false);
        adminviewHolder myholder=new adminviewHolder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull adminviewHolder adminholder, int position) {

       adminholder.textView_shopname_admin.setText(shopList.get(position).desc);
       adminholder.textView_rent_per_month_admin.setText(shopList.get(position).rentpermonth);
       adminholder.textView_status.setText(shopList.get(position).status);

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class adminviewHolder extends RecyclerView.ViewHolder {


        TextView textView_shopname_admin,textView_rent_per_month_admin,textView_status;
        public adminviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_shopname_admin= itemView.findViewById(R.id.t1_item_shopname_admin);
            textView_rent_per_month_admin= itemView.findViewById(R.id.t1_item_rentpermonth_admin);
            textView_status=itemView.findViewById(R.id.textview_adminHome_status);

        }
    }
}
