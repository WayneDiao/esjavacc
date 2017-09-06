package com.huacloud.es;

public class Col extends Exp {
    String opt;
    String property;
    String value;
    public Col(String o,String p,String v) {opt=o;property=p;value =v;}

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {return "("+property+" "+opt+" "+value+")";}
}
