package com.MuhammadDanyialKhan.matrices_ecommerceapp.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.MuhammadDanyialKhan.matrices_ecommerceapp.MainActivity;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.Model.CountryData;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.PhoneVerificationActivity;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import jp.wasabeef.blurry.Blurry;

public class SignupTab extends Fragment {

    Spinner spinner;
    private Button btnCreateAccount;
    private EditText txtPhoneNo, countryCOde;
    private boolean blurred=false;
    String code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_lragment, container, false);

        btnCreateAccount=(Button) view.findViewById(R.id.RegisterButton);
        txtPhoneNo=(EditText) view.findViewById(R.id.Register_phone_no_input);
        countryCOde=(EditText) view.findViewById(R.id.txtcountryCode);

        spinner = (Spinner) view.findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

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

        return view;
    }



    private void createAccount() {
        String phone=txtPhoneNo.getText().toString().trim();

        if(phone.isEmpty()){
            Toast.makeText(getActivity(), R.string.ErrPhoneIsEmpth, Toast.LENGTH_LONG).show();
        }
        else{
            phone = "+" + code + phone;

            Intent intent = new Intent(getActivity(), PhoneVerificationActivity.class);
            intent.putExtra("phone", phone);
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
                                        Toast.makeText(getActivity(), "Account Successfully Created..",Toast.LENGTH_LONG).show();
                                        // blurall();
                                        Intent intent = new Intent(getActivity(), LoginTab.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getActivity(), task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        //blurall();
                                    }
                                }
                            });

                }
                else {
                    Toast.makeText(getActivity(), "This "+phone+" already exists",Toast.LENGTH_LONG).show();

                    //  blurall();
                    Toast.makeText(getActivity(), "Please try agaain using another Phone number.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void blurall(View view) {


        if (blurred) {
            Blurry.delete((ViewGroup) view.findViewById(R.id.Register_Layout));
        } else {
            long startMs = System.currentTimeMillis();
            Blurry.with(getActivity())
                    .radius(25)
                    .sampling(2)
                    .async()
                    .animate(500)
                    .onto((ViewGroup) view.findViewById(R.id.Register_Layout));
            Log.d(getString(R.string.app_name),
                    "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
        }

        blurred = !blurred;


    }

}
