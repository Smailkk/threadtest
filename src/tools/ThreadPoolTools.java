package tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaokai@zhangwen.com
 * @Copyright: Copyright© 2019
 * @Description: java <br/>
 * @Company: 北京黑岩信息技术有限公司 www.heiyan.com
 * @Created on 2019/10/12 zhaokai
 */
public class ThreadPoolTools {

    private static ExecutorService fixThreadPool = null;

    private static ExecutorService cachedThreadPool = null;

    private ThreadPoolTools(){};

    public static ExecutorService getFixThreadPool() {
        if (fixThreadPool == null) {
            fixThreadPool = Executors.newFixedThreadPool(10);
        }
        return fixThreadPool;
    }

    public static ExecutorService getCachedThreadPool() {
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        return cachedThreadPool;
    }


}
