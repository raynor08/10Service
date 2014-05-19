package com.ten.service.utils;

import java.util.concurrent.TimeUnit;

public class SecurityUtils {
    public static final long EXPIRATION_TIME = TimeUnit.MILLISECONDS.convert(90L, TimeUnit.DAYS);
}
