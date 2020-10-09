package com.zp.fw.bean;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author zhoupeng
 * @Date 2020-05-11 10:52
 */
@Component
public class ZpClient {
    private ConcurrentHashMap map= new ConcurrentHashMap();

    public ConcurrentHashMap getMap() {
        return map;
    }

    public void addClient(String ip, String clientName){
        this.map.put(ip, clientName);
    }
}
