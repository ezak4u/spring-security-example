/**
 * 
 */
package com.example.demo.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.auth.ApplicationUserService;
import com.example.demo.jwt.JWTUsernamePasswordAuthenticationFilter;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenVerifier;

/**
 * @author ezak4
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter{
    
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;   
    
    /**
     * @param passwordEncoder
     * @param applicationUserService
     */
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,ApplicationUserService applicationUserService,SecretKey secretKey, JwtConfig jwtConfig ) {
        this.passwordEncoder        = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfig = jwtConfig; 
        this.secretKey = secretKey;
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            //.and()
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JWTUsernamePasswordAuthenticationFilter(authenticationManager(),jwtConfig,secretKey))
            .addFilterAfter(new JwtTokenVerifier(jwtConfig,secretKey), JWTUsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/","/css/*","/js/*").permitAll()
            .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
            //.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name())
            .anyRequest()
            .authenticated();
            //.and()
            //.httpBasic();
            /*.formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/courses", true)
            .and()
            .rememberMe()
                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                .key("securitykey")//MD5 hash encode key
                .rememberMeParameter("remember-me")
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessUrl("/login");*/
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }    
   
}
