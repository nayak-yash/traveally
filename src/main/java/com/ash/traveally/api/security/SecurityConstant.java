package com.ash.traveally.api.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstant {

    public static final long JWT_EXPIRATION = 86400000;

    // keys used with HMAC-SHA algorithms MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size)
    private static final String JWT_SECRET = "yourSecretKeyForSpringBootRestApiWillServeTraveallyApplication";
    public static final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
}
