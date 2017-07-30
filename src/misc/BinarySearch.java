package misc;

import org.junit.Test;

public class BinarySearch {
  
    @Test
    public void test() {
       int[] a = {1,2,3,4,7,8,10,12};
       System.out.println(binarySearch(a, 7));
    }
    
    private static int binarySearch(int[] a, int key){//二分查找 的迭代实现
        //数组a必须是有序的
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi){
            int mid = lo + (hi - lo) / 2;//用(lo+hi)/2 可能会发生溢出
            if (key < a[mid]) hi = mid -1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
    
}
