package com.Persolute.ElectronicLibrary.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author Persolute
 * @version 1.0
 * @description JWT Util
 * @email 1538520381@qq.com
 * @date 2025/01/15 13:39
 */
public class JWTUtil {
    private static final Long JWT_TTL = 15 * 24 * 60 * 60 * 1000L;
    private static final String JWT_KEY = "GraduateManagementSystem";

    /*
     * @author Persolute
     * @version 1.0
     * @description 生成UUID
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午1:42
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 创建JWT
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午1:59
     */
    public static String createJWT(String subject) {
        return getJwtBuilder(getUUID(), subject, null).compact();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 解析JWT
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午1:51
     */
    public static Claims paresJWT(String jwt) {
        SecretKey secretKey = getSecretKey();
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 构造JWT
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午1:58
     */
    private static JwtBuilder getJwtBuilder(String uuid, String subject, Long ttl) {
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        Date exp = new Date(currentTimeMillis + (ttl == null ? JWTUtil.JWT_TTL : ttl));

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = getSecretKey();

        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(exp);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 创建密钥
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午1:49
     */
    private static SecretKey getSecretKey() {
        byte[] decodeKey = Base64.getDecoder().decode(JWTUtil.JWT_KEY);
        return new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES");
    }
}
