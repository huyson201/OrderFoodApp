package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.edu.vn.orderfoodapp.CartActivity;
import com.edu.vn.orderfoodapp.Delegate.ClickCartItemDelegate;
import com.edu.vn.orderfoodapp.HomeActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.Invoice;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder> {
    //propeties
    private ArrayList<Food> menus;
    private Activity context;
    private ClickCartItemDelegate clickCartItemDelegate;
    private ViewBinderHelper viewBinderHelper;
    private DatabaseReference database;

    public MenuItemAdapter(Activity context, ArrayList<Food> menus) {
        this.menus = menus;
        this.context = context;
        viewBinderHelper = new ViewBinderHelper();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImg;
        private TextView foodName, foodPrice, foodDecs;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView lblDelete;
        private TextView lblEdit;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImg = itemView.findViewById(R.id.food_img);
            foodName = itemView.findViewById(R.id.lbl_food_name);
            foodPrice = itemView.findViewById(R.id.lbl_food_price);
            foodDecs = itemView.findViewById(R.id.lbl_food_desc);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
            lblDelete = itemView.findViewById(R.id.lbl_delete);
            lblEdit = itemView.findViewById(R.id.lbl_edit);

        }
    }

    public void setClickAllDelegate(ClickCartItemDelegate clickCartItemDelegate) {
        this.clickCartItemDelegate = clickCartItemDelegate;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(this.context).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Food food = menus.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, position + "");

        Glide.with(this.context).load(food.getFoodImage()).fitCenter().into(holder.foodImg);
        holder.foodName.setText(food.getFoodName());
        holder.foodPrice.setText((food.getFoodPrice() + ""));
        holder.foodDecs.setText(food.getFoodDescription());


        // delete processing
        holder.lblDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("FoodID",food.getFoodId());
                menus.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                deleteFood(food.getFoodId());
//
//                if(clickCartItemDelegate != null){
//                    clickCartItemDelegate.onClickCartItem();
//                }
//                updateCart();
            }
        });


    }

    public void deleteFood(String foodID) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("foods");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            database.child(foodID).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (menus != null) {
            return menus.size();
        }
        return 0;
    }


}
