package symbol.table.test;

import symbol.table.definition.BST;

public class BSTTest {

    public static void main(String[] args) {
        BST<Integer, String> bst = new BST<Integer, String>();
        bst.put(1, "w");
        bst.put(7, "i");
        bst.put(3, "l");
        bst.put(9, "s");
        bst.put(4, "o");
        bst.put(2, "n");
//        System.out.println(bst.max());
//        System.out.println(bst.min());
        for (Integer a : bst.keys()){
            System.out.print(bst.get(a) + " ");
        }
        System.out.println();
//        bst.delete(1);
//        bst.delete(5);
//        bst.deleteMax();
       /* Integer ceiling = bst.ceiling(6);
        System.out.println(ceiling);*/
        /*Integer floor = bst.floor(6);
        System.out.println(floor);*/
//        bst.printKey();
//        System.out.println(bst.size());
//        System.out.println(bst.rank(2));
        for (Integer a : bst.keys(3,5)){
            System.out.print(bst.get(a) + " ");
        }
        System.out.println();
        
        
    }
}
