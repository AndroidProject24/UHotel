package com.acuteksolutions.uhotel.mvp.model.livetv;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Channel implements Comparable<Channel>, Parcelable {
    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
    //use for DTV player
    public int dbId;
    public int serviceType;
    public boolean isLocalChannel;
    @SerializedName("id")
    private long channelId;
    private long activate;
    private long deactivate;
    private int rating;
    private boolean hd;
    private String icon;
    private boolean instanceRecordable;
    private boolean localRecordable;
    private String name;
    private int number;
    private boolean pauseAndResume;
    private boolean programRecordable;
    private boolean timeShift;
    private List<Stream> stream;
    private List<Program> programs;
    public Channel() {
        this.name = name;
    }

    public Channel(long channelId, long activate, long deactivate, int rating, boolean hd, String icon, boolean instanceRecordable, boolean localRecordable, String name, int number, boolean pauseAndResume, boolean programRecordable, boolean timeShift, List<Stream> stream) {
        this.channelId = channelId;
        this.activate = activate;
        this.deactivate = deactivate;
        this.rating = rating;
        this.hd = hd;
        this.icon = icon;
        this.instanceRecordable = instanceRecordable;
        this.localRecordable = localRecordable;
        this.name = name;
        this.number = number;
        this.pauseAndResume = pauseAndResume;
        this.programRecordable = programRecordable;
        this.timeShift = timeShift;
        this.stream = stream;
    }

    protected Channel(Parcel in) {
        this.channelId = in.readLong();
        this.activate = in.readLong();
        this.deactivate = in.readLong();
        this.rating = in.readInt();
        this.hd = in.readByte() != 0;
        this.icon = in.readString();
        this.instanceRecordable = in.readByte() != 0;
        this.localRecordable = in.readByte() != 0;
        this.name = in.readString();
        this.number = in.readInt();
        this.pauseAndResume = in.readByte() != 0;
        this.programRecordable = in.readByte() != 0;
        this.timeShift = in.readByte() != 0;
        this.stream = new ArrayList<Stream>();
        in.readList(this.stream, Stream.class.getClassLoader());
        this.programs = new ArrayList<Program>();
        in.readList(this.programs, Program.class.getClassLoader());
    }

    public long getChannelId() {
        return channelId;
    }

    public long getActivate() {
        return activate;
    }

    public long getDeactivate() {
        return deactivate;
    }

    public int getRating() {
        return rating;
    }

    public boolean isHd() {
        return hd;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isInstanceRecordable() {
        return instanceRecordable;
    }

    public boolean isLocalRecordable() {
        return localRecordable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isPauseAndResume() {
        return pauseAndResume;
    }

    public boolean isProgramRecordable() {
        return programRecordable;
    }

    public boolean isTimeShift() {
        return timeShift;
    }

    public List<Stream> getStream() {
        return stream;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    @Override
    public int compareTo(Channel another) {
        return number - another.getNumber();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.channelId);
        dest.writeLong(this.activate);
        dest.writeLong(this.deactivate);
        dest.writeInt(this.rating);
        dest.writeByte(this.hd ? (byte) 1 : (byte) 0);
        dest.writeString(this.icon);
        dest.writeByte(this.instanceRecordable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.localRecordable ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeInt(this.number);
        dest.writeByte(this.pauseAndResume ? (byte) 1 : (byte) 0);
        dest.writeByte(this.programRecordable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.timeShift ? (byte) 1 : (byte) 0);
        dest.writeList(this.stream);
        dest.writeList(this.programs);
    }


    @Override public String toString() {
        return "Channel{"
            + "dbId="
            + dbId
            + ", serviceType="
            + serviceType
            + ", isLocalChannel="
            + isLocalChannel
            + ", channelId="
            + channelId
            + ", activate="
            + activate
            + ", deactivate="
            + deactivate
            + ", rating="
            + rating
            + ", hd="
            + hd
            + ", icon='"
            + icon
            + '\''
            + ", instanceRecordable="
            + instanceRecordable
            + ", localRecordable="
            + localRecordable
            + ", name='"
            + name
            + '\''
            + ", number="
            + number
            + ", pauseAndResume="
            + pauseAndResume
            + ", programRecordable="
            + programRecordable
            + ", timeShift="
            + timeShift
            + ", stream="
            + stream
            + ", programs="
            + programs
            + '}';
    }

}
