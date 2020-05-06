package com.zp.fw.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author zhoupeng
 * @Date 2020-05-06 15:41
 */
@Configuration
public class ZpInit {

    @PostConstruct
    public void init(){
        System.out.println("zp init,框架初始化时做一些操作 ");
    }

}
