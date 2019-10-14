package test;

import tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhaokai@zhangwen.com
 * @Copyright: Copyright© 2019
 * @Description: java <br/>
 * @Company: 北京黑岩信息技术有限公司 www.heiyan.com
 * @Created on 2019/10/12 zhaokai
 */
public class UseCountDownLatch {

    /**
     * 放行条件》=线程数
     * 一个或者多个线程  等待其他线程完成相关任务后  再执行
     */
    static CountDownLatch latch = new CountDownLatch(6);

    //初始化线程(只有一步，有4个)
    private static class InitThread implements Runnable{

        @Override
        public void run() {
            System.out.println("Thread_"+Thread.currentThread().getId()
                    +" 正在初始化");
            latch.countDown();//初始化线程完成工作了，countDown方法只扣减一次；

            System.out.println("Thread_"+Thread.currentThread().getId()
                        +" 初始化完成");

            System.out.println("---------------------------------------");

        }
    }

    //业务线程
    private static class BusiThread implements Runnable{

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i =0;i<3;i++) {
                System.out.println("BusiThread_"+Thread.currentThread().getId()
                        +" 开始工作！");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //单独的初始化线程,初始化分为2步，需要扣减两次
        new Thread(new Runnable() {
            @Override
            public void run() {
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
                        +" 初始化第一步");
                latch.countDown();//每完成一步初始化工作，扣减一次
                System.out.println("开始初始化第二部");
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
                        +" 初始化完成 ");
                latch.countDown();//每完成一步初始化工作，扣减一次
            }
        }).start();


        new Thread(new BusiThread()).start();


        for(int i=0;i<=3;i++){
            Thread thread = new Thread(new InitThread());
            thread.start();
        }

        latch.await();
        System.out.println("主线程工作");
    }
}
