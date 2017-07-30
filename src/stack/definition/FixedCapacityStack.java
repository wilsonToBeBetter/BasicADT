package stack.definition;

/**
 * 顺序栈    构造栈对象时需要传入初始栈的长度 
 * 可自动调整数组大小的栈  底层基于顺序存储结构的数组
 * 泛型栈 可以存储任何数据类型
 * @author wjs13
 *java的泛型 是类型擦除实现的  在编译成字节码文件后 类型擦除了 替换成原生类型 本类参数化类型T就被替换成Object了
 */
public class FixedCapacityStack<T> {
    private T[] a;//定容栈的 数据底层存储数组
    private int length;//栈的长度 即有效的元素的个数 非数组a的长度  可以用来指示栈顶
    public FixedCapacityStack(int capacity) {
        a = (T[]) new Object[capacity];//JAVA不允许创建泛型数组 只能通过Object[]强制向下转换到T[]
    }
    public boolean isEmpty(){
        return length == 0;
    }
    public int size(){
        return length;
    }
    public void push(T value){//java的泛型 是类型擦除实现的  在编译成字节码文件后 类型擦除了 替换成原生类型 本类T就被替换成Object类了
        //若栈满了 需要扩容数组的长度 此处我们扩容一倍  再把数据压入到扩容后的栈中
        if (length == a.length)
            resize(2*a.length);
        a[length++] = value;
    }
    public T pop(){//java的泛型 是类型擦除实现的  在编译成字节码文件后 类型擦除了 替换成原生类型 本类T就被替换成Object类了
        //删除栈顶的元素 如果数组太大 栈中元素太少  可以选择减小数组的容量  此处的策略是容量减半 数组的长度减半 进而得到 检测条件是栈的长度小于数组长度的四分之一就减半数组的长度
        T t = a[--length];
        if (length > 0 && length == a.length / 4) 
            resize(a.length / 2);//数组a[]容量减半 使目前栈中元素处于数组的半满状态 之后可以正常的push和pop多次而不必再调整容量
        return t;//注意栈顶位于数组a[length-1]处 非a[length]
    }
    private void resize(int max){
        //将大小为length<=max的栈移动到一个新的大小为max的数组中
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < length; i++){
            temp[i] = a[i];
        }
        a = temp;
    }
    
}
