package com.datastructure.algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class KnightTravel {
    private static int X; //column
    private static int Y; //row
    //create a array to mark if the point is visited
    private static boolean[] visited;
    //create a property to mark if all the points have been visited
    private static boolean finished;

    public static void main(String[] args) {
        X = 8;
        Y = 8;
        int row = 1;
        int column = 1;
        int[][] chessboard = new int[X][Y];
        visited = new boolean[X * Y]; //init value is false

        long start = System.currentTimeMillis();
        travelChessboard(chessboard, 0, 0, 1);

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        for (int[] rows : chessboard) {
            for (int step : rows) {
                System.out.print(step+"\t");
            }
            System.out.println();
        }
    }

    /**
     * @param chessboard
     * @param row        currrent position row from zero
     * @param column     current position column from zero
     * @param step       init step is 1, calc how many steps have been traveled
     */
    public static void travelChessboard(int[][] chessboard, int row, int column, int step) {
        chessboard[row][column] = step;
        visited[row * X + column] = true;
        ArrayList<Point> ps = next(new Point(column, row));
        sort(ps);
        //iteration ps
        while (!ps.isEmpty()) {
            Point p = ps.remove(0); //get next possible point
            //check if it has been visited
            if (!visited[p.y * X + p.x]) {
                //did not visited
                travelChessboard(chessboard, p.y, p.x, step + 1);
            }
        }
        if (step < X * Y && !finished) {
            chessboard[row][column] = 0;
            visited[row * X + column] = false;
        } else {
            finished = true;
        }
    }

    /**
     * ?????????????????????point??????????????????????????????????????????Point??????????????????????????????ArrayList????????????8?????????
     *
     * @param curPoint
     * @return
     */
    public static ArrayList<Point> next(Point curPoint) {
        ArrayList<Point> ps = new ArrayList<>();
        Point p1 = new Point();

        //#1. ???2
//        p1.x = curPoint.x - 2; //????????????????????? ?????????????????????????????????
//        p1.y = curPoint.y - 1;  //???????????????????????????????????????????????????
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        //#2  ???1
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        //#3 ???1
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        //#4 ???2
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        //#5. ???3
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        //#6  ???4
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        //#7 ???4
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        //#8 ???3
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }

        return ps;
    }

    //?????????????????????????????????????????????????????????????????????????????????
    public static void sort(ArrayList<Point> ps) {
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                int count1 = next(o1).size();
                int count2 = next(o2).size();
                if (count1 < count2) {
                    return -1;
                } else if (count1 == count2) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }
}
