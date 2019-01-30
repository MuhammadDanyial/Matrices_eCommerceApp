package com.MuhammadDanyialKhan.matrices_ecommerceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button PhoneButton, GoogleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PhoneButton = findViewById(R.id.main_join_now_with_Phone_button);
        GoogleButton=findViewById(R.id.main_google_button);



        GoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleSignin.class);
                startActivity(intent);
            }
        });

        PhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginOrSignup.class);
                startActivity(intent);
            }
        });



    }


}
