package com.acuteksolutions.uhotel.mvp.model.setting;

/**
 * Created by Toan.IT on 5/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class Device {
    private int type; //1:phone, 2:tablet, 3:laptop
    private String name;
    private boolean isActive;

    public Device(int type, String name, boolean isActive) {
        this.isActive = isActive;
        this.name = name;

        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
