package symbol.table.definition;

import queue.definition.Queue;
/**
 * 基于AVL树的  符号表ST实现
 * BBST ——AVL树  适度平衡的二叉搜索树   height(AVL) = 大O(logn)  渐进意义下AVL树高为logn
 * AVL意义下的 适度平衡: 任何结点的平衡因子(左右子树的高度差)     -1 <= bal(v) <=1
 * 理想平衡 : 所有的叶子结点的深度统一相等
 * @author wjs13
 *子树高度: 子树只有一个结点 则该子树的高度为0  空结点的子树的高度为-1
 * @param <Key>
 * @param <Value>
 */
public class AVLTreeST<Key extends Comparable<Key>, Value> {
    
    Node root;
    
    public AVLTreeST(){
        root = null;
    }
    private class Node{
        private Key key;
        private Value val;
        private Node left;
        private Node right;
      //结点的高度  用以计算结点的平衡因子  比定义一个结点的平衡因子更加直观
//        height的属性值是在每插入一个结点和重平衡过程中维护的
        private int height;//结点的高度为该节点的左右子树高度的较大者+1
//        子树高度: 子树只有一个结点 则该子树的高度为0  空结点的子树的高度为-1
        private int n;//值为 结点的子树中总的结点数量+结点本身1   方便一些API的实现
        public Node(Key key, Value val, int height, int n) {
        super();
        this.key = key;
        this.val = val;
        this.height = height;
        this.n = n;
        }
    }
    
    public int height(){//BBST树高
        return height(root);
    }
    private int height(Node x){//结点x的高度   即node对象的height属性值 而height的属性值是在每插入一个结点和重平衡过程中维护的
        if (x == null) return -1;
        return x.height;//每个新插入的结点的height属性都会被设置成0  且在插入过程中会维护height的大小
    }
    
    private int balanceFactor(Node x){//结点平衡因子
       return height(x.left) - height(x.right); //开始用x.left.height 这样当x.left为null时 会报空指针异常
    }
    
    
    public void put(Key key, Value val){//将键值队存入表中 若值为空则将键key从表中删除  符号表ST的标准API
        if (val == null){//若值为空则将键key从表中删除
            delete(key);//防御性代码  保证了符号表中的任何键的值不会为null
            return;
        }
        root = put(root, key, val);
    }
    private Node put(Node x, Key key, Value val){//AVL树插入结点算法   前面部分表示找到要插入到的AVL树的位置的代码和标准的BST结点插入算法同理 
//      对AVL树结点插入  做zig和zag局部变换后  该局部的整体子树的高度相对与整个AVL树的高度不变   固整树的失衡性得到了解决 且不会发生 失衡传播而导致更高结点发生失衡  
        if (x == null) return new Node(key, val, 0, 1);//只有一个结点的树高度为0
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val); 
        else if (cmp > 0) x.right = put(x.right, key, val);
        else {
            x.val = val;
            return x;
        }
        //维护每一个结点的n值
        x.n = 1 + size(x.left) + size(x.right);
        //维护每一个结点的高度
        x.height = 1 + Math.max(height(x.left), height(x.right));//结点的高度为该节点的左右子树高度的较大者+1
        //先维护n和height再判断是否需要重平衡 且需要重平衡的 再局部转换完成后还需要更新维护n和height   注意不同方法中维护n和height的时机和作用
      //递归中遍历到的结点   都需要依次重平衡判断和重平衡转换  才不会漏掉可能发生失衡的结点  这个要理解  通过递归回溯的理解平衡和失衡发生和传递的过程
        return reBalance(x);//在AVL树中插入新结点后 需要对AVL树进行重平衡操作  递归回溯的把重平衡后的结点链接到其原来的父结点
//        还可以用 3 + 4 重构 重平衡算法 reBalance34(Node x)
    }
    
    private Node reBalance(Node x) {//AVL树结点插入的重平衡
      //失衡情况  分类讨论   且本方法和rotatexx方法是通过失衡结点及其子孙三代 g p v来做局部转换修复失衡情况
        if (balanceFactor(x) < -1) {
//          思路参考 邓俊辉 数据结构 视频讲解AVL 图形的 zig、zag变换
            if (balanceFactor(x.right) > 0) {//AVL树 zigzag双旋情况  需要先对x的右结点做zig再对x做zag
                x.right = rotateRight(x.right);//BST旋转等价变换的 zig局部变换   以v为旋转中心 顺时针旋转rotateRight(v)
            }
          //AVL树 zagzag单旋  则只需要对非平衡结点x做一次的zag变换   就达到了重平衡
            x = rotateLeft(x);//BST旋转等价变换的 zag局部变换   以v为旋转中心 逆时针旋转rotateLeft(v)
        }
        else if (balanceFactor(x) > 1) {
//            思路参考 邓俊辉 数据结构 视频讲解AVL 图形的 zig、zag变换
            if (balanceFactor(x.left) < 0) {//AVL树 zagzig双旋   需要先对x的左结点做zag再对x做zig
                x.left = rotateLeft(x.left);//zag变换
            }
          //AVL树 zigzig单旋  则只需要对非平衡结点x做一次的zig变换  就达到了重平衡
            x = rotateRight(x);//zig变换
        }
        return x;//未失衡 则可以直接返回x  也返回经过重平衡后的原x位置的平衡结点x 返回到put中递归回溯的链接到原x的父结点
    }
    
    
//    zag局部变化
    private Node rotateLeft(Node x) {//BST旋转等价变换的 zag局部变换   以x为旋转中心 逆时针旋转rotateLeft(x)
//      发生旋转变换的结点主要是x和y  他们子树的平衡性不会发生改变  且子树中结点的高度的变化不需要在旋转中维护  只要在put回溯过程中维护就行  重要  想明白
//        在zag讲解中本x相当于旋转中心v y为c结点
//      x==v   y==c 一下一些讲解中 对应 邓俊辉 《数据结构》讲解图中的结点名称
      Node y = x.right;//发生旋转变化的另外一个结点 在zag中就是x的右子树的树根结点c
      x.right = y.left;//zag思路参考 邓俊辉 数据结构  笔记
      y.left = x;
      //旋转变化过程中 发现 x和y结点的子树的高度会发生变化  于是参考了《算法》中AVL树的源码 发现
      //不用想办法把x y左右子树的高度增加或者下降一个单位来使x和y的高度复合旋转后的变化    而是直接利用结点高度的定义 直接维护x和y的高度 
//      因为结点是一个一个的插入进AVL树的 所以 只维护x和y的高度 下一次插入x或y的子树结点时 子树的高度也会被本方法或者put方法所维护成和父结点高度只差一个单位的正确树形逻辑结构
    //维护发生旋转变换的x和y结点的n值
      y.n = x.n;//zig变换后  c的结点的n值和变换前v的n值相等
      x.n = 1 + size(x.left) + size(x.right);//变换后v的n值 需要重新计算维护
      // //维护发生旋转变换的x和y结点的高度
      x.height = 1 + Math.max(height(x.left), height(x.right));//结点的高度为该节点的左右子树高度的较大者+1
      y.height = 1 + Math.max(height(y.left), height(y.right));//结点的高度为该节点的左右子树高度的较大者+1
      return y;//返回新的旋转中心c 即y结点  返回值再返回到put方法中去递归回溯的链接到原x结点的父结点    就完成了等价旋转变换
    }
    
//    zig局部变换
    private Node rotateRight(Node x) {//BST旋转等价变换的 zig局部变换   以x为旋转中心 顺时针旋转rotateRight(x)
//      发生旋转变换的结点主要是x和y  他们子树的平衡性不会发生改变  且子树中结点的高度的变化不需要在旋转中维护  只要在put回溯过程中维护就行   重要  想明白
//      在zag讲解中本x相当于旋转中心v y为c结点
//        x==v   y==c 一下一些讲解中 对应 邓俊辉 《数据结构》讲解图中的结点名称
        Node y = x.left;//发生旋转变化的另外一个结点 在zig中就是x的左子树的树根结点c 
        x.left = y.right;//zig思路参考 邓俊辉 数据结构  笔记
        y.right = x;
        //旋转变化过程中 发现 x和y结点的子树的高度会发生变化  于是参考了《算法》中AVL树的源码 发现
        //不用想办法把x y左右子树的高度增加或者下降一个单位来使x和y的高度复合旋转后的变化    而是直接利用结点高度的定义 直接维护x和y的高度 
//        因为结点是一个一个的插入进AVL树的 所以 只维护x和y的高度 下一次插入x或y的子树结点时 子树的高度也会被本方法或者put方法所维护成和父结点高度只差一个单位的正确树形逻辑结构
      //维护发生旋转变换的x和y结点的n值
        y.n = x.n;//zig变换后  c的结点的n值和变换前v的n值相等
        x.n = 1 + size(x.left) + size(x.right);//变换后v的n值 需要重新计算维护
        // //维护发生旋转变换的x和y结点的高度
        x.height = 1 + Math.max(height(x.left), height(x.right));//结点的高度为该节点的左右子树高度的较大者+1
        y.height = 1 + Math.max(height(y.left), height(y.right));//结点的高度为该节点的左右子树高度的较大者+1
        return y;//返回新的旋转中心c 即y结点  返回值再返回到put方法中去递归回溯的链接到原x结点的父结点    就完成了等价旋转变换
    }
    
    
    public Value get(Key key){//获取键key对应的值 若键key不存在则返回空    符号表ST的标准API AVL结点的查找 和一般的BST二叉搜索方法一样
        return get(root,key);
    }
    private Value get(Node x, Key key){//递归的 查找BST的结点   基于BST的二叉树查找  AVL树也是BST
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp == 0) return x.val;
        else return get(x.right, key);
    }    
    
    
    
    public void delete(Key key){//从表中删去对应的key 及其对应的值
//        put(key,null);//默认实现 实际暂时没有删除key 是一种延时删除  需要在之后的某个时候删除所有值为null的所有键
        root = delete(root, key);
    }
    private Node delete(Node x, Key key){//AVL树结点的删除 和 重平衡       删除结点的前部分删除代码和标准的BST结点删除代码同理  有个后继结点占被删除结点位置的算法
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else{
            if (x.right == null) return x.left;//若x无右子树 则通过递归的回溯 直接把x的左子树链接到x的父结点  就完成了delete操作 
            if (x.left == null)  return x.right;//若x无左子树 则通过递归的回溯 直接把x的右子树链接到x的父结点  就完成了delete操作
            Node t= x;
            x = min(t.right);//后继结点    x右子树中的最小键结点
            x.right = deleteMin(t.right);//x还要链接到新的x右子树
            x.left = t.left;//x还要链接到x原来的左子树
          //x还要通过递归回溯 链接到x原来的父结点  即最后一步的  return x  就可以完成结点删除操作 后面还需要完成AVL树的重平衡
        }
        //维护结点的n和height
        x.n = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
      //递归中遍历到的结点   都需要依次重平衡判断和重平衡转换  才不会漏掉可能发生失衡的结点  这个要理解  通过递归回溯的理解平衡和失衡发生和传递的过程
        return reBalance(x);//AVL树 结点的删除和 AVL树结点的插入 重平衡判断和执行的时机和重平衡策略单旋、双旋算法是同一个
    }
    
    
    public boolean contains(Key key){
        return get(key) != null;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    public int size(){//表中键值对的数量
        return size(root);
    }
    private int size(Node x){
        if (x == null) return 0;
        return x.n; 
    }
    
    
    
  
    public Key min(){//最小的键   同标准的基于BST的 符号表实现相同
        return min(root).key;
    }//而是利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么  
    private Node min(Node x){//返回以x为根结点的(子)BST 中最小的键   可用于递归的实现 全BST树最小的键
        if (x.left == null) return x;
        return min(x.left);
    }
    
    public Key max(){//最大的键   同标准的基于BST的 符号表实现相同
        return max(root).key;
    }//而是利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么  
    private Node max(Node x){//返回以x为根结点的(子)BST 中最大的键   可用于递归的实现 全BST树最大的键
        if (x.right == null) return x;
        return max(x.right);
    }
    
    
    /*以下API的实现和标准的BST 符号表的API实现 一样  与AVL树的特性无关
     * public Key floor(Key key){//小于等于key的最大键
        return null;
        
    }
    public Key ceiling(Key key){//大于等于key的最小键
        return null;
        
    }
    public int rank(Key key){//小于key的键的数量 
        return 0;
    }
    public Key select(int k){//排名为k的键
        return null;
    }
    public int size(Key lo, Key hi){//[lo..hi]之间键的数量
        return 0;
    }
    */
    
    
    public void deleteMin(){//删除最小的键
        root = deleteMin(root);
    }
    private Node deleteMin(Node x){//x表示删除以x为根的子AVL树中键最小的结点       原理同BST的deleteMIn相同
//        返回值 就是经过删除最小键值对维护了BST的顺序性  和结点的属性n和height后的x的结点的引用
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.n = size(x.left) + size(x.right) + 1;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return reBalance(x);//此处注意 删除最小键结点 AVL树也是可能发生失衡的 之前没发现这点 以为不会发生失衡
    }
    
    
    
    public void deleteMax(){//删除最大的键
        root = deleteMax(root);
    }
    private Node deleteMax(Node x){//x表示删除以x为根的子AVL树中键最小的结点       原理同BST的deleteMIn相同
//      返回值 就是经过删除最小键值对维护了BST的顺序性  和结点的属性n和height后的x的结点的引用
      if (x.right == null) return x.left;
      x.right = deleteMax(x.right);
      x.n = size(x.left) + size(x.right) + 1;
      x.height = 1 + Math.max(height(x.left), height(x.right));
      return reBalance(x);
  }
  
    public  boolean isAVL() {//判断是不是AVL树
        return isAVL(root);
    }
    private boolean isAVL(Node x) {
        if (x == null) return true;
        int bf = balanceFactor(x);
        if (bf > 1 || bf < -1) return false;
        return isAVL(x.left) && isAVL(x.right);
    }
    
    public boolean isBST() {//判断本BST中插入的元素 是否满足BST的顺序性
        return isBST(root, null, null);
    }
    private boolean isBST(Node x, Key min, Key max){//记住  注意理解  此方法 递归的方式  还比较特殊 和其他的API的递归套路不同
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;//注意 键值不能重复
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }
    
    private Node reBalance34(Node x){//基于3+4重构  的重平衡算法 
//      下面边界情况 空值空指针异常的代码 有时间看能不能优化一下 看着有点乱
        if (x == null) return null;
        Node a = null;
        Node b = null; 
        Node c = null;
        Node t0 = null;
        Node t1 = null;
        Node t2 = null;
        Node t3 = null;
//      下面边界情况 空值空指针异常的代码 有时间看能不能优化一下 看着有点乱
        if (balanceFactor(x) < -1) {  //按zig zag分类 计算处3+4重构的参数a b c t0 t1 t2 t3
            if (balanceFactor(x.right) > 0) {//AVL树 zigzag双旋情况  
                 a = x;
                 if (x.right != null)
                     b = x.right.left; 
                 if (x.left != null)
                     c = x.left;
                 t0 = x.left;
                 if (b != null){
                 t1 = b.left;
                 t2 = b.right;
                 }
                 if (c != null)
                 t3 = c.right;
                
                return connect34(a, b, c, t0, t1, t2, t3);
            }
            else{//AVL树 zagzag单旋  
                a = x;
                b = x.right; 
                if (x.right != null)
                c = x.right.right;
                t0 = x.left;
                if (b != null)
                t1 = b.left;
                if (c != null)
                t2 = c.left;
                if (c != null)
                t3 = c.right;
               return connect34(a, b, c, t0, t1, t2, t3);
            }
          
        }
        else if (balanceFactor(x) > 1) {

            if (balanceFactor(x.left) < 0) {//AVL树 zagzig双旋  
                a = x.left;
                b = a.right; 
                c = x;
                if (a != null)
                t0 = a.left;
                if (b != null)
                t1 = b.left;
                if (b != null)
                t2 = b.right;
                if (c != null)
                t3 = c.right;
               return connect34(a, b, c, t0, t1, t2, t3);
            }
            else{//AVL树 zigzig单旋  
                if (x.left != null)
                a = x.left.left;
                b = x.left; 
                c = x;
                if (a != null)
                t0 = a.left;
                if (a != null)
                t1 = a.right;
                if (b != null)
                t2 = b.right;
                if (c != null)
                t3 = c.right;
               return connect34(a, b, c, t0, t1, t2, t3);
            }
        }
        return x;//未失衡 则可以直接返回x  也返回经过重平衡后的原x位置的平衡结点x 返回到put中递归回溯的链接到原x的父结点
    }
//    3+4重构算法   比用zig zag处理更加的概括 易于理解记忆 且深入 也更加简明 高效和鲁棒
//    下面边界情况 空值空指针异常的代码 有时间看能不能优化一下 看着有点乱
    private Node connect34(Node a, Node b, Node c, Node t0, Node t1, Node t2, Node t3){
//      思路参考 邓俊辉 数据结构 视频讲解AVL 图形的3+4重构
        if (a != null){
        a.left = t0;
        a.right = t1;
        a.n = size(a.left) + size(a.right) + 1;
        a.height = 1 + Math.max(height(a.left), height(a.right));
        }
        if (c != null){
        c.left = t2;
        c.right = t3;
        c.n = size(c.left) + size(c.right) + 1;
        c.height = 1 + Math.max(height(c.left), height(c.right));
        }
        if (b != null){
        b.left = a;
        b.right = c;
        b.n = size(b.left) + size(b.right) + 1;
        b.height = 1 + Math.max(height(b.left), height(b.right));
        }
        return b;//该子树新的根节点
    }
    
  //用以支持对 符号表BST的  foreach操作   以下3个方法的代码和标准的BST API一样
    public Iterable<Key> keys(Key lo, Key hi){//[lo..hi]之间的所有键  已排序
        Queue<Key> queue =new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }
  //递归的查找 左子树 根节点  右子树 来完成 范围查找key
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi){//中序遍历以x为根节点的BST 且把key在[lo,hi]之间的键存一份引用到队列中
        if (x == null) return;
        int cmp_lo = lo.compareTo(x.key);
        int cmp_hi = hi.compareTo(x.key);
        //利用中序遍历的方式 完成  注意 理解 中序遍历的思想
        if (cmp_lo < 0) keys(x.left, queue, lo, hi);
        if ((cmp_lo <=0) && cmp_hi >= 0)  queue.enqueue(x.key);//把等于和大于lo 和 小于等于hi 的key都放到queue中
        if (cmp_hi > 0) keys(x.right, queue, lo, hi);
    }
    
    //用以支持对 符号表BST的  foreach操作
    public Iterable<Key> keys(){//表中所有键的集合 已排序  利用keys(Key lo, Key hi){//[lo..hi]之间的所有键  已排序  来实现
        return keys(min(),max());
    }
    
}
