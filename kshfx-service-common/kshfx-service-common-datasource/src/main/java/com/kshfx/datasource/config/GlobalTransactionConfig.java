package com.kshfx.datasource.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName GlobalTransactionConfig
 * @Description 全局事务配置
 * @Version 1.0.0
 * @Date 2022/10/31 10:33
 * @Author Jacky
 */
@EnableTransactionManagement
@Configuration
@Aspect
public class GlobalTransactionConfig {

    /**
     * 写事务的超时时间
     */
    private static final int TX_METHOD_TIMEOUT = 10;

    private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.kshfx..*.service..*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

    @Bean
    public TransactionInterceptor txAdvice() {

        //methodMap声明具备需要管理事务的方法名.
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap = this.readOnlyTx(txMap);
        txMap = this.requiredTx(txMap);
        txMap = this.noTx(txMap);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setNameMap(txMap);
        return new TransactionInterceptor(transactionManager, source);
    }

    protected Map<String, TransactionAttribute> readOnlyTx(Map<String, TransactionAttribute> txMap) {

        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        /**
         * 约定大于配置，统一风格， 统一规范方法名。
         */
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("list*", readOnlyTx);
        txMap.put("count*", readOnlyTx);
        txMap.put("exist*", readOnlyTx);
        txMap.put("search*", readOnlyTx);
        txMap.put("fetch*", readOnlyTx);
        return txMap;
    }

    protected Map<String, TransactionAttribute> requiredTx(Map<String, TransactionAttribute> txMap) {

        //当前存在事务就使用当前事务，当前不存在事务,就开启一个新的事务
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TX_METHOD_TIMEOUT);

        //约定大于配置，统一风格， 统一规范方法名。
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("modify*", requiredTx);
        txMap.put("delete*", requiredTx);
        return txMap;
    }

    protected Map<String, TransactionAttribute> noTx(Map<String, TransactionAttribute> txMap) {

        //无事务地执行，挂起任何存在的事务
        RuleBasedTransactionAttribute noTx = new RuleBasedTransactionAttribute();
        noTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        txMap.put("*", noTx);
        return txMap;
    }
}
