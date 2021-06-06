package com.edu.vn.orderfoodapp.apdapters;

import android.app.Activity;
import android.content.SharedPreferences;
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
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.models.Invoice;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    //propeties
    private ArrayList<Invoice> invoices;
    private Activity context;
    private ClickCartItemDelegate clickCartItemDelegate;
    private ViewBinderHelper viewBinderHelper;
    private Gson gson;
    public CartItemAdapter(Activity context, ArrayList<Invoice> invoices) {
        this.invoices = invoices;
        this.context = context;
        viewBinderHelper = new ViewBinderHelper();
        gson = new Gson();
    }

    public void setClickAllDelegate(ClickCartItemDelegate clickCartItemDelegate) {
        this.clickCartItemDelegate = clickCartItemDelegate;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(this.context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartItemViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {

        Invoice invoice = invoices.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout,position + "");

        Glide.with(this.context).load(invoice.getFoods().getFoodImage()).fitCenter().into(holder.foodImg);
        holder.foodName.setText(invoice.getFoods().getFoodName());
        holder.foodPrice.setText((invoice.getFoods().getFoodPrice() + ""));
        holder.quantity.setText((invoice.getQuantity() + ""));
        holder.chkSelect.setChecked(invoice.isSelected());

        //delete processing
        holder.lbl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoices.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                if(clickCartItemDelegate != null){
                    clickCartItemDelegate.onClickCartItem();
                }
                updateCart();
            }
        });

        // processing click selected check box
        holder.chkSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoice.setSelected(((CheckBox) v).isChecked());
               if(clickCartItemDelegate != null){
                   clickCartItemDelegate.onClickCartItem();
               }
            }
        });

        // processing click minus btn
        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = invoice.getQuantity();
                if(quantity > 1){
                    quantity--;
                    invoice.setQuantity(quantity);
                    holder.quantity.setText(quantity + "");
                }

                if(clickCartItemDelegate != null){
                    clickCartItemDelegate.onClickCartItem();
                }

                updateCart();
            }
        });

        // processing click plus btn
        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = invoice.getQuantity();
                if(quantity < 10){
                    quantity++;
                    invoice.setQuantity(quantity);
                    holder.quantity.setText(quantity + "");
                }else{
                    Toast.makeText(context, "Maximum is 10", Toast.LENGTH_SHORT).show();
                }

                if(clickCartItemDelegate != null){
                    clickCartItemDelegate.onClickCartItem();
                }

                updateCart();

            }
        });
    }

    private  void updateCart(){
        String value = gson.toJson(invoices, new TypeToken<ArrayList<Invoice>>(){}.getType());
        SharedPreferences sharedPref = context.getSharedPreferences(CartActivity.CART_TAG, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CartActivity.INVOICES_TAG, value);
        editor.apply();

    }
    @Override
    public int getItemCount() {
        if(invoices != null){
            return invoices.size();
        }
        return 0;
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder{
        //properties
        private ImageView foodImg;
        private TextView foodName, foodPrice, quantity;
        private ImageButton minusBtn, plusBtn;
        private CheckBox chkSelect;
        private SwipeRevealLayout swipeRevealLayout;
        private TextView lbl_delete;
        public CartItemViewHolder(@NonNull View cartItem) {
            super(cartItem);

            foodImg =  cartItem.findViewById(R.id.food_img);
            foodName =  cartItem.findViewById(R.id.lbl_food_name);
            foodPrice =  cartItem.findViewById(R.id.lbl_food_price);
            quantity =  cartItem.findViewById(R.id.lbl_quantity);
            minusBtn =  cartItem.findViewById(R.id.minus_btn);
            plusBtn =  cartItem.findViewById(R.id.plus_btn);
            chkSelect = cartItem.findViewById(R.id.chk_select);
            swipeRevealLayout = cartItem.findViewById(R.id.swipe_reveal_layout);
            lbl_delete = cartItem.findViewById(R.id.lbl_delete);
        }
    }
}
