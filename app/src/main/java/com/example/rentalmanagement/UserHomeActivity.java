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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {
TextView  h_name,h_email,h_password;
Button btn_nxt_user_final, btn_user_logout;
//For Retriving data from the User///
FirebaseUser user;
DatabaseReference reference;
    String USERID;

    //Setting Recycle view for Shops in Complex..//
    RecyclerView user_recyclerView;
    User_home_adapter user_home_adapter;
    String shop_USERID;
    DatabaseReference shop_refernce;
    FirebaseUser shop_user;
    ArrayList<DATA> Shoplist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        h_email=findViewById(R.id.u_h_email);
        h_password=findViewById(R.id.u_h_password);
        h_name=findViewById(R.id.u_h_name);
        btn_nxt_user_final=findViewById(R.id.nxt_btn);
        btn_user_logout=findViewById(R.id.btn_user_logout);

        //Setting Recycle view///
        user_recyclerView = (RecyclerView) findViewById(R.id.recycleview_user_home);
        user_home_adapter = new User_home_adapter(UserHomeActivity.this, Shoplist);


        shop_user=FirebaseAuth.getInstance().getCurrentUser();
        shop_refernce=FirebaseDatabase.getInstance().getReference("SHOPS");

        shop_USERID=shop_user.getUid();

        Dis();

        user_recyclerView.setLayoutManager(new LinearLayoutManager(UserHomeActivity.this));

        user_recyclerView.setAdapter(user_home_adapter);



        btn_user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class));
                Intent intent=new Intent(UserHomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //Refernce for user Login details...........
        user = FirebaseAuth.getInstance().getCurrentUser();///Make reference for user//
        reference= FirebaseDatabase.getInstance().getReference("USERS");
        USERID= user.getUid();


//For retriving the data from Database//
        reference.child(USERID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_user userprofile_user=snapshot.getValue(User_user.class);
                if(userprofile_user !=null){
                    String name_h= userprofile_user.name;
                    String password_h=userprofile_user.password;
                    String email_h=userprofile_user.email;

                    h_email.setText(email_h);
                    h_password.setText(password_h);
                    h_name.setText(name_h);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserHomeActivity.this, "Something Wrong Happend", Toast.LENGTH_SHORT).show();
            }
        });

        btn_nxt_user_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserHomeActivity.this,UserFinalActivity.class);
                startActivity(intent);

            }
        });
    }
    private void Dis() {
        shop_refernce.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dss: snapshot.getChildren()){
//                        String name= dss.getKey();//Name of child //here 2;

                        for(DataSnapshot dss_child:snapshot.getChildren()){
//                            for ( DataSnapshot i : snapshot.getChildren()){

                               String s= dss_child.getKey();
//                                    Toast.makeText(UserHomeActivity.this, "s="+s, Toast.LENGTH_SHORT).show();
                            for ( DataSnapshot i : snapshot.child(s).getChildren()){
//
//                                String ss= i.getKey();
//                                Toast.makeText(UserHomeActivity.this, "ss="+ss, Toast.LENGTH_SHORT).show();

                                DATA fd=i.getValue(DATA.class);
                                if(fd !=null){
                                    String desc=fd.desc;
                                    String rent_per_month=fd.rentpermonth;
                                    String status=fd.status;

                                    DATA shop=new DATA(desc,rent_per_month,status);


                                    Shoplist.add(shop);
                                }
                            }


                        }
                        user_recyclerView.setLayoutManager(new LinearLayoutManager(UserHomeActivity.this));

                        user_recyclerView.setAdapter(user_home_adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}