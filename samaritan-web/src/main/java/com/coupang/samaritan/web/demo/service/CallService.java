package com.coupang.samaritan.web.demo.service;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CallService {

    public  void call(String phoneNumber) {

        String url = "http://api.submail.cn/voice/verify.json";
        HttpPost post = new HttpPost(url);
        DefaultHttpClient client = new DefaultHttpClient();
        List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("appid", "20200"));
        nameValuePairs.add(new BasicNameValuePair("to", phoneNumber));
        nameValuePairs.add(new BasicNameValuePair("code", "7777"));
        nameValuePairs.add(new BasicNameValuePair("signature", "f56da816967d9f07759413bbd9036dbe"));
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // send the post request
            CloseableHttpResponse response = client.execute(post);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
