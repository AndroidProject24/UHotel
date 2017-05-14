package com.acuteksolutions.uhotel.mvp.model.livetv;

import android.os.Parcel;
import android.os.Parcelable;

public class Program implements Comparable<Program>, Parcelable {
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
    public int duration;
    private long channelId;
    private String title;
    private String description;
    private long start;
    private long end;
    private int rating;
    private String picture;
    private boolean recordable;

    public Program() {
        title = "";
    }

    public Program(long channelId, String title, String description, long start, long end, int rating, String picture, boolean recordable) {
        this.channelId = channelId;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.rating = rating;
        this.picture = picture;
        this.recordable = recordable;
    }

    protected Program(Parcel in) {
        this.channelId = in.readLong();
        this.title = in.readString();
        this.description = in.readString();
        this.start = in.readLong();
        this.end = in.readLong();
        this.rating = in.readInt();
        this.picture = in.readString();
        this.recordable = in.readByte() != 0;
        this.duration = in.readInt();
    }

    public long getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public int getRating() {
        return rating;
    }

    public String getPicture() {
        return picture;
    }

    public boolean isRecordable() {
        return recordable;
    }

    @Override
    public int compareTo(Program another) {
        long pos = start - another.getStart();
        return pos > 0 ? 1 : 0;
    }

    public long getDuration() {
        return ((getEnd() - getStart()) / 1000) / 60;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.channelId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.start);
        dest.writeLong(this.end);
        dest.writeInt(this.rating);
        dest.writeString(this.picture);
        dest.writeByte(this.recordable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.duration);
    }


    @Override public String toString() {
        return "Program{"
            + "duration="
            + duration
            + ", channelId="
            + channelId
            + ", title='"
            + title
            + '\''
            + ", description='"
            + description
            + '\''
            + ", start="
            + start
            + ", end="
            + end
            + ", rating="
            + rating
            + ", picture='"
            + picture
            + '\''
            + ", recordable="
            + recordable
            + '}';
    }

}
