package graph.definition;


/**
 * 有向图的可达性 
 * 单点可达性  多点可达性
 * 即 基于深度优先搜索的  单点可达性 和  多点可达性 查找从给定顶点(或顶点集)到指定顶点是否存在有向路径 即是否可达
 * @author wjs13
 *是s->指向外  s->x->y...->v最后能指向v则单点s可达到v表示
 *多点可达  就表示 集合中至少有一个顶点s能指向指定的顶点v  至少一个顶点s单点可达到v
 *
 *多点可达性  用于 内存管理 标记—清除的垃圾收集
 *其中 程序执行的任何时候可以被直接访问的对象 就是 我们这里的 顶点集source
 *指定的顶点v 就是我们要判断是否应该满足可达性 是否需要被回收的对象
 *只要source中至少有一个(顶点s)能指向要判断的对象(顶点v)  那么就是多点可达的    该被判断可达性的对象(顶点v)暂时就不能够被回收
 */
public class DirectedDFS {
    private boolean[] marked;//标记所有顶点 是否被访问     false没有被访问
    
    //可以解决  单点可达性  深度优先搜索后  用marked(v)就能知道是否存在一条从s到达给定顶点v的有向路径  返回true才有 
    public DirectedDFS(Digraph g, int s){//注意是s->指向外  s->x->y...->v最后能指向v则单点s可达到v表示
        marked = new boolean[g.v()];//默认标记数组 全部初始化为false  表示没有顶点被访问过  即没有经过dfs搜索过
        dfs(g, s);//开始基于深度搜索的   以s为起点 寻找s的路径
    }
    
    //可以解决  多点可达性  深度优先搜索后  用marked(v)就能知道是否存在一条从集合sources中的任意顶点s到达给定顶点v的有向路径  返回true才有 
    public DirectedDFS(Digraph g, Iterable<Integer> sources){
        marked = new boolean[g.v()];
        for (int s : sources){//任意一点可达 就是集合的多点可达  就表示 集合中至少有一个顶点s能指向指定的顶点v
            if (!marked[s]) dfs(g, s);//只要集合source中任意顶点s经深度优先搜索后使marked(v)为true  就是多点可达性
        }
    }
    
    private void dfs(Digraph g, int v){//DFS 遍历 递归 邻接顶点的过程
        marked[v] = true;
      //对v的邻接顶点做递归的DFS深度优先搜索
        for (int w : g.adj(v)){//有向图中 adj(v) 表示 由v 指出(即v为尾) 的邻接顶点的集合
            if (!marked[w]){
                dfs(g, w);
            }               
        }
    }
    //在经过图的深度优先搜索dfs(g,v)后  就能用此方法判断 可达性
    public boolean marked(int v){
        return marked[v];
    }
    
    
}
