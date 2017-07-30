package priority.queue.test;

import priority.queue.definition.OrderedArrayMaxPQ;

public class OrderedArrayMaxPQTest {

    public static void main(String[] args) {
        OrderedArrayMaxPQ<Integer> orderedArrayMaxPQ = new OrderedArrayMaxPQ<Integer>();
        orderedArrayMaxPQ.insert(10);
        orderedArrayMaxPQ.insert(7);
        orderedArrayMaxPQ.insert(3);
        orderedArrayMaxPQ.insert(15);
        orderedArrayMaxPQ.insert(100);
        orderedArrayMaxPQ.insert(9);
        orderedArrayMaxPQ.insert(20);
        System.out.println(orderedArrayMaxPQ.delMax());
        System.out.println(orderedArrayMaxPQ.delMax());
        System.out.println(orderedArrayMaxPQ.delMax());
        System.out.println(orderedArrayMaxPQ.delMax());
        System.out.println(orderedArrayMaxPQ.delMax());
        System.out.println(orderedArrayMaxPQ.delMax());
        System.out.println(orderedArrayMaxPQ.delMax());
//        System.out.println(orderedArrayMaxPQ.delMax());
        Integer[] a = new Integer[]{11,9,18,5,2};
        OrderedArrayMaxPQ<Integer> unordered = new OrderedArrayMaxPQ<Integer>(a);
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
        System.out.println(unordered.delMax());
//        System.out.println(unordered.delMax());
        
    }
}
