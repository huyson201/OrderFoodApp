package com.edu.vn.orderfoodapp.customerfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.vn.orderfoodapp.HomeActivity;
import com.edu.vn.orderfoodapp.LoginActivity;
import com.edu.vn.orderfoodapp.R;

public class ProfileFragment extends Fragment {
    private TextView fullName;
    private TextView phone;
    private TextView email;
    private TextView address;
    private Button btnEdit;
    private View view;
    private HomeActivity homeActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_layout, container, false);
        fullName = view.findViewById(R.id.txtFullName);
        phone = view.findViewById(R.id.txtPhone);
        email = view.findViewById(R.id.txtEmail);
        address = view.findViewById(R.id.txtAddress);
        btnEdit = view.findViewById(R.id.btnEdit);


        homeActivity = (HomeActivity) getActivity();
        Intent intent = homeActivity.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            fullName.setText(bundle.getString("fullName"));
            email.setText(bundle.getString("email"));
            phone.setText(bundle.getString("phone"));
            address.setText(bundle.getString("address"));
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnEdit","True");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}

