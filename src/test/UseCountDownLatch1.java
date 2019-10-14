package test;

import tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhaokai@zhangwen.com
 * @Copyright: Copyright© 2019
 * @Description: java <br/>
 * @Company: 北京黑岩信息技术有限公司 www.heiyan.com
 * @Created on 2019/10/14 zhaokai
 */
public class UseCountDownLatch1 {

    static CountDownLatch latch = new CountDownLatch(5);

    //初始化线程(只有一步，有4个)
    private static class InitThread implements Runnable{

        @Override
        public void run() {
            System.out.println("运动员_"+Thread.currentThread().getId()
                    +" 到达终点");
            latch.countDown();//初始化线程完成工作了，countDown方法只扣减一次；

            System.out.println("运动员_"+Thread.currentThread().getId()
                    +" 去休息了");

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

            System.out.println("裁判员_"+Thread.currentThread().getId()
                        +" 去休息了");

        }
    }

    public static void main(String[] args) throws InterruptedException {
        //单独的初始化线程,初始化分为2步，需要扣减两次
        new Thread(new Runnable() {
            @Override
            public void run() {
                SleepTools.ms(1);
                System.out.println("运动员_"+Thread.currentThread().getId()
                        +" 到达终点");
                latch.countDown();//每完成一步初始化工作，扣减一次
                System.out.println("运动员_"+Thread.currentThread().getId()
                        +" 去休息了");
//                SleepTools.ms(1);
//                System.out.println("运动员_"+Thread.currentThread().getId()
//                        +" 到达终点 ");
//                latch.countDown();//每完成一步初始化工作，扣减一次
//                System.out.println("运动员_"+Thread.currentThread().getId()
//                        +" 去休息了");
            }
        }).start();


        new Thread(new UseCountDownLatch1.BusiThread()).start();


        for(int i=0;i<=3;i++){
            Thread thread = new Thread(new UseCountDownLatch1.InitThread());
            thread.start();
        }

        latch.await();
        System.out.println("主裁判去休息了");
    }
}
