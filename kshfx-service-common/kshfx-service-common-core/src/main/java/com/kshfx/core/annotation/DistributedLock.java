package com.kshfx.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName DistributedLock
 * @Description 分布式锁处理，锁的粒度比较大，锁定的是方法级的代码块，普通业务系统均可满足，如果系统并发较高，可进行局部优化锁顶区间。
 * @Version 1.0.0
 * @Date 2023/10/17 10:47
 * @Author Jacky
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * 默认开启锁
     *
     * @return
     */
    boolean enabled() default true;

    /**
     * 锁的前缀
     *
     * @return
     */
    String lockedPrefix() default "request_lock:%s";

    /**
     * 锁自动释放时间
     *
     * @return
     */
    long expireTime() default 5000;

    /**
     * 锁获取失败,是否重试
     *
     * @return
     */
    boolean reTry() default true;

    /**
     * 锁是否需要判断超时
     *
     * @return
     */
    boolean overTime() default true;

    /**
     * 尝试超时时间
     *
     * @return
     */
    long timeOutMillis() default 1000;
}
