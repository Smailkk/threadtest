package test;

import tools.SleepTools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zhaokai@zhangwen.com
 * @Copyright: Copyright© 2019
 * @Description: java <br/>
 * @Company: 北京黑岩信息技术有限公司 www.heiyan.com
 * @Created on 2019/10/14 zhaokai
 */
public class UseCyclicBarrier1 {

    private static CyclicBarrier barrier
            = new CyclicBarrier(5,new UseCyclicBarrier1.CollectThread());

    private static ConcurrentHashMap<String,Long> resultMap
            = new ConcurrentHashMap<>();//存放子线程工作结果的容器

    public static void main(String[] args) {
        for(int i=0;i<=4;i++){
            Thread thread = new Thread(new UseCyclicBarrier1.SubThread());
            thread.start();
        }

    }

    //负责屏障开放以后的工作
    private static class CollectThread implements Runnable{

        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for(Map.Entry<String,Long> workResult:resultMap.entrySet()){
                result.append("["+workResult.getValue()+"]");
            }
            System.out.println(" 准备就绪的运动员 = "+ result);
            SleepTools.second(5);
            System.out.println("发令枪打响........");
        }
    }

    //工作线程
    private static class SubThread implements Runnable{

        @Override
        public void run() {
            long id = Thread.currentThread().getId();//线程本身的处理结果
            resultMap.put(Thread.currentThread().getId()+"",id);
            Random r = new Random();//随机决定工作线程的是否睡眠
            try {
                if(r.nextBoolean()) {
                    Thread.sleep(2000+id);
                    System.out.println("运动员_"+id+" ....迟到了");
                }
                System.out.println("运动员_"+id+"....准备就绪");
                barrier.await();
                Thread.sleep(1000+id);
                System.out.println("运动员_"+id+" ....开始奔跑 ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
