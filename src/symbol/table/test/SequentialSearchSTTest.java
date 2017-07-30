package symbol.table.test;

import symbol.table.definition.SequentialSearchST;

public class SequentialSearchSTTest {

    public static void main(String[] args) {
       SequentialSearchST<String,Integer> sequentialSearchST = new SequentialSearchST<String, Integer>();
       sequentialSearchST.put("w", 1);
       sequentialSearchST.put("i", 2);
       sequentialSearchST.put("l", 3);
       sequentialSearchST.put("s", 4);
       sequentialSearchST.put("o", 5);
       sequentialSearchST.put("n", 6);
//       System.out.println(sequentialSearchST.contains("t"));
       for (String s : sequentialSearchST.keys()){
           System.out.print(sequentialSearchST.get(s));
       }
       
       System.out.println();
       
       sequentialSearchST.put("w", 7);
       sequentialSearchST.put("o", 9);
       sequentialSearchST.put("j", 11);
       sequentialSearchST.delete("w");
       sequentialSearchST.delete("i");
       sequentialSearchST.delete("l");
       sequentialSearchST.delete("s");
       sequentialSearchST.delete("o");
       sequentialSearchST.delete("n");
       sequentialSearchST.delete("j");
//       sequentialSearchST.delete("j");
       System.out.println(sequentialSearchST.isEmpty());
       for (String s : sequentialSearchST.keys()){
           System.out.print(sequentialSearchST.get(s));
       }
       
    }
}
