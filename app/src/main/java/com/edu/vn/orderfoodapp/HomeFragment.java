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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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