package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

public class FoodDetailActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail_layout);
        backBtn = findViewById(R.id.btn_back);
        toolbar = findViewById(R.id.tool_bar);

        setActionBar(toolbar);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}