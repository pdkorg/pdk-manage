package com.pdk.manage.util;

import java.util.UUID;

/**
 * Created by hubo on 2015/9/2
 */
public class UUIDGenerator {

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }
    //获得指定数量的UUID
    public static String[] generateUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = generateUUID();
        }
        return ss;
    }

}
