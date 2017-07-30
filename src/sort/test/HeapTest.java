package sort.test;

import java.util.Arrays;
import java.util.Random;

import sort.definition.Heap;

public class HeapTest {

    public static void main(String[] args) {
        Integer[] array = new Integer[20];
        for (int i = 0; i < array.length; i++){//构造100以内的20个随机int数
            array[i] = new Random().nextInt(100);
        }
        System.out.println(Arrays.toString(array));
//        排序
        Heap.sort(array);
        System.out.println(Heap.isSorted(array));
        System.out.println(Arrays.toString(array));
    }
}
