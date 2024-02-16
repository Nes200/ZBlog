package com.zlt.Utils;

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
 * @Authof: zlt
 * @Date: 2024-2-12 21:33
 */
public class JwtUtil {
    //有效期为(默认)
    public static final Long JWT_TTL=72*60*60*1000L;// 60 * 60 *1000  一个小时

    //设置密钥明文
    public static final String JWT_KEY="zlt";

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjYWM2ZDVhZi1mNjVlLTQ0MDAtYjcxMi0zYWEwOGIyOTIwYjQiLCJzdWIiOiJzZyIsImlzcyI6InNnIiwiaWF0IjoxNjM4MTA2NzEyLCJleHAiOjE2MzgxMTAzMTJ9.JVsSbkP94wuczb4QryQbAke3ysBDIL5ou8fWsbt_ebg";
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }

    /**
     * 生成jwt
     * @param subject token中要存放的数据(json格式)
     * @return
     */
    public static String createJWT(String subject){
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());//设置过期时间
        return builder.compact();
    }

    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id,String subject,Long ttlMillis){
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);//设置过期时间
        return builder.compact();
    }

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    private static JwtBuilder getJwtBuilder(String subject,Long ttlMillis,String uuid){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMillis=nowMillis+ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)//唯一的ID
                .setSubject(subject)//主题 可以是JSON数据
                .setIssuer("HF")//签发者
                .setIssuedAt(now)//签发时间
                .signWith(signatureAlgorithm,secretKey)//使用HS256对称算法签名
                .setExpiration(expDate);
    }



    /**
     * 生成加密后的密钥 secreKey
     * @return
     */
    public static SecretKey generalKey(){
        byte[] encodeKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKeySpec key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    /**
     * 解析
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
