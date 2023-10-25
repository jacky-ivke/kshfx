package com.kshfx.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName DynamicRateLimiter
 * @Description 服务器接口访问限制，防止恶意攻击
 * @Version 1.0.0
 * @Date 2022/10/31 10:25
 * @Author Jacky
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VisitLimiter {

    /**
     * 是否开启限流
     *
     * @return
     */
    boolean enabled() default true;

    /**
     * 服务接口限流保护，服务接口3秒内访问次数不能超过次
     *
     * @return
     */
    int visits() default 10;

    /**
     * 配置时间范围
     *
     * @return
     */
    long time() default 3;
}
