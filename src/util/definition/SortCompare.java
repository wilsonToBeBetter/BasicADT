package util.definition;

import sort.definition.Heap;
import sort.definition.Insertion;
import sort.definition.Merge;
import sort.definition.Quick;
import sort.definition.Selection;
import sort.definition.Shell;
/**
 * sort算法性能比较工具
 * 在对比Insertion和Selection和Shell中  发现选择排序快 次之插入排序 希尔排序慢  不符合理论的性能
 * 在网上查后 发现应该是javac和jvm优化导致的  看网上说 换到matlab下试验排序算法性能就行了
 * @author wjs13
 *排序100个不同的长度为1000的随机double数组
 *算法Merge的速度是算法Selection的3.6倍   发现归并算法还是符合理论性能的
 *Quick算法还是最快的  特别是在大数据量排序 Quick是其他初级排序算法的几百上千、万倍
 *排序10个不同的 长度为1000000的随机double数组
 *算法Quick的速度是算法Merge的1.2倍
 */
public class SortCompare {
    
    /**
     * 用算法alg对Comparable[] a排序所耗时间
     * @param alg
     * @param a
     * @return
     */
    public static double time(String alg, Comparable[] a){
        Stopwatch start = new Stopwatch();
        switch (alg){
        case "Selection" : Selection.sort(a); break;
        case "Insertion" : Insertion.sort(a); break;
        case "Merge" : Merge.sort(a); break;
        case "Shell" : Shell.sort(a); break;
        case "Quick" : Quick.sort(a); break;
        case "Heap" : Heap.sort(a); break;
        }
        return start.elapsedTime();//单位秒
    }
    
    public static double timeRandomInput(String alg, int n, int t){
//        使用算法alg 将t个长度为n的数组排序   t个长度为n的数组 排序t次 返回总时间
        double total = 0.0;//一个算法执行多次排序的总时间  单位秒
        Double[] a = new Double[n];
        for (int i = 0; i < t; i++)
        {  //进行一次测试  生成一个长度为n随机的double数组并排序
            for (int j = 0; j < n; j++){
                a[j] = Math.random();
            }
            total += time(alg, a);
        }
        return total;
    }
    
    public static void main(String[] args) {
        String alg1 = args[0];
        String alg2 = args[1];
        int n = Integer.parseInt(args[2]);
        int t = Integer.parseInt(args[3]);
        double t1 = timeRandomInput(alg1, n, t);//算法alg1 排序t次   t个长度为n的数组   耗时总时间
        double t2 = timeRandomInput(alg2, n, t);//算法alg2 排序t次   t个长度为n的数组   耗时总时间
        //输出 算法alg1是算法alg2执行排序速度的几倍
        System.out.printf("排序%d个不同的 长度为%d的随机double数组\n", t, n);
        System.out.printf("算法%s的速度是算法%s的%.1f倍\n", alg1, alg2, t2 / t1);
        
    }
}
