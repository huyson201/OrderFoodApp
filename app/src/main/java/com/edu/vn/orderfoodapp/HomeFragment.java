package com.edu.vn.orderfoodapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.vn.orderfoodapp.apdapters.CategoryAdapter;
import com.edu.vn.orderfoodapp.apdapters.FoodAdapter;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.UpdateRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UpdateRecyclerView {

    private ArrayList<Category> categories;
    private ArrayList<Food> foodList;
    private DatabaseReference database_Categories;
    private RecyclerView categoryRecyclerView;
    private RecyclerView foodRecyclerView;
    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodAdapter;
    private ImageView imageView;
    private EditText edtSearch;
    private View view;
    private HomeActivity homeActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        homeActivity=(HomeActivity) getActivity();

        categories = new ArrayList<Category>();
        database_Categories = FirebaseDatabase.getInstance().getReference("categories");

        //hien thi du lieu categories ra recylerview
        categoryRecyclerView = view.findViewById(R.id.categories);
        categoryAdapter = new CategoryAdapter(homeActivity, categories, this);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(homeActivity, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);
        //lay du lieu categories trong database
        database_Categories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //hien thi du lieu food ra rycylerview
        foodList = new ArrayList<>();
        foodRecyclerView = view.findViewById(R.id.list_foods);
        foodAdapter = new FoodAdapter(homeActivity, foodList);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(homeActivity, LinearLayoutManager.VERTICAL, false));
        foodRecyclerView.setAdapter(foodAdapter);

        edtSearch=view.findViewById(R.id.editSearch);
        imageView=view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtSearch.getText().toString().equalsIgnoreCase(""))
                {
                    Intent intent = new Intent(homeActivity, SearchActivity.class);
                    intent.putExtra("text",edtSearch.getText().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }else {
                    Toast.makeText(homeActivity,"Please Input Keyword !!",Toast.LENGTH_LONG).show();
                }
//                Log.d("edtSearch",edtSearch.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void callBack(int position, ArrayList<Food> foods) {
        foodAdapter = new FoodAdapter(homeActivity, foods);
        foodAdapter.notifyDataSetChanged();
        foodRecyclerView.setAdapter(foodAdapter);
    }

}