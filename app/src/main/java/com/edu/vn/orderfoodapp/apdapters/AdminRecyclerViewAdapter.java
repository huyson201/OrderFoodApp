package com.edu.vn.orderfoodapp.apdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.AdminMenu;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<AdminRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<AdminMenu> menus;
    private IMenuItemClick iMenuItemClick;
    public AdminRecyclerViewAdapter(ArrayList<AdminMenu> menus, IMenuItemClick iMenuItemClick) {
        this.menus = menus;
        this.iMenuItemClick = iMenuItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MyViewHolder(view, iMenuItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AdminMenu adminMenu = menus.get(position);
        holder.img.setBackgroundResource(adminMenu.getImgId());
        holder.itemName.setText(adminMenu.getName());

    }

    @Override
    public int getItemCount() {
        return this.menus.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.admin_item_cardview;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView itemName;
        private IMenuItemClick iMenuItemClick;

        public MyViewHolder(@NonNull View cardView, IMenuItemClick iMenuItemClick) {
            super(cardView);
            img = cardView.findViewById(R.id.image_item);
            itemName = cardView.findViewById(R.id.item_name);
            cardView.setOnClickListener(this);
            this.iMenuItemClick = iMenuItemClick;
        }

        @Override
        public void onClick(View v) {
            String name = itemName.getText().toString();
            switch (name){
                case AdminMenu.PROFILE_TAG:
                    iMenuItemClick.onClickProfile();
                    break;
                case AdminMenu.WAITING_INVOICE_TAG:
                    iMenuItemClick.onClickWatingInvoice();
                    break;
                case AdminMenu.CONFIRMED_INVOICE_TAG:
                    iMenuItemClick.onClickConfirmedInvoice();
                    break;
                case AdminMenu.LOGOUT_TAG:
                    iMenuItemClick.onClickLogout();
                    break;
                default:
                    break;
            }
        }
    }

    public interface IMenuItemClick{
        void onClickProfile();
        void onClickLogout();
        void onClickWatingInvoice();
        void onClickConfirmedInvoice();
    }

}
