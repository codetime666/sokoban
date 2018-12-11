package com.ste.sokoban;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class Android_GameView extends View {

    // Variables - the Core
    private int gate = 0;

    private int manX;
    private int manY;
    private float xOff = 10;
    private float yOff = 20;

    public int mapRow = 0;
    public int mapCol = 0;
    float currentX;
    float currentY;

    private int tileSize;
    private int width = 0;
    private int height = 0;

    // Constants - map related
    final int WALL = 1;
    final int GOAL = 2;
    final int ROAD = 3;
    final int BOX = 4;
    final int BOXATGOAL = 5;
    final int MAN = 6;

    // Objects - Yo
    private Bitmap pic[] = null;
    private Paint pnt;

    // Arrays - Yo
    public int[][] map = null;
    private int[][] tem;

    // Class init
    private Android_NewGame newgame = null;

    public Android_GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        newgame = (Android_NewGame) context;

        WindowManager winMnger = newgame.getWindowManager();

        width = winMnger.getDefaultDisplay().getWidth();
        height = winMnger.getDefaultDisplay().getHeight();

        this.setFocusable(true);

        if (newgame.continue_game) {
            map = newgame.stored;

            getMapDetail();
            getManPosition();
        } else {
            initMap();
        }

        initPic();
    }

    public void initMap() {
        map = getMap(gate);

        getMapDetail();
        getManPosition();

        list.clear();
    }

    public int[][] getMap(int grade) {
        return Android_Maplist.getMap(grade);
    }

    private void getMapDetail() {
        mapRow = map.length;
        mapCol = map[gate].length;

        xOff = 30;
        yOff = 60;

        int t = mapRow > mapCol ? mapRow : mapCol;

        int s1 = (int) Math.floor((width - 2 * xOff) / t);
        int s2 = (int) Math.floor((height - yOff) / t);

        tileSize = s1 < s2 ? s1 : s2;
        tem = Android_Maplist.getMap(gate);
    }

    public void getManPosition() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == MAN) {
                    manX = i;
                    manY = j;
                    break;
                }
            }
        }
    }

    /* pic stuff */

    public void initPic() {
        pic = new Bitmap[7];

        Resources res = this.getContext().getResources();

        loadPic(WALL, res.getDrawable(R.drawable.game_wall));
        loadPic(GOAL, res.getDrawable(R.drawable.game_goal));
        loadPic(ROAD, res.getDrawable(R.drawable.game_road));
        loadPic(BOX, res.getDrawable(R.drawable.game_box));
        loadPic(BOXATGOAL, res.getDrawable(R.drawable.game_box_at_goal));
        loadPic(MAN, res.getDrawable(R.drawable.game_character));
    }

    public void loadPic(int key, Drawable tile) {
        Bitmap bitmap = Bitmap.createBitmap(tileSize, tileSize, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        tile.setBounds(0, 0, tileSize, tileSize);
        tile.draw(canvas);

        pic[key] = bitmap;
    }

    public int roadOrGoal(int x, int y) {
        int result = ROAD;

        if (tem[x][y] == GOAL) {
            result = GOAL;
        }

        return result;
    }

    /* The touch crap */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        currentX = event.getX();
        currentY = event.getY();

        float x = (float) (xOff + manY * tileSize);
        float y = (float) (yOff + manX * tileSize);

        if (currentY > y && (currentY < y + tileSize)) {
            if (currentX > x + tileSize) {
                moveRight();
            }
            if (currentX < x) {
                moveLeft();
            }
        }

        if (currentX > x && (currentX < x + tileSize)) {
            if (currentY > y + tileSize) {
                moveDown();
            }
            if (currentY < y) {
                moveUp();
            }
        }

        if (finished()) {
            nextGate();
        }

        this.invalidate();

        return super.onTouchEvent(event);
    }


    public void moveUp() {
        if (map[manX - 1][manY] == BOX || map[manX - 1][manY] == BOXATGOAL) {
            if (map[manX - 2][manY] == GOAL || map[manX - 2][manY] == ROAD) {
                storyMap(map);
                map[manX - 2][manY] = map[manX - 2][manY] == GOAL ? BOXATGOAL : BOX;

                map[manX - 1][manY] = MAN;
                map[manX][manY] = roadOrGoal(manX, manY);
                manX--;
            }
        } else {
            if (map[manX - 1][manY] == ROAD || map[manX - 1][manY] == GOAL) {
                storyMap(map);
                map[manX - 1][manY] = MAN;
                map[manX][manY] = roadOrGoal(manX, manY);

                manX--;
            }
        }
    }

    public void moveDown() {
        if (map[manX + 1][manY] == BOX || map[manX + 1][manY] == BOXATGOAL) {
            if (map[manX + 2][manY] == GOAL || map[manX + 2][manY] == ROAD) {
                storyMap(map);
                map[manX + 2][manY] = map[manX + 2][manY] == GOAL ? BOXATGOAL : BOX;

                map[manX + 1][manY] = MAN;
                map[manX][manY] = roadOrGoal(manX, manY);
                manX++;
            }
        } else {
            if (map[manX + 1][manY] == ROAD || map[manX + 1][manY] == GOAL) {
                storyMap(map);
                map[manX + 1][manY] = MAN;
                map[manX][manY] = roadOrGoal(manX, manY);

                manX++;
            }
        }
    }

    public void moveLeft() {
        if (map[manX][manY - 1] == BOX || map[manX][manY - 1] == BOXATGOAL) {
            if (map[manX][manY - 2] == GOAL || map[manX][manY - 2] == ROAD) {
                storyMap(map);
                map[manX][manY - 2] = map[manX][manY - 2] == GOAL ? BOXATGOAL : BOX;

                map[manX][manY - 1] = MAN;
                map[manX][manY] = roadOrGoal(manX, manY);
                manY--;
            }
        } else {
            if (map[manX][manY - 1] == ROAD || map[manX][manY - 1] == GOAL) {
                storyMap(map);
                map[manX][manY - 1] = MAN;
                map[manX][manY] = roadOrGoal(manX, manY);

                manY--;
            }
        }
    }

    public void moveRight() {
        if (map[manX][manY + 1] == BOX || map[manX][manY + 1] == BOXATGOAL) {
            if (map[manX][manY + 2] == GOAL || map[manX][manY + 2] == ROAD) {
                storyMap(map);
                map[manX][manY + 2] = map[manX][manY + 2] == GOAL ? BOXATGOAL : BOX;

                map[manX][manY + 1] = MAN;
                map[manX][manY] = roadOrGoal(manX, manY);
                manY++;
            }
        } else {
            if (map[manX][manY + 1] == ROAD || map[manX][manY + 1] == GOAL) {
                storyMap(map);
                map[manX][manY + 1] = MAN;
                map[manX][manY] = roadOrGoal(manX, manY);

                manY++;
            }
        }
    }

    /* and ... I dunno XDD */

    private boolean finished() {
        boolean finish = true;

        for (int i = 0; i < mapRow; i++) {
            for (int j = 0; j < mapCol; j++) {
                if (map[i][j] == GOAL || map[i][j] == BOX) {
                    finish = false;
                }
            }
        }

        return finish;
    }

    public void nextGate() {
        if (gate < Android_Maplist.getCount() - 1) {
            gate++;
        } else {
            Toast.makeText(
                    this.getContext(),
                    "It's the last last last gate!",
                    Toast.LENGTH_SHORT
            ).show();
        }

        reinitMap();
    }

    private void reinitMap() {
        initMap();
        initPic();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mapRow; i++) {
            for (int j = 0; j < mapCol; j++) {
                if (map[i][j] != 0) {
                    canvas.drawBitmap(
                            pic[map[i][j]],
                            xOff + j * tileSize,
                            yOff + i * tileSize,
                            pnt
                    );
                }
            }
        }
    }

    /* 保存进度相关 - Base */

    class CurrentMap {
        int[][] currMap;

        public CurrentMap(int[][] maps) {
            int row = maps.length;
            int col = maps[0].length;

            int[][] temp = new int[row][col];

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    temp[i][j] = maps[i][j];
                }
            }
            this.currMap = temp;
        }

        public int[][] getMap() {
            return currMap;
        }
    }

    public ArrayList<CurrentMap> list = new ArrayList<CurrentMap>();

    /* 保存进度相关 - Core */

    public void back() {
        if (list.size() > 0) {
            CurrentMap priorMap = list.get(list.size() - 1);
            map = priorMap.getMap();

            getManPosition();

            list.remove(list.size() - 1);
        } else {
            Toast.makeText(
                    this.getContext(),
                    "You can't go back.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void storyMap(int[][] map) {
        CurrentMap crtMap = new CurrentMap(map);

        list.add(crtMap);

        if (list.size() > 10) {
            list.remove(0);
        }
    }

}
