package com.x_and_l.work.util;

public class StringUtil {
    // 判断两个字符串是否相等
    public static Boolean isEquals(String a,String b){
        char[] a1 = a.toCharArray();
        char[] b1 = b.toCharArray();
        if (a1.length != b1.length){
            return false;
        }
        for (int i = 0;i < a1.length;i++){
            if (a1[i] != b1[i]){
                return false;
            }
        }
        return true;
    }
    public static int length(String target){
        return target.length();
    }

}
