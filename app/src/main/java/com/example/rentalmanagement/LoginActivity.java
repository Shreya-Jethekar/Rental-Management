package com.example.rentalmanagement;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText l_email,l_password;
    Button l_btn_login;
    TextView l_textView;
    ProgressBar l_progressBar;
    FirebaseAuth fath;

    Switch s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        l_email= (EditText) findViewById(R.id.editext_email_id);
        l_password=(EditText)findViewById(R.id.editext_password);
        l_btn_login=(Button) findViewById(R.id.btn_Login);
        l_textView=(TextView)findViewById(R.id.textView_login_to_register);
        l_progressBar=(ProgressBar)findViewById(R.id.progressBar_Login_activity);
        Switch s2 = (Switch) findViewById(R.id.switch_login);


        Toast.makeText(LoginActivity.this, "Alert...." +
                "Plz Choose Login type clearly", Toast.LENGTH_SHORT).show();
        l_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSignup();
            }
        });

        fath=FirebaseAuth.getInstance();
        l_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String EMAIL=l_email.getText().toString().trim();
                String PASSWORD=l_password.getText().toString().trim();

                if(TextUtils.isEmpty(EMAIL))
                {
                    l_email.setError("Email is required");
                }
                if(TextUtils.isEmpty(PASSWORD))
                {
                    l_password.setError("Password is required");
                }
                if(l_password.length()<6)
                {
                    l_password.setError("Password must be greater than 6 characters");
                }


                l_progressBar.setVisibility(View.VISIBLE);


                fath.signInWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            if(s2.isChecked()){
                                Toast.makeText(LoginActivity.this,"Login as USER Succesfull",Toast.LENGTH_LONG).show();
                                 Intent intent=new Intent(LoginActivity.this,UserHomeActivity.class);
                                startActivity(intent);

                                }
                            else if( ! s2.isChecked()){
                                Toast.makeText(LoginActivity.this,"Login as ADMIN Succesfull",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(LoginActivity.this,AdminHomeActivity.class);
                                startActivity(intent);
                            }

                            l_progressBar.setVisibility(View.INVISIBLE);

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Login is not Succesfull",Toast.LENGTH_LONG).show();
                            l_progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });





    }



    private void OpenSignup() {
        Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}