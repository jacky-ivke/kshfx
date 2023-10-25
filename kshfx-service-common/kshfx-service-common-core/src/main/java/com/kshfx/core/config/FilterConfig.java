package com.kshfx.core.config;


import com.kshfx.core.filter.CachingContentFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(replaceStreamFilter());
        registration.addUrlPatterns("/*");
        registration.setName("streamFilter");
        return registration;
    }

    @Bean
    public CachingContentFilter replaceStreamFilter() {
        return new CachingContentFilter();
    }
}
