package com.kshfx.core.utils;

import java.util.UUID;

public class IdCreateUtil {
    private static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1);

    public static long getSnowId() {
        return idWorker.nextId();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}