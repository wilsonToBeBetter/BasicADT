package string.test;

import java.util.Arrays;

import string.definition.MSD;
//排序非定长 数字字符串
public class MSDTest {
    public static void main(String[] args) {
//       MSD高位优先的 基础 垃圾版本 只能稍微高效的排序0-9字母表的字符串  排256字母表 就非常慢 速度可能差了几千倍
//        只能稍微排序0-9字母表  且字符串长度不超过10 的字符串  超出范围 效率将很低下 不可容忍的性能
        String[] a = new String[]{"152684","912","91245","123045","895624","548921","63147","631473","152683"};
//       sort方法 中   之前一直没有添加递归基 导致递归  程序 栈溢出 
        new MSD().sort(a);//高位优先 从左到右  一般(可以不定长)字符串排序
        System.out.println(Arrays.toString(a));
//        结果[123045, 152684, 548921, 63147, 631473, 895624, 912, 91245]
    }
}
