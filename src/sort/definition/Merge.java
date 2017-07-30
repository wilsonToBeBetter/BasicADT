package sort.definition;

import java.util.Arrays;

/**
 * 归并排序    ——分治策略典型应用
 * ——标准的自顶向下归并算法
 * 无序向量递归分解  到  有序向量逐层归并
 * 技术点: 1-序列一份为二  2-子序列递归排序  3-合并有序子序列
 * 注意API为sort(Comparable[] a,Comparable[] a1, int lo, int hi)  hi=a.length   hi相当于一个哨兵
 *a1为归并排序辅助空间  可以改名字为aux
 * 基于二路归并  完成两个有序子序列合并成一个有序序列
 * @author wjs13
 *merge方法中有个i++的问题需要特别注意
 */
public class Merge {
    private Merge(){}
    
    public static void sort(Comparable[] a){//重载sort方法 使对外提供统一的sort排序接口  技巧需要注意！
        int n = a.length;
        Comparable[] a1 = new Comparable[n / 2 + 1]; //因为每次合并一半的子序列 固辅助的数组空间最大为a长度的一半  四舍五入的一半
        sort(a, a1, 0, n);
    }
    
    private static void sort(Comparable[] a, Comparable[] a1, int lo, int hi){//a[]按非降排序     hi为待排序数组的长度a.length  非数组末尾索引
      //注意本API设计的hi的意义
        if (hi - lo < 2) return;//到达递归基
        int mid = (lo + hi) / 2;//以中点把待排序列一分为二
      //递归的归并排序
        sort(a, a1, lo, mid);//对左半边排序
        sort(a, a1, mid, hi);//对右半边排序
        //二路归并  完成两个有序子序列合并成一个有序序列
        merge(a, a1, lo, mid, hi);
    }
    
    //二路归并 merge算法 前一部分待合并数组单独用数组a1表示 后一半待合并数组直接就地用待排序数组a
    //此实现 参考了 《数据结构》 清华,邓俊辉  视频
    public static void merge(Comparable[] a,Comparable[] a1, int lo, int mid, int hi){
//        此处在lo和mid差距很小即a1长度很小时也会从新创建一个数组   固可以改进 不用每次都去创建一个新的数组  
//        则可通过sort方法传用户自己的辅助数组a1  在merge中重复利用一个数组a1空间  传入的a1可以设置容量为一半的a
//        注意hi一定要设计成 哨兵 即为数组边界 否则 不好完成合并操作  即数组[lo,hi)左开右闭
      //对于左右两个有序子序列a[mid-1]小与等于a[mid] 则数组[lo,hi)就已经是有序的了 可以跳过merge归并操作
        if (!less(a[mid],a[mid - 1])) return;
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
//    上面的精简版 如下
    private static void merge(int[] x, int[] aux, int lo, int mi, int hi) {//归并排序的 二路归并算法
//      注意hi一定要设计成 哨兵 即为数组边界 否则 不好完成合并操作  即数组[lo,hi)左开右闭
      int auxLen = mi - lo;
      for (int i = lo; i < mi; i++) {
          aux[i - lo] = x[i];
      }
//          是下面一个for的逻辑判断精简版  依靠的是  辅助的数组有效长度auxLen遍历完 即i >= auxLen后 
//       * 不管j位置如何 因为j是指示原数组x的索引  所以可以不用在做原数组x[k++] = x[j++]的操作 就已经使合并完成的
        for (int i = 0, j = mi, k = lo; i < auxLen;) {
          if (j < hi && x[j] <= aux[i] ) x[k++] = x[j++];
          if (j >= hi || aux[i] < x[j] ) x[k++] = aux[i++];
      }
      /*for (int i = 0, j = mi, k = lo; i < auxLen || j < hi;) {
          if (j < hi && (i >= auxLen || x[j] <= aux[i])) x[k++] = x[j++];
          if (i < auxLen && (j >= hi || aux[i] < x[j] )) x[k++] = aux[i++];
      }*/
  }
    
//    由问题：计算数组中逆序对的个数  引出的   从右往左由大到小的  反向的 二路归并
    public static void mergeXXX(int[] x, int[] aux, int lo, int mi, int hi) {
//      注意hi一定要设计成 哨兵 即为数组边界 否则 不好完成合并操作  即数组[lo,hi)左开右闭
      //对于左右两个有序子序列a[mid-1]小与等于a[mid] 则数组[lo,hi)就已经是有序的了 可以跳过merge归并操作
        if (!less(x[mi],x[mi - 1])) return;
      int auxLen = hi - mi;
      for (int i = mi; i < hi; i++) {//把序列[lo,hi)的后半部分复制到辅助数组aux中
          aux[i - mi] = x[i];
      }
//      改变标准二路归并从左往右 从小到大的合并排序 本方法 用 从大到小 从右往左 的进行合并排序
      for (int i = auxLen - 1, j = mi - 1, k = hi - 1; i >= 0 || j >= lo;) {
          if (j >= lo && (i < 0 || x[j] >= aux[i]))  x[k--] = x[j--];  
          if (i >= 0 && (j < lo || aux[i] > x[j])) x[k--] = aux[i--];
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
