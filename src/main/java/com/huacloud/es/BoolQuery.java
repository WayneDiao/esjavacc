package com.huacloud.es;

public interface BoolQuery {

    /**
     * 将对象Exp转为bool query body
     * @param exp
     * @return
     */
    String transform(Exp exp);

}
