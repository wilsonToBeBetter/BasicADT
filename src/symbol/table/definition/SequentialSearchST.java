package symbol.table.definition;

import java.util.NoSuchElementException;

import stack.definition.Stack;

/**
 * Map 简单实现
 * 简单符号表的实现 之——
 * 无序符号表  基于无序链表的 顺序查找
 * 基于 (无序)链表  实现的符号表     其中get的实现是一种顺序查找的符号表
 * 实际测试和理论证明 这个顺序查找是非常低效的
 * @author wjs13
 *
 */
public class SequentialSearchST<Key, Value> {
    private Node<Key,Value> first ;//链表的头指针  指示链表插入和删除的位置
    private int n;//符号表 中键值对的数量
    private static class Node<Key,Value>{//底层链表结点  每个结点的数据域存放一个键和一个值
        private Key key;
        private Value value;
        private Node<Key, Value> next;//指针域
    }
    
    public SequentialSearchST(){
        first = null;
        n = 0;
    }
    
  //将键值队存入表中 若值为空则将键key从表中删除
    public void put(Key key, Value val){//无序链表实现的符号表 的 put在存放下一个不存在重复key的键值对时  也要遍历整个链表
        if (val == null){//若值为空则将键key从表中删除
            delete(key);//防御性代码  保证了符号表中的任何键的值不会为null
            return;
        }
        if (get(key) == null){//无重复的键 则新建结点存放到first指示的位置  即存放到链表开头
            Node<Key,Value> oldFirst = first;
            first = new Node<Key,Value>();
            first.key = key;
            first.value = val;
            first.next = oldFirst;
            n++;
            return;
        }
        else {//有重复的键 则新键值覆盖掉老的
//            注意first是全局的头结点 在方法中小心的改变first的值  这个注意之前写Stack和Queue已经出现了的
            Node<Key, Value> head = first;
            while (!head.key.equals(key)){
                head = head.next;
            }
            head.value = val;
        }
        
    }
    public Value get(Key key){//get查找键对应的值  是遍历整个链表的操作   基于无序链表的顺序查找
//        注意first是全局的头结点 在方法中小心的改变first的值  这个注意之前写Stack和Queue已经出现了的
        Node<Key, Value> head = first;
        while(head != null){
            if (head.key.equals(key)){//对于没有设计实现Comparable的Key 是用Object的equals比较  Comparable则是用compareTo比较
                return head.value;
            }
           head = head.next;
        }
        return null;
    }
    public void delete(Key key){//即时型的 delete 立刻从符号表中删除key 即删除底层对应链表中的结点
//        put(key,null);//默认实现 实际暂时没有删除key 是一种延时删除  需要在之后的某个时候删除所有值为null的所有键
//        注意first是全局的头结点 在方法中小心的改变first的值  这个注意之前写Stack和Queue已经出现了的
//        在删除链表的非first结点时  在遍历链表时 应该单独复制一个链表的头指针node = first  在操作中操作node而不操作first 
//        则删除非first结点时就不会破坏first的位置   first始终必须使指向链表的头结点的
//        注意边界情况 在删除first指示的结点时 要改变first的位置
        Node<Key, Value> node = first;//指示当前的结点 
        Node<Key, Value> preNode = null;//指示当前节点的前一个结点 即preNode.next=node 
        if (contains(key)){
            while(!node.key.equals(key)){
                preNode = node;
               node = node.next;
            }
            if (node != first){
                Node<Key, Value> afterNode = node.next;
                node = null;
                preNode.next = afterNode;
            }
            else {//而在删除first指示的头结点  则必须对全局的first进行改变  而不是改变本方法的局部变量node
                first = first.next;
            }
            n--;
        }
        else throw new NoSuchElementException("the key "+key+" is not exist!");
    }
    public boolean contains(Key key){
        return get(key) != null;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    public int size(){
        return n;
    }
    public Iterable<Key> keys(){//把遍历出所有的key值 放到支持Iterable的Stack或Queue等容器中 以支持对keys的foreach
//        注意first是全局的头结点 在方法中小心的改变first的值  这个注意之前写Stack和Queue已经出现了的
        Node<Key, Value> head = first;
        Stack<Key> stack = new Stack<Key>();
        while(head != null){
           stack.push(head.key);
           head = head.next;
        }
        return  stack;
    }
}
