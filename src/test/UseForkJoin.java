package test;

import tools.SleepTools;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author zhaokai@zhangwen.com
 * @Copyright: Copyright© 2019
 * @Description: java <br/>
 * @Company: 北京黑岩信息技术有限公司 www.heiyan.com
 * @Created on 2019/10/12 zhaokai
 */
public class UseForkJoin extends RecursiveTask<Integer> {

    public static final int ARRAY_LENGTH  = 1000;    //数组长度
    private final static int THRESHOLD = ARRAY_LENGTH/10;   //阈值
    private int[] src; //表示我们要实际统计的数组
    private int fromIndex;//开始统计的下标
    private int toIndex;//统计到哪里结束的下标

    public UseForkJoin(int[] src, int fromIndex, int toIndex) {
        this.src = src;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    protected Integer compute() {
        if(toIndex-fromIndex < THRESHOLD) {
            int count = 0;
            for(int i=fromIndex;i<=toIndex;i++) {
                SleepTools.ms(1);
                count = count + src[i];
            }
            return count;
        }else {
            int mid = (fromIndex+toIndex)/2;
            UseForkJoin left = new UseForkJoin(src,fromIndex,mid);
            UseForkJoin right = new UseForkJoin(src,mid+1,toIndex);
            invokeAll(left,right);
            return left.join()+right.join();
        }
    }

    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool();
        int[] src = makeArray();

        UseForkJoin innerFind = new UseForkJoin(src,0,src.length-1);

        long start = System.currentTimeMillis();

        pool.invoke(innerFind);//同步调用
        System.out.println("线程执行中.....");

        System.out.println("合计： "+innerFind.join()
                +" 耗时:"+(System.currentTimeMillis()-start)+"ms");


        System.out.println("-----------下面不使用线程计算-----------------");

        int count = 0;
        long startNormal = System.currentTimeMillis();
        for(int i= 0;i<src.length;i++){
            SleepTools.ms(1);
            count = count + src[i];
        }
        System.out.println("合计：  "+count
                +" 耗时:"+(System.currentTimeMillis()-startNormal)+"ms");
    }


    public static int[] makeArray() {

        //new一个随机数发生器
        Random r = new Random();
        int[] result = new int[ARRAY_LENGTH];
        for(int i=0;i<ARRAY_LENGTH;i++){
            //用随机数填充数组
            result[i] =  r.nextInt(ARRAY_LENGTH*3);
        }
        return result;

    }
}
