package com.rd.iot_rdss_gateway.process.dataProcessThreadPool;

import com.rd.iot_rdss_gateway.process.processTask.model.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/7/2 19:12
 * @Version V1.0
 */
@Component
public class ThreadPoolManager implements BeanFactoryAware {
    private BeanFactory factory;
    // 线程池维护线程的最少数量
    private final static int CORE_POOL_SIZE = 2;
    // 线程池维护线程的最大数量
    private final static int MAX_POOL_SIZE = 10;
    // 线程池维护线程所允许的空闲时间
    private final static int KEEP_ALIVE_TIME = 0;
    // 线程池所使用的缓冲队列大小
    private final static int WORK_QUEUE_SIZE = 50;

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolManager.class);

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }

    /**
     * 用于储存在队列中的任务,防止重复提交,在真实场景中，可用redis代替 验证重复
     */
//    Map<String, Object> cacheMap = new ConcurrentHashMap<>();


    /**
     * 任务的缓冲队列,当线程池满了，则将任务存入到此缓冲队列
     */
    Queue<MessageModel> msgQueue = new LinkedBlockingQueue<>();


    /**
     * 当线程池的容量满了，执行下面代码，将任务存入到缓冲队列
     */
    final RejectedExecutionHandler handler = (r, executor) -> {
        //任务加入到缓冲队列
        msgQueue.offer(((ProcessThread) r).getAcceptStr());
        logger.info("系统任务太忙了,把此任务交给(调度线程池)逐一处理，消息：" + ((ProcessThread) r).getAcceptStr());
    };


    /**
     * 创建线程池
     */
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue(WORK_QUEUE_SIZE), this.handler);


    /**
     * 将任务加入任务线程池
     */
    public void addOrders(MessageModel msg) {
        logger.info("此任务准备添加到线程池，帧类型标识：" + msg.getZhenMark());
        ProcessThread processThread = new ProcessThread(msg);
        threadPool.execute(processThread);
    }

    /**
     * 线程池的定时任务----> 称为(调度线程池)。此线程池支持 定时以及周期性执行任务的需求。
     */
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);


    /**
     * 检查(调度线程池)，每秒执行一次，查看任务的缓冲队列是否有 任务记录，则重新加入到线程池
     */
    final ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
        //判断缓冲队列是否存在记录
        if (!msgQueue.isEmpty()) {
            //当线程池的队列容量少于WORK_QUEUE_SIZE，则开始把缓冲队列的任务 加入到 线程池
            if (threadPool.getQueue().size() < WORK_QUEUE_SIZE) {
                MessageModel msg = msgQueue.poll();
                ProcessThread processThread = new ProcessThread(msg);
                threadPool.execute(processThread);
                logger.info("(调度线程池)缓冲队列出现任务业务，重新添加到线程池，消息：" + msg);
            }
        }
    }, 0, 1, TimeUnit.SECONDS);


    /**
     * 获取消息缓冲队列
     */
    public Queue<MessageModel> getMsgQueue() {
        return msgQueue;
    }

    /**
     * 终止任务线程池+调度线程池
     */
    public void shutdown() {
        //true表示如果定时任务在执行，立即中止，false则等待任务结束后再停止
        logger.info("终止任务线程池+调度线程池：" + scheduledFuture.cancel(false));
        scheduler.shutdown();
        threadPool.shutdown();
    }
}
