package com.MuhammadDanyialKhan.matrices_ecommerceapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import jp.wasabeef.blurry.Blurry;

public class SignUpActivity extends AppCompatActivity {

    private Button btnCreateAccount;
    private EditText txtUserName, txtPassword, txtConfirmPassword, txtPhoneNo;
    private ProgressBar loadingBar;
    private Handler hdl;
    private boolean blurred=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnCreateAccount=findViewById(R.id.RegisterButton);
        txtUserName=findViewById(R.id.Register_UserName_input);
        txtPhoneNo=findViewById(R.id.Register_phone_no_input);
        txtPassword=findViewById(R.id.Register_password_input);
        txtConfirmPassword=findViewById(R.id.Register_ConfirmPassword_input);
        loadingBar = findViewById(R.id.progressBarDialog);
        hdl = new Handler();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String name=txtUserName.getText().toString().trim();
        String phone=txtPhoneNo.getText().toString().trim();
        String password=txtPassword.getText().toString();
        String confirm=txtConfirmPassword.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.ErrNameIsEmpty, Toast.LENGTH_LONG).show();
        }
        else if(phone.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.ErrPhoneIsEmpth, Toast.LENGTH_LONG).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.ErrPhoneIsEmpth, Toast.LENGTH_LONG).show();
        }
        else if(!confirm.equals(password)){
            Toast.makeText(SignUpActivity.this, R.string.ErrPasswordNotMatch, Toast.LENGTH_LONG).show();
        }
        else{
            txtUserName.setEnabled(false);
            txtPassword.setEnabled(false);
            txtConfirmPassword.setEnabled(false);
            txtPhoneNo.setEnabled(false);
            btnCreateAccount.setEnabled(false);
            velidatePhoneNumber(phone,name, password);
        }
    }

    private void velidatePhoneNumber(final String phone, final String name, final String password) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone);
                    userDataMap.put("password", password);
                    userDataMap.put("name", name);
                    rootRef.child("Users")
                            .child(phone)
                            .updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Account Successfully Created..",Toast.LENGTH_LONG).show();
                                       // blurall();
                                        txtUserName.setEnabled(true);
                                        txtPassword.setEnabled(true);
                                        txtConfirmPassword.setEnabled(true);
                                        txtPhoneNo.setEnabled(true);
                                        btnCreateAccount.setEnabled(true);
                                        txtUserName.setText(null);
                                        txtPassword.setText(null);
                                        txtConfirmPassword.setText(null);
                                        txtPhoneNo.setText(null);
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        //blurall();
                                        txtUserName.setEnabled(true);
                                        txtPassword.setEnabled(true);
                                        txtConfirmPassword.setEnabled(true);
                                        txtPhoneNo.setEnabled(true);
                                        btnCreateAccount.setEnabled(true);
                                    }
                                }
                            });

                }
                else {
                    Toast.makeText(SignUpActivity.this, "This "+phone+" already exists",Toast.LENGTH_LONG).show();
                    txtUserName.setEnabled(true);
                    txtPassword.setEnabled(true);
                    txtConfirmPassword.setEnabled(true);
                    txtPhoneNo.setEnabled(true);
                    btnCreateAccount.setEnabled(true);
                  //  blurall();
                    Toast.makeText(SignUpActivity.this, "Please try agaain using another Phone number.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void blurall() {


        if (blurred) {
            Blurry.delete((ViewGroup) findViewById(R.id.Register_Layout));
        } else {
            long startMs = System.currentTimeMillis();
            Blurry.with(SignUpActivity.this)
                    .radius(25)
                    .sampling(2)
                    .async()
                    .animate(500)
                    .onto((ViewGroup) findViewById(R.id.Register_Layout));
            Log.d(getString(R.string.app_name),
                    "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
        }

        blurred = !blurred;


    }
}
