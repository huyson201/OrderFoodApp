package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Bill;

import java.util.ArrayList;

public class OrderedListBillAdapter extends RecyclerView.Adapter<OrderedListBillAdapter.OrderedViewHolder> {
    private Activity context;
    private ArrayList<Bill> bills;

    public OrderedListBillAdapter(Activity context, ArrayList<Bill> bills) {
        this.context = context;
        this.bills = bills;
    }

    @NonNull
    @Override
    public OrderedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_card_item, parent, false);
        return new OrderedViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedViewHolder holder, int position) {
        Bill bill = bills.get(position);
        BillItemsAdapter adapter = new BillItemsAdapter(context, bill);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(adapter);

        holder.lblTotalPrice.setText(bill.getSumPrice() + "");
        holder.lblStatus.setText(bill.getStatus());

    }

    @Override
    public int getItemCount() {
        if(bills.size() > 0){
            return bills.size();
        }
        return 0;
    }

    public class OrderedViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        private TextView lblTotalPrice, lblStatus;
        public OrderedViewHolder( @NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.list_invoice);
            lblTotalPrice = itemView.findViewById(R.id.lbl_total_price);
            lblStatus = itemView.findViewById(R.id.lbl_status);

        }
    }
}
