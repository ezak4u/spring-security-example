/**
 * 
 */
package com.example.demo.auth;

import java.util.Optional;

/**
 * @author ezak4
 *
 */
public interface ApplicationUserDAO {
    
    Optional<ApplicationUserDetail> selectApplicationUserByUserName(String username);
}
