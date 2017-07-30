package sort.definition;

import priority.queue.definition.MaxPQ;

/**
 * 堆排序 
 * 利用   基于最大(小)二叉堆的   优先级队列 来实现 
 * 最大二叉堆——实现的是非降次序
 * 堆排序两个阶段:
 * 1、批量建立二叉堆    可以利用MaxPQ(MinPQ)的构造函数public MaxPQ(T[] a)传入带排序数组a 构造了一个基于最大二叉堆的优先级队列
 * 2、循环调用二叉堆的delMax()(delMin)方法取出未排序序列中的最大值依次放入数组a的末尾  即可完成整个堆排序  使整个序列有序
 * @author wjs13
 *
 */
public class Heap {
    
    public static void sort(Comparable[] a){//a[]按非降排序
       MaxPQ maxPQ = new MaxPQ(a);//批量建立 最大二叉堆
       int len = a.length;
       for (int i = len - 1; i >= 0; i--){
         //根据delMax的具体实现  弹出根元素值  交换队尾和根元素 delMax实际删除的是MaxPQ队尾  
//           即每次del数组所表示的PQ队尾的一个元素就会被置空 且刚好是位于数组尾部  即每次可以把弹出的极值放到数组a的末尾就完成了排序
           a[i] = maxPQ.delMax();
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
