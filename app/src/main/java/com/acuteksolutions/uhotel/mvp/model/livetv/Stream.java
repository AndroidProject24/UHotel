package com.acuteksolutions.uhotel.mvp.model.livetv;

import android.os.Parcel;
import android.os.Parcelable;

public class Stream implements Parcelable {
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
    private String src;
    private String provider;
    private String protocolStack;
    private String profiles;
    private String capabilities;

    public Stream() {
        this.src = "";
        this.provider = "";
        this.protocolStack = "";
        this.profiles = "";
        this.capabilities = "";

    }

    public Stream(String src, String provider, String protocolStack, String profiles, String capabilities) {
        this.src = src;
        this.provider = provider;
        this.protocolStack = protocolStack;
        this.profiles = profiles;
        this.capabilities = capabilities;
    }

    protected Stream(Parcel in) {
        this.src = in.readString();
        this.provider = in.readString();
        this.protocolStack = in.readString();
        this.profiles = in.readString();
        this.capabilities = in.readString();
    }

    public String getSrc() {
        return src;
    }

    public String getProvider() {
        return provider;
    }

    public String getProtocolStack() {
        return protocolStack;
    }

    public String getProfiles() {
        return profiles;
    }

    public String getCapabilities() {
        return capabilities;
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
        dest.writeString(this.profiles);
        dest.writeString(this.capabilities);
    }
}
