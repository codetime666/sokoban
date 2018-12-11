package com.ste.sokoban;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Android_NewGame extends Activity {

    Android_GameView gameView = null;

    public boolean continue_game;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        continue_game = false;

        s = getIntent().getStringExtra("continue");

        if (s.equals("yes")) {
            continue_game = true;
        }

        if (continue_game) {
            loadMap();
        }

        setContentView(R.layout.activity_android_new_game);

        gameView = (Android_GameView) findViewById(R.id.viewCustomOurOwn);
    }

    @Override
        protected void onResume() {
            super.onResume();
            Android_Music_Handle.play(this, R.raw.braveheart);
        }

    @Override
    protected void onPause() {
        super.onPause();
        Android_Music_Handle.stop(this);

        getPreferences(MODE_PRIVATE).edit().putString("maps", saveGame()).apply();
        getPreferences(MODE_PRIVATE).edit().putLong("maprow", gameView.mapRow).apply();
        getPreferences(MODE_PRIVATE).edit().putLong("mapcol", gameView.mapCol).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflatr = new MenuInflater(this);
        inflatr.inflate(R.menu.option_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemHelp:
                Intent intentHelp = new Intent(
                        Android_NewGame.this,
                        Android_OptionHelp.class
                );

                startActivity(intentHelp);
                break;

            case R.id.itemMusic:
                Intent intentMusic = new Intent(
                        Android_NewGame.this,
                        Android_Music.class
                );

                startActivity(intentMusic);
                break;

            case R.id.itemBack:
                gameView.back();
                gameView.invalidate();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public String saveGame() {
        int[] saved = new int[gameView.mapRow * gameView.mapCol];

        for (int i = 0; i < gameView.mapRow; i++) {
            for (int j = 0; j < gameView.mapCol; j++) {
                saved[i * gameView.mapRow + j] = gameView.map[i][j];
            }
        }

        StringBuilder strBuffer = new StringBuilder();

        for (int element : saved) {
            strBuffer.append(element);
        }

        String s = strBuffer.toString();

        return s;
    }

    // well, levelOne as single string

    String levelOne = "0011100000121000001311111114342112346111111141000001210000011100";
    public int[][] stored;

    public void loadMap() {
        String mapString = getPreferences(MODE_PRIVATE).getString("maps", levelOne);

        int ii = (int) getPreferences(MODE_PRIVATE).getLong("maprow", 8);
        int jj = (int) getPreferences(MODE_PRIVATE).getLong("mapcol", 8);

        int[] maps = new int[mapString.length()];

        for (int i = 0; i < maps.length; i++) {
            maps[i] = mapString.charAt(i) - '0';
        }

        stored = new int[ii][jj];

        int a = 0;

        for (int m = 0; m < ii; m++) {
            for (int n = 0; n < jj; n++) {
                stored[m][n] = maps[a];
                a++;
            }
        }
    }

}
