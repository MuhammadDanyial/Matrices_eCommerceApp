package com.MuhammadDanyialKhan.matrices_ecommerceapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.MuhammadDanyialKhan.matrices_ecommerceapp.Model.CountryData;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import jp.wasabeef.blurry.Blurry;

public class SignUpActivity extends AppCompatActivity {

    Spinner spinner;
    private Button btnCreateAccount;
    private EditText txtPhoneNo, countryCOde;
    private boolean blurred=false;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnCreateAccount=findViewById(R.id.RegisterButton);
        txtPhoneNo=findViewById(R.id.Register_phone_no_input);
        countryCOde=findViewById(R.id.txtcountryCode);


        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                countryCOde.setText("+" + code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String phone=txtPhoneNo.getText().toString().trim();

        if(phone.isEmpty()){
            Toast.makeText(SignUpActivity.this, R.string.ErrPhoneIsEmpth, Toast.LENGTH_LONG).show();
        }
        else{
            phone = "+" + code + phone;

            Intent intent = new Intent(SignUpActivity.this, PhoneVerificationActivity.class);
            intent.putExtra("phone", phone);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        //blurall();
                                    }
                                }
                            });

                }
                else {
                    Toast.makeText(SignUpActivity.this, "This "+phone+" already exists",Toast.LENGTH_LONG).show();

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
