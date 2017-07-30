package bag.definition;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**链Bag 从链表头add元素 不支持删除元素
 * Bag数据结构
 * 是一种不支持从中删除元素的集合数据类型
 * 它的目的就是帮助用例收集元素并迭代遍历所有收集到的元素、检查Bag是否为空 和Bag的元素数量
 * Bag迭代的顺序可以是不确定的 
 * 但是本Bag设计底层是用链表 且在表头添加新元素 所以底层数据存放是有相对顺序的  但是我们不关心Bag存取元素的顺序
 * @author wjs13
 *
 * @param <T>
 */
public class Bag<T> implements Iterable<T>{
    
    private Node<T> first;//指示链表的头结点
    private int n;//元素的数量
    private static class Node<T>{//链表节点类   //用静态内部类 可以使结点类在外部类加载的时候就加载 似乎就这个作用 是否加快程序的执行 不知道
       private T data;
       private Node<T> next;
    }
    /**
     * Initializes an empty bag.
     */
    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty(){
        return n == 0;
    }
    public int size(){
        return n;
    }
    public void add(T item){//向Bag中添加新元素
        Node<T> oldFirst = first;
        first = new Node<T>();
        first.data = item;
        first.next = oldFirst;
        n++;
    }
    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return new ListIterator<T>();
    }
    /**
     * Bag 迭代器    next即是从链表头出Bag  同Stack和Queue的迭代器实现都是一样的 
     * 因为他们的底层都是用的链表
     * 
     * @author wjs13
     *
     */
    private class ListIterator<T> implements Iterator<T>{
        
        private Node<T> current = (Node<T>) first;//一定要单独定义一个结点对象 来作为迭代操作中的临时结点 不能直接用first结点
//        否则在迭代中会修改原链栈的栈顶结点first的数据结构的数据  不满足我们的要求   不能在迭代中修改数据结构 
//        此处同顺序栈中 迭代器内部类中引入 栈长度的实例变量是一样的道理  注意 注意！
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.data;
            current = current.next;//此处的操作只会修改迭代器对象的数据不会修改所遍历Bag的数据结构 是安全符合要求的
            return item;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
          //空实现 我们不希望在迭代器中 修改数据结构中的数据  固是空实现 有必要可以 进行健壮性设计 使调用本方法抛出未检查异常    
        }
        
    }
}
