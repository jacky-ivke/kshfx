//package com.kshfx.auth.aop;
//
//import com.esports.common.annotation.Auth;
//import com.esports.common.api.ResultCode;
//import com.esports.common.config.GlobalExceptionConfig.CustomException;
//import com.esports.common.constant.CommonCode;
//import com.esports.common.constant.PlayerCode;
//import com.esports.common.constant.RedisCacheKey;
//import com.esports.common.util.*;
//import com.esports.general.service.IpBlacklistManager;
//import com.esports.member.service.MemberManager;
//import com.google.common.collect.Maps;
//import com.google.common.util.concurrent.RateLimiter;
//import com.kshfx.core.aop.AbstractBaseAop;
//import com.kshfx.core.config.GlobalExceptionConfig;
//import com.kshfx.core.constant.CommonCode;
//import com.kshfx.core.constant.ResultCode;
//import com.kshfx.core.utils.SignUtil;
//import com.kshfx.core.utils.SortUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Pattern;
//
//@Slf4j
//@Aspect
//@Component
//public class AccessSecurity extends AbstractBaseAop {
//
//    private static final Map<String, RateLimiter> limitEntityMap = Maps.newConcurrentMap();
//
//    @Autowired
//    private JWT jwt;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    private final String[] ignoreRequest = new String[]{"HEAD", "OPTIONS", "TRACE"};
//
//    @Pointcut(" execution(* com.kshfx..*.controller..*.*(..))")
//    public void pointcut() {
//
//    }
//
//    @Before("pointcut()")
//    public void beforeAdvice(JoinPoint joinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        Method method = this.getMethod(joinPoint);
//        Auth auth = method.getDeclaredAnnotation(Auth.class);
//        String token = request.getHeader(CommonCode.TOKEN_KEY);
//        String username = request.getParameter("username");
//        String methodName = method.getName();
//        this.isFrequentVisits(auth, request);
//        this.isAccessTimeout(auth, request);
//        this.isVaildSign(auth, request);
//        this.isSqlValidate(request);
//        this.isRateLimit(auth, methodName, request);
//        this.isTokenExpired(auth, token, request);
//        this.isSso(auth, token, request);
//        this.isUserLocked(auth, token, username);
//    }
//
//    protected boolean sqlInject(String value) {
//        boolean success = false;
//        if (StringUtils.isEmpty(value)) {
//            return success;
//        }
//        String regex = ".*(select|update|union|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|drop|execute|[+]|%).*";
//        success = Pattern.matches(regex, value);
//        return success;
//    }
//
//    protected void isSqlValidate(HttpServletRequest request) {
//        Map<String, Object> paramsMap = SortUtil.getParameterMap(request);
//        if (CollectionUtils.isEmpty(paramsMap)) {
//            return;
//        }
//        Iterator<Entry<String, Object>> entries = paramsMap.entrySet().iterator();
//        while (entries.hasNext()) {
//            Entry<String, Object> entry = entries.next();
//            Object value = entry.getValue();
//            String param = "";
//            if (null != value && value instanceof String) {
//                param = String.valueOf(value);
//                boolean isMatch = this.sqlInject(param.toLowerCase());
//                if (isMatch) {
//                    log.info("【 您的页面请求中有违反安全规则元素存在：{}】", param);
//                    throw new GlobalExceptionConfig.CustomException(ResultCode._505.getCode(), ResultCode._505.getMessage());
//                }
//            }
//        }
//    }
//
//    /**
//     * 验证本次请求是否过期，时间戳超时机制（用户每次请求都带上当前时间的时间戳timestamp,服务端接收到timestamp后跟当前时间进行比对,如果时间差大于一定时间（比如5分钟），则认为该请求失效）
//     *
//     * @param auth
//     * @param request
//     */
//    protected void isAccessTimeout(Auth auth, HttpServletRequest request) {
//        if (null != auth && auth.sign() && !Arrays.asList(ignoreRequest).contains(request.getMethod())) {
//            Long time = 0L;
//            String timestamp = request.getHeader(CommonCode.FAILURE_TIME_KEY);
//            if (!StringUtils.isEmpty(timestamp)) {
//                time = Long.parseLong(timestamp);
//            }
//
//            boolean result = SignUtil.checkTimestamp(time, CommonCode.INF_FAILURE_TIME);
//            if (!result) {
//                throw new GlobalExceptionConfig.CustomException(ResultCode._502.getCode(), ResultCode._502.getMessage());
//            }
//        }
//    }
//
//    /**
//     * 验证签名是否有效
//     *
//     * @param auth
//     * @param request
//     */
//    protected void isVaildSign(Auth auth, HttpServletRequest request) {
//        if (null != auth && auth.sign() && !Arrays.asList(ignoreRequest).contains(request.getMethod())) {
//            String originalSign = request.getHeader(CommonCode.SIGN_KEY);
//            String params = SortUtil.getAllParams(request);
//            boolean result = SortUtil.verifySign(params, originalSign);
//            if (!result) {
//                throw new GlobalExceptionConfig.CustomException(ResultCode._503.getCode(), ResultCode._503.getMessage());
//            }
//        }
//    }
//
//    protected void isFrequentVisits(Auth auth, HttpServletRequest request) {
//        if (null != auth && auth.visitLimiter().enabled() && !Arrays.asList(ignoreRequest).contains(request.getMethod())) {
//            int visitLimit = auth.visitLimiter().visits() <= 0 ? 10 : auth.visitLimiter().visits();
//            long time = auth.visitLimiter().time() <= 0L ? CommonCode.REDIS_DEFAULT_EXPRISE : auth.visitLimiter().time();
//            String ip = IPUtils.getIpAddr(request);
//            String url = request.getRequestURI();
//            String ipKey = String.format(RedisCacheKey.IP_CHECK_KEY, ip, url);
//            Integer count = redisUtil.hasKey(ipKey) && null != redisUtil.get(ipKey) ? Integer.parseInt(redisUtil.get(ipKey).toString()) : 0;
//            if (null == count || count <= 0) {
//                redisUtil.set(ipKey, 1, time);
//            } else if (count > visitLimit) {
//                log.info("【用户IP：{},访问地址：{},超过了在规定时间{}s内访问接口次数>={}】", ip, url, time, visitLimit);
//                throw new GlobalExceptionConfig.CustomException(ResultCode._506.getCode(), ResultCode._506.getMessage());
//            } else {
//                redisUtil.incr(ipKey, 1);
//                redisUtil.expire(ipKey, time);
//            }
//        }
//    }
//
//    /**
//     * 前期不考虑分布式情况，服务限流【单体服务限制】
//     *
//     * @param auth
//     * @param methodName
//     */
//    protected void isRateLimit(Auth auth, String methodName, HttpServletRequest request) {
//        if (null != auth && auth.rateLimiter().enabled() && !Arrays.asList(ignoreRequest).contains(request.getMethod())) {
//            RateLimiter rateLimiter = null;
//            if (limitEntityMap.containsKey(methodName)) {
//                rateLimiter = limitEntityMap.get(methodName);
//            } else {
//                // 每秒只发出permits个令牌，此处是单进程服务的限流,内部采用令牌捅算法实现
//                double permits = auth.rateLimiter().permitsPerSecond();
//                rateLimiter = RateLimiter.create(permits);
//                limitEntityMap.putIfAbsent(methodName, rateLimiter);
//            }
//            // 如果用户在timeout毫秒内没有获取到令牌,就直接放弃获取进行服务降级处理（timeout=0）
//            long timeout = auth.rateLimiter().timeout();
//            boolean result = rateLimiter.tryAcquire(timeout, TimeUnit.MILLISECONDS);
//            if (!result) {
//                throw new GlobalExceptionConfig.CustomException(ResultCode._501.getCode(), ResultCode._501.getMessage());
//            }
//        }
//    }
//
//    /**
//     * 验证当前令牌是否有效
//     *
//     * @param auth
//     * @param token
//     * @return
//     */
//    protected void isTokenExpired(Auth auth, String token, HttpServletRequest request) {
//        if (null != auth && auth.authentication() && !Arrays.asList(ignoreRequest).contains(request.getMethod())) {
//            boolean expired = jwt.isTokenExpired(token);
//            if (expired || StringUtils.isEmpty(token)) {
//                throw new CustomException(ResultCode._408.getCode(), ResultCode._408.getMessage());
//            }
//        }
//        String account = jwt.getSubject(token);
//        if (StringUtils.isEmpty(account)) {
//            return;
//        }
//        String onlineKey = String.format(RedisCacheKey.ONLINE_KEY, account);
//        try {
//            redisUtil.set(onlineKey, account, CommonCode.ONLINE_EXPRIRED_TIME);
//        } catch (Exception e) {
//            log.error("redis设置key,异常信息:{}", e.getMessage());
//        }
//    }
//
//    public void isSso(Auth auth, String token, HttpServletRequest request) {
//        if (null != auth && auth.authentication() && !Arrays.asList(ignoreRequest).contains(request.getMethod())) {
//            if (StringUtils.isEmpty(token)) {
//                return;
//            }
//            String account = jwt.getSubject(token);
//            if (StringUtils.isEmpty(account)) {
//                return;
//            }
//            String identity = auth.identity();
//            String redsiToken = "";
//            String redisKey = String.format(RedisCacheKey.MEMBER_SSO_KEY, account);
//            try {
//                redsiToken = redisUtil.hasKey(redisKey) && null != redisUtil.get(redisKey) ? redisUtil.get(redisKey).toString() : "";
//            } catch (Exception e) {
//                log.error("redis获取key:{},异常信息:{}", redisKey, e.getMessage());
//            }
//            if (!StringUtils.isEmpty(redsiToken) && !redsiToken.equals(token)) {
//                throw new CustomException(ResultCode._408.getCode(), ResultCode._408.getMessage());
//            }
//        }
//    }
//
//    protected void isUserLocked(Auth auth, String token, String username) {
//        if (null != auth && username != null && auth.userlocked()) {
//            String account = jwt.getSubject(token);
//            account = StringUtils.isEmpty(account) ? username : account;
//            boolean result = memberManager.checkLocked(account);
//            if (result) {
//                throw new CustomException(ResultCode._403.getCode(), ResultCode._403.getMessage());
//            }
//        }
//    }
//}
