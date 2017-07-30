package priority.queue.definition;

import java.util.NoSuchElementException;

/**
 * 无序数组实现的 优先级队列(MaxPQ)
 * 自动调整数组长度 缩容和扩容
 * @author wjs13
 *所有自己写的
 *后来改进: 将最大元素和边界元素交换后再删除 
 */
public class UnorderedArrayMaxPQ<T extends Comparable<T>> {
    
    private T[] array ;//底层数组 可以通过resize扩容
    private int n ;//PQ优先级队列中的元素的个数
    public void insert(T key){//插入元素到PQ 采用无序数组实现
        array[n++] = key;
        if (array.length == n) resize(2 * n);//数组扩容
    }
    public T delMax(){//找到MaxPQ并删除最大元素    采用无序数组实现
//  好的实现  应该是将最大元素和边界元素(PQ队尾)交换后再删除队尾即可  下次要插入从队尾插入就行  且PQ队列中不会有null
//        自己开始写的版本就是把max处值置null没有先交换到PQ队尾  导致PQ队列中因为delMax而有null 增加处理难度
        if (n == 0) throw new NoSuchElementException("MaxPQ is underflow!");//下溢异常处理 增加健壮性
        int max = getMaxIndex();
        exch(array, max, n - 1);
        T maxKey = (T) array[n - 1];//交换后的PQ队尾就是Max 需要删除
        array[n - 1] = null;//防止内存泄漏
        n--;
        if (n == array.length / 4) resize(array.length / 2);//缩容
        return maxKey;
    }
    public T getMax(){//找到MaxPQ最大元素   采用无序数组实现
        int max = getMaxIndex();
        return (T) array[max];
    }
    private int getMaxIndex(){ //找到MaxPQ中最大元素在数组中的索引   采用无序数组实现  查找过程类似选择排序的内循环代码
        int max = 0;
        for (int i = 0; i < n ; i++){
            if (less(array[max], array[i]))
                max = i;
        }
        return max;
    }
    private void exch(Comparable[] a, int i, int j){//交换元素 抽取出的通用方法
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    private boolean less(Comparable key1, Comparable key2){
        return key1.compareTo(key2) < 0;
    }
    private void resize(int capacity){
        T[] temp = (T[]) new Comparable[capacity];
        for (int i = 0; i < n; i++){
            temp[i] = array[i];
        }
        array = temp;
    }
    
    public UnorderedArrayMaxPQ(){
        array = (T[]) new Comparable[1];
        n = 0;
    }
    public UnorderedArrayMaxPQ(int size){
        array = (T[]) new Comparable[size];
        n = size;
    }
    public UnorderedArrayMaxPQ(T[] a){
        array = a;
        n = a.length;
    }
    public boolean isEmpty(){
        return n == 0;
    }
    public int size(){
        return n;
    }
}
