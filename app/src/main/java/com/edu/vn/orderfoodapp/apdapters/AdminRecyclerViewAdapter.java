package com.edu.vn.orderfoodapp.apdapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.vn.orderfoodapp.LoginActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.AdminMenu;
import com.google.android.material.card.MaterialCardView;

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
        holder.card.setElevation(elevationValue(holder.itemName.getText().toString()));
    }

    public int elevationValue(String name) {
        int num = 0;
        switch (name) {
            case AdminMenu.MENUS_TAG:
            case AdminMenu.WAITING_BILL_TAG:
            case AdminMenu.CONFIRMED_BILL_TAG:
            case AdminMenu.CATEGORY_TAG:
                if (LoginActivity.userProFile.getRole() != 0) {
                    num = 0;
                } else {
                    num = 8;
                }
                break;
            case AdminMenu.COOKING_TAG:
                if (LoginActivity.userProFile.getRole() == 3) {
                    num = 0;
                } else {
                    num = 8;
                }
                break;
            case AdminMenu.DELIVERY_TAG:
                if (LoginActivity.userProFile.getRole() != 3) {
                    num = 0;
                } else {
                    num = 8;
                }
                break;
            default:
                num = 8;
                break;
        }
        return num;
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
        private LinearLayout item;
        private MaterialCardView card;

        public MyViewHolder(@NonNull View cardView, IMenuItemClick iMenuItemClick) {
            super(cardView);
            img = cardView.findViewById(R.id.image_item);
            itemName = cardView.findViewById(R.id.item_name);
            item = cardView.findViewById(R.id.cardview_item);
            card = cardView.findViewById(R.id.admin_cardview_item);
            cardView.setOnClickListener(this);
            this.iMenuItemClick = iMenuItemClick;
        }

        @Override
        public void onClick(View v) {
            String name = itemName.getText().toString();
            switch (name) {
                case AdminMenu.PROFILE_TAG:
                    iMenuItemClick.onClickProfile();
                    break;
                case AdminMenu.MENUS_TAG:
                    if (LoginActivity.userProFile.getRole() == 0) {
                        iMenuItemClick.onClickMenu();
                    } else {
                        v.setClickable(false);
                    }
                    break;
                case AdminMenu.WAITING_BILL_TAG:
                    if (LoginActivity.userProFile.getRole() == 0) {
                        iMenuItemClick.onClickWaitingInvoice();
                    } else {
                        v.setClickable(false);
                    }
                    break;
                case AdminMenu.CONFIRMED_BILL_TAG:
                    if (LoginActivity.userProFile.getRole() == 0) {
                        iMenuItemClick.onClickConfirmedInvoice();
                    } else {
                        v.setClickable(false);
                    }
                    break;
                case AdminMenu.COOKING_TAG:
                    if (LoginActivity.userProFile.getRole() == 0 || LoginActivity.userProFile.getRole() == 2) {
                        iMenuItemClick.onClickCooking();
                    } else {
                        v.setClickable(false);
                    }
                    break;
                case AdminMenu.DELIVERY_TAG:
                    if (LoginActivity.userProFile.getRole() == 3) {
                        iMenuItemClick.onClickDelivery();
                    } else {
                        v.setClickable(false);

                    }
                    break;
                case AdminMenu.LOGOUT_TAG:
                    iMenuItemClick.onClickLogout();
                    break;
                case AdminMenu.CATEGORY_TAG:
                    if (LoginActivity.userProFile.getRole() == 0) {
                        iMenuItemClick.onClickAddCategory();
                    } else {
                        v.setClickable(false);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public interface IMenuItemClick {
        void onClickProfile();

        void onClickLogout();

        void onClickWaitingInvoice();

        void onClickConfirmedInvoice();

        void onClickMenu();

        void onClickDelivery();

        void onClickCooking();

        void onClickAddCategory();
    }

}
