package stack.definition;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 顺序栈  
 * 能自动调整栈长度 支持foreach操作  支持泛型 能够存储任意类型数据
 * 迭代器不支持remove操作
 * @author wjs13
 *
 */
public class ResizingArrayStack<T> implements Iterable<T>  {

    private T[] array = (T[]) new Object[1];//顺序栈 底层数组 
    //数组的头作为栈底  数组尾作为栈顶
    private int n = 0;//表示栈的长度  即栈中元素的个数   可以用以指示栈顶 
    
    /**将栈移动到一个大小为newCap的新数组
     * 动态调整数组的长度   可以用于扩容两倍或者容量减半
     * @param newCap  参数 新容量
     */
    private void resize(int newCap){//底层数组支持改变容量   这种方法对于 大数组是很低效的  以后优化的方向是 用链表实现
        T[] temp = (T[]) new Object[newCap];
        for (int i = 0; i < n; i++){//注意此处只要复制栈中元素即length个元素 非数组a.length个元素
          //注意此处 扩容只复制有效的栈元素
            temp[i] = array[i];
        }
        array = temp;//数组引用的替换 实现栈对象中底层数组对象的改变  也即容量改变
    }
    public void push(T t) {
        if (n == array.length){
            resize(2 * array.length);//栈满 则底层数组的容量扩大一倍
        }
        array[n++] = t;//新元素压入栈顶
    }
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is underflow!"); //栈为空 无法出栈
        T item = array[--n];//弹出栈顶元素
        array[n] = null;//把原数组a(即改变数组容量前，也即替换成新容量数组前)中的元素值都置null  防止原数组a可能产生的内存泄露并导致元素对象产生内存泄漏
        if (n > 0 && n == array.length / 4){//此时栈元素个数刚好为数组长度的四分之一  则可以减半底层数组的容量 节省容量
            resize(array.length / 2);
        }
        return item;
    }
    public boolean isEmpty() {
        return n == 0;
    }
    public int size() {
        // TODO Auto-generated method stub
        return n;
    }
    public Iterator<T> iterator() {
        return new ReverseArrayIterator<T>() ;//新建迭代器内部类对象
    }
    
    /**内部类
     * 反向遍历序列(数组)的迭代器
     * 可以用于实现栈的LOFO 结构
     * @author wjs13
     *
     * @param <T>
     */
    class ReverseArrayIterator<T> implements Iterator<T>{//支持后进先出的迭代
        
        private int i = n;//此处 必须添加一个实例变量 且等于当前栈对象的length长 则就可以保证本迭代操作不会改变栈的长度length
//        否则 在后面的next()中用a[--length] 就改变了栈顶的位置 一次next就相当于执行了一次pop 这不是我们需要的迭代操作
//        因为本数据结构的迭代器 我们只会用在foreach中 方便遍历数据结构  而不希望迭代器对数据结构产生修改
        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public T next() {//数组的索引[n]即栈顶出栈
            if (isEmpty()) throw new NoSuchElementException("Stack is underflow!"); //栈为空 无法出栈
            return (T) array[--i];//此处的操作只会修改迭代器对象的数据不会修改所遍历栈的数据结构 是安全符合要求的
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
            //空实现 我们不希望在迭代器中 修改数据结构中的数据  固是空实现 有必要可以 进行健壮性设计 使调用本方法抛出未检查异常
        }
        
    }
}
