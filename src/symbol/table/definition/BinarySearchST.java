package symbol.table.definition;

import java.util.NoSuchElementException;

import queue.definition.Queue;
import sort.definition.Quick;
import stack.definition.Stack;


/**
 * Map 简单实现
 * 简单符号表的实现 之——
 * 有序符号表      基于有序数组的 二分查找
 * 利用了 二分查找  完成插入和查找键值对
 * 基于有序数组实现的   底层键数组  按升序  键不能重复 值不能为null   底层数组支持可变容量
 * 底层使用一对平行数组 Key[] Value[]  Key是实现了Comparable类型的有序键
 * 本类实现的核心是 rank(Key key)方法   它返回表中小于给定键的键的数量
 * rank(Key key)方法实现应该完成的有:
 * 如果表中存在该键   则rank(Key key)应该返回该键key在有序数组keys中的位置(数组的秩)  且返回的秩的大小刚好等于表中小于键key的数量
 * 如果表中不存在该键 rank(Key key)  还是应该返回表中小于它的键的数量  且该数量若在数组秩的范围内 也是表示该key若插入到有序的keys数组中 其应该按顺序性插入到数组秩为返回的数量值
 * @author wjs13
 * 查找效率较高  但是插入效率太低
 * 可以找到 查找和插入效率都在对数级别的数据结构和算法 —— 二叉查找树  二叉搜索树
 *
 */
public class BinarySearchST<Key extends Comparable<Key>, Value>{
    private Key[] keys;//底层键数组   要保证升序排序key
    private Value[] vals;//底层值数组  要保证同一秩的keys和vals值是正确对应的键值对
    private int n;//符号表中键值对的数目
    public BinarySearchST(){//初始默认构造函数
        keys = (Key[]) new Comparable[1];
        vals = (Value[]) new Object[1];
        n = 0;
    }
    //根据传入的键值对数组  将键完成排序和初始化符号表 即表的预排序     利用的是rank二分查找来完成预排序
    public BinarySearchST(Item<Key,Value>[] items){//Item中封装了key和val值
        int len = items.length;
        n = len;
        keys = (Key[]) new Comparable[len];
        vals = (Value[]) new Object[len];
        for (int i = 0; i < len; i++){
            keys[i] = items[i].getKey();//取出对象中的键值
        }
        Quick.sort(keys);//利用快速排序 对键值进行排序  完成对键数组的初始化
        for (int i = 0; i < len; i++){//利用  二分查找 rank 找到item对象中键key对应的值val应该在vals数组中的秩
            vals[rank(items[i].getKey())] = items[i].getVal();//完成对值数组的初始化
        }
    }
    private boolean less(Key key1, Key key2){
        return key1.compareTo(key2) < 0;
    }
    private void resize(int cap){//底层数组支持改变容量   这种方法对于 大数组是很低效的  以后优化的 符号表 用链表实现
        int len = keys.length;
        Key[] temp1 = (Key[]) new Comparable[cap];
        Value[] temp2 = (Value[]) new Object[cap];
        for (int i = 0; i < len; i++){
            temp1[i] = keys[i];//注意键和值对应的修改和容量的变化
            temp2[i] = vals[i];
        }
        keys = temp1;
        vals = temp2;
    }
    
    //put还是很低效   在一个空符号表中插入N个元素在最坏情况下需要访问~N^2此数组  成本过高
    public void put(Key key, Value val){//将键值对存入表中 若值为空则将键key从表中删除
        if (val == null){//若值为空则将键key从表中删除
            delete(key);//防御性代码  保证了符号表中的任何键的值不会为null
            return;
        }
        if (get(key) == null){//若要插入的键不存在    则利用二分查找  找到应该插入有序数组keys的位置
            int index = rank(key);//不管key在不在表中  key在升序的keys中应该所处的位置index
            if (n + 1== keys.length) resize(keys.length * 2);//不管什么情况 都是要 先 判断是否扩容
            if (index == n){//若是在已存在的key的最后一个位置插入 即在keys[n]插入 或  是第一个键值对的插入  即keys[0]的插入  则 直接在索引处赋值键值对  
                keys[index] = key;
                vals[index] = val;
            }
            else{//要插入的位置 不是在已存在的key的末尾  即不是keys[n]  也不是第一个键值对的插入  则插入键值对  需要对后面的键值对整体向右移动一格
                for (int i = n ; i > index; i--){
                    keys[i] = keys[i - 1];//键值对整体向右移动一格
                    vals[i] = vals[i - 1];
                }
                keys[index] = key;//对要插入的位置赋值应该处在改位置的 要插入的键值对
                vals[index] = val;
                }
            n++;
        }
        else{
            vals[rank(key)] = val;//表中有重复的键  则新值覆盖旧值
        }
        
    }
    
    //基于有序数组的 二分查找
    public Value get(Key key){//获取键key对应的值 若键key不存在则返回空  利用有序的keys数组的二分查找来高效完成
        int index = rank(key);//不管key在不在表中  key在升序的keys中应该所处的位置index
        if (index == n) return null;//要插在最后一个位置 keys[index]为null  不能用于compareTo比较了 否则空指针异常  固单独if出来 返回空
        if (key.compareTo(keys[index]) == 0) return vals[index];
        return null;
    }
    
    public void delete(Key key){//从表中删去对应的key 及其对应的值
//        put(key,null);//默认实现 实际暂时没有删除key 是一种延时删除  需要在之后的某个时候删除所有值为null的所有键
        if (get(key)==null) throw new NoSuchElementException("the key "+key+"is not exist!");
        int index = rank(key);//不管key在不在表中  key在升序的keys中应该所处的位置index
        for (int i = index; i < n - 1; i++){
            keys[i] = keys[i + 1];
            vals[i] = vals[i + 1];
        }
        n--;
        if (n == keys.length / 4) resize(keys.length / 2);
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
    public Key min(){//最小的键
        return keys[0];
    }
    public Key max(){//最大的键
        return keys[n - 1];
    }
    public Key floor(Key key){//小于等于key的最大键
//        rank()返回的index为 不管key在不在表中  key在升序的keys中应该所处的位置index
        if (contains(key)) return keys[rank(key)];
        if (rank(key) == 0) return keys[0];
        return keys[rank(key) - 1];
        
    }
    public Key ceiling(Key key){//大于等于key的最小键
        return keys[rank(key)];
        
    }
    
    //本二分查找的顺序符号表 实现的 核心方法
    //注意符号表的 定义 就是 键不能为空且不能重复  值也不能为空
    //不管key在不在表中  key在升序的keys中应该所处的位置index就是rank的返回值  
    public int rank(Key key){//小于key的键的数量  在无重复升序有序key数组中 rank返回值就是key应该在有序数组中的位置秩
        //基于 二分查找的思想   来找到key在升序的keys中应该所处的位置
        int lo = 0;
        int hi = n - 1;
//        要查找的数值在  lo和hi之外
        if (hi == -1) return 0;//空表 则key应该插入在keys数组的0索引处
        if (!less(keys[0], key)) return 0;//key小于 等于  keys[0]也是要插入在数组的0索引处
//        如果表中存在该键   则rank(Key key)应该返回该键key在有序数组keys中的位置(数组的秩)  且返回的秩的大小刚好等于表中小于键key的数量
        if (key.compareTo(keys[hi]) == 0) return hi;
        if (less(keys[hi], key )) return hi + 1;// key在升序的keys中应该所处的位置在最后 则应该是 返回n  即hi+1
//        要查找的数值在lo和hi之间
        while (hi - lo > 1 ){//二分查找    基于 迭代的 二分法
            int mid = (lo + hi) / 2;
            if (less(key,keys[mid])){
                hi = mid; 
            }
            else if (less(keys[mid],key)){
                lo = mid;
            }
            else return mid;
        }
        return lo + 1;//key的值位于lo和hi索引对应值之间  则利用二分法  查出的 key在升序的keys中应该所处的位置
    }
    public Key select(int k){//排名为k的键
        return keys[k - 1];
    }
    public void deleteMin(){//删除最小的键
        int index = 0;//类似delete(Key key)思想
        for (int i = index; i < n - 1; i++){
            keys[i] = keys[i + 1];
            vals[i] = vals[i + 1];
        }
        n--;
        if (n == keys.length / 4) resize(keys.length / 2);  
    }
    public void deleteMax(){//删除最大的键
        keys[n - 1] = null;
        vals[n - 1] = null;
        n--;
        if (n == keys.length / 4) resize(keys.length / 2); 
    }
    public int size(Key lo, Key hi){//[lo..hi]之间键的数量
        boolean lo_flag = contains(lo);
        boolean hi_flag = contains(hi);
        int lo_index = rank(lo);//不管key在不在表中  key在升序的keys中应该所处的位置index就是rank的返回值  
        int hi_index = rank(hi);
        int len = hi_index- lo_index;
        //分情况讨论 lo 和 hi分别是否在表中
        if (lo_flag && hi_flag){//情况1
            return len + 1;
        }
        else if (!lo_flag && hi_flag){//同情况1
            return len + 1;
        }
        else if (lo_flag && !hi_flag){//情况2
            return len;
        }
        else {//同情况2   if(!lo_flag && !hi_flag)
            return len;
        }
    }
    public Iterable<Key> keys(Key lo, Key hi){//[lo..hi]之间的所有键  已排序
        Queue<Key> queue = new Queue<Key>();
        boolean lo_flag = contains(lo);
        boolean hi_flag = contains(hi);
        int lo_index = rank(lo);//不管key在不在表中  key在升序的keys中应该所处的位置index就是rank的返回值  
        int hi_index = rank(hi);
        //分情况讨论 lo 和 hi分别是否在表中
        if (lo_flag && hi_flag){//情况1
            for (int i = lo_index; i <= hi_index; i++)
             queue.enqueue((keys[i]));
        }
        else if (!lo_flag && hi_flag){//同情况1
            for (int i = lo_index; i <= hi_index; i++)
             queue.enqueue(keys[i]);
        }
        else if (lo_flag && !hi_flag){//情况2
            for (int i = lo_index; i <= hi_index - 1; i++)
             queue.enqueue(keys[i]);
        }
        else if(!lo_flag && !hi_flag){//同情况2
            for (int i = lo_index; i <= hi_index - 1; i++)
             queue.enqueue(keys[i]);
        }
        return queue;
    }
    public Iterable<Key> keys(){//表中所有键的集合 已排序
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < n; i++){
            queue.enqueue((keys[i]));
        }
        return queue;
    }
}
