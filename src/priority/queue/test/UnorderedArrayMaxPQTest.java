package priority.queue.test;

import priority.queue.definition.UnorderedArrayMaxPQ;

public class UnorderedArrayMaxPQTest {

    public static void main(String[] args) {
        UnorderedArrayMaxPQ<Integer> unorderedArrayMaxPQ = new UnorderedArrayMaxPQ<Integer>();
        unorderedArrayMaxPQ.insert(10);
        unorderedArrayMaxPQ.insert(7);
        unorderedArrayMaxPQ.insert(3);
        unorderedArrayMaxPQ.insert(15);
        unorderedArrayMaxPQ.insert(11);
        unorderedArrayMaxPQ.insert(9);
        unorderedArrayMaxPQ.insert(20);
        System.out.println(unorderedArrayMaxPQ.delMax());
        System.out.println(unorderedArrayMaxPQ.delMax());
        System.out.println(unorderedArrayMaxPQ.delMax());
        System.out.println(unorderedArrayMaxPQ.delMax());
        System.out.println(unorderedArrayMaxPQ.delMax());
        System.out.println(unorderedArrayMaxPQ.delMax());
        System.out.println(unorderedArrayMaxPQ.delMax());
//        System.out.println(unorderedArrayMaxPQ.delMax());
        Integer[] a = new Integer[]{11,9,18,5,2};
        UnorderedArrayMaxPQ<Integer> unordered = new UnorderedArrayMaxPQ<Integer>(a);
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
    }
}
