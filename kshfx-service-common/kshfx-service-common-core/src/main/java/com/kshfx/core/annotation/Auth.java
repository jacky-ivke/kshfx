package com.kshfx.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Auth
 * @Description 全局安全认证拦截注解（AOP）
 * @Version 1.0.0
 * @Date 2022/10/31 14:00
 * @Author Jacky
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {

    /**
     * 是否需要认证令牌
     *
     * @return
     */
    boolean authentication() default true;

    /**
     * 是否需要验证签名
     *
     * @return
     */
    boolean sign() default true;

    /**
     * 是否需要验证IP黑名单
     *
     * @return
     */
    boolean domain() default false;

    /**
     * 是否需要验证用户是否合法
     */
    boolean userlocked() default false;

    /**
     * 是否需要验证用户请求重复
     *
     * @return
     */
    boolean repeat() default false;

    /**
     * 是否需要分布式锁机制
     *
     * @return
     */
    DistributedLock sync() default @DistributedLock(enabled = false);

    /**
     * 是否需要服务限流
     *
     * @return
     */
    DynamicRateLimiter rateLimiter() default @DynamicRateLimiter(enabled = true);

    /**
     * 受否接口访问限制
     *
     * @return
     */
    VisitLimiter visitLimiter() default @VisitLimiter(enabled = true);
}
