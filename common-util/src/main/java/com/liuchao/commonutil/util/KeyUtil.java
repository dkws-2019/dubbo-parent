package com.liuchao.commonutil.util;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class KeyUtil {

    public static PrivateKey getPrivateKey(String keytoolsPath,String keytoolsAlias,String keytoolsPassword){
        try {
            //keytoolsPath :D:/jwt/jwt.jks   keytoolsAlias:jwt  keytoolsPassword:264100
            //File file=new File("D:/jwt/jwt.jks");
            File file=new File(keytoolsPath);
            InputStream inputStream1=new FileInputStream(file);
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream1, keytoolsPassword.toCharArray());
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keytoolsAlias, keytoolsPassword.toCharArray());
            return privateKey;
           // PublicKey publicKey = keyStore.getCertificate(keytoolsAlias).getPublicKey();
        }catch (Exception e){
            e.printStackTrace();
        }
            return null;
    }

    public static PublicKey getPublicKey(String keytoolsPath,String keytoolsAlias,String keytoolsPassword){
        try {
            File file=new File(keytoolsPath);
            InputStream inputStream1=new FileInputStream(file);
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream1, keytoolsPassword.toCharArray());
            PublicKey publicKey = keyStore.getCertificate(keytoolsAlias).getPublicKey();
            return publicKey;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
