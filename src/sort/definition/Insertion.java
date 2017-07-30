package sort.definition;
/**
 * 插入排序
 * 输入敏感的
 * 稳定的
 * 非降顺序排序
 * @author wjs13
 *小数组对与高位优先的字符串排序的影响尤其强烈
 *将小数组切换到插入排序对于高位优先的字符串排序算法是必须的
 */
public class Insertion {
    
    /**
     * 正宗的插入排序 .
     * 之前写的不是插入 是冒泡排序
     * @param a
     */
    public static void sort(Comparable[] a){//a[]按非降排序
        int n = a.length;//待排序元素的个数
        for (int i = 1; i < n; i++){
            //将a[i]插入到 a[i-1]、a[i-2]、a[i-3]......对应位置之中
            for (int j = 0; j < i; j++) {//注意是从索引从0开始的位置开始比较 并判断是否要插入的
                if (less(a[i], a[j])) {//插入元素到相应的位置  插入前 先把j到i之前的元素整体向后移动一个单位
                    Comparable t = a[i];//O(1)的辅助空间 是落地算法
                    for (int k = i; k > j; k--) {
                        a[k] = a[k - 1];
                    }
                    a[j] = t;
                    break;
                }
            }
               
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
    
    //对前d个字符均相同的字符串执行插入排序
    public static void sort(String[] a, int lo, int hi, int d) {
        //从第d个字符 开始对a[l0]到a[hi]字符串进行排序  用于 高位优先字符串排序 来处理小型子数组的字符串数组排序  提高效率
        // TODO Auto-generated method stub
        for (int i = lo; i <= hi; i++){
            for (int j = i; j > lo && less(a[j], a[j - 1], d);j--){
                exch(a,j,j-1);
            }
        }
        
    }
//    用于 高位优先字符串排序 来处理小型子数组的字符串数组排序 提高效率
    private static boolean less(String v, String w, int d) {
        // TODO Auto-generated method stub
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }
    
}
