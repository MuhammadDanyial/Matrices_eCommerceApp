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
import com.MuhammadDanyialKhan.matrices_ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginTab extends Fragment {

    private Button btnLogin;
    private EditText phoneNo, password;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.login_fragement, container, false);

        btnLogin = (Button)  View.findViewById(R.id.LoginButton);

        phoneNo= (EditText) View.findViewById(R.id.LOGIN_phone_no_input);
        password= (EditText) View.findViewById(R.id.login_password_input);

        mAuth=FirebaseAuth.getInstance();

        Paper.init(getActivity());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = phoneNo.getText().toString().trim();
                final String _password=password.getText().toString();
                final String email = "+92"+phone+"@gmail.com";
                if(phone.isEmpty()){
                    Toast.makeText(getActivity(), R.string.ErrPhoneIsEmpth, Toast.LENGTH_LONG).show();
                }
                else if(_password.isEmpty()){
                    Toast.makeText(getActivity(), R.string.ErrPasswordIsEmpth, Toast.LENGTH_LONG).show();
                }else {

                    mAuth.signInWithEmailAndPassword(email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //we can return to another activity
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                   /* if(checkBoxRememberMe.isChecked()) {
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

*/
                }
            }
        });

        return View;
    }
}
