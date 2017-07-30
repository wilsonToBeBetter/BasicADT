package symbol.table.definition;

import java.util.Iterator;
/**
 * 有序符号表的API 不允许有重复的键 不允许有null的值
 * @author wjs13
 *
 * @param <Key>
 * @param <Value>
 */
public class AbstractST<Key, Value> {
    
    public void put(Key key, Value val){//将键值队存入表中 若值为空则将键key从表中删除
        if (val == null){//若值为空则将键key从表中删除
            delete(key);//防御性代码  保证了符号表中的任何键的值不会为null
            return;
        }
    }
    public Value get(Key key){//获取键key对应的值 若键key不存在则返回空
        return null;
    }
    public void delete(Key key){//从表中删去对应的key 及其对应的值
//        put(key,null);//默认实现 实际暂时没有删除key 是一种延时删除  需要在之后的某个时候删除所有值为null的所有键
    }
    public boolean contains(Key key){
        return get(key) != null;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    public int size(){//表中键值对的数量
        return 0;
    }
    public Key min(){//最小的键
        return null;
    }
    public Key max(){//最大的键
        return null;
    }
    public Key floor(Key key){//小于等于key的最大键
        return null;
        
    }
    public Key ceiling(Key key){//大于等于key的最小键
        return null;
        
    }
    public int rank(Key key){//小于key的键的数量  在无重复升序有序key数组中 rank返回值就是key应该在有序数组中的位置秩
        return 0;
    }
    public Key select(int k){//排名为k的键
        return null;
        
    }
    public void deleteMin(){//删除最小的键
        
    }
    public void deleteMax(){//删除最大的键
        
    }
    public int size(Key lo, Key hi){//[lo..hi]之间键的数量
        return 0;
    }
    public Iterable<Key> keys(Key lo, Key hi){//[lo..hi]之间的所有键  已排序
        return null;
    }
    public Iterable<Key> keys(){//表中所有键的集合 已排序
        return null;
    }
    
}
