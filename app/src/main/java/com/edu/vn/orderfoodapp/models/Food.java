package com.edu.vn.orderfoodapp.models;

public class Food {
    private String foodId;
    private String imgUrl;
    private String name;
    private String description;
    private int price;

    public Food(String foodId, String imgUrl, String name, String description, int price) {
        this.foodId = foodId;
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
