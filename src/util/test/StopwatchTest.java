package util.test;

import util.definition.Stopwatch;

public class StopwatchTest {

    public static void main(String[] args) {
        int[] a = new int[100000000];
        int len = a.length;
        Stopwatch stopwatch = new Stopwatch();//按下秒表
        for(int i =0; i < len; i++ ){//计时过程执行
            a[i] = 121;
        }
        double elapsedTime = stopwatch.elapsedTime();//按下秒表 得到计时过程耗时时间
        System.out.println(elapsedTime+"秒");
    }
}
