package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Category;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder>{
    private Activity context;
    private int layoutID;
    private ArrayList<Category> categories;

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
        Category category  = categories.get(position);
        viewHolder.setPosition(position);

        Glide.with(this.context).load(category.getCategoryImg()).fitCenter().into(viewHolder.imageButton);
        viewHolder.categoryName.setText(category.getCategoryName());


    }

    //    Tra ve so phan tu trong mang
    @Override
    public int getItemCount() {
        if (categories!=null){
            return categories.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageButton imageButton;
        private TextView categoryName;
        private  int position = 0;

        public MyViewHolder(View itemView, final ArrayList<Category> categories) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.imgButton);
            categoryName = itemView.findViewById(R.id.categoryName);

        }
        public void setPosition(int pos){
            position = pos;
        }
    }
}
