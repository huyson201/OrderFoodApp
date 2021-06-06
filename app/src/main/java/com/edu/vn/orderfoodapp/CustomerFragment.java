package com.edu.vn.orderfoodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.edu.vn.orderfoodapp.apdapters.CustomerPagerAdapter;
import com.edu.vn.orderfoodapp.customerfragment.HistoryFragment;
import com.edu.vn.orderfoodapp.customerfragment.ProfileFragment;
import com.google.android.material.tabs.TabLayout;

public class CustomerFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    private  TextView txtName;
    private  TextView logout;
    private HomeActivity homeActivity;
    private CustomerPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_customer, container, false);
        homeActivity=(HomeActivity) getActivity();
        tabLayout=view.findViewById(R.id.tablayout);
        viewPager=view.findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        txtName = view.findViewById(R.id.username);
        txtName.setText(LoginActivity.userProFile.getName());
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove user from shared preferences
                SharedPreferences sharedPref = homeActivity.getSharedPreferences(LoginActivity.REMEMBER_LOGIN_TAG, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(LoginActivity.USER_LOGGED_IN);
                editor.apply();

                // go to login activity
                Intent intent = new Intent(homeActivity, LoginActivity.class);
                startActivity(intent);

            }
        });


        adapter=new CustomerPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new ProfileFragment(),"PROFILE");
        adapter.addFragment(new HistoryFragment(),"HISTORY");
        viewPager.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}