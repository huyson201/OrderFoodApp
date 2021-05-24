package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Food;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    public ArrayList<Food> foods;
    private Activity context;

    public FoodAdapter(Activity context,ArrayList<Food> foods){
        this.context =context;
        this.foods = foods;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView foodName;
        private TextView foodPrice;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
        }
    }

    @NonNull
    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView =(CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_cardview,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(cardView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.MyViewHolder holder, int position) {
        Food food = foods.get(position);
        Glide.with(this.context).load(food.getFoodImage()).fitCenter().into(holder.imageView);
        holder.foodName.setText(food.getFoodName());
        holder.foodPrice.setText(food.getFoodPrice()+"");

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }


}
