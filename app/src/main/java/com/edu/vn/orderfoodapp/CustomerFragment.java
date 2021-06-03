package com.edu.vn.orderfoodapp;

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
        adapter=new CustomerPagerAdapter(homeActivity.getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new ProfileFragment(),"PROFILE");
        adapter.addFragment(new HistoryFragment(),"HISTORY");
        viewPager.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}