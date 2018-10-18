package cn.mccreefei.technologystack.rpc.client.config;

import cn.mccreefei.technologystack.rpc.support.service.RpcService;
import cn.mccreefei.technologystack.rpc.client.proxy.RpcProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2018-10-17 下午3:13
 * Rpc配置 发现RpcService注入容器
 */
@Configuration
@Slf4j
public class RpcConfig implements ApplicationContextAware, InitializingBean {
    private ApplicationContext context;
    @Resource
    private RpcProxyFactory proxyFactory;
    @Override
    public void afterPropertiesSet() throws Exception {
        Reflections reflections = new Reflections("cn.mccreefei.technologystack.rpc.support.service");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcService.class);
        if (!CollectionUtils.isEmpty(typesAnnotatedWith)){
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
            typesAnnotatedWith.forEach(cls -> {
                RpcService annotation = cls.getAnnotation(RpcService.class);
                if (annotation.proxyTargetClass()){
                    beanFactory.registerSingleton(cls.getName(), proxyFactory.createInstance(cls, true));
                }else {
                    beanFactory.registerSingleton(cls.getName(), proxyFactory.createInstance(cls, false));
                }
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
