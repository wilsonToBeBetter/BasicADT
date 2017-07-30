package stack.application;

import java.util.Stack;


/**
 * 中缀表达式求值   支持+ - * 除 多位小数double型数据的运算
 * 栈的应用  中缀表达式的 ”解释“ 求值  利用操作数栈和操作符栈 两个栈   线性扫描算法
 * 表达式不能省略任何的括号  即所有的运算符优先级要加左右括号表示  eg: "((3.07+(15.03*7.60))-110.07)"
 * 开始的版本Stack<Double> vals = new Stack<Double>();  把操作数栈定义成Double来处理  在处理多位小数如10.05 10.55等处理很不方便 
 * 后来改成Stack<String> vals = new Stack<String>();  用字符串处理操作数很方便  多位小数 多位数 直接利用字符串的拼接 不用考虑数字位的权值等问题 
 * @author wjs13
 *
 */
public class ExpEvaluate {
    
    private String exp;//表达式 字符串
    public ExpEvaluate(String exp) {
        super();
        this.exp = exp;
    }
    
    public double evaluate() {
        Stack<String> ops = new Stack<String>();//操作符栈
        Stack<String> vals = new Stack<String>();//操作数栈  操作数做String字符串处理 方便 计算时再转换成double运算
        int len = exp.length();//表达式长度 也就是char数组的长度  java中string为char数组 且一般字母用一个字节表示 中文用unicode-2编码占两个字节
        int flag = 0;//当前线性扫描到的字符的状态   0表示未开始扫描状态0 1扫描到操作符    2扫描到一个数字    3扫描到小数点
//        + - * 、/ ( )都算操作符
        for (int i = 0; i < len; i++){//开始顺序扫描表达式
//            读取字符 如果是运算符则压入运算符栈
            String s = String.valueOf(exp.charAt(i));//char转换成String来处理  方便
            if (s.equals("(")) {flag = 1;}//忽略运算表达式的左括号 只把状态置1表示本次扫描到一个操作符
            else if (s.equals("+")) {ops.push(s);flag = 1;}//flag置1表示本次扫描到的是一个操作符
            else if (s.equals("-")) {ops.push(s);flag = 1;}
            else if (s.equals("*")) {ops.push(s);flag = 1;}
            else if (s.equals("/")) {ops.push(s);flag = 1;}
            else if (s.equals(".")) {flag = 3;}//扫描到小数点 不做处理忽略 只把状态置3 表示本次扫描到小数点
            else if (s.equals(")")){
//                如果字符为")" 弹出运算符和相应的操作数 计算结果并再压入操作数栈
                String op = ops.pop();
                String value = vals.pop();//栈顶的String类型的操作数
                Double v = Double.valueOf(value);//String操作数转换成Double来运算
                if (op.equals("+")) v = Double.valueOf(vals.pop()) + v;//有操作符需要多个操作数 再从栈顶弹出所需的操作数参与运算
                else if (op.equals("-")) v = Double.valueOf(vals.pop()) - v;
                else if (op.equals("*")) v = Double.valueOf(vals.pop()) * v;
                else if (op.equals("/")) v = Double.valueOf(vals.pop()) / v;
                vals.push(v.toString());
                flag = 1;//flag置1表示本次扫描到的是一个操作符
            }
            
//            如果字符非括号、小数点和操作符那么就是操作数
            else {//本次扫描到的是一位十进制的数字   多位操作数 利用弹出栈顶元素做字符串拼接处理后再次入栈
                    if (flag == 1){//表示前一次线性扫描表达式压入的是操作符   则本次扫描到的操作数直接入栈
                        vals.push(s);
                        flag = 2;//flag置2表示本次扫描到的是一位十进制的操作数
                    }
                    //利用Double类型的容器Stack来处理如下两种多位小数的操作数 会非常麻烦且可能无法处理完美  用String的Stack就非常完美处理
                    else if (flag == 2) {//表示前一次线性扫描到的是数字操作数 
//     若下一次线性扫描还是扫描到一个操作数 则表示是一个多位数的十进制数 需要弹出操作数栈顶元素利用字符串拼接处理 拼接成多位十进制数
                        String num = vals.pop();//弹出栈顶操作数
                        vals.push(num + s);//字符串拼接数字
                        flag = 2;//flag置true表示本次扫描到的是一位十进制的操作数
                    }
                    else if (flag == 3) {//表示前一次线性扫描到的是小数点 此时需要对操作数栈顶的数字做处理 
                        String num = vals.pop();//弹出栈顶操作数
                      //字符串拼接数字  因前一次扫描到小数点  固本次要拼接小数点到数字中
                        vals.push(num + "." + s);
                        flag = 2;//flag置true表示本次扫描到的是一位十进制的操作数
                    }
            }
        }
        double result = Double.valueOf(vals.pop());//经过上面循环解释计算 最后一个在操作数栈中的数就是表达式计算的结果
        return result;
    }
    
}
