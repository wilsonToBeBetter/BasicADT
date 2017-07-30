package bag.definition;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**顺序Bag 
 * 基于数组实现的
 * 支持自动调整底层数组的长度 以满足Bag容量的需求
 * 支持foreach操作  支持泛型 能够存储任意类型数据
 * Bag数据结构
 * 是一种不支持从中删除元素的集合数据类型
 * 它的目的就是帮助用例收集元素并迭代遍历所有收集到的元素、检查Bag是否为空 和Bag的元素数量
 * Bag迭代的顺序可以是不确定的 
 * 但是本Bag设计底层是用数组  且在数组末尾添加新元素 所以底层数据存放是有相对顺序的  但是我们不关心Bag存取元素的顺序
 * 迭代器不支持remove操作
 * @author wjs13
 *
 */
public class ResizingArrayBag<T> implements Iterable<T>{

    private T[] array ;//Bag底层装元素的数组
    private int n;//Bag元素的个数
    
    public ResizingArrayBag(){
        array = (T[]) new Object[1];//初始化Bag
        n = 0;
    }
    public boolean isEmpty(){
        return n == 0;
    }
    public int size(){
        return n;
    }
    private void resize(int capacity){//底层数组支持改变容量   这种方法对于 大数组是很低效的  以后优化的方向是 用链表实现
        T[] temp = (T[]) new Object[capacity];
        for (int i = 0; i < n; i++){//注意此处只要复制Bag中的元素即n个元素 非数组array.length个元素
          //注意此处 扩容只复制有效的Bag元素
            temp[i] = array[i];
        }
        array = temp;   
    }
    public void add(T item){//从数组末尾添加元素  则添加元素的索引刚好就是 元素的个数n
        if (n == array.length) resize(2 * array.length);//上溢 则扩容底层数组 扩2倍
        array[n++] = item;
    }
    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return new BagIterator();
    }
    
    /**
     * 顺序Bag 迭代器  
     * 支持按放入的顺序遍历Bag 虽然Bag数据结构的特性不关心放入和取出元素的顺序
     * @author wjs13
     *
     * @param <T>
     */
    private class BagIterator<T> implements Iterator<T>{
        
        int length = n;
        int tempLength = length;
        @Override
        public boolean hasNext() {
            return length > 0;
        }
        @Override
        public T next() {
            if (isEmpty()) throw new NoSuchElementException("Bag is underflow!");
            return (T) array[tempLength - (length--)];//从数组的头开始取出Bag中元素
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
            
        }
    }
    
    
    
}
