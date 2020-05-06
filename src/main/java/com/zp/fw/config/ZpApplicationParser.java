package com.zp.fw.config;

import com.zp.fw.annotation.ZpApplication;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhoupeng
 * @Date 2020-05-06 17:22
 */
@Configuration
public class ZpApplicationParser implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 在此方法中可以拿到IndexController上的ZpApplication注解
        if(bean.getClass().isAnnotationPresent(ZpApplication.class)){
            System.out.println("ZpApplication init");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 在此方法中拿不到IndexController上的ZpApplication注解，原因不明
        if(bean.getClass().isAnnotationPresent(ZpApplication.class)){
            System.out.println("ZpApplication init after");
        }
        return bean;
    }
}
