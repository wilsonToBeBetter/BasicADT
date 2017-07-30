package string.definition;
/**
 * 快速3向高位优先字符串排序
 * 普通快速排序Quick 和 高位优先的字符串排序MSD  的结合
 * 是基于比较式的快速排序策略   只是用了字符串比较高位优先的思想   但并没有用基于非比较排序的桶/计数排序  注意
 * 可以很好的处理所有高位优先字符串排序MSD不善长的各种情况  如非随机输入、等值键、有较长公共前缀的键、取值范围较小、和小数组(用插入排序改进)
 * 且不需要额外的辅助空间  这种切分方法能够适应键的不同部分的不同结构
 * 且不会因为要排序的字符串长度不均匀 而产生许多的空数组 也不会进行空数组的重复排序  空数组就按前一个首字母的顺序决定顺序
 * @author wjs13
 *
 */
public class Quick3string {
    
    public static void sort(String[] a){
        sort(a, 0, a.length-1, 0);//从0开始到n-1 以第0个字符作为键 进行排序   即高位优先 从左到右进行排序
     }
    
  //用的是基于比较式的快速排序策略   只是用了字符串比较高位优先的思想   但并没有用基于非比较排序的桶/计数排序  注意
    private static void sort(String[] a, int lo, int hi, int d){
//        基于普通二切分的快速排序的思想  要注意 相同值的索引处理
//        且已考虑了 字符为空 即v候选轴点值为-1的情况
        if (hi <= lo) return;//递归基 每个字符串子数组到达串末尾 v为-1 则下一次lo就已经等于hi
        int lt = lo, gt = hi;//lt为小于轴点的右边界索引  gt为大于轴点的左边界的索引
        int v = charAt(a[lo], d);//候选轴点值v 即切分字符 以待排序序列的第一个作为候选轴点
        int i = lo + 1;//左扫描指针  从候选轴点的下一个 开始扫描
        while (i <= gt){
            int t = charAt(a[i], d);//左扫描处的需要做比较的值t   v为候选轴点的值
            if (t < v) exch(a, lt++, i++);//根据比较大小结果  交换元素位置 改变lt gt i的索引
            else if (t > v) exch(a, i, gt--);
            else  i++;
        }
        //while循环结束后 结果为:   
//        a[lo...lt-1]< v = a[lt..gt] < a[gt+1...hi] 即切分成三向子字符串数组 然后递归的进行快速排序
      //含有所有首字母小于切分字符的字符串子数组 
        sort(a, lo, lt - 1, d);//左子序列递归排序
//        一个含有所有首字母等于切分字符的字符串的子数组 在下一次递归排序时 要忽略掉本次相等的首字母 即下一次d要自增1 ++d(注意不是d++ 要先d+1)
//       到达字符串末尾 则可以免去递归的排序 空子字符串数组
        if (v >= 0) sort(a, lt, gt, d+1);//候选轴点v为-1的 字符串子数组 不需要进行递归排序
//        含有所有首字母大于切分字符的字符串子数组 
        sort(a, gt + 1, hi, d);//右子序列递归排序
    }
    private static int charAt(String s, int d){
        if (d >= s.length())
            return -1;//且返回-1 在进行排序比较时 也可表示 空字符应该排在其他任意非空字符之前
        if (d < 0)
            throw new IllegalArgumentException();
        return s.charAt(d) ;
    }
    private static void exch(String[] a, int i, int j){//交换元素 抽取出的通用方法
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
