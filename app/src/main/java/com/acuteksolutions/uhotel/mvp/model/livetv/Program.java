package com.acuteksolutions.uhotel.mvp.model.livetv;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Program extends RealmObject implements Parcelable {
    private Integer idChannel;
    private long duration;
    @PrimaryKey
    private String title;
    private String description;
    private long start;
    private long end;
    private int rating;
    private String picture;
    private boolean recordable;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Integer getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(Integer idChannel) {
        this.idChannel = idChannel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isRecordable() {
        return recordable;
    }

    public void setRecordable(boolean recordable) {
        this.recordable = recordable;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.duration);
        dest.writeValue(this.idChannel);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.start);
        dest.writeLong(this.end);
        dest.writeInt(this.rating);
        dest.writeString(this.picture);
        dest.writeByte(this.recordable ? (byte) 1 : (byte) 0);
    }

    public Program() {
    }

    protected Program(Parcel in) {
        this.duration = in.readLong();
        this.idChannel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
        this.start = in.readLong();
        this.end = in.readLong();
        this.rating = in.readInt();
        this.picture = in.readString();
        this.recordable = in.readByte() != 0;
    }

    public static final Creator<Program> CREATOR = new Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel source) {
            return new Program(source);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };

    @Override
    public String toString() {
        return "Program{" +
                "duration=" + duration +
                ", idChannel=" + idChannel +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", rating=" + rating +
                ", picture='" + picture + '\'' +
                ", recordable=" + recordable +
                '}';
    }

}
