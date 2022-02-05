package com.example.rentalmanagement;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddShopActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyCustomAdapter adapter;
    FloatingActionButton btn_fab;
    ArrayList<DATA> arraylist = new ArrayList<>();
    String status_of_shop;

    EditText edtname, rent_per,text_status;

    String s_USERID;
    DatabaseReference s_refernce;
    FirebaseUser s_user;

    public AddShopActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);


        btn_fab = (FloatingActionButton) findViewById(R.id.btn_fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_admin_shoplist);
        adapter = new MyCustomAdapter(AddShopActivity.this, arraylist);

        s_user=FirebaseAuth.getInstance().getCurrentUser();
        s_refernce=FirebaseDatabase.getInstance().getReference("SHOPS");

        s_USERID=s_user.getUid();


        DisplayData();

        recyclerView.setLayoutManager(new LinearLayoutManager(AddShopActivity.this));

        recyclerView.setAdapter(adapter);


        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AddShopActivity.this);
                dialog.setContentView(R.layout.add_dialog);

                edtname = dialog.findViewById(R.id.add_text);
                rent_per = dialog.findViewById(R.id.add_text_rentpermonth);
                text_status=dialog.findViewById(R.id.add_status);
                Button btn = dialog.findViewById(R.id.add_btn);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String NAME = "";
                        String RENT_PER_MONTH="";
                        String STATUS="";

                        if (!edtname.getText().toString().equals("") && !rent_per.getText().toString().equals("") && !text_status.getText().toString().equals("")) {
                            NAME = edtname.getText().toString().trim();
                            RENT_PER_MONTH=rent_per.getText().toString().trim();
                            STATUS=text_status.getText().toString().trim();

                        } else {
                            Toast.makeText(AddShopActivity.this, "ERROR  name is required", Toast.LENGTH_SHORT).show();
                        }


                        dialog.dismiss();

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        arraylist.add(new DATA(NAME,RENT_PER_MONTH,STATUS));
                        ADDMethod(NAME,RENT_PER_MONTH,STATUS);
                        adapter.notifyItemInserted(arraylist.size() - 1);
                        recyclerView.scrollToPosition(arraylist.size() - 1);
                    }
                });

                dialog.show();

            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

    public void DisplayData() {
        s_refernce.child(s_USERID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot dss: snapshot.getChildren()){
                        DATA shop=dss.getValue(DATA.class);

                        arraylist.add(shop);//Storing snapshot data to array list & then displaying in recycle view//

                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(AddShopActivity.this));

                    recyclerView.setAdapter(adapter);
    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddShopActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ADDMethod(String name,String rent,String staus) {

        DATA data = new DATA(name,rent,staus);
        FirebaseDatabase.getInstance().getReference("SHOPS").child(FirebaseAuth.getInstance().getUid()).setValue(arraylist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                     Toast.makeText(AddShopActivity.this, "SHOP Created Sucessfully", Toast.LENGTH_SHORT).show();
                  }
                else {
                    Toast.makeText(AddShopActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                  }

            }
        });


    }

//    Deleting data from Firebase//
    public static void DeleteItem(int position) {
        String cur=Integer.toString(position);
        DatabaseReference deleteshop= FirebaseDatabase.getInstance().getReference("SHOPS").child(FirebaseAuth.getInstance().getUid()).child(cur);
        deleteshop.removeValue();
    }

}