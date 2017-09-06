package com.huacloud.es.impl;

import com.alibaba.fastjson.JSONObject;
import com.huacloud.es.BoolQuery;
import com.huacloud.es.Col;
import com.huacloud.es.Exp;
import com.huacloud.es.Opt;

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
        if(exp instanceof Col){
            Col col=(Col)exp;
            if(Opt.NOT_EQUAL.equals(col.getOpt())){
                return getMustNot(getTerm(col));
            }else{
                return getMust(getTerm(col));
            }
        }
        return null;
    }

    private JSONObject getBool(JSONObject jsonObject){
       return createKeyQuery("bool",jsonObject);
    }

    private JSONObject getMust(JSONObject jsonObject){
        return createKeyQuery("must",jsonObject);
    }

    private JSONObject getMustNot(JSONObject jsonObject){
        return createKeyQuery("must_not",jsonObject);
    }


    private JSONObject getFilter(JSONObject jsonObject){
       return createKeyQuery("filter",jsonObject);
    }

    private JSONObject createKeyQuery(String key,JSONObject jsonObject){
        JSONObject keyObject=new JSONObject();
        keyObject.put(key,jsonObject);
        return keyObject;
    }

    private JSONObject getTerm(Col col){
        JSONObject term=new JSONObject();
        JSONObject property=new JSONObject();
        property.put(col.getProperty(),col.getValue());
        term.put("term",property);
       return term;
    }

    private JSONObject getRange(Col left,Col right){
        JSONObject range=new JSONObject();
        JSONObject property=new JSONObject();
        String opLeft=null,opRight=null;
        if(Opt.GT.equals(left.getOpt())){
            opLeft="gt";
        }else if(Opt.GTE.equals(left.getOpt())){
            opLeft="gte";
        }else if(Opt.lT.equals(left.getOpt())){
            opLeft="lt";
        }else if(Opt.lTE.equals(left.getOpt())){
            opLeft="lte";
        }
        if(Opt.GT.equals(right.getOpt())){
            opRight="gt";
        }else if(Opt.GTE.equals(right.getOpt())){
            opRight="gte";
        }else if(Opt.lT.equals(right.getOpt())){
            opRight="lt";
        }else if(Opt.lTE.equals(right.getOpt())){
            opRight="lte";
        }

        property.put(opLeft,left.getValue());
        property.put(opRight,right.getValue());
        range.put(left.getProperty(),property);
        return range;
    }




}
