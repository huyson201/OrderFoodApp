package com.edu.vn.orderfoodapp.apdapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.edu.vn.orderfoodapp.EditCategoryActivity;
import com.edu.vn.orderfoodapp.EditFoodActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class CategoryItemAdapter extends FirebaseRecyclerAdapter<Category, CategoryItemAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private ViewBinderHelper viewBinderHelper;
    public CategoryItemAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
        viewBinderHelper = new ViewBinderHelper();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder,final int position, @NonNull final Category category) {
        viewBinderHelper.bind(holder.swipeRevealLayout, position + "");
        Glide.with(holder.cateImg.getContext()).load(category.getCategoryImg()).fitCenter().into(holder.cateImg);
        holder.cateName.setText(category.getCategoryName());


        // delete button
        holder.lblDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.cateName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("categories").child(category.getCategoryID()).removeValue();
                        FirebaseStorage.getInstance().getReferenceFromUrl(category.getCategoryImg()).delete();
                        Toast.makeText(holder.cateName.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.cateName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        // edit button
        holder.lblEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.cateName.getContext(), EditCategoryActivity.class);
                intent.putExtra("cateId",category.getCategoryID());
                intent.putExtra("cateName",category.getCategoryName());
                intent.putExtra("cateImage",category.getCategoryImg());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.cateName.getContext().startActivity(intent);
            }
        });
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView cateImg;
        private TextView cateName;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView lblDelete;
        private TextView lblEdit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cateImg = itemView.findViewById(R.id.cate_img);
            cateName = itemView.findViewById(R.id.lbl_cate_name);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
            lblDelete = itemView.findViewById(R.id.lbl_delete);
            lblEdit = itemView.findViewById(R.id.lbl_edit);
        }
    }


}
