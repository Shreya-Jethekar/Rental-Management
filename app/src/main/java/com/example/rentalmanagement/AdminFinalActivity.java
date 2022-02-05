//package com.example.rentalmanagement;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.example.rentalmanagement.R;
//
//public class AdminFinalActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_final);
//    }
//}

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

public class AdminFinalActivity extends AppCompatActivity {

    // creating variables for
    // EditText and buttons.
    TextView a_pendingamt;
    private EditText shopname,tenantname,tenantmno,rentpermonth,defaultmonth,rentpaid,occupiedmonth,a_paid_amt;
    private Button sendDatabtn;
    FirebaseUser c_user;
    String c_USERID;
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    FinalData RentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_final);

        // initializing our edittext and button
        shopname = findViewById(R.id.a_shopname);
        tenantname = findViewById(R.id.a_tenantname);
        tenantmno = findViewById(R.id.a_tenantmno);
        rentpermonth = findViewById(R.id.a_per_month_rent);
        defaultmonth = findViewById(R.id.a_default_months);
        rentpaid = findViewById(R.id.a_rent_paid);
        occupiedmonth = findViewById(R.id.a_occupied_month);
        a_paid_amt = findViewById(R.id.a_paid_amt);
        a_pendingamt=findViewById(R.id.a_pendingamt);
        sendDatabtn = findViewById(R.id.a_btn_ok);

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        c_user= FirebaseAuth.getInstance().getCurrentUser();

        c_USERID=c_user.getUid();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("RentInfo");

        // initializing our object
        // class variable.
        RentInfo = new FinalData();


        // adding on click listener for our button.
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_deatils();
            }
        });
    }

    private void Calculate_admin() {
        String permonth_payment=rentpermonth.getText().toString();
        String default_month=defaultmonth.getText().toString();
        String paid_amt=a_paid_amt.getText().toString();

        int t1= Integer.parseInt(permonth_payment);
        int t2= Integer.parseInt(default_month);

        int total_amt=t1*t2;

        int pending_rent=total_amt-Integer.parseInt(paid_amt);
        String r= Integer.toString(pending_rent);
        a_pendingamt.setText(r);

    }

    public void upload_deatils(){
        // getting text from our edittext fields.

        String sshopname = shopname.getText().toString();
        String stenantname = tenantname.getText().toString();
        String stenantmno = tenantmno.getText().toString();
        String srentpermonth = rentpermonth.getText().toString();
        String sdefaultmonth = defaultmonth.getText().toString();
        String srentpaid = rentpaid.getText().toString();
        String paidamt = a_paid_amt.getText().toString();
        String soccupiedmonth = occupiedmonth.getText().toString();
        Calculate_admin();

        // below line is for checking weather the
        // edittext fields are empty or not.
        if (TextUtils.isEmpty(sshopname) || TextUtils.isEmpty(stenantname)||TextUtils.isEmpty(stenantmno)
                || TextUtils.isEmpty(srentpaid) || TextUtils.isEmpty(srentpermonth)
                || TextUtils.isEmpty(sdefaultmonth) || TextUtils.isEmpty(soccupiedmonth)||TextUtils.isEmpty(paidamt)) {
            // if the text fields are empty
            // then show the below message.
            Toast.makeText(AdminFinalActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
        } else {
            // else call the method to add
            // data to our database.
            addDatatoFirebase(sshopname, stenantname,stenantmno, srentpermonth, sdefaultmonth, srentpaid, soccupiedmonth, paidamt);
        }
    }
    private void addDatatoFirebase( String shopname, String tenantname, String tenantmno ,String rentpermonth, String defaultmonth,
                                    String rentpaid, String occupiedmonth,String paidamt) {
        // below 3 lines of code is used to set
        // data in our object class.
        //String sshopid = databaseReference.push().getKey();
        RentInfo.setShopName(shopname);
        RentInfo.setTenantName(tenantname);
        RentInfo.setTenantMno(tenantmno);
        RentInfo.setPerMonthRent(rentpermonth);
        RentInfo.setDefaultMonth(defaultmonth);
        RentInfo.setRentPaid(rentpaid);
        RentInfo.setOccupiedMonth(occupiedmonth);
        RentInfo.setPaidamt(paidamt);
        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child(c_USERID).child(shopname).setValue(RentInfo);

                // after adding this data we are showing toast message.
                Toast.makeText(AdminFinalActivity.this, "Data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(AdminFinalActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
