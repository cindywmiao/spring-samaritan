package com.coupang.samaritan.domain.service;

import org.springframework.core.CollectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by frank.fu on 9/14/16.
 */

@Component
public class HdfsConfig {
    public byte[] download(String webHdfsFullUrl) {
        Map<String, String> paramMap = CollectionFactory.createMap(ConcurrentHashMap.class, 2);
        paramMap.put("user.name", "mercury");
        paramMap.put("op", "OPEN");
        try {
            byte[] b = new HTTPHandler().get(webHdfsFullUrl, paramMap);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getFileList(String user, String webHdfsFullUrl) {
        Map<String, String> paramMap = CollectionFactory.createMap(ConcurrentHashMap.class, 2);
        if (!StringUtils.isEmpty(user)) {
            paramMap.put("user.name", user);
            paramMap.put("op", "LISTSTATUS");
        }
        try {
            byte[] b = new HTTPHandler().get(webHdfsFullUrl, paramMap);
            String result = new String(b);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getContentSummary(String user, String webHdfsFullUrl) {
        Map<String, String> paramMap = CollectionFactory.createMap(ConcurrentHashMap.class, 2);
        if (!StringUtils.isEmpty(user)) {
            paramMap.put("user.name", user);
            paramMap.put("op", "GETCONTENTSUMMARY");
        }
        try {
            byte[] b = new HTTPHandler().get(webHdfsFullUrl, paramMap);
            String result = new String(b);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
