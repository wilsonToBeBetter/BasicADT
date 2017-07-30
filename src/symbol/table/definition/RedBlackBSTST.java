package symbol.table.definition;



/**
 * BBST——红黑树  实现 符号表
 * 红黑树 我们所使用的定义: 由红、黑两类结点组成的BST  且统一增设外部结点null 使之成为真二叉树
 * 1、树根和所有的外部结点为黑色
 * 2、红结点之子、之父 为黑结点         该定义控制了红黑树的深度
 * 3、外部结点到根：途中黑结点数目相等 即黑深度统一相等    该定义控制了红黑树的平衡性
 * 
 * 红黑树  经过 提升变换  就是 (2,4)4阶B树      4阶B树 与红黑树 彼此等价
 * @author wjs13
 *
 * @param <Key>
 * @param <Value>
 */
public class RedBlackBSTST<Key extends Comparable<Key>, Value> {
    
    private Node root; //指示二叉树查找树的根结点  在BST的重排序过程中root所引用的Node对象值会改变
//    此flag有并发问题 以后改进
    private boolean flag = false;//需要做 双黑修正  在一次删除过程中 这种修正只需要做一次  双黑是需要进行分类讨论的双黑需要进行分类讨论的修复修复  非双黑
    private boolean flag_delMin = false;//删除最小键结点 是否需要做双黑修复的 标志位
    
    private enum Color{
        RED,BLACK;
    }
    private class Node{//二叉链表
        private Key key;//键值对
        private Value val;
        private Node left;//指针域 指向左右子树
        private Node right;
        private Color color;
//        结点的 黑高度   本红黑树中 不需要维护结点的高度 只需要维护黑高度   主要是之前写的维护结点高度的代码有点问题 后来发现 红黑树API用不到树高 固就放弃设置维护了
        private int b_height;////结点的高度为该节点的左右子树黑高度的较大者+1(该节点为黑)或0(该结点为红) 
//      子树高度: 子树只有一个黑结点 则该子树的高度为0  无黑结点的子树的高度为-1
        private int n;//以该结点为根的子树中的结点总数+ 本身结点1
        public Node(Key key, Value val, int n, int b_height, int height) {
            super();
            this.key = key;
            this.val = val;
            this.n = n;
            this.b_height = b_height;
            color = Color.RED;//新插入的结点 默认设置为红色
        }
    }
    
    public RedBlackBSTST(){
        root = null;
    }
    
    public int height(){//BBST树高
        return height(root);
    }
    private int height(Node x){//结点x的高度   即node对象的height属性值 而height的属性值是在每插入一个结点和重平衡过程中维护的
        if (x == null) return -1;
        return x.b_height;//每个新插入的结点的height属性都会被设置成0  且在插入过程中会维护height的大小
    }
    public int b_height(){//BBST树高
        return height(root);
    }
    private int b_height(Node x){//结点x的高度   即node对象的height属性值 而height的属性值是在每插入一个结点和重平衡过程中维护的
        if (x == null) return 0;//为空虽为外部结点 但统一不计入黑高度  方便处理  固返回0
        return x.b_height;//每个新插入的结点的height属性都会被设置成0  且在插入过程中会维护height的大小
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
    
    
    public void put(Key key, Value val){//将键值队存入表中 若值为空则将键key从表中删除
        if (val == null){//若值为空则将键key从表中删除
            delete(key);//防御性代码  保证了符号表中的任何键的值不会为null
            return;
        }
        root = put(root, key, val);
        if(isRed(root)){
            root.color = Color.BLACK;//根节点必须为黑色 这是红黑树的定义
            root.b_height++;//根节点由红变成黑色   则根结点黑高度需要自增1
        }
    }
    private Node put(Node x, Key key, Value val){//插入结点的过程中 要维护 黑高度  高度 结点总数
//        Node(Key key, Value val, int n, int b_height, int height)
        if (x == null) return new Node(key, val, 1, 0, 0);//红节点黑高度为0
        int cmp = key.compareTo(x.key);
        if (cmp > 0) x.right = put(x.right, key, val);//在方法中对引用所指向的对象进行修改才是有效的  修改x是无效的  修改x.right等才是有效的
        else if (cmp < 0) x.left = put(x.left, key, val);
        else  x.val = val;//若结点在BST中存在 则直接更新值
      //BST需要维护每一个结点中的n(左右子树的总结点数+本身结点1)值
        x.n = size(x.left) + size(x.right) + 1;  //此处是回溯的维护了在BST重排序过程中改变了n的每一个结点的计数器
        updateHeight(x);//维护结点的黑高度
        //若有必要需要做双红修正
        return solveDoubleRed(x);//双红修复  且需要再双红修复中维护黑高度
    }
    
    
    private Node solveDoubleRed(Node x){//双红修正
        return reBalance34(x);
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
        if (isRed(x.right)) {  //按zig zag分类 计算处3+4重构的参数a b c t0 t1 t2 t3
            if (isRed(x.right.left)) {//AVL树 zigzag双旋情况  
                 a = x;//能进此语句块来 则x.right  x.right.left就不会为空
                 b = x.right.left; 
                 c = x.left;//即 a和b不会为空 c可能为空
                 t0 = x.left;
                 t1 = b.left;
                 t2 = b.right;
                 if (c != null) //c可能为空
                 t3 = c.right;
                if(isBlack(t0) ){//双红缺陷修复的第一种情况  叔父结点为黑结点
                       b.color = Color.BLACK;
                       a.color = Color.RED;
                    if(c != null)
                       c.color = Color.RED;
                    //需要重构  再3+4重构中 就已经完成了 黑高度的维护
                    return connect34(a, b, c, t0, t1, t2, t3);
                }
                else {//双红缺陷修复的第二种情况  叔父结点为红结点   p与u转黑 g转红   p代表parent g代表爷爷  u代表叔父
                    c.color = Color.BLACK;//p转黑
                    t0.color = Color.BLACK;//u转黑
                    a.color = Color.RED;//g转红
//                  不用重构 则结点染色之后再进行结点高度维护 才对
                    updateHeight(t0);//先维护子的结点高度 再维护父结点高度
                    updateHeight(a);
                    updateHeight(c);
                }
            }
            if (isRed(x.right.right)){//AVL树 zagzag单旋  
              //能进此语句块来 则x.right  x.right.right就不会为空
                a = x;//也就是 a b c 都不会为空
                b = x.right; 
                c = x.right.right;
                t0 = x.left;//t0可能为空
                t1 = b.left;
                t2 = c.left;
                t3 = c.right;
                if (isRed(b) && isBlack(t0)){//双红缺陷修复的第一种情况  叔父结点为黑结点  则3+4重构后  b为黑结点 a和c为红结点 其他的不变
                    b.color = Color.BLACK;
                    a.color = Color.RED;
                    c.color = Color.RED;
                  //需要重构  再3+4重构中 就已经完成了 黑高度的维护
                    return connect34(a, b, c, t0, t1, t2, t3);
                }
                else if(isRed(b) && isRed(t0)){//双红缺陷修复的第二种情况  叔父结点为红结点   p与u转黑 g转红   p代表parent g代表爷爷  u代表叔父
                    if(t0 != null)
                    t0.color = Color.BLACK;//u转黑
                    b.color = Color.BLACK;//p转黑
                    a.color = Color.RED;
//                  不用重构 则结点染色之后再进行结点高度维护 才对
                    updateHeight(t0);//先维护子的结点高度 再维护父结点高度
                    updateHeight(a);
                    updateHeight(b);
                }
            }
          
        }
        if (isRed(x.left)) {
            if (isRed(x.left.right)) {//AVL树 zagzig双旋  
              //能进此语句块来 则x.left  x.left.right就不会为空
                a = x.left;//即a b c都不会为空
                b = a.right; 
                c = x;
                t0 = a.left;
                t1 = b.left;
                t2 = b.right;
                t3 = c.right;//t3可能为空
                if (isRed(a) && isBlack(t3)){//双红缺陷修复的第一种情况  叔父结点为黑结点
                    b.color = Color.BLACK;
                    a.color = Color.RED;
                    c.color = Color.RED;
                  //需要重构  再3+4重构中 就已经完成了 黑高度的维护
                    return connect34(a, b, c, t0, t1, t2, t3);
                }
                else if(isRed(a) && isRed(t3)){//双红缺陷修复的第二种情况  叔父结点为红结点   p与u转黑 g转红   p代表parent g代表爷爷  u代表叔父
                    if(t3 != null)
                    t3.color = Color.BLACK;
                    a.color = Color.BLACK;
                    c.color = Color.RED;
//                  不用重构 则结点染色之后再进行结点高度维护 才对
                    updateHeight(t3);//先维护子的结点高度 再维护父结点高度
                    updateHeight(c);
                    updateHeight(a);
                }
            }
            if (isRed(x.left.left)) {//AVL树 zigzig单旋  
                //能进此语句块来 则x.left  x.left.left就不会为空
                a = x.left.left;//即a b c都不会为空
                b = x.left; 
                c = x;
                t0 = a.left;
                t1 = a.right;
                t2 = b.right;
                t3 = c.right;//t3可能为空
                if (isRed(b) && isBlack(t3)){//双红缺陷修复的第一种情况  叔父结点为黑结点
                    b.color = Color.BLACK;
                    a.color = Color.RED;
                    c.color = Color.RED;
                  //需要重构  再3+4重构中 就已经完成了 黑高度的维护
                    return connect34(a, b, c, t0, t1, t2, t3);
                }
                else if(isRed(b) && isRed(t3)){//双红缺陷修复的第二种情况  叔父结点为红结点   p与u转黑 g转红   p代表parent g代表爷爷  u代表叔父
                    if(t3 != null)
                    t3.color = Color.BLACK;
                    b.color = Color.BLACK;
                    c.color = Color.RED;
//                  不用重构 则结点染色之后再进行结点高度维护 才对
                    updateHeight(t3);//先维护子的结点高度 再维护父结点高度
                    updateHeight(c);
                    updateHeight(b);
                }
            }
        }
        return x;//未失衡 则可以直接返回x  也返回经过重平衡后的原x位置的平衡结点x 返回到put中递归回溯的链接到原x的父结点
    }
//    3+4重构算法   比用zig zag处理更加的概括 易于理解记忆 且深入 也更加简明 高效和鲁棒
//    下面边界情况 空值空指针异常的代码 有时间看能不能优化一下 看着有点乱
    private Node connect34(Node a, Node b, Node c, Node t0, Node t1, Node t2, Node t3){
//      思路参考 邓俊辉 数据结构 视频讲解AVL 图形的3+4重构
//        再3+4重构后需要维护结点的黑高度
        if (a != null){
        a.left = t0;
        a.right = t1;
        a.n = size(a.left) + size(a.right) + 1;
        updateHeight(a);   //维护结点黑高度
        }
        if (c != null){
        c.left = t2;
        c.right = t3;
        c.n = size(c.left) + size(c.right) + 1;
        updateHeight(c);//维护结点黑高度
        }
        if (b != null){
        b.left = a;
        b.right = c;
        b.n = size(b.left) + size(b.right) + 1;
        updateHeight(b);//维护结点黑高度
        }
        return b;//该子树新的根节点
    }
    
    
    
    
    
    public void delete(Key key){//从表中删去对应的key 及其对应的值
//        put(key,null);//默认实现 实际暂时没有删除key 是一种延时删除  需要在之后的某个时候删除所有值为null的所有键
        root = delete(root, key);
    }
    private Node delete(Node x, Key key){//双黑指 删除结点和替换的后继结点之间的颜色关系
        //以下要用到flag  有并发问题 需要研究 改进
        Node t = x;
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else{//要删除结点x的左子树也提到要替换的结点r的子树中 讨论 即把只把x的链接连到r 把原x的左右子树链接到r来讨论 红黑树删除结点的修复情况
            //本else中返回的都是已经替换后的x  但是已经完成了判断是否进行双黑修复 只需要在下一次递归回溯 用x的父节点 完成双黑修复 且整个递归过程中只执行一次 注意
            if (x.right == null) {//这两个if是下一次递归回溯中父节点来维护的n和height 固无法在此做维护
                if (isBlack(x.left) && isBlack(x)) flag = true;//双黑需要进行分类讨论的修复
                else {
                    x.left.color = Color.BLACK;//不需要双黑修复 则只需要把替代的结点 置为黑色 就完成整个红黑树的修复工作
                    updateHeight(x.left);
                }
                return x.left;
            }
            if (x.left == null) {
                if (isBlack(x.right) && isBlack(x)) flag = true;//双黑需要进行分类讨论的修复
                else {
                    x.right.color = Color.BLACK;//不需要双黑修复 则只需要把替代的结点 置为黑色 就完成整个红黑树的修复工作
                    updateHeight(x.right);//要维护黑高度
                }
              //因为在本else中提前返回了x 所以结点x的n和height还需要在此维护 无法
                return x.right;
            }
            
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;//x还要链接到x原来的左子树
          //因为在本else中提前返回了x 所以结点x的n和height还需要在此维护 无法在下一次回溯中维护 因为下一次回溯是维护的父结点的n和height
            x.n = size(x.left) + size(x.right) + 1;  //此处是回溯的维护了在BST重排序过程中改变了n的每一个结点的计数器
            if (isRed(t) || isRed(x)) {//t和替代后继结点 至少一个是红色   则把新替代的结点染成黑色 即完成了红黑树的修复 且不需要进行双黑修复
                x.color = Color.BLACK;
                updateHeight(x);//要维护黑高度
            }
            else{//需要进行双红修复
                flag = true;
            }
            return x;
        }
        x.n = size(x.left) + size(x.right) + 1;  //此处是回溯的维护了在BST重排序过程中改变了n的每一个结点的计数器
        //若有必要需要做双黑修正
        if (flag) solveDoubleBlack(x);//此flag控制在递归中 只会执行一次的双黑修复  很巧 是自己想的  注意理解
        //只能对要替换的结点x的父节点   也就是对上面找到要删除的x后的下一次的回溯中的x进行双黑修复
        flag = false;//一次的delete只需要做一次的双黑修复 不需要在递归回溯中做多次   
        return x;//通过回溯 递归的 完成了BST的重排序和计数器维护
    }
    private void solveDoubleBlack(Node x){//双黑修正   
//       双黑修复 分4种 情况讨论  见 邓俊辉  《数据结构》视频 红黑树的插入讲解     参数x相当于p
//        删除之前红黑树是平衡的 黑高度是统一的 删除后 会出现黑高度差
        int bn = x.left.b_height - x.right.b_height;//结点的左右子树的黑高度差 可以用于判断哪边是被删除替换结点的兄弟结点所在的边
//        对于x与r是双黑的情况下做的判断   因r替换掉x后 不修复颜色的话 那么x的被r替换的一边的子树的黑高度就会下降一个单位  删除之前红黑树是平衡的 黑高度是统一的 删除后 会出现黑高度差
        Node s  = bn > 0 ? x.left : x.right;//新替换结点r的兄弟结点s是在x的左还是右子树的判断  对于x与r是双黑的情况下做的判断 

        // 情况1：s为黑 且至少有一个红孩子t   代号 BB-1  需要做一个3+4重构 和重染色操作
        if (isBlack(s) && (isRed(s.left) || isRed(s.right))) {
            reBalance34_BB1(x);
        }
        // 情况2：s为黑 s无红孩子  p为红   代号 BB-2R  不需要重构调整  只需要做重染色操作  r保持黑 s转红 p转黑
        if (isBlack(s) && isBlack(s.left) && isBlack(s.right) && isRed(x)){
            s.color = Color.RED;
            x.color = Color.BLACK;
            //先更新子结点的黑高度 再更新父节点的
            updateHeight(s);
            updateHeight(x);
        }
        // 情况3: s为黑 s无红孩子  p为黑   代号 BB-2B    s转红 r与p保持黑   从B树的角度看 本情况的结点删除 会发生结点下溢的失衡传播  但从红黑树角度看  本情况的修复不会改变其拓扑结构
        if (isBlack(s) && isBlack(s.left) && isBlack(s.right) && isBlack(x)){
            s.color = Color.RED;
        }
        //情况4：s为红  根据红黑树的定义 s为红 则s的孩子和父亲p 即x都为黑结点  先做zig或zag旋转 且在旋转变换后  红s转黑 黑p转红  
//    再把新p的左子树作为新的s  且新的p为红 s为黑 则可以再按情况归为BB-1情况或者BB-2R处理
        if (isRed(s)) {
            if (bn > 0) {
                x =rotateRight(x);//bn>0 则表示s在p的左子树中  则进行zig变换
//                bn>0 的情况 在zig变换后 新的p为新x的右子树  且新的s为新p的左子树
                Node new_s = x.right.left;
             // 情况1：s为黑 且至少有一个红孩子t   代号 BB-1  需要做一个3+4重构 和重染色操作
                if (isBlack(s) && (isRed(s.left) || isRed(s.right))) {
                    reBalance34_BB1(x.right);//情况4中的 本选择分支 新p为x.right
                }
                // 情况2：s为黑 s无红孩子  p为红   代号 BB-2R  不需要重构调整  只需要做重染色操作  r保持黑 s转红 p转黑
                if (isBlack(s) && isBlack(s.left) && isBlack(s.right) ){//情况4中 新p为x.right 再zig变换后 已经是红色的 不用做isRed(p)了
                    s.color = Color.RED;
                    x.right.color = Color.BLACK;//注意新p已经为x.right
                  //先更新子结点的黑高度 再更新父节点的
                    updateHeight(s);
                    updateHeight(x.right);
                }
            }
            else{
                x = rotateLeft(x);//反之bn<0 则表示s在p的右子树中  则进行zag变换
//              bn<0 的情况 在zag变换后 新的p为新x的左子树  且新的s为新p的右子树
              Node new_s = x.left.right;
           // 情况1：s为黑 且至少有一个红孩子t   代号 BB-1  需要做一个3+4重构 和重染色操作
              if (isBlack(s) && (isRed(s.left) || isRed(s.right))) {
                  reBalance34_BB1(x.left);//情况4中的 本选择分支 新p为x.left
              }
              // 情况2：s为黑 s无红孩子  p为红   代号 BB-2R  不需要重构调整  只需要做重染色操作  r保持黑 s转红 p转黑
              if (isBlack(s) && isBlack(s.left) && isBlack(s.right) ){//情况4中 本选择分支 新p为x.left 在zag变换后 已经是红色的 不用做isRed(p)了
                  s.color = Color.RED;
                  x.left.color = Color.BLACK;//注意新p已经为x.right
                //先更新子结点的黑高度 再更新父节点的
                  updateHeight(s);
                  updateHeight(x.left);
              }
            } 
        }
        
        
        
    }
//  zag局部变化   用于红黑树删除结点双黑修复的情况4 BB-3 
  private Node rotateLeft(Node x) {//BST旋转等价变换的 zag局部变换   以x为旋转中心 逆时针旋转rotateLeft(x)
//    发生旋转变换的结点主要是x和y  他们子树的平衡性不会发生改变  且子树中结点的高度的变化不需要在旋转中维护  只要在put回溯过程中维护就行  重要  想明白
//      在zag讲解中本x相当于旋转中心v y为c结点
//    x==v   y==c 一下一些讲解中 对应 邓俊辉 《数据结构》讲解图中的结点名称
    Node y = x.right;//发生旋转变化的另外一个结点 在zag中就是x的右子树的树根结点c
    x.right = y.left;//zag思路参考 邓俊辉 数据结构  笔记
    y.left = x;
    //旋转变化过程中 发现 x和y结点的子树的高度会发生变化  于是参考了《算法》中AVL树的源码 发现
    //不用想办法把x y左右子树的高度增加或者下降一个单位来使x和y的高度复合旋转后的变化    而是直接利用结点高度的定义 直接维护x和y的高度 
//    因为结点是一个一个的插入进AVL树的 所以 只维护x和y的高度 下一次插入x或y的子树结点时 子树的高度也会被本方法或者put方法所维护成和父结点高度只差一个单位的正确树形逻辑结构
  //维护发生旋转变换的x和y结点的n值
    y.n = x.n;//zig变换后  c的结点的n值和变换前v的n值相等
    x.n = 1 + size(x.left) + size(x.right);//变换后v的n值 需要重新计算维护
    // 红黑树中删除结点双黑修复的情况4 BB-3   旋转变换后 红s转黑 黑p转红
    x.color = Color.RED;
    y.color = Color.BLACK;
//    先维护子结点x的黑高度 再维护父结点的
    updateHeight(x);
    updateHeight(y);
    return y;//返回新的旋转中心c 即y结点  返回值再返回到put方法中去递归回溯的链接到原x结点的父结点    就完成了等价旋转变换
  }
  
//  zig局部变换
  private Node rotateRight(Node x) {//BST旋转等价变换的 zig局部变换   以x为旋转中心 顺时针旋转rotateRight(x)
//    发生旋转变换的结点主要是x和y  他们子树的平衡性不会发生改变  且子树中结点的高度的变化不需要在旋转中维护  只要在put回溯过程中维护就行   重要  想明白
//    在zag讲解中本x相当于旋转中心v y为c结点
//      x==v   y==c 一下一些讲解中 对应 邓俊辉 《数据结构》讲解图中的结点名称
      Node y = x.left;//发生旋转变化的另外一个结点 在zig中就是x的左子树的树根结点c 
      x.left = y.right;//zig思路参考 邓俊辉 数据结构  笔记
      y.right = x;
      //旋转变化过程中 发现 x和y结点的子树的高度会发生变化  于是参考了《算法》中AVL树的源码 发现
      //不用想办法把x y左右子树的高度增加或者下降一个单位来使x和y的高度复合旋转后的变化    而是直接利用结点高度的定义 直接维护x和y的高度 
//      因为结点是一个一个的插入进AVL树的 所以 只维护x和y的高度 下一次插入x或y的子树结点时 子树的高度也会被本方法或者put方法所维护成和父结点高度只差一个单位的正确树形逻辑结构
    //维护发生旋转变换的x和y结点的n值
      y.n = x.n;//zig变换后  c的结点的n值和变换前v的n值相等
      x.n = 1 + size(x.left) + size(x.right);//变换后v的n值 需要重新计算维护
      // 红黑树中删除结点双黑修复的情况4 BB-3   旋转变换后 红s转黑 黑p转红
      x.color = Color.RED;//黑p转红
      y.color = Color.BLACK;//红s转黑
//      先维护子结点x的黑高度 再维护父结点的
      updateHeight(x);
      updateHeight(y);
      return y;//返回新的旋转中心c 即y结点  返回值再返回到put方法中去递归回溯的链接到原x结点的父结点    就完成了等价旋转变换
  }
    private Node reBalance34_BB1(Node x) {
//        BB-1情况  r保持黑 a和c染黑  b继承p的原色
//      下面边界情况 空值空指针异常的代码 有时间看能不能优化一下 看着有点乱
        if (x == null) return null;
        Node a = null;
        Node b = null; 
        Node c = null;
        Node t0 = null;
        Node t1 = null;
        Node t2 = null;
        Node t3 = null;
        Color color_x = x.color;//用以记录p的原色
        int bn = x.left.b_height - x.right.b_height;
      //按zig zag分类 计算处3+4重构的参数a b c t0 t1 t2 t3
        if (bn < 0) { // 替换结点r位于x的左子树中 x.right为s
            Node s = x.right;
            if (isBlack(s) && isRed(s.left)) {//AVL树 zigzag双旋情况    BB-1情况
                 a = x;//能进此语句块来 则x.right  x.right.left就不会为空
                 b = x.right.left; 
                 c = x.left;//即 a和b不会为空 c可能为空
                 t0 = x.left;
                 t1 = b.left;
                 t2 = b.right;
                 if (c != null) //c可能为空
                 t3 = c.right;
//                 BB-1情况 重构后 还需要染色  染色后还需要再次维护黑高度
                 x = connect34(a, b, c, t0, t1, t2, t3);
                 a.color = Color.BLACK;
                 c.color = Color.BLACK;
                 b.color = color_x;//b继承p的原色
                 updateHeight(a);//先更新子结点的黑高度 再更新父节点的
                 updateHeight(c);
                 updateHeight(b);
                return x;
            }
            else if (isBlack(s) && isRed(s.right)){//AVL树 zagzag单旋    BB-1情况
              //能进此语句块来 则x.right  x.right.right就不会为空
                a = x;//也就是 a b c 都不会为空
                b = x.right; 
                c = x.right.right;
                t0 = x.left;//t0可能为空
                t1 = b.left;
                t2 = c.left;
                t3 = c.right;
                x = connect34(a, b, c, t0, t1, t2, t3);
                a.color = Color.BLACK;
                c.color = Color.BLACK;
                b.color = color_x;//b继承p的原色
                updateHeight(a);
                updateHeight(c);
                updateHeight(b);
               return x;
            }
        }
        else {//bn > 0 替换结点r位于x的右子树中
            if (isRed(x.left.right)) {//AVL树 zagzig双旋  
              //能进此语句块来 则x.left  x.left.right就不会为空
                a = x.left;//即a b c都不会为空
                b = a.right; 
                c = x;
                t0 = a.left;
                t1 = b.left;
                t2 = b.right;
                t3 = c.right;//t3可能为空
                x = connect34(a, b, c, t0, t1, t2, t3);
                a.color = Color.BLACK;
                c.color = Color.BLACK;
                b.color = color_x;//b继承p的原色
                updateHeight(a);//先更新子结点的黑高度 再更新父节点的
                updateHeight(c);
                updateHeight(b);
               return x;
               }
            else if (isRed(x.left.left)) {//AVL树 zigzig单旋  
                //能进此语句块来 则x.left  x.left.left就不会为空
                a = x.left.left;//即a b c都不会为空
                b = x.left; 
                c = x;
                t0 = a.left;
                t1 = a.right;
                t2 = b.right;
                t3 = c.right;//t3可能为空
                x = connect34(a, b, c, t0, t1, t2, t3);
                a.color = Color.BLACK;
                c.color = Color.BLACK;
                b.color = color_x;//b继承p的原色
                updateHeight(a);//先更新子结点的黑高度 再更新父节点的
                updateHeight(c);
                updateHeight(b);
                return x;
            }
        }
        return x;//未失衡 则可以直接返回x  也返回经过重平衡后的原x位置的平衡结点x 返回到put中递归回溯的链接到原x的父结点
        // TODO Auto-generated method stub
        
    }

    private int updateHeight(Node x){//更新结点x的高度  只更新黑高度
        x.b_height =  Math.max(b_height(x.left), b_height(x.right));//结点的高度为该节点的左右子树高度的较大者+1
        if (isBlack(x)) x.b_height++;//若x为黑结点  则黑高度还要自增1
        return x.b_height;
    }
    private boolean isBlack(Node x) {
        if (x == null) return true;
        return x.color.equals(Color.BLACK); 
    }
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color.equals(Color.RED); 
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
    public Key min(){//最小的键   同标准的基于BST的 符号表实现相同
        return min(root).key;
    }//而是利用重载的方法 来递归的实现    要了解递归基是什么  回溯过程 做了些什么  
    private Node min(Node x){//返回以x为根结点的(子)BST 中最小的键   可用于递归的实现 全BST树最小的键
        if (x.left == null) return x;
        return min(x.left);
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
        root = deleteMin(root);
    }
    private Node deleteMin(Node x){//x表示删除以x为根的子AVL树中键最小的结点       原理同BST的deleteMIn相同
//        返回值 就是经过删除最小键值对维护了BST的顺序性  和结点的属性n和height后的x的结点的引用
        if (x.left == null) {
            if (isBlack(x) && isBlack(x.right)) flag_delMin = true;//双黑则需要做双黑修复
            else {
                x.right.color = Color.BLACK;//不需要双黑修复 则只需要把替代的结点 置为黑色 就完成整个红黑树的修复工作
                updateHeight(x.right);//要维护黑高度
            }
            return x.right; 
        }
        x.left = deleteMin(x.left);
        x.n = size(x.left) + size(x.right) + 1;
        if (flag_delMin) solveDoubleBlack(x);//此flag_delMin控制在递归中 只会执行一次的双黑修复  很巧 是自己想的  注意理解
        //只能对要替换的结点x的父节点   也就是对上面找到要删除的x后的下一次的回溯中的x进行双黑修复
        flag_delMin = false;//一次的deleteMin只需要做一次的双黑修复 不需要在递归回溯中做多次   
        return x;
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
