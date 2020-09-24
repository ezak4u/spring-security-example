/**
 * 
 */
package com.example.demo.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.security.ApplicationUserRole;
import com.google.common.collect.Lists;

/**
 * @author ezak4
 *
 */
@Repository("fake")
public class FakeApplicationUserDetailDAOImpl implements ApplicationUserDAO {
    
    private final PasswordEncoder passwordEncoder;

    /**
     * @param passwordEncoder
     */
    @Autowired
    public FakeApplicationUserDetailDAOImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUserDetail> selectApplicationUserByUserName(
    String username) {
        return getUserDetails().stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }
    
    private List<ApplicationUserDetail> getUserDetails(){
        List<ApplicationUserDetail> userDetails = Lists.newArrayList(
            new ApplicationUserDetail(ApplicationUserRole.STUDENT.getGrantedAuthority(),passwordEncoder.encode("password"), "ezak", true, true, true, true),
            new ApplicationUserDetail(ApplicationUserRole.ADMIN.getGrantedAuthority(),passwordEncoder.encode("password"), "muthu", true, true, true, true),
            new ApplicationUserDetail(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthority(),passwordEncoder.encode("password"), "worldstar", true, true, true, true)
        );
        
        return userDetails;
        
    }
}
