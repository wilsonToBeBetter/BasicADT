package sort.definition;
/**
 * 快速排序  ——分治策略典型应用
 * 技术   1—将序列划分为左右两个子序列sL和sR 且max(sL)<=min(sR)  即左小右大
 * 2—再在子序列分别递归的排序之后 原序列自然有序
 * quick计算量和难点在与 "分"  meger在于"合”
 * quick核心： 快速划分轴点          轴点：左/右侧元素 均不比它更大/小   且轴点必然就位(即其秩就是有序序列中的秩，但就位不一定就是轴点 注意轴点的定义)
 * 以轴点为界 原序列的划分自然实现了  即完成了快速排序的技术1 再交给递归就可完成整体排序
 * 快速排序:就是将所有元素逐个转换为轴点的过程   通过适当的交换可以使任意元素转换为轴点
 * @author wjs13
 *综合对比 partitionX版本性能最好  其次partitionXX
 *本类中三个partition都是双向切分
 *JDK中排序用的是  快速排序的三向切分  即用的是经过精心调优和设计的快速排序
 */
public class Quick {
    
    private Quick(){}
    
    public static void sort(Comparable[] a){
        int len = a.length;
        sort(a, 0, len);
    }
    
    private static void sort(Comparable[] a, int lo, int hi){//a[]按非降排序
//       注意  hi是一个边界的哨兵 
      if (hi - lo < 2) return;
      int mi = partitionX(a , lo, hi - 1);//快排所划分出的轴点mi
      sort(a, lo, mi);//左子序列递归排序
      sort(a, mi + 1, hi);//右子序列递归排序
    }
    
    //这是算法 第四版 上的轴点构造  方法很简单  但是不好理解  自己写的好理解一点 但是执行步骤有待改进 性能较本方法低一些
    private static  int partitionX(Comparable[] a, int lo, int hi) { //双向切分 
        //总是假设lo为候选轴点   整个partition的过程就是通过移动元素 找到候选轴点m就位的秩rank 即a[rank]=m
        Comparable m = a[lo];//候选轴点的值
        int i = lo; int j = hi + 1;//左右扫描指针
        while (true){
            //扫描左右 检查扫描是否结束并交换元素
            while (less(a[++i], m)) if (i == hi) break;
            while (less(m, a[--j])) if (j == lo) break;
             if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);//将轴点值m放入找到的轴点秩为j处   即就位
        return j;
    }
   
    
    
//    自己写的partition的加强 改进版本 主要改进了 lo和hi空闲状态判定和转换空闲的方式  使程序代码更少 性能更高
    private static  int partitionXx(Comparable[] a, int lo, int hi) {  //双向切分
//        注意 本方法实现没有用到元素的交换 只是利用空闲位置来进行元素的赋值 并不是交换元素
        Comparable m = a[lo];//候选轴点的值  
        //本改进 不需要定义和判断lo和hi的状态标识  此版本在此处没有用多余的其他局部变量 栈空间复杂度相对版本partitionX较好点吧
        while(lo != hi){ 
            while (lo != hi ){//开始版本 控制循环条件加在while的{}内用if (hi == lo) break;  后改进为现在版本
                if (less(a[hi], m)){
                    a[lo++] = a[hi];
                    //此处是改进的重要地方 也大幅提高了 本partition的效率
                    break;//因为一旦此if条件满足 执行了交换元素值 那么就会发生一次lo和hi的空闲交替 
//                    那么直接就可以break去执行下一个while的非空闲的lo    可以对比自己写的原始版本进行理解
                }
                else  hi--;//此处可能再改进就是《算法》中的版本即本类中的partitionX版本
                }
            while ( lo != hi){//开始版本 控制循环条件加在while内用if (hi == lo) break;  后改进为现在版本
                if (less(m, a[lo])){
                    a[hi--] = a[lo];
                  //此处是改进的重要地方 也大幅提高了 本partition的效率
                    break;//因为一旦此if条件满足 执行了交换元素值 那么就会发生一次lo和hi的空闲交替 
//                    那么直接就可以break去执行下一个while的非空闲的hi  可以对比自己写的原始版本进行理解
                }
                else  lo++;//此处可能再改进就是《算法》中的版本即本类中的partitionX版本
                }
        }
        a[lo] = m;//轴点就位 且轴点左边的数不大于轴点 轴点右边的数不小于轴点
        return lo;
    }
    
    
    /**
     * 轴点mi构造算法   自己写的原始版本的代码  上面的partitionXX  是本版本的改进版  可能把性能提高了50%-100%
     * 没看任何的源码  完全是自己写的 只是参考了 霍尔爵士快排轴点构造的图形过程思路 
     * @param a
     * @param lo
     * @param hi   注意秩hi不能越界 并不是一个边界的哨兵
     * @return
     */
    private static  int partition(Comparable[] a, int lo, int hi) {  //双向切分
        //总是假设lo为候选轴点   整个partition的过程就是通过移动元素 找到候选轴点m就位的秩rank 即a[rank]=m   且轴点左边的数不大于轴点 轴点右边的数不小于轴点
//        注意 本方法实现没有用到元素的交换 只是利用空闲位置来进行元素的赋值 并不是交换元素
        Comparable m = a[lo];//候选轴点的值
//        参考了邓俊辉 讲的快排的 霍尔爵士 轴点构造思路
        boolean lo_flag = true;//lo空闲标识    true为处于空闲状态
        boolean hi_flag = false;//hi空闲标识    false为处于非空闲状态
        while(lo != hi){ 
            while (lo_flag && hi != lo ){//开始版本 控制循环条件加在while的{}内用if (hi == lo) break;  后改进为现在版本
                if (less(a[hi], m)){
                    a[lo] = a[hi];//注意a[hi]的值移动过去后 是已经在本次比较过的  所以有下一句的lo++
                    lo++;//需要交替空闲状态  则通过lo++ 则下一次新的非空闲状态就可以跳过本次已经比较过的a[hi]和m
                    lo_flag = false;//lo置为非空闲状态
                    hi_flag = true;//hi置为空闲状态
                }
                else  hi--;//移动hi为下一个非空闲状态进行和m的比较
                }
            while (hi_flag && hi != lo){//开始版本 控制循环条件加在while内用if (hi == lo) break;  后改进为现在版本
                if (less(m, a[lo])){
                    a[hi] = a[lo];//注意a[lo]的值移动过去后 是已经在本次比较过的  所以有下一句的hi--
                    hi--;//需要交替空闲状态  则通过hi-- 则下一次新的非空闲状态就可以跳过本次已经比较过的a[lo]和m
                    hi_flag = false;//hi置为非空闲状态
                    lo_flag = true;//lo置为空闲状态
                }
                else  lo++;//移动lo为下一个非空闲状态进行和m的比较
                }
        }
        a[lo] = m;
        return lo;
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
