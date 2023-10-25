package com.kshfx.datasource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description mybatisPlus配置类
 * @Version 1.0.0
 * @Date 2022/10/31 10:29
 * @Author xuzm
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public IdentifierGenerator idGenerator(){
        return new CustomIdGenerator();
    }

    static class CustomIdGenerator implements IdentifierGenerator {
        @Override
        public Number nextId(Object entity) {
            return null;
        }
    }

    @Bean
    public MybatisPlusInterceptor paginationInterceptor(){
        final MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        final PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.DM);
        //溢出总页数的时候默认是跳到第一页
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        final OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        return interceptor;
    }
}
