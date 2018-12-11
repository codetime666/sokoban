package com.ste.sokoban;


import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Android_Music extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.music_setting);
    }

    public static boolean isMusicChecked(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("Music", true);
    }
}