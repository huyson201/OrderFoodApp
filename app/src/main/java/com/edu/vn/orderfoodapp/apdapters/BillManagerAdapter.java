package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.vn.orderfoodapp.ConfirmBillActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.AdminMenu;
import com.edu.vn.orderfoodapp.models.Bill;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BillManagerAdapter extends RecyclerView.Adapter<BillManagerAdapter.OrderedViewHolder> {
    private Activity context;
    private ArrayList<Bill> bills;
    private String menuType = "";
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public BillManagerAdapter(Activity context, ArrayList<Bill> bills, String menuType) {
        this.context = context;
        this.bills = bills;
        this.menuType = menuType;
    }

    @NonNull
    @Override
    public OrderedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_ordered_card_item, parent, false);
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

        //show popup menu
        holder.popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,v);

                switch (menuType){
                    case Bill.CONFIRM_STATUS_TAG:
                        popupMenu.getMenuInflater().inflate(R.menu.confirm_poup_menu, popupMenu.getMenu());
                        popupMenu.show();
                        break;
                    default:
                        break;
                }
                // processing click menu item
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.confirmed:
                                updateStatusBill(bill.getId(), Bill.COOKING_STATUS_TAG);
                                break;
                            case R.id.canceled:
                                updateStatusBill(bill.getId(), Bill.DELIVERY_STATUS_TAG);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });

            }
        });

    }
    private void updateStatusBill(String billId, String status){
        db.child("bills/" + billId + "/" + "status").setValue(status);
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
        private ImageView popupMenu;
        public OrderedViewHolder( @NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.list_invoice);
            lblTotalPrice = itemView.findViewById(R.id.lbl_total_price);
            lblStatus = itemView.findViewById(R.id.lbl_status);
            popupMenu = itemView.findViewById(R.id.popup_menu);
        }
    }
}
