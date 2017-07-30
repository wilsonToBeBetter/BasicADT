package misc;

import java.util.Arrays;

import org.junit.Test;

/**
 * 复制数组
 * 颠倒数组元素的顺序
 * 方阵相乘
 * @author wjs13
 *
 */
public class Demo1 {

    @Test
    public void test() {
        // System.out.println(5.0f/3.0f);
        double[] nums = { 1.0, 2.0, 3.0, 3.3, 5, 1.1, 9, 5.2, 11 };
        System.out.println(findMax(nums));
        System.out.println(avgNums(nums));
        double[] newNums = copyArray(nums);
        newNums[5] = 100;
        System.out.println(Arrays.toString(newNums));
        System.out.println(Arrays.toString(nums));
        double[] reverseArray = reverseArray(nums);
        System.out.println(Arrays.toString(nums));
        System.out.println(Arrays.toString(reverseArray));

    }

    private static double findMax(double[] nums) {// 大括号使用遵循Alibaba java代码规范
        double max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (max < nums[i])
                max = nums[i];
        }
        return max;
    }

    private static double avgNums(double[] nums) {
        double sum = 0.0;
        int len = nums.length;
        for (int i = 0; i < len; i++)
            sum += nums[i];
        return sum / len;

    }
    
    private static double[] copyArray(double[] nums){//复制数组
        int len = nums.length;
        double[] copyArray = new double[len];
        for (int i = 0; i < len; i++){
            copyArray[i] = nums[i];
        }
        return copyArray;
    }
      
    private static double[] reverseArray(double[] nums){//颠倒数组元素的顺序 
        int len = nums.length;
        double temp = 0;
        for (int i = 0; i < len / 2; i++){
            temp = nums[i];
            nums[i] = nums[len-1-i];
            nums[len-1-i] = temp;
        }
        return nums;
    }
    
    private static double[][] squareMatrixMul(double[][] a, double[][] b){
        //方阵相乘
        int len = a.length;
        double[][] result = new double[len][len];
        for (int i = 0; i < len; i++)
            for (int j = 0; j < len; j++){
                //计算行i和列j的点乘
                for (int k = 0; k < len; k++){
                    result[i][j] += a[i][k]*b[k][j];
                }
            }
        return result;
    }
}
