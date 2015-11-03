package com.pdk.manage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by hubo on 2015/10/9
 */
public class OrderCodeGenerator {
    /**
     * 序列号（1-99）
     */
    private static final long ONE_STEP = 100;
    /**
     * 格式化日期器
     */
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
    /**
     * 计数器
     */
    private int lastCount;
    /**
     * 最后同步时间
     */
    private String lastTime;
    /**
     * 单例实例
     */
    private volatile static OrderCodeGenerator instance;
    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(OrderCodeGenerator.class);

    private OrderCodeGenerator() {
        lastTime = getCurrentTime();
        lastCount = 1;
    }

    /**
     * 生成订单编号
     *
     * @return 生成的ID
     */
    public synchronized String generateOrderCode() {

        int count = generateNumber();

        lastTime = getCurrentTime();

        return "D" + lastTime + "" + String.format("%02d", count);

    }

    /**
     * 同一秒内生成序列号，如果超过最大生成数，则等到下一秒重新生成序列号
     * @return 序列号
     */
    private int generateNumber() {
        if (lastCount == ONE_STEP) {
            while (true) {
                String now = getCurrentTime();
                if (now.equals(lastTime)) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                } else {
                    lastTime = now;
                    lastCount = 1;
                    break;
                }
            }
        }
        return lastCount++;
    }

    /**
     * 获取当前时间(格式：yyMMddHHmmss)
     *
     * @return 当前时间
     */
    public String getCurrentTime() {
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 获取ID生成器 实例
     *
     * @return SequenceGenerator 实例
     */
    public static OrderCodeGenerator getInstance() {
        if (instance == null) {
            synchronized (OrderCodeGenerator.class) {
                if (instance == null) {
                    instance = new OrderCodeGenerator();
                }
            }
        }
        return instance;
    }

}
