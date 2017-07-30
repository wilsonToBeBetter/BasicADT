package symbol.table.definition;

import stack.definition.Stack;

/**
 * 基于 独立链法  的 排解冲突   的   哈希符号表
 * 封闭定址策略
 * @author wjs13
 *独立链法的 哈希符号表  如果能准确地估计用例所需的散列表的大小N 调整数组的工作并不是必须的
 *只要根据查找耗时和(1+N/M)成正比 来选取一个适当的M即可
 *且调整桶数组容量m  键需要重散列 否则 原来的键用新的散列函数(因m改变 散列函数也改变)就找不到key对应的桶单元 
 */
public class SeparateChainingHashST<Key, Value>{
    private int m ;//散列表的容量
    private int hash;//缓存的hash值  若hash函数计算慢 则同一个对象的多次hash值计算就可以用缓存值
    private int n;//键值对总数
    private SequentialSearchST<Key, Value>[] sequentialSearchST;//桶数组 散列表 基于独立链的冲突排解
    
    public SeparateChainingHashST(){
        this(997);//this()语句用于调用本类的构造函数 super()用于调用父类的构造函数  子类都会默认调用父类的构造函数
    }
    public SeparateChainingHashST(int m){
        this.m = m;
        n = 0;
        sequentialSearchST = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        //一定要初始化每一个桶单元中的链表  否则添加.put就空指针异常
        for (int i = 0; i < m; i++){
            sequentialSearchST[i] = new SequentialSearchST<Key,Value>();
        }
    }
    
    public void put(Key key, Value val){//将键值队存入表中 若值为空则将键key从表中删除
        if (val == null){//若值为空则将键key从表中删除
            delete(key);//防御性代码  保证了符号表中的任何键的值不会为null
            return;
        }
        sequentialSearchST[hash(key)].put(key, val);
        n++;
    }
    public Value get(Key key){//获取键key对应的值 若键key不存在则返回空
        return sequentialSearchST[hash(key)].get(key);
        
    }
    public void delete(Key key){//从表中删去对应的key 及其对应的值
//        put(key,null);//默认实现 实际暂时没有删除key 是一种延时删除  需要在之后的某个时候删除所有值为null的所有键
        sequentialSearchST[hash(key)].delete(key);
        n--;
    }
    
    private void resize(int cap){//调整桶数组容量m  键需要重散列 否则 原来的键用新的散列函数(因m改变 散列函数也改变)就找不到key对应的桶单元 
        //独立链法的 哈希符号表  如果能准确地估计用例所需的散列表的大小N 调整数组的工作并不是必须的
        //只要根据查找耗时和(1+N/M)成正比 来选取一个适当的M即可
        //且发现 调整独立链法的散列表容量 需要把原来所有键对于新的散列表容量m要重散列 需要顺序遍历链表和桶数组 感觉很麻烦 效率有点低
        //所以 暂时不实现  后来细想一下 似乎不难 可以利用keys()拿到所有的键 用底层的无序链表符号表的get(key)拿到值  再用容量改变后的的
//        新的m 即有新的哈希函数  来计算 桶数组的索引 并put插入   即重散列所有的键 用底层无序链表符号表的put来插入
        
        
    }
    public boolean contains(Key key){
        return get(key) != null;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    public int size(){//表中键值对的数量
        return n;
    }
    private int hash(Key key){
        int h = hash;
        if (h == 0 && key != null){
            h = (key.hashCode() & 0x7fffffff) % m;
            hash = h;
        }
        return h;
    }
    public Iterable<Key> keys(){//表中所有键的集合 
        Stack<Key> stack = new Stack<Key>();
        for (int i = 0; i < m; i++){
            Stack<Key> keys = (Stack<Key>) sequentialSearchST[i].keys();
            stack.push(keys.pop());
        }
        return stack;
    }
    
    
    
}
