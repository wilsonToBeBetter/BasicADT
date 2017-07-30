package priority.queue.test;

import priority.queue.definition.MaxPQ;

public class MaxPQTest {
    public static void main(String[] args) {
        MaxPQ<Integer> maxPQ = new MaxPQ<Integer>();
        maxPQ.insert(1);
        maxPQ.insert(2);
        maxPQ.insert(10);
        maxPQ.insert(4);
        maxPQ.insert(5);
        maxPQ.insert(6);
        maxPQ.insert(7);
        maxPQ.insert(8);
        maxPQ.insert(9);
        maxPQ.insert(10);
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        System.out.println(maxPQ.delMax());
        Integer[] a = new Integer[]{1,2,3,4,5,6,7,8};
        MaxPQ<Integer> maxPQ1 = new MaxPQ<Integer>(a);//批量建堆
        System.out.println(maxPQ1.delMax());
        System.out.println(maxPQ1.delMax());
        System.out.println(maxPQ1.delMax());
        System.out.println(maxPQ1.delMax());
        System.out.println(maxPQ1.delMax());
        System.out.println(maxPQ1.delMax());
        System.out.println(maxPQ1.delMax());
        System.out.println(maxPQ1.delMax());
        
    }

}
