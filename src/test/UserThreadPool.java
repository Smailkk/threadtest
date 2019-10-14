package test;

import tools.ThreadPoolTools;

/**
 * @author zhaokai@zhangwen.com
 * @Copyright: Copyright© 2019
 * @Description: java <br/>
 * @Company: 北京黑岩信息技术有限公司 www.heiyan.com
 * @Created on 2019/10/14 zhaokai
 */
public class UserThreadPool {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
//        for(int i=0; i< 100; i++) {
//            ThreadPoolTools.getCachedThreadPool().submit(new UseThred(start));
//        }

        for(int i=0; i< 100; i++) {
            ThreadPoolTools.getFixThreadPool().submit(new UseThred(start));
        }


    }
}
