package com.edu.vn.orderfoodapp.Delegate;

import android.view.View;

import com.edu.vn.orderfoodapp.models.Invoice;

import java.util.ArrayList;

public interface ClickAllDelegate {
    void onClickAll(View v, Invoice invoice);
}
