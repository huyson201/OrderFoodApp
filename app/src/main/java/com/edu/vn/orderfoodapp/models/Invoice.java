package com.edu.vn.orderfoodapp.models;

import java.util.ArrayList;

public class Invoice {
    private Food foods;
    private int quantity;
    private boolean selected = false;

    public Invoice(Food foods, int quantity) {
        this.foods = foods;
        this.quantity = quantity;
    }

    public Invoice() {
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
