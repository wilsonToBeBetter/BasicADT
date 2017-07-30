package stack.test;

import stack.definition.Stack;

public class StackTest {

    public static void main(String[] args) {
        Stack<String> a = new Stack<String>();
        a.push("a");
        a.push("b");
        a.push("c");
        a.push("d");
        System.out.println(a);//Stack的toString()方法用到了foreach
    }
}
