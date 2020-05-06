package com.zp.fw.annotation;

import java.lang.annotation.*;

/**
 * @Author zhoupeng
 * @Date 2020-04-28 17:20
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyParameter {
    boolean includeResultData() default true;
}
