package bag.test;

import bag.definition.Bag;

public class BagTest {

    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        bag.add("a");
        bag.add("b");
        bag.add("c");
        bag.add("d");
        for( String b : bag){
            System.out.println(b);
        }
    }
}
