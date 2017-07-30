package graph.definition;
/**
 * 无向图
 * 图  数据结构   图在计算机中的表示与实现   
 * 基于  邻接表数组  主要用于  疏密图    而稠密图 则多基于邻接矩阵
 * 顶点编号为Integer(int)值   且图中顶点编号是从0到(v-1)连续编号的  v个顶点
 * 若顶点是字符串  则我们可以设计index(String key)来把顶点值key映射到邻接表adj数组的int值的索引  可以不用重新设计ST型的邻接表  但可能效率有所下降
 */
import java.io.InputStream;

import bag.definition.Bag;

public class Graph {
    
    private int v;//顶点数目
    private int e;//边的数目
    //一种更好的邻接表 是利用符号表ST 键来存顶点 值用Set来存与键顶点邻接的顶点链表  可以支持添加删除顶点更多的操作
    private Bag<Integer>[] adj;//邻接表数组  此种数据结构 是用顶点编号值作为数组索引的 数组中元素都是和该顶点相邻的顶点列表  注意 
//  Bag底层是链表 邻接矩阵适用于稠密图
    
    public Graph(int v){//创建一个含有v个顶点但不含有边的图
       this.v = v;
       e = 0;
       adj = (Bag<Integer>[])new Bag[v];//因为这样定义 adj[i]都为被默认初始化为null  所以还需要对邻接表进行显示初始化为空链表
       for (int i = 0; i < v; i++){
           adj[i] = new Bag<Integer>();//显示初始化为空链表
       }
    }
    public Graph(InputStream in){//从标准输入流in读入一副图
        
    }
    public int v(){//顶点数
       return v; 
    }
    public int e(){//边数
        return e;
    }
    public void addEdge(int v, int w){//向图中添加一条边v-w
      //无向图  添加一条边 则v和w都要在邻接表中互相记录它们的邻接顶点
//        顶点编号为Integer(int)值   且编号是从0到(v-1)连续编号的  v个顶点
      adj[v].add(w);//adj邻接表  此种数据结构 是用顶点编号值作为数组索引的  注意 
      adj[w].add(v);
      e++;
    }
    public Iterable<Integer> adj(int v){//和v相邻的所有顶点
        return adj[v];//注意v就是邻接表adj数组的索引值  对应数组元素存储的就是与v相邻的所有的顶点
    }
    public String toString(){
        //TODO
        return null;
    }
    
    
    
    
}
