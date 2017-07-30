package priority.queue.definition;

import java.util.NoSuchElementException;

import sort.definition.Quick;

/**
 * 有序数组实现的    优先级队列(MaxPQ)
 * insert实现中  把所有较大的元素向右边移动一格以使数组保持有序  和插入排序思想类似
 * @author wjs13
 * 注意 构造函数public OrderedArrayMaxPQ(T[] a)实现 要把传进来的数组先排序  才能正确执行PQ相关的操作
 *
 */
public class OrderedArrayMaxPQ<T extends Comparable<T>> {
    private T[] array ;//底层数组 可以通过resize扩容
    private int n ;//PQ优先级队列中的元素的个数
    public void insert(T key){//插入元素到PQ 
        array[n++] = key;
        for ( int i = 0; i < n; i++){//始终保证最大的数在队列的队尾  即队列中所有元素是整体有序的 按非降排序
            if (less(array[n - 1], array[i]))
                exch(array, n - 1, i);
        }
        if (array.length == n) resize(2 * n);//数组扩容
    }
    public T delMax(){//找到MaxPQ并删除最大元素    采用有序数组实现很容易  直接弹出队列队尾array[n-1]元素即可
        if (n == 0) throw new NoSuchElementException("MaxPQ is underflow!");//下溢异常处理 增加健壮性
        T maxKey = (T) array[n - 1];//队尾 即为MaxPQ中最大的数
        array[n - 1] = null;//防止内存泄漏
        n--;
        if (n == array.length / 4) resize(array.length / 2);//缩容
        return maxKey;
    }
    public T getMax(){//找到MaxPQ最大元素   采用有序数组实现   直接弹出队列队尾array[n-1]元素即可
        return (T) array[n - 1];
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
    public OrderedArrayMaxPQ(){
        array = (T[]) new Comparable[1];
        n = 0;
    }
    public OrderedArrayMaxPQ(int size){
        array = (T[]) new Comparable[size];
        n = 0;
    }
    public OrderedArrayMaxPQ(T[] a){
        array = a;
        n = a.length;
//        因为MaxPQ的按优先级插入和删除都是基于 有序数组 实现的  固这里需要对数组a排序 可以用快速排序
        Quick.sort(array);
    }
    public boolean isEmpty(){
        return n == 0;
    }
    public int size(){
        return n;
    }
}
