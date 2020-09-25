/**
 * 
 */
package com.example.demo.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.net.HttpHeaders;


/**
 * @author ezak4
 *
 */
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
   
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;
    
     /**
     * 
     */
    public JwtConfig() {
    }
    
    /**
     * @return the secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }
    /**
     * @param secretKey the secretKey to set
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    /**
     * @return the tokenPrefix
     */
    public String getTokenPrefix() {
        return tokenPrefix;
    }
    /**
     * @param tokenPrefix the tokenPrefix to set
     */
    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
    /**
     * @return the tokenExpirationAfterDays
     */
    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }
    /**
     * @param tokenExpirationAfterDays the tokenExpirationAfterDays to set
     */
    public void setTokenExpirationAfterDays(
    Integer tokenExpirationAfterDays) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }
    
        
    public String getAutherizationHeader() {
        return HttpHeaders.AUTHORIZATION; 
    } 
   
    
}
