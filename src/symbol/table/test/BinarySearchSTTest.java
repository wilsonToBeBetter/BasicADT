package symbol.table.test;

import symbol.table.definition.BinarySearchST;

public class BinarySearchSTTest {

    public static void main(String[] args) {
        BinarySearchST<Integer,String> binarySearchST = new BinarySearchST<Integer, String>();
        binarySearchST.put(1, "w");
        binarySearchST.put(32, "i");
        binarySearchST.put(3, "l");
        binarySearchST.put(45, "s");
        binarySearchST.put(5, "o");
        binarySearchST.put(9, "n");
        for (Integer key : binarySearchST.keys()){
            System.out.print(binarySearchST.get(key) + " ");
        }
        System.out.println();
        System.out.println(binarySearchST.max());
        System.out.println(binarySearchST.min());
        System.out.println(binarySearchST.contains(12));
        binarySearchST.delete(9);
        for (Integer key : binarySearchST.keys()){
            System.out.print(binarySearchST.get(key) + " ");
        }
        System.out.println();
        System.out.println(binarySearchST.ceiling(46));//大于等于key的最小键
        System.out.println(binarySearchST.floor(-1));//小于等于key的最大键
        binarySearchST.deleteMax();
        for (Integer key : binarySearchST.keys()){
            System.out.print(binarySearchST.get(key) + " ");
        }
        binarySearchST.deleteMin();
        System.out.println();
        for (Integer key : binarySearchST.keys()){
            System.out.print(binarySearchST.get(key) + " ");
        }
        System.out.println();
        System.out.println(binarySearchST.max());
        System.out.println(binarySearchST.min());
        System.out.println(binarySearchST.select(3));//排名为k的键
        System.out.println(binarySearchST.size(3, 32));//[lo..hi]之间键的数量
        Iterable<Integer> keys = binarySearchST.keys(3, 32);
        for (Integer a : keys){
            System.out.print(a+" ");
        }
    }
}
