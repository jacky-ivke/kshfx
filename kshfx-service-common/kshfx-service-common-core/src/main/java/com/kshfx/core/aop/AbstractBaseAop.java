package com.kshfx.core.aop;

import com.alibaba.fastjson.JSONArray;
import com.kshfx.core.utils.IPUtils;
import com.kshfx.core.utils.JsonUtil;
import com.kshfx.core.utils.ParamsUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @ClassName AbstractBaseAop
 * @Description 抽象基类，拦截处理
 * @Version 1.0.0
 * @Date 2022/10/31 10:25
 * @Author Jacky
 */
public abstract class AbstractBaseAop {

    /**
     * @Title getMethodName
     * @Description AOP获取调用方法名称
     * @Param joinPoint-aop连接点
     * @Return 方法名称
     * @Date 2022/10/31 16:08
     */
    public String getMethodName(JoinPoint joinPoint) {
        String name = "";
        try {
            Method method = this.getMethod(joinPoint);
            name = method.getName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return name;
    }

    /**
     * @Title getClassName
     * @Description AOP获取类名称
     * @Param joinPoint-aop连接点
     * @Return 类名称
     * @Date 2022/10/31 16:00
     */
    public String getClassName(JoinPoint joinPoint) {
        Class<?> classTarget = this.getClass(joinPoint);
        String objClass = classTarget.getName();
        return objClass;
    }

    /**
     * @Title getMethod
     * @Description AOP获取方法对象
     * @Param joinPoint-aop连接点
     * @Return 对象
     * @Date 2022/10/31 16:00
     */
    public Method getMethod(JoinPoint joinPoint) throws Exception {
        Class<?> classTarget = this.getClass(joinPoint);
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] per = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method = null;
        method = classTarget.getMethod(methodName, per);
        return method;
    }

    /**
     * @Title getClass
     * @Description AOP获取类对象
     * @Param joinPoint-aop连接点
     * @Return 对象
     * @Date 2022/10/31 16:00
     */
    public Class<?> getClass(JoinPoint joinPoint) {
        Class<?> classTarge = joinPoint.getTarget().getClass();
        return classTarge;
    }

    /**
     * @Title log
     * @Description 接口访问日志处理
     * @Param request-请求对象, logger-日志处理器, joinPoint-AOP连接点, result-返回结果, runTime-接口响应时长
     * @Return 对象
     * @Date 2022/10/31 16:00
     */
    public void log(HttpServletRequest request, Logger logger, JoinPoint joinPoint, Object result, Long runTime) {
        String ip = IPUtils.getIpAddr(request);
        String url = String.valueOf(request.getRequestURL());
        String args = JSONArray.toJSONString(ParamsUtils.getParameterMap(request));
        String method = request.getMethod();
        this.printLog(ip, url, args, method, logger, joinPoint, result, runTime);
    }

    /**
     * @Title printLog
     * @Description 统一接口日志打印格式
     * @Param request-请求对象, logger-日志处理器, joinPoint-AOP连接点, result-返回结果, runTime-接口响应时长
     * @Return void
     * @Date 2022/10/31 16:00
     */
    public void printLog(String ip, String url, String args, String method, Logger logger, JoinPoint joinPoint, Object result, Long runTime) {
        logger.info("=====================HTTP接口请求====================");
        logger.info("请求类名{}", this.getClassName(joinPoint));
        logger.info("请求IP:{}", ip);
        logger.info("请求路径:{}", url);
        logger.info("请求方式:{}", method);
        logger.info("请求方法:{}", this.getMethodName(joinPoint));
        logger.info("请求参数：{}", args);
        logger.info("执行时间:{}ms", runTime);
        logger.info("返回结果:{}", JsonUtil.object2String(result));
    }
}
