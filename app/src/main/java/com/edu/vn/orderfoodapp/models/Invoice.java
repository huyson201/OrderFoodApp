package com.edu.vn.orderfoodapp.models;

import java.util.ArrayList;

public class Invoice {
    private String id;
    private Food foods;
    private int quantity;

    public Invoice(String id, Food foods, int quantity) {
        this.id = id;
        this.foods = foods;
        this.quantity = quantity;
    }

    public Invoice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Food getFoods() {
        return foods;
    }

    public void setFoods(Food foods) {
        this.foods = foods;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
