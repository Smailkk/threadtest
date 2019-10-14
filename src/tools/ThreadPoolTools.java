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

    /**
     * 固定数量的线程（即核心线程）  使用无界队列  ExecutorService.shutdown() 关闭
     */
    private static ExecutorService fixThreadPool = null;

    /**
     * 线程数不固定（核心线程数为0） 即用即产生  短生命周期的异步任务  线程池里的线程60s没有被使用 自动销毁
     */
    private static ExecutorService cachedThreadPool = null;

    private ThreadPoolTools(){};

    public static synchronized ExecutorService getFixThreadPool() {
        if (fixThreadPool == null) {
            fixThreadPool = Executors.newFixedThreadPool(10);
        }
        return fixThreadPool;
    }

    public static synchronized ExecutorService getCachedThreadPool() {
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        return cachedThreadPool;
    }


}
