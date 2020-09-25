/**
 * 
 */
package com.example.demo.jwt;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

/**
 * @author ezak4
 *
 */
@Configuration
public class JwtSecreteKey {
    private final JwtConfig jwtConfig;
    
    /**
     * 
     */
    public JwtSecreteKey(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    
    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }
}
