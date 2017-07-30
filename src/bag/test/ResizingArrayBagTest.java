package bag.test;

import bag.definition.ResizingArrayBag;

public class ResizingArrayBagTest {

    public static void main(String[] args) {
        ResizingArrayBag<String> resizingArrayBag = new ResizingArrayBag<String>();
        resizingArrayBag.add("a");
        resizingArrayBag.add("b");
        resizingArrayBag.add("c");
        resizingArrayBag.add("d");
        for (String str : resizingArrayBag){//顺序Bag 实现了Iterable 遍历顺序是按放入元素的顺序
            System.out.println(str);
        }
    }
}
