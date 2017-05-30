package com.acuteksolutions.uhotel.mvp.model.movies;

import io.realm.RealmObject;

public class Period extends RealmObject{
    private long start;
    private long end;

    public Period() {
    }

    public Period(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
