package string.definition;

import sort.definition.Insertion;

/**
 * 高位优先的字符串排序 算法     高效且可用的版本  可以处理大型的字符串数组 且在处理小型的数组时也是高效的
 * 字符串数组排序 先用基于 桶/计数排序   快速不断递归切分到小型子数组 再用插入排序
 * 更加通用(可用于不等长的字符串排序)的字符串排序算法
 *  桶/计数排序    是一种非比较式的排序算法策略
 * @author wjs13
 *注意charAt(a[i],d) 在字符串到达末尾时 即d大于数组索引  返回-1   
 *要使count[charAt(a[i],d)] 数组不会为count[-1] 需要把count和accum数组扩大一个长度 用来放表示字符串末尾 且应该排在最前面的字符串
 *
 *改进版: 参考《算法》 第四版 P465  添加一个用以处理小型子数组 的插入排序 
 *小数组对与高位优先的字符串排序的影响尤其强烈
 *将小数组切换到插入排序对于高位优先的字符串排序算法是必须的
 *递归中 小型子字符串数组排序用插入算法
 *
 *不擅长: 非随机输入、等值键、有较长公共前缀的键、取值范围较小、和小数组(用插入排序改进)
 */
public class MSDX {   
    
    private static int r = 256;//表示键值的取值范围  这里 就是字符串中每个字符的取值范围  我们约定字符是扩展ascii码 最多有256中字符
    private static final int M = 15;//小数组的切换阈值
    private static String[] aux;//辅助数组 用来暂存排序结果
    public static void sort(String[] a){
       int n = a.length;//待排序字符串的个数
       aux = new String[n];
       sort(a, 0, n-1, 0);//从0开始到n-1 以第0个字符作为键 进行排序   即高位优先 从左到右进行排序
    }
    
    //以下部分的桶/计数排序思路 可以参考LSD 低位优先 或者桶排序
    private static void sort(String[] a, int lo, int hi, int d){
        
        if (hi <= lo + M) {
            Insertion.sort(a, lo, hi, d);return;
        }
        if (d >= 6) return;//之前一直没有添加递归基 导致 程序栈溢出  递归基 为 d大于最大的字符串的长度
        //以第d个字符为键将a[lo]至a[hi]组字符串排序
        //基于 桶/计数排序
            int[] count = new int[r + 1];
            int[] accum = new int[r + 1 + 1];
            for (int i = lo; i <= hi; i++){//统计频率
                //注意charAt(a[i],d) 在字符串到达末尾时 即d大于数组索引  返回-1  则要统一的把所有charAt值+1 作为索引存到count数组
                //因charAt遇到字符串末尾会返回-1  则count[0]不是表示字母表第一个字符  而是表示字符串结尾
                count[charAt(a[i],d) + 1]++;//count[0]值表示字符串末尾空位  空位在字符串排序中应该排在最前面
            }
            accum[0] = 0;//accum[]从1开始到r+1代表 字符串末尾 和 255种字母表中的字符
            for (int i = 0; i < r + 1; i++){//将频率转换为索引
              //accum有效值从accum[0]开始  accum[1]其值表示排在最前面的字符累计个数 字符在有序数组中索引为[accum[0],accum[1])
              //accum[1]表示字符串长度小于d的字符串 其应该排在最前面
                accum[i + 1] = accum[i] + count[i];
            }
            for (int i = lo; i <= hi; i++){//数据分类
              //accum[1]表示字符串长度小于d的字符串 其应该排在最前面
                aux[accum[charAt(a[i], d) + 1]++] = a[i];
              //排序完成 则下次相同的key值在有序数组中的索引应该加1  固accum中的值需要自增1
            }
            for (int i = lo; i <= hi; i++){//回写
                a[i] = aux[i - lo];//此处需要注意 aux[]数组中的值在数据分类中是从索引为0开始写入的
            }
            //递归的以每个字符为键进行排序
            for (int i = 0; i < r + 1; i++){
                //注意accum[]中的值所表示的索引是有序序列数组从索引0开始的  固要加上一个基址lo
                sort(a, lo + accum[i], lo + accum[i+1] - 1, d + 1);//递归的对d下一个字符为键 将a[lo]至a[hi]组字符串排序
            }
            
    }
    
    private static int charAt(String s, int d){
        if (d >= s.length())
            return -1;//且返回-1 在进行比较排序时 也可表示 空字符应该排在其他任意非空字符之前
        if (d < 0)
            throw new IllegalArgumentException();
        return s.charAt(d) ;
    }
    
}
