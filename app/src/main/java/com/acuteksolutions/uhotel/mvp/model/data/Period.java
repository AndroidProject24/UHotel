package com.acuteksolutions.uhotel.mvp.model.data;

public class Period {
    private long start;
    private long end;

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
