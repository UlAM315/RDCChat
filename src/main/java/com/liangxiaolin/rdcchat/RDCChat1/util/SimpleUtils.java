package com.liangxiaolin.rdcchat.RDCChat1.util;

import java.util.Random;

public final class SimpleUtils {

    public static String getCheckCode() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(str.length());
            //在str字符串中获取下标为index的字符
            char c = str.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }
}
