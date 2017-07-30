package sort.definition;
/**
 * 希尔排序    不同步长序列对应不同的Shell排序算法    Shell排序是一类算法
 * 技术要点:1、矩阵的重排列 借助一维数组即可完成    2、矩阵的每列用 输入敏感的初级排序算法  ——插入排序    
 * 3、步长序列的选择   决定了W-sorting每列子序列的局部有序性 也决定了W-sorting的效率
 * @author wjs13
 *本例 用的步长序列为   h = w(k) = (3 ^ k - 1) / 2  从 n / 3开始递减至 1
 */
public class Shell {
    
    public static void sort(Comparable[] a){//a[]按非降排序
        int n = a.length;//待排序列长度
        int h = 1;
//        步长序列为   h = w(k) = (3 ^ k - 1) / 2 
        while (h < n / 3) h = 3 * h + 1; //1, 4, 13, 40, 121, 364......
        while (h >= 1){
//        int k = (int) Math.pow((2 * n + 1), 1.0 / 3.0); //步长序列的个数
//        for (int p = k; p >=1; p--){   //自己这样计算步长序列的方法也是可以的
//          int h = ((int)(Math.pow(3, p) - 1) / 2); //w(k)值 从h递减到1
//            将数组从h-sorting用插入排序算法变换成h-ordered  
//            不考虑按列做插入排序 直接从a[i=h]开始i++改变h相关的内循环条件进行插入排序 也是很好理解的思路
            //以下是插入算法的标准过程
            for (int i = h; i < n; i++){
                //将a[i]插入到a[i-h] a[i-2*h] a[i-3*h]....之中
                for (int j = i; j >=h; j -= h){//注意此处循环条件j >=h
                    if (less(a[j], a[j - h])){
                        exch(a, j - h, j); 
                     }
                }
            }
            h = h / 3;
            /*for (int p = 0; p < h; p++){//开始自己写的一个 垃圾版本  思考方式复杂一点   分列进行了排序 优化的标准版本参考上面
                //对矩阵的每列  进行插入排序  p即可代表列数
                for (int j = p; j < n; j += h){
                    if ((j + h) < n)
                    for (int m = j + h; m >= h; m -= h){
                        if (less(a[m], a[m - h])){
                           exch(a, m - h, m); 
                        }
                    }
                }
            }*/
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
