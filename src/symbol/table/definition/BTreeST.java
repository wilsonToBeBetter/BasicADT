/*package symbol.table.definition;

import edu.princeton.cs.algs4.BTree.Entry;
import edu.princeton.cs.algs4.BTree.Node;





*//**
 * 
 * B树 ——多路平衡搜索树 
 * @author wjs13
 *
 * @param <Key>
 * @param <Value>
 *//*
public class BTreeST<Key extends Comparable<Key>, Value> {
    
    private static final int M = 3;
    
    private Node root;       // root of the B-tree
    private int n;           // number of key-value pairs in the B-tree
    private int height;      //B树根节点树高  
    
    
   
    private static class Node{
        private Comparable[] keys = new Comparable[M];
        private Object[] vals = new Object[M];
        private Node[] nodes = new Node[M + 1];
        private int m  = 0;//每个超级结点中的关键码数量
        private int h = -1;//每个超级结点的树高   规定外部结点的树高为0   空结点树高为0
        public Node(Comparable key, Object val){
            entries[0] = new Entry(key, val);
            m = 1;
            h = 0;
        }
        public Node(int k){
            m = k;
        }
        
        public int search(Comparable key){
          //基于 二分查找的思想   来找到key在升序的keys中应该所处的位置
            int lo = 0;
            int hi = m - 1;
//            要查找的数值在  lo和hi之外
            if (hi == -1) return 0;//空表 则key应该插入在keys数组的0索引处
            if (!less(keys[0], key)) return 0;//key小于 等于  keys[0]也是要插入在数组的0索引处
//            如果表中存在该键   则rank(Key key)应该返回该键key在有序数组keys中的位置(数组的秩)  且返回的秩的大小刚好等于表中小于键key的数量
            if (key.compareTo(keys[hi]) == 0) return hi;
            if (less(keys[hi], key )) return hi + 1;// key在升序的keys中应该所处的位置在最后 则应该是 返回n  即hi+1
//            要查找的数值在lo和hi之间
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
    }
    
    *//**
     * Initializes an empty B-tree.
     *//*
    public BTreeST() {
        root = new Node(0);
    }
    
    
    public Value get(Key key){//获取键key对应的值 若键key不存在则返回空  B树查找算法
        return get(root, key);
    }
    private Value get(Node x, Key key) {
        if (x == null) return null;
        int index = x.search(key);
        if (eq(key, x.keys[index])) return (Value) x.vals[index];
        else return get(x.nodes[index], key);
    }
    
    
    
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        Node u = insert(root, key, val, height); 
        n++;
        if (u == null) return;

        // need to split root
        Node t = new Node(1);
        t.keys[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }
    private Node insert(Node h, Key key, Value val, int ht) {
        int j;

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.keys[j])) break;
            }
        }

        // internal node
        else {
            int index = h.search(key);
            if (!eq(key, h.keys[index])) {
                h.nodes[index] = insert(h.nodes[index], key, val, ht-1);
                return h;
            }
                    
        }
        for (int i = h.m; i > j; i--){
            h.keys[i] = h.keys[i-1];
            h.vals[i] = h.vals[i-1];
            h.nodes[i] = h.nodes[i-1];
        }
        h.keys[j] = key;
        h.vals[j] = val;
        h.nodes[j] = null;
        h.m++;
        if (h.m < M) return h;
        else         return split(h);
    }

    // split node in half
    private Node split(Node h) {
        int s = (int) Math.floor(M/2.0);//中位数
        Node t1 = new Node(s);
        Node t2 = new Node(M-s-1);
        Node t = new Node(1);
        h.m = M/2;
        for (int j = 0; j < s; j++){
            t1.keys[j] = h.keys[j];
            t1.vals[j] = h.vals[j];
            t1.nodes[j] = h.nodes[j];
        }
        for (int j = 0; j < M-s-1; j++){
            t2.keys[j] = h.keys[s+j+1];
            t2.vals[j] = h.vals[s+j+1];
            t2.nodes[j] = h.nodes[s+j+1];
        }
        t.nodes[0] = t1;
        t.nodes[1] = t2;
        return t;    
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
        return n;
    }
    // comparison functions - make Comparable instead of Key to avoid casts
    private static boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }
    
    private static boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
    *//**
     * Returns the height of this B-tree (for debugging).
     *
     * @return the height of this B-tree
     *//*
    public int height() {
        return height;
    }
   
    public Iterable<Key> keys(Key lo, Key hi){//[lo..hi]之间的所有键  已排序
        return null;
    }
    public Iterable<Key> keys(){//表中所有键的集合 已排序
        return null;
    }
    
}
*/