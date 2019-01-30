package com.MuhammadDanyialKhan.matrices_ecommerceapp;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Switch;
import android.widget.TextView;

import com.MuhammadDanyialKhan.matrices_ecommerceapp.Tabs.LoginTab;
import com.MuhammadDanyialKhan.matrices_ecommerceapp.Tabs.SignupTab;

public class LoginOrSignup extends AppCompatActivity {


    private SectionPageAdaptor sectionPageAdaptor;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);

        SectionPageAdaptor adaptor = new SectionPageAdaptor(getSupportFragmentManager());

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
