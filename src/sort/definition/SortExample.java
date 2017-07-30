package sort.definition;
/**
 * 排序类的  模版
 * 标准是按   非降次序   排序
 * Comparable 是实现了Comparable接口的可以比较的要进行排序的元素对象类型
 * @author wjs13
 *
 */
class SortExample {
    public static void sort(Comparable[] a){//a[]按非降排序
        
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
