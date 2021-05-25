package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.models.Food;

public class FoodDetailActivity extends AppCompatActivity {
    //properties
    private ImageButton backBtn;
    private Toolbar toolbar;
    private ImageView foodImg;
    private TextView foodName, foodPrice, foodDesc;
    private Button addCartBtn, buyNowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail_layout);

        // find view by id
        backBtn = findViewById(R.id.btn_back);
        toolbar = findViewById(R.id.tool_bar);

        foodImg = findViewById(R.id.food_img);
        foodName = findViewById(R.id.lbl_food_name);
        foodPrice = findViewById(R.id.lbl_price);
        foodDesc = findViewById(R.id.lbl_description_content);

        addCartBtn = findViewById(R.id.add_to_cart_btn);
        buyNowBtn = findViewById(R.id.buy_now_btn);




        Glide.with(this).load(getIntent().getStringExtra("foodImage")).fitCenter().into(foodImg);
        foodName.setText(getIntent().getStringExtra("foodName"));
        foodDesc.setText(getIntent().getStringExtra("foodDesc"));
        foodPrice.setText(getIntent().getIntExtra("foodPrice",0)+"");



        setActionBar(toolbar);

        // processing click back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // processing click add to cart button
        addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoodDetailActivity.this, "clicked add to cart button", Toast.LENGTH_SHORT).show();
            }
        });

        // processing click buyNowBtn button
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoodDetailActivity.this, "clicked buy now button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}