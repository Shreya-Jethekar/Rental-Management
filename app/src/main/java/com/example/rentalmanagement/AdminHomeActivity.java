package com.example.rentalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
TextView a_h_name,a_h_email,a_h_password, admin_ID_KEY;
Button btn_a_h_a, btn_admin_logout;
Button btn_addshop_activity, refresh_adminactivity;
SwipeRefreshLayout swipeRefreshLayout_admin;
//Setting refernce for user...//
String a_USERID;
DatabaseReference a_refernce;
FirebaseUser a_user;


//Setting Recycle view for Shops in Complex..//
    RecyclerView admin_recyclerView;
    Admin_home_adapter admin_home_adapter;
   public String s_USERID;
    DatabaseReference s_refernce;
    FirebaseUser s_user;


    ArrayList<DATA> shopList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

//        swipeRefreshLayout_admin=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_admin);
        a_h_email=(TextView) findViewById(R.id.a_h_email);
        a_h_password=(TextView) findViewById(R.id.a_h_password);
        a_h_name=(TextView) findViewById(R.id.a_h_name);
        admin_ID_KEY=(TextView) findViewById(R.id.textView_admin_userKey);
        btn_a_h_a=(Button) findViewById(R.id.a_h_btn);
        btn_addshop_activity=(Button)findViewById(R.id.btn_fab_addshop_activity);
        btn_admin_logout=(Button)findViewById(R.id.btn_admin_logout);
        refresh_adminactivity =(Button)findViewById(R.id.refresh_admin);

        refresh_adminactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopList.clear();
                DisplayData();

                admin_home_adapter.notifyDataSetChanged();
                admin_recyclerView.setLayoutManager(new LinearLayoutManager(AdminHomeActivity.this));

                admin_recyclerView.setAdapter(admin_home_adapter);

            }
        });
        //Setting Recycle view///
        admin_recyclerView = (RecyclerView) findViewById(R.id.recycleview_admin_home);
        admin_home_adapter = new Admin_home_adapter(AdminHomeActivity.this, shopList);


        s_user=FirebaseAuth.getInstance().getCurrentUser();
        s_refernce=FirebaseDatabase.getInstance().getReference("SHOPS");

        s_USERID=s_user.getUid();
        DisplayData();

        admin_home_adapter.notifyDataSetChanged();
        admin_recyclerView.setLayoutManager(new LinearLayoutManager(AdminHomeActivity.this));

        admin_recyclerView.setAdapter(admin_home_adapter);





         ///Logout Button...//
        btn_admin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent=new Intent(AdminHomeActivity.this,LoginActivity.class);
                startActivity(intent);
                AdminHomeActivity.this.finish();
            }
        });

      // Databse Refernce value///
        a_user=FirebaseAuth.getInstance().getCurrentUser();
        a_refernce=FirebaseDatabase.getInstance().getReference("ADMIN");
        a_USERID=a_user.getUid();
        admin_ID_KEY.setText(a_USERID);

        ///Loading the Contents from Firebase//for Login details//
        a_refernce.child(a_USERID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_admin user_admin= snapshot.getValue(User_admin.class);

                if(user_admin !=null){
                    String a_name= user_admin.a_admin_name;
                    String a_email= user_admin.a_admin_email;
                    String a_password= user_admin.a_admin_password;

                   a_h_email.setText(a_email);
                   a_h_name.setText(a_name);
                   a_h_password.setText(a_password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Button for next Final Activity//
        btn_a_h_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this,AdminFinalActivity.class);
                 startActivity(intent);
            }
        });

        //Button for adding new Shop details//
        btn_addshop_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AdminHomeActivity.this,AddShopActivity.class);
                startActivity(intent);
            }
        });

    }



    private void DisplayData() {
        s_refernce.child(s_USERID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dss: snapshot.getChildren()){
//
//                        DATA shop=dss.getValue(DATA.class);
                        String shop_name=dss.child("desc").getValue().toString();
                        String rent_amount=dss.child("rentpermonth").getValue().toString();
                        String status_dis=dss.child("status").getValue().toString();
                        DATA shop=new DATA(shop_name,rent_amount,status_dis);


                        shopList.add(shop);//Storing snapshot data to array list & then displaying in recycle view//


                    }
//                    admin_home_adapter.notifyAll();
                    admin_home_adapter.notifyDataSetChanged();
                    admin_recyclerView.setLayoutManager(new LinearLayoutManager(AdminHomeActivity.this));

                    admin_recyclerView.setAdapter(admin_home_adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminHomeActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}