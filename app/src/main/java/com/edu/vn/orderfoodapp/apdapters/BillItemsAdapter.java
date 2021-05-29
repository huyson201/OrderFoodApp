package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Bill;
import com.edu.vn.orderfoodapp.models.Invoice;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BillItemsAdapter extends RecyclerView.Adapter<BillItemsAdapter.BillItemViewHolder> {
    private Bill bill;
    private Activity context;
    public BillItemsAdapter(Activity context, Bill bill) {
        this.bill = bill;
        this.context = context;
    }

    @NonNull
    @Override
    public BillItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item_card, parent, false);
        return new BillItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillItemViewHolder holder, int position) {
        Invoice invoice = bill.getInvoices().get(position);
        Glide.with(this.context).load(invoice.getFoods().getFoodImage()).fitCenter().into(holder.foodImg);
        holder.lblFoodName.setText(invoice.getFoods().getFoodName());
        holder.lblQuantity.setText("x" + invoice.getQuantity());
    }

    @Override
    public int getItemCount() {
        if(bill.getInvoices().size() > 0){
            return bill.getInvoices().size();
        }
        return 0;
    }

    public class BillItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImg;
        private TextView lblFoodName, lblPrice, lblQuantity;

        public BillItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImg = itemView.findViewById(R.id.food_img);
            lblFoodName = itemView.findViewById(R.id.lbl_food_name);
            lblQuantity = itemView.findViewById(R.id.lbl_quantity);
            lblPrice = itemView.findViewById(R.id.lbl_price);
        }
    }
}
