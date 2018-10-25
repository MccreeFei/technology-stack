package cn.mccreefei.technologystack.rpc.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MccreeFei
 * @create 2018-10-24 下午5:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcProxy {
    boolean proxyTargetClass() default false;
}
