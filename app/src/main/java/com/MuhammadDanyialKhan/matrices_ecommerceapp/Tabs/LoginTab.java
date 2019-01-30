package com.MuhammadDanyialKhan.matrices_ecommerceapp.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MuhammadDanyialKhan.matrices_ecommerceapp.HomeActivity;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.Model.Users;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.Prevalent.Prevalent;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginTab extends Fragment {

    private Button btnLogin;
    private EditText phoneNo, password;
    private static final String parentDBname="Users";
    private CheckBox checkBoxRememberMe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.login_fragement, container, false);

        btnLogin = (Button)  View.findViewById(R.id.LoginButton);
        checkBoxRememberMe = (CheckBox)View.findViewById(R.id.Login_rememberme_chkb);

        phoneNo= (EditText) View.findViewById(R.id.login_phone_no_input);
        password= (EditText) View.findViewById(R.id.login_password_input);

        Paper.init(getActivity());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = phoneNo.getText().toString().trim();
                final String _password=password.getText().toString();

                if(phone.isEmpty()){
                    Toast.makeText(getActivity(), R.string.ErrPhoneIsEmpth, Toast.LENGTH_LONG).show();
                }
                else if(_password.isEmpty()){
                    Toast.makeText(getActivity(), R.string.ErrPasswordIsEmpth, Toast.LENGTH_LONG).show();
                }else{

                    if(checkBoxRememberMe.isChecked()) {
                        Paper.book().write(Prevalent.UserPhoneKey, phone);
                        Paper.book().write(Prevalent.UserPasswordKey, _password);
                    }

                    final DatabaseReference rootRef;
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(parentDBname).child(phone).exists()){
                                Users users = dataSnapshot.child(parentDBname).child(phone).getValue(Users.class);
                                if(users.getPhone().equals(phone)){
                                    if(users.getPassword().equals(_password)){
                                        Toast.makeText(getActivity(), R.string.successfullLogin, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getActivity(), R.string.incorrectPassword, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }else {
                                Toast.makeText(getActivity(), "Phone number not found..", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });

        return View;
    }
}
