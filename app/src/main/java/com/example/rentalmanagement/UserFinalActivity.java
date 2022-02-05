package com.example.rentalmanagement;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFinalActivity extends AppCompatActivity {
private Button btn_ok,btn_search;
    String uf_USERID;
    DatabaseReference uf_refernce;
    FirebaseUser uf_user;
private TextView  u_shopname,u_tenantname,u_tenantmno,u_rentpermonth,u_defaultmonth,u_rentpaid,u_occupiedmonth,u_pendinfAmt,u_paidamt;
private EditText admin_name,shop_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_final);


        btn_ok=(Button) findViewById(R.id.u_btn_ok);
        btn_search=(Button) findViewById(R.id.u_btn_search_user);
        // initializing our Textview and button
        u_shopname =(TextView) findViewById(R.id.u_shopname);
        u_tenantname =(TextView) findViewById(R.id.u_tenantname);
        u_paidamt =(TextView) findViewById(R.id.u_paid_amt);
        u_tenantmno = (TextView)findViewById(R.id.u_tenantmno);
        u_rentpermonth =(TextView) findViewById(R.id.u_per_month_rent);
        u_defaultmonth =(TextView) findViewById(R.id.u_default_months);
        u_rentpaid = (TextView) findViewById(R.id.u_rent_paid);
        u_occupiedmonth = (TextView) findViewById(R.id.u_occupied_month);
        u_pendinfAmt = (TextView) findViewById(R.id.u_pending_amt);

        admin_name=(EditText) findViewById(R.id.ed_admin_name_user);
        shop_name=(EditText) findViewById(R.id.ed_shop_user);




        uf_user= FirebaseAuth.getInstance().getCurrentUser();
        uf_refernce= FirebaseDatabase.getInstance().getReference("RentInfo");

        uf_USERID=uf_user.getUid();


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String ADMIN_NAME= admin_name.getText().toString().trim();
               String SHOP_NAME=shop_name.getText().toString().trim();

                if(TextUtils.isEmpty(ADMIN_NAME)){
                    admin_name.setError("Admin name is required");
                }
                if(TextUtils.isEmpty(SHOP_NAME)){
                   shop_name.setError("Admin name is required");
                }

                if(TextUtils.isEmpty(ADMIN_NAME) && TextUtils.isEmpty(SHOP_NAME)){
                    Toast.makeText(UserFinalActivity.this, "Plz fill required info....", Toast.LENGTH_SHORT).show();
                }
                else {

                    //Method which calls automatically
                    D(ADMIN_NAME,SHOP_NAME);
                }
            }
        });

        
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Intent intent= new Intent(UserFinalActivity.this,AddShopActivity.class);
//               startActivity(intent);
                System.exit(0);
            }
        });
    }

    private void Calculate() {
            String permonth_payment=u_rentpermonth.getText().toString();
            String default_month=u_defaultmonth.getText().toString();
            String paid_amt=u_paidamt.getText().toString();

            int t1= Integer.parseInt(permonth_payment);
            int t2= Integer.parseInt(default_month);

            int total_amt=t1*t2;

        int pending_rent=total_amt-Integer.parseInt(paid_amt);
        String r= Integer.toString(pending_rent);
        u_pendinfAmt.setText(r);

        Toast.makeText(UserFinalActivity.this, "Pending Amount ="+pending_rent, Toast.LENGTH_SHORT).show();


    }


    private void D(String ADMIN_NAME, String SHOP_NAME) {
        uf_refernce.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
//                    Toast.makeText(UserFinalActivity.this, "D1", Toast.LENGTH_SHORT).show();
                      for(DataSnapshot s:snapshot.getChildren()){
                          String nama = s.getKey();//Name of child //here 2;
//                          Toast.makeText(UserFinalActivity.this, "D2= "+nama, Toast.LENGTH_SHORT).show();//Worked...
                             for(DataSnapshot ss: snapshot.child(ADMIN_NAME).getChildren()){
                                 String nama_child = ss.getKey();//Name of child //here 2;
//                                 Toast.makeText(UserFinalActivity.this, "D3= "+nama_child, Toast.LENGTH_SHORT).show();//Worked...

                                 if(nama_child.equals(SHOP_NAME)){
//                                     Toast.makeText(UserFinalActivity.this, "D4= ..Matched", Toast.LENGTH_SHORT).show();//Worked...
                                         FinalData fd=ss.getValue(FinalData.class);
                                     if(fd !=null){
//                                         Toast.makeText(UserFinalActivity.this, "Entered", Toast.LENGTH_SHORT).show();

                                         String ShopName,TenantName,TenantMno,PerMonthRent,DefaultMonth,RentPaid,OccupiedMonth,Paidamt;
                                          DefaultMonth= fd.DefaultMonth;
                                          OccupiedMonth=fd.OccupiedMonth;
                                          PerMonthRent=fd.PerMonthRent;
                                          RentPaid=fd.RentPaid;
                                          ShopName=fd.ShopName;
                                          TenantName=fd.TenantName;
                                          TenantMno=fd.TenantMno;
                                          Paidamt=fd.paidamt;

//                                         Toast.makeText(UserFinalActivity.this, "Tenanr mobile No="+TenantMno, Toast.LENGTH_SHORT).show();

                                                 u_defaultmonth.setText(DefaultMonth);
                                                 u_shopname.setText(ShopName);
                                                 u_tenantname.setText(TenantName);
                                                 u_rentpermonth.setText(PerMonthRent);
                                                 u_rentpaid.setText(RentPaid);
                                                 u_occupiedmonth.setText(OccupiedMonth);
                                                 u_tenantmno.setText(TenantMno);
                                                 u_paidamt.setText(Paidamt);
                                         Calculate();

                                     }

                                 }

                             }
                      }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
         }
