package com.acuteksolutions.uhotel.mvp.model.food;

import java.util.List;

/**
 * Created by Toan.IT on 11/24/16.
 * Email: huynhvantoan.itc@gmail.com
 */

public class ListFood {
    private String title;
    private List<Food> foodList;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

}
