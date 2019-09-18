package com.liuchao.commonutil.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String keytoolsPath;
    private String keytoolsAlias;
    private String keytoolsPassword;
    private int expirationSeconds;
    private List<String> allows;

    public String getKeytoolsPath() {
        return keytoolsPath;
    }

    public void setKeytoolsPath(String keytoolsPath) {
        this.keytoolsPath = keytoolsPath;
    }

    public String getKeytoolsAlias() {
        return keytoolsAlias;
    }

    public void setKeytoolsAlias(String keytoolsAlias) {
        this.keytoolsAlias = keytoolsAlias;
    }

    public String getKeytoolsPassword() {
        return keytoolsPassword;
    }

    public void setKeytoolsPassword(String keytoolsPassword) {
        this.keytoolsPassword = keytoolsPassword;
    }

    public int getExpirationSeconds() {
        return expirationSeconds;
    }

    public void setExpirationSeconds(int expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }

    public List<String> getAllows() {
        return allows;
    }

    public void setAllows(List<String> allows) {
        this.allows = allows;
    }
}
