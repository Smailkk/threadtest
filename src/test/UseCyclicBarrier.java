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
 * @Created on 2019/10/12 zhaokai
 */
public class UseCyclicBarrier {

    /**
     * 放行条件=线程数
     * 一个或者多个线程 等待目标线程执行完成后      继续执行
     */

    private static CyclicBarrier barrier
            = new CyclicBarrier(5,new CollectThread());

    private static ConcurrentHashMap<String,Long> resultMap
            = new ConcurrentHashMap<>();//存放子线程工作结果的容器

    public static void main(String[] args) {
        for(int i=0;i<=4;i++){
            Thread thread = new Thread(new SubThread());
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
            System.out.println(" 等待线程 = "+ result);
            SleepTools.second(10);
            System.out.println("执行目标工作........");
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
                    System.out.println("Thread_"+id+" ....去做其他的事 ");
                }
                System.out.println(id+"....正在等待");
                barrier.await();
                Thread.sleep(1000+id);
                System.out.println("Thread_"+id+" ....继续工作 ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
