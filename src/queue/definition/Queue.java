package queue.definition;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 链队列  链表头出队列  链表尾进队列
 * @author wjs13
 *
 * @param <T>
 */
public class Queue<T> implements Iterable<T>{
    
    private Node<T> first;//指向最早添加的结点的结点引用   即队头
    private Node<T> last;//指向最近添加的结点的结点引用      即队尾
    private int n;//队列中元素的数量
    private static class Node<T>{//用静态内部类 可以使结点类在外部类加载的时候就加载 似乎就这个作用 是否加快程序的执行 不知道
        private T data;//数据域
        private Node<T> next;//指针域
    }
    /**
     * Initializes an empty queue.
     */
    public Queue() {
        first = null;
        last  = null;
        n = 0;
    }
    
    public boolean isEmpty(){
        return n == 0;
    }
    public int size(){
        return n;
    }
    public void enqueue(T item){//进队列 从队尾进
        Node<T> oldLast = last;
        last = new Node<T>();
        last.data = item;
        last.next = null;
        if (isEmpty()) first = last;//若是第一个元素进队 则队头和队尾设置同一个引用
        else oldLast.next = last;//若非一个进队的  则直接就进队 队尾元素链接到对中前一个元素(即上一个last的next域)
        n++;
    }
    public T dequeue(){//出队列  从队头出 且删除队头元素
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");//队为空 出队则抛运行时异常 提高程序健壮性 注意鲁棒性的设计
        T item = first.data;
        first = first.next;//如果最后一个元素出队列 则此处first引用也是完成置空的
        if (isEmpty()) last = null;//如果最后一个元素出队列 则last引用也要置空
        n--;
        return item;
    }
    /**出队列  从队头出 但不删除队头元素
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.data;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T item : this) {
            s.append(item);
            s.append(' ');//空格相隔的 queue元素toString()输出
        }
        return s.toString();
    } 
    
    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return new ListIterator<T>();
    }
    
    /**链队列迭代器
     * 因链队列 也是从链表头出队列的  固链队列迭代器实现和链栈迭代器相同
     * @author wjs13
     */
    private class ListIterator<T> implements Iterator<T>{
        
        private Node<T> current = (Node<T>) first;//此处按理说不用强转 但不知为什么 不加就报错  可能编译器认为本内部类的类型T和外部类的类型T不同
//        一定要单独定义一个结点对象 来作为迭代操作中的临时结点 不能直接用first结点
//      否则在迭代中会修改原链栈的栈顶结点first的数据结构的数据  不满足我们的要求   不能在迭代中修改数据结构 
//      此处同顺序栈中 迭代器内部类中引入 栈长度的实例变量是一样的道理  注意 注意！
//      算法第四版中给的全的数据结构代码 用的是ListIterator 构造函数把first传入current
      @Override
      public boolean hasNext() {
          return current != null;
      }
      @Override
      public T next() {
          T item = current.data;
          current = current.next;//此处的操作只会修改迭代器对象的数据不会修改所遍历栈的数据结构 是安全符合要求的
          return item;
      }
      @Override
      public void remove() {
          throw new UnsupportedOperationException();
        //空实现 我们不希望在迭代器中 修改数据结构中的数据  固是空实现 有必要可以 进行健壮性设计 使调用本方法抛出未检查异常    
      }
        
    }
    
}
