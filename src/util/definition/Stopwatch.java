package util.definition;
/**
 * 时间分析工具  计时器
 * 秒表   
 * @author wjs13
 *构造函数保存了当前的时间 
 *elapsedTime能够返回自本类的一个对象创建以来所经过的时间
 *使用方法:  先创建计时器 秒表对象 要计时的过程完成  则elapsedTime按下秒表 返回该计时过程耗时秒数
 *这种利用组合的方式 创建秒表对象 比用  模版方法设计的计数器  要使测试类继承计数器才可行  的继承方法 要灵活 提高了代码的可维护性
 *能用组合就不要用继承  用组合和实现接口的方式 开发
 */
public class Stopwatch {
    private final long start;
    public Stopwatch(){
        start = System.currentTimeMillis();
    }
    public double elapsedTime(){//消逝的时间   消耗的时间
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;//返回 秒
    }
    
    
}
