package com.datastructure.recursion;

public class ChessDemo {
    //定义max表示共有多少个皇后
    int max = 8;
    //定义数组array, 保存皇后放置的位置结果，arr = {0,4,7,5,2,6,1,3}
    int[] array = new int[max];
    static int count = 0;
    public static void main(String[] args) {
        ChessDemo queen8 = new ChessDemo();
        queen8.check(0);

        System.out.printf("total= %d",count);
    }


    //编写一个方法，放置第n个皇后
    private void check(int n){
        if(n==max){
            print();
            return;
        }

        //依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            //先把当前的皇后n，放到该行第1列
            array[n] = i;

            if(judge(n)){
                check(n+1);
            }
            //如果冲突，就继续执行array[n]=i, 即将第n个皇后，放置在本行 后移的一个位置


        }



    }




    //查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突

    /**
     *
     * @param n  表示第n个皇后
     * @return
     */
    private boolean judge(int n){
        for(int i=0; i<n; i++){
            //1. array[i]==array[n] 表示判断 第n个皇后是否和前面的n-1个皇后在同一列
            //2. Math.abs(n-i)==Math.abs(array[n]-array[i]) 表示判断第n个皇后是否和第i个皇后在同一个斜线
            if(array[i]==array[n]||Math.abs(n-i)==Math.abs(array[n]-array[i])){
                return false;
            }
        }
        return  true;
    }




    //写一个方法
    private void print(){
        count++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }

}
