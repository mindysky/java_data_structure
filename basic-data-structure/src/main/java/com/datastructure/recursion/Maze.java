package com.datastructure.recursion;

public class Maze {
    public static void main(String[] args) {

        //创建迷宫二维数组
        int[][] map = new int[8][7];
        //使用1 表示墙
        //上下全部设置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //左右全部设置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        map[3][1] = 1;
        map[3][2] = 1;
        //输入地图
        System.out.println("地图");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        //使用递归回溯给小球找路
//        setWay(map,1,1);
//
//        //输出新的地图
//        System.out.println("地图----小球走过");
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 7; j++) {
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }



        //使用递归回溯给小球找路
        setWay2(map,1,1);

        //输出新的地图
        System.out.println("地图----小球走过2");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }



    }

    /**
     *
     * @param map
     * @param i  从哪个位置开始找
     * @param j
     * @return  如果找到通路就返回true, 否则返回false
     * 如果小球能到map[6][5]位置，则说明通路找到
     * 约定： 0表示没走过，1表示墙，2表示通路可以走，3表示该路已经走过，但走不通
     * 在走迷宫时，需要确定一个策略 下-》右-》上-》左， 如果该点走不通，再回溯
     */
    public static boolean setWay(int[][] map,int i, int j){
        if(map[6][5]==2){
            //通路已找到
            return true;
        }else{
            if(map[i][j]==0){
                //如果当前这个点还没走过
                map[i][j]=2; //假定该点可以走通
                if(setWay(map,i+1,j)){//向下走
                    return true;
                }else if(setWay(map,i,j+1)){//向右走
                    return true;
                }else if(setWay(map, i-1,j)){//向上走
                    return true;
                }else if(setWay(map,i,j-1)){//向左走
                    return true;
                }else{
                    //说明该点走不通
                    map[i][j] = 3;
                    return false;
                }
            }else {//如果map[i][j]不等于0 可能是1,2,3
                return false;

            }
        }
    }



    public static boolean setWay2(int[][] map,int i, int j){
        if(map[6][5]==2){
            //通路已找到
            return true;
        }else{
            if(map[i][j]==0){
                //如果当前这个点还没走过
                map[i][j]=2; //假定该点可以走通
                if(setWay2(map,i-1,j)){//向下走
                    return true;
                }else if(setWay2(map,i,j+1)){//向右走
                    return true;
                }else if(setWay2(map, i+1,j)){//向上走
                    return true;
                }else if(setWay2(map,i,j-1)){//向左走
                    return true;
                }else{
                    //说明该点走不通
                    map[i][j] = 3;
                    return false;
                }
            }else {//如果map[i][j]不等于0 可能是1,2,3
                return false;

            }
        }
    }
}
