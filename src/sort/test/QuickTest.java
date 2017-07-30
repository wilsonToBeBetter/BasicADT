package sort.test;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import sort.definition.Quick;
import util.definition.Stopwatch;
/**
 * Quick排序 太快了 以至于 排序几万长度的数组 测出消耗的时间都不能用毫秒表示 用毫秒输出都是0
 * 固测试至少用十万长度的数组排序  结果才是花费几十毫秒
 * @author wjs13
 *
 */
public class QuickTest {
    
    public static void main(String[] args) {
        Integer[] array = new Integer[100000];
        for (int i = 0; i < array.length; i++){//构造1000000以内的100000个随机int数
            array[i] = new Random().nextInt(10000000);
        }
//        System.out.println(Arrays.toString(array));
//        排序
        Stopwatch stopwatch = new Stopwatch();
        Quick.sort(array);
        System.out.println(stopwatch.elapsedTime());
        System.out.println(Quick.isSorted(array));
//        System.out.println(Arrays.toString(array));
    }
    @Test
    public void test1() {
        Integer[] a ={9,9,9,8,9,8,7,9,8,8,8,9,8,9,8,8,6,9};
        Quick.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
