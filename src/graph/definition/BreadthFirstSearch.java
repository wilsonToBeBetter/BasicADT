package graph.definition;

import queue.definition.Queue;
import stack.definition.Stack;

/**
 * 图    广度优先搜索    是解决很多图算法问题的基本框架
 * 可解决 单点最短路径的问题
 * 访问顶点s 
 * 依次访问s 所有 尚未访问的 邻接顶点
 * 依次访问他们尚未访问的邻接顶点
 * 如此反复 直至没有尚未访问的邻接顶点
 * @author wjs13
 *edgeTo;//可以表示从起点到一个顶点的已知路径上的最后一个顶点的路径向量     且在BFS中可以表示起点s到与其他所有顶点的最短路径
 *广度优先搜索则像扇面一般扫描图 用一个队列保存访问过的最前端的顶点
 */
public class BreadthFirstSearch {
    private boolean[] marked;//标记所有顶点 是否被访问 false没有被访问
    private int[] edgeTo;//可以表示从起点到一个顶点的已知路径上的最后一个顶点的路径向量  可以表示起点s到与其他所有顶点的最短路径
    private final int s;//起点
    
    public BreadthFirstSearch(Graph g, int s){
        this.s = s;
        marked = new boolean[g.v()];//默认标记数组 全部初始化为false  表示没有顶点被访问过  即没有经过bfs搜索过
        edgeTo = new int[g.v()];//g.v()表示图总结点的总数
        bfs(g, s);//开始基于广度搜索的   以s为起点 寻找s的路径
    }
    private void bfs(Graph g, int v){//BFS 依次 迭代遍历 邻接顶点    可以得到起点s到与其他所有顶点的最短路径
        Queue<Integer> queue = new Queue<Integer>();
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty()){//这个循环就是 广度优先遍历的核心  此过程和树的层序遍历是一样的
            int ve = queue.dequeue();//从队列中删除下一个顶点  顶点ve就是下一轮广度搜索的起点
            for (int w : g.adj(ve)){
                if (!marked[w]){
                edgeTo[w] = ve;//表示在广度遍历过程中 w是ve的邻接结点 广度搜索的过程是从ve搜到w的  ve是每一轮BFS的起点
//         在广度优先搜索算法中 edgeTo[]表示从起点s到顶点ve最后到顶点w 且表示的s到w的路径是最短的路径(相对于DFS中edgeTo[]存的路径)
                marked[w] = true;//已访问标志置1
                queue.enqueue(w); 
                }
            }  
        }
        
    }
    
    public boolean marked(int w){
        return marked[w];
    }
    public boolean hasPathTo(int v){//给定结点v是否与起点s连通  即是否与起点s构成路径
        return marked[v];
    }
  //遍历从s到任意和s连通的定点v的路径上的所有顶点   和DFS中实现相同
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
