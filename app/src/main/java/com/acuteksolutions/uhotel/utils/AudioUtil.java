package com.acuteksolutions.uhotel.utils;

import android.content.Context;
import android.media.AudioManager;

public class AudioUtil {

    private AudioManager mAudioManager;
    private static AudioUtil mInstance;

    private AudioUtil(Context context){
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public synchronized static AudioUtil getInstance(Context context){
        if(mInstance == null){
            mInstance = new AudioUtil(context);
        }
        return mInstance;
    }

    public int getMediaMaxVolume(){
        return mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
    }

    public int getMediaVolume(){
        return mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
    }

    public int getCallMaxVolume(){
        return mAudioManager.getStreamMaxVolume( AudioManager.STREAM_VOICE_CALL );
    }

    public int getSystemMaxVolume(){

        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM );
    }

    public int getSystemVolume(){

        return mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM );
    }

    public int getAlermMaxVolume(){
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM );
    }

    public void setMediaVolume(int volume){
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, //音量类型
                volume,
                AudioManager.FLAG_PLAY_SOUND
                        | AudioManager.FLAG_SHOW_UI);
    }

    public void setCallVolume(int volume){
        mAudioManager.setStreamVolume( AudioManager.STREAM_VOICE_CALL,
                volume,
                AudioManager.STREAM_VOICE_CALL);
    }

    @SuppressWarnings("deprecation")
    public void setSpeakerStatus(boolean on) {
        if (on) {
            mAudioManager.setSpeakerphoneOn(true);
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
        } else {
            int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
            mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, max, AudioManager.STREAM_VOICE_CALL);
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            mAudioManager.setSpeakerphoneOn(false);// 关闭扬声器
            mAudioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
        }
    }
}