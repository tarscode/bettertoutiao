package com.bettertoutiao.util;

import com.alibaba.fastjson.JSONObject;
import com.bettertoutiao.model.News;

import java.util.List;

/**
 * Created by liuyang on 17/1/2.
 */
public class JsonUtil {

    public static String getJsonString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJsonString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJsonString(int code, List<News> msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }
}
