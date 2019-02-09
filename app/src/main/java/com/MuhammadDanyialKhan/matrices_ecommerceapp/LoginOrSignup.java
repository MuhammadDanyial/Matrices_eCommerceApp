package com.MuhammadDanyialKhan.matrices_ecommerceapp;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.MuhammadDanyialKhan.matrices_ecommerceapp.Tabs.LoginTab;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.Tabs.SignupTab;

public class LoginOrSignup extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void setupViewPager(ViewPager viewPager){
        SectionPageAdaptor adaptor = new SectionPageAdaptor(getSupportFragmentManager());
        adaptor.addFragment(new LoginTab(), "Login");
        adaptor.addFragment(new SignupTab(), "Signup");
        viewPager.setAdapter(adaptor);
    }

}
