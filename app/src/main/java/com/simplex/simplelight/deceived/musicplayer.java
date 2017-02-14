package com.simplex.simplelight.deceived;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by David on 7/1/2016.
 */

public class musicplayer {
    MediaPlayer mediaPlayer=new MediaPlayer();

    public musicplayer(Context context, int song){
        mediaPlayer = MediaPlayer.create(context, song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    public void startMusic()
    {
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer.start();
    }

    public void startMusic(Context context, int song){
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else mediaPlayer = MediaPlayer.create(context, song);
        mediaPlayer.start();
    }

    public void stopMusic()
    {
        mediaPlayer.stop();
    }

    public void endMusicPlayer()
    {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
    }

}