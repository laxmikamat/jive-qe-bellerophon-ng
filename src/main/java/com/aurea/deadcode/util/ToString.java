package com.aurea.deadcode.util;

import com.google.gson.Gson;

public class ToString {
    public static String toString(final Object obj) {
        return "[" + obj.getClass().getSimpleName() + "] " 
                + new Gson().toJson(obj);
    }
}
