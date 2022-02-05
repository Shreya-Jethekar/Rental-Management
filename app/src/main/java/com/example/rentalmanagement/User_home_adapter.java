package com.example.rentalmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class User_home_adapter extends RecyclerView.Adapter<User_home_adapter.userMyViewholder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<DATA> SHOPLIST;

    public User_home_adapter(Context context, ArrayList<DATA> SHOPLIST) {
        this.context=context;
        this.SHOPLIST=SHOPLIST;
    }

    @NonNull
    @Override
    public userMyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
//        userMyViewHolder myholder=new userMyViewHolder(view);
        userMyViewholder myholder=new userMyViewholder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull userMyViewholder holder, int position) {
int currentposition=position;
        holder.textView_shopname_user.setText(SHOPLIST.get(position).desc);
        holder.textView_rent_per_month_user.setText(SHOPLIST.get(position).rentpermonth);
        holder.textView_status.setText(SHOPLIST.get(position).status);
        holder.linearLayout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//               Intent intent =  new Intent(context, UserFinalActivity.class);
//
//               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return SHOPLIST.size();
    }

    public class userMyViewholder extends RecyclerView.ViewHolder {
LinearLayout linearLayout_user;
//Context context1;
        TextView textView_shopname_user,textView_rent_per_month_user,textView_status;
        public userMyViewholder(@NonNull View itemView) {
            super(itemView);
            textView_shopname_user= itemView.findViewById(R.id.t1_item_shopname_user);
            textView_rent_per_month_user= itemView.findViewById(R.id.t1_item_rentpermonth_user);
            textView_status= itemView.findViewById(R.id.textview_userHome_status);
            linearLayout_user=itemView.findViewById(R.id.linearlayot_user_item);
            context = itemView.getContext();
        }
    }
}