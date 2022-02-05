package com.example.rentalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
Button btn_signup;
TextView r_login_textview;
EditText email,password,name;
ProgressBar p1;
FirebaseAuth auth;
Switch s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        r_login_textview=(TextView) findViewById(R.id.textView_register_to_login);
        btn_signup=(Button) findViewById(R.id.btn_signup);
        email=(EditText)findViewById(R.id.u_email);
        name=(EditText)findViewById(R.id.u_name);
        password=(EditText)findViewById(R.id.u_password);
        p1= (ProgressBar)findViewById(R.id.p1_signup_U);
        auth=FirebaseAuth.getInstance();
        Switch s = (Switch) findViewById(R.id.switch1);


////        Keep signup code...
//        if(auth.getCurrentUser()!=null)
//        {
//            startActivity(new Intent(MainActivity.this,UserHomeActivity.class));
//            finish();
//        }


        r_login_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                String NAME=name.getText().toString().trim();
                String PASSWORD=password.getText().toString().trim();
                String EMAIL=email.getText().toString().trim();

                if(TextUtils.isEmpty(NAME))
                {
                    name.setError("Name is required");
                }
                if(TextUtils.isEmpty(PASSWORD))
                {
                    password.setError("lastname is required");
                }
                if(TextUtils.isEmpty(EMAIL))
                {
                    email.setError("email is required");
                }
                if(PASSWORD.length()>6)
                {
                    password.setError("password is required");
                }



                p1.setVisibility(View.VISIBLE);
                if(s.isChecked())
                {

                    auth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User_user user_us=new User_user(EMAIL,PASSWORD,NAME);
                                FirebaseDatabase.getInstance().getReference("USERS").child(FirebaseAuth.getInstance().getUid()).setValue(user_us).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(SignUpActivity.this, "User Created Sucessfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));
                                            finish();

                                        }
                                        else{
                                            Toast.makeText(SignUpActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    p1.setVisibility(View.INVISIBLE);
                }

                else{
                    auth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User_admin admin_us=new User_admin(EMAIL,PASSWORD,NAME);
                                FirebaseDatabase.getInstance().getReference("ADMIN").child(FirebaseAuth.getInstance().getUid()).setValue(admin_us).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(SignUpActivity.this, "Admin Created Sucessfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), AdminHomeActivity.class));
                                            finish();

                                        }
                                        else{
                                            Toast.makeText(SignUpActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                            p1.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
        });
    }
}