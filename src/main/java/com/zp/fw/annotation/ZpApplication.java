package com.zp.fw.annotation;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * @Author zhoupeng
 * @Date 2020-04-30 16:13
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan(basePackages = {"com.zp.fw.config"})
public @interface ZpApplication {
}
