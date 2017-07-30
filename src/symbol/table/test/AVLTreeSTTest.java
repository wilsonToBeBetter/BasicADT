package symbol.table.test;

import symbol.table.definition.AVLTreeST;


public class AVLTreeSTTest {

    public static void main(String[] args) {
        AVLTreeST<Integer, String>  avlTree= new AVLTreeST<Integer, String>();
        avlTree.put(1, "w");
        avlTree.put(22, "i");
        avlTree.put(3, "l");
        avlTree.put(4, "s");
//        avlTree.put(23, "o");
        avlTree.put(6, "n");
//        avlTree.put(7, "t");
        avlTree.put(33, "c");
        avlTree.put(9, "c");
//        avlTree.put(43, "c");
        avlTree.put(11, "c");
//        avlTree.delete(4);
        System.out.println(avlTree.isAVL());
        System.out.println(avlTree.isBST());
        System.out.println(avlTree.height());
        for (Integer a : avlTree.keys()){
            System.out.print(avlTree.get(a));
        }
    }
}
