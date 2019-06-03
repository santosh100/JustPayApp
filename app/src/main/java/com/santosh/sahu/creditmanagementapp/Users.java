package com.santosh.sahu.creditmanagementapp;

public class Users {
    private String name;
    private String ac_number;
    private int photo;

    public Users() {
    }

    public Users(String name, String ac_number,int photo) {
        this.name = name;
        this.ac_number = ac_number;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getAc_number() {
        return ac_number;
    }

    public int getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAc_number(String ac_number) {
        this.ac_number = ac_number;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
