package stack.definition;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 链栈   链表头进栈 链表头出栈
 * 内部定义了链表的结点类Node
 * @author wjs13
 *
 * @param <T>
 */
public class Stack<T> implements Iterable<T>{
    private Node<T> first ;//栈顶
    private int n;//元素数量
    
    private static class Node<T>{//链表的结点类Node //用静态内部类 可以使结点类在外部类加载的时候就加载 似乎就这个作用 是否加快程序的执行 不知道
        private T data;//链表结点的 数据域
        private Node<T> next;//链表结点的 指针域
    }
    
    /**
     * Initializes an empty stack.
     */
    public Stack() {
        first = null;
        n = 0;
    }
    
    public boolean isEmpty(){
        return first == null;
    }
    public int size(){
        return n;
    }
    public void push(T item){//向栈顶添加新元素
        Node<T> oldFirst = first;
        first = new Node<T>();//栈顶更新
        first.data = item;
        first.next = oldFirst;
        n++;//栈中元素数量+1
    }
    public T pop(){//弹出和删除栈顶元素 
        if (isEmpty()) new NoSuchElementException("Stack underflow");
        T item = first.data;
        first = first.next;
        n--;
        return item;
    }
    
    /**弹出但不删除栈顶元素 
     * Returns (but does not remove) the item most recently added to this stack.
     *
     * @return the item most recently added to this stack
     * @throws NoSuchElementException if this stack is empty
     */
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.data;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T item : this) {//内部已经实现了迭代器
            s.append(item);
            s.append(' ');//空格相隔的 stack元素toString()输出
        }
        return s.toString();
    }
    
    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return new ListIterator<T>();
    }
    
    /**
     * 链栈   
     * 迭代器 next即是从链表头出栈
     * 
     * @author wjs13
     *
     */
    private class ListIterator<T> implements Iterator<T>{
        
        private Node<T> current = (Node<T>) first;//此处按理说不用强转 但不知为什么 不加就报错   可能编译器认为本内部类的类型T和外部类的类型T不同
//        一定要单独定义一个结点对象 来作为迭代操作中的临时结点 不能直接用first结点
//        否则在迭代中会修改原链栈的栈顶结点first的数据结构的数据  不满足我们的要求   不能在迭代中修改数据结构 
//        此处同顺序栈中 迭代器内部类中引入 栈长度的实例变量是一样的道理  注意 注意！
//        算法第四版中给的全的数据结构代码 用的是ListIterator 构造函数把first传入current
        
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public T next() {//注意检查异常 是程序更加健壮
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.data;
            current = current.next;//此处的操作只会修改迭代器对象的数据不会修改所遍历栈的数据结构 是安全符合要求的
            return item;
        }
        @Override
        public void remove() {
            //空实现 我们不希望在迭代器中 修改数据结构中的数据  固是空实现 有必要可以 进行健壮性设计 使调用本方法抛出未检查异常    
            throw new UnsupportedOperationException();
        }
        
    }
    
    
    
}
