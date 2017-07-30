package queue.definition;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 顺序队列   一般顺序队列 非循环队列
 * 底层数组实现 
 * 支持自动调整数组长度
 * 支持foreach操作  支持泛型 能够存储任意类型数据
 * 迭代器不支持remove操作
 * @author wjs13
 *
 */
public class ResizingArrayQueue<T> implements Iterable<T>{

    private T[] array;//顺序队列底层数组
  //本设计的ArrayQueue 是一般的顺序队列  不是循环队列   固first在数组中的索引只能增加 
    private int first;//指示队列头索引
    //本设计的ArrayQueue 是一般的顺序队列  不是循环队列   固last在数组中的索引只能增加 
    private int last;//指示队列尾索引  且始终是队尾哨兵 即为下一个进队的数组索引 array[last]  
    private int n;//队列的长度
  
    public ResizingArrayQueue(){
        array = (T[]) new Object[1];
        first = 0;
        last = 0;
        n = 0;
    }
    public boolean isEmpty(){
        return n == 0;
    }
    public int size(){
        return n;
    }
    private void resize(int capacity){////底层数组支持改变容量   这种方法对于 大数组是很低效的  以后优化的方向是 用链表实现
        T[] temp = (T[]) new Object[capacity];
        for (int i = 0; i < n; i++){
            temp[i] = array[i + first];//注意此处 扩容只复制有效的队列元素 即只复制有效的array的[first,last)索引
        }
        array = temp;
        first = 0;//且注意此处  扩容后 因只复制有效的[first,last)索引的数组值 固新的array指示队头first和队尾last的索引要修改为[0-n)
        last = n;//这两个指示first和last位置的调整 很关键
    }
    public void enqueue(T item){//入队 从队尾指示last处入队
        if (isEmpty()) {//入队前 队列为空 则
            last = 0;
            first = 0;
        }
//        队尾指示last已经越界 则要对底层数组扩容  且resize函数已经从新调整队列在数组中的位置  且对数组容量增加一倍
        if (last == array.length) resize(2 * n );//注意实际扩容只复制了有效的索引为[first,last)的数据 固新数组应是扩容到队列长度的2倍
        array[last++] = item;//是一般的顺序队列  不是循环队列   固last在数组中的索引只能增加
        n++;
    }
    public T dequeue(){//出队 从队头指示first处出队
        if (isEmpty()) throw new NoSuchElementException("Queue is underflow!");//队列为空 无法出队
        T item = array[first];
        array[first] = null;//防止原数组所引用的对象发生内存泄漏   此步是有必要的  需要注意 这对一个程序的健壮性 鲁棒性是有影响的
        first++; //是一般的顺序队列  不是循环队列   固first在数组中的索引只能增加 
        n--;
        if (isEmpty())//出队后若队列为空 则 last和first置0 表示下一次入队从数组头开始
        {
            last = 0;
            first = 0; 
        }
        if (n == array.length / 4) //若出队后 队列中元素只有数组总长度的四分之一  且resize函数已经从新调整队列在数组中的位置 且对数组容量减半
            resize(array.length / 2);//注意缩容是底层数组长度减半  而扩容是队列长度增1倍作为数组新长度 这两策略不同
        return item;
    }
    
    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return new QueueIterator<T>();
    }
    /**
     * 顺序队列的迭代器
     * FIFO特性
     * @author wjs13
     *
     * @param <T>
     */
    private class QueueIterator<T> implements Iterator<T>{
        
        int queueHead = first;//复制一个队列头指针  使迭代器的操作不会改变队列对象的队列头指针first
        int length = n;//复制一个队列长度  使迭代器的操作不会改变队列对象的队列长度
        @Override
        public boolean hasNext() {
            return length > 0;
        }

        @Override
        public T next() {//FIFO迭代 即一直输出first指示的数组索引的值
            if (isEmpty()) throw new NoSuchElementException("Queue is underflow!");
            T item = (T) array[queueHead++];
            length--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
}
