package com.cate.typingthedictionary;

public class StringCleaner {

    public static String removeQuotations(String s) {
        return s.replaceAll("\"", "");
    }

    public static String removeBrackets(String s) {
        return s.replaceAll("\\[|\\]", "");
    }
}
