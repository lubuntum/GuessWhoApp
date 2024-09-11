package com.lubuntum.guesswhoapp.utils;

import java.util.UUID;

public class SessionKeyGenerator {
    public static String generateStr(int count){
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, count);
    }
}
