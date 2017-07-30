package sort.definition;
/**
 * 插入排序的最优版   
 * 利用了 哨兵
 * 在插入排序的实现中先找出最小的元素并将其置于数组的最左边  这样做就能去掉内循环的判断条件j>0
 * 这是一种常用的规避边界测试的方法 能够省略判断条件的元素通常被称为哨兵
 * 非降顺序排序
 * @author wjs13
 *
 */
public class InsertionX {
    
    public static void sort(Comparable[] a){//a[]按非降排序
        int n = a.length;

        // put smallest element in position to serve as sentinel
        int exchanges = 0;
        for (int i = n-1; i > 0; i--) {
            if (less(a[i], a[i-1])) {
                exch(a, i, i-1);
                exchanges++;
            }
        }
        if (exchanges == 0) return;


        // insertion sort with half-exchanges
        for (int i = 2; i < n; i++) {
            Comparable v = a[i];
            int j = i;
            while (less(v, a[j-1])) {
                a[j] = a[j-1];
                j--;
            }
            a[j] = v;
        }

    }
    
  //标准是按非降次序排列的  所以按非降比较两个对象次序
    private static boolean less(Comparable v,Comparable w){//比较两对象
        return v.compareTo(w) < 0;
    }
    private static void exch(Comparable[] a, int i, int j){//交换元素 抽取出的通用方法
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static boolean isSorted(Comparable[] a){//测试数组元素是否是有序的  按非降次序排序
        for (int i =0; i < a.length - 1; i++){
            if (less(a[i + 1], a[i]))//发现后一个比前一个小的 则非降次序就不对 即非有序的   元素相等也算是有序 非降
                return false;
        }
            return true;
    }
    
}
