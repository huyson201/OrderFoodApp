package com.edu.vn.orderfoodapp.models;

public class Food {
    private String categoryId;
    private String foodId;
    private String foodImage;
    private String foodName;
    private String foodDescription;
    private int foodPrice;

    public Food(String foodId, String imgUrl, String name, String description, int price) {
        this.foodId = foodId;
        this.foodImage = imgUrl;
        this.foodName = name;
        this.foodDescription = description;
        this.foodPrice = price;
    }

    public Food() {
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
