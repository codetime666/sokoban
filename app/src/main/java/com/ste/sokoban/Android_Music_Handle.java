package com.ste.sokoban;

import android.content.Context;
import android.media.MediaPlayer;

public class Android_Music_Handle {
    private static MediaPlayer mediaplr = null;

    public static void play(Context context, int resource) {
        stop(context);

        if (Android_Music.isMusicChecked(context)) {
            mediaplr = MediaPlayer.create(context, resource);

            mediaplr.setLooping(true);
            mediaplr.start();
        }
    }

    public static void stop(Context context) {
        if (mediaplr != null) {
            mediaplr.stop();
            mediaplr.release();

            mediaplr = null;
        }
    }
}
