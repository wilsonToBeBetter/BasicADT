package string.definition;
/**
 * LSD
 * 低位优先的字符串排序    本方法更加适合于定长字符串排序 
 * 是一种适用于一般应用的 线性时间 排序算法 
 *  低位优先 从右向左的位置依次对所有多组字符串进行排序
 * 基于 桶/计数 排序     是一种非比较式的排序算法策略
 * @author wjs13
 *运行时间与输入规模成正比
 */
public class LSD {
    
//    低位优先  从右往左 依次以每个位置的字符为键进行计数排序一次    一共计数排序w次  就可以可到从高位到低位的字符串自然有序
    //高位优先 不能用本方法  否则  结果的字符串数组从高位到低位不是有序的
    public static void sort(String[] a, int w){
//        w表示定长字符串的长度
        int n = a.length;//待排字符串的个数
        int r = 256;//表示键值的取值范围  这里 就是字符串中每个字符的取值范围  我们约定字符是扩展ascii码 最多有256中字符
      // 使aux只被初始化一次 节省效率 放到for循环外 也是可行的 注意理解
        String[] aux = new String[n];  //开始是写到下面的一个for循环中的
        for (int i = w - 1; i >= 0; i--){
            int[] count = new int[r];
            int[] accum = new int[r + 1];
            
            for (int j = 0; j < n; j++){
                //扩展ascii码 字符int就是0-255对应 count索引0-255
                count[a[j].charAt(i)]++;//字符i在字母表中的索引就是count中的索引 即字母表中第一个字符计数表示是count[0]
            }
            accum[0] = 0;
            for (int j = 0; j < r; j++){
                accum[j+1] = accum[j] + count[j];
            }
            for (int j = 0; j < n; j++){
                //accum有效值从accum[0]开始 accum[1]其值表示排在最前面的字符累计个数 字符在有序数组中索引是从[accum[0]开始到accum[1])
                aux[accum[a[j].charAt(i)]++] = a[j];
                //排序完成 则下次相同的key值在有序数组中的索引应该加1  固accum中的值需要自增1
            }
            for (int j = 0; j < n; j++){
                a[j] = aux[j];
            }
        }
    }
}
