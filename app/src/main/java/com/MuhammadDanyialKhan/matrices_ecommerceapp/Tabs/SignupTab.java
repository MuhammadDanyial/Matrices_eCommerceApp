package com.MuhammadDanyialKhan.matrices_ecommerceapp.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.Model.Users;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.PhoneVerificationActivity;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import jp.wasabeef.blurry.Blurry;

public class SignupTab extends Fragment {

    private Button btnCreateAccount;
    private EditText txtPhoneNo;
    private boolean blurred=false;
    private EditText username, password, confirmPassword;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_lragment, container, false);

        btnCreateAccount=(Button) view.findViewById(R.id.RegisterButton);
        txtPhoneNo=(EditText) view.findViewById(R.id.Register_phone_no_input);
        username=(EditText)view.findViewById(R.id.signup_userName);
        password=(EditText)view.findViewById(R.id.signup_txtPassword);
        confirmPassword=(EditText)view.findViewById(R.id.signup_txt_confirm_Password);

        mAuth=FirebaseAuth.getInstance();

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
        String name=username.getText().toString().trim();
        String _password=password.getText().toString().trim();
        String _confPassword=confirmPassword.getText().toString().trim();


        if(name.isEmpty()){
            Toast.makeText(getActivity(), R.string.ErrNameIsEmpty, Toast.LENGTH_LONG).show();
        }
        else if(phone.isEmpty()){
            Toast.makeText(getActivity(), R.string.ErrPhoneIsEmpth, Toast.LENGTH_LONG).show();
        }
        else if(_password.isEmpty()){
            Toast.makeText(getActivity(), R.string.ErrPasswordIsEmpth, Toast.LENGTH_LONG).show();
        }
        else if(!_confPassword.equals(_password)){
            Toast.makeText(getActivity(), R.string.ErrPasswordNotMatch, Toast.LENGTH_LONG).show();
        }
        else{
            phone = "+92" + phone;
            verifyPhone(phone, _password, name);
        }
    }

    public void verifyPhone(final String phone, final String password, final String name){
        final String email=phone+"@gmail.com";
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Users user = new Users(name, email, phone);
                    Log.d("Create User", "user created with email and password: ");
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()) {
                               Intent intent = new Intent(getActivity(), PhoneVerificationActivity.class);
                                intent.putExtra("phone", phone);
                                intent.putExtra("password", password);
                                intent.putExtra("name", name);
                                startActivity(intent);
                            }else{
                               Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                           }
                        }
                    });
                }else{
                    Toast.makeText(getActivity(), "This phone number is already exist", Toast.LENGTH_LONG).show();
                }
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
