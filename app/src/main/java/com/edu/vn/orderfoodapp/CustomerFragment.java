package com.edu.vn.orderfoodapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.edu.vn.orderfoodapp.apdapters.CustomerPagerAdapter;
import com.edu.vn.orderfoodapp.customerfragment.HistoryFragment;
import com.edu.vn.orderfoodapp.customerfragment.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


public class CustomerFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    private TextView txtName;
    private TextView logout, delete;
    private HomeActivity homeActivity;
    private CustomerPagerAdapter adapter;
//    private LoginActivity loginActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer, container, false);
        homeActivity = (HomeActivity) getActivity();
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        txtName = view.findViewById(R.id.username);
        txtName.setText(LoginActivity.userProFile.getName());
        logout = view.findViewById(R.id.logout);
        delete = view.findViewById(R.id.delete);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove user from shared preferences
                SharedPreferences sharedPref = homeActivity.getSharedPreferences(LoginActivity.REMEMBER_LOGIN_TAG, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(LoginActivity.USER_LOGGED_IN);
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                // go to login activity
                Intent intent = new Intent(homeActivity, LoginActivity.class);
                startActivity(intent);

            }
        });

        // delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("context: ",txtName.getContext()+"");
                AlertDialog.Builder builder = new AlertDialog.Builder(txtName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("auth deleted", "Succeed");
                                            } else {
                                                Log.d("auth deleted", " Fail");
                                            }
                                        }
                                    });
                        }
                        FirebaseDatabase.getInstance().getReference("users").child(LoginActivity.userProFile.getId()).removeValue();
                        Log.d("deleted - ", "successfully");
                        LoginActivity.userProFile=null;
                        Intent intent = new Intent(homeActivity, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(txtName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        adapter = new CustomerPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new ProfileFragment(), "PROFILE");
        adapter.addFragment(new HistoryFragment(), "HISTORY");
        viewPager.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}