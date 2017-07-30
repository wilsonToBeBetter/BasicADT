package stack.test;

import stack.application.ExpEvaluate;

public class ExpEvaluateTest {
    
    public static void main(String[] args) {
        ExpEvaluate expEvaluate = new ExpEvaluate("((3.07+(15.03*7.60))-110.07)");
        System.out.println(expEvaluate.evaluate());
    }
    
}
