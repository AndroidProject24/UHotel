package com.acuteksolutions.uhotel.mvp.model.livetv;

import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.realm.RealmList;
import io.realm.RealmObject;

public class TVInfo extends RealmObject implements Comparator<TVInfo>{
    private String title;
    private long start;
    private long end;
    private String channelName;
    private int channelNo;
    private String description;
    private String pictureLink;
    private RealmList<Stream> channelStreams;
    public TVInfo() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public void setChannelStreams(RealmList<Stream> channelStreams) {
        this.channelStreams = channelStreams;
    }

    public String getTitle() {
        return title;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public RealmList<Stream> getChannelStreams() {
        return channelStreams;
    }


    public String getTimeLeftNow() {
        long diff = end - new Date().getTime();

        //long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff)+1;

        return (minutes < 0 ? 0 : minutes) + " min(s). left";
    }

    public String getTimeLeftUpComing() {
        long diff = start - new Date().getTime();

        long days= TimeUnit.MILLISECONDS.toDays(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff)+1;

        String timeleft=days>0? (days+" day(s)") : hours>0? (hours+" hour(s)"):minutes+" min(s)";
        return "in "+timeleft;
    }

    public boolean isPlaying() {
        long current=new Date().getTime();
        long currentStartDiff = current-start;
        long endCurrentDiff = end - current;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(endCurrentDiff);

        return currentStartDiff >= 0 && endCurrentDiff >= 0;
    }

    public boolean isComingUp() {
        long current=new Date().getTime();
        return current > start && current < end;
    }

    @Override
    public String toString() {
        return "TVInfo{" +
                "channelId=" + channelNo +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", channelName='" + channelName + '\'' +
                ", description='" + description + '\'' +
                ", pictureLink='" + pictureLink + '\'' +
                ", channelStreams=" + channelStreams +
                '}';
    }

    public boolean isNew() {
        long diff = start - new Date().getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return minutes > 0;
    }

    @Override
    public int compare(TVInfo source, TVInfo desc) {
        return source.channelNo-desc.channelNo;
    }
}
