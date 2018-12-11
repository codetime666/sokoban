package com.ste.sokoban;

import android.app.Activity;
import android.os.Bundle;

public class Android_Maplist {

    /*
        The map
            0   Nothin'
            1   Wall
            2   Goal
            3   Road
            4   Box
            5   Bot at Goal
            6   Man
    */

    static int map[][][] = {
            {{0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 1, 2, 1, 0, 0, 0}, {0, 0, 1, 3, 1, 1, 1, 1},
                    {1, 1, 1, 4, 3, 4, 2, 1}, {1, 2, 3, 4, 6, 1, 1, 1}, {1, 1, 1, 1, 4, 1, 0, 0},
                    {0, 0, 0, 1, 2, 1, 0, 0}, {0, 0, 0, 1, 1, 1, 0, 0}},
            {{1, 1, 1, 1, 1, 0, 0, 0, 0}, {1, 3, 3, 6, 1, 0, 0, 0, 0}, {1, 3, 4, 4, 1, 0, 1, 1, 1},
                    {1, 3, 4, 3, 1, 0, 1, 2, 1}, {1, 1, 1, 3, 1, 1, 1, 2, 1}, {0, 1, 1, 3, 3, 3, 3, 2, 1},
                    {0, 1, 3, 3, 3, 1, 3, 3, 1}, {0, 1, 3, 3, 3, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1, 0, 0, 0}},
            {{0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, {0, 1, 3, 3, 3, 3, 3, 1, 1, 1}, {1, 1, 4, 1, 1, 1, 3, 3, 3, 1},
                    {1, 6, 3, 3, 4, 3, 3, 4, 3, 1}, {1, 3, 2, 2, 1, 3, 4, 3, 1, 1},
                    {1, 1, 2, 2, 1, 3, 3, 3, 1, 0}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 0}},
            {{0, 1, 1, 1, 1, 0}, {1, 1, 3, 3, 1, 0}, {1, 3, 6, 4, 1, 0}, {1, 1, 4, 3, 1, 1},
                    {1, 1, 3, 4, 3, 1}, {1, 2, 4, 3, 3, 1}, {1, 2, 2, 3, 2, 1}, {1, 1, 1, 1, 1, 1}}};

    static int count = map.length;

    public static int[][] getMap(int grade) {
        int temp[][];

        if (grade >= 0 && grade < count) {
            temp = map[grade];
        } else {
            temp = map[0];
        }

        int row = temp.length;
        int col = temp[0].length;

        int[][] result = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result[i][j] = temp[i][j];
            }
        }

        return result;
    }

    public static int getCount() {
        return count;
    }

}
