package symbol.table.test;

import symbol.table.definition.BinarySearchST;
import symbol.table.definition.Item;

public class BinarySearchSTTestTwo {

    public static void main(String[] args) {
        Item<Integer, String> item5 = new Item<Integer, String>(1, "w");
        Item<Integer, String> item2 = new Item<Integer, String>(2, "i");
        Item<Integer, String> item6 = new Item<Integer, String>(3, "l");
        Item<Integer, String> item4 = new Item<Integer, String>(4, "s");
        Item<Integer, String> item3 = new Item<Integer, String>(5, "o");
        Item<Integer, String> item1 = new Item<Integer, String>(6, "n");
        Item<Integer, String>[] items = new Item[]{item1,item2,item3,item4,item5,item6};
        BinarySearchST<Integer,String> binarySearchST = new BinarySearchST<Integer, String>(items);
        for (Integer key : binarySearchST.keys()){
            System.out.print(binarySearchST.get(key) + " ");
        }
       
    }
}
