package com.codeleven.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * JSON处理工具
 */
public class JsonUtil {
    /**
     * 判断是否为JSON
     *
     * @param json 字符串
     * @return true是，false为否
     */
    public static boolean isJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readTree(json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String prettyPrint(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T parseJson2Object(InputStream inputStream, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseJson2Object(String jsonStr, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param object
     * @return
     */
    public static String stringifyObject(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 转换JSON字符串为Map对象
     *
     * @param json 字符串
     * @return Map JSON对象
     */
    public static Map<String, Object> transferJson2Map(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换JSON字符串为Array对象
     *
     * @param json 字符串
     * @return List JSON数组
     */
    public static List<Object> transferJson2Array(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断JSON字符串解析后是否为JSON数组
     *
     * @param json 字符串
     * @return true，是JSON数组；反之，是JSON对象；抛出异常另当别论
     */
    public static boolean arrayJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readTree(json);
            return node.isArray();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除空白区域
     *
     * @return
     */
    public static String removeWhitespace(String json) {
        return Pattern.compile("[\\n|\\s]*").matcher(json).replaceAll("");
    }
}
