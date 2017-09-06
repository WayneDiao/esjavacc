package com.huacloud.es;

public class Cols extends Exp {
    String op;
    Exp left, right;
    public Cols(String o, Exp l, Exp r) {op = o; left = l; right = r;}
    public String toString() {return "(" + op + " " + left + " " + right + ")";}
}
