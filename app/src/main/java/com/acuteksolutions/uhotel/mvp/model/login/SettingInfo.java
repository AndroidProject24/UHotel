package com.acuteksolutions.uhotel.mvp.model.login;

/**
 * Created by Toan.IT on 5/21/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class SettingInfo {

    private boolean watchTvState;
    private boolean moviesState;
    private boolean conciergeState;
    private boolean fnaState;
    private boolean roomControlState;


    public boolean isWatchTvState() {
        return watchTvState;
    }

    public boolean isMoviesState() {
        return moviesState;
    }

    public boolean isConciergeState() {
        return conciergeState;
    }

    public boolean isFnaState() {
        return fnaState;
    }

    public boolean isRoomControlState() {
        return roomControlState;
    }

    @Override
    public String toString() {
        return "SettingInfo{" +
                "conciergeState=" + conciergeState +
                ", watchTvState=" + watchTvState +
                ", moviesState=" + moviesState +
                ", fnaState=" + fnaState +
                ", roomControlState=" + roomControlState +
                '}';
    }
}
