package com.liuchao.dubbocustom.propertiesConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String keytoolsPath;
    private String keytoolsAlias;
    private String keytoolsPassword;
    private int expirationSeconds;

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
}
