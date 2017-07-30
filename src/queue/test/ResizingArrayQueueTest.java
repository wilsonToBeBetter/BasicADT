package queue.test;

import queue.definition.ResizingArrayQueue;

public class ResizingArrayQueueTest {

    public static void main(String[] args) {
        ResizingArrayQueue<String> resizingArrayQueue = new ResizingArrayQueue<String>();
        resizingArrayQueue.enqueue("a");
        resizingArrayQueue.enqueue("b");
        resizingArrayQueue.enqueue("c");
        resizingArrayQueue.enqueue("d");
        for ( String str : resizingArrayQueue){
            System.out.println(str);
        }
        resizingArrayQueue.dequeue();
        resizingArrayQueue.dequeue();
        resizingArrayQueue.dequeue();//此处队列底层数组缩容了
        resizingArrayQueue.enqueue("e");
        resizingArrayQueue.enqueue("f");//此处扩容了
        resizingArrayQueue.enqueue("g");
        resizingArrayQueue.enqueue("h");
        for ( String str : resizingArrayQueue){
            System.out.println(str);
        }
    }
}
