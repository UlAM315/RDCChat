package com.liangxiaolin.rdcchat.RDCChat1.util;

import java.util.UUID;

public final class UuidUtil {
    private UuidUtil(){}
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
    /**
     * 测试
     */
    public static void main(String[] args) {
        System.out.println(UuidUtil.getUuid());
        System.out.println(UuidUtil.getUuid());
        System.out.println(UuidUtil.getUuid());
        System.out.println(UuidUtil.getUuid());
    }
}
