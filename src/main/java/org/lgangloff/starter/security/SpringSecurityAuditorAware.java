package org.lgangloff.starter.security;

import java.util.Optional;

import org.lgangloff.starter.config.Constants;
import org.lgangloff.starter.domain.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
    	String login = Optional.of(SecurityUtils.getCurrentUser())
    			.map(User::getLogin).orElse(Constants.SYSTEM_ACCOUNT);    	
    	return Optional.of(login);
    }
}
