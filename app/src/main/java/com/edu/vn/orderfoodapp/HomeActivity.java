package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageButton;

import com.edu.vn.orderfoodapp.apdapters.CategoryRecyclerViewAdapter;
import com.edu.vn.orderfoodapp.apdapters.FoodRecyclerViewAdapter;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<Category> categories = new ArrayList<Category>();
    private ArrayList<Food> foodList = new ArrayList<Food>();
    //private ArrayAdapter<Person> adapter;
    //private PersonAdapter adapter;
    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    private FoodRecyclerViewAdapter foodRecyclerViewAdapter;
//    private PersonDatabase DAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        RecyclerView category_recyclerView = findViewById(R.id.categories);
        RecyclerView food_recyclerView = findViewById(R.id.list_foods);

        //
        categories.add(new Category("PIZZA","01"));
        categories.add(new Category("PIZZA","02"));
        categories.add(new Category("PIZZA","02"));
        categories.add(new Category("PIZZA","02"));
        categories.add(new Category("PIZZA","02"));
        categories.add(new Category("PIZZA","02"));
        categories.add(new Category("PIZZA","02"));
        categories.add(new Category("PIZZA","02"));
        categories.add(new Category("PIZZA","02"));

        //
        foodList.add(new Food("hihi","hihi","hihi","50000",50000));
        foodList.add(new Food("hihi","hihi","hihi","50000",50000));
        foodList.add(new Food("hihi","hihi","hihi","50000",50000));
        foodList.add(new Food("hihi","hihi","hihi","50000",50000));


        //
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(this, R.layout.category_item, categories);
        LinearLayoutManager linearLayoutManager_horizontal=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        category_recyclerView.setLayoutManager(linearLayoutManager_horizontal);
        category_recyclerView.setAdapter(categoryRecyclerViewAdapter);

        //
        foodRecyclerViewAdapter=new FoodRecyclerViewAdapter(this,R.layout.food_item_cardview,foodList);
        LinearLayoutManager linearLayoutManager_vertical=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        food_recyclerView.setLayoutManager(linearLayoutManager_vertical);
        food_recyclerView.setAdapter(foodRecyclerViewAdapter);
//
//       //Set layout manager
//        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
//
//
////        recyclerView.scrollBy(50,0);
//        layoutManager.setOrientation((LinearLayoutManager.VERTICAL));
////        layoutManager.setOrientation((LinearLayoutManager.HORIZONTAL));
//        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

}