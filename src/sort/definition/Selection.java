package sort.definition;
/**
 * 选择排序
 * 非稳定的
 * @author wjs13
 *
 */
public class Selection {
    
    public static void sort(Comparable[] a){//a[]按非降排序
        int n = a.length;//待排序元素的个数
        for (int i = 0; i < n - 1; i++){//最后一个元素不用参与最后一次的比较 所以为len-1 注意！
//            将a[i]与a[i+1、...len-1]中最小的元素交换
            int min = i;  //最小元素的索引
            for (int j = i + 1; j < n; j++)
                if (less(a[j], a[min]))//此处元素相等 则不会改变索引  即排序是稳定的  不改变相等元素的相对次序
                    min = j;//选择排序的一种改进 在一次扫描中找到最小元素的索引 一次扫描后才执行一次元素的交换 不用每次比较后交换元素
            exch(a, i, min);
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
            if (less(a[i + 1], a[i]))//发现后一个比前一个小的 则非降次序就不对 即非有序的  元素相等也算是有序 非降
                return false;
        }
            return true;
    }
}
