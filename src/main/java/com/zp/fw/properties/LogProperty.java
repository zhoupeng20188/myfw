package com.zp.fw.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author zhoupeng
 * @Date 2020-05-07 15:37
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "zp.fw.controller.log")
public class LogProperty {
    private boolean enable;
    private boolean resultInclusive;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isResultInclusive() {
        return resultInclusive;
    }

    public void setResultInclusive(boolean resultInclusive) {
        this.resultInclusive = resultInclusive;
    }
}
