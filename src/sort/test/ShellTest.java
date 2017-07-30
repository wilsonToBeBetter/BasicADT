package sort.test;

import java.util.Arrays;
import java.util.Random;

import sort.definition.Shell;
import util.definition.Stopwatch;

public class ShellTest {

    public static void main(String[] args) {
        Integer[] array = new Integer[10000];
        for (int i = 0; i < array.length; i++){//构造1000以内的20个随机int数
            array[i] = new Random().nextInt(10000);
        }
        System.out.println(Arrays.toString(array));
//      排序
        Stopwatch stopwatch = new Stopwatch();
        Shell.sort(array);
        System.out.println(stopwatch.elapsedTime());
        System.out.println(Shell.isSorted(array));
        System.out.println(Arrays.toString(array));
    }
}
