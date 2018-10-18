package cn.mccreefei.technologystack.rpc.support.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MccreeFei
 * @create 2018-10-17 下午3:21
 * RpcService注解 默认使用jdk动态代理 proxyTargetClass=true使用Cglib代理
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
    boolean proxyTargetClass() default false;
}
