package priority.queue.definition;

import java.util.NoSuchElementException;
/**
 * 二叉堆 实现优先级队列    最大二叉堆
 * 二叉堆 逻辑结构为—— 完全二叉树       物理上用——顺序存储的数组可以实现完全二叉树
 * 完全二叉树不存在只有右子树的情况   最下层叶子一定集中在左部连续位置
 * 完全二叉树性质:  有n个结点的完全二叉树  按层序(从上到下 从左到右)给结点编号1、2、3......n  编号为i  对任意结点i 有
 * 1、 i > 1 则 i的父结点为下整[i/2] 即floor(i / 2.0)
 * 2、 i * 2 <= n 则 2i 为i的 左孩子 结点     i * 2 > n 无左孩子
 * 3、 i * 2 + 1 <= n 则  i*2+1 为i的 右孩子 结点    i * 2 + 1> n 无右孩子
 * 注意层序编号i和数组秩差1
 * 
 * 二叉堆 插入元素  用 上滤 实现
 * 要保证：
 * 1、逻辑结构上保证   完全二叉树的结构性(即满足上面的完全二叉树的性质)
 * 2、优先级次序上保证       堆序性    大堆 小堆
 * 大堆:即任何完全二叉树结点在数值上不得超过它的父亲     即树根元素是全局最大的元素
 * 小堆:即任何完全二叉树结点在数值上不得小于它的父亲     即树根元素是全局最小的元素
 * 
 * 二叉堆 删除元素    即弹出根元素并删除 且始终使二叉堆的完全二叉树的结构性和堆序性保持   即复原二叉堆结构性和堆序性
 * 用 下滤 实现  被删除的根节点先用二叉堆尾元素替代 即array[n-1]替代    再下滤 保证堆序性和完全二叉树的结构性
 * 
 * 本MaxPQ利用了 自下而上 自右而左的 下滤  高效的实现了  public MaxPQ(T[] a){//批量建立 二叉堆  heapification
 * @author wjs13
 *代码全部是自己写的 只参考了 邓俊辉《数据结构》中讲解的思路 没有参考任何的代码
 *自己写得很好 很好理解  具有学习意义
 * @param <T>
 */
public class MaxPQ<T extends Comparable<T>> {
    private T[] array ;//二叉堆底层数组 可以通过resize扩容
    
    private int n ;//MaxPQ最大二叉堆优先级队列中的元素的个数
    
    public void insert(T key){//按优先级顺序插入元素到MaxPQ最大二叉堆   始终保证  完全二叉树的 结构性 和  堆序性
//        完全二叉堆 插入元素 就是 上滤  的过程
        array[n++] = key;//先插入
        int i = n  ;//要插入二叉堆元素的结点的编号   即可指示尾元素上滤过程中的完全二叉树层序编号  注意不是数组的秩    i和数组的秩相差一个1
      //本while实现 根据 完全二叉树的性质
        while (i != 1){   //注意i为层序编号 从1开始 不是指数组的秩 一开始弄错了    上滤直到根编号1
            int j = (int)Math.floor(i / 2.0);//i的父结点秩为j
            if (less(array[j - 1], array[i - 1])){//注意i和数组的秩相差一个1
                exch(array, i - 1, j - 1);//注意i和数组的秩相差一个1
                i = j;//此处是层序编号的交换 不单单理解成 数组秩的交换
            }
            else break;
        }
        if (array.length == n) resize(2 * n);//数组扩容
    }
    public T delMax(){//找到MaxPQ并删除最大元素    采用二叉堆实现   即弹出根元素并删除 且始终使二叉堆的完全二叉树的结构性和堆序性保持
        //二叉堆 删除元素  就是   下滤  的过程
        if (n == 0) throw new NoSuchElementException("MaxPQ is underflow!");//下溢异常处理 增加健壮性
        T maxKey = (T) array[0];//最大二叉堆根结点 即为MaxPQ中最大的数   根结点也就是底层数组秩为0的元素
        exch(array, 0, n - 1);//被删除的根节点先用二叉堆尾元素替代 即array[n-1]替代
        array[n - 1] = null;//再把MaxPQ尾元素置null删除  也可防止内存泄漏
        n--;//二叉堆 元素数量 减1
        //再把根结点  下滤  保证堆序性 
        int i = 1;//指示根结点下滤过程中的层序编号  从1开始  并不是数组的秩  i和数组的秩相差一个1
      //本while实现 根据 完全二叉树的性质    2 * i > n  则i为叶子结点  注意i为层序编号  不是数组的秩
        while (2 * i <= n){//在结点i有左孩子的情况下     即秩为i的结点不是叶子结点  且左孩子秩为2 * i
            int maxChild = 2 * i;//值较大的孩子的秩  开始假设为左孩子的秩
            if (2 * i + 1 <= n){//若结点i也有右孩子    2 * i + 1为右孩子秩
              //注意i和数组的秩相差一个1
               maxChild = less(array[2 * i - 1],array[2 * i + 1 - 1]) ? (2 * i + 1) : (2 * i) ; //找出左右孩子的较大的孩子的秩
//               即使两个孩子结点的值相同 结点i下滤到任何一个孩子结点的子树 都不会影响整体的堆序性  这点可以放心
            }
            if (less(array[i - 1], array[maxChild - 1])){//i和数组的秩相差一个1
                exch(array, i - 1, maxChild - 1);
                i = maxChild;//此处是层序编号的交换 不单单理解成 数组秩的交换
            }
            else break;
        } 
        if (n == array.length / 4) resize(array.length / 2);//缩容
        return maxKey;
    }
    public T getMax(){//找到MaxPQ最大元素       采用二叉堆实现   找出根元素即可
        return (T) array[0];
    }
    
    private void exch(Comparable[] a, int i, int j){//交换元素 抽取出的通用方法
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    private boolean less(Comparable key1, Comparable key2){
        return key1.compareTo(key2) < 0;
    }
    private void resize(int capacity){
        T[] temp = (T[]) new Comparable[capacity];
        for (int i = 0; i < n; i++){
            temp[i] = array[i];
        }
        array = temp;
    }
    public MaxPQ(){
        array = (T[]) new Comparable[1];
        n = 0;
    }
    public MaxPQ(int size){
        array = (T[]) new Comparable[size];
        n = 0;
    }
    public MaxPQ(T[] a){//批量建立 二叉堆  heapification
       /* //蛮力实现  自上而下 、自左而右的上滤  即一个一个调用完全二叉堆的插入上滤算法insert  效率低下
        int len = a.length;
        n = 0;//利用的是插入上滤 未插入之前 表示MaxPQ队列为空 虽然数组a长度不为空
        array = a;//根据 插入上滤的 算法  可以就地的利用a进行 二叉堆的建立  不用建立一个新的数组   且最终也是数组a完成了堆序性
        for (int i =0; i< len; i++){
            insert(a[i]);
        }*/
        
        //自下而上 自右而左的 下滤  高效 实现 批量建立二叉堆   思想为delMax()中后半部分代码所做的尾结点替换根节点后进行的下滤 过程   也即子堆的逐层合并
        n = a.length;//利用的是下滤 一开始就可以设置二叉堆长度为数组a的长度
        array = a;//就地利用数组a进行 二叉堆的建立
      //只需要对数组a组成的逻辑完全二叉树的内部结点开始  直到根节点 进行子堆的逐层合并
        for (int i = (int) Math.floor(n / 2.0); i >=1; i--){//完全二叉树最末尾的内部结点的层序编号为下整[n/2]   树根层序编号为1
//            此下代码和delMax后半部分一样  是一样的思想和算法
        while (2 * i <= n){//在结点i有左孩子的情况下     即秩为i的结点不是叶子结点  且左孩子秩为2 * i
            int maxChild = 2 * i;//值较大的孩子的秩  开始假设为左孩子的秩
            if (2 * i + 1 <= n){//若结点i也有右孩子    2 * i + 1为右孩子秩
              //注意i和数组的秩相差一个1
               maxChild = less(array[2 * i - 1],array[2 * i + 1 - 1]) ? (2 * i + 1) : (2 * i) ; //找出左右孩子的较大的孩子的秩
//               即使两个孩子结点的值相同 结点i下滤到任何一个孩子结点的子树 都不会影响整体的堆序性  这点可以放心
            }
            if (less(array[i - 1], array[maxChild - 1])){//i和数组的秩相差一个1
                exch(array, i - 1, maxChild - 1);
                i = maxChild;//此处是层序编号的交换 不单单理解成 数组秩的交换
            }
            else break;
        }
        }
    }
    public boolean isEmpty(){
        return n == 0;
    }
    public int size(){
        return n;
    }
}
