package priority.queue.test;

import priority.queue.definition.MaxPQ;
import priority.queue.definition.MinPQ;

public class MinPQTest {

    public static void main(String[] args) {
        MinPQ<Integer> minPQ = new MinPQ<Integer>();
        minPQ.insert(10);
        minPQ.insert(5);
        minPQ.insert(8);
        minPQ.insert(4);
        minPQ.insert(7);
        minPQ.insert(3);
        minPQ.insert(6);
        minPQ.insert(4);
        System.out.println(minPQ.delMin());
        System.out.println(minPQ.delMin());
        System.out.println(minPQ.delMin());
        System.out.println(minPQ.delMin());
        System.out.println(minPQ.delMin());
        System.out.println(minPQ.delMin());
        System.out.println(minPQ.delMin());
        System.out.println(minPQ.delMin());
        System.out.println(minPQ.delMin());
//        System.out.println(minPQ.delMin());
//        System.out.println(minPQ.delMax());
        Integer[] a = new Integer[]{8,7,6,5,4,3,2,1};
        MinPQ<Integer> minPQ1 = new MinPQ<Integer>(a);//批量建堆
        System.out.println(minPQ1.delMin());
        System.out.println(minPQ1.delMin());
        System.out.println(minPQ1.delMin());
        System.out.println(minPQ1.delMin());
        System.out.println(minPQ1.delMin());
        System.out.println(minPQ1.delMin());
        System.out.println(minPQ1.delMin());
        System.out.println(minPQ1.delMin());
    }
}
