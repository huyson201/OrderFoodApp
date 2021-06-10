package com.edu.vn.orderfoodapp.models;

public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int role;
    private String imgUrl;

    public static final int ADMIN_ROLE = 0;
    public static final int USER_ROLE = 1;
    public static final String EMAIL_KEY = "email";


    public User() {
    }

    public User(String id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = USER_ROLE;
        this.imgUrl = "";
    }

    public User(String id, String name, String email, String phone, String address, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = USER_ROLE;
        this.imgUrl = image;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role + '\'' +
                ", img=" + imgUrl +
                '}';
    }

    public void setRole(int role) {
        this.role = role;
    }
}
