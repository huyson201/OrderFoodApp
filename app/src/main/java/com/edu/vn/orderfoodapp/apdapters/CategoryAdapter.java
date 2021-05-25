package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.UpdateRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private ArrayList<Category> categories;
    int rowIndex = -1;
    UpdateRecyclerView updateRecyclerView;
    boolean check = true;
    boolean select = true;
    private Activity context;

    public CategoryAdapter(Activity context, ArrayList<Category> categories, UpdateRecyclerView updateRecyclerView) {
        this.context = context;
        this.categories = categories;
        this.updateRecyclerView = updateRecyclerView;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView categoryName;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgButton);
            categoryName = itemView.findViewById(R.id.categoryName);
            linearLayout = itemView.findViewById(R.id.linearlayout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = categories.get(position);

        Glide.with(this.context).load(category.getCategoryImg()).fitCenter().into(holder.imageView);
        holder.categoryName.setText(category.getCategoryName());
        if (check) {
            getValueFromDB(position);
            check = false;
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowIndex = position;
                notifyDataSetChanged();
                getValueFromDB(position);
            }
        });

        // check item selected
        if (select) {
            if (position == 0) {
                holder.linearLayout.setBackgroundResource(R.drawable.category_item_bg_selected);
            }
            select = false;

        } else if (rowIndex == position) {
            holder.linearLayout.setBackgroundResource(R.drawable.category_item_bg_selected);
        } else {
            holder.linearLayout.setBackgroundResource(R.drawable.category_item_bg);
        }

    }
    // lay gia tri tu database
    private void getValueFromDB(int position){
        ArrayList<Food> foods = new ArrayList<>();
        DatabaseReference databaseFood = FirebaseDatabase.getInstance().getReference("foods");
        databaseFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    if (Integer.parseInt(food.getCategoryId()) == (position + 1)) {
                        foods.add(food);
                    }
                }
                updateRecyclerView.callBack(position, foods);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }


}
