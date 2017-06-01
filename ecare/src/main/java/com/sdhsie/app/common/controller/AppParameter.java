package com.sdhsie.app.common.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcx on 2017/1/5.
 */
public class AppParameter {
    public static Map<String, Object> getBody( Map<String, Object> map){
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        if( map.get("body")!=null) {
            bodyMap = (Map<String, Object>) map.get("body");//内容传参
        }
        return  bodyMap;
    }
    public static String getSessionId( Map<String, Object> map){
        String sessionId = "";
        if( map.get("session_id")!=null) {
            sessionId = map.get("session_id").toString();
        }
        return  sessionId;
    }
}
