package com.kshfx.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName DynamicRateLimiter
 * @Description 服务器接口限流器，防止接口恶意访问（后期结果业务需场景需要可考虑分布式限流）
 * @Version 1.0.0
 * @Date 2022/10/31 10:25
 * @Author Jacky
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicRateLimiter {

	/**
	 * 是否开启限流
	 * 
	 * @return
	 */
	boolean enabled() default true;

	/**
	 * 服务限流保护，服务每秒允许的TPS（需评估单个服务所允许的最大TPS）
	 * 
	 * @return
	 */
	double permitsPerSecond() default 150.0;

	/**
	 * 配置超时时间（配置将等待获取，不配置将直接返回）,单位毫秒
	 * 
	 * @return
	 */
	long timeout() default 100;

}
