package com.kshfx.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;

@Slf4j
@Component
public class CachingContentFilter implements Filter {

    /**
     * 文件上传类型
     */
    private static final String CONTENT_TYPE = "multipart/form-data";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // 转换http请求对象
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        ServletRequest requestWrapper = null;
        // http请求才会被过滤
        if (req instanceof HttpServletRequest) {
            // 文件上传请求
            if (request.getContentType() != null && request.getContentType().contains(CONTENT_TYPE)) {
                requestWrapper = new StandardServletMultipartResolver().resolveMultipart(request);
            }
            // 普通请求
            else {
                requestWrapper = new MultiReadHttpServletRequest(request);
            }
        }
        // 需要过滤
        if (null != requestWrapper) {
            chain.doFilter(requestWrapper, resp);
        }
        // 不需要过滤
        else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}