package com.acuteksolutions.uhotel.mvp.model.livetv;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Stream extends RealmObject implements Parcelable {
    @PrimaryKey
    private String src;
    private String provider;
    private String protocolStack;
    private String location;
    private String profiles;
    private String capabilities;
    private String other;
    private Integer duration;
    private Integer offset;
    private String protocol;
    private String dialect;
    private String oTag;
    private boolean signOauth;

    public Stream() {
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProtocolStack() {
        return protocolStack;
    }

    public void setProtocolStack(String protocolStack) {
        this.protocolStack = protocolStack;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfiles() {
        return profiles;
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getoTag() {
        return oTag;
    }

    public void setoTag(String oTag) {
        this.oTag = oTag;
    }

    public boolean isSignOauth() {
        return signOauth;
    }

    public void setSignOauth(boolean signOauth) {
        this.signOauth = signOauth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.src);
        dest.writeString(this.provider);
        dest.writeString(this.protocolStack);
        dest.writeString(this.location);
        dest.writeString(this.profiles);
        dest.writeString(this.capabilities);
        dest.writeString(this.other);
        dest.writeValue(this.duration);
        dest.writeValue(this.offset);
        dest.writeString(this.protocol);
        dest.writeString(this.dialect);
        dest.writeString(this.oTag);
        dest.writeByte(this.signOauth ? (byte) 1 : (byte) 0);
    }

    protected Stream(Parcel in) {
        this.src = in.readString();
        this.provider = in.readString();
        this.protocolStack = in.readString();
        this.location = in.readString();
        this.profiles = in.readString();
        this.capabilities = in.readString();
        this.other = in.readString();
        this.duration = (Integer) in.readValue(Integer.class.getClassLoader());
        this.offset = (Integer) in.readValue(Integer.class.getClassLoader());
        this.protocol = in.readString();
        this.dialect = in.readString();
        this.oTag = in.readString();
        this.signOauth = in.readByte() != 0;
    }

    public static final Creator<Stream> CREATOR = new Creator<Stream>() {
        @Override
        public Stream createFromParcel(Parcel source) {
            return new Stream(source);
        }

        @Override
        public Stream[] newArray(int size) {
            return new Stream[size];
        }
    };
}
