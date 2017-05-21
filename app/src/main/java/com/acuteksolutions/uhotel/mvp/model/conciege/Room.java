package com.acuteksolutions.uhotel.mvp.model.conciege;

import com.acuteksolutions.uhotel.ui.adapter.concierge.RoomAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Toan.IT on 5/14/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class Room extends RealmObject implements MultiItemEntity {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Room(String name, int value, int position) {
        this.name = name;
        this.value = value;
        this.position = position;
    }

    public Room(String name) {
        this.name = name;
    }

    @PrimaryKey
    private String name;

    private int value;

    private int position;

    public Room(){}

    @Override
    public int getItemType() {
        return RoomAdapter.TYPE_ROOM;
    }
}
