package com.example.rentalmanagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder>{
    Context context;
    LayoutInflater inflater;
    ArrayList<DATA> arrayList;
    public MyCustomAdapter(){

    }

    public MyCustomAdapter(Context context, ArrayList<DATA> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        MyViewHolder myholder=new MyViewHolder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewholder, int position) {
        int currentposition=position;
        myViewholder.textView_shopname.setText(arrayList.get(position).desc);
        myViewholder.textView_rent_per_month.setText(arrayList.get(position).rentpermonth);
        myViewholder.textView_item_status.setText(arrayList.get(position).status);
         myViewholder.linearLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Dialog dialog=new Dialog(context);
                 dialog.setContentView(R.layout.add_dialog);

                 EditText edtname=dialog.findViewById(R.id.add_text);
                 EditText  rent_per=dialog.findViewById(R.id.add_text_rentpermonth);
                 EditText  text_status=dialog.findViewById(R.id.add_status);
                 Button btn=dialog.findViewById(R.id.add_btn);
                 btn.setText("Update");
                 TextView  heading=dialog.findViewById(R.id.heading_of_dialog);
                 heading.setText("UPDATE");
                 edtname.setText((arrayList.get(currentposition)).desc);
                 rent_per.setText(arrayList.get(currentposition).rentpermonth);
                 text_status.setText(arrayList.get(currentposition).status);

                 btn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         String name="";
                         String rentpermonth="";
                         String status="";

                         if(!edtname.getText().toString().equals("") && !rent_per.getText().toString().equals("") && !text_status.getText().toString().equals("")){
                             name=edtname.getText().toString().trim();
                             rentpermonth=rent_per.getText().toString().trim();
                             status=text_status.getText().toString().trim();
                         }
                         else{
                             Toast.makeText(context, "ERROR  name is required", Toast.LENGTH_SHORT).show();
                         }
                         arrayList.set(currentposition,new DATA(name,rentpermonth,status));
                         notifyItemChanged(currentposition);

                         DatabaseReference s=FirebaseDatabase.getInstance().getReference().child("SHOPS").child(FirebaseAuth.getInstance().getUid()).child(String.valueOf(currentposition));
                             s.child("desc").setValue(name);
                             s.child("rentpermonth").setValue(rentpermonth);
                             s.child("status").setValue(status);
                         dialog.dismiss();
                     }
                 });

                 dialog.show();
             }
         });

        myViewholder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(context)
                        .setTitle("DELETE Contact")
                        .setMessage("Are you sure want to delete")
                        .setIcon(R.drawable.ic_baseline_delete_forever_24)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                AddShopActivity.DeleteItem(currentposition);

                                arrayList.remove(currentposition);
                                notifyItemRemoved(currentposition);
//
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();



                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView_shopname,textView_rent_per_month,textView_item_status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.Linearlayout_item);
            textView_shopname= itemView.findViewById(R.id.t1_item_shopname);
            textView_rent_per_month= itemView.findViewById(R.id.t1_item_rentpermonth);
            textView_item_status= itemView.findViewById(R.id.textView_item_status);



        }



    }
}
