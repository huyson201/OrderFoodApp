package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.models.Bill;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.Invoice;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class FoodDetailActivity extends AppCompatActivity {
    //properties
    private ImageButton backBtn;
    private Toolbar toolbar;
    private ImageView foodImg;
    private TextView foodName, foodPrice, foodDesc;
    private Button addCartBtn, buyNowBtn;

    public static final String FOOD_ID_TAG = "foodId";
    public static final String FOOD_IMG_TAG = "foodImage";
    public static final String FOOD_NAME_TAG = "foodName";
    public static final String FOOD_DESC_TAG = "foodDesc";
    public static final String FOOD_PRICE_TAG = "foodPrice";

    private String id;
    private String imgUri;
    private String name;
    private String desc;
    private int price;

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

        // get food detail value from intent
        this.id = getIntent().getStringExtra(FOOD_ID_TAG);
        this.imgUri = getIntent().getStringExtra(FOOD_IMG_TAG);
        this.name = getIntent().getStringExtra(FOOD_NAME_TAG);
        this.desc = getIntent().getStringExtra(FOOD_DESC_TAG);
        this.price = getIntent().getIntExtra(FOOD_PRICE_TAG,0);

        // set value for food detail
        Glide.with(this).load(this.imgUri).fitCenter().into(foodImg);
        foodName.setText(this.name);
        foodDesc.setText(this.desc);
        foodPrice.setText(this.price + "");



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

                SharedPreferences sharedPref = getSharedPreferences(CartActivity.CART_TAG, MODE_PRIVATE);
                String strInvoices = sharedPref.getString(CartActivity.INVOICES_TAG, "");
                SharedPreferences.Editor editor = sharedPref.edit();
                Gson gson = new Gson();

                ArrayList<Invoice> invoices;
                Food food = new Food(id, imgUri, name, desc, price );
                if(strInvoices == ""){
                    invoices = new ArrayList<Invoice>();
                    invoices.add(new Invoice(food, 1));
                }else {
                    boolean isExistFood = false;
                    invoices = gson.fromJson(strInvoices,new TypeToken<ArrayList<Invoice>>(){}.getType());
                    for (Invoice invoice : invoices){
                        if(invoice.getFoods().getFoodId().equals(food.getFoodId())){
                            invoice.setQuantity(invoice.getQuantity() + 1);
                            isExistFood = true;
                            break;
                        }
                    }

                    if(!isExistFood){
                        invoices.add(new Invoice(food, 1));
                    }

                }

                String strValue = gson.toJson(invoices, new TypeToken<ArrayList<Invoice>>(){}.getType());
                editor.putString(CartActivity.INVOICES_TAG, strValue);
                editor.apply();
               startActivity(new Intent(FoodDetailActivity.this, CartActivity.class));
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