package sort.test;

import java.util.Arrays;
import java.util.Random;

import sort.definition.Insertion;

public class InsertionTest {

    public static void main(String[] args) {
        Integer[] array = new Integer[20];
        for (int i = 0; i < array.length; i++){//构造1000以内的20个随机int数
            array[i] = new Random().nextInt(1000);
        }
        System.out.println(Arrays.toString(array));
//      排序
        Insertion.sort(array);
        System.out.println(Insertion.isSorted(array));
        System.out.println(Arrays.toString(array));
    }
}
