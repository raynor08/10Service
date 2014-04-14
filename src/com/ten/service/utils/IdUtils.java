package com.ten.service.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class IdUtils {
    private static final HashFunction HASH = Hashing.murmur3_128();

    /**
     * Generate user id from email address, facebook user id or google+ user id etc (by OAuth2)
     * 
     * @param key
     * @return
     */
    public static long generateUserId(String key) {
        return HASH.newHasher().putInt(key.length()).putString(key, Charsets.UTF_8).hash().asLong();
    }
}
