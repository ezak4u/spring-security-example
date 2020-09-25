/**
 * 
 */
package com.example.demo.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * @author ezak4
 *
 */
public class JwtTokenVerifier extends OncePerRequestFilter{
    
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;   
    

    /**
     * @param jwtConfig
     * @param secretKey
     */
    public JwtTokenVerifier(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {
        String autherizationHeader = request.getHeader(jwtConfig.getAutherizationHeader());
        
        if(Strings.isNullOrEmpty(autherizationHeader) || !autherizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = autherizationHeader.replaceAll(jwtConfig.getTokenPrefix(), "");
        
        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                                        .setSigningKey(secretKey)
                                        .parseClaimsJws(token);
            Claims body = jwsClaims.getBody();
            String username = body.getSubject();
            List<Map<String,String>> authorities = (List<Map<String,String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,simpleGrantedAuthorities);
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);                                        
            
        }catch(JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be truest",token));
        }
    }
    
    
}
