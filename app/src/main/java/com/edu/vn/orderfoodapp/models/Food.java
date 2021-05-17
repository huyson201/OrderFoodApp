package com.edu.vn.orderfoodapp.models;

public class Food {
    private String imgUrl;
    private String name;
    private String description;
    private String price;

    public Food(String imgUrl, String name, String description, String price) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
