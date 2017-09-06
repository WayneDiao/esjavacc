package com.huacloud.es.impl;

import com.alibaba.fastjson.JSONObject;
import com.huacloud.es.BoolQuery;
import com.huacloud.es.Col;
import com.huacloud.es.Exp;

import java.util.Arrays;

public class BoolQueryImpl implements BoolQuery{


    @Override
    public String transform(Exp exp) {
        JSONObject query=new JSONObject();

        JSONObject indices=new JSONObject();
        indices.put("indices", Arrays.asList("user_11","user_10"));
        indices.put("query",getBoolQuery(exp));

        query.put("query",indices);
        return query.toJSONString();
    }

    private JSONObject getBoolQuery(Exp exp){
        JSONObject bool=new JSONObject();
        JSONObject must=new JSONObject();

        bool.put("bool","");
        return bool;
    }

    private JSONObject getMust(Col col){
        return null;
    }

    private JSONObject getMustNot(Col col){
        return null;
    }


    private JSONObject getFilter(Col col){
        return null;
    }

    private JSONObject getTerm(Col col){
       return null;
    }

    private JSONObject getRange(Col left,Col right){
        JSONObject range=new JSONObject();
        range.put(left.getProperty(),left.getValue());
        return range;
    }



}
