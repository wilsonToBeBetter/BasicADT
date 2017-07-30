package string.test;

import java.util.Arrays;

import string.definition.LSD;

public class LSDTest {

    public static void main(String[] args) {
        String[] a = new String[]{"152684","951275","951245","123045","895624","548921","631478","631473"};
        new LSD().sort(a,6);//低位优先 从右到左  定长字符串的排序
        System.out.println(Arrays.toString(a));
//        结果[123045, 152684, 548921, 631472, 631473, 895624, 951245, 951275]
    }
}
