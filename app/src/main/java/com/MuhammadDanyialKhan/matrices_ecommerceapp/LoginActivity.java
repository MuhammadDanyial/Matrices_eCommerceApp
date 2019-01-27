package com.MuhammadDanyialKhan.matrices_ecommerceapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MuhammadDanyialKhan.matrices_ecommerceapp.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText phoneNo, password;
    private String parentDBname="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=findViewById(R.id.LoginButton);

        phoneNo=findViewById(R.id.login_phone_no_input);
        password=findViewById(R.id.login_password_input);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = phoneNo.getText().toString().trim();
                final String _password=password.getText().toString();

                if(phone.isEmpty()){
                    Toast.makeText(LoginActivity.this, R.string.ErrPhoneIsEmpth, Toast.LENGTH_LONG).show();
                }
                else if(_password.isEmpty()){
                    Toast.makeText(LoginActivity.this, R.string.ErrPasswordIsEmpth, Toast.LENGTH_LONG).show();
                }else{
                    final DatabaseReference rootRef;
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(parentDBname).child(phone).exists()){
                                Users users = dataSnapshot.child(parentDBname).child(phone).getValue(Users.class);
                                if(users.getPhone().equals(phone)){
                                    if(users.getPassword().equals(_password)){
                                        Toast.makeText(LoginActivity.this, R.string.successfullLogin, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, R.string.incorrectPassword, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "Phone number not found..", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
}
