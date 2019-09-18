package com.liuchao.commonutil.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    //加载jwt.jks文件
    private static InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("D:/jwt/jwt.jks");
    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    static {
        try {
            File file=new File("D:/jwt/jwt.jks");
            InputStream inputStream1=new FileInputStream(file);
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream1, "264100".toCharArray());
            privateKey = (PrivateKey) keyStore.getKey("jwt", "264100".toCharArray());
            publicKey = keyStore.getCertificate("jwt").getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateToken(String subject, int expirationSeconds, Map<String,Object> map,PrivateKey privateKey) {
        return Jwts.builder()
                .setClaims(map)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public static Claims parseToken(String token,PublicKey publicKey) {
        Claims claims= null;
        try {
             claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
        }
        return claims;
    }

    public static void main(String[] args) {
       /* Map<String,Object> map=new HashMap<String,Object>();
        map.put("name","abc");
        map.put("age",18);
        map.put("sex",1);
        String token = JwtUtil.generateToken("fdsafdsa", 2,map);
        String token1 = JwtUtil.parseToken(token);
        System.out.println(token);
        System.out.println(token1);*/
    }

}
