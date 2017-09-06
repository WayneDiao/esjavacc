package com.huacloud.es;

public class Col extends Exp {
    String opt;
    String property;
    String value;
    public Col(String o,String p,String v) {opt=o;property=p;value =v;}
    public String toString() {return "("+property+" "+opt+" "+value+")";}
}
