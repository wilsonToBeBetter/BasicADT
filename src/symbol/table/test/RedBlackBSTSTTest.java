package symbol.table.test;

import symbol.table.definition.RedBlackBSTST;

public class RedBlackBSTSTTest {

    public static void main(String[] args) {
        RedBlackBSTST<Integer,String> redBlackBSTST = new RedBlackBSTST<Integer, String>();
        redBlackBSTST.put(1, "w");
        redBlackBSTST.put(2, "i");
        redBlackBSTST.put(3, "l");
        redBlackBSTST.put(4, "s");
        redBlackBSTST.put(5, "o");
        redBlackBSTST.put(6, "n");
        System.out.println(redBlackBSTST.get(6));
        System.out.println(redBlackBSTST.get(5));
        System.out.println(redBlackBSTST.get(4));
        System.out.println(redBlackBSTST.get(3));
        System.out.println(redBlackBSTST.get(2));
        System.out.println(redBlackBSTST.get(1));
    }
}
