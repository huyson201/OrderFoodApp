package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
//        viewHolder.chkSelection.setChecked(false);

//        if (category.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.CAODANG)){
//            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.college, context.getTheme()));
//        } else if (category.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.DAIHOC)){
//            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.university, context.getTheme()));
//        } else if (category.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.TRUNGCAP)){
//            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.midium, context.getTheme()));
//        } else {
//            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.none, context.getTheme()));
//        }
//        viewHolder.txtName.setText(category.getName());
//        viewHolder.txtHoppies.setText(category.getHobbies());

        if (food.getName().equalsIgnoreCase("Delicious stir-fried beef with attractive flavor ")){
            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.img_thit_bo_xao,context.getTheme()));
        }

        viewHolder.foodName.setText(food.getName());
        viewHolder.foodPrice.setText(food.getPrice());

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
