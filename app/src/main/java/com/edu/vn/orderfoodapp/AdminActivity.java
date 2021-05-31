package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.edu.vn.orderfoodapp.apdapters.AdminRecyclerViewAdapter;
import com.edu.vn.orderfoodapp.models.AdminMenu;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements AdminRecyclerViewAdapter.IMenuItemClick {
    private RecyclerView recyclerView;
    private ArrayList<AdminMenu> menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_layout);

        recyclerView = findViewById(R.id.admin_recycler_view);

        menus = new ArrayList<AdminMenu>();
        menus.add(new AdminMenu(R.drawable.ic_profile, AdminMenu.PROFILE_TAG));
        menus.add(new AdminMenu(R.drawable.ic_category, AdminMenu.CATEGORY_TAG));
        menus.add(new AdminMenu(R.drawable.ic_menu, AdminMenu.MENUS_TAG));
        menus.add(new AdminMenu(R.drawable.ic_task_list, AdminMenu.WAITING_BILL_TAG));
        menus.add(new AdminMenu(R.drawable.ic_confirm_list, AdminMenu.CONFIRMED_BILL_TAG));
        menus.add(new AdminMenu(R.drawable.ic_chef, AdminMenu.COOKING_TAG));
        menus.add(new AdminMenu(R.drawable.ic_food_delivery, AdminMenu.DELIVERY_TAG));
        menus.add(new AdminMenu(R.drawable.ic_logout, AdminMenu.LOGOUT_TAG));

        AdminRecyclerViewAdapter adapter = new AdminRecyclerViewAdapter(menus, this);
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
        Toast.makeText(this, "Clicked  Completed Invoice", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickMenu() {
        Toast.makeText(this, "Clicked  Menu", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickDelivery() {
        Toast.makeText(this, "Clicked Delivery Invoice", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickCooking() {
        Toast.makeText(this, "Clicked  Cooking", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickAddCategory() {
         Intent intent = new Intent(AdminActivity.this, AddCategoryActivity.class);
         startActivity(intent);
    }

}