package com.edu.vn.orderfoodapp.apdapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.edu.vn.orderfoodapp.EditFoodActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MenuItemAdapter extends FirebaseRecyclerAdapter<Food, MenuItemAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private ViewBinderHelper viewBinderHelper;
    public MenuItemAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
        viewBinderHelper = new ViewBinderHelper();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder,final int position, @NonNull final Food food) {
        viewBinderHelper.bind(holder.swipeRevealLayout, position + "");
        Glide.with(holder.foodImg.getContext()).load(food.getFoodImage()).fitCenter().into(holder.foodImg);
        holder.foodName.setText(food.getFoodName());
        holder.foodPrice.setText((food.getFoodPrice() + ""));
        holder.foodDecs.setText(food.getFoodDescription());

        // delete button
        holder.lblDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.foodName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("foods").child(food.getFoodId()).removeValue();
                        Toast.makeText(holder.foodName.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.foodName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        // edit button
        holder.lblEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.foodName.getContext(), EditFoodActivity.class);
                intent.putExtra("foodId",food.getFoodId());
                intent.putExtra("foodName",food.getFoodName());
                intent.putExtra("foodImage",food.getFoodImage());
                intent.putExtra("foodDesc",food.getFoodDescription());
                intent.putExtra("foodPrice",food.getFoodPrice());
                intent.putExtra("foodCate",food.getCategoryId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.foodName.getContext().startActivity(intent);
            }
        });
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView foodImg;
        private TextView foodName, foodPrice, foodDecs;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView lblDelete;
        private TextView lblEdit;
        public MyViewHolder(@NonNull View itemView) {
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


}
