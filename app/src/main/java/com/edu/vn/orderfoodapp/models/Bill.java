package com.edu.vn.orderfoodapp.models;

import java.util.ArrayList;

public class Bill {
    private String id;
    private ArrayList<Invoice> invoices;
    private String status;
    private String userId;

    public static final String CONFIRM_STATUS_TAG = "Đang xác nhận";
    public static final String COOKING_STATUS_TAG = "Đang chuẩn bị";
    public static final String DELIVERY_STATUS_TAG = "Đang Giao";
    public static final String CANCEL_STATUS_TAG = "Đã hủy";

    public Bill(String id, ArrayList<Invoice> invoices, String status, String userId) {
        this.id = id;
        this.invoices = invoices;
        this.status = status;
        this.userId = userId;
    }

    public Bill() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSumPrice(){
        int sum = 0;
        for(Invoice invoice: invoices){
            sum += (invoice.getQuantity() * invoice.getFoods().getFoodPrice());
        }

        return sum;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", invoices=" + invoices +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
