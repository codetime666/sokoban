package com.ste.sokoban;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Android_Game_Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
    }

    public void onClick(View view) {

        Intent intent = null;

            switch (view.getId()) {
            case R.id.btnExit:
                isFinish();
                break;
            case R.id.btnAbout:
                intent = new Intent(Android_Game_Main.this, Android_About.class);
                startActivity(intent);
                break;
            case R.id.btnNew:
                intent = new Intent(Android_Game_Main.this, Android_NewGame.class);
                intent.putExtra("continue", "no");
                startActivity(intent);
                break;
            case R.id.btnContinue:
                intent = new Intent(this, Android_NewGame.class);
                intent.putExtra("continue", "yes");
                startActivity(intent);
                break;
        }
    }

    public void isFinish() {
        AlertDialog.Builder dll= new AlertDialog.Builder(this);

        dll.setTitle("你确定要退出？");
        dll.setIcon(R.drawable.game_exit);
        dll.setMessage("在玩会白");

        dll.setPositiveButton("我不", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Android_Game_Main.this.finish();
            }
        });

        dll.setNeutralButton("再玩会", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // pass
            }
        });

        dll.create();
        dll.show();
    }
}
