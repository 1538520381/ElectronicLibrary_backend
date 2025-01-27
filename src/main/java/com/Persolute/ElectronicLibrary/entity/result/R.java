package com.Persolute.ElectronicLibrary.entity.result;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Persolute
 * @version 1.0
 * @description 全局通用返回类
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:55
 */
public class R extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static R success() {
        return success("成功");
    }

    public static R success(String msg) {
        R r = new R();
        r.put("code", 200);
        r.put("msg", msg);
        return r;
    }

    public static R error() {
        return error("服务器异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
