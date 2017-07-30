package sort.definition;

import java.util.Arrays;

/**
 * 归并排序   ——  自底而上的归并
 * 比标准归并代码量要少   但稍微难理解一点
 * 两两归并  四四归并 八八归并 十六十六归并........
 * 
 * 注意API为sort(Comparable[] a,Comparable[] a1, int lo, int hi)  hi=a.length   hi相当于一个哨兵
 * 基于二路归并  完成两个有序子序列合并成一个有序序列
 * @author wjs13
 *merge方法中有个i++的问题需要特别注意
 *a1为归并排序辅助空间  可以改名字为aux
 *此处空间复杂度不同于自顶向下的归并算法  经过递推分析 n取n/2会导致辅助空间不够发生数组越界  
 */
public class MergeX {
    
    private MergeX(){}
    
    public static void sort(Comparable[] a){//a[]按非降排序   
        int n = a.length;
      //此处空间复杂度不同于自顶向下的归并算法  经过递推分析 n取n/2会导致辅助空间不够发生数组越界  
        Comparable[] a1 = new Comparable[n];//且暂时不知道辅助空间使用大小相对于n的规律 所以就用大小为n 
        //进行lgn次两两归并
        for (int i = 1; i < n; i += i)//下次归并i扩大两倍     i为子序列大小
            //最大的待排序子序列的下限lo就是最右边的子序列[n-i,n)    lo为子序列索引
            for (int lo = 0; lo < n - i; lo += i + i)//下一个lo为前一个lo加两倍的i
                //因为最右一次的归并的第二个子序列可能长度比第一个子序列要小  则最后的上界为n和lo+i+i中的小者
                merge(a, a1, lo, lo + i, Math.min(lo + i + i, n));
    }
    
  //二路归并 merge算法 前一部分待合并数组单独用数组a1表示 后一半待合并数组直接就地用待排序数组a
    //此实现 参考了 《数据结构》 清华,邓俊辉  视频
    public static void merge(Comparable[] a,Comparable[] a1, int lo, int mid, int hi){
//        此处在lo和mid差距很小即a1长度很小时也会从新创建一个数组   固可以改进 不用每次都去创建一个新的数组  
//        可通过sort方法传用户自己的辅助数组a1  在merge中重复利用一个数组a1空间  传入的a1可以设置容量为一半的a
        for (int i = 0; i < mid - lo; i++){//前一半待合并数组单独复制出来放到数组a1中
            a1[i] = a[i + lo];
        }
//        Comparable[] a1 = Arrays.copyOfRange(a, lo, mid);//自己开始的版本  前一半待合并数组单独复制出来放到数组a1中
//        System.out.println("数组a1为"+Arrays.toString(a1)); 调试代码
        int len = mid - lo ;//数组a1中表示待本次合并的前一半的元素个数
        int k = mid;//后一半带合并数组 就地 用数组a表示    k用以指示后半部分待合并数组的索引
        //前一半待合并数组为a1[j]   后一半待合并数组为a[k]
        for (int i = lo, j = 0; (j < len) || (k < hi); ){
            if ((j < len) && (k >= hi || !less(a[k], a1[j]))){//包含 后半部分待合并序列索引k出界(超过hi，即超过后半部分待合并数组长度 成无效索引)的情况
                a[i++] = a1[j++]; //此处i++必须放到此处 不能在for()中    一开始就犯这样的错误 检查了n久 
                //因为如果放在for中 两个if都满足的话   则 同一个i的值会被覆盖  就错误了
//                即要注意两个if可以在一次的for循环中连续满足条件而连续的执行 如果i不++就会出错
            }
           if ((k < hi) && (j >= len || less(a[k], a1[j]))){//包含 前半部分待合并序列索引j出界(超过前半部分待合并数组长度成无效索引)的情况
            a[i++] = a[k++];    //此处i++必须放到此处 不能在for()中    一开始就犯这样的错误 检查了n久  
          //即注意随着第一个if中的j++ 到第二个if的a1[j]又是一个不同与第一个if中的值 固第二个if是有可能再次满足条件而执行 固i++必须放在每一个if中a[i++]
            }
        }
//        System.out.println(Arrays.toString(Arrays.copyOfRange(a,lo,hi)));调试代码
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
