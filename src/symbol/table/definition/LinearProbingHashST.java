package symbol.table.definition;

import java.util.NoSuchElementException;

import stack.definition.Stack;

/**
 * 基于 线性试探   冲突排解  哈希符号表
 * 开放定址策略 
 * @author wjs13
 *  线性试探法 哈希符号表 桶数组容量的 扩充 是必须的  否则 当插入的键值对数量超过预期时  它的查找时间会变得非常的长  
 * 线性试探  装填因子r=N/M 要保持在r<=0.5  才不置于 查找的效率太低 甚至是查找不到
 */
public class LinearProbingHashST<Key, Value> {
    private int m ;//散列表的容量
    private int hash;//缓存的hash值  若hash函数计算慢 则同一个对象的多次hash值计算就可以用缓存值
    private int n;//键值对总数
    private Key[] keys;
    private Value[] vals;
    private boolean[] flags;
    
    public LinearProbingHashST(){
        this(16);
    }
    public LinearProbingHashST(int m){
        this.m = m;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
        flags = new boolean[m];
    }
    
    public void put(Key key, Value val){//将键值队存入表中 若值为空则将键key从表中删除
        if (n >= m/2) resize(2*m);
        if (val == null){//若值为空则将键key从表中删除
            delete(key);//防御性代码  保证了符号表中的任何键的值不会为null
            return;
        }
        int index  = hash(key);
        for (int i = index; keys[i] != null; i = (i + 1) % m){//非空若和key不相等 则一直循环查找 散列表 
//            i = (i + 1) % m  表示 i数组索引会从index一直到m-1再从0继续循环
            if (keys[i].equals(key)) {
                vals[i] = val;return;
            }
        }
        keys[index] = key;
        vals[index] = val;
        n++;
        
    }
    public Value get(Key key){//获取键key对应的值 若键key不存在则返回空
        int index = hash(key);
        while (true){
            if (keys[index] != null && keys[index].equals(key)) 
                {
                    return vals[index];
                }
            else if (keys[index] == null && flags[index] == false) {//非伪删除的空 则说明 查找链正常的断了  就返回找不到元素
                //否则 就是 伪删除点 查找链 还没断 需要去接着下一个查找点找
                return null;
            }
            index = (index + 1) % m;//表示 index数组索引会从index一直到m-1再从0继续循环
        }
        
    }
    
    //基于伪删除的 元素删除
    public void delete(Key key){//从表中删去对应的key 及其对应的值
//        put(key,null);//默认实现 实际暂时没有删除key 是一种延时删除  需要在之后的某个时候删除所有值为null的所有键
        int index = hash(key);
        while (true){
            if (keys[index] != null && keys[index].equals(key)) 
                {
                   keys[index] = null;//防止内存泄漏
                   vals[index] = null;
                   flags[index] = true;//该index处要被删除的元素 做 伪删除 留一个记号  防止查找链断
                   n--;
                   //删除过程中  伪元素删除 标致 不影响 散列表的扩容或缩容实现  这点要理解清楚
                // halves size of array if it's 12.5% full or less
                   if (n > 0 && n <= m/8) resize(m/2);
                   return;
                }
            else if (keys[index] == null && flags[index] == false) {//非伪删除的空 则说明 查找链正常的断了  就返回找不到元素
                throw new NoSuchElementException("the key "+key+"is not exist!");
            }
            index = (index + 1) % m;//表示 index数组索引会从index一直到m-1再从0继续循环
        }
     // halves size of array if it's 12.5% full or less
    }
    
    
    private void resize(int cap){//调整桶数组容量m  键需要重散列 否则 原来的键用新的散列函数(因m改变 散列函数也改变)就找不到key对应的桶单元 
       //线性试探法 哈希符号表 桶数组容量的 扩充 是必须的  否则 当插入的键值对数量超过预期时  它的查找时间会变得非常的长  
        //线性试探  装填因子r=N/M 要保持在r<=0.5  才不置于 查找的效率太低 甚至是查找不到
      /* 错误的方法  无法 保证 线性试探查找链的完整链接  因未处理哈希冲突
       * int old_m = m;
        m = cap;
       Key[] keys_t = (Key[]) new Object[cap];
       Value[] vals_t = (Value[]) new Object[cap];
       for (int i = 0; keys[i] != null && i < old_m; i++){
           keys_t[hash(keys[i])] = keys[i];//未考虑 处理哈希冲突  错误  应该直接用本类的put方法 重新散列元素 且排解冲突
           vals_t[hash(keys[i])] = vals[i];
       }
       keys = keys_t;
       vals = vals_t;*/
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(cap );
        for (int i = 0; i < m; i++) {
//          且 在新的散列表 把原来的伪删除的数组位置 也丢弃了 因为重散列的过程 不同于查找过程 不需要查找链的完整 
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);//键的重散列 还要考虑 排解冲突 固直接用put插入
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        flags = temp.flags;//注意 这个伪删除的标志位 也一并置为新的散列表标致 其实新的散列表中标致flags都为false 因为扩容过程中的散列表只有插入没有删除操作 标致位不会变
        m    = temp.m;
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
            if (keys[i] != null){
                stack.push(keys[i]);
            }
        }
        return stack;
    }
}
