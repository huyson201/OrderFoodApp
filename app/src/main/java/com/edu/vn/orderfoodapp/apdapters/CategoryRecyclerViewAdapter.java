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

        if (category.getCategoryName().equalsIgnoreCase("PIZZA")){
            viewHolder.imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_local_pizza_24,context.getTheme()));
        }

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
