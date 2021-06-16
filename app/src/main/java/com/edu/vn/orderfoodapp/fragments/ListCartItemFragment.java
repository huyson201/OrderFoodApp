package com.edu.vn.orderfoodapp.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.vn.orderfoodapp.CartActivity;
import com.edu.vn.orderfoodapp.Delegate.ClickCartItemDelegate;
import com.edu.vn.orderfoodapp.LoginActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.apdapters.CartItemAdapter;
import com.edu.vn.orderfoodapp.models.Bill;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.Invoice;
import com.edu.vn.orderfoodapp.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class ListCartItemFragment extends Fragment implements ClickCartItemDelegate {
    private CheckBox chkAll;
    private boolean isCheckAll = false;
    private RecyclerView listCartItems;
    private ArrayList<Invoice> invoices = new ArrayList<Invoice>();
    private CartItemAdapter adapter;
    private Button buyBtn;
    private TextView lblTotalPrice;
    private User user;

    private Activity context;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public ListCartItemFragment(Activity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_cart_item_fragment, container, false);

        chkAll = view.findViewById(R.id.chkAll);
        invoices = new ArrayList<Invoice>();
        listCartItems = view.findViewById(R.id.list_cart_item);
        lblTotalPrice = view.findViewById(R.id.lbl_total_price);
        buyBtn = view.findViewById(R.id.buy_btn);



        this.invoices = CartActivity.invoices;

        //get user
        this.user = LoginActivity.user;

        // default price
        lblTotalPrice.setText(0 +"");

        //init adapter
        adapter = new CartItemAdapter(context, this.invoices);
        adapter.setClickAllDelegate(this);

        //init linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // set for recycler view
        listCartItems.setAdapter(adapter);
        listCartItems.setLayoutManager(linearLayoutManager);

        // check all processing
        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckAll = ((CheckBox) v).isChecked();
                for (Invoice invoice : invoices) {
                    invoice.setSelected(isCheckAll);
                }

                adapter.notifyDataSetChanged();

                // get sum price
                int sumPrice = getSumPrice(invoices);
                lblTotalPrice.setText(sumPrice + "");

            }
        });

        // buy processing
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get a bill
               Bill bill = getBill();

               if(bill != null){
                   String billId = db.child("bills").push().getKey();
                   bill.setId(billId);
                   // insert to firebase
                   db.child("bills").child(billId).setValue(bill);




                   Toast.makeText(context, "Buy successfully", Toast.LENGTH_SHORT).show();
                   // removing ordered invoice in cart
                   for (Invoice invoice : bill.getInvoices()){
                       invoices.remove(invoice);
                   }

                   // update cart
                   Gson gson = new Gson();
                   String value = gson.toJson(invoices, new TypeToken<ArrayList<Invoice>>(){}.getType());
                   SharedPreferences sharedPref = context.getSharedPreferences(CartActivity.CART_TAG, context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPref.edit();
                   editor.putString(CartActivity.INVOICES_TAG, value);
                   editor.apply();

                   adapter.notifyDataSetChanged();
               }else{
                   Toast.makeText(context, "Buy fail", Toast.LENGTH_SHORT).show();
               }

            }
        });

        return view;
    }

    private Bill getBill(){
        ArrayList<Invoice> invoices = new ArrayList<Invoice>();

        for (int i = 0; i < this.invoices.size() ; i++){
            if(this.invoices.get(i).isSelected()){
                invoices.add(this.invoices.get(i));
            }
        }

        if(invoices.size() > 0){
            Bill bill = new Bill("", invoices, Bill.CONFIRM_STATUS_TAG, LoginActivity.user.getId());
            return  bill;
        }

        return  null;

    }
    // calculate sum price of invoices
    public int getSumPrice(ArrayList<Invoice> invoices) {
        int sum = 0;
        for (Invoice invoice : invoices) {
            if (invoice.isSelected()) {
                sum += invoice.getQuantity() * invoice.getFoods().getFoodPrice();
            }
        }
        return sum;
    }

    @Override
    public void onClickCartItem() {
        int sumPrice = getSumPrice(invoices);
        lblTotalPrice.setText(sumPrice + "");
    }

}