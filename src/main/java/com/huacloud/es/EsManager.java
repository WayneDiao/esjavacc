package com.huacloud.es;

public interface EsManager {
    /**
     * 查询数据
     * @param exp
     */
    String query(Exp exp);

    /**
     * 查询boolQuery
     * @param boolQuery
     * @return
     */
    String query(String boolQuery);

}
