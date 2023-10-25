//package com.kshfx.auth.aop;
//
//import com.esports.common.annotation.Auth;
//import com.esports.common.annotation.DistributedLock;
//import com.esports.common.api.ResultCode;
//import com.esports.common.config.GlobalExceptionConfig;
//import com.esports.common.constant.CommonCode;
//import com.esports.common.constant.GlobalCode;
//import com.esports.common.constant.MemberCode;
//import com.esports.common.constant.PlayerCode;
//import com.esports.common.lock.DistributedReadWriteLock;
//import com.esports.common.util.IPUtils;
//import com.esports.common.util.JWT;
//import com.esports.common.util.JsonUtil;
//import com.esports.common.util.JsonWrapper;
//import com.esports.member.service.MemberManager;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//
//@Aspect
//@Component
//public class ConcurrentSecurity extends AbstractBaseAop {
//
//    private final Logger sysApiLogger = LoggerFactory.getLogger("callExternalApiLogger");
//
//    @Autowired
//    private DistributedReadWriteLock redisLock;
//
//    @Autowired
//    private JWT jwt;
//
//    @Autowired
//    private MemberManager memberManager;
//
//    private final String[] ignoreRequest = new String[]{"HEAD", "OPTIONS", "TRACE"};
//
//    @Pointcut(" execution(* com.kshfx..*.controller..*.*(..))")
//    public void pointcut() {
//
//    }
//
//    @Around("pointcut()")
//    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        if (Arrays.asList(ignoreRequest).contains(request.getMethod())) {
//            return null;
//        }
//        Method method = this.getMethod(joinPoint);
//        Auth auth = method.getDeclaredAnnotation(Auth.class);
//        Object result = null;
//        String token = request.getHeader(CommonCode.TOKEN_KEY);
//        try {
//            this.isSyncLocked(auth, token);
//            this.requestConfig(auth, request, token);
//            result = joinPoint.proceed();
//            result = this.changeResultValue(auth, request, result, token);
//        } finally {
//            this.release(auth, token);
//            long runTime = System.currentTimeMillis() - startTime;
//            this.log(request, joinPoint, startTime, result, runTime, sysApiLogger);
//        }
//        return result;
//    }
//
//    protected JsonWrapper changeResultValue(Auth auth, HttpServletRequest request, Object result, String token) {
//        JsonWrapper jsonWrapper = new JsonWrapper();
//        if (ObjectUtils.isEmpty(result)) {
//            jsonWrapper.setCode(ResultCode._500.getCode());
//            jsonWrapper.setMsg(ResultCode._500.getMessage());
//            jsonWrapper.setSuccess(Boolean.FALSE);
//            jsonWrapper.setData(null);
//            jsonWrapper.setTimestamp(System.currentTimeMillis());
//            return jsonWrapper;
//        }
//        try {
//            String json = JsonUtil.object2String(result);
//            jsonWrapper = JsonUtil.string2Object(json, JsonWrapper.class);
//            Object extData = null;
//            if (!StringUtils.isEmpty(token)) {
//                String account = jwt.getSubject(token);
//                String ip = IPUtils.getIpAddr(request);
//                String userSource = request.getHeader(CommonCode.USER_SOURCE);
//                Integer source = !StringUtils.isEmpty(userSource) ? Integer.valueOf(userSource) : null;
//                source = null == source || GlobalCode._PC.getCode().equals(source) ? GlobalCode._PC.getCode() : GlobalCode._MOBILE.getCode();
//                String identity = StringUtils.isEmpty(auth.identity()) ? PlayerCode._MEMBER.getCode() : auth.identity();
//                extData = memberManager.getMemberExtProfile(account, identity, source, ip);
//            }
//            jsonWrapper.setExtParams(extData);
//        } catch (Exception ex) {
//            if (ex instanceof GlobalExceptionConfig.CustomException) {
//                GlobalExceptionConfig.CustomException exception = (GlobalExceptionConfig.CustomException) ex;
//                jsonWrapper = JsonWrapper.failureWrapper(exception.getData(), exception.getCode(), ex.getMessage());
//            } else {
//                jsonWrapper = JsonWrapper.failureWrapper(null, ResultCode._500.getCode(), ex.getMessage());
//            }
//        }
//        return jsonWrapper;
//    }
//
//    protected void requestConfig(Auth auth, HttpServletRequest request, String token) {
//        if (null != auth && !StringUtils.isEmpty(token)) {
//            String account = jwt.getSubject(token);
//            request.setAttribute(CommonCode.JWT_USERNAME, account);
//        }
//    }
//
//    protected void isSyncLocked(Auth auth, String token) {
//        DistributedLock disLock = auth.sync();
//        boolean enabled = disLock.enabled();
//        String prefix = disLock.lockedPrefix();
//        long expireTime = disLock.expireTime();
//        boolean reTry = disLock.reTry();
//        boolean overTime = disLock.overTime();
//        long timeOutMillis = disLock.timeOutMillis();
//        if (enabled && !StringUtils.isEmpty(token)) {
//            try {
//                String account = jwt.getSubject(token);
//                String lockKey = redisLock.generatorKey(prefix, account);
//                redisLock.lock(lockKey, account, expireTime, reTry, 0, overTime, timeOutMillis);
//            } catch (Exception e) {
//            }
//        }
//    }
//
//    protected void release(Auth auth, String token) {
//        DistributedLock disLock = auth.sync();
//        boolean enabled = disLock.enabled();
//        String prefix = disLock.lockedPrefix();
//        if (enabled) {
//            try {
//                String account = jwt.getSubject(token);
//                String lockKey = redisLock.generatorKey(prefix, account);
//                redisLock.unlock(lockKey, account);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
