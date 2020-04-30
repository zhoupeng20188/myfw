package com.zp.fw.config;

import com.zp.fw.inteceptor.ZpInteceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author zhoupeng
 * @Date 2020-04-28 17:29
 */
@Configuration
public class ZpWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("ZpWebMvcConfigurer -- addInterceptors");
        registry.addInterceptor(new ZpInteceptor());
    }
}
