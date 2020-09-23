/**
 * 
 */
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author ezak4
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter{
    
    private final PasswordEncoder passwordEncoder;
    
    /**
     * 
     */
    public ApplicationSecurityConfig( PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //
            .authorizeRequests()
            .antMatchers("/","/css/*","/js/*").permitAll()
            .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
            //.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name())
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
    }
    
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userDetail1 = User.builder()
                                    .username("ezak")
                                    .password(passwordEncoder.encode("password"))
                                    //.roles(ApplicationUserRole.STUDENT.name()) //ROLE_STUDENT
                                    .authorities(ApplicationUserRole.STUDENT.getGrantedAuthority())
                                    .build();
        UserDetails userDetail2 = User.builder()
                                    .username("muthu")
                                    .password(passwordEncoder.encode("password123"))
                                    //.roles(ApplicationUserRole.ADMIN.name()) //ROLE_ADMIN
                                    .authorities(ApplicationUserRole.ADMIN.getGrantedAuthority())
                                    .build();
        UserDetails userDetail3 = User.builder()
                                    .username("worldstar")
                                    .password(passwordEncoder.encode("password123"))
                                    //.roles(ApplicationUserRole.ADMINTRAINEE.name()) //ROLE_ADMINTRAINE
                                    .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthority())
                                    .build();
        return new InMemoryUserDetailsManager(userDetail1,userDetail2,userDetail3);
    }
}
