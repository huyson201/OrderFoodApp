package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.vn.orderfoodapp.apdapters.AdminRecyclerViewAdapter;
import com.edu.vn.orderfoodapp.models.AdminMenu;
import com.edu.vn.orderfoodapp.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements AdminRecyclerViewAdapter.IMenuItemClick {
    private RecyclerView recyclerView;
    private ArrayList<AdminMenu> menus;
    private AdminRecyclerViewAdapter adapter;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_layout);

        recyclerView = findViewById(R.id.admin_recycler_view);
        name = findViewById(R.id.admin_name);

        name.setText(LoginActivity.userProFile.getName());
        menus = new ArrayList<AdminMenu>();
        menus.add(new AdminMenu(R.drawable.ic_profile, AdminMenu.PROFILE_TAG));
        if(LoginActivity.userProFile.getRole()== User.ADMIN_ROLE) {
            menus.add(new AdminMenu(R.drawable.ic_category, AdminMenu.CATEGORY_TAG));
            menus.add(new AdminMenu(R.drawable.ic_menu, AdminMenu.MENUS_TAG));
            menus.add(new AdminMenu(R.drawable.ic_task_list, AdminMenu.WAITING_BILL_TAG));
            menus.add(new AdminMenu(R.drawable.ic_chef, AdminMenu.COOKING_TAG));
            menus.add(new AdminMenu(R.drawable.ic_confirm_list, AdminMenu.CONFIRMED_BILL_TAG));
        }
        else if(LoginActivity.userProFile.getRole()== User.COOKING_ROLE){
            menus.add(new AdminMenu(R.drawable.ic_chef, AdminMenu.COOKING_TAG));
        }
        else if(LoginActivity.userProFile.getRole()== User.SHIPPER_ROLE){
            menus.add(new AdminMenu(R.drawable.ic_food_delivery, AdminMenu.DELIVERY_TAG));
        }
        menus.add(new AdminMenu(R.drawable.ic_logout, AdminMenu.LOGOUT_TAG));

        adapter = new AdminRecyclerViewAdapter(menus, this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

    }


    @Override
    public void onClickProfile() {
        Toast.makeText(this, "Clicked Profile", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickLogout() {
        // remove user from shared preferences
        SharedPreferences sharedPref = getSharedPreferences(LoginActivity.REMEMBER_LOGIN_TAG, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(LoginActivity.USER_LOGGED_IN);
        editor.apply();
        FirebaseAuth.getInstance().signOut();
        // go to login activity
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickWaitingInvoice() {
        startActivity(new Intent(AdminActivity.this, ConfirmBillActivity.class));
    }

    @Override
    public void onClickConfirmedInvoice() {
        startActivity(new Intent(AdminActivity.this, ConfirmedListActivity.class));
    }

    @Override
    public void onClickMenu() {
        startActivity(new Intent(AdminActivity.this, MenuActivity.class));

    }

    @Override
    public void onClickDelivery() {
        startActivity(new Intent(AdminActivity.this, DeliveryListActivity.class));
    }

    @Override
    public void onClickCooking() {
        startActivity(new Intent(AdminActivity.this, CookingActivity.class));
    }

    @Override
    public void onClickAddCategory() {
        Intent intent = new Intent(AdminActivity.this, CategoryListActivity.class);
        startActivity(intent);
    }

}