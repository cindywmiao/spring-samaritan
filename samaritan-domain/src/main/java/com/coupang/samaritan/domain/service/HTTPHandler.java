package com.coupang.samaritan.domain.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

@Component
public class HTTPHandler {
    public static final String HEADER_XML_CONTENT_TYPE = "application/xml;charset=UTF-8";

    public byte[] get(String url, Map<String, String> paramMap) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        url = this.makeUrlParams(url, paramMap);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            return EntityUtils.toByteArray(response.getEntity());
        } finally {
            response.close();
        }
    }

    public byte[] put(String url, Map<String, String> paramMap, byte[] b) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        url = this.makeUrlParams(url, paramMap);
        HttpPut httpPut = new HttpPut(url);

        if (b != null) {
            httpPut.setEntity(new StringEntity(new String(b)));
            httpPut.setHeader("Content-type", "application/octet-stream");
        }
        CloseableHttpResponse response = httpclient.execute(httpPut);
        try {
            return EntityUtils.toByteArray(response.getEntity());
        } finally {
            response.close();
        }
    }

    public byte[] postStringBody(String url, String content, String contentType) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", contentType);
        httpPost.setEntity(new StringEntity(content));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            return EntityUtils.toByteArray(response.getEntity());
        } finally {
            response.close();
        }
    }

    public byte[] putStringBody(String url, String content, String contentType) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-type;charset=UTF-8", contentType);
        httpPut.setEntity(new StringEntity(content));
        CloseableHttpResponse response = httpclient.execute(httpPut);
        try {
            return EntityUtils.toByteArray(response.getEntity());
        } finally {
            response.close();
        }
    }

    public byte[] put(String url, Map<String, String> paramMap) throws IOException {
        return this.put(url, paramMap, null);
    }

    private String makeUrlParams(String url, Map<String, String> paramMap) {
        if (CollectionUtils.isEmpty(paramMap)) {
            return url;
        }
        StringBuffer params = new StringBuffer("?");
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            params.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        return url + params.toString();
    }


}
