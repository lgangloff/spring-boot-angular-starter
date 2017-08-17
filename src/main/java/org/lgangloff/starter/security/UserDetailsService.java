package org.lgangloff.starter.security;

import java.util.Optional;

import org.lgangloff.starter.domain.User;
import org.lgangloff.starter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();
        Optional<User> userFromDatabase =  userRepository.findOneByLogin(lowercaseLogin);
        
        User userDetails = userFromDatabase.orElseThrow(
        		() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
        
        //Enabled the user when try to authenticating
        userDetails.setEnabled(true);
        
        return userDetails;
    }
}
