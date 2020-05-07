package com.zp.fw.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author zhoupeng
 * @Date 2020-05-07 15:04
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"},encoding = "utf-8")
@ConfigurationProperties(prefix = "zp.fw.registry")
public class RegistryProperty {
    private String ip;
    private String name;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
