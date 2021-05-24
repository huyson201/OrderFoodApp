package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.UpdateRectyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder> {
    private Activity context;
    private int layoutID;
    private ArrayList<Category> categories;
    UpdateRectyclerView updateRectyclerView;
    boolean check=true;
    boolean select=true;
    private DatabaseReference database_Foods;

    public CategoryRecyclerViewAdapter(Activity context, ArrayList<Category> categories, UpdateRectyclerView updateRectyclerView) {
        this.context = context;
        this.categories = categories;
        this.updateRectyclerView = updateRectyclerView;
    }

    public CategoryRecyclerViewAdapter(Activity context, int layoutID, ArrayList<Category> categories) {
        this.context = context;
        this.layoutID = layoutID;
        this.categories = categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged(); //load data to categoriesadapter
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        CardView cardView = (CardView) inflater.inflate(viewType, viewGroup, false);
        return new MyViewHolder(cardView, categories);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        //get data from person list
        Category category = categories.get(position);
        viewHolder.setPosition(position);



        Glide.with(this.context).load(category.getCategoryImg()).fitCenter().into(viewHolder.imageView);
        viewHolder.categoryName.setText(category.getCategoryName());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutID = position;

                Toast.makeText(context, "Categories" + layoutID, Toast.LENGTH_LONG).show();
            }
        });

    }

    //    Tra ve so phan tu trong mang
    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView categoryName;
        private LinearLayout linearLayout;
        private int position = 0;

        public MyViewHolder(View itemView, final ArrayList<Category> categories) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgButton);
            categoryName = itemView.findViewById(R.id.categoryName);
            linearLayout = itemView.findViewById(R.id.linearlayout);
        }

        public void setPosition(int pos) {
            position = pos;
        }
    }
}
