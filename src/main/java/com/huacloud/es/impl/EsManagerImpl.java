package com.huacloud.es.impl;

import com.huacloud.es.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class EsManagerImpl implements EsManager {

    private static final Logger log = LogManager.getLogger(EsManagerImpl.class);

    private BoolQuery boolQuery=null;

    private List<HttpHost> httpHosts = null;

    private AtomicInteger hostIndex = new AtomicInteger(0);


    public EsManagerImpl() throws Exception {
        this(new ESConfig("172.16.13.248",9200));
    }

    public EsManagerImpl(ESConfig esConfig) throws Exception {
        this.boolQuery=new BoolQueryImpl();
        if (StringUtils.isEmpty(esConfig.getIp())) {
            throw new Exception("ip is null");
        }
        String[] ips = esConfig.getIp().split(",");
        httpHosts = new ArrayList<HttpHost>(ips.length);
        for (String ip : ips) {
            httpHosts.add(new HttpHost(ip, esConfig.getPort()));
        }
    }

    @Override
    public String query(Exp exp) {
      String query=boolQuery.transform(exp);
      return doQuery(query);
    }

    @Override
    public String query(String boolQuery) {
        return doQuery(boolQuery);
    }


    private HttpHost next() {
        return httpHosts.get(hostIndex.getAndIncrement() % httpHosts.size());
    }

    private String doQuery(String boolQuery) {
        String url = next().toURI() + "/_search";
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setEntity(new StringEntity(boolQuery, Charset.forName("UTF-8")));
        CloseableHttpClient client = HttpClients.custom().setConnectionTimeToLive(5, TimeUnit.MINUTES)
                .setSSLSocketFactory(CustomSSLConnectionSocketFactory.getSocketFactory()).build();
        //set timeout
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(2000)
                .setSocketTimeout(5000).build();
        post.setConfig(requestConfig);
        CloseableHttpResponse respone = null;

        try {
            respone = client.execute(post);
            if (log.isDebugEnabled()) {
                log.debug("用户透传请求:{}:{}", url,boolQuery);
            }
            StatusLine statusLine = respone.getStatusLine();
            String rs = null;
            if (statusLine.getStatusCode() == 200) {
                HttpEntity entity = respone.getEntity();
                if (entity != null) {
                    try{
                        rs=EntityUtils.toString(entity, "UTF-8");
                        EntityUtils.consume(entity);
                        return rs;
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("解析请求结果出错!:"+statusLine.getStatusCode());
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                log.error("请求不正确!:"+statusLine.getStatusCode());
                return "";
            }

        } catch (IOException e) {
            e.printStackTrace();
            log.error("初化请求出错!");
        } finally {
            if (respone != null) {
                try {
                    respone.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }



}
