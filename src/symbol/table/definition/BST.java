package symbol.table.definition;

import queue.definition.Queue;

/**
 * Map 简单实现
 * 简单符号表的实现 之——
 * 有序符号表 基于BST的 二叉树查找
 * @author wjs13
 *BST顺序性：  任意结点均不小于/大于其左/右后代(注意是后代 不是孩子)    也即BST的单调性  BST的key的中序遍历序列 必然单调非降(若无重复key结点 则单调递增)
 *BST在插入删除后必须维护BST的顺序性  即要重排序
 * @param <Key>
 * @param <Value>
 * 本BST几乎全部的API都是用的递归策略来实现    注意理解
 * 递归： 要了解递归基是什么  回溯过程 做了些什么  
 */
public class BST<Key extends Comparable<Key>, Value> {
   
    private Node root; //指示二叉树查找树的根结点  在BST的重排序过程中root所引用的Node对象值会改变
    
    public BST(){
        root = null;
    }
    private  class Node{//二叉链表
        private Key key;//键值对
        private Value val;
        private Node left;//指针域 指向左右子树
        private Node right;
        private int n;//以该结点为根的子树中的结点总数+ 本身结点1
        public Node(Key key, Value val, int n) {
            super();
            this.key = key;
            this.val = val;
            this.n = n;
        }
    }
    
    public void put(Key key, Value val){//将键值队存入表中 若值为空则将键key从表中删除
        if (val == null){//若值为空则将键key从表中删除
            delete(key);//防御性代码  保证了符号表中的任何键的值不会为null
            return;
        }
        root = put(root, key, val);//返回经过重排序BST的整树树根节点  以使下一次的插入正确进行
    }//利用下面重载的方法 来递归的实现   要了解递归基是什么  回溯过程 做了些什么  
    
  //BST插入元素 且保证BST的顺序性     因为是递归的从根节点开始从上到下  依次递归按相对每个子树的大小把元素放到左/右子树中的  所以是能够保证BST的顺序性 
//    BST顺序性：  任意结点均不小于/大于其左/右后代(注意是后代 不是孩子)
//    这个方法是递归的实现 是BST其他几种实现的模板  要理解
//    这跟 BinarySearchST的二分查找rank(key)返回索引int值 几乎是一样的思路和简单
    //此处递归的实现插入  put好像必须要设计成返回Node x 才能正确有效的重排序和维护BST
//    private void put(Node x, Key key, Value val)的设计方法 用递归 似乎无法正确的完成BST的重排序和相关维护工作
    private Node put(Node x, Key key, Value val){ //参数x就是 要插入的BST树(或子BST树)根结点
        //搞清楚 返回值的意义      就是经过添加键值对 且维护BST或子BST的顺序性  和结点的 计数器值n后的树根结点x的引用
        //开始自己写的 没有定义put的返回值 方法中对x进行的操作都是无效的
       /* if (x == null) {//错误范例
            Node node = new Node(key, val, 0);
            x = node;//错误  引用的地址值是按 值传递的  修改引用地址值对原对象没有任何改变  在方法体中只有修改引用所指向的对象才是有效的
        }*/
//        本方法实现的逻辑 和递归查找很相似
        //递归基
        if (x == null) return new Node(key, val, 1);//若x链接为空 则新建一个结点 链接到其上一个递归的结点
        int cmp = key.compareTo(x.key);
        //此处之后的操作 因为需要根据左右结点键的大小 链接到新的结点  固本put设计必须要设计Node的返回值 以有效的修改结点的指针域
        if (cmp > 0) x.right = put(x.right, key, val);//在方法中对引用所指向的对象进行修改才是有效的  修改x是无效的  修改x.right等才是有效的
        else if (cmp < 0) x.left = put(x.left, key, val);
        else  x.val = val;//若结点在BST中存在 则直接更新值
      //BST需要维护每一个结点中的n(左右子树的总结点数+本身结点1)值
        x.n = size(x.left) + size(x.right) + 1;  //此处是回溯的维护了在BST重排序过程中改变了n的每一个结点的计数器
        //本方法的精髓  返回x 即是返回经过插入或者修改或者维护了新n值的二叉链表或子二叉链表的头结点
        return x;//通过回溯 递归的 完成了BST的重排序和计数器维护
    }
    
    
    public Value get(Key key){//获取键key对应的值 若键key不存在则返回空
        return get(root,key);
    }//利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么  
    private Value get(Node x, Key key){//递归的 查找BST的结点   基于BST的二叉树查找
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp == 0) return x.val;
        else return get(x.right, key);
    }    
        
        
    public void delete(Key key){//从表中删去对应的key 及其对应的值
//        put(key,null);//默认实现 实际暂时没有删除key 是一种延时删除  需要在之后的某个时候删除所有值为null的所有键
        root = delete(root, key);
    }//利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么
//    实现了1962 Hibbard 提出的的二叉查找树中对结点的即时删除
//    在删除结点x后 用它的 后继结点 填补它的位置
//    后继结点 就是x右子树(也可能没有)中的最小键结点  即具体通过找到x右子树的最小键结点t 删除右子树中的该最小结点  并把t链接到x原来的左子树和新的x右子树 和 x的父结点  
    private Node delete(Node x, Key key){//删除以x为树根结点的(子)BST中键为key的结点  并且要保证BST的顺序性和维护每个需要维护的结点的n值
//        返回值 就是经过维护后的新的子BST的树根结点
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);//x.left可以指示 递归回溯中x的父节点
        else if (cmp > 0) x.right = delete(x.right, key);//x.right可以指示 递归回溯中x的父节点
        else {//以上几步为找到key所在的结点x  本else后    x就是已经要删除的结点了
           if (x.right == null) return x.left;//若x无右子树 则通过递归的回溯 直接把x的左子树链接到x的父结点  就完成了delete操作
           if (x.left == null)  return x.right;//若x无左子树 则通过递归的回溯 直接把x的右子树链接到x的父结点  就完成了delete操作
           //若x存在左右子树的 情况  则要旋转移位  找到x的后继结点 来填补x的位置
           Node t= x;
           x = min(t.right);//后继结点    x右子树中的最小键结点
           x.right = deleteMin(t.right);//x还要链接到新的x右子树
           x.left = t.left;//x还要链接到x原来的左子树
         //x还要通过递归回溯 链接到x原来的父结点  即最后一步的  return x  就可以完成
        }
        x.n = size(x.left) + size(x.right) + 1;
        return x;//回溯 不断的 更新和维护 delete过程中需要维护的结点
    }
    
    
    public boolean contains(Key key){
        return get(key) != null;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    
    public int size(){//表中键值对的数量   即BST中的总结点数   也即树根结点root的n值
        return size(root);
    }
    private int size(Node x){//结点x的n值 
        if (x == null) return 0;//空结点 n值为0
        else return x.n;//结点的n值 是在结点插入BST过程中 由put插入方法所维护的
    }
    
    public Key min(){//最小的键
        /*不去用迭代实现 否则 后面的某些方法 不好利用本重载的方法来实现
         * Node min = root;
        while (min.left != null){
            min = min.left;
        }
        return min.key;*/
        return min(root).key;
    }//而是利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么  
    private Node min(Node x){//返回以x为根结点的(子)BST 中最小的键   可用于递归的实现 全BST树最小的键
        if (x.left == null) return x;
        return min(x.left);
    }
    
    
    
    
    public Key max(){//最大的键
        /*不去用迭代实现 否则 后面的某些方法 不好利用本重载的方法来实现
         * Node max = root;
        while (max.right != null){
            max = max.right;
        }
        return max.key;*/
        return max(root).key;
    }//而是利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么  
    private Node max(Node x){//返回以x为根结点的(子)BST 中最大的键   可用于递归的实现 全BST树最大的键
        if (x.right == null) return x;
        return max(x.right);
    }
    
    
    public Key floor(Key key){//小于等于key的最大键
        return floor(root, key).key;
    }//利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么  
    private Node floor(Node x, Key key){//返回以x为根结点的(子)BST 中小于等于key的最大键   可用于递归的实现 全BST树小于等于key的最大键
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);//递归找小于等于key的最大键 直到cmp > 0 则递归的去找右子树中小于等于key的最大键 
        if (t != null) return t;//若能在右子树中找出小于等于key的最大键  则也就是以x为根结点的(子)BST中小于等于key的最大键
        else return x;
    }
    
    
    public Key ceiling(Key key){//大于等于key的最小键
        return ceiling(root, key).key;
    }//利用重载的方法 来递归的实现   要了解递归基是什么  回溯过程 做了些什么  
    private Node ceiling(Node x, Key key){//返回以x为根结点的(子)BST 中大于等于key的最小键   可用于递归的实现 全BST树大于等于key的最小键
        if (x == null) return null;//递归基
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp > 0) return ceiling(x.right, key);
        Node t = ceiling(x.left, key);//思路同floor方法
        if (t != null) return t;//递归基
        else return x;
    }
    
    
    public int rank(Key key){//小于key的键的数量 
        return rank(root, key);
    }//利用重载的方法 来递归的实现   要了解递归基是什么  回溯过程 做了些什么  
    private int rank(Node x, Key key){//返回以x为根结点的子BST树中小于key的键的数量
        if (x == null) return 0;//递归基
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
      //若key在右子树 则返回的小于key的键的数量除了递归的找x右子树中小于key的键的数量   还要加上x左子树的总结点数 和已经判断出是小于key的x结点本身
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);//若key等于BST中某个结点的key  则rank结果就是 该结点x的左子树的总结点数
    }
    
    
    public Key select(int k){//排名为k的键
        return select(root, k).key;
    }//利用重载的方法 来递归的实现   要了解递归基是什么  回溯过程 做了些什么  
    private Node select(Node x, int k){//返回以x为根结点的子BST树中排名为k的键所在的结点
        if (x == null) return null;
//        x结点在以x为树根的子BST中 的排名 rank   因x.key不为null则一定是存在在BST中的键 则rank出的值加1 才是排名  排名是从1开始的
        int rank = rank(x.key) + 1;//利用了rank来实现select 也可以不用rank  
        if (rank == k) return x;//递归基
        else if (rank < k) return select(x.right, k);
        else return select(x.left, k);
    }
    
    public void deleteMin(){//删除最小的键   需要维护 BST的顺序性  和结点的 计数器值n
        root = deleteMin(root);
    }//利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么  
    private Node deleteMin(Node x){//搞清楚 参数x和返回值的意义  x表示删除以x为根的子BST树中键最小的结点    
//       返回值 就是经过删除最小键值对维护了BST的顺序性  和结点的 计数器值n后的x的结点的引用
      //递归基   直到x的左子树为空 则x为最小的键 
        if (x.left == null) return x.right;  //则删除x  就是把x的右子树(可以为空)返回(也即链接)到上一个递归中的x的左子树  注意理解
        x.left = deleteMin(x.left);//要 递推分析  在到达递归基后 逆向返回的过程中(即 回溯 过程)  执行了哪些步骤 要理解清楚
        x.n = size(x.left) + size(x.right) + 1;//维护计数器值n   递归 回溯 过程 维护x及其x的每一个左子树的结点的n值
        return x;
    }
    
    
    public void deleteMax(){//删除最大的键      需要维护 BST的顺序性  和结点的 计数器值n
        root = deleteMax(root);
    }//利用重载的方法 来递归的实现     要了解递归基是什么  回溯过程 做了些什么  
    private Node deleteMax(Node x){
      //递归基   直到x的右子树为空 则x为最大的键 
        if (x.right == null) return x.left;//则删除x  就是把x的左子树(可以为空)返回(也即链接)到上一个递归中的x的右子树   注意理解
        x.right = deleteMax(x.right);//要 递推分析  在到达递归基后 逆向返回的过程中(即 回溯 过程)  执行了哪些步骤 要理解清楚
        x.n = size(x.left) + size(x.right) + 1;//维护计数器值n   递归 回溯 过程 维护x及其x的每一个左子树的结点的n值
        return x;
    }
    
    
    public void printKey(){//按顺序打印 BST中的所有的键  打印
       printKey(root); 
    }//利用重载的方法 来递归的实现     要了解递归基是什么  回溯过程 做了些什么 
    private void printKey(Node x){//中序遍历BST  即可按key的顺序遍历以x为树根的(子)BST中所有的结点
        if (x == null) return;
        printKey(x.left);
        System.out.println(x.key);
        printKey(x.right);
    }
    
    public boolean isBST() {//判断本BST中插入的元素 是否满足BST的顺序性
        return isBST(root, null, null);
    }
    private boolean isBST(Node x, Key min, Key max){//记住  注意理解  此方法 递归的方式  还比较特殊 和其他的API的递归套路不同
        if (x == null) return true;
        // 下一个递归实现判断 是否满足BST的顺序性
        if (min != null && x.key.compareTo(min) <= 0) return false;//注意 键值不能重复
        if (max != null && x.key.compareTo(max) >= 0) return false;
        //若为BST则 当前递归实例的x.val比 x.left.val大
        // 且 x.val 比 x.right.val小  这两个判断是到下一个递归实例实现的
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }
    
    
    /*public int size(Key lo, Key hi){//[lo..hi]之间键的数量
        return 0;//暂没实现
    }*/
  //用以支持对 符号表BST的  foreach操作
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
