package queue.test;

import queue.definition.Queue;

public class QueueTest {

    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        System.out.println(queue);//Queue的toString()方法用到了foreach
        
    }
}
