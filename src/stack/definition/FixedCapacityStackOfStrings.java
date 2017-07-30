package stack.definition;

/**定容栈
 * 固定容量的字符串栈  的实现
 * @author wjs13
 *
 */
public class FixedCapacityStackOfStrings {
    private String[] a;//定容栈的 数据底层存储数组
    private int length;//栈的长度 即有效的元素的个数 非数组a的长度
    public FixedCapacityStackOfStrings(int capacity) {
        a = new String[capacity];
    }
    public boolean isEmpty(){
        return length == 0;
    }
    public int size(){
        return length;
    }
    public void push(String value){
        a[length++] = value;
    }
    public String pop(){
        return a[--length];//注意栈顶位于数组a[length-1]处 非a[length]
    }
}
