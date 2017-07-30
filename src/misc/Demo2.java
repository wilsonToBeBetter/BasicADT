package misc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 判断素数  构造n以内的素数表
 * @author wjs13
 *
 */
public class Demo2 {

    @Test
    public  void test() {
        System.out.println(isPrime(49));
        List<Integer> primeList = createPrimeList(1000);
        System.out.println(primeList.size());
        System.out.println(primeList);
    }
    
   
    private static boolean isPrime(int num){//判断num是否是素数
        if (num == 1 || (num % 2 == 0 && num != 2))//偶数则不是素数
            return false;
        for (int i = 3; i * i <= num; i+=2){//注意此处跳过偶数的循环判断 常数意义次的效率更高
//        for (int i = 3; i <= Math.sqrt(num); i+=2) 用java的类库中的平方根方法
//                最多只用循环sqrt(num)遍
            if (num % i ==0)
            return false;
            }
        return true;
    }
   
    private static List<Integer> createPrimeList(int n){//构造n以内的素数表
        
//    primeState[0]到primeState[n] 即表示从数0到数n   从0到n一共n+1个数 固用primeState[n+1]数组表示 
        int[] primeState = new int[n+1];//0到n所有数是否是素数的状态数组
        
        for (int i = 0; i < n + 1; i++){
            primeState[i] = 1;//全部初始化为1  表示0至n所有的数都是素数  状态都为1
        }
        for (int num = 2; num <= n / 2; ){//素数是从2开始  因j从2开始且num * j <= n 固num值判断到n/2就行
            for (int j = 2;  num * j <= n; j++){//num*2 num*3 num*4....都可以判断为非素数 状态置0
                primeState[num * j] = 0;//标记为非素数 状态为0
            }
            //令num为下一个没有被标记为非素数的数  即是从num=2;primeState[num+1]开始的下一个值为1的数组索引num  即保证在循环过程中 num不取0-n中被标记为非素数的数
//            有此步 选择下一个迭代循环的num 而不是简单的num++ 会使程序更加高效
            for (int index = num + 1; index <  n; index++){
                if (primeState[index] == 1){//下一个数是没有被标记为非素数的数  即起始被标记为素数 数组元素值为1的数
                    num = index ;break;//令num为下一个没有被标记为非素数的数 就可以跳出状态数组的遍历了
                }
            }
        }
        List<Integer> primeList = new ArrayList<Integer>();
        for(int k = 2; k < n; k++){
            if (primeState[k] == 1)//从素数2开始  找到标记为素数1的数
            primeList.add(Integer.valueOf(k));
        }
        return primeList;
    }
    
}
