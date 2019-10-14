package test;

import tools.SleepTools;
import tools.ThreadPoolTools;

/**
 * @author zhaokai@zhangwen.com
 * @Copyright: Copyright© 2019
 * @Description: java <br/>
 * @Company: 北京黑岩信息技术有限公司 www.heiyan.com
 * @Created on 2019/10/14 zhaokai
 */
public class UseThred implements Runnable{

    private Long start;

    public UseThred(Long start) {
        this.start = start;
    }

    @Override
    public void run() {

        SleepTools.ms(1);
        System.out.println(Thread.currentThread().getId() +"-----耗时:" +(System.currentTimeMillis()-start)+"ms");
    }
}
