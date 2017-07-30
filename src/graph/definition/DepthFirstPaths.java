package graph.definition;

import stack.definition.Stack;

/**
 * 寻找路径 算法
 * 给定顶点s
 * 计算s到与s连通的每一个顶点之间的路径
 * 基于 深度优先搜索DFS 的 查找图中路径
 * 顶点编号为Integer(int)值
 * @author wjs13
 *edgeTo;//可以表示从起点到一个顶点的已知路径上的最后一个顶点的路径向量   在DFS中 只是一条路径 并不是最短路径
 */
public class DepthFirstPaths {
    private boolean[] marked;//标记所有顶点 是否被访问 false没有被访问
    private int[] edgeTo;//可以表示从起点到一个顶点的已知路径上的最后一个顶点的路径向量
    private final int  s;//起点编号为s
    
    public DepthFirstPaths(Graph g, int s){
        this.s = s;
        marked = new boolean[g.v()];//默认标记数组 全部初始化为false  表示没有顶点被访问过  即没有经过dfs搜索过
        edgeTo = new int[g.v()];//g.v()表示图总结点的总数
        dfs(g, s);//开始基于深度搜索的   以s为起点 寻找s的路径
    }
    private void dfs(Graph g, int v){//DFS 遍历 递归 邻接顶点的过程
        marked[v] = true;
        for (int w : g.adj(v)){//对v的邻接顶点做递归的DFS深度优先搜索
            if (!marked[w]){
                edgeTo[w] = v;//表示在深度遍历过程中 w是v的邻接结点 深度搜索的过程是从v搜到w的  v是每一轮DFS的起点
// 在广度优先搜索算法中 edgeTo[]表示从起点s到顶点ve最后到顶点w 且表示的s到w的路径是最短的路径   而对于DFS中edgeTo[]存的路径并不是最短路径
                dfs(g, w);
            }               
        }
    }
    
    public boolean marked(int w){
        return marked[w];
    }
    public boolean hasPathTo(int v){//给定结点v是否与起点s连通  即是否与起点s构成路径
        return marked[v];
    }
  //遍历从s到任意和s连通的定点v的路径上的所有顶点
    public Iterable<Integer> pathTo(int v){
        if (!hasPathTo(v)) return null;//与起点s非连通的顶点v 则无路径返回 
        Stack<Integer> path = new Stack<Integer>();//v到起点s的路径依次压入栈中 则栈pop输出即可以表示起点s到v的路径
        for (int i = v; i != s; i = edgeTo[i]){//edgeTo[i]表示与i连接的(DFS过程中的)上一个顶点 一直遍历到起点s 都压入栈中
            path.push(i);
        }
        path.push(s);//遍历完DFS的路径后 退出for循环 再添加上起点s  栈中就存储了s到任意和s连通的定点v的路径上的所有顶点
        return path;
    }

}
