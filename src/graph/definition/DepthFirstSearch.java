package graph.definition;
/**
 * 图  深度优先搜索  是解决很多图算法问题的基本框架
 * 可解决 路径检测  单点路径 等问题
 * 访问顶点s 若s尚有未被访问的邻接结点  则任取其一u  递归执行dfs(u)  否则回溯 继续遍历或完成返回
 * 顶点编号为Integer(int)值
 * @author wjs13
 *深度优先搜索不断深入图中并在栈中保存了所有分叉的顶点
 */
public class DepthFirstSearch {

    private boolean[] marked;//标记所有顶点 是否被访问 false没有被访问
    private int count;//其值表示  以s为一个顶点的图g中的一个连通子图的顶点总数
    public DepthFirstSearch(Graph g, int s){
        marked = new boolean[g.v()];//默认标记数组 全部初始化为false  表示没有顶点被访问过
        dfs(g, s);
    }
    private void dfs(Graph g, int v){//DFS 遍历 递归 邻接顶点的过程
        marked[v] = true;
        count++;
        for (int w : g.adj(v)){//对v的邻接顶点做递归的DFS深度优先搜索
            if (!marked[w]) dfs(g, w);
        }
    }
    
    public boolean marked(int w){
        return marked[w];
    }
    public int count(){
        return count;
    }
    
}
