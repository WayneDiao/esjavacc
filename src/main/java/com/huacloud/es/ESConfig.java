package com.huacloud.es;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by Administrator on 2017/6/23.
 */
public class ESConfig implements Serializable {


    public static final  String CON_WAIT="con_waint";

    private String ip;

    private int port;

    private Properties dataSourceProperties;


    public ESConfig(String ip, int port){
          this.ip=ip;
          this.port=port;
          this.dataSourceProperties=new Properties();
    }

    public ESConfig(String ip, int port,Properties dataSourceProperties){
        this.ip=ip;
        this.port=port;
        this.dataSourceProperties=dataSourceProperties;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public Properties getDataSourceProperties() {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(Properties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public String getESJdbcUrl(){
        return "jdbc:elasticsearch://"+ip+":"+port+"/";
    }

    public void addProperties(Object key,Object value){
        this.dataSourceProperties.put(key,value);
    }


}
