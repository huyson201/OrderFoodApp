package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Food;

import java.util.ArrayList;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.MyViewHolder> {
    private Activity context;
    private int layoutID;
    private ArrayList<Food> foodlist;

    public FoodRecyclerViewAdapter(Activity context, int layoutID, ArrayList<Food> foodlist) {
        this.context = context;
        this.layoutID = layoutID;
        this.foodlist = foodlist;
    }

    public void setFoodlist(ArrayList<Food> foodlist) {
        this.foodlist = foodlist;
        notifyDataSetChanged(); //load data to foodlist adapter
    }

    @Override
    public FoodRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        CardView cardView = (CardView) inflater.inflate(viewType, viewGroup, false);
        return new FoodRecyclerViewAdapter.MyViewHolder(cardView, foodlist);
    }

    @Override
    public void onBindViewHolder(FoodRecyclerViewAdapter.MyViewHolder viewHolder, int position) {
        //get data from person list
        Food food  = foodlist.get(position);
        viewHolder.setPosition(position);

        Glide.with(this.context).load(food.getFoodImage()).fitCenter().into(viewHolder.imageView);
        viewHolder.foodName.setText(food.getFoodName());
       viewHolder.foodPrice.setText(food.getFoodPrice()+"");

    }

    //    Tra ve so phan tu trong mang
    @Override
    public int getItemCount() {
        if (foodlist!=null){
            return foodlist.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView foodName;
        private TextView foodPrice;
        private  int position = 0;

        public MyViewHolder(View itemView, final ArrayList<Food> foodlist) {
            super(itemView);
            imageView = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
//            chkSelection.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (((CheckBox)v).isChecked()) {
//                        members.get(position).setCheck(true);
//                    }
//                    else {
//                        members.get(position).setCheck(false);
//                    }
//                }
//            });
        }
        public void setPosition(int pos){
            position = pos;
        }
    }
}
