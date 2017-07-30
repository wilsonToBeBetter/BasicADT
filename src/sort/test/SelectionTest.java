package sort.test;

import java.util.Arrays;
import java.util.Random;

import sort.definition.Selection;

public class SelectionTest {

    public static void main(String[] args) {
        Integer[] array = new Integer[20];
        for (int i = 0; i < array.length; i++){//构造1000以内的20个随机int数
            array[i] = new Random().nextInt(1000);
        }
        System.out.println(Arrays.toString(array));
//        排序
        Selection.sort(array);
        System.out.println(Selection.isSorted(array));
        System.out.println(Arrays.toString(array));
    }
}
