package com.challenge.stock.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    private Utils() {
    }

    public static String decodeName(String name) {
        try {
            return URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<String> splitIds(String ids){
        List<String> list = new ArrayList<>();
        try {
            for (String id : ids.split(",")) {
                list.add(id);
            }
        } catch(NumberFormatException e) {
            return Collections.emptyList();
        }
        return list;
    }

}
