package com.acuteksolutions.uhotel.mvp.model.food;

/**
 * Created by huynhvantoan on 11/23/16.
 * Email huynhvantoan.itc@gmail.com
 */

public class Food {

    private int rating;
    private String name;
    private String type;
    private String des;
    private String address;
    private int url;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
    @Override
    public String toString() {
        return "Food{" +
                "rating=" + rating +'\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", des='" + des + '\'' +
                ", address='" + address + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
